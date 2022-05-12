// https://www.w3schools.com/ai/ai_google_chart.asp
function drawChart() {
    // Set Data
    // var data = google.visualization.arrayToDataTable([
    //     ['Price', 'Size'],[50, 10]
    // ]);
    const chart = new google.visualization.LineChart(document.getElementById('myChart'));
        
    
    // Set Options
    var options = {
        title: 'Salário Mínimo x Cesta Básica',
        hAxis: { title: 'Periodo' },
        vAxis: { title: 'Porcentagem custo da cesta sobre o salário' },
        legend: 'none'
    };

    fetch('/prices')
    .then(response => response.json())
    .then(data => {
        const test = [['Price', 'Size']]

        const prices = {}
        data.SALARY.forEach(element => {
            prices[element.month + "-" + element.year] = element
        });

        data.BFB.forEach(element => {
            if (element.year > 1996){
                const salary = prices[element.month + "-" + element.year]
                test.push([element.month + "-" + element.year, element.price * 100 / salary.price]);
            }
        });
        const dataChart = google.visualization.arrayToDataTable(test);
        chart.draw(dataChart, options);
    });
}