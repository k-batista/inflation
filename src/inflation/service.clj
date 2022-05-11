(ns inflation.service
  (:require [compojure.core :refer [routes, GET]]
            [compojure.route :refer [not-found, resources]]
            [ring.util.response :refer [response]]
            [inflation.controller.price :as price-controller]
            [inflation.adapter.web :as pages]))

(defn app-routes [components]
  (routes
   (resources "/")
   (GET "/" [] pages/home-page)
   (GET "/api" [] (response (price-controller/default components)))
   (GET "/prices" [] (response (price-controller/prices components)))
   (not-found "Not Found")))