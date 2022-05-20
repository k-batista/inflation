(ns inflation.server
  (:require [com.stuartsierra.component :refer [stop]]
            [inflation.adapter.load_files :as load-files]
            [inflation.component :as component]
            [inflation.controller.price :refer [prices-by-date]]
            [org.httpkit.server :as server]
            [ring.middleware.reload :refer [wrap-reload]])
  (:gen-class))

(def system-port
  (Integer/parseInt (or (System/getenv "PORT") "3000")))

(defn -main
  "Production"
  [& args]
  (let [port system-port]
    (println (str "[Production] - Running webserver at http:/127.0.0.1:" port "/"))
    (component/create-and-start-system! #(server/run-server % {:port port}))))

(defn -dev-main
  "Dev/Test (auto reload watch enabled)"
  [& args]
  (let [port system-port]
    (println (str "[Dev] - Running webserver at http:/127.0.0.1:" port "/"))
    (component/create-and-start-system! #(server/run-server (wrap-reload %) {:port port}))))

(defonce system* (atom nil))

(defn local-start []
  (reset! system* (-dev-main)))

(defn local-stop []
  (when-let [system @system*]
    (stop system)
    (reset! system* nil)))

(defn restart []
  (local-stop)
  (local-start))

(defn populate-db []
  (load-files/load-data (-> @system*
                            :components
                            :db-connection)))

(defn teste []
  (prices-by-date (-> @system* :components)))