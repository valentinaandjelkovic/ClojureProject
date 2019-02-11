(ns traffic.routes.reports
  (:require [compojure.core :refer :all]
            [traffic.layout :as layout]
            [ring.util.response :refer [response redirect]]
            [traffic.models.report :as report]
            [buddy.auth.accessrules :refer [restrict]]
            [traffic.routes.rules :refer [authenticated-access]]
            [buddy.auth :refer [authenticated?]]))

(defn reports-page
  [session]
  (if (authenticated? session)
    (layout/render "report/all.html"
                   {:reports (report/get-all)})
    (redirect "/login")))


(defn reports-by-type-page
  [id]
  (layout/render "report/all.html"
                 {:reports (report/get-reports-by-type id)}))

;(defn report-type-page
;  [session]
;  (if (authenticated? session)
;    (layout/render "report/menu-user.html"
;                   {:types (report/get-all-types)})
;    (redirect "/login")))

(defn is-admin [{user :identity :as req}]
  (contains? (apply hash-set (:admin user)) "1"))


(defroutes report-routes
           (GET "/reports" request (reports-page (:session request)))
           (GET "/reports/:id" [& id] (reports-by-type-page id)))