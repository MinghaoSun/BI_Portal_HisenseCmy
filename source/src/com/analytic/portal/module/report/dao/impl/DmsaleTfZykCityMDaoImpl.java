package com.analytic.portal.module.report.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.analytic.portal.module.common.dao.IBaseDao;
import com.analytic.portal.module.report.dao.interfaces.DmsaleTfZykCityMDao;
import com.analytic.portal.module.report.vo.AlltypeParam;
import com.analytic.portal.module.report.vo.QPLDIYReportVO;
@Repository("dmsaleTfZykCityMDao")
public class DmsaleTfZykCityMDaoImpl implements DmsaleTfZykCityMDao{
	
	
	@Autowired
	@Qualifier(value="iBaseDaoHsbi")
	private IBaseDao iBaseDao;

	@SuppressWarnings("unchecked")
	@Override
	public List<QPLDIYReportVO> getReportResultByParam(String region,String market,String month,String oltype,String type) {
		String strSQL="";
		String strSQL2="";
		String[] params=new String[]{oltype,month};
		if(StringUtils.isBlank(region)&&StringUtils.isBlank(market)){
		   strSQL="select region_ggrp,line_name,sum(sale_qty)as qty,sum(sale_amt) as amt,sum(sale_lm_qty) as lmqty,sum(sale_lm_amt) as lmamt,sum(sale_lym_qty) as lymqty,sum(sale_lym_amt) as lymamt from dmsale_tf_zyk_city_m  where brand_name='海信' and ol_typ=? and dt_month=? group by line_name,region_ggrp order by region_ggrp,line_name";
		   strSQL2="select region_ggrp,line_name,sum(sale_qty)as qty,sum(sale_amt) as amt,sum(sale_lm_qty) as lmqty,sum(sale_lm_amt) as lmamt,sum(sale_lym_qty) as lymqty,sum(sale_lym_amt) as lymamt from dmsale_tf_zyk_city_m  where ol_typ=? and dt_month=? group by line_name,region_ggrp order by region_ggrp,line_name";
		}else if(StringUtils.isNotBlank(region)&&StringUtils.isBlank(market)){
		   strSQL="select markt_center,line_name,sum(sale_qty)as qty,sum(sale_amt) as amt,sum(sale_lm_qty) as lmqty,sum(sale_lm_amt) as lmamt,sum(sale_lym_qty) as lymqty,sum(sale_lym_amt) as lymamt from dmsale_tf_zyk_city_m  where brand_name='海信' and ol_typ=? and dt_month=? and region_ggrp=?  group by line_name,markt_center order by markt_center,line_name";
		   strSQL2="select markt_center,line_name,sum(sale_qty)as qty,sum(sale_amt) as amt,sum(sale_lm_qty) as lmqty,sum(sale_lm_amt) as lmamt,sum(sale_lym_qty) as lymqty,sum(sale_lym_amt) as lymamt from dmsale_tf_zyk_city_m  where ol_typ=? and dt_month=? and region_ggrp=?  group by line_name,markt_center order by markt_center,line_name";
		   params[3]=region;
		}else if(StringUtils.isNotBlank(region)&&StringUtils.isNotBlank(market)){
		   strSQL="select city_name,line_name,sum(sale_qty)as qty,sum(sale_amt) as amt,sum(sale_lm_qty) as lmqty,sum(sale_lm_amt) as lmamt,sum(sale_lym_qty) as lymqty,sum(sale_lym_amt) as lymamt from dmsale_tf_zyk_city_m  where brand_name='海信' and ol_typ=? and dt_month=? and region_ggrp=? and markt_center=? group by line_name,city_name order by city_name,line_name";
		   strSQL2="select city_name,line_name,sum(sale_qty)as qty,sum(sale_amt) as amt,sum(sale_lm_qty) as lmqty,sum(sale_lm_amt) as lmamt,sum(sale_lym_qty) as lymqty,sum(sale_lym_amt) as lymamt from dmsale_tf_zyk_city_m  where ol_typ=? and dt_month=? and region_ggrp=? and markt_center=? group by line_name,city_name order by city_name,line_name";
		   params[3]=region;
		   params[4]=market;
		}
		List<Object> list=(List<Object>) iBaseDao.getListBySQL(0, 0, strSQL,params);
		List<Object> list2=(List<Object>) iBaseDao.getListBySQL(0, 0, strSQL2,params);
		List<QPLDIYReportVO> resultList=new ArrayList<QPLDIYReportVO>();
		for(Object o:list){
		 QPLDIYReportVO vo=new QPLDIYReportVO();
		 Object[] oo=(Object[])o;
		 String areaName=(String)oo[0];
		 String productName=(String)oo[1];
		 if(StringUtils.isBlank(areaName)||StringUtils.isBlank(productName)){
			 continue;
		 }
		 vo.setAreaName(areaName);
		 vo.setProductName(productName);
		 BigDecimal qty=(BigDecimal)oo[2];
		 BigDecimal amt=(BigDecimal)oo[3];
		 BigDecimal lmqty=(BigDecimal)oo[4];
		 BigDecimal lmamt=(BigDecimal)oo[5];
		 BigDecimal lymqty=(BigDecimal)oo[6];
		 BigDecimal lymamt=(BigDecimal)oo[7];
		 if("0".equals(type)){
			 //额amt
			 BigDecimal lmamtpercent=null;
			 if(lmamt.compareTo(new BigDecimal("0"))==0){
				 lmamtpercent=new BigDecimal("100");
			 }else{
				 lmamtpercent=amt.subtract(lmamt).divide(lmamt,4,BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100)).setScale(2);
			 }
			 BigDecimal lymamtpercent=null;
			 if(lymamt.compareTo(new BigDecimal("0"))==0){
				 lymamtpercent=new BigDecimal("100");
			 }else{
				 lymamtpercent=amt.subtract(lymamt).divide(lymamt,4,BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100)).setScale(2); 
			 }
			 vo.setValue(amt.toString());
			 vo.setLmpercent(lmamtpercent.toString()+"%");
			 vo.setLympercent(lymamtpercent.toString()+"%");
		 }else{
			 //量qty
			 BigDecimal lmqtypercent=null;
			 if(lmqty.compareTo(new BigDecimal("0"))==0){
				 lmqtypercent=new BigDecimal("100");
			 }else{
				 lmqtypercent=qty.subtract(lmqty).divide(lmqty,4,BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100)).setScale(2); 
			 }
			 BigDecimal lymqtypercent=null;
			 if(lymqty.compareTo(new BigDecimal("0"))==0){
				 lymqtypercent=new BigDecimal("100"); 
			 }else{
				 lymqtypercent=qty.subtract(lymqty).divide(lymqty,4,BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100)).setScale(2);
			 }
			 vo.setValue(qty.toString());
			 vo.setLmpercent(lmqtypercent.toString()+"%");
			 vo.setLympercent(lymqtypercent.toString()+"%");
		 }
		 //占有率
		 for(Object o2:list2){
			 //根据areaname和productname匹配
			 Object[] oo2=(Object[])o2;
			 String areaName2=(String)oo2[0];
			 String productName2=(String)oo2[1];
			 if(areaName.equals(areaName2)&&productName.equals(productName2)){
				 if("0".equals(type)){
					 //amt
					 BigDecimal amt2=(BigDecimal)oo2[3];
					 BigDecimal lmamt2=(BigDecimal)oo2[5];
					 BigDecimal lymamt2=(BigDecimal)oo2[7];
					 BigDecimal occupy=amt2.compareTo(new BigDecimal("0"))==0? new BigDecimal("100"):(amt.divide(amt2,4,BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100))).setScale(2);
					 BigDecimal lmoccupy=lmamt2.compareTo(new BigDecimal("0"))==0? new BigDecimal("100"):(lmamt.divide(lmamt2,4,BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100))).setScale(2);
					 BigDecimal lymoccupy=lymamt2.compareTo(new BigDecimal("0"))==0? new BigDecimal("100"):(lymamt.divide(lymamt2,4,BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100))).setScale(2);
					 vo.setOccup(occupy.toString()+"%");
					 vo.setLmoccuppercent(lmoccupy.toString()+"%");
					 vo.setLymoccuppercent(lymoccupy.toString()+"%");
					 vo.setTotalamt(amt2.toString());
					
				 }else{
					 //qty
					 BigDecimal qty2=(BigDecimal)oo2[2];
					 BigDecimal lmqty2=(BigDecimal)oo2[4];
					 BigDecimal lymqty2=(BigDecimal)oo2[6];
					 BigDecimal occupy=qty2.compareTo(new BigDecimal("0"))==0? new BigDecimal("100"):(qty.divide(qty2,4,BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100))).setScale(2);
					 BigDecimal lmoccupy=lmqty2.compareTo(new BigDecimal("0"))==0? new BigDecimal("100"):(lmqty.divide(lmqty2,4,BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100))).setScale(2);
					 BigDecimal lymoccupy=lymqty2.compareTo(new BigDecimal("0"))==0? new BigDecimal("100"):(lymqty.divide(lymqty2,4,BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100))).setScale(2);
					 vo.setOccup(occupy.toString()+"%");
					 vo.setLmoccuppercent(lmoccupy.toString()+"%");
					 vo.setLymoccuppercent(lymoccupy.toString()+"%");
					 vo.setTotalnum(qty2.toString());
				 }
				 break;
			 }
			
		 }
		 resultList.add(vo);
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getReportResultByParam(AlltypeParam param, String brand) {
		String areaName=param.getAreaName(); //大区
		String centerName=param.getCenterName(); //营销中心
		String proName=param.getProName(); //省份
	 	String date=param.getYm(); //年月
	 	String queryLat=param.getQueryLat();//查询类型（0地理纬度1竞争纬度）
	 	String tblType=param.getTblType();//0月报1周报
	 	String olType=param.getOlType();
	 	List<String> params=new ArrayList<String>();
	 	//拼接SQL
	 	StringBuffer strSQL=new StringBuffer("select ");
	 	String groupAndOrder="";
	 	if(StringUtils.isBlank(areaName)){
	 		strSQL.append("region_ggrp,");
	 		groupAndOrder=" group by line_name,region_ggrp order by region_ggrp,line_name";
	 	}else{
	 		if("0".equals(queryLat)){
		 		//地理纬度
		 		if(StringUtils.isBlank(proName)){
		 			strSQL.append("prov,");
		 			groupAndOrder=" group by line_name,prov order by prov,line_name";
		 		}else{
		 			strSQL.append("city_name,");
		 			groupAndOrder=" group by line_name,city_name order by city_name,line_name";
		 		}
	 		}else{
	 			if(StringUtils.isBlank(centerName)){
	 				strSQL.append("markt_center,");
	 				groupAndOrder=" group by line_name,markt_center order by markt_center,line_name";
	 			}else{
	 				strSQL.append("city_name,");
	 				groupAndOrder=" group by line_name,city_name order by city_name,line_name";
	 			}
	 		}
	 		
	 	}
	 	strSQL.append("line_name,sum(sale_qty)as qty,sum(sale_amt) as amt,sum(sale_lm_qty) as lmqty,sum(sale_lm_amt) as lmamt,sum(sale_lym_qty) as lymqty,sum(sale_lym_amt) as lymamt from ");
	 	if("1".equals(tblType)){
	 		strSQL.append("dmsale_tf_zyk_city_w where 1=1 and dt_week=? ");
	 		params.add(date);
	 	}else{
	 		strSQL.append("dmsale_tf_zyk_city_m where 1=1 and dt_month=? ");
	 		params.add(date);
	 	}
	 	if("hisense".equals(brand)){
	 		strSQL.append("and brand_name='海信' ");
	 	}
	 	strSQL.append("and ol_typ=? ");//类型线上线下
	 	params.add(olType);
	 	if(StringUtils.isNotBlank(areaName)){
	 		strSQL.append("and region_ggrp=? ");
	 		params.add(areaName);
	 	}
	 	if("0".equals(queryLat)){
	 		//地理纬度
	 		if(StringUtils.isNotBlank(proName)){
	 			strSQL.append("and prov=? ");
	 			params.add(proName);
	 		}
	 		
	 	}else{
	 		//竞争纬度
	 		//markt_center
	 		if(StringUtils.isNotBlank(centerName)){
	 			strSQL.append("and markt_center=? ");
	 			params.add(centerName);
	 		}
	 	}
	 	strSQL.append(groupAndOrder);
	 	System.out.println("strSql="+strSQL);
	 	List<Object> list=(List<Object>) iBaseDao.getListBySQL(0, 0, strSQL.toString(),(String[])params.toArray(new String[params.size()]));
		return list;
	}
}
