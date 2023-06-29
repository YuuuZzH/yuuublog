<template>
  <div class="dashboard-container" id="#login-img">
    <div class="dashboard-text" style="font-weight: bolder">
      个人博客管理系统
    </div>
    <div>
      纳尼？你怎么找到这里来的？！？
    </div>
    <div>
      既然被你发现了，那就尽情探讨吧！有什么问题尽管发送到我的邮箱：***REMOVED*** 
    </div>
    <div>
      基于SpringBoot + MyBatis-Plus + SpringSecurity + Vue的个人博客管理系统。
    </div>
    <div>
      特别感谢三更老师的辛勤付出和无私奉献。
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
    font-size: 30px;
    line-height: 46px;
  }
}

</style>
