<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

	<%@ include file="/WEB-INF/jsp/includes.jsp" %>
	<%@ include file="/WEB-INF/jsp/header.jsp" %>

	<body onload="document.f.j_username.focus();">
		
		<c:set var="authenticated" value="${false}" />
		<sec:authorize access="isAuthenticated()">
		    <c:set var="authenticated" value="${true}" />
		</sec:authorize>		

		<div id="main">
			
			<h3>Welcome <c:if test="${authenticated}">${user.email}</c:if></h3>
			
			<p><a href="<spring:url value="/" htmlEscape="true" />">Home</a></p>
			
			<c:choose>
				<c:when test="${!authenticated}">
					<p><a href="<spring:url value="/access/sign_in.html" htmlEscape="true" />">Sign In</a></p>
					<p><a href="<spring:url value="/access/sign_up.html" htmlEscape="true" />">Sign Up</a></p>
				</c:when>
				<c:otherwise>
					<a href="<c:url value="/j_spring_security_logout" />" >Sign Out</a>
				</c:otherwise>
			</c:choose>

		</div>

		<%@ include file="/WEB-INF/jsp/footer.jsp" %>
	
	</body>
	
</html>