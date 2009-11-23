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
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO,ANSI_QUOTES,MYSQL323' */;


--
-- Create schema frame
--

CREATE DATABASE IF NOT EXISTS frame;
USE frame;

--
-- Definition of table "cms_article"
--

DROP TABLE IF EXISTS "cms_article";
CREATE TABLE "cms_article" (
  "article_id" int(11) NOT NULL AUTO_INCREMENT,
  "created" datetime DEFAULT NULL,
  "keywords" varchar(255) DEFAULT NULL,
  "summary" varchar(255) DEFAULT NULL,
  "tags" varchar(255) DEFAULT NULL,
  "title" varchar(255) DEFAULT NULL,
  "updated" datetime DEFAULT NULL,
  "category_id" int(11) DEFAULT NULL,
  "creater_id" int(11) DEFAULT NULL,
  "updater_id" int(11) DEFAULT NULL,
  PRIMARY KEY ("article_id"),
  KEY "FK935C9C00233738C0" ("creater_id"),
  KEY "FK935C9C00BFF7220D" ("updater_id"),
  KEY "FK935C9C0049536E2A" ("category_id"),
  CONSTRAINT "FK935C9C00233738C0" FOREIGN KEY ("creater_id") REFERENCES "p_users" ("user_id"),
  CONSTRAINT "FK935C9C0049536E2A" FOREIGN KEY ("category_id") REFERENCES "cms_category" ("category_id"),
  CONSTRAINT "FK935C9C00BFF7220D" FOREIGN KEY ("updater_id") REFERENCES "p_users" ("user_id")
) TYPE=InnoDB;

--
-- Dumping data for table "cms_article"
--

/*!40000 ALTER TABLE "cms_article" DISABLE KEYS */;
/*!40000 ALTER TABLE "cms_article" ENABLE KEYS */;


--
-- Definition of table "cms_article_click"
--

DROP TABLE IF EXISTS "cms_article_click";
CREATE TABLE "cms_article_click" (
  "click_id" int(11) NOT NULL AUTO_INCREMENT,
  "article_id" int(11) DEFAULT NULL,
  PRIMARY KEY ("click_id"),
  KEY "FK9239C48948D85ACA" ("article_id"),
  CONSTRAINT "FK9239C48948D85ACA" FOREIGN KEY ("article_id") REFERENCES "cms_article" ("article_id")
) TYPE=InnoDB;

--
-- Dumping data for table "cms_article_click"
--

/*!40000 ALTER TABLE "cms_article_click" DISABLE KEYS */;
/*!40000 ALTER TABLE "cms_article_click" ENABLE KEYS */;


--
-- Definition of table "cms_article_content"
--

DROP TABLE IF EXISTS "cms_article_content";
CREATE TABLE "cms_article_content" (
  "article_id" int(11) NOT NULL AUTO_INCREMENT,
  "value" longtext,
  PRIMARY KEY ("article_id")
) TYPE=InnoDB;

--
-- Dumping data for table "cms_article_content"
--

/*!40000 ALTER TABLE "cms_article_content" DISABLE KEYS */;
/*!40000 ALTER TABLE "cms_article_content" ENABLE KEYS */;


--
-- Definition of table "cms_attribute"
--

DROP TABLE IF EXISTS "cms_attribute";
CREATE TABLE "cms_attribute" (
  "attribute_id" int(11) NOT NULL AUTO_INCREMENT,
  "attribute_label" varchar(255) DEFAULT NULL,
  "attribute_name" varchar(255) DEFAULT NULL,
  "model_id" int(11) DEFAULT NULL,
  PRIMARY KEY ("attribute_id"),
  KEY "FKE13DF266037BDEA" ("model_id"),
  CONSTRAINT "FKE13DF266037BDEA" FOREIGN KEY ("model_id") REFERENCES "cms_model" ("model_id")
) TYPE=InnoDB AUTO_INCREMENT=26;

--
-- Dumping data for table "cms_attribute"
--

/*!40000 ALTER TABLE "cms_attribute" DISABLE KEYS */;
INSERT INTO "cms_attribute" ("attribute_id","attribute_label","attribute_name","model_id") VALUES 
 (17,'编号','articleId',13),
 (18,'标题','title',13),
 (19,'内容','content',13),
 (20,'创建人','creater',13),
 (21,'修改人','updater',13),
 (22,'创建时间','created',13),
 (23,'修改时间','updated',13),
 (24,'编号','pageId',14),
 (25,'页面内容','pageContent',14);
/*!40000 ALTER TABLE "cms_attribute" ENABLE KEYS */;


--
-- Definition of table "cms_bulletin"
--

DROP TABLE IF EXISTS "cms_bulletin";
CREATE TABLE "cms_bulletin" (
  "bulletin_id" int(11) NOT NULL AUTO_INCREMENT,
  "created" datetime DEFAULT NULL,
  "creater" tinyblob,
  "keywords" varchar(255) DEFAULT NULL,
  "summary" varchar(255) DEFAULT NULL,
  "updated" datetime DEFAULT NULL,
  "updater" tinyblob,
  PRIMARY KEY ("bulletin_id")
) TYPE=InnoDB;

--
-- Dumping data for table "cms_bulletin"
--

/*!40000 ALTER TABLE "cms_bulletin" DISABLE KEYS */;
/*!40000 ALTER TABLE "cms_bulletin" ENABLE KEYS */;


--
-- Definition of table "cms_bulletin_content"
--

DROP TABLE IF EXISTS "cms_bulletin_content";
CREATE TABLE "cms_bulletin_content" (
  "bulletin_id" int(11) NOT NULL AUTO_INCREMENT,
  "_value" longtext,
  PRIMARY KEY ("bulletin_id")
) TYPE=InnoDB;

--
-- Dumping data for table "cms_bulletin_content"
--

/*!40000 ALTER TABLE "cms_bulletin_content" DISABLE KEYS */;
/*!40000 ALTER TABLE "cms_bulletin_content" ENABLE KEYS */;


--
-- Definition of table "cms_category"
--

DROP TABLE IF EXISTS "cms_category";
CREATE TABLE "cms_category" (
  "category_id" int(11) NOT NULL AUTO_INCREMENT,
  "allowpost" bit(1) DEFAULT NULL,
  "description" longtext,
  "keywords" varchar(255) DEFAULT NULL,
  "name" varchar(255) NOT NULL,
  "sequence" int(11) NOT NULL,
  "is_show" bit(1) NOT NULL,
  "title" varchar(255) DEFAULT NULL,
  "type" varchar(255) NOT NULL,
  "url" varchar(255) NOT NULL,
  "model_id" int(11) DEFAULT NULL,
  "parent_id" int(11) DEFAULT NULL,
  "site_id" int(11) DEFAULT NULL,
  PRIMARY KEY ("category_id"),
  KEY "FK2478CF34691172FE" ("parent_id"),
  KEY "FK2478CF346037BDEA" ("model_id"),
  KEY "FK2478CF3489BB758A" ("site_id")
) TYPE=InnoDB AUTO_INCREMENT=6;

--
-- Dumping data for table "cms_category"
--

/*!40000 ALTER TABLE "cms_category" DISABLE KEYS */;
INSERT INTO "cms_category" ("category_id","allowpost","description","keywords","name","sequence","is_show","title","type","url","model_id","parent_id","site_id") VALUES 
 (2,0x01,'aaa','sss','新闻',0,0x01,'新闻信息','co','cms/news',13,NULL,1),
 (5,0x01,'aaa','aaa','国内新闻',1,0x00,'国内新闻','co','cms/news/gnxw',13,2,1);
/*!40000 ALTER TABLE "cms_category" ENABLE KEYS */;


--
-- Definition of table "cms_function"
--

DROP TABLE IF EXISTS "cms_function";
CREATE TABLE "cms_function" (
  "function_id" int(11) NOT NULL AUTO_INCREMENT,
  "function_name" varchar(255) DEFAULT NULL,
  "url" varchar(255) DEFAULT NULL,
  "model_id" int(11) DEFAULT NULL,
  "is_show" bit(1) DEFAULT NULL,
  PRIMARY KEY ("function_id"),
  KEY "FK73C5860E6037BDEA" ("model_id"),
  CONSTRAINT "FK73C5860E6037BDEA" FOREIGN KEY ("model_id") REFERENCES "cms_model" ("model_id")
) TYPE=InnoDB AUTO_INCREMENT=14;

--
-- Dumping data for table "cms_function"
--

/*!40000 ALTER TABLE "cms_function" DISABLE KEYS */;
INSERT INTO "cms_function" ("function_id","function_name","url","model_id","is_show") VALUES 
 (9,'添加文章','cms/article/add.jhtm',13,0x01),
 (10,'管理文章','cms/article/list.jhtm',13,0x01),
 (11,'删除文章','cms/article/delete.jhtm',13,0x01),
 (12,'保存文章','cms/article/save.jhtm',13,0x01),
 (13,'页面修改','/cms/onepage/index.jhtm',14,0x00);
/*!40000 ALTER TABLE "cms_function" ENABLE KEYS */;


--
-- Definition of table "cms_member_group"
--

DROP TABLE IF EXISTS "cms_member_group";
CREATE TABLE "cms_member_group" (
  "group_id" int(11) NOT NULL AUTO_INCREMENT,
  "group_name" varchar(32) DEFAULT NULL,
  PRIMARY KEY ("group_id"),
  UNIQUE KEY "group_name" ("group_name")
) TYPE=InnoDB AUTO_INCREMENT=3;

--
-- Dumping data for table "cms_member_group"
--

/*!40000 ALTER TABLE "cms_member_group" DISABLE KEYS */;
INSERT INTO "cms_member_group" ("group_id","group_name") VALUES 
 (1,'fff'),
 (2,'ggggggg');
/*!40000 ALTER TABLE "cms_member_group" ENABLE KEYS */;


--
-- Definition of table "cms_model"
--

DROP TABLE IF EXISTS "cms_model";
CREATE TABLE "cms_model" (
  "model_id" int(11) NOT NULL AUTO_INCREMENT,
  "model_name" varchar(255) DEFAULT NULL,
  "table_name" varchar(255) DEFAULT NULL,
  "entity_class" varchar(255) DEFAULT NULL,
  "has_child" bit(1) DEFAULT NULL,
  "sequence" int(11) DEFAULT '0',
  "is_show" bit(1) DEFAULT NULL,
  PRIMARY KEY ("model_id")
) TYPE=InnoDB AUTO_INCREMENT=15;

--
-- Dumping data for table "cms_model"
--

/*!40000 ALTER TABLE "cms_model" DISABLE KEYS */;
INSERT INTO "cms_model" ("model_id","model_name","table_name","entity_class","has_child","sequence","is_show") VALUES 
 (13,'文章','cms_article','CmsArticle',0x01,0,0x01),
 (14,'单页面','cms_one_page','CmsOnePage',0x01,0,0x01);
/*!40000 ALTER TABLE "cms_model" ENABLE KEYS */;


--
-- Definition of table "cms_model_functions"
--

