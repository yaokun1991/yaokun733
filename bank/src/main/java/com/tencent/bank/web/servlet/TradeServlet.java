package com.tencent.bank.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencent.bank.pojo.Account;
import com.tencent.bank.pojo.ResultInfo;
import com.tencent.bank.pojo.Trade;
import com.tencent.bank.service.TradeService;
import com.tencent.bank.service.impl.TradeServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/trade/*")
public class TradeServlet extends BaseServlet {
	private TradeService tradeService = new TradeServiceImpl();
	public void findTrade(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String msg = "";
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		long timeNow = System.currentTimeMillis();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(beginTime == null || "".equals(beginTime) || endTime == null || "".equals(endTime)){
			ResultInfo resultInfo = new ResultInfo();
			String errorMsg = "输入时间不能为空！";
			ObjectMapper objectMapper = new ObjectMapper();
			resultInfo.setFlag(false);
			resultInfo.setErrorMsg(errorMsg);
			response.setContentType("application/json;charset=utf-8");
			String json = objectMapper.writeValueAsString(resultInfo);
			response.getWriter().write(json);
			return;
		}
		//把开始时间结束时间转成毫秒值  为了校验
		long beginTime1 = 0;
		long endTime1 = 0;
		try {
			beginTime1 = dateFormat.parse(beginTime).getTime();
			endTime1 = dateFormat.parse(endTime).getTime();

		} catch (ParseException e) {
			e.printStackTrace();
		}

		if(beginTime1 > endTime1){
			ResultInfo resultInfo = new ResultInfo();
			String errorMsg = "开始时间必须小于结束时间";
			ObjectMapper objectMapper = new ObjectMapper();
			resultInfo.setFlag(false);
			resultInfo.setErrorMsg(errorMsg);
			response.setContentType("application/json;charset=utf-8");
			String json = objectMapper.writeValueAsString(resultInfo);
			response.getWriter().write(json);
			return;
		}
		if(timeNow - beginTime1 >= (1000*60*60*24*30L)){
			ResultInfo resultInfo = new ResultInfo();
			String errorMsg = "查询不能超越一个月";
			ObjectMapper objectMapper = new ObjectMapper();
			resultInfo.setFlag(false);
			resultInfo.setErrorMsg(errorMsg);
			response.setContentType("application/json;charset=utf-8");
			String json = objectMapper.writeValueAsString(resultInfo);
			response.getWriter().write(json);
			return ;
		}

		/*Date beginTime1 = null;
		Date endTime1  = null;
		try {
			 beginTime1 = dateFormat.parse(beginTime);
			endTime1 = dateFormat.parse(endTime);


		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		//只查询本账户的信息
		Account account = (Account) request.getSession().getAttribute("account");
		List<Trade> tradeList = tradeService.findTradeByAccoutId(account.getAccountId(),beginTime,endTime);
		/*for (Trade trade : tradeList) {
			System.out.println(trade.getTradeTime() + trade.getAccountId());
		}*/
		ResultInfo resultInfo = new ResultInfo();
		resultInfo.setFlag(true);
		resultInfo.setData(tradeList);
		writeValue(resultInfo,response);


	}

}
