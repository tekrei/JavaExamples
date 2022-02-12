package reflector;

import java.lang.reflect.*;
import java.util.ArrayList;

public class Main {
    public static final int i = 42;
    String s = "example";
    @SuppressWarnings("CanBeFinal")
    private final double d = 3.14;
    @SuppressWarnings("CanBeFinal")
    private final String className = "reflector.ReflectionExample";

    private Main() {
    }

    protected Main(int i, double d) {
    }

    public Main(Integer a, Integer b) {
        System.out.println("a = " + a + " b = " + b);
    }

    public static void main(String[] args) {
        Main example = new Main();
        example.constructorInformation();
        example.fieldInformation();
        example.methodInformation();
        example.changeValue();
        example.arrayExample();
        example.matrixExample();
        example.listMethods();
        example.instantiateObjectUsingConstructor();
        example.invokeMethod();
    }

    private void instantiateObjectUsingConstructor() {
        System.out.println("------------------------------------");
        System.out.println("Find and Invoke a Constructor to Instantiate a Class");
        System.out.println("------------------------------------");
        try {
            Class<?> cls = Class.forName(className);
            //we are going to use a constructor
            //which have 2 integer parameters
            Class<?>[] types = {Integer.class, Integer.class};
            //get constructor with these types from class
            Constructor<?> constructor = cls.getDeclaredConstructor(types);
            //prepare the arguments of this constructor
            Object[] arguments = {37, 47};
            //invoke this constructor using arguments to instantiate an object
            Object obj = constructor.newInstance(arguments);
            //display simple class name of this object
            System.out.println(obj.getClass().getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("------------------------------------");
    }

    private void fieldInformation() {
        System.out.println("------------------------------------");
        System.out.println("Fields of ReflectionExample Class");
        System.out.println("------------------------------------");
        try {
            Class<?> cls = Class.forName(className);
            //get and traverse all fields of the class
            for (Field fld : cls.getDeclaredFields()) {
                //name of the field
                System.out.println("name: " + fld.getName());
                //its declaring class
                System.out.println("declaring class:" + fld.getDeclaringClass());
                //type of the field
                System.out.println("type:" + fld.getType());
                //its modifiers
                int mod = fld.getModifiers();
                System.out.println("modifier: " + Modifier.toString(mod));
                System.out.println("-----");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("------------------------------------");
    }

    private void constructorInformation() {
        System.out.println("------------------------------------");
        System.out.println("Constructors of ReflectionExample Class");
        System.out.println("------------------------------------");
        try {
            Class<?> cls = Class.forName(className);
            //get and traverse all declared constructors
            for (Constructor<?> ct : cls.getDeclaredConstructors()) {
                System.out.println("name: " + ct.getName());
                System.out.println("declaring class: " + ct.getDeclaringClass());
                //parameters of constructor
                Class<?>[] pvec = ct.getParameterTypes();
                for (int j = 0; j < pvec.length; j++) {
                    System.out.println("parameter #" + j + " " + pvec[j]);
                }
                //exceptions of constructor
                Class<?>[] evec = ct.getExceptionTypes();
                for (int j = 0; j < evec.length; j++) {
                    System.out.println("exception #" + j + " " + evec[j]);
                }
                System.out.println("-----");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("------------------------------------");
    }

    private void matrixExample() {
        System.out.println("------------------------------------");
        System.out.println("Matrix Example");
        System.out.println("------------------------------------");
        int[] dimensions = {5, 10, 15};
        //create a three dimensional integer array using dimensions
        Object arr = Array.newInstance(Integer.TYPE, dimensions);
        //get 3rd element of array (first dimension)
        Object arrobj = Array.get(arr, 3);
        //get class of this element
        Class<?> cls = arrobj.getClass();
        //display name of its class, which is a two dimensional integer array
        System.out.println(cls.getSimpleName());
        //get 5th element of it
        arrobj = Array.get(arrobj, 5);
        //set 10th element of this element as 37
        Array.setInt(arrobj, 10, 37);
        //cast the object to its type
        int[][][] arrcast = (int[][][]) arr;
        //display the value that we set
        System.out.println(arrcast[3][5][10]);
        System.out.println("------------------------------------");
    }

    private void arrayExample() {
        System.out.println("------------------------------------");
        System.out.println("Array Example");
        System.out.println("------------------------------------");
        try {
            //Get String class
            Class<?> cls = Class.forName("java.lang.String");
            //Create an array with cls type
            Object arr = Array.newInstance(cls, 10);
            //set its 5th element
            Array.set(arr, 5, "fifth");
            //read 5th element
            String s = (String) Array.get(arr, 5);
            //display it
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("------------------------------------");
    }

    private void changeValue() {
        System.out.println("------------------------------------");
        System.out.println("Change Value Example");
        System.out.println("------------------------------------");
        try {
            Class<?> cls = Class.forName(className);
            //get field 'd' from class
            Field fld = cls.getDeclaredField("d");
            //instantiate a new object of the class
            Main f2obj = new Main();
            //display current value of 'd'
            System.out.println("current value of d = " + f2obj.d);
            //change value o 'd' as 12.34
            //it is easy if we know the type, we can directly use type specific
            //getter and setter
            fld.setDouble(f2obj, 12.34);
            //display new value of 'd'
            System.out.println("new value of d = " + f2obj.d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("------------------------------------");
    }

    private int f1(Object p, int x) throws NullPointerException {
        if (p == null) {
            throw new NullPointerException();
        }
        return x;
    }

    private void methodInformation() {
        System.out.println("------------------------------------");
        System.out.println("Methods of ReflectionExample Class");
        System.out.println("------------------------------------");
        try {
            //get Class
            Class<?> cls = Class.forName(className);
            //get and traverse all methods
            for (Method m : cls.getDeclaredMethods()) {
                //display method information
                System.out.println("name: " + m.getName());
                //its declaring class
                System.out.println("declaring class: " + m.getDeclaringClass());
                //parameters of the method
                Class<?>[] pvec = m.getParameterTypes();
                for (int j = 0; j < pvec.length; j++) {
                    System.out.println("parametre #" + j + " " + pvec[j]);
                }
                //exceptions of the method
                Class<?>[] evec = m.getExceptionTypes();
                for (int j = 0; j < evec.length; j++) {
                    System.out.println("exception #" + j + " " + evec[j]);
                }
                //its return type
                System.out.println("returns: " + m.getReturnType());
                System.out.println("-----");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("------------------------------------");
    }

    private void listMethods() {
        System.out.println("------------------------------------");
        System.out.println("Listing Methods of ArrayList Class");
        System.out.println("------------------------------------");
        try {
            //get ArrayList class
            Class<?> c = ArrayList.class;
            //get and traverse all declared methods of this class
            for (Method m : c.getDeclaredMethods()) {
                System.out.println(m.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("------------------------------------");
    }

    private int add(int a, int b) {
        return a + b;
    }

    private void invokeMethod() {
        System.out.println("------------------------------------");
        System.out.println("Invoking a Method");
        System.out.println("------------------------------------");
        try {
            //get class
            Class<?> cls = Class.forName(className);
            //parameter types
            Class<?>[] types = {Integer.TYPE, Integer.TYPE};
            //get method named 'add' with parameters of types
            Method method = cls.getDeclaredMethod("add", types);
            //initiate an object of class
            Main object = new Main();
            //prepare arguments
            Object[] arguments = {42, 42};
            //invoke the method over the object we initiated
            Object returnObject = method.invoke(object, arguments);
            //Donen degeri uygun tipe dondurur
            Integer returnValue = (Integer) returnObject;
            //result should be 42+42=84
            System.out.println(returnValue.intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("------------------------------------");
    }
}
