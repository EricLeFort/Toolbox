package mathTools;
/**
* @author Eric Le Fort
* @version 01
*/
import java.util.Arrays;
import java.util.Scanner;

public class MinimaFinding{
	
	public static void main(String[] args){
		double[][] x = {
				{1, 1},
				{1, 2},
				{2, 1}
			};
		
		double x1 = 9524;
		double a = (((((x1*1.05 - 2000)*1.05 - 2100)*1.05 - 2205)*1.05 - 2315.25)*1.05 - 2431.01);
		System.out.println(a);
		
		//new MinimaFinding().goldenLineSearch(0, 1, 0, 1, 1, 2, 0.15);
		new MinimaFinding().nelderMead(x, 0.5, 2.0/3, 2, 0.1);
	}//main()
	
	/**
	 * Computes the minimum of the function in the nested Function class over the range specified.
	 * The current function is log((x2 - x1^2)^2)) + (1 - x1)^2.
	 * @param a - The lower starting bound.
	 * @param b - The upper starting bound.
	 * @param x1 - The first value of the starting point.
	 * @param x2 - The second value of the starting point.
	 * @param d1 - The first value of the direction.
	 * @param d2 - The second value of the direction.
	 * @param epsilon - The stopping criteria for the algorithm.
	 * @return The final range.
	 */
	public double[] goldenLineSearch(double a, double b, double x1, double x2, double d1, double d2, double epsilon){
		Function f1, f2;
		double aNew, bNew;
		while(b - a > epsilon){
			aNew = a + 0.382*(b-a);			//Sets the new boundaries to check.
			bNew = a + 0.618*(b-a);
			
			f1 = new Function(new double[]{x1 + aNew*d1, x2 + aNew*d2});
			f2 = new Function(new double[]{x1 + bNew*d1, x2 + bNew*d2});
			
			if(f1.compareTo(f2) < 0){
				b = bNew;
			}else if(f1.compareTo(f2) > 0){
				a = aNew;
			}else{
				a = aNew;
				b = bNew;
			}
			System.out.println("[" + a + ", " + b + "]");
		}
		
		return new double[]{a, b};
	}//goldenLineSearch()
	
	/**
	 * Computes the minimum of the function in the nested Function class using the Nelder-Mead algorithm.
	 * The current function is log((x2 - x1^2)^2)) + (1 - x1)^2.
	 * @param x - The set of points to start with.
	 * @param alpha - The damping factor.
	 * @param beta - The contraction factor.
	 * @param gamma - The extension factor.
	 * @param epsilon - The acceptable error for a final answer.
	 * @return The final values this method arrives at.
	 */
	public double[][] nelderMead(double[][] x, double alpha, double beta, double gamma, double epsilon){
		Scanner sc = new Scanner(System.in);
		Function xResults[] = new Function[x.length],
				xRResult, xCResult, xEResult;
		double tempX[][], xBar[], xR[], xC[], xE[], xNew[], sum;
		int n = x.length - 1, numDim = x[0].length;				//numDim is how many variables in vector, n is last point's index.
		boolean skip;
		
		if(x.length > 2 && numDim > 0){							//Initialize variable points.
			tempX = new double[n + 1][numDim];
			xBar = new double[numDim];
			xR = new double[numDim];
			xC = new double[numDim];
			xE = new double[numDim];
			xNew = new double[numDim];
		}else{
			sc.close();
			throw new IllegalArgumentException("There were either not enough points provided or those points were empty.");
		}
		
		for(int i = 0; i < x.length; i++){						//Initialize results of function.	
			xResults[i] = new Function(x[i]);		
		}
		
		while(true){
			skip = false;
			Arrays.sort(xResults);	//Sort points
			
			xBar = new double[numDim]; 							//Calculate centroid (xBar) and reflection (xR)
			for(int i = 0; i < numDim; i++){
				sum = 0;
				for(int j = 0; j < n; j++){
					sum += xResults[j].getX()[i];				//Adds all points except x_n.
				}
				
				xBar[i] = sum / n;
				xR[i] = 2 * xBar[i] - xResults[n].getX()[i];
			}
			xRResult = new Function(xR);
																//Steps to determine where xR lies in your points
			if(xRResult.compareTo(xResults[0]) < 0){			//Step 2a: f(xR) < f(x_O)
				skip = true;
				
				for(int i = 0; i < numDim; i++){				//Compute xE
					xE[i] = xBar[i] + gamma * (xBar[i] - xResults[n].getX()[i]);
				}
				xEResult = new Function(xE);
				
				if(xEResult.compareTo(xRResult) <= 0){
					xResults[n] = xEResult;
				}else{
					xResults[n] = xRResult;
				}
			}else if(xRResult.compareTo(xResults[0]) >= 0 &&	//Step 2b: f(x_0) <= f(xR) < f(x_n-1)
					xRResult.compareTo(xResults[n-1]) < 0){
				skip = true;
				
				xResults[n] = xRResult;
			}else if(xRResult.compareTo(xResults[n-1]) >= 0 &&	//Step 2c: f(x_n-1) <= f(xR) < f(x_n)
					xRResult.compareTo(xResults[n]) < 0){
				for(int i = 0; i < numDim; i++){
					xC[i] = xBar[i] + beta * (xBar[i] - xResults[n].getX()[i]);
				}
				xCResult = new Function(xC);
				
				if(xCResult.compareTo(xResults[n-1]) <= 0){
					skip = true;
					xResults[n] = xCResult;
				}
			}else{												//Step 2d: f(x_n) <= f(xR)
				for(int i = 0; i < numDim; i++){
					xC[i] = xBar[i] - beta * (xBar[i] - xResults[n].getX()[i]);
				}
				xCResult = new Function(xC);
				
				if(xCResult.compareTo(xResults[n-1]) <= 0){
					skip = true;
					xResults[n] = xCResult;
				}
			}
			
			if(!skip){											//Goes straight to beginning if we were meant to GOTO 1 earlier.
				for(int i = 0; i < x.length; i++){				//populate temp x using current x values.
					tempX[i] = xResults[i].getX();
				}
				
				if(euclideanDistance(xResults[n].getX(), xResults[0].getX()) < epsilon ||
						findGreatestDistance(tempX) < epsilon){	//Remaining points give a close enough range, return results.
					break;
				}
				
				for(int i = 1; i < x.length; i++){				//Contract the simplex to x_0.
					for(int j = 0; j < numDim; j++){
						xNew[j] = xResults[0].getX()[j] + alpha *(xResults[i].getX()[j] - xResults[0].getX()[j]);
					}
					xResults[i] = new Function(xNew);
				}
			}
			
			printValues(xResults);
			sc.nextLine();
		}
		
		sc.close();
		return x;
	}//nelderMead
	
