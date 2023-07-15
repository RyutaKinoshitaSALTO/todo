package sql;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import object.Admin;
import object.Customer;

public class Login {

	public Admin check(String admin_id, String password) throws FileNotFoundException, ClassNotFoundException {

		// データベースへの接続情報をプロパティファイルから取得
//		DBconfig db_info = new DBconfig();
		Connection conn = null;
		String url ="jdbc:mysql://localhost:3306/customer_management_db";
		String user ="root";
		String pass = "05Ryuta59mosare";

		// 実行SQL
		String login_sql = "select * from admin_tb "
				+ "where admin_id = ? and password = ?;";
		// 管理者のオブジェクトを作成
		Admin admin = new Admin();

		// データベースへの接続
		// try〜catch〜resources構文を使用
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url,user,pass);
			PreparedStatement stmt = conn.prepareStatement(login_sql);

			// 変数login_sqlの一番目の?に引数のuser_idをセット
			stmt.setString(1, admin_id);
			// 変数login_sqlの二番目の?に引数のpasswordをセット
			stmt.setString(2, password);

			// SQLを実行し、結果を取得
			ResultSet rs = stmt.executeQuery();

			// データベースから取得した値をAdminオブジェクトに格納
			// 値がなければ、login_flag（false）のみ格納
			if(rs.next()) {
				admin.setId(rs.getInt("admin_id"));
				admin.setName(rs.getString("name"));
				admin.setPassword(rs.getString("password"));
				admin.setLogin_flag(true);
			} else {
				admin.setLogin_flag(false);
			}
		} catch (SQLException e) {
			System.out.println("データベースとの接続を閉じます");
			e.printStackTrace();
		}
		// データベースから取得した値を返す
		return admin;
	}

	// ログイン成功後に管理者が管理する顧客情報の取得
	public List<Customer> getCustomerInfo(String admin_id) throws FileNotFoundException, ClassNotFoundException {

		// データベースへの接続情報をプロパティファイルから取得
		Connection conn = null;
		String url ="jdbc:mysql://localhost:3306/customer_management_db";
		String user ="root";
		String pass = "05Ryuta59mosare";

		// 実行SQL
		// admin_id(管理者ID)で該当する顧客情報を取得する
		String customer_sql = "select * from customer_tb "
				            + "where admin_id = ?;";

		// 顧客情報のデータを格納するListを作成
		List<Customer> cus_list = new ArrayList<Customer>();

		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url,user,pass);
			PreparedStatement stmt = conn.prepareStatement(customer_sql);

			stmt.setString(1, admin_id);
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				// 顧客情報用のオブジェクトを作成
				Customer cus_info = new Customer();
				// オブジェクトにデータを一時格納
				cus_info.setCustomer_id(rs.getInt("customer_id"));
				cus_info.setAdmin_id(rs.getInt("admin_id"));
				cus_info.setName(rs.getString("name"));
				cus_info.setAddress(rs.getString("address"));
				cus_info.setRegistered_time(rs.getDate("registered_time"));
				cus_info.setUpdated_time(rs.getDate("updated_time"));

				// オブジェクトに格納された
				// 顧客情報用のデータをリストに加える
				cus_list.add(cus_info);
			}
		} catch (SQLException e) {
			System.out.println("データベースとの接続を閉じます");
			e.printStackTrace();
		}
		return cus_list;
	}
}