<!-- 顶部页面滚动进度条 -->
<template>
    <div class="scroll-progress" :style="{ width: percent + '%' }" aria-hidden="true"></div>
</template>

<script>
export default {
    name: 'ScrollProgress',
    data() {
        return { percent: 0 }
    },
    methods: {
        update() {
            const doc = document.documentElement
            const scrollTop = window.pageYOffset || doc.scrollTop || 0
            const height = (doc.scrollHeight || 0) - window.innerHeight
            this.percent = height > 0 ? Math.min(100, Math.round((scrollTop / height) * 1000) / 10) : 0
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
.scroll-progress {
    position: fixed;
    left: 0;
    top: 0;
    height: 3px;
    z-index: 2000;
    background: linear-gradient(90deg, var(--brand), var(--brand-2) 50%, var(--accent));
    box-shadow: 0 0 12px rgba(79, 140, 255, .55);
    transition: width .12s linear;
    pointer-events: none;
    border-radius: 0 3px 3px 0;
}
</style>
