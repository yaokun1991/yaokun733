package com.tencent.bank.service.impl;

import com.tencent.bank.dao.TradeDao;
import com.tencent.bank.dao.impl.TradeDaoImpl;
import com.tencent.bank.pojo.Trade;
import com.tencent.bank.service.TradeService;

import java.util.List;

public class TradeServiceImpl implements TradeService {
	TradeDao tradeDao  = new TradeDaoImpl();
	@Override
	public List<Trade> findTradeByAccoutId(String accountId, String beginTime1, String endTime1) {
		return tradeDao.findTradeByAccoutId(accountId,beginTime1,endTime1);
	}

	@Override
	public void AddTransferInfo(String accountId, int tradeTypeTransfer, String transferMoney, String transferId) {
		tradeDao.AddTransferInfo(accountId,tradeTypeTransfer,transferMoney,transferId);
	}
}
