package jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnectionExample {

	public static void main(String[] args) {
		try {
			Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
			DriverManager.registerDriver(driver);
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vt", "root", "tekrei");
			// Insert record to database
			PreparedStatement ps = con
					.prepareStatement("INSERT INTO Example (Field) VALUES (" + System.currentTimeMillis() + ")");
			ps.execute();
			// Select record from database
			ps = con.prepareStatement("SELECT * FROM Example");
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
