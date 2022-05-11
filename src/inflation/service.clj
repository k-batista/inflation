(ns inflation.service
  (:require [compojure.core :refer [routes, GET]]
            [compojure.route :refer [not-found]]
            [ring.util.response :refer [response]]
            [inflation.controller.price :as price-controller]))


(defn app-routes [components]
  (routes (GET "/" [] "Hello World")
          (GET "/api" [] (response (price-controller/default components)))
          (GET "/prices" [] (response (price-controller/prices)))
          (not-found "Not Found")))