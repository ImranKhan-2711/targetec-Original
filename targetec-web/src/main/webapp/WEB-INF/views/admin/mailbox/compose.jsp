<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<section class="content-header">
	<h1>
		Compose <small>13 new messages</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Accounts</a></li>
		<li class="active">Compose</li>
	</ol>

	<div class="row">
		<div class="col-md-3">
			<a href="${pageContext.request.contextPath}/admin/mailbox" class="btn btn-primary btn-block margin-bottom">Back
				to Inbox</a>

			<div class="box box-solid">
				<div class="box-header with-border">
					<h3 class="box-title">Folders</h3>

					<div class="box-tools">
						<button type="button" class="btn btn-box-tool"
							data-widget="collapse">
							<i class="fa fa-minus"></i>
						</button>
					</div>
				</div>
				<div class="box-body no-padding">
					<ul class="nav nav-pills nav-stacked">
						<li><a href="mailbox.html"><i class="fa fa-inbox"></i>
								Inbox <span class="label label-primary pull-right">12</span></a></li>
						<li><a href="#"><i class="fa fa-envelope-o"></i> Sent</a></li>
						<li><a href="#"><i class="fa fa-file-text-o"></i> Drafts</a></li>
						<li><a href="#"><i class="fa fa-filter"></i> Junk <span
								class="label label-warning pull-right">65</span></a></li>
						<li><a href="#"><i class="fa fa-trash-o"></i> Trash</a></li>
					</ul>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /. box -->
			<div class="box box-solid">
				<div class="box-header with-border">
					<h3 class="box-title">Labels</h3>

					<div class="box-tools">
						<button type="button" class="btn btn-box-tool"
							data-widget="collapse">
							<i class="fa fa-minus"></i>
						</button>
					</div>
				</div>
				<!-- /.box-header -->
				<div class="box-body no-padding">
					<ul class="nav nav-pills nav-stacked">
						<li><a href="#"><i class="fa fa-circle-o text-red"></i>
								Important</a></li>
						<li><a href="#"><i class="fa fa-circle-o text-yellow"></i>
								Promotions</a></li>
						<li><a href="#"><i class="fa fa-circle-o text-light-blue"></i>
								Social</a></li>
					</ul>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
		<!-- /.col -->
		<div class="col-md-9">
			<div class="box box-primary">
				<div class="box-header with-border">
					<h3 class="box-title">Compose New Message</h3>
				</div>
				<!-- /.box-header -->
				<div class="box-body">
					<div class="form-group">
						<input class="form-control" placeholder="To:">
					</div>
					<div class="form-group">
						<input class="form-control" placeholder="Subject:">
					</div>
							<div class="row">
							 		<div class="form-group col-md-12 col-lg-12">
						                <textarea class="textarea" id ="questionText" name="questionText" placeholder="Enter Question Text" style="width: 100%; height: 200px; font-size: 14px; line-height: 18px; border: 1px solid #dddddd; padding: 10px;">${question.questionText}</textarea>
						            </div>
				          </div>
				</div>
				<!-- /.box-body -->
				<div class="box-footer">
					<div class="pull-right">
						<button type="button" class="btn btn-default">
							<i class="fa fa-pencil"></i> Draft
						</button>
						<button type="submit" class="btn btn-primary">
							<i class="fa fa-envelope-o"></i> Send
						</button>
					</div>
				</div>
				<!-- /.box-footer -->
			</div>
			<!-- /. box -->
		</div>
		<!-- /.col -->
	</div>

</section>

<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script type="text/javascript">
$(function () {
    $(".textarea").wysihtml5();
});
    
</script>
