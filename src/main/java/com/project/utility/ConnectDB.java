package com.project.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.project.pages.CodeBase;

/**
 * @author Salman Saeed
 * @email salmansaeed321@hotmail.com
 * @version 3.1
 **/

public class ConnectDB extends CodeBase {

	public static String connectDb(String exeQuery) {
		Connection con = null;
		String dbData = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://project-stage-web.c0buomajylmq.eu-west-3.rds.amazonaws.com:3306/project_v5_prod",
					"salman", "8werq90pDa9CGa4UbKXn8YN");
			Statement stmt = con.createStatement();
			String query = exeQuery;

			boolean status = stmt.execute(query);
			if (status) {

				ResultSet rs = stmt.getResultSet();
				while (rs.next()) {
					for (int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) {
						// System.out.println("");

						// System.out.print(rs.getMetaData().getColumnName(i) +
						// "===" + rs.getObject(i));
						dbData = (String) rs.getObject(i);
					}
					// System.out.println("");
					// System.out.println("Total Columns:
					// "+rs.getMetaData().getColumnCount());
				}
				// System.out.println(dbData);
				rs.close();

			} else {
				// query can be of update or any query apart from select query
				int count = stmt.getUpdateCount();
				System.out.println("Total records updated: " + count);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception ex) {
			}
		}
		return dbData;
	}

	// message format that we get from db: Your Application Login PIN is 2956.
	public static String getOTP(String phoneNumber) {

		String getMsg = connectDb("SELECT `text` FROM `sms_sent` WHERE `phone_no` = " + phoneNumber
				+ " AND `created_datetime` = (SELECT MAX(`created_datetime`) FROM `sms_sent` WHERE `phone_no` = "
				+ phoneNumber + ")");
		System.out.println(getMsg);
		String otp = getMsg.replaceAll("\\D+", "");
		return otp;
	}
}