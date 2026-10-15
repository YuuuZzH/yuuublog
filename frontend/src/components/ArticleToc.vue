<!-- 文章目录：宽屏（≥1661px）浮在正文左侧留白，窄屏作为正文上方的卡片 -->
<template>
  <transition name="toc-fade">
    <nav class="article-toc glass" v-if="toc && toc.length > 1">
      <div class="toc-head" @click="open = !open">
        <span class="toc-title">目录</span>
        <span class="toc-pct">{{ percent }}%</span>
        <i :class="['fa', open ? 'fa-chevron-up' : 'fa-chevron-down', 'toc-fold']"></i>
      </div>
      <div class="toc-track"><i :style="{ height: percent + '%' }"></i></div>
      <ul class="toc-list" v-show="open">
        <li v-for="item in toc" :key="item.id" :class="['toc-item', 'lv' + item.level, { active: activeId === item.id }]">
          <a :href="'#' + item.id" :title="item.text" @click.prevent="jump(item)">{{ item.text }}</a>
        </li>
      </ul>
    </nav>
  </transition>
</template>

<script>
export default {
  name: 'ArticleToc',
  props: {
    toc: { type: Array, default: function () { return [] } },
    container: { type: String, default: '.article-content' },
    offset: { type: Number, default: 96 }
  },
  data() {
    return {
      activeId: '',
      percent: 0,
      open: true,
      ticking: false
    }
  },
  watch: {
    toc: function () {
      this.$nextTick(this.onScroll)
    }
  },
  methods: {
    onScroll: function () {
      if (this.ticking) return
      this.ticking = true
      const self = this
      window.requestAnimationFrame(function () {
        self.ticking = false
        self.sync()
      })
    },
    sync: function () {
      const list = this.toc
      if (!list.length) return
      let current = list[0].id
      for (let i = 0; i < list.length; i++) {
        const el = document.getElementById(list[i].id)
        if (!el) continue
        if (el.getBoundingClientRect().top - this.offset <= 0) current = list[i].id
      }
      this.activeId = current

      const root = document.querySelector(this.container)
      if (root) {
        const top = root.getBoundingClientRect().top - this.offset
        const total = root.offsetHeight
        const p = total > 0 ? Math.round((-top / total) * 100) : 0
        this.percent = Math.min(100, Math.max(0, p))
      } else {
        const idx = list.findIndex(function (t) { return t.id === current })
        this.percent = Math.round(((idx + 1) / list.length) * 100)
      }
    },
    jump: function (item) {
      const el = document.getElementById(item.id)
      if (!el) return
      const top = el.getBoundingClientRect().top + window.pageYOffset - this.offset
      const reduce = window.matchMedia && window.matchMedia('(prefers-reduced-motion: reduce)').matches
      window.scrollTo({ top: top, behavior: reduce ? 'auto' : 'smooth' })
      this.activeId = item.id
    }
  },
  mounted() {
    window.addEventListener('scroll', this.onScroll, { passive: true })
    window.addEventListener('resize', this.onScroll)
    this.$nextTick(this.onScroll)
  },
  beforeDestroy() {
    window.removeEventListener('scroll', this.onScroll)
    window.removeEventListener('resize', this.onScroll)
  }
}
</script>

<style lang="less" scoped>
/* 默认（窄屏）：正文上方的卡片 */
.article-toc {
  position: relative;
  margin: 16px 0 6px;
  padding: 12px 14px;
  border-radius: var(--radius);
  box-shadow: var(--shadow-1);
  display: flex;
  flex-direction: column;
  overflow: hidden;

  .toc-head {
    display: flex;
    align-items: center;
    gap: 10px;
    cursor: pointer;

    .toc-title {
      font-size: 13px;
      font-weight: 700;
      letter-spacing: 2px;
      color: var(--text);
    }

    .toc-pct {
      font-size: 11px;
      color: var(--brand);
      font-variant-numeric: tabular-nums;
    }

    .toc-fold {
      margin-left: auto;
      font-size: 11px;
      color: var(--text-weak);
    }
  }

  .toc-track { display: none; }

  .toc-list {
    list-style: none;
    margin: 8px 0 0;
    padding: 0;
    max-height: 34vh;
    overflow-y: auto;
    scrollbar-width: thin;
  }

  .toc-item {
    line-height: 1.5;
    padding: 4px 0;

    a {
      display: block;
      font-size: 12.5px;
      color: var(--text-weak);
      text-decoration: none;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      transition: color .25s var(--ease);
    }

    &.lv3 a { padding-left: 10px; font-size: 12px; }
    &.lv4 a { padding-left: 20px; font-size: 11.5px; opacity: .9; }

    &:hover a { color: var(--text); }

    &.active a {
      color: var(--brand);
      font-weight: 600;
    }
  }
}

/* 宽屏：浮到正文左侧留白 */
@media (min-width: 1661px) {
  .article-toc {
    position: fixed;
    left: 16px;
    top: 50%;
    transform: translateY(-50%);
    width: 216px;
    max-height: 62vh;
    margin: 0;
    padding: 14px 8px 14px 14px;
    z-index: 60;

    .toc-head { cursor: default; margin-bottom: 10px; padding-right: 6px; }
    .toc-fold { display: none; }

    .toc-track {
      display: block;
      position: absolute;
      left: 6px;
      top: 44px;
      bottom: 14px;
      width: 2px;
      background: var(--border);
      border-radius: 2px;

      i {
        display: block;
        width: 100%;
        background: linear-gradient(180deg, var(--brand), var(--brand-2));
        border-radius: 2px;
        transition: height .2s var(--ease);
      }
    }

    .toc-list {
      max-height: none;
      padding: 0 6px 0 10px;

      &::-webkit-scrollbar { width: 4px; }
      &::-webkit-scrollbar-thumb { background: var(--border-strong); border-radius: 4px; }
    }

    .toc-item:hover a { transform: translateX(2px); }
  }
}

.toc-fade-enter-active,
.toc-fade-leave-active {
  transition: opacity .3s var(--ease);
}

.toc-fade-enter,
.toc-fade-leave-to {
  opacity: 0;
}

@media (prefers-reduced-motion: reduce) {
  .article-toc { transition: none; }
}
</style>
