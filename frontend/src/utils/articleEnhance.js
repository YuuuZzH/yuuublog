/**
 * 文章正文增强（作用于 v-html 渲染后的 DOM）
 *  - buildToc：为 h2~h4 补锚点 id 并抽取目录
 *  - enhanceCodeBlocks：代码块包装（语言标签 + 一键复制 + 超长折叠）
 *  - countWords：正文字数与预计阅读时长
 */

function slugId(index) {
  return 'sec-' + (index + 1)
}

export function buildToc(root) {
  if (!root) return []
  const heads = root.querySelectorAll('h2, h3, h4')
  const toc = []
  Array.prototype.forEach.call(heads, function (el, i) {
    const id = slugId(i)
    el.setAttribute('id', id)
    toc.push({
      id: id,
      text: (el.textContent || '').trim() || '小节 ' + (i + 1),
      level: parseInt(el.tagName.charAt(1), 10)
    })
  })
  return toc
}

function copyText(text) {
  if (navigator.clipboard && navigator.clipboard.writeText) {
    return navigator.clipboard.writeText(text)
  }
  return new Promise(function (resolve, reject) {
    const ta = document.createElement('textarea')
    ta.value = text
    ta.setAttribute('readonly', 'readonly')
    ta.style.position = 'fixed'
    ta.style.top = '-1000px'
    document.body.appendChild(ta)
    ta.select()
    ta.setSelectionRange(0, 99999)
    const ok = document.execCommand('copy')
    document.body.removeChild(ta)
    ok ? resolve() : reject(new Error('copy failed'))
  })
}

export function enhanceCodeBlocks(root) {
  if (!root) return
  const pres = root.querySelectorAll('pre')
  Array.prototype.forEach.call(pres, function (pre) {
    if (!pre.parentNode || pre.parentNode.classList.contains('code-block')) return

    const code = pre.querySelector('code')
    const langMatch = code ? /language-([\w+#-]+)/.exec(code.className || '') : null
    const lang = langMatch ? langMatch[1] : ''

    const wrap = document.createElement('div')
    wrap.className = 'code-block'
    pre.parentNode.insertBefore(wrap, pre)
    wrap.appendChild(pre)

    const bar = document.createElement('div')
    bar.className = 'code-bar'

    if (lang) {
      const tag = document.createElement('span')
      tag.className = 'code-lang'
      tag.textContent = lang.toUpperCase()
      bar.appendChild(tag)
    }

    const copy = document.createElement('button')
    copy.type = 'button'
    copy.className = 'code-copy'
    copy.textContent = '复制'
    copy.addEventListener('click', function () {
      const raw = (code || pre).innerText || (code || pre).textContent || ''
      copyText(raw).then(function () {
        copy.textContent = '已复制'
        copy.classList.add('is-done')
      }).catch(function () {
        copy.textContent = '复制失败'
      })
      setTimeout(function () {
        copy.textContent = '复制'
        copy.classList.remove('is-done')
      }, 1600)
    })
    bar.appendChild(copy)

    // 超长代码块默认折叠，避免正文被大段代码撑爆
    if (pre.scrollHeight > 520) {
      wrap.classList.add('is-collapsed')
      const toggle = document.createElement('button')
      toggle.type = 'button'
      toggle.className = 'code-toggle'
      toggle.textContent = '展开全部'
      toggle.addEventListener('click', function () {
        const collapsed = wrap.classList.toggle('is-collapsed')
        toggle.textContent = collapsed ? '展开全部' : '收起'
        if (collapsed) {
          const top = wrap.getBoundingClientRect().top + window.pageYOffset - 100
          window.scrollTo({ top: top, behavior: 'smooth' })
        }
      })
      bar.appendChild(toggle)
    }

    wrap.insertBefore(bar, pre)
  })
}

export function countWords(root) {
  if (!root) return { words: 0, minutes: 1 }
  const text = (root.innerText || root.textContent || '').trim()
  // 中文按字计，英文按词计
  const cn = (text.match(/[\u4e00-\u9fa5]/g) || []).length
  const en = (text.replace(/[\u4e00-\u9fa5]/g, ' ').match(/[A-Za-z0-9]+/g) || []).length
  const words = cn + en
  return { words: words, minutes: Math.max(1, Math.round(words / 320)) }
}
