package net.tekrei.db2pojo.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBManager {

	private static DBManager _instance;

	private String driverName;

	private String databaseURL;

	private String username;

	private String password;

	private ConnectionPool connectionPool;

	public static DBManager initManager(String surucu, String url, String kullanici, String sifre) throws Exception {
		_instance = new DBManager(surucu, url, kullanici, sifre);
		return _instance;
	}

	private DBManager(String dn, String url, String u, String p) throws Exception {
		this.driverName = dn;
		this.databaseURL = url;
		this.username = u;
		this.password = p;
		initDB();
	}

	public static DBManager getInstance() {
		return _instance;
	}

	private void initDB() throws Exception {
		connectionPool = new ConnectionPool(databaseURL, username, password, driverName, 5);
	}

	public boolean execute(String sql) throws Exception {
		Connection conn = null;

		try {
			conn = connectionPool.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			System.out.println("execute:" + sql);
			return ps.execute();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (conn != null) {
				connectionPool.releaseConnection(conn);
			}
		}
	}

	public long insert(String sql) throws Exception {
		Connection conn = null;

		try {
			conn = connectionPool.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			long key = rs.getLong(1);

			System.out.println("insert:" + sql);
			return key;

		} catch (SQLException e) {
			throw e;
		} finally {
			if (conn != null) {
				connectionPool.releaseConnection(conn);
			}
		}
	}

	public ResultSet executeQuery(String sql) throws Exception {
		Connection conn = null;

		try {
			conn = connectionPool.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql);

			System.out.println("executeQuery:" + sql);
			return ps.executeQuery();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (conn != null) {
				connectionPool.releaseConnection(conn);
			}
		}
	}

	public ResultSetMetaData getMetadata(String table) throws SQLException {
		Connection conn = null;

		try {
			conn = connectionPool.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + table);
			return ps.executeQuery().getMetaData();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (conn != null) {
				connectionPool.releaseConnection(conn);
			}
		}
	}

	public Connection getConnection() {
		return connectionPool.getConnection();
	}

	public String[] getTableNames() throws Exception {
		Connection conn = null;

		ArrayList<String> tables = new ArrayList<String>();

		try {
			conn = connectionPool.getConnection();
			DatabaseMetaData metaData = conn.getMetaData();

			ResultSet rs = metaData.getTables(null, null, null, new String[] { "Table" });
			while (rs.next()) {
				tables.add(rs.getString("TABLE_NAME"));
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (conn != null) {
				connectionPool.releaseConnection(conn);
			}
		}
		return tables.toArray(new String[] {});
	}
}
