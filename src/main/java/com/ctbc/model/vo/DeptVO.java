package com.ctbc.model.vo;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.jdbc.core.RowMapper;

public class DeptVO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer deptno;
	private String dname;
	private String loc;
	
	public DeptVO() {
		super();
	}

	public Integer getDeptno() {
		return deptno;
	}


	public void setDeptno(Integer deptno) {
		this.deptno = deptno;
	}


	public String getDname() {
		return dname;
	}


	public void setDname(String dname) {
		this.dname = dname;
	}


	public String getLoc() {
		return loc;
	}


	public void setLoc(String loc) {
		this.loc = loc;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE);
	}
	
	public static DeptRowMapper getDeptRowMapper() {
		return new DeptRowMapper();
	}
	
	public static class DeptRowMapper implements RowMapper<DeptVO>{
		@Override
		public DeptVO mapRow(ResultSet rs, int colnum) throws SQLException {
			DeptVO deptVO = new DeptVO();
			deptVO.setDeptno(rs.getInt("deptno"));
			deptVO.setDname(rs.getString("dname"));
			deptVO.setLoc(rs.getString("loc"));
			return deptVO;
		}
	} 
	
}
