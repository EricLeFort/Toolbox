package mathTools;
/**
* @author Eric Le Fort
* @version 01
*/
import java.util.Arrays;

public abstract class MinimaFinding{
	
	/**
	 * Computes the minimum of the function log((x2 - x1^2)^2)) + (1 - x1)^2 over the range specified.
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
	
	public double[][] nelderMead(double[][] x, double alpha, double beta, double gamma, double epsilon){
		Function xResults[] = new Function[x[0].length],
				xRResult, xCResult, xEResult;
		double xBar[], xR[], xC[], xE[], xNew[], sum;
		int n = x.length - 1, numDim = x[0].length;				//numDim is how many variables in vector, n is last point's index.
		boolean skip;
		
		if(x.length > 2 && numDim > 0){							//Initialize variable points.
			xBar = new double[numDim];
			xR = new double[numDim];
			xC = new double[numDim];
			xE = new double[numDim];
			xNew = new double[numDim];
		}else{
			throw new IllegalArgumentException("There were either not enough points provided or those points were empty.");
		}
		
		for(int i = 0; i < x.length; i++){						//Initialize results of function.	
			xResults[i] = new Function(x[i]);		
		}
		
		while(true){
			skip = false;
			Arrays.sort(xResults);								//Sort points
			
			xBar = new double[numDim]; 							//Calculate centroid (xBar) and reflection (xR)
			for(int i = 0; i < numDim; i++){
				sum = 0;
				
				for(int j = 0; i < n; j++){
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
				if(euclideanDistance(xResults[n].getX(), xResults[0].getX()) < epsilon ||
						findGreatestDistance(x) < epsilon){		//Remaining points give a close enough range, return results.
					break;
				}
				
				for(int i = 1; i < x.length; i++){				//Contract the simplex to x_0.
					for(int j = 0; j < numDim; j++){
						xNew[j] = xResults[0].getX()[j] + alpha *(xResults[i].getX()[j] - xResults[0].getX()[j]);
					}
					xResults[i] = new Function(xNew);
				}
			}
		}
		
		return x;
	}//nelderMead
	
	/**
	 * Computes the Euclidean distance between the two points passed in.
	 * @param a - The first point.
	 * @param b - The second point.
	 * @return The distance between points a and b.
	 */
	public double euclideanDistance(double[] a, double[] b){
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
	public double findGreatestDistance(double[][] points){
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
			this.x = x;
			fun();
		}//Constructor
		
		/**
		 * Evaluates the function contained at the input points.
		 * Currently, the interior function is log((x2 - x1^2)^2)) + (1 - x1)^2.
		 * @param x1 - The first input value.
		 * @param x2 - The second input value.
		 * @return The result of the function.
		 */
		private void fun(){ result = Math.log(Math.pow(x[1] - Math.pow(x[0], 2), 2)) + Math.pow(1-x[0], 2); }
		
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
