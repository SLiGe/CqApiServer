/*
 Navicat Premium Data Transfer

 Source Server         : LocalHost
 Source Server Type    : MySQL
 Source Server Version : 50646
 Source Host           : 192.168.137.3:3306
 Source Schema         : niurensec

 Target Server Type    : MySQL
 Target Server Version : 50646
 File Encoding         : 65001

 Date: 05/02/2020 19:15:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for qr_app_notice
-- ----------------------------
DROP TABLE IF EXISTS `qr_app_notice`;
CREATE TABLE `qr_app_notice`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `app_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应用代码',
  `update_date` datetime(0) NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '应用公告表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qr_app_version
-- ----------------------------
DROP TABLE IF EXISTS `qr_app_version`;
CREATE TABLE `qr_app_version`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用名称',
  `app_current_version` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用当前版本',
  `app_download_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用下载地址',
  `app_file_id` bigint(20) NULL DEFAULT NULL COMMENT '关联文件表ID',
  `app_force_version` int(1) NOT NULL COMMENT '是否强制更新',
  `app_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用代码',
  `update_description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新说明',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'app版本表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qr_beasen
-- ----------------------------
DROP TABLE IF EXISTS `qr_beasen`;
CREATE TABLE `qr_beasen`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `sentence` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4454 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '一言表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qr_biaobai
-- ----------------------------
DROP TABLE IF EXISTS `qr_biaobai`;
CREATE TABLE `qr_biaobai`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表白内容',
  `qq` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'QQ号码',
  `me` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '你的昵称',
  `you` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对方昵称',
  `addtime` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `uptime` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qr_config
-- ----------------------------
DROP TABLE IF EXISTS `qr_config`;
CREATE TABLE `qr_config`  (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `PASSWORD` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `EXTEND` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `DESCRIPTION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '信息配置表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qr_file
-- ----------------------------
DROP TABLE IF EXISTS `qr_file`;
CREATE TABLE `qr_file`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名',
  `file_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件路径',
  `file_size` decimal(10, 0) NULL DEFAULT NULL COMMENT '文件大小',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qr_fortune_data
-- ----------------------------
DROP TABLE IF EXISTS `qr_fortune_data`;
CREATE TABLE `qr_fortune_data`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `QQ` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'QQ号',
  `JSON_DATA` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'JSON数据',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `UPDATE_DATE` datetime(0) NOT NULL COMMENT '修改日期',
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `QQ_INDEX`(`QQ`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22720 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '今日运势数据储存' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qr_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `qr_operate_log`;
CREATE TABLE `qr_operate_log`  (
  `operate_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '业务标题',
  `method` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求方式',
  `operate_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求URL',
  `operate_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '主机地址',
  `operate_param` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '返回参数',
  `status` int(1) NULL DEFAULT 0 COMMENT '操作状态（1正常 0异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '错误消息',
  `operate_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`operate_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 152555 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志记录' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qr_points_luck_draw_prize
-- ----------------------------
DROP TABLE IF EXISTS `qr_points_luck_draw_prize`;
CREATE TABLE `qr_points_luck_draw_prize`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '奖品名称',
  `url` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `value` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '类型1:红包2:积分3:礼物4:谢谢惠顾5:自定义',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态',
  `is_del` bit(1) NULL DEFAULT NULL COMMENT '是否删除',
  `position` int(5) NULL DEFAULT NULL COMMENT '位置',
  `phase` int(10) NULL DEFAULT NULL COMMENT '期数',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '奖品表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qr_points_luck_draw_probability
-- ----------------------------
DROP TABLE IF EXISTS `qr_points_luck_draw_probability`;
CREATE TABLE `qr_points_luck_draw_probability`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `points_prize_id` bigint(20) NULL DEFAULT NULL COMMENT '奖品ID',
  `points_prize_phase` int(10) NULL DEFAULT NULL COMMENT '奖品期数',
  `probability` float(4, 2) NULL DEFAULT NULL COMMENT '概率',
  `frozen` int(11) NULL DEFAULT NULL COMMENT '商品抽中后的冷冻次数',
  `prize_day_max_times` int(11) NULL DEFAULT NULL COMMENT '该商品平台每天最多抽中的次数',
  `user_prize_month_max_times` int(11) NULL DEFAULT NULL COMMENT '每位用户每月最多抽中该商品的次数',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抽奖概率限制表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qr_points_luck_draw_record
-- ----------------------------
DROP TABLE IF EXISTS `qr_points_luck_draw_record`;
CREATE TABLE `qr_points_luck_draw_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `member_mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '中奖用户手机号',
  `points` int(11) NULL DEFAULT NULL COMMENT '消耗积分',
  `prize_id` bigint(20) NULL DEFAULT NULL COMMENT '奖品ID',
  `result` smallint(4) NULL DEFAULT NULL COMMENT '1:中奖 2:未中奖',
  `month` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '中奖月份',
  `daily` datetime(0) NULL DEFAULT NULL COMMENT '中奖日期（不包括时间）',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抽奖记录表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qr_robot
-- ----------------------------
DROP TABLE IF EXISTS `qr_robot`;
CREATE TABLE `qr_robot`  (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `client_qq` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户QQ',
  `robot_qq` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机器人QQ',
  `group_num` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授权群号',
  `start_date` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_date` datetime(0) NULL DEFAULT NULL COMMENT '到期时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '机器人授权表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qr_short_url
-- ----------------------------
DROP TABLE IF EXISTS `qr_short_url`;
CREATE TABLE `qr_short_url`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `original_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '原始地址',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '随机字符',
  `expire_date` timestamp(0) NULL DEFAULT NULL COMMENT '到期时间',
  `create_date` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `code`(`code`) USING BTREE,
  INDEX `url`(`original_url`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3311 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qr_signIn_data
-- ----------------------------
DROP TABLE IF EXISTS `qr_signIn_data`;
CREATE TABLE `qr_signIn_data`  (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `QQ` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `INTEGRAL` int(255) NOT NULL COMMENT '积分',
  `DAYS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '连续签到天数',
  `CREATE_DATE` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `ID`(`ID`) USING BTREE,
  INDEX `QQ`(`QQ`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8605 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'QQ签到表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qr_signIn_days
-- ----------------------------
DROP TABLE IF EXISTS `qr_signIn_days`;
CREATE TABLE `qr_signIn_days`  (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `QQ` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `MDAYS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '月连续签到天数',
  `TDAYS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '总天数',
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `qq_index`(`QQ`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8600 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '签到天数关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qr_user
-- ----------------------------
DROP TABLE IF EXISTS `qr_user`;
CREATE TABLE `qr_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `login_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录账号',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '头像路径',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '密码',
  `salt` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '盐加密',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '帐号状态（1正常 0停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（1代表存在 2代表删除）',
  `login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后登陆IP',
  `login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后登陆时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
