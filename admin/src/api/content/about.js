import request from '@/utils/request'

// 查询关于我配置
export function getAbout() {
  return request({
    url: '/content/about/get',
    method: 'get'
  })
}

// 保存关于我配置
export function saveAbout(data) {
  return request({
    url: '/content/about/save',
    method: 'put',
    data: data
  })
}
