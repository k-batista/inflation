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
  (let [prices (all-prices connection)]
    {:salary (filter #(= (:type %) "SALARY") prices)
     :bfb (filter #(= (:type %) "BFB") prices)}))