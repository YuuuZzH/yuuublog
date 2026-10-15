<!-- 移动端底部 Tab 导航（≤768px 显示，桌面端完全不渲染） -->
<template>
    <nav class="mobile-tabbar glass" v-if="visible">
        <a v-for="t in tabs" :key="t.key" :class="['m-tab', { active: isActive(t) }]" href="javascript:void(0);"
            @click="go(t)">
            <i :class="'fa ' + t.icon"></i>
            <span>{{ t.label }}</span>
        </a>
    </nav>
</template>

<script>
const TABS = [
    { key: 'home', label: '首页', icon: 'fa-home', path: '/Home', match: ['/', '/Home'] },
    { key: 'share', label: '归档', icon: 'fa-archive', path: '/Share?classId=0', match: ['/Share'] },
    { key: 'word', label: '留言', icon: 'fa-comments', path: '/WordBoard', match: ['/WordBoard'] },
    { key: 'link', label: '友链', icon: 'fa-link', path: '/FriendsLink', match: ['/FriendsLink'] },
    { key: 'me', label: '我的', icon: 'fa-user', path: '/UserInfo', match: ['/UserInfo', '/Login'] }
]

export default {
    name: 'MobileTabBar',
    data() {
        return {
            visible: false,
            tabs: TABS
        }
    },
    methods: {
        onResize() {
            // ≤768px 与主题其余断点保持一致；路由级组件复用时也要同步
            this.visible = window.innerWidth <= 768
        },
        isActive(t) {
            return t.match.indexOf(this.$route.path) > -1
        },
        go(t) {
            if (this.isActive(t)) {
                window.scrollTo({ top: 0, behavior: 'smooth' })
                return
            }
            this.$router.push({ path: t.path })
        }
    },
    mounted() {
        this.onResize()
        window.addEventListener('resize', this.onResize)
    },
    beforeDestroy() {
        window.removeEventListener('resize', this.onResize)
    }
}
</script>

<style lang="less" scoped>
.mobile-tabbar {
    position: fixed;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 1300;
    display: flex;
    align-items: stretch;
    height: 56px;
    padding-bottom: env(safe-area-inset-bottom);
    border-top: 1px solid var(--border);
    box-shadow: 0 -8px 24px rgba(20, 30, 60, .12);
}

.m-tab {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 3px;
    font-size: 11px;
    color: var(--text-weak);
    text-decoration: none;
    transition: color .25s var(--ease);

    i {
        font-size: 17px;
        line-height: 1;
    }

    &.active {
        color: var(--brand);

        i {
            transform: translateY(-1px) scale(1.08);
        }
    }

    &:active {
        color: var(--brand);
    }
}

@media (prefers-reduced-motion: reduce) {
    .m-tab { transition: none; }
}
</style>
