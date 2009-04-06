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
-- Create schema springside
--

CREATE DATABASE IF NOT EXISTS springside;
USE springside;

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
  KEY `FKC2EE368CF515FC1E` (`parent_id`),
  KEY `FKC2EE368CDDA60A19` (`parent_id`),
  CONSTRAINT `FKC2EE368CDDA60A19` FOREIGN KEY (`parent_id`) REFERENCES `p_functions` (`function_id`),
  CONSTRAINT `FKC2EE368CF515FC1E` FOREIGN KEY (`parent_id`) REFERENCES `p_functions` (`function_id`)
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
 ('product_brand','品牌管理',1,1,'_self',1,'/product/brand.jhtm',211,257,1,1),
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(20) DEFAULT NULL,
  `operation` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC48C441ECAA30F29` (`org_id`),
  KEY `FKC48C441EC8C81FCB` (`user_id`),
  CONSTRAINT `FKC48C441EC8C81FCB` FOREIGN KEY (`user_id`) REFERENCES `p_users` (`id`),
  CONSTRAINT `FKC48C441ECAA30F29` FOREIGN KEY (`org_id`) REFERENCES `p_orgs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=357 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `p_logs`
--

/*!40000 ALTER TABLE `p_logs` DISABLE KEYS */;
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
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
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
 (26,'127.0.0.1','home','2009-04-01 12:00:59','/main.jhtm',1,1),
 (27,'127.0.0.1','home','2009-04-01 12:01:07','/main.jhtm',1,1),
 (28,'127.0.0.1','system','2009-04-01 12:01:23','/test.jsp',1,1),
 (29,'127.0.0.1','home','2009-04-01 12:07:06','/main.jhtm',1,1),
 (30,'127.0.0.1','成功登录系统','2009-04-01 12:28:36','/login.jsp',1,1),
 (31,'127.0.0.1','home','2009-04-01 12:28:36','/main.jhtm',1,1),
 (32,'127.0.0.1','home','2009-04-01 12:29:40','/main.jhtm',1,1),
 (33,'127.0.0.1','home','2009-04-01 12:29:41','/main.jhtm',1,1),
 (34,'127.0.0.1','home','2009-04-01 12:30:22','/main.jhtm',1,1),
 (35,'127.0.0.1','成功登录系统','2009-04-01 12:33:24','/login.jsp',1,1),
 (36,'127.0.0.1','home','2009-04-01 12:33:24','/main.jhtm',1,1),
 (37,'127.0.0.1','成功登录系统','2009-04-01 12:34:24','/login.jsp',1,1),
 (38,'127.0.0.1','home','2009-04-01 12:34:25','/main.jhtm',1,1),
 (39,'127.0.0.1','home','2009-04-01 12:45:43','/main.jhtm',1,1),
 (40,NULL,'成功退出系统','2009-04-01 13:06:09','/logout.jsp',NULL,1);
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
 (41,'127.0.0.1','成功登录系统','2009-04-01 13:51:42','/login.jsp',1,1),
 (42,'127.0.0.1','home','2009-04-01 13:51:43','/main.jhtm',1,1),
 (43,'127.0.0.1','home','2009-04-01 13:52:42','/main.jhtm',1,1),
 (44,'127.0.0.1','home','2009-04-01 13:52:44','/main.jhtm',1,1),
 (45,'127.0.0.1','成功登录系统','2009-04-01 13:59:30','/login.jsp',1,1),
 (46,'127.0.0.1','home','2009-04-01 13:59:31','/main.jhtm',1,1),
 (47,'127.0.0.1','home','2009-04-01 14:05:13','/main.jhtm',1,1),
 (48,'127.0.0.1','home','2009-04-01 14:06:24','/main.jhtm',1,1),
 (49,'127.0.0.1','home','2009-04-01 14:06:34','/main.jhtm',1,1),
 (50,'127.0.0.1','home','2009-04-01 14:06:47','/main.jhtm',1,1),
 (51,'127.0.0.1','home','2009-04-01 14:06:48','/main.jhtm',1,1),
 (52,'127.0.0.1','home','2009-04-01 14:08:37','/main.jhtm',1,1),
 (53,'127.0.0.1','home','2009-04-01 14:08:51','/main.jhtm',1,1),
 (54,'127.0.0.1','成功登录系统','2009-04-01 14:18:10','/login.jsp',1,1),
 (55,'127.0.0.1','home','2009-04-01 14:18:11','/main.jhtm',1,1);
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
 (56,'127.0.0.1','成功登录系统','2009-04-01 14:19:08','/login.jsp',1,1),
 (57,'127.0.0.1','home','2009-04-01 14:19:08','/main.jhtm',1,1),
 (58,'127.0.0.1','home','2009-04-01 14:20:43','/main.jhtm',1,1),
 (59,'127.0.0.1','home','2009-04-01 14:20:45','/main.jhtm',1,1),
 (60,'127.0.0.1','成功登录系统','2009-04-01 14:22:07','/login.jsp',1,1),
 (61,'127.0.0.1','home','2009-04-01 14:22:07','/main.jhtm',1,1),
 (62,'127.0.0.1','成功登录系统','2009-04-01 14:23:33','/login.jsp',1,1),
 (63,'127.0.0.1','home','2009-04-01 14:23:33','/main.jhtm',1,1),
 (64,'127.0.0.1','成功登录系统','2009-04-01 14:25:15','/login.jsp',1,1),
 (65,'127.0.0.1','home','2009-04-01 14:25:15','/main.jhtm',1,1),
 (66,'127.0.0.1','成功登录系统','2009-04-01 14:26:42','/login.jsp',1,1),
 (67,'127.0.0.1','home','2009-04-01 14:26:42','/main.jhtm',1,1),
 (68,'127.0.0.1','home','2009-04-01 14:26:50','/main.jhtm',1,1),
 (69,'127.0.0.1','home','2009-04-01 14:30:12','/main.jhtm',1,1);
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
 (70,'127.0.0.1','home','2009-04-01 14:30:14','/main.jhtm',1,1),
 (71,'127.0.0.1','成功登录系统','2009-04-01 14:36:14','/login.jsp',1,1),
 (72,'127.0.0.1','home','2009-04-01 14:36:15','/main.jhtm',1,1),
 (73,'127.0.0.1','home','2009-04-01 14:36:47','/main.jhtm',1,1),
 (74,'127.0.0.1','home','2009-04-01 14:36:51','/main.jhtm',1,1),
 (75,'127.0.0.1','home','2009-04-01 14:36:53','/main.jhtm',1,1),
 (76,'127.0.0.1','成功登录系统','2009-04-01 14:46:14','/login.jsp',1,1),
 (77,'127.0.0.1','home','2009-04-01 14:46:14','/main.jhtm',1,1),
 (78,'127.0.0.1','home','2009-04-01 14:47:34','/main.jhtm',1,1),
 (79,'127.0.0.1','home','2009-04-01 14:47:39','/main.jhtm',1,1),
 (80,'127.0.0.1','home','2009-04-01 14:47:41','/main.jhtm',1,1),
 (81,'127.0.0.1','成功登录系统','2009-04-01 14:50:04','/login.jsp',1,1),
 (82,'127.0.0.1','home','2009-04-01 14:50:04','/main.jhtm',1,1),
 (83,'127.0.0.1','成功登录系统','2009-04-01 14:54:09','/login.jsp',1,1),
 (84,'127.0.0.1','home','2009-04-01 14:54:09','/main.jhtm',1,1);
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
 (85,'127.0.0.1','成功登录系统','2009-04-01 14:59:33','/login.jsp',1,1),
 (86,'127.0.0.1','home','2009-04-01 14:59:33','/main.jhtm',1,1),
 (87,'127.0.0.1','system','2009-04-01 14:59:56','/test.jsp',1,1),
 (88,'127.0.0.1','成功登录系统','2009-04-01 15:29:29','/login.jsp',1,1),
 (89,'127.0.0.1','home','2009-04-01 15:29:29','/main.jhtm',1,1),
 (90,'127.0.0.1','成功登录系统','2009-04-01 15:47:02','/login.jsp',1,1),
 (91,'127.0.0.1','home','2009-04-01 15:47:03','/main.jhtm',1,1),
 (92,'127.0.0.1','home','2009-04-01 15:47:27','/main.jhtm',1,1),
 (93,'127.0.0.1','home','2009-04-01 15:47:56','/main.jhtm',1,1),
 (94,'127.0.0.1','home','2009-04-01 15:47:58','/main.jhtm',1,1),
 (95,'127.0.0.1','home','2009-04-01 15:47:58','/main.jhtm',1,1),
 (96,'127.0.0.1','home','2009-04-01 15:48:11','/main.jhtm',1,1),
 (97,'127.0.0.1','home','2009-04-01 15:48:12','/main.jhtm',1,1),
 (98,'127.0.0.1','成功登录系统','2009-04-01 15:49:30','/login.jsp',1,1),
 (99,'127.0.0.1','home','2009-04-01 15:49:31','/main.jhtm',1,1);
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
 (100,'127.0.0.1','home','2009-04-01 15:50:03','/main.jhtm',1,1),
 (101,'127.0.0.1','home','2009-04-01 15:50:04','/main.jhtm',1,1),
 (102,'127.0.0.1','home','2009-04-01 15:50:05','/main.jhtm',1,1),
 (103,'127.0.0.1','home','2009-04-01 15:50:06','/main.jhtm',1,1),
 (104,'127.0.0.1','home','2009-04-01 15:50:06','/main.jhtm',1,1),
 (105,'127.0.0.1','home','2009-04-01 15:50:07','/main.jhtm',1,1),
 (106,'127.0.0.1','home','2009-04-01 15:50:07','/main.jhtm',1,1),
 (107,'127.0.0.1','home','2009-04-01 15:50:08','/main.jhtm',1,1),
 (108,'127.0.0.1','home','2009-04-01 15:50:09','/main.jhtm',1,1),
 (109,'127.0.0.1','home','2009-04-01 15:50:09','/main.jhtm',1,1),
 (110,'127.0.0.1','home','2009-04-01 15:50:10','/main.jhtm',1,1),
 (111,'127.0.0.1','home','2009-04-01 15:50:11','/main.jhtm',1,1),
 (112,'127.0.0.1','home','2009-04-01 15:50:12','/main.jhtm',1,1),
 (113,'127.0.0.1','home','2009-04-01 15:50:13','/main.jhtm',1,1),
 (114,'127.0.0.1','home','2009-04-01 15:50:13','/main.jhtm',1,1);
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
 (115,'127.0.0.1','home','2009-04-01 15:50:14','/main.jhtm',1,1),
 (116,'127.0.0.1','home','2009-04-01 15:50:15','/main.jhtm',1,1),
 (117,'127.0.0.1','home','2009-04-01 15:50:17','/main.jhtm',1,1),
 (118,'127.0.0.1','home','2009-04-01 15:50:18','/main.jhtm',1,1),
 (119,'127.0.0.1','home','2009-04-01 15:50:19','/main.jhtm',1,1),
 (120,'127.0.0.1','home','2009-04-01 15:50:20','/main.jhtm',1,1),
 (121,'127.0.0.1','home','2009-04-01 15:50:21','/main.jhtm',1,1),
 (122,'127.0.0.1','home','2009-04-01 15:50:21','/main.jhtm',1,1),
 (123,'127.0.0.1','home','2009-04-01 15:50:22','/main.jhtm',1,1),
 (124,'127.0.0.1','home','2009-04-01 15:50:24','/main.jhtm',1,1),
 (125,'127.0.0.1','成功登录系统','2009-04-01 16:05:22','/login.jsp',1,1),
 (126,'127.0.0.1','home','2009-04-01 16:05:23','/main.jhtm',1,1),
 (127,'127.0.0.1','home','2009-04-01 16:05:38','/main.jhtm',1,1),
 (128,'127.0.0.1','home','2009-04-01 16:05:40','/main.jhtm',1,1),
 (129,'127.0.0.1','成功登录系统','2009-04-01 16:09:45','/login.jsp',1,1);
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
 (130,'127.0.0.1','home','2009-04-01 16:09:45','/main.jhtm',1,1),
 (131,'127.0.0.1','home','2009-04-01 16:10:17','/main.jhtm',1,1),
 (132,'127.0.0.1','home','2009-04-01 16:10:18','/main.jhtm',1,1),
 (133,'127.0.0.1','成功登录系统','2009-04-01 16:11:05','/login.jsp',1,1),
 (134,'127.0.0.1','home','2009-04-01 16:11:05','/main.jhtm',1,1),
 (135,'127.0.0.1','home','2009-04-01 16:11:25','/main.jhtm',1,1),
 (136,'127.0.0.1','成功登录系统','2009-04-01 16:12:49','/login.jsp',1,1),
 (137,'127.0.0.1','home','2009-04-01 16:12:49','/main.jhtm',1,1),
 (138,'127.0.0.1','home','2009-04-01 16:13:00','/main.jhtm',1,1),
 (139,'127.0.0.1','成功登录系统','2009-04-01 16:15:57','/login.jsp',1,1),
 (140,'127.0.0.1','home','2009-04-01 16:15:57','/main.jhtm',1,1),
 (141,'127.0.0.1','home','2009-04-01 16:16:13','/main.jhtm',1,1),
 (142,'127.0.0.1','home','2009-04-01 16:16:52','/main.jhtm',1,1),
 (143,'127.0.0.1','home','2009-04-01 16:20:37','/main.jhtm',1,1),
 (144,'127.0.0.1','home','2009-04-01 16:20:39','/main.jhtm',1,1);
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
 (145,'127.0.0.1','成功登录系统','2009-04-01 16:21:21','/login.jsp',1,1),
 (146,'127.0.0.1','home','2009-04-01 16:21:22','/main.jhtm',1,1),
 (147,'127.0.0.1','home','2009-04-01 16:23:32','/main.jhtm',1,1),
 (148,'127.0.0.1','home','2009-04-01 16:23:51','/main.jhtm',1,1),
 (149,'127.0.0.1','成功登录系统','2009-04-01 16:28:08','/login.jsp',1,1),
 (150,'127.0.0.1','home','2009-04-01 16:28:09','/main.jhtm',1,1),
 (151,'127.0.0.1','system','2009-04-01 16:28:16','/test.jsp',1,1),
 (152,'127.0.0.1','system','2009-04-01 16:29:24','/test.jsp',1,1),
 (153,'127.0.0.1','home','2009-04-01 16:29:33','/main.jhtm',1,1),
 (154,'127.0.0.1','home','2009-04-01 16:29:51','/main.jhtm',1,1),
 (155,'127.0.0.1','home','2009-04-01 16:30:31','/main.jhtm',1,1),
 (156,'127.0.0.1','home','2009-04-01 16:31:27','/main.jhtm',1,1),
 (157,'127.0.0.1','home','2009-04-01 16:32:23','/main.jhtm',1,1),
 (158,'127.0.0.1','成功登录系统','2009-04-01 16:42:33','/login.jsp',1,1),
 (159,'127.0.0.1','home','2009-04-01 16:42:33','/main.jhtm',1,1);
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
 (160,'127.0.0.1','home','2009-04-01 16:42:46','/main.jhtm',1,1),
 (161,'127.0.0.1','home','2009-04-01 16:42:51','/main.jhtm',1,1),
 (162,'127.0.0.1','home','2009-04-01 16:42:55','/main.jhtm',1,1),
 (163,'127.0.0.1','home','2009-04-01 16:43:05','/main.jhtm',1,1),
 (164,'127.0.0.1','成功登录系统','2009-04-01 17:46:11','/login.jsp',1,1),
 (165,'127.0.0.1','home','2009-04-01 17:46:13','/main.jhtm',1,1),
 (166,'127.0.0.1','home','2009-04-01 17:46:37','/main.jhtm',1,1),
 (167,'127.0.0.1','home','2009-04-01 17:47:08','/main.jhtm',1,1),
 (168,'127.0.0.1','成功登录系统','2009-04-01 17:51:41','/login.jsp',1,1),
 (169,'127.0.0.1','home','2009-04-01 17:51:41','/main.jhtm',1,1),
 (170,'127.0.0.1','成功登录系统','2009-04-01 17:54:26','/login.jsp',1,1),
 (171,'127.0.0.1','home','2009-04-01 17:54:27','/main.jhtm',1,1),
 (172,'127.0.0.1','home','2009-04-01 17:55:52','/main.jhtm',1,1),
 (173,'127.0.0.1','home','2009-04-01 17:56:19','/main.jhtm',1,1),
 (174,'127.0.0.1','home','2009-04-01 17:56:28','/main.jhtm',1,1);
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
 (175,'127.0.0.1','home','2009-04-01 17:56:32','/main.jhtm',1,1),
 (176,'127.0.0.1','home','2009-04-01 17:56:41','/main.jhtm',1,1),
 (177,'127.0.0.1','成功登录系统','2009-04-01 18:26:56','/login.jsp',1,1),
 (178,'127.0.0.1','home','2009-04-01 18:26:57','/main.jhtm',1,1),
 (179,'127.0.0.1','成功登录系统','2009-04-01 18:30:10','/login.jsp',1,1),
 (180,'127.0.0.1','home','2009-04-01 18:30:11','/main.jhtm',1,1),
 (181,'127.0.0.1','成功登录系统','2009-04-01 18:37:57','/login.jsp',1,1),
 (182,'127.0.0.1','home','2009-04-01 18:37:57','/main.jhtm',1,1),
 (183,'127.0.0.1','home','2009-04-01 18:38:16','/main.jhtm',1,1),
 (184,'127.0.0.1','成功登录系统','2009-04-01 18:46:34','/login.jsp',1,1),
 (185,'127.0.0.1','home','2009-04-01 18:46:35','/main.jhtm',1,1),
 (186,'127.0.0.1','home','2009-04-01 18:47:02','/main.jhtm',1,1),
 (187,'127.0.0.1','成功登录系统','2009-04-01 18:48:02','/login.jsp',1,1),
 (188,'127.0.0.1','home','2009-04-01 18:48:02','/main.jhtm',1,1);
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
 (189,'127.0.0.1','成功登录系统','2009-04-02 10:27:03','/login.jsp',1,1),
 (190,'127.0.0.1','home','2009-04-02 10:27:05','/main.jhtm',1,1),
 (191,'127.0.0.1','home','2009-04-02 10:28:24','/main.jhtm',1,1),
 (192,'127.0.0.1','home','2009-04-02 10:32:26','/main.jhtm',1,1),
 (193,'127.0.0.1','home','2009-04-02 10:32:31','/main.jhtm',1,1),
 (194,'127.0.0.1','home','2009-04-02 10:48:58','/main.jhtm',1,1),
 (195,'127.0.0.1','home','2009-04-02 10:51:41','/main.jhtm',1,1),
 (196,'127.0.0.1','home','2009-04-02 10:51:42','/main.jhtm',1,1),
 (197,'127.0.0.1','home','2009-04-02 10:51:51','/main.jhtm',1,1),
 (198,'127.0.0.1','home','2009-04-02 10:51:52','/main.jhtm',1,1),
 (199,'127.0.0.1','home','2009-04-02 10:53:54','/main.jhtm',1,1),
 (200,'127.0.0.1','home','2009-04-02 10:55:40','/main.jhtm',1,1),
 (201,'127.0.0.1','shop','2009-04-02 11:02:20','/main.jhtm',1,1),
 (202,'127.0.0.1','成功登录系统','2009-04-02 11:25:33','/login.jsp',1,1),
 (203,'127.0.0.1','shop','2009-04-02 11:25:33','/main.jhtm',1,1);
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
 (204,'127.0.0.1','shop','2009-04-02 11:26:53','/main.jhtm',1,1),
 (205,'127.0.0.1','shop','2009-04-02 11:27:07','/main.jhtm',1,1),
 (206,'127.0.0.1','成功登录系统','2009-04-02 11:56:35','/login.jsp',1,1),
 (207,'127.0.0.1','shop','2009-04-02 11:56:35','/main.jhtm',1,1),
 (208,'127.0.0.1','shop','2009-04-02 11:56:57','/main.jhtm',1,1),
 (209,'127.0.0.1','shop','2009-04-02 12:01:12','/main.jhtm',1,1),
 (210,'127.0.0.1','shop','2009-04-02 12:09:44','/main.jhtm',1,1),
 (211,'127.0.0.1','成功登录系统','2009-04-02 12:16:39','/login.jsp',1,1),
 (212,'127.0.0.1','shop','2009-04-02 12:16:39','/main.jhtm',1,1),
 (213,'127.0.0.1','shop','2009-04-02 12:21:33','/main.jhtm',1,1),
 (214,NULL,'成功退出系统','2009-04-02 12:42:18','/logout.jsp',NULL,1),
 (215,'127.0.0.1','成功登录系统','2009-04-02 21:00:35','/login.jsp',1,1),
 (216,'127.0.0.1','shop','2009-04-02 21:00:35','/main.jhtm',1,1),
 (217,'127.0.0.1','shop','2009-04-02 21:02:25','/main.jhtm',1,1),
 (218,'127.0.0.1','shop','2009-04-02 21:08:00','/main.jhtm',1,1);
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
 (219,'127.0.0.1','shop','2009-04-02 21:08:39','/main.jhtm',1,1),
 (220,'127.0.0.1','成功登录系统','2009-04-02 21:15:13','/login.jsp',1,1),
 (221,'127.0.0.1','shop','2009-04-02 21:15:14','/main.jhtm',1,1),
 (222,'127.0.0.1','shop','2009-04-02 21:16:14','/main.jhtm',1,1),
 (223,'127.0.0.1','成功登录系统','2009-04-02 21:34:08','/login.jsp',1,1),
 (224,'127.0.0.1','shop','2009-04-02 21:34:15','/main.jhtm',1,1),
 (225,'127.0.0.1','shop','2009-04-02 21:34:49','/main.jhtm',1,1),
 (226,'127.0.0.1','shop','2009-04-02 21:35:42','/main.jhtm',1,1),
 (227,'127.0.0.1','shop','2009-04-02 21:37:24','/main.jhtm',1,1),
 (228,'127.0.0.1','shop','2009-04-02 21:37:49','/main.jhtm',1,1),
 (229,'127.0.0.1','成功登录系统','2009-04-02 21:41:02','/login.jsp',1,1),
 (230,'127.0.0.1','shop','2009-04-02 21:41:03','/main.jhtm',1,1),
 (231,'127.0.0.1','shop','2009-04-02 21:41:46','/main.jhtm',1,1),
 (232,'127.0.0.1','shop','2009-04-02 21:43:20','/main.jhtm',1,1),
 (233,'127.0.0.1','shop','2009-04-02 21:45:02','/main.jhtm',1,1);
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
 (234,'127.0.0.1','成功登录系统','2009-04-02 21:46:14','/login.jsp',1,1),
 (235,'127.0.0.1','shop','2009-04-02 21:46:14','/main.jhtm',1,1),
 (236,'127.0.0.1','shop','2009-04-02 21:47:03','/main.jhtm',1,1),
 (237,'127.0.0.1','shop','2009-04-02 21:48:24','/main.jhtm',1,1),
 (238,'127.0.0.1','shop','2009-04-02 21:49:46','/main.jhtm',1,1),
 (239,'127.0.0.1','成功登录系统','2009-04-02 22:37:16','/login.jsp',1,1),
 (240,'127.0.0.1','shop','2009-04-02 22:37:17','/main.jhtm',1,1),
 (241,'127.0.0.1','system','2009-04-02 22:37:40','/test.jsp',1,1),
 (242,'127.0.0.1','成功登录系统','2009-04-03 10:36:39','/login.jsp',1,1),
 (243,'127.0.0.1','shop','2009-04-03 10:36:40','/main.jhtm',1,1),
 (244,'127.0.0.1','成功登录系统','2009-04-03 10:52:13','/login.jsp',1,1),
 (245,'127.0.0.1','shop','2009-04-03 10:52:15','/main.jhtm',1,1),
 (246,'127.0.0.1','成功登录系统','2009-04-03 12:07:20','/login.jsp',1,1),
 (247,'127.0.0.1','shop','2009-04-03 12:07:20','/main.jhtm',1,1);
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
 (248,'127.0.0.1','shop','2009-04-03 12:12:35','/main.jhtm',1,1),
 (249,'127.0.0.1','shop','2009-04-03 12:12:44','/main.jhtm',1,1),
 (250,'127.0.0.1','shop','2009-04-03 12:12:46','/main.jhtm',1,1),
 (251,'127.0.0.1','shop','2009-04-03 12:13:21','/main.jhtm',1,1),
 (252,'127.0.0.1','成功登录系统','2009-04-03 12:32:19','/login.jsp',1,1),
 (253,'127.0.0.1','shop','2009-04-03 12:32:19','/main.jhtm',1,1),
 (254,'127.0.0.1','shop','2009-04-03 13:00:48','/main.jhtm',1,1),
 (255,'127.0.0.1','shop','2009-04-03 13:02:51','/main.jhtm',1,1),
 (256,'127.0.0.1','shop','2009-04-03 13:09:50','/main.jhtm',1,1),
 (257,'127.0.0.1','shop','2009-04-03 13:17:01','/main.jhtm',1,1),
 (258,'127.0.0.1','shop','2009-04-03 13:17:13','/main.jhtm',1,1),
 (259,'127.0.0.1','shop','2009-04-03 13:20:41','/main.jhtm',1,1),
 (260,'127.0.0.1','成功登录系统','2009-04-03 14:06:39','/login.jsp',1,1),
 (261,'127.0.0.1','店铺管理','2009-04-03 14:06:40','/main.jhtm',1,1),
 (262,'127.0.0.1','成功登录系统','2009-04-03 14:11:48','/login.jsp',1,1);
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
 (263,'127.0.0.1','店铺管理','2009-04-03 14:11:49','/main.jhtm',1,1),
 (264,'127.0.0.1','系统日志','2009-04-03 14:13:31','/log/page.jhtm',1,1),
 (265,'127.0.0.1','系统日志','2009-04-03 14:14:14','/log/page.jhtm',1,1),
 (266,'127.0.0.1','系统日志','2009-04-03 14:14:53','/log/page.jhtm',1,1),
 (267,'127.0.0.1','系统日志','2009-04-03 14:24:29','/log/page.jhtm',1,1),
 (268,'127.0.0.1','系统日志','2009-04-03 14:24:31','/log/page.jhtm',1,1),
 (269,'127.0.0.1','店铺管理','2009-04-03 14:28:00','/main.jhtm',1,1),
 (270,'127.0.0.1','系统日志','2009-04-03 14:29:31','/log/page.jhtm',1,1),
 (271,'127.0.0.1','系统日志','2009-04-03 14:29:51','/log/page.jhtm',1,1),
 (272,'127.0.0.1','成功登录系统','2009-04-03 14:34:05','/login.jsp',1,1),
 (273,'127.0.0.1','店铺管理','2009-04-03 14:34:05','/main.jhtm',1,1),
 (274,'127.0.0.1','系统日志','2009-04-03 14:34:14','/log/page.jhtm',1,1),
 (275,'127.0.0.1','成功登录系统','2009-04-03 14:37:40','/login.jsp',1,1);
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
 (276,'127.0.0.1','店铺管理','2009-04-03 14:37:41','/main.jhtm',1,1),
 (277,'127.0.0.1','系统日志','2009-04-03 14:37:50','/log/page.jhtm',1,1),
 (278,'127.0.0.1','系统日志','2009-04-03 14:38:04','/log/page.jhtm',1,1),
 (279,'127.0.0.1','系统日志','2009-04-03 14:39:33','/log/page.jhtm',1,1),
 (280,'127.0.0.1','成功登录系统','2009-04-03 14:41:16','/login.jsp',1,1),
 (281,'127.0.0.1','店铺管理','2009-04-03 14:41:16','/main.jhtm',1,1),
 (282,'127.0.0.1','系统日志','2009-04-03 14:41:25','/log/page.jhtm',1,1),
 (283,'127.0.0.1','成功登录系统','2009-04-03 14:43:00','/login.jsp',1,1),
 (284,'127.0.0.1','店铺管理','2009-04-03 14:43:00','/main.jhtm',1,1),
 (285,'127.0.0.1','系统日志','2009-04-03 14:45:37','/log/page.jhtm',1,1),
 (286,'127.0.0.1','店铺管理','2009-04-03 14:46:18','/main.jhtm',1,1),
 (287,'127.0.0.1','店铺管理','2009-04-03 14:47:50','/main.jhtm',1,1),
 (288,'127.0.0.1','系统日志','2009-04-03 14:48:01','/log/page.jhtm',1,1);
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
 (289,'127.0.0.1','系统日志','2009-04-03 14:52:42','/log/page.jhtm',1,1),
 (290,'127.0.0.1','缓存更新','2009-04-04 16:49:35','/cache/list.jhtm',1,1),
 (291,'127.0.0.1','缓存更新','2009-04-04 16:50:01','/cache/list.jhtm',1,1),
 (292,'127.0.0.1','缓存更新','2009-04-04 16:50:46','/cache/list.jhtm',1,1),
 (293,'127.0.0.1','缓存更新','2009-04-04 16:51:51','/cache/list.jhtm',1,1),
 (294,'127.0.0.1','缓存更新','2009-04-04 16:52:08','/cache/list.jhtm',1,1),
 (295,'127.0.0.1','缓存更新','2009-04-04 16:53:29','/cache/list.jhtm',1,1),
 (296,'127.0.0.1','缓存更新','2009-04-04 16:54:18','/cache/list.jhtm',1,1),
 (297,'127.0.0.1','缓存更新','2009-04-04 16:54:35','/cache/list.jhtm',1,1),
 (298,'127.0.0.1','缓存更新','2009-04-04 16:56:21','/cache/list.jhtm',1,1),
 (299,'127.0.0.1','缓存更新','2009-04-04 16:57:14','/cache/list.jhtm',1,1),
 (300,'127.0.0.1','缓存更新','2009-04-04 16:57:31','/cache/list.jhtm',1,1),
 (301,'127.0.0.1','缓存更新','2009-04-04 16:57:51','/cache/list.jhtm',1,1);
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
 (302,'127.0.0.1','缓存更新','2009-04-04 16:58:06','/cache/list.jhtm',1,1),
 (303,'127.0.0.1','缓存更新','2009-04-04 16:58:26','/cache/list.jhtm',1,1),
 (304,'127.0.0.1','缓存更新','2009-04-04 16:59:17','/cache/list.jhtm',1,1),
 (305,'127.0.0.1','缓存更新','2009-04-04 16:59:37','/cache/list.jhtm',1,1),
 (306,'127.0.0.1','缓存更新','2009-04-04 17:01:26','/cache/list.jhtm',1,1),
 (307,'127.0.0.1','缓存更新','2009-04-04 17:01:59','/cache/list.jhtm',1,1),
 (308,'127.0.0.1','缓存更新','2009-04-04 17:02:05','/cache/list.jhtm',1,1),
 (309,'127.0.0.1','缓存更新','2009-04-04 17:02:16','/cache/list.jhtm',1,1),
 (310,'127.0.0.1','缓存更新','2009-04-04 17:03:22','/cache/list.jhtm',1,1),
 (311,'127.0.0.1','缓存更新','2009-04-04 17:04:00','/cache/list.jhtm',1,1),
 (312,'127.0.0.1','缓存更新','2009-04-04 17:04:24','/cache/list.jhtm',1,1),
 (313,'127.0.0.1','缓存更新','2009-04-04 17:04:41','/cache/list.jhtm',1,1),
 (314,'127.0.0.1','缓存更新','2009-04-04 18:06:36','/cache/list.jhtm',1,1);
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
 (315,'127.0.0.1','缓存更新','2009-04-04 18:06:49','/cache/list.jhtm',1,1),
 (316,'127.0.0.1','缓存更新','2009-04-04 18:06:55','/cache/list.jhtm',1,1),
 (317,'127.0.0.1','缓存更新','2009-04-04 18:10:14','/cache/list.jhtm',1,1),
 (318,'127.0.0.1','缓存更新','2009-04-04 18:10:17','/cache/list.jhtm',1,1),
 (319,NULL,'成功退出系统','2009-04-04 18:10:34','/logout.jsp',NULL,1),
 (320,'127.0.0.1','系统状态','2009-04-04 18:10:34','/system/status.jhtm',1,1),
 (321,'127.0.0.1','系统状态','2009-04-04 18:14:47','/system/status.jhtm',1,1),
 (322,'127.0.0.1','系统状态','2009-04-04 18:15:45','/system/status.jhtm',1,1),
 (323,'127.0.0.1','系统状态','2009-04-04 18:15:46','/system/status.jhtm',1,1),
 (324,'127.0.0.1','系统状态','2009-04-04 18:15:50','/system/status.jhtm',1,1),
 (325,'127.0.0.1','系统状态','2009-04-04 18:16:44','/system/status.jhtm',1,1),
 (326,'127.0.0.1','系统状态','2009-04-04 18:16:48','/system/status.jhtm',1,1),
 (327,'127.0.0.1','系统状态','2009-04-04 18:16:51','/system/status.jhtm',1,1);
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
 (328,'127.0.0.1','系统状态','2009-04-04 18:17:09','/system/status.jhtm',1,1),
 (329,'127.0.0.1','系统状态','2009-04-04 18:18:34','/system/status.jhtm',1,1),
 (330,'127.0.0.1','系统状态','2009-04-04 18:18:36','/system/status.jhtm',1,1),
 (331,'127.0.0.1','系统状态','2009-04-04 18:20:54','/system/status.jhtm',1,1),
 (332,'127.0.0.1','系统状态','2009-04-04 18:20:56','/system/status.jhtm',1,1),
 (333,'127.0.0.1','系统状态','2009-04-04 18:21:15','/system/status.jhtm',1,1),
 (334,'127.0.0.1','系统状态','2009-04-04 18:21:16','/system/status.jhtm',1,1),
 (335,'127.0.0.1','系统状态','2009-04-04 18:23:19','/system/status.jhtm',1,1),
 (336,'127.0.0.1','系统状态','2009-04-04 18:24:39','/system/status.jhtm',1,1),
 (337,'127.0.0.1','系统状态','2009-04-04 18:24:51','/system/status.jhtm',1,1),
 (338,'127.0.0.1','系统状态','2009-04-04 18:37:23','/system/status.jhtm',1,1),
 (339,'127.0.0.1','系统状态','2009-04-04 18:38:00','/system/status.jhtm',1,1),
 (340,'127.0.0.1','系统状态','2009-04-04 18:38:31','/system/status.jhtm',1,1);
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
 (341,'127.0.0.1','系统状态','2009-04-04 18:38:56','/system/status.jhtm',1,1),
 (342,'127.0.0.1','系统状态','2009-04-04 18:41:39','/system/status.jhtm',1,1),
 (343,'127.0.0.1','系统状态','2009-04-04 18:41:55','/system/status.jhtm',1,1),
 (344,'127.0.0.1','系统状态','2009-04-04 18:42:26','/system/status.jhtm',1,1),
 (345,'127.0.0.1','系统状态','2009-04-04 18:42:35','/system/status.jhtm',1,1),
 (346,'127.0.0.1','系统状态','2009-04-04 18:43:11','/system/status.jhtm',1,1),
 (347,'127.0.0.1','系统状态','2009-04-04 18:43:51','/system/status.jhtm',1,1),
 (348,'127.0.0.1','系统状态','2009-04-04 18:55:36','/system/status.jhtm',1,1),
 (349,'127.0.0.1','系统状态','2009-04-04 18:57:26','/system/status.jhtm',1,1),
 (350,'127.0.0.1','系统状态','2009-04-04 18:58:08','/system/status.jhtm',1,1),
 (351,'127.0.0.1','系统状态','2009-04-04 18:58:17','/system/status.jhtm',1,1),
 (352,'127.0.0.1','系统状态','2009-04-04 18:58:19','/system/status.jhtm',1,1),
 (353,'127.0.0.1','系统状态','2009-04-04 18:58:22','/system/status.jhtm',1,1);
