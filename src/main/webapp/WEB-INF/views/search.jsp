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
		<script type="text/javascript">
		
		$(document).ready(function(){
			
			//Checks for empty keyword every 500ms to clear the result grid
			var checkKey = function(){
				if($("#keyword").val().length==0){
					$('.searchResult').html('');
					
				}
			};
			setInterval(checkKey,500);
			
			//Stop a form submit if the keyword is empty
			$("form").submit(function() {
				if($("#keyword").val().length==0){
					return false;
				}
			});
			
			$('#extraresults').hide();
			
		});
		
		function toggleExtra(){
			$("#extraresults").fadeToggle();
		}
		
		</script>
	</head>

	<body>
  
		<div id="main">
			<%@include file="header.jsp" %>
			<div id="content_header"></div>
			<div id="site_content">
			<div id="content">
				<div id="searchBar" class="center searchbar">
					<form action="${pageContext.request.contextPath}/search" method="post">
						<input id="keyword" name="keyword" type="text" value="${keyword}"/>
						<input id="submitKeyword" type="submit" value="Search"/>
					</form>
				</div>
				<c:if test="${noResult}">
					<div class="searchResult">
						Your keyword did not match any repository
					</div>
				</c:if>
				<c:if test="${not empty repositories}">
					<div class="searchResult">
						<table class="searchTable">
							<tr>
								<th style="width: 25%">Name</th>
								<th style="width: 25%">Username</th>
								<th style="width: 50%">Description</th>
							</tr>
							<c:forEach var="repository" items="${repositories}" end="9">
								<tr id="${repository.name}${repository.username}">
									<td><a href="${pageContext.request.contextPath}/repository/${repository.username}/${repository.name}">${repository.name}</a></td>
									<td>${repository.username}</td>
									<td>${repository.description}</td>
								</tr>
							</c:forEach>
						</table>
						<c:if test="${ fn:length(repositories) >10}">
							<div>
								<a href="javascript:toggleExtra()" id="extralink">see more results...</a>
							</div>
							<table id="extraresults" class="searchTable">
								<c:forEach var="repository" items="${repositories}" begin="10">
									<tr id="${repository.name}${repository.username}">
										<td style="width: 25%"><a href="${pageContext.request.contextPath}/repository/${repository.username}/${repository.name}">${repository.name}</a></td>
										<td style="width: 25%">${repository.username}</td>
										<td style="width: 50%">${repository.description}</td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
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