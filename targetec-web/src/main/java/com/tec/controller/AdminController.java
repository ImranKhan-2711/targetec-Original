package com.tec.controller;

import java.security.Principal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tec.model.Account;
import com.tec.model.AccountDetail;
import com.tec.model.AnswerMaster;
import com.tec.model.AnswerOptions;
import com.tec.model.MessageDetail;
import com.tec.model.QuestionMaster;
import com.tec.model.ResourceMaster;
import com.tec.model.StudentTest;
import com.tec.model.StyleMaster;
import com.tec.model.TestMaster;
import com.tec.model.TestQuestion;
import com.tec.model.TypeMaster;
import com.tec.service.AccountService;
import com.tec.service.AnswerService;
import com.tec.service.MessageService;
import com.tec.service.QuestionService;
import com.tec.service.TestQuestionService;
import com.tec.service.TestService;
import com.tec.service.TestStudentService;
import com.tec.template.Page;
import com.tec.tools.IConstants.QuestionStyle;
import com.tec.tools.IConstants.QuestionType;

@Controller
@RequestMapping({ "/admin" })
@PropertySources(@PropertySource("classpath:com/tec/config/application.properties"))
public class AdminController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Resource(name = "accountService")
	protected AccountService accountService;

	@Resource(name = "questionService")
	private QuestionService questionService;

	@Resource(name = "testService")
	private TestService testService;

	@Resource(name = "testQuestionService")
	private TestQuestionService testQuestionService;

	@Resource(name = "testStudentService")
	private TestStudentService testStudentService;

	@Resource(name = "messageService")
	private MessageService messageService;

	@Resource(name = "answerService")
	private AnswerService answerService;

	@RequestMapping(value = "/dashboard")
	public String home(Principal currentUser, HttpServletRequest request, Model model) {
		model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
		return "admindashboard";
	}

	@RequestMapping(value = "/students")
	public String listStudents(Principal currentUser, HttpServletRequest request, Model model) {
		if (currentUser != null) {

			// get page attributes
			pageSize = request.getParameter("pageSize") == null ? 10
					: Integer.parseInt(request.getParameter("pageSize"));
			pageNumber = request.getParameter("pageNumber") == null ? 1
					: Integer.parseInt(request.getParameter("pageNumber"));
			sortIndex = request.getParameter("sortIndex") == null ? "" : request.getParameter("sortIndex");
			sortOrder = request.getParameter("sortOrder") == null ? "asc" : request.getParameter("sortOrder");

			// get search parameters
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String mobileNumber = request.getParameter("mobileNumber");

			Page<AccountDetail> page = new Page<AccountDetail>(pageNumber, pageSize, -1, sortIndex, sortOrder);
			model.addAttribute("students", accountService.listStudents(page, firstName, lastName, mobileNumber));
			model.addAttribute("pageNumber", pageNumber);
			model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
			return "adminstudents";
		} else {
			return "login";
		}
	}

	@RequestMapping(value = "/trainers")
	public String listTrainers(Principal currentUser, HttpServletRequest request, Model model) {
		if (currentUser != null) {
			// get page attributes
			pageSize = request.getParameter("pageSize") == null ? 10
					: Integer.parseInt(request.getParameter("pageSize"));
			pageNumber = request.getParameter("pageNumber") == null ? 1
					: Integer.parseInt(request.getParameter("pageNumber"));
			sortIndex = request.getParameter("sortIndex") == null ? "" : request.getParameter("sortIndex");
			sortOrder = request.getParameter("sortOrder") == null ? "asc" : request.getParameter("sortOrder");

			// get search parameters
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String mobileNumber = request.getParameter("mobileNumber");

			Page<AccountDetail> page = new Page<AccountDetail>(pageNumber, pageSize, -1, sortIndex, sortOrder);
			model.addAttribute("trainers", accountService.listTrainers(page, firstName, lastName, mobileNumber));
			model.addAttribute("pageNumber", pageNumber);
			model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
			return "admintrainers";
		} else {
			return "login";
		}
	}

	@RequestMapping(value = "/testlist")
	public String listTest(Principal currentUser, HttpServletRequest request, Model model) {
		if (currentUser != null) {
			HashMap<String, String> map = new HashMap<String, String>();
			request.getSession().setAttribute("questionMap", map);
			request.getSession().removeAttribute("questionMap");
			// get page attributes
			pageSize = request.getParameter("pageSize") == null ? 10
					: Integer.parseInt(request.getParameter("pageSize"));
			pageNumber = request.getParameter("pageNumber") == null ? 1
					: Integer.parseInt(request.getParameter("pageNumber"));
			sortIndex = request.getParameter("sortIndex") == null ? "" : request.getParameter("sortIndex");
			sortOrder = request.getParameter("sortOrder") == null ? "asc" : request.getParameter("sortOrder");

			// get search parameters
			String testName = request.getParameter("testName");
			String description = request.getParameter("description");
			String typeName = request.getParameter("typeName");
			String categoryName = request.getParameter("categoryName");

			Page<TestMaster> page = new Page<TestMaster>(pageNumber, pageSize, -1, sortIndex, sortOrder);
			model.addAttribute("testlist", testService.listTest(page, testName, description, typeName, categoryName));
			model.addAttribute("pageNumber", pageNumber);
			model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
			return "admintest";
		} else {
			return "login";
		}
	}

	@RequestMapping(value = "/questions")
	public String questionList(Principal currentUser, HttpServletRequest request, Model model) {
		String typeId = request.getParameter("typeId");
		// get page attributes
		pageSize = request.getParameter("pageSize") == null ? 20 : Integer.parseInt(request.getParameter("pageSize"));
		pageNumber = request.getParameter("pageNumber") == null ? 1
				: Integer.parseInt(request.getParameter("pageNumber"));
		sortIndex = request.getParameter("sortIndex") == null ? "" : request.getParameter("sortIndex");
		sortOrder = request.getParameter("sortOrder") == null ? "asc" : request.getParameter("sortOrder");

		Page<QuestionMaster> page = new Page<QuestionMaster>(pageNumber, pageSize, -1, sortIndex, sortOrder);
		model.addAttribute("questions", questionService.listQuestions(page, Long.parseLong(typeId)));
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
		if (QuestionType.WRITING.equals(Long.parseLong(typeId))) {
			return "adminwlist";
		} else if (QuestionType.LISTENING.equals(Long.parseLong(typeId))) {
			return "adminllist";
		} else if (QuestionType.SPEAKING.equals(Long.parseLong(typeId))) {
			return "adminslist";
		} else {
			return "adminrlist";
		}

	}

	@RequestMapping(value = "/form/test/update")
	public String updateTestForm(Principal currentUser, HttpServletRequest request, Model model) {
		QuestionMaster questionMaster = new QuestionMaster();
		TypeMaster typeMaster = new TypeMaster();
		StyleMaster styleMaster = new StyleMaster();
		List<AnswerMaster> list = new ArrayList<AnswerMaster>();
		AnswerMaster answerMaster = null;
		AnswerOptions answerOptions = null;
		List<AnswerOptions> answerOptionsList = null;
		String status = request.getParameter("status");
		String resourceId = request.getParameter("resourceId");
		String questionId = request.getParameter("questionId");
		String styleId = request.getParameter("styleId");
		String typeId = request.getParameter("typeId");
		String[] answers = request.getParameterValues("answer");
		String[] answerId = request.getParameterValues("answerId");
		String[] opts = request.getParameterValues("opts");
		String[] optsId = request.getParameterValues("optsId");
		String[] indx = request.getParameterValues("indx");
		String[] correctYN = request.getParameterValues("correctYN");
		String direction = request.getParameter("direction");
		String question = request.getParameter("questionText");
		String description = request.getParameter("description");
		String duration = request.getParameter("duration");
		String seqNo[] = request.getParameterValues("seqNo");
		String ansdesc[] = request.getParameterValues("ansdesc");
		String questionCode = request.getParameter("questionCode");
		if (answers != null && !answers.equals("")) {
			for (int i = 0; i < answers.length; i++) {
				answerOptionsList = new ArrayList<AnswerOptions>();
				answerMaster = new AnswerMaster();
				if (QuestionStyle.MULTICHOICE.equals(Long.parseLong(styleId))
						|| QuestionStyle.MULTICHOICE_L.equals(Long.parseLong(styleId))) {
					if (correctYN != null && !correctYN.equals("")) {
						for (int j = 0; j < correctYN.length; j++) {
							if (i == Integer.parseInt(correctYN[j])) {
								answerMaster.setCorrectYN("Y");
								break;
							} else {
								answerMaster.setCorrectYN("N");
							}
						}
					} else {
						answerMaster.setCorrectYN("N");
					}
				} else if (QuestionStyle.DROPDOWN.equals(Long.parseLong(styleId))) {
					for (int j = 0; j < opts.length; j++) {
						if (i == Integer.parseInt(indx[j])) {
							answerOptions = new AnswerOptions();
							answerOptions.setAnsOption(opts[j]);
							if (optsId[j] != null && !optsId[j].equals("")) {
								answerOptions.setId(Long.parseLong(optsId[j]));
							}
							answerOptionsList.add(answerOptions);
						}
					}
					answerMaster.setCorrectYN("N");
				} else {
					answerMaster.setCorrectYN("N");
				}

				if (answerId[i] != null && !answerId[i].equals("")) {
					answerMaster.setId(Long.parseLong(answerId[i]));
				}
				answerMaster.setDescription(ansdesc[i]);
				answerMaster.setSeqNo(seqNo[i]);
				answerMaster.setAnswer(answers[i]);
				answerMaster.setOptions(answerOptionsList);
				list.add(answerMaster);
			}
		}
		if (questionId != null && !questionId.equals("")) {
			questionMaster.setId(Long.parseLong(questionId));
		}
		typeMaster.setId(Long.parseLong(typeId));
		styleMaster.setId(Long.parseLong(styleId));
		questionMaster.setAnswers(list);
		questionMaster.setDirection(direction);
		questionMaster.setQuestionText(question);
		questionMaster.setStyleMaster(styleMaster);
		questionMaster.setTypeMaster(typeMaster);
		questionMaster.setDescription(description);
		questionMaster.setQuestionCode(questionCode);
		if (status != null && !status.equals("")) {
			questionMaster.setStatus(status);
		}
		if (resourceId != null && !resourceId.equals("")) {
			questionMaster.setResourceMaster(new ResourceMaster(Long.parseLong(resourceId)));
		}
		questionMaster.setDuration(Time.valueOf(duration));

		QuestionMaster questionMasterBean = questionService.updateQuestion(questionMaster);

		model.addAttribute("question", questionMasterBean);
		model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
		model.addAttribute("msg", "Saved Sucessfully");
		if (QuestionStyle.SINGLETEXT.equals(Long.parseLong(styleId))) {
			return "adminlisteningform1";
		} else if (QuestionStyle.MULTICHOICE_L.equals(Long.parseLong(styleId))) {
			return "adminlisteningform2";
		} else if (QuestionStyle.MULTITEXT.equals(Long.parseLong(styleId))) {
			return "adminlisteningform3";
		} else if (QuestionStyle.DIFFERTRANSC.equals(Long.parseLong(styleId))) {
			return "adminlisteningform4";
		} else if (QuestionStyle.WRITING_STYLE.equals(Long.parseLong(styleId))) {
			return "adminwritingform1";
		} else if (QuestionStyle.SPEAKING_STYLE.equals(Long.parseLong(styleId))) {
			return "adminspeakingform1";
		} else if (QuestionStyle.DESCRIBE_IMAGE.equals(Long.parseLong(styleId))) {
			return "adminspeakingform2";
		} else if (QuestionStyle.REPEAT_SENTENCE.equals(Long.parseLong(styleId))) {
			return "adminspeakingform3";
		} else if (QuestionStyle.DRAGTOCORRECT.equals(Long.parseLong(styleId))) {
			return "adminreadingform2";
		} else if (QuestionStyle.DRAGTOSORT.equals(Long.parseLong(styleId))) {
			return "adminreadingform3";
		} else if (QuestionStyle.DROPDOWN.equals(Long.parseLong(styleId))) {
			return "adminreadingform4";
		} else {
			return "adminreadingform1";
		}
	}

	@RequestMapping(value = "/reading/answer/add/description")
	public String addAnswerDescription(Principal currentUser, HttpServletRequest request, Model model) {
		String questionId = request.getParameter("questionId");
		model.addAttribute("question", questionService.findQuestionBean(Long.parseLong(questionId)));
		model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
		return "adminreadingansdesc";
	}

	@RequestMapping(value = "/ansdetail/update")
	public String ansDetailUpdate(Principal currentUser, HttpServletRequest request, Model model) {
		String ansId[] = request.getParameterValues("ansid");
		String ansdesc[] = request.getParameterValues("ansdesc");
		String questionId = request.getParameter("qId");
		AnswerMaster answerMaster = null;
		for (int i = 0; i < ansId.length; i++) {
			answerMaster = new AnswerMaster();
			answerMaster = answerService.findAnswer(Long.parseLong(ansId[i]));
			answerMaster.setDescription(ansdesc[i]);
			answerService.updateAnswer(answerMaster);
		}
		model.addAttribute("question", questionService.findQuestionBean(Long.parseLong(questionId)));
		model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
		return "adminreadingansdesc";
	}

	@RequestMapping(value = "/reading/question/form/edit")
	public String readQquestion(Principal currentUser, HttpServletRequest request, Model model) {
		String styleId = request.getParameter("styleId");
		String questionId = request.getParameter("questionId");
		if (questionId == null || questionId == "") {
			model.addAttribute("question", questionService.findQuestionBean(null));
		} else {
			model.addAttribute("question", questionService.findQuestionBean(Long.parseLong(questionId)));
		}
		model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
		if (QuestionStyle.DRAGTOCORRECT.equals(Long.parseLong(styleId))) {
			return "adminreadingform2";
		} else if (QuestionStyle.DRAGTOSORT.equals(Long.parseLong(styleId))) {
			return "adminreadingform3";
		} else if (QuestionStyle.DROPDOWN.equals(Long.parseLong(styleId))) {
			return "adminreadingform4";
		} else {
			return "adminreadingform1";
		}

	}

	@RequestMapping(value = "/listening/question/form/edit")
	public String listenQuestion(Principal currentUser, HttpServletRequest request, Model model) {
		String styleId = request.getParameter("styleId");
		String questionId = request.getParameter("questionId");
		if (questionId == null || questionId == "") {
			model.addAttribute("question", questionService.findQuestionBean(null));
		} else {
			model.addAttribute("question", questionService.findQuestionBean(Long.parseLong(questionId)));
		}
		model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
		if (QuestionStyle.MULTICHOICE_L.equals(Long.parseLong(styleId))) {
			return "adminlisteningform2";
		} else if (QuestionStyle.MULTITEXT.equals(Long.parseLong(styleId))) {
			return "adminlisteningform3";
		} else if (QuestionStyle.DIFFERTRANSC.equals(Long.parseLong(styleId))) {
			return "adminlisteningform4";
		} else {
			return "adminlisteningform1";
		}
	}

	@RequestMapping(value = "/writing/question/form/edit")
	public String writingQuestion(Principal currentUser, HttpServletRequest request, Model model) {
		String styleId = request.getParameter("styleId");
		String questionId = request.getParameter("questionId");
		if (questionId == null || questionId == "") {
			model.addAttribute("question", questionService.findQuestionBean(null));
		} else {
			model.addAttribute("question", questionService.findQuestionBean(Long.parseLong(questionId)));
		}
		model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
		return "adminwritingform1";
	}

	@RequestMapping(value = "/speaking/question/form/edit")
	public String speakingQuestion(Principal currentUser, HttpServletRequest request, Model model) {
		String styleId = request.getParameter("styleId");
		String questionId = request.getParameter("questionId");
		if (questionId == null || questionId == "") {
			model.addAttribute("question", questionService.findQuestionBean(null));
		} else {
			model.addAttribute("question", questionService.findQuestionBean(Long.parseLong(questionId)));
		}
		model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
		if (QuestionStyle.DESCRIBE_IMAGE.equals(Long.parseLong(styleId))) {
			return "adminspeakingform2";
		} else if (QuestionStyle.REPEAT_SENTENCE.equals(Long.parseLong(styleId))) {
			return "adminspeakingform3";
		} else {
			return "adminspeakingform1";
		}
	}

	@RequestMapping(value = "/form/test/question/edit")
	public String testQuestion(Principal currentUser, HttpServletRequest request, Model model) {

		String typeId = request.getParameter("typeId");
		String testId = request.getParameter("testId");
		String[] correctYN = request.getParameterValues("correctYN");
		String[] questionId = request.getParameterValues("questionId");
		System.out.println("page no >>>>>>>>>>>>>>> " + request.getParameter("pageNumber"));
		List<TestQuestion> testQuestions = null;

		HashMap<String, String> map = (HashMap<String, String>) request.getSession().getAttribute("questionMap");
		pageSize = request.getParameter("pageSize") == null ? 10 : Integer.parseInt(request.getParameter("pageSize"));
		pageNumber = request.getParameter("pageNumber") == null ? 1
				: Integer.parseInt(request.getParameter("pageNumber"));
		sortIndex = request.getParameter("sortIndex") == null ? "" : request.getParameter("sortIndex");
		sortOrder = request.getParameter("sortOrder") == null ? "asc" : request.getParameter("sortOrder");

		Page<QuestionMaster> page = new Page<QuestionMaster>(pageNumber, pageSize, -1, sortIndex, sortOrder);

		if (typeId != null && !typeId.equals("")) {
			List<QuestionMaster> questionMasters = questionService.listQuestions(page, Long.parseLong(typeId))
					.getPageItems();
			model.addAttribute("questions", questionMasters);
			model.addAttribute("test", testService.getTest(Long.parseLong(testId.toString())));
			if (map == null) {
				map = new HashMap<String, String>();
				testQuestions = testQuestionService.findList(Long.parseLong(testId.toString()));
				for (TestQuestion testQuestion : testQuestions) {
					String qid = testQuestion.getQuestionMaster().getId().toString();
					map.put(testQuestion.getQuestionMaster().getId().toString(), "Y");
				}
			} else {
				testQuestions = new ArrayList<TestQuestion>();
				for (HashMap.Entry<String, String> entry : map.entrySet()) {
					TestQuestion testQuestion = new TestQuestion();
					testQuestion.setQuestionMaster(questionService.findQuestionBean(Long.parseLong(entry.getKey())));
					testQuestion.setTestMaster(testService.getTest(Long.parseLong(testId.toString())));
					testQuestions.add(testQuestion);
				}
			}
			model.addAttribute("testquestion", testQuestions);
			model.addAttribute("questionMap", map);
			model.addAttribute("pageNumber", pageNumber);
		}
		request.getSession().setAttribute("questionMap", map);
		model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
		return "admintestquestionform1";
	}

	@RequestMapping(value = "/form/test/question/update")
	public String updateTestQuestionForm(Principal currentUser, HttpServletRequest request, Model model) {
		TestMaster testMaster = null;
		String[] correctYN = request.getParameterValues("correctYN");
		String[] questionId = request.getParameterValues("questionId");
		String testId = request.getParameter("testId");
		QuestionMaster questionMaster = null;
		TestQuestion testQuestion = null;
		Time time = Time.valueOf("00:00:00");
		int hours = 0;
		int mints = 0;
		int sec = 0;
		HashMap<String, String> map = (HashMap<String, String>) request.getSession().getAttribute("questionMap");

		System.out.println(">>>>>>>>>>>>>>during update>>>>>>>>>>>>>>>>>>>>" + map);
		testQuestionService.delete(Long.parseLong(testId));
		// if (questionId != null && !questionId.equals("")) {
		// for (int i = 0; i < questionId.length; i++) {
		// for (int j = 0; j < correctYN.length; j++) {
		// if (i == Integer.parseInt(correctYN[j])) {
		// map.put(questionId[i], "Y");
		// }
		// }
		// }
		// }
		for (HashMap.Entry<String, String> entry : map.entrySet()) {
			testQuestion = new TestQuestion();
			testQuestion.setQuestionMaster(questionService.findQuestionBean(Long.parseLong(entry.getKey())));
			testQuestion.setTestMaster(testService.getTest(Long.parseLong(testId.toString())));
			questionMaster = questionService.findQuestionBean(Long.parseLong(entry.getKey()));
			hours = hours + (questionMaster.getDuration() == null ? 0 : questionMaster.getDuration().getHours());
			mints = mints + (questionMaster.getDuration() == null ? 0 : questionMaster.getDuration().getMinutes());
			sec = sec + (questionMaster.getDuration() == null ? 0 : questionMaster.getDuration().getSeconds());
			testQuestionService.insert(testQuestion);
		}

		time.setHours(hours);
		time.setMinutes(mints);
		time.setSeconds(sec);

		model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
		testMaster = testService.getTest(Long.parseLong(testId));
		testMaster.setQuestionCount(map.size());
		testMaster.setDuration(time);
		testService.update(testMaster);
		String redirectUrl = "/admin/testlist";
		return "redirect:" + redirectUrl;

	}

	@RequestMapping(value = "/form/test/student/edit")
	public String testStudent(Principal currentUser, HttpServletRequest request, Model model) {
		String testId = request.getParameter("testId");
		pageSize = request.getParameter("pageSize") == null ? 10 : Integer.parseInt(request.getParameter("pageSize"));
		pageNumber = request.getParameter("pageNumber") == null ? 1
				: Integer.parseInt(request.getParameter("pageNumber"));
		sortIndex = request.getParameter("sortIndex") == null ? "" : request.getParameter("sortIndex");
		sortOrder = request.getParameter("sortOrder") == null ? "asc" : request.getParameter("sortOrder");
		// get search parameters
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String mobileNumber = request.getParameter("mobileNumber");

		Page<AccountDetail> page = new Page<AccountDetail>(pageNumber, pageSize, -1, sortIndex, sortOrder);
		model.addAttribute("test", testService.getTest(Long.parseLong(testId.toString())));
		model.addAttribute("students", accountService.findStudentsfindStudentsWithoutTestId(page, firstName, lastName,
				mobileNumber, Long.parseLong(testId)));
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
		return "adminteststudentform1";
	}

	@RequestMapping(value = "/form/test/student/update")
	public String updateTestStudentForm(Principal currentUser, HttpServletRequest request, Model model) {
		String[] correctYN = request.getParameterValues("correctYN");
		String[] studentId = request.getParameterValues("studentId");
		String[] accountId = request.getParameterValues("accountId");
		String testId = request.getParameter("testId");
		StudentTest studentTest = null;
		for (int i = 0; i < studentId.length; i++) {
			for (int j = 0; j < correctYN.length; j++) {
				if (i == Integer.parseInt(correctYN[j])) {
					studentTest = new StudentTest();
					studentTest.setTestMaster(new TestMaster(Long.parseLong(testId)));
					studentTest.setAccount(new Account(Long.parseLong(accountId[i])));
					testStudentService.insert(studentTest);
				}
			}
		}
		model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
		String redirectUrl = "/admin/testlist";
		return "redirect:" + redirectUrl;

	}

	@RequestMapping(value = "/profile")
	public String profile(Principal currentUser, HttpServletRequest request, Model model) {
		if (currentUser != null) {

			model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
			return "adminprofile";
		} else {
			return "login";
		}
	}

	@RequestMapping(value = "/profile/update")
	public String updateProfile(Principal currentUser, HttpServletRequest request, Model model) {

		String id = request.getParameter("id");
		String accountId = request.getParameter("accountId");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String phoneNumber = request.getParameter("phoneNumber");
		String userName = currentUser.getName();
		String profileImage = request.getParameter("resourceId");
		Account account = new Account();
		AccountDetail accountDetail = new AccountDetail();
		if (accountId != null && !accountId.equals("")) {
			account = accountService.getAccountById(Long.parseLong(accountId));
		} else {
			account.setUserName(userName);
			account.setPassword(new BCryptPasswordEncoder(11).encode("gill0891"));
			account = accountService.createTrainer(account);
		}
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
		String redirectUrl = "/admin/profile";
		return "redirect:" + redirectUrl;

	}

	@RequestMapping(value = "/logout")
	public String logout(Principal currentUser, HttpServletRequest request, Model model) {

		return "redirect:/signout";
	}

	@RequestMapping(value = "/changepassword")
	public String changepassword(Principal currentUser, HttpServletRequest request, Model model) {
		model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
		return "adminchangepassword";
	}

	@RequestMapping(value = "/password/update")
	public String updatePassword(Principal currentUser, HttpServletRequest request, Model model) {
		String currentPassword = request.getParameter("currentPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");
		Account account = accountService.getAccount(currentUser.getName());

		String pattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[-_@$!]).{6,}";
		if (newPassword.matches("\\s+")) {
			System.out.println("Password should not contain any spaces");
		} else if (!newPassword.matches(pattern)) {
			System.out.println(
					"Passwords must contain at least six characters, including uppercase, lowercase letters,one special character from (-,_,@,$,!) and numbers");
		} else if (currentPassword.equals(newPassword)) {
			System.out.println("Current Password and New password can't be same");
		} else if (!newPassword.equals(confirmPassword)) {
			System.out.println("Password confirmation must match Password");
		}
		account.setPassword(new BCryptPasswordEncoder(11).encode(newPassword));
		accountService.update(account);
		return "redirect:/signin";
	}

	@RequestMapping(value = "/mailbox")
	public String mailbox(Principal currentUser, HttpServletRequest request, Model model) {
		pageSize = request.getParameter("pageSize") == null ? 10 : Integer.parseInt(request.getParameter("pageSize"));
		pageNumber = request.getParameter("pageNumber") == null ? 1
				: Integer.parseInt(request.getParameter("pageNumber"));
		sortIndex = request.getParameter("sortIndex") == null ? "" : request.getParameter("sortIndex");
		sortOrder = request.getParameter("sortOrder") == null ? "asc" : request.getParameter("sortOrder");
		// get search parameters
		String subject = request.getParameter("subject");
		String message = request.getParameter("message");
		Page<MessageDetail> page = new Page<MessageDetail>(pageNumber, pageSize, -1, sortIndex, sortOrder);
		model.addAttribute("messagedetail", messageService.listMessages(page, subject, message, currentUser.getName()));
		model.addAttribute("fromaccount", accountService.getAccountByUserName(currentUser.getName()));
		model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
		return "adminmailbox";
	}

	@RequestMapping(value = "/compose")
	public String composemail(Principal currentUser, HttpServletRequest request, Model model) {
		model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
		return "adminmailcompose";
	}

}
