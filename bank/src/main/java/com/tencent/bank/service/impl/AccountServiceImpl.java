package com.tencent.bank.service.impl;

import com.tencent.bank.dao.AccountDao;
import com.tencent.bank.dao.TradeDao;
import com.tencent.bank.dao.impl.AccountDaoImpl;
import com.tencent.bank.pojo.Account;
import com.tencent.bank.pojo.ResultInfo;
import com.tencent.bank.service.AccountService;
import com.tencent.bank.service.TradeService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountServiceImpl implements AccountService {
	private AccountDao accountDao = new AccountDaoImpl();

	private TradeService tradeService = new TradeServiceImpl();

	public static  final  int TRADE_TYPE_TRANSFER = 3;
	// transferId接受账户名称  transferMoney转账金额 accountId 转账人名称


	@Override
	public ResultInfo transfer(String transferId, String transferMoney, String accountId) {
		ResultInfo resultInfo = new ResultInfo();
		//之前debug if(){}  执行了return  后面的还能 执行 这是为什么
		//判断转入账号是否存在
		Account accountTransferTo = accountDao.findAccountByAccountId(transferId);
		if(accountTransferTo == null || "".equals(accountTransferTo)){
			resultInfo.setFlag(false);
			resultInfo.setErrorMsg("转入账号不存在！");
			return resultInfo;
		}

		//余额 不能小于 转账金额 + 手续费
		//校验余额
		//查询余额
		Account account = accountDao.findAccountByAccountId(accountId);
		BigDecimal remaining = account.getRemaining();


		//计算手续费

		//转账金额
		BigDecimal transferMoneyBig = new BigDecimal(transferMoney);

		//radix 0.005 手续费率
		BigDecimal radix = new BigDecimal(0.005);
		//charge手续费
		BigDecimal charge = transferMoneyBig.multiply(radix);
		//如果手续费比2小 手续费为2
		if(charge.compareTo(BigDecimal.valueOf(2)) == -1){
			charge = BigDecimal.valueOf(2);
		}
		//如果手续费比20大 手续费为20
		if(charge.compareTo(BigDecimal.valueOf(20)) == 1){
			charge = BigDecimal.valueOf(20);
		}
		//计算手续费结束

		//判断余额是否充足
		if(remaining.compareTo(transferMoneyBig.add(charge)) == -1){
			//钱不够了
			resultInfo.setFlag(false);
			resultInfo.setErrorMsg("余额不足");
			return resultInfo;

		}


		//转账
		//补充添加事件回滚
		accountDao.transfer(transferId,transferMoneyBig,accountId,charge);


		//插入转账记录

		tradeService.AddTransferInfo(accountId,TRADE_TYPE_TRANSFER,transferMoney,transferId);


		resultInfo.setFlag(true);
		List list = new ArrayList<>();
		list.add(transferId);
		list.add(transferMoney);
		resultInfo.setData(list);

		return resultInfo;
	}
}
