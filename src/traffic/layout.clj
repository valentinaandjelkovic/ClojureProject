(ns traffic.layout
  (:require [selmer.parser :as parser]
            [buddy.auth :refer [authenticated?]]
            [ring.util.http-response :refer [content-type ok]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]))

(parser/set-resource-path! (clojure.java.io/resource "views"))
(parser/add-tag! :csrf-field (fn [_ _] (anti-forgery-field)))
(parser/cache-off!)

(defn is-admin? [session]
  (and (authenticated? session)
       (= true (:admin (:identity session)))))

(defn is-authenticated? [session]
  (authenticated? session))

(defn render
  [template & [params]]
  (content-type
    (ok
      (parser/render-file
        template
        (assoc params
          :page template
          :csrf-token *anti-forgery-token*)))
    "text/html; charset=utf-8"))