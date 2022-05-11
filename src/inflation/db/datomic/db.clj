(ns inflation.db.datomic.db
  (:require [datomic.api :as d]))


(defn connection [db-uri]
  (d/create-database db-uri)
  (d/connect db-uri))

(defn drop-db [db-uri]
  (d/delete-database db-uri))

(defn all-prices [db]
  (d/q '[:find (pull ?entidade [*])
         :where [?entidade :price/year]] db))
