package tools;
/**
 * @author Eric Le Fort
 * @version 01
 */
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.regex.*;

public class Testing{
	
	public static void main(String...args){
		Document site;
		Elements elems;
		
		try{
			site = Jsoup.connect("https://www.google.ca").get();
			elems = site.getElementsByTag("a");
			for(int i = 0; i < elems.size(); i++){
				if(elems.get(i).toString().contains("a href")){
					System.out.println(elems.get(i));
				}
			}
			System.out.println(elems.get(5));
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}//main()
	
	/**
	 * Given a class path of the form package.className and the name of the method, this will return the method 
	 * @param classPath
	 * @param methodName
	 * @param targetObject
	 * @param parameters
	 * @return The private method to be tested.
	 */
	public static java.lang.reflect.Method extractPrivateMethod(String classPath, String methodName, java.lang.Class<?>[] parameters){
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
