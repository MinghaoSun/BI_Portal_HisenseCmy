/**
 * 
 */
package com.analytic.portal.common.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期相关处理
 * @author Boger
 *
 */
public class DateUtil {
	  
	private static Calendar calendar=Calendar.getInstance(Locale.CHINA);
	public static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 计算开始时间和结算时间差
	 * @param beginDate
	 * @param endDate
	 * @return
	 * Boger
	 * 2015年8月7日下午5:47:06
	 */
    public static long getMillisDiff(Date beginDate,Date endDate)
    {    
    	long millis=0;
    	try {
            long beginTime = beginDate.getTime();                 
            long endTime = endDate.getTime();         
            millis=endTime-beginTime;
		} catch (Exception e) {
			e.printStackTrace();
		}

    	return millis;
    }    
    
	/**
	 * 计算开始时间和结束时间的分钟差
	 * @param beginDate
	 * @param endDate
	 * @return
	 * Boger
	 * 2015年8月19日下午2:46:06
	 */
    public static long getMinuteDiff(Date beginDate,Date endDate){
    	long minute=0;

    	long millis=DateUtil.getMillisDiff(beginDate, endDate);
    	minute=Long.parseLong(String.valueOf(millis/(1000*60)));
    	
    	return minute;
    }
    
	/**
	 * 计算开始时间和结算时间的小时差
	 * @param beginDate
	 * @param endDate
	 * @return
	 * Boger
	 * 2015年8月7日下午2:12:34
	 */
    public static int getHoursDiff(Date beginDate,Date endDate){
    	int hours=0;

    	long minute=DateUtil.getMinuteDiff(beginDate, endDate);
    	if (minute%60>0){
    		hours=(int)(minute/60)+1;
    	}else {
    		hours=(int)(minute/60);
		}
    	
    	return hours;
    }
   
	/**
	 * 计算开始时间和结算时间的天数差
	 * @param beginDate
	 * @param endDate
	 * @return
	 * Boger
	 * 2015年8月7日下午2:12:34
	 */
    public static int getDaysDiff(Date beginDate,Date endDate){
    	int days=0;
   
    	days=getHoursDiff(beginDate, endDate)/24;
    	
    	return days;
    }
    
	/**
	 * 获取当前日期的前后某天的日期
	 * @param date
	 * @param days
	 * @return
	 * Boger
	 * 2015年8月5日下午2:46:26
	 */
	public static Date getDay(Date date,int days){
		
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH,days);
		
		return calendar.getTime();
	}
	
	//获取当前时间前一天
	public static  String getDate(){
		Date dNow = new Date();   //当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
		dBefore = calendar.getTime(); 
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
		String beforeDate = sdf.format(dBefore);    //格式化前一天
		return beforeDate;
		
	}
	
	//获取年月
	public static String getYearMonth(){
		Date dNow = new Date();   //当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
		dBefore = calendar.getTime(); 
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM"); //设置时间格式
		String beforeDate = sdf.format(dBefore);    //格式化前一天
		return beforeDate;
		
	}
	
	public static String getNowTime(){
	      Date date = new Date();//获得系统时间.
          String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);//将时间格式转换成符合Timestamp要求的格式.
          return nowTime;
	}
	
	public static Timestamp getTimestamp(){
		return new Timestamp(System.currentTimeMillis());
	}	
}
