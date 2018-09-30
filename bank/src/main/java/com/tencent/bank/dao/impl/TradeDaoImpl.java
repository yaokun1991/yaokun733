package com.tencent.bank.dao.impl;


import com.tencent.bank.dao.TradeDao;
import com.tencent.bank.pojo.Trade;
import com.tencent.bank.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TradeDaoImpl implements TradeDao {
	JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

	@Override
	public List<Trade> findTradeByAccoutId(String accountId, String beginTime1, String endTime1) {
		String sql = "select * from trade where AccountID = ? and TradeTime BETWEEN ? and ? ";
		List<Trade> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Trade>(Trade.class),accountId,beginTime1,endTime1);
		return list;

	}

	@Override
	public void AddTransferInfo(String accountId, int tradeTypeTransfer, String transferMoney, String transferId) {
		String sql = "insert  into trade (AccountID,TradeType,TradeMoney,TradeTime,TradeDigest)  values(?,?,?,?,?)";
		List list = new ArrayList<>();
		jdbcTemplate.update(sql,accountId,tradeTypeTransfer,transferMoney,new Date(),"接受账号:" + transferId);
	}
}
