(ns traffic.routes.weather
  (:require [compojure.core :refer :all]
            [buddy.auth :refer [authenticated?]]
            [traffic.layout :as layout]
            [traffic.models.weather :as weather]))


(defn get-weather-data [request]
  (let [weather-data (weather/get-weather (:params request))]
    {:status  200
     :headers {"Content-Type" "application/json"}
     :body    weather-data}))

(defn weather-page [request]
  (if (authenticated? (:session request))
    (layout/render "weather.html")))

(defroutes weather-routes
           (GET "/weather" request (weather-page request))
           (GET "/weatherData" request (get-weather-data request)))