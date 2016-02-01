package SQLTools;
/**
 * Library of methods to be used in order to execute various SQL commands and related tasks.
 * 
 * @author Eric Le Fort
 * @version 1.0
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

//TODO harden against SQLInjection, complete javaDoc.
public class JDBC{
	private final String host, user, pass, database;

	public static void main(String[] args){
		try{
			JDBC connection = new JDBC("jdbc:mysql://localhost:8889/mysql", "root", "root", "mysql");
			
			connection.createTable("JavaStuff",
					new String[]{"ID", "Error"},
					new String[]{"INT NOT NULL", "VARCHAR(255) NOT NULL"},
					new String[]{"PRIMARY KEY (`ID`)"});
			
			connection.insert("JavaStuff", new String[]{"ID", "Error"}, new String[]{"404", "Fucks not found."});
			connection.update("JavaStuff", new String[]{"ID", "Error"}, new String[]{"403", "Fuck found!"}, "`ID`=404");
			connection.delete("JavaStuff", "`ID`=403");
			
			connection.insert("JavaStuff", new String[]{"ID", "Error"}, new String[]{"5", "Eric"});
			connection.update("JavaStuff", new String[]{"Error"}, new String[]{"Eric v2.0"}, "`Error`='Eric'");
			connection.delete("JavaStuff", "`Error`='Eric'");
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}
	}//main()

	/**
	 * Constructs a new JDBCConnection to the specified database on a specified host.
	 * @param host
	 * @param user
	 * @param pass
	 * @param database
	 */
	public JDBC(String host, String user, String pass, String database) throws SQLException{
		this.host = host;
		this.user = user;
		this.pass = pass;
		this.database = database;
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());			//Creates and registers an instance of Driver.
	}//Constructor

	/**
	 * Performs a basic check and returns whether the connection is successfully established.
	 * @return true if the connection is stable, false otherwise.
	 */
	public boolean testConnection(){
		Connection connection = null;

		try{
			connection = DriverManager.getConnection(host,user,pass);		//Create connection, with credentials

			connection.close();
			return true;
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}
		return false;
	}//testConnection()

	/**
	 * Creates a new table in the database with the provided columns and their matching datatypes as well as any necessary keys.
	 * @param table - The table to apply the command to.
	 * @param columns
	 * @param dataTypes
	 * @param keys
	 * @return Whether the command completed successfully or not.
	 */
	public boolean createTable(String table, String[] columns, String[] dataTypes, String[] keys){
		String command = "CREATE TABLE `" + database + "`.`" + table + "` (";
		if(columns.length == 0 || dataTypes.length != columns.length){
			return false;
		}

		command += "`" + columns[0] + "` " + dataTypes[0];
		for(int i = 1; i < dataTypes.length; i++){
			command += ",`" + columns[i] + "` " + dataTypes[i];
		}
		for(int i = 0; i < keys.length; i++){
			command += "," + keys[i];
		}
		command += ");";

		return executeUpdateCommand(command);
	}//createTable()

	/**
	 * Drops the specified table from the database.
	 * @param table - The name of the table you wish to drop.
	 * @return Whether the command completed successfully or not.
	 */
	public boolean dropTable(String table){
		return executeUpdateCommand("DROP TABLE `" + database + "`.`" + table + "`;");
	}//dropTable()

	/**
	 * Completes an UPDATE command on the table according to the provided parameters and returns true if the command was
	 * successfully executed and false otherwise.
	 * @param table - The table to apply the command to.
	 * @param columns - The columns which are having values changed.
	 * @param newValues - The new value to be stored in the matching column.
	 * @param whereClause - The boolean clause at the end of the statement or null if you wish 
	 * @return Whether the command completed successfully or not.
	 */
	public boolean update(String table, String[] columns, String[] newValues, String whereClause){
		String command = "UPDATE `" + database + "`.`" + table + "`" + " SET ";
		if(columns.length == 0 || columns.length != newValues.length){
			return false;
		}

		command += columns[0]+ "='" + newValues[0] + "'";
		for(int i = 1; i < columns.length; i++){
			command += ",`" + columns[i] + "`='" + newValues[i] + "'";
		}

		if(whereClause != null){
			command += " WHERE " + whereClause + ";";
		}else{
			command += ";";
		}

		return executeUpdateCommand(command);
	}//update()

	/**
	 * Completes an INSERT command on the table according to the provided parameters and returns true if the command was
	 * successfully executed and false otherwise.
	 * @param table - The table to apply the command to.
	 * @param columns - The columns to be inserted into.
	 * @param values - The values to be inserted into the corresponding column.
	 * @return Whether the command completed successfully or not.
	 */
	public boolean insert(String table, String[] columns, String[] values){
		String command = "INSERT INTO `"+ database + "`.`" + table + "` (";

		if(columns.length == 0 || values.length != columns.length){
			return false;
		}

		command += "`" + columns[0] + "`";
		for(int i = 1; i < columns.length; i++){
			command += ",`" + columns[i] + "`";
		}

		command += ") VALUES ('" + values[0] + "'";
		for(int i = 1; i < values.length; i++){
			command += ",'" + values[i] + "'";
		}
		command += ");";

		return executeUpdateCommand(command);
	}//insert()

	/**
	 * Completes a DELETE FROM command on the table according to the provided parameters and returns true if the command was
	 * successfully executed and false otherwise.
	 * @param table
	 * @param whereClause
	 * @return Whether the command completed successfully or not.
	 */
	public boolean delete(String table, String whereClause){
		return executeUpdateCommand("DELETE FROM `" + database + "`.`" + table + "` WHERE " + whereClause);
	}//delete()

	/**
	 * Used to execute CREATE, DROP, UPDATE, INSERT and DELETE commands.
	 * @param command - SQL Statement to be processed.
	 * @return boolean - True if statement executed, False is not.
	 */
	public boolean executeUpdateCommand(String command){
		Connection connection = null;
		Statement statement = null;

		try{
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());			//Create instance of Driver
			connection = DriverManager.getConnection(host, user, pass);	//Create connection, with credentials
			statement = connection.createStatement();
			statement.executeUpdate(command);

			statement.close();
			connection.close();
			return true;
		}catch(SQLException sqle){
			System.out.println(sqle.getMessage());
		}

		return false;
	}//update()

	/**
	 * Performs a query on the database.
	 * @param query - Query to be processed.
	 * @return A 2-D array containing all results of the given query.
	 */
	public String[][] executeQuery(String query) {
		ArrayList<String[]> tuplesOut = new ArrayList<String[]>();
		Connection connection = null;
		Statement statement = null;
		ResultSet tuplesIn = null;
		ResultSetMetaData meta = null;

		try{
			connection = DriverManager.getConnection(host, user, pass);			//Creates connection with credentials
			statement = connection.createStatement();
			tuplesIn = statement.executeQuery(query);							//Retrieve Tuples from query
			meta = tuplesIn.getMetaData();										//Some Meta Data regarding the Tuples returned

			statement.close();
			connection.close();

			do{
				tuplesOut.add(new String[meta.getColumnCount()]);
				Arrays.fill(tuplesOut.get(tuplesOut.size() - 1), "N/A");		//Initialize this row's values to N/A.

				for(int i = 0; i > meta.getColumnCount(); i++){					//Grab query results for each column.
					tuplesOut.get(tuplesOut.size() - 1)[i] = tuplesIn.getString(i);
				}						
			}while(tuplesIn.next());

			return (String[][])tuplesOut.toArray();
		}catch(SQLException sqle){
			System.out.println(sqle.getMessage());
		}

		return null;
	}//executeQuery
	
}//JDBC
