

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `prop_info`
-- ----------------------------
DROP TABLE IF EXISTS `prop_info`;
CREATE TABLE `prop_info` (
  `id` varchar(255) NOT NULL,
  `title` varchar(200) DEFAULT NULL COMMENT '标题',
  `salePrice` double DEFAULT NULL COMMENT '售价(万)',
  `downPay` double DEFAULT NULL COMMENT '首付(万)',
  `unitPrice` double DEFAULT NULL COMMENT '单价(元/平米)',
  `community` varchar(50) DEFAULT NULL COMMENT '小区',
  `position` varchar(50) DEFAULT NULL COMMENT '位置',
  `roomNum` int(11) DEFAULT NULL COMMENT '卧室数量',
  `hallNum` int(11) DEFAULT NULL COMMENT '客厅数量',
  `toiletNum` int(11) DEFAULT NULL COMMENT '卫生间数量',
  `acreage` double DEFAULT NULL COMMENT '面积(平米)',
  `orientation` varchar(5) DEFAULT NULL COMMENT '朝向',
  `totalFloor` int(11) DEFAULT NULL COMMENT '楼层数',
  `floor` int(11) DEFAULT NULL COMMENT '所在楼层',
  `decoration` varchar(255) DEFAULT NULL COMMENT '装修',
  `type` varchar(255) DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


