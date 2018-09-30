package com.tencent.bank.dao;

import com.tencent.bank.pojo.Account;

public interface LoginDao {

	Account findAccountByAccountIdAndPassword(String accountId, String password);
}
