package com.tencent.bank.service.impl;

import com.tencent.bank.dao.LoginDao;
import com.tencent.bank.dao.impl.LoginDaoImpl;
import com.tencent.bank.pojo.Account;
import com.tencent.bank.service.LoginService;

public class LoginServiceImpl implements LoginService {
	LoginDao loginDao = new LoginDaoImpl();
	@Override
	public Account loginCheck(Account account) {
		return loginDao.findAccountByAccountIdAndPassword(account.getAccountId(), account.getPassword());


	}
}
