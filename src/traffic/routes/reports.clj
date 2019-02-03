(ns traffic.routes.reports
  (:require [compojure.core :refer :all]
            [traffic.layout :as layout]
            [traffic.models.report :as report]))

(defn reports-page
  []
  (layout/render "report/all.html"
                 {:reports (report/get-all)}))

(defn report-type-page
  []
  (layout/render "report/home.html"
                 {:types (report/get-all-types)}))

(defroutes report-routes
           (GET "/reports" [] (reports-page))
           (GET "/report-type" [] (report-type-page)))