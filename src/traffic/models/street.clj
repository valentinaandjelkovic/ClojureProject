(ns traffic.models.street
  (:require [yesql.core :refer [defqueries]]))

(def db-spec (clojure.edn/read-string (slurp "config.edn")))

(defqueries "sql/street.sql"
            {:connection db-spec})
