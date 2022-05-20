(ns inflation.adapter.load_files
  (:require
   [clojure.string :as str]
   [datomic.api :as d]
   [inflation.db.datomic.db :as db]
   [inflation.model.price :as model]
   [clojure.data.csv :as csv]
   [clojure.java.io :as io]))


(defn insert-entity [entity connection]
  (prn entity)
  (d/transact connection [entity]))

(defn read-file [filename] 
  (with-open [reader (io/reader filename)]
    (doall
     (csv/read-csv reader))))

(defn create-price [[year-str month-str value-str] country type]
  (let [value (bigdec value-str)
        month (long (Float/valueOf month-str))
        year (long (Float/valueOf year-str))]
    (model/new-price country type value year month)))

(defn load-file [connection filename country type]
  (doseq [item (read-file filename)]
    (-> item 
        (first)
        (str/split #";")
        (create-price country type)
        (insert-entity connection))))

(defn load-data [connection]
  (load-file connection "resources/br_salary.csv" "BR" "SALARY")
  (load-file connection "resources/br_basic_food_basket.csv" "BR" "BFB")
  (load-file connection "resources/br_gasoline.csv" "BR"  "GASOLINE")
  (load-file connection "resources/br_electrical_power.csv" "BR"  "ELECTRICAL_POWER")
  (prn (db/all-prices (d/db connection)))
  )