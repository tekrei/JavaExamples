
/*
 * Copyright (c) 1998 by Gefion software.
 *
 * Permission to use, copy, and distribute this software for
 * NON-COMMERCIAL purposes and without fee is hereby granted
 * provided that this copyright notice appears in all copies.
 *
 * Modified by: tekrei, 01.06.2008
 */

package jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

/**
 * This class is a Singleton that provides access to one or many connection
 * pools defined in a Property file. A client gets access to the single instance
 * through the static getInstance() method and can then check-out and check-in
 * connections from a pool. When the client shuts down it should call the
 * release() method to close all open connections and do other clean up.
 */
public class ConnectionManager {
	private static Parameters dbp;

	static private ConnectionManager instance; // The single instance

	static private int clients;

	private ConnectionPool pool;

	/**
	 * Returns the single instance, creating one if it's the first time this
	 * method is called.
	 * 
	 * @return ConnectionManager The single instance.
	 */
	static synchronized public ConnectionManager getInstance() {
		if (instance == null) {
			instance = new ConnectionManager();
		}
		clients++;
		return instance;
	}

	/**
	 * A private constructor since this is a Singleton
	 */
	private ConnectionManager() {
		init();
	}

	/**
	 * Returns a connection to the named pool.
	 * 
	 * @param name
	 *             The pool name as defined in the properties file
	 * @param con
	 *             The Connection
	 */
	public void freeConnection(String name, Connection con) {
		if (pool != null) {
			pool.freeConnection(con);
		}
	}

	/**
	 * Returns an open connection. If no one is available, and the max number of
	 * connections has not been reached, a new connection is created.
	 * 
	 * @param name
	 *             The pool name as defined in the properties file
	 * @return Connection The connection or null
	 */
	public Connection getConnection() {
		if (pool != null) {
			return pool.getConnection();
		}
		return null;
	}

	/**
	 * Returns an open connection. If no one is available, and the max number of
	 * connections has not been reached, a new connection is created. If the max
	 * number has been reached, waits until one is available or the specified
	 * time has elapsed.
	 * 
	 * @param name
	 *             The pool name as defined in the properties file
	 * @param time
	 *             The number of milliseconds to wait
	 * @return Connection The connection or null
	 */
	public Connection getConnection(String name, long time) {
		if (pool != null) {
			return pool.getConnection(time);
		}
		return null;
	}

	/**
	 * Closes all open connections and deregisters all drivers.
	 */
	public synchronized void release() {
		// Wait until called by the last client
		if (--clients != 0) {
			return;
		}

		pool.release();

	}

	/**
	 * Creates instances of ConnectionPool based on the properties. A
	 * ConnectionPool can be defined with the following properties:
	 * 
	 * <PRE>
	 * 
	 * &lt;poolname&gt;.url The JDBC URL for the database &lt;poolname&gt;.user
	 * A database user (optional) &lt;poolname&gt;.password A database user
	 * password (if user specified) &lt;poolname&gt;.maxconn The maximal number
	 * of connections (optional)
	 * 
	 * </PRE>
	 * 
	 * @param props
	 *              The connection pool properties
	 */
	private void createPool(Parameters props) {
		pool = new ConnectionPool("ConPool", dbp.getUrl(), dbp.getUser(),
				dbp.getPassword(), dbp.getMaxConnection());
		log("Initialized pool " + "ConPool");
	}

	/**
	 * Loads properties and initializes the instance with its values.
	 */
	private void init() {
		if (dbp == null)
			dbp = ParametersReader.readParameters();
		loadDriver(dbp);
		createPool(dbp);
	}

	/**
	 * Loads and registers all JDBC drivers. This is done by the
	 * ConnectionManager, as opposed to the ConnectionPool, since many pools
	 * may share the same driver.
	 * 
	 * @param props
	 *              The connection pool properties
	 */
	private void loadDriver(Parameters dbp) {
		try {
			Driver driver = (Driver) Class.forName(dbp.getDriver()).getDeclaredConstructor().newInstance();
			DriverManager.registerDriver(driver);
			log("Registered JDBC driver " + dbp.getDriver());
		} catch (Exception e) {
			log("Can't register JDBC driver: " + dbp.getDriver() + ", Exception: "
					+ e);
		}
	}

