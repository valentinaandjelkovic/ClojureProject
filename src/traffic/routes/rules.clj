(ns traffic.routes.rules
  (:require [buddy.auth :refer [authenticated?]]))

(defn admin-access [request]
  (and (authenticated? request)
       (= (:admin (:identity request)) 1)))

(defn authenticated-access [request]
  (authenticated? request))

(defn any-access [request]
  (not (authenticated? request)))

(defn access-error
  [request value]
  {:status  403
   :headers {}
   :body    "Not authorized"})

(def rules [{:pattern #"^/admin/.*"
             :handler admin-access}
            {:pattern #"^/login$"
             :handler any-access}
            {:pattern #"^/.*"
             :handler authenticated-access}])
