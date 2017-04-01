/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     2016/5/10 10:47:38                           */
/*==============================================================*/


if exists (select 1
            from  sysobjects
           where  id = object_id('report_menu_param')
            and   type = 'U')
   drop table report_menu_param
go

if exists (select 1
            from  sysobjects
           where  id = object_id('report_menu_url')
            and   type = 'U')
   drop table report_menu_url
go

if exists (select 1
            from  sysobjects
           where  id = object_id('report_param')
            and   type = 'U')
   drop table report_param
go

if exists (select 1
            from  sysobjects
           where  id = object_id('report_tool')
            and   type = 'U')
   drop table report_tool
go

if exists (select 1
            from  sysobjects
           where  id = object_id('report_user_mapping')
            and   type = 'U')
   drop table report_user_mapping
go

if exists (select 1
            from  sysobjects
           where  id = object_id('sys_config')
            and   type = 'U')
   drop table sys_config
go

if exists (select 1
            from  sysobjects
           where  id = object_id('sys_dic')
            and   type = 'U')
   drop table sys_dic
go

if exists (select 1
            from  sysobjects
           where  id = object_id('sys_log')
            and   type = 'U')
   drop table sys_log
go

if exists (select 1
            from  sysobjects
           where  id = object_id('sys_menu')
            and   type = 'U')
   drop table sys_menu
go

if exists (select 1
            from  sysobjects
           where  id = object_id('sys_role')
            and   type = 'U')
   drop table sys_role
go

if exists (select 1
            from  sysobjects
           where  id = object_id('sys_role_menu')
            and   type = 'U')
   drop table sys_role_menu
go

if exists (select 1
            from  sysobjects
           where  id = object_id('sys_role_param')
            and   type = 'U')
   drop table sys_role_param
go

if exists (select 1
            from  sysobjects
           where  id = object_id('sys_user')
            and   type = 'U')
   drop table sys_user
go

if exists (select 1
            from  sysobjects
           where  id = object_id('sys_user_role')
            and   type = 'U')
   drop table sys_user_role
go

/*==============================================================*/
/* Table: report_menu_param                                     */
/*==============================================================*/
create table report_menu_param (
   id                   varchar(100)         not null,
   menu_id              varchar(100)         null,
   param_id             varchar(100)         null
)
go

alter table report_menu_param
   add constraint PK_REPORT_MENU_PARAM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: report_menu_url                                       */
/*==============================================================*/
create table report_menu_url (
   id                   varchar(100)         not null,
   user_id              varchar(100)         null,
   menu_id              varchar(100)         null,
   report_url           varchar(200)         null
)
go

alter table report_menu_url
   add constraint PK_REPORT_MENU_URL primary key nonclustered (id)
go

/*==============================================================*/
/* Table: report_param                                          */
/*==============================================================*/
create table report_param (
   id                   varchar(100)         not null,
   code                 varchar(100)         null,
   param_name           varchar(100)         null,
   param_key            varchar(100)         null,
   param_remark         varchar(200)         null,
   param_order          integer              null,
   is_delete            varchar(1)           null,
   create_time          varchar(100)         null,
   create_user          varchar(100)         null,
   update_time          varchar(100)         null,
   update_user          varchar(100)         null
)
go

alter table report_param
   add constraint PK_REPORT_PARAM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: report_tool                                           */
/*==============================================================*/
create table report_tool (
   id                   varchar(100)         not null,
   code                 varchar(100)         null,
   report_tool_name     varchar(100)         null,
   report_tool_type     varchar(100)         null,
   report_tool_url      varchar(100)         null,
   report_tool_auth_url varchar(100)         null,
   report_tool_version  varchar(100)         null,
   report_tool_display  varchar(100)         null,
   is_delete            varchar(1)           null,
   report_tool_remark   varchar(200)         null,
   create_time          varchar(100)         null,
   create_user          varchar(100)         null,
   update_time          varchar(100)         null,
   update_user          varchar(100)         null
)
go

alter table report_tool
   add constraint PK_REPORT_TOOL primary key nonclustered (id)
go

/*==============================================================*/
/* Table: report_user_mapping                                   */
/*==============================================================*/
create table report_user_mapping (
   id                   varchar(100)         not null,
   user_id              varchar(100)         null,
   report_tool_id       varchar(100)         null,
   report_sys_name      varchar(100)         null,
   report_sys_password  varchar(100)         null,
   create_time          varchar(100)         null,
   create_user          varchar(100)         null,
   update_time          varchar(100)         null,
   update_user          varchar(100)         null
)
go

alter table report_user_mapping
   add constraint PK_REPORT_USER_MAPPING primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sys_config                                            */
