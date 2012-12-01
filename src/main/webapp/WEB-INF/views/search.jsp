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
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.watermark.min.js"></script>
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
				$("#keyword").val($("#keyword").val().replace('/', ''));
				if($("#keyword").val().length==0){
					return false;
				}
			});
			
			$('.hidable').hide();
			$('#keyword').watermark('Search a git repository');
			
		});
		
		var showExtra = function(){
			$('.hidable').show();
			$('#extralink').hide();
		};
		
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
							<c:forEach var="repository" items="${repositories}" varStatus="status">
								<tr
								<c:if test="${ status.index > 9}">
											class="hidable"</c:if>>
									<td><a href="${pageContext.request.contextPath}/repository/${repository.owner}/${repository.name}">${repository.name}</a></td>
									<td>${repository.owner}</td>
									<td>${repository.description}</td>
								</tr>
							</c:forEach>
						</table>
						<c:if test="${ fn:length(repositories) >10}">
							<div>
								<a href="javascript:showExtra()" id="extralink">see more results...</a>
							</div>
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