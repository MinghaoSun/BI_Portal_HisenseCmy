INSERT INTO sys_config (id, param_name, param_code, param_value, param_type, param_desc) VALUES (1, '�������ʱ��', 'report_recently_day', '30', 'I', '������Ȼ��������');
INSERT INTO sys_config (id, param_name, param_code, param_value, param_type, param_desc) VALUES (2, '�����������', 'report_recently_item', '10', 'I', '��ʾ10��������Ϣ');
INSERT INTO sys_config (id, param_name, param_code, param_value, param_type, param_desc) VALUES (3, '��Ʒ�汾��', 'sys_versio', '1.0.0', 'S', '');
INSERT INTO sys_config (id, param_name, param_code, param_value, param_type, param_desc) VALUES (4, '��Ȩ���', 'copyright_year', '2016', 'I', '');
INSERT INTO sys_config (id, param_name, param_code, param_value, param_type, param_desc) VALUES (5, '��ҳչ�ַ�ʽ', 'index_page_display', 'H', 'S', '��ҳ��չ����ʽ��H��ʾ����V��ʾ����');
INSERT INTO sys_config (id, param_name, param_code, param_value, param_type, param_desc) VALUES (6, 'BO�����߰汾', 'bo_report_version', '4.1 sp5 patch3', 'S', '����汾���Էֺŷָ�');
INSERT INTO sys_config (id, param_name, param_code, param_value, param_type, param_desc) VALUES (7, 'QV�����߰汾', 'qv_report_version', '11.0', 'S', '����汾���Էֺŷָ�');
INSERT INTO sys_config (id, param_name, param_code, param_value, param_type, param_desc) VALUES (8, 'Tableau�����߰汾', 'tableau_report_version', '9.2.0', 'S', '����汾���Էֺŷָ�');
INSERT INTO sys_config (id, param_name, param_code, param_value, param_type, param_desc) VALUES (9, '���ʻ�������ʽ', 'i18n_start', '1', 'S', '������ʽ��0������Ӧ��1����Ӧ');
INSERT INTO sys_config (id, param_name, param_code, param_value, param_type, param_desc) VALUES (10, '��¼��ʽ', 'login_type', '0', 'S', '0��ʾ�����˻���¼��1��ʾ���˺ŵ�¼');
INSERT INTO sys_config (id, param_name, param_code, param_value, param_type, param_desc) VALUES (11, 'BOʱЧ����', 'bo_session', '120', 'I', '��λΪ���ӣ�Ĭ��Ϊ120����');

INSERT INTO sys_dic (id, dic_name, dic_category, dic_code, dic_value, dic_desc) VALUES (1, '�˵�����', '01', 'sys_menu_type', '01;02;03', 'ϵͳ����;����ϵͳ;����ϵͳ');
INSERT INTO sys_dic (id, dic_name, dic_category, dic_code, dic_value, dic_desc) VALUES (2, '�Ա�', '01', 'sys_user_sex', '1;0', '��;Ů');
INSERT INTO sys_dic (id, dic_name, dic_category, dic_code, dic_value, dic_desc) VALUES (3, '����״̬', '01', 'sys_oper_stauts', '1;0', '����;����');
INSERT INTO sys_dic (id, dic_name, dic_category, dic_code, dic_value, dic_desc) VALUES (4, '��ɫ���', '01', 'sys_role_type', '1;3', '���ܽ�ɫ;���ݽ�ɫ');
INSERT INTO sys_dic (id, dic_name, dic_category, dic_code, dic_value, dic_desc) VALUES (5, '��������', '01', 'sys_oper_type', '01;02;03;04;05;06', '��¼;ע��;����;ɾ��;�޸�;��ѯ');
INSERT INTO sys_dic (id, dic_name, dic_category, dic_code, dic_value, dic_desc) VALUES (6, '������', '02', 'report_tool_category', '01;02;03', 'BusinessObjects;Tableau;QlikView');
INSERT INTO sys_dic (id, dic_name, dic_category, dic_code, dic_value, dic_desc) VALUES (7, '����չ�ַ�ʽ', '02', 'report_display_mode', '0;1', 'ѡ���ʽ;���ڷ�ʽ');
INSERT INTO sys_dic (id, dic_name, dic_category, dic_code, dic_value, dic_desc) VALUES (8, '����˵�����', '02', 'report_menu_type', '0;1', 'Ŀ¼;����');
INSERT INTO sys_dic (id, dic_name, dic_category, dic_code, dic_value, dic_desc) VALUES (9, '���ö������', '02', 'object_param', 'SYS_USER_LOGIN_NAME', '��¼�˻�');

