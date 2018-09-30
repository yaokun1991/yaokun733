package com.tencent.bank.service;

import com.tencent.bank.pojo.ResultInfo;

public interface AccountService {
	ResultInfo transfer(String transferId, String transferMoney, String accountId);
}
