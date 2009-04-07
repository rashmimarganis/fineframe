-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.30-community


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
) ENGINE=InnoDB AUTO_INCREMENT=316 DEFAULT CHARSET=utf8;

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
 ('datarestore','数据恢复',1,1,'_self',1,'platform/datarestore.jsp',24,22,0,1);
INSERT INTO `p_functions` (`function_name`,`function_title`,`is_log`,`is_show`,`open_type`,`sequence`,`url`,`function_id`,`parent_id`,`type`,`enabled`) VALUES 
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
 ('shop_article','文章管理',1,1,'_self',3,'/shop/article.jhtm',174,1,1,1);
INSERT INTO `p_functions` (`function_name`,`function_title`,`is_log`,`is_show`,`open_type`,`sequence`,`url`,`function_id`,`parent_id`,`type`,`enabled`) VALUES 
 ('shop_bullitin','店铺公告',1,1,'_self',2,'/shop/bullitin.jhtm',175,1,1,1),
 ('shop_system_tool','系统工具',1,1,'_self',1,'/shop/system_tool.jhtm',176,1,1,1),
 ('shop_system_report','系统报告',1,1,'_self',0,'/shop/system_report.jhtm',177,1,1,1),
 ('shop_config_base','基本设置',1,1,'_self',0,'/shop/config_base.jhtm',178,172,1,1),
 ('shop_config_email','邮件设置',1,1,'_self',1,'/shop/config_email.jhtm',179,172,1,1),
 ('shop_config_style','样式设置',1,1,'_self',2,'/shop/config_style.jhtm',180,172,1,1),
 ('shop_config_picture','图片上传设置',1,1,'_self',3,'/shop/config_picture.jhtm',181,172,1,1),
 ('shop_security_org','部门管理',1,1,'_self',0,'/shop/security_org.jhtm',182,173,1,1),
 ('shop_security_admin','店铺管理员',1,1,'_self',1,'/shop/security_admin.jhtm',183,173,1,1),
 ('shop_security_backupkey','备份密钥',1,1,'_self',2,'/shop/security_backupkey.jhtm',184,173,1,1),
 ('shop_security_restorekey','导入密钥',1,1,'_self',3,'/shop/security_importkey.jhtm',185,173,1,1);
INSERT INTO `p_functions` (`function_name`,`function_title`,`is_log`,`is_show`,`open_type`,`sequence`,`url`,`function_id`,`parent_id`,`type`,`enabled`) VALUES 
 ('shop_article_manage','文章管理',1,1,'_self',0,'/shop/article_manage.jhtm',186,174,1,1),
 ('shop_article_add','添加文章',1,1,'_self',1,'/shop/article_add.jhtm',187,174,1,1),
 ('shop_article_type','文章分类',1,1,'_self',2,'/shop/article_type.jhtm',188,174,1,1),
 ('shop_bullitin_add','添加公告',1,1,'_self',1,'/shop/bullitin_add.jhtm',189,175,1,1),
 ('shop_bullitin_manage','公告管理',1,1,'_self',1,'/shop/bullitin_manage.jhtm',190,175,1,1),
 ('shop_system_tool_vote','投票管理',1,1,'_self',0,'/shop/system_vote.jhtm',191,176,1,1),
 ('shop_system_tool_adv','广告管理',1,1,'_self',1,'/shop/system_adv.jhtm',192,176,1,1),
 ('shop_system_tool_link','友情链接',1,1,'_self',2,'/shop/system_link.jhtm',193,176,1,1),
 ('shop_system_tool_service','在线客服',1,1,'_self',3,'/shop/system_service.jhtm',194,176,1,1),
 ('shop_system_tool_email','邮件模板',1,1,'_self',4,'/shop/system_email.jhtm',195,176,1,1);