	/**
	 * Writes a message to the log file.
	 */
	private void log(String msg) {
		System.out.println(new Date() + ": " + msg);
	}

	/**
	 * Writes a message with an Exception to the log file.
	 */
	private void log(Throwable e, String msg) {
		System.out.println(new Date() + ": " + msg);
		e.printStackTrace();
	}

	/**
	 * This inner class represents a connection pool. It creates new connections
	 * on demand, up to a max number if specified. It also makes sure a
	 * connection is still open before it is returned to a client.
	 */
	class ConnectionPool {
		private int checkedOut;

		private Vector<Connection> freeConnections = new Vector<Connection>();

		private int maxConn;

		private String name;

		private String password;

		private String URL;

		private String user;

		/**
		 * Creates new connection pool.
		 * 
		 * @param name
		 *                 The pool name
		 * @param URL
		 *                 The JDBC URL for the database
		 * @param user
		 *                 The database user, or null
		 * @param password
		 *                 The database user password, or null
		 * @param maxConn
		 *                 The maximal number of connections, or 0 for no limit
		 */
		public ConnectionPool(String name, String URL, String user,
				String password, int maxConn) {
			this.name = name;
			this.URL = URL;
			this.user = user;
			this.password = password;
			this.maxConn = maxConn;
		}

		/**
		 * Checks in a connection to the pool. Notify other Threads that may be
		 * waiting for a connection.
		 * 
		 * @param con
		 *            The connection to check in
		 */
		public synchronized void freeConnection(Connection con) {
			// Put the connection at the end of the Vector
			freeConnections.addElement(con);
			checkedOut--;
			notifyAll();
		}

		/**
		 * Checks out a connection from the pool. If no free connection is
		 * available, a new connection is created unless the max number of
		 * connections has been reached. If a free connection has been closed by
		 * the database, it's removed from the pool and this method is called
		 * again recursively.
		 */
		public synchronized Connection getConnection() {
			Connection con = null;
			if (freeConnections.size() > 0) {
				// Pick the first Connection in the Vector
				// to get round-robin usage
				con = (Connection) freeConnections.firstElement();
				freeConnections.removeElementAt(0);
				try {
					if (con.isClosed()) {
						log("Removed bad connection from " + name);
						// Try again recursively
						con = getConnection();
					}
				} catch (SQLException e) {
					log("Removed bad connection from " + name);
					// Try again recursively
					con = getConnection();
				}
			} else if (maxConn == 0 || checkedOut < maxConn) {
				con = newConnection();
			}
			if (con != null) {
				checkedOut++;
			}
			return con;
		}

		/**
		 * Checks out a connection from the pool. If no free connection is
		 * available, a new connection is created unless the max number of
		 * connections has been reached. If a free connection has been closed by
		 * the database, it's removed from the pool and this method is called
		 * again recursively.
		 * <P>
		 * If no connection is available and the max number has been reached,
		 * this method waits the specified time for one to be checked in.
		 * 
		 * @param timeout
		 *                The timeout value in milliseconds
		 */
		public synchronized Connection getConnection(long timeout) {
			long startTime = new Date().getTime();
			Connection con;
			while ((con = getConnection()) == null) {
				try {
					wait(timeout);
				} catch (InterruptedException e) {
				}
				if ((new Date().getTime() - startTime) >= timeout) {
					// Timeout has expired
					return null;
				}
			}
			return con;
		}

		/**
		 * Closes all available connections.
		 */
		public synchronized void release() {
			Enumeration<Connection> allConnections = freeConnections.elements();
			while (allConnections.hasMoreElements()) {
				Connection con = allConnections.nextElement();
				try {
					con.close();
					log("Closed connection for pool " + name);
				} catch (SQLException e) {
					log(e, "Can't close connection for pool " + name);
				}
			}
			freeConnections.removeAllElements();
		}

		/**
		 * Creates a new connection, using a userid and password if specified.
		 */
		private Connection newConnection() {
			Connection con = null;
			try {
				if (user == null) {
					con = DriverManager.getConnection(URL);
				} else {
					con = DriverManager.getConnection(URL, user, password);
				}
				log("Created a new connection in pool " + name);
			} catch (SQLException e) {
				log(e, "Can't create a new connection for " + URL);
				return null;
			}
			return con;
		}
	}
}
