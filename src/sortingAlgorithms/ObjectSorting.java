package sortingAlgorithms;
/**
* @author Eric Le Fort
* @version 01
*/

public class ObjectSorting{
	
	public static void main(String...args){
		
	}//main()

	/**
	 * Implements a standard selection sorting algorithm. This algorithm is destructive; the array passed in will be sorted.
	 * @param array - The array to be sorted.
	 */
	public static <T extends Comparable<T>> void selectionSort(T[] array){
		int smallestIndex;
		
		for(int i = 0; i < array.length - 1; i++){		//Goes through all but one iteration, when there's just one left it's sorted.
			smallestIndex = i;
			
			for(int j = i+1; j < array.length; j++){	//Finds smallest value.
				if(array[j].compareTo(array[smallestIndex]) < 1){
					smallestIndex = j;
				}
			}
			
			swap(array, i, smallestIndex);				//Switches the smallest value into position.
		}
		
	}//selectionSort()

	/**
	 * Implements a standard bubble sorting algorithm. This algorithm is destructive; the array passed in will be sorted.
	 * @param array - The array to be sorted.
	 */
	public static <T extends Comparable<T>> void bubbleSort(T[] array){
		for(int i = array.length - 1; i > 0; i--){				//Value of i corresponds to current position.
			for(int j = 0; j < i; j++){
				if(array[j].compareTo(array[j+1]) > 0){			//This value is greater than the next one, swap the values.
					swap(array, j, j + 1);
				}
			}
		}
	}//bubbleSort()
 	
	/**
	 * Swaps the elements located in the indices <code>index1</code> and <code>index2</code> within the array passed in.
	 * @param array - The array to perform the swap on.
	 * @param index1 - The index of one element to be swapped.
	 * @param index2 - The index of the other element to be swapped.
	 * @throws IndexOutOfBoundsException
	 * @throws NullPointerException
	 */
	private static <T> void swap(T[] array, int index1, int index2) throws IndexOutOfBoundsException, NullPointerException{
		T temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}//swap()
	
}//ObjectSorting
