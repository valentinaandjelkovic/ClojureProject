(ns traffic.routes.city
  (:require
    [compojure.core :refer :all]
    [traffic.models.city :as city]
    [traffic.models.street :as street]))

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

(defroutes city-routes
           (GET "/city/json" request (get-city request))
           (GET "/street/json" request (get-street request)))