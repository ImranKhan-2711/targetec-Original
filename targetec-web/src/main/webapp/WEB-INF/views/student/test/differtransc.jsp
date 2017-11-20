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
		Test <small>List..</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Accounts</a></li>
		<li class="active">Test</li>
	</ol>
	<form action="processquest" id="studentquestion" method="post">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <input type="hidden" name="qId"
			value="${question.id}"> <input type="hidden" name="qstyleId"
			value="${question.styleMaster.id}"> <input type="hidden"
			name="cpq" value="${cpq}"><input type="hidden" value="${hrs}"
			id="hrs" name="hrs" /> <input type="hidden" value="${mints}"
			id="mints" name="mints" /> <input type="hidden" value="${sec}"
			id="sec" name="sec" />
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
								<audio controls controlsList="nodownload">
									<source
										src="${pageContext.request.contextPath}/resources/media/${question.resourceMaster.resourcePath}"
										type="audio/mpeg">
								</audio>
							</div>
							<div class="form-group col-md-12 col-lg-12">
								<div>${question.questionText}</div>
							</div>

							<div class="form-group col-md-12 col-lg-12">
								<div id="tablelist"></div>
							</div>

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
<script type="text/javascript">
	var p = $('p');

	p.html(function(index, oldHtml) {
		return oldHtml.replace(/\b(\w+?)\b/g, '<span class="word">$1</span>')
	});
	$('div span')
			.click(
					function() {
						if ($("#" + event.target.innerHTML).val() == event.target.innerHTML) {
							$(this).css('background-color', '');
							$("#" + event.target.innerHTML).remove();
						} else {
							$("#tablelist")
									.append(
											'<input type="hidden" id="'+event.target.innerHTML+'" name="answers" value="'+event.target.innerHTML+'">');

							$(this).css('background-color', '#9FD7FB');
						}

					});

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