<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>TargetEC | Dashboard</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap/bootstrap.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap/bootstrap.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/css/gsi-step-indicator.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/css/image-upload.css" />">
<!-- Font Awesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
<!-- jvectormap -->
<link rel="stylesheet"
	href="<c:url value="/resources/css/jvectormap/jquery-jvectormap-1.2.2.css"/>">
<!-- Theme style -->
<!-- DataTables -->
<link rel="stylesheet"
	href="<c:url value="/resources/css/plugins/datatables/dataTables.bootstrap.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/css/AdminLTE.min.css" />">
<!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet"
	href="<c:url value="/resources/css/skins/_all-skins.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css" />">
<link
	href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css"
	rel="stylesheet">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
<style type="text/css">
.bs-example {
	margin: 20px;
}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<header class="main-header">
			<!-- Logo -->
			<a href="index2.html" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
				<span class="logo-mini"><b>T</b>EC</span> <!-- logo for regular state and mobile devices -->
				<span class="logo-lg"><b>Target</b>EC</span>
			</a>
			<!-- Header Navbar: style can be found in header.less -->
			<nav class="navbar navbar-static-top">
				<!-- Sidebar toggle button-->
				<a href="#" class="sidebar-toggle" data-toggle="offcanvas"
					role="button"> <span class="sr-only">Toggle navigation</span>
				</a>

				<div class="navbar-custom-menu">
					<ul class="nav navbar-nav">
						<li class="messages-menu"><a href="${pageContext.request.contextPath}/admin/mailbox"><i
								class="fa fa-envelope-o"></i><span class="label label-success">4</span></a>
						</li>
						<!-- User Account: style can be found in dropdown.less -->
						<li class="dropdown user user-menu"><a href="#"
							class="dropdown-toggle" data-toggle="dropdown"> <img
								src="${pageContext.request.contextPath}/resources/images/${home.profileImage.resourcePath}"
								class="user-image" alt="User Image"> <span
								class="hidden-xs">
									${home.firstName}&nbsp;${home.lastName}</span>
						</a>
							<ul class="dropdown-menu">
								<!-- User image -->
								<li class="user-header"><img
									src="${pageContext.request.contextPath}/resources/images/${home.profileImage.resourcePath}"
									class="img-circle" alt="User Image">

									<p>
										${home.firstName}&nbsp;${home.lastName} <small>Member
											since Nov. 2012</small>
									</p></li>
								<!-- Menu Footer-->
								<li class="user-footer">
									<div class="pull-left">
										<a href="${pageContext.request.contextPath}/admin/profile"
											class="btn btn-default btn-flat">Profile</a>
									</div>
									<div class="pull-right">
										<a href="${pageContext.request.contextPath}/admin/logout"
											class="btn btn-default btn-flat">Sign out</a>
									</div>
								</li>
							</ul></li>
						<li class="dropdown user user-menu"><a href="#"
							class="dropdown-toggle" data-toggle="dropdown"><i
								class="fa fa-gears"></i> </a>
							<ul class="dropdown-menu">
								<li class="user-footer">
									<div class="pull-right">
										<a
											href="${pageContext.request.contextPath}/admin/changepassword"
											class="btn btn-default btn-flat">Change Password</a>
									</div>
								</li>
							</ul></li>
					</ul>
				</div>
			</nav>
		</header>
		<!-- Left side column. contains the logo and sidebar -->
		<aside class="main-sidebar">
			<!-- sidebar: style can be found in sidebar.less -->
			<section class="sidebar">
				<!-- Sidebar user panel -->
				<div class="user-panel">
					<div class="pull-left image">
						<img
							src="${pageContext.request.contextPath}/resources/images/${home.profileImage.resourcePath}"
							class="img-circle" alt="User Image">
					</div>
					<div class="pull-left info">
						<p>${home.firstName}&nbsp;${home.lastName}</p>
						<a href="#"><i class="fa fa-circle text-success"></i> Online</a>
					</div>
				</div>
				<!-- search form -->
				<form action="#" method="get" class="sidebar-form">
					<div class="input-group">
						<input type="text" name="q" class="form-control"
							placeholder="Search..."> <span class="input-group-btn">
							<button type="submit" name="search" id="search-btn"
								class="btn btn-flat">
								<i class="fa fa-search"></i>
							</button>
						</span>
					</div>
				</form>
				<!-- /.search form -->
				<!-- sidebar menu: : style can be found in sidebar.less -->
				<form role="form" id="listform" method="post"
					action="${pageContext.request.contextPath}/admin">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
					<ul class="sidebar-menu">
						<li class="treeview" class="treeview"><a href="#"> <i
								class="fa fa-dashboard"></i> <span>Dashboard</span>
						</a></li>
						<li class="treeview" onclick="submitFormAction('students');"><a
							href="#"><i class="fa fa-user"></i> Students</a></li>
						<li class="treeview" onclick="submitFormAction('trainers');"><a
							href="#"><i class="fa fa-user"></i> Trainer</a></li>
						<li class="treeview" onclick="submitFormAction('questions');"><a
							href="#"><i class="fa fa-question-circle"></i> Question</a> <input
							type="hidden" value="10000001" name="typeId" id="typeId"></li>
						<li class="treeview" onclick="submitFormAction('testlist');"><a
							href="#"><i class="fa fa-graduation-cap"></i>All Test</a></li>
					</ul>
				</form>
			</section>
			<!-- /.sidebar -->
		</aside>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<tiles:insertAttribute name="content" />
		</div>
		<!-- /.content-wrapper -->
		<footer class="main-footer">
			
		</footer>

		<!-- Control Sidebar -->
 
		<!-- /.control-sidebar -->
		<!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
		<div class="control-sidebar-bg"></div>
	</div>
	<!-- ./wrapper -->

	<!-- jQuery 2.2.3 -->
	<script src="<c:url value="/resources/js/jquery-2.2.3.min.js" />"></script>
	<!-- Bootstrap 3.3.6 -->
	<script
		src="<c:url value="/resources/js/bootstrap/bootstrap.min.js" />"></script>
	<!-- DataTables -->
	<script
		src="<c:url value="/resources/js/plugins/datatables/jquery.dataTables.min.js" />"></script>
	<script
		src="<c:url value="/resources/js/plugins/datatables/dataTables.bootstrap.min.js" />"></script>
	<script
		src="<c:url value="/resources/js/slimScroll/jquery.slimscroll.min.js" />"></script>
	<!-- FastClick -->
	<script src="<c:url value="/resources/js/fastclick/fastclick.js" />"></script>

	<!-- AdminLTE App -->
	<script src="<c:url value="/resources/js/app.min.js" />"></script>
	<!-- jQuery UI 1.11.4 -->
	<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
	<script src="<c:url value="/resources/js/jquery.maskedinput.js" />"></script>
	<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
	<script>
		$.widget.bridge('uibutton', $.ui.button);
	</script>

	<!-- Morris.js charts -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
	<!-- script src="plugins/morris/morris.min.js"></script-->
	<!-- Sparkline -->
	<script
		src="<c:url value="/resources/js/sparkline/jquery.sparkline.min.js" />"></script>
	<!-- jvectormap -->
	<script
		src="<c:url value="/resources/js/jvectormap/jquery-jvectormap-1.2.2.min.js" />"></script>
	<script
		src="<c:url value="/resources/js/jvectormap/jquery-jvectormap-world-mill-en.js" />"></script>
	<!-- jQuery Knob Chart -->
	<!-- script src="plugins/knob/jquery.knob.js"></script-->
	<!-- daterangepicker -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.11.2/moment.min.js"></script>
	<!-- script src="plugins/daterangepicker/daterangepicker.js"></script-->
	<!-- datepicker -->
	<!-- script src="plugins/datepicker/bootstrap-datepicker.js"></script-->
	<!-- Bootstrap WYSIHTML5 -->
	<!-- script src="plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script-->
	<!-- Slimscroll -->

	<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
	<!-- script src="<c:url value="/resources/js/pages/dashboard.js" />"></script-->
	<!-- AdminLTE for demo purposes -->
	<script src="<c:url value="/resources/js/demo.js" />"></script>

	<script
		src="<c:url value="/resources/js/bootstrap/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.js" />"></script>

</body>
<script type="text/javascript">
	function readURL(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			debugger;
			reader.onload = function(e) {

				$('#wizardPicturePreview').attr('src', e.target.result).fadeIn(
						'slow');
			}
			reader.readAsDataURL(input.files[0]);
		}
	}

	function saveMedia(imageId) {
		var formData = new FormData();
		formData.append('file', $('input[type=file]')[0].files[0]);
		$('#loadingimage').removeClass('hide');
		$
				.ajax({
					url : '/targetec-web/ajax/upload?${_csrf.parameterName}=${_csrf.token}',
					data : formData,
					processData : false,
					contentType : false,
					type : 'POST',
					success : function(data) {
						$('#loadingimage').addClass('hide');
						$("#" + imageId).val(data);
					},
					error : function(err) {
						alert(err);
					}
				});
	}

	function submitFormAction(val) {
		document.getElementById('listform').action = document
				.getElementById('listform').action
				+ "/" + val;
		document.getElementById('listform').submit();
	}
</script>
</html>