(defproject traffic "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [compojure "1.6.1"]
                 [funcool/struct "1.3.0"]
                 [mysql/mysql-connector-java "8.0.12"]
                 [migratus "1.2.0"]
                 [metosin/ring-http-response "0.9.1"]
                 [ring/ring-defaults "0.3.2"]
                 [selmer "1.12.5"]
                 [ring-webjars "0.2.0"]
                 [org.webjars/bootstrap "4.2.1"]
                 [org.webjars/font-awesome "5.6.1"]
                 [org.webjars/jquery "3.3.1-1"]
                 [com.novemberain/validateur "2.5.0"]
                 [lib-noir "0.9.9"]
                 [funcool/struct "1.3.0"]]
  :plugins [[lein-ring "0.12.4"]
            [migratus-lein "0.7.0"]]
  :migratus {:store :database
             :migration-dir "migrations"
             :db {:classname "com.mysql.jdbc.Driver"
                  :subprotocol "mysql"
                  :subname "//localhost/clojure"
                  :user "root"
                  :password ""}}
  :ring {:handler traffic.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
