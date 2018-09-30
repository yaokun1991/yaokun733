package com.tencent.bank.dao;

import com.tencent.bank.pojo.Account;

import java.math.BigDecimal;

public interface AccountDao {
	void transfer(String transferId, BigDecimal transferMoneyBig, String accountId, BigDecimal charge);
	public Account findAccountByAccountId(String transferId);
}
