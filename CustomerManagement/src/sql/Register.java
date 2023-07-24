package sql;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register {

	public void customer_register(int admin_id, String name, String address) throws FileNotFoundException, ClassNotFoundException {

		// データベースへの接続情報をプロパティファイルから取得
		Connection conn = null;
		String url ="jdbc:mysql://localhost:3306/customer_management_db";
		String user ="root";
		String pass = "05Ryuta59mosare";


		// 実行SQL
		String register_sql = "insert into customer_tb"
				+ "(admin_id, name, address) values(?,?,?)";

		// データベースへの接続
		// try〜catch〜resources構文を使用
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url,user,pass);
			// オートコミット機能を無効化
			conn.setAutoCommit(false);

			try(PreparedStatement stmt = conn.prepareStatement(register_sql)){
				// 変数register_sqlの一番目の?にdmin_idをセット
				stmt.setInt(1, admin_id);
				// 変数register_sqlの一番目の?にnameをセット
				stmt.setString(2, name);
				// 変数register_sqlの一番目の?にaddressをセット
				stmt.setString(3, address);
				 // SQLの実行
				stmt.executeUpdate();

				// コミット
				conn.commit();
				System.out.println("コミット処理を行いました");
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