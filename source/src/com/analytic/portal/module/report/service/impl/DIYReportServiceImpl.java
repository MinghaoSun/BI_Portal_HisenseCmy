package com.analytic.portal.module.report.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analytic.portal.common.util.BigdecimalUtil;
import com.analytic.portal.module.report.dao.interfaces.DmsaleTfZykCityMDao;
import com.analytic.portal.module.report.service.interfaces.DIYReportService;
import com.analytic.portal.module.report.vo.AllTypeVO;
import com.analytic.portal.module.report.vo.AlltypeDIYReportVO;
import com.analytic.portal.module.report.vo.AlltypeParam;
import com.analytic.portal.module.report.vo.AlltypeValueVO;
import com.analytic.portal.module.report.vo.D3VO;
import com.analytic.portal.module.report.vo.QPLDIYReportVO;

/**
 * @description 自主设计报表Service接口实现类
 * @author Minghao
 * @date 2017年4月10日13:47:46
 */
@Service("dIYReportService")
public class DIYReportServiceImpl implements DIYReportService {

	@Autowired
	private DmsaleTfZykCityMDao dmsaleTfZykCityMDao;

	@Override
	public List<AllTypeVO> getReportResultByParam() {
		// 2016年1月份所有大区的线下销售额情况
		List<QPLDIYReportVO> list = dmsaleTfZykCityMDao.getReportResultByParam(null, null, "201601", "0", "0");
		List<AllTypeVO> volist = new ArrayList<>();
		for (QPLDIYReportVO vo : list) {
			AllTypeVO allTypeVO = new AllTypeVO();
			allTypeVO.setAreaId(vo.getAreaName());
			allTypeVO.setAreaName(vo.getAreaName());
			allTypeVO.setHisense_sale_num(vo.getValue());
			allTypeVO.setLmoccuppercent(vo.getLmoccuppercent());
			allTypeVO.setLmpercent(vo.getLmpercent());
			allTypeVO.setLymoccuppercent(vo.getLymoccuppercent());
			allTypeVO.setLympercent(vo.getLympercent());
			allTypeVO.setOccup(vo.getOccup());
			allTypeVO.setProductId(vo.getProductName());
			allTypeVO.setProductName(vo.getProductName());
			allTypeVO.setSale_amt(vo.getTotalamt());
			// allTypeVO.setSale_num(vo.getTotalnum());
			allTypeVO.setSale_num(vo.getTotalamt());
			volist.add(allTypeVO);
		}
		return volist;
		/*
		 * Map<String, D3VO> maps=new HashMap<String, D3VO>();
		 * for(QPLDIYReportVO vo:list){ String areaName=vo.getAreaName(); String
		 * productName=vo.getProductName(); String value=vo.getValue();
		 * if(!maps.containsKey(areaName)){ maps.put(areaName, new
		 * D3VO(areaName, new ArrayList<D3VO>(), null)); }
		 * maps.get(areaName).getChildren().add(new D3VO(productName, null,
		 * value)); } //组建root节点 Iterator<String> it=maps.keySet().iterator();
		 * D3VO root=new D3VO("root", new ArrayList<D3VO>(),null);
		 * while(it.hasNext()){ String key=it.next(); D3VO d3vo=maps.get(key);
		 * root.getChildren().add(d3vo); } return root;
		 */
	}

