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
			<h3 class="box-title">Add/Edit Multichoice Questions</h3>
			<div class="row">
				<div class="form-group  col-md-4 col-lg-4 ">
                  <div class="radio">
                    <label>
                      <input type="radio" name="styleId" id="styleId1" value="10000001"  onclick="window.location='/targetec-web/admin/reading/question/form/edit?styleId=10000001&questionId=';">
                     Multiple Choice
                    </label>
                  </div>
                  </div>
                  <div class="form-group  col-md-4 col-lg-4 ">
                  <div class="radio">
                    <label>
                      <input type="radio" name="styleId" id="styleId2" value="10000002" onclick="window.location='/targetec-web/admin/reading/question/form/edit?styleId=10000002&questionId=';">
                      Drag and Drop values in paragraph
                    </label>
                  </div>
                  </div>
                  <div class="form-group  col-md-4 col-lg-4 ">
                  <div class="radio">
                    <label>
                      <input type="radio" name="styleId" id="styleId3" value="10000003" onclick="window.location='/targetec-web/admin/reading/question/form/edit?styleId=10000003&questionId=';">
                      Fill in the blanks
                    </label>
                  </div>
                </div>
                </div>
                <div class="row">
                  <div class="form-group  col-md-4 col-lg-4 ">
                  <div class="radio">
                    <label>
                      <input type="radio" name="styleId" id="styleId4" value="10000004" onclick="window.location='/targetec-web/admin/reading/question/form/edit?styleId=10000004&questionId=';">
                      DropDown values in paragraph
                    </label>
                  </div>
                </div>
                 <div class="form-group  col-md-4 col-lg-4 ">
                  <div class="radio">
                    <label>
                      <input type="radio" name="styleId" id="styleId5" value="10000005" checked="checked" onclick="window.location='/targetec-web/admin/reading/question/form/edit?styleId=10000005&questionId=';">
                      Random order to sorting order
                    </label>
                  </div>
                </div>
                </div>
			</div>
			<!-- /.box-header -->
			<!-- form start -->
			<form role="form" id="trainer" method="post"
				action="${pageContext.request.contextPath}/admin/form/test/update">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			  <input type="hidden" value="${question.id}" />
				<div class="box-body">
					<div class="row">
						<div class="form-group col-md-4 col-lg-12">
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
					<div class="row">
					<div class="form-group col-md-2 col-lg-2 " >
							<label  style="margin-top: 14px;">Duration</label>	
						</div>
						<div class="form-group col-md-4 col-lg-4" >
							<input type="time" value="${question.duration}" step="600" name="duration">
						</div>
					 <div class="form-group col-md-4 col-lg-12" >
             			<button type="button" class="btn btn-danger pull-right" onclick="addnew(this);" >Add More</button>
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
								  <th>
								  	<label for="" >Corrected</label>
								  </th>
							  </tr>
						  </thead>
						  <tbody>
						  <c:forEach items="${question.answers}" var="answers" varStatus="st">
						  <tr >
							  <td >
						       		<input type="text" class="form-control" name="answer" id="answer" value="${answers.answer}"   placeholder="Enter Answer Text"  />
						       		<input type="hidden"  name="seqNo" id="seqNo" value="${st.count-1}" />
							  </td>
							  <td>
								  <input type="checkbox"  value="${st.count-1}" id="correctYN-${st.count}" name="correctYN" 
								   <c:if test="${answers.correctYN == 'Y'}">checked="checked"</c:if>
								  />
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

<script type="text/javascript">

	function addnew(obj) {
		debugger
		// subtracting header and footer rows
		var dataRows = $('#tableList').find('tr').length - 1;
		var i = dataRows
		var newRow = $('#tableList').find('tr:last').clone();
		// finding all the form elemnts like select, inputs checkbox radio
		// and change name params as per list index
		newRow.find(":input").each(
				function(j) {
					debugger;
					if ($(this).prop('name') != undefined) {
						var oldName = $(this).attr('name');
						var replaceNum = '['
								+ oldName.charAt(oldName.indexOf('[') + 1)
								+ ']';
						var replaceWith = '[' + i + ']';
						debugger;
						var newName = oldName.replace(replaceNum, replaceWith);
						$(this).prop('name', newName);
						//newRow.find("td:last").html('');
						if ($(this).prop('type') == "number") {
							$(this).attr('value', '');
							// removing class indexed claas and adding new
							// class
						} else if ($(this).prop('type') == "text"){
							$(this).attr('value', '');
						}else if ($(this).prop('type') == "hidden") {
							//if ($(this).hasClass('clear-data')) {
								$(this).attr('value',i);
							//}
						} else if ($(this).prop('type') == "checkbox") {
							$(this).attr('value', i);
						} else {
							console.log("Missed attribute type"
									+ $(this).prop('type'));
						}
					}
				});
		
		$("#tableList").append(newRow);			
	}
</script>
