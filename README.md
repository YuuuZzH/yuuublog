# yuuublog

基于 Spring Boot + Vue 的个人博客（前台展示 + 后台管理 + 后端服务）。

线上示例：https://www.yuuu.online

本仓库由原先拆分在 Gitee 的三个项目合并而成：

| 目录 | 说明 | 原仓库 |
|------|------|--------|
| `backend/` | Spring Boot 后端（前台 API + 管理端 API） | [gitee.com/cc-mx/blog](https://gitee.com/cc-mx/blog) |
| `frontend/` | Vue 前台展示页 | [gitee.com/cc-mx/blogvue](https://gitee.com/cc-mx/blogvue) |
| `admin/` | Vue 后台管理端（基于 vue-admin-template） | [gitee.com/cc-mx/adminvue](https://gitee.com/cc-mx/adminvue) |

## 技术栈

- 后端：JDK 8、Spring Boot 2.5、MyBatis-Plus、MySQL 8、Redis 6
- 前台 / 管理端：Vue 2、Element UI

## 目录结构

```text
yuuublog/
├── backend/          # Java 多模块工程
│   ├── yuuu-framework/
│   ├── yuuu-blog/    # 前台服务（默认端口 7777）
│   ├── yuuu-admin/   # 管理端服务（默认端口 8989）
│   └── yuuu_blog.sql # 数据库脚本
├── frontend/         # 博客前台
└── admin/            # 博客后台管理
```

## 快速开始

### 1. 数据库与中间件

1. 安装 MySQL 8、Redis 6
2. 导入 `backend/yuuu_blog.sql`
3. 修改后端配置中的数据源 / Redis / 第三方密钥：
   - `backend/yuuu-blog/src/main/resources/application.yml`
   - `backend/yuuu-admin/src/main/resources/application.yaml`

> OSS、邮箱、GitHub OAuth 等密钥请自行填写，勿把真实密钥提交到仓库。

### 2. 启动后端

```bash
cd backend
# 使用 IDE 分别启动，或 Maven 打包后运行：
# yuuu-blog  -> 端口 7777
# yuuu-admin -> 端口 8989
```

### 3. 启动前台

```bash
cd frontend
npm install
npm run dev
# 默认 http://localhost:8080 ，开发环境 API 指向 http://localhost:7777
```

### 4. 启动管理端

```bash
cd admin
npm install
npm run dev
# 默认 http://localhost:9528 ，开发环境 API 指向 http://localhost:8989
```

## 已实现功能

- 登录注册、文章详情、评论发表等
- 管理端文章 / 分类 / 标签 / 用户等后台能力

## TODO

- [ ] 邮箱验证码、邮箱密码登录
- [ ] 邮件回复功能
- [ ] 定时清除 login_config 表
- [ ] 文章置顶 / 按需排序
- [ ] 标签云、词云
- [ ] 文章阅读进度保存
- [ ] 暗黑模式、多语言切换