DROP TABLE IF EXISTS "cms_model_functions";
CREATE TABLE "cms_model_functions" (
  "cms_model" int(11) NOT NULL,
  "functions" int(11) NOT NULL,
  UNIQUE KEY "functions" ("functions"),
  KEY "FK1A8030F823188AC" ("cms_model"),
  KEY "FK1A8030F349680A3" ("functions"),
  CONSTRAINT "FK1A8030F349680A3" FOREIGN KEY ("functions") REFERENCES "cms_function" ("function_id"),
  CONSTRAINT "FK1A8030F823188AC" FOREIGN KEY ("cms_model") REFERENCES "cms_model" ("model_id")
) TYPE=InnoDB;

--
-- Dumping data for table "cms_model_functions"
--

/*!40000 ALTER TABLE "cms_model_functions" DISABLE KEYS */;
/*!40000 ALTER TABLE "cms_model_functions" ENABLE KEYS */;


--
-- Definition of table "cms_model_page"
--

DROP TABLE IF EXISTS "cms_model_page";
CREATE TABLE "cms_model_page" (
  "page_id" int(11) NOT NULL AUTO_INCREMENT,
  "page_name" varchar(255) DEFAULT NULL,
  "model_id" int(11) DEFAULT NULL,
  PRIMARY KEY ("page_id"),
  KEY "FK19B10E9B6037BDEA" ("model_id"),
  CONSTRAINT "FK19B10E9B6037BDEA" FOREIGN KEY ("model_id") REFERENCES "cms_model" ("model_id")
) TYPE=InnoDB AUTO_INCREMENT=4;

--
-- Dumping data for table "cms_model_page"
--

/*!40000 ALTER TABLE "cms_model_page" DISABLE KEYS */;
INSERT INTO "cms_model_page" ("page_id","page_name","model_id") VALUES 
 (1,'栏目首页',13),
 (2,'文章列表页',13),
 (3,'文章内容页',13);
/*!40000 ALTER TABLE "cms_model_page" ENABLE KEYS */;


--
-- Definition of table "cms_one_page"
--

DROP TABLE IF EXISTS "cms_one_page";
CREATE TABLE "cms_one_page" (
  "page_id" int(11) NOT NULL AUTO_INCREMENT,
  "created" datetime DEFAULT NULL,
  "keywords" varchar(255) DEFAULT NULL,
  "summary" varchar(255) DEFAULT NULL,
  "updated" datetime DEFAULT NULL,
  PRIMARY KEY ("page_id")
) TYPE=InnoDB;

--
-- Dumping data for table "cms_one_page"
--

/*!40000 ALTER TABLE "cms_one_page" DISABLE KEYS */;
/*!40000 ALTER TABLE "cms_one_page" ENABLE KEYS */;


--
-- Definition of table "cms_page_template"
--

DROP TABLE IF EXISTS "cms_page_template";
CREATE TABLE "cms_page_template" (
  "page_template_id" int(11) NOT NULL AUTO_INCREMENT,
  "is_default" bit(1) DEFAULT NULL,
  "category_id" int(11) DEFAULT NULL,
  "model_page_id" int(11) DEFAULT NULL,
  "template_id" int(11) DEFAULT NULL,
  PRIMARY KEY ("page_template_id"),
  KEY "FKAEEAEBB44A27EFDD" ("model_page_id"),
  KEY "FKAEEAEBB449536E2A" ("category_id"),
  KEY "FKAEEAEBB455D6CAA" ("template_id"),
  CONSTRAINT "FKAEEAEBB449536E2A" FOREIGN KEY ("category_id") REFERENCES "cms_category" ("category_id"),
  CONSTRAINT "FKAEEAEBB455D6CAA" FOREIGN KEY ("template_id") REFERENCES "cms_template" ("template_file_id")
) TYPE=InnoDB;

--
-- Dumping data for table "cms_page_template"
--

/*!40000 ALTER TABLE "cms_page_template" DISABLE KEYS */;
/*!40000 ALTER TABLE "cms_page_template" ENABLE KEYS */;


--
-- Definition of table "cms_parameter"
--

DROP TABLE IF EXISTS "cms_parameter";
CREATE TABLE "cms_parameter" (
  "parameter_id" int(11) NOT NULL AUTO_INCREMENT,
  "label" varchar(255) DEFAULT NULL,
  "name" varchar(255) DEFAULT NULL,
  "value" varchar(255) DEFAULT NULL,
  "tag_id" int(11) DEFAULT NULL,
  PRIMARY KEY ("parameter_id"),
  KEY "FK81CAE83389D0904A" ("tag_id"),
  CONSTRAINT "FK81CAE83389D0904A" FOREIGN KEY ("tag_id") REFERENCES "cms_tag" ("tag_id")
) TYPE=InnoDB;

--
-- Dumping data for table "cms_parameter"
--

/*!40000 ALTER TABLE "cms_parameter" DISABLE KEYS */;
/*!40000 ALTER TABLE "cms_parameter" ENABLE KEYS */;


--
-- Definition of table "cms_role_function"
--

DROP TABLE IF EXISTS "cms_role_function";
CREATE TABLE "cms_role_function" (
  "function_id" int(11) NOT NULL,
  "role_id" int(11) NOT NULL,
  UNIQUE KEY "role_id" ("role_id"),
  KEY "FK8134290B239D5BEB" ("role_id"),
  KEY "FK8134290BC8F2C7EA" ("function_id"),
  CONSTRAINT "FK8134290B239D5BEB" FOREIGN KEY ("role_id") REFERENCES "p_roles" ("role_id"),
  CONSTRAINT "FK8134290BC8F2C7EA" FOREIGN KEY ("function_id") REFERENCES "cms_function" ("function_id")
) TYPE=InnoDB;

--
-- Dumping data for table "cms_role_function"
--

/*!40000 ALTER TABLE "cms_role_function" DISABLE KEYS */;
/*!40000 ALTER TABLE "cms_role_function" ENABLE KEYS */;


--
-- Definition of table "cms_site"
--

DROP TABLE IF EXISTS "cms_site";
CREATE TABLE "cms_site" (
  "site_id" int(11) NOT NULL AUTO_INCREMENT,
  "close_reason" varchar(255) DEFAULT NULL,
  "closed" bit(1) NOT NULL,
  "description" varchar(255) DEFAULT NULL,
  "html_path" varchar(255) DEFAULT NULL,
  "name" varchar(255) DEFAULT NULL,
  "site_url" varchar(255) DEFAULT NULL,
  "title" varchar(255) DEFAULT NULL,
  "watermark_pic" varchar(255) DEFAULT NULL,
  "watermark_postion" varchar(255) DEFAULT NULL,
  "template_suit_id" int(11) DEFAULT NULL,
  "is_watermark" bit(1) DEFAULT NULL,
  "watermark_position" varchar(255) DEFAULT NULL,
  PRIMARY KEY ("site_id"),
  KEY "FK7BC97C7D8B82349F" ("template_suit_id"),
  CONSTRAINT "FK7BC97C7D8B82349F" FOREIGN KEY ("template_suit_id") REFERENCES "cms_template_suit" ("suit_id")
) TYPE=InnoDB AUTO_INCREMENT=5;

--
-- Dumping data for table "cms_site"
--

/*!40000 ALTER TABLE "cms_site" DISABLE KEYS */;
INSERT INTO "cms_site" ("site_id","close_reason","closed","description","html_path","name","site_url","title","watermark_pic","watermark_postion","template_suit_id","is_watermark","watermark_position") VALUES 
 (1,'',0x00,NULL,'d:/html','测试站点','http://localhost/ceshi','测试站点','',NULL,5,0x00,''),
 (2,'',0x00,NULL,'d:/html','测试站点','http://localhost/ceshi','测试站点啊','',NULL,1,0x00,''),
 (3,'',0x00,NULL,'d:/html','测试站点','http://localhost/html','测试站点','',NULL,5,0x00,''),
 (4,'',0x00,NULL,'aaaa','aaaa','http://dddd.com','aaaaa','',NULL,1,0x00,'lt');
/*!40000 ALTER TABLE "cms_site" ENABLE KEYS */;


--
-- Definition of table "cms_tag"
--

DROP TABLE IF EXISTS "cms_tag";
CREATE TABLE "cms_tag" (
  "tag_id" int(11) NOT NULL AUTO_INCREMENT,
  "max_result" int(11) DEFAULT NULL,
  "paged" bit(1) DEFAULT NULL,
  "category_id" int(11) DEFAULT NULL,
  "model_id" int(11) DEFAULT NULL,
  "template_id" int(11) DEFAULT NULL,
  "name" varchar(255) DEFAULT NULL,
  "title" varchar(255) DEFAULT NULL,
  PRIMARY KEY ("tag_id"),
  KEY "FK358AA3A455D6CAA" ("template_id"),
  KEY "FK358AA3A46037BDEA" ("model_id"),
  KEY "FK358AA3A449536E2A" ("category_id"),
  CONSTRAINT "FK358AA3A449536E2A" FOREIGN KEY ("category_id") REFERENCES "cms_category" ("category_id"),
  CONSTRAINT "FK358AA3A455D6CAA" FOREIGN KEY ("template_id") REFERENCES "cms_template" ("template_file_id"),
  CONSTRAINT "FK358AA3A46037BDEA" FOREIGN KEY ("model_id") REFERENCES "cms_model" ("model_id")
) TYPE=InnoDB;

--
-- Dumping data for table "cms_tag"
--

/*!40000 ALTER TABLE "cms_tag" DISABLE KEYS */;
/*!40000 ALTER TABLE "cms_tag" ENABLE KEYS */;


--
-- Definition of table "cms_template"
--

DROP TABLE IF EXISTS "cms_template";
CREATE TABLE "cms_template" (
  "template_file_id" int(11) NOT NULL AUTO_INCREMENT,
  "template_file_name" varchar(255) DEFAULT NULL,
  "template_name" varchar(255) DEFAULT NULL,
  "template_type" varchar(255) DEFAULT NULL,
  "suit_id" int(11) DEFAULT NULL,
  PRIMARY KEY ("template_file_id"),
  KEY "FKD2B0DEB0F6A6DB64" ("suit_id"),
  CONSTRAINT "FKD2B0DEB0F6A6DB64" FOREIGN KEY ("suit_id") REFERENCES "cms_template_suit" ("suit_id")
) TYPE=InnoDB AUTO_INCREMENT=5;

--
-- Dumping data for table "cms_template"
--

/*!40000 ALTER TABLE "cms_template" DISABLE KEYS */;
INSERT INTO "cms_template" ("template_file_id","template_file_name","template_name","template_type","suit_id") VALUES 
 (1,'aaaaa','aaaaa','common',NULL),
 (2,'article_content','文章内容模板','c',5),
 (3,'article_index','文章首页模板','c',5),
 (4,'article_list','文章列表模板','c',5);
/*!40000 ALTER TABLE "cms_template" ENABLE KEYS */;


--
-- Definition of table "cms_template_suit"
--

DROP TABLE IF EXISTS "cms_template_suit";
CREATE TABLE "cms_template_suit" (
  "suit_id" int(11) NOT NULL AUTO_INCREMENT,
  "is_default" bit(1) DEFAULT NULL,
  "package_name" varchar(255) DEFAULT NULL,
  "package_title" varchar(255) DEFAULT NULL,
  "name" varchar(255) DEFAULT NULL,
  PRIMARY KEY ("suit_id")
) TYPE=InnoDB AUTO_INCREMENT=6;

