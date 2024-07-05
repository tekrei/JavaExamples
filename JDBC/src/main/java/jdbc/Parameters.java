package jdbc;

public class Parameters {
	private String driver;
	private String url;
	private String user;
	private String password;
	private int maxConnection = 5;

	public Parameters(String driver, String url, String user, String password, int maxConnection) {
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
		this.maxConnection = maxConnection;
	}

	public Parameters(String driver, String url, String user, String password) {
		this(driver, url, user, password, 5);
	}

	public String getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public int getMaxConnection() {
		return maxConnection;
	}
}