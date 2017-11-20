<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<style>
.diform-controlv1 {
	width: 183px;
	height: 35px;
	padding: 0px;
	display: inline-flex;
	border: 1px dotted #aaaaaa;
}
</style>
<section class="content-header">
	<h1>
		Add <small>Answer Detail</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Accounts</a></li>
		<li class="active">Question</li>
		<li class="active">Answers</li>
	</ol>
	<form action="${pageContext.request.contextPath}/admin/ansdetail/update" method="post">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <input type="hidden" name="qId"
			value="${question.id}"> <input type="hidden" name="qstyleId"
			value="${question.styleMaster.id}"> <input type="hidden"
			name="cpq" value="${cpq}">
		<section class="content">

			<div class="box box-primary">
				<div class="box-header with-border">
					<div class="form-group col-md-9 col-lg-9">
						<b>Question Style : </b>&nbsp; ${question.typeMaster.name}
						(${question.styleMaster.description})
					</div>
					<div class="form-group col-md-3 col-lg-3">
						<div class="pull-right btn_bx">
							<button type="submit" class="btn btn-primary">Save</button>
						</div>
					</div>
				</div>
				<!-- /.box-header -->
				<div class="box-body">
					<div class="form-group col-md-12 col-lg-12">
						<div class="row">
							<div onmousedown='return false;' onselect='return false;'>
								<p>${question.description}</p>
							</div>
						</div>
						<div class="row">
							<div onmousedown='return false;' onselect='return false;'>
								<b>Direction : </b> ${question.direction}
							</div>
						</div>
						<div class="row">
							<div onmousedown='return false;' onselect='return false;'>
								<b>Question : </b>${question.questionText}
							</div>
						</div>
						<div class="row">
							<br>
							<b>Add the description for answers below</b>
						</div>
						<div class="row">
							<div class="form-group col-md-12 col-lg-12">
								<c:forEach items="${question.answers}" var="answer"
									varStatus="st">
									<ul>
										<li>${answer.answer}</li>
										<li><input class="form-control" type="text"
											name="ansdesc" value="${answer.description}">
											<input class="form-control" type="hidden" name="ansid"
											value="${answer.id}"></li>
									</ul>
								</c:forEach>
							</div>
						</div>

					</div>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->

			<!-- /.col -->
		</section>
	</form>
	<!-- /.row -->
</section>
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script>
	function allowDrop(ev) {
		ev.preventDefault();
	}

	function drag(ev) {
		ev.dataTransfer.setData("text", ev.target.id);
	}
	function drop(ev) {
		var val = $('#' + ev.target.id).val();
		if (val != null && val != '') {
			return false;
		}
		ev.preventDefault();
		var data = ev.dataTransfer.getData("text");
		ev.target.appendChild(document.getElementById(data));
	}
</script>
