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
	<form action="${pageContext.request.contextPath}/student/detaillist"
		method="post">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <input type="hidden" name="qId"
			value="${question.id}"> <input type="hidden" name="cpq"
			value="${cpq}">
		<section class="content">
			<div class="row">
				<div class="form-group col-md-4 col-lg-12">
					<div class="pull-right btn_bx">
						<button type="submit" class="btn btn-primary">Back</button>
					</div>
				</div>
			</div>
			<div class="row">

				<div class="form-group col-md-12 col-lg-12 callout callout-danger">
					<h4>Warning!</h4>

					<p>Students can perform 2 times on each Test</p>
				</div>
				<!-- /.col -->
			</div>

		</section>
	</form>
	<!-- /.row -->
</section>


<script type="text/javascript">
	
</script>