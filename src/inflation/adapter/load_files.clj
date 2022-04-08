(ns inflation.adapter.load_files
  (:require
   [clojure.string :as str]
   [datomic.api :as d]
   [inflation.db.datomic.db :as db]
   [inflation.model.price :as model]
   [clojure.data.csv :as csv]
   [clojure.java.io :as io]))

(def conn (db/connection))

(db/create-schema conn)

(defn insert-entity [salary]
  (prn salary)
  (d/transact conn [salary]))

(defn read-file [filename] 
  (with-open [reader (io/reader filename)]
    (doall
     (csv/read-csv reader))))

(defn create-price [item, country, type]
  (let [value (bigdec (nth item 2))
        month (long (Float/valueOf (nth item 1)))
        year (long (Float/valueOf (nth item 0)))]
    (model/new-price country type value year month)))

(defn load-file [filename country type]
  (doseq [item (read-file filename)]
    (-> item 
        (first)
        (str/split #";")
        (create-price country type)
        (insert-entity))))

(load-file "resources/br_salary.csv" "BR" "SALARY")
(load-file "resources/br_basic_food_basket.csv" "BR" "BFB")