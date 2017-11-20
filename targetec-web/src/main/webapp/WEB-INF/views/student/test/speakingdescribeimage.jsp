<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<style>
#btns {
	display: none;
}
</style>

<section class="content-header">
	<h1>
		Test: <small>Describe image</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Accounts</a></li>
		<li class="active">Test</li>
	</ol>
	<section class="content">
	
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
		<div class="form-group  col-md-6 col-lg-6 ">
			<div class="">
				<div class="box box-primary" onmousedown='return false;'
					onselect='return false;'>
					<div class="box-header with-border">
						<div class="form-group col-md-6 col-lg-6">
							<h3 class="box-title">
								<b>Directions:</b>&nbsp;
							</h3>
							${question.direction}
						</div>
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<div class="form-group col-md-6 col-lg-6">
							<p>${question.questionText}</p>
						</div>
					</div>
					<!-- /.box-body -->
				</div>
				<!-- /.box -->
			</div>
			<div class="form-group col-md-6 col-lg-6">
				<div id='gUMArea'>
					<div>
						Record: <input type="radio" name="media" value="video" checked
							id='mediaVideo'>Video <input type="radio" name="media"
							value="audio">audio
					</div>
					<button class="btn btn-default" id='gUMbtn'>Click Here To
						Record</button>
				</div>
				<div id='btns'>
					<button class="btn btn-default" id='start'>Start</button>
					<button class="btn btn-default" id='stop'>Stop</button>
				</div>
				<div>
					<ul class="list-unstyled" id='ul'></ul>
				</div>
			</div>
		</div>
		<div class="form-group  col-md-6 col-lg-6 ">
			<img
				src="${pageContext.request.contextPath}/resources/images/${question.resourceMaster.resourcePath}"
				alt="Mountain View"
				style='height: 70%; width: 70%; margin: auto; display: block;'>

		</div>
		<div class="form-group col-md-4 col-lg-12">
			<div class="pull-right btn_bx">
				<form action="processquest"  id="studentquestion"  method="post">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" /> <input type="hidden" name="qId"
						value="${question.id}"> <input type="hidden" name="cpq"
						value="${cpq}"> <input type="hidden" name="qstyleId"
						value="${question.styleMaster.id}"><input type="hidden"
							value="${hrs}" id="hrs" name="hrs" /> <input type="hidden"
							value="${mints}" id="mints" name="mints" /> <input type="hidden"
							value="${sec}" id="sec" name="sec" />
					<button type="submit" class="btn btn-primary">Continue</button>
				</form>
			</div>
		</div>
	</section>
</section>
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script>
	$(function() {

		var hrs = $('#hrs').val();
		var mints = $('#mints').val();
		var sec = $('#sec').val();
		var time=Number(hrs)*60*60 + Number(mints)*60 + Number(sec);
		var r=document.getElementById('r')
		var tmp=time;
		var flag = false;
		setInterval(function(){
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
		},1000);
		
	});
</script>