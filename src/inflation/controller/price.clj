(ns inflation.controller.price
  (:require [datomic.api :as d]
            [inflation.db.datomic.db :as db]
            [inflation.model.price :refer [model->price]]))


(defn default [{:keys [db-connection]}] 
  (prn db-connection)
  {})

(defn prices [{connection :db-connection}]
  (->> (db/all-prices (d/db connection))
       (mapv #(model->price %))
       (sort-by :year)
       (group-by :type)))