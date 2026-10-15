<!-- 图片灯箱：点击正文图片全屏查看，支持左右切换 / ESC 关闭 -->
<template>
  <transition name="lb">
    <div class="lightbox" v-if="visible" @click.self="close">
      <button class="lb-btn lb-close" type="button" aria-label="关闭" @click="close">×</button>
      <button class="lb-btn lb-prev" type="button" aria-label="上一张" v-if="list.length > 1" @click.stop="prev">‹</button>
      <div class="lb-stage">
        <img class="lb-img" :src="list[index]" :key="list[index]" alt="" />
      </div>
      <button class="lb-btn lb-next" type="button" aria-label="下一张" v-if="list.length > 1" @click.stop="next">›</button>
      <div class="lb-count" v-if="list.length > 1">{{ index + 1 }} / {{ list.length }}</div>
    </div>
  </transition>
</template>

<script>
export default {
  name: 'ImageLightbox',
  props: {
    visible: { type: Boolean, default: false },
    list: { type: Array, default: function () { return [] } },
    index: { type: Number, default: 0 }
  },
  methods: {
    close: function () { this.$emit('update:visible', false) },
    prev: function () {
      const n = this.list.length
      if (!n) return
      this.$emit('update:index', (this.index - 1 + n) % n)
    },
    next: function () {
      const n = this.list.length
      if (!n) return
      this.$emit('update:index', (this.index + 1) % n)
    },
    onKey: function (e) {
      if (e.key === 'Escape') this.close()
      else if (e.key === 'ArrowLeft') this.prev()
      else if (e.key === 'ArrowRight') this.next()
    }
  },
  watch: {
    visible: function (v) {
      document.body.style.overflow = v ? 'hidden' : ''
      if (v) window.addEventListener('keydown', this.onKey)
      else window.removeEventListener('keydown', this.onKey)
    }
  },
  beforeDestroy() {
    document.body.style.overflow = ''
    window.removeEventListener('keydown', this.onKey)
  }
}
</script>

<style lang="less" scoped>
.lightbox {
  position: fixed;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  z-index: 999;
  background: rgba(8, 12, 22, .88);
  -webkit-backdrop-filter: blur(8px);
  backdrop-filter: blur(8px);
  display: flex;
  align-items: center;
  justify-content: center;
}

.lb-stage {
  max-width: 88vw;
  max-height: 86vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

.lb-img {
  max-width: 88vw;
  max-height: 86vh;
  border-radius: var(--radius-sm);
  box-shadow: 0 24px 70px rgba(0, 0, 0, .55);
  animation: lbPop .28s var(--ease);
}

@keyframes lbPop {
  from { opacity: 0; transform: scale(.94); }
  to { opacity: 1; transform: none; }
}

.lb-btn {
  position: absolute;
  border: 1px solid rgba(255, 255, 255, .22);
  background: rgba(255, 255, 255, .1);
  color: #fff;
  border-radius: 50%;
  cursor: pointer;
  font-size: 26px;
  line-height: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background .25s var(--ease), transform .25s var(--ease);

  &:hover {
    background: rgba(255, 255, 255, .22);
    transform: scale(1.08);
  }
}

.lb-close { top: 24px; right: 28px; width: 44px; height: 44px; font-size: 28px; }
.lb-prev { left: 3vw; width: 52px; height: 52px; }
.lb-next { right: 3vw; width: 52px; height: 52px; }

.lb-count {
  position: absolute;
  bottom: 26px;
  left: 50%;
  transform: translateX(-50%);
  color: rgba(255, 255, 255, .82);
  font-size: 13px;
  letter-spacing: 1px;
}

.lb-enter-active, .lb-leave-active { transition: opacity .25s var(--ease); }
.lb-enter, .lb-leave-to { opacity: 0; }

@media (max-width: 700px) {
  .lb-prev { left: 8px; width: 42px; height: 42px; }
  .lb-next { right: 8px; width: 42px; height: 42px; }
}

@media (prefers-reduced-motion: reduce) {
  .lb-img { animation: none; }
}
</style>
