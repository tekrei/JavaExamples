/**
 * =======================================================
 * BizSakladik : Verilerin veritabanina saklanmasinda Java 
 * tarafindan kullanilmak uzere gelistirilmis bir kalici 
 * katman altyapisidir.
 * =======================================================
 * Copyright (C) 2006 AYKIRI MUHENDISLER
 * ======================================
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, 
 * USA.  
 * 
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 * 
 * Copyright (C) 2006 AYKIRI MUHENDISLER
 */
package net.tekrei.db2pojo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectionPool {

	private String url;

	private String username;

	private String password;

	private String driverName;

	private int connectionCount;

	private ArrayList<Connection> passiveConnections = null;

	private ArrayList<Connection> activeConnections = null;

	public ConnectionPool(String _url, String u, String p, String d, int c) throws Exception {
		url = _url;
		username = u;
		password = p;
		connectionCount = c;
		driverName = d;
		passiveConnections = new ArrayList<Connection>(c);
		activeConnections = new ArrayList<Connection>(c);

		initConnections();
	}

	private void initConnections() throws Exception {
		try {
			Class.forName(driverName).newInstance();
		} catch (Exception e) {
			throw new Exception("Couldn't find database driver!");
		}
		for (int i = 0; i < connectionCount; i++) {
			passiveConnections.add(createConnection());
		}
	}

	private Connection createConnection() {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return conn;
	}

	public synchronized Connection getConnection() {
		Connection conn = null;
		if (passiveConnections.size() > 0) {
			conn = passiveConnections.remove(0);
			activeConnections.add(conn);
		}
		return conn;
	}

	public synchronized boolean releaseConnection(Connection conn) {
		if (conn == null) {
			return false;
		}
		passiveConnections.add(conn);
		return activeConnections.remove(conn);
	}
}
