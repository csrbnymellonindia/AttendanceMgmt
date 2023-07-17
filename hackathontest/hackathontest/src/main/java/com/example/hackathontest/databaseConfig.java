package com.example.hackathontest;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class databaseConfig {
	@Bean(name="datasource")
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/attendance");
        dataSource.setUsername("root");
        dataSource.setPassword("Root@123");
		return dataSource;
	}
	
	@Bean(name="jdbcTemplate")
	public JdbcTemplate jdbcTemplate(@Qualifier("datasource") DataSource dsMySql) {
		return new JdbcTemplate(dsMySql);
	}
}
