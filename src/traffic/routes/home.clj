(ns traffic.routes.home
  (:require [compojure.core :refer :all]
            [traffic.layout :as layout]
            [ring.util.response :as response]
            [struct.core :as st]
            [traffic.models.user :as db]
            [crypto.password.bcrypt :as password]))


(def user-schema
  [[:username st/required st/string]
   [:first_name st/required st/string]
   [:last_name st/required st/string]
   [:password
    [st/required :message "This field is required"]
    st/string {:message  "Password must contain at least 6 characters"
               :validate #(> (count %) 6)}]
   [:email st/required st/email]])

(defn validate-user? [user]
  (first (st/validate user user-schema)))

(defn save-user!
  [user]
  (let [new-user (->> (password/encrypt (:password user))
                      (assoc user :password)
                      db/insert-user!)]))

(defn home-page
  []
  (layout/render "home.html"))

(defn signup-page
  []
  (layout/render "auth/signup.html"))

(defn signup-page-submit [params]
  (let [errors (validate-user? params)]
    (if (empty? errors)
      (do
        (save-user! (assoc params :admin false))
        (response/redirect "/login"))
      (layout/render "auth/signup.html" (assoc params :errors errors)))))


(defroutes home-routes
           (GET "/" [] (home-page))
           (GET "/signup" [] (signup-page))
           (POST "/signup" [& form] (signup-page-submit form))
           (GET "/login" [] (str "Log in ")))
