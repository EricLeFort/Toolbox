package tools;

/**
 * @author Eric Le Fort
 * @version 01
 */

public class Testing{

	public static void main(String...args){
		
	}//main()

	/**
	 * Given a class path of the form package.className and the name of the method, this will return the method 
	 * @param classPath
	 * @param methodName
	 * @param targetObject
	 * @param parameters
	 * @return The private method to be tested.
	 */
	public java.lang.reflect.Method extractPrivateMethod(String classPath, String methodName, java.lang.Class<?>[] parameters){
		try{
			java.lang.Class<?> c = java.lang.Class.forName(classPath);
			java.lang.reflect.Method parsePackage = c.getDeclaredMethod(methodName, parameters);
			parsePackage.setAccessible(true);
			return parsePackage;
		}catch(ClassNotFoundException cnfe){
			cnfe.printStackTrace();
			System.out.println("The class " + classPath + " cound not be found. Check your spelling!");
		}catch(NoSuchMethodException nsme){
			nsme.printStackTrace();
			System.out.println("The method " + methodName + "() cound not be found. Check your spelling!");
		}
		return null;
	}//extractPrivateMethod()
	
}//Testing
