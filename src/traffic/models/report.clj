(ns traffic.models.report
  (:require [yesql.core :refer [defqueries]]))

(def db-spec (clojure.edn/read-string (slurp "config.edn")))

(defqueries "sql/report.sql"
            {:connection db-spec})