INSERT INTO `p_functions` (`function_name`,`function_title`,`is_log`,`is_show`,`open_type`,`sequence`,`url`,`function_id`,`parent_id`,`type`,`enabled`) VALUES 
 ('shop_system_report_admin_log','管理员操作日志',1,1,'_self',1,'/shop/system_admin_log.jhtm',196,177,1,1),
 ('shop_system_report_system_log','系统事件日志',1,1,'_self',1,'/shop/system_event_log.jhtm',197,177,1,1),
 ('shop_system_report_task_log','系统任务日志',1,1,'_self',1,'/shop/system_task_log.jhtm',198,177,1,1),
 ('customer_member','会员',1,1,'_self',1,'/customer/member.jhtm',199,49,1,1),
 ('customer_price_template','价格模板',1,1,'_self',2,'/customer/price_template.jhtm',200,256,1,1),
 ('customer_config','客户设置',1,1,'_self',3,'/customer/config.jhtm',201,256,1,1),
 ('customer_agent','代理商',1,1,'_self',0,'/customer/agent.jhtm',202,49,1,1),
 ('customer_member_add','添加会员',1,1,'_self',1,'/customer/member_add.jhtm',203,199,1,1),
 ('customer_member_manage','会员管理',1,1,'_self',1,'/customer/member_manage.jhtm',204,199,1,1),
 ('customer_member_level','会员等级管理',1,1,'_self',1,'/customer/member_type.jhtm',205,256,1,1);
INSERT INTO `p_functions` (`function_name`,`function_title`,`is_log`,`is_show`,`open_type`,`sequence`,`url`,`function_id`,`parent_id`,`type`,`enabled`) VALUES 
 ('customer_agent_level','代理商等级管理',1,1,'_self',1,'/customer/agent_level.jhtm',206,256,1,1),
 ('customer_agent_add','添加代理商',1,1,'_self',1,'/customer/agent_add.jhtm',207,202,1,1),
 ('customer_agent_manage','代理商管理',1,1,'_self',1,'/customer/agent_manage.jhtm',208,202,1,1),
 ('product_type','商品分类',1,1,'_self',1,'/product/type.jhtm',209,131,1,1),
 ('product_manage','商品管理',1,1,'_self',1,'/product/manage.jhtm',210,131,1,1),
 ('product_brand','品牌管理',1,1,'_self',1,'/brand/list.jhtm',211,257,1,1),
 ('product_config','商品设置',1,1,'_self',1,'/product/config.jhtm',212,257,1,1),
 ('product_type_manage','类别管理',1,1,'_self',1,'/product/type_manage.jhtm',213,209,1,1),
 ('product_type_classify','商品分类',1,1,'_self',1,'/product/classify.jhtm',214,209,1,1),
 ('product_type_unclassify','未分类商品',1,1,'_self',1,'/product/unclassify.jhtm',215,209,1,1),
 ('product_add','上架新商品',1,1,'_self',1,'/product/add.jhtm',216,210,1,1);
INSERT INTO `p_functions` (`function_name`,`function_title`,`is_log`,`is_show`,`open_type`,`sequence`,`url`,`function_id`,`parent_id`,`type`,`enabled`) VALUES 
 ('product_on_sale','出售中商品',1,1,'_self',1,'/product/onsale.jhtm',217,210,1,1),
 ('product_on_depot','仓库中商品',1,1,'_self',1,'/product/indepot.jhtm',218,210,1,1),
 ('product_consultation','商品咨询管理',1,1,'_self',1,'/product/consultation.jhtm',219,210,1,1),
 ('product_comments ','商品评论管理',1,1,'_self',1,'/product/comments.jhtm',220,210,1,1),
 ('product_comments_report ','商品评论举报',1,1,'_self',1,'/product/comments_report.jhtm',221,210,1,1),
 ('promotion_gift','礼品',1,1,'_self',0,'/promotion/gift.jhtm',222,50,1,1),
 ('promotion_activity','促销活动',1,1,'_self',1,'/promotion/activity.jhtm',223,50,1,1),
 ('promotion_coupon','优惠券',1,1,'_self',2,'/promotion/coupon.jhtm',224,50,1,1),
 ('promotion_gift_add','添加礼品',1,1,'_self',1,'/promotion/gift_add.jhtm',225,222,1,1),
 ('promotion_gift_manage','礼品管理',1,1,'_self',1,'/promotion/gift_manage.jhtm',226,222,1,1),
 ('promotion_activity_manage','店铺促销活动',1,1,'_self',1,'/promotion/activity.jhtm',227,223,1,1);
