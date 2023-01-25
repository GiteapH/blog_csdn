/*
 Navicat Premium Data Transfer

 Source Server         : Blog
 Source Server Type    : MySQL
 Source Server Version : 80027 (8.0.27)
 Source Host           : localhost:3306
 Source Schema         : userinfo

 Target Server Type    : MySQL
 Target Server Version : 80027 (8.0.27)
 File Encoding         : 65001

 Date: 25/01/2023 15:59:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for articles
-- ----------------------------
DROP TABLE IF EXISTS `articles`;
CREATE TABLE `articles`  (
  `aid` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章id\r\n',
  `uid` int NOT NULL COMMENT '上传者id',
  `articles` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章内容',
  `date` datetime NOT NULL COMMENT '发布日期',
  `like` int NOT NULL DEFAULT 0 COMMENT '点赞数',
  `dislike` int NULL DEFAULT 0 COMMENT '点踩数',
  `comments` int NOT NULL DEFAULT 0 COMMENT '评论数',
  `watched` int NOT NULL DEFAULT 0 COMMENT '观看数',
  `collected` int NOT NULL DEFAULT 0 COMMENT '收藏数',
  `classPer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '文章类别，用户自定义',
  `classSys` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '文章类别，系统定义',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章标题',
  `tinyMes` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '简介',
  PRIMARY KEY (`aid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1167 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `uid` int NOT NULL COMMENT '文章发布者id',
  `aid` int NOT NULL COMMENT '文章id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '评论内容',
  `from_uid` int NULL DEFAULT NULL COMMENT '评论者id',
  `to_uid` int NULL DEFAULT NULL COMMENT '目标评论者id',
  `cid` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `date` datetime NOT NULL COMMENT '评论时间',
  `precid` int NULL DEFAULT NULL COMMENT '上层评论id',
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 89 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for good_collections
-- ----------------------------
DROP TABLE IF EXISTS `good_collections`;
CREATE TABLE `good_collections`  (
  `collect_id` int NOT NULL AUTO_INCREMENT COMMENT '收藏主键',
  `good_uid` int NOT NULL COMMENT '用户id',
  `time` datetime NULL DEFAULT NULL COMMENT '收藏时间',
  `good_aid` int NOT NULL COMMENT '文章id',
  PRIMARY KEY (`collect_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for good_points
-- ----------------------------
DROP TABLE IF EXISTS `good_points`;
CREATE TABLE `good_points`  (
  `good_uid` int NOT NULL COMMENT '用户id',
  `time` datetime NULL DEFAULT NULL COMMENT '时间',
  `type` int NOT NULL COMMENT '点赞类型（评论，文章）\r\n0 点赞 评论\r\n1 点踩 评论\r\n2 点赞 文章\r\n3 点踩 文章\r\n4 收藏 文章',
  `good_cid` int NULL DEFAULT NULL COMMENT '评论id',
  `good_aid` int NULL DEFAULT NULL COMMENT '文章id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_code
-- ----------------------------
DROP TABLE IF EXISTS `user_code`;
CREATE TABLE `user_code`  (
  `code` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `uid` int NOT NULL,
  `expire_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_fan
-- ----------------------------
DROP TABLE IF EXISTS `user_fan`;
CREATE TABLE `user_fan`  (
  `fid` int NOT NULL AUTO_INCREMENT COMMENT '关注主键',
  `f_uid` int NOT NULL COMMENT '粉丝用户id',
  `f_touid` int NULL DEFAULT NULL COMMENT '关注的up用户id',
  `f_date` datetime NULL DEFAULT NULL COMMENT '关注时间',
  `f_type` tinyint NULL DEFAULT 0 COMMENT '关注列表类型',
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_modules
-- ----------------------------
DROP TABLE IF EXISTS `user_modules`;
CREATE TABLE `user_modules`  (
  `uid` int NOT NULL COMMENT '用户id',
  `open_modules` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0,1,2,3' COMMENT '开启的模块\r\n0 收藏\r\n1 关注\r\n2 发布\r\n3 打卡\r\n逗号分隔\r\n',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_token
-- ----------------------------
DROP TABLE IF EXISTS `user_token`;
CREATE TABLE `user_token`  (
  `user_id` bigint NOT NULL COMMENT '用户主键id',
  `token` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'token值(32位字符串)',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `expire_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'token过期时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `uq_token`(`token` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userName` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(33) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `head` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cardImg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `describe` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `lockedFlag` tinyint NULL DEFAULT 0,
  `isDeleted` tinyint NULL DEFAULT 0,
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`, `email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1015 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
