package com.analytic.portal.module.report.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.analytic.portal.module.common.controller.BaseController;
import com.analytic.portal.module.report.model.AllExcelInfo;
import net.sf.json.JSONObject;
import com.analytic.portal.module.report.service.interfaces.DIYReportService;
import com.analytic.portal.module.report.vo.AllTypeVO;
import com.analytic.portal.module.report.vo.AlltypeDIYReportVO;
import com.analytic.portal.module.report.vo.AlltypeParam;
import com.analytic.portal.module.report.vo.D3VO;

@Controller
@RequestMapping("/diyreport")
public class DIYReportContoller extends BaseController {
	
	@Autowired
	private DIYReportService dIYReportService;

	@RequestMapping("/testvalue2")
	public void testValue(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AllExcelInfo> list = new ArrayList<>();
		if(request.getParameter("areaName") == null){
			AllExcelInfo obj1 = new AllExcelInfo();
			obj1.setAreaId("1");
			obj1.setAreaName("西南");
			obj1.setProductId("1");
			obj1.setProductName("冰箱");
			obj1.setSale_amt("5680");
			obj1.setSale_num("568");
			obj1.setHisense_sale_num("79");
			AllExcelInfo obj2 = new AllExcelInfo();
			obj2.setAreaId("2");
			obj2.setAreaName("华北");
			obj2.setProductId("1");
			obj2.setProductName("冰箱");
			obj2.setSale_amt("3330");
			obj2.setSale_num("333");
			obj2.setHisense_sale_num("123");
			AllExcelInfo obj3 = new AllExcelInfo();
			obj3.setAreaId("3");
			obj3.setAreaName("东北");
			obj3.setProductId("1");
			obj3.setProductName("冰箱");
			obj3.setSale_amt("2740");
			obj3.setSale_num("274");
			obj3.setHisense_sale_num("135");
			AllExcelInfo obj4 = new AllExcelInfo();
			obj4.setAreaId("1");
			obj4.setAreaName("西南");
			obj4.setProductId("2");
			obj4.setProductName("空调");
			obj4.setSale_amt("5680");
			obj4.setSale_num("568");
			obj4.setHisense_sale_num("333");
			AllExcelInfo obj5 = new AllExcelInfo();
			obj5.setAreaId("2");
			obj5.setAreaName("华北");
			obj5.setProductId("2");
			obj5.setProductName("空调");
			obj5.setSale_amt("3330");
			obj5.setSale_num("333");
			obj5.setHisense_sale_num("69");
			AllExcelInfo obj6 = new AllExcelInfo();
			obj6.setAreaId("3");
			obj6.setAreaName("东北");
			obj6.setProductId("2");
			obj6.setProductName("空调");
			obj6.setSale_amt("2740");
			obj6.setSale_num("274");
			obj6.setHisense_sale_num("111");
			AllExcelInfo obj7 = new AllExcelInfo();
			obj7.setAreaId("1");
			obj7.setAreaName("西南");
			obj7.setProductId("3");
			obj7.setProductName("电视");
			obj7.setSale_amt("2740");
			obj7.setSale_num("274");
			obj7.setHisense_sale_num("83");
			AllExcelInfo obj8 = new AllExcelInfo();
			obj8.setAreaId("2");
			obj8.setAreaName("华北");
			obj8.setProductId("3");
			obj8.setProductName("电视");
			obj8.setSale_amt("5680");
			obj8.setSale_num("568");
			obj8.setHisense_sale_num("421");
			AllExcelInfo obj9 = new AllExcelInfo();
			obj9.setAreaId("3");
			obj9.setAreaName("东北");
			obj9.setProductId("3");
			obj9.setProductName("电视");
			obj9.setSale_amt("2740");
			obj9.setSale_num("274");
			obj9.setHisense_sale_num("134");
			list.add(obj1);
			list.add(obj2);
			list.add(obj3);
			list.add(obj4);
			list.add(obj5);
			list.add(obj6);
			list.add(obj7);
			list.add(obj8);
			list.add(obj9);
			
		}else{
			String areaName = request.getParameter("areaName");
			if(areaName.equals("东北")){
				AllExcelInfo obj1 = new AllExcelInfo();
				obj1.setAreaId("1");
				obj1.setAreaName("长春");
				obj1.setProductId("1");
				obj1.setProductName("冰箱");
				obj1.setSale_amt("3240");
				obj1.setSale_num("254");
				obj1.setHisense_sale_num("62");
				AllExcelInfo obj2 = new AllExcelInfo();
				obj2.setAreaId("2");
				obj2.setAreaName("哈尔滨");
				obj2.setProductId("2");
				obj2.setProductName("冰箱");
				obj2.setSale_amt("3721");
				obj2.setSale_num("412");
				obj2.setHisense_sale_num("123");
				AllExcelInfo obj3 = new AllExcelInfo();
				obj3.setAreaId("3");
				obj3.setAreaName("铁岭");
				obj3.setProductId("3");
				obj3.setProductName("冰箱");
				obj3.setSale_amt("950");
				obj3.setSale_num("63");
				obj3.setHisense_sale_num("41");
				AllExcelInfo obj4 = new AllExcelInfo();
				obj4.setAreaId("1");
				obj4.setAreaName("长春");
				obj4.setProductId("2");
				obj4.setProductName("空调");
				obj4.setSale_amt("6210");
				obj4.setSale_num("600");
				obj4.setHisense_sale_num("423");
				AllExcelInfo obj5 = new AllExcelInfo();
				obj5.setAreaId("2");
				obj5.setAreaName("哈尔滨");
				obj5.setProductId("2");
				obj5.setProductName("空调");
				obj5.setSale_amt("1210");
				obj5.setSale_num("100");
				obj5.setHisense_sale_num("69");
				AllExcelInfo obj6 = new AllExcelInfo();
				obj6.setAreaId("3");
				obj6.setAreaName("铁岭");
				obj6.setProductId("2");
				obj6.setProductName("空调");
				obj6.setSale_amt("2740");
				obj6.setSale_num("274");
				obj6.setHisense_sale_num("150");
				AllExcelInfo obj7 = new AllExcelInfo();
				obj7.setAreaId("1");
				obj7.setAreaName("长春");
				obj7.setProductId("3");
				obj7.setProductName("电视");
				obj7.setSale_amt("2740");
				obj7.setSale_num("274");
				obj7.setHisense_sale_num("83");
				AllExcelInfo obj8 = new AllExcelInfo();
				obj8.setAreaId("2");
				obj8.setAreaName("哈尔滨");
				obj8.setProductId("3");
				obj8.setProductName("电视");
				obj8.setSale_amt("5680");
				obj8.setSale_num("568");
				obj8.setHisense_sale_num("421");
				AllExcelInfo obj9 = new AllExcelInfo();
				obj9.setAreaId("3");
				obj9.setAreaName("铁岭");
				obj9.setProductId("3");
				obj9.setProductName("电视");
				obj9.setSale_amt("1640");
				obj9.setSale_num("130");
				obj9.setHisense_sale_num("80");
				AllExcelInfo obj10 = new AllExcelInfo();
				obj10.setAreaId("1");
				obj10.setAreaName("长春");
				obj10.setProductId("4");
				obj10.setProductName("激光影院");
				obj10.setSale_amt("2235");
				obj10.setSale_num("200");
				obj10.setHisense_sale_num("145");
				AllExcelInfo obj11 = new AllExcelInfo();
				obj11.setAreaId("2");
				obj11.setAreaName("哈尔滨");
				obj11.setProductId("4");
				obj11.setProductName("激光影院");
				obj11.setSale_amt("1452");
				obj11.setSale_num("53");
				obj11.setHisense_sale_num("22");
				AllExcelInfo obj12 = new AllExcelInfo();
				obj12.setAreaId("3");
				obj12.setAreaName("铁岭");
				obj12.setProductId("4");
				obj12.setProductName("激光影院");
				obj12.setSale_amt("1400");
				obj12.setSale_num("120");
				obj12.setHisense_sale_num("66");
				list.add(obj1);
				list.add(obj2);
				list.add(obj3);
				list.add(obj4);
				list.add(obj5);
				list.add(obj6);
				list.add(obj7);
				list.add(obj8);
				list.add(obj9);
				list.add(obj10);
				list.add(obj11);
				list.add(obj12);
			}
		}
		map.put("list", list);
		JSONObject jo = JSONObject.fromObject(map);
		String json = jo.toString();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
		out.close();
	}
	@RequestMapping("/testvalue")
	public void testValue2(HttpServletRequest request ,HttpServletResponse response) throws IOException{
		List<AllTypeVO> vo=dIYReportService.getReportResultByParam();
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("list", vo);
		writerJSON(map, response);
		/*JSONObject jo = JSONObject.fromObject(map);
		String json = jo.toString();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
		out.close();*/
	}
	/**
	 * @description 获取全品类VO
	 * @param param
	 * @param request
	 * @param responses
	 */
	@RequestMapping("/alltypevalue")
	public void getAllTypeResultVO(HttpServletRequest request ,HttpServletResponse responses){
		AlltypeParam param=new AlltypeParam();
		if(StringUtils.isNotBlank(request.getParameter("ym"))){
			param.setYm(request.getParameter("ym"));
		}
		if(StringUtils.isNotBlank(request.getParameter("saleType"))){
			param.setSaleType(request.getParameter("saleType"));
		}
		if(StringUtils.isNotBlank(request.getParameter("tblType"))){
			param.setTblType(request.getParameter("tblType"));
		}
		if(StringUtils.isNotBlank(request.getParameter("queryLat"))){
			param.setQueryLat(request.getParameter("queryLat"));
		}
		if(StringUtils.isNotBlank(request.getParameter("areaName"))){
			param.setAreaName(request.getParameter("areaName"));
		}
		if(StringUtils.isNotBlank(request.getParameter("centerName"))){
			param.setCenterName(request.getParameter("centerName"));
		}
		if(StringUtils.isNotBlank(request.getParameter("proName"))){
			param.setProName(request.getParameter("proName"));
		}
		if(StringUtils.isNotBlank(request.getParameter("cityName"))){
			param.setCityName(request.getParameter("cityName"));
		}
		/*param.setYm("201601");
		param.setTblType("0");
		param.setQueryLat("1");
		param.setOlType("0");
		param.setSaleType("0");
		param.setAreaName("东北");
		param.setCenterName("长春");*/
		AlltypeDIYReportVO vo=dIYReportService.getAllTypeResult(param);
		writerJSON(vo, responses);
	}

}
