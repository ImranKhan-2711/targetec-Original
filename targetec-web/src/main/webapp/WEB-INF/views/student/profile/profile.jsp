<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<section class="content-header">
	<h1>
		Edit <small>Profile</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Accounts</a></li>
		<li class="active">Edit Profile</li>
	</ol>

	<div class="box box-primary">

		<!-- /.box-header -->
		<!-- form start -->
		<form role="form" id="trainer" method="post"
			action="${pageContext.request.contextPath}/student/profile/update">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
				<input type="hidden" name="id" id="id" value="${home.id}"/>
				<input type="hidden" name="accountId" value="${home.account.id}" class="form-control"
								placeholder="Account Id">
			<div class="box-body">
				<div class="row">
					<div class="form-group col-md-4 col-lg-12">
						<div class="pull-right btn_bx">
							<a href="" class="btn btn-default">Close</a>
							<button type="submit" class="btn btn-primary">Save
								changes</button>

						</div>
					</div>
				</div>

				<div class="divider-1 col-lg-12"></div>
				<div class="row">
					<div class="form-group col-md-6 col-lg-6 ">
						<label style="margin-top: 14px;">First Name</label> <input
							class="form-control" name="firstName"
							placeholder="Enter First Name" value="${home.firstName}">
					</div>
					<div class="form-group col-md-6 col-lg-6 ">
						<label style="margin-top: 14px;">Last Name</label> <input
							class="form-control" name="lastName"
							placeholder="Enter Last Name" value="${home.lastName}">
					</div>
				</div>
				<div class="divider-1 col-lg-12"></div>
				<div class="row">
					<div class="form-group col-md-6 col-lg-6 ">
						<label style="margin-top: 14px;">Email</label> <input
							class="form-control" name="email" placeholder="Enter Email"
							value="${home.email}">
					</div>
					<div class="form-group col-md-6 col-lg-6 ">
						<label style="margin-top: 14px;">Phone</label> <input
							class="form-control" name="phoneNumber"
							placeholder="Enter Phone Number" value="${home.phoneNumber}">
					</div>
				</div>
				<div class="divider-1 col-lg-12"></div>
				<div class="row">
					<div class="form-group col-md-6 col-lg-6 ">
					<input type="hidden" name="resourceId" id="resourceId" value="${home.profileImage.id}"/> 
						<label style="margin-top: 14px;">Profile Image</label> 
							<div class="picture" >
								<img src="${pageContext.request.contextPath}/resources/images/${home.profileImage.resourcePath}" class="picture-src" id="wizardPicturePreview"
									title=""> <input type="file" id="wizard-picture" onchange="uploadmedia(this);" class="">
							</div>
					</div>
				</div>
			</div>
		</form>
	</div>

</section>

<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script type="text/javascript">
function readURL(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		debugger;
		reader.onload = function(e) {

			$('#wizardPicturePreview').attr('src', e.target.result).fadeIn(
					'slow');
		}
		reader.readAsDataURL(input.files[0]);
	}
}
function uploadmedia(obj){
	readURL(obj);
	var formData = new FormData();
    formData.append('file', $('input[type=file]')[0].files[0]);
    $.ajax({
        url : '/targetec-web/ajax/upload?${_csrf.parameterName}=${_csrf.token}',
        data : formData,
        processData : false,
        contentType : false,
        type : 'POST',
        success : function(data) {
            $("#resourceId").val(data);
        },
        error : function(err) {
            alert(err);
        }
    });
}
</script>
