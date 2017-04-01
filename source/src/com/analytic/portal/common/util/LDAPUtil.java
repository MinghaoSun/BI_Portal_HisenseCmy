package com.analytic.portal.common.util;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description LDAP工具类
 * @author Minghao
 * @date 2017年3月20日10:49:46
 */
public class LDAPUtil {
	
	private static final Logger logger=LoggerFactory.getLogger(LDAPUtil.class);
	
	private static final String LDAP_URL = ConfigUtil.get("ad.ip");
	
	/**
	 * @description 获取LDAP用户信息
	 * @param username
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, String> checkLdapInfo(String username){
		Hashtable HashEnv = new Hashtable();
        Map<String, String> ldap = new HashMap<String, String>();
        try {
            HashEnv.put(Context.SECURITY_AUTHENTICATION, "simple"); //LDAP访问安全级别
            HashEnv.put(Context.SECURITY_PRINCIPAL, "uid=bxsyszlgl,ou=applications,o=hisense.com,o=isp"); //AD User
            HashEnv.put(Context.SECURITY_CREDENTIALS, "Bx@SysZL!23"); //AD Password
            HashEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory"); //LDAP工厂类
            HashEnv.put(Context.PROVIDER_URL, LDAP_URL);
            LdapContext ctx = new InitialLdapContext(HashEnv, null);
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String searchFilter="(|(&(objectClass=person)(!(nsaccountlock=true))(departmentNumber=jd_bx*)))";
            String searchBase = "uid="+username+",ou=People,o=hisense.com,o=isp";
            NamingEnumeration<SearchResult> answer = ctx.search(searchBase, searchFilter, searchCtls);
            if (!(answer == null || answer.equals(null))) {
                    while (answer.hasMore()) {
                        SearchResult result = answer.next();
                        NamingEnumeration<? extends Attribute> attrs = result.getAttributes().getAll();
                        while (attrs.hasMore()) {
                            Attribute attr = attrs.next();
                            ldap.put(attr.getID(),attr.get().toString());
                        }
                    }
                ctx.close();
                return ldap;
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}

	/**
	 * @description 验证LDAP用户
	 * @param adName
	 * @param password
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean validateUser(String adName, String password) {
		Hashtable HashEnv = new Hashtable();
		try {
			// window2008 R2 AD IP 192.168.6.89
			// String ad_name = ConfigUtil.get("ad.name");
			// 注意用户名的写法：domain\User 或 User@domain.com
			// String adminName = ad_name + adName;
			// String adminPassword = password;
			HashEnv.put(Context.SECURITY_AUTHENTICATION, "simple"); // LDAP访问安全级别
			/*
			 * HashEnv.put(Context.SECURITY_PRINCIPAL, adminName); //AD User
			 * HashEnv.put(Context.SECURITY_CREDENTIALS, adminPassword); //AD
			 * Password
			 */
			// HashEnv.put(Context.SECURITY_PRINCIPAL,
			// "uid="+adName+",ou=applications,o=hisense.com,o=isp"); //AD User
			HashEnv.put(Context.SECURITY_PRINCIPAL, "uid=" + adName + ",ou=People,o=hisense.com,o=isp"); // AD
			HashEnv.put(Context.SECURITY_CREDENTIALS, password); // AD Password
			HashEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory"); // LDAP工厂类
			HashEnv.put(Context.PROVIDER_URL, LDAP_URL);
			LdapContext ctx = new InitialLdapContext(HashEnv, null);
			SearchControls searchCtls = new SearchControls();
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			/*
			 * String searchFilter = "sAMAccountName=lwang168"; String
			 * searchBase = "DC=ap,DC=jnj,DC=com";
			 */
			// String searchFilter =
			// "(|(&(objectClass=person)(!(nsaccountlock=true))(departmentNumber=jd_bx*))(uid=yujie)(uid=huangxiaojian)(uid=jiashaoqian))";
			// String searchBase = "ou=orgs,o=hisense.com,o=isp";
			String searchFilter = "(|(&(objectClass=person)(!(nsaccountlock=true))(departmentNumber=jd_bx*))(uid=yujie)(uid=huangxiaojian)(uid=jiashaoqian))";
			String searchBase = "ou=orgs,o=hisense.com,o=isp";
			NamingEnumeration answer = ctx.search(searchBase, searchFilter, searchCtls);
			if (!(answer == null || answer.equals(null))) {
				ctx.close();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("LDAP验证出错！");
			e.printStackTrace();
			return false;
		}
	}
}
