(ns traffic.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults api-defaults]]
            [traffic.routes.home :refer [home-routes]]
            [ring.middleware.webjars :refer [wrap-webjars]]
            [ring.middleware.flash :refer [wrap-flash]]
            [ring.middleware.resource :refer [wrap-resource]]))

(defroutes base-routes
           (route/not-found "Not Found")
           (route/resources "/"))

(def app
  (-> (routes home-routes base-routes
              (wrap-routes wrap-defaults api-defaults))
      (handler/site)
      (wrap-flash)
      (wrap-webjars)
      (wrap-resource "public")))