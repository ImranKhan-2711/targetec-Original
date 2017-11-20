<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<div class="modal-dialog">
	<div class="modal-content"
		style="width: 50%; margin-left: 25%; margin-top: 5%">
		<div class="box box-primary">
			<div class="box-header with-border">
				<h3 class="box-title">Add/Edit Test</h3>
			</div>
				<div id="danger1"
								class="alert alert-danger alert-dismissible hide"></div>
			<!-- /.box-header -->
			<!-- form start -->
			<form role="form" id="testlistid" method="post"
				action="${pageContext.request.contextPath}/ajax/model/test/update">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<div class="box-body">
					<input type="hidden" name="id" id="id" value="${testlist.id}"
						class="form-control">
					<div class="row">
						<div class="form-group col-md-6 col-lg-6">
							<label for="">Test Name</label> <input class="form-control"
								value="${testlist.name}" type="text" id="testName"
								name="testName" class="form-control" placeholder="Enter Test Name" />
						</div>

						<div class="form-group col-md-6 col-lg-6">
							<label for="">Test Description</label> <input
								class="form-control" value="${testlist.description}" type="text"
								id="description" name="description" class="form-control"
								placeholder="Enter Test Description" />
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-6 col-lg-6">
							<label for="">Test Type</label> <select id="selectedTypeId"
								name="selectedTypeId" class="form-control" required="required">
								<option value="">--Select--</option>
								<c:forEach items="${types}" var="type" varStatus="st">
									<c:choose>
										<c:when test="${type.id==testlist.typeMaster.id}">
											<option selected value="${type.id}">${type.name}</option>
										</c:when>
										<c:otherwise>
											<option value="${type.id}">${type.name}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</div>
						<div class="form-group col-md-6 col-lg-6">
							<label for="">Test Category</label> <select
								id="selectedCategoryId" name="selectedCategoryId"
								class="form-control" required="required">
								<option value="">--Select--</option>
								<c:forEach items="${categories}" var="category" varStatus="st">
									<c:choose>
										<c:when test="${category.id==testlist.categoryMaster.id}">
											<option selected value="${category.id}">${category.name}</option>
										</c:when>
										<c:otherwise>
											<option value="${category.id}">${category.name}</option>
										</c:otherwise>
									</c:choose>

								</c:forEach>
							</select>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-6 col-lg-6">
							<input type="hidden" id="resourceId" name="resourceId"
								value="${testlist.image.id}" placeholder="Choose Image" />
							<div class="col-sm-2">
								<label for="exampleInputEmail1">Profile Image</label>
							</div>
							<div class="col-sm-5">
								<div class="picture">
									<img
										src="${pageContext.request.contextPath}/resources/images/${testlist.image.resourcePath}"
										class="picture-src" id="wizardPicturePreview" title="">
									<input type="file" onchange="uploadmedia();"
										id="wizard-picture" class="">
								</div>
							</div>
							<div id="loadingimage" class="col-sm-2 hide">
								<img
									src="${pageContext.request.contextPath}/resources/img/loadings.gif"
									id="loading">
							</div>
						</div>

						<div class="form-group col-md-6 col-lg-6">
							<label for="">Question Count</label> <input class="form-control"
								value="${testlist.questionCount}" type="text" id="questionCount"
								name="questionCount" class="form-control"  disabled="disabled"
								placeholder="Question Count" />
						</div>
					</div>
					<!-- div class="row">
						<div class="form-group col-md-6 col-lg-6">
							<label for="" >Duration</label>
							<input  class="form-control" value="${testlist.duration}"	type="time" step="600" id="duration" name="duration" class="form-control"
								placeholder="Test Duration Time" />
						</div>
					</div> -->
				</div>
				<!-- /.box-body -->

				<div class="box-footer">
					<div class="row">
						<div class="col-sm-2">&nbsp;</div>
						<div class="col-sm-2">
							<button type="button" class="btn btn-block btn-primary"
								onclick="javascript:saveTest();">Submit</button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
