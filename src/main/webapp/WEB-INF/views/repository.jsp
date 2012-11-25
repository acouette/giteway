<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>

<head>
	<title>Giteway</title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/style.css" />  
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.flot.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.flot.pie.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/giteway.js"></script>
	
	<script type="text/javascript">
	
	$(document).ready(function(){
		$("#collaborators").hide();

		drawTimeLine('${timelineData}','#timeline-chart');
		drawCommitterActivities('${committerActivities}',"#committer-activities-chart");

	});
	
	function toggleCollaborators(){
		$("#collaborators").fadeToggle();
	}
	
	</script>
</head>

<body>
  <div id="main">
		<%@include file="header.jsp" %>
		<div id="content_header"></div>
		<div id="site_content">
			<div id="content">
				<h1>Repository : ${repository.name}</h1>
				Owner : ${repository.owner.login}<br/>
				Description : ${repository.description}<br/>
				
				
				
				<h3>- Commits history</h3>
				<div id="timeline" class="timeline">
					<div id="timeline-chart" style="width:600px;height:300px"></div>
				</div>
				
				<div id="committer-activities">
					<div id="committer-activities-chart" style="width:600px;height:300px"></div>
				
				</div>
				
				<br/>
				<a href="javascript:toggleCollaborators()">Show all committers</a>
				<br/>
				<br/>
				
			    <div id="collaborators" class="container">
			    	<c:forEach  var="user" items="${collaborators}" varStatus="status">
				    	<c:if test="${(status.count - 1) % 3==0}">
				    		<div class="spacer">
							  &nbsp;
							</div>
				    	</c:if>
		    			<div class="float">
		    				<img src="${user.avatarUrl}" alt="img not available" height="100" width="100"/>
		    				<p>${user.login}</p>
		    			</div>
			    	</c:forEach>
		    		<div class="spacer">
					  &nbsp;
					</div>
			    </div>
			</div>
		</div>
		
		<div id="content_footer"></div>
		<div id="footer">
			<a href="http://www.html5webtemplates.co.uk">design from HTML5webtemplates.co.uk</a>
		</div>
	</div>
  
  </body>
</html>