--
-- Dumping data for table "cms_template_suit"
--

/*!40000 ALTER TABLE "cms_template_suit" DISABLE KEYS */;
INSERT INTO "cms_template_suit" ("suit_id","is_default","package_name","package_title","name") VALUES 
 (1,NULL,'suit2',NULL,'方案2'),
 (5,NULL,'default',NULL,'默认模板');
/*!40000 ALTER TABLE "cms_template_suit" ENABLE KEYS */;


--
-- Definition of table "flow_outputparam_enum_binding"
--

DROP TABLE IF EXISTS "flow_outputparam_enum_binding";
CREATE TABLE "flow_outputparam_enum_binding" (
  "param_enum_bind_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "output_param_enum_id" varchar(255) DEFAULT NULL,
  "node_output_param_binding_id" bigint(20) NOT NULL,
  "driver_output_param_enum" bigint(20) DEFAULT NULL,
  PRIMARY KEY ("param_enum_bind_id"),
  KEY "FK92A5946BE409A64B" ("driver_output_param_enum"),
  KEY "FK92A5946B6E38D6F6" ("node_output_param_binding_id")
) TYPE=MyISAM;

--
-- Dumping data for table "flow_outputparam_enum_binding"
--

/*!40000 ALTER TABLE "flow_outputparam_enum_binding" DISABLE KEYS */;
/*!40000 ALTER TABLE "flow_outputparam_enum_binding" ENABLE KEYS */;


--
-- Definition of table "frame_attribute"
--

DROP TABLE IF EXISTS "frame_attribute";
CREATE TABLE "frame_attribute" (
  "attribute_id" int(11) NOT NULL AUTO_INCREMENT,
  "attribute_label" varchar(255) DEFAULT NULL,
  "length" int(11) DEFAULT NULL,
  "attribute_name" varchar(255) DEFAULT NULL,
  "control_id" int(11) DEFAULT NULL,
  "model_id" int(11) DEFAULT NULL,
  "is_key" bit(1) DEFAULT NULL,
  "java_class" varchar(255) DEFAULT NULL,
  "required" bit(1) DEFAULT NULL,
  PRIMARY KEY ("attribute_id"),
  KEY "FK6EBC2A4ACA6E5C71" ("control_id"),
  KEY "FK6EBC2A4AF34402B1" ("model_id")
) TYPE=MyISAM AUTO_INCREMENT=13;

--
-- Dumping data for table "frame_attribute"
--

/*!40000 ALTER TABLE "frame_attribute" DISABLE KEYS */;
INSERT INTO "frame_attribute" ("attribute_id","attribute_label","length","attribute_name","control_id","model_id","is_key","java_class","required") VALUES 
 (1,'地址',22,'address',3,5,0x00,'String',0x00),
 (3,'年龄',23,'age',3,5,0x00,'Integer',0x00),
 (5,'说明',60,'note',6,5,0x00,'String',0x00),
 (4,'名称',22,'name',3,5,0x00,'String',0x00),
 (10,'编号1',8,'userId',7,5,0x01,'Integer',0x00),
 (11,'编号',8,'roleId',7,6,0x00,'Integer',0x00);
/*!40000 ALTER TABLE "frame_attribute" ENABLE KEYS */;


--
-- Definition of table "frame_component"
--

DROP TABLE IF EXISTS "frame_component";
CREATE TABLE "frame_component" (
  "component_id" int(11) NOT NULL AUTO_INCREMENT,
  "enabled" bit(1) DEFAULT NULL,
  "name" varchar(255) DEFAULT NULL,
  "package_name" varchar(255) DEFAULT NULL,
  "type" varchar(255) DEFAULT NULL,
  "template_id" int(11) DEFAULT NULL,
  "componenet_level" varchar(255) DEFAULT NULL,
  "file_name" varchar(255) DEFAULT NULL,
  PRIMARY KEY ("component_id"),
  KEY "FK1A839CAB26151E83" ("template_id")
) TYPE=MyISAM AUTO_INCREMENT=10;

--
-- Dumping data for table "frame_component"
--

/*!40000 ALTER TABLE "frame_component" DISABLE KEYS */;
INSERT INTO "frame_component" ("component_id","enabled","name","package_name","type","template_id","componenet_level","file_name") VALUES 
 (1,0x01,'DaoImpl','dao.impl','java',6,'model','${p.name?cap_first}${m.name?cap_first}DaoImpl.java'),
 (2,0x01,'Dao','dao','java',4,'model','I${p.name?cap_first}${m.name?cap_first}Dao.java'),
 (3,0x01,'Service','service','java',1,'model','I${p.name?cap_first}${m.name?cap_first}Service.java'),
 (4,0x01,'ServiceImpl','service.impl','java',7,'model','${p.name?cap_first}${m.name?cap_first}ServiceImpl.java'),
 (6,0x01,'web.xml','WEB-INF','xml',14,'project','web.xml'),
 (5,0x01,'Action','action','java',5,'model','${p.name?cap_first}${m.name?cap_first}Action.java'),
 (7,0x01,'js','js/${p.name}','js',12,'model','${m.name}.js'),
 (8,0x01,'application.properties','src\\conf','properties',13,'project','application.properties'),
 (9,0x01,'Model类','model','java',15,'model','${p.name?cap_first}${m.name?cap_first}.java');
/*!40000 ALTER TABLE "frame_component" ENABLE KEYS */;


--
-- Definition of table "frame_control"
--

DROP TABLE IF EXISTS "frame_control";
CREATE TABLE "frame_control" (
  "control_id" int(11) NOT NULL AUTO_INCREMENT,
  "label" varchar(255) DEFAULT NULL,
  "name" varchar(255) DEFAULT NULL,
  "template_id" int(11) DEFAULT NULL,
  PRIMARY KEY ("control_id"),
  KEY "FK2783F88B26151E83" ("template_id")
) TYPE=MyISAM AUTO_INCREMENT=8;

--
-- Dumping data for table "frame_control"
--

/*!40000 ALTER TABLE "frame_control" DISABLE KEYS */;
INSERT INTO "frame_control" ("control_id","label","name","template_id") VALUES 
 (3,'输入框','text',3),
 (4,'多行输入框','textarea',NULL),
 (5,'多行文本输入框','textarea',NULL),
 (6,'多行文本输入框','textarea1',3),
 (7,'隐藏输入框','hidden',3);
/*!40000 ALTER TABLE "frame_control" ENABLE KEYS */;


--
-- Definition of table "frame_field"
--

DROP TABLE IF EXISTS "frame_field";
CREATE TABLE "frame_field" (
  "field_id" int(11) NOT NULL AUTO_INCREMENT,
  "length" int(11) DEFAULT NULL,
  "field_name" varchar(255) DEFAULT NULL,
  "note" varchar(255) DEFAULT NULL,
  "sql_type" varchar(255) DEFAULT NULL,
  "field_title" varchar(255) DEFAULT NULL,
  "control_id" int(11) DEFAULT NULL,
  "model_id" int(11) DEFAULT NULL,
  "template_id" int(11) DEFAULT NULL,
  PRIMARY KEY ("field_id"),
  KEY "FKB6227A8CA6E5C71" ("control_id"),
  KEY "FKB6227A826151E83" ("template_id"),
  KEY "FKB6227A8F34402B1" ("model_id")
) TYPE=MyISAM;

--
-- Dumping data for table "frame_field"
--

/*!40000 ALTER TABLE "frame_field" DISABLE KEYS */;
/*!40000 ALTER TABLE "frame_field" ENABLE KEYS */;


--
-- Definition of table "frame_model"
--

DROP TABLE IF EXISTS "frame_model";
CREATE TABLE "frame_model" (
  "model_id" int(11) NOT NULL AUTO_INCREMENT,
  "model_label" varchar(255) DEFAULT NULL,
  "model_name" varchar(255) DEFAULT NULL,
  "model_note" varchar(255) DEFAULT NULL,
  "project_id" int(11) DEFAULT NULL,
  "table_name" varchar(255) DEFAULT NULL,
  PRIMARY KEY ("model_id"),
  KEY "FKBC781D7E3DDB2F1" ("project_id")
) TYPE=MyISAM AUTO_INCREMENT=8;

--
-- Dumping data for table "frame_model"
--

/*!40000 ALTER TABLE "frame_model" DISABLE KEYS */;
INSERT INTO "frame_model" ("model_id","model_label","model_name","model_note","project_id","table_name") VALUES 
 (5,'用户','User',NULL,1,NULL),
 (7,'菜单','Menu',NULL,1,NULL),
 (6,'角色','Role',NULL,1,NULL);
/*!40000 ALTER TABLE "frame_model" ENABLE KEYS */;


--
-- Definition of table "frame_model_relation"
--

DROP TABLE IF EXISTS "frame_model_relation";
CREATE TABLE "frame_model_relation" (
  "relation_id" int(11) NOT NULL AUTO_INCREMENT,
  "relation" varchar(255) DEFAULT NULL,
  "model_id" int(11) NOT NULL,
  "relation_model_id" int(11) NOT NULL,
  PRIMARY KEY ("relation_id"),
  KEY "FK5420CB642268B594" ("relation_model_id"),
  KEY "FK5420CB64F34402B1" ("model_id")
) TYPE=MyISAM AUTO_INCREMENT=7;

--
-- Dumping data for table "frame_model_relation"
--

/*!40000 ALTER TABLE "frame_model_relation" DISABLE KEYS */;
INSERT INTO "frame_model_relation" ("relation_id","relation","model_id","relation_model_id") VALUES 
 (1,'ManyToMany',5,6),
 (2,'ManyToMany',6,7),
 (5,'OneToMany',5,7),
 (4,'ManyToMany',6,5),
 (6,'OneToMany',7,6);
/*!40000 ALTER TABLE "frame_model_relation" ENABLE KEYS */;


--
-- Definition of table "frame_project"
--

DROP TABLE IF EXISTS "frame_project";
CREATE TABLE "frame_project" (
  "project_id" int(11) NOT NULL AUTO_INCREMENT,
  "base_path" varchar(255) DEFAULT NULL,
  "encode" varchar(255) DEFAULT NULL,
  "name" varchar(255) DEFAULT NULL,
  "package_name" varchar(255) DEFAULT NULL,
  "source_path" varchar(255) DEFAULT NULL,
  "web_path" varchar(255) DEFAULT NULL,
  "database_name" varchar(255) DEFAULT NULL,
  "database_password" varchar(255) DEFAULT NULL,
  "database_type" varchar(255) DEFAULT NULL,
  "database_url" varchar(255) DEFAULT NULL,
  "database_user" varchar(255) DEFAULT NULL,
  "database_class" varchar(255) DEFAULT NULL,
  "table_prefix" varchar(255) DEFAULT NULL,
  "driver_class" varchar(255) DEFAULT NULL,
  "js_path" varchar(255) DEFAULT NULL,
  "title" varchar(255) DEFAULT NULL,
  PRIMARY KEY ("project_id")
) TYPE=MyISAM AUTO_INCREMENT=30;

--
-- Dumping data for table "frame_project"
--