	@Override
	public AlltypeDIYReportVO getAllTypeResult(AlltypeParam param) {
		List<Object> list_hisense = dmsaleTfZykCityMDao.getReportResultByParam(param, "hisense");
		List<Object> list_all = dmsaleTfZykCityMDao.getReportResultByParam(param, "all");
		AlltypeDIYReportVO vo = new AlltypeDIYReportVO();
		// hisense
		String saleType = param.getSaleType();
		List<AlltypeValueVO> alltypeValueVOList = new ArrayList<AlltypeValueVO>();
		BigDecimal histoal = new BigDecimal("0");
		BigDecimal histoallm = new BigDecimal("0");
		BigDecimal histoallym = new BigDecimal("0");
		BigDecimal hisamttotal = new BigDecimal("0");
		BigDecimal hisqtytotal = new BigDecimal("0");
		for (Object o : list_hisense) {
			AlltypeValueVO alltypeValueVO = new AlltypeValueVO();
			Object[] oo = (Object[]) o;
			String areaName = (String) oo[0];
			String productName = (String) oo[1];
			if (StringUtils.isBlank(areaName) || StringUtils.isBlank(productName)) {
				continue;
			}
			alltypeValueVO.setAreaName(areaName);
			alltypeValueVO.setProductName(productName);
			BigDecimal qty = (BigDecimal) oo[2];
			BigDecimal amt = (BigDecimal) oo[3];
			BigDecimal lmqty = (BigDecimal) oo[4];
			BigDecimal lmamt = (BigDecimal) oo[5];
			BigDecimal lymqty = (BigDecimal) oo[6];
			BigDecimal lymamt = (BigDecimal) oo[7];
			hisamttotal=hisamttotal.add(amt);
			hisqtytotal=hisqtytotal.add(qty);
			if ("0".equals(saleType)) {
				histoal=histoal.add(amt);
				histoallm=histoallm.add(lmamt);
				histoallym=histoallym.add(lymamt);
				// 额amt
				BigDecimal lmamtpercent = BigdecimalUtil.getIncreasePercent(amt, lmamt, 2);
				BigDecimal lymamtpercent = BigdecimalUtil.getIncreasePercent(amt, lymamt, 2);
				alltypeValueVO.setValue(amt.toString());
				alltypeValueVO.setLmpercent(lmamtpercent.toString() + "%");
				alltypeValueVO.setLympercent(lymamtpercent.toString() + "%");
			} else {
				// 量qty
				histoal=histoal.add(qty);
				histoallm=histoallm.add(lmqty);
				histoallym=histoallym.add(lymqty);
				BigDecimal lmqtypercent = BigdecimalUtil.getIncreasePercent(qty, lmqty, 2);
				BigDecimal lymqtypercent = BigdecimalUtil.getIncreasePercent(qty, lymqty, 2);
				alltypeValueVO.setValue(qty.toString());
				alltypeValueVO.setLmpercent(lmqtypercent.toString() + "%");
				alltypeValueVO.setLympercent(lymqtypercent.toString() + "%");
			}
			// 占有率
			for (Object o2 : list_all) {
				// 根据areaname和productname匹配
				Object[] oo2 = (Object[]) o2;
				String areaName2 = (String) oo2[0];
				String productName2 = (String) oo2[1];
				if (areaName.equals(areaName2) && productName.equals(productName2)) {
					if ("0".equals(saleType)) {
						// amt
						BigDecimal amt2 = (BigDecimal) oo2[3];
						BigDecimal lmamt2 = (BigDecimal) oo2[5];
						BigDecimal lymamt2 = (BigDecimal) oo2[7];
						BigDecimal occupy = BigdecimalUtil.getPercent(amt, amt2, 2);
						BigDecimal lmoccupy = BigdecimalUtil.getPercent(lmamt, lmamt2, 2);
						BigDecimal lymoccupy = BigdecimalUtil.getPercent(lymamt, lymamt2, 2);
						BigDecimal lmoccuppercent=BigdecimalUtil.getIncreasePercent(occupy, lmoccupy, 2);
						BigDecimal lymoccuppercent=BigdecimalUtil.getIncreasePercent(occupy, lymoccupy, 2);
						alltypeValueVO.setOccupy(occupy.toString() + "%");
						alltypeValueVO.setLmoccuppercent(lmoccuppercent.toString() + "%");
						alltypeValueVO.setLymoccuppercent(lymoccuppercent.toString() + "%");

					} else {
						// qty
						BigDecimal qty2 = (BigDecimal) oo2[2];
						BigDecimal lmqty2 = (BigDecimal) oo2[4];
						BigDecimal lymqty2 = (BigDecimal) oo2[6];
						BigDecimal occupy = BigdecimalUtil.getPercent(qty, qty2, 2);
						BigDecimal lmoccupy = BigdecimalUtil.getPercent(lmqty, lmqty2, 2);
						BigDecimal lymoccupy = BigdecimalUtil.getPercent(lymqty, lymqty2, 2);
						BigDecimal lmoccuppercent=BigdecimalUtil.getIncreasePercent(occupy, lmoccupy, 2);
						BigDecimal lymoccuppercent=BigdecimalUtil.getIncreasePercent(occupy, lymoccupy, 2);
						alltypeValueVO.setOccupy(occupy.toString() + "%");
						alltypeValueVO.setLmoccuppercent(lmoccuppercent.toString() + "%");
						alltypeValueVO.setLymoccuppercent(lymoccuppercent.toString() + "%");
					}
					break;
				}

			}
			alltypeValueVOList.add(alltypeValueVO);
		}
		vo.setValues(alltypeValueVOList);
		// 合计行业总体量额
		BigDecimal alltoal = new BigDecimal("0");
		BigDecimal alltoallm = new BigDecimal("0");
		BigDecimal alltoallym = new BigDecimal("0");
		BigDecimal allamttotal = new BigDecimal("0");
		BigDecimal allqtytotal = new BigDecimal("0");
		for (Object o : list_all) {
			Object[] oo = (Object[]) o;
			BigDecimal qty = (BigDecimal) oo[2];
			BigDecimal amt = (BigDecimal) oo[3];
			BigDecimal lmqty = (BigDecimal) oo[4];
			BigDecimal lmamt = (BigDecimal) oo[5];
			BigDecimal lymqty = (BigDecimal) oo[6];
			BigDecimal lymamt = (BigDecimal) oo[7];
			allamttotal=allamttotal.add(amt);
			allqtytotal=allqtytotal.add(qty);
			if ("0".equals(saleType)) {
				// amt
				alltoal=alltoal.add(amt);
				alltoallm=alltoallm.add(lmamt);
				alltoallym=alltoallym.add(lymamt);
			} else {
				// qty
				alltoal=alltoal.add(qty);
				alltoallm=alltoallm.add(lmqty);
				alltoallym=alltoallym.add(lymqty);
			}
		}
		vo.setHistoal(histoal.toString());
		vo.setHytotal(alltoal.toString());
		BigDecimal hishbpercent = BigdecimalUtil.getIncreasePercent(histoal, histoallm, 2);
		BigDecimal histbpercent = BigdecimalUtil.getIncreasePercent(histoal, histoallym, 2);
		BigDecimal hyhbpercent = BigdecimalUtil.getIncreasePercent(alltoal, alltoallm, 2);
		BigDecimal hytbpercent = BigdecimalUtil.getIncreasePercent(alltoal, alltoallym, 2);
		vo.setHistotalhb(hishbpercent.toString()+"%");
		vo.setHistotaltb(histbpercent.toString()+"%");
		vo.setHytotalhb(hyhbpercent.toString()+"%");
		vo.setHytotaltb(hytbpercent.toString()+"%");
		BigDecimal hisoccupyPercent = BigdecimalUtil.getPercent(histoal, alltoal, 2);
		vo.setHistotalzhyl(hisoccupyPercent.toString()+"%");
		BigDecimal hisamtoccupy = BigdecimalUtil.getPercent(hisamttotal, allamttotal, 2);
		BigDecimal hisqtyoccupy = BigdecimalUtil.getPercent(hisqtytotal, allqtytotal, 2);
		BigDecimal hisppzs = BigdecimalUtil.getPercent(hisamtoccupy, hisqtyoccupy, 2);
		vo.setHistotalppzhsh(hisppzs.toString());
		return vo;
	}

}
