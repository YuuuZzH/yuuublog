# backend

Spring Boot 多模块后端，属于 [yuuublog](../README.md) 单体仓库的一部分。

## 模块

- `yuuu-framework`：公共领域模型、服务与工具
- `yuuu-blog`：前台 API（默认端口 `7777`）
- `yuuu-admin`：管理端 API（默认端口 `8989`）

## 配置

启动前请修改：

- `yuuu-blog/src/main/resources/application.yml`
- `yuuu-admin/src/main/resources/application.yaml`

并导入根目录下的 `yuuu_blog.sql`。

OSS / 邮箱 / OAuth 等密钥请使用本地配置，不要提交真实密钥。
