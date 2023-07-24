package servlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import object.Admin;
import object.Customer;
import sql.Login;
import sql.SelectOneCustomer;
import sql.Update;

/**
 * Servlet implementation class CustomerUpdateServlet
 */
@WebServlet("/CustomerUpdateServlet")
public class CustomerUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 「顧客編集画面」への遷移
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 文字コードの設定
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		// 「編集」リンクから顧客IDを取得
		int id = Integer.parseInt(request.getParameter("id"));

		SelectOneCustomer one_customer = new SelectOneCustomer();
		// リンクで選択された顧客情報を取得する
		Customer customer = null;
		try {
			customer = one_customer.get_One_Customer_Info(id);
		} catch (FileNotFoundException | ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		// 遷移先画面に値を渡す
		request.setAttribute("customer", customer);

		RequestDispatcher dispatcher =
				request.getRequestDispatcher("WEB-INF/jsp/customer_update.jsp");
		dispatcher.forward(request, response);
	}

	// 顧客編集処理
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 文字コードの設定
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String address = request.getParameter("address");

		Update sql = new Update();
		// 顧客情報編集（更新）処理を実行
		try {
			sql.customer_update(name, address, id);
		} catch (FileNotFoundException | ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		// 管理者のセッションを取得
		HttpSession session = request.getSession(true);
		Admin admin = (Admin) session.getAttribute("admin");

		Login login = new Login();
		List<Customer> customer = null;

		// データベースから取得した顧客情報を格納
		try {
			customer = login.getCustomerInfo(String.valueOf(admin.getId()));
		} catch (FileNotFoundException | ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		// 格納した顧客情報を遷移先の画面に渡す
		request.setAttribute("customer", customer);

		RequestDispatcher dispatcher =
				request.getRequestDispatcher("WEB-INF/jsp/customer_list.jsp");
		dispatcher.forward(request, response);
	}
}