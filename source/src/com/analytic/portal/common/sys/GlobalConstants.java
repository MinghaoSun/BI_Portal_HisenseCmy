package com.analytic.portal.common.sys;

/**
 * 全局变量
 * @author admin
 */
public class GlobalConstants {
	/**页面访问：状态*/ 
	public final static String SUCCESS = "Success";// 获取成功
	public final static int SUCCESS_STATE = 200;// 获取成功的状态码
	public final static String FAILURE = "failure";// 获取失败
	public final static int FAILURE_STATE = 500;// 获取失败的状态码
	public final static int FAILURE_NOTEXIST_STATE = 501;// 不存在状态码
	public final static int FAILURE_EXIST_STATE = 40301;// 已存在
	public final static int FORBIDDEN_STATE = 403;// 禁止访问
	public final static String RESPONSE_CODE="code";
	/**页面访问：分页*/
	public static final String PAGE_PAGE_SIZE ="pageSize";// 页面数据标识
	public static final int PAGE_SIZE = 10;// 每页10条
	public static final String PAGE_CURRENT_PAGE ="currentPage";// 当前页的标识
	public static final int PAGE_START=1;//开始页

	/**标识符：国际化*/
	public static final String I18N_EN_US = "en-US";
	public static final String I18N_ZH_CN = "zh-CN";
	public static final String I18N_ZH = "zh";
	public static final String I18N_CN = "CN";
	public static final String I18N_COUNTRY_CN = "localCountry";
	public static final String I18N_LOCAL_LANGUAGE="localLanguage";
	/**国际化：通过操作系统适配国际化*/
	public static final String I18N_START_OS="1";
	/**国际化：通过配置文件适配国际化*/
	public static final String I18N_START_CONFIG="0";
	/**操作系统：系统语言*/
	public static final String OS_ENV_LANGUAGE="os_language";
	/**操作系统：系统国家*/
	public static final String OS_ENV_COUNTRY="os_country";
	
	/**标识符：执行状态*/
	public static final int SUCCESS_CODE = 0;
	public static final int ERROR_CODE = -1;
	/**标识符：数据状态*/
	public static final String DATA_STATUS_NORMAL = "0";
	public static final String DATA_STATUS_DELETE = "1";
	/**标识符：缓冲*/
	public static final String CACHE_CONFIG_SIGN = "sysConfig";
	public static final String CACHE_DIC_SIGN = "sysDic";
	/**标识符：会话User*/
	public static final String SESSION_USER = "userSession";
	/**标识符：会话Token*/
	public static final String SESSION_Token = "tokenSession";
	/**标识符：会话SESS*/
	public static final String SESSION_SESS = "enterpriseSession";
	/**标识符：会话用户密码*/
	public static final String SESSION_USER_PASS = "loginUserPassword";
	
	/**业务编码:标识定义*/
	public static final String BUSINESS_ENCODING_REPORT_TOOL="RT";//报表工具
	public static final String BUSINESS_ENCODING_REPORT_PARAM="RP";//报表参数
	public static final String BUSINESS_ENCODING_MENU_SYSTEM="MS";//系统菜单
	public static final String BUSINESS_ENCODING_MENU_REPORT="MR";//报表菜单
	public static final String BUSINESS_ENCODING_MENU_OTHER="MT";//其它系统
	public static final String BUSINESS_ENCODING_SYSTEM_ROLE="SR";//系统角色
	/**业务编码:起始编码*/
	public static final String BUSINESS_ENCODING_START="000001";
	
