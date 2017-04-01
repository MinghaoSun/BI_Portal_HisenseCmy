
package com.analytic.portal.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.BaseDigestPasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import com.yzd.plat.common.util.security.MD5Encryption;

/**
 * 用户登录密码的验证
 * @author Louis
 *
 */
public class UserPasswordEncoder extends BaseDigestPasswordEncoder implements PasswordEncoder {

	public String encodePassword(String rawPass, Object salt) throws DataAccessException {
		return MD5Encryption.encrypt(rawPass);

	}

	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		boolean flag = true;
		String pass1 = "" + encPass;
		String pass2 = encodePassword(rawPass, salt);
		if (!pass1.equals(pass2)) {
			flag = false;
		}
		return flag;
	}

	public static void main(String[] args){
		String pwd=MD5Encryption.encrypt("infopower168plat");
	}
}
