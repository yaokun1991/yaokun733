package com.tencent.bank.dao;

import com.tencent.bank.pojo.Trade;

import java.math.BigDecimal;
import java.util.List;

public interface TradeDao {
	List<Trade> findTradeByAccoutId(String accountId, String beginTime1, String endTime1);

	void AddTransferInfo(String accountId, int tradeTypeTransfer, String transferMoney, String transferId);
}