/*!40000 ALTER TABLE "frame_project" DISABLE KEYS */;
INSERT INTO "frame_project" ("project_id","base_path","encode","name","package_name","source_path","web_path","database_name","database_password","database_type","database_url","database_user","database_class","table_prefix","driver_class","js_path","title") VALUES 
 (1,'d:/cms','UTF-8','cms','com.entwin.cms','src','webcontent','cms','admin','mysql','jdbc:mysql://localhost/cms','root','admin',NULL,'com.mysql.jdbc.Driver','js','内容管理器'),
 (29,'d:/jxc','GBK','jxc','com.entwin.jxc','src','webcontent','jinxiaocun','admin','mysql','jdbc:mysql://localhost/jxc','root','admin',NULL,'com.mysql.jdbc.Driver','js','进销存系统');
/*!40000 ALTER TABLE "frame_project" ENABLE KEYS */;


--
-- Definition of table "frame_template"
--

DROP TABLE IF EXISTS "frame_template";
CREATE TABLE "frame_template" (
  "template_file_id" int(11) NOT NULL AUTO_INCREMENT,
  "template_name" varchar(255) DEFAULT NULL,
  "template_file_name" varchar(255) DEFAULT NULL,
  "template_type" varchar(255) DEFAULT NULL,
  PRIMARY KEY ("template_file_id")
) TYPE=MyISAM AUTO_INCREMENT=16;

--
-- Dumping data for table "frame_template"
--

/*!40000 ALTER TABLE "frame_template" DISABLE KEYS */;
INSERT INTO "frame_template" ("template_file_id","template_name","template_file_name","template_type") VALUES 
 (1,'Service接口模板','IService','component'),
 (3,'输入框','input','control'),
 (4,'Dao接口模板','IDao','component'),
 (5,'Action类模板','Action','component'),
 (6,'DaoImpl类模板','DaoImpl','component'),
 (7,'ServiceImpl类模板','ServiceImpl','component'),
 (8,'Index模板','cmp_index','component'),
 (9,'Delete模板','cmp_delete','component'),
 (10,'Load模板','cmp_load','component'),
 (11,'Save模板','cmp_save','component'),
 (12,'JS模板','cmp_js','component'),
 (13,'配置文件模板','application.properties','component'),
 (14,'web配置文件','prj_web','component'),
 (15,'Model类','cmp_model','component');
/*!40000 ALTER TABLE "frame_template" ENABLE KEYS */;


--
-- Definition of table "frame_template_suit"
--

DROP TABLE IF EXISTS "frame_template_suit";
CREATE TABLE "frame_template_suit" (
  "suit_id" int(11) NOT NULL AUTO_INCREMENT,
  "package_name" varchar(255) DEFAULT NULL,
  "package_title" varchar(255) DEFAULT NULL,
  PRIMARY KEY ("suit_id")
) TYPE=MyISAM;

--
-- Dumping data for table "frame_template_suit"
--

/*!40000 ALTER TABLE "frame_template_suit" DISABLE KEYS */;
/*!40000 ALTER TABLE "frame_template_suit" ENABLE KEYS */;


--
-- Definition of table "oa_message"
--

DROP TABLE IF EXISTS "oa_message";
CREATE TABLE "oa_message" (
  "msg_id" int(11) NOT NULL AUTO_INCREMENT,
  "content" longtext,
  "created" datetime DEFAULT NULL,
  "state" int(11) NOT NULL,
  "title" varchar(100) DEFAULT NULL,
  "from_id" int(11) DEFAULT NULL,
  "to_id" int(11) DEFAULT NULL,
  PRIMARY KEY ("msg_id"),
  KEY "FKDF40C29AD823BF1B" ("to_id"),
  KEY "FKDF40C29AAE20738C" ("from_id"),
  CONSTRAINT "FKDF40C29AAE20738C" FOREIGN KEY ("from_id") REFERENCES "p_users" ("user_id"),
  CONSTRAINT "FKDF40C29AD823BF1B" FOREIGN KEY ("to_id") REFERENCES "p_users" ("user_id")
) TYPE=InnoDB;

--
-- Dumping data for table "oa_message"
--

/*!40000 ALTER TABLE "oa_message" DISABLE KEYS */;
/*!40000 ALTER TABLE "oa_message" ENABLE KEYS */;


--
-- Definition of table "p_agent_types"
--

DROP TABLE IF EXISTS "p_agent_types";
CREATE TABLE "p_agent_types" (
  "type_id" int(11) NOT NULL AUTO_INCREMENT,
  "description" varchar(255) DEFAULT NULL,
  "type_name" varchar(255) DEFAULT NULL,
  PRIMARY KEY ("type_id")
) TYPE=InnoDB;

--
-- Dumping data for table "p_agent_types"
--

/*!40000 ALTER TABLE "p_agent_types" DISABLE KEYS */;
/*!40000 ALTER TABLE "p_agent_types" ENABLE KEYS */;


--
-- Definition of table "p_functions"
--

DROP TABLE IF EXISTS "p_functions";
CREATE TABLE "p_functions" (
  "function_name" varchar(40) DEFAULT NULL,
  "is_log" tinyint(1) DEFAULT '1',
  "is_menu" tinyint(1) DEFAULT '1',
  "sequence" int(11) NOT NULL,
  "url" varchar(255) DEFAULT NULL,
  "function_id" int(11) NOT NULL AUTO_INCREMENT,
  "parent_id" int(11) DEFAULT NULL,
  "type" int(10) unsigned DEFAULT '0' COMMENT '0-功能模块,1-菜单,2-操作',
  "enabled" tinyint(1) NOT NULL DEFAULT '1' COMMENT '默认可用',
  PRIMARY KEY ("function_id"),
  KEY "FKC2EE368CDDA60A19" ("parent_id"),
  CONSTRAINT "FKC2EE368CDDA60A19" FOREIGN KEY ("parent_id") REFERENCES "p_functions" ("function_id")
) TYPE=InnoDB AUTO_INCREMENT=359 ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table "p_functions"
--

/*!40000 ALTER TABLE "p_functions" DISABLE KEYS */;
INSERT INTO "p_functions" ("function_name","is_log","is_menu","sequence","url","function_id","parent_id","type","enabled") VALUES 
 ('工程管理',1,1,2,'/shop.jhtm',1,NULL,0,0),
 ('系统管理',1,1,10,'/system.jhtm',2,NULL,0,1),
 ('权限管理',1,1,3,'/',4,2,0,1),
 ('功能配置',1,1,1,'/function/index.jhtm',5,4,0,1),
 ('组织机构',1,1,2,'/org/index.jhtm',7,333,0,1),
 ('用户管理',1,1,2,'/user/index.jhtm',8,4,0,1),
 ('角色权限',1,1,3,'/role/right.jhtm',9,4,0,1),
 ('区域管理',1,1,2,'platform/region.jsp',11,173,0,1),
 ('系统日志',0,1,0,'/log/index.jhtm',13,15,0,1),
 ('系统信息',1,1,2,'/',15,2,0,1),
 ('在线用户',1,1,1,'/user/online.jhtm',21,15,0,1),
 ('数据管理',1,1,1,'/',22,2,0,1),
 ('数据备份',1,1,0,'platform/databack.jsp',23,22,0,1),
 ('数据恢复',1,1,1,'platform/datarestore.jsp',24,22,0,1),
 ('缓存更新',1,1,2,'/cache/index.jhtm',30,22,0,1),
 ('角色管理',1,1,0,'/role/index.jhtm',124,333,0,0),
 ('基础数据',1,1,0,'/shop/security.jhtm',173,2,0,0),
 ('内容管理',1,1,3,'/cms.jhtm',287,NULL,0,0),
 ('模型管理',1,1,1,'/cms/model.jhtm',288,287,0,1),
 ('栏目管理',1,1,3,'/cms/category.jhtm',290,287,0,1),
 ('网站配置',0,0,4,'/cms/site_config.jhtm',291,287,0,1),
 ('系统工具',0,0,5,'/cms/system_tool.jhtm',292,287,0,1),
 ('模板方案',1,1,6,'/cms/template_suit.jhtm',293,287,0,1),
 ('模板管理',1,1,7,'/cms/template.jhtm',294,287,0,1),
 ('管理模型',1,1,2,'/cms/model/index.jhtm',296,288,1,1),
 ('文件管理器',0,0,1,'/cms/file_manage.jhtm',297,292,1,1),
 ('缓存管理',0,0,2,'/cms/system_cache.jhtm',298,292,1,1),
 ('网站地图',0,0,3,'/cms/web_map.jhtm',299,292,1,1),
 ('基本信息',1,1,1,'/cms/site_base.jhtm',300,291,1,1),
 ('网站设置',1,1,2,'/cms/site_config.jhtm',301,291,1,1),
 ('邮件设置',1,1,3,'/cms/site_email.jhtm',302,291,1,1),
 ('添加栏目',1,1,1,'/cms/category_add.jhtm',303,290,1,1),
 ('管理栏目',1,1,2,'/cms/category/index.jhtm',304,290,1,1),
 ('合并栏目',1,1,3,'/cms/category_merge.jhtm',305,290,1,1),
 ('模板方案',1,1,1,'/cms/suit/index.jhtm',306,293,1,1),
 ('风格管理',1,1,2,'/cms/template_style.jhtm',307,293,1,1),
 ('碎片管理',1,1,1,'/cms/block_manage.jhtm',308,294,1,1),
 ('新建模板',1,1,2,'/template/add.jhtm',309,294,1,1),
 ('管理模板',1,1,3,'/cms/template/index.jhtm',310,294,1,1),
 ('添加碎片',1,1,4,'/block/add.jhtm',311,294,1,1),
 ('添加标签',1,1,5,'/templatetag/add.jhtm',312,294,1,1),
 ('管理标签',1,1,6,'/templatetag/list.jhtm',313,294,1,1),
 ('首页',0,0,0,'/finecms.jhtm',314,NULL,0,1),
 ('系统状态',1,1,3,'/system/status.jhtm',315,15,1,1),
 ('代码生成',1,1,1,'/',316,1,1,1),
 ('项目管理',1,1,5,'/frame/project/index.jhtm',317,316,1,1),
 ('模型管理',1,1,4,'/frame/model/index.jhtm',318,316,1,1),
 ('模板管理',1,1,1,'/frame/template/index.jhtm',319,316,1,0),
 ('控件管理',1,1,2,'/frame/control/index.jhtm',320,316,1,1),
 ('组件管理',1,1,3,'/frame/component/index.jhtm',321,316,1,1),
 ('属性管理',1,1,4,'/frame/attribute/index.jhtm',322,316,1,1),
 ('人员管理',1,1,1,'/person/index.jhtm',331,333,0,0),
 ('用户权限',1,1,0,'/user/right.jhtm',332,4,0,0),
 ('人事管理',1,1,4,'/',333,2,0,0),
 ('站点管理',1,1,10,'/cms/site/index.jhtm',334,287,0,0),
 ('工作流管理',0,1,3,'/',338,NULL,0,0),
 ('流程管理',0,1,0,'/',339,338,0,0),
 ('流程监控',0,1,0,'/',340,338,0,0),
 ('流程类型',1,1,0,'/flow/type.jhtm',341,339,0,0),
 ('流程定义',1,1,0,'/flow/flow.jhtm',342,339,0,0),
 ('表单管理',1,1,0,'/flow/form.jhtm',343,339,0,0),
 ('节点管理',1,1,0,'/flow/node.jhtm',344,339,0,0),
 ('流程实例',1,1,0,'/flow/instance.jhtm',345,340,0,0),
 ('正在进行的任务',1,1,0,'/flow/tasking.jhtm',346,340,0,0),
 ('已完成任务',1,1,0,'/flow/tasked.jhtm',347,340,0,0),
 ('个人办公',0,1,1,'/',348,NULL,0,0),
 ('短消息',1,1,0,'/',349,348,0,0),
 ('发送短消息',1,1,0,'/message/send.jhtm',350,349,0,0),
 ('已发短消息',1,1,0,'/message/sended.jhtm',351,349,0,0),
 ('已收短消息',1,1,0,'/message/received.jhtm',352,349,0,0),
 ('日常办公',1,1,0,'/',353,348,0,0),
 ('待办事宜',1,1,0,'/office/daiban.jhtm',354,353,0,0),
 ('发文',1,1,0,'/office/fawen.jhtm',355,353,0,0),
 ('会员管理',0,1,0,'/',356,287,0,0),
 ('会员组管理',1,1,0,'/cms/group/index.jhtm',357,356,0,0),
 ('会员管理',1,1,0,'/cms/member/index.jhtm',358,356,0,0);
