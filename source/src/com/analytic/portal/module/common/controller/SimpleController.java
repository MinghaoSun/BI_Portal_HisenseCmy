package com.analytic.portal.module.common.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/simple")
public class SimpleController extends BaseController{

		//映射路径/simple/index当访问这个路径时，执行这个方法
		@RequestMapping("/index")
		public String index(HttpServletRequest request ,HttpServletResponse response){
	               //response,request会自动传进来
			request.setAttribute("message", "Hello,This is a example of Spring3 RESTful!");
			return "index.jsp";
		}
				
		//根据ID获取不同的内容，通过@PathVariable 获得属性
		@RequestMapping(value="/{id}",method=RequestMethod.GET)
		public String get(@PathVariable String id,HttpServletRequest request ,HttpServletResponse response) throws IOException{
			String name=request.getParameter("name");
			request.setAttribute("message", "Hello,This is a example of Spring3 RESTful!<br/>ID:"+id+"<br/>Name:"+name);
			return "index.jsp";
		}
		
		//根据ID获取不同的内容，通过@PathVariable 获得属性,以流的形式返回数据
		@RequestMapping(value="/getById/{id}",method=RequestMethod.GET)
		public void getById(@PathVariable String id,HttpServletRequest request ,HttpServletResponse response) throws IOException{
			String name=this.getParameter(request, "name");
			String sex=this.getParameter(request, "sex");
			String content="You put id is : "+id+",name:"+name+",sex:"+sex+",parmasLength:"+request.getParameterMap().size();
			this.writeJSONResult(200, "获取成功", content, response);
			//response.getWriter().write("You put id is : "+id);
		}
				
		//通过@RequestBody获取数据流,以流的形式返回数据
		@RequestMapping(value="/postInfoByBody",method=RequestMethod.POST)
		public void postInfoByBody(@RequestBody String bodyInfo,HttpServletRequest request ,HttpServletResponse response) throws IOException{
			this.writeJSONResult(200, "获取成功", bodyInfo, response);
		}
		
		//新增信息，以流的形式返回数据
		@RequestMapping(value="/saveInfo",method=RequestMethod.POST)
		public void saveInfo(HttpServletRequest request ,HttpServletResponse response) throws IOException{
			String name=this.getParameter(request, "name");
			String sex=this.getParameter(request, "sex");
			String content="You post id info,name:"+name+",sex:"+sex+",parmasLength:"+request.getParameterMap().size();
			this.writeJSONResult(200, "获取成功", content, response);
			//response.getWriter().write("You put id is : "+id);
		}
		
		//修个信息，以流的形式返回数据
		@RequestMapping(value="/updateInfo/{id}",method=RequestMethod.PUT)
		public void updateInfo(@PathVariable String id,HttpServletRequest request ,HttpServletResponse response) throws IOException{
			String name=this.getParameter(request, "name");
			String sex=this.getParameter(request, "sex");
			String content="You update info id"+id+",name:"+name+",sex:"+sex+",parmasLength:"+request.getParameterMap().size();
			this.writeJSONResult(200, "获取成功", content, response);
			//response.getWriter().write("You put id is : "+id);
		}
		
		//修个信息，以流的形式返回数据
		@RequestMapping(value="/del/{id}",method=RequestMethod.DELETE)
		public void del(@PathVariable String id,HttpServletRequest request ,HttpServletResponse response) throws IOException{
			String name=this.getParameter(request, "name");
			String sex=this.getParameter(request, "sex");
			String content="You delete info id"+id+",name:"+name+",sex:"+sex+",parmasLength:"+request.getParameterMap().size();
			this.writeJSONResult(200, "获取成功", content, response);
			//response.getWriter().write("You put id is : "+id);
		}
}
