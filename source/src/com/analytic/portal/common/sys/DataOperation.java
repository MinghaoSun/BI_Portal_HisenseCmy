package com.analytic.portal.common.sys;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.*;
import java.util.Date;

import com.yzd.plat.common.util.SystemException;
import com.yzd.plat.common.util.Tools;

/**
 * ���sql�Ľ���ݿ��ѯ������ݷ��뵽ArrayList��HashMap��
 * 
 * @see SQLObject
 */
public class DataOperation {
	/**
	 * ��ö�����¼
	 * 
	 * @param conn
	 *            ��ݿ�����
	 * @param sqlObj
	 *            select
	 * @return ��¼��������ΪArrayList,Ԫ��ΪHashMap,HashMap��keyΪ�ֶ����Сд
	 * @throws SQLException
	 * @see SQLObject
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList getDatas(Connection conn, SQLObject sqlObj) throws SQLException {
		ArrayList rows = new ArrayList();
		if (sqlObj == null)
			return rows;
		PreparedStatement pst = conn.prepareStatement(sqlObj.getSql());
		Vector params = sqlObj.getParams();
		for (int i = 0; i < params.size(); i++) {
			if (params.get(i) == null)
				throw new SystemException("��ѯsql����е�" + (i + 1) + "����Ϊnull��");
			pst.setObject(i + 1, params.get(i));
		}
		ResultSet rs = pst.executeQuery();
		ResultSetMetaData rsm = rs.getMetaData();
		while (rs.next()) {
			rows.add(getRow(rs, rsm));
		}
		rs.close();
		pst.close();
		return rows;
	}

	/**
	 * ResultSet ת���� ArrayList (ResultSet ������ر�) ������Ϣר��
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList getDatas(ResultSet rs) throws SQLException {
		ArrayList rows = new ArrayList();
		ResultSetMetaData rsm = rs.getMetaData();
		while (rs.next()) {
			rows.add(getRow(rs, rsm));
		}
		rs.close();
		return rows;
	}

	/**
	 * ���һ����¼
	 * 
	 * @param conn
	 *            ��ݿ�����
	 * @param sqlObj
	 *            select
	 * @return һ����¼��HashMap��keyΪ�ֶ����Сд
	 * @throws SQLException
	 * @see SQLObject
	 */

	@SuppressWarnings("rawtypes")
	public static HashMap getData(Connection conn, SQLObject sqlObj) throws SQLException {
		if (sqlObj == null)
			return null;
		PreparedStatement pst = conn.prepareStatement(sqlObj.getSql());
		Vector params = sqlObj.getParams();
		for (int i = 0; i < params.size(); i++) {
			if (params.get(i) == null)
				throw new SystemException("��ѯsql����е�" + (i + 1) + "����Ϊnull��");
			pst.setObject(i + 1, params.get(i));
		}
		ResultSet rs = pst.executeQuery();
		ResultSetMetaData rsm = rs.getMetaData();
		HashMap row = null;
		if (rs.next()) {
			row = getRow(rs, rsm);
		}
		rs.close();
		pst.close();
		return row;
	}

