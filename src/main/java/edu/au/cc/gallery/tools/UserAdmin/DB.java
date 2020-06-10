package edu.au.cc.gallery.tools.UserAdmin;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB {
	private static final String dbURL = "jdbc:postgresql://java-db.cqxj5v5xjbzr.us-east-2.rds.amazonaws.com/name_list";


        private static Connection connection;



	private String getPassword() {
		try(BufferedReader br = new BufferedReader(new FileReader("/home/ec2-user/.sql-passwd"))) {
		String result = br.readLine();
		return result;
	} catch (IOException ex) {
		ex.printStackTrace();
		System.exit(1);
		}
	return null;
	}


	public void connect() throws SQLException {
		try {
			connection = DriverManager.getConnection(dbURL, "admin", getPassword());
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.exit(1);
		}


	}


	public static void demo() throws Exception {
		DB db = new DB();
		db.connect();
		System.out.println("Connection Established.");
	}


	public static void listUsers() throws SQLException {
	
        try (PreparedStatement stmt = connection.prepareStatement("select * from users")) {
          	 ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString(1) + "\t\t|\t\t"
                    + rs.getString(2) + "\t\t|\t\t"
                    + rs.getString(3));
       			 }
        rs.close();
 	  	 }

	}
}