INSERT INTO `p_logs` (`id`,`ip`,`operation`,`time`,`url`,`org_id`,`user_id`) VALUES 
 (354,'127.0.0.1','系统状态','2009-04-04 18:58:36','/system/status.jhtm',1,1),
 (355,NULL,'成功退出系统','2009-04-04 19:21:39','/logout.jsp',NULL,1),
 (356,'127.0.0.1','系统状态','2009-04-06 15:14:28','/system/status.jhtm',1,1);
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
-- Definition of table `p_orgs`
--

DROP TABLE IF EXISTS `p_orgs`;
CREATE TABLE `p_orgs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `depth` int(11) NOT NULL,
  `full_title` varchar(250) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `path` varchar(250) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `title` varchar(32) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC48DAC7E3AD1367E` (`parent_id`),
  KEY `FKC48DAC7E822917A3` (`parent_id`),
  CONSTRAINT `FKC48DAC7E3AD1367E` FOREIGN KEY (`parent_id`) REFERENCES `p_orgs` (`id`),
  CONSTRAINT `FKC48DAC7E822917A3` FOREIGN KEY (`parent_id`) REFERENCES `p_orgs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `p_orgs`
--

/*!40000 ALTER TABLE `p_orgs` DISABLE KEYS */;
INSERT INTO `p_orgs` (`id`,`depth`,`full_title`,`name`,`path`,`sort`,`title`,`type`,`parent_id`) VALUES 
 (1,1,'山东省工商行政管理局',NULL,'/0/1',0,'山东省工商局',0,NULL),
 (2,1,'山东省工商局局领导',NULL,'/0/2',0,'局领导',1,1),
 (3,1,NULL,NULL,'/0/4',1,'两总师',1,1),
 (4,1,NULL,NULL,'/0/13',2,'办公室',1,1),
 (5,1,NULL,NULL,'/0/14',4,'人事处',1,1),
 (6,1,NULL,NULL,'/0/14',4,'法制处',1,1),
 (7,1,NULL,NULL,'/0/16',5,'企业处',1,1),
 (8,1,NULL,NULL,'/0/17',6,'外资处',1,1),
 (9,1,NULL,NULL,'/0/17',7,'个体处',1,1),
 (10,1,NULL,NULL,'/0/18',8,'市场处',1,1),
 (11,1,NULL,NULL,'/0/19',9,'广告处',1,1),
 (12,1,NULL,NULL,'/0/20',10,'合同处',1,1),
 (13,1,NULL,NULL,'/0/21',11,'财装处',1,1),
 (14,1,NULL,NULL,'/0/22',12,'政治工作处',1,1),
 (15,1,NULL,NULL,'/0/23',12,'宣传处',1,1),
 (16,1,NULL,NULL,'/0/24',13,'离退休干部处',1,1),
 (17,1,NULL,NULL,'/0/25',14,'机关党委',1,1),
 (18,1,NULL,NULL,'/0/26',16,'商标处',1,1),
 (19,1,NULL,NULL,'/0/27',17,'纪检监察室',1,1),
 (20,1,NULL,NULL,'/0/28',18,'公平交易局',1,1);
INSERT INTO `p_orgs` (`id`,`depth`,`full_title`,`name`,`path`,`sort`,`title`,`type`,`parent_id`) VALUES 
 (21,1,NULL,NULL,'/0/29',19,'消保处',1,1),
 (22,1,NULL,NULL,'/0/31',20,'机关服务中心',1,1),
 (23,1,'山东省个体私营企业协会',NULL,'/0/32',21,'个体私营企业协会',1,1),
 (24,1,'山东省消费者协会',NULL,'/0/33',22,'消费者协会',1,1),
 (25,1,NULL,NULL,'/0/34',23,'信息中心',1,1),
 (26,1,NULL,NULL,'/0/34',24,'局印刷所',1,1),
 (27,1,NULL,NULL,'/0/35',25,'十七地市用户',1,1),
 (28,0,NULL,NULL,'/0',0,'山东省省级机关人民防空委员会办公室',0,1),
 (29,0,NULL,NULL,'/0',0,'人民防空办公室',0,1);
/*!40000 ALTER TABLE `p_orgs` ENABLE KEYS */;


--
-- Definition of table `p_persons`
--

DROP TABLE IF EXISTS `p_persons`;
CREATE TABLE `p_persons` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(200) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `card_id` varchar(18) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `first_name` varchar(10) DEFAULT NULL,
  `gender` varchar(4) DEFAULT NULL,
  `homepage` varchar(50) DEFAULT NULL,
  `last_name` varchar(10) DEFAULT NULL,
  `marriage` varchar(18) DEFAULT NULL,
  `mobilephone` varchar(11) DEFAULT NULL,
  `msn` varchar(50) DEFAULT NULL,
  `picture` varchar(50) DEFAULT NULL,
  `political_status` varchar(18) DEFAULT NULL,
  `postcode` varchar(6) DEFAULT NULL,
  `qq` varchar(18) DEFAULT NULL,
  `qualifications` varchar(10) DEFAULT NULL,
  `realname` varchar(20) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `telephone` varchar(18) DEFAULT NULL,
  `vocation` varchar(50) DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  `region_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK421006EF834B2E04` (`org_id`),
  KEY `FK421006EF55F1DA30` (`region_id`),
  KEY `FK421006EFCAA30F29` (`org_id`),
  KEY `FK421006EFA1932AEB` (`region_id`),
  CONSTRAINT `FK421006EF55F1DA30` FOREIGN KEY (`region_id`) REFERENCES `p_regions` (`id`),
  CONSTRAINT `FK421006EF834B2E04` FOREIGN KEY (`org_id`) REFERENCES `p_orgs` (`id`),
  CONSTRAINT `FK421006EFA1932AEB` FOREIGN KEY (`region_id`) REFERENCES `p_regions` (`id`),
  CONSTRAINT `FK421006EFCAA30F29` FOREIGN KEY (`org_id`) REFERENCES `p_orgs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=347 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `p_persons`
