package com.tec.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@RequestMapping(value = "/")
	public String home() {
		return "redirect:/signin";
	}

	@RequestMapping(value = "/userdefault")
	public String userdefault(Principal currentUser, HttpServletRequest request) {
		if (currentUser != null) {
			String userRole = findLoginUserRole();
			System.out.println("role"+userRole);
			if (userRole.equals("ADMIN_ROLE")) {
				return "redirect:/admin/dashboard";
			}else
			if (userRole.equals("STUDENT_ROLE")) {
				return "redirect:/student/dashboard";
			} else {
				return "redirect:/signin";
			}
		} else {
			return "login";
		}
	}
	
	private String findLoginUserRole() {
		return SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities().toArray()[0].toString();
	}
}