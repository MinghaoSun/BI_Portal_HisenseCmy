package com.analytic.portal.common.sys;
import java.sql.*;

import javax.sql.DataSource;

import com.yzd.plat.common.util.SystemException;



public class ConnectionPool {
	private  DataSource ds = null;

	private static ConnectionPool mySelf = null;

	private ConnectionPool(DataSource ds) {
		this.ds = ds;
	}

	public static void init() {
		if (mySelf != null)
			return;
		try {
			mySelf = new ConnectionPool((DataSource)AppContext.getInstance().getAppContext().getBean("dataSource"));
		} catch (Exception e) {
			throw new SystemException("Unable to open datasource.-"
					+ e.getMessage());
		}

	}


	public static ConnectionPool getIstance() {
		if (mySelf == null) {
			init();
			//throw new SystemException("Pool not initialized.");
		}

		return mySelf;
	}


	public Connection getConnection() throws SQLException {
		Connection conn = ds.getConnection();
		conn.setAutoCommit(false);
		return conn;
	}

	public static void destory() {
		/*try {
			mySelf.ds.close();
		} catch (Exception e) {
			throw new SystemException(e.getMessage());
		} finally {
			mySelf = null;
		}*/
	}


	public static void closeConn(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (Exception e) {
			throw new SystemException(e.getMessage());
		}
	}

	public static void connRollBack(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (Exception e) {
			throw new SystemException(e.getMessage());
		}
	}
}