<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<section class="content-header">
	<h1>
		Change <small>Password</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Accounts</a></li>
		<li class="active">Change Password</li>
	</ol>

	<div class="box box-primary">

		<!-- /.box-header -->
		<!-- form start -->
		<form role="form" id="trainer" method="post"
			action="${pageContext.request.contextPath}/admin/password/update">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" /> <input type="hidden" name="id" id="id"
				value="${home.id}" /> <input type="hidden" name="accountId"
				value="${home.account.id}" class="form-control"
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
					<div class="form-group col-md-12 col-lg-12 ">
						<label style="margin-top: 14px;">Current Password</label> <input
							class="form-control" name="currentPassword"
							placeholder="Enter Current Password">
					</div>
				</div>
				<div class="divider-1 col-lg-12"></div>
				<div class="row">
					<div class="form-group col-md-12 col-lg-12 ">
						<label style="margin-top: 14px;">New Password</label> <input
							class="form-control" name="newPassword"
							placeholder="Enter New Password">
					</div>
				</div>
				<div class="divider-1 col-lg-12"></div>
				<div class="row">
					<div class="form-group col-md-12 col-lg-12 ">
						<label style="margin-top: 14px;">Confirm Password</label> <input
							class="form-control" name="confirmPassword"
							placeholder="Enter Confirm Password">
					</div>
				</div>
			</div>
		</form>
	</div>

</section>

<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script type="text/javascript">
	
</script>
