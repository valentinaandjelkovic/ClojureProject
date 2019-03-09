(ns traffic.models.city
  (:require [yesql.core :refer [defqueries]]
            [traffic.models.street :as street]))

(def db-spec (clojure.edn/read-string (slurp "config.edn")))

(defqueries "sql/city.sql"
            {:connection db-spec})
