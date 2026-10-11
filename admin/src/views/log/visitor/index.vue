<template>
    <div class="my-container">
        <el-form :inline="true" label-width="80">
            <el-form-item label="时间选择">
                <el-switch style="display: block ;top: 9px;" v-model="nearly" active-color="rgb(107, 179, 41)"
                    inactive-color="#13ce66" @change="queryLoginLog" active-text="近三十天" inactive-text="近七天">
                </el-switch>
            </el-form-item>
        </el-form>
        <div ref="chart" style="position:absolute ;left: 20px; width: 800px; height: 400px;"></div>
        <div ref="chartBar" style="position:absolute ;left: 20px; bottom: 0px; width: 800px; height: 400px;"></div>
        <div ref="chartMap" style="position:absolute ;right: 20px; bottom: 30px; width: 900px; height: 700px;"></div>

    </div>
</template>

<script>
import request from '@/utils/request'
import * as echarts from 'echarts'
import 'echarts/theme/dark'; // 引入暗黑系主题
import "@/assets/china.js"

export default {
    name: 'VisitLog',
    data() {
        return {
            data: [],
            total: 0,
            nearly: 0, // 近一周
        }
    },
    methods: {
        queryLoginLog() {
            request.get(`/log/visitorLog/` + this.nearly,
            ).then(res => {
                this.data = res
                this.initChart()
                this.initChartMap()
                this.initChartBar()
            })
        },
        processData(data) {
            let dateMap = {}; // 用于存储每天的访问人数
            data.forEach(item => {
                let date = item.loginTime.split(' ')[0]; // 提取日期部分
                if (dateMap[date]) {
                    dateMap[date]++;
                } else {
                    dateMap[date] = 1;
                }
            });
            // 将 dateMap 转换为日期数组和访问人数数组
            let dates = Object.keys(dateMap).sort();
            let visitCounts = Object.values(dateMap);

            return {
                dates: dates,
                visitCounts: visitCounts
            };
        },
        initChart() {
            let myChart = echarts.init(this.$refs.chart, 'dark');
            let processedData = this.processData(this.data);
            myChart.setOption({
                title: {
                    text: '系统访问人数统计'
                },
                tooltip: {
                    trigger: 'axis',
                    formatter: '{b} : 【{c}】人访问'
                },
                xAxis: {
                    type: 'category',
                    data: processedData.dates
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    data: processedData.visitCounts,
                    type: 'line',
                    smooth: true
                }]
            });
        },
        initChartMap() {
            let myChart = echarts.init(this.$refs.chartMap, 'dark');

            // 处理地区访问数据，统计每个地区的访问次数
            let areaData = {};
            this.data.forEach(item => {
                if (item.address && item.address.startsWith('中国')) {
                    let area = item.address.split('-')[1]
                    let name = area.substring(0, area.length - 1)
                    if (name == "香港特别行政") {
                        name = "香港"
                    } else if (name == "澳门特别行政") {
                        name = "澳门"
                    } else if (name == "新疆维吾尔自治") {
                        name = "新疆"
                    } else if (name == "广西壮族自治") {
                        name = "广西"
                    } else if (name == "内蒙古自治") {
                        name = "内蒙古"
                    } else if (name == "宁夏回族自治") {
                        name = "宁夏"
                    } else if (name == "西藏自治") {
                        name = "西藏"
                    }
                    areaData[name] = (areaData[name] || 0) + 1;
                }
            });
            const provinces = [
                '北京', '天津', '河北', '山西', '内蒙古', '辽宁', '吉林', '黑龙江', '上海', '江苏', '浙江', '安徽', '福建', '江西', '山东',
                '河南', '湖北', '湖南', '广东', '广西', '海南', '重庆', '四川', '贵州', '云南', '西藏', '陕西', '甘肃', '青海', '宁夏', '新疆', '台湾',
                '香港', '澳门', '南海诸岛'
            ];
            // 格式化数据为地图系列所需的格式
            let seriesData = provinces.map(name => {
                return { name: name, value: areaData[name] || 0 };
            });

            let option = {
                title: {
                    text: '系统访问地区热力图(中国)'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{b}：{c}次访问'
                },
                visualMap: {
                    min: 0,
                    max: Math.max(...Object.values(areaData)),
                    left: 850,
                    bottom: 40,
                    showLabel: true,
                    text: ['多', '少'],
                    textStyle: {
                        color: 'orange'  // 将文本颜色设置为红色
                    },
                    inRange: {
                        color: ['#BEDAEE', '#ff5428'] // 根据访问次数设置颜色范围
                    }
                },
                series: [
                    {
                        name: '访问次数',
                        type: 'map',
                        map: 'china',
                        roam: true,
                        label: {
                            show: true
                        },
                        data: seriesData
                    }
                ],
                geo: {
                    map: 'china', // 地图名称，对应地图数据文件的文件名（不包括扩展名）
                    label: {
                        show: true // 显示省份名称
                    },
                    itemStyle: {
                        areaColor: '#323c48', // 地图背景颜色
                        borderColor: '#111' // 省份边界线颜色
                    }
                }
            };

            myChart.setOption(option);
        },
        initChartBar() {
            let myChart = echarts.init(this.$refs.chartBar, 'dark');

            // 处理非中国地区访问数据，统计每个国家的访问次数
            let countryData = {};
            this.data.forEach(item => {
                if (item.address && !item.address.startsWith('中国')) {
                    let country = item.address.split('-')[0];
                    countryData[country] = (countryData[country] || 0) + 1;
                }
            });

            // 格式化数据为柱状图系列所需的格式
            let seriesData = Object.keys(countryData).map(country => {
                return { name: country, value: countryData[country] };
            });

            let option = {
                title: {
                    text: '系统访问地区统计(国外)'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{b}：{c}次访问'
                },
                xAxis: {
                    type: 'value',
                    name: '人次'
                },
                yAxis: {
                    type: 'category',
                    name: '国家',
                    data: Object.keys(countryData)
                },
                series: [
                    {
                        name: '访问次数',
                        type: 'bar',
                        data: seriesData
                    }
                ]
            };

            myChart.setOption(option);
        }
    },
    mounted() {
        this.queryLoginLog()
    },
}
</script>
<style>
.my-container {
    background-color: #333333;
    height: 94.5vh;
    width: 100%;
}
</style>