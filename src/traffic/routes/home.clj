(ns traffic.routes.home
  (:require [compojure.core :refer :all]
            [traffic.layout :as layout]
            [traffic.models.report :as report]
            [buddy.auth :refer [authenticated?]]))

(defn home-page
  [session]
  (if (authenticated? session)
    (layout/render "menu-user.html"
                   {:types (report/get-all-types)})
    (layout/render "auth/login.html")))

(defroutes home-routes
           (GET "/" request (home-page (:session request))))
