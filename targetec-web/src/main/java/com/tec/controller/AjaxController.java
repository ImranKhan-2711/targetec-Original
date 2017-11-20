package com.tec.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tec.model.Account;
import com.tec.model.AccountDetail;
import com.tec.model.CategoryMaster;
import com.tec.model.EmailTemplate;
import com.tec.model.ResourceMaster;
import com.tec.model.ResponseBean;
import com.tec.model.TestMaster;
import com.tec.model.TypeMaster;
import com.tec.service.AccountService;
import com.tec.service.EmailService;
import com.tec.service.QuestionService;
import com.tec.service.ResourceService;
import com.tec.service.TestService;
import com.tec.tools.IConstants.EmailTempate;

@Controller
@RequestMapping({ "/ajax" })
@PropertySources(@PropertySource("classpath:com/tec/config/application.properties"))
public class AjaxController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(AjaxController.class);

	@Resource(name = "accountService")
	protected AccountService accountService;

	@Resource(name = "resourceService")
	protected ResourceService resourceService;

	@Resource(name = "questionService")
	protected QuestionService questionService;

	@Autowired
	protected JavaMailSender javaMailSender;

	@Resource(name = "testService")
	protected TestService testService;

	@Resource(name = "emailService")
	protected EmailService emailService;

	@RequestMapping(value = "/upload")
	@ResponseBody
	public String upload(Principal currentUser, HttpServletRequest request, Model model,
			@RequestParam("file") MultipartFile file) {
		String fileName = null;
		String ext = file.getOriginalFilename().split("\\.")[1];
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				File dir = new File(getProperty("server.resource.base") + "images/std_profile/");
				fileName = new Date().getTime() + "_image." + ext;
				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				return String.valueOf(resourceService.updateStudentProfile(fileName, ext));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@RequestMapping(value = "/uploadmedia")
	@ResponseBody
	public String uploadmedia(Principal currentUser, HttpServletRequest request, Model model,
			@RequestParam("file") MultipartFile file) {
		String fileName = null;
		String ext = file.getOriginalFilename().split("\\.")[1];
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				File dir = new File(getProperty("server.resource.base") + "media/std_profile/");
				fileName = new Date().getTime() + "_media." + ext;
				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				return String.valueOf(resourceService.updateStudentProfile(fileName, ext));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@RequestMapping(value = "/model/student/edit")
	public String editStudentModel(Principal currentUser, HttpServletRequest request, Model model) {
		if (currentUser != null) {
			String id = request.getParameter("id");

			if (id != null) {
				model.addAttribute("student", accountService.getStudent(Long.parseLong(id.toString())));
			}
			System.out.println("executed\n\n\n\n");
			return "ajaxstudentmodel";
		} else {
			return "login";
		}
	}

	@RequestMapping(value = "/model/student/update")
	public String updateStudentModel(Principal currentUser, HttpServletRequest request, Model model) {

		String id = request.getParameter("id");
		String accountId = request.getParameter("accountId");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String phoneNumber = request.getParameter("phoneNumber");
		String userName = request.getParameter("userName");
		String profileImage = request.getParameter("profileImage");
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("firstName", firstName);
		params.put("requestUrl", "targetec.com.au");
		params.put("userName", userName);
		String password = null;
		Account account = new Account();
		if (accountId != null && !accountId.equals("")) {
			account = accountService.getAccountById(Long.parseLong(accountId));
		} else {
			account.setUserName(userName);
			password = accountService.generatePassword(6, 1, 1, 1);
			params.put("password", password);
			account.setPassword(new BCryptPasswordEncoder(11).encode(password));
			EmailTemplate emailTemplate = accountService.getContent(EmailTempate.FIRST_LOGIN_CREDENTIALS);
			emailService.sendMail(emailTemplate, email, params);
			account = accountService.createStudent(account);
		}
		AccountDetail accountDetail = new AccountDetail();
		accountDetail.setAccount(account);
		if (id != null && !id.trim().equals("")) {
			accountDetail.setId(Long.parseLong(id));
		}
		accountDetail.setFirstName(firstName);
		accountDetail.setLastName(lastName);
		accountDetail.setEmail(email);
		accountDetail.setPhoneNumber(phoneNumber);
		accountDetail.setProfileImage(new ResourceMaster(Long.parseLong(profileImage)));

		accountService.updateStudent(accountDetail);

		return "ajaxstudentmodel";

	}

	@RequestMapping(value = "/model/trainer/edit")
	public String editTrainerModel(Principal currentUser, HttpServletRequest request, Model model) {
		if (currentUser != null) {
			String id = request.getParameter("id");

			if (id != null) {
				model.addAttribute("trainer", accountService.getTrainer(Long.parseLong(id.toString())));
			}
			return "ajaxtrainermodel";
		} else {
			return "login";
		}
	}

	@RequestMapping(value = "/model/trainer/update")
	public String updateTrainerModel(Principal currentUser, HttpServletRequest request, Model model) {
		Account account = new Account();
		String id = request.getParameter("id");
		String accountId = request.getParameter("accountId");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String phoneNumber = request.getParameter("phoneNumber");
		String userName = request.getParameter("userName");
		String profileImage = request.getParameter("profileImage");

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("firstName", firstName);
		params.put("requestUrl", "targetec.com.au");
		params.put("userName", userName);
		String password = null;
		if (accountId != null && !accountId.equals("")) {
			account = accountService.getAccountById(Long.parseLong(accountId));
		} else {
			account.setUserName(userName);
			password = accountService.generatePassword(6, 1, 1, 1);
			params.put("password", password);
			account.setPassword(new BCryptPasswordEncoder(11).encode(password));
			EmailTemplate emailTemplate = accountService.getContent(EmailTempate.FIRST_LOGIN_CREDENTIALS);
			emailService.sendMail(emailTemplate, email, params);
			account = accountService.createTrainer(account);
		}

		AccountDetail accountDetail = new AccountDetail();
		accountDetail.setAccount(account);
		if (id != null && !id.trim().equals("")) {
			accountDetail.setId(Long.parseLong(id));
		}
		accountDetail.setFirstName(firstName);
		accountDetail.setLastName(lastName);
		accountDetail.setEmail(email);
		accountDetail.setPhoneNumber(phoneNumber);
		accountDetail.setProfileImage(new ResourceMaster(Long.parseLong(profileImage)));

		accountService.updateTrainer(accountDetail);
		return "ajaxtrainermodel";
	}

	@RequestMapping(value = "/model/testlist/edit")
	public String editTestModel(Principal currentUser, HttpServletRequest request, Model model) {
		if (currentUser != null) {
			String id = request.getParameter("id");
			if (id != null) {
				model.addAttribute("testlist", testService.getTest(Long.parseLong(id.toString())));
				model.addAttribute("types", questionService.typeList());
				model.addAttribute("categories", questionService.categoryList());
			} else {
				model.addAttribute("types", questionService.typeList());
				model.addAttribute("categories", questionService.categoryList());
			}
			return "ajaxtestmodel";
		} else {
			return "login";
		}
	}

	@RequestMapping(value = "/model/test/update")
	public String updateTestModel(Principal currentUser, HttpServletRequest request, Model model) {
		TestMaster testMaster = new TestMaster();
		CategoryMaster categoryMaster = new CategoryMaster();
		TypeMaster typeMaster = new TypeMaster();
		ResourceMaster resourceMaster = new ResourceMaster((long) 10000006);
		String id = request.getParameter("id");
		String testName = request.getParameter("testName");
		String description = request.getParameter("description");
		String selectedTypeId = request.getParameter("selectedTypeId");
		String selectedCategoryId = request.getParameter("selectedCategoryId");
		String questionCount = request.getParameter("questionCount");
		String profileImage = request.getParameter("profileImage");
		String duration = request.getParameter("duration");
		testMaster.setDuration(Time.valueOf("02:05:00"));
		testMaster.setName(testName);
		testMaster.setDescription(description);
		typeMaster.setId(Long.parseLong(selectedTypeId));
		testMaster.setTypeMaster(typeMaster);
		categoryMaster.setId(Long.parseLong(selectedCategoryId));
		testMaster.setCategoryMaster(categoryMaster);
		testMaster.setQuestionCount(Integer.parseInt(questionCount == null ? "0" : questionCount));
		testMaster.setImage(resourceMaster);
		if (id != null && !id.equals("")) {
			testMaster.setId(Long.parseLong(id));
			testService.update(testMaster);
		} else {
			testService.insert(testMaster);
		}

		return "ajaxtestmodel";

	}

	@RequestMapping(value = "/update/map")
	public String updateMap(Principal currentUser, HttpServletRequest request, Model model) {
		if (currentUser != null) {
			String questionId = request.getParameter("questionId");
			HashMap<String, String> map = (HashMap<String, String>) request.getSession().getAttribute("questionMap");

			if (map != null) {
				for (HashMap.Entry<String, String> entry : map.entrySet()) {
					if (entry.getKey().equals(questionId)) {
						map.remove(entry.getKey());
						map.remove(entry.getValue());
						System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + map);
						request.getSession().setAttribute("questionMap", map);
						return "";
					}
				}
			}
			map.put(questionId, "Y");
			request.getSession().setAttribute("questionMap", map);

			return "";
		} else {
			return "login";
		}
	}

	@RequestMapping(value = "/student/delete")
	@ResponseBody
	public String deleteStudent(Principal currentUser, HttpServletRequest request) {
		ResponseBean responseBean = null;
		if (currentUser != null) {
			String id = request.getParameter("id");
			String pageNumber = request.getParameter("pageNumber");
			responseBean = accountService.delete(Long.parseLong(id));

		}
		return responseBean.getMessage();

	}

	@RequestMapping(value = "/trainer/delete")
	@ResponseBody
	public String deleteTrainer(Principal currentUser, HttpServletRequest request) {
		ResponseBean responseBean = null;
		if (currentUser != null) {
			String id = request.getParameter("id");
			String pageNumber = request.getParameter("pageNumber");
			responseBean = accountService.delete(Long.parseLong(id));

		}
		return responseBean.getMessage();

	}

	@RequestMapping(value = "/student/validation")
	@ResponseBody
	public String validateStudent(Principal currentUser, HttpServletRequest request) {
		String msg = "";
		if (currentUser != null) {
			String userName = request.getParameter("userName");
			String id = request.getParameter("id");
			if ((id == null) || (id.isEmpty())) {
				int count = accountService.getAccountCount(userName);
				System.out.println();
				if (count > 0) {
					msg = userName + " user name already exist";
				}
			}
		}
		return msg;

	}

	@RequestMapping(value = "/test/validation")
	@ResponseBody
	public String validateTest(Principal currentUser, HttpServletRequest request) {
		String msg = "";
		if (currentUser != null) {
			String testName = request.getParameter("testName");
			String id = request.getParameter("id");
			if ((id == null) || (id.isEmpty())) {
				int count = testService.getTestCount(testName);
				if (count > 0) {
					msg = testName + " test name already exist";
				}
			}
		}
		return msg;

	}

	@RequestMapping(value = "/question/delete")
	@ResponseBody
	public String deleteQuestion(Principal currentUser, HttpServletRequest request) {
		ResponseBean responseBean = null;
		System.out.println();
		if (currentUser != null) {
			String id = request.getParameter("id");
			String pageNumber = request.getParameter("pageNumber");
			responseBean = questionService.delete(Long.parseLong(id));
		}
		return responseBean.getMessage();

	}

	@RequestMapping(value = "/test/delete")
	@ResponseBody
	public String deleteTest(Principal currentUser, HttpServletRequest request) {
		ResponseBean responseBean = null;
		System.out.println();
		if (currentUser != null) {
			String id = request.getParameter("id");
			String pageNumber = request.getParameter("pageNumber");
			responseBean = testService.delete(Long.parseLong(id));
		}
		return responseBean.getMessage();

	}
}
