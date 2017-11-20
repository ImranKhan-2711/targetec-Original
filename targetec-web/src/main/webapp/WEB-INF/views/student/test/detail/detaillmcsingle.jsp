<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<style>
.div1 {
	width: 160px;
	height: 35px;
	padding: 0px;
	display: inline-flex;
	border: 1px dotted #aaaaaa;
}
</style>
<section class="content-header">
	<h1>
		Detail <small>${question.styleMaster.description}</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Dashboard</a></li>
		<li class="active">Detail Analysis</li>
	</ol>
	<div class="box box-primary">

		<div class="box-header with-border">


			<section class="content">
				<div class="row">
					<form
						action="${pageContext.request.contextPath}/student/detaillist"
						method="post">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<div class="form-group col-md-4 col-lg-12">
							<div class="pull-right btn_bx">
								<button type="submit" class="btn btn-primary">Back</button>
							</div>
						</div>
					</form>
				</div>
				<form
					action="${pageContext.request.contextPath}/student/testdetaillive"
					method="post">
					<input type="hidden" name="qId" value="${question.id}"> <input
						type="hidden" name="lpq" value="${lpq}"> <input
						type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<input type="hidden" name="testDetailId"
						value="${studentTestDetail.id}">
					<div class="row">
						<div class="callout callout-info form-group col-md-12 col-lg-12">
							<div class="col-md-6 col-lg-6">
								<h3 align="left">
									<b>Test Name : </b>${studentTestDetail.testMaster.name}
								</h3>
							</div>
							<div class="col-md-6 col-lg-6">
								<h3 align="right">
									<b>Test Date : </b>${studentTestDetail.performedOn}</h3>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="callout callout-info form-group col-md-4 col-lg-3">
							<h3 align="right">
								<b>${studentTestDetail.attempted}</b> Out of <b>${studentTestDetail.testMaster.questionCount}</b>
							</h3>
							<p align="right">Attempted</p>
						</div>
						<div class="callout callout-info form-group col-md-4 col-lg-3">
							<h3 align="right">
								<b>${studentTestDetail.corrected}</b> Out of <b>${studentTestDetail.testMaster.questionCount}</b>
							</h3>
							<p align="right">Corrected</p>
						</div>
						<div class="callout callout-info form-group col-md-4 col-lg-3">
							<h3 align="right">
								<b>${studentTestDetail.uncorrected}</b> Out of <b>${studentTestDetail.testMaster.questionCount}</b>
							</h3>
							<p align="right">Uncorrected</p>
						</div>
						<div class="callout callout-info form-group col-md-4 col-lg-3">
							<h3 align="right">
								<b>${hours}</b> hrs <b>${mints}</b> min <b>${seconds}</b> sec
							</h3>

							<p align="right">Total Time</p>
						</div>
					</div>
					<div class="row">

						<div class="form-group col-md-6 col-lg-6">
							<p>${question.description}</p>

						</div>
						<div class="form-group col-md-6 col-lg-6">
							<b>Directions : </b>${question.direction}<br> <b>${question.questionText}</b>
							<c:forEach items="${question.answers}" var="answers"
								varStatus="st">
								<ul>
									<li><c:choose>
											<c:when test="${answers.correctYN == 'Y'}">
												<div class=" alert alert-success alert-dismissible">

													<h4>
														<i class="icon fa fa-check"></i> Correct Answer
													</h4>

													<b>${answers.answer}</b><br>
													<b>Reasons for correct answer - </b>${answers.description}
												</div>
											</c:when>
											<c:otherwise>
												<div class=" alert alert-warning alert-dismissible">
													<h4>
														<i class="icon fa fa-times"></i> Incorrect Answer
													</h4>

													<b>${answers.answer}</b>

												</div>
											</c:otherwise>
										</c:choose></li>
								</ul>

							</c:forEach>
						</div>
						<!-- /.col -->
					</div>
					<div class="row">
						<div class="form-group col-md-12 col-lg-12">
							<div class="pull-right btn_bx">
								<button type="submit" class="btn btn-primary">Next</button>
							</div>
						</div>
					</div>
				</form>
			</section>
		</div>
	</div>
	<!-- /.row -->
</section>


<script type="text/javascript">
	
</script>