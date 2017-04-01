package com.analytic.portal.common.resolver;

/**
 * @ClassName: AuthorizationException
 * @Description: TODO(权限过滤的异常)
 * @author Minghao
 * @date 2017年3月17日14:17:56
 */
public class AuthorizationException extends RuntimeException {

	private static final long serialVersionUID = -7606877557097550755L;

	public AuthorizationException() {
	};

	public AuthorizationException(String msg) {
		super(msg);
	};
}
