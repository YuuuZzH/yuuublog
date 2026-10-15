// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/css/style.less'
// 设计系统：主题变量与通用动效（必须在其它样式之前，供后续变量覆盖）
import './assets/css/theme.less'
// Element UI 组件在暗色主题下的适配（必须在 element-ui 的 theme-chalk 之后）
import './assets/css/element-dark.less'
import { initTheme } from './utils/theme'
import reveal from './directives/reveal'
import store from './store'
import MavonEditor from 'mavon-editor'

Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(MavonEditor)

// 滚动入场动效指令：<div v-reveal>
Vue.directive('reveal', reveal)

// 主题必须在挂载前应用，避免首屏闪白
initTheme()

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  store
})
