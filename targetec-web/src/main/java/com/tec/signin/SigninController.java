package com.tec.signin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tec.controller.BaseController;


@Controller
public class SigninController extends BaseController {
	
	@RequestMapping({ "/signin" })
	public String signin(HttpServletRequest request, Model model) {
		return "login";
	}

	@RequestMapping({ "/signout" })
	public String signout(HttpServletRequest request, Model model) {
		return "login";
	}

	
}
