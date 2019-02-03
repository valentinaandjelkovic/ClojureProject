(ns traffic.models.user
  (:require [yesql.core :refer [defqueries]]))

(def db-spec {:classname   "com.mysql.cj.jdbc.Driver"
              :subprotocol "mysql"
              :subname     "//localhost/clojure"
              :user        "root"
              :password    ""})

(defqueries "sql/user.sql"
            {:connection db-spec})
