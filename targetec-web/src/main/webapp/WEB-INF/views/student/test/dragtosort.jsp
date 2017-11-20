<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<style>
#qselected, #qlist {
	display: inline-block;
	min-height: 450px;
}

.sort-answer {
	padding: 15px;
	background: #fff;
}

.sort-answer ul li {
	background: #f1f1f1;
	border: 1px solid #ccc;
	padding: 10px;
	margin: 10px 0;
	cursor: pointer;
	width: 100% !important;
}

.sort-answer ul {
	list-style: none;
	border: 1px solid #f1f1f1;
}

@media ( max-width :767px) {
	#qselected, #qlist {
		min-height: 150px;
	}
}
</style>
<section class="content-header">
	<h1>
		Test <small>Drag to Sort</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Accounts</a></li>
		<li class="active">Test</li>
	</ol>
	<section class="content">
		<form action="processquest" id="studentquestion" method="post">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" /> <input type="hidden" name="qId"
				value="${question.id}"> <input type="hidden" name="qstyleId"
				value="${question.styleMaster.id}"> <input type="hidden"
				name="cpq" value="${cpq}"> <input type="hidden"
				value="${hrs}" id="hrs" name="hrs" /> <input type="hidden"
				value="${mints}" id="mints" name="mints" /> <input type="hidden"
				value="${sec}" id="sec" name="sec" />
			<div class="row" onmousedown='return false;' onselect='return false;'>


				<div class="row intro-center sort-answer">
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
						<div class="box-body">
							<p>${question.questionText}</p>

							<ul class="col-xs-12 col-sm-6" id="qlist">
								<c:forEach items="${question.answers}" var="answer"
									varStatus="st">
									<li class="qitem" id="qitem${st.count-1}"
										value="${answer.answer}">${answer.answer}<input
										value="${answer.answer}" name="answers" type="hidden" /></li>
								</c:forEach>
							</ul>

							<ul class="col-xs-12 col-sm-6" id="qselected"></ul>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-4 col-lg-12">
					<div class="pull-right btn_bx">
						<button type="submit" class="btn btn-primary">Continue</button>
					</div>
				</div>
			</div>
		</form>

	</section>
</section>
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script>
	$(function() {

		$("#qselected").sortable();
		$("#qselected").disableSelection();

		$(".qitem").draggable({
			containment : "#container",
			helper : 'clone',
			revert : 'invalid'
		});

		$("#qselected, #qlist").droppable({
			hoverClass : 'ui-state-highlight',
			accept : ":not(.ui-sortable-helper)",
			drop : function(ev, ui) {
				$(ui.draggable).clone().appendTo(this);
				$(ui.draggable).remove();
			}

		});

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
