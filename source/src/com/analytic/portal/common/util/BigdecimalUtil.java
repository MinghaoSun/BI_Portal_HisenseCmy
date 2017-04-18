package com.analytic.portal.common.util;

import java.math.BigDecimal;
/**
 * @description Bigdecimal工具类
 * @author Minghao
 * @date 2017年4月17日15:21:01
 */
public class BigdecimalUtil {

	/**
	 * @description 获取增长百分比 （四舍五入）
	 * @param now 当期 
	 * @param last 往期 
	 * @param scale 保留多少位小数
	 * @return
	 */
	public static BigDecimal getIncreasePercent(BigDecimal now, BigDecimal last, int scale) {
		BigDecimal result = new BigDecimal("0");
		try {
			result = now.subtract(last).divide(last, scale + 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(scale);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * @description 获取百分比（四舍五入）
	 * @param fenzi 分子
	 * @param fenmu 分母
	 * @param scale 保留多少位小数
	 * @return
	 */
	public static BigDecimal getPercent(BigDecimal fenzi,BigDecimal fenmu,int scale){
		BigDecimal result = new BigDecimal("0");
		try {
			result=fenzi.divide(fenmu,scale+2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(scale);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return result;
	}

}
