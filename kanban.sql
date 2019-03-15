/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50163
 Source Host           : localhost:3306
 Source Schema         : kanban

 Target Server Type    : MySQL
 Target Server Version : 50163
 File Encoding         : 65001

 Date: 15/03/2019 20:12:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_product_monitor
-- ----------------------------
DROP TABLE IF EXISTS `t_product_monitor`;
CREATE TABLE `t_product_monitor`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `sku_sum` int(11) NOT NULL DEFAULT 0 COMMENT '统计了多少样商品？',
  `expiring_sum` int(11) NOT NULL DEFAULT 0 COMMENT '临期商品数',
  `expired_sum` int(11) NOT NULL DEFAULT 0 COMMENT '过期商品数',
  `count_time` int(10) NOT NULL COMMENT '统计时间，格式yyyymmddhh',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_store_product_monitor
-- ----------------------------
DROP TABLE IF EXISTS `t_store_product_monitor`;
CREATE TABLE `t_store_product_monitor`  (
  `id` bigint(11) NOT NULL COMMENT '自增长主键',
  `type` smallint(1) NOT NULL COMMENT '类型：1、临期；2、过期',
  `store_id` int(11) NOT NULL COMMENT '店铺ID',
  `product_id` int(11) NOT NULL COMMENT '商品ID',
  `total` int(11) NOT NULL DEFAULT 0 COMMENT '商品合计数',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_store_product_monitor_item
-- ----------------------------
DROP TABLE IF EXISTS `t_store_product_monitor_item`;
CREATE TABLE `t_store_product_monitor_item`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `type` smallint(1) NOT NULL COMMENT '类型：1、临期；2、过期',
  `store_id` int(11) NOT NULL COMMENT '门店ID',
  `product_id` int(11) NOT NULL COMMENT '商品ID',
  `batch_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '批次ID',
  `batch_update_time` timestamp NULL DEFAULT NULL COMMENT 'store_product_batch.update_time',
  `total` int(11) NOT NULL COMMENT '商品合计数',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
