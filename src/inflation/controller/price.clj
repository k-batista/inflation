(ns inflation.controller.price
  (:require
   [datomic.api :as d]
   [inflation.db.datomic.db :as db]))


(defn default [{:keys [db-connection]}] 
  (prn db-connection)
  {})

(defn prices [{connection :db-connection}]
  (db/all-prices (d/db connection)))