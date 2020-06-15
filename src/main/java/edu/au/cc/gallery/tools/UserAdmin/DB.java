package edu.au.cc.gallery.tools.UserAdmin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import org.json.JSONArray;
import org.json.JSONObject;


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
			JSONObject secret = getSecret();
			connection = DriverManager.getConnection(dbURL, "admins", getPassword(secret));
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


//// Use this code snippet in your app.
//// If you need more information about configurations or implementing the sample code, visit the AWS docs:
//// https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/java-dg-samples.html#prerequisites
//
//	public static void getSecret() {
//
//		String secretName = "sec-image-gallery-user-admin";
//		String region = "us-east-2";
//
//		// Create a Secrets Manager client
//		AWSSecretsManager client  = AWSSecretsManagerClientBuilder.standard()
//				.withRegion(region)
//				.build();
//
//		// In this sample we only handle the specific exceptions for the 'GetSecretValue' API.
//		// See https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
//		// We rethrow the exception by default.
//
//		String secret, decodedBinarySecret;
//		GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
//				.withSecretId(secretName);
//		GetSecretValueResult getSecretValueResult = null;
//
//		try {
//			getSecretValueResult = client.getSecretValue(getSecretValueRequest);
//		} catch (DecryptionFailureException e) {
//			// Secrets Manager can't decrypt the protected secret text using the provided KMS key.
//			// Deal with the exception here, and/or rethrow at your discretion.
//			throw e;
//		} catch (InternalServiceErrorException e) {
//			// An error occurred on the server side.
//			// Deal with the exception here, and/or rethrow at your discretion.
//			throw e;
//		} catch (InvalidParameterException e) {
//			// You provided an invalid value for a parameter.
//			// Deal with the exception here, and/or rethrow at your discretion.
//			throw e;
//		} catch (InvalidRequestException e) {
//			// You provided a parameter value that is not valid for the current state of the resource.
//			// Deal with the exception here, and/or rethrow at your discretion.
//			throw e;
//		} catch (ResourceNotFoundException e) {
//			// We can't find the resource that you asked for.
//			// Deal with the exception here, and/or rethrow at your discretion.
//			throw e;
//		}
//
//		// Decrypts secret using the associated KMS CMK.
//		// Depending on whether the secret is a string or binary, one of these fields will be populated.
//		if (getSecretValueResult.getSecretString() != null) {
//			secret = getSecretValueResult.getSecretString();
//		}
//		else {
//			decodedBinarySecret = new String(Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array());
//		}
//
//		// Your code goes here.
//	}


