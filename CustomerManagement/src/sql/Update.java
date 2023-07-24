package sql;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Update {

	public  void customer_update(String name, String address, int customer_id) throws FileNotFoundException, ClassNotFoundException {

		// データベースへの接続情報をプロパティファイルから取得
		Connection conn = null;
		String url ="jdbc:mysql://localhost:3306/customer_management_db";
		String user ="root";
		String pass = "05Ryuta59mosare";

		// 実行SQL
		String update_sql = "update customer_tb "
				+ "set name = ?, address = ? where customer_id = ?;";

		// データベースへの接続
		// try〜catch〜resources構文を使用
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url,user,pass);
			// オートコミット機能を無効化
			conn.setAutoCommit(false);

			try(PreparedStatement stmt = conn.prepareStatement(update_sql)){
				// 変数update_sqlの一番目の?にnameをセット
				stmt.setString(1, name);
				// 変数update_sqlの二番目の?にaddressをセット
				stmt.setString(2, address);
				// 変数update_sqlの三番目の?にcustomer_idをセット
				stmt.setInt(3, customer_id);
				// SQLの実行
				stmt.executeUpdate();
				//コミット
				conn.commit();
				System.out.println("更新処理が成功しました");
			} catch (SQLException e) {
				conn.rollback();
				System.out.println("ロールバック処理を行いました");
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}