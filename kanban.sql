/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.40.160
 Source Server Type    : MySQL
 Source Server Version : 50625
 Source Host           : 192.168.40.160:3306
 Source Schema         : kanban

 Target Server Type    : MySQL
 Target Server Version : 50625
 File Encoding         : 65001

 Date: 04/04/2019 19:58:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for batch_version
-- ----------------------------
DROP TABLE IF EXISTS `batch_version`;
CREATE TABLE `batch_version`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务名称',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '本版号',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_task_name`(`task_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '批次数据版本' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for car
-- ----------------------------
DROP TABLE IF EXISTS `car`;
CREATE TABLE `car`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `car_brand` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '车牌号',
  `so_id` int(11) NOT NULL DEFAULT 0 COMMENT '服务id',
  `dept_id` int(11) NOT NULL DEFAULT 0 COMMENT '部门id',
  `dept_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '部门名称',
  `car_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '运输类型 1:冷鲜 2:百货 3:冷冻 4:冷库 5:蔬果 6:加班车辆 7:杭州-冷冻、冷藏 8:杭州-蔬果/百货',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_so_id`(`so_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11894 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for car_avg_temperature
-- ----------------------------
DROP TABLE IF EXISTS `car_avg_temperature`;
CREATE TABLE `car_avg_temperature`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `car_brand` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '车牌号',
  `so_id` int(11) NOT NULL DEFAULT 0 COMMENT '服务id',
  `car_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '运输类型 1:冷鲜 2:百货 3:冷冻 4:冷库 5:蔬果 6:加班车辆 7:杭州-冷冻、冷藏 8:杭州-蔬果/百货',
  `mean_temperature` decimal(8, 1) NOT NULL DEFAULT 0.0 COMMENT '平均温度',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `below_standard_number` tinyint(4) NOT NULL DEFAULT 0 COMMENT '未达标次数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_so_id`(`so_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 807 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '车辆平均温度表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for car_history_data
-- ----------------------------
DROP TABLE IF EXISTS `car_history_data`;
CREATE TABLE `car_history_data`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `car_brand` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '车牌号',
  `so_id` int(11) NOT NULL DEFAULT 0 COMMENT '服务id',
  `car_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '运输类型 1:冷鲜 2:百货 3:冷冻 4:冷库 5:蔬果 6:加班车辆 7:杭州-冷冻、冷藏 8:杭州-蔬果/百货',
  `temperature` decimal(8, 1) NOT NULL DEFAULT 0.0 COMMENT '温度',
  `gpstime` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'gps记录时间,运输车辆当前时间',
  `station` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '车辆所在的前置仓',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `so_id`(`so_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7575565 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '车辆历史轨迹表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for finance_bill_remark
-- ----------------------------
DROP TABLE IF EXISTS `finance_bill_remark`;
CREATE TABLE `finance_bill_remark`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `source_id` bigint(11) NOT NULL DEFAULT 0 COMMENT '单据id：采购单id，退货单id ',
  `type` int(5) NOT NULL DEFAULT 0 COMMENT '单据类型 1-采购单，2-退货单',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '财务备注内容',
  `operator` bigint(11) NOT NULL COMMENT '操作人',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `source_id_index`(`source_id`, `type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '财务单据备注表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for gov_user
-- ----------------------------
DROP TABLE IF EXISTS `gov_user`;
CREATE TABLE `gov_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `telephone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `last_ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `last_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '政府人员表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for pest_inspection
-- ----------------------------
DROP TABLE IF EXISTS `pest_inspection`;
CREATE TABLE `pest_inspection`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `store_id` int(11) NOT NULL COMMENT '前置门店ID',
  `start_date` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '周起始日期',
  `end_date` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '周截止日期',
  `is_mosquito` int(1) NOT NULL DEFAULT 0 COMMENT '蚊虫情况：1 有，0 无',
  `treatment` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '处理措施',
  `operator` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作人',
  `create_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '鼠虫害周检查表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for pest_inspection_mouse
-- ----------------------------
DROP TABLE IF EXISTS `pest_inspection_mouse`;
CREATE TABLE `pest_inspection_mouse`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `inspection_id` int(10) NOT NULL COMMENT '鼠虫害周检查主表ID',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '粘鼠板名称',
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '1、正常 2、捕获 0、无效',
  `create_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_pest_inspection_id`(`inspection_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 59 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '鼠虫害周检查粘鼠板表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for pest_inspection_mouse_dict
-- ----------------------------
DROP TABLE IF EXISTS `pest_inspection_mouse_dict`;
CREATE TABLE `pest_inspection_mouse_dict`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '粘鼠板名称',
  `is_delete` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0否1是',
  `create_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `weight` int(11) NOT NULL DEFAULT 0 COMMENT '重量',
  `product_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `unit_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '单位',
  `bar_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '条码',
  `remark` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00',
  `type_id` smallint(6) NOT NULL DEFAULT 0 COMMENT '所属类目',
  `status` tinyint(4) NOT NULL DEFAULT 0,
  `image` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品图片',
  `arithmetic_type` tinyint(4) NOT NULL DEFAULT 1 COMMENT '商品预估算法',
  `is_material` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否原料',
  `unit_size` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '箱规',
  `sku_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品id',
  `update_user` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '更新人',
  `is_scan` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否强制扫码',
  `is_sale` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否售卖',
  `is_inventory_scan` tinyint(4) NOT NULL COMMENT '扫描盘货',
  `category` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '内部分类',
  `category_path` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '分类路径',
  `sizes` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '销售规格',
  `weight_unit` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'g' COMMENT '净含量单位',
  `propertys` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类目属性',
  `storage_value_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '保存条件值',
  `purchase_on` tinyint(4) NOT NULL DEFAULT 1 COMMENT '采购开关 1需采购 0否 ',
  `is_batch` tinyint(4) NOT NULL DEFAULT 0 COMMENT '批次开关',
  `is_batch_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '开启批次时间',
  `transfer_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '调拨方式 0保留库存 1全部调拨',
  `purchase_strategy` tinyint(4) NOT NULL DEFAULT 1 COMMENT '采购策略',
  `box_size` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '箱规',
  `labor_cost` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '人工成本',
  `in_tax` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '进项税',
  `out_tax` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '销项税',
  `only_general_store` tinyint(4) NOT NULL DEFAULT 0 COMMENT '仅限总仓生产',
  `is_auto` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否自动',
  `transfer_advance_day` smallint(6) NOT NULL DEFAULT 0 COMMENT '调拨提前期',
  `purchase_to_address` tinyint(4) NOT NULL DEFAULT 0 COMMENT '采购到址',
  `store_inventory` tinyint(4) NOT NULL DEFAULT 1 COMMENT '门店存货情况',
  `store_list` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `auto_batch` tinyint(4) NOT NULL DEFAULT 0 COMMENT '自动填写批次',
  `weight_limit` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'å‡€å«é‡ä¸Šä¸‹é™',
  `producer` tinyint(4) NOT NULL DEFAULT 0 COMMENT 'ç”Ÿäº§æ–¹ 0ä¾›è´§å•†ã€1é—¨åº—ã€2æ€»ä»“',
  `basket_num` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标准装筐数量',
  `quality_period` int(11) NOT NULL DEFAULT 0 COMMENT '保质期',
  `sale_period` int(11) NOT NULL DEFAULT 0 COMMENT '可售期',
  `batch_type` tinyint(4) NOT NULL DEFAULT 1 COMMENT '批次类型 1按日 2按周 3按月',
  `distribute_cost` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '分拣成本',
  `responsible_group_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '负责组',
  `operation_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '运营状态',
  `is_bom_material` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否制造原料',
  PRIMARY KEY (`product_id`) USING BTREE,
  UNIQUE INDEX `product_name`(`product_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9413 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for product_monitor
-- ----------------------------
DROP TABLE IF EXISTS `product_monitor`;
CREATE TABLE `product_monitor`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `type` smallint(1) NOT NULL DEFAULT 0 COMMENT '1、临期商品，2、过期商品',
  `sku_sum` int(11) NOT NULL DEFAULT 0 COMMENT '统计了多少样商品？',
  `total` int(11) NOT NULL DEFAULT 0 COMMENT '临期或过期商品总数',
  `exe_start_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '执行开始时间',
  `exe_end_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '执行结束时间',
  `count_time` int(10) NOT NULL COMMENT '统计时间，格式yyyyMMddhh',
  `create_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `VERSION` int(11) NOT NULL COMMENT '数据版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 180 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for product_scm_db
-- ----------------------------
DROP TABLE IF EXISTS `product_scm_db`;
CREATE TABLE `product_scm_db`  (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `weight` int(11) NOT NULL DEFAULT 0 COMMENT '重量',
  `product_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `unit_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '单位',
  `bar_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '条码',
  `remark` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00',
  `type_id` smallint(6) NOT NULL DEFAULT 0 COMMENT '所属类目',
  `status` tinyint(4) NOT NULL DEFAULT 0,
  `image` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品图片',
  `arithmetic_type` tinyint(4) NOT NULL DEFAULT 1 COMMENT '商品预估算法',
  `is_material` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否原料',
  `unit_size` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '箱规',
  `sku_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品id',
  `update_user` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '更新人',
  `is_scan` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否强制扫码',
  `is_sale` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否售卖',
  `is_inventory_scan` tinyint(4) NOT NULL COMMENT '扫描盘货',
  `category` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '内部分类',
  `category_path` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '分类路径',
  `sizes` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '销售规格',
  `weight_unit` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'g' COMMENT '净含量单位',
  `propertys` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类目属性',
  `storage_value_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '保存条件值',
  `purchase_on` tinyint(4) NOT NULL DEFAULT 1 COMMENT '采购开关 1需采购 0否 ',
  `is_batch` tinyint(4) NOT NULL DEFAULT 0 COMMENT '批次开关',
  `is_batch_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '开启批次时间',
  `transfer_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '调拨方式 0保留库存 1全部调拨',
  `purchase_strategy` tinyint(4) NOT NULL DEFAULT 1 COMMENT '采购策略',
  `box_size` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '箱规',
  `labor_cost` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '人工成本',
  `in_tax` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '进项税',
  `out_tax` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '销项税',
  `only_general_store` tinyint(4) NOT NULL DEFAULT 0 COMMENT '仅限总仓生产',
  `is_auto` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否自动',
  `transfer_advance_day` smallint(6) NOT NULL DEFAULT 0 COMMENT '调拨提前期',
  `purchase_to_address` tinyint(4) NOT NULL DEFAULT 0 COMMENT '采购到址',
  `store_inventory` tinyint(4) NOT NULL COMMENT '门店存货情况',
  `store_list` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  `auto_batch` tinyint(4) NOT NULL DEFAULT 0 COMMENT '自动填写批次',
  `weight_limit` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'å‡€å«é‡ä¸Šä¸‹é™',
  `producer` tinyint(4) NOT NULL DEFAULT 0 COMMENT 'ç”Ÿäº§æ–¹ 0ä¾›è´§å•†ã€1é—¨åº—ã€2æ€»ä»“',
  `basket_num` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标准装筐数量',
  `quality_period` int(11) NOT NULL DEFAULT 0 COMMENT '保质期',
  `sale_period` int(11) NOT NULL DEFAULT 0 COMMENT '可售期',
  `batch_type` tinyint(4) NOT NULL DEFAULT 1 COMMENT '批次类型 1按日 2按周 3按月',
  `distribute_cost` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '分拣成本',
  PRIMARY KEY (`product_id`) USING BTREE,
  UNIQUE INDEX `product_name`(`product_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8599 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for purchase
-- ----------------------------
DROP TABLE IF EXISTS `purchase`;
CREATE TABLE `purchase`  (
  `purchase_id` int(11) NOT NULL AUTO_INCREMENT,
  `vendor_id` int(11) NOT NULL DEFAULT 0,
  `store_id` int(11) NOT NULL DEFAULT 0,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `delivery_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00',
  `products` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品json字符串,[{product_id,product_name,amount:采购量,real_amount:实收数量,price}]',
  `purchase_title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态, 0:未完成,1:已完成',
  `draft_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '完成草稿时间',
  `import_log` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '导入日志',
  `amount_total` decimal(10, 4) NOT NULL DEFAULT 0.0000,
  `real_amount_total` decimal(10, 4) NOT NULL DEFAULT 0.0000,
  `creater` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `update_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `finance_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '财务处理状态',
  `finance_remark` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '备注单号',
  `finance_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '处理时间',
  `finance_operator` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '处理人',
  `plan_id` int(11) NOT NULL DEFAULT 0 COMMENT '计划ID',
  `update_user` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '更新人',
  `is_timeout` tinyint(4) NOT NULL DEFAULT 0 COMMENT '异常 超时未处理 ',
  `type_ids` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `settlement_amount_total` decimal(10, 4) NOT NULL DEFAULT 0.0000 COMMENT '结算总金额',
  `communicate_time` tinyint(4) NOT NULL DEFAULT 0 COMMENT '沟通时效 30 60 90',
  `temperature` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '温度',
  `car_environment` tinyint(4) NOT NULL DEFAULT 0 COMMENT '车内环境：正常，一般，异味脏污',
  `quality_control_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '品控状态',
  `images` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图片',
  `supplier_sign` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '供货商签名',
  `additional` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '附加成本',
  `additional_total` decimal(10, 4) NOT NULL DEFAULT 0.0000 COMMENT '杂项总金额',
  `is_arrival` tinyint(4) NOT NULL DEFAULT 0 COMMENT '到货',
  `quality_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '检测时间',
  `receive_operator` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货处理人',
  `plan_delivery_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '计划到货时间',
  `is_replenishment` tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否补货采购单:0否 1是',
  `is_weigh_completed` tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否称重清点完成:0否 1是',
  `is_prepay` tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否支持预付，枚举值  0：否； 1：是，默认0',
  `adjustment_amount` decimal(10, 4) NOT NULL DEFAULT 0.0000 COMMENT '采购单完成之后的调整金额总计',
  `adjustment_amount_detail` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '调整费用json  [{\"adjustment_type_id\":\"1\",\"adjustment_type_name\":\"运输费\",\"adjustment_amount\":\"2.3654\",\"remark\": \"备注\",\"caozuo_name\":\"操作人\",\"create_time\":\"2019-03-01 11:11:11\"}]',
  `final_settlement_amount` decimal(10, 4) NOT NULL DEFAULT 0.0000 COMMENT '结算总金额, 结算总金额= 商品结算总金额+杂项金额+调整金额',
  `finish_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '采购单完成时间',
  PRIMARY KEY (`purchase_id`) USING BTREE,
  INDEX `plan_id`(`plan_id`) USING BTREE,
  INDEX `store_id`(`create_time`, `store_id`) USING BTREE,
  INDEX `status_delivery_time`(`status`, `delivery_time`, `store_id`) USING BTREE,
  INDEX `vendor_finance_status`(`vendor_id`, `finance_status`) USING BTREE,
  INDEX `update_time_index`(`update_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100000001 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '采购记录' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for purchase_product
-- ----------------------------
DROP TABLE IF EXISTS `purchase_product`;
CREATE TABLE `purchase_product`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `purchase_id` int(11) NOT NULL DEFAULT 0 COMMENT '采购单id',
  `product_id` int(11) NOT NULL DEFAULT 0 COMMENT '商品id',
  `product_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品名称',
  `unit_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '单位',
  `amount` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '采购数量',
  `real_amount` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '实收数量',
  `deny_amount` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '拒收数量',
  `price` decimal(10, 4) NOT NULL DEFAULT 0.0000 COMMENT '价格',
  `ref_price` decimal(10, 4) NOT NULL DEFAULT 0.0000 COMMENT '参照价格',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `batch_list` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `settlement_amount` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '结算数量',
  `checkin_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '入库时间',
  `checkin_operator` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '入库操作人',
  `is_checkin` tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否入库: 0否 1是',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `purchase_id`(`purchase_id`) USING BTREE,
  INDEX `product_id`(`product_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 800001 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '采购单商品' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for quality_control
-- ----------------------------
DROP TABLE IF EXISTS `quality_control`;
CREATE TABLE `quality_control`  (
  `quality_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `purchase_id` int(11) NOT NULL DEFAULT 0 COMMENT '采购单ID',
  `product_id` int(11) NOT NULL DEFAULT 0 COMMENT '商品ID',
  `sampling_quantity` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '抽检量',
  `sampling_rate` decimal(5, 4) NOT NULL DEFAULT 0.0000 COMMENT '抽检率',
  `size_inconformity` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '规格不符',
  `one_unqualified` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '一级不合格',
  `two_unqualified` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '二级不合格',
  `three_unqualified` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '三级不合格',
  `four_unqualified` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '四级不合格',
  `unqualified_rate` decimal(5, 4) NOT NULL DEFAULT 0.0000 COMMENT '不合格率',
  `sugar_value` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '糖度值%Brix',
  `mouth_feel` tinyint(4) NOT NULL DEFAULT 0 COMMENT '口感测试 1通过、2可接受、0不通过',
  `quality_judgment` tinyint(4) NOT NULL DEFAULT 0 COMMENT '质量判断 1合格、0不合格、2部分合格',
  `reception_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '接收类型 1全部入库、2拣选入库、0拒收',
  `delivery_times` smallint(6) NOT NULL DEFAULT 0 COMMENT '送达次数',
  `inspector` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '质检员',
  `remarks` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '备注',
  `cable_ticket` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '索证索票',
  `ticket_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '票证编号',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`quality_id`) USING BTREE,
  INDEX `purchase_product_index`(`purchase_id`, `product_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 400001 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '品控单' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for recycle_area_inspection
-- ----------------------------
DROP TABLE IF EXISTS `recycle_area_inspection`;
CREATE TABLE `recycle_area_inspection`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `store_id` int(11) NOT NULL COMMENT '前置门店ID',
  `date` int(8) NOT NULL COMMENT '巡检日期，格式：yyyyMMdd',
  `inspector` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '巡检人',
  `recycle_area_img` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '回收区图片地址',
  `scrap_area_img` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '报废区图片地址',
  `handle_area_img` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '处理区图片地址',
  `status` int(5) NOT NULL DEFAULT 1 COMMENT '完成状态 1-未完成，2-完成',
  `finish_time` datetime(0) NULL DEFAULT NULL COMMENT '完成时间',
  `create_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_store_date`(`store_id`, `date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '回收区巡检记录表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for refri_house_avg_temp
-- ----------------------------
DROP TABLE IF EXISTS `refri_house_avg_temp`;
CREATE TABLE `refri_house_avg_temp`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `out_gate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '座头鲸网关ID',
  `terminal_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '终端ID',
  `calculate_day` int(11) NOT NULL COMMENT '计算日期yyyyMMdd',
  `amount` int(11) NOT NULL COMMENT '本日累计次数',
  `avg_temperature` decimal(10, 2) NULL DEFAULT NULL COMMENT '平均温度',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UNIQ_TERMINAL_COLLECT`(`terminal_id`, `calculate_day`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '冷库监控日平均温度表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for refri_house_dev_monitor
-- ----------------------------
DROP TABLE IF EXISTS `refri_house_dev_monitor`;
CREATE TABLE `refri_house_dev_monitor`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `out_gate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '座头鲸网关ID',
  `terminal_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '终端ID',
  `collect_time` bigint(20) NOT NULL COMMENT '温度采集时间yyyyMMddHHmmss',
  `temperature` decimal(10, 2) NOT NULL COMMENT '温度',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UNIQ_TERMINAL_COLLECT`(`terminal_id`, `collect_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '冷库监控终端数据表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for refri_house_except
-- ----------------------------
DROP TABLE IF EXISTS `refri_house_except`;
CREATE TABLE `refri_house_except`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `out_gate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '座头鲸网关ID',
  `terminal_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '终端ID',
  `start_time` bigint(20) NOT NULL COMMENT '异常开始时间yyyyMMddHHmmss',
  `end_time` bigint(20) NOT NULL COMMENT '异常结束时间yyyyMMddHHmmss',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UNIQ_TERMINAL_COLLECT`(`terminal_id`, `start_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '冷库监控终异常表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for refri_house_summary_log
-- ----------------------------
DROP TABLE IF EXISTS `refri_house_summary_log`;
CREATE TABLE `refri_house_summary_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `out_gate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '座头鲸网关ID',
  `terminal_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '终端ID',
  `except_start_time` bigint(20) NULL DEFAULT NULL COMMENT '异常开始时间: yyyyMMddHHmmss',
  `current_collect_time` bigint(20) NOT NULL COMMENT '本次计算最新采集时间：yyyyMMddHHmmss',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UNIQ_TERMINAL_COLLECT`(`terminal_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '冷库监控统计日志表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for refri_house_sync_log
-- ----------------------------
DROP TABLE IF EXISTS `refri_house_sync_log`;
CREATE TABLE `refri_house_sync_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `out_gate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '座头鲸网关ID',
  `last_collect_time` bigint(20) NOT NULL COMMENT '最近温度采集时间yyyyMMddHHmmss',
  `last_sync_time` bigint(20) NOT NULL COMMENT '最近同步时间yyyyMMddHHmmss',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '冷库监控终端数据同步日志表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for refrigeration_house_device
-- ----------------------------
DROP TABLE IF EXISTS `refrigeration_house_device`;
CREATE TABLE `refrigeration_house_device`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `out_gate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '座头鲸网关ID',
  `terminal_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '终端ID',
  `terminal_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '终端名称，本表只保留LD\\LC',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '1、生效；0、失效',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UNIQ_TERMINAL`(`terminal_id`) USING BTREE,
  INDEX `IDX_HOUSE_ID`(`out_gate_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '冷库监控终端表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for refrigeration_house_gate
-- ----------------------------
DROP TABLE IF EXISTS `refrigeration_house_gate`;
CREATE TABLE `refrigeration_house_gate`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `out_gate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '座头鲸网关ID',
  `out_gate_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '网关名称',
  `station_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '服务站',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '1、生效；2、失效',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UNIQ_HOUSE_ID`(`out_gate_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '冷库-网关表（记录座头鲸监控门店的冷库信息0301类）' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for store
-- ----------------------------
DROP TABLE IF EXISTS `store`;
CREATE TABLE `store`  (
  `store_id` int(11) NOT NULL AUTO_INCREMENT,
  `store_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `city_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '城市id',
  `address` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `remark` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` tinyint(4) NOT NULL DEFAULT 0,
  `is_purchase` tinyint(4) NOT NULL DEFAULT 1 COMMENT '是否参加采购计划, 0:否,1:是',
  `is_general` tinyint(4) NOT NULL DEFAULT 0,
  `update_user` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '更新人',
  `station_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '服务站ID',
  `sort` float NOT NULL DEFAULT 0 COMMENT '排序值 ',
  `is_test` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否测试仓',
  `is_prestore` tinyint(4) NOT NULL DEFAULT 1 COMMENT '是否前置仓',
  `is_sale` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否售卖仓 0否 1是',
  `is_scan` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否强扫',
  `type` tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '仓库类型:1总仓存储2临时存储3销售4测试5报废存储',
  `is_chive_notice` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否开启香葱提醒:0否1是',
  PRIMARY KEY (`store_id`) USING BTREE,
  UNIQUE INDEX `store_name`(`store_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 602 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '仓库' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for store_inspection
-- ----------------------------
DROP TABLE IF EXISTS `store_inspection`;
CREATE TABLE `store_inspection`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `store_id` int(11) NOT NULL COMMENT '前置门店ID',
  `date` int(8) NOT NULL COMMENT '巡检日期，格式：yyyyMMdd',
  `score` int(3) NULL DEFAULT 0 COMMENT '巡检总分',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '0、待发布，1、已发布',
  `file_path` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '巡检文件路径',
  `inspector` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '巡检人',
  `create_itme` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_store_date`(`store_id`, `date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '前置仓巡检记录表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for store_inspection_rectify
-- ----------------------------
DROP TABLE IF EXISTS `store_inspection_rectify`;
CREATE TABLE `store_inspection_rectify`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `inspection_id` int(11) NOT NULL COMMENT '门店巡检记录ID',
  `rectify_name` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '整改项名称',
  `rectify_person` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '整改实施人',
  `rectify_date` datetime(0) NULL DEFAULT NULL COMMENT '整改期限',
  `rectify_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '整改内容',
  `create_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inspecion_id`(`inspection_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '前置仓巡检整改表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for store_product
-- ----------------------------
DROP TABLE IF EXISTS `store_product`;
CREATE TABLE `store_product`  (
  `store_id` int(11) NOT NULL DEFAULT 0,
  `product_id` int(11) NOT NULL DEFAULT 0,
  `amount` decimal(10, 2) NOT NULL DEFAULT 0.00,
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`store_id`, `product_id`) USING BTREE,
  INDEX `product_id`(`product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '仓库库存' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for store_product_batch
-- ----------------------------
DROP TABLE IF EXISTS `store_product_batch`;
CREATE TABLE `store_product_batch`  (
  `store_id` int(11) NOT NULL DEFAULT 0,
  `product_id` int(11) NOT NULL DEFAULT 0,
  `batch_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `amount` decimal(10, 2) NOT NULL DEFAULT 0.00,
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`store_id`, `product_id`, `batch_id`) USING BTREE,
  INDEX `update_time_index`(`update_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '仓库批次库存' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for store_product_batch_temp
-- ----------------------------
DROP TABLE IF EXISTS `store_product_batch_temp`;
CREATE TABLE `store_product_batch_temp`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `store_id` int(11) NOT NULL COMMENT '门店ID',
  `product_id` int(11) NOT NULL COMMENT '商品ID',
  `batch_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '批次ID',
  `batch_update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '商品批次更新时间',
  `amount` decimal(10, 2) NOT NULL COMMENT '批次库存',
  `quality_period` int(11) NOT NULL COMMENT '保质期天数',
  `sale_period` int(11) NOT NULL COMMENT '可售期天数',
  `quality_date` int(8) NOT NULL COMMENT '保质期=批次+保质期天数',
  `sale_date` int(8) NOT NULL COMMENT '可售期期=批次+可售期天数',
  `create_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `store_product_batch_index`(`store_id`, `product_id`, `batch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9289885 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for store_product_monitor
-- ----------------------------
DROP TABLE IF EXISTS `store_product_monitor`;
CREATE TABLE `store_product_monitor`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `type` smallint(1) NOT NULL COMMENT '类型：1、临期；2、过期',
  `store_id` int(11) NOT NULL COMMENT '店铺ID',
  `product_id` int(11) NOT NULL COMMENT '商品ID',
  `total` int(11) NOT NULL DEFAULT 0 COMMENT '商品合计数',
  `create_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `version` int(11) NOT NULL COMMENT '数据版本号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `store_index`(`store_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6583507 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tool_disinfection
-- ----------------------------
DROP TABLE IF EXISTS `tool_disinfection`;
CREATE TABLE `tool_disinfection`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `store_id` int(11) NOT NULL COMMENT '前置门店ID',
  `date` int(8) NOT NULL COMMENT '消毒日期，格式：yyyyMMdd',
  `create_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `store_id_date_index`(`store_id`, `date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '刀具消毒记录表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tool_disinfection_dict
-- ----------------------------
DROP TABLE IF EXISTS `tool_disinfection_dict`;
CREATE TABLE `tool_disinfection_dict`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_delete` int(1) NOT NULL DEFAULT 0,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tool_disinfection_item
-- ----------------------------
DROP TABLE IF EXISTS `tool_disinfection_item`;
CREATE TABLE `tool_disinfection_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `tool_disinfection_id` int(11) NOT NULL COMMENT '刀具消毒记录ID',
  `operator` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作人',
  `tool_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '刀具名称',
  `tool_num` int(3) NOT NULL DEFAULT 1 COMMENT '刀具数量',
  `disinfection_way` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消毒方式',
  `create_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `tool_disinfection_id_index`(`tool_disinfection_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '刀具消毒记录明细表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for yestoday_quality_control_batch
-- ----------------------------
DROP TABLE IF EXISTS `yestoday_quality_control_batch`;
CREATE TABLE `yestoday_quality_control_batch`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `batch_id` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '批次id',
  `product_id` bigint(11) NOT NULL COMMENT '商品id',
  `product_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `purchase_id` bigint(11) NOT NULL COMMENT '采购单号',
  `quality_id` bigint(11) NOT NULL DEFAULT 0 COMMENT '质检单号',
  `quality_judgment` tinyint(4) NOT NULL DEFAULT -1 COMMENT '质量判断 1合格、0不合格 、-1 未质检',
  `reception_type` tinyint(4) NOT NULL DEFAULT -1 COMMENT '接收类型 1全部入库、2特采入库、0拒收  、-1-未入库',
  `quality_time` timestamp(0) NULL DEFAULT NULL COMMENT '质检时间',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3209 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '昨日入库质检数据批次表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for yestoday_quality_summary
-- ----------------------------
DROP TABLE IF EXISTS `yestoday_quality_summary`;
CREATE TABLE `yestoday_quality_summary`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `batch_id` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `summary_date` date NOT NULL COMMENT '统计的数据所属日期',
  `total_count` int(11) NOT NULL DEFAULT 0 COMMENT '质检数',
  `un_quality_count` int(11) NOT NULL COMMENT '未质检数',
  `un_qualified_count` int(11) NOT NULL COMMENT '质检不合格数量',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '昨日入库质检数据统计表' ROW_FORMAT = Compact;

-- ----------------------------
-- Function structure for mycat_seq_currval
-- ----------------------------
DROP FUNCTION IF EXISTS `mycat_seq_currval`;
delimiter ;;
CREATE FUNCTION `mycat_seq_currval`(seq_name VARCHAR(50))
 RETURNS varchar(64) CHARSET utf8
  DETERMINISTIC
BEGIN DECLARE retval VARCHAR(64);
        SET retval="-999999999,null";  
        SELECT CONCAT(CAST(current_value AS CHAR),",",CAST(increment AS CHAR) ) INTO retval 
          FROM MYCAT_SEQUENCE WHERE NAME = seq_name;  
        RETURN retval ; 
END
;;
delimiter ;

-- ----------------------------
-- Function structure for mycat_seq_nextval
-- ----------------------------
DROP FUNCTION IF EXISTS `mycat_seq_nextval`;
delimiter ;;
CREATE FUNCTION `mycat_seq_nextval`(seq_name VARCHAR(50))
 RETURNS varchar(64) CHARSET utf8
  DETERMINISTIC
BEGIN UPDATE MYCAT_SEQUENCE  
                 SET current_value = current_value + increment 
                  WHERE NAME = seq_name;  
         RETURN mycat_seq_currval(seq_name);  
END
;;
delimiter ;

-- ----------------------------
-- Function structure for mycat_seq_setval
-- ----------------------------
DROP FUNCTION IF EXISTS `mycat_seq_setval`;
delimiter ;;
CREATE FUNCTION `mycat_seq_setval`(seq_name VARCHAR(50), VALUE INTEGER)
 RETURNS varchar(64) CHARSET utf8
  DETERMINISTIC
BEGIN UPDATE MYCAT_SEQUENCE  
                   SET current_value = VALUE  
                   WHERE NAME = seq_name;  
         RETURN mycat_seq_currval(seq_name);  
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for proc_set_batch_stock
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_set_batch_stock`;
delimiter ;;
CREATE PROCEDURE `proc_set_batch_stock`(param_store_id int ,param_product_id int,param_update_amount decimal(10,2),param_source_type smallint,param_source_id int,param_batch_id varchar(10) CHARACTER SET utf8 COLLATE 'utf8_general_ci')
begin
    declare old_amount decimal(10,2) default 0.00 ;
    insert into store_product_batch set amount=param_update_amount,product_id=param_product_id,store_id=param_store_id,batch_id=param_batch_id on duplicate key update amount=param_update_amount;
    select amount into old_amount from store_product_batch where store_id=param_store_id and product_id=param_product_id and batch_id=param_batch_id ;
    insert into store_product_batch_log set store_id=param_store_id,product_id=param_product_id,update_amount=param_update_amount - old_amount,create_time=now(),source_type=param_source_type,source_id=param_source_id,new_amount=param_update_amount,batch_id=param_batch_id;
end
;;
delimiter ;

-- ----------------------------
-- Procedure structure for proc_set_stock
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_set_stock`;
delimiter ;;
CREATE PROCEDURE `proc_set_stock`(param_store_id int ,param_product_id int,param_update_amount decimal(10,2),param_source_type smallint,param_source_id int)
begin
    declare old_amount decimal(10,2) default 0.00 ;
    select amount into old_amount from store_product where store_id=param_store_id and product_id=param_product_id ;
    insert into store_product set amount=param_update_amount,product_id=param_product_id,store_id=param_store_id on duplicate key update amount=param_update_amount;
    insert into store_product_log set store_id=param_store_id,product_id=param_product_id,update_amount=param_update_amount - old_amount,create_time=now(),source_type=param_source_type,source_id=param_source_id,new_amount=param_update_amount;
end
;;
delimiter ;

-- ----------------------------
-- Procedure structure for proc_update_batch_stock
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_update_batch_stock`;
delimiter ;;
CREATE PROCEDURE `proc_update_batch_stock`(IN `param_store_id` INT, IN `param_product_id` INT, IN `param_update_amount` DECIMAL(10,2), IN `param_source_type` SMALLINT, IN `param_source_id` INT, IN `param_batch_id` VARCHAR(10) CHARACTER SET utf8 COLLATE 'utf8_general_ci')
  NO SQL 
begin
    declare new_amount decimal(10,2) default 0.00 ;
    insert into store_product_batch set amount=param_update_amount,product_id=param_product_id,store_id=param_store_id,batch_id=param_batch_id on duplicate key update amount=amount + param_update_amount;
    select amount into new_amount from store_product_batch where store_id=param_store_id and product_id=param_product_id and batch_id=param_batch_id ;
    insert into store_product_batch_log set store_id=param_store_id,product_id=param_product_id,update_amount=param_update_amount,create_time=now(),source_type=param_source_type,source_id=param_source_id,batch_id=param_batch_id,new_amount=new_amount ;
end
;;
delimiter ;

-- ----------------------------
-- Procedure structure for proc_update_batch_stock_other
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_update_batch_stock_other`;
delimiter ;;
CREATE PROCEDURE `proc_update_batch_stock_other`(param_other_type_id int ,param_store_id int ,param_product_id int,param_update_amount decimal(10,2),param_source_type smallint,param_source_id int,param_batch_id varchar(10))
begin
    declare new_amount decimal(10,2) default 0.00 ;
    insert into store_product_batch_other set amount=param_update_amount,product_id=param_product_id,store_id=param_store_id,other_type_id=param_other_type_id,batch_id=param_batch_id on duplicate key update amount=amount + param_update_amount;
    select amount into new_amount from store_product_batch_other where other_type_id=param_other_type_id and store_id=param_store_id and product_id=param_product_id and batch_id=param_batch_id ;
    insert into store_product_batch_other_log set other_type_id=param_other_type_id,store_id=param_store_id,product_id=param_product_id,update_amount=param_update_amount,create_time=now(),source_type=param_source_type,source_id=param_source_id,batch_id=param_batch_id,new_amount=new_amount ;
end
;;
delimiter ;

-- ----------------------------
-- Procedure structure for proc_update_stock
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_update_stock`;
delimiter ;;
CREATE PROCEDURE `proc_update_stock`(param_store_id int ,param_product_id int,param_update_amount decimal(10,2),param_source_type smallint,param_source_id int)
begin
    declare new_amount decimal(10,2) default 0.00 ;
    insert into store_product set amount=param_update_amount,product_id=param_product_id,store_id=param_store_id on duplicate key update amount=amount + param_update_amount;
    select amount into new_amount from store_product where store_id=param_store_id and product_id=param_product_id ;
    insert into store_product_log set store_id=param_store_id,product_id=param_product_id,update_amount=param_update_amount,create_time=now(),source_type=param_source_type,source_id=param_source_id,new_amount=new_amount ;
end
;;
delimiter ;

-- ----------------------------
-- Procedure structure for proc_update_stock_other
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_update_stock_other`;
delimiter ;;
CREATE PROCEDURE `proc_update_stock_other`(param_other_type_id int ,param_store_id int ,param_product_id int,param_update_amount decimal(10,2),param_source_type smallint,param_source_id int)
begin
    declare new_amount decimal(10,2) default 0.00 ;
    insert into store_product_other set amount=param_update_amount,product_id=param_product_id,store_id=param_store_id,other_type_id=param_other_type_id on duplicate key update amount=amount + param_update_amount;
    select amount into new_amount from store_product_other where other_type_id=param_other_type_id and store_id=param_store_id and product_id=param_product_id ;
    insert into store_product_other_log set other_type_id=param_other_type_id,store_id=param_store_id,product_id=param_product_id,update_amount=param_update_amount,create_time=now(),source_type=param_source_type,source_id=param_source_id,new_amount=new_amount ;
end
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
