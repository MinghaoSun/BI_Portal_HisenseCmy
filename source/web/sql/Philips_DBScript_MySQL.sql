/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/5/10 10:50:34                           */
/*==============================================================*/


drop table if exists report_menu_param;

drop table if exists report_menu_url;

drop table if exists report_param;

drop table if exists report_tool;

drop table if exists report_user_mapping;

drop table if exists sys_config;

drop table if exists sys_dic;

drop table if exists sys_log;

drop table if exists sys_menu;

drop table if exists sys_role;

drop table if exists sys_role_menu;

drop table if exists sys_role_param;

drop table if exists sys_user;

drop table if exists sys_user_role;

/*==============================================================*/
/* Table: report_menu_param                                     */
/*==============================================================*/
create table report_menu_param
(
   id                   varchar(100) not null,
   menu_id              varchar(100),
   param_id             varchar(100)
);

alter table report_menu_param
   add primary key (id);

/*==============================================================*/
/* Table: report_menu_url                                       */
/*==============================================================*/
create table report_menu_url
(
   id                   varchar(100) not null,
   user_id              varchar(100),
   menu_id              varchar(100),
   report_url           varchar(200)
);

alter table report_menu_url
   add primary key (id);

/*==============================================================*/
/* Table: report_param                                          */
/*==============================================================*/
create table report_param
(
   id                   varchar(100) not null,
   code                 varchar(100),
   param_name           varchar(100),
   param_key            varchar(100),
   param_order          char(10),
   is_delete            integer,
   param_remark         varchar(200),
   create_time          varchar(100),
   create_user          varchar(100),
   update_time          varchar(100),
   update_user          varchar(100)
);

alter table report_param
   add primary key (id);

/*==============================================================*/
/* Table: report_tool                                           */
/*==============================================================*/
create table report_tool
(
   id                   varchar(100) not null,
   code                 varchar(100),
   report_tool_name     varchar(100),
   report_tool_type     varchar(100),
   report_tool_url      varchar(100),
   report_tool_auth_url varchar(100),
   report_tool_version  varchar(100),
   report_tool_display  varchar(100),
   is_delete            varchar(1),
   report_tool_remark   varchar(200),
   create_time          varchar(100),
   create_user          varchar(100),
   update_time          varchar(100),
   update_user          varchar(100)
);

alter table report_tool
   add primary key (id);

/*==============================================================*/
/* Table: report_user_mapping                                   */
/*==============================================================*/
create table report_user_mapping
(
   id                   varchar(100) not null,
   user_id              varchar(100),
   report_tool_id       varchar(100),
   report_sys_name      varchar(100),
   report_sys_password  varchar(100),
   create_time          varchar(100),
   create_user          varchar(100),
   update_time          varchar(100),
   update_user          varchar(100)
);

alter table report_user_mapping
   add primary key (id);

/*==============================================================*/
/* Table: sys_config                                            */
/*==============================================================*/
create table sys_config
(
   id                   int not null,
   param_name           varchar(100),
   param_code           varchar(100),
   param_value          varchar(100),
   param_type           varchar(100),
   param_desc           varchar(200)
);

alter table sys_config
   add primary key (id);

/*==============================================================*/
/* Table: sys_dic                                               */
/*==============================================================*/
create table sys_dic
(
   id                   int not null,
   dic_name             varchar(100),
   dic_category         varchar(100),
   dic_code             varchar(100),
   dic_value            varchar(100),
   dic_desc             varchar(200)
);

alter table sys_dic
   add primary key (id);

/*==============================================================*/
/* Table: sys_log                                               */
/*==============================================================*/
create table sys_log
(
   id                   varchar(100) not null,
   login_name           varchar(100),
   user_name            varchar(100),
   menu_name            varchar(100),
   menu_code            varchar(100),
   oper_type            varchar(100),
   req_path             varchar(1000),
   req_data             varchar(2000),
   status_code          varchar(100),
   log_desc             varchar(200),
   menu_report_id       varchar(100),
   create_time          varchar(100)
);

alter table sys_log
   add primary key (id);

/*==============================================================*/
/* Table: sys_menu                                              */
/*==============================================================*/
create table sys_menu
(
   id                   varchar(100) not null,
   code                 varchar(100),
   menu_name            varchar(1000),
   menu_name_en         varchar(1000),
   menu_name_zh         varchar(100),
   menu_icon            varchar(100),
   menu_url             varchar(100),
   menu_status          char(1),
   menu_order           int,
   menu_parent_id       varchar(100),
   menu_type            char(2),
   menu_attribute       char(1),
   menu_report_tool     varchar(100),
   menu_remark          varchar(200),
   is_delete            char(1),
   create_time          varchar(100),
   create_user          varchar(100),
   update_time          varchar(100),
   update_user          varchar(100)
);

alter table sys_menu
   add primary key (id);

/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
   id                   varchar(100) not null,
   code                 varchar(100),
   role_name            varchar(100),
   role_name_en         varchar(100),
   role_name_zh         varchar(100),
   role_status          char(1),
   sub_role_id          varchar(100),
   role_type            char(1),
   is_delete            char(1),
   role_remark          varchar(200),
   create_time          varchar(100),
   create_user          varchar(100),
   update_time          varchar(100),
   update_user          varchar(100)
);

alter table sys_role
   add primary key (id);

/*==============================================================*/
/* Table: sys_role_menu                                         */
/*==============================================================*/
create table sys_role_menu
(
   id                   varchar(100) not null,
   role_id              varchar(100),
   menu_id              varchar(100)
);

alter table sys_role_menu
   add primary key (id);

/*==============================================================*/
/* Table: sys_role_param                                        */
/*==============================================================*/
create table sys_role_param
(
   id                   varchar(100) not null,
   role_id              varchar(100),
   param_id             varchar(100),
   param_value          varchar(100),
   param_type           varchar(1)
);

alter table sys_role_param
   add primary key (id);

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
   id                   varchar(100) not null,
   login_name           varchar(100),
   login_password       varchar(100),
   user_full_name       varchar(100),
   first_name           varchar(100),
   last_name            varchar(100),
   user_sex             char(1),
   user_birthday        varchar(100),
   user_mobile          varchar(100),
   user_email           varchar(100),
   is_online            char(1),
   user_enabled         char(1),
   is_delete            char(1),
   last_login_time      varchar(100),
   data_role_id         varchar(100),
   user_themes          varchar(100),
   user_remark          varchar(200),
   create_time          varchar(100),
   create_user          varchar(100),
   update_time          varchar(100),
   update_user          varchar(100)
);

alter table sys_user
   add primary key (id);

/*==============================================================*/
/* Table: sys_user_role                                         */
/*==============================================================*/
create table sys_user_role
(
   id                   varchar(100) not null,
   user_id              varchar(100),
   role_id              varchar(100),
   create_time          varchar(100),
   create_user          varchar(100),
   update_time          varchar(100),
   update_user          varchar(100)
);

alter table sys_user_role
   add primary key (id);

