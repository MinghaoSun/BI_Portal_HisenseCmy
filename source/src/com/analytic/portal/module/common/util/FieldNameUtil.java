package com.analytic.portal.module.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FieldNameUtil {

	/**
	 *  获取类的属性名的集合
	 * @param clazz
	 * @return 
	 * @author fenglei.xu
	 */
	public static List<String> getFieldNames(Class<?> clazz){
		List<String> list = new ArrayList<String>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			list.add(field.getName());
		}
		return list;
	}
}
