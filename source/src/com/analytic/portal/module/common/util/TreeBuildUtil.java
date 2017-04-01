/**
 * @author Boger
 */
package com.analytic.portal.module.common.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.analytic.portal.common.util.StringUtil;
import com.analytic.portal.module.common.dao.IBaseDao;

/**
 * @author Boger
 */
public class TreeBuildUtil {
	@Resource
	private static IBaseDao iBaseDao;
	
	/**
	 * 获取树节点列表
	 * @param <T>
	 * @param treeObj
	 * @param parentCode
	 * @return
	 * Boger
	 * 2016年1月28日下午2:06:10
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<Object> getTreeNodeList(Class<T> classTree,String parentCode){
		String entityName=classTree.getName();
		
		//重置根节点
		if (StringUtil.isEmpty(parentCode)){
			String queryHql="SELECT Code FROM "+entityName+"WHERE parentCode=''";
			List<Object> parentList=iBaseDao.getListByHql(queryHql);
			for (Object object : parentList) {
				String parentValue=String.valueOf(object);
				parentCode=parentCode.isEmpty()?parentValue:","+parentValue;
			}
		}

		return getSonNodeList(entityName, parentCode);
	}
	
	/**
	 * 遍历节点列表
	 * 递归计算（从根节点到子节点计算）
	 * @param treeObj
	 * @param parentCode
	 * @return
	 * Boger
	 * 2016年1月27日下午6:25:17
	 */
	public static List<Object> getSonNodeList(String entityName,String parentCode){
		List<Object> list=new ArrayList<Object>();
		List<Object> tempList=new ArrayList<Object>();
		
		tempList=getLeafNodeList(entityName, parentCode);
		if (tempList.size()==0){
			return list;
		}else {
			//获取新的子节点
			parentCode="";
			for (Object object : list) {
				JSONObject jsonObject=JSONObject.fromObject(object);
				if (parentCode.equals("")){
					parentCode+=jsonObject.getString("Code");
				}else {
					parentCode+=","+jsonObject.getString("Code");
				}
			}
			
			list.add(tempList);
			return getSonNodeList(entityName, parentCode);
		}
	}
	
	/**
	 * 获取子节点列表
	 * @param treeObj
	 * @param parentCode
	 * @return
	 * Boger
	 * 2016年1月27日下午6:25:04
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> getLeafNodeList(String entityName,String parentCode) {
		List<Object> list=new ArrayList<Object>();
		StringBuilder hqlBuilder=new StringBuilder();
		
		parentCode=parentCode.replaceAll(",", "','");
		hqlBuilder.append("SELECT son.ID,son.Name,son.Code,son.ParentCode,father.Name AS ParentName ");
		hqlBuilder.append("FROM "+entityName+" son INNER JOIN "+entityName+" father ON son.ParentCode=father.Code ");
		hqlBuilder.append("WHERE ParentCode IN ('"+parentCode+"')");
		list=iBaseDao.getListByHql(hqlBuilder.toString());
		
		return list;
	}
	
}
