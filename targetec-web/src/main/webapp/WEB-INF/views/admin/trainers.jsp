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
		Trainers <small>List</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Accounts</a></li>
		<li class="active">Trainers</li>
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
								<a class="btn btn-success" href="javascript:trainer('');"> <i
									class="fa fa-pencil" aria-hidden="true"></i> <span>New</span>
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
									<th>First Name</th>
									<th>Last Name</th>
									<th>Mobile Number</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${trainers.pageItems}" var="trainer">
									<tr>
										<td>${trainer.firstName}</td>
										<td>${trainer.lastName}</td>
										<td>${trainer.phoneNumber}</td>
										<td width="100px"><a href="javascript:void(0)" 
											onclick="javascript:trainer(${trainer.id});"
											class="edit-r"> <i class="fa fa-pencil"
												aria-hidden="true"></i>
										</a>  <a class="delete-r"
											onClick="javascript:deleteTrainer(${trainer.account.id});">
												<i class="fa fa-trash" aria-hidden="true"></i>
										</a></td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<th>First Name</th>
									<th>Last Name</th>
									<th>Mobile Number</th>
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
										action="${pageContext.request.contextPath}/admin/trainers">
							 <input type="hidden" name="${_csrf.parameterName}"
												value="${_csrf.token}" />
								<input type="hidden" id="pageNumber" name="pageNumber" value="${pageNumber}"/>
								<ul class="pagination">
									<li class="paginate_button previous disabled"
										id="example2_previous"><a onClick="javaScript:pageNo(0);"
										aria-controls="example2" data-dt-idx="0" tabindex="0">Previous</a></li>
									<li class="paginate_button active"><a onClick="javaScript:pageNo(1);"
										aria-controls="example2" data-dt-idx="1" tabindex="0">1</a></li>
									<li class="paginate_button "><a onClick="javaScript:pageNo(2);"
										aria-controls="example2" data-dt-idx="2" tabindex="0">2</a></li>
									<li class="paginate_button "><a onClick="javaScript:pageNo(3);"
										aria-controls="example2" data-dt-idx="3" tabindex="0">3</a></li>
									<li class="paginate_button "><a onClick="javaScript:pageNo(4);"
										aria-controls="example2" data-dt-idx="4" tabindex="0">4</a></li>
									<li class="paginate_button "><a onClick="javaScript:pageNo(5);"
										aria-controls="example2" data-dt-idx="5" tabindex="0">5</a></li>
									<li class="paginate_button "><a onClick="javaScript:pageNo(6);"
										aria-controls="example2" data-dt-idx="6" tabindex="0">6</a></li>
									<li class="paginate_button next" id="example2_next"><a
										onClick="javaScript:pageNo(7);" aria-controls="example2" data-dt-idx="7" tabindex="0">Next</a></li>
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
	function trainer(obj) {
		var url = "/targetec-web/ajax/model/trainer/edit";
		
		if(obj!='undefined' && obj!==""){
			url=url+"?id="+obj;
		}
		$.get(url,
				function(data) {
			debugger;
					$('#myModal').html($(data).html());
					$("#wizard-picture").change(function() {
						readURL(this);
						saveMedia("profileImage");
					});
				});
		$("#myModal").modal('show');
	}

	function saveTrainer() {
		var fname = $('#firstName').val();
		var id = $('#id').val();
		var lname = $('#lastName').val();
		var email = $('#email').val();
		var phoneNumber = $('#phoneNumber').val();
		var profileImage = $('#profileImage').val();
		var userName = $('#userName').val();
		 var regexEmail = new RegExp(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/);
		 var regexPh = new RegExp("^[0-9]+$");	
		 if(fname == '' || fname == null){
			showValidation('#firstName');
		}else if(lname == '' || lname == null){
			showValidation('#lastName');
		}else if(email == '' || email == null){
			showValidation('#email');
		}else if(phoneNumber == '' || phoneNumber == null){
			showValidation('#phoneNumber');
		}else if(profileImage == '' || profileImage == null){
			showValidation('#profileImage');
		}else if(userName == '' || userName == null){
			showValidation('#userName');
		}else  if(!regexEmail.test(email)){
			 $('#email').css('border-color', 'red');
			 $('#danger1').text('Please Enter Correct Email').removeClass('hide').slideDown('slow');
			 setTimeout(function() {
				  $('.alert-danger').slideUp('slow');
				  $('#email').css('border-color', '');
			}, 4000);
		 }else if(!regexPh.test(phoneNumber)){
			 $('#phoneNumber').css('border-color', 'red');
			 $('#danger1').text('Please Enter Correct Phone Number').removeClass('hide').slideDown('slow');
			 setTimeout(function() {
				  $('.alert-danger').slideUp('slow');
				  $('#phoneNumber').css('border-color', '');
			}, 4000);
		 }else{
				var url = "/targetec-web/ajax/student/validation?userName="+userName+"&id="+id;
			    $.ajax({
			        url : url,
			        type : 'GET',
			        success: function(response){
			        	if(response != ''){
			        		$('#userName').css('border-color', 'red');
			   			 $('#danger1').text(response).removeClass('hide').slideDown('slow');
			   			 setTimeout(function() {
			   				  $('.alert-danger').slideUp('slow');
			   				  $('#userName').css('border-color', '');
			   			}, 4000);
			        	}else{
			        		$
							.ajax({
								type : "GET",
								url : "/targetec-web/ajax/model/trainer/update",
								data : $('#trainer').serialize(),
								success : function() {
									$("#myModal").modal('hide');
									pageNo('1');
								}
							});

			        	}
			        } });
		 }
		
	}
	function showValidation(value){
		var msg =$(value).attr('placeholder');
		 $(value).css('border-color', 'red');
		$('#danger1').text('Please '+msg).removeClass('hide').slideDown('slow');
		 setTimeout(function() {
			  $('.alert-danger').slideUp('slow');
			  $(value).css('border-color', '');
		}, 4000);
	}
	
	function deleteTrainer(obj){
		if (confirm('You are proceeding to delete record')) {
		
		var id = obj;
		var pageNumber = $('#pageNumber').val();
		var url = "/targetec-web/ajax/trainer/delete?${_csrf.parameterName}=${_csrf.token}&id="+id+"&pageNumber="+pageNumber+"";
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

