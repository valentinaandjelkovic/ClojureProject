(ns traffic.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults api-defaults]]
            [traffic.routes.home :refer [home-routes]]
            [traffic.routes.auth :refer [auth-routes]]
            [ring.middleware.webjars :refer [wrap-webjars]]
            [ring.middleware.flash :refer [wrap-flash]]
            [ring.middleware.resource :refer [wrap-resource]]
            [traffic.routes.reports :refer [report-routes]]
            [buddy.auth.backends.session :refer [session-backend]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [ring.middleware.session :refer [wrap-session]]
            [ring.middleware.params :refer [wrap-params]]))


(defroutes base-routes
           (route/not-found "Not Found")
           (route/resources "/"))

(def backend (session-backend))

(def app
  (-> (routes auth-routes home-routes report-routes base-routes
              (wrap-routes wrap-defaults api-defaults))
      (handler/site)
      (wrap-authorization backend)
      (wrap-authentication backend)
      (wrap-session)
      (wrap-params)
      (wrap-flash)
      (wrap-webjars)
      (wrap-resource "public")))