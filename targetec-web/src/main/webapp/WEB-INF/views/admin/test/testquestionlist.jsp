<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<style>
.users-list>li {
	width: 20%;
}

.btn-group-lg>.btn, .btn-lg {
	padding: 8px 10px;
}

.gsi-step-indicator.triangle li.current>* {
	background-color: #3c8dbc;
	border-color: #3c8dbc;
}

.gsi-step-indicator.triangle li.done>* {
	background-color: #F5FAF5;
	border-color: #F5FAF5;
}
</style>
<section class="content-header">
	<h1>
		Test <small>Questions</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Accounts</a></li>
		<li class="active">Assign Question to Test</li>
	</ol>
	<section class="content">
		<form role="form" id="trainer" method="post"
			action="${pageContext.request.contextPath}/admin/form/test/question">

			<div class="row">
				<div class="col-xs-12">

					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
					<!-- Main content -->
					<div class="box">

						<div class="box-header">
							<div class="row">
								<div class="col-sm-6">

									<label>${test.name}</label>
								</div>
								<div class="form-group col-sm-6">
									<div class="pull-right btn_bx">
										<a href="${pageContext.request.contextPath}/admin/testlist"
											class="btn btn-default">Close</a>
										<button type="button" onclick="actionSubmit();"
											class="btn btn-primary">Save changes</button>

									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-6">
									<div class="dataTables_length" id="example1_length">
										<label>Show <select name="example1_length"
											aria-controls="example1" class="form-control input-sm"><option
													value="10">10</option>
												<option value="25">25</option>
												<option value="50">50</option>
												<option value="100">100</option></select> entries
										</label>
									</div>
								</div>
								<div class="col-sm-6">
									<div id="example1_filter" class="dataTables_filter">
										<label>Search:<input type="search"
											class="form-control input-sm" placeholder=""
											aria-controls="example1"></label>
									</div>
								</div>
							</div>
						</div>
						<!-- /.box-header -->
						<div class="box-body">
							<div class="divider-1 col-lg-12"></div>
							<input type="hidden" value="${test.id}" id="testId" name="testId" />
							<input type="hidden" value="${test.typeMaster.id}" id="typeId"
								name="typeId" />
							<table id="example1_wrapper"
								class="table table-bordered table-striped">
								<thead>
									<tr>
										<th>Assign</th>
										<th>Question Name</th>
										<th>Question Code</th>
										<th>Question Style</th>
										<th>Question Type</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${questions}" var="question"
										varStatus="st">
										<tr>
											<td><input type="checkbox" value="${st.count-1}"
												id="correctYN-${st.count}" name="correctYN" onchange="changecheckbox('${question.id}');"
												<c:forEach items="${testquestion}" var="testQues" varStatus="st">
				                    	  <c:if test="${testQues.questionMaster.id==question.id}">
									  	  		checked="checked"
									  	  </c:if>
				                      </c:forEach> />
												<input type="hidden" value="${question.id}" id="questionId"
												name="questionId" /></td>
											<td>${question.questionText}</td>
											<td>${question.questionCode}</td>
											<td>${question.styleMaster.description}</td>
											<td>${question.typeMaster.name}</td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot>
									<tr>
										<th>Assign</th>
										<th>Question Name</th>
										<th>Description</th>
										<th>Question Style</th>
										<th>Question Type</th>
									</tr>
								</tfoot>
							</table>

						</div>
						<!-- /.box-body -->
					</div>
					<!-- /.box -->

					<div class="col-sm-5">
						<div class="dataTables_info" id="example2_info" role="status"
							aria-live="polite">Showing 1 to 10 of 57 entries</div>
					</div>
					<div class="col-sm-7">
						<div class="dataTables_paginate paging_simple_numbers"
							id="example2_paginate">
							<input type="hidden" id="pageNumber" name="pageNumber"
								value="${pageNumber}" />
							<ul class="pagination">
								<li class="paginate_button previous disabled"
									id="example2_previous"><a onClick="javaScript:pageNo(0);"
									aria-controls="example2" data-dt-idx="0" tabindex="0">Previous</a></li>
								<li class="paginate_button active"><a
									onClick="javaScript:pageNo(1);" aria-controls="example2"
									data-dt-idx="1" tabindex="0">1</a></li>
								<li class="paginate_button "><a
									onClick="javaScript:pageNo(2);" aria-controls="example2"
									data-dt-idx="2" tabindex="0">2</a></li>
								<li class="paginate_button "><a
									onClick="javaScript:pageNo(3);" aria-controls="example2"
									data-dt-idx="3" tabindex="0">3</a></li>
								<li class="paginate_button "><a
									onClick="javaScript:pageNo(4);" aria-controls="example2"
									data-dt-idx="4" tabindex="0">4</a></li>
								<li class="paginate_button "><a
									onClick="javaScript:pageNo(5);" aria-controls="example2"
									data-dt-idx="5" tabindex="0">5</a></li>
								<li class="paginate_button "><a
									onClick="javaScript:pageNo(6);" aria-controls="example2"
									data-dt-idx="6" tabindex="0">6</a></li>
								<li class="paginate_button next" id="example2_next"><a
									onClick="javaScript:pageNo(7);" aria-controls="example2"
									data-dt-idx="7" tabindex="0">Next</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</form>
	</section>
</section>

<script type="text/javascript">
	function actionSubmit() {
		document.getElementById('trainer').action = document
				.getElementById('trainer').action
				+ "/update";
		document.getElementById('trainer').submit();
	}
	function pageNo(val) {
		debugger;
		var pageVal = $("#pageNumber").val();
		if (val == 0) {
			if (pageVal > 1) {
				val = pageVal - 1;
			} else {
				val = 1;
			}

		}
		var testId = $("#testId").val();
		var typeId = $("#typeId").val();
		$("#pageNumber").val(val);
		document.getElementById('trainer').action = document
				.getElementById('trainer').action
				+ "/edit";
		document.getElementById('trainer').submit();

	}
	function changecheckbox(id){
		debugger;
		$
		.ajax({
			type : "GET",
			url : "/targetec-web/ajax/update/map?questionId="+id+"",
			success : function() {
			}
		});
		
	}
</script>
