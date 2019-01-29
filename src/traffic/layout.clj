(ns traffic.layout
  (:require [selmer.parser :as parser]
            [selmer.filters :as filters]
            [ring.util.http-response :refer [content-type ok]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]))

(parser/set-resource-path!  (clojure.java.io/resource "views"))
(parser/add-tag! :csrf-field (fn [_ _] (anti-forgery-field)))
(parser/cache-off!)


(defn render
  "renders the HTML template located relative to resources/views"
  [template & [params]]
  (content-type
    (ok
      (parser/render-file
        template
        (assoc params
          :page template
          :csrf-token *anti-forgery-token*)))
    "text/html; charset=utf-8"))