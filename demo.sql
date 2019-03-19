/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50163
Source Host           : localhost:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50163
File Encoding         : 65001

Date: 2019-03-18 23:47:22
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `t_users`
-- ----------------------------
DROP TABLE IF EXISTS `t_users`;
CREATE TABLE `t_users` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `name` varchar(40) NOT NULL,
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `age` int(3) NOT NULL DEFAULT '0',
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