INSERT INTO `p_functions` (`function_name`,`function_title`,`is_log`,`is_show`,`open_type`,`sequence`,`url`,`function_id`,`parent_id`,`type`,`enabled`) VALUES 
 ('promotion_activity_medzh','满额打折',1,1,'_self',1,'/promotion/medzh.jhtm',228,223,1,1),
 ('promotion_activity_mezs','满额赠送',1,1,'_self',1,'/promotion/mezs.jhtm',229,223,1,1),
 ('promotion_activity_mjsj','买几送几',1,1,'_self',1,'/promotion/mjsj.jhtm',230,223,1,1),
 ('promotion_activity_memfy','满额免费用',1,1,'_self',1,'/promotion/memfy.jhtm',231,223,1,1),
 ('promotion_activity_pfdz','批发打折',1,1,'_self',1,'/promotion/pfdz.jhtm',232,223,1,1),
 ('promotion_coupon_add','添加优惠券',1,1,'_self',1,'/promotion/coupon_add.jhtm',233,224,1,1),
 ('promotion_coupon_manage','管理优惠券',1,1,'_self',1,'/promotion/coupon_manage.jhtm',234,224,1,1),
 ('sale_order','订单管理',1,1,'_self',1,'/sale/order.jhtm',235,51,1,1),
 ('sale_pay_type','支付方式',1,1,'_self',1,'/sale/pay_type.jhtm',236,51,1,1),
 ('sale_money','货币管理',1,1,'_self',1,'/sale/money.jhtm',237,258,1,1);
INSERT INTO `p_functions` (`function_name`,`function_title`,`is_log`,`is_show`,`open_type`,`sequence`,`url`,`function_id`,`parent_id`,`type`,`enabled`) VALUES 
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
 ('sale_analysis_order','订单统计',1,1,'_self',1,'/sale/analysis_order.jhtm',248,238,1,1);
INSERT INTO `p_functions` (`function_name`,`function_title`,`is_log`,`is_show`,`open_type`,`sequence`,`url`,`function_id`,`parent_id`,`type`,`enabled`) VALUES 
 ('sale_analysis_customer','客户统计',1,1,'_self',1,'/sale/analysis_customer.jhtm',249,238,1,1),
 ('distribution_type','配送方式',1,1,'_self',1,'/distribution/type.jhtm',250,52,1,1),
 ('distribution_area','配送地区',1,1,'_self',1,'/distribution/area.jhtm',251,259,1,1),
 ('distribution_area_kind','地区分组',1,1,'_self',1,'/distribution/area_kind.jhtm',252,259,1,1),
 ('distribution_type_add','添加配送方式',1,1,'_self',1,'/distribution/type_add.jhtm',253,250,1,1),
 ('distribution_type_manage','配送方式管理',1,1,'_self',1,'/distribution/type_manage.jhtm',254,250,1,1),
 ('base','基础数据',1,1,'_self',8,'/base.jhtm',255,NULL,0,1),
 ('base_customer','客户管理',1,1,'_self',1,'/base/customer.jhtm',256,255,1,1),
 ('base_product','商品管理',1,1,'_self',1,'/base/product.jhtm',257,255,1,1),
 ('base_sale','销售管理',1,1,'_self',1,'/base/sale.jhtm',258,255,1,1),
 ('base_distribution','配送管理',1,1,'_self',1,'/base/promotion.jhtm',259,255,1,1);
INSERT INTO `p_functions` (`function_name`,`function_title`,`is_log`,`is_show`,`open_type`,`sequence`,`url`,`function_id`,`parent_id`,`type`,`enabled`) VALUES 
 ('mydesk_product','商品',1,1,'_self',0,'/mydesk/product.jhtm',260,25,1,1),
 ('mydesk_sale','销售',1,1,'_self',1,'/mydesk/sale.jhtm',261,25,1,1),
 ('mydesk_promotion','促销',1,1,'_self',2,'/mydesk/promotion.jhtm',262,25,1,1),
 ('mydesk_order','订单',1,1,'_self',3,'/mydesk/order.jhtm',263,25,1,1),
 ('mydesk_customer','客户',1,1,'_self',4,'/mydesk/customer.jhtm',264,25,1,1),
 ('mydesk_product_add','上架新商品',1,1,'_self',0,'/product/add.jhtm',265,260,1,1),
 ('mydesk_product_onsale','出售中商品',1,1,'_self',1,'/product/onsale.jhtm',266,260,1,1),
 ('mydesk_product_indepot','仓库中商品',1,1,'_self',2,'/product/indepot.jhtm',267,260,1,1),
 ('mydesk_sale_paytype','支付方式管理',1,1,'_self',0,'/sale/paytype_manage.jhtm',268,261,1,1),
 ('mydesk_trade_report','生意报告',1,1,'_self',1,'/sale/analysis_trade.jhtm',269,261,1,1),
 ('mydesk_promotion_acrivity','店铺促销活动 ',1,1,'_self',0,'/promotion/activity.jhtm',270,262,1,1);
