/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50163
Source Host           : localhost:3306
Source Database       : db_order

Target Server Type    : MYSQL
Target Server Version : 50163
File Encoding         : 65001

Date: 2019-04-17 15:07:09
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `t_orders`
-- ----------------------------
DROP TABLE IF EXISTS `t_orders`;
CREATE TABLE `t_orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(20) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_orders
-- ----------------------------
