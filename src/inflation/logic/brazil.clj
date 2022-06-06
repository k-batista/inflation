(ns inflation.logic.brazil)

(def ranges-income-tax
  [{:range 1 :range_start 0       :range_end 1903.98 :aliquota 0}
   {:range 2 :range_start 1903.98 :range_end 2826.65 :aliquota 0.075}
   {:range 3 :range_start 2826.65 :range_end 3751.06 :aliquota 0.15}
   {:range 4 :range_start 3751.06 :range_end 4664.68 :aliquota 0.225}
   {:range 5 :range_start 4664.68 :range_end 1000000 :aliquota 0.275}])

(def ranges-inss
  [{:range 1 :range_start 0       :range_end 1212.00 :aliquota 0.075}
   {:range 2 :range_start 1212.00 :range_end 2427.35 :aliquota 0.09}
   {:range 3 :range_start 2427.35 :range_end 3641.03 :aliquota 0.12}
   {:range 4 :range_start 3641.03 :range_end 7087.22 :aliquota 0.14}])

(defn tax-range [{:keys [range_start range_end aliquota]} income period]
  (let [range_start_period (* range_start period)
        range_end_period (* range_end period)
        amount_payable (- income range_start_period)
        max_amount_payable (- range_end_period range_start_period)]
    (if (> amount_payable max_amount_payable)
      (* aliquota max_amount_payable)
      (if (> amount_payable 0)
        (* aliquota amount_payable)
        0))))

(defn calculate-tax [range_tax income period]
  (->> range_tax
       (map #(tax-range % income period))
       (reduce +)
       ))

(defn income-tax
  ([income]
   (calculate-tax ranges-income-tax income 1))
  ([income period]
   (calculate-tax ranges-income-tax income period)))

(defn inss-tax 
  ([income]
   (calculate-tax ranges-inss income 1))
  ([income period]
   (calculate-tax ranges-inss income period)))

(prn (inss-tax 10000))
(prn (income-tax (- 10000 (inss-tax 10000))))