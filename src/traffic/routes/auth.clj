(ns traffic.routes.auth
  (:require [compojure.core :refer :all]
            [traffic.layout :as layout]
            [ring.util.response :refer [response redirect]]
            [struct.core :as st]
            [traffic.models.user :as db]
            [crypto.password.bcrypt :as password]))

(def user-schema-register
  [[:username st/required st/string]
   [:first_name st/required st/string]
   [:last_name st/required st/string]
   [:password
    [st/required :message "This field is required"]
    st/string {:message  "Password must contain at least 6 characters"
               :validate #(> (count %) 6)}]
   [:email st/required st/email]])

(def user-schema-login
  [[:username st/required st/string]
   [:password st/required st/string]])

(defn validate-user-register? [user]
  (first (st/validate user user-schema-register)))

(defn validate-user-login? [user]
  (first (st/validate user user-schema-login)))

(defn save-user!
  [user]
  (let [new-user (->> (password/encrypt (:password user))
                      (assoc user :password)
                      db/insert-user!)]))

(defn login-page
  []
  (layout/render "auth/login.html"))

(defn signup-page
  []
  (layout/render "auth/signup.html"))

(defn find-user [params]
  (let [errors (validate-user-login? params)]
    (if (empty? errors)
      (first (db/get-user-by-username (select-keys params [:username]))))))

(defn signup-page-submit [params]
  (let [errors (validate-user-register? params)]
    (if (empty? errors)
      (do
        (save-user! (assoc params :admin false))
        (redirect "/login"))
      (layout/render "auth/signup.html" (assoc params :errors errors)))))


(defn login-page-submit [{:keys [params session]}]
  (let [user (find-user params)]
    (if (and user (password/check (:password params)
                                  (:password user)))
      (assoc (redirect "/") :session (assoc session :identity user))
      (layout/render "auth/login.html" (assoc params :errors "The provided username and/or password are incorrect.")))))

(defn logout [request]
  (-> (redirect "/login")
      (assoc :session {})))

(defroutes auth-routes
           (GET "/signup" [] (signup-page))
           (POST "/signup" [& form] (signup-page-submit form))
           (GET "/login" [] (login-page))
           (POST "/login" request (login-page-submit request))
           (GET "/logout" request (logout request)))