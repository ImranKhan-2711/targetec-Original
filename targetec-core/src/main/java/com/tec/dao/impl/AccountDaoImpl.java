package com.tec.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.tec.dao.AccountDao;
import com.tec.dao.NeuroGenericDAO;
import com.tec.model.Account;
import com.tec.model.EmailTemplate;
import com.tec.model.RoleMaster;
import com.tec.template.Page;
import com.tec.template.PaginationHelper;

@Repository("accountDao")
public class AccountDaoImpl extends NeuroGenericDAO implements AccountDao {

	public AccountDaoImpl() {

	}

	@Inject
	public AccountDaoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public Account insert(Account account) {
		String insertSQL = "INSERT into account (role_id,username,password,status) VALUES (?,?,?,?)";
		jdbcTemplate.update(insertSQL,
				new Object[] { account.getRole().getId(), account.getUserName(), account.getPassword(), "ACTIVE" });
		account.setId(jdbcTemplate.queryForLong("select max(id) from account"));
		return account;
	}

	public Account update(Account account) {
		String updateSQL = "UPDATE account aet username=?,status=? WHERE id=?";
		String updateWithSQL = "UPDATE account set username=?,password=?,status=? WHERE id=?";
		if (account.getPassword() != null && !account.getPassword().trim().equals("")) {
			jdbcTemplate.update(updateWithSQL, new Object[] { account.getUserName(), account.getPassword(),
					account.getStatus(), account.getId() });
		} else {
			jdbcTemplate.update(updateSQL,
					new Object[] { account.getUserName(), account.getStatus(), account.getId() });
		}
		return account;
	}

	public EmailTemplate getContent(Long id) {
		PaginationHelper<EmailTemplate> ph = new PaginationHelper<EmailTemplate>();
		Page<EmailTemplate> page = new Page<EmailTemplate>("");
		String selSql = "select et.id,et.name,et.subject,et.content from email_template et where et.id=?";
		String countSQL = "select count(*) from (" + selSql + ") c";
		return ph.fetchPage(jdbcTemplate, countSQL, selSql, new Object[] { id }, page, new EmailTempateMapper())
				.getPageItems().get(0);
	}

	private static final class EmailTempateMapper implements ParameterizedRowMapper<EmailTemplate> {

		public EmailTemplate mapRow(ResultSet rs, int rowNum) throws SQLException {

			return new EmailTemplate(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4));
		}
	}

	public Account getAccount(String username) {
		PaginationHelper<Account> ph = new PaginationHelper<Account>();
		Page<Account> page = new Page<Account>("");
		String selSql = "SELECT id, username, password, status, role_id FROM account where username=?";
		String countSQL = "select count(*) from (" + selSql + ") c";
		return ph.fetchPage(jdbcTemplate, countSQL, selSql, new Object[] { username }, page, new AccountMapper())
				.getPageItems().get(0);
	}

	private static final class AccountMapper implements ParameterizedRowMapper<Account> {

		public Account mapRow(ResultSet rs, int rowNum) throws SQLException {

			return new Account(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4),
					new RoleMaster(rs.getString(5)));
		}
	}

	public void delete(Long acctId) {
		String deleteSQL = "delete from account_detail  WHERE account_id=?";
		jdbcTemplate.update(deleteSQL, new Object[] { acctId });

		String deleteSQL1 = "delete from account   WHERE  id=?";
		jdbcTemplate.update(deleteSQL1, new Object[] { acctId });

	}

	public int getAccountCount(String username) {
		String selSql = "SELECT count(*) FROM account where username=?";
		return jdbcTemplate.queryForInt(selSql, new Object[] { username });
	}
}