	/**
	 * ���һ����¼
	 * 
	 * @param sqlObj
	 *            select
	 * @return һ����¼��HashMap��keyΪ�ֶ����Сд
	 * @throws SQLException
	 * @see SQLObject
	 */
	@SuppressWarnings({ "rawtypes", "static-access" })
	public static HashMap getData(SQLObject sqlObj) {
		HashMap map = null;
		Connection conn = null;
		try {
			conn = ConnectionPool.getIstance().getConnection();
			map = getData(conn, sqlObj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException("��ѯ��ݳ���-" + e.getMessage()+ "\n" + sqlObj.getSql());
		} finally {
			ConnectionPool.getIstance().closeConn(conn);
		}
		return map;
	}

	/**
	 * ���һ����¼
	 * 
	 * @param sqlObj
	 *            select
	 * @return һ����¼��HashMap��keyΪ�ֶ����Сд
	 * @throws SQLException
	 * @see SQLObject
	 */
	@SuppressWarnings({ "static-access", "rawtypes" })
	public static HashMap getData(String strSql) {
		HashMap map = null;
		Connection conn = null;
		try {
			SQLObject sqlObj = new SQLObject(strSql);
			conn = ConnectionPool.getIstance().getConnection();
			map = getData(conn, sqlObj);
		} catch (Exception e) {
			throw new SystemException("��ѯ��ݳ���-" + e.getMessage()+ "\n" + strSql);
		} finally {
			ConnectionPool.getIstance().closeConn(conn);
		}
		return map;
	}

	/**
	 * ��ö�����¼
	 * 
	 * @param sqlObj
	 *            select
	 * @return ��¼��������ΪArrayList,Ԫ��ΪHashMap,HashMap��keyΪ�ֶ����Сд
	 * @throws SQLException
	 * @see SQLObject
	 */
	@SuppressWarnings({ "rawtypes", "static-access" })
	public static ArrayList getDatas(SQLObject sqlObj) {
		Connection conn = null;
		ArrayList al = null;
		try {
			conn = ConnectionPool.getIstance().getConnection();
			al = getDatas(conn, sqlObj);
		} catch (Exception e) {
			throw new SystemException("��ѯ��ݳ���-" + e.getMessage() + "\n" + sqlObj.getSql());
		} finally {
			ConnectionPool.getIstance().closeConn(conn);
		}
		return al;
	}

	@SuppressWarnings({ "rawtypes", "static-access" })
	public static ArrayList getDatas(String strSql) {
		Connection conn = null;
		ArrayList al = null;
		try {
			conn = ConnectionPool.getIstance().getConnection();
			SQLObject sqlObj = new SQLObject(strSql);
			al = getDatas(conn, sqlObj);
		} catch (Exception e) {
			throw new SystemException("��ѯ��ݳ���-" + e.getMessage() + "\n" + strSql);
		} finally {
			ConnectionPool.getIstance().closeConn(conn);
		}
		return al;
	}
	@SuppressWarnings({ "rawtypes", "static-access" })
	public static ArrayList getDatas(String strSql,Vector<Object> params) {
		Connection conn = null;
		ArrayList al = null;
		try {
			conn = ConnectionPool.getIstance().getConnection();
			SQLObject sqlObj = new SQLObject(strSql);
			if(params!=null&&params.size()!=0){
				sqlObj.setParams(params);
			}
			al = getDatas(conn, sqlObj);
		} catch (Exception e) {
			throw new SystemException("��ѯ��ݳ���-" + e.getMessage() + "\n" + strSql);
		} finally {
			ConnectionPool.getIstance().closeConn(conn);
		}
		return al;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static HashMap getRow(ResultSet rs, ResultSetMetaData rsm) throws SQLException {
		HashMap map = new HashMap();
		int count = rsm.getColumnCount();

		for (int i = 1; i <= count; i++) {
			Object value;
			int colType = rsm.getColumnType(i);
//			String columnName=rsm.getColumnName(i).toLowerCase();
			String columnName=rsm.getColumnName(i);
			switch (colType) {
			case Types.DATE:
				value = rs.getDate(i);
				if (value != null)
					value = Tools.formatDate((Date) value);
				break;
			case Types.TIMESTAMP:
				value = rs.getDate(i);
				
				if(value != null&&!rs.getTime(i).toString().substring(0,5).equals("00:00")){
					value = rs.getDate(i);
					if (value != null)
						value = Tools.formatDate((Date) value)+" "+rs.getTime(i).toString().substring(0,5);
				}else{
					if (value != null)
						value = Tools.formatDate((Date) value);
					
				}	
				break;

			case Types.FLOAT:
			case Types.DOUBLE:
				value = rs.getString(i);
				if (value != null) {
					int index = value.toString().indexOf(".");
					if (index == 0) {
						value = "0".concat(value.toString());
					}
					NumberFormat numberFormat = NumberFormat.getNumberInstance();
			        numberFormat.setMinimumFractionDigits(2);
			        numberFormat.setGroupingUsed(false);
			        value=numberFormat.format(Double.parseDouble(value.toString()));

				}
				break;
			case Types.NUMERIC:
				value = rs.getString(i);
				if (value != null) {
					int index = value.toString().indexOf(".");
					if (index == 0) {
						value = "0".concat(value.toString());
					}
				}
				break;
			default:
				try {
					value = rs.getString(i);
				} catch (Exception e) {
					value = null;
				}

			}
			map.put(columnName, value == null ? "" : value.toString());
		}
		return map;
	}

	/**
	 * ���»������ݡ���֧��������
	 * 
	 * @param sqlObj
	 *            update��insert
	 * @return ���»����ļ�¼����
	 * @see SQLObject
	 */
	public static int updateData(SQLObject sqlObj) {
		Connection conn = null;
		try {
			conn = ConnectionPool.getIstance().getConnection();
			int count = updateData(sqlObj, conn);
			conn.commit();
			return count;
		} catch (Exception e) {
			ConnectionPool.connRollBack(conn);
			throw new SystemException("updating data happens errors-" + e.getMessage());
		} finally {
			ConnectionPool.closeConn(conn);
		}
	}

	/**
	 * ���»������ݡ�֧��������
	 * 
	 * @param sqlObj
	 *            update��insert
	 * @param conn
	 *            ��ݿ�����
	 * @return ���»����ļ�¼����
	 * @see SQLObject
	 */
	@SuppressWarnings("rawtypes")
	public static int updateData(SQLObject sqlObj, Connection conn) throws SQLException {
		PreparedStatement pst = conn.prepareStatement(sqlObj.getSql());
		Vector params = sqlObj.getParams();
		for (int i = 0; i < params.size(); i++) {
			if (params.get(i) == null) {
				throw new SystemException("����sql����е�" + (i + 1) + "����Ϊnull��\n\nsql=" + getTotalSql(sqlObj) + "\n\n");
			}
			pst.setObject(i + 1, params.get(i));
		}
		int count = pst.executeUpdate();
		pst.close();
		return count;
	}

	/**
	 * ���»������ݡ�֧��������
	 * 
	 * @param sqlObj
	 *            update��insert
	 * @param conn
	 *            ��ݿ�����
	 * @return ���»����ļ�¼����
	 * @see SQLObject
	 */
	public static void execute(String strSql) throws SQLException {
		execute(strSql,null);
	}
	
	public static void execute(String strSql,Object[] args) throws SQLException {

		Connection conn = null;
		try {
			conn = ConnectionPool.getIstance().getConnection();

			PreparedStatement pst = conn.prepareStatement(strSql);
			
			if(args != null){
				int i = 1;
				for(Object arg : args){
					pst.setObject(i++, arg);
				}
			}
			pst.execute();
			conn.commit();
			pst.close();
		} catch (Exception e) {
			ConnectionPool.connRollBack(conn);
			e.printStackTrace();
			throw new SystemException("updating data happens errors-" + e.getMessage());
		} finally {
			ConnectionPool.closeConn(conn);
		}
	}

	@SuppressWarnings("rawtypes")
	private static String getTotalSql(SQLObject sqlObj) {
		if (sqlObj.getSql() == null)
			return null;
		String sqlWithNoDate = sqlObj.getSql();
		Vector v = sqlObj.getParams();

		for (int i = 0; i < v.size(); i++) {
			sqlWithNoDate = sqlWithNoDate.replaceFirst("\\?", v.get(i) == null ? "null" : v.get(i).toString());
		}

		return sqlWithNoDate;
	}

	/**
	 * ��ȡ���к�
	 * 
	 * @param name
	 *            Sequense��
	 * @return ���к�
	 */

	public static synchronized long getSequense() {
		/*Date dTemp = new Date();
		SimpleDateFormat tw = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.ENGLISH);
		String strTemp = tw.format(dTemp);
		return strTemp;*/
		try {
			Thread.sleep(50);//�����̼߳���ĸ���ͬ���ȴ�50����,�Ա�����ɵ����к��ظ�
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Date dTemp = new Date();
		SimpleDateFormat tw = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.ENGLISH);
		String strTemp = tw.format(dTemp);
		Long seq = Long.parseLong(strTemp);
		return seq;
	}
	
	public static synchronized String getSequenseStr() {
		try {
			Thread.sleep(50);//�����̼߳���ĸ���ͬ���ȴ�50����,�Ա�����ɵ����к��ظ�
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Date dTemp = new Date();
		SimpleDateFormat tw = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.ENGLISH);
		String strTemp = tw.format(dTemp);
		return strTemp;
	}

	/**
	 * ��ȡ���к�
	 * 
	 * @param name
	 *            Sequense��
	 * @return ���к�
	 */
	public static String getSequense(String name) {
		String sql = "select " + name + ".nextval seq from dual";
		SQLObject sqlObj = new SQLObject(sql);
		return (String) getData(sqlObj).get("seq");
	}

	/**
	 * ��ȡ���к�
	 * 
	 * @param name
	 *            Sequense��
	 * @return ���к�
	 */
	public static String getSequense(Connection conn, String name) throws SQLException {
		String sql = "select " + name + ".nextval seq from dual";
		SQLObject sqlObj = new SQLObject(sql);
		return (String) getData(conn, sqlObj).get("seq");
	}
	
	/*
	 * 
	 * ��ҳ����˵����
	 * tableName ����ݱ����
	 * fieldKey �� ���ڶ�λ��¼������(Ψһ��)�ֶ�,�����Ƕ��ŷָ��Ķ���ֶ�
	 * pageCurrent ����ǰҳ
	 * pageSize ��ÿҳ��ʾ����
	 * fieldShow ���Զ��ŷָ���Ҫ��ʾ���ֶ��б�,���ָ��,����ʾ�����ֶ�
	 * fieldOrder ���Զ��ŷָ��������ֶ��б�,����ָ�����ֶκ���ָ��DESC/ASC������ָ������˳��
	 * condition �� ��ѯ������������where֮������
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap sqlPageView(String tableName,String fieldKey,int pageCurrent,
			int pageSize,String fieldShow,String fieldOrder,String condition){
		Connection conn=null;
		ResultSet rs=null;
		CallableStatement cs=null;
		HashMap rtn_map=new HashMap();
		int pageCount=0;
		int rowsCount=0;
		try {
			conn = ConnectionPool.getIstance().getConnection();
			cs=conn.prepareCall("{call sp_PageView(?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, tableName);
			cs.setString(2,fieldKey);
			cs.setInt(3, pageCurrent);
			cs.setInt(4, pageSize);
			cs.setString(5, fieldShow);
			cs.setString(6, fieldOrder);
			cs.setString(7, condition);
			cs.registerOutParameter(8, Types.INTEGER);
			cs.registerOutParameter(9, Types.INTEGER);
			rs = cs.executeQuery();
			ArrayList rows = new ArrayList();
			ResultSetMetaData rsm = rs.getMetaData();
			while (rs.next()) {
				rows.add(getRow(rs, rsm));
			}
			pageCount=cs.getInt(8);
			rowsCount=cs.getInt(9);
			rtn_map.put("datas", rows);
			rtn_map.put("pageCount", pageCount);
			rtn_map.put("rowsCount", rowsCount);

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(rs!=null)
				rs.close();
				if(cs!=null)
				cs.close();
				if(conn!=null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rtn_map;
	}

	public static boolean updateSQL(String sql, Object... params) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectionPool.getIstance().getConnection();
			ps = conn.prepareStatement(sql);
			for(int i=0;i<params.length;i++){
				ps.setObject(i+1,params[i]);
			}
			int n = ps.executeUpdate();
			conn.commit();
			return n>0?true:false;
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if(ps!=null)
				ps.close();
				if(conn!=null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static boolean updateListSQL(String sql, List<Object> params) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectionPool.getIstance().getConnection();
			ps = conn.prepareStatement(sql);
			for(int i=0;i<params.size();i++){
				ps.setObject(i+1,params.get(i));
			}
			int n = ps.executeUpdate();
			conn.commit();
			return n>0?true:false;
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if(ps!=null)
				ps.close();
				if(conn!=null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
