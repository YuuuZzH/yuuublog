<template>
  <div @click.stop style="display: flex;height: 455px;flex-direction: column;
    justify-content: space-between;">
    <div class="chat-header">
      <h2>YuuuChat</h2>
      <p>我欲见你，何惧春秋<br>站长邮箱：***REMOVED***</p>
    </div>
    <div style="overflow-y: auto;max-height:390px" id="chatContent" ref="chatContent">
      <div v-for="message in messages" :key="message.chatTimeStamp" v-if="message">
        <div class="chat-message-me">
          <div class="message-me-asWhole-headPortrait">
            <img :src="userInfo.avatar.startsWith('http://qiniu.yuuu.online') ? userInfo.avatar : 'https' + userInfo.avatar" class="examineeFace_logo_style">
          </div>
          <div class="message-me-asWhole-right">
            <div class="message-me-asWhole-top">
              {{ userInfo.nickName }}
            </div>
            <div class="message-me">
              {{ message.query }}
            </div>
          </div>
        </div>
        <div class="chat-message-other">
          <div class="message-other-asWhole-headPortrait">
            <img src="../../static/img/yuuuchat.png" class="examineeFace_logo_style">
          </div>
          <div class="message-other-asWhole-right">
            <div class="message-other-asWhole-top">
              Yuuu
            </div>
            <div class="message-other">
              {{ message.answer }}
              <!-- 加载状态 -->
              <span v-if="message.loading"><i class="el-icon-loading"></i></span>
            </div>
          </div>
        </div>

      </div>
    </div>

    <div style="display: flex; align-items: center;width: 100%;">
      <el-input type="textarea" autosize placeholder="Chat With Yuuu" v-model="question" @keydown.native="handleKeyDown">
      </el-input>
      <el-button type="primary" icon="el-icon-position" circle size="mini" @click="sendMessage"></el-button>
    </div>
  </div>
</template>


<script>
import request from "@/utils/request.js"
import {
  Typeit
} from '../utils/plug.js'
export default {
  data() {
    return {
      messages: [],
      question: '',
      userInfo: {},
    };
  },
  methods: {
    sendMessage() {
      var that = this
      const requestBody = { question: this.question };
      const newMessage = { query: this.question, loading: true, answer: "" };
      // const newMessage = { query: this.question, answer: "" };
      this.messages.push(newMessage);
      this.question = '';  // 清空输入框
      request.post('/chat/chat-message', requestBody, { timeout: 20000 }).then(response => {
        newMessage.loading = false;
        const resp = response.answer
        for (var i = 0; i < resp.length; i++) {
          (function (i) {
            setTimeout(function () {
              that.messages[that.messages.length - 1].answer += resp[i];
            }, i * 100);
          })(i);
        }
      }).catch(error => {
        newMessage.loading = false;
        this.$message.error('网络异常，请稍后再试！');
        console.error("Error:", error);
      })
      this.scrollToBottom();
    },
    handleKeyDown(event) {
      if (event.key === "Enter" && !event.shiftKey) {
        event.preventDefault();
        this.sendMessage();
      } else if (event.key === "Enter" && event.shiftKey) {
        this.addNewLine();
      }
    },
    async getAllMessages() {
      try {
        const response = await request.get('/chat/messages');
        this.messages = response || [];
        this.userInfo = JSON.parse(localStorage.getItem('userInfo'));
        this.scrollToBottom();
      } catch (error) {
        console.error("Error:", error);
      }
    },
    scrollToBottom() {
      this.$nextTick(() => {
        if (this.$refs.chatContent) {
          this.$refs.chatContent.scrollTop = this.$refs.chatContent.scrollHeight;
        }
      });
    },
  },
  mounted() {
    this.getAllMessages()
  },
  updated() {
    this.scrollToBottom();
  },
};
</script>

<style scoped>
.chat-header {
  background-color: #f0f0f0;
  padding: 10px;
  text-align: center;
}

.chat-header h2 {
  margin: 0;
  font-size: 1.2em;
}

@font-face {
  font-family: "Content";
  src: url("../assets/font/AaWeiDuXinDong-NianFengAiNi/AaWeiDuXinDong-NianFengAiNi-2.ttf");
}

@font-face {
  font-family: "Title";
  src: url("../assets/font/ShangShouGuHuangTi/No.300-ShangShouGuHuangTi-2.ttf");
}

h2 {
  font-family: "Title";
}

p {
  font-family: "Content";
}

.chat-header p {
  margin: 0;
  color: #777;
}

.chat-message-other {
  display: flex;
  padding-left: 1rem;
  padding-top: 1rem;
}

.chat-message-me {
  display: flex;
  padding-right: 1rem;
  padding-top: 1rem;
  flex-direction: row-reverse;
}

.message-me-asWhole-headPortrait,
.message-other-asWhole-headPortrait {
  padding: 3px;
}

.message-me-asWhole-right,
.message-other-asWhole-right {
  display: flex;
  flex-direction: column;
  margin-left: 0.1rem;
}

.message-me-asWhole-top {
  padding: 3px;
  font-family: 微软雅黑;
  color: rgba(134, 144, 156, 1);
  text-align: right;
}

.message-other-asWhole-top {
  padding: 3px;
  font-family: 微软雅黑;
  color: rgba(134, 144, 156, 1);
  text-align: left;
}

.message-me,
.message-other {
  background-color: rgba(242, 243, 245, 1);
  max-width: 280px;
  word-wrap: break-word;
  word-break: break-all;
  display: inline-block;
  width: auto;
  padding: 6px 12px;
  border-radius: 4px;
}

.examineeFace_logo_style {
  border-radius: 50%;
  width: 30px;
  height: 30px;
}
</style>
