// https://www.w3schools.com/ai/ai_google_chart.asp
function drawChart() {
    // Set Data
    // var data = google.visualization.arrayToDataTable([
    //     ['Price', 'Size'],[50, 10]
    // ]);
    const chart = new google.visualization.LineChart(document.getElementById('myChart'));


    // Set Options
    var options = {
        title: 'Basic Salary vs Prices',
        hAxis: { title: 'Period' },
        vAxis: { title: 'Percentage prices on salary' },
        legend: 'none'
    };

    const keys = [{ key: 'BFB', factor: 1 },
    { key: 'GASOLINE', factor: 100 },
    { key: 'ELECTRICAL_POWER', factor: 1}]

    fetch('/prices-by-date')
        .then(response => response.json())
        .then(data => {
            const prices = [['Price', 'Basic Food Basket', '100l Gasoline', 'R$/MWh Energy']]

            for (const key in data) {
                if (Object.hasOwnProperty.call(data, key)) {
                    const element = data[key];
                    if(element['SALARY']){
                        const salary = element['SALARY'];
                        const line = [key]
                        for (const elementKey of keys) {
                            if (element[elementKey.key]) {
                                line.push((element[elementKey.key].price * elementKey.factor) * 100 / salary.price)
                            }
                        }
                        if (line.length === keys.length + 1) {
                            prices.push(line);
                        }
                    }
                }
            }
            const dataChart = google.visualization.arrayToDataTable(prices);
            chart.draw(dataChart, options);
        });
}