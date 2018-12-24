package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class test {
	public static void main(String[] args) {
		String insertQuery = "INSERT INTO Items (name, type, color, design) VALUES ('Blue baddy','Shirt','blue','striped');";
		Database db = new Database("50.62.209.17:3306","example","noah_dev","root123");
		//db.executeQuery("SELECT * FROM Items;",true);
		String[] arr = {"username","email","password"};
		//char[] type = {'v','v','v'};
		//db.createTable("secondusers", 3, "username,email,password","vvv" );
		db.insertIntoTable("Items", "name,type,color,design","Black Jeans,Pants,Black,Plain" );
		//System.out.println(db.tableContains("Items", "color", "blue"));
		String table_name = "Items";
		String col = "name";
		String condition = "Black Jean";
		String query = "SELECT FROM " + table_name + " WHERE " + col + "= '" + condition + "';";
		db.executeQuery(query, true);
	}
}