	public static void printValues(Function[] xResults){
		for(int i = 0; i < xResults.length; i++){
			System.out.print("x_" + i + ": (");
			for(int j = 0; j < xResults[0].getX().length - 1; j++){
				System.out.print(xResults[i].getX()[j] + ", ");
			}
			
			System.out.print(xResults[i].getX()[xResults[i].getX().length - 1] + ")");
			System.out.println(" = " + xResults[i].getResult());
		}
	}//printValues()
	
	/**
	 * Computes the Euclidean distance between the two points passed in.
	 * @param a - The first point.
	 * @param b - The second point.
	 * @return The distance between points a and b.
	 */
	public static double euclideanDistance(double[] a, double[] b){
		double sum = 0;
		for(int i = 0; i < Math.max(a.length, b.length); i++){
			sum += Math.pow(a[i] - b[i], 2);
		}
		return Math.sqrt(sum);
	}//euclideanDistance()

	/**
	 * Given a set of n-dimensional points, returns the distance of the two points farthest apart from one another.
	 * @param points - The points to check distances of.
	 * @return The Euclidean difference of the two furthest points.
	 */
	public static double findGreatestDistance(double[][] points){
		double greatestDist = 0, currentDist;
		for(int i = 0; i < points.length - 1; i++){
			for(int j = i; j < points.length; j++){
				currentDist = euclideanDistance(points[i], points[j]);
				if(currentDist > greatestDist){
					greatestDist = currentDist;
				}
			}
		}
		return greatestDist;
	}//findGreatestDistance()
	
	private class Function implements Comparable<Function>{
		private double x[], result;
		
		public Function(double[] x){
			double a, b;
			this.x = x;
			a = Math.pow(x[0] - 1, 2) + Math.pow(x[1] - 3, 2);
			b = Math.pow(x[0] - 1, 2) + Math.pow(x[1] - 4, 2); 
			result = Math.min(a, b);
			
//			result = Math.log(Math.pow(x[1] - Math.pow(x[0], 2), 2)) + Math.pow(1-x[0], 2);
		}//Constructor
		
		public double getResult(){ return result; }//getResult()

		public double[] getX(){ return x; }//getX()
		
		public int compareTo(Function f){
			if(result < f.getResult()){
				return -1;
			}else if(result > f.getResult()){
				return 1;
			}else{
				return 0;
			}
		}
	}//Function
}//MinimaFinding
