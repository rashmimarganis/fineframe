package com.izhi.platform.security.support;

public class Constants {
	public static final String DEFAULT_ROLE="IZHI_WEBAPP_ONLINE_ROLE";
	public static final String APP_MANAGER_THEME="IZHI_APP_MANAGER_THEME";
	
	/** The name of the ResourceBundle used in this application */
	public static final String BUNDLE_KEY = "ApplicationResources";

	/** The encryption algorithm key to be used for passwords */
	public static final String ENC_ALGORITHM = "algorithm";

	/** A flag to indicate if passwords should be encrypted */
	public static final String ENCRYPT_PASSWORD = "encryptPassword";

	/** File separator from System properties */
	public static final String FILE_SEP = System.getProperty("file.separator");

	/** User home from System properties */
	public static final String USER_HOME = System.getProperty("user.home")
			+ FILE_SEP;

	/** The name of the configuration hashmap stored in application scope. */
	public static final String CONFIG = "appConfig";

	/**
	 * Session scope attribute that holds the locale set by the user. By setting
	 * this key to the same one that Struts uses, we get synchronization in
	 * Struts w/o having to do extra work or have two session-level variables.
	 */
	public static final String PREFERRED_LOCALE_KEY = "org.apache.struts.action.LOCALE";

	/**
	 * The request scope attribute under which an editable user form is stored
	 */
	public static final String USER_KEY = "userForm";

	/**
	 * The request scope attribute that holds the user list
	 */
	public static final String USER_LIST = "userList";

	/**
	 * The request scope attribute for indicating a newly-registered user
	 */
	public static final String REGISTERED = "registered";

	/**
	 * The name of the Administrator role, as specified in web.xml
	 */
	public static final String ADMIN_ROLE = "admin";

	/**
	 * The name of the User role, as specified in web.xml
	 */
	public static final String USER_ROLE = "user";

	/**
	 * The name of the user's role list, a request-scoped attribute when
	 * adding/editing a user.
	 */
	public static final String USER_ROLES = "userRoles";

	/**
	 * The name of the available roles list, a request-scoped attribute when
	 * adding/editing a user.
	 */
	public static final String AVAILABLE_ROLES = "availableRoles";

	/**
	 * The name of the CSS Theme setting.
	 */
	public static final String CSS_THEME = "csstheme";
	
	/**
	 * total count string 
	 */
	public static final String totalCountString = "totalCount";
	
	public static final String SUCCESS_STRING = "success";
	public static final String Error_STRING = "error";
	/**
	 * 设备审核状态值
	 */
	// 审核通过
	public static final Integer AUDIT_PASS = 1;
	// 审核不通过
	public static final Integer AUDIT_REFUSE = 0;
	// 正在审核中
	public static final Integer AUDIT_ING = 2;
	
	/**
	 * 招标状态标志
	 */
	// 未开始
	public static final Integer TENDER_UNSTART = 0 ;
	// 进行中
	public static final Integer TENDER_ING = 1;
	// 已完成
	public static final Integer TENDER_COMPLETE = 2;
	
	/**
	 * 设备状态取值
	 * 申请中 -2，审核不通过-0，审核通过-1，正在招标-3，招标成功-4，招标失败-5
	 */
	// 申请中的设备
	public static final Integer EQUIPMENT_APP = 2;
	// 审核不通过
	public static final Integer EQUIPMENT_AUDIT_REFUSE = 0;
	// 审核通过
	public static final Integer EQUIPMENT_AUDIT_PASS = 1;
	// 正在招标中
	public static final Integer EQUIPMENT_TENDERING = 3;
	// 招标成功
	public static final Integer EQUIPMENT_TENDER_SUCCESS = 4;
	// 招标失败
	public static final Integer EQUIPMENT_TENDER_FAIL = 5;
	
	/**
	 * 贵重设备价格：
	 */
	public static final double PRECIOUS_EQUIPMENT_PRICE = 100000.00 ;
	// 普通设备
	public static final Integer NORMAL_EQUIPMENT = 0;
	// 贵重设备 
	public static final Integer PRECIOUS_EQUIPMENT = 1;
	
	/**
	 * 设备项招标信息
	 */
	// 未招标
	public static final Integer PACKAGE_ITEM_TENDER_UNSTART = 10;
	// 正在招标中
	public static final Integer PACKAGE_ITEM_TENDER_ING = 11;
	// 招标成功
	public static final Integer PACKAGE_ITEM_TENDER_SUCCESS = 12;
	// 招标失败
	public static final Integer PACKAGE_ITEM_TENDER_FAIL = 13 ;
	/**
	 * 设备项招标信息的状态值 转换为 采购设备的状态值。
	 * @param pistate
	 * @return
	 */
	public static final Integer converPiStateToEquipState(Integer pistate ){
		if( pistate.equals(PACKAGE_ITEM_TENDER_ING)){
			return EQUIPMENT_TENDERING;
		}
		else if( pistate.equals(PACKAGE_ITEM_TENDER_SUCCESS)){
			return EQUIPMENT_TENDER_SUCCESS;
		}
		else if( pistate.equals(PACKAGE_ITEM_TENDER_FAIL)) {
			return EQUIPMENT_TENDER_FAIL;
		}
		return null;
	}
	
}
