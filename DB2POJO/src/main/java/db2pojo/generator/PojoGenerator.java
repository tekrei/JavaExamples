package db2pojo.generator;

import db2pojo.database.DBManager;

import java.sql.ResultSetMetaData;
import java.util.Locale;

/**
 * This class generates POJO from parameters
 *
 * @author emre
 */
public class PojoGenerator {

    private static PojoGenerator instance = null;

    private PojoGenerator() {

    }

    public static PojoGenerator getInstance() {
        if (instance == null) {
            instance = new PojoGenerator();
        }
        return instance;
    }

    /**
     * This method generated POJO of the table given as parameter
     *
     * @param tableName    table to create POJO from
     */
    public String getPojo(String tableName) {
        StringBuilder result = new StringBuilder("/*\n* TABLE_NAME: " + tableName + "\n*/\n");
        result.append("public class ").append(getClassName(tableName)).append(" {\n");
        try {
            ResultSetMetaData metaData = DBManager.getInstance().getMetadata(tableName);
            for (int i = 1; i < metaData.getColumnCount() + 1; i++) {
                //Field
                String fieldName = metaData.getColumnName(i);
                String fieldClass = metaData.getColumnClassName(i);
                result.append("\tprivate ").append(fieldClass).append(" ").append(fieldName).append(";\n\n");
                //Get
                result.append("\tpublic ").append(fieldClass).append(" get").append(firstCharacterUppercase(fieldName)).append("(){\n");
                result.append("\t\treturn ").append(fieldName).append(";\n");
                result.append("\t}\n\n");
                //Set
                result.append("\tpublic void set").append(firstCharacterUppercase(fieldName)).append("(").append(fieldClass).append(" _").append(fieldName).append("){\n");
                result.append("\t\tthis.").append(fieldName).append("=_").append(fieldName).append(";\n");
                result.append("\t}\n\n");
            }
            result.append("}");
        } catch (Exception e) {
            e.printStackTrace();
            result = new StringBuilder(e.toString());
        }
        return result.toString();

    }

    public String getClassName(String tableName) {
        return firstCharacterUppercase(tableName.toLowerCase());
    }

    private String firstCharacterUppercase(String tableName) {
        return tableName.substring(0, 1).toUpperCase(Locale.ENGLISH) + tableName.substring(1);
    }
}
