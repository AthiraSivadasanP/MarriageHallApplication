package com.hall.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hall.booking.model.Employee;
import com.hall.booking.model.LoginUser;
import com.hall.booking.services.EmployeeService;
import com.hall.booking.services.MarriageHallService;

@Controller
public class LoginController {

	@Autowired
	private MarriageHallService marrHallService;
	public static long
	lid=0;
	@GetMapping("/logout")
	public String logout() {
		return "new_login";
	}
	@GetMapping("/")
	public String index() {
		return "welcome";
	}
	
 
	
	@RequestMapping(value = "/showLoginForm",method = RequestMethod.GET)
	public String showLoginForm(Model model) {
		// create model attribute to bind form data
	 
		return "new_login";
	}
 
 	@PostMapping("/saveLogin")
 	public String saveLogin(@RequestParam("username") String username,@RequestParam("password") String password) {
 		// save employee to database
 		List<LoginUser> users=marrHallService.getAllLogins();
 		String role="";
 		boolean flag=false;
 		for(LoginUser lu:users) {
 			if(lu.getUsername().equals(username)&&lu.getPassword().equals(password)) {
 				flag=true;
 				role=lu.getRole();
 				 lid=lu.getId();
 				break;
 			}
 		}
 		if(flag&&role.equals("admin"))
 		 
 			return "redirect:/halls";
 		else if(flag&&role.equals("customer")) {
 			return "redirect:/halls_user";
 		}
 		else
 			return "new_login";
 	}
 	
	
}
