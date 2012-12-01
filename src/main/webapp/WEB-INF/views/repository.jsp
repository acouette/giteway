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

		drawTimeLine('${timelineChunks}','#timeline-chart');
		drawCommitterActivities('${committerActivities}',"#committer-activities-chart");

	});
	

	var toggleCollaborators = function(){
		if($("#collaborators").is(":visible")){
			$("#toggleCollaborators").text('Show all collaborators');
		}else{
			$("#toggleCollaborators").text('Hide all collaborators');
		}
		$("#collaborators").fadeToggle();
	};
	
	</script>
</head>

<body>
  <div id="main">
		<%@include file="header.jsp" %>
		<div id="content_header"></div>
		<div id="site_content">
			<div id="content">
			
				<h2>${repository.name}</h2>
				<div id="repo-description">
					Owner : ${repository.owner}<br/>
					Description : ${repository.description}<br/>
				</div>
				
				<h3>Commits history</h3>
				<div id="timeline-chart" ></div>
				<p class="chart-comment">This timeline shows the number of commits per period of ${chunkDuration} days.</p>
				
				<h3>Committers stats</h3>
				<div id="committer-activities-chart"></div>
				<p class="chart-comment">This pie chart represents the committer's collaboration over the last ${commitCount} commits.</p>
				<c:if test="${not empty collaborators}">
				
					<br/>
					<a href="javascript:toggleCollaborators()" id="toggleCollaborators">Show all collaborators</a>
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
				</c:if>
			</div>
		</div>
		
		<div id="content_footer"></div>
		<div id="footer">
			<a href="http://www.html5webtemplates.co.uk">design from HTML5webtemplates.co.uk</a>
		</div>
	</div>
  
  </body>
</html>