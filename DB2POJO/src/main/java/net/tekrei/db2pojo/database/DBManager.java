package net.tekrei.db2pojo.database;

import java.sql.*;
import java.util.ArrayList;

public class DBManager {

    private static DBManager _instance;
    private ConnectionPool connectionPool;

    private DBManager(String driverName, String databaseURL, String username, String password) {
        try {
            connectionPool = new ConnectionPool(databaseURL, username, password, driverName, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initManager(String surucu, String url, String kullanici, String sifre) {
        _instance = new DBManager(surucu, url, kullanici, sifre);
    }

    public static DBManager getInstance() {
        return _instance;
    }

    public boolean execute(String sql) {
        Connection conn = null;

        try {
            conn = connectionPool.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);
            System.out.println("execute:" + sql);
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                connectionPool.releaseConnection(conn);
            }
        }
        return false;
    }

    public long insert(String sql) {
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
            e.printStackTrace();
        } finally {
            if (conn != null) {
                connectionPool.releaseConnection(conn);
            }
        }
        return -1;
    }

    public ResultSet executeQuery(String sql) {
        Connection conn = null;

        try {
            conn = connectionPool.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);

            System.out.println("executeQuery:" + sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                connectionPool.releaseConnection(conn);
            }
        }
        return null;
    }

    public ResultSetMetaData getMetadata(String table) {
        Connection conn = null;

        try {
            conn = connectionPool.getConnection();

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + table);
            return ps.executeQuery().getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                connectionPool.releaseConnection(conn);
            }
        }
        return null;
    }

    public Connection getConnection() {
        return connectionPool.getConnection();
    }

    public String[] getTableNames() {
        Connection conn = null;

        ArrayList<String> tables = new ArrayList<>();

        try {
            conn = connectionPool.getConnection();
            DatabaseMetaData metaData = conn.getMetaData();

            ResultSet rs = metaData.getTables(null, null, null, new String[]{"Table"});
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                connectionPool.releaseConnection(conn);
            }
        }
        return tables.toArray(new String[]{});
    }
}
