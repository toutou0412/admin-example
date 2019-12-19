mainTemplateLayui.use(["index", "carousel"], function () {
    var layuiSelector = layui.$, _carousel = (layui.admin, layui.carousel), _element = layui.element,
        _device = layui.device();
    layuiSelector(".layadmin-carousel").each(function () {
        var _this = layuiSelector(this);
        _carousel.render({
            elem: this,
            width: "100%",
            arrow: "none",
            interval: _this.data("interval"),
            autoplay: _this.data("autoplay") === !0,
            trigger: _device.ios || _device.android ? "click" : "hover",
            anim: _this.data("anim")
        })
    }), _element.render("progress")
});
mainTemplateLayui.use(['index', "carousel", 'echarts'], function () {
    var layuiSelector = layui.$, _echart = (layui.carousel, layui.echarts), normlineInit = [], normlineData = [{
        title: {text: "未来一周气温变化", subtext: "纯属虚构"},
        tooltip: {trigger: "axis"},
        legend: {data: ["最高气温", "最低气温"]},
        calculable: !0,
        xAxis: [{type: "category", boundaryGap: !1, data: ["周一", "周二", "周三", "周四", "周五", "周六", "周日"]}],
        yAxis: [{type: "value", axisLabel: {formatter: "{value} °C"}}],
        series: [{
            name: "最高气温",
            type: "line",
            data: [11, 11, 15, 13, 12, 13, 10],
            markPoint: {data: [{type: "max", name: "最大值"}, {type: "min", name: "最小值"}]},
            markLine: {data: [{type: "average", name: "平均值"}]}
        }, {
            name: "最低气温",
            type: "line",
            data: [1, -2, 2, 5, 3, 2, 0],
            markPoint: {data: [{name: "周最低", value: -2, xAxis: 1, yAxis: -1.5}]},
            markLine: {data: [{type: "average", name: "平均值"}]}
        }]
    }], normlineDiv = layuiSelector("#LAY-index-normline").children("div"), normlineEChart = function (e) {
        normlineInit[e] = _echart.init(normlineDiv[e], layui.echartsTheme), normlineInit[e].setOption(normlineData[e]), window.onresize = normlineInit[e].resize
    };
    normlineDiv[0];
    normlineEChart(0);
    var heaplineInit = [], heaplineData = [{
        tooltip: {trigger: "axis"},
        legend: {data: ["邮件营销", "联盟广告", "视频广告", "直接访问", "搜索引擎"]},
        calculable: !0,
        xAxis: [{type: "category", boundaryGap: !1, data: ["周一", "周二", "周三", "周四", "周五", "周六", "周日"]}],
        yAxis: [{type: "value"}],
        series: [{
            name: "邮件营销",
            type: "line",
            stack: "总量",
            data: [120, 132, 101, 134, 90, 230, 210]
        }, {
            name: "联盟广告",
            type: "line",
            stack: "总量",
            data: [220, 182, 191, 234, 290, 330, 310]
        }, {
            name: "视频广告",
            type: "line",
            stack: "总量",
            data: [150, 232, 201, 154, 190, 330, 410]
        }, {
            name: "直接访问",
            type: "line",
            stack: "总量",
            data: [320, 332, 301, 334, 390, 330, 320]
        }, {
            name: "搜索引擎",
            type: "line",
            stack: "总量",
            data: [820, 932, 901, 934, 1290, 1330, 1320]
        }]
    }], heaplineDiv = layuiSelector("#LAY-index-heapline").children("div"), heaplineEChart = function (e) {
        heaplineInit[e] = _echart.init(heaplineDiv[e], layui.echartsTheme), heaplineInit[e].setOption(heaplineData[e]), window.onresize = heaplineInit[e].resize
    };
    heaplineDiv[0]
    heaplineEChart(0);
    var heapareaInit = [], heapareaData = [{
        tooltip: {trigger: "axis"},
        legend: {data: ["邮件营销", "联盟广告", "视频广告", "直接访问", "搜索引擎"]},
        calculable: !0,
        xAxis: [{type: "category", boundaryGap: !1, data: ["周一", "周二", "周三", "周四", "周五", "周六", "周日"]}],
        yAxis: [{type: "value"}],
        series: [{
            name: "邮件营销",
            type: "line",
            stack: "总量",
            itemStyle: {normal: {areaStyle: {type: "default"}}},
            data: [120, 132, 101, 134, 90, 230, 210]
        }, {
            name: "联盟广告",
            type: "line",
            stack: "总量",
            itemStyle: {normal: {areaStyle: {type: "default"}}},
            data: [220, 182, 191, 234, 290, 330, 310]
        }, {
            name: "视频广告",
            type: "line",
            stack: "总量",
            itemStyle: {normal: {areaStyle: {type: "default"}}},
            data: [150, 232, 201, 154, 190, 330, 410]
        }, {
            name: "直接访问",
            type: "line",
            stack: "总量",
            itemStyle: {normal: {areaStyle: {type: "default"}}},
            data: [320, 332, 301, 334, 390, 330, 320]
        }, {
            name: "搜索引擎",
            type: "line",
            stack: "总量",
            itemStyle: {normal: {areaStyle: {type: "default"}}},
            data: [820, 932, 901, 934, 1290, 1330, 1320]
        }]
    }], heapareaDiv = layuiSelector("#LAY-index-heaparea").children("div"), heapareaEchart = function (e) {
        heapareaInit[e] = _echart.init(heapareaDiv[e], layui.echartsTheme), heapareaInit[e].setOption(heapareaData[e]), window.onresize = heapareaInit[e].resize
    };
    heapareaDiv[0];
    heapareaEchart(0);
});