--

/*!40000 ALTER TABLE `p_persons` DISABLE KEYS */;
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (1,'',NULL,'1979-01-06 00:00:00',NULL,NULL,'zhuzhsh@gmail.com',NULL,'男',NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'系统管理员',1,NULL,NULL,NULL,1,2),
 (2,'',NULL,'2009-02-09 00:00:00',NULL,NULL,'zhuzhsh@gmail.com','华理','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李华理局长',1,NULL,NULL,NULL,1,5),
 (3,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','天仁','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王天仁副局长',1,NULL,NULL,NULL,1,5),
 (4,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','铁军','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张铁军副局长',1,NULL,NULL,NULL,1,5),
 (5,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','国丽','男',NULL,'孙','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'孙国丽副局长',1,NULL,NULL,NULL,1,5),
 (6,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','学法','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李学法副局长',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (7,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','福安','男',NULL,'蔡','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'蔡福安副局长',1,NULL,NULL,NULL,1,5),
 (8,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','言春','男',NULL,'刘','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'刘言春组长',1,NULL,NULL,NULL,1,5),
 (9,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','悍真','男',NULL,'赵','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'赵悍真副巡',1,NULL,NULL,NULL,1,5),
 (10,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','东威','男',NULL,'马','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'马东威副巡',1,NULL,NULL,NULL,1,5),
 (11,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','泽科','男',NULL,'杨','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'杨泽科',1,NULL,NULL,NULL,1,5),
 (12,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','荣龙','男',NULL,'曲','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'曲荣龙',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (13,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','庆峰','男',NULL,'穆','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'穆庆峰',1,NULL,NULL,NULL,1,5),
 (14,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','全胜','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张全胜',1,NULL,NULL,NULL,1,5),
 (15,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','汉平','男',NULL,'刘','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'刘汉平',1,NULL,NULL,NULL,1,5),
 (16,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','连强','男',NULL,'纪','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'纪连强',1,NULL,NULL,NULL,1,5),
 (17,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','宝祥','男',NULL,'孙','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'孙宝祥',1,NULL,NULL,NULL,1,5),
 (18,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','洪水','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王洪水',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (19,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','莉萍','男',NULL,'侯','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'侯莉萍',1,NULL,NULL,NULL,1,5),
 (20,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','晓燕','男',NULL,'于','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'于晓燕',1,NULL,NULL,NULL,1,5),
 (21,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','新红','男',NULL,'任','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'任新红',1,NULL,NULL,NULL,1,5),
 (22,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','华伟','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王华伟',1,NULL,NULL,NULL,1,5),
 (23,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','玉军','男',NULL,'倪','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'倪玉军',1,NULL,NULL,NULL,1,5),
 (24,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','彦博','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李彦博',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (25,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','来智','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王来智',1,NULL,NULL,NULL,1,5),
 (26,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','均胜','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李均胜',1,NULL,NULL,NULL,1,5),
 (27,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','宁','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王宁',1,NULL,NULL,NULL,1,5),
 (28,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','蓓','男',NULL,'陈','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'陈蓓',1,NULL,NULL,NULL,1,5),
 (29,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','红英','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王红英',1,NULL,NULL,NULL,1,5),
 (30,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','冬','男',NULL,'冯','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'冯冬',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (31,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','强','男',NULL,'黄','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'黄强',1,NULL,NULL,NULL,1,5),
 (32,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','宝贵','男',NULL,'郑','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'郑宝贵',1,NULL,NULL,NULL,1,5),
 (33,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','彦军','男',NULL,'龚','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'龚彦军',1,NULL,NULL,NULL,1,5),
 (34,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','文正','男',NULL,'徐','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'徐文正',1,NULL,NULL,NULL,1,5),
 (35,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','承明','男',NULL,'胡','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'胡承明',1,NULL,NULL,NULL,1,5),
 (36,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','伟','男',NULL,'韩','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'韩伟',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (37,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','建艇','男',NULL,'郝','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'郝建艇',1,NULL,NULL,NULL,1,5),
 (38,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','艳','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李艳',1,NULL,NULL,NULL,1,5),
 (39,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','玉增','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王玉增',1,NULL,NULL,NULL,1,5),
 (40,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','如兴','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王如兴',1,NULL,NULL,NULL,1,5),
 (41,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','玉刚','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张玉刚',1,NULL,NULL,NULL,1,5),
 (42,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','路','男',NULL,'刘','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'刘路',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (43,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','凤岐','男',NULL,'姬','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'姬凤岐',1,NULL,NULL,NULL,1,5),
 (44,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','敬胜','男',NULL,'魏','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'魏敬胜',1,NULL,NULL,NULL,1,5),
 (45,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','建','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王建',1,NULL,NULL,NULL,1,5),
 (46,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','德福','男',NULL,'刘','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'刘德福',1,NULL,NULL,NULL,1,5),
 (47,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','速成','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王速成',1,NULL,NULL,NULL,1,5),
 (48,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','瑞兴','男',NULL,'杨','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'杨瑞兴',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (49,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','元桐','男',NULL,'郑','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'郑元桐',1,NULL,NULL,NULL,1,5),
 (50,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','建国','男',NULL,'刘','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'刘建国',1,NULL,NULL,NULL,1,5),
 (51,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','军','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李军',1,NULL,NULL,NULL,1,5),
 (52,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','新海','男',NULL,'刘','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'刘新海',1,NULL,NULL,NULL,1,5),
 (53,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','青','男',NULL,'唐','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'唐青',1,NULL,NULL,NULL,1,5),
 (54,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','力','男',NULL,'董','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'董力',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (55,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','英','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王英',1,NULL,NULL,NULL,1,5),
 (56,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','树玲','男',NULL,'孙','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'孙树玲',1,NULL,NULL,NULL,1,5),
 (57,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','青松','男',NULL,'高','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'高青松',1,NULL,NULL,NULL,1,5),
 (58,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','艳','男',NULL,'孙','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'孙艳',1,NULL,NULL,NULL,1,5),
 (59,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','红','男',NULL,'姚','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'姚红',1,NULL,NULL,NULL,1,5),
 (60,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','增谦','男',NULL,'闫','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'闫增谦',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (61,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','风勇','男',NULL,'赵','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'赵风勇',1,NULL,NULL,NULL,1,5),
 (62,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','爱华','男',NULL,'纪','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'纪爱华',1,NULL,NULL,NULL,1,5),
 (63,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','志军','男',NULL,'郭','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'郭志军',1,NULL,NULL,NULL,1,5),
 (64,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','玲','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李玲',1,NULL,NULL,NULL,1,5),
 (65,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','强','男',NULL,'柳','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'柳强',1,NULL,NULL,NULL,1,5),
 (66,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','际水','男',NULL,'郭','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'郭际水',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (67,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','佳木','男',NULL,'谭','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'谭佳木',1,NULL,NULL,NULL,1,5),
 (68,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','英聚','男',NULL,'马','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'马英聚',1,NULL,NULL,NULL,1,5),
 (69,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','钦超','男',NULL,'马','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'马钦超',1,NULL,NULL,NULL,1,5),
 (70,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','新航','男',NULL,'刘','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'刘新航',1,NULL,NULL,NULL,1,5),
 (71,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','志臣','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李志臣',1,NULL,NULL,NULL,1,5),
 (72,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','振鲁','男',NULL,'胡','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'胡振鲁',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (73,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','波','男',NULL,'姜','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'姜波',1,NULL,NULL,NULL,1,5),
 (74,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','勇','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王勇',1,NULL,NULL,NULL,1,5),
 (75,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','加涛','男',NULL,'赵','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'赵加涛',1,NULL,NULL,NULL,1,5),
 (76,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','钦','男',NULL,'董','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'董钦',1,NULL,NULL,NULL,1,5),
 (77,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','斌','男',NULL,'韩','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'韩斌',1,NULL,NULL,NULL,1,5),
 (78,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','常三','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李常三',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (79,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','敬武','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张敬武',1,NULL,NULL,NULL,1,5),
 (80,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','以湖','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张以湖',1,NULL,NULL,NULL,1,5),
 (81,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','非','男',NULL,'颜','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'颜非',1,NULL,NULL,NULL,1,5),
 (82,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','学军','男',NULL,'邢','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'邢学军',1,NULL,NULL,NULL,1,5),
 (83,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','志民','男',NULL,'尚','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'尚志民',1,NULL,NULL,NULL,1,5),
 (84,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','廷新','男',NULL,'董','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'董廷新',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (85,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','真','男',NULL,'郭','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'郭真',1,NULL,NULL,NULL,1,5),
 (86,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','海燕','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王海燕',1,NULL,NULL,NULL,1,5),
 (87,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','道前','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张道前',1,NULL,NULL,NULL,1,5),
 (88,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','春霞','男',NULL,'衣','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'衣春霞',1,NULL,NULL,NULL,1,5),
 (89,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','红雁','男',NULL,'盛','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'盛红雁',1,NULL,NULL,NULL,1,5),
 (90,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','胜利','男',NULL,'季','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'季胜利',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (91,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','忠','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李忠',1,NULL,NULL,NULL,1,5),
 (92,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','汉波','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王汉波',1,NULL,NULL,NULL,1,5),
 (93,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','德书','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王德书',1,NULL,NULL,NULL,1,5),
 (94,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','秀清','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李秀清',1,NULL,NULL,NULL,1,5),
 (95,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','汝峰','男',NULL,'苗','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'苗汝峰',1,NULL,NULL,NULL,1,5),
 (96,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','冬梅','男',NULL,'何','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'何冬梅',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (97,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','富琛','男',NULL,'杨','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'杨富琛',1,NULL,NULL,NULL,1,5),
 (98,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','祖科','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王祖科',1,NULL,NULL,NULL,1,5),
 (99,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','文新','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张文新',1,NULL,NULL,NULL,1,5),
 (100,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','惠香','男',NULL,'刘','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'刘惠香',1,NULL,NULL,NULL,1,5),
 (101,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','浩远','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王浩远',1,NULL,NULL,NULL,1,5),
 (102,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','敏','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王敏',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (103,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','洪岩','男',NULL,'朱','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'朱洪岩',1,NULL,NULL,NULL,1,5),
 (104,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','树森','男',NULL,'孙','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'孙树森',1,NULL,NULL,NULL,1,5),
 (105,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','海屿','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李海屿',1,NULL,NULL,NULL,1,5),
 (106,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','兆云','男',NULL,'丁','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'丁兆云',1,NULL,NULL,NULL,1,5),
 (107,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','春和','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王春和',1,NULL,NULL,NULL,1,5),
 (108,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','允褔','男',NULL,'吴','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'吴允褔',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (109,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','有华','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王有华',1,NULL,NULL,NULL,1,5),
 (110,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','述褔','男',NULL,'魏','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'魏述褔',1,NULL,NULL,NULL,1,5),
 (111,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','贤勤','男',NULL,'范','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'范贤勤',1,NULL,NULL,NULL,1,5),
 (112,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','德山','男',NULL,'刘','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'刘德山',1,NULL,NULL,NULL,1,5),
 (113,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','峰','男',NULL,'藤','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'藤峰',1,NULL,NULL,NULL,1,5),
 (114,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','家荣','男',NULL,'董','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'董家荣',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (115,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','粤','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李粤',1,NULL,NULL,NULL,1,5),
 (116,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','燕','男',NULL,'曹','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'曹燕',1,NULL,NULL,NULL,1,5),
 (117,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','京南','男',NULL,'董','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'董京南',1,NULL,NULL,NULL,1,5),
 (118,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','艳','男',NULL,'刘','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'刘艳',1,NULL,NULL,NULL,1,5),
 (119,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','小媛','男',NULL,'朱','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'朱小媛',1,NULL,NULL,NULL,1,5),
 (120,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','衍铮','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李衍铮',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (121,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','乐平','男',NULL,'孙','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'孙乐平',1,NULL,NULL,NULL,1,5),
 (122,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','志东','男',NULL,'徐','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'徐志东',1,NULL,NULL,NULL,1,5),
 (123,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','红','男',NULL,'杜','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'杜红',1,NULL,NULL,NULL,1,5),
 (124,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','刚','男',NULL,'褚','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'褚刚',1,NULL,NULL,NULL,1,5),
 (125,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','翠华','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李翠华',1,NULL,NULL,NULL,1,5),
 (126,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','云洛','男',NULL,'徐','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'徐云洛',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (127,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','国恩','男',NULL,'曹','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'曹国恩',1,NULL,NULL,NULL,1,5),
 (128,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','安红','男',NULL,'马','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'马安红',1,NULL,NULL,NULL,1,5),
 (129,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','钢','男',NULL,'孙','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'孙钢',1,NULL,NULL,NULL,1,5),
 (130,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','小齐','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李小齐',1,NULL,NULL,NULL,1,5),
 (131,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','倩','男',NULL,'刘','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'刘倩',1,NULL,NULL,NULL,1,5),
 (132,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','桂民','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王桂民',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (133,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','时','男',NULL,'贺','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'贺时',1,NULL,NULL,NULL,1,5),
 (134,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','令岐','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李令岐',1,NULL,NULL,NULL,1,5),
 (135,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','继海','男',NULL,'陈','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'陈继海',1,NULL,NULL,NULL,1,5),
 (136,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','新','男',NULL,'路','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'路新',1,NULL,NULL,NULL,1,5),
 (137,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','德慧','男',NULL,'车','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'车德慧',1,NULL,NULL,NULL,1,5),
 (138,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','鹏','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张鹏',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (139,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','寒','男',NULL,'高','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'高寒',1,NULL,NULL,NULL,1,5),
 (140,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','富东','男',NULL,'刘','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'刘富东',1,NULL,NULL,NULL,1,5),
 (141,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','书范','男',NULL,'康','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'康书范',1,NULL,NULL,NULL,1,5),
 (142,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','玉芹','男',NULL,'孙','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'孙玉芹',1,NULL,NULL,NULL,1,5),
 (143,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','吉海','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李吉海',1,NULL,NULL,NULL,1,5),
 (144,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','宝东','男',NULL,'霍','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'霍宝东',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (145,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','义','男',NULL,'郑','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'郑义',1,NULL,NULL,NULL,1,5),
 (146,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','新杰','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王新杰',1,NULL,NULL,NULL,1,5),
 (147,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','明','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张明',1,NULL,NULL,NULL,1,5),
 (148,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','长勇','男',NULL,'章','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'章长勇',1,NULL,NULL,NULL,1,5),
 (149,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','汉杰','男',NULL,'刘','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'刘汉杰',1,NULL,NULL,NULL,1,5),
 (150,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','如英','男',NULL,'袁','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'袁如英',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (151,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','慧萍','男',NULL,'马','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'马慧萍',1,NULL,NULL,NULL,1,5),
 (152,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','宝兴','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王宝兴',1,NULL,NULL,NULL,1,5),
 (153,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','泽林','男',NULL,'单','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'单泽林',1,NULL,NULL,NULL,1,5),
 (154,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','绍辉','男',NULL,'高','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'高绍辉',1,NULL,NULL,NULL,1,5),
 (155,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','世杰','男',NULL,'臧','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'臧世杰',1,NULL,NULL,NULL,1,5),
 (156,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','文金','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张文金',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (157,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','九盟','男',NULL,'马','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'马九盟',1,NULL,NULL,NULL,1,5),
 (158,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','光','男',NULL,'阳','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'阳光',1,NULL,NULL,NULL,1,5),
 (159,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','宝祥','男',NULL,'高','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'高宝祥',1,NULL,NULL,NULL,1,5),
 (160,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','维仁','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张维仁',1,NULL,NULL,NULL,1,5),
 (161,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','向平','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张向平',1,NULL,NULL,NULL,1,5),
 (162,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','振华','男',NULL,'吕','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'吕振华',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (163,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','思辰','男',NULL,'栾','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'栾思辰',1,NULL,NULL,NULL,1,5),
 (164,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','玉坚','男',NULL,'解','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'解玉坚',1,NULL,NULL,NULL,1,5),
 (165,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','跃华','男',NULL,'玄','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'玄跃华',1,NULL,NULL,NULL,1,5),
 (166,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','景华','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王景华',1,NULL,NULL,NULL,1,5),
 (167,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','春山','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李春山',1,NULL,NULL,NULL,1,5),
 (168,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','志剑','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王志剑',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (169,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','培杰','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李培杰',1,NULL,NULL,NULL,1,5),
 (170,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','浩','男',NULL,'冯','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'冯浩',1,NULL,NULL,NULL,1,5),
 (171,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','胜利','男',NULL,'刘','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'刘胜利',1,NULL,NULL,NULL,1,5),
 (172,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','杰','男',NULL,'米','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'米杰',1,NULL,NULL,NULL,1,5),
 (173,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','建华','男',NULL,'申','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'申建华',1,NULL,NULL,NULL,1,5),
 (174,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','剑峰','男',NULL,'杨','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'杨剑峰',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (175,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','涛','男',NULL,'宋','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'宋涛',1,NULL,NULL,NULL,1,5),
 (176,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','明磊','男',NULL,'于','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'于明磊',1,NULL,NULL,NULL,1,5),
 (177,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','艳','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李艳',1,NULL,NULL,NULL,1,5),
 (178,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','军','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李军',1,NULL,NULL,NULL,1,5),
 (179,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','锡义','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李锡义',1,NULL,NULL,NULL,1,5),
 (180,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','潍','男',NULL,'韩','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'韩潍',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (181,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','维忠','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张维忠',1,NULL,NULL,NULL,1,5),
 (182,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','强民','男',NULL,'尹','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'尹强民',1,NULL,NULL,NULL,1,5),
 (183,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','永华','男',NULL,'叶','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'叶永华',1,NULL,NULL,NULL,1,5),
 (184,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','兴玲','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王兴玲',1,NULL,NULL,NULL,1,5),
 (185,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','荣榜','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张荣榜',1,NULL,NULL,NULL,1,5),
 (186,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','胜兰','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李胜兰',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (187,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','守玉','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王守玉',1,NULL,NULL,NULL,1,5),
 (188,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','淑江','男',NULL,'赵','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'赵淑江',1,NULL,NULL,NULL,1,5),
 (189,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','树亭','男',NULL,'汤','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'汤树亭',1,NULL,NULL,NULL,1,5),
 (190,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','维丽','男',NULL,'刘','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'刘维丽',1,NULL,NULL,NULL,1,5),
 (191,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','进贤','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张进贤',1,NULL,NULL,NULL,1,5),
 (192,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','守袆','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张守袆',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (193,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','明钊','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王明钊',1,NULL,NULL,NULL,1,5),
 (194,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','光玉','男',NULL,'孟','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'孟光玉',1,NULL,NULL,NULL,1,5),
 (195,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','晓华','男',NULL,'杨','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'杨晓华',1,NULL,NULL,NULL,1,5),
 (196,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','国祥','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李国祥',1,NULL,NULL,NULL,1,5),
 (197,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','卫','男',NULL,'陈','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'陈卫',1,NULL,NULL,NULL,1,5),
 (198,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','玉民','男',NULL,'刘','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'刘玉民',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (199,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','静','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张静',1,NULL,NULL,NULL,1,5),
 (200,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','长河','男',NULL,'胥','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'胥长河',1,NULL,NULL,NULL,1,5),
 (201,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','华杰','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王华杰',1,NULL,NULL,NULL,1,5),
 (202,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','红军','男',NULL,'吴','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'吴红军',1,NULL,NULL,NULL,1,5),
 (203,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','刚','男',NULL,'程','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'程刚',1,NULL,NULL,NULL,1,5),
 (204,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','焱','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张焱',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (205,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','晶晶','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王晶晶',1,NULL,NULL,NULL,1,5),
 (206,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','少华','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王少华',1,NULL,NULL,NULL,1,5),
 (207,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','之生','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李之生',1,NULL,NULL,NULL,1,5),
 (208,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','霞','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王霞',1,NULL,NULL,NULL,1,5),
 (209,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','学平','男',NULL,'陈','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'陈学平',1,NULL,NULL,NULL,1,5),
 (210,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','宝成','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李宝成',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (211,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','吉兴','男',NULL,'孙','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'孙吉兴',1,NULL,NULL,NULL,1,5),
 (212,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','小明','男',NULL,'刘','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'刘小明',1,NULL,NULL,NULL,1,5),
 (213,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','梅子','男',NULL,'陈','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'陈梅子',1,NULL,NULL,NULL,1,5),
 (214,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','吉永','男',NULL,'苏','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'苏吉永',1,NULL,NULL,NULL,1,5),
 (215,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','玉华','男',NULL,'沈','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'沈玉华',1,NULL,NULL,NULL,1,5),
 (216,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','虎','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李虎',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (217,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','念臣','男',NULL,'杨','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'杨念臣',1,NULL,NULL,NULL,1,5),
 (218,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','延褔','男',NULL,'徐','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'徐延褔',1,NULL,NULL,NULL,1,5),
 (219,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','敬国','男',NULL,'任','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'任敬国',1,NULL,NULL,NULL,1,5),
 (220,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','兴水','男',NULL,'周','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'周兴水',1,NULL,NULL,NULL,1,5),
 (221,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','月军','男',NULL,'殷','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'殷月军',1,NULL,NULL,NULL,1,5),
 (222,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','海波','男',NULL,'吕','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'吕海波',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (223,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','超','男',NULL,'刘','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'刘超',1,NULL,NULL,NULL,1,5),
 (224,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','敏进','男',NULL,'徐','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'徐敏进',1,NULL,NULL,NULL,1,5),
 (225,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','飞','男',NULL,'马','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'马飞',1,NULL,NULL,NULL,1,5),
 (226,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','军','男',NULL,'成','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'成军',1,NULL,NULL,NULL,1,5),
 (227,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','兴杰','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张兴杰',1,NULL,NULL,NULL,1,5),
 (228,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','书峰','男',NULL,'史','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'史书峰',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (229,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','之店','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张之店',1,NULL,NULL,NULL,1,5),
 (230,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','卫东','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王卫东',1,NULL,NULL,NULL,1,5),
 (231,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','渝','男',NULL,'于','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'于渝',1,NULL,NULL,NULL,1,5),
 (232,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','健康','男',NULL,'赵','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'赵健康',1,NULL,NULL,NULL,1,5),
 (233,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','晓云','男',NULL,'侯','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'侯晓云',1,NULL,NULL,NULL,1,5),
 (234,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','灏','男',NULL,'蒋','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'蒋灏',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (235,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','版人','男',NULL,'排','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'排版人员',1,NULL,NULL,NULL,1,5),
 (236,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','青','男',NULL,'尚','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'尚青',1,NULL,NULL,NULL,1,5),
 (237,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','连友','男',NULL,'段','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'段连友',1,NULL,NULL,NULL,1,5),
 (238,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','忠贤','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李忠贤',1,NULL,NULL,NULL,1,5),
 (239,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','启伟','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张启伟',1,NULL,NULL,NULL,1,5),
 (240,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','辉','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张辉',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (241,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','执铨','男',NULL,'郭','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'郭执铨',1,NULL,NULL,NULL,1,5),
 (242,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','英','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李英',1,NULL,NULL,NULL,1,5),
 (243,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','阳林','男',NULL,'于','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'于阳林',1,NULL,NULL,NULL,1,5),
 (244,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','丽军','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王丽军',1,NULL,NULL,NULL,1,5),
 (245,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','建中','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王建中',1,NULL,NULL,NULL,1,5),
 (246,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','和民','男',NULL,'牛','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'牛和民',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (247,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','彬','男',NULL,'鞠','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'鞠彬',1,NULL,NULL,NULL,1,5),
 (248,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','历平','男',NULL,'方','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'方历平',1,NULL,NULL,NULL,1,5),
 (249,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','宗强','男',NULL,'朱','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'朱宗强',1,NULL,NULL,NULL,1,5),
 (250,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','涛','男',NULL,'邹','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'邹涛',1,NULL,NULL,NULL,1,5),
 (251,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','继忠','男',NULL,'袁','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'袁继忠',1,NULL,NULL,NULL,1,5),
 (252,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','学军','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李学军',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (253,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','洪云','男',NULL,'姜','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'姜洪云',1,NULL,NULL,NULL,1,5),
 (254,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','庆淼','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王庆淼',1,NULL,NULL,NULL,1,5),
 (255,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','咏梅','男',NULL,'姜','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'姜咏梅',1,NULL,NULL,NULL,1,5),
 (256,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','振加','男',NULL,'宋','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'宋振加',1,NULL,NULL,NULL,1,5),
 (257,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','世彬','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王世彬',1,NULL,NULL,NULL,1,5),
 (258,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','静','男',NULL,'田','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'田静',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (259,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','延柱','男',NULL,'许','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'许延柱',1,NULL,NULL,NULL,1,5),
 (260,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','体私营协会排版人','男',NULL,'个','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'个体私营协会排版人',1,NULL,NULL,NULL,1,5),
 (261,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','文亮','男',NULL,'陈','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'陈文亮',1,NULL,NULL,NULL,1,5),
 (262,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','汝太','男',NULL,'贠','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'贠汝太',1,NULL,NULL,NULL,1,5),
 (263,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','海亮','男',NULL,'周','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'周海亮',1,NULL,NULL,NULL,1,5),
 (264,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','序水','男',NULL,'赵','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'赵序水',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (265,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','卫民','男',NULL,'武','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'武卫民',1,NULL,NULL,NULL,1,5),
 (266,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','云鹏','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王云鹏',1,NULL,NULL,NULL,1,5),
 (267,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','斌','男',NULL,'刘','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'刘斌',1,NULL,NULL,NULL,1,5),
 (268,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','钟','男',NULL,'魏','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'魏钟',1,NULL,NULL,NULL,1,5),
 (269,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','庆敏','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王庆敏',1,NULL,NULL,NULL,1,5),
 (270,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','洪旭','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李洪旭',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (271,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','新成','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王新成',1,NULL,NULL,NULL,1,5),
 (272,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','阳泗','男',NULL,'许','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'许阳泗',1,NULL,NULL,NULL,1,5),
 (273,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','店刚','男',NULL,'于','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'于店刚',1,NULL,NULL,NULL,1,5),
 (274,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','刚','男',NULL,'牟','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'牟刚',1,NULL,NULL,NULL,1,5),
 (275,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','沛','男',NULL,'洪','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'洪沛',1,NULL,NULL,NULL,1,5),
 (276,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','靖','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王靖',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (277,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','文青','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李文青',1,NULL,NULL,NULL,1,5),
 (278,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','致远','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王致远',1,NULL,NULL,NULL,1,5),
 (279,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','雁','男',NULL,'岳','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'岳雁',1,NULL,NULL,NULL,1,5),
 (280,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','莹','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王莹',1,NULL,NULL,NULL,1,5),
 (281,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','登稳','男',NULL,'巩','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'巩登稳',1,NULL,NULL,NULL,1,5),
 (282,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','平','男',NULL,'薛','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'薛平',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (283,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','文峰','男',NULL,'宋','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'宋文峰',1,NULL,NULL,NULL,1,5),
 (284,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','晓盟','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张晓盟',1,NULL,NULL,NULL,1,5),
 (285,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','有文','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张有文',1,NULL,NULL,NULL,1,5),
 (286,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','协排版人员','男',NULL,'消','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'消协排版人员',1,NULL,NULL,NULL,1,5),
 (287,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','洪涛','男',NULL,'胡','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'胡洪涛',1,NULL,NULL,NULL,1,5),
 (288,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','顺祥','男',NULL,'宋','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'宋顺祥',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (289,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','忠林','男',NULL,'夏','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'夏忠林',1,NULL,NULL,NULL,1,5),
 (290,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','建民','男',NULL,'左','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'左建民',1,NULL,NULL,NULL,1,5),
 (291,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','培顺','男',NULL,'赵','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'赵培顺',1,NULL,NULL,NULL,1,5),
 (292,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','宗国','男',NULL,'姚','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'姚宗国',1,NULL,NULL,NULL,1,5),
 (293,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','明杰','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李明杰',1,NULL,NULL,NULL,1,5),
 (294,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','安清','男',NULL,'郑','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'郑安清',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (295,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','金玲','男',NULL,'包','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'包金玲',1,NULL,NULL,NULL,1,5),
 (296,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','鲁天','男',NULL,'杨','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'杨鲁天',1,NULL,NULL,NULL,1,5),
 (297,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','风','男',NULL,'徐','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'徐风',1,NULL,NULL,NULL,1,5),
 (298,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','绪连','男',NULL,'鲁','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'鲁绪连',1,NULL,NULL,NULL,1,5),
 (299,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','玉国','男',NULL,'付','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'付玉国',1,NULL,NULL,NULL,1,5),
 (300,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','宝民','男',NULL,'郭','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'郭宝民',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (301,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','繁玉','男',NULL,'孟','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'孟繁玉',1,NULL,NULL,NULL,1,5),
 (302,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','廷广','男',NULL,'颜','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'颜廷广',1,NULL,NULL,NULL,1,5),
 (303,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','晓翠','男',NULL,'初','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'初晓翠',1,NULL,NULL,NULL,1,5),
 (304,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','志军','男',NULL,'周','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'周志军',1,NULL,NULL,NULL,1,5),
 (305,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','忠发','男',NULL,'许','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'许忠发',1,NULL,NULL,NULL,1,5),
 (306,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','凤英','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王凤英',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (307,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','明','男',NULL,'黄','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'黄明',1,NULL,NULL,NULL,1,5),
 (308,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','斌','男',NULL,'陈','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'陈斌',1,NULL,NULL,NULL,1,5),
 (309,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','国维','男',NULL,'徐','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'徐国维',1,NULL,NULL,NULL,1,5),
 (310,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','政','男',NULL,'姜','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'姜政',1,NULL,NULL,NULL,1,5),
 (311,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','丽旺','男',NULL,'顾','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'顾丽旺',1,NULL,NULL,NULL,1,5),
 (312,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','爱华','男',NULL,'陈','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'陈爱华',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (313,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','宁','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李宁',1,NULL,NULL,NULL,1,5),
 (314,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','欣','男',NULL,'孙','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'孙欣',1,NULL,NULL,NULL,1,5),
 (315,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','波','男',NULL,'杨','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'杨波',1,NULL,NULL,NULL,1,5),
 (316,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','息中心排版人','男',NULL,'信','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'信息中心排版人',1,NULL,NULL,NULL,1,5),
 (317,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','长智','男',NULL,'邹','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'邹长智',1,NULL,NULL,NULL,1,5),
 (318,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','立军','男',NULL,'韩','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'韩立军',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (319,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','伟','男',NULL,'孙','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'孙伟',1,NULL,NULL,NULL,1,5),
 (320,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','玉江','男',NULL,'袁','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'袁玉江',1,NULL,NULL,NULL,1,5),
 (321,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','小洁','男',NULL,'王','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王小洁',1,NULL,NULL,NULL,1,5),
 (322,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','玉凤','男',NULL,'李','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'李玉凤',1,NULL,NULL,NULL,1,5),
 (323,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','根月','男',NULL,'陈','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'陈根月',1,NULL,NULL,NULL,1,5),
 (324,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','凤桐','男',NULL,'刘','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'刘凤桐',1,NULL,NULL,NULL,1,5);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (325,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','军','男',NULL,'董','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'董军',1,NULL,NULL,NULL,1,5),
 (326,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','志民','男',NULL,'张','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'张志民',1,NULL,NULL,NULL,1,5),
 (327,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','君','男',NULL,'成','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'成君',1,NULL,NULL,NULL,1,5),
 (328,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','嘉泉','男',NULL,'高','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'高嘉泉',1,NULL,NULL,NULL,1,5),
 (329,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','南市局','男',NULL,'济','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'济南市局',1,NULL,NULL,NULL,1,5),
 (330,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','岛市局','男',NULL,'青','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'青岛市局',1,NULL,NULL,NULL,1,6);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (331,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','博市局','男',NULL,'淄','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'淄博市局',1,NULL,NULL,NULL,1,14),
 (332,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','庄市局','男',NULL,'枣','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'枣庄市局',1,NULL,NULL,NULL,1,19),
 (333,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','营市局','男',NULL,'东','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'东营市局',1,NULL,NULL,NULL,1,18),
 (334,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','台市局','男',NULL,'烟','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'烟台市局',1,NULL,NULL,NULL,1,7),
 (335,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','坊市局','男',NULL,'潍','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'潍坊市局',1,NULL,NULL,NULL,1,9),
 (336,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','宁市局','男',NULL,'济','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'济宁市局',1,NULL,NULL,NULL,1,12);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (337,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','安市局','男',NULL,'泰','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'泰安市局',1,NULL,NULL,NULL,1,21),
 (338,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','海市局','男',NULL,'威','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'威海市局',1,NULL,NULL,NULL,1,8),
 (339,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','照市局','男',NULL,'日','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'日照市局',1,NULL,NULL,NULL,1,10),
 (340,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','芜市局','男',NULL,'莱','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'莱芜市局',1,NULL,NULL,NULL,1,20),
 (341,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','沂市局','男',NULL,'临','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'临沂市局',1,NULL,NULL,NULL,1,11),
 (342,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','州市局','男',NULL,'德','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'德州市局',1,NULL,NULL,NULL,1,16);
INSERT INTO `p_persons` (`id`,`address`,`age`,`birthday`,`card_id`,`code`,`email`,`first_name`,`gender`,`homepage`,`last_name`,`marriage`,`mobilephone`,`msn`,`picture`,`political_status`,`postcode`,`qq`,`qualifications`,`realname`,`sort`,`start_date`,`telephone`,`vocation`,`org_id`,`region_id`) VALUES 
 (343,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','城市局','男',NULL,'聊','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'聊城市局',1,NULL,NULL,NULL,1,15),
 (344,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','州市局','男',NULL,'滨','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'滨州市局',1,NULL,NULL,NULL,1,17),
 (345,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','泽市局','男',NULL,'菏','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'菏泽市局',1,NULL,NULL,NULL,1,13),
 (346,'',NULL,'1899-11-30 00:00:00',NULL,NULL,'','绍祥','男',NULL,'丁','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'丁绍祥',1,NULL,NULL,NULL,1,5);
/*!40000 ALTER TABLE `p_persons` ENABLE KEYS */;


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
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `name` (`name`),
  KEY `FKAB3CEF30CCEA14BA` (`parent_id`),
  KEY `FKAB3CEF30188B6575` (`parent_id`),
  CONSTRAINT `FKAB3CEF30188B6575` FOREIGN KEY (`parent_id`) REFERENCES `p_regions` (`id`),
  CONSTRAINT `FKAB3CEF30CCEA14BA` FOREIGN KEY (`parent_id`) REFERENCES `p_regions` (`id`)
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
  KEY `FKA3DD66C17FF91870` (`role_id`),
  KEY `FKA3DD66C15AA9A30` (`function_id`),
  KEY `FKA3DD66C1239D5BEB` (`role_id`),
  KEY `FKA3DD66C1EE3AA82B` (`function_id`),
  CONSTRAINT `FKA3DD66C1239D5BEB` FOREIGN KEY (`role_id`) REFERENCES `p_roles` (`id`),
  CONSTRAINT `FKA3DD66C15AA9A30` FOREIGN KEY (`function_id`) REFERENCES `p_functions` (`function_id`),
  CONSTRAINT `FKA3DD66C17FF91870` FOREIGN KEY (`role_id`) REFERENCES `p_roles` (`id`),
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `note` varchar(100) DEFAULT NULL,
  `title` varchar(32) DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKCD50DE2E834B2E04` (`org_id`),
  KEY `FKCD50DE2ECAA30F29` (`org_id`),
  CONSTRAINT `FKCD50DE2E834B2E04` FOREIGN KEY (`org_id`) REFERENCES `p_orgs` (`id`),
  CONSTRAINT `FKCD50DE2ECAA30F29` FOREIGN KEY (`org_id`) REFERENCES `p_orgs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `p_roles`
--

/*!40000 ALTER TABLE `p_roles` DISABLE KEYS */;
INSERT INTO `p_roles` (`id`,`name`,`note`,`title`,`org_id`) VALUES 
 (1,'admin',NULL,'管理员',1);
/*!40000 ALTER TABLE `p_roles` ENABLE KEYS */;


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
  CONSTRAINT `FK11D94878C8C81FCB` FOREIGN KEY (`user_id`) REFERENCES `p_users` (`id`),
  CONSTRAINT `FK11D94878239D5BEB` FOREIGN KEY (`role_id`) REFERENCES `p_roles` (`id`)
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
  `password` varchar(32) DEFAULT NULL,
  `validate_code` varchar(255) DEFAULT NULL,
  `username` varchar(32) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `credentials_non_expired` bit(1) DEFAULT NULL,
  `login_attempts` int(11) DEFAULT NULL,
  `non_expired` bit(1) DEFAULT NULL,
  `non_locked` bit(1) DEFAULT NULL,
  `password_code` varchar(255) DEFAULT NULL,
  `person_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FKCD7CDD79CC9AC34B` (`person_id`),
  CONSTRAINT `FKCD7CDD79CC9AC34B` FOREIGN KEY (`person_id`) REFERENCES `p_persons` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `p_users`
--

/*!40000 ALTER TABLE `p_users` DISABLE KEYS */;
INSERT INTO `p_users` (`concurrent_max`,`email`,`enabled`,`hint_answer`,`hint_question`,`last_login_ip`,`last_login_time`,`login_attemptz_times`,`login_attempts_max`,`login_times`,`password`,`validate_code`,`username`,`age`,`id`,`credentials_non_expired`,`login_attempts`,`non_expired`,`non_locked`,`password_code`,`person_id`) VALUES 
 (10,NULL,1,NULL,NULL,'127.0.0.1','2009-04-03 18:23:25',100,100,72,'21232f297a57a5a743894a0e4a801fc3',NULL,'admin',1,1,0x01,1,0x01,0x01,NULL,1);
/*!40000 ALTER TABLE `p_users` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
