<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<section class="content-header">
	<h1>
		Create <small>Question</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Accounts</a></li>
		<li class="active">Listening Question</li>
	</ol>
	
<div class="box box-primary">

                
			<div class="box-header with-border">
				<div id="danger" class="alert alert-danger alert-dismissible hide"></div>
			<c:if test="${msg != null}">
				<div id="success" class="alert alert-success alert-dismissible ">${msg}</div>
			</c:if>
			<h3 class="box-title">Add/Edit Multiple Choice Questions</h3>
			<div class="row">
				<div class="form-group  col-md-4 col-lg-4 ">
                  <div class="radio">
                    <label>
                      <input type="radio" name="styleId" id="styleId1" value="10000006" checked="checked" onclick="window.location='/targetec-web/admin/listening/question/form/edit?styleId=10000006&questionId=';">
                      Summarize Spoken Text
                    </label>
                  </div>
                  </div>
                  <div class="form-group  col-md-4 col-lg-4 ">
                  <div class="radio">
                    <label>
                      <input type="radio" name="styleId" id="styleId2" value="10000009" onclick="window.location='/targetec-web/admin/listening/question/form/edit?styleId=10000009&questionId=';">
                      Mulitiple Choice
                    </label>
                  </div>
                  </div>
                  <div class="form-group  col-md-4 col-lg-4 ">
                  <div class="radio">
                    <label>
                      <input type="radio" name="styleId" id="styleId3" value="10000007" onclick="window.location='/targetec-web/admin/listening/question/form/edit?styleId=10000007&questionId=';">
                      Write Missing Word
                    </label>
                  </div>
                </div>
                </div>
                <div class="row">
                  <div class="form-group  col-md-4 col-lg-4 ">
                  <div class="radio">
                    <label>
                      <input type="radio" name="styleId" id="styleId4" value="10000008" onclick="window.location='/targetec-web/admin/listening/question/form/edit?styleId=10000008&questionId=';">
                     Highlight Incorrect Words
                    </label>
                  </div>
                </div>
                </div>
			</div>
			<!-- /.box-header -->
			<!-- form start -->
			<form role="form" id="trainer" method="post"
				action="${pageContext.request.contextPath}/admin/form/test/update?typeId=10000003&styleId=10000006">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			    <input type="hidden" name="questionId" value="${question.id}" />
			  <input type="hidden" name="status" value="${question.status}" />
			  <input type="hidden" id="resourceId" name="resourceId" value="${question.resourceMaster.id}" />
				<div class="box-body">
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
								<a href="" class="btn btn-default" >Close</a>
								<button  type="submit" class="btn btn-primary">Save	changes</button>
						
							</div>
						</div>
				</div>
	
				<div class="divider-1 col-lg-12"></div>
					<div class="row">
							<div class="form-group col-md-2 col-lg-2 " >
								<label  style="margin-top: 14px;">Description</label>	
							</div>
							<div class="form-group col-md-10 col-lg-10" >
								<textarea class="form-control" name="description"  placeholder="Enter Description"  >${question.description}</textarea>
							</div>
						</div>
					<div class="row">
						<div class="form-group col-md-2 col-lg-2 " >
							<label  style="margin-top: 14px;">Directions</label>	
						</div>
						<div class="form-group col-md-10 col-lg-10" >
							<textarea class="form-control" name="direction"  placeholder="Enter Direction"  >${question.direction}</textarea>
						</div>
					</div>
					<div class="divider-1 col-lg-12"></div>
					<div class="row">
						<div class="form-group col-md-2 col-lg-2 " >
							<label  style="margin-top: 14px;">Question Text</label>	
						</div>
						<div class="form-group col-md-10 col-lg-10" >
							<textarea class="form-control"  placeholder="Enter Question Text" name="questionText">${question.questionText}</textarea>
						</div>
					</div> 
					<div class="divider-1 col-lg-12"></div>
					 <div class="row">
						<div class="form-group col-md-2 col-lg-2 " >
							<label  style="margin-top: 14px;">Upload Media</label>	
						</div>
						<div class="form-group col-md-2 col-lg-2" >
							<input type="file" onchange="uploadmedia();" id="wizard-picture" class="">
						</div>
						<div id="loadingimage" class="form-group col-md-10 col-lg-10 hide">
						<img src="${pageContext.request.contextPath}/resources/img/loadings.gif"   id="loading"> 
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-2 col-lg-2 " >
							<label  style="margin-top: 14px;">Uploaded File</label>	
						</div>
						<div class="form-group col-md-10 col-lg-10" >
						<audio controls>
						  <source src="${pageContext.request.contextPath}/resources/media/${question.resourceMaster.resourcePath}" type="audio/mpeg">
						</audio>
						</div>
					</div>
					<div class="row">
					<div class="form-group col-md-2 col-lg-2 " >
							<label  style="margin-top: 14px;">Duration</label>	
						</div>
						<div class="form-group col-md-4 col-lg-4" >
							<input type="text" value="${question.duration}" id="duration"
							name="duration">
						</div>
					</div>
					<div class="row">
						  <div id="insert">
					  <table class="table table-hover table-bordered" id="tableList">
						  <thead>
							  <tr>
								  <th>
								    <label for="" >Answer</label> 
								  </th>
							  </tr>
						  </thead>
						  <tbody>
						  <c:forEach items="${question.answers}" var="answers" varStatus="st">
						  <tr >
							  <td >
						       		<input type="text" class="form-control" name="answer" id="answer" value="${answers.answer}"   placeholder="Enter Answer Text"  />
						       		<input type="hidden" id="answerId" name="answerId" value="${answers.id}" />
						       		<input class="form-control" type="hidden"
											name="ansdesc" value="${answers.description}">
						       		<input type="hidden"  name="seqNo" id="seqNo" value="${st.count-1}" />
							  </td>
						  </tr>
						  </c:forEach>
						  </tbody>
					  </table>
	 				 </div>
					</div>
				</div>
			</form>
		</div>
	
</section>

<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script type="text/javascript">
	$(function() {
		$("#duration").mask("99:99:99");
		if ($('#duration').val() == '') {
			$('#duration').val('00:00:00');
		}
		setTimeout(function() {
			$('.alert-success').slideUp('slow');
		}, 4000);
	});
	function uploadmedia(){
		var formData = new FormData();
	    formData.append('file', $('input[type=file]')[0].files[0]);
	    $('#loadingimage').removeClass('hide');
	    $.ajax({
	        url : '/targetec-web/ajax/uploadmedia?${_csrf.parameterName}=${_csrf.token}',
	        data : formData,
	        processData : false,
	        contentType : false,
	        type : 'POST',
	        success : function(data) {
	        	 $('#loadingimage').addClass('hide');
	            $("#resourceId").val(data);
	        },
	        error : function(err) {
	            alert(err);
	        }
	    });
	}
</script>
