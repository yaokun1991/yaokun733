package com.tencent.bank.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencent.bank.pojo.Account;
import com.tencent.bank.pojo.ResultInfo;
import com.tencent.bank.service.AccountService;
import com.tencent.bank.service.LoginService;
import com.tencent.bank.service.impl.AccountServiceImpl;
import com.tencent.bank.service.impl.LoginServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/account/*")
public class AccountServlet extends BaseServlet {
	private AccountService accountService = new AccountServiceImpl();

	/*
	 * @Description 从session调用account
	 * @Author  yaokun <wawatianbing@126.com>
	 * @Version V1.0.0
	 * @param
	 * @return
	 * @Date $DATE
	 */
	public void findAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Account account = (Account) request.getSession().getAttribute("account");
		ObjectMapper objectMapper = new ObjectMapper();
		response.setContentType("application/json;charset=utf-8");
		writeValue(account, response);


	}

	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		/*//先校验验证码
		String check = request.getParameter("check");
		String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
		//移除session中的验证码
		if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
			//验证失败
			ResultInfo resultInfo = new ResultInfo();
			resultInfo.setErrorMsg("验证码失败");
			resultInfo.setFlag(false);

			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(resultInfo);

			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(json);
			return ;
		}*/


		//验证码成功继续进行下一步
		Account account = new Account();
		Map<String, String[]> parameterMap = request.getParameterMap();
		try {
			BeanUtils.populate(account, parameterMap);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		//调用service方法 验证 如果存在 那么跳转  不成功 留在本页 并显示错误信息
		LoginService loginService = new LoginServiceImpl();
		Account account1 = loginService.loginCheck(account);
		ResultInfo resultInfo = new ResultInfo();

		if (account1 == null) {
			//校验通过 跳转
			resultInfo.setFlag(false);
			resultInfo.setErrorMsg("用户名或密码错误");
		} else {
			request.getSession().setAttribute("account", account1);
			resultInfo.setFlag(true);
		}
		ObjectMapper objectMapper = new ObjectMapper();

		response.setContentType("application/json;charset=utf-8");
		objectMapper.writeValue(response.getOutputStream(), resultInfo);

	}

	/*
	 * @Description 转账
	 * @Author  yaokun <wawatianbing@126.com>
	 * @Version V1.0.0
	 * @param
	 * @return
	 * @Date $DATE
	 */
	public void transfer(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String transferId = request.getParameter("transferId");
		String transferMoney = request.getParameter("transferMoney");


		ResultInfo resultInfo = new ResultInfo();

		//校验是否登录
		Account account = (Account) request.getSession().getAttribute("account");

		if(account == null){
			resultInfo.setFlag(false);
			resultInfo.setErrorMsg("请先登录");
			writeValue(resultInfo, response);
		}



		//校验输入金额是否为数字
		String regex = "^[0.0-9.0]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher isNum = pattern.matcher(transferMoney);
		if (!isNum.matches()) {
			resultInfo.setFlag(false);
			resultInfo.setErrorMsg("转账金额必须是数字");

			writeValue(resultInfo, response);
			return;

		}


		String accountId = account.getAccountId();
		if (transferId == null || "".equals(transferId) || transferMoney == null && "".equals(transferId)) {
			resultInfo.setFlag(false);
			resultInfo.setErrorMsg("输入不能为空！");
			writeValue(resultInfo, response);
			return;
		}


		//转入的账号不能为自己的账号
		if(accountId.equals(transferId)){
			resultInfo.setFlag(false);
			resultInfo.setErrorMsg("无法转入自己的账号！");
			writeValue(resultInfo, response);
			return;
		}

		resultInfo = accountService.transfer(transferId, transferMoney, accountId);


		writeValue(resultInfo, response);

	}

}
