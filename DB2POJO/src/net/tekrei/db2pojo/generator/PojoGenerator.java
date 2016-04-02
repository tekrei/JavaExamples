package net.tekrei.db2pojo.generator;

import java.sql.ResultSetMetaData;
import java.util.Locale;

import net.tekrei.db2pojo.database.DBManager;

/**
 * This class generates POJO from parameters
 * 
 * @author emre
 *
 */
public class PojoGenerator {

	private static PojoGenerator instance = null;
	
	private PojoGenerator(){
		
	}
	
	public static PojoGenerator getInstance(){
		if(instance == null){
			instance = new PojoGenerator();
		}
		return instance;
	}
	
	/**
	 * This method generated POJO of the table given as parameter
	 * 
	 * @param tableName table to create POJO from
	 * @param dbConnection connection to use to access DB
	 * @return
	 */
	public String getPojo(String tableName){
		String result ="/*\n* TABLE_NAME: "+tableName+"\n*/\n"; 
		result+="public class "+getClassName(tableName)+" {\n";
		try {
			ResultSetMetaData metaData = DBManager.getInstance().getMetadata(tableName);
			for(int i=1;i<metaData.getColumnCount()+1;i++){
				//Field
				String fieldName = metaData.getColumnName(i);
				String fieldClass = metaData.getColumnClassName(i);
				result+="\tprivate "+fieldClass+" "+fieldName+";\n\n";
				//Get
				result+="\tpublic "+fieldClass+" get"+firstCharacterUppercase(fieldName)+"(){\n";
				result+="\t\treturn "+fieldName+";\n";
				result+="\t}\n\n";
				//Set
				result+="\tpublic void set"+firstCharacterUppercase(fieldName)+"("+fieldClass+" _"+fieldName+"){\n";
				result+="\t\tthis."+fieldName+"=_"+fieldName+";\n";
				result+="\t}\n\n";
			}
			result+="}";
		} catch (Exception e) {
			e.printStackTrace();
			result = e.toString();
		}
		return result;
		
	}
	
	public String getClassName(String tableName){
		return firstCharacterUppercase(tableName.toLowerCase());
	}

	private String firstCharacterUppercase(String tableName){
		return tableName.substring(0,1).toUpperCase(Locale.ENGLISH)+tableName.substring(1);
	}
}