/*!40000 ALTER TABLE "p_functions" ENABLE KEYS */;


--
-- Definition of table "p_logs"
--

DROP TABLE IF EXISTS "p_logs";
CREATE TABLE "p_logs" (
  "log_id" int(11) NOT NULL AUTO_INCREMENT,
  "ip" varchar(20) DEFAULT NULL,
  "operation" varchar(255) DEFAULT NULL,
  "time" datetime DEFAULT NULL,
  "url" varchar(200) DEFAULT NULL,
  "org_id" int(11) DEFAULT NULL,
  "user_id" int(11) DEFAULT NULL,
  PRIMARY KEY ("log_id"),
  KEY "FKC48C441ECAA30F29" ("org_id"),
  KEY "FKC48C441EC8C81FCB" ("user_id")
) TYPE=MyISAM AUTO_INCREMENT=4102;

--
-- Dumping data for table "p_logs"
--

/*!40000 ALTER TABLE "p_logs" DISABLE KEYS */;
INSERT INTO "p_logs" ("log_id","ip","operation","time","url","org_id","user_id") VALUES 
 (4088,'127.0.0.1','成功登录系统','2009-11-14 22:11:52','/login.jsp',1,1),
 (4089,'127.0.0.1','成功登录系统','2009-11-15 13:02:16','/login.jsp',1,1),
 (4090,'127.0.0.1','添加标签','2009-11-15 13:03:39','/templatetag/add.jhtm',1,1),
 (4091,'127.0.0.1','管理标签','2009-11-15 13:03:40','/templatetag/list.jhtm',1,1),
 (4092,'127.0.0.1','成功登录系统','2009-11-15 13:05:44','/login.jsp',1,1),
 (4093,'127.0.0.1','成功登录系统','2009-11-20 11:54:43','/login.jsp',1,1),
 (4094,NULL,'成功退出系统','2009-11-20 11:54:48','/logout.jsp',NULL,1),
 (4095,NULL,'成功退出系统','2009-11-20 11:54:48','/logout.jsp',NULL,1),
 (4096,'127.0.0.1','成功登录系统','2009-11-21 10:01:42','/login.jsp',1,1),
 (4097,NULL,'成功退出系统','2009-11-21 10:21:59','/logout.jsp',NULL,1),
 (4098,'127.0.0.1','成功登录系统','2009-11-21 10:29:22','/login.jsp',1,1),
 (4099,'127.0.0.1','用户管理','2009-11-21 10:30:13','/user/index.jhtm',1,1),
 (4100,'127.0.0.1','用户管理','2009-11-21 10:33:36','/user/index.jhtm',1,1),
 (4101,'127.0.0.1','成功登录系统','2009-11-21 17:05:54','/login.jsp',1,1);
/*!40000 ALTER TABLE "p_logs" ENABLE KEYS */;


--
-- Definition of table "p_member_types"
--

DROP TABLE IF EXISTS "p_member_types";
CREATE TABLE "p_member_types" (
  "type_id" int(11) NOT NULL AUTO_INCREMENT,
  "description" varchar(255) DEFAULT NULL,
  "type_name" varchar(255) DEFAULT NULL,
  PRIMARY KEY ("type_id")
) TYPE=InnoDB;

--
-- Dumping data for table "p_member_types"
--

/*!40000 ALTER TABLE "p_member_types" DISABLE KEYS */;
/*!40000 ALTER TABLE "p_member_types" ENABLE KEYS */;


--
-- Definition of table "p_orgs"
--

DROP TABLE IF EXISTS "p_orgs";
CREATE TABLE "p_orgs" (
  "org_id" int(11) NOT NULL AUTO_INCREMENT,
  "sequence" int(11) DEFAULT '0',
  "org_name" varchar(32) DEFAULT NULL,
  "type" varchar(45) NOT NULL,
  "parent_id" int(11) DEFAULT NULL,
  PRIMARY KEY ("org_id"),
  KEY "FKC48DAC7E822917A3" ("parent_id")
) TYPE=MyISAM AUTO_INCREMENT=54;

--
-- Dumping data for table "p_orgs"
--

/*!40000 ALTER TABLE "p_orgs" DISABLE KEYS */;
INSERT INTO "p_orgs" ("org_id","sequence","org_name","type","parent_id") VALUES 
 (1,3,'乐佳购物总店','org',NULL),
 (2,2,'分店124','org',1),
 (3,0,'山东省工商局','org',NULL),
 (4,0,'局领导','dep',3),
 (5,1,'两总师','dep',3),
 (6,2,'办公室','dep',3),
 (7,4,'人事处','dep',3),
 (8,4,'法制处','dep',3),
 (9,5,'企业处','dep',3),
 (10,6,'外资处','dep',3),
 (11,7,'个体处','dep',3),
 (12,8,'市场处','dep',3),
 (13,9,'广告处','dep',3),
 (14,10,'合同处','dep',3),
 (15,11,'财装处','dep',3),
 (16,12,'政治工作处','dep',3),
 (19,14,'机关党委','dep',3),
 (20,16,'商标处','dep',3),
 (21,17,'纪检监察室','dep',3),
 (23,19,'消保处','dep',3),
 (24,20,'机关服务中心','dep',3),
 (25,21,'个体私营企业协会','dep',3),
 (26,22,'消费者协会','dep',3),
 (27,23,'信息中心','dep',3),
 (28,24,'局印刷所','dep',3),
 (29,25,'十七地市用户','dep',3);
/*!40000 ALTER TABLE "p_orgs" ENABLE KEYS */;


--
-- Definition of table "p_persons"
--

DROP TABLE IF EXISTS "p_persons";
CREATE TABLE "p_persons" (
  "person_id" int(11) NOT NULL AUTO_INCREMENT,
  "address" varchar(255) DEFAULT NULL,
  "age" int(11) NOT NULL,
  "birthday" datetime DEFAULT NULL,
  "email" varchar(255) DEFAULT NULL,
  "gender" varchar(255) DEFAULT NULL,
  "home_telephone" varchar(255) DEFAULT NULL,
  "mobilephone" varchar(255) DEFAULT NULL,
  "office_telephone" varchar(255) DEFAULT NULL,
  "realname" varchar(255) NOT NULL,
  "sequence" int(11) NOT NULL DEFAULT '0',
  "org_id" int(11) DEFAULT NULL,
  PRIMARY KEY ("person_id"),
  KEY "FKC4E39B55CAA30F29" ("org_id"),
  KEY "FK421006EFCAA30F29" ("org_id")
) TYPE=MyISAM AUTO_INCREMENT=6;

--
-- Dumping data for table "p_persons"
--

/*!40000 ALTER TABLE "p_persons" DISABLE KEYS */;
INSERT INTO "p_persons" ("person_id","address","age","birthday","email","gender","home_telephone","mobilephone","office_telephone","realname","sequence","org_id") VALUES 
 (1,'aaaaaaaaaa',2,'2009-08-09 00:00:00','ddd@ddd.com','m','33333333','d333333333','31323234234','管理员',1,1),
 (2,'arrrrrr',30,'1985-09-02 00:00:00','aaa@ddd.com','m','2222222','2222222','2222222','出出丑',0,2),
 (3,'asdafsdfasdf',30,'1986-02-26 00:00:00','dd@ddd.com','m','33333333','33333333','34444444','来吧',0,1),
 (5,'aaaaaaaaaaa',0,'2009-10-21 00:00:00','aaa@ddd.com','m','','','','bbbb',0,1);
/*!40000 ALTER TABLE "p_persons" ENABLE KEYS */;


--
-- Definition of table "p_regions"
--

DROP TABLE IF EXISTS "p_regions";
CREATE TABLE "p_regions" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "code" varchar(32) NOT NULL,
  "name" varchar(32) NOT NULL,
  "type" int(11) NOT NULL,
  "parent_id" int(11) DEFAULT NULL,
  PRIMARY KEY ("id"),
  KEY "FKAB3CEF30188B6575" ("parent_id"),
  CONSTRAINT "FKAB3CEF30188B6575" FOREIGN KEY ("parent_id") REFERENCES "p_regions" ("id")
) TYPE=InnoDB AUTO_INCREMENT=22;

--
-- Dumping data for table "p_regions"
--

/*!40000 ALTER TABLE "p_regions" DISABLE KEYS */;
INSERT INTO "p_regions" ("id","code","name","type","parent_id") VALUES 
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
/*!40000 ALTER TABLE "p_regions" ENABLE KEYS */;


--
-- Definition of table "p_role_functions"
--

DROP TABLE IF EXISTS "p_role_functions";
CREATE TABLE "p_role_functions" (
  "function_id" int(11) NOT NULL DEFAULT '0',
  "role_id" int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY ("function_id","role_id"),
  KEY "FKA3DD66C1239D5BEB" ("role_id"),
  KEY "FKA3DD66C1EE3AA82B" ("function_id"),
  CONSTRAINT "FKA3DD66C1239D5BEB" FOREIGN KEY ("role_id") REFERENCES "p_roles" ("role_id"),
  CONSTRAINT "FKA3DD66C1EE3AA82B" FOREIGN KEY ("function_id") REFERENCES "p_functions" ("function_id")
) TYPE=InnoDB;

--
-- Dumping data for table "p_role_functions"
--

