/**
 * 无封面文章：按标题哈希生成稳定的渐变封面，避免列表里出现大片单调空白
 */
function hashString(str) {
  let h = 5381
  for (let i = 0; i < str.length; i++) {
    h = ((h << 5) + h + str.charCodeAt(i)) >>> 0
  }
  return h
}

export function coverStyle(title) {
  const h = hashString(title || 'yuuu')
  const hue = h % 360
  const hue2 = (hue + 35 + ((h >> 8) % 60)) % 360
  return {
    background: 'linear-gradient(135deg, hsl(' + hue + ', 72%, 62%) 0%, hsl(' + hue2 + ', 74%, 52%) 100%)'
  }
}

export function coverInitial(title) {
  const t = (title || '').trim()
  return t ? t.charAt(0).toUpperCase() : '文'
}
