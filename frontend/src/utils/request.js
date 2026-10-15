import axios from 'axios'
import { Notification, MessageBox, Message } from 'element-ui'
import router from '@/router'
import store from '../store'
import { getToken } from '@/utils/auth'
import errorCode from '@/utils/errorCode'

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
//这个封装了axios

// 401 弹框去重标记：首页并发多个请求同时 401 时，只弹一次，避免 Element MessageBox 排队连弹
let isReloginShow = false

// 创建axios实例
const service = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: store.state.baseURL,
  // 超时
  timeout: 10000
})
// request拦截器
service.interceptors.request.use(config => {
  // 是否需要设置 token
  const isToken = (config.headers || {}).isToken === false
  if (getToken() && !isToken) {
    config.headers['token'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
  }
  // get请求映射params参数
  if (config.method === 'get' && config.params) {
    let url = config.url + '?'
    for (const propName of Object.keys(config.params)) {
      const value = config.params[propName]
      var part = encodeURIComponent(propName) + '='
      if (value !== null && typeof (value) !== 'undefined') {
        if (typeof value === 'object') {
          for (const key of Object.keys(value)) {
            if (value[key] !== null && typeof (value[key]) !== 'undefined') {
              const params = propName + '[' + key + ']'
              const subPart = encodeURIComponent(params) + '='
              url += subPart + encodeURIComponent(value[key]) + '&'
            }
          }
        } else {
          url += part + encodeURIComponent(value) + '&'
        }
      }
    }
    url = url.slice(0, -1)
    config.params = {}
    config.url = url
  }
  return config
}, error => {
  console.log(error)
  Promise.reject(error)
})

// 响应拦截器
service.interceptors.response.use(res => {
  // 未设置状态码则默认成功状态
  const code = res.data.code || 200
  // 获取错误信息
  const msg = errorCode[code] || res.data.msg || errorCode['default']
  if (code === 401) {
    // 已经在登录页时不弹框、不记录回跳地址，避免把 logUrl 覆盖成登录页自身
    if (!isReloginShow && router.currentRoute.path !== '/Login') {
      isReloginShow = true
      MessageBox.confirm('登录状态已过期，您可以继续留在该页面，或者重新登录', '系统提示', {
        confirmButtonText: '重新登录',
        cancelButtonText: '取消',
        type: 'warning'
      }
      ).then(() => {
        isReloginShow = false
        localStorage.setItem('logUrl', router.currentRoute.fullPath);
        router.push({
          path: '/Login',
          query: { login: 1 }
        });

      }).catch(() => {
        isReloginShow = false
      })
    }
    return Promise.reject('无效的会话，或者会话已过期，请重新登录。')
  } else if (code === 500) {
    Message({
      message: msg,
      type: 'error'
    })
    return Promise.reject(new Error(msg))
  } else if (code !== 200) {
    Notification.error({
      title: msg
    })
    return Promise.reject('error')
  } else {
    // 把字符串total 转换成 数字 total
    if (res.data.data && res.data.data.total) {
      res.data.data.total = parseInt(res.data.data.total)
    }
    return res.data.data

    // 检查是否存在 data 属性
    // if (res.data.hasOwnProperty('data')) {
    //   const data = res.data.data;
    //   // 检查是否存在 captchaImageBase64 属性
    //   if (data.hasOwnProperty('captchaImageBase64')) {
    //     // 把字符串total 转换成 数字 total
    //     if (data.total) {
    //       data.total = parseInt(data.total);
    //     }
    //     return data;
    //   }
  //   }
  //   // 如果无法获取到验证码数据，则抛出错误
  //   return Promise.reject(new Error('无法获取验证码数据'));
  // }
}
},
  error => {
    console.log('err' + error)
    let { message } = error
    if (message === 'Network Error') {
      message = '后端接口连接异常'
    } else if (message.includes('timeout')) {
      message = '系统接口请求超时'
    } else if (message.includes('Request failed with status code')) {
      message = '系统接口' + message.substr(message.length - 3) + '异常'
    }
    Message({
      message: message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service