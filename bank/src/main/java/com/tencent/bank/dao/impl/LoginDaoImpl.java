package com.tencent.bank.dao.impl;

import com.tencent.bank.dao.LoginDao;
import com.tencent.bank.pojo.Account;
import com.tencent.bank.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class LoginDaoImpl implements LoginDao {
	private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
	@Override
	public Account findAccountByAccountIdAndPassword(String accountId, String password) {
		Account account = null;
		try {
			String sql = "select * from account where AccountID = ? and Password = ? ";
			 account = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Account>(Account.class),accountId,password);
		} catch (DataAccessException e) {
			System.out.println("用户名或密码错误");
		}
		return account;

	}
}
