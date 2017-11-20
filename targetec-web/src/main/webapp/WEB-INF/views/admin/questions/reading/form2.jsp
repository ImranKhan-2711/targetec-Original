<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<section class="content-header">
	<h1>
		Create <small>Question</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Accounts</a></li>
		<li class="active">Reading Question</li>
	</ol>

	<div class="box box-primary">


		<div class="box-header with-border">
		<div id="danger" class="alert alert-danger alert-dismissible hide"></div>
			<c:if test="${msg != null}">
				<div id="success" class="alert alert-success alert-dismissible ">${msg}</div>
			</c:if>
			<h3 class="box-title">Add/Edit Fill in the Blanks (Short)
				Questions</h3>
			<div class="row">
				<div class="form-group  col-md-4 col-lg-4 ">
					<div class="radio">
						<label> <input type="radio" name="styleId" id="styleId1"
							value="10000001"
							onclick="window.location='/targetec-web/admin/reading/question/form/edit?typeId=10000001&styleId=10000001&questionId=';">
							Multiple Choice
						</label>
					</div>
				</div>
				<div class="form-group  col-md-4 col-lg-4 ">
					<div class="radio">
						<label> <input type="radio" name="styleId" id="styleId2"
							value="10000002" checked="checked"
							onclick="window.location='/targetec-web/admin/reading/question/form/edit?typeId=10000001&styleId=10000002&questionId=';">
							Fill in the Blanks (Short)
						</label>
					</div>
				</div>
				<div class="form-group  col-md-4 col-lg-4 ">
					<div class="radio">
						<label> <input type="radio" name="styleId" id="styleId3"
							value="10000003"
							onclick="window.location='/targetec-web/admin/reading/question/form/edit?typeId=10000001&styleId=10000003&questionId=';">
							Re-Order
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group  col-md-4 col-lg-4 ">
					<div class="radio">
						<label> <input type="radio" name="styleId" id="styleId4"
							value="10000004"
							onclick="window.location='/targetec-web/admin/reading/question/form/edit?typeId=10000001&styleId=10000004&questionId=';">
							Fill in the Blanks (Long)
						</label>
					</div>
				</div>
			</div>
		</div>
		<!-- /.box-header -->
		<!-- form start -->
		<form role="form" id="trainer" method="post"
			action="${pageContext.request.contextPath}/admin/form/test/update?typeId=10000001&styleId=10000002">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" /> <input type="hidden" name="questionId"
				value="${question.id}" /> <input type="hidden" name="status"
				value="${question.status}" /> <input type="hidden"
				name="resourceId" value="10000067" />
			<div class="box-body">
				<div class="box">
					<div class="box-body pad">
						<div class="row">
							<div class="form-group col-md-2 col-lg-2 ">
								<label style="margin-top: 14px;">Question Code</label>
							</div>
							<div class="form-group col-md-4 col-lg-4">
								<textarea class="form-control" name="questionCode"
									placeholder="Enter Description">${question.questionCode}</textarea>
							</div>

							<div class="form-group col-md-6 col-lg-6">
								<div class="pull-right btn_bx">
									<a href="" class="btn btn-default">Close</a>
									<button type="submit" class="btn btn-primary">Save
										changes</button>

								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-md-2 col-lg-2 ">
								<label style="margin-top: 14px;">Description</label>
							</div>
							<div class="form-group col-md-10 col-lg-10">
								<textarea class="form-control" name="description"
									placeholder="Enter Description">${question.description}</textarea>
							</div>
						</div>
						<div class="divider-1 col-lg-12"></div>
						<div class="row">
							<div class="form-group col-md-2 col-lg-2 ">
								<label style="margin-top: 14px;">Directions</label>
							</div>
							<div class="form-group col-md-10 col-lg-10">
								<textarea class="form-control" name="direction"
									placeholder="Enter Direction">${question.direction}</textarea>
							</div>
						</div>
						<div class="divider-1 col-lg-12"></div>
						<!-- div class="row">
									<div class="form-group col-md-2 col-lg-2 " >
										<label  style="margin-top: 14px;">Question Text</label>	
									</div>
									<div class="form-group col-md-8 col-lg-8" >
										<textarea class="form-control" id="questionText"  placeholder="Enter Question Text" name="questionText">${question.questionText}</textarea>
									</div>
									<div class="form-group col-md-2 col-lg-2 " >
										<button type="button" class="btn btn-sucess pull-right" onclick="selectedText();" >Make It Option</button>
									</div>
							</div>
							<div class="divider-1 col-lg-12"></div> -->
						<div class="row">
							<div class="form-group col-md-2 col-lg-2 ">
								<label style="margin-top: 14px;">Question Text</label>
							</div>
							<div class="form-group col-md-8 col-lg-8">
								<textarea class="textarea" id="questionText" name="questionText"
									placeholder="Enter Question Text"
									style="width: 100%; height: 200px; font-size: 14px; line-height: 18px; border: 1px solid #dddddd; padding: 10px;">${question.questionText}</textarea>
							</div>
							<div class="form-group col-md-2 col-lg-2 ">
								<button type="button" class="btn btn-sucess pull-right"
									onclick="selectedText();">Make It Option</button>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-md-2 col-lg-2 ">
								<label style="margin-top: 14px;">Duration</label>
							</div>
							<div class="form-group col-md-4 col-lg-4">
								<input type="text" value="${question.duration}" id="duration"
									name="duration">
							</div>
							<div class="form-group col-md-6 col-lg-6">
								<input type="text" name="inputdropdown" id="inputdropdown"
									value="" /> <input type="button" name="append" id=""
									value="Append More Answers" onclick="append();" />
							</div>
						</div>
						<div class="row">
							<div id="insert">
								<table class="table table-hover table-bordered" id="tableList">
									<thead>
										<tr>
											<th><label for="">Answer</label></th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<c:forEach items="${question.answers}" var="answers"
												varStatus="st">

												<td>
													<div id="option">
														<input type="button" class="btn btn-info " name="button"
															id="button" value="${answers.answer}"
															placeholder="Enter Answer Text" /> <input type="hidden"
															id="answerId" name="answerId" value="${answers.id}" />
													</div> <input type="hidden" readonly="readonly"
													class="form-control" name="answer" id="answer"
													value="${answers.answer}" placeholder="Enter Answer Text" />

													<input class="form-control" type="hidden" name="ansdesc"
													value="${answers.description}"> <input
													type="hidden" name="seqNo" id="seqNo"
													value="${answers.seqNo}" />
												</td>


											</c:forEach>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>

			</div>
		</form>
	</div>

