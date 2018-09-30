package com.tencent.bank.dao.impl;

import com.tencent.bank.dao.AccountDao;
import com.tencent.bank.pojo.Account;
import com.tencent.bank.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
@Transactional
public class AccountDaoImpl implements AccountDao {
	private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
	@Override
	public void transfer(String transferId, BigDecimal transferMoneyBig, String accountId, BigDecimal charge) {
		//转出的sql
		String sqlfrom = "update account set Remaining = Remaining - ? - ? where  AccountID = ?  ";
		//转入的sql
		String sqlto = "update account set Remaining = Remaining + ?  where  AccountID = ? ";
		jdbcTemplate.update(sqlfrom,transferMoneyBig,charge,accountId);
		jdbcTemplate.update(sqlto,transferMoneyBig,transferId);

	}


	@Override
	public Account findAccountByAccountId(String transferId) {
		Account account = null;
		try {
			String sql = "select * from account where AccountID = ? ";
			account = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Account>(Account.class),transferId);
		} catch (DataAccessException e) {
			System.out.println("转入账号不存在");
		}
		return account;

	}
}
