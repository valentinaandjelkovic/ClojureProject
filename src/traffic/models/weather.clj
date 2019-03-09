(ns traffic.models.weather
  (:require [clj-http.client :as client]
            [cheshire.core :as json]))

(def config {:key     "1e2cde2652c0a9a9006560e14d3c83dd",
             :apiBase "https://api.darksky.net/forecast",
             :units   "si"})


(defn get-weather-data
  "Contact darksky for the data"
  [lat lng]
  (let [url (format "%s/%s/%s,%s?units=%s" (:apiBase config) (:key config) lat lng (:units config))
        response (client/get url)]
    (cond (= 200 (:status response))
          (json/parse-string (:body response)))))

(defn weather-report [params]
  (let [weather (get-weather-data (:lat params) (:lng params))]
    weather))

(defn get-weather
  [params]
  (if params
    (weather-report params)))