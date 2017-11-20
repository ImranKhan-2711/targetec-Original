package com.tec.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
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
import com.tec.model.QuestionMaster;
import com.tec.model.ResourceMaster;
import com.tec.model.StudentTestAnswer;
import com.tec.model.StudentTestDetail;
import com.tec.model.TestMaster;
import com.tec.service.AccountService;
import com.tec.service.AnswerOptionService;
import com.tec.service.AnswerService;
import com.tec.service.QuestionService;
import com.tec.service.ResourceService;
import com.tec.service.TestQuestionService;
import com.tec.service.TestService;
import com.tec.service.TestStudentService;
import com.tec.template.Page;
import com.tec.tools.IConstants.QuestionStyle;

@Controller
@RequestMapping({ "/student" })
@PropertySources(@PropertySource("classpath:com/tec/config/application.properties"))
public class StudentController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

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

	@Resource(name = "answerService")
	private AnswerService answerService;

	@Resource(name = "answerOptionService")
	private AnswerOptionService answerOptionService;

	@Resource(name = "resourceService")
	private ResourceService resourceService;

	@RequestMapping(value = "/dashboard")
	public String home(Principal currentUser, HttpServletRequest request, Model model) {
		model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
		return "studentdashboard";
	}

	@RequestMapping(value = "/testlist")
	public String listTest(Principal currentUser, HttpServletRequest request, Model model) {
		if (currentUser != null) {
			request.getSession().removeAttribute("questions");
			request.getSession().removeAttribute("testId");
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
			model.addAttribute("testlist", testService.listTestAssignToStudent(currentUser.getName(), page, testName,
					description, typeName, categoryName));
			model.addAttribute("pageNumber", pageNumber);
			model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
			return "studenttest";
		} else {
			return "login";
		}
	}

	@RequestMapping(value = "/detaillist")
	public String listDetail(Principal currentUser, HttpServletRequest request, Model model) {
		if (currentUser != null) {
			request.getSession().removeAttribute("questions");
			pageSize = request.getParameter("pageSize") == null ? 10
					: Integer.parseInt(request.getParameter("pageSize"));
			pageNumber = request.getParameter("pageNumber") == null ? 1
					: Integer.parseInt(request.getParameter("pageNumber"));
			sortIndex = request.getParameter("sortIndex") == null ? "" : request.getParameter("sortIndex");
			sortOrder = request.getParameter("sortOrder") == null ? "asc" : request.getParameter("sortOrder");

			String testTakenDate = request.getParameter("testTakenDate");
			String testName = request.getParameter("testName");
			String totalQuestion = request.getParameter("totalQuestion");
			String correct = request.getParameter("correct");
			String incorrect = request.getParameter("incorrect");
			String score = request.getParameter("score");
			String percentage = request.getParameter("percentage");

			Page<StudentTestDetail> page = new Page<StudentTestDetail>(pageNumber, pageSize, -1, sortIndex, sortOrder);
			model.addAttribute("testdetaillist", testService.listTestDetail(testTakenDate, testName, totalQuestion,
					correct, incorrect, score, percentage, page, currentUser.getName()));
			model.addAttribute("pageNumber", pageNumber);
			model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));

			return "studenttestdetail";
		} else {
			return "login";
		}
	}

	@RequestMapping(value = "/testdetaillive")
	public String testDetailLive(Principal currentUser, HttpServletRequest request, Model model) {

		List<QuestionMaster> questions = null;
		StudentTestDetail studentTestDetail = testService
				.listTestDetailByTestDetailId(Long.parseLong(request.getParameter("testDetailId")));
		TestMaster testMaster = testService.getTest(studentTestDetail.getTestMaster().getId());
		String hours = testMaster.getDuration().getHours() + "";
		String mints = testMaster.getDuration().getMinutes() + "";
		String seconds = testMaster.getDuration().getSeconds() + "";
		model.addAttribute("hours", hours);
		model.addAttribute("mints", mints);
		model.addAttribute("seconds", seconds);
		String lpqNext = request.getParameter("lpq");
		request.getSession().setAttribute("lpq", lpqNext);
		Integer lpq = null;
		ResourceMaster resourceMaster = null;
		model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
		if (currentUser != null) {
			if (request.getSession().getAttribute("questions") == null) {
				request.getSession().setAttribute("lpq", null);
				if (request.getParameter("testDetailId") != null && !request.getParameter("testDetailId").equals("")) {
					questions = testStudentService
							.listTestQuestionsByDetailId(Long.parseLong(request.getParameter("testDetailId")));
					request.getSession().setAttribute("questions", questions);
				}
			} else {
				questions = (List<QuestionMaster>) request.getSession().getAttribute("questions");
			}
			if (request.getSession().getAttribute("lpq") == null) {
				lpq = -1;
			} else {
				lpq = Integer.parseInt(request.getSession().getAttribute("lpq").toString());
			}
			if (lpq + 1 == questions.size()) {
				return "redirect:detaillist";
			}
			QuestionMaster question = questions.get(lpq + 1);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>::::::::::" + question.getStyleMaster().getName());
			if (question.getStyleMaster().getName().equals("DRAGTOCORRECT")
					|| question.getStyleMaster().getName().equals("DROPDOWN")) {
				Page<AnswerMaster> page = new Page<AnswerMaster>("");
				List<AnswerMaster> ansMaster = answerService.findAnswers(page, question.getId()).getPageItems();
				String qt = question.getQuestionText();
				String[] parts = qt.split("<b>");
				for (int i = 1; i < parts.length; ++i) {
					parts[i] = parts[i].substring(parts[i].indexOf("</b>") + 4);
				}
				String finalString = "";
				for (int i = 1; i < parts.length; ++i) {
					finalString += "<input class=\"btn btn-default disabled\" id=\"btn_" + i + "\"  value=\"" + i
							+ "\"  disabled=\"disabled\" >" + parts[i];
				}
				finalString = "<p>" + parts[0] + finalString + "</p>";
				System.out.println(finalString + "\n\n\n\n\n");
				question.setQuestionText(finalString);
				question.setAnswers(ansMaster);
				model.addAttribute("studentTestDetail", studentTestDetail);
				model.addAttribute("question", question);
				model.addAttribute("lpq", lpq + 1);

				return "livedetailrdc";
			} else if (question.getStyleMaster().getName().equals("WRITING")) {
				Page<AnswerMaster> page = new Page<AnswerMaster>("");
				List<AnswerMaster> ansMaster = answerService.findAnswers(page, question.getId()).getPageItems();
				Page<StudentTestAnswer> page1 = new Page<StudentTestAnswer>("");
				List<StudentTestAnswer> studentTestAnswers = answerService.findStudentTestAnswers(page1,
						question.getId(), studentTestDetail.getTestMaster().getId(), studentTestDetail.getId())
						.getPageItems();
				String qt = question.getQuestionText();
				String[] parts = qt.split("<b>");
				for (int i = 1; i < parts.length; ++i) {
					parts[i] = parts[i].substring(parts[i].indexOf("</b>") + 4);
				}
				String finalString = "";
				for (int i = 1; i < parts.length; ++i) {
					finalString += "<input class=\"btn btn-default disabled\" id=\"btn_" + i + "\"  value=\"" + i
							+ "\"  disabled=\"disabled\" >" + parts[i];
				}
				finalString = "<p>" + parts[0] + finalString + "</p>";
				System.out.println(finalString + "\n\n\n\n\n");
				question.setQuestionText(finalString);
				question.setAnswers(ansMaster);
				model.addAttribute("studentTestDetail", studentTestDetail);
				model.addAttribute("studentAnswers", studentTestAnswers);
				model.addAttribute("question", question);
				model.addAttribute("lpq", lpq + 1);

				return "livedetailwriting";
			} else if (question.getStyleMaster().getName().equals("MULTICHOICE_LISTENING")) {
				Page<AnswerMaster> page = new Page<AnswerMaster>("");
				List<AnswerMaster> ansMaster = answerService.findAnswers(page, question.getId()).getPageItems();
				int count = 0;
				for (AnswerMaster a : ansMaster) {
					if (a.getCorrectYN().equals("Y")) {
						count++;
					}
				}
				question.setAnswers(ansMaster);
				model.addAttribute("question", question);
				model.addAttribute("studentTestDetail", studentTestDetail);
				model.addAttribute("lpq", lpq + 1);
				if (count > 1) {
					return "livedetaillmc";
				} else {
					return "livedetaillmcsingle";
				}
			} else if (question.getStyleMaster().getName().equals("MULTITEXT")
					|| question.getStyleMaster().getName().equals("DIFFERTRANSC")) {
				Page<AnswerMaster> page = new Page<AnswerMaster>("");
				List<AnswerMaster> ansMaster = answerService.findAnswers(page, question.getId()).getPageItems();
				String qt = question.getQuestionText();
				String[] parts = qt.split("<b>");
				for (int i = 1; i < parts.length; ++i) {
					parts[i] = parts[i].substring(parts[i].indexOf("</b>") + 4);
				}
				String finalString = "";
				for (int i = 1; i < parts.length; ++i) {
					finalString += "<input class=\"btn btn-default disabled\" id=\"btn_" + i + "\"  value=\"" + i
							+ "\"  disabled=\"disabled\" >" + parts[i];
				}
				finalString = "<p>" + parts[0] + finalString + "</p>";
				System.out.println(finalString + "\n\n\n\n\n");
				question.setQuestionText(finalString);
				question.setAnswers(ansMaster);
				model.addAttribute("studentTestDetail", studentTestDetail);
				model.addAttribute("question", question);
				model.addAttribute("lpq", lpq + 1);

				return "livedetaillmt";

			} else if (question.getStyleMaster().getName().equals("SINGLETEXT")) {
				Page<AnswerMaster> page = new Page<AnswerMaster>("");
				List<AnswerMaster> ansMaster = answerService.findAnswers(page, question.getId()).getPageItems();
				Page<StudentTestAnswer> page1 = new Page<StudentTestAnswer>("");
				List<StudentTestAnswer> studentTestAnswers = answerService.findStudentTestAnswers(page1,
						question.getId(), studentTestDetail.getTestMaster().getId(), studentTestDetail.getId())
						.getPageItems();
				String qt = question.getQuestionText();
				String[] parts = qt.split("<b>");
				for (int i = 1; i < parts.length; ++i) {
					parts[i] = parts[i].substring(parts[i].indexOf("</b>") + 4);
				}
				String finalString = "";
				for (int i = 1; i < parts.length; ++i) {
					finalString += "<input class=\"btn btn-default disabled\" id=\"btn_" + i + "\"  value=\"" + i
							+ "\"  disabled=\"disabled\" >" + parts[i];
				}
				finalString = "<p>" + parts[0] + finalString + "</p>";
				System.out.println(finalString + "\n\n\n\n\n");
				question.setQuestionText(finalString);
				question.setAnswers(ansMaster);
				model.addAttribute("studentTestDetail", studentTestDetail);
				model.addAttribute("studentAnswers", studentTestAnswers);
				model.addAttribute("question", question);
				model.addAttribute("lpq", lpq + 1);

				return "livedetaillst";
			} else if (question.getStyleMaster().getName().equals("WRITING")) {

			} else if (question.getStyleMaster().getName().equals("DRAGTOSORT")) {
				Page<AnswerMaster> page = new Page<AnswerMaster>("");
				Page<StudentTestAnswer> page1 = new Page<StudentTestAnswer>("");
				List<AnswerMaster> ansMaster = answerService.findAnswers(page, question.getId()).getPageItems();
				List<StudentTestAnswer> studentTestAnswers = answerService.findStudentTestAnswers(page1,
						question.getId(), studentTestDetail.getTestMaster().getId(), studentTestDetail.getId())
						.getPageItems();
				int count = 0;
				for (AnswerMaster a : ansMaster) {
					if (a.getCorrectYN().equals("Y")) {
						count++;
					}
				}
				question.setAnswers(ansMaster);
				model.addAttribute("question", question);
				model.addAttribute("studentTestDetail", studentTestDetail);
				model.addAttribute("studentAnswers", studentTestAnswers);
				model.addAttribute("lpq", lpq + 1);
				return "livedetailrds";
			} else if (question.getStyleMaster().getName().equals("MULTICHOICE")) {
				Page<AnswerMaster> page = new Page<AnswerMaster>("");
				List<AnswerMaster> ansMaster = answerService.findAnswers(page, question.getId()).getPageItems();
				int count = 0;
				for (AnswerMaster a : ansMaster) {
					if (a.getCorrectYN().equals("Y")) {
						count++;
					}
				}
				question.setAnswers(ansMaster);
				model.addAttribute("question", question);
				model.addAttribute("studentTestDetail", studentTestDetail);
				model.addAttribute("lpq", lpq + 1);
				if (count > 1) {
					return "livedetailrmc";
				} else {
					return "livedetailrmcsingle";
				}
			}
			return "";
		} else {
			return "login";
		}
	}

	@RequestMapping(value = "/livetest")
	public String liveTest(Principal currentUser, HttpServletRequest request, Model model) {
		List<QuestionMaster> questions = null;
		Integer lpq = null;
		ResourceMaster resourceMaster = null;
		model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
		if (currentUser != null) {
			if (request.getSession().getAttribute("questions") == null) {
				request.getSession().setAttribute("lpq", null);
				if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
					List<StudentTestDetail> studentTestDetails = testService
							.listTestDetailByTestId(Long.parseLong(request.getParameter("id")), currentUser.getName());

					if (studentTestDetails.size() >= 2) {
						return "blockTestStudent";
					}
					request.getSession().setAttribute("testId", request.getParameter("id"));
					questions = testStudentService.listTestQuestions(Long.parseLong(request.getParameter("id")));
					request.getSession().setAttribute("questions", questions);
					Account account = accountService.getAccount(currentUser.getName());
					StudentTestDetail detail = new StudentTestDetail();
					detail.setAccount(account);
					detail.setTestMaster(new TestMaster(Long.parseLong(request.getParameter("id"))));
					detail = testStudentService.insertTestDetail(detail);
					request.getSession().setAttribute("testDetail", detail);
				}
			} else {
				questions = (List<QuestionMaster>) request.getSession().getAttribute("questions");
			}
			if (request.getSession().getAttribute("lpq") == null) {
				lpq = -1;
			} else {
				lpq = Integer.parseInt(request.getSession().getAttribute("lpq").toString());
			}

			if (lpq + 1 == questions.size()) {
				return "redirect:testlist";
			}
			QuestionMaster question = questions.get(lpq + 1);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + question.getStyleMaster().getName());
			if (question.getStyleMaster().getName().equals("SPEAKING")) {
				model.addAttribute("question", question);
				TestMaster testMaster = testService
						.getTest(Long.parseLong((String) request.getSession().getAttribute("testId")));
				model.addAttribute("totalTime", testMaster.getDuration());
				model.addAttribute("quesCount", testMaster.getQuestionCount());
				model.addAttribute("hrs", question.getDuration() == null ? 0 : question.getDuration().getHours());
				model.addAttribute("mints", question.getDuration() == null ? 0 : question.getDuration().getMinutes());
				model.addAttribute("sec", question.getDuration() == null ? 0 : question.getDuration().getSeconds());
				
				model.addAttribute("cpq", lpq + 1);
				return "livetestquestionspk";

			} else if (question.getStyleMaster().getName().equals("DESCRIBE_IMAGE")) {
				resourceMaster = resourceService.getResourceMaster(question.getResourceMaster().getId());
				question.setResourceMaster(resourceMaster);
				model.addAttribute("question", question);
				TestMaster testMaster = testService
						.getTest(Long.parseLong((String) request.getSession().getAttribute("testId")));
				model.addAttribute("totalTime", testMaster.getDuration());
				model.addAttribute("hrs", question.getDuration() == null ? 0 : question.getDuration().getHours());
				model.addAttribute("mints", question.getDuration() == null ? 0 : question.getDuration().getMinutes());
				model.addAttribute("sec", question.getDuration() == null ? 0 : question.getDuration().getSeconds());
				model.addAttribute("quesCount", testMaster.getQuestionCount());
				model.addAttribute("cpq", lpq + 1);
				return "livetestquestionspk2";

			} else if (question.getStyleMaster().getName().equals("REPEAT_SENTENCE")) {
				resourceMaster = resourceService.getResourceMaster(question.getResourceMaster().getId());
				question.setResourceMaster(resourceMaster);
				model.addAttribute("question", question);
				TestMaster testMaster = testService
						.getTest(Long.parseLong((String) request.getSession().getAttribute("testId")));
				model.addAttribute("totalTime", testMaster.getDuration());
				model.addAttribute("hrs", question.getDuration() == null ? 0 : question.getDuration().getHours());
				model.addAttribute("mints", question.getDuration() == null ? 0 : question.getDuration().getMinutes());
				model.addAttribute("sec", question.getDuration() == null ? 0 : question.getDuration().getSeconds());
				model.addAttribute("quesCount", testMaster.getQuestionCount());
				model.addAttribute("cpq", lpq + 1);
				return "livetestquestionspk3";

			} else if (question.getStyleMaster().getName().equals("MULTICHOICE_LISTENING")) {
				resourceMaster = resourceService.getResourceMaster(question.getResourceMaster().getId());
				question.setResourceMaster(resourceMaster);
				Page<AnswerMaster> page = new Page<AnswerMaster>("");
				List<AnswerMaster> ansMaster = answerService.findAnswers(page, question.getId()).getPageItems();
				int count = 0;
				for (AnswerMaster a : ansMaster) {
					if (a.getCorrectYN().equals("Y")) {
						count++;
					}
				}
				question.setAnswers(ansMaster);
				model.addAttribute("question", question);
				TestMaster testMaster = testService
						.getTest(Long.parseLong((String) request.getSession().getAttribute("testId")));
				model.addAttribute("totalTime", testMaster.getDuration());
				model.addAttribute("hrs", question.getDuration() == null ? 0 : question.getDuration().getHours());
				model.addAttribute("mints", question.getDuration() == null ? 0 : question.getDuration().getMinutes());
				model.addAttribute("sec", question.getDuration() == null ? 0 : question.getDuration().getSeconds());
				model.addAttribute("quesCount", testMaster.getQuestionCount());
				// model.addAttribute("test",
				// testService.getTest(Long.parseLong(testId)));
				model.addAttribute("cpq", lpq + 1);
				if (count > 1) {
					return "livetestquestionlmc";
				} else {
					return "livetestquestionlmcsingle";
				}
			} else if (question.getStyleMaster().getName().equals("DIFFERTRANSC")) {
				Page<AnswerMaster> page = new Page<AnswerMaster>("");
				List<AnswerMaster> answers = answerService.findAnswers(page, question.getId()).getPageItems();
				resourceMaster = resourceService.getResourceMaster(question.getResourceMaster().getId());
				question.setResourceMaster(resourceMaster);
				String qt = question.getQuestionText().replaceAll("&nbsp;", " ");
				;
				System.out.println(qt + ">>>>>>>>>>>>>>>>>>>>>");
				String[] parts = qt.split("<b>");
				for (int i = 1; i < parts.length; ++i) {
					parts[i] = parts[i].substring(parts[i].indexOf("</b>") + 4);
				}
				String finalString = "";
				for (int i = 1; i < parts.length; ++i) {
					finalString += answers.get(i - 1).getAnswer() + parts[i];
				}
				finalString = "<p>" + parts[0] + finalString + "</p>";
				System.out.println(finalString + "\n\n\n\n\n");
				question.setQuestionText(finalString);
				model.addAttribute("question", question);
				TestMaster testMaster = testService
						.getTest(Long.parseLong((String) request.getSession().getAttribute("testId")));
				model.addAttribute("totalTime", testMaster.getDuration());
				model.addAttribute("hrs", question.getDuration() == null ? 0 : question.getDuration().getHours());
				model.addAttribute("mints", question.getDuration() == null ? 0 : question.getDuration().getMinutes());
				model.addAttribute("sec", question.getDuration() == null ? 0 : question.getDuration().getSeconds());
				model.addAttribute("quesCount", testMaster.getQuestionCount());
				model.addAttribute("cpq", lpq + 1);
				return "livetestquestionldt";
			} else if (question.getStyleMaster().getName().equals("MULTITEXT")) {
				String qt = question.getQuestionText();
				resourceMaster = resourceService.getResourceMaster(question.getResourceMaster().getId());
				question.setResourceMaster(resourceMaster);
				String[] parts = qt.split("<b>");
				for (int i = 1; i < parts.length; ++i) {
					parts[i] = parts[i].substring(parts[i].indexOf("</b>") + 4);
				}
				String finalString = "";
				for (int i = 1; i < parts.length; ++i) {
					finalString += "<input type=\"text\" id=\"div1\" name=\"answers\" value=\"\"   placeholder=\"Answer Text\"  />"
							+ parts[i];
				}
				finalString = "<p>" + parts[0] + finalString + "</p>";
				System.out.println(finalString + "\n\n\n\n\n");
				question.setQuestionText(finalString);
				model.addAttribute("question", question);
				TestMaster testMaster = testService
						.getTest(Long.parseLong((String) request.getSession().getAttribute("testId")));
				model.addAttribute("totalTime", testMaster.getDuration());
				model.addAttribute("hrs", question.getDuration() == null ? 0 : question.getDuration().getHours());
				model.addAttribute("mints", question.getDuration() == null ? 0 : question.getDuration().getMinutes());
				model.addAttribute("sec", question.getDuration() == null ? 0 : question.getDuration().getSeconds());
				model.addAttribute("quesCount", testMaster.getQuestionCount());
				model.addAttribute("cpq", lpq + 1);
				return "livetestquestionlmt";
			} else if (question.getStyleMaster().getName().equals("SINGLETEXT")) {
				resourceMaster = resourceService.getResourceMaster(question.getResourceMaster().getId());
				question.setResourceMaster(resourceMaster);
				model.addAttribute("question", question);
				TestMaster testMaster = testService
						.getTest(Long.parseLong((String) request.getSession().getAttribute("testId")));
				model.addAttribute("totalTime", testMaster.getDuration());
				model.addAttribute("hrs", question.getDuration() == null ? 0 : question.getDuration().getHours());
				model.addAttribute("mints", question.getDuration() == null ? 0 : question.getDuration().getMinutes());
				model.addAttribute("sec", question.getDuration() == null ? 0 : question.getDuration().getSeconds());
				model.addAttribute("quesCount", testMaster.getQuestionCount());
				model.addAttribute("cpq", lpq + 1);
				return "livetestquestionlst";
			} else if (question.getStyleMaster().getName().equals("WRITING")) {
				// Page<AnswerMaster> page = new Page<AnswerMaster>("");
				// question.setAnswers(answerService.findAnswers(page,
				// question.getId()).getPageItems());
				model.addAttribute("question", question);
				TestMaster testMaster = testService
						.getTest(Long.parseLong((String) request.getSession().getAttribute("testId")));
				model.addAttribute("totalTime", testMaster.getDuration());
				model.addAttribute("hrs", question.getDuration() == null ? 0 : question.getDuration().getHours());
				model.addAttribute("mints", question.getDuration() == null ? 0 : question.getDuration().getMinutes());
				model.addAttribute("sec", question.getDuration() == null ? 0 : question.getDuration().getSeconds());
				model.addAttribute("quesCount", testMaster.getQuestionCount());
				model.addAttribute("cpq", lpq + 1);
				return "livetestquestionwrt";

			} else if (question.getStyleMaster().getName().equals("DRAGTOSORT")) {
				Page<AnswerMaster> page = new Page<AnswerMaster>("");
				List<AnswerMaster> ansList = answerService.findAnswers(page, question.getId()).getPageItems();
				Collections.shuffle(ansList);
				question.setAnswers(ansList);

				model.addAttribute("question", question);
				
				TestMaster testMaster = testService
						.getTest(Long.parseLong((String) request.getSession().getAttribute("testId")));
				model.addAttribute("totalTime", testMaster.getDuration());
				model.addAttribute("hrs", question.getDuration() == null ? 0 : question.getDuration().getHours());
				model.addAttribute("mints", question.getDuration() == null ? 0 : question.getDuration().getMinutes());
				model.addAttribute("sec", question.getDuration() == null ? 0 : question.getDuration().getSeconds());
				model.addAttribute("quesCount", testMaster.getQuestionCount());
				model.addAttribute("cpq", lpq + 1);
				return "livetestquestiondts";

			} else if (question.getStyleMaster().getName().equals("MULTICHOICE")) {
				Page<AnswerMaster> page = new Page<AnswerMaster>("");
				List<AnswerMaster> ansMaster = answerService.findAnswers(page, question.getId()).getPageItems();
				int count = 0;
				for (AnswerMaster a : ansMaster) {
					if (a.getCorrectYN().equals("Y")) {
						count++;
					}
				}
				question.setAnswers(ansMaster);
				model.addAttribute("question", question);
				TestMaster testMaster = testService
						.getTest(Long.parseLong((String) request.getSession().getAttribute("testId")));
				model.addAttribute("totalTime", testMaster.getDuration());
				System.out.println(">>>>>>>total time : " + testMaster.getDuration());
				model.addAttribute("hrs", question.getDuration() == null ? 0 : question.getDuration().getHours());
				model.addAttribute("mints", question.getDuration() == null ? 0 : question.getDuration().getMinutes());
				model.addAttribute("sec", question.getDuration() == null ? 0 : question.getDuration().getSeconds());
				model.addAttribute("quesCount", testMaster.getQuestionCount());
				// model.addAttribute("test",
				// testService.getTest(Long.parseLong(testId)));
				model.addAttribute("cpq", lpq + 1);
				if (count > 1) {
					return "livetestquestionrmc";
				} else {
					return "livetestquestionrmcsingle";
				}
			} else if (question.getStyleMaster().getName().equals("DRAGTOCORRECT")) {
				Page<AnswerMaster> page = new Page<AnswerMaster>("");
				List<AnswerMaster> ansList = answerService.findAnswers(page, question.getId()).getPageItems();
				Collections.shuffle(ansList);
				question.setAnswers(ansList);
				String qt = question.getQuestionText();
				String[] parts = qt.split("<b>");
				for (int i = 1; i < parts.length; ++i) {
					parts[i] = parts[i].substring(parts[i].indexOf("</b>") + 4);
				}
				String finalString = "";
				for (int i = 1; i < parts.length; ++i) {
					finalString += "<span class=\"div1\" id=\"span_" + i
							+ "\" ondrop=\"drop(event)\" ondragover=\"allowDrop(event)\"></span>" + parts[i];
				}
				finalString = "<p>" + parts[0] + finalString + "</p>";
				System.out.println(finalString + "\n\n\n\n\n");
				question.setQuestionText(finalString);
				model.addAttribute("question", question);
				TestMaster testMaster = testService
						.getTest(Long.parseLong((String) request.getSession().getAttribute("testId")));
				model.addAttribute("totalTime", testMaster.getDuration());
				model.addAttribute("hrs", question.getDuration() == null ? 0 : question.getDuration().getHours());
				model.addAttribute("mints", question.getDuration() == null ? 0 : question.getDuration().getMinutes());
				model.addAttribute("sec", question.getDuration() == null ? 0 : question.getDuration().getSeconds());
				model.addAttribute("quesCount", testMaster.getQuestionCount());
				model.addAttribute("cpq", lpq + 1);
				return "livetestquestiondtc";
			} else if (question.getStyleMaster().getName().equals("DROPDOWN")) {
				Page<AnswerMaster> page = new Page<AnswerMaster>("");
				List<AnswerMaster> answers = answerService.findAnswers(page, question.getId()).getPageItems();

				Page<AnswerOptions> page1 = null;
				for (int i = 0; i < answers.size(); ++i) {
					page1 = new Page<AnswerOptions>("");
					answers.get(i).setOptions(
							answerOptionService.findAnswerOptions(page1, answers.get(i).getId()).getPageItems());
				}
				question.setAnswers(answers);
				String qt = question.getQuestionText();
				String[] parts = qt.split("<b>");
				for (int i = 1; i < parts.length; ++i) {
					parts[i] = parts[i].substring(parts[i].indexOf("</b>") + 4);
				}
				String finalString = "";
				for (int i = 1; i < parts.length; ++i) {
					List<AnswerOptions> list = answers.get(i - 1).getOptions();
					Collections.shuffle(list);
					String optionString = "<option value=\"\"></option>";
					for (AnswerOptions op : list) {
						optionString += "<option value=\"" + op.getAnsOption() + "\">" + op.getAnsOption()
								+ "</option>";
					}
					finalString += "<select name=\"answers\">" + optionString + "</select>" + parts[i];
				}
				finalString = "<p>" + parts[0] + finalString + "</p>";
				System.out.println(finalString + "\n\n\n\n\n");
				question.setQuestionText(finalString);
				model.addAttribute("question", question);
				TestMaster testMaster = testService
						.getTest(Long.parseLong((String) request.getSession().getAttribute("testId")));
				model.addAttribute("totalTime", testMaster.getDuration());
				model.addAttribute("hrs", question.getDuration() == null ? 0 : question.getDuration().getHours());
				model.addAttribute("mints", question.getDuration() == null ? 0 : question.getDuration().getMinutes());
				model.addAttribute("sec", question.getDuration() == null ? 0 : question.getDuration().getSeconds());
				model.addAttribute("quesCount", testMaster.getQuestionCount());
				model.addAttribute("cpq", lpq + 1);
				return "livetestquestiondd";

			}
			return "login";
		} else {
			return "login";
		}
	}

	@RequestMapping(value = "/processquest")
	public String processuest(Principal currentUser, HttpServletRequest request, Model model) {
		String qstyleId = request.getParameter("qstyleId");
		if (currentUser != null) {
			if (qstyleId.equals(QuestionStyle.DRAGTOSORT.toString())) {
				StudentTestDetail detail = (StudentTestDetail) request.getSession().getAttribute("testDetail");
				String questionId = request.getParameter("qId");
				String cpq = request.getParameter("cpq");
				String[] answers = request.getParameterValues("answers");
				StudentTestAnswer answerDomain = null;
				List<StudentTestAnswer> answersList = new ArrayList<StudentTestAnswer>();
				int i = 0;
				if (answers != null && !answers.equals("")) {
					for (String answer : answers) {
						answerDomain = new StudentTestAnswer();
						answerDomain.setAnsSeqNo(i);
						answerDomain.setQuestionMaster(new QuestionMaster(Long.parseLong(questionId)));
						answerDomain.setStudentTestDetail(detail);
						answerDomain.setTestMaster(detail.getTestMaster());
						answerDomain.setTextAnswer(answer);
						answersList.add(answerDomain);
						++i;
					}
					testStudentService.insertStudentAnswers(answersList);
				}
				detail = testStudentService.processDragAndCorrect(detail, Long.parseLong(questionId),
						Long.parseLong(qstyleId));
				testStudentService.updateTestDetail(detail);
				request.getSession().setAttribute("lpq", cpq);
				request.getSession().setAttribute("testDetail", detail);
			} else if (qstyleId.equals(QuestionStyle.DIFFERTRANSC.toString())) {
				StudentTestDetail detail = (StudentTestDetail) request.getSession().getAttribute("testDetail");
				String questionId = request.getParameter("qId");
				String cpq = request.getParameter("cpq");
				String[] answers = request.getParameterValues("answers");
				StudentTestAnswer answerDomain = null;
				List<StudentTestAnswer> answersList = new ArrayList<StudentTestAnswer>();
				int i = 0;
				if (answers != null && !answers.equals("")) {
					for (String answer : answers) {
						answerDomain = new StudentTestAnswer();
						answerDomain.setAnsSeqNo(i);
						answerDomain.setQuestionMaster(new QuestionMaster(Long.parseLong(questionId)));
						answerDomain.setStudentTestDetail(detail);
						answerDomain.setTestMaster(detail.getTestMaster());
						answerDomain.setTextAnswer(answer);
						answersList.add(answerDomain);
						++i;
					}
					testStudentService.insertStudentAnswers(answersList);
				}
				detail = testStudentService.processDragAndCorrect(detail, Long.parseLong(questionId),
						Long.parseLong(qstyleId));
				testStudentService.updateTestDetail(detail);
				request.getSession().setAttribute("lpq", cpq);
				request.getSession().setAttribute("testDetail", detail);
			} else if (qstyleId.equals(QuestionStyle.SINGLETEXT.toString())) {
				StudentTestDetail detail = (StudentTestDetail) request.getSession().getAttribute("testDetail");
				String questionId = request.getParameter("qId");
				String cpq = request.getParameter("cpq");
				String answer = request.getParameter("answer");
				StudentTestAnswer answerDomain = new StudentTestAnswer();
				answerDomain.setQuestionMaster(new QuestionMaster(Long.parseLong(questionId)));
				List<StudentTestAnswer> answersList = new ArrayList<StudentTestAnswer>();
				if (answer != null && !answer.equals("")) {
					answerDomain.setStudentTestDetail(detail);
					answerDomain.setTestMaster(detail.getTestMaster());
					answerDomain.setTextAnswer(answer);
					answersList.add(answerDomain);
					testStudentService.insertStudentAnswers(answersList);
				}
				detail = testStudentService.processDragAndCorrect(detail, Long.parseLong(questionId),
						Long.parseLong(qstyleId));
				testStudentService.updateTestDetail(detail);
				request.getSession().setAttribute("lpq", cpq);
				request.getSession().setAttribute("testDetail", detail);
			} else if (qstyleId.equals(QuestionStyle.MULTITEXT.toString())) {
				StudentTestDetail detail = (StudentTestDetail) request.getSession().getAttribute("testDetail");
				String questionId = request.getParameter("qId");
				String cpq = request.getParameter("cpq");
				String[] answers = request.getParameterValues("answers");
				StudentTestAnswer answerDomain = null;
				List<StudentTestAnswer> answersList = new ArrayList<StudentTestAnswer>();
				int i = 0;
				for (String answer : answers) {
					answerDomain = new StudentTestAnswer();
					answerDomain.setAnsSeqNo(i);
					answerDomain.setQuestionMaster(new QuestionMaster(Long.parseLong(questionId)));
					answerDomain.setStudentTestDetail(detail);
					answerDomain.setTestMaster(detail.getTestMaster());
					answerDomain.setTextAnswer(answer);
					answersList.add(answerDomain);
					++i;
				}
				testStudentService.insertStudentAnswers(answersList);
				detail = testStudentService.processDragAndCorrect(detail, Long.parseLong(questionId),
						Long.parseLong(qstyleId));
				testStudentService.updateTestDetail(detail);
				request.getSession().setAttribute("lpq", cpq);
				request.getSession().setAttribute("testDetail", detail);
			} else if (qstyleId.equals(QuestionStyle.MULTICHOICE_L.toString())) {
				StudentTestDetail detail = (StudentTestDetail) request.getSession().getAttribute("testDetail");
				String questionId = request.getParameter("qId");
				String cpq = request.getParameter("cpq");
				String[] answers = request.getParameterValues("answers");
				String[] correctYN = request.getParameterValues("correctYN");
				StudentTestAnswer answerDomain = null;
				List<StudentTestAnswer> answersList = new ArrayList<StudentTestAnswer>();
				for (int i = 0; i < answers.length; i++) {
					answerDomain = new StudentTestAnswer();
					if (correctYN != null && !correctYN.equals("")) {
						for (int j = 0; j < correctYN.length; j++) {
							if (correctYN[j] != null && !correctYN[j].equals("")) {
								if (i == Integer.parseInt(correctYN[j])) {
									answerDomain.setCorrectYN("Y");
									break;
								} else {
									answerDomain.setCorrectYN("N");
								}
							} else {
								answerDomain.setCorrectYN("N");
							}
						}
					} else {
						answerDomain.setCorrectYN("N");
					}
					answerDomain.setAnsSeqNo(i);
					answerDomain.setQuestionMaster(new QuestionMaster(Long.parseLong(questionId)));
					answerDomain.setStudentTestDetail(detail);
					answerDomain.setTestMaster(detail.getTestMaster());
					answerDomain.setTextAnswer(answers[i]);
					answersList.add(answerDomain);

				}
				testStudentService.insertStudentAnswers(answersList);
				detail = testStudentService.processDragAndCorrect(detail, Long.parseLong(questionId),
						Long.parseLong(qstyleId));
				testStudentService.updateTestDetail(detail);
				request.getSession().setAttribute("lpq", cpq);
				request.getSession().setAttribute("testDetail", detail);
			} else if (qstyleId.equals(QuestionStyle.WRITING_STYLE.toString())) {
				StudentTestDetail detail = (StudentTestDetail) request.getSession().getAttribute("testDetail");
				String questionId = request.getParameter("qId");
				String cpq = request.getParameter("cpq");
				String answer = request.getParameter("answer");
				StudentTestAnswer answerDomain = new StudentTestAnswer();
				answerDomain.setQuestionMaster(new QuestionMaster(Long.parseLong(questionId)));
				List<StudentTestAnswer> answersList = new ArrayList<StudentTestAnswer>();
				if (answer != null && !answer.equals("")) {
					answerDomain.setStudentTestDetail(detail);
					answerDomain.setTestMaster(detail.getTestMaster());
					answerDomain.setTextAnswer(answer);
					answersList.add(answerDomain);
					testStudentService.insertStudentAnswers(answersList);
				}
				detail = testStudentService.processDragAndCorrect(detail, Long.parseLong(questionId),
						Long.parseLong(qstyleId));
				testStudentService.updateTestDetail(detail);
				request.getSession().setAttribute("lpq", cpq);
				request.getSession().setAttribute("testDetail", detail);

			} else if (qstyleId.equals(QuestionStyle.MULTICHOICE.toString())) {

				StudentTestDetail detail = (StudentTestDetail) request.getSession().getAttribute("testDetail");
				String questionId = request.getParameter("qId");
				String cpq = request.getParameter("cpq");
				String[] answers = request.getParameterValues("answers");
				String[] correctYN = request.getParameterValues("correctYN");
				StudentTestAnswer answerDomain = null;
				List<StudentTestAnswer> answersList = new ArrayList<StudentTestAnswer>();
				if (answers != null && !answers.equals("")) {
					for (int i = 0; i < answers.length; i++) {
						answerDomain = new StudentTestAnswer();
						if (correctYN != null && !correctYN.equals("")) {
							for (int j = 0; j < correctYN.length; j++) {
								if (correctYN[j] != null && !correctYN[j].equals("")) {
									if (i == Integer.parseInt(correctYN[j])) {
										answerDomain.setCorrectYN("Y");
										break;
									} else {
										answerDomain.setCorrectYN("N");
									}
								} else {
									answerDomain.setCorrectYN("N");
								}
							}
						} else {
							answerDomain.setCorrectYN("N");
						}
						answerDomain.setAnsSeqNo(i);
						answerDomain.setQuestionMaster(new QuestionMaster(Long.parseLong(questionId)));
						answerDomain.setStudentTestDetail(detail);
						answerDomain.setTestMaster(detail.getTestMaster());
						answerDomain.setTextAnswer(answers[i]);
						answersList.add(answerDomain);

					}
					testStudentService.insertStudentAnswers(answersList);
				}
				detail = testStudentService.processDragAndCorrect(detail, Long.parseLong(questionId),
						Long.parseLong(qstyleId));
				testStudentService.updateTestDetail(detail);
				request.getSession().setAttribute("lpq", cpq);
				request.getSession().setAttribute("testDetail", detail);
			} else {
				StudentTestDetail detail = (StudentTestDetail) request.getSession().getAttribute("testDetail");
				String questionId = request.getParameter("qId");
				String cpq = request.getParameter("cpq");
				String[] answers = request.getParameterValues("answers");
				StudentTestAnswer answerDomain = null;
				List<StudentTestAnswer> answersList = new ArrayList<StudentTestAnswer>();
				int i = 0;
				if (answers != null && !answers.equals("")) {
					for (String answer : answers) {
						answerDomain = new StudentTestAnswer();
						answerDomain.setAnsSeqNo(i);
						answerDomain.setQuestionMaster(new QuestionMaster(Long.parseLong(questionId)));
						answerDomain.setStudentTestDetail(detail);
						answerDomain.setTestMaster(detail.getTestMaster());
						answerDomain.setTextAnswer(answer);
						answersList.add(answerDomain);
						++i;
					}
					testStudentService.insertStudentAnswers(answersList);
				}
				detail = testStudentService.processDragAndCorrect(detail, Long.parseLong(questionId),
						Long.parseLong(qstyleId));
				testStudentService.updateTestDetail(detail);
				request.getSession().setAttribute("lpq", cpq);
				request.getSession().setAttribute("testDetail", detail);
			}
			return "redirect:livetest";

		} else {
			return "login";
		}
	}

	@RequestMapping(value = "/profile")
	public String profile(Principal currentUser, HttpServletRequest request, Model model) {
		if (currentUser != null) {

			model.addAttribute("home", accountService.getAccountByUserName(currentUser.getName()));
			return "studentprofile";
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
		String redirectUrl = "/student/profile";
		return "redirect:" + redirectUrl;

	}

	@RequestMapping(value = "/logout")
	public String logout(Principal currentUser, HttpServletRequest request, Model model) {

		return "redirect:/signout";
	}
}
