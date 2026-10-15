<!-- 光标特效：跟随鼠标的柔光晕 + 点击涟漪（仅精确指针设备，尊重 reduced-motion） -->
<template>
  <div class="cursor-fx" v-if="enabled">
    <div class="cursor-glow" :style="glowStyle"></div>
    <span v-for="r in ripples" :key="r.id" class="cursor-ripple" :style="{ left: r.x + 'px', top: r.y + 'px' }"></span>
  </div>
</template>

<script>
export default {
  name: 'CursorFx',
  data() {
    return {
      enabled: false,
      x: -400,
      y: -400,
      tx: -400,
      ty: -400,
      ripples: [],
      seq: 0
    }
  },
  computed: {
    glowStyle: function () {
      return {
        transform: 'translate3d(' + (this.x - 90) + 'px,' + (this.y - 90) + 'px,0)'
      }
    }
  },
  methods: {
    onMove: function (e) {
      this.tx = e.clientX
      this.ty = e.clientY
    },
    onDown: function (e) {
      this.seq += 1
      const id = this.seq
      this.ripples.push({ id: id, x: e.clientX, y: e.clientY })
      const self = this
      window.setTimeout(function () {
        self.ripples = self.ripples.filter(function (r) { return r.id !== id })
      }, 700)
    },
    tick: function () {
      // 缓动跟随，避免光晕贴死鼠标显得廉价
      this.x += (this.tx - this.x) * 0.18
      this.y += (this.ty - this.y) * 0.18
      this.raf = window.requestAnimationFrame(this.tick)
    }
  },
  mounted() {
    const fine = window.matchMedia && window.matchMedia('(pointer: fine)').matches
    const reduce = window.matchMedia && window.matchMedia('(prefers-reduced-motion: reduce)').matches
    if (!fine || reduce) return
    this.enabled = true
    window.addEventListener('mousemove', this.onMove, { passive: true })
    window.addEventListener('mousedown', this.onDown)
    this.raf = window.requestAnimationFrame(this.tick)
  },
  beforeDestroy() {
    if (!this.enabled) return
    window.removeEventListener('mousemove', this.onMove)
    window.removeEventListener('mousedown', this.onDown)
    if (this.raf) window.cancelAnimationFrame(this.raf)
  }
}
</script>

<style lang="less" scoped>
.cursor-fx {
  position: fixed;
  top: 0;
  left: 0;
  z-index: 1500;
  pointer-events: none;
}

.cursor-glow {
  position: fixed;
  top: 0;
  left: 0;
  width: 180px;
  height: 180px;
  border-radius: 50%;
  background: radial-gradient(circle, rgba(79, 140, 255, .28) 0%, rgba(123, 108, 255, .16) 45%, transparent 70%);
  filter: blur(6px);
  opacity: .9;
  transition: opacity .3s var(--ease);
}

.cursor-ripple {
  position: fixed;
  width: 14px;
  height: 14px;
  margin: -7px 0 0 -7px;
  border-radius: 50%;
  border: 2px solid var(--brand);
  animation: rippleOut .7s var(--ease) forwards;
}

@keyframes rippleOut {
  0% {
    transform: scale(.4);
    opacity: .9;
  }

  100% {
    transform: scale(4.2);
    opacity: 0;
  }
}

@media (max-width: 768px) {
  .cursor-fx { display: none; }
}
</style>
