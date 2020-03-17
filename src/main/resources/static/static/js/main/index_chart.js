

function show_number_chart(){
    var data = {"api":"index_get_number"};
    $.ajax( {
        url:"/api_index_get_number",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,

        success: function(data){
            if (data.status == "1"){
                option = null;
                option = {
                    backgroundColor: '#2c343c',

                    title: {
                        text: '帖子数量统计',
                        left: 'center',
                        top: 5,
                        textStyle: {
                            color: '#ccc'
                        }
                    },

                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },

                    visualMap: {
                        show: false,
                        min: 80,
                        max: 600,
                        inRange: {
                            colorLightness: [0, 1]
                        }
                    },
                    series : [
                        {
                            name:'帖子数量',
                            type:'pie',
                            radius : '55%',
                            center: ['50%', '65%'],
                            data:data.data.sort(function (a, b) { return a.value - b.value; }),
                            roseType: 'radius',
                            label: {
                                normal: {
                                    textStyle: {
                                        color: 'rgba(255, 255, 255, 0.3)'
                                    }
                                }
                            },
                            labelLine: {
                                normal: {
                                    lineStyle: {
                                        color: 'rgba(255, 255, 255, 0.3)'
                                    },
                                    smooth: 0.2,
                                    length: 10,
                                    length2: 20
                                }
                            },
                            itemStyle: {
                                normal: {
                                    color: '#c23531',
                                    shadowBlur: 200,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            },

                            animationType: 'scale',
                            animationEasing: 'elasticOut',
                            animationDelay: function (idx) {
                                return Math.random() * 200;
                            }
                        }
                    ]
                };;
                if (option && typeof option === "object") {
                    myChart.setOption(option, true);
                    window.addEventListener("resize", function () {
                        myChart.resize();
                    });
                }
            }
        },
        error: function(){
            alert("连接服务器失败！");
        }
    });
}

function show_number_chart2(){
    var data = {"api":"index_get_number2"};
    $.ajax( {
        url:"/api_index_get_number2",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,

        success: function(data){
            if (data.status == "1"){
                option = null;
                option = {
                    title: {
                        text: '',
                        subtext: ''
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c}"
                    },
                    toolbox: {
                        feature: {
                            dataView: {readOnly: false},
                            restore: {},
                            saveAsImage: {}
                        }
                    },
                    legend: {
                        data: data.name
                    },
                    calculable: true,
                    series: [
                        {
                            name:'漏斗图',
                            type:'funnel',
                            left: '10%',
                            top: 60,
                            //x2: 80,
                            bottom: 60,
                            width: '80%',
                            // height: {totalHeight} - y - y2,
                            min: 0,
                            max: 100,
                            minSize: '0%',
                            maxSize: '100%',
                            sort: 'descending',
                            gap: 2,
                            label: {
                                show: true,
                                position: 'inside'
                            },
                            labelLine: {
                                length: 10,
                                lineStyle: {
                                    width: 1,
                                    type: 'solid'
                                }
                            },
                            itemStyle: {
                                borderColor: '#fff',
                                borderWidth: 1
                            },
                            emphasis: {
                                label: {
                                    fontSize: 20
                                }
                            },
                            data: data.data
                        }
                    ]
                };
                ;
                if (option && typeof option === "object") {
                    myChart2.setOption(option, true);
                    window.addEventListener("resize", function () {
                        myChart2.resize();
                    });
                }
            }
        },
        error: function(){
            alert("连接服务器失败！");
        }
    });
}

var dom = document.getElementById("ec_container");
var myChart = echarts.init(dom);
var app = {};
show_number_chart();



var dom2 = document.getElementById("ec2_container");
var myChart2 = echarts.init(dom2);
var app2 = {};
show_number_chart2();
