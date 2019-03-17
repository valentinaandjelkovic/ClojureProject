(ns traffic.routes.home
  (:require [compojure.core :refer :all]
            [traffic.layout :as layout]
            [traffic.models.report :as report]))

(defn home-page
  [session]
  (if (layout/is-authenticated? session)
    (layout/render "menu.html"
                   {:types (report/get-all-types)
                    :admin (layout/is-admin? session)})
    (layout/render "auth/login.html")))

(defroutes home-routes
           (GET "/" request (home-page (:session request))))
