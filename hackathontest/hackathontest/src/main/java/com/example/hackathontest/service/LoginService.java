package com.example.hackathontest.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.hackathontest.User;

@Service
public class LoginService {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	public User ValidateLogin(String USER_IDENTIFIER,String PASSWORD) {
		//System.out.println(USER_IDENTIFIER);
		String sql = "select * from xxxT_USR_LOGN_DTL where USR_ID=?" ;
		List<Map<String,Object>> data = jdbcTemplate.queryForList(sql,USER_IDENTIFIER);
//	    System.out.println(data);
		if(data==null||data.size()==0) {
//	    	System.out.println("hehe");
			return null;
		}else {
		    Map<String, Object> firstElement = data.get(0); // Accessing the first element
		    //System.out.println(firstElement);
		    User user = new User();
		    user.setUSER_IDENTIFIER((String)firstElement.get("USR_ID"));
		    user.setUSER_PROFILE_IDENTIFIER((int)firstElement.get("USR_PRFL_ID"));
		    user.setUSER_NAME((String)firstElement.get("USR_NM"));
		    user.setPASSWORD((String)firstElement.get("PASS"));
		    if(user.getPASSWORD().equals(PASSWORD)) {
		    	user.setPASSWORD(null);
		    return user;}else {
		    	return null;
		    }
		}
	}
	
//	public User getUser(String USER_IDENTIFIER) {
//		String sql = "select * from xxxT_USR_LOGN_DTL where USR_ID=?" ;
//		List<Map<String,Object>> data = jdbcTemplate.queryForList(sql,USER_IDENTIFIER);
//		Map<String, Object> firstElement = data.get(0); // Accessing the first element
//	    User user = new User();
//	    
//	    user.setUSER_IDENTIFIER((String)firstElement.get("USR_ID"));
//	    user.setUSER_PROFILE_IDENTIFIER((int)firstElement.get("USR_PRFL_ID"));
//	    user.setUSER_NAME((String)firstElement.get("USR_NM"));
////    	System.out.println(user);
//	    return user;
//	}
	
}