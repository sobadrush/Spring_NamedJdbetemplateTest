package com.ctbc.test.namedparamjdbc.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ctbc.model.vo.DeptVO;

import _01_Config.RootConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = { RootConfig.class })
@Transactional
@ActiveProfiles(profiles = { "sqlite_env" })
@SuppressWarnings(value = { "rawtypes", "unchecked" })
public class JdbcTemplateSqlDef_in_xml_Test {

	private static final Logger logger = Logger.getLogger(JdbcTemplateSqlDef_in_xml_Test.class);
	
	private static int testNum = 1;

	@Resource(name = "sql_statements")
	private Properties sqlsProp;
	
	@Autowired
	private NamedParameterJdbcOperations namedJdbcTemplate;

	@Before
	public void setUp() throws Exception {
		String testNumStr = String.format("%03d", testNum++);
		System.err.println("========================================================= ");
		System.err.println(" ======================== " + "test_" + testNumStr + " ======================= ");
		System.err.println(" ========================================================= ");
	}

	@Test
	@Ignore
	@Rollback(true)
	public void test_001() throws SQLException {
		System.out.println(" namedJdbcTemplate >>> " + namedJdbcTemplate);
	}
	
	@Test
	@Ignore
	@Rollback(true)
	public void test_002() throws SQLException {
		/**
		 * 查全部
		 */
		String sql = sqlsProp.getProperty("deptSQL.getAllDept");
		List<DeptVO> dList = namedJdbcTemplate.query(sql, DeptVO.getDeptRowMapper());
		for (DeptVO dd : dList) {
			System.out.println(" >>> " + dd);
		}
	}
	
	@Test
	@Ignore
	@Rollback(true)
	public void test_003() throws SQLException {
		/**
		 * 查一筆 by id
		 */
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("deptId", "20");
		
		String sql = sqlsProp.getProperty("deptSQL.getDeptById");
//		namedJdbcTemplate.query(sql, paramMap, DeptVO.getDeptRowMapper());
		DeptVO dd = namedJdbcTemplate.queryForObject(sql, paramMap, DeptVO.getDeptRowMapper());
		System.out.println(" dd >>> " + dd);
	}
	
	@Test
	@Ignore
	@Rollback(true)
	public void test_004() throws SQLException {
		/**
		 * 查詢 LIKE
		 */
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("keyword", "發");
		
		String sql = sqlsProp.getProperty("deptSQL.getDeptLike");
		DeptVO dd = namedJdbcTemplate.queryForObject(sql, paramMap, DeptVO.getDeptRowMapper());
		System.out.println(" dd >>> " + dd);
	}
	
	@Test
	@Ignore
	@Rollback(true)
	public void test_005() throws SQLException {
		/**
		 * 新增一筆
		 */
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("p_deptNo", 50).addValue("p_deptName", "數金部").addValue("p_deptLoc", "南港");
		
		String sql = sqlsProp.getProperty("deptSQL.insert");
		int addPen = namedJdbcTemplate.update(sql, paramMap);
		System.out.println(" 新增成功筆數： >>> " + addPen);
		this.getShowAllData();
	}
	
	@Test
	@Ignore
	@Rollback(true)
	public void test_006() throws SQLException {
		/**
		 * 新增一筆 ( Keyholder 取得最新主鍵 )
		 */
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("p_deptNo", 50).addValue("p_deptName", "數金部").addValue("p_deptLoc", "南港");
		
		String sql = sqlsProp.getProperty("deptSQL.insert");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int addPen = namedJdbcTemplate.update(sql, paramMap, keyHolder);
		System.out.println(" (1) 新增成功筆數： >>> " + addPen);
		System.out.println(" (2) 最新主鍵： >>> " + keyHolder.getKey());
		this.getShowAllData();
	}
	
	@Test
	@Ignore
	@Rollback(true)
	public void test_007() throws SQLException {
		/**
		 * 更新一筆
		 */
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("p_deptNo", 10).addValue("p_deptName", "國防部").addValue("p_deptLoc", "綠島");
		
		String sql = sqlsProp.getProperty("deptSQL.update");
		int addPen = namedJdbcTemplate.update(sql, paramMap);
		System.out.println(" 更新成功筆數： >>> " + addPen);
		this.getShowAllData();
	}
	
	@Test
	@Ignore
	@Rollback(true)
	public void test_008() throws SQLException {
		/**
		 * 刪除一筆
		 */
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("p_deptNo", 10);
		
		String sql = sqlsProp.getProperty("deptSQL.delete");
		int addPen = namedJdbcTemplate.update(sql, paramMap);
		System.out.println(" 刪除成功筆數： >>> " + addPen);
		this.getShowAllData();
	}

	@Test
	@Ignore
	@Rollback(true)
	public void test_009() throws SQLException {
		/**
		 * 批量新增
		 */
		List<MapSqlParameterSource> dataParamList = new ArrayList<>();
		MapSqlParameterSource paramMap1 = new MapSqlParameterSource();
		MapSqlParameterSource paramMap2 = new MapSqlParameterSource();
		paramMap1.addValue("p_deptNo", 50).addValue("p_deptName", "數金部11").addValue("p_deptLoc", "南港11");
		paramMap2.addValue("p_deptNo", 60).addValue("p_deptName", "數金部22").addValue("p_deptLoc", "南港22");
		
		dataParamList.add(paramMap1);
		dataParamList.add(paramMap2);
		
		String sql = sqlsProp.getProperty("deptSQL.insertBatch");
		int[] addPens = namedJdbcTemplate.batchUpdate(sql, dataParamList.toArray(new MapSqlParameterSource[] {}));
		for (int pp : addPens) {
			System.out.println(" 新增成功筆數： >>> " + pp);
		}
		this.getShowAllData();
	}
	
	@Test
	@Ignore
	@Rollback(true)
	public void test_010() throws SQLException {
		/**
		 * 批量更新
		 */
		List<MapSqlParameterSource> dataParamList = new ArrayList<>();
		MapSqlParameterSource paramMap1 = new MapSqlParameterSource();
		MapSqlParameterSource paramMap2 = new MapSqlParameterSource();
		paramMap1.addValue("p_deptNo", 10).addValue("p_deptName", "數金部11").addValue("p_deptLoc", "南港11");
		paramMap2.addValue("p_deptNo", 20).addValue("p_deptName", "數金部22").addValue("p_deptLoc", "南港22");
		
		dataParamList.add(paramMap1);
		dataParamList.add(paramMap2);
		
		String sql = sqlsProp.getProperty("deptSQL.updateBatch");
		int[] updatePens = namedJdbcTemplate.batchUpdate(sql, dataParamList.toArray(new MapSqlParameterSource[] {}));
		for (int pp : updatePens) {
			System.out.println(" 更新成功筆數： >>> " + pp);
		}
		this.getShowAllData();
	}
	
	@Test
//	@Ignore
	@Rollback(true)
	public void test_011() throws SQLException {
		/**
		 * 查詢 部門表 JOIN 員工表
		 */
		String sql = sqlsProp.getProperty("deptSQL.joinEmpData");
		List<Map<String, Object>> resultList = namedJdbcTemplate.queryForList(sql, new MapSqlParameterSource());
		for (Map<String, Object> hmap : resultList) {
			System.out.println(" >>> " + hmap);
		}
		
	}
	
	private final void getShowAllData() {
		String sql = sqlsProp.getProperty("deptSQL.getAllDept");
		List<DeptVO> dList = namedJdbcTemplate.query(sql, DeptVO.getDeptRowMapper());
		for (DeptVO dd : dList) {
			System.out.println(" >>> " + dd);
		}
	}

}
