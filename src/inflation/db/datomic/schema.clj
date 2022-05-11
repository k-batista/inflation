(ns inflation.db.datomic.schema
  (:require
   [datomic.api :as d]))

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