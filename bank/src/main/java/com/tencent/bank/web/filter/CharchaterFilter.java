package com.tencent.bank.web.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 解决全站乱码问题，处理所有的请求
 */
@WebFilter("/*")
public class CharchaterFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse rep, FilterChain filterChain) throws IOException, ServletException {
		//将父接口转为子接口
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) rep;

		HttpServletRequest myRequest = (HttpServletRequest) Proxy.newProxyInstance(request.getClass().getClassLoader(), request.getClass().getInterfaces(), new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				String methodName = request.getMethod();
				if ("post".equalsIgnoreCase(methodName)) {
					request.setCharacterEncoding("utf-8");
				} else if ("get".equalsIgnoreCase(methodName)) {

					if ("getParameter".equals(method.getName())) {


						String parameter = (String) method.invoke(request, args);

						if(parameter != null && !"".equals(parameter)){
							parameter = new String(parameter.getBytes("iso-8859-1"), "utf-8");
						}
						return parameter;

					}

				}

				return method.invoke(request, args);
			}
		});
		//处理响应乱码
		response.setContentType("text/html;charset=utf-8");
		filterChain.doFilter(myRequest, response);
	}

	@Override
	public void destroy() {

	}
}
