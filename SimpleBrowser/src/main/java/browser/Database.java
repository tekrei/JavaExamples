package browser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

class Database {
    private static Database instance;

    private Statement stat;

    private Database() {
        try {
            Class.forName("org.sqlite.JDBC");
            // database is the "sites" file in top folder of the project
            Connection conn = DriverManager.getConnection("jdbc:sqlite:sites");
            stat = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;
    }

    ArrayList<String> getKategoriler() throws Exception {
        ResultSet rs = stat.executeQuery("SELECT * FROM kategori");
        ArrayList<String> al = new ArrayList<>();
        while (rs.next()) {
            al.add(rs.getString("kategori"));
        }
        rs.close();
        return al;
    }

    ArrayList<Site> getSiteler(int kategori) throws Exception {
        ResultSet rs = stat
                .executeQuery("SELECT * FROM siteler WHERE kategori="
                        + kategori);
        ArrayList<Site> al = new ArrayList<>();
        while (rs.next()) {
            al.add(new Site(rs.getString("isim"), rs.getString("url")));
        }
        rs.close();
        return al;
    }

    void siteEkle(Site site, int kategori) throws Exception {
        stat
                .executeUpdate("INSERT INTO siteler (isim, url, kategori) values ('"
                        + site.isim + "','" + site.url + "'," + kategori + ")");
    }

    void siteSil(String url) throws Exception {
        stat.executeUpdate("DELETE FROM siteler WHERE url='" + url + "'");
    }

    static class Site {
        final String isim;
        final String url;

        Site(String _isim, String _url) {
            isim = _isim;
            url = _url;
        }

        public String toString() {
            return isim;
        }
    }
}
