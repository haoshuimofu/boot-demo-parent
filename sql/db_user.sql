/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50163
Source Host           : localhost:3306
Source Database       : db_user

Target Server Type    : MYSQL
Target Server Version : 50163
File Encoding         : 65001

Date: 2019-04-17 15:47:09
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `t_users`
-- ----------------------------
DROP TABLE IF EXISTS `t_users`;
CREATE TABLE `t_users` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `name` varchar(100) NOT NULL,
  `age` int(3) NOT NULL DEFAULT '0',
  `description` text,
  `register_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `field1` varchar(20) DEFAULT '',
  `field2` varchar(20) DEFAULT '',
  `field3` varchar(20) DEFAULT '',
  `field4` varchar(20) DEFAULT '',
  `field5` varchar(20) DEFAULT '',
  `field6` varchar(20) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_users
-- ----------------------------
INSERT INTO `t_users` VALUES ('3', 'User_8b412b57-b07e-4676-8bda-38170a2682cb', '30', 'User_8b412b57-b07e-4676-8bda-38170a2682cb', '2019-04-17 15:32:30', '2019-04-17 15:32:30', '2019-04-17 15:32:30', null, null, null, null, null, null);
INSERT INTO `t_users` VALUES ('4', 'User_357ee914-dcee-4ea4-80e1-afee79aec82c', '30', 'User_357ee914-dcee-4ea4-80e1-afee79aec82c', '2019-04-17 15:33:38', '2019-04-17 15:33:38', '2019-04-17 15:33:38', null, null, null, null, null, null);
