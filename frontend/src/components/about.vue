<!-- 关于我模块 -->
<template>
    <div class="tAboutBox">
        <!-- 顶部名片 -->
        <div class="about-hero" :style="heroStyle">
            <div class="about-hero-inner">
                <img class="about-avatar" :src="aboutObj.avatar ? aboutObj.avatar : 'static/img/tou.png'"
                    :onerror="$store.state.errorImg" alt="">
                <div class="about-meta">
                    <h1>{{ aboutObj.name ? aboutObj.name : '关于我' }}</h1>
                    <p class="about-title" v-if="aboutObj.title">{{ aboutObj.title }}</p>
                    <p class="about-summary" v-if="aboutObj.summary">{{ aboutObj.summary }}</p>
                    <div class="about-contacts">
                        <span v-if="aboutObj.location"><i class="fa fa-fw fa-map-marker"></i>{{ aboutObj.location }}</span>
                        <a v-if="aboutObj.email" :href="'mailto:' + aboutObj.email"><i
                                class="fa fa-fw fa-envelope"></i>{{ aboutObj.email }}</a>
                        <a v-if="aboutObj.github" :href="aboutObj.github" target="_blank" rel="noopener"><i
                                class="fa fa-fw fa-github"></i>GitHub</a>
                        <a v-if="aboutObj.website" :href="aboutObj.website" target="_blank" rel="noopener"><i
                                class="fa fa-fw fa-globe"></i>个人网站</a>
                    </div>
                </div>
            </div>
        </div>

        <!-- 技能栈 -->
        <div class="about-card" v-if="skillList.length">
            <h2><i class="fa fa-fw fa-cubes"></i>技能栈</h2>
            <ul class="skill-list">
                <li v-for="(item, index) in skillList" :key="'s' + index">
                    <span class="skill-name">{{ item.name }}</span>
                    <span class="skill-bar"><i :style="{ width: (item.level ? item.level : 70) + '%' }"></i></span>
                    <span class="skill-level">{{ item.level ? item.level : '' }}</span>
                </li>
            </ul>
        </div>

        <!-- 自我介绍正文（Markdown 渲染） -->
        <div class="about-card">
            <h2><i class="fa fa-fw fa-quote-left"></i>自我介绍</h2>
            <div class="markdown-body about-content" v-html="contentHtml"></div>
            <p class="about-empty" v-if="!contentHtml">还没有填写自我介绍～</p>
        </div>
    </div>
</template>

<script>
import { getAbout } from '../api/about.js'
import { mavonEditor } from 'mavon-editor'

export default {
    name: 'About',
    data() { //选项 / 数据
        return {
            aboutObj: {},//关于我配置
            contentHtml: ''//正文渲染后的 HTML
        }
    },
    computed: {
        heroStyle() {
            return this.aboutObj.cover
                ? { backgroundImage: 'url(' + this.aboutObj.cover + ')' }
                : {};
        },
        skillList() {
            var raw = this.aboutObj.skills;
            if (!raw) {
                return [];
            }
            return String(raw).split(',').map(function (item) {
                var parts = String(item).split(':');
                var level = parseInt(parts[1], 10);
                return {
                    name: (parts[0] || '').trim(),
                    level: isNaN(level) ? 0 : Math.min(Math.max(level, 0), 100)
                };
            }).filter(function (item) {
                return item.name;
            });
        }
    },
    methods: { //事件处理器
        getList() {
            var that = this;
            getAbout().then((response) => {
                that.aboutObj = response ? response : {};
                // 与文章详情保持一致：用 markdown-it 渲染正文（默认不解析原始 HTML）
                var content = that.aboutObj.content;
                that.contentHtml = content ? mavonEditor.getMarkdownIt().render(content) : '';
            }).catch(() => {
                that.aboutObj = {};
                that.contentHtml = '';
            });
        }
    },
    created() { //生命周期函数
        this.getList()
    }
}
</script>

<style>
.tAboutBox {
    background: var(--surface);
    padding: 0 0 15px;
    border-radius: var(--radius, 5px);
    margin-bottom: 40px;
    overflow: hidden;
}

/* 顶部名片：有封面时以封面做背景，无封面时用品牌渐变 */
.about-hero {
    position: relative;
    background: linear-gradient(120deg, var(--brand), var(--brand-2));
    background-size: cover;
    background-position: center;
    color: #fff;
}

.about-hero::after {
    content: "";
    position: absolute;
    inset: 0;
    background: linear-gradient(180deg, rgba(12, 18, 40, .25), rgba(12, 18, 40, .55));
}

.about-hero-inner {
    position: relative;
    z-index: 1;
    display: flex;
    align-items: center;
    padding: 40px 30px;
}

.about-avatar {
    width: 96px;
    height: 96px;
    border-radius: 50%;
    object-fit: cover;
    border: 3px solid rgba(255, 255, 255, .85);
    box-shadow: 0 8px 26px rgba(20, 30, 60, .3);
    flex-shrink: 0;
}

.about-meta {
    margin-left: 20px;
    min-width: 0;
}

.about-meta h1 {
    font-size: 26px;
    font-weight: 700;
    margin: 0;
}

.about-meta .about-title {
    margin: 6px 0 0;
    font-size: 15px;
    opacity: .9;
}

.about-meta .about-summary {
    margin: 10px 0 0;
    font-size: 14px;
    line-height: 1.6;
    opacity: .85;
}

.about-contacts {
    margin-top: 12px;
    display: flex;
    flex-wrap: wrap;
    gap: 8px 18px;
    font-size: 13px;
}

.about-contacts a,
.about-contacts span {
    color: #fff;
    text-decoration: none;
    opacity: .9;
    max-width: 100%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.about-contacts a:hover {
    opacity: 1;
    text-decoration: underline;
}

/* 下方卡片 */
.about-card {
    padding: 20px 30px 5px;
}

.about-card h2 {
    font-size: 18px;
    font-weight: 700;
    color: var(--text);
    margin: 0 0 15px;
}

/* 技能条 */
.skill-list {
    padding: 0;
}

.skill-list li {
    display: flex;
    align-items: center;
    margin-bottom: 12px;
    font-size: 14px;
    color: var(--text);
}

.skill-name {
    width: 120px;
    flex-shrink: 0;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.skill-bar {
    flex: 1;
    height: 8px;
    border-radius: 999px;
    background: var(--surface-3, rgba(120, 140, 180, .18));
    overflow: hidden;
}

.skill-bar i {
    display: block;
    height: 100%;
    border-radius: 999px;
    background: linear-gradient(120deg, var(--brand), var(--brand-2));
    transition: width .6s var(--ease, ease);
}

.skill-level {
    width: 36px;
    text-align: right;
    color: var(--text-weak);
    font-size: 12px;
}

.about-content {
    background: transparent;
    color: var(--text);
    font-size: 15px;
    line-height: 1.8;
}

.about-empty {
    color: var(--text-weak);
    font-size: 14px;
}

@media screen and (max-width: 768px) {
    .about-hero-inner {
        flex-direction: column;
        text-align: center;
        padding: 26px 18px;
    }

    .about-meta {
        margin-left: 0;
        margin-top: 14px;
    }

    .about-contacts {
        justify-content: center;
    }

    .about-card {
        padding: 16px 18px 5px;
    }

    .skill-name {
        width: 84px;
    }
}
</style>
