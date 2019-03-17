(ns traffic.routes.reports
  (:require [compojure.core :refer :all]
            [traffic.layout :as layout]
            [ring.util.response :refer [response redirect]]
            [traffic.models.report :as report]
            [struct.core :as st]))


(def report-schema
  [[:city [st/required :message "City is required"]]
   [:type [st/required :message "Type is required"]]
   [:street [st/required :message "Street is required"]]])

(defn validate-report? [report]
  (first (st/validate report report-schema)))

(defn reports-page
  [session]
  (if (layout/is-authenticated? session)
    (layout/render "report/all.html"
                   {:reports (report/get-all)
                    :admin   (layout/is-admin? session)})
    (redirect "/login")))


(defn reports-by-type-page [request]
  (if (layout/is-authenticated? (:session request))
    (layout/render "report/all.html"
                   {:reports (report/get-reports-by-type (request :route-params))
                    :admin   (layout/is-admin? (:session request))})
    (redirect "/login")))

(defn add-report-page [session]
  (if (layout/is-authenticated? session)
    (layout/render "report/add.html"
                   {:types (report/get-all-types)
                    :admin (layout/is-admin? session)})
    (redirect "/login")))


(defn save-report! [request]
  (let [report (merge (:params request) {:user (get-in request [:session :identity :id]) :date (new java.util.Date)})]
    (report/insert-report! report)))

(defn add-report-page-submit [request]
  (if (layout/is-authenticated? (:session request))
    (let [errors (validate-report? (:params request))]
      (if (empty? errors)
        (if (> (save-report! request) 0) {:status  200
                                          :headers {"Content-Type" "application/json"}
                                          :body    {:message "Successfully saved report"}})
        {:status  404
         :headers {"Content-Type" "application/json"}
         :body    (into [] errors)}))
    (redirect "/login")))

(defroutes report-routes
           (GET "/reports" request (reports-page (:session request)))
           (GET "/reports/add" request (add-report-page (:session request)))
           (POST "/reports/add" request (add-report-page-submit request))
           (GET "/reports/:id" request (reports-by-type-page request)))