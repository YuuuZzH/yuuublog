import request from '@/utils/request'

// 获取验证码图片和验证码代码
export function getCaptchaImg() {
    return new Promise((resolve, reject) => {
      request({
        url: '/captcha',
        headers: {
          isToken: false
        },
        
        method: 'get'
      }).then(response => {
        
        // 从响应数据中获取验证码图片Base64编码
        const captchaImageBase64 = response.captchaImageBase64;
        resolve(captchaImageBase64);
      }).catch(error => {
        console.error(error); // 打印错误信息
        reject(error);
      });
    });
  }