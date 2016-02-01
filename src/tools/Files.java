package tools;
/**
 * @author Eric Le Fort
 * @version 01
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Files{

	public static void main(String... args){
//		i represents absolute difference in scores.
//		for(int i = 0; i < 100; i++){
//			System.out.println(i + ": " + 2.0*i / (i + 100));
//		}
		
		
	}//main()
	
	/**
	 * Creates a new directory in the path specified.
	 * @param path
	 */
	public static void createDirectory(String path){
		File dir = new File(path);
		if(!dir.isDirectory()){		//If directory doesn't exist, create directory.
			try{
				dir.mkdir();
				System.out.println("Folder created in " + path);
			}catch(Exception e){
				e.getMessage();
			}
		}else{
			System.out.println("The directory " + path + " already exists.");
		}
	}//createDirectory()

	/**
	 * Creates a new file or overwrites a previous file using the given parameters.
	 * @param fileName
	 * @param contents
	 * @param fileType
	 */
	public static void createFile(String filePath, String contents, String fileType){
		File file = new File(filePath + fileType);
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(contents);
			writer.close();
			System.out.println(file.toString() + " successfully written.");
		}catch(IOException ioe){
			ioe.getMessage();
		}
	}//createFile()

	/**
	 * Writes a new .csv file, or overwrites a previous one, using the parameters provided.
	 * @param values
	 * @param fileName
	 */
	public static void createCSVFile(ArrayList<int[]> testValues, String filePath){
		File file = new File(filePath + ".csv");
		String line, newLine = System.getProperty("line.separator");
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for(int i = 0; i < testValues.size(); i++){
				line = "";
				for(int j = 0; j < testValues.get(i).length; j++){
					line += testValues.get(i)[j] + ", ";
				}
				bw.write(line + newLine);
			}
			bw.close();
			System.out.println(file.toString() + " successfully written.");
		}catch(IOException ioe){
			ioe.getMessage();
		}
	}//createCSVFile()

	/**
	 * Reads in line by line data from the specified file, returns it in an ArrayList separated by lines.
	 * @param file: The file to read data from.
	 * @return lines: An ArrayList containing each line from the file.
	 */
	public static ArrayList<String> readFromFile(File file){
		ArrayList<String> lines = new ArrayList<String>();
		try{												//Reads the ids from the text file at the given path into ids.
			Scanner in = new Scanner(new FileReader(file));
			while(in.hasNext()){
				lines.add(in.nextLine());
			}
			in.close();
		}catch(FileNotFoundException fnfe){
			fnfe.getMessage();
		}catch(InputMismatchException ime){
			ime.getMessage();
		}
		return lines;
	}//readFromFile()

	/**
	 * Reads data line-by-line from the input file and creates a series of INSERT INTO statements that are written to the output file.
	 * Function was written to perform on single data point insertions.
	 * Lines with ' character are ignored.
	 * @param inFile - The file to read data from.
	 * @param outFileName - The file to store the results in, can be a new file.
	 * @param database - The database the statements should refer to.
	 * @param tableName - The name of the table to insert into.
	 */
	public static void createSQLInsertionStmtsFile(File inFile, String outFileName, String database, String tableName){
		ArrayList<String> data = readFromFile(inFile);
		String newline = System.getProperty("line.separator"),
				prestmt = "INSERT INTO `" + database + "`.`" + tableName + "` VALUES ('";

		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(outFileName));
			for(int i = 0; i < data.size(); i++){
				data.set(i, Character.valueOf(data.get(i).charAt(0)).toString().toUpperCase() + data.get(i).substring(1));
				if(!data.get(i).contains("'")){
					writer.write(prestmt + data.get(i) + "');" + newline);
				}
			}
			writer.close();
			System.out.println(outFileName + " successfully written.");
		}catch(IOException ioe){
			ioe.getMessage();
		}
	}//createSQLInsertionStatementsFile()

	public static String deleteDuplicateLinesFromSortedArrayList(ArrayList<String> adjectives){
		String last = "", contents = "", newline = System.getProperty("line.separator");
		
		for(int i = 0; i < adjectives.size(); i++){
			
			if(last.equals(adjectives.get(i))){
				System.out.println("Removed " + adjectives.get(i));
				adjectives.remove(i);
			}else{
				contents += adjectives.get(i) + newline;
			}
			
			last = adjectives.get(i);
		}
		return contents;
	}//deleteDuplicateLinesFromSortedArrayList()
	
}//FileCreation
