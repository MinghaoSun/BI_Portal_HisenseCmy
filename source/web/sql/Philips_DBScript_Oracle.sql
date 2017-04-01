/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2016/5/10 10:49:27                           */
/*==============================================================*/


drop table "report_menu_param" cascade constraints;

drop table "report_menu_url" cascade constraints;

drop table "report_param" cascade constraints;

drop table "report_tool" cascade constraints;

drop table "report_user_mapping" cascade constraints;

drop table "sys_config" cascade constraints;

drop table "sys_dic" cascade constraints;

drop table "sys_log" cascade constraints;

drop table "sys_menu" cascade constraints;

drop table "sys_role" cascade constraints;

drop table "sys_role_menu" cascade constraints;

drop table "sys_role_param" cascade constraints;

drop table "sys_user" cascade constraints;

drop table "sys_user_role" cascade constraints;

/*==============================================================*/
/* Table: "report_menu_param"                                   */
/*==============================================================*/
create table "report_menu_param" 
(
   "id"                 VARCHAR2(100)        not null,
   "menu_id"            VARCHAR2(100),
   "param_id"           VARCHAR2(100)
);

alter table "report_menu_param"
   add constraint PK_REPORT_MENU_PARAM primary key ("id");

/*==============================================================*/
/* Table: "report_menu_url"                                     */
/*==============================================================*/
create table "report_menu_url" 
(
   "id"                 VARCHAR2(100)        not null,
   "user_id"            VARCHAR2(100),
   "menu_id"            VARCHAR2(100),
   "report_url"         VARCHAR2(200)
);

alter table "report_menu_url"
   add constraint PK_REPORT_MENU_URL primary key ("id");

/*==============================================================*/
/* Table: "report_param"                                        */
/*==============================================================*/
create table "report_param" 
(
   "id"                 VARCHAR2(100)        not null,
   "code"               VARCHAR2(100),
   "param_name"         VARCHAR2(100),
   "param_key"          VARCHAR2(100),
   "param_order"        INTEGER,
   "is_delete"          VARCHAR2(1),
   "param_remark"       VARCHAR2(200),
   "create_time"        VARCHAR2(100),
   "create_user"        VARCHAR2(100),
   "update_time"        VARCHAR2(100),
   "update_user"        VARCHAR2(100)
);

alter table "report_param"
   add constraint PK_REPORT_PARAM primary key ("id");

/*==============================================================*/
/* Table: "report_tool"                                         */
/*==============================================================*/
create table "report_tool" 
(
   "id"                 VARCHAR2(100)        not null,
   "code"               VARCHAR2(100),
   "report_tool_name"   VARCHAR2(100),
   "report_tool_type"   VARCHAR2(100),
   "report_tool_url"    VARCHAR2(100),
   "report_tool_auth_url" VARCHAR2(100),
   "report_tool_version" VARCHAR2(100),
   "report_tool_display" VARCHAR2(100),
   "is_delete"          VARCHAR2(1),
   "report_tool_remark" VARCHAR2(200),
   "create_time"        VARCHAR2(100),
   "create_user"        VARCHAR2(100),
   "update_time"        VARCHAR2(100),
   "update_user"        VARCHAR2(100)
);

alter table "report_tool"
   add constraint PK_REPORT_TOOL primary key ("id");

/*==============================================================*/
/* Table: "report_user_mapping"                                 */
/*==============================================================*/
create table "report_user_mapping" 
(
   "id"                 VARCHAR2(100)        not null,
   "user_id"            VARCHAR2(100),
   "report_tool_id"     VARCHAR2(100),
   "report_sys_name"    VARCHAR2(100),
   "report_sys_password" VARCHAR2(100),
   "create_time"        VARCHAR2(100),
   "create_user"        VARCHAR2(100),
   "update_time"        VARCHAR2(100),
   "update_user"        VARCHAR2(100)
);

alter table "report_user_mapping"
   add constraint PK_REPORT_USER_MAPPING primary key ("id");

/*==============================================================*/
/* Table: "sys_config"                                          */
/*==============================================================*/
create table "sys_config" 
(
   "id"                 INTEGER              not null,
   "param_name"         VARCHAR2(100),
   "param_code"         VARCHAR2(100),
   "param_value"        VARCHAR2(100),
   "param_type"         VARCHAR2(100),
   "param_desc"         VARCHAR2(200)
);

alter table "sys_config"
   add constraint PK_SYS_CONFIG primary key ("id");

/*==============================================================*/
/* Table: "sys_dic"                                             */
/*==============================================================*/
create table "sys_dic" 
(
   "id"                 INTEGER              not null,
   "dic_name"           VARCHAR2(100),
   "dic_category"       VARCHAR2(100),
   "dic_code"           VARCHAR2(100),
   "dic_value"          VARCHAR2(100),
   "dic_desc"           VARCHAR2(200)
);

alter table "sys_dic"
   add constraint PK_SYS_DIC primary key ("id");

