package com.example.hackathontest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

//@Component
//public class DataRepo {
//	@Autowired
//	private JdbcTemplate jdbcTemplate;
//	public List<Map<String,Object>> demo(){
//		return jdbcTemplate.queryForList("select * from xxxT_FETR");
//	}
//}
@Component
public class DataRepo {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public List<Map<String,Object>> demo(){
		return jdbcTemplate.queryForList("select * from demo");
	}
	public List<String> getTeacherInfo(String UserId) {
		String sql = "select STAFF_ID FROM xxxT_USR_STAFF_MAP WHERE USR_ID=?";
		List<Map<String,Object>> user = jdbcTemplate.queryForList(sql,UserId);
		if(user == null || user.size() == 0) return null;
		String teacherId = String.valueOf((Number) user.get(0).get("STAFF_ID"));
		sql = "select * from xxxT_CLAS_DTLS where STAFF_ID=?";
		List<Map<String,Object>> classes = jdbcTemplate.queryForList(sql,teacherId);
		if(classes == null || classes.size() == 0) return null;
		List<String> ans = new ArrayList<String>();
		for(int i = 0; i < classes.size(); i++) {
			ans.add((String)classes.get(i).get("CLAS_ID"));
		}
		return ans;
	}
	
	public List<Object> getClassInfo(String UserId, String ClassId){
		List<Object> ans = new ArrayList<Object>();
		String sql = "select STAFF_ID FROM xxxT_USR_STAFF_MAP WHERE USR_ID=?";
		List<Map<String,Object>> user = jdbcTemplate.queryForList(sql,UserId);
		if(user == null || user.size() == 0) return null;
		String teacherId = String.valueOf((Number) user.get(0).get("STAFF_ID"));
		
		sql = "select * from\r\n"
				+ "xxxT_STUDENT_CLAS_DTLS as clas\r\n"
				+ "join \r\n"
				+ "xxxT_USR_STUDENT_MAP as map\r\n"
				+ "on map.STUDENT_ID = clas.STUDENT_ID "
				+ ""
				+ "where CLAS_ID=?";
		
		List<Map<String,Object>> data = jdbcTemplate.queryForList(sql,ClassId);
		for(int i = 0; i < data.size(); i++) {
			List<Object> tmp = new ArrayList<Object>();
			sql = "select STUDENT_FST_NM, STUDENT_LAST_NM from xxxT_STUDENT_DTLS "
					+ "where STUDENT_ID=?";
			List<Map<String,Object>> name = jdbcTemplate.queryForList(sql,(Number)data.get(i).get("STUDENT_ID"));
			tmp.add((String)data.get(i).get("USR_ID"));
			tmp.add((String)name.get(0).get("STUDENT_FST_NM"));
			tmp.add((String)name.get(0).get("STUDENT_LAST_NM"));
			int p = (int)data.get(i).get("DY_PRSNT_NR");
			tmp.add(p);
			int a = (int)data.get(i).get("DY_ABSENT_NR");
			tmp.add(a);
			tmp.add(100*(p)/(a+p));
			ans.add(tmp);
		}
		return ans;
	}
	
	
	public Map<Date,List<String>> getClassInfoDate(String UserId, String ClassId, Date from, Date to){
		String sql = "select STAFF_ID FROM xxxT_USR_STAFF_MAP WHERE USR_ID=?";
		List<Map<String,Object>> user = jdbcTemplate.queryForList(sql,UserId);
		if(user == null || user.size() == 0) return null;
		String teacherId = String.valueOf((Number) user.get(0).get("STAFF_ID"));
		
		sql = "select * from\r\n"
				+ "xxxT_STUDENT_CLAS_DTLS as clas\r\n"
				+ "join \r\n"
				+ "xxxT_USR_STUDENT_MAP as map\r\n"
				+ "on map.STUDENT_ID = clas.STUDENT_ID "
				+ "where CLAS_ID=?";
		Map<Date,List<String>> ans1 = new HashMap<Date,List<String>>();
		Map<Number,String> mapper = new HashMap<Number,String>();
		List<Map<String,Object>> data = jdbcTemplate.queryForList(sql,ClassId);
		for(int i = 0; i < data.size(); i++) 
			mapper.put((Number) data.get(i).get("STUDENT_ID"), (String)data.get(i).get("USR_ID"));
		for(Date date = from; date.before(to);) {
			List<String> list = new ArrayList<String>();
			sql = "select distinct STUDENT_ID from xxxT_ABS_MAP where ABS_DT = ?";
			List<Map<String,Object>> absents = jdbcTemplate.queryForList(sql,date);
			for(int j = 0; j < absents.size(); j++) {
				if(mapper.get((Number) absents.get(j).get("STUDENT_ID")) != null)
				list.add(mapper.get((Number) absents.get(j).get("STUDENT_ID")));
			}
			ans1.put(date, list);
			LocalDate localDate = date.toLocalDate();
			LocalDate updatedLocalDate = localDate.plusDays(1);
			date = Date.valueOf(updatedLocalDate);
		}
		return ans1;
	}
	
}