	/**数据字典：分类*/
	public static final String DIC_SYS_MENU_TYPE="sys_menu_type";
	public static final String DIC_SYS_USER_SEX="sys_user_sex";
	public static final String DIC_SYS_OPER_STAUTS="sys_oper_stauts";
	public static final String DIC_SYS_ROLE_TYPE="sys_role_type";
	public static final String DIC_SYS_OPER_TYPE="sys_oper_type";
	public static final String DIC_REPORT_TOOL_CATEGORY="report_tool_category";
	public static final String DIC_REPORT_DISPLAY_MODE="report_display_mode";
	public static final String DIC_REPORT_MENU_TYPE="report_menu_type";
	public static final String DIC_REPORT_DESIGN_TYPE="report_design_type";
	public static final String DIC_OBJECT_PARAM="object_param";
	public static final String DIC_SYS_PARAM_RELATE_DATA="sys_param_relate_data";
	public static final String DIC_SYS_PARAM_OPERAT_TYPE="sys_param_operat_type";
	public static final String DIC_SYS_PARAM_VALUE_TYPE="sys_param_value_type";
	/**数据字典：数据项分隔符*/
	public static final String DIC_ITEM_SPLIT_SIGN=";";
	/**数据字典：菜单类型*/
	public static final String SYS_MENU_TYPE_MANAGE="systemManage";
	public static final String SYS_MENU_TYPE_REPORT="reportSystem";
	public static final String SYS_MENU_TYPE_OTHER="otherSystem";
	/**数据字典:用户性别*/
	public static final String SYS_USER_SEX_MALE="male";
	public static final String SYS_USER_SEX_FEMALE="female";
	/**数据字典：操作状态*/
	public static final String SYS_OPER_STATUS_ENABLE="enable";
	public static final String SYS_OPER_STATUS_DISABLE="disable";
	/**数据字典：角色类型*/
	public static final String SYS_ROLE_TYPE_FUNCTION="functionRole";
	public static final String SYS_ROLE_TYPE_REPORT="reportRole";
	public static final String SYS_ROLE_TYPE_DATA="dataRole";
	/**数据字典：操作类型*/
	public static final String SYS_OPER_TYPE_LOGIN="loginAction";
	public static final String SYS_OPER_TYPE_LOGOUT="logoutAction";
	public static final String SYS_OPER_TYPE_ADD="addAction";
	public static final String SYS_OPER_TYPE_DELETE="deleteAction";
	public static final String SYS_OPER_TYPE_UPDATE="updateAction";
	public static final String SYS_OPER_TYPE_QUERY="queryAction";
	/**数据字典：报表工具类型*/
	public static final String REPORT_TOOL_CATEGORY_BO="reportToolBO";
	public static final String REPORT_TOOL_CATEGORY_QV="reportToolQV";
	public static final String REPORT_TOOL_CATEGORY_TABLEAU="reportToolTableau";
	/**数据字典：报表显示方式*/
	public static final String REPORT_DISPLAY_MODE_TAB="tabMode";
	public static final String REPORT_DISPLAY_MODE_WINDOW="windowMode";
	/**数据字典：报表菜单类型*/
	public static final String REPORT_MENU_TYPE_REPORT="menuReport";
	public static final String REPORT_MENU_TYPE_DIRECTORY="menuDirectory";
	
	/**数据字典：报表菜单类型*/
	public static final String REPORT_MENU_DESIGNTYPE_JICHENG="jicheng";
	public static final String REPORT_MENU_DESIGNTYPE_DIY="diy";
	
	/**系统参数：最近访问报表时间*/ 
	public static final String CONFIG_REPORT_RECENTLY_DAY="report_recently_day";
	/**系统参数：最近访问报表显示的条目*/
	public static final String CONFIG_REPORT_RECENTLY_ITEM="report_recently_item";
	/**系统参数：系统版本*/
	public static final String CONFIG_SYS_VERSION="sys_version";
	/**系统参数：版权年份*/
	public static final String CONFIG_COPYRIGHT_YEAR="copyright_year";
	/**系统参数：首页的显示方式*/
	public static final String CONFIG_INDEX_PAGE_DISPLAY="index_page_display";
	/**系统参数：BO报表工具版本*/
	public static final String CONFIG_BO_REPORT_VERSION="bo_report_version";
	/**系统参数：QV报表工具的版本*/
	public static final String CONFIG_QV_REPORT_VERSION="qv_report_version";
	/**系统参数：Tableau报表工具的版本*/
	public static final String CONFIG_TABLEAU_REPORT_VERSION="tableau_report_version";
	/**系统参数：国际化启动方式*/
	public static final String CONFIG_I18N_START_TYPE="i18n_start";
	/**系统参数：登录方式*/
	public static final String CONFIG_LOGIN_TYPE="login_type";
	/**系统参数：BO时效*/
	public static final String CONFIG_BO_SESSION="bo_session";
	/**系统参数：内置对象*/
	public static final String CONFIG_OBJECT_PARAM="object_param";
	
	/**系统日志：操作信息*/
	public static final String lOGGER_LEVEL_INFO="INFO";
	/**系统日志：操作信息开始*/
	public static final String lOGGER_LEVEL_INFO_BEGIN="Begin......";
	/**系统日志：操作信息结束*/
	public static final String lOGGER_LEVEL_INFO_END="End......";
	/**系统日志：异常信息*/
	public static final String lOGGER_LEVEL_ERROR="ERROR";
	
	/**操作方式约定：新增*/
	public static final String lOG_OPER_SAVE="save";
	/**操作方式约定：删除*/
	public static final String lOG_OPER_DELETE="delete";
	/**操作方式约定：修改*/
	public static final String lOG_OPER_UPDATE="update";
	/**操作方式约定：查询*/
	public static final String lOG_OPER_GET="get";
	/**操作方式约定：登录*/
	public static final String lOG_OPER_LOGIN="login";
	/**操作方式约定：注销*/
	public static final String lOG_OPER_LOGOUT="logout";
	
