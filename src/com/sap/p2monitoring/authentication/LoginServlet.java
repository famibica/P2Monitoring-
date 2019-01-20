package com.sap.p2monitoring.authentication;

import java.io.IOException;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sap.security.auth.login.LoginContextFactory;

/**
 * Servlet implementation class login
 */	
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String user = request.getRemoteUser();
    if (user != null) {
      response.getWriter().println("Hello, " + user);
    } else {
      LoginContext loginContext;
      try {
        loginContext = LoginContextFactory.createLoginContext("FORM");
        loginContext.login();
        response.getWriter().println("Hello, " + request.getRemoteUser());

      } catch (LoginException e) {
        e.printStackTrace();
      }
    }
  }
}
