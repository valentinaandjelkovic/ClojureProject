(ns traffic.routes.home
  (:require [compojure.core :refer :all]
            [traffic.layout :as layout]))

(defn home-page
  []
  (layout/render "home.html"))



(defroutes home-routes
           (GET "/" [] (home-page)))
