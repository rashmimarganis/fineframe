-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.34-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema lejia_shop
--

CREATE DATABASE IF NOT EXISTS lejia_shop;
USE lejia_shop;

--
-- Definition of table `cms_block`
--

DROP TABLE IF EXISTS `cms_block`;
CREATE TABLE `cms_block` (
  `block_id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`block_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cms_block`
--

/*!40000 ALTER TABLE `cms_block` DISABLE KEYS */;
INSERT INTO `cms_block` (`block_id`,`content`,`name`) VALUES 
 (1,'<p>FFFFFFFFFFFFFFFFFFFFFFFFFFFF</p>','header');
/*!40000 ALTER TABLE `cms_block` ENABLE KEYS */;


--
-- Definition of table `cms_category`
--

DROP TABLE IF EXISTS `cms_category`;
CREATE TABLE `cms_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `sequence` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`category_id`),
  KEY `FK2478CF3488BD0267` (`parent_id`),
  CONSTRAINT `FK2478CF3488BD0267` FOREIGN KEY (`parent_id`) REFERENCES `cms_category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cms_category`
--

/*!40000 ALTER TABLE `cms_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `cms_category` ENABLE KEYS */;


--
-- Definition of table `cms_datamodel`
--

DROP TABLE IF EXISTS `cms_datamodel`;
CREATE TABLE `cms_datamodel` (
  `model_id` int(11) NOT NULL AUTO_INCREMENT,
  `class_name` varchar(255) NOT NULL,
  `model_name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT 'hibernate',
  `unit` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`model_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cms_datamodel`
--

/*!40000 ALTER TABLE `cms_datamodel` DISABLE KEYS */;
INSERT INTO `cms_datamodel` (`model_id`,`class_name`,`model_name`,`type`,`unit`) VALUES 
 (1,'User','用户','hibernate','个');
/*!40000 ALTER TABLE `cms_datamodel` ENABLE KEYS */;


--
-- Definition of table `cms_model_field`
--

DROP TABLE IF EXISTS `cms_model_field`;
CREATE TABLE `cms_model_field` (
  `field_id` int(11) NOT NULL AUTO_INCREMENT,
  `field_name` varchar(32) DEFAULT NULL,
  `required` bit(1) DEFAULT NULL,
  `field_title` varchar(32) DEFAULT NULL,
  `field_type` varchar(255) DEFAULT NULL,
  `model_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`field_id`),
  KEY `FK1BE7766E4908E17` (`model_id`),
  CONSTRAINT `FK1BE7766E4908E17` FOREIGN KEY (`model_id`) REFERENCES `cms_datamodel` (`model_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cms_model_field`
--

/*!40000 ALTER TABLE `cms_model_field` DISABLE KEYS */;
/*!40000 ALTER TABLE `cms_model_field` ENABLE KEYS */;


--
-- Definition of table `cms_module`
--

DROP TABLE IF EXISTS `cms_module`;
CREATE TABLE `cms_module` (
  `module_id` int(11) NOT NULL AUTO_INCREMENT,
  `enabled` bit(1) NOT NULL DEFAULT b'0',
  `installed` datetime DEFAULT NULL,
  `module_name` varchar(255) DEFAULT NULL,
  `package_name` varchar(255) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`module_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cms_module`
--

/*!40000 ALTER TABLE `cms_module` DISABLE KEYS */;
INSERT INTO `cms_module` (`module_id`,`enabled`,`installed`,`module_name`,`package_name`,`updated`,`version`) VALUES 
 (1,0x01,'2009-04-27 00:00:00','商店','cms','2009-04-27 00:00:00','1');
/*!40000 ALTER TABLE `cms_module` ENABLE KEYS */;


--
-- Definition of table `cms_tag_field`
--

DROP TABLE IF EXISTS `cms_tag_field`;
CREATE TABLE `cms_tag_field` (
  `tag_id` int(11) NOT NULL,
  `field_id` int(11) NOT NULL,
  KEY `FK892D5C9FE94D3D67` (`tag_id`),
  KEY `FK892D5C9F874902A` (`field_id`),
  CONSTRAINT `FK892D5C9F874902A` FOREIGN KEY (`field_id`) REFERENCES `cms_model_field` (`field_id`),
  CONSTRAINT `FK892D5C9FE94D3D67` FOREIGN KEY (`tag_id`) REFERENCES `cms_template_tag` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cms_tag_field`
--

/*!40000 ALTER TABLE `cms_tag_field` DISABLE KEYS */;
/*!40000 ALTER TABLE `cms_tag_field` ENABLE KEYS */;


--
-- Definition of table `cms_template`
--

DROP TABLE IF EXISTS `cms_template`;
CREATE TABLE `cms_template` (
  `template_id` int(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) DEFAULT NULL,
  `template_name` varchar(255) DEFAULT NULL,
  `suit_id` int(11) DEFAULT NULL,
  `module_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`template_id`),
  KEY `FKD2B0DEB02DBA8E4D` (`suit_id`),
  KEY `FKD2B0DEB072FAB113` (`module_id`),
  CONSTRAINT `FKD2B0DEB02DBA8E4D` FOREIGN KEY (`suit_id`) REFERENCES `cms_template_suit` (`suit_id`),
  CONSTRAINT `FKD2B0DEB072FAB113` FOREIGN KEY (`module_id`) REFERENCES `cms_module` (`module_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cms_template`
--

/*!40000 ALTER TABLE `cms_template` DISABLE KEYS */;
INSERT INTO `cms_template` (`template_id`,`file_name`,`template_name`,`suit_id`,`module_id`) VALUES 
 (1,'user','用户列表',1,1);
/*!40000 ALTER TABLE `cms_template` ENABLE KEYS */;


--
-- Definition of table `cms_template_suit`
--

DROP TABLE IF EXISTS `cms_template_suit`;
CREATE TABLE `cms_template_suit` (
  `suit_id` int(11) NOT NULL AUTO_INCREMENT,
  `is_default` bit(1) DEFAULT b'0',
  `package_name` varchar(255) DEFAULT NULL,
  `suit_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`suit_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cms_template_suit`
--

/*!40000 ALTER TABLE `cms_template_suit` DISABLE KEYS */;
INSERT INTO `cms_template_suit` (`suit_id`,`is_default`,`package_name`,`suit_name`) VALUES 
 (1,0x01,'ddd','test');
/*!40000 ALTER TABLE `cms_template_suit` ENABLE KEYS */;


--
-- Definition of table `cms_template_tag`
--

DROP TABLE IF EXISTS `cms_template_tag`;
CREATE TABLE `cms_template_tag` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `item_count` int(11) DEFAULT NULL,
  `is_paged` bit(1) DEFAULT b'0',
  `tag_name` varchar(255) DEFAULT NULL,
  `model_id` int(11) DEFAULT NULL,
  `template_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`tag_id`),
  KEY `FK3CEEF58B2508FC13` (`template_id`),
  KEY `FK3CEEF58B4908E17` (`model_id`),
  CONSTRAINT `FK3CEEF58B2508FC13` FOREIGN KEY (`template_id`) REFERENCES `cms_template` (`template_id`),
  CONSTRAINT `FK3CEEF58B4908E17` FOREIGN KEY (`model_id`) REFERENCES `cms_datamodel` (`model_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cms_template_tag`
--

/*!40000 ALTER TABLE `cms_template_tag` DISABLE KEYS */;
INSERT INTO `cms_template_tag` (`tag_id`,`item_count`,`is_paged`,`tag_name`,`model_id`,`template_id`) VALUES 
 (1,10,0x00,'user',1,1);
/*!40000 ALTER TABLE `cms_template_tag` ENABLE KEYS */;


--
-- Definition of table `p_agent_types`
--

DROP TABLE IF EXISTS `p_agent_types`;
CREATE TABLE `p_agent_types` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `p_agent_types`
--

/*!40000 ALTER TABLE `p_agent_types` DISABLE KEYS */;
/*!40000 ALTER TABLE `p_agent_types` ENABLE KEYS */;


--
-- Definition of table `p_functions`
--

DROP TABLE IF EXISTS `p_functions`;
CREATE TABLE `p_functions` (
  `function_name` varchar(32) DEFAULT NULL,
  `function_title` varchar(40) DEFAULT NULL,
  `is_log` tinyint(1) DEFAULT '1',
  `is_show` tinyint(1) DEFAULT '1',
  `open_type` varchar(255) DEFAULT '_self',
  `sequence` int(11) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `function_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL,
  `type` int(10) unsigned DEFAULT '0' COMMENT '0-功能模块,1-菜单,2-操作',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '默认可用',
  PRIMARY KEY (`function_id`) USING BTREE,
  KEY `FKC2EE368CDDA60A19` (`parent_id`),
  CONSTRAINT `FKC2EE368CDDA60A19` FOREIGN KEY (`parent_id`) REFERENCES `p_functions` (`function_id`)
) ENGINE=InnoDB AUTO_INCREMENT=317 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `p_functions`
--

/*!40000 ALTER TABLE `p_functions` DISABLE KEYS */;
INSERT INTO `p_functions` (`function_name`,`function_title`,`is_log`,`is_show`,`open_type`,`sequence`,`url`,`function_id`,`parent_id`,`type`,`enabled`) VALUES 
 ('shop','店铺管理',0,1,'_self',1,'/shop.jhtm',1,NULL,0,1),
 ('system','系统管理',1,1,'_self',10,'/system.jhtm',2,NULL,0,1),
 ('right','权限管理',1,1,'_self',3,'/',4,2,0,1),
 ('menu','功能配置',1,1,'_self',1,'platform/function.jsp',5,4,0,1),
 ('human','人事管理',1,0,'_self',4,'/',6,2,0,1),
 ('org1','组织机构',1,1,'_self',0,'platform/org.jsp',7,8,0,1),
 ('user','用户管理',1,1,'_self',2,'platform/user.jsp',8,4,0,1),
 ('role','岗位权限',1,1,'_self',3,'platform/role.jsp',9,4,0,1),
 ('region','区域管理',1,1,'_self',2,'platform/region.jsp',11,8,0,1),
 ('log','系统日志',0,1,'_self',0,'/log/list.jhtm',13,15,0,1),
 ('syseminfo','系统信息',1,1,'_self',2,'/',15,2,0,1),
 ('online','在线用户',1,1,'_self',1,'platform/online.jsp',21,15,0,1),
 ('data','数据管理',1,1,'_self',1,'/',22,2,0,1),
 ('databack','数据备份',1,1,'_self',0,'platform/databack.jsp',23,22,0,1),
 ('datarestore','数据恢复',1,1,'_self',1,'platform/datarestore.jsp',24,22,0,1),
 ('mydesk','我的桌面',1,1,'_self',0,'/mydesk.jhtm',25,NULL,0,1),
 ('manual_update_cache','缓存更新',1,1,'_self',2,'/cache/list.jhtm',30,22,0,1),
 ('customer','客户管理',1,1,'_self',2,'/customer.jhtm',49,NULL,0,1),
 ('sales_promotion','网店促销',1,1,'_self',4,'/promotion.jhtm',50,NULL,0,1),
 ('sale','销售管理',1,1,'_self',5,'/sale.jhtm',51,NULL,0,1),
 ('distribution','配送管理',1,1,'_self',6,'/distribution.jhtm',52,NULL,0,1),
 ('rolemanagement','岗位管理',1,1,'_self',2,'platform/roleMgt.jsp',124,8,0,1),
 ('hr-zhiwei','岗位调动',1,1,'_self',4,'oa/hr-zhiwei.jsp',127,8,0,1),
 ('hr-document','档案管理',1,1,'_self',5,'oa/hr-document.jsp',128,8,0,1),
 ('product','商品管理',1,1,'_self',3,'/product.jhtm',131,NULL,0,1),
 ('shop_config','店铺设置',1,1,'_self',5,'/shop/config.jhtm',172,1,1,1),
 ('shop_security','安全中心',1,1,'_self',4,'/shop/security.jhtm',173,1,1,1),
 ('shop_article','文章管理',1,1,'_self',3,'/shop/article.jhtm',174,1,1,1),
 ('shop_bullitin','店铺公告',1,1,'_self',2,'/shop/bullitin.jhtm',175,1,1,1),
 ('shop_system_tool','系统工具',1,1,'_self',1,'/shop/system_tool.jhtm',176,1,1,1),
 ('shop_system_report','系统报告',1,1,'_self',0,'/shop/system_report.jhtm',177,1,1,1),
 ('shop_info_manage','店铺管理',1,1,'_self',0,'/shop/list.jhtm',178,172,1,1),
 ('shop_config_email','邮件设置',1,1,'_self',1,'/shop/config_email.jhtm',179,172,1,1),
 ('shop_config_style','样式设置',1,1,'_self',2,'/shop/config_style.jhtm',180,172,1,1),
 ('shop_config_picture','图片上传设置',1,1,'_self',3,'/shop/config_picture.jhtm',181,172,1,1),
 ('shop_security_org','部门管理',1,1,'_self',0,'/shop/security_org.jhtm',182,173,1,1),
 ('shop_security_admin','店铺管理员',1,1,'_self',1,'/shop/security_admin.jhtm',183,173,1,1),
 ('shop_security_backupkey','备份密钥',1,1,'_self',2,'/shop/security_backupkey.jhtm',184,173,1,1),
 ('shop_security_restorekey','导入密钥',1,1,'_self',3,'/shop/security_importkey.jhtm',185,173,1,1),
 ('shop_article_manage','文章管理',1,1,'_self',0,'/shop/article_manage.jhtm',186,174,1,1),
 ('shop_article_add','添加文章',1,1,'_self',1,'/shop/article_add.jhtm',187,174,1,1),
 ('shop_article_type','文章分类',1,1,'_self',2,'/shop/article_type.jhtm',188,174,1,1),
 ('shop_bullitin_add','添加公告',1,1,'_self',1,'/shop/bullitin_add.jhtm',189,175,1,1),
 ('shop_bullitin_manage','公告管理',1,1,'_self',1,'/shop/bullitin_manage.jhtm',190,175,1,1),
 ('shop_system_tool_vote','投票管理',1,1,'_self',0,'/shop/system_vote.jhtm',191,176,1,1),
 ('shop_system_tool_adv','广告管理',1,1,'_self',1,'/shop/system_adv.jhtm',192,176,1,1),
 ('shop_system_tool_link','友情链接',1,1,'_self',2,'/shop/system_link.jhtm',193,176,1,1),
 ('shop_system_tool_service','在线客服',1,1,'_self',3,'/shop/system_service.jhtm',194,176,1,1),
 ('shop_system_tool_email','邮件模板',1,1,'_self',4,'/shop/system_email.jhtm',195,176,1,1),
 ('shop_system_report_admin_log','管理员操作日志',1,1,'_self',1,'/shop/system_admin_log.jhtm',196,177,1,1),
 ('shop_system_report_system_log','系统事件日志',1,1,'_self',1,'/shop/system_event_log.jhtm',197,177,1,1),
 ('shop_system_report_task_log','系统任务日志',1,1,'_self',1,'/shop/system_task_log.jhtm',198,177,1,1),
 ('customer_member','会员',1,1,'_self',1,'/customer/member.jhtm',199,49,1,1),
 ('customer_price_template','价格模板',1,1,'_self',2,'/customer/price_template.jhtm',200,256,1,1),
 ('customer_config','客户设置',1,1,'_self',3,'/customer/config.jhtm',201,256,1,1),
 ('customer_agent','代理商',1,1,'_self',0,'/customer/agent.jhtm',202,49,1,1),
 ('customer_member_add','添加会员',1,1,'_self',1,'/customer/member_add.jhtm',203,199,1,1),
 ('customer_member_manage','会员管理',1,1,'_self',1,'/customer/member_manage.jhtm',204,199,1,1),
 ('customer_member_level','会员等级管理',1,1,'_self',1,'/memberlevel/list.jhtm',205,256,1,1),
 ('customer_agent_level','代理等级管理',1,1,'_self',1,'/agentlevel/list.jhtm',206,256,1,1),
 ('customer_agent_add','添加代理商',1,1,'_self',1,'/customer/agent_add.jhtm',207,202,1,1),
 ('customer_agent_manage','代理商管理',1,1,'_self',1,'/customer/agent_manage.jhtm',208,202,1,1),
 ('product_type','商品分类',1,1,'_self',1,'/product/type.jhtm',209,131,1,1),
 ('product_manage','商品管理',1,1,'_self',1,'/product/manage.jhtm',210,131,1,1),
 ('product_brand','品牌管理',1,1,'_self',1,'/brand/list.jhtm',211,257,1,1),
 ('product_config','商品设置',1,1,'_self',1,'/productconfig/list.jhtm',212,257,1,1),
 ('product_category_manage','类别管理',1,1,'_self',1,'/category/list.jhtm',213,209,1,1),
 ('product_manage_list','商品管理',1,1,'_self',1,'/product/list.jhtm',214,210,1,1),
 ('product_type','商品类型',1,1,'_self',1,'/producttype/list.jhtm',215,209,1,1),
 ('product_add','上架新商品',1,1,'_self',1,'/product/add.jhtm',216,210,1,1),
 ('product_consultation','商品咨询管理',1,1,'_self',1,'/product/consultation.jhtm',219,210,1,1),
 ('product_comments ','商品评论管理',1,1,'_self',1,'/product/comments.jhtm',220,210,1,1),
 ('product_comments_report ','商品评论举报',1,1,'_self',1,'/product/comments_report.jhtm',221,210,1,1),
 ('promotion_gift','礼品',1,1,'_self',0,'/promotion/gift.jhtm',222,50,1,1),
 ('promotion_activity','促销活动',1,1,'_self',1,'/promotion/activity.jhtm',223,50,1,1),
 ('promotion_coupon','优惠券',1,1,'_self',2,'/promotion/coupon.jhtm',224,50,1,1),
 ('promotion_gift_add','添加礼品',1,1,'_self',1,'/promotion/gift_add.jhtm',225,222,1,1),
 ('promotion_gift_manage','礼品管理',1,1,'_self',1,'/promotion/gift_manage.jhtm',226,222,1,1),
 ('promotion_activity_manage','店铺促销活动',1,1,'_self',1,'/promotion/activity.jhtm',227,223,1,1),
 ('promotion_activity_medzh','满额打折',1,1,'_self',1,'/promotion/medzh.jhtm',228,223,1,1),
 ('promotion_activity_mezs','满额赠送',1,1,'_self',1,'/promotion/mezs.jhtm',229,223,1,1),
 ('promotion_activity_mjsj','买几送几',1,1,'_self',1,'/promotion/mjsj.jhtm',230,223,1,1),
 ('promotion_activity_memfy','满额免费用',1,1,'_self',1,'/promotion/memfy.jhtm',231,223,1,1),
 ('promotion_activity_pfdz','批发打折',1,1,'_self',1,'/promotion/pfdz.jhtm',232,223,1,1),
 ('promotion_coupon_add','添加优惠券',1,1,'_self',1,'/promotion/coupon_add.jhtm',233,224,1,1),
 ('promotion_coupon_manage','管理优惠券',1,1,'_self',1,'/promotion/coupon_manage.jhtm',234,224,1,1),
 ('sale_order','订单管理',1,1,'_self',1,'/sale/order.jhtm',235,51,1,1),
 ('sale_pay_type','支付方式',1,1,'_self',1,'/sale/pay_type.jhtm',236,51,1,1),
 ('sale_money','货币管理',1,1,'_self',1,'/currency/list.jhtm',237,258,1,1),
 ('sale_analysis','分析统计',1,1,'_self',1,'/sale/analysis.jhtm',238,51,1,1),
 ('sale_conf','销售选项设置',1,1,'_self',1,'/sale/conf.jhtm',239,258,1,1),
 ('sale_order_option','订单可选项',1,1,'_self',1,'/sale/order_option.jhtm',240,235,1,1),
 ('sale_order_add','新建订单',1,1,'_self',1,'/sale/order_add.jhtm',241,235,1,1),
 ('sale_temp_order','临时订单',1,1,'_self',1,'/sale/temp_order.jhtm',242,235,1,1),
 ('sale_order_manage','订单管理',1,1,'_self',1,'/sale/order_manage.jhtm',243,235,1,1),
 ('sale_pay_addtype','添加支付方式',1,1,'_self',1,'/sale/paytype_add.jhtm',244,236,1,1),
 ('sale_pay_managetype','支付方式管理',1,1,'_self',1,'/sale/paytype_manage.jhtm',245,236,1,1),
 ('sale_analysis_trade','生意报告',1,1,'_self',1,'/sale/analysis_trade.jhtm',246,238,1,1),
 ('sale_analysis_sale','销售报告',1,1,'_self',1,'/sale/analysis_sale.jhtm',247,238,1,1),
 ('sale_analysis_order','订单统计',1,1,'_self',1,'/sale/analysis_order.jhtm',248,238,1,1),
 ('sale_analysis_customer','客户统计',1,1,'_self',1,'/sale/analysis_customer.jhtm',249,238,1,1),
 ('distribution_type','配送方式',1,1,'_self',1,'/distribution/type.jhtm',250,52,1,1),
 ('distribution_area','配送地区',1,1,'_self',1,'/area/list.jhtm',251,259,1,1),
 ('distribution_area_kind','地区分组',1,1,'_self',1,'/areatype/list.jhtm',252,259,1,1),
 ('distribution_type_add','添加配送方式',1,1,'_self',1,'/distribution/type_add.jhtm',253,250,1,1),
 ('distribution_type_manage','配送方式管理',1,1,'_self',1,'/distribution/type_manage.jhtm',254,250,1,1),
 ('base','基础数据',1,1,'_self',8,'/base.jhtm',255,NULL,0,1),
 ('base_customer','客户管理',1,1,'_self',1,'/base/customer.jhtm',256,255,1,1),
 ('base_product','商品管理',1,1,'_self',1,'/base/product.jhtm',257,255,1,1),
 ('base_sale','销售管理',1,1,'_self',1,'/base/sale.jhtm',258,255,1,1),
 ('base_distribution','配送管理',1,1,'_self',1,'/base/promotion.jhtm',259,255,1,1),
 ('mydesk_product','商品',1,1,'_self',0,'/mydesk/product.jhtm',260,25,1,1),
 ('mydesk_sale','销售',1,1,'_self',1,'/mydesk/sale.jhtm',261,25,1,1),
 ('mydesk_promotion','促销',1,1,'_self',2,'/mydesk/promotion.jhtm',262,25,1,1),
 ('mydesk_order','订单',1,1,'_self',3,'/mydesk/order.jhtm',263,25,1,1),
 ('mydesk_customer','客户',1,1,'_self',4,'/mydesk/customer.jhtm',264,25,1,1),
 ('mydesk_product_add','上架新商品',1,1,'_self',0,'/product/add.jhtm',265,260,1,1),
 ('mydesk_sale_paytype','支付方式管理',1,1,'_self',0,'/sale/paytype_manage.jhtm',268,261,1,1),
 ('mydesk_trade_report','生意报告',1,1,'_self',1,'/sale/analysis_trade.jhtm',269,261,1,1),
 ('mydesk_promotion_acrivity','店铺促销活动 ',1,1,'_self',0,'/promotion/activity.jhtm',270,262,1,1),
 ('mydesk_promotion_addgift','添加礼品 ',1,1,'_self',1,'/promotion/gift_add.jhtm',271,262,1,1),
 ('mydesk_promotion_medzh','满额打折',1,1,'_self',2,'/promotion/medzh.jhtm',272,262,1,1),
 ('mydesk_promotion_memfs','满额免费用',1,1,'_self',3,'/promotion/memfy.jhtm',273,262,1,1),
 ('mydesk_promotion_mjsj','买几送几',1,1,'_self',4,'/promotion/mjsj.jhtm',274,262,1,1),
 ('mydesk_promotion_mezs','满额赠送',1,1,'_self',5,'/promotion/mezs.jhtm',275,262,1,1),
 ('mydesk_promotion_pfdzh','批发打折',1,1,'_self',6,'/promotion/pfdz.jhtm',276,262,1,1),
 ('mydesk_promotion_addcoupon','添加优惠券',1,1,'_self',7,'/promotion/coupon_add.jhtm',277,262,1,1),
 ('mydesk_promotion_coupon','优惠券管理',1,1,'_self',8,'/promotion/coupon_manage.jhtm',278,262,1,1),
 ('mydesk_order_add','新建订单',1,1,'_self',1,'/sale/order_add.jhtm',279,263,1,1),
 ('mydesk_order_manage','订单管理',1,1,'_self',2,'/sale/order_manage.jhtm',280,263,1,1),
 ('mydesk_order_option','订单可选项',1,1,'_self',3,'/sale/order_option.jhtm',281,263,1,1),
 ('mydesk_customer_agent','代理商管理',1,1,'_self',2,'/customer/agent.jhtm',283,264,1,1),
 ('mydesk_customer_member','会员管理',1,1,'_self',4,'/customer/member.jhtm',285,264,1,1),
 ('mydesk_customer_pricetemplate','价格模板',1,1,'_self',5,'/customer/price_template.jhtm',286,264,1,1),
 ('cms','内容管理',1,1,'_self',9,'/cms.jhtm',287,NULL,0,1),
 ('cms_model','模型管理',1,1,'_self',1,'/cms/model.jhtm',288,287,0,1),
 ('cms_module','模块管理',1,1,'_self',2,'/cms/module.jhtm',289,287,0,1),
 ('cms_category','栏目管理',1,1,'_self',3,'/cms/category.jhtm',290,287,0,1),
 ('cms_site','网站配置',1,1,'_self',4,'/cms/site_config.jhtm',291,287,0,1),
 ('cms_system_tool','系统工具',1,1,'_self',5,'/cms/system_tool.jhtm',292,287,0,1),
 ('cms_templatesuit','模板方案',1,1,'_self',6,'/cms/template_suit.jhtm',293,287,0,1),
 ('cms_template','模板管理',1,1,'_self',7,'/cms/template.jhtm',294,287,0,1),
 ('cms_model_add','添加模型',1,1,'_self',1,'/cms/model_add.jhtm',295,288,1,1),
 ('cms_model_manage','管理模型',1,1,'_self',2,'/cms/model_manage.jhtm',296,288,1,1),
 ('cms_systemtool_file','文件管理器',1,1,'_self',1,'/cms/file_manage.jhtm',297,292,1,1),
 ('cms_systemtool_cache','缓存管理',1,1,'_self',2,'/cms/system_cache.jhtm',298,292,1,1),
 ('cms_systemtool_map','网站地图',1,1,'_self',3,'/cms/web_map.jhtm',299,292,1,1),
 ('cms_site_base','基本信息',1,1,'_self',1,'/cms/site_base.jhtm',300,291,1,1),
 ('cms_site_config','网站设置',1,1,'_self',2,'/cms/site_config.jhtm',301,291,1,1),
 ('cms_site_email','邮件设置',1,1,'_self',3,'/cms/site_email.jhtm',302,291,1,1),
 ('cms_category_add','添加栏目',1,1,'_self',1,'/cms/category_add.jhtm',303,290,1,1),
 ('cms_category_manage','管理栏目',1,1,'_self',2,'/cms/category_manage.jhtm',304,290,1,1),
 ('cms_category_merge','合并栏目',1,1,'_self',3,'/cms/category_merge.jhtm',305,290,1,1),
 ('cms_template_suit','模板方案',1,1,'_self',1,'/cms/template_suit.jhtm',306,293,1,1),
 ('cms_template_style','风格管理',1,1,'_self',2,'/cms/template_style.jhtm',307,293,1,1),
 ('cms_block_manage','碎片管理',1,1,'_self',1,'/cms/block_manage.jhtm',308,294,1,1),
 ('cms_template_add','新建模板',1,1,'_self',2,'/template/add.jhtm',309,294,1,1),
 ('cms_template_manage','管理模板',1,1,'_self',3,'/template/list.jhtm',310,294,1,1),
 ('cms_block_add','添加碎片',1,1,'_self',4,'/block/add.jhtm',311,294,1,1),
 ('cms_tag_add','添加标签',1,1,'_self',5,'/templatetag/add.jhtm',312,294,1,1),
 ('cms_tag_manage','管理标签',1,1,'_self',6,'/templatetag/list.jhtm',313,294,1,1),
 ('main','首页',0,0,'_self',0,'/main.jhtm',314,NULL,0,1),
 ('system_status','系统状态',1,1,'_self',3,'/system/status.jhtm',315,15,1,1),
 ('product_supplier','供应商信息',1,1,'_self',1,'/supplier/list.jhtm',316,257,1,1);
/*!40000 ALTER TABLE `p_functions` ENABLE KEYS */;


--
-- Definition of table `p_logs`
--

DROP TABLE IF EXISTS `p_logs`;
CREATE TABLE `p_logs` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(20) DEFAULT NULL,
  `operation` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `shop_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`log_id`) USING BTREE,
  KEY `FKC48C441E4CC14BEB` (`shop_id`),
  KEY `FKC48C441EC8C81FCB` (`user_id`),
  CONSTRAINT `FKC48C441EC8C81FCB` FOREIGN KEY (`user_id`) REFERENCES `p_users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1259 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `p_logs`
--

/*!40000 ALTER TABLE `p_logs` DISABLE KEYS */;
INSERT INTO `p_logs` (`log_id`,`ip`,`operation`,`time`,`url`,`shop_id`,`user_id`) VALUES 
 (12,'127.0.0.1','home','2009-04-01 11:05:24','/main.jhtm',1,1),
 (13,'127.0.0.1','成功登录系统','2009-04-01 11:19:04','/login.jsp',1,1),
 (14,'127.0.0.1','home','2009-04-01 11:19:05','/main.jhtm',1,1),
 (15,'127.0.0.1','成功登录系统','2009-04-01 11:37:37','/login.jsp',1,1),
 (16,'127.0.0.1','home','2009-04-01 11:37:38','/main.jhtm',1,1),
 (17,'127.0.0.1','home','2009-04-01 11:37:54','/main.jhtm',1,1),
 (18,'127.0.0.1','home','2009-04-01 11:39:03','/main.jhtm',1,1),
 (19,'127.0.0.1','home','2009-04-01 11:40:46','/main.jhtm',1,1),
 (20,'127.0.0.1','成功登录系统','2009-04-01 11:45:44','/login.jsp',1,1),
 (21,'127.0.0.1','成功退出系统','2009-04-01 11:46:14','/logout.jsp',1,1),
 (22,'127.0.0.1','成功登录系统','2009-04-01 11:49:07','/login.jsp',1,1),
 (23,'127.0.0.1','成功登录系统','2009-04-01 11:54:40','/login.jsp',1,1),
 (24,'127.0.0.1','成功登录系统','2009-04-01 11:57:52','/login.jsp',1,1),
 (25,'127.0.0.1','成功登录系统','2009-04-01 12:00:58','/login.jsp',1,1),
 (357,'127.0.0.1','成功登录系统','2009-04-06 18:23:46','/login.jsp',1,1),
 (358,'127.0.0.1','成功登录系统','2009-04-06 18:29:00','/login.jsp',1,1),
 (359,'127.0.0.1','系统状态','2009-04-06 18:29:32','/system/status.jhtm',1,1),
 (360,'127.0.0.1','客户设置','2009-04-06 18:31:59','/customer/config.jhtm',1,1),
 (361,'127.0.0.1','成功登录系统','2009-04-06 18:44:21','/login.jsp',1,1),
 (362,'127.0.0.1','系统状态','2009-04-06 18:44:34','/system/status.jhtm',1,1),
 (363,'127.0.0.1','系统状态','2009-04-06 18:45:16','/system/status.jhtm',1,1),
 (364,'127.0.0.1','系统状态','2009-04-06 18:45:18','/system/status.jhtm',1,1),
 (365,'127.0.0.1','系统状态','2009-04-06 18:45:20','/system/status.jhtm',1,1),
 (366,'127.0.0.1','系统状态','2009-04-06 18:45:22','/system/status.jhtm',1,1),
 (367,'127.0.0.1','系统状态','2009-04-06 18:48:21','/system/status.jhtm',1,1),
 (368,'127.0.0.1','系统状态','2009-04-06 18:48:24','/system/status.jhtm',1,1),
 (369,'127.0.0.1','系统状态','2009-04-06 18:48:26','/system/status.jhtm',1,1),
 (370,'127.0.0.1','系统状态','2009-04-06 18:48:28','/system/status.jhtm',1,1),
 (371,'127.0.0.1','系统状态','2009-04-06 18:48:30','/system/status.jhtm',1,1),
 (372,'127.0.0.1','系统状态','2009-04-06 18:49:00','/system/status.jhtm',1,1),
 (373,'127.0.0.1','系统状态','2009-04-06 18:49:02','/system/status.jhtm',1,1),
 (374,'127.0.0.1','系统状态','2009-04-06 18:49:04','/system/status.jhtm',1,1),
 (375,'127.0.0.1','成功登录系统','2009-04-06 21:35:47','/login.jsp',1,1),
 (376,'127.0.0.1','品牌管理','2009-04-06 21:36:23','/product/brand/list.jhtm',1,1),
 (377,'127.0.0.1','品牌管理','2009-04-06 21:37:06','/product/brand/list.jhtm',1,1),
 (378,'127.0.0.1','品牌管理','2009-04-06 21:38:18','/product/brand/list.jhtm',1,1),
 (379,'127.0.0.1','品牌管理','2009-04-06 21:38:19','/product/brand/list.jhtm',1,1),
 (380,'127.0.0.1','成功登录系统','2009-04-06 21:42:10','/login.jsp',1,1),
 (381,'127.0.0.1','成功登录系统','2009-04-06 21:46:20','/login.jsp',1,1),
 (382,'127.0.0.1','品牌管理','2009-04-06 21:46:27','/brand/list.jhtm',1,1),
 (383,'127.0.0.1','品牌管理','2009-04-06 21:47:30','/brand/list.jhtm',1,1),
 (384,'127.0.0.1','品牌管理','2009-04-06 21:47:34','/brand/list.jhtm',1,1),
 (385,'127.0.0.1','成功登录系统','2009-04-06 21:49:12','/login.jsp',1,1),
 (386,'127.0.0.1','品牌管理','2009-04-06 21:49:19','/brand/list.jhtm',1,1),
 (387,'127.0.0.1','品牌管理','2009-04-06 21:50:37','/brand/list.jhtm',1,1),
 (388,'127.0.0.1','成功登录系统','2009-04-06 21:59:16','/login.jsp',1,1),
 (389,'127.0.0.1','品牌管理','2009-04-06 21:59:24','/brand/list.jhtm',1,1),
 (390,'127.0.0.1','品牌管理','2009-04-06 22:00:11','/brand/list.jhtm',1,1),
 (391,'127.0.0.1','品牌管理','2009-04-06 22:01:03','/brand/list.jhtm',1,1),
 (392,'127.0.0.1','品牌管理','2009-04-06 22:01:48','/brand/list.jhtm',1,1),
 (393,'127.0.0.1','品牌管理','2009-04-06 22:03:18','/brand/list.jhtm',1,1),
 (394,'127.0.0.1','品牌管理','2009-04-06 22:04:58','/brand/list.jhtm',1,1),
 (395,'127.0.0.1','品牌管理','2009-04-06 22:11:16','/brand/list.jhtm',1,1),
 (396,'127.0.0.1','品牌管理','2009-04-06 22:12:19','/brand/list.jhtm',1,1),
 (397,'127.0.0.1','品牌管理','2009-04-06 22:13:04','/brand/list.jhtm',1,1),
 (398,NULL,'成功退出系统','2009-04-06 22:33:54','/logout.jsp',NULL,1),
 (399,'127.0.0.1','成功登录系统','2009-04-07 11:20:27','/login.jsp',1,1),
 (400,'127.0.0.1','系统状态','2009-04-07 11:31:16','/system/status.jhtm',1,1),
 (401,'127.0.0.1','系统状态','2009-04-07 11:35:12','/system/status.jhtm',1,1),
 (402,NULL,'成功退出系统','2009-04-07 11:55:20','/logout.jsp',NULL,1),
 (403,'127.0.0.1','成功登录系统','2009-04-07 12:59:35','/login.jsp',1,1),
 (404,'127.0.0.1','系统状态','2009-04-07 12:59:46','/system/status.jhtm',1,1),
 (405,'127.0.0.1','系统状态','2009-04-07 12:59:54','/system/status.jhtm',1,1),
 (406,'127.0.0.1','系统状态','2009-04-07 12:59:59','/system/status.jhtm',1,1),
 (407,'127.0.0.1','成功登录系统','2009-04-07 13:00:42','/login.jsp',1,1),
 (408,'127.0.0.1','系统状态','2009-04-07 13:00:47','/system/status.jhtm',1,1),
 (409,'127.0.0.1','系统状态','2009-04-07 13:00:51','/system/status.jhtm',1,1),
 (410,'127.0.0.1','系统状态','2009-04-07 13:02:35','/system/status.jhtm',1,1),
 (411,'127.0.0.1','系统状态','2009-04-07 13:02:44','/system/status.jhtm',1,1),
 (412,'127.0.0.1','系统状态','2009-04-07 13:02:54','/system/status.jhtm',1,1),
 (413,'127.0.0.1','系统状态','2009-04-07 13:03:09','/system/status.jhtm',1,1),
 (414,'127.0.0.1','系统状态','2009-04-07 13:03:11','/system/status.jhtm',1,1),
 (415,'127.0.0.1','系统状态','2009-04-07 13:03:13','/system/status.jhtm',1,1),
 (416,'127.0.0.1','系统状态','2009-04-07 13:03:14','/system/status.jhtm',1,1),
 (417,'127.0.0.1','系统状态','2009-04-07 13:03:16','/system/status.jhtm',1,1),
 (418,'127.0.0.1','系统状态','2009-04-07 13:03:17','/system/status.jhtm',1,1),
 (419,'127.0.0.1','系统状态','2009-04-07 13:03:18','/system/status.jhtm',1,1),
 (420,'127.0.0.1','系统状态','2009-04-07 13:03:53','/system/status.jhtm',1,1),
 (421,'127.0.0.1','系统状态','2009-04-07 13:05:10','/system/status.jhtm',1,1),
 (422,'127.0.0.1','系统状态','2009-04-07 13:05:31','/system/status.jhtm',1,1),
 (423,'127.0.0.1','系统状态','2009-04-07 13:05:53','/system/status.jhtm',1,1),
 (424,'127.0.0.1','品牌管理','2009-04-07 13:06:00','/brand/list.jhtm',1,1),
 (425,'127.0.0.1','品牌管理','2009-04-07 13:06:44','/brand/list.jhtm',1,1),
 (426,'127.0.0.1','品牌管理','2009-04-07 13:07:27','/brand/list.jhtm',1,1),
 (427,'127.0.0.1','品牌管理','2009-04-07 13:08:05','/brand/list.jhtm',1,1),
 (428,'127.0.0.1','品牌管理','2009-04-07 13:08:59','/brand/list.jhtm',1,1),
 (429,'127.0.0.1','品牌管理','2009-04-07 13:10:26','/brand/list.jhtm',1,1),
 (430,'127.0.0.1','成功登录系统','2009-04-07 13:44:05','/login.jsp',1,1),
 (431,'127.0.0.1','品牌管理','2009-04-07 13:44:22','/brand/list.jhtm',1,1),
 (432,'127.0.0.1','品牌管理','2009-04-07 13:45:26','/brand/list.jhtm',1,1),
 (433,'127.0.0.1','品牌管理','2009-04-07 13:47:53','/brand/list.jhtm',1,1),
 (434,'127.0.0.1','品牌管理','2009-04-07 13:49:27','/brand/list.jhtm',1,1),
 (435,'127.0.0.1','品牌管理','2009-04-07 13:51:33','/brand/list.jhtm',1,1),
 (436,'127.0.0.1','品牌管理','2009-04-07 13:54:18','/brand/list.jhtm',1,1),
 (437,'127.0.0.1','品牌管理','2009-04-07 13:54:51','/brand/list.jhtm',1,1),
 (438,'127.0.0.1','成功登录系统','2009-04-07 14:30:08','/login.jsp',1,1),
 (439,'127.0.0.1','品牌管理','2009-04-07 14:30:21','/brand/list.jhtm',1,1),
 (440,'127.0.0.1','品牌管理','2009-04-07 14:31:52','/brand/list.jhtm',1,1),
 (441,'127.0.0.1','品牌管理','2009-04-07 14:34:54','/brand/list.jhtm',1,1),
 (442,'127.0.0.1','成功登录系统','2009-04-07 14:37:12','/login.jsp',1,1),
 (443,'127.0.0.1','品牌管理','2009-04-07 14:37:23','/brand/list.jhtm',1,1),
 (444,'127.0.0.1','成功登录系统','2009-04-07 14:51:33','/login.jsp',1,1),
 (445,'127.0.0.1','品牌管理','2009-04-07 14:51:40','/brand/list.jhtm',1,1),
 (446,'127.0.0.1','成功登录系统','2009-04-07 14:56:27','/login.jsp',1,1),
 (447,'127.0.0.1','品牌管理','2009-04-07 14:56:38','/brand/list.jhtm',1,1),
 (448,'127.0.0.1','品牌管理','2009-04-07 14:57:15','/brand/list.jhtm',1,1),
 (449,'127.0.0.1','成功登录系统','2009-04-07 14:59:51','/login.jsp',1,1),
 (450,'127.0.0.1','品牌管理','2009-04-07 15:00:04','/brand/list.jhtm',1,1),
 (451,'127.0.0.1','品牌管理','2009-04-07 15:00:24','/brand/list.jhtm',1,1),
 (452,'127.0.0.1','品牌管理','2009-04-07 15:01:29','/brand/list.jhtm',1,1),
 (453,'127.0.0.1','品牌管理','2009-04-07 15:01:35','/brand/list.jhtm',1,1),
 (454,'127.0.0.1','品牌管理','2009-04-07 15:01:52','/brand/list.jhtm',1,1),
 (455,'127.0.0.1','品牌管理','2009-04-07 15:02:21','/brand/list.jhtm',1,1),
 (456,'127.0.0.1','品牌管理','2009-04-07 15:03:37','/brand/list.jhtm',1,1),
 (457,'127.0.0.1','品牌管理','2009-04-07 15:03:52','/brand/list.jhtm',1,1),
 (458,'127.0.0.1','品牌管理','2009-04-07 15:04:07','/brand/list.jhtm',1,1),
 (459,'127.0.0.1','品牌管理','2009-04-07 15:04:39','/brand/list.jhtm',1,1),
 (460,'127.0.0.1','品牌管理','2009-04-07 15:04:41','/brand/list.jhtm',1,1),
 (461,'127.0.0.1','成功登录系统','2009-04-07 15:11:46','/login.jsp',1,1),
 (462,'127.0.0.1','品牌管理','2009-04-07 15:12:05','/brand/list.jhtm',1,1),
 (463,'127.0.0.1','品牌管理','2009-04-07 15:12:14','/brand/list.jhtm',1,1),
 (464,'127.0.0.1','品牌管理','2009-04-07 15:12:16','/brand/list.jhtm',1,1),
 (465,'127.0.0.1','品牌管理','2009-04-07 15:12:18','/brand/list.jhtm',1,1),
 (466,'127.0.0.1','成功登录系统','2009-04-07 15:17:25','/login.jsp',1,1),
 (467,'127.0.0.1','品牌管理','2009-04-07 15:17:35','/brand/list.jhtm',1,1),
 (468,'127.0.0.1','品牌管理','2009-04-07 15:19:21','/brand/list.jhtm',1,1),
 (469,'127.0.0.1','品牌管理','2009-04-07 15:19:27','/brand/list.jhtm',1,1),
 (470,'127.0.0.1','品牌管理','2009-04-07 15:19:29','/brand/list.jhtm',1,1),
 (471,'127.0.0.1','品牌管理','2009-04-07 15:19:32','/brand/list.jhtm',1,1),
 (472,'127.0.0.1','品牌管理','2009-04-07 15:23:53','/brand/list.jhtm',1,1),
 (473,'127.0.0.1','品牌管理','2009-04-07 15:26:09','/brand/list.jhtm',1,1),
 (474,'127.0.0.1','品牌管理','2009-04-07 15:26:32','/brand/list.jhtm',1,1),
 (475,'127.0.0.1','品牌管理','2009-04-07 15:26:47','/brand/list.jhtm',1,1),
 (476,'127.0.0.1','品牌管理','2009-04-07 15:28:03','/brand/list.jhtm',1,1),
 (477,'127.0.0.1','品牌管理','2009-04-07 15:28:39','/brand/list.jhtm',1,1),
 (478,'127.0.0.1','品牌管理','2009-04-07 15:28:44','/brand/list.jhtm',1,1),
 (479,'127.0.0.1','品牌管理','2009-04-07 15:28:48','/brand/list.jhtm',1,1),
 (480,'127.0.0.1','品牌管理','2009-04-07 15:30:58','/brand/list.jhtm',1,1),
 (481,'127.0.0.1','品牌管理','2009-04-07 15:31:15','/brand/list.jhtm',1,1),
 (482,'127.0.0.1','品牌管理','2009-04-07 15:31:27','/brand/list.jhtm',1,1),
 (483,'127.0.0.1','品牌管理','2009-04-07 15:31:37','/brand/list.jhtm',1,1),
 (484,'127.0.0.1','缓存更新','2009-04-07 15:34:39','/cache/list.jhtm',1,1),
 (485,'127.0.0.1','缓存更新','2009-04-07 15:34:48','/cache/list.jhtm',1,1),
 (486,'127.0.0.1','品牌管理','2009-04-07 15:35:02','/brand/list.jhtm',1,1),
 (487,'127.0.0.1','品牌管理','2009-04-07 15:35:42','/brand/list.jhtm',1,1),
 (488,'127.0.0.1','品牌管理','2009-04-07 15:36:29','/brand/list.jhtm',1,1),
 (489,'127.0.0.1','品牌管理','2009-04-07 15:51:38','/brand/list.jhtm',1,1),
 (490,'127.0.0.1','品牌管理','2009-04-07 15:52:05','/brand/list.jhtm',1,1),
 (491,'127.0.0.1','品牌管理','2009-04-07 15:52:13','/brand/list.jhtm',1,1),
 (492,'127.0.0.1','品牌管理','2009-04-07 16:02:18','/brand/list.jhtm',1,1),
 (493,'127.0.0.1','品牌管理','2009-04-07 16:07:07','/brand/list.jhtm',1,1),
 (494,'127.0.0.1','品牌管理','2009-04-07 16:09:35','/brand/list.jhtm',1,1),
 (495,'127.0.0.1','品牌管理','2009-04-07 16:10:40','/brand/list.jhtm',1,1),
 (496,'127.0.0.1','成功登录系统','2009-04-07 16:18:36','/login.jsp',1,1),
 (497,'127.0.0.1','品牌管理','2009-04-07 16:18:53','/brand/list.jhtm',1,1),
 (498,'127.0.0.1','品牌管理','2009-04-07 16:19:04','/brand/list.jhtm',1,1),
 (499,'127.0.0.1','品牌管理','2009-04-07 16:20:18','/brand/list.jhtm',1,1),
 (500,'127.0.0.1','品牌管理','2009-04-07 16:22:40','/brand/list.jhtm',1,1),
 (501,'127.0.0.1','品牌管理','2009-04-07 16:23:31','/brand/list.jhtm',1,1),
 (502,'127.0.0.1','品牌管理','2009-04-07 16:25:41','/brand/list.jhtm',1,1),
 (503,'127.0.0.1','品牌管理','2009-04-07 16:26:38','/brand/list.jhtm',1,1),
 (504,'127.0.0.1','品牌管理','2009-04-07 16:29:08','/brand/list.jhtm',1,1),
 (505,'127.0.0.1','品牌管理','2009-04-07 16:29:54','/brand/list.jhtm',1,1),
 (506,'127.0.0.1','品牌管理','2009-04-07 16:31:18','/brand/list.jhtm',1,1),
 (507,'127.0.0.1','品牌管理','2009-04-07 16:32:16','/brand/list.jhtm',1,1),
 (508,'127.0.0.1','品牌管理','2009-04-07 16:33:59','/brand/list.jhtm',1,1),
 (509,'127.0.0.1','品牌管理','2009-04-07 16:34:04','/brand/list.jhtm',1,1),
 (510,'127.0.0.1','品牌管理','2009-04-07 16:34:11','/brand/list.jhtm',1,1),
 (511,'127.0.0.1','品牌管理','2009-04-07 16:34:21','/brand/list.jhtm',1,1),
 (512,'127.0.0.1','品牌管理','2009-04-07 16:34:24','/brand/list.jhtm',1,1),
 (513,'127.0.0.1','品牌管理','2009-04-07 16:38:24','/brand/list.jhtm',1,1),
 (514,'127.0.0.1','品牌管理','2009-04-07 16:38:30','/brand/list.jhtm',1,1),
 (515,'127.0.0.1','品牌管理','2009-04-07 16:39:09','/brand/list.jhtm',1,1),
 (516,'127.0.0.1','品牌管理','2009-04-07 16:39:15','/brand/list.jhtm',1,1),
 (517,'127.0.0.1','品牌管理','2009-04-07 16:40:06','/brand/list.jhtm',1,1),
 (518,'127.0.0.1','品牌管理','2009-04-07 16:40:10','/brand/list.jhtm',1,1),
 (519,'127.0.0.1','品牌管理','2009-04-07 16:41:30','/brand/list.jhtm',1,1),
 (520,'127.0.0.1','品牌管理','2009-04-07 16:42:26','/brand/list.jhtm',1,1),
 (521,'127.0.0.1','品牌管理','2009-04-07 16:42:33','/brand/list.jhtm',1,1),
 (522,'127.0.0.1','品牌管理','2009-04-07 16:43:01','/brand/list.jhtm',1,1),
 (523,'127.0.0.1','品牌管理','2009-04-07 16:43:32','/brand/list.jhtm',1,1),
 (524,'127.0.0.1','品牌管理','2009-04-07 16:43:36','/brand/list.jhtm',1,1),
 (525,'127.0.0.1','品牌管理','2009-04-07 16:43:40','/brand/list.jhtm',1,1),
 (526,'127.0.0.1','品牌管理','2009-04-07 16:43:44','/brand/list.jhtm',1,1),
 (527,'127.0.0.1','品牌管理','2009-04-07 16:43:47','/brand/list.jhtm',1,1),
 (528,'127.0.0.1','品牌管理','2009-04-07 16:43:54','/brand/list.jhtm',1,1),
 (529,'127.0.0.1','品牌管理','2009-04-07 16:46:53','/brand/list.jhtm',1,1),
 (530,'127.0.0.1','品牌管理','2009-04-07 16:47:45','/brand/list.jhtm',1,1),
 (531,'127.0.0.1','品牌管理','2009-04-07 16:48:31','/brand/list.jhtm',1,1),
 (532,'127.0.0.1','品牌管理','2009-04-07 16:49:08','/brand/list.jhtm',1,1),
 (533,'127.0.0.1','品牌管理','2009-04-07 16:50:25','/brand/list.jhtm',1,1),
 (534,'127.0.0.1','品牌管理','2009-04-07 16:51:12','/brand/list.jhtm',1,1),
 (535,'127.0.0.1','品牌管理','2009-04-07 16:51:37','/brand/list.jhtm',1,1),
 (536,'127.0.0.1','品牌管理','2009-04-07 16:52:12','/brand/list.jhtm',1,1),
 (537,'127.0.0.1','品牌管理','2009-04-07 16:53:06','/brand/list.jhtm',1,1),
 (538,'127.0.0.1','品牌管理','2009-04-07 16:54:27','/brand/list.jhtm',1,1),
 (539,'127.0.0.1','品牌管理','2009-04-07 16:57:06','/brand/list.jhtm',1,1),
 (540,'127.0.0.1','品牌管理','2009-04-07 16:57:12','/brand/list.jhtm',1,1),
 (541,'127.0.0.1','品牌管理','2009-04-07 16:58:13','/brand/list.jhtm',1,1),
 (542,'127.0.0.1','品牌管理','2009-04-07 16:58:26','/brand/list.jhtm',1,1),
 (543,'127.0.0.1','品牌管理','2009-04-07 16:59:41','/brand/list.jhtm',1,1),
 (544,'127.0.0.1','品牌管理','2009-04-07 16:59:47','/brand/list.jhtm',1,1),
 (545,'127.0.0.1','品牌管理','2009-04-07 17:00:20','/brand/list.jhtm',1,1),
 (546,'127.0.0.1','品牌管理','2009-04-07 17:00:30','/brand/list.jhtm',1,1),
 (547,'127.0.0.1','品牌管理','2009-04-07 17:02:08','/brand/list.jhtm',1,1),
 (548,'127.0.0.1','品牌管理','2009-04-07 17:03:33','/brand/list.jhtm',1,1),
 (549,'127.0.0.1','品牌管理','2009-04-07 17:04:07','/brand/list.jhtm',1,1),
 (550,'127.0.0.1','品牌管理','2009-04-07 17:04:30','/brand/list.jhtm',1,1),
 (551,'127.0.0.1','品牌管理','2009-04-07 17:08:09','/brand/list.jhtm',1,1),
 (552,'127.0.0.1','品牌管理','2009-04-07 17:09:30','/brand/list.jhtm',1,1),
 (553,'127.0.0.1','品牌管理','2009-04-07 17:10:29','/brand/list.jhtm',1,1),
 (554,'127.0.0.1','品牌管理','2009-04-07 17:11:26','/brand/list.jhtm',1,1),
 (555,'127.0.0.1','品牌管理','2009-04-07 17:12:36','/brand/list.jhtm',1,1),
 (556,'127.0.0.1','成功登录系统','2009-04-07 17:18:52','/login.jsp',1,1),
 (557,'127.0.0.1','配送地区','2009-04-07 17:19:00','/distribution/area.jhtm',1,1),
 (558,'127.0.0.1','品牌管理','2009-04-07 17:19:05','/brand/list.jhtm',1,1),
 (559,'127.0.0.1','成功登录系统','2009-04-07 17:28:10','/login.jsp',1,1),
 (560,'127.0.0.1','品牌管理','2009-04-07 17:28:19','/brand/list.jhtm',1,1),
 (561,'127.0.0.1','品牌管理','2009-04-07 17:29:28','/brand/list.jhtm',1,1),
 (562,'127.0.0.1','品牌管理','2009-04-07 17:29:37','/brand/list.jhtm',1,1),
 (563,'127.0.0.1','品牌管理','2009-04-07 17:29:42','/brand/list.jhtm',1,1),
 (564,'127.0.0.1','成功登录系统','2009-04-07 17:45:16','/login.jsp',1,1),
 (565,'127.0.0.1','成功登录系统','2009-04-07 18:30:33','/login.jsp',1,1),
 (566,'127.0.0.1','供应商','2009-04-07 18:31:21','/supplier/list.jhtm',1,1),
 (567,'127.0.0.1','供应商','2009-04-07 18:32:29','/supplier/list.jhtm',1,1),
 (568,'127.0.0.1','供应商','2009-04-07 18:33:28','/supplier/list.jhtm',1,1),
 (569,'127.0.0.1','供应商','2009-04-07 18:34:36','/supplier/list.jhtm',1,1),
 (570,'127.0.0.1','供应商','2009-04-07 18:35:20','/supplier/list.jhtm',1,1),
 (571,'127.0.0.1','供应商','2009-04-07 18:37:52','/supplier/list.jhtm',1,1),
 (572,'127.0.0.1','供应商','2009-04-07 18:41:00','/supplier/list.jhtm',1,1),
 (573,'127.0.0.1','供应商','2009-04-07 18:46:57','/supplier/list.jhtm',1,1),
 (574,'127.0.0.1','供应商','2009-04-07 18:47:37','/supplier/list.jhtm',1,1),
 (575,'127.0.0.1','供应商','2009-04-07 18:49:07','/supplier/list.jhtm',1,1),
 (576,'127.0.0.1','供应商','2009-04-07 18:51:34','/supplier/list.jhtm',1,1),
 (577,'127.0.0.1','供应商','2009-04-07 18:53:00','/supplier/list.jhtm',1,1),
 (578,NULL,'成功退出系统','2009-04-07 19:13:05','/logout.jsp',NULL,1),
 (579,'127.0.0.1','成功登录系统','2009-04-07 19:13:14','/login.jsp',1,1),
 (580,'127.0.0.1','供应商','2009-04-07 19:13:22','/supplier/list.jhtm',1,1),
 (581,'127.0.0.1','供应商','2009-04-07 19:14:08','/supplier/list.jhtm',1,1),
 (582,'127.0.0.1','供应商','2009-04-07 19:15:45','/supplier/list.jhtm',1,1),
 (583,'127.0.0.1','供应商','2009-04-07 19:15:49','/supplier/list.jhtm',1,1),
 (584,'127.0.0.1','供应商','2009-04-07 19:16:25','/supplier/list.jhtm',1,1),
 (585,'127.0.0.1','供应商','2009-04-07 19:17:53','/supplier/list.jhtm',1,1),
 (586,'127.0.0.1','供应商','2009-04-07 19:18:50','/supplier/list.jhtm',1,1),
 (587,'127.0.0.1','供应商','2009-04-07 19:19:12','/supplier/list.jhtm',1,1),
 (588,'127.0.0.1','供应商','2009-04-07 19:20:32','/supplier/list.jhtm',1,1),
 (589,'127.0.0.1','供应商','2009-04-07 19:21:04','/supplier/list.jhtm',1,1),
 (590,'127.0.0.1','供应商','2009-04-07 19:25:45','/supplier/list.jhtm',1,1),
 (591,'127.0.0.1','供应商','2009-04-07 19:25:57','/supplier/list.jhtm',1,1),
 (592,'127.0.0.1','供应商','2009-04-07 19:26:24','/supplier/list.jhtm',1,1),
 (593,'127.0.0.1','供应商','2009-04-07 19:30:04','/supplier/list.jhtm',1,1),
 (594,'127.0.0.1','供应商','2009-04-07 19:30:41','/supplier/list.jhtm',1,1),
 (595,'127.0.0.1','供应商','2009-04-07 19:43:19','/supplier/list.jhtm',1,1),
 (596,'127.0.0.1','供应商','2009-04-07 19:44:13','/supplier/list.jhtm',1,1),
 (597,'127.0.0.1','供应商','2009-04-07 19:44:30','/supplier/list.jhtm',1,1),
 (598,'127.0.0.1','供应商','2009-04-07 19:45:23','/supplier/list.jhtm',1,1),
 (599,NULL,'成功退出系统','2009-04-07 20:05:46','/logout.jsp',NULL,1),
 (600,'127.0.0.1','成功登录系统','2009-04-08 10:08:38','/login.jsp',1,1),
 (601,'127.0.0.1','供应商信息','2009-04-08 10:09:03','/supplier/list.jhtm',1,1),
 (602,'127.0.0.1','系统状态','2009-04-08 10:35:49','/system/status.jhtm',1,1),
 (603,'127.0.0.1','系统状态','2009-04-08 10:35:57','/system/status.jhtm',1,1),
 (604,'127.0.0.1','系统状态','2009-04-08 10:40:40','/system/status.jhtm',1,1),
 (605,'127.0.0.1','供应商信息','2009-04-08 10:57:26','/supplier/list.jhtm',1,1),
 (606,'127.0.0.1','供应商信息','2009-04-08 10:58:07','/supplier/list.jhtm',1,1),
 (607,'127.0.0.1','供应商信息','2009-04-08 10:58:39','/supplier/list.jhtm',1,1),
 (608,'127.0.0.1','供应商信息','2009-04-08 10:59:28','/supplier/list.jhtm',1,1),
 (609,'127.0.0.1','供应商信息','2009-04-08 11:00:44','/supplier/list.jhtm',1,1),
 (610,'127.0.0.1','成功登录系统','2009-04-08 11:16:40','/login.jsp',1,1),
 (611,'127.0.0.1','供应商信息','2009-04-08 11:16:49','/supplier/list.jhtm',1,1),
 (612,'127.0.0.1','供应商信息','2009-04-08 11:16:55','/supplier/list.jhtm',1,1),
 (613,'127.0.0.1','供应商信息','2009-04-08 11:16:59','/supplier/list.jhtm',1,1),
 (614,NULL,'成功退出系统','2009-04-08 11:38:16','/logout.jsp',NULL,1),
 (615,'127.0.0.1','成功登录系统','2009-04-08 11:46:42','/login.jsp',1,1),
 (616,'127.0.0.1','供应商信息','2009-04-08 11:46:50','/supplier/list.jhtm',1,1),
 (617,'127.0.0.1','供应商信息','2009-04-08 11:47:46','/supplier/list.jhtm',1,1),
 (618,'127.0.0.1','供应商信息','2009-04-08 11:48:42','/supplier/list.jhtm',1,1),
 (619,'127.0.0.1','供应商信息','2009-04-08 11:49:03','/supplier/list.jhtm',1,1),
 (620,'127.0.0.1','供应商信息','2009-04-08 11:50:28','/supplier/list.jhtm',1,1),
 (621,'127.0.0.1','供应商信息','2009-04-08 11:50:44','/supplier/list.jhtm',1,1),
 (622,'127.0.0.1','供应商信息','2009-04-08 12:04:05','/supplier/list.jhtm',1,1),
 (623,'127.0.0.1','供应商信息','2009-04-08 12:04:20','/supplier/list.jhtm',1,1),
 (624,'127.0.0.1','品牌管理','2009-04-08 12:06:54','/brand/list.jhtm',1,1),
 (625,'127.0.0.1','供应商信息','2009-04-08 12:06:57','/supplier/list.jhtm',1,1),
 (626,'127.0.0.1','地区分组','2009-04-08 12:07:01','/distribution/area_kind.jhtm',1,1),
 (627,'127.0.0.1','成功登录系统','2009-04-08 14:59:51','/login.jsp',1,1),
 (628,'127.0.0.1','供应商信息','2009-04-08 15:00:09','/supplier/list.jhtm',1,1),
 (629,'127.0.0.1','会员等级管理','2009-04-08 15:00:15','/customer/member_type.jhtm',1,1),
 (630,'127.0.0.1','缓存更新','2009-04-08 15:03:20','/cache/list.jhtm',1,1),
 (631,'127.0.0.1','缓存更新','2009-04-08 15:03:23','/cache/list.jhtm',1,1),
 (632,'127.0.0.1','会员等级管理','2009-04-08 15:04:01','/memberlevel/list.jhtm',1,1),
 (633,'127.0.0.1','会员等级管理','2009-04-08 15:04:42','/memberlevel/list.jhtm',1,1),
 (634,'127.0.0.1','代理商等级管理','2009-04-08 15:05:17','/agentlevel/list.jhtm',1,1),
 (635,'127.0.0.1','代理商等级管理','2009-04-08 15:05:29','/agentlevel/list.jhtm',1,1),
 (636,'127.0.0.1','代理商等级管理','2009-04-08 15:05:44','/agentlevel/list.jhtm',1,1),
 (637,'127.0.0.1','代理商等级管理','2009-04-08 15:06:16','/agentlevel/list.jhtm',1,1),
 (638,'127.0.0.1','代理商等级管理','2009-04-08 15:07:30','/agentlevel/list.jhtm',1,1),
 (639,'127.0.0.1','会员等级管理','2009-04-08 15:11:38','/memberlevel/list.jhtm',1,1),
 (640,'127.0.0.1','代理商等级管理','2009-04-08 15:11:42','/agentlevel/list.jhtm',1,1),
 (641,'127.0.0.1','代理商等级管理','2009-04-08 15:12:35','/agentlevel/list.jhtm',1,1),
 (642,'127.0.0.1','代理商等级管理','2009-04-08 15:13:18','/agentlevel/list.jhtm',1,1),
 (643,'127.0.0.1','代理商等级管理','2009-04-08 15:14:04','/agentlevel/list.jhtm',1,1),
 (644,'127.0.0.1','代理商等级管理','2009-04-08 15:14:20','/agentlevel/list.jhtm',1,1),
 (645,'127.0.0.1','代理商等级管理','2009-04-08 15:15:47','/agentlevel/list.jhtm',1,1),
 (646,'127.0.0.1','代理商等级管理','2009-04-08 15:16:03','/agentlevel/list.jhtm',1,1),
 (647,'127.0.0.1','代理商等级管理','2009-04-08 15:16:41','/agentlevel/list.jhtm',1,1),
 (648,'127.0.0.1','代理商等级管理','2009-04-08 15:16:56','/agentlevel/list.jhtm',1,1),
 (649,'127.0.0.1','代理商等级管理','2009-04-08 15:17:13','/agentlevel/list.jhtm',1,1),
 (650,'127.0.0.1','代理商等级管理','2009-04-08 15:17:19','/agentlevel/list.jhtm',1,1),
 (651,'127.0.0.1','代理商等级管理','2009-04-08 15:17:29','/agentlevel/list.jhtm',1,1),
 (652,'127.0.0.1','代理商等级管理','2009-04-08 15:19:54','/agentlevel/list.jhtm',1,1),
 (653,'127.0.0.1','代理商等级管理','2009-04-08 15:20:29','/agentlevel/list.jhtm',1,1),
 (654,'127.0.0.1','代理商等级管理','2009-04-08 15:20:44','/agentlevel/list.jhtm',1,1),
 (655,'127.0.0.1','会员等级管理','2009-04-08 15:20:51','/memberlevel/list.jhtm',1,1),
 (656,'127.0.0.1','会员等级管理','2009-04-08 15:23:47','/memberlevel/list.jhtm',1,1),
 (657,'127.0.0.1','会员等级管理','2009-04-08 15:24:43','/memberlevel/list.jhtm',1,1),
 (658,'127.0.0.1','会员等级管理','2009-04-08 15:27:10','/memberlevel/list.jhtm',1,1),
 (659,'127.0.0.1','会员等级管理','2009-04-08 15:28:11','/memberlevel/list.jhtm',1,1),
 (660,'127.0.0.1','代理商等级管理','2009-04-08 15:28:12','/agentlevel/list.jhtm',1,1),
 (661,'127.0.0.1','会员等级管理','2009-04-08 15:29:32','/memberlevel/list.jhtm',1,1),
 (662,'127.0.0.1','会员等级管理','2009-04-08 15:43:19','/memberlevel/list.jhtm',1,1),
 (663,'127.0.0.1','会员等级管理','2009-04-08 15:47:50','/memberlevel/list.jhtm',1,1),
 (664,'127.0.0.1','会员等级管理','2009-04-08 15:47:59','/memberlevel/list.jhtm',1,1),
 (665,'127.0.0.1','会员等级管理','2009-04-08 15:48:50','/memberlevel/list.jhtm',1,1),
 (666,'127.0.0.1','代理商等级管理','2009-04-08 15:49:28','/agentlevel/list.jhtm',1,1),
 (667,'127.0.0.1','会员等级管理','2009-04-08 15:49:30','/memberlevel/list.jhtm',1,1),
 (668,'127.0.0.1','会员等级管理','2009-04-08 15:49:59','/memberlevel/list.jhtm',1,1),
 (669,'127.0.0.1','成功登录系统','2009-04-08 15:59:43','/login.jsp',1,1),
 (670,'127.0.0.1','会员等级管理','2009-04-08 15:59:53','/memberlevel/list.jhtm',1,1),
 (671,'127.0.0.1','会员等级管理','2009-04-08 16:00:07','/memberlevel/list.jhtm',1,1),
 (672,'127.0.0.1','会员等级管理','2009-04-08 16:00:37','/memberlevel/list.jhtm',1,1),
 (673,'127.0.0.1','会员等级管理','2009-04-08 16:00:43','/memberlevel/list.jhtm',1,1),
 (674,'127.0.0.1','代理等级管理','2009-04-08 16:02:11','/agentlevel/list.jhtm',1,1),
 (675,'127.0.0.1','会员等级管理','2009-04-08 16:02:13','/memberlevel/list.jhtm',1,1),
 (676,'127.0.0.1','代理等级管理','2009-04-08 16:02:15','/agentlevel/list.jhtm',1,1),
 (677,'127.0.0.1','会员等级管理','2009-04-08 16:02:18','/memberlevel/list.jhtm',1,1),
 (678,'127.0.0.1','会员等级管理','2009-04-08 16:03:19','/memberlevel/list.jhtm',1,1),
 (679,'127.0.0.1','会员等级管理','2009-04-08 16:03:28','/memberlevel/list.jhtm',1,1),
 (680,'127.0.0.1','会员等级管理','2009-04-08 16:04:10','/memberlevel/list.jhtm',1,1),
 (681,'127.0.0.1','成功登录系统','2009-04-08 16:50:35','/login.jsp',1,1),
 (682,'127.0.0.1','货币管理','2009-04-08 16:50:51','/currency/list.jhtm',1,1),
 (683,'127.0.0.1','货币管理','2009-04-08 16:51:50','/currency/list.jhtm',1,1),
 (684,'127.0.0.1','货币管理','2009-04-08 16:52:26','/currency/list.jhtm',1,1),
 (685,'127.0.0.1','货币管理','2009-04-08 16:52:39','/currency/list.jhtm',1,1),
 (686,'127.0.0.1','货币管理','2009-04-08 16:53:44','/currency/list.jhtm',1,1),
 (687,'127.0.0.1','货币管理','2009-04-08 16:54:01','/currency/list.jhtm',1,1),
 (688,'127.0.0.1','货币管理','2009-04-08 16:54:20','/currency/list.jhtm',1,1),
 (689,'127.0.0.1','货币管理','2009-04-08 16:54:51','/currency/list.jhtm',1,1),
 (690,'127.0.0.1','货币管理','2009-04-08 16:55:20','/currency/list.jhtm',1,1),
 (691,'127.0.0.1','货币管理','2009-04-08 16:56:47','/currency/list.jhtm',1,1),
 (692,'127.0.0.1','货币管理','2009-04-08 16:57:18','/currency/list.jhtm',1,1),
 (693,'127.0.0.1','货币管理','2009-04-08 16:58:19','/currency/list.jhtm',1,1),
 (694,'127.0.0.1','货币管理','2009-04-08 16:59:05','/currency/list.jhtm',1,1),
 (695,'127.0.0.1','货币管理','2009-04-08 16:59:17','/currency/list.jhtm',1,1),
 (696,'127.0.0.1','货币管理','2009-04-08 16:59:53','/currency/list.jhtm',1,1),
 (697,'127.0.0.1','货币管理','2009-04-08 17:00:19','/currency/list.jhtm',1,1),
 (698,'127.0.0.1','货币管理','2009-04-08 17:00:46','/currency/list.jhtm',1,1),
 (699,'127.0.0.1','货币管理','2009-04-08 17:03:45','/currency/list.jhtm',1,1),
 (700,'127.0.0.1','货币管理','2009-04-08 17:04:20','/currency/list.jhtm',1,1),
 (701,'127.0.0.1','货币管理','2009-04-08 17:04:40','/currency/list.jhtm',1,1),
 (702,'127.0.0.1','货币管理','2009-04-08 17:04:58','/currency/list.jhtm',1,1),
 (703,'127.0.0.1','货币管理','2009-04-08 17:05:04','/currency/list.jhtm',1,1),
 (704,'127.0.0.1','成功登录系统','2009-04-08 17:52:22','/login.jsp',1,1),
 (705,'127.0.0.1','地区分组','2009-04-08 17:52:40','/areatype/list.jhtm',1,1),
 (706,'127.0.0.1','地区分组','2009-04-08 17:53:22','/areatype/list.jhtm',1,1),
 (707,'127.0.0.1','地区分组','2009-04-08 17:53:46','/areatype/list.jhtm',1,1),
 (708,'127.0.0.1','地区分组','2009-04-08 17:53:55','/areatype/list.jhtm',1,1),
 (709,'127.0.0.1','地区分组','2009-04-08 17:54:04','/areatype/list.jhtm',1,1),
 (710,'127.0.0.1','地区分组','2009-04-08 17:55:10','/areatype/list.jhtm',1,1),
 (711,'127.0.0.1','地区分组','2009-04-08 17:55:26','/areatype/list.jhtm',1,1),
 (712,'127.0.0.1','地区分组','2009-04-08 17:55:40','/areatype/list.jhtm',1,1),
 (713,'127.0.0.1','地区分组','2009-04-08 17:55:52','/areatype/list.jhtm',1,1),
 (714,'127.0.0.1','地区分组','2009-04-08 17:56:39','/areatype/list.jhtm',1,1),
 (715,'127.0.0.1','地区分组','2009-04-08 17:56:57','/areatype/list.jhtm',1,1),
 (716,'127.0.0.1','地区分组','2009-04-08 17:57:52','/areatype/list.jhtm',1,1),
 (717,'127.0.0.1','货币管理','2009-04-08 17:57:57','/currency/list.jhtm',1,1),
 (718,'127.0.0.1','货币管理','2009-04-08 17:59:46','/currency/list.jhtm',1,1),
 (719,'127.0.0.1','配送地区','2009-04-08 18:00:22','/area/list.jhtm',1,1),
 (720,'127.0.0.1','配送地区','2009-04-08 18:02:18','/area/list.jhtm',1,1),
 (721,'127.0.0.1','配送地区','2009-04-08 18:02:38','/area/list.jhtm',1,1),
 (722,'127.0.0.1','地区分组','2009-04-08 18:02:54','/areatype/list.jhtm',1,1),
 (723,'127.0.0.1','配送地区','2009-04-08 18:02:57','/area/list.jhtm',1,1),
 (724,'127.0.0.1','配送地区','2009-04-08 18:05:32','/area/list.jhtm',1,1),
 (725,'127.0.0.1','配送地区','2009-04-08 18:06:00','/area/list.jhtm',1,1),
 (726,'127.0.0.1','配送地区','2009-04-08 18:06:36','/area/list.jhtm',1,1),
 (727,'127.0.0.1','配送地区','2009-04-08 18:06:57','/area/list.jhtm',1,1),
 (728,'127.0.0.1','配送地区','2009-04-08 18:07:16','/area/list.jhtm',1,1),
 (729,'127.0.0.1','配送地区','2009-04-08 18:07:29','/area/list.jhtm',1,1),
 (730,'127.0.0.1','配送地区','2009-04-08 18:10:57','/area/list.jhtm',1,1),
 (731,'127.0.0.1','配送地区','2009-04-08 18:13:26','/area/list.jhtm',1,1),
 (732,'127.0.0.1','配送地区','2009-04-08 18:15:11','/area/list.jhtm',1,1),
 (733,'127.0.0.1','配送地区','2009-04-08 18:15:22','/area/list.jhtm',1,1),
 (734,'127.0.0.1','配送地区','2009-04-08 18:15:41','/area/list.jhtm',1,1),
 (735,'127.0.0.1','配送地区','2009-04-08 18:15:46','/area/list.jhtm',1,1),
 (736,'127.0.0.1','配送地区','2009-04-08 18:16:05','/area/list.jhtm',1,1),
 (737,'127.0.0.1','配送地区','2009-04-08 18:16:46','/area/list.jhtm',1,1),
 (738,'127.0.0.1','配送地区','2009-04-08 18:17:42','/area/list.jhtm',1,1),
 (739,'127.0.0.1','配送地区','2009-04-08 18:17:59','/area/list.jhtm',1,1),
 (740,'127.0.0.1','配送地区','2009-04-08 18:18:07','/area/list.jhtm',1,1),
 (741,'127.0.0.1','配送地区','2009-04-08 18:18:54','/area/list.jhtm',1,1),
 (742,'127.0.0.1','配送地区','2009-04-08 18:19:01','/area/list.jhtm',1,1),
 (743,'127.0.0.1','配送地区','2009-04-08 18:20:21','/area/list.jhtm',1,1),
 (744,'127.0.0.1','配送地区','2009-04-08 18:21:13','/area/list.jhtm',1,1),
 (745,'127.0.0.1','配送地区','2009-04-08 18:21:57','/area/list.jhtm',1,1),
 (746,'127.0.0.1','配送地区','2009-04-08 18:23:07','/area/list.jhtm',1,1),
 (747,'127.0.0.1','配送地区','2009-04-08 18:23:31','/area/list.jhtm',1,1),
 (748,'127.0.0.1','配送地区','2009-04-08 18:24:04','/area/list.jhtm',1,1),
 (749,'127.0.0.1','配送地区','2009-04-08 18:25:09','/area/list.jhtm',1,1),
 (750,'127.0.0.1','配送地区','2009-04-08 18:25:25','/area/list.jhtm',1,1),
 (751,'127.0.0.1','配送地区','2009-04-08 18:26:14','/area/list.jhtm',1,1),
 (752,'127.0.0.1','配送地区','2009-04-08 18:26:45','/area/list.jhtm',1,1),
 (753,'127.0.0.1','配送地区','2009-04-08 18:26:56','/area/list.jhtm',1,1),
 (754,'127.0.0.1','配送地区','2009-04-08 18:27:16','/area/list.jhtm',1,1),
 (755,'127.0.0.1','地区分组','2009-04-08 18:27:34','/areatype/list.jhtm',1,1),
 (756,'127.0.0.1','成功登录系统','2009-04-08 19:11:22','/login.jsp',1,1),
 (757,'127.0.0.1','缓存更新','2009-04-08 19:12:30','/cache/list.jhtm',1,1),
 (758,'127.0.0.1','缓存更新','2009-04-08 19:12:34','/cache/list.jhtm',1,1),
 (759,'127.0.0.1','商品设置','2009-04-08 19:12:58','/productconfig/list.jhtm',1,1),
 (760,'127.0.0.1','商品设置','2009-04-08 19:13:38','/productconfig/list.jhtm',1,1),
 (761,'127.0.0.1','商品设置','2009-04-08 19:14:14','/productconfig/list.jhtm',1,1),
 (762,'127.0.0.1','商品设置','2009-04-08 19:14:30','/productconfig/list.jhtm',1,1),
 (763,'127.0.0.1','商品设置','2009-04-08 19:14:55','/productconfig/list.jhtm',1,1),
 (764,'127.0.0.1','商品设置','2009-04-08 19:15:29','/productconfig/list.jhtm',1,1),
 (765,'127.0.0.1','商品设置','2009-04-08 19:15:35','/productconfig/list.jhtm',1,1),
 (766,'127.0.0.1','商品设置','2009-04-08 19:16:01','/productconfig/list.jhtm',1,1),
 (767,'127.0.0.1','商品设置','2009-04-08 19:17:00','/productconfig/list.jhtm',1,1),
 (768,'127.0.0.1','商品设置','2009-04-08 19:17:20','/productconfig/list.jhtm',1,1),
 (769,'127.0.0.1','商品设置','2009-04-08 19:17:45','/productconfig/list.jhtm',1,1),
 (770,'127.0.0.1','商品设置','2009-04-08 19:18:42','/productconfig/list.jhtm',1,1),
 (771,'127.0.0.1','商品设置','2009-04-08 19:19:11','/productconfig/list.jhtm',1,1),
 (772,'127.0.0.1','商品设置','2009-04-08 19:19:53','/productconfig/list.jhtm',1,1),
 (773,'127.0.0.1','商品设置','2009-04-08 19:20:58','/productconfig/list.jhtm',1,1),
 (774,'127.0.0.1','成功登录系统','2009-04-08 19:24:52','/login.jsp',1,1),
 (775,'127.0.0.1','商品设置','2009-04-08 19:25:12','/productconfig/list.jhtm',1,1),
 (776,'127.0.0.1','商品设置','2009-04-08 19:27:31','/productconfig/list.jhtm',1,1),
 (777,'127.0.0.1','成功登录系统','2009-04-08 19:30:33','/login.jsp',1,1),
 (778,'127.0.0.1','商品设置','2009-04-08 19:30:45','/productconfig/list.jhtm',1,1),
 (779,'127.0.0.1','商品设置','2009-04-08 19:32:11','/productconfig/list.jhtm',1,1),
 (780,'127.0.0.1','商品设置','2009-04-08 19:32:33','/productconfig/list.jhtm',1,1),
 (781,'127.0.0.1','商品设置','2009-04-08 19:33:36','/productconfig/list.jhtm',1,1),
 (782,'127.0.0.1','商品设置','2009-04-08 19:33:55','/productconfig/list.jhtm',1,1),
 (783,'127.0.0.1','商品设置','2009-04-08 19:34:28','/productconfig/list.jhtm',1,1),
 (784,'127.0.0.1','商品设置','2009-04-08 19:35:05','/productconfig/list.jhtm',1,1),
 (785,'127.0.0.1','商品设置','2009-04-08 19:35:30','/productconfig/list.jhtm',1,1),
 (786,'127.0.0.1','商品设置','2009-04-08 19:36:14','/productconfig/list.jhtm',1,1),
 (787,'127.0.0.1','商品设置','2009-04-08 19:37:04','/productconfig/list.jhtm',1,1),
 (788,'127.0.0.1','商品设置','2009-04-08 19:37:09','/productconfig/list.jhtm',1,1),
 (789,'127.0.0.1','商品设置','2009-04-08 19:37:25','/productconfig/list.jhtm',1,1),
 (790,'127.0.0.1','商品设置','2009-04-08 19:37:31','/productconfig/list.jhtm',1,1),
 (791,'127.0.0.1','成功登录系统','2009-04-08 20:34:13','/login.jsp',1,1),
 (792,'127.0.0.1','商品设置','2009-04-08 20:38:42','/productconfig/list.jhtm',1,1),
 (793,'127.0.0.1','商品设置','2009-04-08 20:39:10','/productconfig/list.jhtm',1,1),
 (794,'127.0.0.1','商品设置','2009-04-08 20:39:34','/productconfig/list.jhtm',1,1),
 (795,'127.0.0.1','商品设置','2009-04-08 20:39:43','/productconfig/list.jhtm',1,1),
 (796,'127.0.0.1','商品设置','2009-04-08 20:40:02','/productconfig/list.jhtm',1,1),
 (797,'127.0.0.1','商品设置','2009-04-08 20:40:08','/productconfig/list.jhtm',1,1),
 (798,'127.0.0.1','商品设置','2009-04-08 20:48:54','/productconfig/list.jhtm',1,1),
 (799,'127.0.0.1','商品设置','2009-04-08 20:49:05','/productconfig/list.jhtm',1,1),
 (800,'127.0.0.1','成功登录系统','2009-04-09 14:08:54','/login.jsp',1,1),
 (801,'127.0.0.1','配送地区','2009-04-09 14:12:30','/area/list.jhtm',1,1),
 (802,'127.0.0.1','地区分组','2009-04-09 14:12:34','/areatype/list.jhtm',1,1),
 (803,'127.0.0.1','货币管理','2009-04-09 14:17:53','/currency/list.jhtm',1,1),
 (804,'127.0.0.1','供应商信息','2009-04-09 14:17:55','/supplier/list.jhtm',1,1),
 (805,'127.0.0.1','品牌管理','2009-04-09 14:17:58','/brand/list.jhtm',1,1),
 (806,'127.0.0.1','供应商信息','2009-04-09 14:18:01','/supplier/list.jhtm',1,1),
 (807,'127.0.0.1','供应商信息','2009-04-09 14:18:10','/supplier/list.jhtm',1,1),
 (808,'127.0.0.1','供应商信息','2009-04-09 14:18:21','/supplier/list.jhtm',1,1),
 (809,'127.0.0.1','供应商信息','2009-04-09 14:19:08','/supplier/list.jhtm',1,1),
 (810,'127.0.0.1','商品设置','2009-04-09 14:19:11','/productconfig/list.jhtm',1,1),
 (811,'127.0.0.1','商品设置','2009-04-09 14:19:21','/productconfig/list.jhtm',1,1),
 (812,'127.0.0.1','货币管理','2009-04-09 14:19:27','/currency/list.jhtm',1,1),
 (813,'127.0.0.1','销售选项设置','2009-04-09 14:19:30','/sale/conf.jhtm',1,1),
 (814,'127.0.0.1','供应商信息','2009-04-09 14:19:32','/supplier/list.jhtm',1,1),
 (815,'127.0.0.1','商品设置','2009-04-09 14:19:34','/productconfig/list.jhtm',1,1),
 (816,'127.0.0.1','品牌管理','2009-04-09 14:19:37','/brand/list.jhtm',1,1),
 (817,'127.0.0.1','价格模板','2009-04-09 14:19:41','/customer/price_template.jhtm',1,1),
 (818,'127.0.0.1','会员等级管理','2009-04-09 14:19:42','/memberlevel/list.jhtm',1,1),
 (819,'127.0.0.1','代理等级管理','2009-04-09 14:19:46','/agentlevel/list.jhtm',1,1),
 (820,'127.0.0.1','成功登录系统','2009-04-09 14:35:13','/login.jsp',1,1),
 (821,'127.0.0.1','商品设置','2009-04-09 14:35:53','/productconfig/list.jhtm',1,1),
 (822,'127.0.0.1','会员等级管理','2009-04-09 14:35:56','/memberlevel/list.jhtm',1,1),
 (823,'127.0.0.1','代理等级管理','2009-04-09 14:36:00','/agentlevel/list.jhtm',1,1),
 (824,'127.0.0.1','会员等级管理','2009-04-09 14:36:06','/memberlevel/list.jhtm',1,1),
 (825,'127.0.0.1','成功登录系统','2009-04-09 16:38:06','/login.jsp',1,1),
 (826,'127.0.0.1','会员等级管理','2009-04-09 16:38:45','/memberlevel/list.jhtm',1,1),
 (827,'127.0.0.1','会员等级管理','2009-04-09 16:39:32','/memberlevel/list.jhtm',1,1),
 (828,'127.0.0.1','会员等级管理','2009-04-09 16:39:52','/memberlevel/list.jhtm',1,1),
 (829,'127.0.0.1','会员等级管理','2009-04-09 16:40:22','/memberlevel/list.jhtm',1,1),
 (830,'127.0.0.1','成功登录系统','2009-04-09 16:46:45','/login.jsp',1,1),
 (831,'127.0.0.1','会员等级管理','2009-04-09 16:46:54','/memberlevel/list.jhtm',1,1),
 (832,'127.0.0.1','会员等级管理','2009-04-09 16:47:02','/memberlevel/list.jhtm',1,1),
 (833,'127.0.0.1','会员等级管理','2009-04-09 16:47:08','/memberlevel/list.jhtm',1,1),
 (834,NULL,'成功退出系统','2009-04-09 17:24:40','/logout.jsp',NULL,1),
 (835,'127.0.0.1','成功登录系统','2009-04-09 20:34:00','/login.jsp',1,1),
 (836,'127.0.0.1','缓存更新','2009-04-09 20:37:27','/cache/list.jhtm',1,1),
 (837,'127.0.0.1','缓存更新','2009-04-09 20:37:33','/cache/list.jhtm',1,1),
 (838,'127.0.0.1','类别管理','2009-04-09 20:37:46','/category/list.jhtm',1,1),
 (839,'127.0.0.1','成功登录系统','2009-04-09 20:57:00','/login.jsp',1,1),
 (840,'127.0.0.1','类别管理','2009-04-09 20:57:14','/category/list.jhtm',1,1),
 (841,'127.0.0.1','类别管理','2009-04-09 20:58:17','/category/list.jhtm',1,1),
 (842,'127.0.0.1','成功登录系统','2009-04-09 21:23:52','/login.jsp',1,1),
 (843,'127.0.0.1','类别管理','2009-04-09 21:24:18','/category/list.jhtm',1,1),
 (844,'127.0.0.1','类别管理','2009-04-09 21:35:26','/category/list.jhtm',1,1),
 (845,'127.0.0.1','类别管理','2009-04-09 21:36:34','/category/list.jhtm',1,1),
 (846,'127.0.0.1','类别管理','2009-04-09 21:38:19','/category/list.jhtm',1,1),
 (847,'127.0.0.1','类别管理','2009-04-09 21:39:39','/category/list.jhtm',1,1),
 (848,'127.0.0.1','类别管理','2009-04-09 21:40:16','/category/list.jhtm',1,1),
 (849,'127.0.0.1','成功登录系统','2009-04-09 21:44:36','/login.jsp',1,1),
 (850,'127.0.0.1','类别管理','2009-04-09 21:45:49','/category/list.jhtm',1,1),
 (851,'127.0.0.1','类别管理','2009-04-09 21:48:02','/category/list.jhtm',1,1),
 (852,'127.0.0.1','类别管理','2009-04-09 21:51:01','/category/list.jhtm',1,1),
 (853,'127.0.0.1','成功登录系统','2009-04-09 21:56:45','/login.jsp',1,1),
 (854,'127.0.0.1','客户设置','2009-04-09 21:56:56','/customer/config.jhtm',1,1),
 (855,'127.0.0.1','类别管理','2009-04-09 21:57:04','/category/list.jhtm',1,1),
 (856,'127.0.0.1','成功登录系统','2009-04-09 22:00:06','/login.jsp',1,1),
 (857,'127.0.0.1','类别管理','2009-04-09 22:00:17','/category/list.jhtm',1,1),
 (858,'127.0.0.1','类别管理','2009-04-09 22:00:57','/category/list.jhtm',1,1),
 (859,'127.0.0.1','类别管理','2009-04-09 22:02:49','/category/list.jhtm',1,1),
 (860,'127.0.0.1','类别管理','2009-04-09 22:03:18','/category/list.jhtm',1,1),
 (861,'127.0.0.1','成功登录系统','2009-04-09 22:16:01','/login.jsp',1,1),
 (862,'127.0.0.1','类别管理','2009-04-09 22:16:11','/category/list.jhtm',1,1),
 (863,'127.0.0.1','类别管理','2009-04-09 22:19:05','/category/list.jhtm',1,1),
 (864,'127.0.0.1','品牌管理','2009-04-09 22:19:23','/brand/list.jhtm',1,1),
 (865,'127.0.0.1','类别管理','2009-04-09 22:20:54','/category/list.jhtm',1,1),
 (866,'127.0.0.1','类别管理','2009-04-09 22:21:18','/category/list.jhtm',1,1),
 (867,'127.0.0.1','类别管理','2009-04-09 22:21:39','/category/list.jhtm',1,1),
 (868,'127.0.0.1','类别管理','2009-04-09 22:21:52','/category/list.jhtm',1,1),
 (869,'127.0.0.1','类别管理','2009-04-09 22:22:15','/category/list.jhtm',1,1),
 (870,'127.0.0.1','类别管理','2009-04-09 22:35:23','/category/list.jhtm',1,1),
 (871,'127.0.0.1','类别管理','2009-04-09 22:37:24','/category/list.jhtm',1,1),
 (872,'127.0.0.1','类别管理','2009-04-09 22:39:34','/category/list.jhtm',1,1),
 (873,'127.0.0.1','类别管理','2009-04-09 22:40:20','/category/list.jhtm',1,1),
 (874,'127.0.0.1','类别管理','2009-04-09 22:42:11','/category/list.jhtm',1,1),
 (875,'127.0.0.1','类别管理','2009-04-09 22:48:49','/category/list.jhtm',1,1),
 (876,'127.0.0.1','类别管理','2009-04-09 22:50:06','/category/list.jhtm',1,1),
 (877,'127.0.0.1','类别管理','2009-04-09 22:50:43','/category/list.jhtm',1,1),
 (878,'127.0.0.1','类别管理','2009-04-09 22:51:16','/category/list.jhtm',1,1),
 (879,'127.0.0.1','类别管理','2009-04-09 22:51:51','/category/list.jhtm',1,1),
 (880,'127.0.0.1','类别管理','2009-04-09 22:54:04','/category/list.jhtm',1,1),
 (881,'127.0.0.1','类别管理','2009-04-09 22:54:25','/category/list.jhtm',1,1),
 (882,'127.0.0.1','类别管理','2009-04-09 22:56:21','/category/list.jhtm',1,1),
 (883,'127.0.0.1','类别管理','2009-04-09 22:57:14','/category/list.jhtm',1,1),
 (884,'127.0.0.1','类别管理','2009-04-09 22:58:39','/category/list.jhtm',1,1),
 (885,'127.0.0.1','类别管理','2009-04-09 22:59:34','/category/list.jhtm',1,1),
 (886,'127.0.0.1','类别管理','2009-04-09 23:03:30','/category/list.jhtm',1,1),
 (887,'127.0.0.1','类别管理','2009-04-09 23:04:55','/category/list.jhtm',1,1),
 (888,'127.0.0.1','类别管理','2009-04-09 23:05:46','/category/list.jhtm',1,1),
 (889,'127.0.0.1','类别管理','2009-04-09 23:06:32','/category/list.jhtm',1,1),
 (890,'127.0.0.1','类别管理','2009-04-09 23:06:45','/category/list.jhtm',1,1),
 (891,'127.0.0.1','类别管理','2009-04-09 23:06:58','/category/list.jhtm',1,1),
 (892,'127.0.0.1','类别管理','2009-04-09 23:07:11','/category/list.jhtm',1,1),
 (893,'127.0.0.1','类别管理','2009-04-09 23:07:17','/category/list.jhtm',1,1),
 (894,'127.0.0.1','成功登录系统','2009-04-12 14:20:00','/login.jsp',1,1),
 (895,'127.0.0.1','成功登录系统','2009-04-12 14:27:36','/login.jsp',1,1),
 (896,'127.0.0.1','缓存更新','2009-04-12 14:28:52','/cache/list.jhtm',1,1),
 (897,'127.0.0.1','缓存更新','2009-04-12 14:28:59','/cache/list.jhtm',1,1),
 (898,'127.0.0.1','店铺管理','2009-04-12 14:29:10','/shop/list.jhtm',1,1),
 (899,'127.0.0.1','成功登录系统','2009-04-12 15:20:09','/login.jsp',1,1),
 (900,'127.0.0.1','店铺管理','2009-04-12 15:20:18','/shop/list.jhtm',1,1),
 (901,'127.0.0.1','成功登录系统','2009-04-12 15:22:08','/login.jsp',1,1),
 (902,'127.0.0.1','店铺管理','2009-04-12 15:22:17','/shop/list.jhtm',1,1),
 (903,'127.0.0.1','店铺管理','2009-04-12 15:23:09','/shop/list.jhtm',1,1),
 (904,'127.0.0.1','店铺管理','2009-04-12 15:23:40','/shop/list.jhtm',1,1),
 (905,'127.0.0.1','成功登录系统','2009-04-12 15:36:01','/login.jsp',1,1),
 (906,'127.0.0.1','成功登录系统','2009-04-12 15:53:35','/login.jsp',1,1),
 (907,'127.0.0.1','成功登录系统','2009-04-12 15:55:31','/login.jsp',1,1),
 (908,'127.0.0.1','店铺管理','2009-04-12 15:55:39','/shop/list.jhtm',1,1),
 (909,'127.0.0.1','成功登录系统','2009-04-12 17:03:35','/login.jsp',1,1),
 (910,'127.0.0.1','店铺管理','2009-04-12 17:03:44','/shop/list.jhtm',1,1),
 (911,'127.0.0.1','成功登录系统','2009-04-12 17:06:35','/login.jsp',1,1),
 (912,'127.0.0.1','店铺管理','2009-04-12 17:06:43','/shop/list.jhtm',1,1),
 (913,'127.0.0.1','成功登录系统','2009-04-12 17:12:07','/login.jsp',1,1),
 (914,'127.0.0.1','店铺管理','2009-04-12 17:12:35','/shop/list.jhtm',1,1),
 (915,'127.0.0.1','店铺管理','2009-04-12 17:13:05','/shop/list.jhtm',1,1),
 (916,'127.0.0.1','店铺管理','2009-04-12 17:13:31','/shop/list.jhtm',1,1),
 (917,'127.0.0.1','店铺管理','2009-04-12 17:24:10','/shop/list.jhtm',1,1),
 (918,'127.0.0.1','店铺管理','2009-04-12 17:25:07','/shop/list.jhtm',1,1),
 (919,'127.0.0.1','店铺管理','2009-04-12 17:25:14','/shop/list.jhtm',1,1),
 (920,'127.0.0.1','成功登录系统','2009-04-12 18:32:05','/login.jsp',1,1),
 (921,'127.0.0.1','店铺管理','2009-04-12 18:32:15','/shop/list.jhtm',1,1),
 (922,'127.0.0.1','成功登录系统','2009-04-12 18:37:33','/login.jsp',1,1),
 (923,'127.0.0.1','店铺管理','2009-04-12 18:37:41','/shop/list.jhtm',1,1),
 (924,'127.0.0.1','成功登录系统','2009-04-12 18:40:47','/login.jsp',1,1),
 (925,'127.0.0.1','店铺管理','2009-04-12 18:41:08','/shop/list.jhtm',1,1),
 (926,'127.0.0.1','店铺管理','2009-04-12 18:45:09','/shop/list.jhtm',1,1),
 (927,'127.0.0.1','店铺管理','2009-04-12 18:45:32','/shop/list.jhtm',1,1),
 (928,'127.0.0.1','店铺管理','2009-04-12 18:45:55','/shop/list.jhtm',1,1),
 (929,'127.0.0.1','成功登录系统','2009-04-13 21:01:58','/login.jsp',1,1),
 (930,'127.0.0.1','店铺管理','2009-04-13 21:02:25','/shop/list.jhtm',1,1),
 (931,NULL,'成功退出系统','2009-04-13 21:23:24','/logout.jsp',NULL,1),
 (932,'127.0.0.1','成功登录系统','2009-04-14 10:22:17','/login.jsp',1,1),
 (933,'127.0.0.1','配送地区','2009-04-14 10:22:37','/area/list.jhtm',1,1),
 (934,'127.0.0.1','地区分组','2009-04-14 10:22:39','/areatype/list.jhtm',1,1),
 (935,'127.0.0.1','货币管理','2009-04-14 10:22:41','/currency/list.jhtm',1,1),
 (936,'127.0.0.1','供应商信息','2009-04-14 10:22:43','/supplier/list.jhtm',1,1),
 (937,'127.0.0.1','品牌管理','2009-04-14 10:22:45','/brand/list.jhtm',1,1),
 (938,'127.0.0.1','商品设置','2009-04-14 10:22:47','/productconfig/list.jhtm',1,1),
 (939,'127.0.0.1','货币管理','2009-04-14 10:22:57','/currency/list.jhtm',1,1),
 (940,'127.0.0.1','供应商信息','2009-04-14 10:22:59','/supplier/list.jhtm',1,1),
 (941,'127.0.0.1','货币管理','2009-04-14 10:23:03','/currency/list.jhtm',1,1),
 (942,'127.0.0.1','品牌管理','2009-04-14 10:23:07','/brand/list.jhtm',1,1),
 (943,'127.0.0.1','商品设置','2009-04-14 10:23:11','/productconfig/list.jhtm',1,1),
 (944,'127.0.0.1','客户设置','2009-04-14 10:23:13','/customer/config.jhtm',1,1),
 (945,'127.0.0.1','价格模板','2009-04-14 10:23:15','/customer/price_template.jhtm',1,1),
 (946,'127.0.0.1','会员等级管理','2009-04-14 10:23:16','/memberlevel/list.jhtm',1,1),
 (947,'127.0.0.1','会员等级管理','2009-04-14 10:24:11','/memberlevel/list.jhtm',1,1),
 (948,'127.0.0.1','会员等级管理','2009-04-14 10:24:52','/memberlevel/list.jhtm',1,1),
 (949,'127.0.0.1','会员等级管理','2009-04-14 10:25:11','/memberlevel/list.jhtm',1,1),
 (950,'127.0.0.1','会员等级管理','2009-04-14 10:26:04','/memberlevel/list.jhtm',1,1),
 (951,'127.0.0.1','会员等级管理','2009-04-14 10:26:44','/memberlevel/list.jhtm',1,1),
 (952,'127.0.0.1','类别管理','2009-04-14 10:27:02','/category/list.jhtm',1,1),
 (953,'127.0.0.1','类别管理','2009-04-14 10:27:08','/category/list.jhtm',1,1),
 (954,'127.0.0.1','类别管理','2009-04-14 10:27:18','/category/list.jhtm',1,1),
 (955,'127.0.0.1','类别管理','2009-04-14 10:27:29','/category/list.jhtm',1,1),
 (956,'127.0.0.1','类别管理','2009-04-14 10:31:05','/category/list.jhtm',1,1),
 (957,'127.0.0.1','类别管理','2009-04-14 10:31:16','/category/list.jhtm',1,1),
 (958,'127.0.0.1','成功登录系统','2009-04-14 11:03:11','/login.jsp',1,1),
 (959,'127.0.0.1','类别管理','2009-04-14 11:03:22','/category/list.jhtm',1,1),
 (960,'127.0.0.1','成功登录系统','2009-04-14 11:14:22','/login.jsp',1,1),
 (961,'127.0.0.1','类别管理','2009-04-14 11:14:31','/category/list.jhtm',1,1),
 (962,'127.0.0.1','成功登录系统','2009-04-14 11:35:36','/login.jsp',1,1),
 (963,'127.0.0.1','类别管理','2009-04-14 11:35:47','/category/list.jhtm',1,1),
 (964,'127.0.0.1','成功登录系统','2009-04-14 11:41:01','/login.jsp',1,1),
 (965,'127.0.0.1','类别管理','2009-04-14 11:41:12','/category/list.jhtm',1,1),
 (966,'127.0.0.1','成功登录系统','2009-04-14 11:46:40','/login.jsp',1,1),
 (967,'127.0.0.1','类别管理','2009-04-14 11:48:15','/category/list.jhtm',1,1),
 (968,'127.0.0.1','成功登录系统','2009-04-14 12:06:24','/login.jsp',1,1),
 (969,'127.0.0.1','类别管理','2009-04-14 12:06:32','/category/list.jhtm',1,1),
 (970,'127.0.0.1','成功登录系统','2009-04-14 12:08:30','/login.jsp',1,1),
 (971,'127.0.0.1','类别管理','2009-04-14 12:08:40','/category/list.jhtm',1,1),
 (972,'127.0.0.1','成功登录系统','2009-04-14 12:16:47','/login.jsp',1,1),
 (973,'127.0.0.1','类别管理','2009-04-14 12:16:53','/category/list.jhtm',1,1),
 (974,'127.0.0.1','成功登录系统','2009-04-14 14:30:05','/login.jsp',1,1),
 (975,'127.0.0.1','商品管理','2009-04-14 14:30:34','/product/list.jhtm',1,1),
 (976,'127.0.0.1','类别管理','2009-04-14 14:30:41','/category/list.jhtm',1,1),
 (977,'127.0.0.1','商品类型','2009-04-14 14:30:45','/producttype/list.jhtm',1,1),
 (978,'127.0.0.1','成功登录系统','2009-04-14 14:35:05','/login.jsp',1,1),
 (979,'127.0.0.1','商品类型','2009-04-14 14:35:23','/producttype/list.jhtm',1,1),
 (980,'127.0.0.1','成功登录系统','2009-04-14 14:44:31','/login.jsp',1,1),
 (981,'127.0.0.1','商品类型','2009-04-14 14:44:49','/producttype/list.jhtm',1,1),
 (982,'127.0.0.1','商品类型','2009-04-14 14:46:48','/producttype/list.jhtm',1,1),
 (983,'127.0.0.1','商品类型','2009-04-14 14:47:30','/producttype/list.jhtm',1,1),
 (984,'127.0.0.1','商品类型','2009-04-14 14:48:00','/producttype/list.jhtm',1,1),
 (985,'127.0.0.1','商品类型','2009-04-14 14:48:35','/producttype/list.jhtm',1,1),
 (986,'127.0.0.1','商品类型','2009-04-14 14:49:01','/producttype/list.jhtm',1,1),
 (987,'127.0.0.1','成功登录系统','2009-04-14 14:57:53','/login.jsp',1,1),
 (988,'127.0.0.1','商品类型','2009-04-14 14:58:01','/producttype/list.jhtm',1,1),
 (989,'127.0.0.1','商品类型','2009-04-14 14:58:39','/producttype/list.jhtm',1,1),
 (990,'127.0.0.1','商品类型','2009-04-14 14:58:45','/producttype/list.jhtm',1,1),
 (991,'127.0.0.1','商品类型','2009-04-14 15:01:56','/producttype/list.jhtm',1,1),
 (992,'127.0.0.1','商品类型','2009-04-14 15:02:17','/producttype/list.jhtm',1,1),
 (993,'127.0.0.1','类别管理','2009-04-14 15:04:03','/category/list.jhtm',1,1),
 (994,'127.0.0.1','商品类型','2009-04-14 15:04:03','/producttype/list.jhtm',1,1),
 (995,'127.0.0.1','商品类型','2009-04-14 15:04:16','/producttype/list.jhtm',1,1),
 (996,'127.0.0.1','商品类型','2009-04-14 15:04:33','/producttype/list.jhtm',1,1),
 (997,'127.0.0.1','商品管理','2009-04-14 15:04:44','/product/list.jhtm',1,1),
 (998,'127.0.0.1','成功登录系统','2009-04-14 15:30:57','/login.jsp',1,1),
 (999,'127.0.0.1','商品管理','2009-04-14 15:31:08','/product/list.jhtm',1,1),
 (1000,'127.0.0.1','成功登录系统','2009-04-14 15:43:18','/login.jsp',1,1),
 (1001,'127.0.0.1','商品管理','2009-04-14 15:43:27','/product/list.jhtm',1,1),
 (1002,'127.0.0.1','上架新商品','2009-04-14 15:43:33','/product/add.jhtm',1,1),
 (1003,'127.0.0.1','商品管理','2009-04-14 15:46:33','/product/list.jhtm',1,1),
 (1004,'127.0.0.1','上架新商品','2009-04-14 15:46:35','/product/add.jhtm',1,1),
 (1005,'127.0.0.1','商品类型','2009-04-14 15:48:15','/producttype/list.jhtm',1,1),
 (1006,'127.0.0.1','商品类型','2009-04-14 15:48:30','/producttype/list.jhtm',1,1),
 (1007,'127.0.0.1','商品管理','2009-04-14 15:48:39','/product/list.jhtm',1,1),
 (1008,'127.0.0.1','上架新商品','2009-04-14 15:48:41','/product/add.jhtm',1,1),
 (1009,'127.0.0.1','商品类型','2009-04-14 15:49:57','/producttype/list.jhtm',1,1),
 (1010,'127.0.0.1','商品管理','2009-04-14 15:50:00','/product/list.jhtm',1,1),
 (1011,'127.0.0.1','上架新商品','2009-04-14 15:50:02','/product/add.jhtm',1,1),
 (1012,'127.0.0.1','商品类型','2009-04-14 15:50:53','/producttype/list.jhtm',1,1),
 (1013,'127.0.0.1','商品管理','2009-04-14 15:51:03','/product/list.jhtm',1,1),
 (1014,'127.0.0.1','上架新商品','2009-04-14 15:51:05','/product/add.jhtm',1,1),
 (1015,'127.0.0.1','商品类型','2009-04-14 15:52:05','/producttype/list.jhtm',1,1),
 (1016,'127.0.0.1','商品管理','2009-04-14 15:52:08','/product/list.jhtm',1,1),
 (1017,'127.0.0.1','上架新商品','2009-04-14 15:52:10','/product/add.jhtm',1,1),
 (1018,'127.0.0.1','商品类型','2009-04-14 15:52:30','/producttype/list.jhtm',1,1),
 (1019,'127.0.0.1','商品管理','2009-04-14 15:53:29','/product/list.jhtm',1,1),
 (1020,'127.0.0.1','商品类型','2009-04-14 15:57:57','/producttype/list.jhtm',1,1),
 (1021,'127.0.0.1','商品类型','2009-04-14 15:58:25','/producttype/list.jhtm',1,1),
 (1022,'127.0.0.1','商品管理','2009-04-14 15:58:30','/product/list.jhtm',1,1),
 (1023,'127.0.0.1','上架新商品','2009-04-14 15:58:42','/product/add.jhtm',1,1),
 (1024,'127.0.0.1','商品管理','2009-04-14 16:01:50','/product/list.jhtm',1,1),
 (1025,'127.0.0.1','上架新商品','2009-04-14 16:02:29','/product/add.jhtm',1,1),
 (1026,'127.0.0.1','商品管理','2009-04-14 16:03:28','/product/list.jhtm',1,1),
 (1027,'127.0.0.1','上架新商品','2009-04-14 16:03:30','/product/add.jhtm',1,1),
 (1028,'127.0.0.1','商品管理','2009-04-14 16:04:55','/product/list.jhtm',1,1),
 (1029,'127.0.0.1','上架新商品','2009-04-14 16:05:04','/product/add.jhtm',1,1),
 (1030,'127.0.0.1','成功登录系统','2009-04-14 16:12:19','/login.jsp',1,1),
 (1031,'127.0.0.1','商品管理','2009-04-14 16:12:33','/product/list.jhtm',1,1),
 (1032,'127.0.0.1','商品管理','2009-04-14 16:14:01','/product/list.jhtm',1,1),
 (1033,'127.0.0.1','商品管理','2009-04-14 16:16:33','/product/list.jhtm',1,1),
 (1034,'127.0.0.1','商品类型','2009-04-14 16:16:35','/producttype/list.jhtm',1,1),
 (1035,'127.0.0.1','商品管理','2009-04-14 16:16:36','/product/list.jhtm',1,1),
 (1036,'127.0.0.1','上架新商品','2009-04-14 16:16:39','/product/add.jhtm',1,1),
 (1037,'127.0.0.1','商品管理','2009-04-14 16:17:54','/product/list.jhtm',1,1),
 (1038,'127.0.0.1','上架新商品','2009-04-14 16:17:58','/product/add.jhtm',1,1),
 (1039,'127.0.0.1','商品管理','2009-04-14 16:18:33','/product/list.jhtm',1,1),
 (1040,'127.0.0.1','商品管理','2009-04-14 16:19:11','/product/list.jhtm',1,1),
 (1041,'127.0.0.1','商品管理','2009-04-14 16:26:10','/product/list.jhtm',1,1),
 (1042,'127.0.0.1','商品管理','2009-04-14 16:26:29','/product/list.jhtm',1,1),
 (1043,'127.0.0.1','商品管理','2009-04-14 16:26:45','/product/list.jhtm',1,1),
 (1044,'127.0.0.1','商品管理','2009-04-14 16:43:51','/product/list.jhtm',1,1),
 (1045,'127.0.0.1','商品管理','2009-04-14 16:44:45','/product/list.jhtm',1,1),
 (1046,'127.0.0.1','商品管理','2009-04-14 16:44:52','/product/list.jhtm',1,1),
 (1047,'127.0.0.1','成功登录系统','2009-04-14 16:51:49','/login.jsp',1,1),
 (1048,'127.0.0.1','商品管理','2009-04-14 16:52:06','/product/list.jhtm',1,1),
 (1049,'127.0.0.1','商品管理','2009-04-14 16:54:07','/product/list.jhtm',1,1),
 (1050,'127.0.0.1','上架新商品','2009-04-14 16:54:13','/product/add.jhtm',1,1),
 (1051,'127.0.0.1','货币管理','2009-04-14 16:56:43','/currency/list.jhtm',1,1),
 (1052,'127.0.0.1','商品管理','2009-04-14 16:57:00','/product/list.jhtm',1,1),
 (1053,'127.0.0.1','上架新商品','2009-04-14 16:58:44','/product/add.jhtm',1,1),
 (1054,'127.0.0.1','商品管理','2009-04-14 16:59:33','/product/list.jhtm',1,1),
 (1055,'127.0.0.1','上架新商品','2009-04-14 16:59:36','/product/add.jhtm',1,1),
 (1056,'127.0.0.1','商品管理','2009-04-14 17:00:26','/product/list.jhtm',1,1),
 (1057,'127.0.0.1','上架新商品','2009-04-14 17:00:28','/product/add.jhtm',1,1),
 (1058,'127.0.0.1','商品管理','2009-04-14 17:01:26','/product/list.jhtm',1,1),
 (1059,'127.0.0.1','上架新商品','2009-04-14 17:01:29','/product/add.jhtm',1,1),
 (1060,'127.0.0.1','商品管理','2009-04-14 17:01:32','/product/list.jhtm',1,1),
 (1061,'127.0.0.1','商品管理','2009-04-14 17:02:14','/product/list.jhtm',1,1),
 (1062,'127.0.0.1','商品管理','2009-04-14 17:02:47','/product/list.jhtm',1,1),
 (1063,'127.0.0.1','上架新商品','2009-04-14 17:04:08','/product/add.jhtm',1,1),
 (1064,'127.0.0.1','商品管理','2009-04-14 17:04:13','/product/list.jhtm',1,1),
 (1065,'127.0.0.1','上架新商品','2009-04-14 17:04:30','/product/add.jhtm',1,1),
 (1066,'127.0.0.1','商品管理','2009-04-14 17:04:33','/product/list.jhtm',1,1),
 (1067,'127.0.0.1','商品管理','2009-04-14 17:04:49','/product/list.jhtm',1,1),
 (1068,'127.0.0.1','商品管理','2009-04-14 17:05:04','/product/list.jhtm',1,1),
 (1069,'127.0.0.1','商品管理','2009-04-14 17:10:55','/product/list.jhtm',1,1),
 (1070,'127.0.0.1','上架新商品','2009-04-14 17:10:58','/product/add.jhtm',1,1),
 (1071,'127.0.0.1','商品管理','2009-04-14 17:11:31','/product/list.jhtm',1,1),
 (1072,'127.0.0.1','上架新商品','2009-04-14 17:11:37','/product/add.jhtm',1,1),
 (1073,'127.0.0.1','商品管理','2009-04-14 17:12:10','/product/list.jhtm',1,1),
 (1074,'127.0.0.1','上架新商品','2009-04-14 17:12:11','/product/add.jhtm',1,1),
 (1075,'127.0.0.1','商品管理','2009-04-14 17:12:49','/product/list.jhtm',1,1),
 (1076,'127.0.0.1','上架新商品','2009-04-14 17:12:54','/product/add.jhtm',1,1),
 (1077,'127.0.0.1','商品管理','2009-04-14 17:13:29','/product/list.jhtm',1,1),
 (1078,'127.0.0.1','上架新商品','2009-04-14 17:13:35','/product/add.jhtm',1,1),
 (1079,'127.0.0.1','商品管理','2009-04-14 17:14:18','/product/list.jhtm',1,1),
 (1080,'127.0.0.1','上架新商品','2009-04-14 17:14:24','/product/add.jhtm',1,1),
 (1081,'127.0.0.1','商品管理','2009-04-14 17:15:56','/product/list.jhtm',1,1),
 (1082,'127.0.0.1','上架新商品','2009-04-14 17:15:58','/product/add.jhtm',1,1),
 (1083,'127.0.0.1','商品管理','2009-04-14 17:17:09','/product/list.jhtm',1,1),
 (1084,'127.0.0.1','商品管理','2009-04-14 17:17:15','/product/list.jhtm',1,1),
 (1085,'127.0.0.1','类别管理','2009-04-14 17:22:50','/category/list.jhtm',1,1),
 (1086,'127.0.0.1','商品管理','2009-04-14 17:22:53','/product/list.jhtm',1,1),
 (1087,'127.0.0.1','上架新商品','2009-04-14 17:22:55','/product/add.jhtm',1,1),
 (1088,'127.0.0.1','商品管理','2009-04-14 17:23:04','/product/list.jhtm',1,1),
 (1089,'127.0.0.1','上架新商品','2009-04-14 17:23:15','/product/add.jhtm',1,1),
 (1090,'127.0.0.1','商品管理','2009-04-14 17:23:17','/product/list.jhtm',1,1),
 (1091,'127.0.0.1','商品管理','2009-04-14 17:23:57','/product/list.jhtm',1,1),
 (1092,'127.0.0.1','商品管理','2009-04-14 17:26:21','/product/list.jhtm',1,1),
 (1093,'127.0.0.1','商品管理','2009-04-14 17:26:21','/product/list.jhtm',1,1),
 (1094,'127.0.0.1','商品管理','2009-04-14 17:28:14','/product/list.jhtm',1,1),
 (1095,'127.0.0.1','缓存更新','2009-04-14 17:29:34','/cache/list.jhtm',1,1),
 (1096,'127.0.0.1','缓存更新','2009-04-14 17:29:38','/cache/list.jhtm',1,1),
 (1097,'127.0.0.1','商品管理','2009-04-14 17:34:23','/product/list.jhtm',1,1),
 (1098,'127.0.0.1','上架新商品','2009-04-14 17:34:34','/product/add.jhtm',1,1),
 (1099,'127.0.0.1','商品管理','2009-04-14 17:34:37','/product/list.jhtm',1,1),
 (1100,'127.0.0.1','商品管理','2009-04-14 17:34:44','/product/list.jhtm',1,1),
 (1101,'127.0.0.1','上架新商品','2009-04-14 17:34:55','/product/add.jhtm',1,1),
 (1102,'127.0.0.1','上架新商品','2009-04-14 17:34:56','/product/add.jhtm',1,1),
 (1103,'127.0.0.1','商品管理','2009-04-14 17:34:58','/product/list.jhtm',1,1),
 (1104,'127.0.0.1','商品管理','2009-04-14 17:35:58','/product/list.jhtm',1,1),
 (1105,'127.0.0.1','商品管理','2009-04-14 17:36:26','/product/list.jhtm',1,1),
 (1106,'127.0.0.1','商品管理','2009-04-14 17:36:56','/product/list.jhtm',1,1),
 (1107,'127.0.0.1','上架新商品','2009-04-14 17:37:27','/product/add.jhtm',1,1),
 (1108,'127.0.0.1','商品管理','2009-04-14 17:37:30','/product/list.jhtm',1,1),
 (1109,'127.0.0.1','商品管理','2009-04-14 17:37:38','/product/list.jhtm',1,1),
 (1110,'127.0.0.1','商品管理','2009-04-14 17:37:43','/product/list.jhtm',1,1),
 (1111,'127.0.0.1','商品管理','2009-04-14 17:39:34','/product/list.jhtm',1,1),
 (1112,'127.0.0.1','上架新商品','2009-04-14 17:39:48','/product/add.jhtm',1,1),
 (1113,'127.0.0.1','商品管理','2009-04-14 17:39:50','/product/list.jhtm',1,1),
 (1114,'127.0.0.1','商品管理','2009-04-14 17:39:56','/product/list.jhtm',1,1);
INSERT INTO `p_logs` (`log_id`,`ip`,`operation`,`time`,`url`,`shop_id`,`user_id`) VALUES 
 (1115,'127.0.0.1','商品管理','2009-04-14 17:40:02','/product/list.jhtm',1,1),
 (1116,'127.0.0.1','上架新商品','2009-04-14 17:41:14','/product/add.jhtm',1,1),
 (1117,'127.0.0.1','商品管理','2009-04-14 17:41:16','/product/list.jhtm',1,1),
 (1118,'127.0.0.1','商品管理','2009-04-14 17:41:27','/product/list.jhtm',1,1),
 (1119,'127.0.0.1','商品管理','2009-04-14 17:41:34','/product/list.jhtm',1,1),
 (1120,'127.0.0.1','商品管理','2009-04-14 17:41:41','/product/list.jhtm',1,1),
 (1121,'127.0.0.1','成功登录系统','2009-04-14 18:00:15','/login.jsp',1,1),
 (1122,'127.0.0.1','商品管理','2009-04-14 18:00:24','/product/list.jhtm',1,1),
 (1123,'127.0.0.1','商品管理','2009-04-14 18:01:00','/product/list.jhtm',1,1),
 (1124,'127.0.0.1','成功登录系统','2009-04-14 18:05:39','/login.jsp',1,1),
 (1125,'127.0.0.1','商品管理','2009-04-14 18:05:52','/product/list.jhtm',1,1),
 (1126,'127.0.0.1','商品管理','2009-04-14 18:06:01','/product/list.jhtm',1,1),
 (1127,'127.0.0.1','商品管理','2009-04-14 18:06:42','/product/list.jhtm',1,1),
 (1128,'127.0.0.1','商品管理','2009-04-14 18:06:48','/product/list.jhtm',1,1),
 (1129,'127.0.0.1','商品管理','2009-04-14 18:06:54','/product/list.jhtm',1,1),
 (1130,'127.0.0.1','商品管理','2009-04-14 18:06:59','/product/list.jhtm',1,1),
 (1131,'127.0.0.1','商品管理','2009-04-14 18:11:52','/product/list.jhtm',1,1),
 (1132,'127.0.0.1','上架新商品','2009-04-14 18:12:00','/product/add.jhtm',1,1),
 (1133,'127.0.0.1','成功登录系统','2009-04-14 18:22:40','/login.jsp',1,1),
 (1134,'127.0.0.1','商品管理','2009-04-14 18:23:10','/product/list.jhtm',1,1),
 (1135,'127.0.0.1','商品管理','2009-04-14 18:24:08','/product/list.jhtm',1,1),
 (1136,'127.0.0.1','商品类型','2009-04-14 18:24:58','/producttype/list.jhtm',1,1),
 (1137,'127.0.0.1','类别管理','2009-04-14 18:24:59','/category/list.jhtm',1,1),
 (1138,'127.0.0.1','商品管理','2009-04-14 18:25:01','/product/list.jhtm',1,1),
 (1139,'127.0.0.1','上架新商品','2009-04-14 18:25:08','/product/add.jhtm',1,1),
 (1140,'127.0.0.1','缓存更新','2009-04-14 18:25:14','/cache/list.jhtm',1,1),
 (1141,'127.0.0.1','缓存更新','2009-04-14 18:25:16','/cache/list.jhtm',1,1),
 (1142,'127.0.0.1','缓存更新','2009-04-14 18:26:07','/cache/list.jhtm',1,1),
 (1143,'127.0.0.1','缓存更新','2009-04-14 18:26:10','/cache/list.jhtm',1,1),
 (1144,'127.0.0.1','商品管理','2009-04-14 18:26:13','/product/list.jhtm',1,1),
 (1145,'127.0.0.1','上架新商品','2009-04-14 18:26:16','/product/add.jhtm',1,1),
 (1146,'127.0.0.1','商品咨询管理','2009-04-14 18:26:19','/product/consultation.jhtm',1,1),
 (1147,'127.0.0.1','上架新商品','2009-04-14 18:26:21','/product/add.jhtm',1,1),
 (1148,'127.0.0.1','商品管理','2009-04-14 18:26:35','/product/list.jhtm',1,1),
 (1149,'127.0.0.1','店铺管理','2009-04-14 18:26:57','/shop/list.jhtm',1,1),
 (1150,'127.0.0.1','配送地区','2009-04-14 18:27:45','/area/list.jhtm',1,1),
 (1151,'127.0.0.1','地区分组','2009-04-14 18:27:47','/areatype/list.jhtm',1,1),
 (1152,'127.0.0.1','货币管理','2009-04-14 18:27:48','/currency/list.jhtm',1,1),
 (1153,'127.0.0.1','供应商信息','2009-04-14 18:27:50','/supplier/list.jhtm',1,1),
 (1154,'127.0.0.1','品牌管理','2009-04-14 18:27:51','/brand/list.jhtm',1,1),
 (1155,'127.0.0.1','商品设置','2009-04-14 18:27:52','/productconfig/list.jhtm',1,1),
 (1156,'127.0.0.1','客户设置','2009-04-14 18:27:54','/customer/config.jhtm',1,1),
 (1157,'127.0.0.1','价格模板','2009-04-14 18:27:55','/customer/price_template.jhtm',1,1),
 (1158,'127.0.0.1','会员等级管理','2009-04-14 18:27:57','/memberlevel/list.jhtm',1,1),
 (1159,'127.0.0.1','代理等级管理','2009-04-14 18:27:58','/agentlevel/list.jhtm',1,1),
 (1160,'127.0.0.1','成功登录系统','2009-04-16 14:49:32','/login.jsp',1,1),
 (1161,'127.0.0.1','类别管理','2009-04-16 14:50:28','/category/list.jhtm',1,1),
 (1162,'127.0.0.1','商品类型','2009-04-16 14:50:32','/producttype/list.jhtm',1,1),
 (1163,'127.0.0.1','商品管理','2009-04-16 14:50:34','/product/list.jhtm',1,1),
 (1164,'127.0.0.1','上架新商品','2009-04-16 14:50:37','/product/add.jhtm',1,1),
 (1165,'127.0.0.1','成功登录系统','2009-04-17 10:48:28','/login.jsp',1,1),
 (1166,'127.0.0.1','成功登录系统','2009-04-17 11:44:42','/login.jsp',1,1),
 (1167,'127.0.0.1','商品管理','2009-04-17 11:45:20','/product/list.jhtm',1,1),
 (1168,'127.0.0.1','商品管理','2009-04-17 11:45:30','/product/list.jhtm',1,1),
 (1169,'127.0.0.1','成功登录系统','2009-04-17 14:49:38','/login.jsp',1,1),
 (1170,'127.0.0.1','成功退出系统','2009-04-17 14:50:03','/logout.jsp',1,1),
 (1171,'127.0.0.1','成功登录系统','2009-04-17 14:52:24','/login.jsp',1,1),
 (1172,'127.0.0.1','缓存更新','2009-04-17 14:52:30','/cache/list.jhtm',1,1),
 (1173,'127.0.0.1','缓存更新','2009-04-17 14:52:32','/cache/list.jhtm',1,1),
 (1174,'127.0.0.1','成功退出系统','2009-04-17 14:52:35','/logout.jsp',1,1),
 (1175,'127.0.0.1','成功登录系统','2009-04-17 14:52:41','/login.jsp',1,2),
 (1176,'127.0.0.1','成功退出系统','2009-04-17 14:52:48','/logout.jsp',1,2),
 (1177,'127.0.0.1','成功登录系统','2009-04-17 14:52:56','/login.jsp',1,2),
 (1178,'127.0.0.1','缓存更新','2009-04-17 14:53:01','/cache/list.jhtm',1,2),
 (1179,'127.0.0.1','缓存更新','2009-04-17 14:53:03','/cache/list.jhtm',1,2),
 (1180,'127.0.0.1','成功退出系统','2009-04-17 14:53:05','/logout.jsp',1,2),
 (1181,'127.0.0.1','成功登录系统','2009-04-17 14:53:40','/login.jsp',1,2),
 (1182,'127.0.0.1','成功退出系统','2009-04-17 14:53:49','/logout.jsp',1,2),
 (1183,'127.0.0.1','成功登录系统','2009-04-19 20:28:13','/login.jsp',1,2),
 (1184,'127.0.0.1','成功登录系统','2009-04-20 14:51:46','/login.jsp',1,1),
 (1185,'127.0.0.1','成功退出系统','2009-04-20 14:51:52','/logout.jsp',1,1),
 (1186,'127.0.0.1','成功登录系统','2009-04-20 14:51:58','/login.jsp',1,1),
 (1187,'127.0.0.1','货币管理','2009-04-20 14:52:10','/currency/list.jhtm',1,1),
 (1188,'127.0.0.1','成功登录系统','2009-04-27 20:58:44','/login.jsp',1,1),
 (1189,NULL,'成功退出系统','2009-04-27 21:19:18','/logout.jsp',NULL,1),
 (1190,'127.0.0.1','成功登录系统','2009-04-29 11:31:04','/login.jsp',1,1),
 (1191,'127.0.0.1','成功登录系统','2009-04-29 11:34:07','/login.jsp',1,1),
 (1192,'127.0.0.1','系统状态','2009-04-29 11:34:16','/system/status.jhtm',1,1),
 (1193,'127.0.0.1','系统状态','2009-04-29 11:34:36','/system/status.jhtm',1,1),
 (1194,'127.0.0.1','缓存更新','2009-04-29 11:34:57','/cache/list.jhtm',1,1),
 (1195,'127.0.0.1','成功登录系统','2009-05-05 15:53:20','/login.jsp',1,1),
 (1196,NULL,'成功退出系统','2009-05-05 16:31:56','/logout.jsp',NULL,1),
 (1197,'127.0.0.1','成功登录系统','2009-05-05 17:10:12','/login.jsp',1,1),
 (1198,'127.0.0.1','管理标签','2009-05-05 17:10:19','/cms/tag_manage.jhtm',1,1),
 (1199,'127.0.0.1','缓存更新','2009-05-05 17:12:38','/cache/list.jhtm',1,1),
 (1200,'127.0.0.1','缓存更新','2009-05-05 17:12:40','/cache/list.jhtm',1,1),
 (1201,'127.0.0.1','添加标签','2009-05-05 17:12:49','/templatetag/add.jhtm',1,1),
 (1202,'127.0.0.1','添加标签','2009-05-05 17:13:36','/templatetag/add.jhtm',1,1),
 (1203,'127.0.0.1','管理标签','2009-05-05 17:13:45','/templatetag/list.jhtm',1,1),
 (1204,'127.0.0.1','添加碎片','2009-05-05 17:17:06','/block/add.jhtm',1,1),
 (1205,'127.0.0.1','添加标签','2009-05-05 17:17:09','/templatetag/add.jhtm',1,1),
 (1206,NULL,'成功退出系统','2009-05-05 17:37:38','/logout.jsp',NULL,1),
 (1207,'127.0.0.1','成功登录系统','2009-06-15 23:46:48','/login.jsp',1,1),
 (1208,'127.0.0.1','管理标签','2009-06-15 23:47:05','/templatetag/list.jhtm',1,1),
 (1209,'127.0.0.1','管理模板','2009-06-15 23:47:10','/template/list.jhtm',1,1),
 (1210,'127.0.0.1','管理模型','2009-06-15 23:47:16','/cms/model_manage.jhtm',1,1),
 (1211,'127.0.0.1','模板方案','2009-06-15 23:47:19','/cms/template_suit.jhtm',1,1),
 (1212,'127.0.0.1','碎片管理','2009-06-15 23:47:21','/cms/block_manage.jhtm',1,1),
 (1213,'127.0.0.1','碎片管理','2009-06-15 23:47:33','/cms/block_manage.jhtm',1,1),
 (1214,'127.0.0.1','新建模板','2009-06-15 23:47:35','/template/add.jhtm',1,1),
 (1215,'127.0.0.1','添加碎片','2009-06-15 23:47:37','/block/add.jhtm',1,1),
 (1216,'127.0.0.1','新建模板','2009-06-15 23:47:41','/template/add.jhtm',1,1),
 (1217,'127.0.0.1','添加碎片','2009-06-15 23:47:42','/block/add.jhtm',1,1),
 (1218,'127.0.0.1','添加标签','2009-06-15 23:47:45','/templatetag/add.jhtm',1,1),
 (1219,'127.0.0.1','管理标签','2009-06-15 23:47:49','/templatetag/list.jhtm',1,1),
 (1220,'127.0.0.1','添加标签','2009-06-15 23:47:51','/templatetag/add.jhtm',1,1),
 (1221,'127.0.0.1','添加碎片','2009-06-15 23:47:53','/block/add.jhtm',1,1),
 (1222,'127.0.0.1','碎片管理','2009-06-15 23:47:55','/cms/block_manage.jhtm',1,1),
 (1223,'127.0.0.1','成功登录系统','2009-06-16 08:54:36','/login.jsp',1,1),
 (1224,'127.0.0.1','成功登录系统','2009-06-16 08:59:13','/login.jsp',1,1),
 (1225,'127.0.0.1','管理标签','2009-06-16 09:01:55','/templatetag/list.jhtm',1,1),
 (1226,'127.0.0.1','碎片管理','2009-06-16 09:02:01','/cms/block_manage.jhtm',1,1),
 (1227,'127.0.0.1','风格管理','2009-06-16 09:02:03','/cms/template_style.jhtm',1,1),
 (1228,'127.0.0.1','模板方案','2009-06-16 09:02:04','/cms/template_suit.jhtm',1,1),
 (1229,'127.0.0.1','网站地图','2009-06-16 09:02:05','/cms/web_map.jhtm',1,1),
 (1230,'127.0.0.1','配送地区','2009-06-16 09:02:16','/area/list.jhtm',1,1),
 (1231,'127.0.0.1','地区分组','2009-06-16 09:02:17','/areatype/list.jhtm',1,1),
 (1232,'127.0.0.1','货币管理','2009-06-16 09:02:18','/currency/list.jhtm',1,1),
 (1233,'127.0.0.1','销售选项设置','2009-06-16 09:02:19','/sale/conf.jhtm',1,1),
 (1234,'127.0.0.1','风格管理','2009-06-16 09:02:23','/cms/template_style.jhtm',1,1),
 (1235,'127.0.0.1','模板方案','2009-06-16 09:02:24','/cms/template_suit.jhtm',1,1),
 (1236,'127.0.0.1','网站地图','2009-06-16 09:02:25','/cms/web_map.jhtm',1,1),
 (1237,'127.0.0.1','邮件设置','2009-06-16 09:02:26','/cms/site_email.jhtm',1,1),
 (1238,'127.0.0.1','管理栏目','2009-06-16 09:02:27','/cms/category_manage.jhtm',1,1),
 (1239,'127.0.0.1','添加栏目','2009-06-16 09:02:28','/cms/category_add.jhtm',1,1),
 (1240,'127.0.0.1','管理模型','2009-06-16 09:02:29','/cms/model_manage.jhtm',1,1),
 (1241,'127.0.0.1','添加模型','2009-06-16 09:02:31','/cms/model_add.jhtm',1,1),
 (1242,'127.0.0.1','模块管理','2009-06-16 09:02:33','/cms/module.jhtm',1,1),
 (1243,'127.0.0.1','管理模型','2009-06-16 09:02:34','/cms/model_manage.jhtm',1,1),
 (1244,'127.0.0.1','管理优惠券','2009-06-16 09:02:55','/promotion/coupon_manage.jhtm',1,1),
 (1245,'127.0.0.1','满额打折','2009-06-16 09:02:57','/promotion/medzh.jhtm',1,1),
 (1246,'127.0.0.1','买几送几','2009-06-16 09:02:58','/promotion/mjsj.jhtm',1,1),
 (1247,'127.0.0.1','满额免费用','2009-06-16 09:02:58','/promotion/memfy.jhtm',1,1),
 (1248,'127.0.0.1','管理标签','2009-06-16 09:09:36','/templatetag/list.jhtm',1,1),
 (1249,'127.0.0.1','添加标签','2009-06-16 09:09:39','/templatetag/add.jhtm',1,1),
 (1250,'127.0.0.1','管理模板','2009-06-16 09:09:43','/template/list.jhtm',1,1),
 (1251,'127.0.0.1','新建模板','2009-06-16 09:09:45','/template/add.jhtm',1,1),
 (1252,'127.0.0.1','碎片管理','2009-06-16 09:09:47','/cms/block_manage.jhtm',1,1),
 (1253,'127.0.0.1','风格管理','2009-06-16 09:09:49','/cms/template_style.jhtm',1,1),
 (1254,'127.0.0.1','模板方案','2009-06-16 09:09:51','/cms/template_suit.jhtm',1,1),
 (1255,'127.0.0.1','管理模型','2009-06-16 09:09:56','/cms/model_manage.jhtm',1,1),
 (1256,'127.0.0.1','添加模型','2009-06-16 09:09:58','/cms/model_add.jhtm',1,1),
 (1257,NULL,'成功退出系统','2009-06-16 09:15:02','/logout.jsp',NULL,1),
 (1258,NULL,'成功退出系统','2009-06-16 09:30:27','/logout.jsp',NULL,1);
/*!40000 ALTER TABLE `p_logs` ENABLE KEYS */;


--
-- Definition of table `p_member_types`
--

DROP TABLE IF EXISTS `p_member_types`;
CREATE TABLE `p_member_types` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `p_member_types`
--

/*!40000 ALTER TABLE `p_member_types` DISABLE KEYS */;
/*!40000 ALTER TABLE `p_member_types` ENABLE KEYS */;


--
-- Definition of table `p_regions`
--

DROP TABLE IF EXISTS `p_regions`;
CREATE TABLE `p_regions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL,
  `type` int(11) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKAB3CEF30188B6575` (`parent_id`),
  CONSTRAINT `FKAB3CEF30188B6575` FOREIGN KEY (`parent_id`) REFERENCES `p_regions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `p_regions`
--

/*!40000 ALTER TABLE `p_regions` DISABLE KEYS */;
INSERT INTO `p_regions` (`id`,`code`,`name`,`type`,`parent_id`) VALUES 
 (1,'regions','区域',0,NULL),
 (2,'china','中国',0,1),
 (3,'usa','美国',0,1),
 (4,'shandong','山东省',1,2),
 (5,'jinan','济南市',2,4),
 (6,'qingdao','青岛市',2,4),
 (7,'yantai','烟台市',2,4),
 (8,'weihai','威海市',2,4),
 (9,'weifang','潍坊市',2,4),
 (10,'rizhao','日照市',2,4),
 (11,'linyi','临沂市',2,4),
 (12,'jining','济宁市',2,4),
 (13,'heze','菏泽市',2,4),
 (14,'zibo','淄博市',2,4),
 (15,'liaocheng','聊城市',2,4),
 (16,'dezhou','德州市',2,4),
 (17,'binzhou','滨州市',2,4),
 (18,'dongying','东营市',2,4),
 (19,'zaozhuang','枣庄市',2,4),
 (20,'laiwu','莱芜市',2,4),
 (21,'taian','泰安市',2,4);
/*!40000 ALTER TABLE `p_regions` ENABLE KEYS */;


--
-- Definition of table `p_role_functions`
--

DROP TABLE IF EXISTS `p_role_functions`;
CREATE TABLE `p_role_functions` (
  `function_id` int(11) NOT NULL DEFAULT '0',
  `role_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`function_id`,`role_id`),
  KEY `FKA3DD66C1239D5BEB` (`role_id`),
  KEY `FKA3DD66C1EE3AA82B` (`function_id`),
  CONSTRAINT `FKA3DD66C1239D5BEB` FOREIGN KEY (`role_id`) REFERENCES `p_roles` (`role_id`),
  CONSTRAINT `FKA3DD66C1EE3AA82B` FOREIGN KEY (`function_id`) REFERENCES `p_functions` (`function_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `p_role_functions`
--

/*!40000 ALTER TABLE `p_role_functions` DISABLE KEYS */;
INSERT INTO `p_role_functions` (`function_id`,`role_id`) VALUES 
 (1,1),
 (2,1),
 (4,1),
 (5,1),
 (6,1),
 (7,1),
 (8,1),
 (9,1),
 (11,1),
 (13,1),
 (15,1),
 (21,1),
 (22,1),
 (23,1),
 (24,1),
 (25,1),
 (30,1),
 (49,1),
 (50,1),
 (51,1),
 (52,1),
 (124,1),
 (127,1),
 (128,1),
 (131,1),
 (172,1),
 (173,1),
 (174,1),
 (175,1),
 (176,1),
 (177,1),
 (178,1),
 (179,1),
 (180,1),
 (181,1),
 (182,1),
 (183,1),
 (184,1),
 (185,1),
 (186,1),
 (187,1),
 (188,1),
 (189,1),
 (190,1),
 (191,1),
 (192,1),
 (193,1),
 (194,1),
 (195,1),
 (196,1),
 (197,1),
 (198,1),
 (199,1),
 (200,1),
 (201,1),
 (202,1),
 (203,1),
 (204,1),
 (205,1),
 (206,1),
 (207,1),
 (208,1),
 (209,1),
 (210,1),
 (211,1),
 (212,1),
 (213,1),
 (214,1),
 (215,1),
 (216,1),
 (219,1),
 (220,1),
 (221,1),
 (222,1),
 (223,1),
 (224,1),
 (225,1),
 (226,1),
 (227,1),
 (228,1),
 (229,1),
 (230,1),
 (231,1),
 (232,1),
 (233,1),
 (234,1),
 (235,1),
 (236,1),
 (237,1),
 (238,1),
 (239,1),
 (240,1),
 (241,1),
 (242,1),
 (243,1),
 (244,1),
 (245,1),
 (246,1),
 (247,1),
 (248,1),
 (249,1),
 (250,1),
 (251,1),
 (252,1),
 (253,1),
 (254,1),
 (255,1),
 (256,1),
 (257,1),
 (258,1),
 (259,1),
 (260,1),
 (261,1),
 (262,1),
 (263,1),
 (264,1),
 (265,1),
 (268,1),
 (269,1),
 (270,1),
 (271,1),
 (272,1),
 (273,1),
 (274,1),
 (275,1),
 (276,1),
 (277,1),
 (278,1),
 (279,1),
 (280,1),
 (281,1),
 (283,1),
 (285,1),
 (286,1),
 (287,1),
 (288,1),
 (289,1),
 (290,1),
 (291,1),
 (292,1),
 (293,1),
 (294,1),
 (295,1),
 (296,1),
 (297,1),
 (298,1),
 (299,1),
 (300,1),
 (301,1),
 (302,1),
 (303,1),
 (304,1),
 (305,1),
 (306,1),
 (307,1),
 (308,1),
 (309,1),
 (310,1),
 (311,1),
 (312,1),
 (313,1),
 (314,1),
 (315,1),
 (316,1);
/*!40000 ALTER TABLE `p_role_functions` ENABLE KEYS */;


--
-- Definition of table `p_roles`
--

DROP TABLE IF EXISTS `p_roles`;
CREATE TABLE `p_roles` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(32) DEFAULT NULL,
  `note` varchar(100) DEFAULT NULL,
  `title` varchar(32) DEFAULT NULL,
  `shop_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE,
  KEY `FKCD50DE2E4CC14BEB` (`shop_id`),
  CONSTRAINT `FKCD50DE2E4CC14BEB` FOREIGN KEY (`shop_id`) REFERENCES `p_shops` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `p_roles`
--

/*!40000 ALTER TABLE `p_roles` DISABLE KEYS */;
INSERT INTO `p_roles` (`role_id`,`role_name`,`note`,`title`,`shop_id`) VALUES 
 (1,'admin',NULL,'管理员',1);
/*!40000 ALTER TABLE `p_roles` ENABLE KEYS */;


--
-- Definition of table `p_shops`
--

DROP TABLE IF EXISTS `p_shops`;
CREATE TABLE `p_shops` (
  `shop_id` int(11) NOT NULL AUTO_INCREMENT,
  `sort` int(11) DEFAULT NULL,
  `title` varchar(32) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `shop_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`shop_id`),
  KEY `FKCD5BD3AE4CF2D1B7` (`parent_id`),
  CONSTRAINT `FKCD5BD3AE4CF2D1B7` FOREIGN KEY (`parent_id`) REFERENCES `p_shops` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `p_shops`
--

/*!40000 ALTER TABLE `p_shops` DISABLE KEYS */;
INSERT INTO `p_shops` (`shop_id`,`sort`,`title`,`type`,`parent_id`,`shop_name`) VALUES 
 (1,3,'乐佳购物总店',0,NULL,'lejia'),
 (2,2,'分店1',0,NULL,'fendian1');
/*!40000 ALTER TABLE `p_shops` ENABLE KEYS */;


--
-- Definition of table `p_user_roles`
--

DROP TABLE IF EXISTS `p_user_roles`;
CREATE TABLE `p_user_roles` (
  `role_id` int(11) NOT NULL DEFAULT '0',
  `user_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `FK11D94878239D5BEB` (`role_id`),
  KEY `FK11D94878C8C81FCB` (`user_id`),
  CONSTRAINT `FK11D94878C8C81FCB` FOREIGN KEY (`user_id`) REFERENCES `p_users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `p_user_roles`
--

/*!40000 ALTER TABLE `p_user_roles` DISABLE KEYS */;
INSERT INTO `p_user_roles` (`role_id`,`user_id`) VALUES 
 (1,1),
 (1,2);
/*!40000 ALTER TABLE `p_user_roles` ENABLE KEYS */;


--
-- Definition of table `p_users`
--

DROP TABLE IF EXISTS `p_users`;
CREATE TABLE `p_users` (
  `concurrent_max` int(11) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `hint_answer` varchar(255) DEFAULT NULL,
  `hint_question` varchar(255) DEFAULT NULL,
  `last_login_ip` varchar(20) DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `login_attemptz_times` int(11) DEFAULT NULL,
  `login_attempts_max` int(11) DEFAULT NULL,
  `login_times` int(11) DEFAULT NULL,
  `password` varchar(32) NOT NULL,
  `validate_code` varchar(255) DEFAULT NULL,
  `username` varchar(32) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `login_attempts` int(11) DEFAULT NULL,
  `password_code` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `credentials_expired` bit(1) DEFAULT NULL,
  `expired` bit(1) DEFAULT NULL,
  `locked` bit(1) DEFAULT NULL,
  `realname` varchar(255) DEFAULT NULL,
  `shop_id` int(11) DEFAULT NULL,
  `validated` bit(1) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `gender` varchar(1) DEFAULT NULL,
  `postcode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  KEY `FKCD7CDD794CC14BEB` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `p_users`
--

/*!40000 ALTER TABLE `p_users` DISABLE KEYS */;
INSERT INTO `p_users` (`concurrent_max`,`email`,`enabled`,`hint_answer`,`hint_question`,`last_login_ip`,`last_login_time`,`login_attemptz_times`,`login_attempts_max`,`login_times`,`password`,`validate_code`,`username`,`age`,`login_attempts`,`password_code`,`user_id`,`credentials_expired`,`expired`,`locked`,`realname`,`shop_id`,`validated`,`address`,`gender`,`postcode`) VALUES 
 (10,'zhuzhsh@gmail.com',1,'who r u','zhuzhisheng','127.0.0.1','2009-06-16 08:59:13',100,100,168,'d871e6c9695a072327ca482debb0b1ae',NULL,'admin',1,1,NULL,1,0x00,0x00,0x00,'系统管理员',1,0x01,NULL,NULL,NULL),
 (10,'zzsh2001@163.com',1,'zhuzhisheng','who r u','127.0.0.1','2009-04-19 20:28:14',0,100,4,'d871e6c9695a072327ca482debb0b1ae','9125519618194201160103184711732271562534036','zhuzhsh',1,1,NULL,2,0x00,0x00,0x00,'用户2',1,0x00,NULL,NULL,NULL),
 (10,'zzsh2001@163.com',1,'zhuzhisheng','who r u',NULL,NULL,NULL,0,0,'9734e794eea3d637d947dfb3eb9702da','17414425891122132915180122111120115103125130','zhuzhsh1',0,0,NULL,3,0x01,0x00,0x00,'用户3',1,0x00,NULL,NULL,NULL),
 (10,'zzsh2001@163.com',1,'zhuzhisheng','who r u',NULL,NULL,NULL,0,0,'4d40d17658fa9c2c13bd098693aa77d5','3592662202302331422022387743309212222345','zhuzhsh2',0,0,NULL,4,0x01,0x00,0x00,'用户4',1,0x00,NULL,NULL,NULL),
 (10,'zzsh2001@163.com',1,'zhuzhisheng','who r u',NULL,NULL,NULL,0,0,'65254abfb5bfc44a9cfae70ead61e9b7','29514315716515314822238391969060190252231','zhuzhsh3',0,0,NULL,5,0x01,0x00,0x00,'用户5',1,0x00,NULL,NULL,NULL),
 (10,'zzsh2001@163.com',1,'zhuzhisheng','who r u',NULL,NULL,NULL,0,0,'ab1997ac4bc75ad0770192b473b7cb0c','08479560211253241492022425618313337182','zhuzhsh4',0,0,NULL,6,0x01,0x00,0x00,'用户6',1,0x00,NULL,NULL,NULL),
 (10,'zzsh2001@163.com',1,'zhuzhisheng','who r u',NULL,NULL,NULL,0,0,'12ebbf5f2569a313029c9aa069f950d3','93502720810414621523021415999104689573164','zhuzhsh5',0,0,NULL,7,0x01,0x00,0x00,'用户7',1,0x01,NULL,NULL,NULL),
 (10,'zhuzhsh@gmail.com',1,'zhuzhisheng','who r u',NULL,NULL,NULL,0,0,'12ebbf5f2569a313029c9aa069f950d3','2206159233292532425911365222875022221449','zhuzhsh6',0,0,NULL,8,0x01,0x00,0x00,'用户8',1,0x01,NULL,NULL,NULL),
 (10,'zhuzhsh@gmail.com',1,'zhuzhisheng','who r u',NULL,NULL,NULL,0,0,'02a9a1a0a058c3c7629c6f16e1c15829','6887209385243103233120117849123466962','zhuzhsh7',0,0,NULL,9,0x01,0x00,0x00,'用户9',1,0x01,NULL,NULL,NULL),
 (10,'zhuzhsh@gmail.com',1,'zhuzhisheng','who r u',NULL,NULL,NULL,0,0,'d87e2cacb6592a8c80d472e0e3a838c9','86425113232032023184175711574471172','zhuzhsh8',0,0,NULL,10,0x01,0x00,0x00,'用户10',1,0x01,NULL,NULL,NULL),
 (10,'zhuzhsh@gmail.com',1,'zhuzhisheng','who ru ',NULL,NULL,NULL,0,0,'a2eb7a3fcbd4c8f6532d9f77fefeb971','8423913921482302452311172472436313711213113','zhuzhsh9',0,0,NULL,11,0x01,0x00,0x00,'用户11',1,0x01,NULL,NULL,NULL),
 (10,'zzsh2001@163.com',1,'zhuzhisheng','who ru ',NULL,NULL,NULL,0,0,'a2eb7a3fcbd4c8f6532d9f77fefeb971','1611642521838246144220150178142150688419457','zhuzhsh10',0,0,NULL,12,0x01,0x00,0x00,'用户12',1,0x01,NULL,NULL,NULL),
 (10,'zzsh2001@163.com',1,'zhuzhisheng','who r u',NULL,NULL,NULL,0,0,'7e3fbb8c6c2ce43a20750bfa6c6701c3','216117241234391681221125315223011416287241','zhuzhsh15',0,0,NULL,13,0x01,0x00,0x00,'用户13',1,0x01,NULL,NULL,NULL);
/*!40000 ALTER TABLE `p_users` ENABLE KEYS */;


--
-- Definition of table `shop_agent_level`
--

DROP TABLE IF EXISTS `shop_agent_level`;
CREATE TABLE `shop_agent_level` (
  `level_id` int(11) NOT NULL AUTO_INCREMENT,
  `level_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `max_memeber_number` int(11) DEFAULT NULL,
  `max_template_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`level_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `shop_agent_level`
--

/*!40000 ALTER TABLE `shop_agent_level` DISABLE KEYS */;
INSERT INTO `shop_agent_level` (`level_id`,`level_name`,`description`,`discount`,`max_memeber_number`,`max_template_number`) VALUES 
 (1,'达到','测试拉',5.5,11,11),
 (5,'测试2','',6.9,48,1);
/*!40000 ALTER TABLE `shop_agent_level` ENABLE KEYS */;


--
-- Definition of table `shop_area`
--

DROP TABLE IF EXISTS `shop_area`;
CREATE TABLE `shop_area` (
  `area_id` int(11) NOT NULL AUTO_INCREMENT,
  `area_name` varchar(255) DEFAULT NULL,
  `sequence` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`area_id`),
  KEY `FK829318363228B2B5` (`type_id`),
  CONSTRAINT `FK829318363228B2B5` FOREIGN KEY (`type_id`) REFERENCES `shop_area_type` (`area_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `shop_area`
--

/*!40000 ALTER TABLE `shop_area` DISABLE KEYS */;
INSERT INTO `shop_area` (`area_id`,`area_name`,`sequence`,`type_id`) VALUES 
 (2,'上海',1,1),
 (3,'江苏省 ',2,2),
 (5,'山东省',2,1),
 (6,'四川',4,3),
 (7,'吉林',5,6),
 (11,'河南省',11,2),
 (12,'吉林省',11,5),
 (13,'广东省',11,3);
/*!40000 ALTER TABLE `shop_area` ENABLE KEYS */;


--
-- Definition of table `shop_area_type`
--

DROP TABLE IF EXISTS `shop_area_type`;
CREATE TABLE `shop_area_type` (
  `area_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `area_type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`area_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `shop_area_type`
--

/*!40000 ALTER TABLE `shop_area_type` DISABLE KEYS */;
INSERT INTO `shop_area_type` (`area_type_id`,`area_type_name`) VALUES 
 (1,'华东'),
 (2,'华北'),
 (3,'华南'),
 (4,'华中'),
 (5,'东北'),
 (6,'西北'),
 (7,'西南'),
 (8,'港澳台'),
 (9,'海外1');
/*!40000 ALTER TABLE `shop_area_type` ENABLE KEYS */;


--
-- Definition of table `shop_brand`
--

DROP TABLE IF EXISTS `shop_brand`;
CREATE TABLE `shop_brand` (
  `brand_id` int(11) NOT NULL AUTO_INCREMENT,
  `brand_logo` varchar(255) DEFAULT NULL,
  `brand_name` varchar(255) DEFAULT NULL,
  `brand_url` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`brand_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `shop_brand`
--

/*!40000 ALTER TABLE `shop_brand` DISABLE KEYS */;
INSERT INTO `shop_brand` (`brand_id`,`brand_logo`,`brand_name`,`brand_url`,`description`) VALUES 
 (16,'/upload/2009/04/07/20090407043017.jpg','七匹狼','www.7pilang.com','七匹狼男装'),
 (17,'/upload/2009/04/07/20090407044255.gif','ddddddddddddddddddddd','dddd','dddddddddddddd'),
 (18,'/upload/2009/04/07/20090407044320.jpg','hhhh','ddd','ddddd'),
 (19,'/upload/2009/04/07/20090407044330.jpg','pppp','ddd','dddd');
/*!40000 ALTER TABLE `shop_brand` ENABLE KEYS */;


--
-- Definition of table `shop_bulletin`
--

DROP TABLE IF EXISTS `shop_bulletin`;
CREATE TABLE `shop_bulletin` (
  `bulletin_id` int(11) NOT NULL AUTO_INCREMENT,
  `bulletin_content` varchar(255) DEFAULT NULL,
  `bulletin_title` varchar(255) DEFAULT NULL,
  `shop_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`bulletin_id`),
  KEY `FK699084D04CC14BEB` (`shop_id`),
  CONSTRAINT `FK699084D04CC14BEB` FOREIGN KEY (`shop_id`) REFERENCES `p_shops` (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `shop_bulletin`
--

/*!40000 ALTER TABLE `shop_bulletin` DISABLE KEYS */;
/*!40000 ALTER TABLE `shop_bulletin` ENABLE KEYS */;


--
-- Definition of table `shop_category`
--

DROP TABLE IF EXISTS `shop_category`;
CREATE TABLE `shop_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_en_name` varchar(255) DEFAULT NULL,
  `category_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `page_description` varchar(255) DEFAULT NULL,
  `page_key_kords` varchar(255) DEFAULT NULL,
  `sequence` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`category_id`),
  KEY `FKBCBA082746BA7ADC` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `shop_category`
--

/*!40000 ALTER TABLE `shop_category` DISABLE KEYS */;
INSERT INTO `shop_category` (`category_id`,`category_en_name`,`category_name`,`description`,`image_path`,`page_description`,`page_key_kords`,`sequence`,`parent_id`) VALUES 
 (1,'222','砰砰砰','<p>22222</p>','/upload/2009/04/09/20090409093810.jpg','22','222',0,NULL),
 (5,'adsf','dddddaaaa','<p>dddddddddddddddddddddddd</p>','/upload/2009/04/09/20090409104014.jpg','ddd','ddd',2,4),
 (6,'asdf','ddddaaa','<p>asdfasdfasdf</p>','/upload/2009/04/09/20090409105037.jpg','asdf','ddd',0,1),
 (7,'asdf','bba','<p>sdfasdfsdfa</p>','/upload/2009/04/09/20090409105110.jpg','asdf','aa',10,4),
 (8,'kkk','错错错','<p>asdfasdf</p>','/upload/2009/04/09/20090409105147.gif','asdf','dadsf',0,6),
 (9,'hhhhh','看看看看看','<p>222222222222222</p>',NULL,'222','222',22,1),
 (10,'lll','lll','<p>88888888888</p>','/upload/2009/04/09/20090409110327.jpg','88888888888888','88',88,9),
 (11,'asdfafs','asdasdf','<p>55555</p>','/upload/2009/04/09/20090409110543.jpg','555','55',55,NULL);
/*!40000 ALTER TABLE `shop_category` ENABLE KEYS */;


--
-- Definition of table `shop_currency`
--

DROP TABLE IF EXISTS `shop_currency`;
CREATE TABLE `shop_currency` (
  `currency_id` int(11) NOT NULL AUTO_INCREMENT,
  `currency_name` varchar(255) DEFAULT NULL,
  `currency_symbol` varchar(255) DEFAULT NULL,
  `currency_type` varchar(255) DEFAULT NULL,
  `exchange_rate` double DEFAULT NULL,
  PRIMARY KEY (`currency_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `shop_currency`
--

/*!40000 ALTER TABLE `shop_currency` DISABLE KEYS */;
INSERT INTO `shop_currency` (`currency_id`,`currency_name`,`currency_symbol`,`currency_type`,`exchange_rate`) VALUES 
 (1,'人民币','￥','CNY',1),
 (3,'英镑','$','GBP',13.22);
/*!40000 ALTER TABLE `shop_currency` ENABLE KEYS */;


--
-- Definition of table `shop_link`
--

DROP TABLE IF EXISTS `shop_link`;
CREATE TABLE `shop_link` (
  `link_id` int(11) NOT NULL AUTO_INCREMENT,
  `link_name` varchar(255) DEFAULT NULL,
  `link_url` varchar(255) DEFAULT NULL,
  `logo_url` varchar(255) DEFAULT NULL,
  `sequence` int(11) DEFAULT NULL,
  PRIMARY KEY (`link_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `shop_link`
--

/*!40000 ALTER TABLE `shop_link` DISABLE KEYS */;
/*!40000 ALTER TABLE `shop_link` ENABLE KEYS */;


--
-- Definition of table `shop_member_level`
--

DROP TABLE IF EXISTS `shop_member_level`;
CREATE TABLE `shop_member_level` (
  `level_id` int(11) NOT NULL AUTO_INCREMENT,
  `default_level` bit(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `dsicount` double DEFAULT NULL,
  `level_name` varchar(255) DEFAULT NULL,
  `min_business_scrore` int(11) DEFAULT NULL,
  `min_buy_score` int(11) DEFAULT NULL,
  `mini_money` int(11) DEFAULT NULL,
  PRIMARY KEY (`level_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `shop_member_level`
--

/*!40000 ALTER TABLE `shop_member_level` DISABLE KEYS */;
INSERT INTO `shop_member_level` (`level_id`,`default_level`,`description`,`dsicount`,`level_name`,`min_business_scrore`,`min_buy_score`,`mini_money`) VALUES 
 (3,0x01,'阿斯蒂芬撒旦',5.5,'贴牌会员',11,11,11),
 (4,0x00,'淡淡的',10,'铜牌会员',11,11,11);
/*!40000 ALTER TABLE `shop_member_level` ENABLE KEYS */;


--
-- Definition of table `shop_product`
--

DROP TABLE IF EXISTS `shop_product`;
CREATE TABLE `shop_product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `cost_price` double DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `marker_price` double DEFAULT NULL,
  `online` bit(1) NOT NULL,
  `price` double NOT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `product_no` varchar(32) DEFAULT NULL,
  `sequence` int(11) NOT NULL,
  `store_num` int(11) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `up_time` datetime DEFAULT NULL,
  `weight` int(11) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `shop_id` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `brand_id` int(11) DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `click_times` int(11) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `FK359BF8A64CC14BEB` (`shop_id`),
  KEY `FK359BF8A626FC7608` (`category_id`),
  KEY `FK359BF8A6843CD13B` (`type_id`),
  KEY `FK359BF8A6B9C1AE0C` (`brand_id`),
  KEY `FK359BF8A6EF92023B` (`category_id`),
  CONSTRAINT `FK359BF8A626FC7608` FOREIGN KEY (`category_id`) REFERENCES `shop_category` (`category_id`),
  CONSTRAINT `FK359BF8A64CC14BEB` FOREIGN KEY (`shop_id`) REFERENCES `p_shops` (`shop_id`),
  CONSTRAINT `FK359BF8A6843CD13B` FOREIGN KEY (`type_id`) REFERENCES `shop_product_type` (`type_id`),
  CONSTRAINT `FK359BF8A6B9C1AE0C` FOREIGN KEY (`brand_id`) REFERENCES `shop_brand` (`brand_id`),
  CONSTRAINT `FK359BF8A6EF92023B` FOREIGN KEY (`category_id`) REFERENCES `shop_category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `shop_product`
--

/*!40000 ALTER TABLE `shop_product` DISABLE KEYS */;
INSERT INTO `shop_product` (`product_id`,`cost_price`,`description`,`enabled`,`marker_price`,`online`,`price`,`product_name`,`product_no`,`sequence`,`store_num`,`unit`,`up_time`,`weight`,`category_id`,`shop_id`,`type_id`,`brand_id`,`image_path`,`click_times`) VALUES 
 (2,10,'<p>阿斯顿发生的发生地方</p>',0x00,10,0x01,10.6,'kkkk','kkkkkkkkk',11,10,'件',NULL,10,1,1,1,16,'/UserFiles/2009/04/14/20090414054112.jpg',0);
/*!40000 ALTER TABLE `shop_product` ENABLE KEYS */;


--
-- Definition of table `shop_product_config`
--

DROP TABLE IF EXISTS `shop_product_config`;
CREATE TABLE `shop_product_config` (
  `product_config_id` int(11) NOT NULL AUTO_INCREMENT,
  `default_config` bit(1) DEFAULT NULL,
  `image_height` int(11) DEFAULT NULL,
  `image_width` int(11) DEFAULT NULL,
  `money_per_score` int(11) DEFAULT NULL,
  `product_config_name` varchar(100) DEFAULT NULL,
  `thumb_height` int(11) DEFAULT NULL,
  `thumb_width` int(11) DEFAULT NULL,
  PRIMARY KEY (`product_config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `shop_product_config`
--

/*!40000 ALTER TABLE `shop_product_config` DISABLE KEYS */;
INSERT INTO `shop_product_config` (`product_config_id`,`default_config`,`image_height`,`image_width`,`money_per_score`,`product_config_name`,`thumb_height`,`thumb_width`) VALUES 
 (3,0x01,33,33,2,'方案21',11,22),
 (4,0x00,88,88,2,'方案2',22,22);
/*!40000 ALTER TABLE `shop_product_config` ENABLE KEYS */;


--
-- Definition of table `shop_product_type`
--

DROP TABLE IF EXISTS `shop_product_type`;
CREATE TABLE `shop_product_type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `page_description` varchar(255) DEFAULT NULL,
  `page_key_kords` varchar(255) DEFAULT NULL,
  `type_en_name` varchar(255) DEFAULT NULL,
  `type_name` varchar(255) DEFAULT NULL,
  `sequence` int(11) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `shop_product_type`
--

/*!40000 ALTER TABLE `shop_product_type` DISABLE KEYS */;
INSERT INTO `shop_product_type` (`type_id`,`description`,`image_path`,`page_description`,`page_key_kords`,`type_en_name`,`type_name`,`sequence`) VALUES 
 (1,'<p>2222</p>','/UserFiles/2009/04/14/20090414024726.jpg','22','22','rrrr','rrrr',0),
 (2,'<p>444444444444444444444444</p>','/UserFiles/2009/04/14/20090414024854.jpg','444','444','444','rrrr44',0),
 (3,'<p>sdfgsdfg</p>','/UserFiles/2009/04/14/20090414030429.jpg','sdfg','dfgsdfg','rrrr','rrrr44hhhh',0),
 (4,'<p>aaaaaaaaaaaaa</p>','/UserFiles/2009/04/14/20090414035227.jpg','aaaaaaaaaaaaaa','aaaaaaaaa','dddd','ddd',0);
/*!40000 ALTER TABLE `shop_product_type` ENABLE KEYS */;


--
-- Definition of table `shop_supplier`
--

DROP TABLE IF EXISTS `shop_supplier`;
CREATE TABLE `shop_supplier` (
  `supplier_id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `link_man` varchar(255) DEFAULT NULL,
  `mobilephone` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `postcode` varchar(255) DEFAULT NULL,
  `supplier_name` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`supplier_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `shop_supplier`
--

/*!40000 ALTER TABLE `shop_supplier` DISABLE KEYS */;
INSERT INTO `shop_supplier` (`supplier_id`,`address`,`email`,`fax`,`link_man`,`mobilephone`,`note`,`postcode`,`supplier_name`,`telephone`) VALUES 
 (1,'jinanshi','zzsh@d.com','0531-12345678','海1d','13412345678','','250014','海尔','0531-12345678'),
 (4,'济南历下区','hx@dd.com','0531-12345678','海信联系人','13412345678','淡淡的','250015','海信','0531-12345678'),
 (9,'22222','ddd@d.com','0531-99999999','aaa','111111222222','dddd','333333','aaaa','0531-99999333');
/*!40000 ALTER TABLE `shop_supplier` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
