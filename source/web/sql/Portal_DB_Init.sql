INSERT INTO sys_config (id, param_name, param_code, param_value, param_type, param_desc) VALUES (1, '最近报表时间', 'report_recently_day', '30', 'I', '按照自然天数计算');
INSERT INTO sys_config (id, param_name, param_code, param_value, param_type, param_desc) VALUES (2, '最近报表条数', 'report_recently_item', '10', 'I', '显示10条报表信息');
INSERT INTO sys_config (id, param_name, param_code, param_value, param_type, param_desc) VALUES (3, '产品版本号', 'sys_versio', '1.0.0', 'S', '');
INSERT INTO sys_config (id, param_name, param_code, param_value, param_type, param_desc) VALUES (4, '版权年份', 'copyright_year', '2016', 'I', '');
INSERT INTO sys_config (id, param_name, param_code, param_value, param_type, param_desc) VALUES (5, '首页展现方式', 'index_page_display', 'H', 'S', '首页的展现形式，H表示横向，V表示纵向');
INSERT INTO sys_config (id, param_name, param_code, param_value, param_type, param_desc) VALUES (6, 'BO报表工具版本', 'bo_report_version', '4.1 sp5 patch3', 'S', '多个版本号以分号分隔');
INSERT INTO sys_config (id, param_name, param_code, param_value, param_type, param_desc) VALUES (7, 'QV报表工具版本', 'qv_report_version', '11.0', 'S', '多个版本号以分号分隔');
INSERT INTO sys_config (id, param_name, param_code, param_value, param_type, param_desc) VALUES (8, 'Tableau报表工具版本', 'tableau_report_version', '9.2.0', 'S', '多个版本号以分号分隔');
INSERT INTO sys_config (id, param_name, param_code, param_value, param_type, param_desc) VALUES (9, '国际化启动方式', 'i18n_start', '1', 'S', '启动方式：0非自适应；1自适应');
INSERT INTO sys_config (id, param_name, param_code, param_value, param_type, param_desc) VALUES (10, '登录方式', 'login_type', '0', 'S', '0表示非域账户登录；1表示域账号登录');
INSERT INTO sys_config (id, param_name, param_code, param_value, param_type, param_desc) VALUES (11, 'BO时效周期', 'bo_session', '120', 'I', '单位为分钟，默认为120分钟');

INSERT INTO sys_dic (id, dic_name, dic_category, dic_code, dic_value, dic_desc) VALUES (1, '菜单类型', '01', 'sys_menu_type', '01;02;03', '系统管理;报表系统;其它系统');
INSERT INTO sys_dic (id, dic_name, dic_category, dic_code, dic_value, dic_desc) VALUES (2, '性别', '01', 'sys_user_sex', '1;0', '男;女');
INSERT INTO sys_dic (id, dic_name, dic_category, dic_code, dic_value, dic_desc) VALUES (3, '操作状态', '01', 'sys_oper_stauts', '1;0', '启用;禁用');
INSERT INTO sys_dic (id, dic_name, dic_category, dic_code, dic_value, dic_desc) VALUES (4, '角色类别', '01', 'sys_role_type', '1;3', '功能角色;数据角色');
INSERT INTO sys_dic (id, dic_name, dic_category, dic_code, dic_value, dic_desc) VALUES (5, '操作类型', '01', 'sys_oper_type', '01;02;03;04;05;06', '登录;注销;新增;删除;修改;查询');
INSERT INTO sys_dic (id, dic_name, dic_category, dic_code, dic_value, dic_desc) VALUES (6, '报表工具', '02', 'report_tool_category', '01;02;03', 'BusinessObjects;Tableau;QlikView');
INSERT INTO sys_dic (id, dic_name, dic_category, dic_code, dic_value, dic_desc) VALUES (7, '工具展现方式', '02', 'report_display_mode', '0;1', '选项卡方式;窗口方式');
INSERT INTO sys_dic (id, dic_name, dic_category, dic_code, dic_value, dic_desc) VALUES (8, '报表菜单分类', '02', 'report_menu_type', '0;1', '目录;报表');
INSERT INTO sys_dic (id, dic_name, dic_category, dic_code, dic_value, dic_desc) VALUES (9, '内置对象参数', '02', 'object_param', 'SYS_USER_LOGIN_NAME', '登录账户');

