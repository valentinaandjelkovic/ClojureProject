(ns traffic.routes.user
  (:require [compojure.core :refer :all]
            [traffic.layout :as layout]
            [ring.util.response :refer [response redirect]]
            [struct.core :as st]
            [traffic.models.user :as db]
            [crypto.password.bcrypt :as password]))

(def user-schema-update
  [[:first_name st/required st/string]
   [:last_name st/required st/string]
   [:email st/required st/email]])

(def user-schema-update-password
  [[:repeat_password
    [st/identical-to :new_password :message "Repeat password does not match with new password."]]
   [:new_password
    [st/required :message "New password is required"]
    st/string {:message  "New password must contain at least 6 characters."
               :validate #(> (count %) 6)}]])

(defn find-user-by-id [session]
  (first (db/get-user-by-id {:id (get-in session [:identity :id])})))

(defn user-page
  [session]
  (if (layout/is-authenticated? session)
    (layout/render "user.html" (find-user-by-id session))
    (redirect "/login")))

(defn validate-user? [user]
  (first (st/validate user user-schema-update)))

(defn validate-user-password? [user]
  (first (st/validate user user-schema-update-password)))

(defn user-page-submit [{:keys [params session]}]
  (if (layout/is-authenticated? session)
    (let [errors (validate-user? params)]
      (if (empty? errors)
        (let [rows (->> (assoc params :id (get-in session [:identity :id]))
                        db/update-user!)]
          (if (= rows 1)
            (layout/render "user.html"
                           (assoc (find-user-by-id session)
                             :message {:type "success" :message "User information was successfully updated."}))
            (layout/render "user.html" {assoc params :message {:type "danger" :message "User information was not updated, try again!"}})))
        (layout/render "user.html" (assoc params :errors errors))))
    (redirect "/login")))

(defn update-password! [params]
  (->> {:id (params :id) :password (password/encrypt (:new_password params))}
       db/update-password!))

(defn change-user-password [{:keys [params session]}]
  (if (layout/is-authenticated? session)
    (let [user (find-user-by-id session)]
      (if (and user (password/check (:password params)
                                    (:password user)))
        (let [errors (validate-user-password? params)]
          (if (empty? errors)
            (do (update-password! (assoc params :id (user :id)))
                {:status  200
                 :headers {"Content-Type" "application/json"}
                 :body    {:message "Password successfully changed!"}})
            {:status  404
             :headers {"Content-Type" "application/json"}
             :body    (into [] errors)}))
        {:status  404
         :headers {"Content-Type" "application/json"}
         :body    (into [] {:password "Current password is wrong, try again!"})}))
    (redirect "/login")))

(defroutes user-routes
           (GET "/user" request (user-page (:session request)))
           (POST "/user" request (user-page-submit request))
           (POST "/user/changePassword" request (change-user-password request)))