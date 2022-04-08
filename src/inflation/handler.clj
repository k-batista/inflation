(ns inflation.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as json]
            [ring.util.response :refer [response]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.reload :refer [wrap-reload]]
            [datomic.api :as d]
            [inflation.db.datomic.db :as db]))

(def conn (db/abre-conexao))

(defn teste []
  {})

(defn prices [] 
  (db/all-prices (d/db conn)))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/api" [] (response (teste)))
  (GET "/prices" [] (response (prices)))
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (json/wrap-json-params)
      (json/wrap-json-response)
      (wrap-defaults site-defaults)
      (wrap-reload)))