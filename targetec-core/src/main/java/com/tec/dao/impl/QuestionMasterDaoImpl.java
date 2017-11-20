package com.tec.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.tec.dao.NeuroGenericDAO;
import com.tec.dao.QuestionMasterDao;
import com.tec.model.CategoryMaster;
import com.tec.model.QuestionMaster;
import com.tec.model.ResourceMaster;
import com.tec.model.StyleMaster;
import com.tec.model.TypeMaster;
import com.tec.template.Page;
import com.tec.template.PaginationHelper;

@Repository("questionMasterDao")
public class QuestionMasterDaoImpl extends NeuroGenericDAO implements QuestionMasterDao {

	public QuestionMasterDaoImpl() {

	}

	@Inject
	public QuestionMasterDaoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public Long insert(QuestionMaster questionMaster) {
		String insertSQL = "INSERT INTO question_master (type_id,style_id,description,directions,question_text,media_resource,min_words,max_words,duration,status,question_code) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(insertSQL,
				new Object[] { questionMaster.getTypeMaster().getId(), questionMaster.getStyleMaster().getId(),
						questionMaster.getDescription(), questionMaster.getDirection(),
						questionMaster.getQuestionText(), questionMaster.getResourceMaster().getId(),
						questionMaster.getMinWords(), questionMaster.getMaxWords(), questionMaster.getDuration(),
						"ACTIVE", questionMaster.getQuestionCode() });
		return jdbcTemplate.queryForLong("select max(id) from question_master");
	}

	public QuestionMaster update(QuestionMaster questionMaster) {
		String updateSQL = "UPDATE question_master SET description=?,directions=?,question_text=?,media_resource=?,min_words=?,max_words=?,status=?,duration=?,question_code=? where id=?";
		jdbcTemplate.update(updateSQL,
				new Object[] { questionMaster.getDescription(), questionMaster.getDirection(),
						questionMaster.getQuestionText(), questionMaster.getResourceMaster().getId(),
						questionMaster.getMinWords(), questionMaster.getMaxWords(), questionMaster.getStatus(),
						questionMaster.getDuration(), questionMaster.getQuestionCode(), questionMaster.getId() });
		return questionMaster;

	}

	public QuestionMaster findByQuestionMasterId(Long id) {
		PaginationHelper<QuestionMaster> ph = new PaginationHelper<QuestionMaster>();
		Page<QuestionMaster> page = new Page<QuestionMaster>("");
		String selSQL = "SELECT qm.id id, tm.id type_id,tm.name type_name, sm.id style_id,sm.name style_name, qm.description, qm.directions, qm.question_text, qm.min_words, qm.max_words, qm.duration, qm.status,rm.id resource_id,sm.description style_description,qm.question_code question_code FROM question_master qm,type_master tm,style_master sm,resource_master rm  WHERE qm.type_id =tm.id and qm.style_id = sm.id and rm.id=qm.media_resource ";

		if (id != null && !id.equals("")) {
			selSQL += " and qm.id like '%" + id + "%'";
		}
		String countSQL = "select count(*) from (" + selSQL + ") c";
		return ph.fetchPage(jdbcTemplate, countSQL, selSQL, new String[] {}, page, new QuestionMasterMapper())
				.getPageItems().get(0);
	}

	public Page<QuestionMaster> findQuestions(Page<QuestionMaster> page, Long typeId) {
		PaginationHelper<QuestionMaster> ph = new PaginationHelper<QuestionMaster>();

		String selSQL = " SELECT qm.id id, tm.id type_id,tm.name type_name, sm.id style_id,sm.name style_name, qm.description, qm.directions, qm.question_text, qm.min_words, qm.max_words, qm.duration, qm.status,rm.id resource_id,sm.description style_description,qm.question_code question_code FROM question_master qm,type_master tm,style_master sm,resource_master rm WHERE qm.type_id =tm.id and qm.style_id = sm.id AND tm.id='"
				+ typeId + "' and rm.id=qm.media_resource";

		String countSQL = "select count(*) from (" + selSQL + ") c";
		return ph.fetchPage(jdbcTemplate, countSQL, selSQL, new String[] {}, page, new QuestionMasterMapper());
	}

	public List<QuestionMaster> findQuestionsByTestId(Long testId) {
		PaginationHelper<QuestionMaster> ph = new PaginationHelper<QuestionMaster>();
		Page<QuestionMaster> page = new Page<QuestionMaster>("qm.id");
		String selSQL = " SELECT qm.id id, tm.id type_id,tm.name type_name, sm.id style_id,sm.name style_name, qm.description, qm.directions, qm.question_text, qm.min_words, qm.max_words, qm.duration, qm.status,rm.id resource_id,sm.description style_description,qm.question_code question_code FROM question_master qm,test_question tq, type_master tm,style_master sm,resource_master rm WHERE qm.type_id =tm.id and tq.question_id=qm.id and qm.style_id = sm.id AND tq.test_id='"
				+ testId + "' and rm.id=qm.media_resource";

		String countSQL = "select count(*) from (" + selSQL + ") c";
		return ph.fetchPage(jdbcTemplate, countSQL, selSQL, new String[] {}, page, new QuestionMasterMapper())
				.getPageItems();
	}

	public List<QuestionMaster> findQuestionsByDetailId(Long testDetailId) {
		PaginationHelper<QuestionMaster> ph = new PaginationHelper<QuestionMaster>();
		Page<QuestionMaster> page = new Page<QuestionMaster>("qm.id");
		String selSQL = "SELECT qm.id id, tm.id type_id,tm.name type_name, sm.id style_id,sm.name style_name, qm.description, qm.directions, qm.question_text, qm.min_words, qm.max_words, qm.duration, qm.status,rm.id resource_id,sm.description style_description,qm.question_code question_code FROM question_master qm,test_question tq, type_master tm,style_master sm,resource_master rm,student_test_detail std WHERE qm.type_id =tm.id and tq.question_id=qm.id and qm.style_id = sm.id and rm.id=qm.media_resource and std.test_id = tq.test_id and std.id ='"
				+ testDetailId + "'";

		String countSQL = "select count(*) from (" + selSQL + ") c";
		return ph.fetchPage(jdbcTemplate, countSQL, selSQL, new String[] {}, page, new QuestionMasterMapper())
				.getPageItems();
	}

	private static final class QuestionMasterMapper implements ParameterizedRowMapper<QuestionMaster> {

		public QuestionMaster mapRow(ResultSet rs, int rowNum) throws SQLException {

			return new QuestionMaster(rs.getLong(1), new TypeMaster(rs.getLong(2), rs.getString(3), null, null),
					new StyleMaster(rs.getLong(4), rs.getString(5), rs.getString(14), null),
					new ResourceMaster(rs.getLong(13)), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9),
					rs.getInt(10), rs.getTime(11), rs.getString(12), rs.getString(15));
		}
	}

	public List<TypeMaster> getTypeList() {
		PaginationHelper<TypeMaster> ph = new PaginationHelper<TypeMaster>();
		Page<TypeMaster> page = new Page<TypeMaster>("");
		String selSQL = "SELECT id, name, description,status FROM type_master ";

		String countSQL = "select count(*) from (" + selSQL + ") c";
		return ph.fetchPage(jdbcTemplate, countSQL, selSQL, new String[] {}, page, new TypeMasterMapper())
				.getPageItems();
	}

	private static final class TypeMasterMapper implements ParameterizedRowMapper<TypeMaster> {

		public TypeMaster mapRow(ResultSet rs, int rowNum) throws SQLException {

			return new TypeMaster(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4));
		}
	}

	public List<CategoryMaster> getCategoryList() {
		PaginationHelper<CategoryMaster> ph = new PaginationHelper<CategoryMaster>();
		Page<CategoryMaster> page = new Page<CategoryMaster>("");
		String selSQL = "SELECT id, name, description, status FROM category_master ";

		String countSQL = "select count(*) from (" + selSQL + ") c";
		return ph.fetchPage(jdbcTemplate, countSQL, selSQL, new String[] {}, page, new CategoryMasterMapper())
				.getPageItems();
	}

	private static final class CategoryMasterMapper implements ParameterizedRowMapper<CategoryMaster> {

		public CategoryMaster mapRow(ResultSet rs, int rowNum) throws SQLException {

			return new CategoryMaster(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4));
		}
	}

	public ResourceMaster findByResourceMasterId(Long id) {
		PaginationHelper<ResourceMaster> ph = new PaginationHelper<ResourceMaster>();
		Page<ResourceMaster> page = new Page<ResourceMaster>("");
		String selSQL = "SELECT id, resource_type, resource_ext, resource_path, table_name, column_name, status FROM resource_master ";

		if (id != null && !id.equals("")) {
			selSQL += " where id = '" + id + "' ";
		}
		String countSQL = "select count(*) from (" + selSQL + ") c";
		return ph.fetchPage(jdbcTemplate, countSQL, selSQL, new String[] {}, page, new ResourceMasterMapper())
				.getPageItems().get(0);
	}

	private static final class ResourceMasterMapper implements ParameterizedRowMapper<ResourceMaster> {

		public ResourceMaster mapRow(ResultSet rs, int rowNum) throws SQLException {

			return new ResourceMaster(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7));
		}
	}

	public void delete(Long quesId) {
		String deleteSQL = "delete from answer_master WHERE  question_id=?";
		jdbcTemplate.update(deleteSQL, new Object[] { quesId });
		String deleteSQL1 = "delete from question_master WHERE  id=?";
		jdbcTemplate.update(deleteSQL1, new Object[] { quesId });
	}
}
