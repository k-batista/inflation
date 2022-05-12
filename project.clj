(defproject inflation "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :repositories  [["clojars" {:url "https://clojars.org/repo/"}]]
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/data.csv "1.0.1"]
                 [compojure "1.6.1"]
                 [http-kit "2.5.0"]
                 [hiccup "1.0.5"]
                 [ring/ring-json "0.3.1"]
                 [ring/ring-devel "1.6.3"]
                 [ring/ring-defaults "0.3.2"]
                 [com.datomic/datomic-free "0.9.5697"]
                 [com.stuartsierra/component "1.1.0"]]
  :main ^:skip-aot inflation.server
  :profiles {:uberjar {:aot :all} :dev {:main inflation.server/-dev-main}})
