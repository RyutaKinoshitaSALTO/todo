package config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DBconfig {

	// データベースの接続情報を取得するメソッド
	public Map<String, String>  getDBinfo() throws FileNotFoundException {

		// プロパティファイルのフルパスを指定
		String db_properties_file = "C:/todo/CustomerManagement/DBconfig.properties";

		Properties db_info = new Properties();
		FileInputStream db_file_stream = new FileInputStream(db_properties_file);

		try {
			// プロパティファイルを読み込む
			db_info.load(db_file_stream);
		} catch (IOException e) {
			System.out.println("データベース設定ファイルが認識できませんでした");
			e.printStackTrace();
		}
		//DBconfig.propertiesのキーから値を取得する
		String db_url = db_info.getProperty("url");
		String db_user = db_info.getProperty("user");
		String db_pass = db_info.getProperty("password");

		// 取得したデータベースの接続情報をMapに格納する
		Map<String,String> getDBinfoForMap = new HashMap<>();

		getDBinfoForMap.put("url", db_url);
		getDBinfoForMap.put("user", db_user);
		getDBinfoForMap.put("url", db_pass);

		return getDBinfoForMap;
	}
}