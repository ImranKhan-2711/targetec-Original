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
					<div class="form-group col-md-4 col-lg-12">
						<form
							action="${pageContext.request.contextPath}/student/detaillist"
							method="post">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							<div class="pull-right btn_bx">
								<button type="submit" class="btn btn-primary">Back</button>
							</div>
						</form>
					</div>
				</div>
				<form
					action="${pageContext.request.contextPath}/student/testdetaillive"
					method="post">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" /> <input type="hidden" name="qId"
						value="${question.id}"> <input type="hidden" name="lpq"
						value="${lpq}"> <input type="hidden" name="testDetailId"
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
						<div class="form-group col-md-12 col-lg-12">
							<p>${question.description}</p>

						</div>
						<!-- /.col -->
					</div>
					<div class="row">
						<div class="form-group col-md-12 col-lg-12">
							<b>Directions : </b>${question.direction}
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-12 col-lg-12">${question.questionText}</div>
					</div>
					<div class="row">
						<div class="form-group col-md-12 col-lg-12 " >
						<audio controls>
						  <source src="${pageContext.request.contextPath}/resources/media/${question.resourceMaster.resourcePath}" type="audio/mpeg">
						</audio>
						</div>
					</div>
					<div class="row">
						<div class=" table-responsive form-group col-md-12 col-lg-12">
							<table>
								<tr>
									<c:forEach items="${question.answers}" var="answers"
										varStatus="st">
										<td><input class="btn btn-success disabled"
											value="${st.count} ${answers.answer}"
											id="answer_${st.count-1}" disabled="disabled" /></td>
									</c:forEach>
								</tr>
							</table>
						</div>
					</div>
					<div class="row">
						<h4>
							<b>Reasons for correct answer</b>
						</h4>
						<div class=" table-responsive form-group col-md-12 col-lg-12">
								<c:forEach items="${question.answers}" var="answerss"
									varStatus="st">
									<ul>
										<li><div class="alert alert-warning alert-dismissible">
												${answerss.answer} - ${answerss.description}
											</div></li>
									</ul>
								</c:forEach>

						</div>
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