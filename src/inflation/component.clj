(ns inflation.component
  (:require [com.stuartsierra.component :as component]
            [ring.middleware.json :as json]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [inflation.db.datomic.schema :as schema]
            [inflation.db.datomic.db :as db]
            [inflation.service :as service]))

;; (def db-uri "datomic:dev://localhost:4334/inflation")
(def db-uri "datomic:mem://inflation")

(defn main [components]
  (-> (service/app-routes components)
      json/wrap-json-params
      json/wrap-json-response
      (wrap-defaults  site-defaults)))


(defrecord Database [db-uri connection]
  component/Lifecycle

  (start [_]
         (let [conn (db/connection db-uri)]
          (println ";; Starting database")
           (schema/create-schema conn)
           conn))

  (stop [this]
    (prn ";; Stopping database")
    (.close this)))

(defn new-database [db-uri]
  (map->Database {:db-uri db-uri}))

(defrecord WebServer [http-server components web-server-startup]
  component/Lifecycle
  (start [this]
    (assoc this :http-server
           (web-server-startup (main components))))
  (stop [this]
    (http-server)
    this))

(defn web-server
  "Returns a new instance of the web server component which
  creates its handler dynamically."
  [web-server-startup]
  (component/using (map->WebServer 
                    {:web-server-startup web-server-startup})
                   [:components]))

(defn system [web-server-startup]
  (component/system-map
   :db-connection (new-database db-uri)
   :server (web-server web-server-startup)
   :components (component/using 
                {} [:db-connection])))

(defn create-and-start-system! 
  [web-server-startup]
  (component/start (system web-server-startup)))
