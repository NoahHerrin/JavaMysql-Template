package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	private Connection C;
	private Statement S;
	private ResultSet R;

	public Database(String address, String db_name, String usr, String pwd) {
		//Check that credentials are accurate, if they are not throw IllegalArgumentException
		try {

			address = "jdbc:mysql://" + address + "/" + db_name;
			C = DriverManager.getConnection(address, usr, pwd);
			System.out.println("Connection Established");
			S = C.createStatement();
		} catch(Exception e) {

			System.out.println("Connection Failed");
			throw new IllegalArgumentException();
		}
	}

	public boolean executeQuery(String query, boolean print_results) {
		try {
			R = null;
			if(S.execute(query)) {
				R = S.getResultSet();
				if(print_results) printResults();
				return true;
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return false;
	}
	
	public boolean tableContains(String table_name, String col, String condition) {
		String query = "SELECT FROM " + table_name + " WHERE " + col + "= '" + condition + "';";
		executeQuery(query,false);
		if(toString() == null) {
			System.out.println("yay");
		} else {
			System.out.println("nay");
		}
		return true;
	}
	public void insertIntoTable(String tableName, String col_names, String col_values) {
		
		String[] names = col_names.split(",");
		String[] values = col_values.split(",");
		String query = "INSERT INTO (";
				for(int i = 0; i < names.length; i++ ) {
					query += "'" + names[i] + "'";
					if(i != names.length - 1) query += ", ";
				}
		query += ") VALUES (";
		for(int i = 0; i < values.length; i++ ) {
			query += "'" + values[i] + "'";
			if(i != values.length - 1) query += ", ";
		}
		query += ");";
		executeQuery(query,false);
	}
	public void createTable(String name, int columns, String col_names, String col_type) {
		//Make sure all parameters are legal
		String[] column_names = col_names.split(",");
		if(columns == column_names.length && columns == col_type.length()) {
			String query = "CREATE TABLE " + name + " (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, ";
					for(int i = 0; i < columns; i++) {
						query += column_names[i] + " " +  pickType(col_type.charAt(i));
						if(i != (columns - 1)) query += ", ";
					}
					query += ");";
					executeQuery(query,false);
		}
	}
	public void removeFromTable(String tableName, String column, String condition) {
		String query = "DELETE FROM " + tableName + " WHERE " + column + "=" + condition + ";";
		executeQuery(query,false);
	}
	private String pickType(char type) {
		switch(type) {
		case 'v':
			return "VARCHAR(255)";
		case 'd':
			return "Date";
		case 'c':
			return "CHAR(1)";
		case 'b':
			return "BOOLEAN";
		case 't':
			return "TIME";
		case 'i':
			return "INTEGER";
		}
		throw new IllegalArgumentException();
	}
	public void printResults() {
		int columns;
		try {
			columns = R.getMetaData().getColumnCount();
			while(R.next()) {
				for(int i = 1; i <=columns; i++) {
					System.out.print(R.getString(i) + " ");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			/*
			 * There were no results
			 */
		}

	}
	
	public String toString() {
		int columns;
		String s = "";
		try {
			columns = R.getMetaData().getColumnCount();
			while(R.next()) {
				for(int i = 1; i <=columns; i++) {
					s += R.getString(i) + " ";
				}
				s += "\n";
				return s;
			}
		} catch (SQLException e) {
			/*
			 * There were no results
			 */
		}
		return null;
	}

}
