(ns inflation.model.price )

(defn new-price [country type value year month]
  {:price/country  country
   :price/type  type
   :price/value value
   :price/year year
   :price/month month})


(defn model->price [[element]]
  {:year (:price/year element)
   :month (:price/month element)
   :price (:price/value element)
   :type (:price/type element)})