INSERT INTO `p_functions` (`function_name`,`function_title`,`is_log`,`is_show`,`open_type`,`sequence`,`url`,`function_id`,`parent_id`,`type`,`enabled`) VALUES 
 ('mydesk_promotion_addgift','添加礼品 ',1,1,'_self',1,'/promotion/gift_add.jhtm',271,262,1,1),
 ('mydesk_promotion_medzh','满额打折',1,1,'_self',2,'/promotion/medzh.jhtm',272,262,1,1),
 ('mydesk_promotion_memfs','满额免费用',1,1,'_self',3,'/promotion/memfy.jhtm',273,262,1,1),
 ('mydesk_promotion_mjsj','买几送几',1,1,'_self',4,'/promotion/mjsj.jhtm',274,262,1,1),
 ('mydesk_promotion_mezs','满额赠送',1,1,'_self',5,'/promotion/mezs.jhtm',275,262,1,1),
 ('mydesk_promotion_pfdzh','批发打折',1,1,'_self',6,'/promotion/pfdz.jhtm',276,262,1,1),
 ('mydesk_promotion_addcoupon','添加优惠券',1,1,'_self',7,'/promotion/coupon_add.jhtm',277,262,1,1),
 ('mydesk_promotion_coupon','优惠券管理',1,1,'_self',8,'/promotion/coupon_manage.jhtm',278,262,1,1),
 ('mydesk_order_add','新建订单',1,1,'_self',1,'/sale/order_add.jhtm',279,263,1,1),
 ('mydesk_order_manage','订单管理',1,1,'_self',2,'/sale/order_manage.jhtm',280,263,1,1);
INSERT INTO `p_functions` (`function_name`,`function_title`,`is_log`,`is_show`,`open_type`,`sequence`,`url`,`function_id`,`parent_id`,`type`,`enabled`) VALUES 
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
 ('cms_templatesuit','模板方案',1,1,'_self',6,'/cms/template_suit.jhtm',293,287,0,1);
INSERT INTO `p_functions` (`function_name`,`function_title`,`is_log`,`is_show`,`open_type`,`sequence`,`url`,`function_id`,`parent_id`,`type`,`enabled`) VALUES 
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
 ('cms_category_manage','管理栏目',1,1,'_self',2,'/cms/category_manage.jhtm',304,290,1,1);
