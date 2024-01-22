import store from '@/store';

// 导出一个函数
export function hasPermission(permission) {
  const per = store.state.user.permissions;
  return per.includes(permission);
}

// 将函数注册到 Vue 的原型链上
const install = function (Vue) {
  Vue.prototype.$hasPermission = hasPermission;
};

export default { install };