/*!40000 ALTER TABLE "p_role_functions" DISABLE KEYS */;
INSERT INTO "p_role_functions" ("function_id","role_id") VALUES 
 (1,1),
 (2,1),
 (4,1),
 (5,1),
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
 (30,1),
 (124,1),
 (173,1),
 (287,1),
 (288,1),
 (290,1),
 (291,1),
 (292,1),
 (293,1),
 (294,1),
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
 (316,1),
 (317,1),
 (318,1),
 (319,1),
 (320,1),
 (321,1),
 (331,1),
 (332,1),
 (333,1),
 (334,1),
 (338,1),
 (339,1),
 (340,1),
 (341,1),
 (342,1),
 (343,1),
 (344,1),
 (345,1),
 (346,1),
 (347,1),
 (348,1),
 (349,1),
 (350,1),
 (351,1),
 (352,1),
 (353,1),
 (354,1),
 (355,1),
 (356,1),
 (357,1),
 (358,1),
 (1,2),
 (2,2),
 (11,2),
 (13,2),
 (15,2),
 (21,2),
 (22,2),
 (23,2),
 (24,2),
 (30,2),
 (173,2),
 (287,2),
 (314,2),
 (315,2),
 (1,56),
 (287,56),
 (288,56),
 (290,56),
 (291,56),
 (292,56),
 (293,56),
 (294,56),
 (296,56),
 (297,56),
 (298,56),
 (299,56),
 (300,56),
 (301,56),
 (302,56),
 (303,56),
 (304,56),
 (305,56),
 (306,56),
 (307,56),
 (308,56),
 (309,56),
 (310,56),
 (311,56),
 (312,56),
 (313,56),
 (316,56),
 (317,56),
 (318,56),
 (319,56),
 (320,56),
 (321,56),
 (322,56);
/*!40000 ALTER TABLE "p_role_functions" ENABLE KEYS */;


--
-- Definition of table "p_roles"
--

DROP TABLE IF EXISTS "p_roles";
CREATE TABLE "p_roles" (
  "role_id" int(11) NOT NULL AUTO_INCREMENT,
  "title" varchar(32) DEFAULT NULL,
  "org_id" int(11) DEFAULT NULL,
  "role_name" varchar(45) NOT NULL,
  "sequence" int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY ("role_id")
) TYPE=InnoDB AUTO_INCREMENT=65;

--
-- Dumping data for table "p_roles"
--

/*!40000 ALTER TABLE "p_roles" DISABLE KEYS */;
INSERT INTO "p_roles" ("role_id","title","org_id","role_name","sequence") VALUES 
 (1,'管理员',1,'admin',0),
 (2,'系统管理员',1,'superadmin',0),
 (3,'部门人员',1,'department_person',0),
 (5,'两总师部门主管领导角色',4,'lzsh_leader_role',0),
 (6,'两总师部门员工角色',4,'lzsh_dept_member',0),
 (7,'办公室主管领导',13,'bgsh_leader',0),
 (8,'办公室科员',13,'bgsh_dept_member',0),
 (9,'人事处主管领导',14,'rshch_leader',0),
 (10,'人事处科员',14,'rshch_dept_member',0),
 (11,'法制处主管领导',15,'fzhch_leader',0),
 (12,'法制处科员',15,'fzhch_dept_member',0),
 (13,'企业处主管领导',16,'qych_leader',0),
 (14,'企业处科员',16,'qych_dept_member',0),
 (15,'外资处主管领导',17,'wzch_leader',0),
 (16,'外资处科员',17,'wzch_dept_member',0),
 (17,'个体处主管领导',18,'gtch_leader',0),
 (18,'个体处科员',18,'gtch_dept_member',0),
 (19,'市场处主管领导',19,'shchch_leader',0),
 (20,'市场处科员',19,'shchsh_dept_member',0),
 (21,'广告处主管领导',20,'ggch_leader',0),
 (22,'广告处科员',20,'ggch_dept_member',0),
 (23,'合同处主管领导',21,'htch_leader',0),
 (24,'合同处科员',21,'htch_dept_member',0),
 (25,'财装处主管领导',22,'czhch_leader',0),
 (26,'财装处科员',22,'czhch_dept_member',0),
 (27,'政治工作处主管领导',23,'zhzhgzch_leader',0),
 (28,'政治工作处科员',23,'zhzhgzch_dept_member',0),
 (29,'宣传处主管领导',24,'xchch_leader',0),
 (30,'宣传处科员',24,'xchch_dept_member',0),
 (31,'离退休干部处主管领导',25,'ltxgbch_leader',0),
 (32,'离退休干部处科员',25,'ltxgbch_dept_member',0),
 (33,'机关党委主管领导',26,'jgdw_leader',0),
 (34,'机关党委科员',26,'jgdw_dept_member',0),
 (35,'商标处主管领导',27,'shbch_leader',0),
 (36,'商标处科员',27,'shbch_dept_member',0),
 (37,'纪检监察室主管领导',28,'jjjchsh_leader',0),
 (38,'纪检监察室科员',28,'jjjchsh_dept_member',0),
 (39,'公平交易局主管领导',29,'gpjyj_leader',0),
 (40,'公平交易局科员',29,'gpjyj_dept_member',0),
 (41,'消保处主管领导',30,'xbch_leader',0),
 (42,'消保处科员',30,'xbch_dept_member',0),
 (43,'机关服务中心主管领导',31,'jgfwzx_leader',0),
 (44,'机关服务中心科员',31,'jgfwzx_dept_member',0),
 (45,'个体私营企业协会主管领导',32,'gtsyqyxh_leader',0),
 (46,'个体私营企业协会科员',32,'gtsyqyxh_dept_member',0),
 (47,'消费者协会主管领导',33,'xfzhxh_leader',0),
 (48,'消费者协会科员',33,'xfzhxh_dept_member',0),
 (49,'信息中心主管领导',34,'xxzx_leader',0),
 (50,'信息中心科员',34,'xxzx_dept_member',0),
 (51,'局印刷所主管领导',35,'jyshs_leader',0),
 (52,'局印刷所科员',35,'jyshs_dept_member',0),
 (53,'十七地市用户成员',36,'shqdshyh_member',0),
 (54,'信息约稿角色',13,'bgs_NG',0),
 (55,'收文角色',13,'bgs_SWR',0),
 (56,'局长角色',2,'top_jld',0),
 (57,'车辆管理员角色',31,'jgfwzx_car_mgt',0),
 (58,'局长办公会议汇总角色',13,'bgs_office_metting_gather',0),
 (59,'党组会议汇总角色',13,'bgs_party_metting_gather',0),
 (64,'fhdfhg',2,'jjjjj',0);
/*!40000 ALTER TABLE "p_roles" ENABLE KEYS */;


--
-- Definition of table "p_tag_attribute"
--

DROP TABLE IF EXISTS "p_tag_attribute";
CREATE TABLE "p_tag_attribute" (
  "tag_id" int(11) NOT NULL,
  "attribute_id" int(11) NOT NULL,
  UNIQUE KEY "attribute_id" ("attribute_id"),
  KEY "FKAF077AA82E88BD0A" ("attribute_id"),
  KEY "FKAF077AA889D0904A" ("tag_id")
) TYPE=MyISAM;

--
-- Dumping data for table "p_tag_attribute"
--

/*!40000 ALTER TABLE "p_tag_attribute" DISABLE KEYS */;
/*!40000 ALTER TABLE "p_tag_attribute" ENABLE KEYS */;


--
-- Definition of table "p_user_roles"
--

DROP TABLE IF EXISTS "p_user_roles";
CREATE TABLE "p_user_roles" (
  "role_id" int(11) NOT NULL DEFAULT '0',
  "user_id" int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY ("role_id","user_id"),
  KEY "FK11D94878239D5BEB" ("role_id"),
  KEY "FK11D94878C8C81FCB" ("user_id"),
  CONSTRAINT "FK11D94878C8C81FCB" FOREIGN KEY ("user_id") REFERENCES "p_users" ("user_id")
) TYPE=InnoDB;

--
-- Dumping data for table "p_user_roles"
--

/*!40000 ALTER TABLE "p_user_roles" DISABLE KEYS */;
INSERT INTO "p_user_roles" ("role_id","user_id") VALUES 
 (1,1);
/*!40000 ALTER TABLE "p_user_roles" ENABLE KEYS */;


--
-- Definition of table "p_users"
--

DROP TABLE IF EXISTS "p_users";
CREATE TABLE "p_users" (
  "concurrent_max" int(11) DEFAULT NULL,
  "email" varchar(100) DEFAULT NULL,
  "enabled" tinyint(1) NOT NULL DEFAULT '1',
  "hint_answer" varchar(255) DEFAULT NULL,
  "hint_question" varchar(255) DEFAULT NULL,
  "last_login_ip" varchar(20) DEFAULT NULL,
  "last_login_time" datetime DEFAULT NULL,
  "login_attemptz_times" int(11) DEFAULT NULL,
  "login_attempts_max" int(11) DEFAULT NULL,
  "login_times" int(11) DEFAULT NULL,
  "password" varchar(32) NOT NULL,
  "validate_code" varchar(255) DEFAULT NULL,
  "username" varchar(32) NOT NULL,
  "login_attempts" int(11) DEFAULT NULL,
  "password_code" varchar(255) DEFAULT NULL,
  "user_id" int(11) NOT NULL AUTO_INCREMENT,
  "credentials_expired" bit(1) DEFAULT NULL,
  "expired" bit(1) DEFAULT NULL,
  "locked" bit(1) DEFAULT NULL,
  "validated" bit(1) DEFAULT NULL,
  "org_id" int(11) DEFAULT NULL,
  "person_id" int(11) DEFAULT NULL,
  "online" bit(1) DEFAULT NULL,
  "last_logout_time" datetime DEFAULT NULL,
  "sequence" int(10) unsigned DEFAULT '0',
  "user_type" varchar(31) DEFAULT NULL,
  PRIMARY KEY ("user_id")
) TYPE=InnoDB AUTO_INCREMENT=15;

--
-- Dumping data for table "p_users"
--

/*!40000 ALTER TABLE "p_users" DISABLE KEYS */;
INSERT INTO "p_users" ("concurrent_max","email","enabled","hint_answer","hint_question","last_login_ip","last_login_time","login_attemptz_times","login_attempts_max","login_times","password","validate_code","username","login_attempts","password_code","user_id","credentials_expired","expired","locked","validated","org_id","person_id","online","last_logout_time","sequence","user_type") VALUES 
 (10,'zhuzhsh@gmail.com',1,'who r u','zhuzhisheng','127.0.0.1','2009-11-21 17:05:54',100,100,890,'d871e6c9695a072327ca482debb0b1ae',NULL,'admin',1,NULL,1,0x00,0x00,0x00,0x01,1,1,0x01,'2009-11-21 10:21:59',0,'system'),
 (10,'zzsh2001@163.com',1,'zhuzhisheng','who r u',NULL,NULL,NULL,0,0,'ab1997ac4bc75ad0770192b473b7cb0c','08479560211253241492022425618313337182','zhuzhsh4',0,NULL,6,0x01,0x00,0x00,0x00,1,1,0x00,NULL,0,'system'),
 (10,'zzsh2001@163.com',1,'zhuzhisheng','who r u',NULL,NULL,NULL,0,0,'12ebbf5f2569a313029c9aa069f950d3','93502720810414621523021415999104689573164','zhuzhsh5',0,NULL,7,0x01,0x00,0x00,0x01,1,1,0x00,NULL,0,'system'),
 (10,'zhuzhsh@gmail.com',0,'zhuzhisheng','who r u',NULL,NULL,NULL,0,0,'02a9a1a0a058c3c7629c6f16e1c15829','6887209385243103233120117849123466962','zhuzhsh7',0,NULL,9,0x01,0x00,0x00,0x01,1,1,0x00,NULL,0,'system'),
 (10,'zhuzhsh@gmail.com',0,'zhuzhisheng','who r u',NULL,NULL,NULL,0,0,'d87e2cacb6592a8c80d472e0e3a838c9','86425113232032023184175711574471172','zhuzhsh8',0,NULL,10,0x01,0x00,0x00,0x01,1,1,0x00,NULL,0,'system'),
 (10,'zhuzhsh@gmail.com',1,'zhuzhisheng','who ru ',NULL,NULL,NULL,0,0,'a2eb7a3fcbd4c8f6532d9f77fefeb971','8423913921482302452311172472436313711213113','zhuzhsh9',0,NULL,11,0x01,0x00,0x00,0x01,1,1,0x00,NULL,0,'system'),
 (10,'zzsh2001@163.com',1,'zhuzhisheng','who ru ',NULL,NULL,NULL,0,0,'a2eb7a3fcbd4c8f6532d9f77fefeb971','1611642521838246144220150178142150688419457','zhuzhsh10',0,NULL,12,0x01,0x00,0x00,0x01,1,1,0x00,NULL,0,'system'),
 (10,'zzsh2001@163.com',0,'zhuzhisheng','who r u',NULL,NULL,NULL,0,0,'7e3fbb8c6c2ce43a20750bfa6c6701c3','216117241234391681221125315223011416287241','zhuzhsh15',0,NULL,13,0x01,0x00,0x00,0x01,1,1,0x00,NULL,0,'system'),
 (11,NULL,1,NULL,NULL,NULL,NULL,NULL,0,0,'1a09bf3bf455e19517620bf4abd13797',NULL,'aaaaa',0,NULL,14,0x01,0x00,0x00,0x00,1,5,0x00,NULL,0,'system');
