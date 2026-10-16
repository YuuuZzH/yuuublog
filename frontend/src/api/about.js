import request from '@/utils/request'

// 查询关于我配置（前台匿名接口）
export function getAbout(query) {
    return request({
        url: '/about/getAbout',
        method: 'get',
        headers: {
            isToken: false
        },
        params: query
    })
}
