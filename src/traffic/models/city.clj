(ns traffic.models.city
  (:require [yesql.core :refer [defqueries]]))

(def db-spec (clojure.edn/read-string (slurp "config.edn")))

(defqueries "sql/city.sql"
            {:connection db-spec})
