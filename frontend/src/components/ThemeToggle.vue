<!-- 主题切换：自动 / 浅色 / 深色 循环 -->
<template>
    <button class="theme-toggle" :title="titleText" :aria-label="titleText" @click="next">
        <i class="fa" :class="iconClass" aria-hidden="true"></i>
    </button>
</template>

<script>
import { getStoredTheme, setStoredTheme, applyTheme, currentResolvedTheme } from '../utils/theme.js'

const ORDER = ['auto', 'light', 'dark']
const LABEL = { auto: '主题：跟随系统', light: '主题：浅色', dark: '主题：深色' }
const ICON = { light: 'fa-sun-o', dark: 'fa-moon-o' }

export default {
    name: 'ThemeToggle',
    data() {
        return {
            mode: getStoredTheme(),
            resolved: currentResolvedTheme()
        }
    },
    computed: {
        titleText() {
            return LABEL[this.mode]
        },
        iconClass() {
            // 自动模式下图标跟随系统实际结果，并加 auto 标记
            return ICON[this.resolved] || 'fa-sun-o'
        }
    },
    methods: {
        next() {
            const idx = ORDER.indexOf(this.mode)
            this.mode = ORDER[(idx + 1) % ORDER.length]
            setStoredTheme(this.mode)
            this.resolved = applyTheme(this.mode)
        }
    }
}
</script>

<style scoped>
.theme-toggle {
    position: fixed;
    right: 22px;
    top: 18px;
    z-index: 1200;
    width: 40px;
    height: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
    border: 1px solid var(--border);
    border-radius: 50%;
    cursor: pointer;
    color: var(--text);
    background: var(--surface-2);
    -webkit-backdrop-filter: saturate(160%) blur(12px);
    backdrop-filter: saturate(160%) blur(12px);
    box-shadow: var(--shadow-1);
    transition: transform .3s var(--ease), box-shadow .3s var(--ease), background-color .3s var(--ease);
}

.theme-toggle:hover {
    transform: translateY(-2px) rotate(-12deg);
    box-shadow: var(--shadow-glow);
}

.theme-toggle .fa {
    font-size: 17px;
}

@media (max-width: 768px) {
    .theme-toggle {
        right: 12px;
        top: 12px;
        width: 34px;
        height: 34px;
    }
}
</style>