/*!40000 ALTER TABLE "p_users" ENABLE KEYS */;


--
-- Definition of table "wf_bisiness_type"
--

DROP TABLE IF EXISTS "wf_bisiness_type";
CREATE TABLE "wf_bisiness_type" (
  "type_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "type_note" varchar(255) DEFAULT NULL,
  "type_sort" int(11) DEFAULT NULL,
  "type_name" varchar(255) DEFAULT NULL,
  PRIMARY KEY ("type_id")
) TYPE=MyISAM;

--
-- Dumping data for table "wf_bisiness_type"
--

/*!40000 ALTER TABLE "wf_bisiness_type" DISABLE KEYS */;
/*!40000 ALTER TABLE "wf_bisiness_type" ENABLE KEYS */;


--
-- Definition of table "wf_driver"
--

DROP TABLE IF EXISTS "wf_driver";
CREATE TABLE "wf_driver" (
  "driver_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "context_path" varchar(255) NOT NULL,
  "driver_name" varchar(255) NOT NULL,
  "memo" varchar(255) DEFAULT NULL,
  "read_url" varchar(255) DEFAULT NULL,
  "write_url" varchar(255) DEFAULT NULL,
  PRIMARY KEY ("driver_id")
) TYPE=MyISAM;

--
-- Dumping data for table "wf_driver"
--

/*!40000 ALTER TABLE "wf_driver" DISABLE KEYS */;
/*!40000 ALTER TABLE "wf_driver" ENABLE KEYS */;


--
-- Definition of table "wf_driver_in_param"
--

DROP TABLE IF EXISTS "wf_driver_in_param";
CREATE TABLE "wf_driver_in_param" (
  "param_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "param_alias" varchar(255) DEFAULT NULL,
  "param_name" varchar(255) DEFAULT NULL,
  "flow_driver_id" bigint(20) DEFAULT NULL,
  "driver_id" bigint(20) DEFAULT NULL,
  PRIMARY KEY ("param_id"),
  KEY "FKD1CA85BA4BC6FE3E" ("driver_id"),
  KEY "FKD1CA85BA1D02D86D" ("flow_driver_id")
) TYPE=MyISAM;

--
-- Dumping data for table "wf_driver_in_param"
--

/*!40000 ALTER TABLE "wf_driver_in_param" DISABLE KEYS */;
/*!40000 ALTER TABLE "wf_driver_in_param" ENABLE KEYS */;


--
-- Definition of table "wf_driver_out_param"
--

DROP TABLE IF EXISTS "wf_driver_out_param";
CREATE TABLE "wf_driver_out_param" (
  "param_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "param_alias" varchar(255) NOT NULL,
  "param_name" varchar(255) NOT NULL,
  "driver_id" bigint(20) DEFAULT NULL,
  PRIMARY KEY ("param_id"),
  KEY "FKF2F564754BC6FE3E" ("driver_id")
) TYPE=MyISAM;

--
-- Dumping data for table "wf_driver_out_param"
--

/*!40000 ALTER TABLE "wf_driver_out_param" DISABLE KEYS */;
/*!40000 ALTER TABLE "wf_driver_out_param" ENABLE KEYS */;


--
-- Definition of table "wf_driver_output_param_enum"
--

DROP TABLE IF EXISTS "wf_driver_output_param_enum";
CREATE TABLE "wf_driver_output_param_enum" (
  "param_enum_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "emnu_value" varchar(255) DEFAULT NULL,
  "driver_output_param_id" bigint(20) DEFAULT NULL,
  PRIMARY KEY ("param_enum_id"),
  KEY "FK441D0A8A5F0C4F84" ("driver_output_param_id")
) TYPE=MyISAM;

--
-- Dumping data for table "wf_driver_output_param_enum"
--

/*!40000 ALTER TABLE "wf_driver_output_param_enum" DISABLE KEYS */;
/*!40000 ALTER TABLE "wf_driver_output_param_enum" ENABLE KEYS */;


--
-- Definition of table "wf_flow_deploy"
--

DROP TABLE IF EXISTS "wf_flow_deploy";
CREATE TABLE "wf_flow_deploy" (
  "flow_deploy_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "create_time" datetime DEFAULT NULL,
  "current_state" varchar(255) DEFAULT NULL,
  "flow_deploy_name" varchar(255) DEFAULT NULL,
  "flow_memo" varchar(255) DEFAULT NULL,
  "flow_meta_id" bigint(20) DEFAULT NULL,
  PRIMARY KEY ("flow_deploy_id"),
  KEY "FK28850BA8D1FA800D" ("flow_meta_id")
) TYPE=MyISAM;

--
-- Dumping data for table "wf_flow_deploy"
--

/*!40000 ALTER TABLE "wf_flow_deploy" DISABLE KEYS */;
/*!40000 ALTER TABLE "wf_flow_deploy" ENABLE KEYS */;


--
-- Definition of table "wf_meta"
--

DROP TABLE IF EXISTS "wf_meta";
CREATE TABLE "wf_meta" (
  "meta_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "process_id" varchar(255) DEFAULT NULL,
  "business_type_id" bigint(20) DEFAULT NULL,
  "file_inuse_id" bigint(20) DEFAULT NULL,
  PRIMARY KEY ("meta_id"),
  KEY "FK4A81A97541F50400" ("business_type_id"),
  KEY "FK4A81A97523D3562F" ("file_inuse_id")
) TYPE=MyISAM;

--
-- Dumping data for table "wf_meta"
--

/*!40000 ALTER TABLE "wf_meta" DISABLE KEYS */;
/*!40000 ALTER TABLE "wf_meta" ENABLE KEYS */;


--
-- Definition of table "wf_meta_file"
--

DROP TABLE IF EXISTS "wf_meta_file";
CREATE TABLE "wf_meta_file" (
  "flow_file_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "created_time" varchar(255) DEFAULT NULL,
  "flow_meta_name" varchar(255) DEFAULT NULL,
  "flow_meta_id" bigint(20) DEFAULT NULL,
  PRIMARY KEY ("flow_file_id"),
  KEY "FK4E31A606D1FA800D" ("flow_meta_id")
) TYPE=MyISAM;

--
-- Dumping data for table "wf_meta_file"
--

/*!40000 ALTER TABLE "wf_meta_file" DISABLE KEYS */;
/*!40000 ALTER TABLE "wf_meta_file" ENABLE KEYS */;


--
-- Definition of table "wf_meta_file_store"
--

DROP TABLE IF EXISTS "wf_meta_file_store";
CREATE TABLE "wf_meta_file_store" (
  "flow_file_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "preview_image" longblob,
  "file_content" longblob,
  PRIMARY KEY ("flow_file_id")
) TYPE=MyISAM;

--
-- Dumping data for table "wf_meta_file_store"
--

/*!40000 ALTER TABLE "wf_meta_file_store" DISABLE KEYS */;
/*!40000 ALTER TABLE "wf_meta_file_store" ENABLE KEYS */;


--
-- Definition of table "wf_new_task"
--

DROP TABLE IF EXISTS "wf_new_task";
CREATE TABLE "wf_new_task" (
  "new_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "cnadidate_user_id" varchar(255) DEFAULT NULL,
  "task_id" bigint(20) DEFAULT NULL,
  "new_task_id" bigint(20) DEFAULT NULL,
  PRIMARY KEY ("new_id"),
  KEY "FKE30FA434DD481AEE" ("new_task_id"),
  KEY "FKE30FA4349AA6A8D" ("task_id")
) TYPE=MyISAM;

--
-- Dumping data for table "wf_new_task"
--

/*!40000 ALTER TABLE "wf_new_task" DISABLE KEYS */;
/*!40000 ALTER TABLE "wf_new_task" ENABLE KEYS */;


--
-- Definition of table "wf_node_binding"
--

DROP TABLE IF EXISTS "wf_node_binding";
CREATE TABLE "wf_node_binding" (
  "node_binding_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "flow_node_id" varchar(255) DEFAULT NULL,
  "performer_detail" varchar(255) NOT NULL,
  "performer_rule" varchar(10) DEFAULT NULL,
  "flow_deploy_id" bigint(20) DEFAULT NULL,
  "flow_driver_id" bigint(20) DEFAULT NULL,
  "business_type_id" bigint(20) DEFAULT NULL,
  "user_performer_id" bigint(20) DEFAULT NULL,
  PRIMARY KEY ("node_binding_id"),
  KEY "FK954704B8965FA9DB" ("business_type_id"),
  KEY "FK954704B8BE0800BC" ("flow_deploy_id"),
  KEY "FK954704B8CCF93EF0" ("user_performer_id"),
  KEY "FK954704B81D02D86D" ("flow_driver_id")
) TYPE=MyISAM;

--
-- Dumping data for table "wf_node_binding"
--

/*!40000 ALTER TABLE "wf_node_binding" DISABLE KEYS */;
/*!40000 ALTER TABLE "wf_node_binding" ENABLE KEYS */;


--
-- Definition of table "wf_node_input_param_binding"
--

DROP TABLE IF EXISTS "wf_node_input_param_binding";
CREATE TABLE "wf_node_input_param_binding" (
  "param_binding_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "node_param_id" varchar(255) DEFAULT NULL,
  "flow_node_binding_id" bigint(20) DEFAULT NULL,
  "input_param_id" bigint(20) DEFAULT NULL,
  "input_param_binding_id" bigint(20) DEFAULT NULL,
  PRIMARY KEY ("param_binding_id"),
  KEY "FKE1D7F2D13E900BAC" ("input_param_binding_id"),
  KEY "FKE1D7F2D19797CE31" ("flow_node_binding_id"),
  KEY "FKE1D7F2D11099AF41" ("input_param_id")
) TYPE=MyISAM;

