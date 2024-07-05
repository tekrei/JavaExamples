package jdbc;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * This class will read database parameters
 * 
 * @author tekrei
 * 
 */
public class ParametersReader {

	/**
	 * This method returns the database parameters from parameters file
	 * 
	 * @return Database parameters
	 */
	public static Parameters readParameters() {
		// We need Properties class
		Properties defaultProps = new Properties();
		try {
			// vt.properties is the properties file
			FileInputStream in = new FileInputStream("vt.properties");
			// loading the file content to the Properties object
			defaultProps.load(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Let's create an object from parameters read from file
		return new Parameters(
				defaultProps.getProperty("jdbc.driver"),
				defaultProps.getProperty("jdbc.url"),
				defaultProps.getProperty("jdbc.user"),
				defaultProps.getProperty("jdbc.password"));
	}
}
