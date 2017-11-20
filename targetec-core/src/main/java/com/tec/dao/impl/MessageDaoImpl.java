package com.tec.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.tec.dao.MessageDao;
import com.tec.dao.NeuroGenericDAO;
import com.tec.model.Account;
import com.tec.model.AccountDetail;
import com.tec.model.MessageDetail;
import com.tec.model.MessageHead;
import com.tec.template.Page;
import com.tec.template.PaginationHelper;

@Repository("messageDao")
public class MessageDaoImpl extends NeuroGenericDAO implements MessageDao {

	public MessageDaoImpl() {

	}

	@Inject
	public MessageDaoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public Page<MessageDetail> listMessages(Page<MessageDetail> page, String subject, String message, String userName) {
		PaginationHelper<MessageDetail> ph = new PaginationHelper<MessageDetail>();
		String selSql = "SELECT mh.id,mh.message_type,mh.from_date, mh.to_date, "
				+ "mh.from_account_id,aa.username uname,ad.first_name,ad.last_name,ad.email, mh.subject,mh.message,"
				+ "md.id message_detail_id,md.to_account_id,md.read_y_n FROM message_head mh,message_detail md,account a,account aa,account_detail ad WHERE mh.from_account_id=ad.account_id and mh.from_account_id=aa.id and mh.id=md.message_id and md.to_account_id = a.id and a.username = ?";
		String countSQL = "select count(*) from (" + selSql + ") c";
		return ph.fetchPage(jdbcTemplate, countSQL, selSql, new Object[] { userName }, page, new MessageDetailMapper());
	}

	private static final class MessageDetailMapper implements ParameterizedRowMapper<MessageDetail> {

		public MessageDetail mapRow(ResultSet rs, int rowNum) throws SQLException {

			return new MessageDetail(rs.getLong(12),
					new MessageHead(rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getDate(4),
							new Account(rs.getLong(5), rs.getString(6), rs.getString(7), rs.getString(8),
									rs.getString(9)),
							rs.getString(10), rs.getString(11)),
					new Account(rs.getLong(13)), rs.getString(14));
		}
	}

	public int getMessageWithAccount(Long acctId) {
		return jdbcTemplate.queryForInt("SELECT count(*) FROM  message_detail  WHERE  to_account_id='" + acctId + "'");
	}

	public int getMessageHeadWithAccount(Long acctId) {
		return jdbcTemplate.queryForInt("SELECT count(*) FROM  message_head  WHERE  from_account_id='" + acctId + "'");
	}
}
