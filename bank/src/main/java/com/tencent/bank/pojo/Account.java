package com.tencent.bank.pojo;

import java.math.BigDecimal;

public class Account {
	//账号
	private String accountId;
	//密码
	private String password;
	//余额
	private BigDecimal remaining;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BigDecimal getRemaining() {
		return remaining;
	}

	public void setRemaining(BigDecimal remaining) {
		this.remaining = remaining;
	}
}