INSERT INTO sys_menu (id, code, menu_name, menu_name_en, menu_name_zh, menu_url, menu_status, menu_order, menu_parent_id, menu_type, menu_attribute, menu_report_tool, menu_remark, is_delete, create_time, create_user, update_time, update_user) VALUES ('20160407141712851', 'MS000001', 'ϵͳ����', 'System management', 'ϵͳ����', '', '1', 1, '', '01', ' ', '', '', '0', '2016-05-10 09:30:00', 'admin', '2016-05-10 09:30:00', 'admin');
INSERT INTO sys_menu (id, code, menu_name, menu_name_en, menu_name_zh, menu_url, menu_status, menu_order, menu_parent_id, menu_type, menu_attribute, menu_report_tool, menu_remark, is_delete, create_time, create_user, update_time, update_user) VALUES ('20160407141758754', 'MS000002', '������', 'Report platform', '����ƽ̨', '/reportTool', '1', 2, '20160407141712851', '01', ' ', '', '', '0', '2016-05-10 09:30:00', 'admin', '2016-05-10 09:30:00', 'admin');
INSERT INTO sys_menu (id, code, menu_name, menu_name_en, menu_name_zh, menu_url, menu_status, menu_order, menu_parent_id, menu_type, menu_attribute, menu_report_tool, menu_remark, is_delete, create_time, create_user, update_time, update_user) VALUES ('20160407141854450', 'MS000005', '�������', 'Report management', '�������', '/sysReport', '1', 4, '20160407141712851', '01', ' ', '', '', '0', '2016-05-10 09:30:00', 'admin', '2016-05-10 09:30:00', 'admin');
INSERT INTO sys_menu (id, code, menu_name, menu_name_en, menu_name_zh, menu_url, menu_status, menu_order, menu_parent_id, menu_type, menu_attribute, menu_report_tool, menu_remark, is_delete, create_time, create_user, update_time, update_user) VALUES ('20160407141854454', 'MS000003', '�������', 'Report parameters', '�������', '/reportParam', '1', 3, '20160407141712851', '01', '', '', '', '0', '2016-05-10 09:30:00', 'admin', '2016-05-10 09:30:00', 'admin');
INSERT INTO sys_menu (id, code, menu_name, menu_name_en, menu_name_zh, menu_url, menu_status, menu_order, menu_parent_id, menu_type, menu_attribute, menu_report_tool, menu_remark, is_delete, create_time, create_user, update_time, update_user) VALUES ('20160407141854455', 'MS000004', '�˵�����', 'Menu management', '�˵�����', '/sysMenu', '1', 5, '20160407141712851', '01', ' ', '', '', '0', '2016-05-10 09:30:00', 'admin', '2016-05-10 09:30:00', 'admin');
INSERT INTO sys_menu (id, code, menu_name, menu_name_en, menu_name_zh, menu_url, menu_status, menu_order, menu_parent_id, menu_type, menu_attribute, menu_report_tool, menu_remark, is_delete, create_time, create_user, update_time, update_user) VALUES ('20160407141854456', 'MS000007', '�û�����', 'User management', '�û�����', '/sysUser', '1', 7, '20160407141712851', '01', ' ', '', '', '0', '2016-05-10 09:30:00', 'admin', '2016-05-10 09:30:00', 'admin');
INSERT INTO sys_menu (id, code, menu_name, menu_name_en, menu_name_zh, menu_url, menu_status, menu_order, menu_parent_id, menu_type, menu_attribute, menu_report_tool, menu_remark, is_delete, create_time, create_user, update_time, update_user) VALUES ('20160407141854457', 'MS000006', '��ɫ����', 'Role management', '��ɫ����', '/sysRole', '1', 6, '20160407141712851', '01', ' ', '', '', '0', '2016-05-10 09:30:00', 'admin', '2016-05-10 09:30:00', 'admin');
INSERT INTO sys_menu (id, code, menu_name, menu_name_en, menu_name_zh, menu_url, menu_status, menu_order, menu_parent_id, menu_type, menu_attribute, menu_report_tool, menu_remark, is_delete, create_time, create_user, update_time, update_user) VALUES ('20160407141854458', 'MS000008', '������־', 'Access log', '������־', '/sysLog', '1', 8, '20160407141712851', '01', ' ', '', '', '0', '2016-05-10 09:30:00', 'admin', '2016-05-10 09:30:00', 'admin');


INSERT INTO sys_role (id, code, role_name, role_name_en, role_name_zh, role_status, sub_role_id, role_type, is_delete, role_remark, create_time, create_user, update_time, update_user) VALUES ('20160407143157444', 'SR0000001', '��������Ա', 'Super administrator', '��������Ա', '1', '', '1', '0', '', '2016-05-10 09:30:00', 'admin', '2016-05-10 09:30:00', 'admin');

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

