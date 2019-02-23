(ns traffic.routes.reports
  (:require [compojure.core :refer :all]
            [traffic.layout :as layout]
            [ring.util.response :refer [response redirect]]
            [traffic.models.report :as report]
            [traffic.models.city :as city]
            [traffic.models.street :as street]
            [buddy.auth.accessrules :refer [restrict]]
            [buddy.auth :refer [authenticated?]]
            [struct.core :as st]))


(def report-schema
  [[:city [st/required :message "City is required"]]
   [:type [st/required :message "Type is required"]]
   [:street [st/required :message "Street is required"]]])

(defn validate-report? [report]
  (first (st/validate report report-schema)))

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

(defn add-report-page [session]
  (if (authenticated? session)
    (layout/render "report/add.html"
                   {:types (report/get-all-types)})
    (redirect "/login")))

(defn get-city
  [request]
  (let [cities (->> (:params request)
                    city/search-city)]
    cities))

(defn get-street
  [request]
  (let [streets (->> (:params request)
                     street/get-streets-by-city)]
    streets))

(defn save-report! [request]
  (let [report (merge (:params request) {:user (get-in request [:session :identity :id]) :date (new java.util.Date)})]
    (report/insert-report! report)))

(defn add-report-page-submit [request]
  (println "########### Request " request)
  (if (authenticated? (:session request))
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
           (GET "/reports/:id" [& id] (reports-by-type-page id))
           (GET "/city" request (get-city request))
           (GET "/street" request (get-street request)))