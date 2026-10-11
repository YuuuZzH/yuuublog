<template>
  <div class="bck">
    <div class="dashboard-container" id="#login-img">
      <div class="dashboard-text" style="font-weight: bolder">
        YuuuBlog管理系统
      </div>
      <div class="content">
        <div>
          纳尼？你怎么找到这里来的？！？
        </div>
        <div>
          既然被你发现了，那就尽情探讨吧！有什么问题尽管发送到我的邮箱：864326354@qq.com
        </div>
        <br>
        项目地址：<a href="https://gitee.com/yuuu-zehua">https://gitee.com/yuuu-zehua</a><br>
        前台地址：<a href="https://www.yuuu.online">www.yuuu.online</a>
        <br>
        <br>
        <div>
          项目简介：
          项目采用前后端分离的模式，前端使用Vue框架，后端使用SpringBoot框架。<br><br>
          前台主要有：
          <ul>
            <li>用户功能：用户注册；用户登录，支持Github第三方登录；修改密码</li>
            <li>文章功能：查看文章；点赞文章；评论文章；下载文章</li>
            <li>友链功能：申请友链；查看友链；删除友链</li>
            <li>YuuuChat：聊天机器人</li>
            <li>其他功能：登录信息展示；网络打赏</li>
            <li>注：部分功能需科学上网</li>
          </ul>
          后台主要有：
          <ul>
            <li>用户管理：用户新增；用户修改；用户删除；角色分配</li>
            <li>文章管理：文章新增；文章修改；文章删除；支持MarkDown格式的文本样式</li>
            <li>角色管理：角色新增；角色修改；角色授权等</li>
            <li>菜单管理：新增菜单；修改菜单；删除菜单等</li>
            <li>其他功能：其他功能的CRUD</li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name'
    ])
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }

  &-text {
    font-family: 'Times New Roman', Times, serif;
    font-size: 30px;
    line-height: 46px;
  }
}

@font-face {
  font-family: "Content";
  src: url("../../assets/font/KeLaiYinLanDeYeWan-2.ttf");
}
.content {
  font-family: "Content";
  font-size: large;
}

.bck {
  background-image: url("../../assets/images/backgroundImg.jpg");
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;

  /* 设置透明度为半透明 */
  position: fixed;

  width: 100%;
  height: 100%;
}
</style>
