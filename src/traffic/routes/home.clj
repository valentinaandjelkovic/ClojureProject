(ns traffic.routes.home
  (:require [compojure.core :refer :all]
            [traffic.layout :as layout]
            [ring.util.response :as response]
            [traffic.validators.user-validator :as validator]
            [struct.core :as st]))

(defn home-page
  []
  (layout/render "home.html" {:title   "Nasloov "
                              :content "Ovo je sadrzaj home stranice"
                              :person  {
                                        :firstname "Marko"
                                        :lastname  "Markovic"
                                        }}))
(defn signup-page
  []
  (layout/render "auth/signup.html"))

(def message-schema
  [[:username
    st/required
    st/string
    ]
   [:password
    [st/required :message "This field is required"]
    st/string
    {:message  "Password must contain at least 6 characters"
     :validate #(> (count %) 6)}
    ]
   [:email
    st/required
    st/email
    ]])

(defn validate-message [params]
  (first (st/validate params message-schema)))

(defn signup-page-submit [params]
  (let [errors (validate-message params)]
    (if (empty? errors)
      (response/redirect "/login")
      (layout/render "auth/signup.html" (assoc params :errors errors)))))

(defroutes home-routes
           (GET "/" [] (home-page))
           (GET "/req" request (str request))
           (GET "/traffic/:name" [name] (str name))
           (GET "/traffic/:name/:surname" [name surname] (str name "<br> " surname))
           (GET "/test2/:name/:one/:second" [name & rest] (str name "<br>" rest))
           (GET "/signup" [] (signup-page))
           (POST "/signup" [& form] (signup-page-submit form))
           (GET "/login" [] (str "Log in ")))
