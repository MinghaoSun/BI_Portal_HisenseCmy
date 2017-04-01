package com.analytic.portal.common.resolver;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import com.analytic.portal.common.util.ExceptionHelper;
import net.sf.json.JSONObject;


/**
 * @ClassName: PlatformMappingExceptionResolver
 * @Description: TODO(平台异常信息跳转、解析)
 * @author Minghao
 * @date 2017年3月17日14:18:16
 * @version 1.0-SNAPSHOT
 */
@Component
public class MvcPlatformMappingExceptionResolver extends SimpleMappingExceptionResolver {
	private static final Logger logger = LoggerFactory.getLogger(MvcPlatformMappingExceptionResolver.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		System.out.println(request.getRequestURL().toString());
		MyResult result = new MyResult();
		result.setResult(false);
		if (ex instanceof AuthorizationException) {
			//TODO 前台传递过来的header中都没有“X-Requested-With”无奈啊
			/*if (!isAjaxRequest(request)) {
				System.out.println("非ajax请求");
				return new ModelAndView("redirect:/login.html");
			}else{
				System.out.println("ajax请求");
			}*/
			result.setContent(ex.getMessage());
		} else {
			logger.error(ExceptionHelper.getTrace(ex));
			if (!isAjaxRequest(request)) {
				return new ModelAndView("error/502");
			}
			result.setContent("操作发生异常，请稍后再试或者联系管理员");
		}
		PrintWriter writer = null;
		// 如果浏览器是IE浏览器，就得进行编码转换
		/*String agent = request.getHeader("User-Agent");   
		if (agent.contains("MSIE")||agent.indexOf("rv:11.0")>-1) {
			response.setHeader("Content-type", "text/json;charset=UTF-8");
		} else {
			response.setHeader("Content-type", "application/json;charset=UTF-8");
		}*/
		response.setCharacterEncoding("utf-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cahce-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("text/json; charset=utf-8");
		try {
			writer = response.getWriter();
		} catch (Exception e) {
			logger.error(ExceptionHelper.getTrace(e));
		}
		JSONObject json=JSONObject.fromObject(result);
		writer.write(json.toString());
		writer.flush();
		return new ModelAndView();
		//return new ModelAndView("redirect:/login.html");
	}

	/**
	 * 判断是否为Ajax请求
	 * 
	 * @param request
	 * @return
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		if (header != null && "XMLHttpRequest".equals(header))
			return true;
		else
			return false;
	}
}
