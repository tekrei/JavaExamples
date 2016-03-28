package browser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
	private static Database instance;

	Statement stat;

	public static Database getInstance() {
		if (instance == null)
			instance = new Database();
		return instance;
	}

	private Database() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:sites");
			stat = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getKategoriler() throws Exception {
		ResultSet rs = stat.executeQuery("SELECT * FROM kategori");
		ArrayList<String> al = new ArrayList<String>();
		while (rs.next()) {
			al.add(rs.getString("kategori"));
		}
		rs.close();
		return al;
	}

	public ArrayList<Site> getSiteler(int kategori) throws Exception {
		ResultSet rs = stat
				.executeQuery("SELECT * FROM siteler WHERE kategori="
						+ kategori);
		ArrayList<Site> al = new ArrayList<Site>();
		while (rs.next()) {
			al.add(new Site(rs.getString("isim"), rs.getString("url")));
		}
		rs.close();
		return al;
	}

	public void siteEkle(Site site, int kategori) throws Exception {
		stat
				.executeUpdate("INSERT INTO siteler (isim, url, kategori) values ('"
						+ site.isim + "','" + site.url + "'," + kategori + ")");
	}

	public void siteSil(String url) throws Exception {
		stat.executeUpdate("DELETE FROM siteler WHERE url='" + url + "'");
	}
}
