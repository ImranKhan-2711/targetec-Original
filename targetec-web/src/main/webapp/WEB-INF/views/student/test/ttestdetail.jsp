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
		Test <small>List</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Accounts</a></li>
		<li class="active">Test</li>
	</ol>
	<section class="content">
		<div class="row">
			<div class="col-xs-12">

				<!-- Main content -->
				<div class="box">
					<div class="box-header">
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
									<th>Test Taken Date</th>
									<th>Test Name</th>
									<th>Total Questions</th>
									<th>Attempted</th>
									<th>Correct</th>
									<th>Incorrect</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${testdetaillist.pageItems}" var="testdetail">
									<tr>
										<td>${testdetail.performedOn}</td>
										<td>${testdetail.testMaster.name}</td>
										<td>${testdetail.testMaster.questionCount}</td>
										<td>${testdetail.attempted}</td>
										<td>${testdetail.corrected}</td>
										<td>${testdetail.uncorrected}</td>
										<td width="100px"><a href="testdetaillive?testDetailId=${testdetail.id}"><i
												class="fa fa-caret-square-o-right "></i></a></td>

									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<th>Test Taken Date</th>
									<th>Test Name</th>
									<th>Total Questions</th>
									<th>Attempted</th>
									<th>Correct</th>
									<th>Incorrect</th>
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
									action="${pageContext.request.contextPath}/student/detaillist">
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" /> <input type="hidden" id="pageNumber"
										name="pageNumber" value="${pageNumber}" />
									<ul class="pagination">
										<li class="paginate_button previous disabled"
											id="example2_previous"><a
											onClick="javaScript:pageNo(0);" aria-controls="example2"
											data-dt-idx="0" tabindex="0">Previous</a></li>
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
</script>