/*==============================================================*/
create table sys_config (
   id                   int                  not null,
   param_name           varchar(100)         null,
   param_code           varchar(100)         null,
   param_value          varchar(100)         null,
   param_type           varchar(100)         null,
   param_desc           varchar(200)         null
)
go

alter table sys_config
   add constraint PK_SYS_CONFIG primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sys_dic                                               */
/*==============================================================*/
create table sys_dic (
   id                   int                  not null,
   dic_name             varchar(100)         null,
   dic_category         varchar(100)         null,
   dic_code             varchar(100)         null,
   dic_value            varchar(100)         null,
   dic_desc             varchar(200)         null
)
go

alter table sys_dic
   add constraint PK_SYS_DIC primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sys_log                                               */
/*==============================================================*/
create table sys_log (
   id                   varchar(100)         not null,
   login_name           varchar(100)         null,
   user_name            varchar(100)         null,
   menu_name            varchar(100)         null,
   menu_code            varchar(100)         null,
   oper_type            varchar(100)         null,
   req_path             varchar(1000)        null,
   req_data             varchar(2000)        null,
   status_code          varchar(100)         null,
   log_desc             varchar(200)         null,
   menu_report_id       varchar(100)         null,
   create_time          varchar(100)         null
)
go

alter table sys_log
   add constraint PK_SYS_LOG primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sys_menu                                              */
/*==============================================================*/
create table sys_menu (
   id                   varchar(100)         not null,
   code                 varchar(100)         null,
   menu_name            varchar(1000)        null,
   menu_name_en         varchar(1000)        null,
   menu_name_zh         varchar(100)         null,
   menu_icon            varchar(100)         null,
   menu_url             varchar(100)         null,
   menu_status          char(1)              null,
   menu_order           int                  null,
   menu_parent_id       varchar(100)         null,
   menu_type            char(2)              null,
   menu_attribute       char(1)              null,
   menu_report_tool     varchar(100)         null,
   menu_remark          varchar(200)         null,
   is_delete            char(1)              null,
   create_time          varchar(100)         null,
   create_user          varchar(100)         null,
   update_time          varchar(100)         null,
   update_user          varchar(100)         null
)
go

alter table sys_menu
   add constraint PK_SYS_MENU primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role (
   id                   varchar(100)         not null,
   code                 varchar(100)         null,
   role_name            varchar(100)         null,
   role_name_en         varchar(100)         null,
   role_name_zh         varchar(100)         null,
   role_status          char(1)              null,
   sub_role_id          varchar(100)         null,
   role_type            char(1)              null,
   is_delete            char(1)              null,
   role_remark          varchar(200)         null,
   create_time          varchar(100)         null,
   create_user          varchar(100)         null,
   update_time          varchar(100)         null,
   update_user          varchar(100)         null
)
go

alter table sys_role
   add constraint PK_SYS_ROLE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sys_role_menu                                         */
/*==============================================================*/
create table sys_role_menu (
   id                   varchar(100)         not null,
   role_id              varchar(100)         null,
   menu_id              varchar(100)         null
)
go

alter table sys_role_menu
   add constraint PK_SYS_ROLE_MENU primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sys_role_param                                        */
/*==============================================================*/
create table sys_role_param (
   id                   varchar(100)         not null,
   role_id              varchar(100)         null,
   param_id             varchar(100)         null,
   param_value          varchar(100)         null,
   param_type           char(1)              null
)
go

alter table sys_role_param
   add constraint PK_SYS_ROLE_PARAM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user (
   id                   varchar(100)         not null,
   login_name           varchar(100)         null,
   login_password       varchar(100)         null,
   user_full_name       varchar(100)         null,
   first_name           varchar(100)         null,
   last_name            varchar(100)         null,
   user_sex             char(1)              null,
   user_birthday        varchar(100)         null,
   user_mobile          varchar(100)         null,
   user_email           varchar(100)         null,
   is_online            char(1)              null,
   user_enabled         char(1)              null,
   is_delete            char(1)              null,
   last_login_time      varchar(100)         null,
   data_role_id         varchar(100)         null,
   user_themes          varchar(100)         null,
   user_remark          varchar(200)         null,
   create_time          varchar(100)         null,
   create_user          varchar(100)         null,
   update_time          varchar(100)         null,
   update_user          varchar(100)         null
)
go

alter table sys_user
   add constraint PK_SYS_USER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sys_user_role                                         */
/*==============================================================*/
create table sys_user_role (
   id                   varchar(100)         not null,
   user_id              varchar(100)         null,
   role_id              varchar(100)         null,
   create_time          varchar(100)         null,
   create_user          varchar(100)         null,
   update_time          varchar(100)         null,
   update_user          varchar(100)         null
)
go

alter table sys_user_role
   add constraint PK_SYS_USER_ROLE primary key nonclustered (id)
go

