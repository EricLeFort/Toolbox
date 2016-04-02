package tools;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Eric Le Fort
 * @version 1.0
 */
public class CommonElementsList<T>{
	ArrayList<T> common;
	
	/**
	 * Initializes a new list using the passed in array to begin with. Common elements will
	 * include the whole array since there is nothing to compare to.
	 * @param array - The original array to compare to.
	 */
	public CommonElementsList(T[] array){
		common = new ArrayList<T>();
		common.addAll(Arrays.asList(array));
	}//Constructor

	/**
	 * Reduces the list of common elements to only include those found in the list passed in.
	 * @param array - The array of new elements to check against.
	 */
	public void addList(T[] array){
		ArrayList<T> newCommon = new ArrayList<T>();
		
		for(int i = 0; i < array.length; i++){
			if(common.contains(array[i])){
				newCommon.add(array[i]);
			}
		}
		
		common = newCommon;
	}//addList()
	
	/**
	 * Returns the current list of common elements.
	 * @return The current list of common elements.
	 */
	public T[] getCommonElements(){
		return (T[]) common.toArray();
	}//getCommonElements()
	
}//CommonElementsList
