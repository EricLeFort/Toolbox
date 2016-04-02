package tools;
/**
 * @author Eric Le Fort
 * @version 01
 */
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.InputMismatchException;
import java.util.Scanner;

public class General{
	
	/**
	 * Generates a random number within the specified range(from 0 to the range value inclusive). Returns -1 if an error occurs.
	 * @param range
	 * @return rnd
	 */
	public static int rndNumGen(int range){
		int rnd = 0, stdRnd = (int)(Math.random() * 500);					//Arbitrary value of 500 to give a decent range to the seed value.
		
		try{
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
			sr.setSeed(stdRnd);
			rnd = sr.nextInt(range + 1);
		}catch(NoSuchAlgorithmException nsae){
			return -1;
		}catch(NoSuchProviderException nspe){
			return -1;
		}
		
		return rnd;
	}//rndNumGen(int range)
	
	/**
	 * Similarity ranging from 0%-100%.
	 * If the Strings are identical they return 100%
	 * @param str1
	 * @param str2
	 * @return The percentage representing how similar the two Strings are.
	 */
	public static int stringSimilarity(String str1, String str2){
		int sim = -1;
		
		if(str1.equals(str2)){
			return 100;
		}
			//TODO implement
		return sim;
	}//stringSimilarity()
	
	/**
	 * Extremely fast way to determine the number of digits in an integer.
	 * @param num
	 * @return The number of digits in the given int.
	 */
	public short getNumDigits(int num){
		if (num < 100000){		//5 or less
			if(num < 100){		//2 or less
				if(num < 10){
					return 1;
				}else{
					return 2;
				}
			}else{					//3, 4 or 5
				if(num < 1000){
					return 3;
				}else{				//4 or 5
					if(num < 10000){
						return 4;
					}else{
						return 5;
					}
				}
			}
		}else{						//6 or more
			if(num < 10000000){	//6 or 7
				if(num < 1000000){
					return 6;
				}else{
					return 7;
				}
			}
			else{					// 8 to 10
				if(num < 100000000){
					return 8;
				}else{				// 9 or 10
					if(num < 1000000000){
						return 9;
					}else{
						return 10;
					}
				}
			}
		}
	}//getNumDigits()
	
	/**
	 * Safely receives an integer from the user.
	 * @return The integer received.
	 */
	public int scanInt(){
		Scanner in = new Scanner(System.in);
		int value;
		
		while(true){
			try{
				value = in.nextInt();
				in.close();
				return value;
			}catch(InputMismatchException ime){
				System.out.println("Please make sure you enter an integer.");
			}finally{ in.nextLine(); }									//Increments the Scanner to prevent a nasty infinite loop.
		}
	}//scanInt()

	/**
	 * Computes the Fibonnaci sequence i times recursively and returns the sum of those values.
	 * 
	 * E.g. fibonacci(5) = 1 + 1 + 2 + 3 + 5 = 12
	 * 		fibonacci(1) = 1
	 * 		fibonacci(0) = 0
	 * 		fibonacci(-1)= 0
	 * 
	 * @param i - The number of Fibonacci numbers to include.
	 * @return The result of the Fibonacci sequence iterated through i times
	 */
	public static int fibonacci(int i){
		if(i < 0){
			return 0;
		}
		return fib(i, 0, 0, 1, 0);
	}//fibonacci()
	
	/**
	 * Private class for recursive operation of the <code>fibonacci()</code> function.
	 * @param i - The number of iterations to perform
	 * @param count - The current number of iterations performed.
	 * @param last - The previous iteration's value.
	 * @param curr - The current iteration's value.
	 * @param val - The current sum of the sequence.
	 * @return The sum of the fibonacci sequence iterated i times.
	 */
	private static int fib(int i, int count, int last, int curr, int val){
		if(count == i){
			return last + val;
		}else{
			return fib(i, count + 1, curr, curr + last, val + last);
		}
	}//fib()
}//General