/*==============================================================*/
/* Table: "sys_log"                                             */
/*==============================================================*/
create table "sys_log" 
(
   "id"                 VARCHAR2(100)        not null,
   "login_name"         VARCHAR2(100),
   "user_name"          VARCHAR2(100),
   "menu_name"          VARCHAR2(100),
   "menu_code"          VARCHAR2(100),
   "oper_type"          VARCHAR2(100),
   "req_path"           VARCHAR2(1000),
   "req_data"           VARCHAR2(2000),
   "status_code"        VARCHAR2(100),
   "log_desc"           VARCHAR2(200),
   "menu_report_id"     VARCHAR2(100),
   "create_time"        VARCHAR2(100)
);

alter table "sys_log"
   add constraint PK_SYS_LOG primary key ("id");

/*==============================================================*/
/* Table: "sys_menu"                                            */
/*==============================================================*/
create table "sys_menu" 
(
   "id"                 VARCHAR2(100)        not null,
   "code"               VARCHAR2(100),
   "menu_name"          VARCHAR2(1000),
   "menu_name_en"       VARCHAR2(1000),
   "menu_name_zh"       VARCHAR2(100),
   "menu_icon"          VARCHAR2(100),
   "menu_url"           VARCHAR2(100),
   "menu_status"        CHAR(1),
   "menu_order"         INTEGER,
   "menu_parent_id"     VARCHAR2(100),
   "menu_type"          CHAR(2),
   "menu_attribute"     CHAR(1),
   "menu_report_tool"   VARCHAR2(100),
   "menu_remark"        VARCHAR2(200),
   "is_delete"          CHAR(1),
   "create_time"        VARCHAR2(100),
   "create_user"        VARCHAR2(100),
   "update_time"        VARCHAR2(100),
   "update_user"        VARCHAR2(100)
);

alter table "sys_menu"
   add constraint PK_SYS_MENU primary key ("id");

/*==============================================================*/
/* Table: "sys_role"                                            */
/*==============================================================*/
create table "sys_role" 
(
   "id"                 VARCHAR2(100)        not null,
   "code"               VARCHAR2(100),
   "role_name"          VARCHAR2(100),
   "role_name_en"       VARCHAR2(100),
   "role_name_zh"       VARCHAR2(100),
   "role_status"        CHAR(1),
   "sub_role_id"        VARCHAR2(100),
   "role_type"          CHAR(1),
   "is_delete"          CHAR(1),
   "role_remark"        VARCHAR2(200),
   "create_time"        VARCHAR2(100),
   "create_user"        VARCHAR2(100),
   "update_time"        VARCHAR2(100),
   "update_user"        VARCHAR2(100)
);

alter table "sys_role"
   add constraint PK_SYS_ROLE primary key ("id");

/*==============================================================*/
/* Table: "sys_role_menu"                                       */
/*==============================================================*/
create table "sys_role_menu" 
(
   "id"                 VARCHAR2(100)        not null,
   "role_id"            VARCHAR2(100),
   "menu_id"            VARCHAR2(100)
);

alter table "sys_role_menu"
   add constraint PK_SYS_ROLE_MENU primary key ("id");

/*==============================================================*/
/* Table: "sys_role_param"                                      */
/*==============================================================*/
create table "sys_role_param" 
(
   "id"                 VARCHAR2(100)        not null,
   "role_id"            VARCHAR2(100),
   "param_id"           VARCHAR2(100),
   "param_value"        VARCHAR2(100),
   "param_type"         VARCHAR2(1)
);

alter table "sys_role_param"
   add constraint PK_SYS_ROLE_PARAM primary key ("id");

/*==============================================================*/
/* Table: "sys_user"                                            */
/*==============================================================*/
create table "sys_user" 
(
   "id"                 VARCHAR2(100)        not null,
   "login_name"         VARCHAR2(100),
   "login_password"     VARCHAR2(100),
   "user_full_name"     VARCHAR2(100),
   "first_name"         VARCHAR2(100),
   "last_name"          VARCHAR2(100),
   "user_sex"           CHAR(1),
   "user_birthday"      VARCHAR2(100),
   "user_mobile"        VARCHAR2(100),
   "user_email"         VARCHAR2(100),
   "is_online"          CHAR(1),
   "user_enabled"       CHAR(1),
   "is_delete"          CHAR(1),
   "last_login_time"    VARCHAR2(100),
   "data_role_id"       VARCHAR2(100),
   "user_themes"        VARCHAR2(100),
   "user_remark"        VARCHAR2(200),
   "create_time"        VARCHAR2(100),
   "create_user"        VARCHAR2(100),
   "update_time"        VARCHAR2(100),
   "update_user"        VARCHAR2(100)
);

alter table "sys_user"
   add constraint PK_SYS_USER primary key ("id");

/*==============================================================*/
/* Table: "sys_user_role"                                       */
/*==============================================================*/
create table "sys_user_role" 
(
   "id"                 VARCHAR2(100)        not null,
   "user_id"            VARCHAR2(100),
   "role_id"            VARCHAR2(100),
   "create_time"        VARCHAR2(100),
   "create_user"        VARCHAR2(100),
   "update_time"        VARCHAR2(100),
   "update_user"        VARCHAR2(100)
);

alter table "sys_user_role"
   add constraint PK_SYS_USER_ROLE primary key ("id");

