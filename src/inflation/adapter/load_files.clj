(ns inflation.adapter.load_files
  (:require
   [clojure.string :as str]
   [datomic.api :as d]
   [inflation.db.datomic.db :as db]
   [inflation.model.price :as model]
   [clojure.data.csv :as csv]
   [clojure.java.io :as io]))

(def conn (db/abre-conexao))

(db/cria-schema conn)

(defn execute [salary]
  (prn salary)
  (d/transact conn [salary]))

(defn read [filename] 
  (with-open [reader (io/reader filename)]
    (doall
     (csv/read-csv reader))))

(defn teste [item, country, type]
  (let [value (bigdec (nth item 2))
        month (long (Float/valueOf (nth item 1)))
        year (long (Float/valueOf (nth item 0)))]
    (model/new-price country type value year month)))

(defn read-file [filename country type]
  (doseq [item (read filename)]
    (-> item 
        (first)
        (str/split #";")
        (teste country type)
        (execute))))

(read-file "resources/br_salary.csv" "BR" "SALARY")
(read-file "resources/br_basic_food_basket.csv" "BR" "BFB")