package com.analytic.portal.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class BeanConverUtil {
	private static SimpleDateFormat dateFormatLong=new SimpleDateFormat("yyyy-MM-dd HH:ss:mm",Locale.CHINA);
	
	/**
	 * 实体转换为集合
	 * @param bean
	 * @return
	 * @throws Exception
	 * Boger
	 * 2015年8月20日下午12:51:21
	 */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map convertBeanToMap(Object bean) throws Exception {
        Class curClass=bean.getClass();
        Map map = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(curClass);
        
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
        	PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
            String propertyName = propertyDescriptor.getName();
            if (!propertyName.equals("class")&&!propertyName.equals("type")&&!propertyName.equals("name")) {
            	Method method = propertyDescriptor.getReadMethod();
                Object result = method.invoke(bean, new Object[0]);
                if (result != null) {
                	if (propertyDescriptor.getPropertyType().getName().equals("java.util.Date")){
                		map.put(propertyName, dateFormatLong.format(dateFormatLong.parse(result.toString())));
                	}else{
                		map.put(propertyName, result);
                	}
                } else {
                   map.put(propertyName, "");
                }
           }
        } 
        
        return map;
    }
    
    /**
     * 集合转换为实体
     * @param type
     * @param map
     * @return
     * @throws Exception
     * Boger
     * 2015年8月20日下午12:51:07
     */
    @SuppressWarnings("rawtypes")
	public static Object convertMapToBean(Map map,Class entity) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(entity);
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        Object object = entity.newInstance();
  
        for (int i=0;i<propertyDescriptors.length;i++){
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();  
            if (map.containsKey(propertyName)){
            	Object value = map.get(propertyName);
            	Object[] args = new Object[1];  
                args[0] = value;  
                descriptor.getWriteMethod().invoke(object, args);  
           }  
        }
        
        return object;  
    }
    
    /**
	 * Map转Object
	 * 
	 * @param type
	 * @param map
	 * @return
	 */
	public static <T> T getObj(Map<String, String[]> map, Class<T> clazz) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz); // 获取类属性
			T obj = clazz.newInstance(); // 创建 JavaBean 对象

			// 给 JavaBean 对象的属性赋值
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				String propertyType = descriptor.getPropertyType().toString();
				// 过滤默认的class属性
				if (propertyType.equals("class")) {
					continue;
				}
				String[] values = map.get(propertyName);
				if (values == null || values[0] == null) {
					continue;
				}
				if (map.containsKey(propertyName)) {
					Object[] args = new Object[1];
					if (propertyType.equals("int") || propertyType.equals("class java.lang.Integer")) {
						args[0] = Util.strToInt(values[0]);
					} else if (propertyType.equals("long") || propertyType.equals("class java.lang.Long")) {
						args[0] = Util.strToLong(values[0]);
					} else if (propertyType.equals("double")  || propertyType.equals("class java.lang.Double")) {
						args[0] = Util.strToDouble(values[0]);
					} else if (propertyType.equals("short")  || propertyType.equals("class java.lang.Short")) {
						args[0] = Util.strToShort(values[0]);
					} else if (propertyType.equals("float")  || propertyType.equals("class java.lang.Float")) {
						args[0] = Util.strToFloat(values[0]);
					} else if (propertyType.equals("boolean")  || propertyType.equals("class java.lang.Boolean")) {
						args[0] = Util.strToBoolean(values[0]);
					}  else if (propertyType.equals("interface java.util.List")) {
						args[0] = Util.arrToList(values);
					} else if (propertyType.equals("interface java.util.Set")) {
						args[0] = Util.arrToSet(values);
					}else if(propertyType.equals("class java.util.Date")){
						args[0] = Util.strToDate(values[0]);
					}else {
						args[0] = values[0];
					}
					descriptor.getWriteMethod().invoke(obj, args);
				}
			}
			return obj;
		} catch (Exception e) {
			throw new IllegalArgumentException("转换Map为Object出错", e);
		}
	}
	
	public static <T> T getObj(HttpServletRequest request,Class<T> clazz){
		return getObj(request.getParameterMap(), clazz);
	}
	
	public static class Util {

		public static float strToFloat(String str) {
			try {
				return Float.parseFloat(str);
			} catch (Exception e) {
				return 0f;
			}
		}

		/*
		 * date.toString(); // EEE MMM dd HH:mm:ss zzz yyyy
		 */
		private static SimpleDateFormat SDF = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.ENGLISH);
		
		public static Object strToDate(String str) {
			try {
				return SDF.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		}

		public static double strToDouble(String str) {
			try {
				return Double.parseDouble(str);
			} catch (Exception e) {
				return 0.0;
			}
		}

		public static int strToInt(String str) {
			try {
				return Integer.parseInt(str);
			} catch (Exception e) {
				return 0;
			}
		}

		public static long strToLong(String str) {
			try {
				return Long.parseLong(str);
			} catch (Exception e) {
				return 0L;
			}
		}

		public static short strToShort(String str) {
			try {
				return Short.parseShort(str);
			} catch (Exception e) {
				return 0;
			}
		}

		public static boolean strToBoolean(String str) {
			try {
				return Boolean.parseBoolean(str);
			} catch (Exception e) {
				return false;
			}
		}

		public static Set<String> arrToSet(String[] objs) {
			if (objs == null) {
				return null;
			}
			Set<String> set = new HashSet<String>();
			for (String obj : objs) {
				set.add(obj);
			}
			return set;
		}

		public static List<String> arrToList(String[] objs) {
			if (objs == null) {
				return null;
			}
			List<String> list = new ArrayList<String>();
			for (String obj : objs) {
				list.add(obj);
			}
			return list;
		}
	}

}
