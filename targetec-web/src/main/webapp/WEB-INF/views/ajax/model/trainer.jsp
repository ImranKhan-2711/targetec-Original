<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<div class="modal-dialog">
	<div class="modal-content"
		style="width: 50%; margin-left: 25%; margin-top: 5%">
		<div class="box box-primary">
			<div class="box-header with-border">
				<h3 class="box-title">Add/Edit Trainer</h3>
			</div>
			<div id="danger1"
								class="alert alert-danger alert-dismissible hide"></div>
			<!-- /.box-header -->
			<!-- form start -->
			<form role="form" id="trainer" method="post"
				action="${pageContext.request.contextPath}/ajax/model/trainer/update">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<div class="box-body">
				<input type="hidden" name="id" value="${trainer.id}" class="form-control">
				<input type="hidden" name="accountId" value="${trainer.account.id}" class="form-control"
								placeholder="Account Id">
					<div class="row">
						<div class="col-sm-2">
							<label for="exampleInputEmail1">First Name</label>
						</div>
						<div class="col-sm-5">
							<input type="text" id="firstName" name="firstName" value="${trainer.firstName}" class="form-control"
								placeholder="First name">
						</div>
					</div>
					<br />
					<div class="row">
						<div class="col-sm-2">
							<label for="exampleInputEmail1">Last Name</label>
						</div>
						<div class="col-sm-5">
							<input type="text" id="lastName" name="lastName" value="${trainer.lastName}" class="form-control" placeholder="Last name">
						</div>
					</div>
					<br />
					<div class="row">
						<div class="col-sm-2">
							<label for="exampleInputEmail1">Email</label>
						</div>
						<div class="col-sm-6">
							<input type="text" id="email" name="email" value="${trainer.email}" class="form-control" placeholder="Email">
						</div>
					</div>
					<br />
					<div class="row">
						<div class="col-sm-2">
							<label for="exampleInputEmail1">Phone Number</label>
						</div>
						<div class="col-sm-6">
							<input type="text" id="phoneNumber" name="phoneNumber" value="${trainer.phoneNumber}" class="form-control"
								placeholder="Phone Number">
						</div>
					</div>
					<br />
					<div class="row">
						<input type="hidden" name="profileImage" id="profileImage" value="${trainer.profileImage.id}"/> 
						<div class="col-sm-2">
							<label for="exampleInputEmail1">Profile Image</label>
						</div>
						<div class="col-sm-2">
							<div class="picture">
								<img src="${pageContext.request.contextPath}/resources/images/${trainer.profileImage.resourcePath}" class="picture-src" id="wizardPicturePreview"
									title=""> <input type="file" id="wizard-picture" class="">
							</div>
						</div>
						<div id="loadingimage" class="form-group hide">
							<img
								src="${pageContext.request.contextPath}/resources/img/loadings.gif"
								id="loading">
						</div>
					</div>
					<br/>
					<div class="row">
						<div class="col-sm-2">
							<label for="exampleInputEmail1">User Name</label>
						</div>
						<div class="col-sm-6">
							<input type="text" id="userName" name="userName" value="${trainer.account.userName}" class="form-control"
								placeholder="User Name">
						</div>
					</div>
				</div>
				<!-- /.box-body -->

				<div class="box-footer">
					<div class="row">
						<div class="col-sm-2">&nbsp;</div>
						<div class="col-sm-2">
							<button type="button" class="btn btn-block btn-primary"
								onclick="javascript:saveTrainer();">Submit</button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