--
-- Dumping data for table "wf_node_input_param_binding"
--

/*!40000 ALTER TABLE "wf_node_input_param_binding" DISABLE KEYS */;
/*!40000 ALTER TABLE "wf_node_input_param_binding" ENABLE KEYS */;


--
-- Definition of table "wf_output_param_binding"
--

DROP TABLE IF EXISTS "wf_output_param_binding";
CREATE TABLE "wf_output_param_binding" (
  "param_binding_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "node_param_id" varchar(255) DEFAULT NULL,
  "node_binding_id" bigint(20) DEFAULT NULL,
  "driver_output_param_id" bigint(20) DEFAULT NULL,
  "output_param_binding_id" bigint(20) DEFAULT NULL,
  PRIMARY KEY ("param_binding_id"),
  KEY "FKF681D9855F0C4F84" ("driver_output_param_id"),
  KEY "FKF681D9853EBFF55" ("output_param_binding_id"),
  KEY "FKF681D98519E29342" ("node_binding_id")
) TYPE=MyISAM;

--
-- Dumping data for table "wf_output_param_binding"
--

/*!40000 ALTER TABLE "wf_output_param_binding" DISABLE KEYS */;
/*!40000 ALTER TABLE "wf_output_param_binding" ENABLE KEYS */;


--
-- Definition of table "wf_proc"
--

DROP TABLE IF EXISTS "wf_proc";
CREATE TABLE "wf_proc" (
  "proc_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "preview_text" varchar(255) DEFAULT NULL,
  "start_time" varchar(255) DEFAULT NULL,
  "starter_user_id" varchar(255) DEFAULT NULL,
  "deploy_id" bigint(20) NOT NULL,
  "flow_proc_id" bigint(20) DEFAULT NULL,
  "link_flow_proc_id" bigint(20) DEFAULT NULL,
  "linked_proc_id" bigint(20) DEFAULT NULL,
  PRIMARY KEY ("proc_id"),
  KEY "FK4A8336C655E7C3AD" ("flow_proc_id"),
  KEY "FK4A8336C67E093D68" ("flow_proc_id"),
  KEY "FK4A8336C6CF822E12" ("flow_proc_id"),
  KEY "FK4A8336C6ECCC268D" ("deploy_id"),
  KEY "FK4A8336C6D25570C7" ("linked_proc_id"),
  KEY "FK4A8336C6AB68E701" ("link_flow_proc_id")
) TYPE=MyISAM;

--
-- Dumping data for table "wf_proc"
--

/*!40000 ALTER TABLE "wf_proc" DISABLE KEYS */;
/*!40000 ALTER TABLE "wf_proc" ENABLE KEYS */;


--
-- Definition of table "wf_proc_relative_data"
--

DROP TABLE IF EXISTS "wf_proc_relative_data";
CREATE TABLE "wf_proc_relative_data" (
  "data_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "driver_param_value" varchar(255) DEFAULT NULL,
  "flow_node_output_param_bind_id" bigint(20) DEFAULT NULL,
  "flow_proc_id" bigint(20) DEFAULT NULL,
  PRIMARY KEY ("data_id"),
  KEY "FK40AD43849850E6DC" ("flow_proc_id"),
  KEY "FK40AD4384E204B0E7" ("flow_node_output_param_bind_id")
) TYPE=MyISAM;

--
-- Dumping data for table "wf_proc_relative_data"
--

/*!40000 ALTER TABLE "wf_proc_relative_data" DISABLE KEYS */;
/*!40000 ALTER TABLE "wf_proc_relative_data" ENABLE KEYS */;


--
-- Definition of table "wf_proc_transaction"
--

DROP TABLE IF EXISTS "wf_proc_transaction";
CREATE TABLE "wf_proc_transaction" (
  "transaction_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "transaction_state" varchar(255) DEFAULT NULL,
  "flow_proc_id" bigint(20) DEFAULT NULL,
  PRIMARY KEY ("transaction_id"),
  KEY "FK15466EE59850E6DC" ("flow_proc_id")
) TYPE=MyISAM;

--
-- Dumping data for table "wf_proc_transaction"
--

/*!40000 ALTER TABLE "wf_proc_transaction" DISABLE KEYS */;
/*!40000 ALTER TABLE "wf_proc_transaction" ENABLE KEYS */;


--
-- Definition of table "wf_proc_transition"
--

DROP TABLE IF EXISTS "wf_proc_transition";
CREATE TABLE "wf_proc_transition" (
  "transition_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "condition_express" varchar(255) DEFAULT NULL,
  "condition_type" varchar(255) DEFAULT NULL,
  "from_node_id" varchar(255) DEFAULT NULL,
  "to_node_id" varchar(255) DEFAULT NULL,
  "workflow_transition_id" varchar(255) DEFAULT NULL,
  "proc_transition_id" bigint(20) DEFAULT NULL,
  "proc_transaction_id" bigint(20) DEFAULT NULL,
  PRIMARY KEY ("transition_id"),
  KEY "FK5BFEB46E607A0BF1" ("proc_transition_id"),
  KEY "FK5BFEB46E983BDAFA" ("proc_transaction_id")
) TYPE=MyISAM;

--
-- Dumping data for table "wf_proc_transition"
--

/*!40000 ALTER TABLE "wf_proc_transition" DISABLE KEYS */;
/*!40000 ALTER TABLE "wf_proc_transition" ENABLE KEYS */;


--
-- Definition of table "wf_role_performer"
--

DROP TABLE IF EXISTS "wf_role_performer";
CREATE TABLE "wf_role_performer" (
  "role_performer_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "role_id" bigint(20) DEFAULT NULL,
  "node_binding_id" bigint(20) DEFAULT NULL,
  PRIMARY KEY ("role_performer_id"),
  KEY "FK782F86B55170D825" ("role_performer_id"),
  KEY "FK782F86B519E29342" ("node_binding_id")
) TYPE=MyISAM;

--
-- Dumping data for table "wf_role_performer"
--

/*!40000 ALTER TABLE "wf_role_performer" DISABLE KEYS */;
/*!40000 ALTER TABLE "wf_role_performer" ENABLE KEYS */;


--
-- Definition of table "wf_task"
--

DROP TABLE IF EXISTS "wf_task";
CREATE TABLE "wf_task" (
  "task_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "create_time" varchar(255) DEFAULT NULL,
  "over_time" varchar(255) DEFAULT NULL,
  "preview_text" varchar(255) DEFAULT NULL,
  "send_email" int(11) DEFAULT NULL,
  "start_time" varchar(255) DEFAULT NULL,
  "task_state" varchar(255) DEFAULT NULL,
  "node_binding_id" bigint(20) DEFAULT NULL,
  "proc_transaction_id" bigint(20) DEFAULT NULL,
  PRIMARY KEY ("task_id"),
  KEY "FK4A84C8F5983BDAFA" ("proc_transaction_id"),
  KEY "FK4A84C8F519E29342" ("node_binding_id")
) TYPE=MyISAM;

--
-- Dumping data for table "wf_task"
--

/*!40000 ALTER TABLE "wf_task" DISABLE KEYS */;
/*!40000 ALTER TABLE "wf_task" ENABLE KEYS */;


--
-- Definition of table "wf_task_assigner"
--

DROP TABLE IF EXISTS "wf_task_assigner";
CREATE TABLE "wf_task_assigner" (
  "assigner_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "user_id" varchar(255) DEFAULT NULL,
  "task_id" bigint(20) DEFAULT NULL,
  "task_assigner_id" bigint(20) DEFAULT NULL,
  PRIMARY KEY ("assigner_id"),
  KEY "FK63FEB9E611B2931C" ("task_assigner_id"),
  KEY "FK63FEB9E69AA6A8D" ("task_id")
) TYPE=MyISAM;

--
-- Dumping data for table "wf_task_assigner"
--

/*!40000 ALTER TABLE "wf_task_assigner" DISABLE KEYS */;
/*!40000 ALTER TABLE "wf_task_assigner" ENABLE KEYS */;


--
-- Definition of table "wf_task_refuse"
--

DROP TABLE IF EXISTS "wf_task_refuse";
CREATE TABLE "wf_task_refuse" (
  "refuse_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "refuse_for" varchar(255) DEFAULT NULL,
  "refuse_user" varchar(255) DEFAULT NULL,
  "task_id" bigint(20) DEFAULT NULL,
  "task_refuse_id" bigint(20) DEFAULT NULL,
  PRIMARY KEY ("refuse_id"),
  KEY "FK5842069E9AA6A8D" ("task_id"),
  KEY "FK5842069E594B4F64" ("task_refuse_id")
) TYPE=MyISAM;

--
-- Dumping data for table "wf_task_refuse"
--

/*!40000 ALTER TABLE "wf_task_refuse" DISABLE KEYS */;
/*!40000 ALTER TABLE "wf_task_refuse" ENABLE KEYS */;


--
-- Definition of table "wf_task_role"
--

DROP TABLE IF EXISTS "wf_task_role";
CREATE TABLE "wf_task_role" (
  "task_role_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "role_id" varchar(255) DEFAULT NULL,
  "task_id" bigint(20) DEFAULT NULL,
  PRIMARY KEY ("task_role_id"),
  KEY "FK98BBC180E0AAF542" ("task_role_id"),
  KEY "FK98BBC1809AA6A8D" ("task_id")
) TYPE=MyISAM;

--
-- Dumping data for table "wf_task_role"
--

/*!40000 ALTER TABLE "wf_task_role" DISABLE KEYS */;
/*!40000 ALTER TABLE "wf_task_role" ENABLE KEYS */;


--
-- Definition of table "wf_task_user"
--

DROP TABLE IF EXISTS "wf_task_user";
CREATE TABLE "wf_task_user" (
  "task_user_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "user_id" varchar(255) DEFAULT NULL,
  "task_id" bigint(20) DEFAULT NULL,
  PRIMARY KEY ("task_user_id"),
  KEY "FK98BD2CD59AA6A8D" ("task_id"),
  KEY "FK98BD2CD585D44DCD" ("task_user_id")
) TYPE=MyISAM;

--
-- Dumping data for table "wf_task_user"
--

/*!40000 ALTER TABLE "wf_task_user" DISABLE KEYS */;
/*!40000 ALTER TABLE "wf_task_user" ENABLE KEYS */;


--
-- Definition of table "wf_user_performer"
--

DROP TABLE IF EXISTS "wf_user_performer";
CREATE TABLE "wf_user_performer" (
  "performer_id" bigint(20) NOT NULL AUTO_INCREMENT,
  "user_id" bigint(20) DEFAULT NULL,
  "node_binding_id" bigint(20) DEFAULT NULL,
  PRIMARY KEY ("performer_id"),
  KEY "FK31968BCA19E29342" ("node_binding_id")
) TYPE=MyISAM;

--
-- Dumping data for table "wf_user_performer"
--

/*!40000 ALTER TABLE "wf_user_performer" DISABLE KEYS */;
/*!40000 ALTER TABLE "wf_user_performer" ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
