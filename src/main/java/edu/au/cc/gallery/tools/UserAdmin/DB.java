package edu.au.cc.gallery.tools.UserAdmin;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class DB {
	private static final String dbURL = "jdbc:postgresql://image-gallery.cqxj5v5xjbzr.us-east-2.rds.amazonaws.com/image_gallery";
	public static Connection connection;





	private JSONObject getSecret() {
		String s = Secrets.getSecretImageGallery();
		return new JSONObject(s);
	}

	private String getPassword(JSONObject secret) {
		return secret.getString("password");
	}

	public void connect() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
			JSONObject secret = getSecret();
			connection = DriverManager.getConnection(dbURL, "admins", getPassword(secret));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
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
		String user_name = br.readLine();
		System.out.print("Please enter the password for user :> ");
		String password = br.readLine();
		System.out.print("Please enter user full name :> ");
		String full_name = br.readLine();

		Statement stmt = connection.createStatement();
		stmt.executeUpdate("INSERT into users values ('" + user_name + "', '" + password + "', '" + full_name + "')");
		db.close();
	}

	public static void editUser() throws SQLException, IOException {
		DB db = new DB();
		db.connect();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		listUsers();
		System.out.print("Please enter the preferred name of the user :>");
		String prefName = br.readLine();

		PreparedStatement stmt = connection.prepareStatement("select '" + prefName + "' from users");
		ResultSet rs = stmt.executeQuery();
		String prefNameEdit = null, passWordEdit = null, fullNameEdit = null;
		while (rs.next()) {
			prefNameEdit = rs.getString(1);
			passWordEdit = rs.getString(2);
			fullNameEdit = rs.getString(3);
		}
		rs.close();
		System.out.println(prefNameEdit + " | " + passWordEdit + " | "+ fullNameEdit);
		int editOption = 0;

		while (editOption != 4) {
			System.out.println("1 ) Preferred Name");
			System.out.println("2 ) Password");
			System.out.println("3 ) Full Name");
			System.out.println("4 ) Done Editing");
			System.out.println("Please select the field to edit :> ");
			editOption = Integer.parseInt(br.readLine());

			if (editOption == 1) {
				System.out.println("Current preferred name :>" + prefNameEdit);
				System.out.print("Please enter new preferred name :> ");
				prefNameEdit = br.readLine();
			} else if (editOption == 2) {
				System.out.println("Current password :> " + passWordEdit);
				System.out.print("Please enter new password :> ");
				passWordEdit = br.readLine();
			} else if (editOption == 3) {
				System.out.print("Current full name :>" + fullNameEdit);
				fullNameEdit = br.readLine();
			} else {
				editOption = 4;
			}
		}
		Statement updateStmt = connection.createStatement();
		updateStmt.executeUpdate("update users " +
				"set user_name = '" + prefNameEdit +"', set password = '" + passWordEdit +"', set full_name = '" + fullNameEdit + "' where user_name = '" +  prefName + "')");
		db.close();
	}

	public static void deleteUser() throws SQLException, IOException {
		listUsers();
		DB db = new DB();
		db.connect();
		System.out.print("Please select enter the preferred name of the user you would like to delete :>");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String prefName = br.readLine();

		Statement deleteStmt = connection.createStatement();
		deleteStmt.executeUpdate("delete from users where user_name = '" + prefName + "'");
		db.close();
	}

	public void close() throws SQLException {
		connection.close();
	}
}



