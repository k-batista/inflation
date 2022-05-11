(ns inflation.model.price)

(defn new-price [country type value year month]
  {:price/country  country
   :price/type  type
   :price/value value
   :price/year year
   :price/month month})