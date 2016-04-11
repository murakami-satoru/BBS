package BBS.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import BBS.beans.Users;
import BBS.service.LoginService;

//@WebServlet(urlPatterns= { "/login" })
public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		String loginId = request.getParameter("login_id");
		String password = request.getParameter("password");

		LoginService loginService = new LoginService();
		Users user = loginService.login(loginId, password);

		HttpSession session = request.getSession();
		if(user != null){
			session.setAttribute("loginUser", user);
			response.sendRedirect("home.jsp");
		}else{
			List<String> messages = new ArrayList<String>();
			messages.add("ログインに失敗しました");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("login");
		}
	}
}
