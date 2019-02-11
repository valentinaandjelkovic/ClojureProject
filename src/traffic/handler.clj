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
            [buddy.auth.accessrules :refer [wrap-access-rules success error]]
            [ring.middleware.session :refer [wrap-session]]
            [ring.middleware.params :refer [wrap-params]]
            [traffic.routes.rules :refer [rules access-error]]
            [buddy.auth :refer [authenticated?]]
            [buddy.auth.accessrules :refer [restrict]]))

(defn on-error [request response]
  {:status  403
   :headers {"Content-Type" "text/plain"}
   :body    (str "Access to " (:uri request) " is not authorized")})

(defn wrap-restricted [handler]
  (restrict handler {:handler authenticated?
                     :on-error on-error}))

(defn wrap-rule [handler]
  (-> handler
      (wrap-access-rules {:rules rules
                          :on-error on-error})))

(defroutes base-routes
           (route/not-found "Not Found")
           (route/resources "/"))

(def backend (session-backend))

(def app
  (-> (routes
        auth-routes home-routes report-routes base-routes
              (wrap-routes wrap-defaults api-defaults))
      (handler/site)
      ;(wrap-rule)
      (wrap-authentication backend)
      (wrap-authorization backend)
      (wrap-params)
      ;(wrap-flash)
      (wrap-webjars)
      (wrap-resource "public")))