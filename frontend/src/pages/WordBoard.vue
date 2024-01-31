<!-- 留言板页面 -->
<template>
    <div>
        <yuuu-nav></yuuu-nav>
        <div class="myBodyBg">
            <div style="position: absolute; display: flex;flex-direction: column;align-items: center; top: 30%;left: 45%; transform: translate(-50% ,-50%);
                z-index: 1;">
                <span class="wenzi" style="margin-bottom: 20px;font-weight: 700;">留言板</span>
                <input type="text" v-model="inputValue" style="font-family: 'CustomFont';font-size: 16px;"
                    placeholder="请输入内容" @keyup.enter="sendMessage">
            </div>
            <div class="yuuuDanmaku">
                <yuuu-danmaku ref="myDanmaku" v-model="danmus" autoplay useSlot loop :channels="30" isSuspend randomChannel
                    :speeds="Math.random() * 200 + 100" style="height: 100%; width:100%;position: relative;">
                    <template slot="dm" slot-scope="{ index, danmu }">
                        <div class="showContent">
                            <img style="width: 30px;height: 30px;border-radius: 50%;" :src="danmu.avatar">
                            <div style="text-align: left;margin-left: 5px;">
                                <span style="font-family: Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;color: black">{{ danmu.userName }}：{{ danmu.message }}</span>
                            </div>
                        </div>
                    </template>
                </yuuu-danmaku>
            </div>
            <div class="myBody">
                <div class="myBodyRight">
                    <yuuu-rightlist></yuuu-rightlist>
                </div>
            </div>
        </div>
    </div>
</template>
    
<script>
import header from '../components/header.vue'
import rightlist from '../components/rightlist.vue'
import vueDanmaku from 'vue-danmaku'
import request from "@/utils/request.js"
export default {
    name: 'WordBoard',
    data() { //选项 / 数据
        return {
            inputValue: '',
            danmus: [],
            randomValue: 1,
            timer: null
        }
    },
    methods: { //事件处理器
        sendMessage() {
            request.post('message/leaveMessages',
            {message: this.inputValue}).then(res =>{

                this.$refs.myDanmaku.add(res)
                this.inputValue = ''
            })
            
        },
    },
    components: { //定义组件
        'yuuu-nav': header,
        'yuuu-rightlist': rightlist,
        'yuuu-danmaku': vueDanmaku
    },
    mounted() {
        request.get('message/list').then(res => {
            this.danmus = res
        })
    },
    created() { //生命周期函数
        this.timer = setInterval(() => {
            this.randomValue = Math.random() * 200 + 100
        }, 500)
    },
    destroyed() {
        clearInterval(this.timer);
    },
}
</script>
    
<style scoped>
.myBodyBg {
    width: 100%;
    position: relative;
    height: 100%;
    background: url("../../static/img/message-bac.png") no-repeat;
    background-size: 100% 100%;
}

@font-face {
    font-family: 'CustomFont';
    src: url('../assets/font/YuanDanShaoNianDeJiuShu-2.ttf');
}

.yuuuDanmaku {
    position: absolute;
    top: 0;
    left: 0;
    height: 100%;
    width: 100%;
}

.showContent {
    display: flex;
    align-items: center
}

input {
    outline-style: none;
    border: 1px solid #ccc;
    border-radius: 3px;
    padding: 6px;
    width: 300px;
    font-size: 14px;
    font-family: "CustomFont";
}

input:focus {
    border-color: #66afe9;
    outline: 0;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 8px rgba(102, 175, 233, .6);
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 8px rgba(102, 175, 233, .6)
}

.wenzi {
    font-size: 30px;
    font-family: 'CustomFont';
}

.myBody {
    width: 80%;
    margin: 0 auto;
    display: flex;
    justify-content: right;
}


.myBodyRight {
    width: 30%;
}
</style>
    