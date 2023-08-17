/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80028 (8.0.28)
 Source Host           : localhost:3306
 Source Schema         : yuuu_blog

 Target Server Type    : MySQL
 Target Server Version : 80028 (8.0.28)
 File Encoding         : 65001

 Date: 17/08/2023 09:38:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `is_frame` int NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `menu_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2048 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, 'system', NULL, 1, 'M', '0', '0', '', 'system', 0, '2021-11-12 10:46:19', 0, NULL, '系统管理目录', '0');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', 1, 'C', '0', '0', 'system:user:list', 'user', 0, '2021-11-12 10:46:19', 1, '2022-07-31 15:47:58', '用户管理菜单', '0');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', 1, 'C', '0', '0', 'system:role:list', 'peoples', 0, '2021-11-12 10:46:19', 0, NULL, '角色管理菜单', '0');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', 1, 'C', '0', '0', 'system:menu:list', 'tree-table', 0, '2021-11-12 10:46:19', 0, NULL, '菜单管理菜单', '0');
INSERT INTO `sys_menu` VALUES (1001, '用户查询', 100, 1, '', '', 1, 'F', '0', '0', 'system:user:query', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1002, '用户新增', 100, 2, '', '', 1, 'F', '0', '0', 'system:user:add', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1003, '用户修改', 100, 3, '', '', 1, 'F', '0', '0', 'system:user:edit', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1004, '用户删除', 100, 4, '', '', 1, 'F', '0', '0', 'system:user:remove', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1005, '用户导出', 100, 5, '', '', 1, 'F', '0', '0', 'system:user:export', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1006, '用户导入', 100, 6, '', '', 1, 'F', '0', '0', 'system:user:import', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1007, '重置密码', 100, 7, '', '', 1, 'F', '0', '0', 'system:user:resetPwd', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1008, '角色查询', 101, 1, '', '', 1, 'F', '0', '0', 'system:role:query', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1009, '角色新增', 101, 2, '', '', 1, 'F', '0', '0', 'system:role:add', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1010, '角色修改', 101, 3, '', '', 1, 'F', '0', '0', 'system:role:edit', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1011, '角色删除', 101, 4, '', '', 1, 'F', '0', '0', 'system:role:remove', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1012, '角色导出', 101, 5, '', '', 1, 'F', '0', '0', 'system:role:export', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1013, '菜单查询', 102, 1, '', '', 1, 'F', '0', '0', 'system:menu:query', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1014, '菜单新增', 102, 2, '', '', 1, 'F', '0', '0', 'system:menu:add', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1015, '菜单修改', 102, 3, '', '', 1, 'F', '0', '0', 'system:menu:edit', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1016, '菜单删除', 102, 4, '', '', 1, 'F', '0', '0', 'system:menu:remove', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `sys_menu` VALUES (2017, '内容管理', 0, 4, 'content', NULL, 1, 'M', '0', '0', NULL, 'table', NULL, '2022-01-08 02:44:38', 1, '2022-07-31 12:34:23', '', '0');
INSERT INTO `sys_menu` VALUES (2018, '分类管理', 2017, 1, 'category', 'content/category/index', 1, 'C', '0', '0', 'content:category:list', 'example', NULL, '2022-01-08 02:51:45', NULL, '2022-01-08 02:51:45', '', '0');
INSERT INTO `sys_menu` VALUES (2019, '文章管理', 2017, 0, 'article', 'content/article/index', 1, 'C', '0', '0', 'content:article:list', 'build', NULL, '2022-01-08 02:53:10', NULL, '2022-01-08 02:53:10', '', '0');
INSERT INTO `sys_menu` VALUES (2021, '标签管理', 2017, 6, 'tag', 'content/tag/index', 1, 'C', '0', '0', 'content:tag:index', 'button', NULL, '2022-01-08 02:55:37', NULL, '2022-01-08 02:55:50', '', '0');
INSERT INTO `sys_menu` VALUES (2022, '友链管理', 2017, 4, 'link', 'content/link/index', 1, 'C', '0', '0', 'content:link:list', '404', NULL, '2022-01-08 02:56:50', NULL, '2022-01-08 02:56:50', '', '0');
INSERT INTO `sys_menu` VALUES (2023, '写博文', 0, 0, 'write', 'content/article/write/index', 1, 'C', '0', '0', 'content:article:writer', 'build', NULL, '2022-01-08 03:39:58', 1, '2022-07-31 22:07:05', '', '0');
INSERT INTO `sys_menu` VALUES (2024, '友链新增', 2022, 0, '', NULL, 1, 'F', '0', '0', 'content:link:add', '#', NULL, '2022-01-16 07:59:17', NULL, '2022-01-16 07:59:17', '', '0');
INSERT INTO `sys_menu` VALUES (2025, '友链修改', 2022, 1, '', NULL, 1, 'F', '0', '0', 'content:link:edit', '#', NULL, '2022-01-16 07:59:44', NULL, '2022-01-16 07:59:44', '', '0');
INSERT INTO `sys_menu` VALUES (2026, '友链删除', 2022, 1, '', NULL, 1, 'F', '0', '0', 'content:link:remove', '#', NULL, '2022-01-16 08:00:05', NULL, '2022-01-16 08:00:05', '', '0');
INSERT INTO `sys_menu` VALUES (2027, '友链查询', 2022, 2, '', NULL, 1, 'F', '0', '0', 'content:link:query', '#', NULL, '2022-01-16 08:04:09', NULL, '2022-01-16 08:04:09', '', '0');
INSERT INTO `sys_menu` VALUES (2028, '导出分类', 2018, 1, '', NULL, 1, 'F', '0', '0', 'content:category:export', '#', NULL, '2022-01-21 07:06:59', NULL, '2022-01-21 07:06:59', '', '0');
INSERT INTO `sys_menu` VALUES (2045, '1', 0, 1, '1', NULL, 1, 'M', '0', '0', NULL, 'button', NULL, NULL, NULL, NULL, '', '1');
INSERT INTO `sys_menu` VALUES (2046, '11', 0, 11, '11', NULL, 1, 'M', '0', '0', NULL, 'build', NULL, NULL, NULL, NULL, '', '1');
INSERT INTO `sys_menu` VALUES (2047, '测试', 2019, 1, 'www', NULL, 1, 'M', '0', '0', NULL, 'color', NULL, NULL, NULL, NULL, '', '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '0', '0', 0, '2021-11-12 10:46:19', 0, NULL, '超级管理员');
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 2, '0', '0', 0, '2021-11-12 10:46:19', 0, '2022-01-01 22:32:58', '普通角色');
INSERT INTO `sys_role` VALUES (11, '嘎嘎嘎', 'aggag', 5, '0', '1', NULL, '2022-01-06 14:07:40', NULL, '2022-01-07 03:48:48', '嘎嘎嘎');
INSERT INTO `sys_role` VALUES (12, '友链审核员', 'link', 1, '0', '0', NULL, '2022-01-16 06:49:30', NULL, '2022-01-16 08:05:09', NULL);
INSERT INTO `sys_role` VALUES (13, '11', '11', 1, '0', '1', NULL, NULL, NULL, NULL, '11');
INSERT INTO `sys_role` VALUES (14, '11', '111', 1, '0', '1', NULL, NULL, NULL, NULL, '11');
INSERT INTO `sys_role` VALUES (15, '11', '11', 1, '0', '1', NULL, NULL, NULL, NULL, '111');
INSERT INTO `sys_role` VALUES (16, '1', '1', 1, '0', '1', NULL, NULL, NULL, NULL, '123');
INSERT INTO `sys_role` VALUES (17, 'cscg', 'cscg', 1, '0', '1', NULL, NULL, NULL, NULL, 'cscg');
INSERT INTO `sys_role` VALUES (18, '菜单管理员', 'menuManage', 1, '1', '0', NULL, NULL, NULL, NULL, '管理菜单');
INSERT INTO `sys_role` VALUES (19, '测试管理员', 'cs', 1, '0', '1', NULL, NULL, NULL, NULL, '测试');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (0, 0);
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 102);
INSERT INTO `sys_role_menu` VALUES (2, 1013);
INSERT INTO `sys_role_menu` VALUES (2, 1014);
INSERT INTO `sys_role_menu` VALUES (2, 1015);
INSERT INTO `sys_role_menu` VALUES (2, 1016);
INSERT INTO `sys_role_menu` VALUES (2, 2000);
INSERT INTO `sys_role_menu` VALUES (3, 2);
INSERT INTO `sys_role_menu` VALUES (3, 3);
INSERT INTO `sys_role_menu` VALUES (3, 4);
INSERT INTO `sys_role_menu` VALUES (3, 100);
INSERT INTO `sys_role_menu` VALUES (3, 101);
INSERT INTO `sys_role_menu` VALUES (3, 103);
INSERT INTO `sys_role_menu` VALUES (3, 104);
INSERT INTO `sys_role_menu` VALUES (3, 105);
INSERT INTO `sys_role_menu` VALUES (3, 106);
INSERT INTO `sys_role_menu` VALUES (3, 107);
INSERT INTO `sys_role_menu` VALUES (3, 108);
INSERT INTO `sys_role_menu` VALUES (3, 109);
INSERT INTO `sys_role_menu` VALUES (3, 110);
INSERT INTO `sys_role_menu` VALUES (3, 111);
INSERT INTO `sys_role_menu` VALUES (3, 112);
INSERT INTO `sys_role_menu` VALUES (3, 113);
INSERT INTO `sys_role_menu` VALUES (3, 114);
INSERT INTO `sys_role_menu` VALUES (3, 115);
INSERT INTO `sys_role_menu` VALUES (3, 116);
INSERT INTO `sys_role_menu` VALUES (3, 500);
INSERT INTO `sys_role_menu` VALUES (3, 501);
INSERT INTO `sys_role_menu` VALUES (3, 1001);
INSERT INTO `sys_role_menu` VALUES (3, 1002);
INSERT INTO `sys_role_menu` VALUES (3, 1003);
INSERT INTO `sys_role_menu` VALUES (3, 1004);
INSERT INTO `sys_role_menu` VALUES (3, 1005);
INSERT INTO `sys_role_menu` VALUES (3, 1006);
INSERT INTO `sys_role_menu` VALUES (3, 1007);
INSERT INTO `sys_role_menu` VALUES (3, 1008);
INSERT INTO `sys_role_menu` VALUES (3, 1009);
INSERT INTO `sys_role_menu` VALUES (3, 1010);
INSERT INTO `sys_role_menu` VALUES (3, 1011);
INSERT INTO `sys_role_menu` VALUES (3, 1012);
INSERT INTO `sys_role_menu` VALUES (3, 1017);
INSERT INTO `sys_role_menu` VALUES (3, 1018);
INSERT INTO `sys_role_menu` VALUES (3, 1019);
INSERT INTO `sys_role_menu` VALUES (3, 1020);
INSERT INTO `sys_role_menu` VALUES (3, 1021);
INSERT INTO `sys_role_menu` VALUES (3, 1022);
INSERT INTO `sys_role_menu` VALUES (3, 1023);
INSERT INTO `sys_role_menu` VALUES (3, 1024);
INSERT INTO `sys_role_menu` VALUES (3, 1025);
INSERT INTO `sys_role_menu` VALUES (3, 1026);
INSERT INTO `sys_role_menu` VALUES (3, 1027);
INSERT INTO `sys_role_menu` VALUES (3, 1028);
INSERT INTO `sys_role_menu` VALUES (3, 1029);
INSERT INTO `sys_role_menu` VALUES (3, 1030);
INSERT INTO `sys_role_menu` VALUES (3, 1031);
INSERT INTO `sys_role_menu` VALUES (3, 1032);
INSERT INTO `sys_role_menu` VALUES (3, 1033);
INSERT INTO `sys_role_menu` VALUES (3, 1034);
INSERT INTO `sys_role_menu` VALUES (3, 1035);
INSERT INTO `sys_role_menu` VALUES (3, 1036);
INSERT INTO `sys_role_menu` VALUES (3, 1037);
INSERT INTO `sys_role_menu` VALUES (3, 1038);
INSERT INTO `sys_role_menu` VALUES (3, 1039);
INSERT INTO `sys_role_menu` VALUES (3, 1040);
INSERT INTO `sys_role_menu` VALUES (3, 1041);
INSERT INTO `sys_role_menu` VALUES (3, 1042);
INSERT INTO `sys_role_menu` VALUES (3, 1043);
INSERT INTO `sys_role_menu` VALUES (3, 1044);
INSERT INTO `sys_role_menu` VALUES (3, 1045);
INSERT INTO `sys_role_menu` VALUES (3, 1046);
INSERT INTO `sys_role_menu` VALUES (3, 1047);
INSERT INTO `sys_role_menu` VALUES (3, 1048);
INSERT INTO `sys_role_menu` VALUES (3, 1049);
INSERT INTO `sys_role_menu` VALUES (3, 1050);
INSERT INTO `sys_role_menu` VALUES (3, 1051);
INSERT INTO `sys_role_menu` VALUES (3, 1052);
INSERT INTO `sys_role_menu` VALUES (3, 1053);
INSERT INTO `sys_role_menu` VALUES (3, 1054);
INSERT INTO `sys_role_menu` VALUES (3, 1055);
INSERT INTO `sys_role_menu` VALUES (3, 1056);
INSERT INTO `sys_role_menu` VALUES (3, 1057);
INSERT INTO `sys_role_menu` VALUES (3, 1058);
INSERT INTO `sys_role_menu` VALUES (3, 1059);
INSERT INTO `sys_role_menu` VALUES (3, 1060);
INSERT INTO `sys_role_menu` VALUES (3, 2000);
INSERT INTO `sys_role_menu` VALUES (12, 2017);
INSERT INTO `sys_role_menu` VALUES (12, 2022);
INSERT INTO `sys_role_menu` VALUES (12, 2024);
INSERT INTO `sys_role_menu` VALUES (12, 2025);
INSERT INTO `sys_role_menu` VALUES (12, 2026);
INSERT INTO `sys_role_menu` VALUES (12, 2027);
INSERT INTO `sys_role_menu` VALUES (13, 100);
INSERT INTO `sys_role_menu` VALUES (14, 1);
INSERT INTO `sys_role_menu` VALUES (14, 100);
INSERT INTO `sys_role_menu` VALUES (14, 101);
INSERT INTO `sys_role_menu` VALUES (14, 102);
INSERT INTO `sys_role_menu` VALUES (14, 1001);
INSERT INTO `sys_role_menu` VALUES (14, 1002);
INSERT INTO `sys_role_menu` VALUES (14, 1003);
INSERT INTO `sys_role_menu` VALUES (14, 1004);
INSERT INTO `sys_role_menu` VALUES (14, 1005);
INSERT INTO `sys_role_menu` VALUES (14, 1006);
INSERT INTO `sys_role_menu` VALUES (14, 1007);
INSERT INTO `sys_role_menu` VALUES (14, 1008);
INSERT INTO `sys_role_menu` VALUES (14, 1009);
INSERT INTO `sys_role_menu` VALUES (14, 1010);
INSERT INTO `sys_role_menu` VALUES (14, 1011);
INSERT INTO `sys_role_menu` VALUES (14, 1012);
INSERT INTO `sys_role_menu` VALUES (14, 1013);
INSERT INTO `sys_role_menu` VALUES (14, 1014);
INSERT INTO `sys_role_menu` VALUES (14, 1015);
INSERT INTO `sys_role_menu` VALUES (14, 1016);
INSERT INTO `sys_role_menu` VALUES (15, 2017);
INSERT INTO `sys_role_menu` VALUES (15, 2018);
INSERT INTO `sys_role_menu` VALUES (15, 2019);
INSERT INTO `sys_role_menu` VALUES (15, 2021);
INSERT INTO `sys_role_menu` VALUES (15, 2022);
INSERT INTO `sys_role_menu` VALUES (15, 2024);
INSERT INTO `sys_role_menu` VALUES (15, 2025);
INSERT INTO `sys_role_menu` VALUES (15, 2026);
INSERT INTO `sys_role_menu` VALUES (15, 2027);
INSERT INTO `sys_role_menu` VALUES (15, 2028);
INSERT INTO `sys_role_menu` VALUES (16, 1);
INSERT INTO `sys_role_menu` VALUES (16, 100);
INSERT INTO `sys_role_menu` VALUES (16, 101);
INSERT INTO `sys_role_menu` VALUES (16, 102);
INSERT INTO `sys_role_menu` VALUES (16, 1001);
INSERT INTO `sys_role_menu` VALUES (16, 1002);
INSERT INTO `sys_role_menu` VALUES (16, 1003);
INSERT INTO `sys_role_menu` VALUES (16, 1004);
INSERT INTO `sys_role_menu` VALUES (16, 1005);
INSERT INTO `sys_role_menu` VALUES (16, 1006);
INSERT INTO `sys_role_menu` VALUES (16, 1007);
INSERT INTO `sys_role_menu` VALUES (16, 1008);
INSERT INTO `sys_role_menu` VALUES (16, 1009);
INSERT INTO `sys_role_menu` VALUES (16, 1010);
INSERT INTO `sys_role_menu` VALUES (16, 1011);
INSERT INTO `sys_role_menu` VALUES (16, 1012);
INSERT INTO `sys_role_menu` VALUES (16, 1013);
INSERT INTO `sys_role_menu` VALUES (16, 1014);
INSERT INTO `sys_role_menu` VALUES (16, 1015);
INSERT INTO `sys_role_menu` VALUES (16, 1016);
INSERT INTO `sys_role_menu` VALUES (17, 2023);
INSERT INTO `sys_role_menu` VALUES (18, 1);
INSERT INTO `sys_role_menu` VALUES (18, 102);
INSERT INTO `sys_role_menu` VALUES (18, 1013);
INSERT INTO `sys_role_menu` VALUES (18, 1014);
INSERT INTO `sys_role_menu` VALUES (18, 1015);
INSERT INTO `sys_role_menu` VALUES (18, 1016);
INSERT INTO `sys_role_menu` VALUES (19, 1);
INSERT INTO `sys_role_menu` VALUES (19, 100);
INSERT INTO `sys_role_menu` VALUES (19, 1001);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '用户名',
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '昵称',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '密码',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '用户类型：0代表普通用户，1代表管理员',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户性别（0男，1女，2未知）',
  `avatar` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人的用户id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`user_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14787164048681 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'yuuu', 'yuuu', '$2a$10$Jnq31rRkNV3RNzXe0REsEOSKaYK8UgVZZqlNlNXqn.JeVcj2NdeZy', '1', '0', '23412332@qq.com', '18888888888', '0', 'http://qiniu.yuuu.online/2023/08/11/8dbfd3b51126440ca7a2b239cc628b5c.jpg', NULL, '2022-01-05 09:01:56', 1, '2022-01-30 15:37:03', 0);
