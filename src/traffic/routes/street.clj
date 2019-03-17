(ns traffic.routes.street
  (:require
    [traffic.layout :as layout]
    [compojure.core :refer :all]
    [traffic.models.city :as city]
    [traffic.models.street :as street]
    [ring.util.response :refer [response redirect]]
    [struct.core :as st]))

(def street-schema-update
  [[:name [st/required :message "Name of street is required"]
    st/string]
   [:community
    [st/required :message "Street community is required"]
    st/string]])

(defn search-city
  [request]
  (let [cities (->> (:params request)
                    city/search-city)]
    cities))

(defn search-street
  [request]
  (let [streets (->> (:params request)
                     street/search-street)]
    streets))

(defn get-street-page [{:keys [params session]}]
  (if (layout/is-admin? session)
    (layout/render "streets.html" {:streets (street/get-all)
                                   :admin   (layout/is-admin? session)})
    (redirect "/login")))

(defn validate-street-data? [street]
  (first (st/validate street street-schema-update)))

(defn update-street [{:keys [params session]}]
  (if (layout/is-admin? session)
    (let [errors (validate-street-data? params)]
      (if (empty? errors)
        (do (street/update-street! params)
            {:status  200
             :headers {"Content-Type" "application/json"}
             :body    {:message "Street successfully changed!"}})
        {:status  404
         :headers {"Content-Type" "application/json"}
         :body    (into [] errors)}))
    (redirect "/login")))

(defn delete-street [{:keys [params session]}]
  (if (layout/is-admin? session)
    (let [user (street/find-by-id params)]
      (if (and user (= (street/delete-street! params) 1))
        {:status  200
         :headers {"Content-Type" "application/json"}
         :body    {:message "Street successfully deleted!"}}
        {:status  404
         :headers {"Content-Type" "application/json"}
         :body    (into [] "Street is not deleted, try again!")}))
    (redirect "/login")))

(defroutes city-routes
           (GET "/city/search" request (search-city request))
           (GET "/street/search" request (search-street request))
           (GET "/street" request (get-street-page request))
           (POST "/street/update" request (update-street request))
           (POST "/street/delete" request (delete-street request)))