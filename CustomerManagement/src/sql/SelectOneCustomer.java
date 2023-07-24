package sql;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import object.Customer;

public class SelectOneCustomer {

	public Customer get_One_Customer_Info(int customer_id) throws FileNotFoundException, ClassNotFoundException {
		// データベースへの接続情報をプロパティファイルから取得
		Connection conn = null;
		String url ="jdbc:mysql://localhost:3306/customer_management_db";
		String user ="root";
		String pass = "05Ryuta59mosare";

		// 該当する顧客情報のオブジェクトを作成
		Customer one_customer = new Customer();

		// 実行SQL
		// customer_id（顧客ID）で該当する顧客情報を取得する
		String one_customer_sql = "select * from customer_tb where customer_id = ?;";

		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url,user,pass);
			PreparedStatement stmt = conn.prepareStatement(one_customer_sql);
			

			stmt.setInt(1, customer_id);
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				//オブジェクトにデータを一時格納
				one_customer.setCustomer_id(rs.getInt("customer_id"));
				one_customer.setName(rs.getString("name"));
				one_customer.setAddress(rs.getString("address"));
			}
		} catch (SQLException e) {
			System.out.println("データベースとの接続を閉じます");
			e.printStackTrace();
		}
		return one_customer;
	}
}