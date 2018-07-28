package com.ctbc.test.namedparamjdbc.test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
public class JdbcTemplateTest {

	private static int testNum = 1;

	@Autowired
	private DataSource ds;

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
		System.out.println("================ 查全部 ==================");
		String sql = "SELECT * FROM z40180_deptTB";
		List<DeptVO> dList = namedJdbcTemplate.getJdbcOperations().query(sql, new BeanPropertyRowMapper(DeptVO.class)/* VO屬性名稱跟TABLE中湘同，可用此API進行反射 */);
		for (DeptVO vo : dList) {
			System.out.println(" >>> " + vo);
		}
	}

	@Test
	@Ignore
	@Rollback(true)
	public void test_003() throws SQLException {
		System.out.println("================ 查一筆By ID ==================");
		String sql = "SELECT * FROM z40180_deptTB WHERE deptno = :deptId ";

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("deptId", 20);

		DeptVO deptVO = namedJdbcTemplate.queryForObject(sql, paramMap, DeptVO.getDeptRowMapper());
		System.err.println(" deptVO >>> " + deptVO);

	}

	@Test
	@Ignore
	@Rollback(true)
	public void test_004() throws SQLException {
		System.out.println("================ 查一筆By ID ( MapSqlParameterSource ) ==================");
		String sql = "SELECT * FROM z40180_deptTB WHERE deptno = :deptId AND dname LIKE :deptName";

		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("deptId", 10)
				.addValue("deptName", "%務%");

		DeptVO deptVO = namedJdbcTemplate.queryForObject(sql, param, DeptVO.getDeptRowMapper());
		System.err.println(" deptVO >>> " + deptVO);

	}

	@Test
	@Ignore
	@Rollback(true)
	public void test_005() throws SQLException {
		System.out.println("================ 新增一筆 =================");
		String sql = "INSERT INTO z40180_deptTB( deptno , dname , loc ) VALUES( :deptNo , :deptName , :deptLoc )";

		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("deptNo", "50")
				.addValue("deptName", "應用系統部")
				.addValue("deptLoc", "南港");

		int addPen = namedJdbcTemplate.update(sql, param);
		System.err.println(" 新增成功筆數 >>> " + addPen);

	}

	@Test
//	@Ignore
	@Rollback(true)
	public void test_006() throws SQLException {
		System.out.println("================ 新增一筆 獲得最新主鍵 ( GeneratedKeyHolder ) ==================");
		String sql = "INSERT INTO z40180_deptTB( deptno , dname , loc ) VALUES( :deptNo , :deptName , :deptLoc )";

		KeyHolder keyholder = new GeneratedKeyHolder();

		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("deptNo", "50")
				.addValue("deptName", "應用系統部")
				.addValue("deptLoc", "南港");

		int addPen = namedJdbcTemplate.update(sql, param, keyholder);
		System.err.println(" (1) 新增成功筆數 >>> " + addPen);
		System.err.println(" (2) 最新主鍵 >>> " + keyholder.getKey());

	}

}
