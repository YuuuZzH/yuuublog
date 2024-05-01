import request from '@/utils/request'

// 删除分类
export function delLoginLog(id) {
  return request({
    url: '/log/' + id,
    method: 'delete'
  })
}


