(ns inflation.controller.price
  (:require [datomic.api :as d]
            [inflation.db.datomic.db :as db]
            [inflation.model.price :refer [model->price]]))


(defn default [{:keys [db-connection]}] 
  (prn db-connection)
  {})

(defn all-prices [connection]
  (->> (db/all-prices (d/db connection))
       (mapv #(model->price %))
       (sort-by :year)))

(defn prices [{connection :db-connection}]
  (->> (all-prices connection)
       (group-by :type)))

(defn prices-by-date [{connection :db-connection}]
  (->> (all-prices connection)
       (group-by :date)
       (map (fn [[k v]] {k (group-by :type v)}))
       (into (sorted-map))))