INSERT INTO `p_functions` (`function_name`,`function_title`,`is_log`,`is_show`,`open_type`,`sequence`,`url`,`function_id`,`parent_id`,`type`,`enabled`) VALUES 
 ('cms_category_merge','合并栏目',1,1,'_self',3,'/cms/category_merge.jhtm',305,290,1,1),
 ('cms_template_suit','模板方案',1,1,'_self',1,'/cms/template_suit.jhtm',306,293,1,1),
 ('cms_template_style','风格管理',1,1,'_self',2,'/cms/template_style.jhtm',307,293,1,1),
 ('cms_block_manage','碎片管理',1,1,'_self',1,'/cms/block_manage.jhtm',308,294,1,1),
 ('cms_template_add','新建模板',1,1,'_self',2,'/cms/template_add.jhtm',309,294,1,1),
 ('cms_template_manage','管理模板',1,1,'_self',3,'/cms/template_manage.jhtm',310,294,1,1),
 ('cms_block_add','添加碎片',1,1,'_self',4,'/cms/block_add.jhtm',311,294,1,1),
 ('cms_tag_add','添加标签',1,1,'_self',5,'/cms/tag_add.jhtm',312,294,1,1),
 ('cms_tag_manage','管理标签',1,1,'_self',6,'/cms/tag_manage.jhtm',313,294,1,1),
 ('main','首页',0,0,'_self',0,'/main.jhtm',314,NULL,0,1),
 ('system_status','系统状态',1,1,'_self',3,'/system/status.jhtm',315,15,1,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=564 DEFAULT CHARSET=utf8;

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
 (25,'127.0.0.1','成功登录系统','2009-04-01 12:00:58','/login.jsp',1,1);
INSERT INTO `p_logs` (`log_id`,`ip`,`operation`,`time`,`url`,`shop_id`,`user_id`) VALUES 
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
 (369,'127.0.0.1','系统状态','2009-04-06 18:48:26','/system/status.jhtm',1,1);
INSERT INTO `p_logs` (`log_id`,`ip`,`operation`,`time`,`url`,`shop_id`,`user_id`) VALUES 
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
 (381,'127.0.0.1','成功登录系统','2009-04-06 21:46:20','/login.jsp',1,1);
INSERT INTO `p_logs` (`log_id`,`ip`,`operation`,`time`,`url`,`shop_id`,`user_id`) VALUES 
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
 (394,'127.0.0.1','品牌管理','2009-04-06 22:04:58','/brand/list.jhtm',1,1);
INSERT INTO `p_logs` (`log_id`,`ip`,`operation`,`time`,`url`,`shop_id`,`user_id`) VALUES 
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
 (407,'127.0.0.1','成功登录系统','2009-04-07 13:00:42','/login.jsp',1,1);
INSERT INTO `p_logs` (`log_id`,`ip`,`operation`,`time`,`url`,`shop_id`,`user_id`) VALUES 
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
 (419,'127.0.0.1','系统状态','2009-04-07 13:03:18','/system/status.jhtm',1,1);
INSERT INTO `p_logs` (`log_id`,`ip`,`operation`,`time`,`url`,`shop_id`,`user_id`) VALUES 
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
 (432,'127.0.0.1','品牌管理','2009-04-07 13:45:26','/brand/list.jhtm',1,1);
INSERT INTO `p_logs` (`log_id`,`ip`,`operation`,`time`,`url`,`shop_id`,`user_id`) VALUES 
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
 (445,'127.0.0.1','品牌管理','2009-04-07 14:51:40','/brand/list.jhtm',1,1);
INSERT INTO `p_logs` (`log_id`,`ip`,`operation`,`time`,`url`,`shop_id`,`user_id`) VALUES 
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
 (458,'127.0.0.1','品牌管理','2009-04-07 15:04:07','/brand/list.jhtm',1,1);
INSERT INTO `p_logs` (`log_id`,`ip`,`operation`,`time`,`url`,`shop_id`,`user_id`) VALUES 
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
 (471,'127.0.0.1','品牌管理','2009-04-07 15:19:32','/brand/list.jhtm',1,1);
INSERT INTO `p_logs` (`log_id`,`ip`,`operation`,`time`,`url`,`shop_id`,`user_id`) VALUES 
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
 (484,'127.0.0.1','缓存更新','2009-04-07 15:34:39','/cache/list.jhtm',1,1);
INSERT INTO `p_logs` (`log_id`,`ip`,`operation`,`time`,`url`,`shop_id`,`user_id`) VALUES 
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
 (497,'127.0.0.1','品牌管理','2009-04-07 16:18:53','/brand/list.jhtm',1,1);
INSERT INTO `p_logs` (`log_id`,`ip`,`operation`,`time`,`url`,`shop_id`,`user_id`) VALUES 
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
 (510,'127.0.0.1','品牌管理','2009-04-07 16:34:11','/brand/list.jhtm',1,1);
INSERT INTO `p_logs` (`log_id`,`ip`,`operation`,`time`,`url`,`shop_id`,`user_id`) VALUES 
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
 (523,'127.0.0.1','品牌管理','2009-04-07 16:43:32','/brand/list.jhtm',1,1);
INSERT INTO `p_logs` (`log_id`,`ip`,`operation`,`time`,`url`,`shop_id`,`user_id`) VALUES 
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
 (536,'127.0.0.1','品牌管理','2009-04-07 16:52:12','/brand/list.jhtm',1,1);
INSERT INTO `p_logs` (`log_id`,`ip`,`operation`,`time`,`url`,`shop_id`,`user_id`) VALUES 
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
 (549,'127.0.0.1','品牌管理','2009-04-07 17:04:07','/brand/list.jhtm',1,1);
INSERT INTO `p_logs` (`log_id`,`ip`,`operation`,`time`,`url`,`shop_id`,`user_id`) VALUES 
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
 (562,'127.0.0.1','品牌管理','2009-04-07 17:29:37','/brand/list.jhtm',1,1);
INSERT INTO `p_logs` (`log_id`,`ip`,`operation`,`time`,`url`,`shop_id`,`user_id`) VALUES 
 (563,'127.0.0.1','品牌管理','2009-04-07 17:29:42','/brand/list.jhtm',1,1);
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
 (217,1),
 (218,1),
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
 (244,1);
INSERT INTO `p_role_functions` (`function_id`,`role_id`) VALUES 
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
 (266,1),
 (267,1),
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
 (315,1);
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
  `depth` int(11) NOT NULL,
  `full_title` varchar(250) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `path` varchar(250) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `title` varchar(32) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `shop_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`shop_id`),
  KEY `FKCD5BD3AE4CF2D1B7` (`parent_id`),
  CONSTRAINT `FKCD5BD3AE4CF2D1B7` FOREIGN KEY (`parent_id`) REFERENCES `p_shops` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `p_shops`
--

/*!40000 ALTER TABLE `p_shops` DISABLE KEYS */;
INSERT INTO `p_shops` (`shop_id`,`depth`,`full_title`,`name`,`path`,`sort`,`title`,`type`,`parent_id`,`shop_name`) VALUES 
 (1,1,'山东省工商行政管理局',NULL,'/0/1',0,'山东省工商局',0,NULL,NULL);
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
 (1,1);
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
  `credentials_non_expired` bit(1) DEFAULT NULL,
  `login_attempts` int(11) DEFAULT NULL,
  `non_expired` bit(1) NOT NULL,
  `non_locked` bit(1) NOT NULL,
  `password_code` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `credentials_expired` bit(1) DEFAULT NULL,
  `expired` bit(1) DEFAULT NULL,
  `locked` bit(1) DEFAULT NULL,
  `realname` varchar(255) DEFAULT NULL,
  `shop_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  KEY `FKCD7CDD794CC14BEB` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `p_users`
--

/*!40000 ALTER TABLE `p_users` DISABLE KEYS */;
INSERT INTO `p_users` (`concurrent_max`,`email`,`enabled`,`hint_answer`,`hint_question`,`last_login_ip`,`last_login_time`,`login_attemptz_times`,`login_attempts_max`,`login_times`,`password`,`validate_code`,`username`,`age`,`credentials_non_expired`,`login_attempts`,`non_expired`,`non_locked`,`password_code`,`user_id`,`credentials_expired`,`expired`,`locked`,`realname`,`shop_id`) VALUES 
 (10,'zhuzhsh@gmail.com',1,'who r u','zhuzhisheng','127.0.0.1','2009-04-07 17:28:11',100,100,94,'21232f297a57a5a743894a0e4a801fc3',NULL,'admin',1,0x01,1,0x01,0x01,NULL,1,0x00,0x00,0x00,'系统管理员',1);
/*!40000 ALTER TABLE `p_users` ENABLE KEYS */;


--
-- Definition of table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `roles`
--

/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`id`,`name`) VALUES 
 (1,'管理员'),
 (2,'用户');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;


