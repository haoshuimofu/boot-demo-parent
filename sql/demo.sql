/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50163
Source Host           : localhost:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50163
File Encoding         : 65001

Date: 2019-04-06 17:23:24
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `store_product_batch_monitor`
-- ----------------------------
DROP TABLE IF EXISTS `store_product_batch_monitor`;
CREATE TABLE `store_product_batch_monitor` (
  `id` int(11) NOT NULL DEFAULT '0',
  `store_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `batch_id` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `test_index` (`store_id`,`product_id`,`batch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of store_product_batch_monitor
-- ----------------------------

-- ----------------------------
-- Table structure for `t_test`
-- ----------------------------
DROP TABLE IF EXISTS `t_test`;
CREATE TABLE `t_test` (
  `id_first` int(11) NOT NULL DEFAULT '0',
  `id_two` int(11) NOT NULL DEFAULT '0',
  `gender` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_first`,`id_two`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_test
-- ----------------------------

-- ----------------------------
-- Table structure for `t_users`
-- ----------------------------
DROP TABLE IF EXISTS `t_users`;
CREATE TABLE `t_users` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `name` varchar(40) NOT NULL,
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `age` int(3) NOT NULL DEFAULT '0',
  `description` text,
  `field1` varchar(20) NOT NULL DEFAULT '',
  `field2` varchar(20) DEFAULT '',
  `field3` varchar(20) DEFAULT NULL,
  `field4` varchar(20) DEFAULT NULL,
  `field5` varchar(20) DEFAULT NULL,
  `field6` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_users
-- ----------------------------
