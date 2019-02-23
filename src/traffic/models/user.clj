(ns traffic.models.user
  (:require [yesql.core :refer [defqueries]]))

(def db-spec (clojure.edn/read-string (slurp "config.edn")))

(defqueries "sql/user.sql"
            {:connection db-spec})
