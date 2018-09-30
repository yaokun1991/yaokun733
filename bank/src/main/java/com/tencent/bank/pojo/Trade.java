package com.tencent.bank.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Trade {
	//流水号
	private Integer id;
	//帐号
	private String accountId;
	//交易类型 交易类型有3种,分别为:存款、取款和转出
	private String tradeType;
	//交易金额
	private BigDecimal tradeMoney;
	//交易时间
	private Date tradeTime;
	//交易摘要
	private String tradeDigest;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public BigDecimal getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(BigDecimal tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	public Date getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getTradeDigest() {
		return tradeDigest;
	}

	public void setTradeDigest(String tradeDigest) {
		this.tradeDigest = tradeDigest;
	}
}
