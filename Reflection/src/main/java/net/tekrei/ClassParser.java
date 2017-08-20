package net.tekrei;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Locale;
import java.util.Vector;

/**
 * Utility class for Java Reflection API
 *
 * @author tekrei
 * @date Jun 10, 2005
 */
class ClassParser {
    /**
     * return all (even superclass) fields of the class
     *
     * @param parseClass which class to parse
     * @return vector containing all fields
     */
    public static Vector<String> getListProperties(Class<?> parseClass) {
        Field[] fsSuper = parseClass.getSuperclass().getDeclaredFields();
        Field[] fs = parseClass.getDeclaredFields();
        Vector<String> rs = new Vector<>();
        for (Field aFsSuper : fsSuper) {
            rs.add(aFsSuper.getName());
        }
        for (Field f : fs) {
            rs.add(f.getName());
        }
        return rs;
    }

    /**
     * return class name
     *
     * @param parseClass which class to parse
     * @return name of the parseClass
     */
    public static String getClassName(Class<?> parseClass) {
        return parseClass.getName();
    }

    /**
     * return a method from the class using its name
     *
     * @param parseClass class to search for method
     * @param name       name of the method to search
     * @return searched method or null
     */
    private static Method getMethod(Class<?> parseClass, String name) {
        Method[] methods = parseClass.getMethods();

        for (Method method : methods) {
            if (method.getName().equals(name)) {
                return method;
            }
        }

        return null;
    }

    /**
     * initiate a new object instance from a class
     * using empty/default constructor
     *
     * @param cls class to get an object instance
     * @return initiated object
     */
    public static Object createInstance(Class<?> cls) {
        try {
            Constructor<?> cons = cls.getConstructor((Class<?>[]) null);
            return cons.newInstance((Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * return all fields of the class
     *
     * @param parseClass class to parse for field list
     * @param withSuper  if super class is going to be checked
     * @return vector containing all fields
     */
    private static Vector<Field> listProperties(Class<?> parseClass, boolean withSuper) {
        Vector<Field> rs = new Vector<>();
        Field[] fields = parseClass.getDeclaredFields();
        Collections.addAll(rs, fields);
        if ((parseClass.getSuperclass() != null) && withSuper) {
            rs.addAll(ClassParser.listProperties(parseClass.getSuperclass(), withSuper));
        }
        return rs;
    }

    /**
     * return value of the field in the object
     *
     * @param obj          object to get field value
     * @param propertyName name of the field
     * @return field value or null
     */
    public static Object getProperty(Object obj, String propertyName) {
        String name = "get" + ilkKarakterBuyut(propertyName);
        try {
            Method method = getMethod(obj.getClass(), name);
            if (method != null) {
                return method.invoke(obj, (Object[]) null);
            }
        } catch (Exception ex) {
            System.out.println("getProperty(object,string)" + ex.toString());
        }
        return null;
    }

    /**
     * return value of property (works faster)
     *
     * @param obj          object to get field value
     * @param propertyName name of the field
     * @return value of the field or null
     */
    private static Object getPropertyWithFastGetMethod(Object obj, String propertyName) {
        String name = "get" + ilkKarakterBuyut(propertyName);
        try {
            Method method = findMethod(obj.getClass(), name);
            if (method != null) {
                return method.invoke(obj, (Object[]) null);
            }
        } catch (Exception ex) {
            System.out.println("getProperty(object,string)" + ex.toString());
        }
        return null;
    }

    /**
     * Recursive method to search a method in the class and its super
     * classes
     *
     * @param cls        class to find the method
     * @param methodName name of the method to find
     * @return method if found or null
     */
    private static Method findMethod(Class<?> cls, String methodName) {
        try {
            Method method = cls.getMethod(methodName, (Class<?>[]) null);
            if (method != null) {
                return method;
            }
            //check in super class
            if (cls.getSuperclass() != null) {
                return findMethod(cls.getSuperclass(), methodName);
            }
        } catch (Exception ex) {
            if (cls.getSuperclass() != null) {
                return findMethod(cls.getSuperclass(), methodName);
            }
        }
        return null;
    }

    /**
     * Set value to the field of object
     *
     * @param obj          object to set field value
     * @param propertyname name of the field
     * @param value        value to set
     */
    private static void setProperty(Object obj, String propertyname, Object value) {
        String name = "set" + ilkKarakterBuyut(propertyname);
        try {
            Method method = getMethod(obj.getClass(), name);
            Object[] props = new Object[]{value};
            if (method != null) {
                method.invoke(obj, props);
            }
        } catch (Exception ex) {
            System.out.println("setProperty(object,string,object):" + ex.toString());
        }
    }

    /**
     * Transfer values of fields between two objects of same class
     *
     * @param from object to read field values
     * @param to   object to write field values
     */
    public static void transfer(Object from, Object to) {
        for (Field field : from.getClass().getDeclaredFields()) {
            try {
                setProperty(to, field.getName(), getPropertyWithFastGetMethod(from, field.getName()));
            } catch (Exception ex) {
                System.out.println("Unable to transfer the following field: " + field.getName());
            }
        }
    }

    /**
     * make the first character to be upper case
     *
     * @param string to change
     * @return new string with first character in uppercase
     */
    private static String ilkKarakterBuyut(String string) {
        return string.substring(0, 1).toUpperCase(Locale.ENGLISH) + string.substring(1);
    }

    /**
     * return value of field of an object
     *
     * @param obj          object to parse
     * @param propertyname field to get value
     * @return value of propertyname of obj
     */
    public Object getValue(Object obj, String propertyname) {
        String name = "get" + ilkKarakterBuyut(propertyname);
        try {
            if (obj.getClass().getMethod(name, (Class<?>[]) null) != null) {
                Method method = obj.getClass().getMethod(name, (Class<?>[]) null);
                return method.invoke(obj, (Object[]) null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Return type of the field in the object
     *
     * @param obj          object to get property type
     * @param propertyname name of the field
     * @return type of the field
     */
    public Class<?> getPropertyType(Object obj, String propertyname) {
        String name = "get" + ilkKarakterBuyut(propertyname);
        try {
            Method method = getMethod(obj.getClass(), name);
            if (method != null) {
                return method.getReturnType();
            }
        } catch (Exception ex) {
            System.out.println("getPropertyType(object,string)" + ex.toString());
        }
        return null;
    }
}
