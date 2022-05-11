(ns inflation.adapter.web
  (:require [hiccup.core :refer [html]]))

(def open-chart
  "google.charts.load('current',{packages:['corechart']});
   google.charts.setOnLoadCallback(drawChart);")

(def home-page
  (html [:script {:src "https://www.gstatic.com/charts/loader.js"}]
             [:script {:src "chart.js"}]
             [:body
              [:div {:id "myChart" :style "width:100%; max-width:100%; height:500px;"}]
              [:script open-chart]]))