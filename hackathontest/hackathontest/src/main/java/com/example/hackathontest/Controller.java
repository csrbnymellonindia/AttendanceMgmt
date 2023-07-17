package com.example.hackathontest;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	@Autowired 
	DataRepo repo;
	
	@GetMapping("demo")
	@ResponseBody
	public List<Map<String,Object>> demo(){
		return repo.demo();
	}
	
	@PostMapping("check")
	@ResponseBody
	public String conn(Object Data) {
		System.out.println(Data);
		return "Success";
	}
	
	@GetMapping("/getTeacherInfo/{Id}")
	@ResponseBody
	public List<String> getTeacherInfo(@PathVariable("Id") String Id){
		return repo.getTeacherInfo(Id);
	}
	
	@GetMapping("/getClassInfo/teacherId={Id}&classId={clas}")
	@ResponseBody
	public List<Object> getClassInfo(@PathVariable("Id") String Id, @PathVariable("clas") String clas){
		return repo.getClassInfo(Id,clas);
	}
	
	@GetMapping("/getClassInfoDate/teacherId={Id}&classId={clas}&from={from}&to={to}")
	@ResponseBody
	public Map<Date,List<String>> getClassInfoDate(@PathVariable("Id") String Id, 
			@PathVariable("clas") String clas, @PathVariable("from") String from, @PathVariable("to") String to) throws ParseException{
	
		java.sql.Date From = java.sql.Date.valueOf(from);
		java.sql.Date To = java.sql.Date.valueOf(to);
		return repo.getClassInfoDate(Id,clas,From,To);
	}
}