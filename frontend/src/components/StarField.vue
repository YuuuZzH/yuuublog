<!-- 星空粒子：漂移 + 邻近连线（星座效果），支持亮/暗主题与减少动效偏好 -->
<template>
    <canvas ref="canvas" class="star-field" aria-hidden="true"></canvas>
</template>

<script>
export default {
    name: 'StarField',
    props: {
        count: { type: Number, default: 88 },   // 粒子数量上限
        speed: { type: Number, default: 0.22 }, // 漂移速度
        line: { type: Number, default: 128 }    // 连线距离阈值
    },
    data() {
        return { raf: 0, dots: [], w: 0, h: 0, reduce: false }
    },
    methods: {
        resize() {
            const el = this.$refs.canvas
            if (!el) return
            const dpr = Math.min(window.devicePixelRatio || 1, 2)
            const rect = el.getBoundingClientRect()
            this.w = rect.width
            this.h = rect.height
            el.width = Math.floor(this.w * dpr)
            el.height = Math.floor(this.h * dpr)
            const ctx = el.getContext('2d')
            ctx.setTransform(dpr, 0, 0, dpr, 0, 0)
            this.init()
        },
        init() {
            const n = Math.min(this.count, Math.max(28, Math.round((this.w * this.h) / 14000)))
            this.dots = []
            for (let i = 0; i < n; i++) {
                this.dots.push({
                    x: Math.random() * this.w,
                    y: Math.random() * this.h,
                    vx: (Math.random() - 0.5) * this.speed * 2,
                    vy: (Math.random() - 0.5) * this.speed * 2,
                    r: Math.random() * 1.6 + 0.6
                })
            }
        },
        tick() {
            const el = this.$refs.canvas
            if (!el) return
            const ctx = el.getContext('2d')
            ctx.clearRect(0, 0, this.w, this.h)

            const dark = document.documentElement.getAttribute('data-theme') === 'dark'
            const dotColor = dark ? 'rgba(255,255,255,.85)' : 'rgba(255,255,255,.95)'
            const lineColor = dark ? 'rgba(150,190,255,' : 'rgba(255,255,255,'

            // 连线
            for (let i = 0; i < this.dots.length; i++) {
                const a = this.dots[i]
                for (let j = i + 1; j < this.dots.length; j++) {
                    const b = this.dots[j]
                    const dx = a.x - b.x
                    const dy = a.y - b.y
                    const d2 = dx * dx + dy * dy
                    if (d2 < this.line * this.line) {
                        const alpha = (1 - Math.sqrt(d2) / this.line) * 0.28
                        ctx.strokeStyle = lineColor + alpha.toFixed(3) + ')'
                        ctx.lineWidth = 1
                        ctx.beginPath()
                        ctx.moveTo(a.x, a.y)
                        ctx.lineTo(b.x, b.y)
                        ctx.stroke()
                    }
                }
            }

            // 粒子
            for (let i = 0; i < this.dots.length; i++) {
                const p = this.dots[i]
                p.x += p.vx
                p.y += p.vy
                if (p.x < 0 || p.x > this.w) p.vx *= -1
                if (p.y < 0 || p.y > this.h) p.vy *= -1
                ctx.fillStyle = dotColor
                ctx.beginPath()
                ctx.arc(p.x, p.y, p.r, 0, Math.PI * 2)
                ctx.fill()
            }

            this.raf = window.requestAnimationFrame(this.tick)
        },
        start() {
            this.stop()
            if (this.reduce) { this.drawOnce(); return }
            this.raf = window.requestAnimationFrame(this.tick)
        },
        stop() {
            if (this.raf) window.cancelAnimationFrame(this.raf)
            this.raf = 0
        },
        drawOnce() {
            // 减少动效：只画一帧静态星空
            const el = this.$refs.canvas
            if (!el) return
            const ctx = el.getContext('2d')
            ctx.clearRect(0, 0, this.w, this.h)
            ctx.fillStyle = 'rgba(255,255,255,.85)'
            this.dots.forEach((p) => {
                ctx.beginPath()
                ctx.arc(p.x, p.y, p.r, 0, Math.PI * 2)
                ctx.fill()
            })
        },
        onVisibility() {
            if (document.hidden) this.stop()
            else this.start()
        }
    },
    mounted() {
        this.reduce = !!(window.matchMedia && window.matchMedia('(prefers-reduced-motion: reduce)').matches)
        this.$nextTick(() => {
            this.resize()
            this.start()
        })
        window.addEventListener('resize', this.resize)
        document.addEventListener('visibilitychange', this.onVisibility)
    },
    destroyed() {
        this.stop()
        window.removeEventListener('resize', this.resize)
        document.removeEventListener('visibilitychange', this.onVisibility)
    }
}
</script>

<style scoped>
.star-field {
    position: absolute;
    inset: 0;
    width: 100%;
    height: 100%;
    display: block;
    pointer-events: none;
}
</style>
