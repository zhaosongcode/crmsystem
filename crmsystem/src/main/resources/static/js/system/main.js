$(function () {
    initClientAge();
    initClientAddress();
    initHistogram();
});

function initClientAge() {
    var age20 = parseFloat($("#clientAge20").val());
    var age2040 = parseFloat($("#clientAge2040").val());
    var age40 = parseFloat($("#clientAge40").val());
    //饼状图初始化
    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'histogramAge',
            plotBackgroundColor: "#f9ffde",
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '客户年龄分布'
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.point.name + '</b>: ' + this.percentage.toFixed(2) + ' %';
            }
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    formatter: function () {
                        return '<b>' + this.point.name + '</b>: ' + this.percentage.toFixed(2) + ' %';
                    }
                }
            }
        },
        series: [{
            type: 'pie',
            name: 'pie',
            data: [
                ['20周岁以下人群', age20],
                {
                    name: '20-40周岁人群',
                    y: age2040,
                    sliced: true,
                    selected: true
                },
                ['40周岁以上人群', age40]
            ]
        }],
        credits: {
            enabled: false     //不显示LOGO
        }
    });

}

function initClientAddress() {
    var city = parseFloat($("#clientAdd1").val());
    var city1 = parseFloat($("#clientAdd11").val());
    var city2 = parseFloat($("#clientAdd2").val());
    var city3 = parseFloat($("#clientAdd3").val());
    var city4 = parseFloat($("#clientAdd4").val());

    //饼状图初始化
    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'histogramAddress',
            plotBackgroundColor: "#f9ffde",
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '客户地址分布'
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.point.name + '</b>: ' + this.percentage.toFixed(2) + ' %';
            }
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    formatter: function () {
                        return '<b>' + this.point.name + '</b>: ' + this.percentage.toFixed(2) + ' %';
                    }
                }
            }
        },
        series: [{
            type: 'pie',
            name: 'pie',
            data: [
                {
                    name: '一线城市',
                    y: city,
                    sliced: true,
                    selected: true
                },
                ['新一线城市', city1],
                ['二线城市', city2],
                ['三线城市', city3],
                ['其他城市', city4]
            ]
        }],
        credits: {
            enabled: false     //不显示LOGO
        }
    });
}

/*曲线图*/
function initGraph() {
    var title = {
        text: '每月平均温度'
    };
    var subtitle = {
        text: 'Source: runoob.com'
    };
    var xAxis = {
        categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
            'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
    };
    var yAxis = {
        title: {
            text: 'Temperature (\xB0C)'
        }
    };
    var plotOptions = {
        line: {
            dataLabels: {
                enabled: true
            },
            enableMouseTracking: false
        }
    };
    var series= [{
        name: 'Tokyo',
        data: [7.0, 6.9, 9.5, 14.5, 18.4, 25.2, 26.5, 23.3, 18.3, 0]
    }, {
        name: 'London',
        data: [3.9, 4.2, 5.7, 8.5, 11.9, 17.0, 16.6, 14.2, 10.3, 0]
    }
    ];

    var json = {};

    json.title = title;
    json.subtitle = subtitle;
    json.xAxis = xAxis;
    json.yAxis = yAxis;
    json.series = series;
    json.plotOptions = plotOptions;
    $('#histogramGrapsh').highcharts(json);
}

/*柱状图*/
function initHistogram() {
    var chart = {
        type: 'column'
    };
    var title = {
        text: '每月客户流动情况一览'
    };
    var subtitle = {
        text: '2018年度'
    };
    var xAxis = {
        categories: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
        crosshair: true
    };
    var yAxis = {
        min: 0,
        title: {
            text: '人数 (位)'
        }
    };
    var tooltip = {
        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0"><b>{point.y:.0f} 位</b></td></tr>',
        footerFormat: '</table>',
        shared: true,
        useHTML: true
    };
    var plotOptions = {
        column: {
            pointPadding: 0.2,
            borderWidth: 0
        }
    };
    var credits = {
        enabled: false
    };

    var series= [{
        name: '新增客户',
        data: [parseInt($("#0").val()) ,parseInt($("#2").val()),parseInt($("#4").val()),parseInt($("#6").val()),parseInt($("#8").val()),parseInt($("#10").val()),parseInt($("#12").val()),parseInt($("#14").val()),parseInt($("#16").val()),parseInt($("#18").val()),parseInt($("#20").val()),parseInt($("#22").val())]
    }, {
        name: '流失客户',
        data: [parseInt($("#1").val()),parseInt($("#3").val()),parseInt($("#5").val()),parseInt($("#7").val()),parseInt($("#9").val()),parseInt($("#11").val()),parseInt($("#13").val()),parseInt($("#15").val()),parseInt($("#17").val()),parseInt($("#19").val()),parseInt($("#21").val()),parseInt($("#23").val())]
    }];

    var json = {};
    json.chart = chart;
    json.title = title;
    json.subtitle = subtitle;
    json.tooltip = tooltip;
    json.xAxis = xAxis;
    json.yAxis = yAxis;
    json.series = series;
    json.plotOptions = plotOptions;
    json.credits = credits;
    $('#histogramClient').highcharts(json);
}