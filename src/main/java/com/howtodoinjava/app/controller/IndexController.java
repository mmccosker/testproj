package com.howtodoinjava.app.controller;

import java.sql.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grossmont.ws.WeatherServiceManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.sql.*;
import java.util.*;
import java.sql.*;
import javax.servlet.http.HttpServletRequest;


// import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Controller
// @RestController
@RequestMapping("/api")
public class IndexController {

private final Logger logger = LoggerFactory.getLogger(IndexController.class);
private WeatherServiceManager wm;
private float temp;
private float tempF;
private String city;
private Integer tz;

@RequestMapping("/home")
public String home(Map<String, Object> model) {
	
	model.put("Home", "Weather Report Application!!");
	
	return "index";
}

@PostMapping("/wmap")
	public String wmap(Map<String, Object> model,HttpServletRequest request) {
		
		wm = new WeatherServiceManager();
		city = request.getParameter("city");
		if(city == null) // Use Default
		wm.callWeatherWebService("Chicago");
		else
		wm.callWeatherWebService(city);
		
		temp = wm.getCurrentTemp();
		tempF = wm.getTempManualParse();
		tz = wm.getTimeZone();
        city = wm.getCityName();
		model.put("Report", "Weather Report As Requested for City / Temperature(F) / Time Zone: "+city+" / "+tempF+" / "+tz);
		model.put("City", city);
		model.put("Temp", temp);
		return "index";
	}
/*
@PutMapping("/update")
public String update(@RequestBody EmployeeModel modifiedEmployeeObject) {
	return "next";
}
*/
@RequestMapping("/next")
	public String next(Map<String, Object> model) {
		model.put("message", "You are in new page !!");// 
		return "next";
	}
	
	
	 

}