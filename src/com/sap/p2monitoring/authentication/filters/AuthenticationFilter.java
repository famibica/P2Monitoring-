package com.sap.p2monitoring.authentication.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		if (req instanceof HttpServletRequest && res instanceof HttpServletResponse){
			HttpServletRequest request = (HttpServletRequest) req;

			if( !isAuthenticated( request ) ){
				((HttpServletResponse) res).setStatus( HttpServletResponse.SC_FORBIDDEN );
				((HttpServletResponse) res).getWriter().write("Forbidden: you are not logged in");
				return;
			}
				
			chain.doFilter(req, res);

		} else {
			((HttpServletResponse) res).setStatus( HttpServletResponse.SC_FORBIDDEN );
			((HttpServletResponse) res).getWriter().write("Forbidden: you are not logged in");
		}
	}
	
	private boolean isAuthenticated( HttpServletRequest request ){
		if( request.getRemoteUser() == null ) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