INSERT INTO `sys_user` VALUES (3, 'sg3', 'sg333', '$2a$10$ydv3rLkteFnRx9xelQ7elOiVhFvXOooA98xCqk/omh7G94R.K/E3O', '1', '0', '23412332@qq.com', NULL, '1', 'http://rr2wi3rfl.bkt.clouddn.com/2023/03/06/0a79ba5e9f574985a3b5692274d22df8.png', NULL, '2022-01-05 13:28:43', NULL, '2022-01-05 13:28:43', 0);
INSERT INTO `sys_user` VALUES (4, 'sg2', '1235', '$2a$10$kY4T3SN7i4muBccZppd2OOkhxMN6yt8tND1sF89hXOaFylhY2T3he', '1', '0', '23412332@qq.com', '19098790742', '0', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_user` VALUES (5, 'sg2233', 'tteqe', '', '1', '0', NULL, '18246845873', '1', NULL, NULL, '2022-01-06 03:51:13', NULL, '2022-01-06 07:00:50', 1);
INSERT INTO `sys_user` VALUES (6, 'sangeng', 'sangeng', '$2a$10$Jnq31rRkNV3RNzXe0REsEOSKaYK8UgVZZqlNlNXqn.JeVcj2NdeZy', '1', '0', '', '17777777777', '0', NULL, NULL, '2022-01-16 06:54:26', NULL, '2022-01-16 07:06:34', 0);
INSERT INTO `sys_user` VALUES (14787164048677, '测试', '测试1', '$2a$10$Jcg9J4oxVSgA8w2TnAvJVeM83Qoiiiuk6MVjr7BP39Z3EMURvdrw6', '0', '0', '13273022864@163.com', NULL, '0', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_user` VALUES (14787164048680, 'yuu', 'yuu', '$2a$10$Iz05TpIsYZohJFQNZI5sye3TIkdVAF4IZD0SnTV8p/54gmxJwQZ2K', '0', '0', '***REMOVED***', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);
INSERT INTO `sys_user_role` VALUES (4, 2);
INSERT INTO `sys_user_role` VALUES (5, 2);
INSERT INTO `sys_user_role` VALUES (14787164048672, 12);
INSERT INTO `sys_user_role` VALUES (14787164048673, 2);
INSERT INTO `sys_user_role` VALUES (14787164048675, 2);
INSERT INTO `sys_user_role` VALUES (14787164048676, 2);

-- ----------------------------
-- Table structure for yuuu_article
-- ----------------------------
DROP TABLE IF EXISTS `yuuu_article`;
CREATE TABLE `yuuu_article`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '文章内容',
  `summary` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文章摘要',
  `category_id` bigint NULL DEFAULT NULL COMMENT '所属分类id',
  `thumbnail` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '缩略图',
  `is_top` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '是否置顶（0否，1是）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '状态（0已发布，1草稿）',
  `view_count` bigint NULL DEFAULT 0 COMMENT '访问量',
  `is_comment` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '是否允许评论 1是，0否',
  `create_by` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_by` bigint NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of yuuu_article
-- ----------------------------
INSERT INTO `yuuu_article` VALUES (1, 'SpringSecurity从入门到精通', '## 课程介绍\n![image20211219121555979.png](https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/31/e7131718e9e64faeaf3fe16404186eb4.png)\n\n## 0. 简介1\n\n​	**Spring Security** 是 Spring 家族中的一个安全管理框架。相比与另外一个安全框架**Shiro**，它提供了更丰富的功能，社区资源也比Shiro丰富。\n\n​	一般来说中大型的项目都是使用**SpringSecurity** 来做安全框架。小项目有Shiro的比较多，因为相比与SpringSecurity，Shiro的上手更加的简单。\n\n​	 一般Web应用的需要进行**认证**和**授权**。\n\n​		**认证：验证当前访问系统的是不是本系统的用户，并且要确认具体是哪个用户**\n\n​		**授权：经过认证后判断当前用户是否有权限进行某个操作**\n\n​	而认证和授权也是SpringSecurity作为安全框架的核心功能。\n\n\n\n## 1. 快速入门\n\n### 1.1 准备工作\n\n​	我们先要搭建一个简单的SpringBoot工程\n\n① 设置父工程 添加依赖\n\n~~~~\n    <parent>\n        <groupId>org.springframework.boot</groupId>\n        <artifactId>spring-boot-starter-parent</artifactId>\n        <version>2.5.0</version>\n    </parent>\n    <dependencies>\n        <dependency>\n            <groupId>org.springframework.boot</groupId>\n            <artifactId>spring-boot-starter-web</artifactId>\n        </dependency>\n        <dependency>\n            <groupId>org.projectlombok</groupId>\n            <artifactId>lombok</artifactId>\n            <optional>true</optional>\n        </dependency>\n    </dependencies>\n~~~~\n\n② 创建启动类\n\n~~~~\n@SpringBootApplication\npublic class SecurityApplication {\n\n    public static void main(String[] args) {\n        SpringApplication.run(SecurityApplication.class,args);\n    }\n}\n\n~~~~\n\n③ 创建Controller\n\n~~~~java\n\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport org.springframework.web.bind.annotation.RestController;\n\n@RestController\npublic class HelloController {\n\n    @RequestMapping(\"/hello\")\n    public String hello(){\n        return \"hello\";\n    }\n}\n\n~~~~\n\n\n\n### 1.2 引入SpringSecurity\n\n​	在SpringBoot项目中使用SpringSecurity我们只需要引入依赖即可实现入门案例。\n\n~~~~xml\n        <dependency>\n            <groupId>org.springframework.boot</groupId>\n            <artifactId>spring-boot-starter-security</artifactId>\n        </dependency>\n~~~~\n\n​	引入依赖后我们在尝试去访问之前的接口就会自动跳转到一个SpringSecurity的默认登陆页面，默认用户名是user,密码会输出在控制台。\n\n​	必须登陆之后才能对接口进行访问。\n\n\n\n## 2. 认证\n\n### 2.1 登陆校验流程\n![image20211215094003288.png](https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/31/414a87eeed344828b5b00ffa80178958.png)', 'SpringSecurity框架教程-Spring Security+JWT实现项目级前端分离认证授权', 1, 'https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/31/948597e164614902ab1662ba8452e106.png', '1', '0', 165, '0', NULL, '2022-01-23 23:20:11', NULL, NULL, 0);
INSERT INTO `yuuu_article` VALUES (2, 'weq', 'adadaeqe', 'adad', 2, 'https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/15/fd2e9460c58a4af3bbeae5d9ed581688.png', '1', '0', 24, '0', NULL, '2022-01-21 14:58:30', NULL, NULL, 0);
INSERT INTO `yuuu_article` VALUES (3, 'dad', 'asdasda', 'sadad', 1, 'https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/15/737a0ed0b8ea430d8700a12e76aa1cd1.png', '1', '0', 33, '0', NULL, '2022-01-18 14:58:34', NULL, NULL, 1);
INSERT INTO `yuuu_article` VALUES (5, 'sdad', '![Snipaste_20220115_165812.png](https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/15/1d9d283f5d874b468078b183e4b98b71.png)\r\n\r\n## sda \r\n\r\n222\r\n### sdasd newnewnew', NULL, 2, '', '1', '0', 46, '0', NULL, '2022-01-17 14:58:37', NULL, NULL, 0);
INSERT INTO `yuuu_article` VALUES (8, '111', '11111![微信图片_20211208162758.jpg](http://rr2wi3rfl.bkt.clouddn.com/2023/03/16/fec2e9b4cf294cd7bd9d501e924ad9ff.jpg)', '111', 1, 'http://rr2wi3rfl.bkt.clouddn.com/2023/03/16/3e7ad08fc40d4da39a46ac412566b0af.png', '1', '0', 0, '0', 1, '2023-03-16 13:37:35', 1, '2023-03-16 13:37:35', 1);
INSERT INTO `yuuu_article` VALUES (9, '1234', '111', '1234', 1, '', '1', '0', 0, '0', 1, '2023-03-17 05:10:00', 1, '2023-03-17 05:10:00', 1);
INSERT INTO `yuuu_article` VALUES (10, '12345', '111', '12345', 1, '', '0', '0', 0, '1', 1, '2023-03-17 05:11:14', 1, '2023-03-17 05:11:14', 1);
INSERT INTO `yuuu_article` VALUES (11, '11111', '11111![微信图片_20211208162758.jpg](http://rr2wi3rfl.bkt.clouddn.com/2023/03/16/fec2e9b4cf294cd7bd9d501e924ad9ff.jpg)', '11111', 1, 'http://rr2wi3rfl.bkt.clouddn.com/2023/03/16/3e7ad08fc40d4da39a46ac412566b0af.png', '1', '0', 0, '0', 1, '2023-03-19 10:39:36', 1, '2023-03-19 10:39:36', 1);
INSERT INTO `yuuu_article` VALUES (13, '1112', '11111![微信图片_20211208162758.jpg](http://rr2wi3rfl.bkt.clouddn.com/2023/03/16/fec2e9b4cf294cd7bd9d501e924ad9ff.jpg)', '1112', 1, 'http://rr2wi3rfl.bkt.clouddn.com/2023/03/16/3e7ad08fc40d4da39a46ac412566b0af.png', '1', '0', 0, '0', 1, '2023-03-19 11:13:50', 1, '2023-03-19 11:13:50', 1);
INSERT INTO `yuuu_article` VALUES (14, '11', '11111![微信图片_20211208162758.jpg](http://rr2wi3rfl.bkt.clouddn.com/2023/03/16/fec2e9b4cf294cd7bd9d501e924ad9ff.jpg)', '1', 1, 'http://rr2wi3rfl.bkt.clouddn.com/2023/03/16/3e7ad08fc40d4da39a46ac412566b0af.png', '1', '0', 0, '0', 1, '2023-03-19 11:16:01', 1, '2023-03-19 11:16:01', 1);
INSERT INTO `yuuu_article` VALUES (15, '14', '11111![微信图片_20211208162758.jpg](http://rr2wi3rfl.bkt.clouddn.com/2023/03/16/fec2e9b4cf294cd7bd9d501e924ad9ff.jpg)', '14', 1, 'http://rr2wi3rfl.bkt.clouddn.com/2023/03/16/3e7ad08fc40d4da39a46ac412566b0af.png', '1', '0', 0, '0', 1, '2023-03-19 11:18:57', 1, '2023-03-19 11:18:57', 1);
INSERT INTO `yuuu_article` VALUES (16, '16', '11111![微信图片_20211208162758.jpg](http://rr2wi3rfl.bkt.clouddn.com/2023/03/16/fec2e9b4cf294cd7bd9d501e924ad9ff.jpg)', '16', 1, 'http://rr2wi3rfl.bkt.clouddn.com/2023/03/16/3e7ad08fc40d4da39a46ac412566b0af.png', '1', '0', 0, '0', 1, '2023-03-19 11:21:42', 1, '2023-03-19 11:26:02', 1);
INSERT INTO `yuuu_article` VALUES (17, '最后测评', '测试111', '测试成功', 1, '', '0', '0', 0, '0', 1, '2023-03-19 11:49:55', 1, '2023-03-19 11:51:16', 1);
INSERT INTO `yuuu_article` VALUES (18, '新建文章测试', '# 1 ', NULL, 1, 'http://rr2wi3rfl.bkt.clouddn.com/2023/05/31/7b4fd82aeba64a75904e1ede0a41aa22.jpg', '1', '1', 0, '0', 1, '2023-05-31 17:22:39', 1, '2023-05-31 21:02:37', 0);
INSERT INTO `yuuu_article` VALUES (19, '', '', NULL, NULL, '', '1', '1', 0, '0', 1, '2023-05-31 17:23:34', 1, '2023-05-31 17:23:34', 1);
INSERT INTO `yuuu_article` VALUES (20, '测试保存草稿箱', '', NULL, NULL, '', '1', '1', 0, '0', 1, '2023-05-31 17:24:57', 1, '2023-05-31 17:24:57', 0);
INSERT INTO `yuuu_article` VALUES (21, '21点01分', '# 2023', '2023', 1, '', '1', '0', 3, '0', 1, '2023-05-31 21:02:12', 1, '2023-05-31 21:02:12', 0);
INSERT INTO `yuuu_article` VALUES (22, '111', '### 字号一\n#### 字号二', '111', 1, 'http://rr2wi3rfl.bkt.clouddn.com/2023/06/07/0311b83b8d94430cad6bf14d769195d3.jpg', '0', '0', 5, '0', 1, '2023-06-07 17:54:01', 1, '2023-06-07 17:54:01', 0);

-- ----------------------------
-- Table structure for yuuu_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `yuuu_article_tag`;
CREATE TABLE `yuuu_article_tag`  (
  `article_id` bigint NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `tag_id` bigint NOT NULL DEFAULT 0 COMMENT '标签id',
  PRIMARY KEY (`article_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章标签关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of yuuu_article_tag
-- ----------------------------
INSERT INTO `yuuu_article_tag` VALUES (1, 4);
INSERT INTO `yuuu_article_tag` VALUES (2, 1);
INSERT INTO `yuuu_article_tag` VALUES (2, 4);
INSERT INTO `yuuu_article_tag` VALUES (3, 4);
INSERT INTO `yuuu_article_tag` VALUES (3, 5);
INSERT INTO `yuuu_article_tag` VALUES (4, 4);
INSERT INTO `yuuu_article_tag` VALUES (5, 14);
INSERT INTO `yuuu_article_tag` VALUES (6, 4);
INSERT INTO `yuuu_article_tag` VALUES (7, 6);
INSERT INTO `yuuu_article_tag` VALUES (8, 4);
INSERT INTO `yuuu_article_tag` VALUES (9, 1);
INSERT INTO `yuuu_article_tag` VALUES (10, 5);
INSERT INTO `yuuu_article_tag` VALUES (11, 6);
INSERT INTO `yuuu_article_tag` VALUES (12, 4);
INSERT INTO `yuuu_article_tag` VALUES (13, 1);
INSERT INTO `yuuu_article_tag` VALUES (14, 4);
INSERT INTO `yuuu_article_tag` VALUES (18, 4);
INSERT INTO `yuuu_article_tag` VALUES (19, 4);
INSERT INTO `yuuu_article_tag` VALUES (20, 4);

-- ----------------------------
-- Table structure for yuuu_category
-- ----------------------------
DROP TABLE IF EXISTS `yuuu_category`;
CREATE TABLE `yuuu_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类名',
  `pid` bigint NULL DEFAULT -1 COMMENT '父分类id，如果没有父分类为-1',
  `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态0:正常,1禁用',
  `create_by` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_by` bigint NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of yuuu_category
-- ----------------------------
INSERT INTO `yuuu_category` VALUES (1, 'java', -1, 'wsd', '0', NULL, NULL, NULL, NULL, 0);
INSERT INTO `yuuu_category` VALUES (2, 'PHP', -1, 'wsd', '0', NULL, NULL, NULL, NULL, 0);
INSERT INTO `yuuu_category` VALUES (15, 'Spring', -1, 'Spring全家桶', '1', NULL, NULL, NULL, NULL, 1);

-- ----------------------------
-- Table structure for yuuu_comment
-- ----------------------------
DROP TABLE IF EXISTS `yuuu_comment`;
CREATE TABLE `yuuu_comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '评论类型（0代表文章评论，1代表友链评论）',
  `article_id` bigint NULL DEFAULT NULL COMMENT '文章id',
  `root_id` bigint NULL DEFAULT -1 COMMENT '根评论id',
  `content` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论内容',
  `to_comment_user_id` bigint NULL DEFAULT -1 COMMENT '所回复的目标评论的userid',
  `to_comment_id` bigint NULL DEFAULT -1 COMMENT '回复目标评论id',
  `create_by` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_by` bigint NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of yuuu_comment
-- ----------------------------
INSERT INTO `yuuu_comment` VALUES (1, '0', 1, -1, 'asS', -1, -1, 1, '2022-01-29 07:59:22', 1, '2022-01-29 07:59:22', 0);
INSERT INTO `yuuu_comment` VALUES (2, '0', 1, -1, '[哈哈]SDAS', -1, -1, 1, '2022-01-29 08:01:24', 1, '2022-01-29 08:01:24', 0);
INSERT INTO `yuuu_comment` VALUES (3, '0', 1, -1, '是大多数', -1, -1, 1, '2022-01-29 16:07:24', 1, '2022-01-29 16:07:24', 0);
INSERT INTO `yuuu_comment` VALUES (4, '0', 1, -1, '撒大声地', -1, -1, 1, '2022-01-29 16:12:09', 1, '2022-01-29 16:12:09', 0);
INSERT INTO `yuuu_comment` VALUES (5, '0', 1, -1, '你再说什么', -1, -1, 1, '2022-01-29 18:19:56', 1, '2022-01-29 18:19:56', 0);
INSERT INTO `yuuu_comment` VALUES (6, '0', 1, -1, 'hffd', -1, -1, 1, '2022-01-29 22:13:52', 1, '2022-01-29 22:13:52', 0);
INSERT INTO `yuuu_comment` VALUES (9, '0', 1, 2, '你说什么', 1, 2, 1, '2022-01-29 22:18:40', 1, '2022-01-29 22:18:40', 0);
INSERT INTO `yuuu_comment` VALUES (10, '0', 1, 2, '哈哈哈哈[哈哈]', 1, 9, 1, '2022-01-29 22:29:15', 1, '2022-01-29 22:29:15', 0);
INSERT INTO `yuuu_comment` VALUES (11, '0', 1, 2, 'we全文', 1, 10, 3, '2022-01-29 22:29:55', 1, '2022-01-29 22:29:55', 0);
INSERT INTO `yuuu_comment` VALUES (12, '0', 1, -1, '王企鹅', -1, -1, 1, '2022-01-29 22:30:20', 1, '2022-01-29 22:30:20', 0);
INSERT INTO `yuuu_comment` VALUES (13, '0', 1, -1, '什么阿是', -1, -1, 1, '2022-01-29 22:30:56', 1, '2022-01-29 22:30:56', 0);
INSERT INTO `yuuu_comment` VALUES (14, '0', 1, -1, '新平顶山', -1, -1, 1, '2022-01-29 22:32:51', 1, '2022-01-29 22:32:51', 0);
INSERT INTO `yuuu_comment` VALUES (15, '0', 1, -1, '2222', -1, -1, 1, '2022-01-29 22:34:38', 1, '2022-01-29 22:34:38', 0);
INSERT INTO `yuuu_comment` VALUES (16, '0', 1, 2, '3333', 1, 11, 1, '2022-01-29 22:34:47', 1, '2022-01-29 22:34:47', 0);
INSERT INTO `yuuu_comment` VALUES (17, '0', 1, 2, '回复weqedadsd', 3, 11, 1, '2022-01-29 22:38:00', 1, '2022-01-29 22:38:00', 0);
INSERT INTO `yuuu_comment` VALUES (18, '0', 1, -1, 'sdasd', -1, -1, 1, '2022-01-29 23:18:19', 1, '2022-01-29 23:18:19', 0);
INSERT INTO `yuuu_comment` VALUES (19, '0', 1, -1, '111', -1, -1, 1, '2022-01-29 23:22:23', 1, '2022-01-29 23:22:23', 0);
INSERT INTO `yuuu_comment` VALUES (20, '0', 1, 1, '你说啥？', 1, 1, 1, '2022-01-30 10:06:21', 1, '2022-01-30 10:06:21', 0);
INSERT INTO `yuuu_comment` VALUES (21, '0', 1, -1, '友链添加个呗', -1, -1, 1, '2022-01-30 10:06:50', 1, '2022-01-30 10:06:50', 0);
INSERT INTO `yuuu_comment` VALUES (22, '1', 1, -1, '友链评论2', -1, -1, 1, '2022-01-30 10:08:28', 1, '2022-01-30 10:08:28', 0);
INSERT INTO `yuuu_comment` VALUES (23, '1', 1, 22, '回复友链评论3', 1, 22, 1, '2022-01-30 10:08:50', 1, '2022-01-30 10:08:50', 0);
INSERT INTO `yuuu_comment` VALUES (24, '1', 1, -1, '友链评论4444', -1, -1, 1, '2022-01-30 10:09:03', 1, '2022-01-30 10:09:03', 0);
INSERT INTO `yuuu_comment` VALUES (25, '1', 1, 22, '收到的', 1, 22, 1, '2022-01-30 10:13:28', 1, '2022-01-30 10:13:28', 0);
INSERT INTO `yuuu_comment` VALUES (26, '0', 1, -1, 'sda', -1, -1, 1, '2022-01-30 10:39:05', 1, '2022-01-30 10:39:05', 0);
INSERT INTO `yuuu_comment` VALUES (27, '0', 1, 1, '说你咋地', 1, 20, 14787164048662, '2022-01-30 17:19:30', 14787164048662, '2022-01-30 17:19:30', 0);
INSERT INTO `yuuu_comment` VALUES (28, '0', 1, 1, 'sdad', 1, 1, 14787164048662, '2022-01-31 11:11:20', 14787164048662, '2022-01-31 11:11:20', 0);
INSERT INTO `yuuu_comment` VALUES (29, '0', 1, -1, '你说是的ad', -1, -1, 14787164048662, '2022-01-31 14:10:11', 14787164048662, '2022-01-31 14:10:11', 0);
INSERT INTO `yuuu_comment` VALUES (30, '0', 1, 1, '撒大声地', 1, 1, 14787164048662, '2022-01-31 20:19:18', 14787164048662, '2022-01-31 20:19:18', 0);
INSERT INTO `yuuu_comment` VALUES (33, '0', 1, -1, '09点44分', -1, -1, 1, '2023-03-06 09:44:50', 1, '2023-03-06 09:44:50', 0);
INSERT INTO `yuuu_comment` VALUES (34, '1', 1, -1, '10点09分', -1, -1, 1, '2023-03-06 10:09:49', 1, '2023-03-06 10:09:49', 0);
INSERT INTO `yuuu_comment` VALUES (35, '1', 1, 34, '111', 1, 34, 1, '2023-03-06 10:10:05', 1, '2023-03-06 10:10:05', 0);
INSERT INTO `yuuu_comment` VALUES (36, '0', 1, -1, '15点22分', -1, -1, 14787164048663, '2023-03-07 15:22:28', 14787164048663, '2023-03-07 15:22:28', 0);
INSERT INTO `yuuu_comment` VALUES (37, '1', 1, -1, '[右哼哼]', -1, -1, 14787164048680, '2023-07-08 09:27:11', 14787164048680, '2023-07-08 09:27:11', 0);
INSERT INTO `yuuu_comment` VALUES (38, '0', 22, -1, '11', -1, -1, 1, '2023-08-11 10:08:23', 1, '2023-08-11 10:08:23', 0);

-- ----------------------------
-- Table structure for yuuu_link
-- ----------------------------
DROP TABLE IF EXISTS `yuuu_link`;
CREATE TABLE `yuuu_link`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `logo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '网站地址',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '2' COMMENT '审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)',
  `create_by` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_by` bigint NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '友链' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of yuuu_link
-- ----------------------------
INSERT INTO `yuuu_link` VALUES (1, 'sda', 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn1.itc.cn%2Fimg8%2Fwb%2Frecom%2F2016%2F05%2F10%2F146286696706220328.PNG&refer=http%3A%2F%2Fn1.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1646205529&t=f942665181eb9b0685db7a6f59d59975', 'sda', 'https://www.baidu.com', '0', NULL, '2022-01-13 08:25:47', NULL, '2022-01-13 08:36:14', 0);
INSERT INTO `yuuu_link` VALUES (2, 'sda', 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn1.itc.cn%2Fimg8%2Fwb%2Frecom%2F2016%2F05%2F10%2F146286696706220328.PNG&refer=http%3A%2F%2Fn1.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1646205529&t=f942665181eb9b0685db7a6f59d59975', 'dada', 'https://www.qq.com', '0', NULL, '2022-01-13 09:06:10', NULL, '2022-01-13 09:07:09', 0);
INSERT INTO `yuuu_link` VALUES (3, 'sa', 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn1.itc.cn%2Fimg8%2Fwb%2Frecom%2F2016%2F05%2F10%2F146286696706220328.PNG&refer=http%3A%2F%2Fn1.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1646205529&t=f942665181eb9b0685db7a6f59d59975', 'da', 'https://www.taobao.com', '0', NULL, '2022-01-13 09:23:01', 1, '2023-03-16 13:07:18', 0);
INSERT INTO `yuuu_link` VALUES (4, '333', '11', '222', '11', '2', 1, '2023-03-16 12:42:50', 1, '2023-03-16 13:03:12', 1);
INSERT INTO `yuuu_link` VALUES (5, '222', '111', '111', '111', '1', 1, '2023-03-16 13:07:28', 1, '2023-03-16 13:07:33', 1);

-- ----------------------------
-- Table structure for yuuu_tag
-- ----------------------------
DROP TABLE IF EXISTS `yuuu_tag`;
CREATE TABLE `yuuu_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签名',
  `create_by` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_by` bigint NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '标签' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of yuuu_tag
-- ----------------------------
INSERT INTO `yuuu_tag` VALUES (1, 'Mybatis', NULL, NULL, NULL, '2022-01-11 09:20:50', 0, 'weqwe');
INSERT INTO `yuuu_tag` VALUES (2, 'asdas', NULL, '2022-01-11 09:20:55', NULL, '2022-01-11 09:20:55', 1, 'weqw');
INSERT INTO `yuuu_tag` VALUES (3, 'weqw', NULL, '2022-01-11 09:21:07', NULL, '2022-01-11 09:21:07', 1, 'qweqwe');
INSERT INTO `yuuu_tag` VALUES (4, 'Java', NULL, '2022-01-13 15:22:43', NULL, '2022-01-13 15:22:43', 0, 'sdad');
INSERT INTO `yuuu_tag` VALUES (5, 'WAD', NULL, '2022-01-13 15:22:47', NULL, '2022-01-13 15:22:47', 0, 'ASDAD');
INSERT INTO `yuuu_tag` VALUES (6, 'CCC', 6, '2023-03-15 14:16:13', 1, '2023-03-16 05:49:07', 0, 'CCC');
INSERT INTO `yuuu_tag` VALUES (9, NULL, 1, '2023-03-16 01:17:11', NULL, '2023-03-16 01:17:11', 1, NULL);
INSERT INTO `yuuu_tag` VALUES (10, NULL, 1, '2023-03-16 01:21:58', NULL, '2023-03-16 01:21:58', 1, NULL);
INSERT INTO `yuuu_tag` VALUES (11, 'c', 6, '2023-03-16 03:01:05', 1, '2023-03-16 12:44:15', 0, 'c');
INSERT INTO `yuuu_tag` VALUES (12, NULL, 1, '2023-03-16 03:03:32', NULL, '2023-03-16 03:03:32', 1, NULL);
INSERT INTO `yuuu_tag` VALUES (13, NULL, 1, '2023-03-16 03:07:30', NULL, '2023-03-16 03:07:30', 1, NULL);
INSERT INTO `yuuu_tag` VALUES (14, '1111', 1, '2023-03-16 03:08:18', NULL, '2023-03-16 03:08:18', 0, '1111');
INSERT INTO `yuuu_tag` VALUES (15, 'C#', 1, '2023-03-16 04:50:44', NULL, '2023-03-16 04:50:44', 0, 'C#');
INSERT INTO `yuuu_tag` VALUES (16, 'C##', 1, '2023-03-16 04:51:41', NULL, '2023-03-16 04:51:41', 0, 'C##');
INSERT INTO `yuuu_tag` VALUES (17, 'cs1', 1, '2023-03-16 05:50:09', 1, '2023-03-16 05:50:18', 1, '测试');
INSERT INTO `yuuu_tag` VALUES (18, '12', 1, '2023-03-16 05:50:50', 1, '2023-03-17 04:16:15', 0, '1');
INSERT INTO `yuuu_tag` VALUES (19, '2', 1, '2023-03-16 05:50:54', NULL, '2023-03-16 05:50:54', 0, '2');
INSERT INTO `yuuu_tag` VALUES (20, '3', 1, '2023-03-16 05:50:59', NULL, '2023-03-16 05:50:59', 0, '3');
INSERT INTO `yuuu_tag` VALUES (21, '1234', 1, '2023-03-17 00:56:03', NULL, '2023-03-17 00:56:03', 1, '1234');

SET FOREIGN_KEY_CHECKS = 1;
