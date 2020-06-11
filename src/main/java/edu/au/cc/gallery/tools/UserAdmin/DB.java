package edu.au.cc.gallery.tools.UserAdmin;


import org.postgresql.core.SqlCommand;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import java.io.InputStreamReader;
import java.sql.*;

public class DB {
	private static final String dbURL = "jdbc:postgresql://java-db.cqxj5v5xjbzr.us-east-2.rds.amazonaws.com/name_list";


        public static Connection connection;



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
		DB db = new DB();
		db.connect();


		PreparedStatement stmt = connection.prepareStatement("select * from users");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString(1) + " | " + rs.getString(2) + " | "
					+ rs.getString(3));
		}
		rs.close();
		db.close();
	}

	public static void addUser() throws SQLException, IOException {
		DB db = new DB();
		db.connect();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Please enter the preferred name of the user :> ");
		String prefName = br.readLine();
		System.out.print("Please enter the password for user :> ");
		String password = br.readLine();
		System.out.print("Please enter user full name :> ");
		String fullName = br.readLine();

		Statement stmt = connection.createStatement();
		stmt.executeUpdate("INSERT into users values ('" + prefName + "', '" + password + "', '" + fullName + "')");

		db.close();
	}













	public void close() throws SQLException {
		connection.close();
	}



}


