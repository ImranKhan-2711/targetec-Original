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
		Questions <small>List</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Accounts</a></li>
		<li class="active">Questions</li>
	</ol>

	<div class="row">
		<div class="col-xs-12">

			<!-- Main content -->
			<div class="box box-primary">
				<ul class="nav nav-tabs">
					<li class=""><a href="questions?typeId=10000001">Reading</a></li>
					<li class=""><a href="questions?typeId=10000002">Writing</a></li>
					<li class="active"><a href="questions?typeId=10000003">Listening</a></li>
					<li class=""><a href="questions?typeId=10000004">Speaking</a></li>
				</ul>
				<div class="box-header  with-border">
					<div class="panel-body">
						<div class="filter-invoice">
							<a class="btn btn-success"
								href="listening/question/form/edit?styleId=10000003&questionId=">
								<i class="fa fa-pencil" aria-hidden="true"></i> <span>New</span>
							</a>
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
					<div class="table-responsive">
						<table id="example1_wrapper"
							class="table table-bordered table-striped">
							<thead>
								<tr>
									<th>Question</th>
									<th>Question Code</th>
									<th>Type</th>
									<th>Style</th>
									<th>Answer Detail</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${questions.pageItems}" var="question">
									<tr>
										<td>${question.questionText}</td>
										<td>${question.questionCode}</td>
										<td>${question.typeMaster.name}</td>
										<td>${question.styleMaster.description}</td>
										<td><a class="info-r"
											href="reading/answer/add/description?questionId=${question.id}">
												<i class="fa fa-info-circle" aria-hidden="true"></i>
										</a></td>
										<td width="100px"><a
											href="listening/question/form/edit?styleId=${question.styleMaster.id}&questionId=${question.id}"
											class="edit-r"> <i class="fa fa-pencil"
												aria-hidden="true"></i>
										</a> <a class="delete-r"
											onClick="javaScript: return Holiday.List.deleteHoliday(this);">
												<i class="fa fa-trash" aria-hidden="true"></i>
										</a></td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<th>Question</th>
									<th>Description</th>
									<th>Type</th>
									<th>Style</th>
									<th>Action</th>
								</tr>
							</tfoot>
						</table>
					</div>
					<div class="col-sm-5">
						<div class="dataTables_info" id="example2_info" role="status"
							aria-live="polite">Showing 1 to 10 of 57 entries</div>
					</div>
					<div class="col-sm-7">
						<div class="dataTables_paginate paging_simple_numbers"
							id="example2_paginate">
							<form role="form" id="pagenumberform" method="post"
								action="${pageContext.request.contextPath}/admin/questions?typeId=10000003">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" /> <input type="hidden" id="pageNumber"
									name="pageNumber" value="${pageNumber}" />
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
							</form>
						</div>
					</div>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</div>
</section>
<div id="myModal" class="modal fade"></div>

<script type="text/javascript">
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
		$("#pageNumber").val(val);
		document.getElementById('pagenumberform').submit();

	}
	function saveTrainer() {
		debugger;
		$.ajax({
			type : "GET",
			url : "/targetec-web/ajax/model/trainer/update",
			data : $('#trainer').serialize(),
			success : function() {
				$("#myModal").modal('hide');
			}
		});

	}
</script>

