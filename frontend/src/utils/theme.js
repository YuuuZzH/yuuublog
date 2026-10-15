/**
 * 主题管理：light / dark / auto（跟随系统）
 * 结果写在 <html data-theme> 上，配色由 assets/css/theme.less 的变量接管
 */
const KEY = 'yuuu-theme'

export function getStoredTheme() {
  try {
    return localStorage.getItem(KEY) || 'auto'
  } catch (e) {
    return 'auto'
  }
}

export function setStoredTheme(theme) {
  try {
    localStorage.setItem(KEY, theme)
  } catch (e) {
    /* 忽略隐私模式下的写入失败 */
  }
}

function resolve(theme) {
  if (theme === 'auto') {
    const mq = window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)')
    return mq && mq.matches ? 'dark' : 'light'
  }
  return theme === 'dark' ? 'dark' : 'light'
}

export function applyTheme(theme) {
  const real = resolve(theme)
  const root = document.documentElement
  // 切换时临时开启全局过渡，让颜色变化是渐变而非跳变
  root.classList.add('theme-transition')
  root.setAttribute('data-theme', real)
  window.setTimeout(() => root.classList.remove('theme-transition'), 360)
  return real
}

export function currentResolvedTheme() {
  return resolve(getStoredTheme())
}

/**
 * 初始化：读取本地设置并在「auto」模式下监听系统变化
 */
export function initTheme() {
  applyTheme(getStoredTheme())
  if (window.matchMedia) {
    const mq = window.matchMedia('(prefers-color-scheme: dark)')
    const onChange = () => {
      if (getStoredTheme() === 'auto') applyTheme('auto')
    }
    if (mq.addEventListener) mq.addEventListener('change', onChange)
    else if (mq.addListener) mq.addListener(onChange)
  }
}
