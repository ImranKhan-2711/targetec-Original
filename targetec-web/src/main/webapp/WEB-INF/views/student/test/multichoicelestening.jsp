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
<script>
	function allowDrop(ev) {
		ev.preventDefault();
	}

	function drag(ev) {
		ev.dataTransfer.setData("text", ev.target.id);
	}

	function drop(ev) {
		ev.preventDefault();
		var data = ev.dataTransfer.getData("text");
		ev.target.appendChild(document.getElementById(data));
	}
</script>
<section class="content-header">
	<h1>
		Test: > <small>${test.name}</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Accounts</a></li>
		<li class="active">Test</li>
	</ol>
	<form action="processquest" id="studentquestion" method="post">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <input type="hidden" name="qId"
			value="${question.id}"> <input type="hidden" name="cpq"
			value="${cpq}"> <input type="hidden" name="qstyleId"
			value="${question.styleMaster.id}"><input type="hidden"
			value="${hrs}" id="hrs" name="hrs" /> <input type="hidden"
			value="${mints}" id="mints" name="mints" /> <input type="hidden"
			value="${sec}" id="sec" name="sec" />
		<section class="content">
			<div class="row">
				<div class="col-sm-12">
					<div class="box box-primary">
						<div class="box-header with-border">
							<div class="col-sm-12">
								<div class="callout callout-info form-group col-md-6 col-lg-4">
									<h4 align="right" id="r"></h4>
								</div>
								<div class="callout callout-info form-group col-md-6 col-lg-4">
									<h4 align="right">
										<span>${cpq + 1} Out Of ${quesCount}</span>
									</h4>
								</div>
								<div class="callout callout-info form-group col-md-6 col-lg-4">
									<h4 align="right">
										<span>Total Time for current Test : </span>${totalTime }
									</h4>
								</div>
							</div>
							<h3 class="box-title">
								<b>Directions:</b>&nbsp;
							</h3>
							${question.direction}
						</div>
						<!-- /.box-header -->
						<div class="box-body">
							<div class="form-group col-md-12 col-lg-12">
								<p>${question.questionText}</p>
							</div>
							<div class="form-group col-md-12 col-lg-12">
								<audio controls controlsList="nodownload">
									<source
										src="${pageContext.request.contextPath}/resources/media/${question.resourceMaster.resourcePath}"
										type="audio/mpeg">
								</audio>
							</div>
							<table>
								<tbody>
									<c:forEach items="${question.answers}" var="answer"
										varStatus="st">
										<tr>
											<td><input type="checkbox" value="${st.count-1}"
												id="correctYN-${st.count-1}" name="correctYN" /> <input
												id="answers${st.count-1}" name="answers" type="hidden"
												value="${answer.answer}"> <label>${answer.answer}</label>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<!-- /.box-body -->
					</div>
					<!-- /.box -->
				</div>
				<!-- /.col -->
			</div>
			<div class="row">
				<div class="form-group col-md-4 col-lg-12">
					<div class="pull-right btn_bx">
						<button type="submit" class="btn btn-primary">Continue</button>
					</div>
				</div>
			</div>
		</section>
	</form>
	<!-- /.row -->
</section>
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script>
	$(function() {

		var hrs = $('#hrs').val();
		var mints = $('#mints').val();
		var sec = $('#sec').val();
		var time = Number(hrs) * 60 * 60 + Number(mints) * 60 + Number(sec);
		var r = document.getElementById('r')
		var tmp = time;
		var flag = false;
		setInterval(function() {
			if (flag == false) {
				var c = tmp--, m = (c / 60) >> 0, s = (c - m * 60) + '';
				if (c == 1 || c == -1) {
					flag = true;
				}
				r.textContent = ' Time Left For Current Question: ' + m + ':'
						+ (s.length > 1 ? '' : '0') + s
				tmp != 0 || (tmp = time);
				if (flag) {
					document.getElementById('studentquestion').submit();
				}
			}
		}, 1000);

	});
</script>
