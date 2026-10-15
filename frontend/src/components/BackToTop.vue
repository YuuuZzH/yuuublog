<!-- 返回顶部：圆环显示当前滚动进度 -->
<template>
    <transition name="btt">
        <button v-show="visible" class="back-to-top" title="回到顶部" aria-label="回到顶部" @click="toTop">
            <svg viewBox="0 0 44 44" width="44" height="44">
                <circle class="track" cx="22" cy="22" r="19" />
                <circle class="bar" cx="22" cy="22" r="19" :style="dashStyle" />
            </svg>
            <i class="fa fa-angle-up" aria-hidden="true"></i>
        </button>
    </transition>
</template>

<script>
const CIRC = 2 * Math.PI * 19

export default {
    name: 'BackToTop',
    data() {
        return { visible: false, percent: 0 }
    },
    computed: {
        dashStyle() {
            return {
                strokeDasharray: `${CIRC} ${CIRC}`,
                strokeDashoffset: CIRC * (1 - this.percent / 100)
            }
        }
    },
    methods: {
        update() {
            const doc = document.documentElement
            const top = window.pageYOffset || doc.scrollTop || 0
            const height = (doc.scrollHeight || 0) - window.innerHeight
            this.percent = height > 0 ? Math.min(100, (top / height) * 100) : 0
            this.visible = top > 320
        },
        toTop() {
            const smooth = !window.matchMedia || !window.matchMedia('(prefers-reduced-motion: reduce)').matches
            try {
                window.scrollTo({ top: 0, behavior: smooth ? 'smooth' : 'auto' })
            } catch (e) {
                window.scrollTo(0, 0)
            }
        }
    },
    mounted() {
        this.update()
        window.addEventListener('scroll', this.update, { passive: true })
        window.addEventListener('resize', this.update)
    },
    destroyed() {
        window.removeEventListener('scroll', this.update)
        window.removeEventListener('resize', this.update)
    }
}
</script>

<style scoped>
.back-to-top {
    position: fixed;
    right: 22px;
    bottom: 26px;
    z-index: 1200;
    width: 44px;
    height: 44px;
    padding: 0;
    border: none;
    border-radius: 50%;
    cursor: pointer;
    background: var(--surface-2);
    -webkit-backdrop-filter: saturate(160%) blur(12px);
    backdrop-filter: saturate(160%) blur(12px);
    box-shadow: var(--shadow-1);
    display: flex;
    align-items: center;
    justify-content: center;
    transition: transform .3s var(--ease), box-shadow .3s var(--ease);
}

.back-to-top:hover {
    transform: translateY(-3px);
    box-shadow: var(--shadow-glow);
}

.back-to-top svg {
    position: absolute;
    inset: 0;
    transform: rotate(-90deg);
}

.back-to-top .track {
    fill: none;
    stroke: var(--border);
    stroke-width: 2;
}

.back-to-top .bar {
    fill: none;
    stroke: url(#none);
    stroke: var(--brand);
    stroke-width: 2.5;
    stroke-linecap: round;
    transition: stroke-dashoffset .15s linear;
}

.back-to-top .fa {
    position: relative;
    font-size: 18px;
    color: var(--text);
}

.btt-enter-active,
.btt-leave-active {
    transition: opacity .28s var(--ease), transform .28s var(--ease);
}

.btt-enter,
.btt-leave-to {
    opacity: 0;
    transform: translateY(12px) scale(.85);
}

@media (max-width: 768px) {
    .back-to-top {
        right: 12px;
        bottom: 16px;
        width: 38px;
        height: 38px;
    }
}
</style>
