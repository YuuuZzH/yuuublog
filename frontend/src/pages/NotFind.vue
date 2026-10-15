<template>
    <div class="content">
        <canvas class="snow" ref="snow"></canvas>
        <div class="notfind-card glass">
            <div class="code grad-text">404</div>
            <h1>天呐！出错了 ~</h1>
            <p class="tip">{{ tip }}</p>
            <div class="actions">
                <router-link class="btn primary" to="/">返回首页</router-link>
                <a class="btn ghost" href="javascript:void(0);" @click="openSearch">试试搜索（⌘K）</a>
            </div>
        </div>
        <div class="ground">
            <div class="mound">
                <div class="mound_text">404</div>
                <div class="mound_spade"></div>
            </div>
        </div>
    </div>
</template>

<script>
const TIPS = [
    '您好像去了一个不存在的地方！(灬ꈍ ꈍ灬)',
    '这条路的尽头是雪，和一只迷路的小机器人。',
    '页面大概是被风吹走了，要不换个地方看看？',
    '此处禁止通行，连小yu都找不到路。'
]

export default {
    name: 'notFind',
    data() {
        return {
            tip: TIPS[Math.floor(Math.random() * TIPS.length)]
        }
    },
    methods: {
        openSearch() {
            window.dispatchEvent(new KeyboardEvent('keydown', { key: 'k', metaKey: true, bubbles: true }))
        }
    },
    mounted() {
        const el = this.$refs.snow
        if (!el) return
        const ctx = el.getContext('2d')
        let width = 0
        let height = 0
        let particles = []
        let raf = 0

        function Particle() {
            this.x = this.y = this.dx = this.dy = 0
            this.reset()
        }

        Particle.prototype.reset = function () {
            this.y = Math.random() * height
            this.x = Math.random() * width
            this.dx = Math.random() * 1 - 0.5
            this.dy = Math.random() * 0.5 + 0.5
        }

        function createParticles(count) {
            particles = []
            for (let i = 0; i < count; i++) {
                particles.push(new Particle())
            }
        }

        function onResize() {
            width = window.innerWidth
            height = window.innerHeight
            el.width = width
            el.height = height
            createParticles(Math.round((width * height) / 10000))
        }

        function updateParticles() {
            ctx.clearRect(0, 0, width, height)
            // 暗色模式下白点会消失在深色背景里，这里跟随主题换色
            const dark = document.documentElement.getAttribute('data-theme') === 'dark'
            ctx.fillStyle = dark ? 'rgba(180, 210, 255, .8)' : '#f6f9fa'

            particles.forEach(function (particle) {
                particle.y += particle.dy
                particle.x += particle.dx
                if (particle.y > height) particle.y = 0
                if (particle.x > width) {
                    particle.reset()
                    particle.y = 0
                }
                ctx.beginPath()
                ctx.arc(particle.x, particle.y, 2.6, 0, Math.PI * 2, false)
                ctx.fill()
            })

            raf = window.requestAnimationFrame(updateParticles)
        }

        onResize()
        updateParticles()
        window.addEventListener('resize', onResize)
        this.$once('hook:beforeDestroy', function () {
            window.removeEventListener('resize', onResize)
            if (raf) window.cancelAnimationFrame(raf)
        })
    }
}
</script>

<style scoped>
.content {
    height: 100vh;
    min-height: 450px;
    margin: 0;
    padding: 0;
    border: 0;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    position: relative;
    z-index: 1;
    overflow: hidden;
    background: linear-gradient(to bottom, var(--bg-soft) 0%, var(--bg) 80%);
}

.notfind-card {
    position: relative;
    z-index: 30;
    text-align: center;
    padding: 36px 40px 32px;
    border-radius: var(--radius-lg);
    box-shadow: var(--shadow-2);
    animation: cardIn .6s var(--ease) both;
}

@keyframes cardIn {
    from { opacity: 0; transform: translateY(18px) scale(.98); }
    to { opacity: 1; transform: none; }
}

.code {
    font-size: 76px;
    font-weight: 900;
    letter-spacing: 6px;
    line-height: 1;
}

h1 {
    font-size: 22px;
    font-weight: 600;
    color: var(--text);
    margin: 14px 0 8px;
}

.tip {
    font-size: 14px;
    color: var(--text-weak);
    margin: 0 0 22px;
}

.actions {
    display: flex;
    gap: 12px;
    justify-content: center;
    flex-wrap: wrap;
}

.btn {
    display: inline-block;
    padding: 9px 18px;
    border-radius: 999px;
    font-size: 13px;
    text-decoration: none;
    transition: transform .25s var(--ease), box-shadow .25s var(--ease), color .25s var(--ease);
}

.btn.primary {
    background: linear-gradient(120deg, var(--brand), var(--brand-2));
    color: #fff;
    box-shadow: var(--shadow-glow);
}

.btn.ghost {
    border: 1px solid var(--border-strong);
    color: var(--text-weak);
}

.btn:hover {
    transform: translateY(-2px);
    color: var(--text);
}

.btn.primary:hover {
    color: #fff;
}

.snow {
    position: absolute;
    top: 0;
    left: 0;
    pointer-events: none;
    z-index: 20;
}

.ground {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 120px;
    background: var(--surface);
}

.mound {
    position: absolute;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
    font-weight: 800;
    font-size: 120px;
    text-align: center;
    color: var(--brand);
    opacity: .18;
    pointer-events: none;
}

.mound_text {
    transform: rotate(6deg);
}

@media (max-width: 700px) {
    .notfind-card { padding: 28px 20px; }
    .code { font-size: 56px; }
    .mound { font-size: 84px; }
}

@media (prefers-reduced-motion: reduce) {
    .notfind-card { animation: none; }
    .btn { transition: none; }
}
</style>