INSERT INTO sys_menu (id, code, menu_name, menu_name_en, menu_name_zh, menu_url, menu_status, menu_order, menu_parent_id, menu_type, menu_attribute, menu_report_tool, menu_remark, is_delete, create_time, create_user, update_time, update_user) VALUES ('20160407141712851', 'MS000001', '系统管理', 'System management', '系统管理', '', '1', 1, '', '01', ' ', '', '', '0', '2016-05-10 09:30:00', 'admin', '2016-05-10 09:30:00', 'admin');
INSERT INTO sys_menu (id, code, menu_name, menu_name_en, menu_name_zh, menu_url, menu_status, menu_order, menu_parent_id, menu_type, menu_attribute, menu_report_tool, menu_remark, is_delete, create_time, create_user, update_time, update_user) VALUES ('20160407141758754', 'MS000002', '报表工具', 'Report platform', '报表平台', '/reportTool', '1', 2, '20160407141712851', '01', ' ', '', '', '0', '2016-05-10 09:30:00', 'admin', '2016-05-10 09:30:00', 'admin');
INSERT INTO sys_menu (id, code, menu_name, menu_name_en, menu_name_zh, menu_url, menu_status, menu_order, menu_parent_id, menu_type, menu_attribute, menu_report_tool, menu_remark, is_delete, create_time, create_user, update_time, update_user) VALUES ('20160407141854450', 'MS000005', '报表管理', 'Report management', '报表管理', '/sysReport', '1', 4, '20160407141712851', '01', ' ', '', '', '0', '2016-05-10 09:30:00', 'admin', '2016-05-10 09:30:00', 'admin');
INSERT INTO sys_menu (id, code, menu_name, menu_name_en, menu_name_zh, menu_url, menu_status, menu_order, menu_parent_id, menu_type, menu_attribute, menu_report_tool, menu_remark, is_delete, create_time, create_user, update_time, update_user) VALUES ('20160407141854454', 'MS000003', '报表参数', 'Report parameters', '报表参数', '/reportParam', '1', 3, '20160407141712851', '01', '', '', '', '0', '2016-05-10 09:30:00', 'admin', '2016-05-10 09:30:00', 'admin');
INSERT INTO sys_menu (id, code, menu_name, menu_name_en, menu_name_zh, menu_url, menu_status, menu_order, menu_parent_id, menu_type, menu_attribute, menu_report_tool, menu_remark, is_delete, create_time, create_user, update_time, update_user) VALUES ('20160407141854455', 'MS000004', '菜单管理', 'Menu management', '菜单管理', '/sysMenu', '1', 5, '20160407141712851', '01', ' ', '', '', '0', '2016-05-10 09:30:00', 'admin', '2016-05-10 09:30:00', 'admin');
INSERT INTO sys_menu (id, code, menu_name, menu_name_en, menu_name_zh, menu_url, menu_status, menu_order, menu_parent_id, menu_type, menu_attribute, menu_report_tool, menu_remark, is_delete, create_time, create_user, update_time, update_user) VALUES ('20160407141854456', 'MS000007', '用户管理', 'User management', '用户管理', '/sysUser', '1', 7, '20160407141712851', '01', ' ', '', '', '0', '2016-05-10 09:30:00', 'admin', '2016-05-10 09:30:00', 'admin');
INSERT INTO sys_menu (id, code, menu_name, menu_name_en, menu_name_zh, menu_url, menu_status, menu_order, menu_parent_id, menu_type, menu_attribute, menu_report_tool, menu_remark, is_delete, create_time, create_user, update_time, update_user) VALUES ('20160407141854457', 'MS000006', '角色管理', 'Role management', '角色管理', '/sysRole', '1', 6, '20160407141712851', '01', ' ', '', '', '0', '2016-05-10 09:30:00', 'admin', '2016-05-10 09:30:00', 'admin');
INSERT INTO sys_menu (id, code, menu_name, menu_name_en, menu_name_zh, menu_url, menu_status, menu_order, menu_parent_id, menu_type, menu_attribute, menu_report_tool, menu_remark, is_delete, create_time, create_user, update_time, update_user) VALUES ('20160407141854458', 'MS000008', '访问日志', 'Access log', '访问日志', '/sysLog', '1', 8, '20160407141712851', '01', ' ', '', '', '0', '2016-05-10 09:30:00', 'admin', '2016-05-10 09:30:00', 'admin');


INSERT INTO sys_role (id, code, role_name, role_name_en, role_name_zh, role_status, sub_role_id, role_type, is_delete, role_remark, create_time, create_user, update_time, update_user) VALUES ('20160407143157444', 'SR0000001', '超级管理员', 'Super administrator', '超级管理员', '1', '', '1', '0', '', '2016-05-10 09:30:00', 'admin', '2016-05-10 09:30:00', 'admin');

INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES ('20160411164822658', '20160407143157444', '20160407141712851');
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES ('20160411164822800', '20160407143157444', '20160407141758754');
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES ('20160411164823432', '20160407143157444', '20160407141854450');
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES ('20160411164823574', '20160407143157444', '20160407141854454');
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES ('20160411164823714', '20160407143157444', '20160407141854455');
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES ('20160411164823883', '20160407143157444', '20160407141854456');
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES ('20160411164824035', '20160407143157444', '20160407141854457');
INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES ('20160411164824178', '20160407143157444', '20160407141854458');

INSERT INTO sys_user (id, login_name, login_password, user_full_name, first_name, last_name, user_sex, user_birthday, user_mobile, user_email, is_online, user_enabled, is_delete, last_login_time, data_role_id, user_themes, user_remark, create_time, create_user, update_time, update_user) VALUES ('20160411165206807', 'admin', 'ISMvKXpXpadDiUoOSoAfww==', 'admin', 'admin', 'admin', '1', '', '', '', '1', ' ', '0', '', '', 'skin-blue-light', '', '2016-05-10 09:30:00', 'admin', '2016-05-10 09:30:00', 'admin');

INSERT INTO sys_user_role (id, user_id, role_id, create_time, create_user, update_time, update_user) VALUES ('20160414155557635', '20160411165206807', '20160407143157444', '2016-05-10 09:30:00', 'admin', '2016-05-10 09:30:00', 'admin');