	/**功能菜单：菜单ID*/
	public static final String MENU_OPER_MENU_ID="menuId";
	/**功能菜单：菜单代码*/
	public static final String MENU_OPER_MENU_CODE="menuCode";
	/**功能菜单：菜单名称*/
	public static final String MENU_OPER_MENU_NAME="menuName";
	/**功能菜单：报表菜单*/
	public static final String MENU_OPER_MENU_REPORT="menuReport";
	
	/**报表用户：账户*/
	public static final String REPORT_USER_LOGIN="reportUser";
	/**报表用户：密码*/
	public static final String REPORT_USER_PASSWORD="reportPassword";
	
	/**表单控件：上级菜单*/
	public static final String FORM_CONTROL_MENU_PARENT="menuParentCtrl";
	/**表单控件：菜单参数*/
	public static final String FORM_CONTROL_MENU_PARAM="menuParamCtrl";
	
	/**删除操作：删除结果*/
	public static final String DELETE_OPER_RESULT="deleteResult";
	/**删除操作：删除成功*/
	public static final String DELETE_OPER_DATA_SUCCESS="00";
	/**删除操作：数据占用*/
	public static final String DELETE_OPER_DATA_USING="01";
	/**删除操作：数据丢失*/
	public static final String DELETE_OPER_DATA_REMOVE="02";
	/**删除操作：权限不足*/
	public static final String DELETE_OPER_DATA_FORBID="03";
	
	/**报表请求：请求头参数*/
	public static final String REPORT_REQ_SIGN="BIPortal";
	/**报表请求Ticket：请求失败*/
	public static final String REPORT_TICKET_FAIL="-1";
	/**报表请求Token：请求失败*/
	public static final String REPORT_TOKEN_FAIL="-1";
	/**报表请求Token：请求次数*/
	public static final int REPORT_TOKEN_COUNT=100;
	/**报表请求Token：认证类型*/
	public static final String REPORT_TOKEN_AUTH_TYPE="secEnterprise";
	/**报表请求：请求状态*/
	public static final String REPORT_REQ_STATUS="reqStatus";
	/**报表请求：请求地址*/
	public static final String REPORT_REQ_LOCATION="reqLocation";
	/**报表请求：请求地址*/
	public static final String REPORT_TOOL_TYPE="reportToolType";
	
	/**报表工具版本：Tableau工具版本*/
	public static final String REPORT_VERSION_TABLEAU_920="9.2.0";
	/**报表工具版本：BO工具版本*/
	public static final String REPORT_VERSION_BO_41SP5="4.1 sp5 patch3";
	/**报表工具版本：QV工具版本*/
	public static final String REPORT_VERSION_QV_110="11.0";
	/**首页展示：横向展示*/
	public static final String INDEX_PAGE_DISPLAY_H="H";
	/**首页展示：纵向展示*/
	public static final String INDEX_PAGE_DISPLAY_V="V";
	
	/**内置对象：登录账户*/
	public static final String INNER_OBJECT_LOGIN_NAME="SYS_USER_LOGIN_NAME";

	/*新增*/
	public static final String SYS_PARAM_RELATE_DATA_COMPANY="COMPANY";
	public static final String SYS_PARAM_RELATE_DATA_CATEGORY="CATEGORY";
	public static final String SYS_PARAM_RELATE_DATA_CALENDAR="CALENDAR";
	public static final String SYS_PARAM_OPERAT_TYPE_RADIO="RADIO";
	public static final String SYS_PARAM_OPERAT_TYPE_MULTISELECT="MULTISELECT";
	public static final String SYS_PARAM_OPERAT_TYPE_SINGLEVALUE="SINGLEVALUE";
	public static final String SYS_PARAM_OPERAT_TYPE_MULTIVALUE="MULTIVALUE";

	/**参数值输入方式：输入*/
	public static final String PARAM_VALUE_INPUT="0";
	/**参数值输入方式：选择*/
	public static final String PARAM_VALUE_SELECT="1";
	/**DES加解密：密钥*/
	public static final String SYS_DES_KEY="infopower";
	/**DES加解密：模式*/
	public static final String SYS_DES_MODEL="DES";
	/**DCI系统：账户*/
	public static final String DCI_SYS_LOGIN_NAEM="userName";
	/**DCI系统：密码*/
	public static final String DCI_SYS_LOGIN_PASS="password";
	/**DCI系统：随机码*/
	public static final String DCI_SYS_LOGIN_ID="id";
	public static final String SYS_USER_DEFAULT_THEMES="skin-green-light";
	public static final String SYS_SETTING_TYPE_DEFAULT_REPORT="02";
	public static final String SYS_SETTING_TYPE_COMMON_REPORT="01";
}