</section>
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script type="text/javascript">
	function append() {
		var appendVal = $('#inputdropdown').val();
		var dataRows = $('#tableList').find('td').length;
		var i = dataRows
		debugger;
		var newRow = $('#tableList').find('td:last').clone();
		newRow.find(":input").each(
				function(j) {
					if ($(this).prop('name') != undefined) {
						var oldName = $(this).attr('name');
						var replaceNum = '['
								+ oldName.charAt(oldName.indexOf('[') + 1)
								+ ']';
						var replaceWith = '[' + i + ']';
						var newName = oldName.replace(replaceNum, replaceWith);
						$(this).prop('name', newName);
						if (oldName == 'answer') {
							$(this).attr('value', appendVal);
							$('#insert').removeClass('hide');
						} else if (oldName == 'button') {
							$(this).attr('value', appendVal);
						} else if (oldName == 'seqNo') {
							$(this).attr('value', i);
						} else {
							console.log("Missed attribute type"
									+ $(this).prop('type'));
						}
					}
				});
		$("#tableList").find('tr:last').append(newRow);
	}

	$(function() {
		$("#duration").mask("99:99:99");
		if ($('#duration').val() == '') {
			$('#duration').val('00:00:00');
		}
		$(".textarea").wysihtml5();
		var length = $('#tableList').find('td').length;
		if (length == 1) {
			$('#insert').addClass('hide');
		}
		setTimeout(function() {
			$('.alert-success').slideUp('slow');
		}, 4000);
		/* $(".wysihtml5-toolbar").click(function(){
		 debugger;
		 var text1 = $('#questionText').val();
		
		 })*/
	});
	function selectedText() {
		debugger
		var text = document.getElementById("questionText");
		var stringValue = text.value;
		var str;

		var s = stringValue.split(" ");
		for (var l = 0; l < s.length; l++) {
			str = s[l];
			if (str.lastIndexOf("<b>") != -1) {
				var newStr = str.substring(str.lastIndexOf("<b>") + 3, str
						.lastIndexOf("</b>"));
				var t = newStr;
				debugger;
				var dataRows = $('#tableList').find('td').length - 1;
				var i = dataRows
				debugger;
				var newRow = $('#tableList').find('td:last').clone();
				newRow.find(":input")
						.each(
								function(j) {
									if ($(this).prop('name') != undefined) {
										var oldName = $(this).attr('name');
										var replaceNum = '['
												+ oldName.charAt(oldName
														.indexOf('[') + 1)
												+ ']';
										var replaceWith = '[' + i + ']';
										var newName = oldName.replace(
												replaceNum, replaceWith);
										$(this).prop('name', newName);
										if (oldName == 'answer') {
											$(this).attr('value', t);
											$('#insert').removeClass('hide');
										} else if (oldName == 'button') {
											$(this).attr('value', t);
										} else if (oldName == 'seqNo') {
											$(this).attr('value', i);
										} else {
											console.log("Missed attribute type"
													+ $(this).prop('type'));
										}
									}
								});
				$("#tableList").find('tr:last').append(newRow);
			}

		}
		$("#tableList").find('td:first').remove();
	}
</script>
