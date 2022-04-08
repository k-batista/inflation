(ns inflation.db.datomic.db
  (:require [datomic.api :as d]))

(def db-uri "datomic:dev://localhost:4334/inflation")

(defn connection []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn drop-db []
  (d/delete-database db-uri))

(def schema [{:db/ident       :price/country
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "Country"}
             {:db/ident       :price/type
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "Type of price"}
             {:db/ident       :price/value
              :db/valueType   :db.type/bigdec
              :db/cardinality :db.cardinality/one
              :db/doc         "Value"}
             {:db/ident       :price/year
              :db/valueType   :db.type/long
              :db/cardinality :db.cardinality/one
              :db/doc         "Year"}
             {:db/ident       :price/month
              :db/valueType   :db.type/long
              :db/cardinality :db.cardinality/one
              :db/doc         "month"}])

(defn create-schema [conn]
  (d/transact conn schema))

(defn all-prices [db]
  (d/q '[:find (pull ?entidade [*])
         :where [?entidade :price/year]] db))


