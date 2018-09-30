package com.tencent.bank.service;

import com.tencent.bank.pojo.Trade;

import java.util.List;

public interface TradeService {
	List<Trade> findTradeByAccoutId(String accountId, String beginTime1, String endTime1);

	void AddTransferInfo(String accountId, int tradeTypeTransfer, String transferMoney, String transferId);
}
