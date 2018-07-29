package com.ctbc.test.namedparamjdbc.test;

import java.sql.SQLException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import _01_Config.RootConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = { RootConfig.class })
@Transactional
@ActiveProfiles(profiles = { "sqlite_env" })
public class Log4J2_Test {

	private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(Log4J2_Test.class);
	
	@Test
//	@Ignore
	@Rollback(true)
	public void test_001() throws SQLException {
		for (int i = 0 ; i < 300 ; i++) {
			logger.info("幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹");
		}
	}

}



