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
						<div class="panel-body">
							<div id="danger"
								class="alert alert-danger alert-dismissible hide"></div>
							<div id="success"
								class="alert alert-success alert-dismissible hide"></div>
							<div class="filter-invoice">
								<a class="btn btn-success" href="javascript:testlist('');">
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
						<table id="example1_wrapper"
							class="table table-bordered table-striped">
							<thead>
								<tr>
									<th>Test Name</th>
									<th>Description</th>
									<th>Test Type</th>
									<th>Current Status</th>
									<th>Questions Count</th>
									<th>Assign</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${testlist.pageItems}" var="test">
									<tr>
										<td>${test.name}</td>
										<td>${test.description}</td>
										<td>${test.typeMaster.name}</td>
										<td>${test.categoryMaster.name}</td>
										<td><a
											href="${pageContext.request.contextPath}/admin/form/test/question/edit?testId=${test.id}&typeId=${test.typeMaster.id}">${test.questionCount}</a></td>
										<td><a class="assign-r"
											href="${pageContext.request.contextPath}/admin/form/test/student/edit?testId=${test.id}"><i
												class="fa fa-tasks" aria-hidden="true"></i></a></td>
										<td width="100px"><a href="javascript:void(0)"
											onclick="javascript:testlist(${test.id});" class="edit-r">
												<i class="fa fa-pencil" aria-hidden="true"></i>
										</a> <a class="delete-r"
											onClick="javascript:deleteTest(${test.id});"> <i
												class="fa fa-trash" aria-hidden="true"></i>
										</a></td>

									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<th>Test Name</th>
									<th>Description</th>
									<th>Test Type</th>
									<th>Current Status</th>
									<th>Questions Count</th>
									<th>Assign</th>
									<th>Action</th>
								</tr>
							</tfoot>
						</table>
						<div class="col-sm-5">
							<div class="dataTables_info" id="example2_info" role="status"
								aria-live="polite">Showing 1 to 10 of 57 entries</div>
						</div>
						<div class="col-sm-7">
							<div class="dataTables_paginate paging_simple_numbers"
								id="example2_paginate">
								<form role="form" id="pagenumberform" method="post"
									action="${pageContext.request.contextPath}/admin/testlist">
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" /> <input type="hidden"
										id="pageNumber" name="pageNumber" value="${pageNumber}" />
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

<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script type="text/javascript">
	$(function() {

	});
	function testlist(obj) {
		var url = "/targetec-web/ajax/model/testlist/edit";
		
		if(obj!='undefined' && obj!==""){
			url=url+"?id="+obj;
		}
		$.get(url,
				function(data) {
					$('#myModal').html($(data).html());
					$("#wizard-picture").change(function() {
						readURL(this);
						saveMedia("profileImage");
					});
				});
		$("#myModal").modal('show');
		 setTimeout(function() {
		$('#questionCount').val(0);
		}, 1000);
		
		
	}
 
	function saveTest() {
		
		var testName = $('#testName').val();
		var id = $('#id').val();
		var description = $('#description').val();
		var selectedTypeId = $('#selectedTypeId').val();
		var selectedCategoryId = $('#selectedCategoryId').val(); 
		var resourceId = $('#resourceId').val();
		 if(testName == '' || testName == null){
				showValidation('#testName','');
			}else if(description == '' | description == null){
				showValidation('#description','');
			}else if(selectedTypeId == '' | selectedTypeId == null){
				showValidation('#selectedTypeId','Select Test Type');
			}else if(selectedCategoryId == '' | selectedCategoryId == null){
				showValidation('#selectedCategoryId','Select Test Category');
			}else if(resourceId == '' | resourceId == null){
				showValidation('#resourceId','');
			}else{/* $
				.ajax({
					type : "GET",
					url : "/targetec-web/ajax/model/test/update",
					data : $('#testlistid').serialize(),
					success : function() {
						$("#myModal").modal('hide');
					}
				});*/
				var url = "/targetec-web/ajax/test/validation?testName="+testName+"&id="+id;
			    $.ajax({
			        url : url,
			        type : 'GET',
			        success: function(response){
			        	debugger;
			        	if(response != ''){
			        		$('#testName').css('border-color', 'red');
			   			 $('#danger1').text(response).removeClass('hide').slideDown('slow');
			   			 setTimeout(function() {
			   				  $('.alert-danger').slideUp('slow');
			   				  $('#testName').css('border-color', '');
			   			}, 4000);
			        	}else{
			        		
			        		$
							.ajax({
								type : "GET",
								url : "/targetec-web/ajax/model/test/update",
								data : $('#testlistid').serialize(),
								success : function() {
									$("#myModal").modal('hide');
									pageNo('1');
								}
							});
			        	}
			        } });
			}

	}
	function showValidation(value,message){
		var msg =$(value).attr('placeholder');
		if(value == '#selectedTypeId'){
			msg = message;
		}
		if(value == '#selectedCategoryId'){
			msg = message;
		}
		 $(value).css('border-color', 'red');
		$('#danger1').text('Please '+msg).removeClass('hide').slideDown('slow');
		 setTimeout(function() {
			  $('.alert-danger').slideUp('slow');
			  $(value).css('border-color', '');
		}, 4000);
	}
	function uploadmedia(){
		var formData = new FormData();
	    formData.append('file', $('input[type=file]')[0].files[0]);
	    $('#loadingimage').removeClass('hide');
	    $.ajax({
	        url : '/targetec-web/ajax/upload?${_csrf.parameterName}=${_csrf.token}',
	        data : formData,
	        processData : false,
	        contentType : false,
	        type : 'POST',
	        success : function(data) {
	        	$('#loadingimage').addClass('hide');
	            $("#resourceId").val(data);
	        },
	        error : function(err) {
	            alert(err);
	        }
	    });
	}
	function pageNo(val){
		debugger;
		var pageVal = $("#pageNumber").val();
		if(val==0){
			if(pageVal >1 ){
				val= pageVal - 1;
			}else{
				val=1;
			}
			
		}
		$("#pageNumber").val(val);
		document.getElementById('pagenumberform').submit();
		
	}
	function deleteTest(obj){
		if (confirm('You are proceeding to delete record')) {
		
		var id = obj;
		var pageNumber = $('#pageNumber').val();
		var url = "/targetec-web/ajax/test/delete?${_csrf.parameterName}=${_csrf.token}&id="+id+"&pageNumber="+pageNumber+"";
	    $.ajax({
	        url : url,
	        type : 'GET',
	        success: function(response){
                if(response == ''){
                	 $('.alert-success').text('Sucessfully Deleted').removeClass('hide').slideDown('slow');
					  setTimeout(function() {
						  document.getElementById('pagenumberform').submit();
						  $('.alert-success').slideUp('slow');
						}, 4000);
                }else{
	        	$('.alert-danger').text(response).removeClass('hide').slideDown('slow');
					  setTimeout(function() {
						  $('.alert-danger').slideUp('slow');
						}, 4000);
                }
	        	
	        },
	        error : function(err) {
	            alert(err);
	        }
	    });
		}
	}
</script>