--
-- Definition of table `roles_authorities`
--

DROP TABLE IF EXISTS `roles_authorities`;
CREATE TABLE `roles_authorities` (
  `ROLE_ID` int(11) NOT NULL,
  `AUTHORITY_ID` int(11) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`AUTHORITY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `roles_authorities`
--

/*!40000 ALTER TABLE `roles_authorities` DISABLE KEYS */;
INSERT INTO `roles_authorities` (`ROLE_ID`,`AUTHORITY_ID`) VALUES 
 (1,1),
 (1,2),
 (1,3),
 (1,4),
 (2,1),
 (2,3);
/*!40000 ALTER TABLE `roles_authorities` ENABLE KEYS */;


--
-- Definition of table `s_brand`
--

DROP TABLE IF EXISTS `s_brand`;
CREATE TABLE `s_brand` (
  `brand_id` int(11) NOT NULL AUTO_INCREMENT,
  `brand_logo` varchar(255) DEFAULT NULL,
  `brand_name` varchar(255) DEFAULT NULL,
  `brand_url` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`brand_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `s_brand`
--

/*!40000 ALTER TABLE `s_brand` DISABLE KEYS */;
INSERT INTO `s_brand` (`brand_id`,`brand_logo`,`brand_name`,`brand_url`,`description`) VALUES 
 (16,'/upload/2009/04/07/20090407043017.jpg','七匹狼','www.7pilang.com','七匹狼男装'),
 (17,'/upload/2009/04/07/20090407044255.gif','ddddddddddddddddddddd','dddd','dddddddddddddd'),
 (18,'/upload/2009/04/07/20090407044320.jpg','hhhh','ddd','ddddd'),
 (19,'/upload/2009/04/07/20090407044330.jpg','pppp','ddd','dddd');
/*!40000 ALTER TABLE `s_brand` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
