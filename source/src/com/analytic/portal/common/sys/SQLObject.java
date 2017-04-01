package com.analytic.portal.common.sys;

import java.util.*;

/**
 * sql�Ķ��󡣸����װsql�ĺ���Ҫ�������ݡ�
 */
public class SQLObject {
    /**
     * sql�ı���
     */
    private String sql;
    /**
     * sql�Ĳ���
     */
    @SuppressWarnings("rawtypes")
	private Vector params;

    @SuppressWarnings("rawtypes")
	public SQLObject() {
        params = new Vector();
    }

    /**
     * ����SQLObject����
     * @param sql sql�ı���
     */
    @SuppressWarnings("rawtypes")
	public SQLObject(String sql) {
        this.sql = sql;
        params = new Vector();
    }

    /**
     * �������ֵ
     * @param param ����ֵ
     */
    @SuppressWarnings("unchecked")
	public void addParam(Object param) {
        if (param instanceof String)
            param = ((String) param).trim();

        params.add(param);
    }

    /**
     * �����������Ͳ���ֵ
     * @param param �������Ͳ���ֵ
     */
    @SuppressWarnings("unchecked")
	public void addDateTimeParam(String param) {
        if (param == null || "".equals(param))
            params.add("");
        else {
            if (param.trim().length() == 16) {
                param = param.concat(":00");
            }
            params.add(java.sql.Timestamp.valueOf(param)); //Converts a string in JDBC date escape format to a Date value.
        }
    }

    /**
     * �����������Ͳ���ֵ
     * @param param �������Ͳ���ֵ
     */
    @SuppressWarnings("unchecked")
	public void addDateParam(String param) {
        if (param == null || "".equals(param))
            params.add("");
        else
            params.add(java.sql.Date.valueOf(param.trim())); //Converts a string in JDBC date escape format to a Date value.
    }

    /**
     * ��ò����
     * @return ����� (Vector params)
     */
    @SuppressWarnings("rawtypes")
	public Vector getParams() {
        return params;
    }

    /**
     * ���sql�ı���
     * @return String sql�ı���
     */

    public String getSql() {
        return sql;
    }

    /**
     * ���ò����
     * @param params Vector�����
     */
    @SuppressWarnings("rawtypes")
	public void setParams(Vector params) {
        this.params = params;
    }

    /**
     * ����sql�ı���
     * @param sql sql�ı���
     */
    public void setSql(String sql) {
        this.sql = sql;
    }

    public String toString() {
        if (sql == null)
            return super.toString();
        StringBuffer sb = new StringBuffer(sql);

        for (int i = 0; i < params.size(); i++) {
            int index = sb.indexOf("?");
            if (index > -1)
                sb.replace(index, index + 1,
                           params.get(i) == null ? "null" :
                           params.get(i).toString());
        }

        return sb.toString();
    }

}
