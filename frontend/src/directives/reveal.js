/**
 * v-reveal：元素进入视口时淡入上移（配合 theme.less 的 .reveal）
 * 用法：<div v-reveal> 或 <div v-reveal="120">（120 = 延迟毫秒）
 */
let observer = null

function ensureObserver() {
  if (observer) return observer
  if (typeof window === 'undefined' || typeof window.IntersectionObserver === 'undefined') return null
  observer = new window.IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          entry.target.classList.add('is-visible')
          observer.unobserve(entry.target)
        }
      })
    },
    { rootMargin: '0px 0px -8% 0px', threshold: 0.06 }
  )
  return observer
}

export default {
  inserted(el, binding) {
    el.classList.add('reveal')
    if (binding.value) el.style.transitionDelay = Number(binding.value) + 'ms'
    const ob = ensureObserver()
    if (ob) {
      ob.observe(el)
    } else {
      // 不支持 IntersectionObserver 时直接显示，避免内容不可见
      el.classList.add('is-visible')
    }
  },
  unbind(el) {
    if (observer) observer.unobserve(el)
  }
}
