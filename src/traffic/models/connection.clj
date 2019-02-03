(ns traffic.models.connection)

(def db-spec {:classname   "com.mysql.cj.jdbc.Driver"
              :subprotocol "mysql"
              :subname     "//localhost/clojure"
              :user        "root"
              :password    ""})