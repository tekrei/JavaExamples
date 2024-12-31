package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {

	public static void main(String[] args) {
		// Get a connection from Database Connection Manager
		Connection conn = ConnectionManager.getInstance().getConnection();
		try {
			// Create table
			PreparedStatement ps = conn
					.prepareStatement("CREATE TABLE Example (Field VARCHAR)");
			ps.execute();

			// Insert record to database
			ps = conn
					.prepareStatement("INSERT INTO Example (Field) VALUES (" + System.currentTimeMillis() + ")");
			ps.execute();
			// Select record from database
			ps = conn.prepareStatement("SELECT * FROM Example");
			// Selected records are in ResultSet object
			ResultSet rs = ps.executeQuery();
			// We need to traverse ResultSet object
			while (rs.next()) {
				// And display the values
				System.out.println(rs.getString("Field"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
