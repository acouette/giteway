<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

<head>
	<title>Giteway</title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/style.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/jquery-ui-1.9.2.custom.min.css"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.flot.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.flot.pie.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/giteway.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-ui-1.9.2.custom.min.js"></script>
	
	
	<script type="text/javascript">
	
		//variable to keep track of which view is displayed
		var currentView = "#timeline";
		
		//intitialize view : timeline 
		$(document).ready(function(){
			

			$( "#radio" ).buttonset();
			
			
			$("#timeline-radio").click(function(){
				setView('#timeline');
			});
			$("#activity-radio").click(function(){
				setView('#activity');
			});
			$("#collaborators-radio").click(function(){
				setView('#collaborators');
			});
			
			$("#collaborators").hide();
			$("#activity").hide();
			$("#timeline").hide();
			setView("#timeline");
			
		});
		
		//method invoked when switching view or stat
		var setView = function(view){
			$("#load-gif").show();
			var previousView = this.currentView;
			this.currentView = view;
			$(previousView).slideUp("slow", function(){
				switch(currentView){
					case "#timeline":
						loadTimeline();
						break;
					case "#activity":
						loadCommittersActivity();
						break;
					case "#collaborators":
						showCurrentView();
				}
			});
		};
		
		//Show the panel associated to the current view
		var showCurrentView = function(){
			$("#load-gif").hide();
			$(currentView).slideDown("slow");
		};
		
		//Method invoked to load and display timeline data
		var loadTimeline = function(){
			$.ajax({
				url: "${pageContext.request.contextPath}/restful/repos/${repository.owner}/${repository.name}/timeline",
				dataType: 'json',
			}).done(function(jsonData) {
				showCurrentView();
				$('#timeline-commitCount').text(jsonData.commitCount);
				$('#timeline-timelineDays').text(parseFloat(jsonData.timelineDays).toFixed(1));
				$('#timeline-intervalDays').text(parseFloat(jsonData.intervalDays).toFixed(1));
				drawTimeLine(jsonData,'#timeline-chart');
			});
		};
		
		//Method invoked to load and display committers' activity
		var loadCommittersActivity = function(){
			$.ajax({
				url: "${pageContext.request.contextPath}/restful/repos/${repository.owner}/${repository.name}/activity",
				dataType: 'json',
			}).done(function(jsonData) {
				showCurrentView();
				$('#activity-commitCount').text(jsonData.commitCount);
				drawCommitterActivities(jsonData,'#activity-chart');
			});
		};
	
	</script>
</head>

<body>
  <div id="main">
		<%@include file="header.jsp" %>
		<div id="content_header"></div>
		<div id="site_content">
			<div id="content">
				<!-- Title and basic attributes -->
				<h2>${repository.name}</h2>
				<div id="repo-description">
					Owner : ${repository.owner}<br/>
					Description : ${repository.description}<br/>
				</div>
				<div id="stat-chooser" >
					<form>
					    <div id="radio" class="left">
					        <input type="radio" id="timeline-radio" name="radio" checked="checked"/><label for="timeline-radio">Timeline</label>
					        <input type="radio" id="activity-radio" name="radio" /><label for="activity-radio">Committers' activity</label>
					        <input type="radio" id="collaborators-radio" name="radio" /><label for="collaborators-radio">Show collaborators</label>
					    </div>
					</form>
					<div style="height: 25px;" class="right"><img id="load-gif" alt="loading..." src="${pageContext.request.contextPath}/resources/style/loading-icon.gif"/></div>
					<div class="spacer"></div>
				</div>
				
				<!-- Timeline -->
				<div id="timeline" class="stat-container">
					<h3>Timeline</h3>
					<p class="chart-comment">
					Number of commits displayed : <span id="timeline-commitCount"></span><br/>
					Total timeline duration : <span id="timeline-timelineDays"></span> day(s)<br/>
					Interval duration : <span id="timeline-intervalDays"></span> day(s)</p>
					<div id="timeline-chart" ></div>
				</div>
				
				<!-- Committers activity -->
				<div id="activity" class="stat-container">
					<h3>Committers' activity</h3>
					<p class="chart-comment">Based on the last <span id="activity-commitCount"></span> commits.</p>
					<div id="activity-chart"></div>
				</div>
				
				<!-- Collaborators -->
				<div id="collaborators">
					<!-- The collaborators panel -->
					<c:choose>
						<c:when test="${not empty collaborators}">
						<h3>Collaborators</h3>
					    	<c:forEach  var="user" items="${collaborators}" varStatus="status">
						    	<c:if test="${(status.count - 1) % 3==0}">
						    		<div class="spacer"></div>
						    	</c:if>
				    			<div class="float">
				    				<img src="${user.avatarUrl}" alt="img not available" height="100" width="100"/>
				    				<p>${user.login}</p>
				    			</div>
					    	</c:forEach>
				    		<div class="spacer"></div>
						</c:when>
						<c:otherwise>
							<h3>No Collaborators</h3>
						</c:otherwise>
					</c:choose>
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