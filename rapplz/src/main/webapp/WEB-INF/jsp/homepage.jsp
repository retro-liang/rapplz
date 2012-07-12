<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

	<%@ include file="/WEB-INF/jsp/includes.jsp" %>
	<%@ include file="/WEB-INF/jsp/header.jsp" %>

	<body onload="document.f.j_username.focus();">

		<div id="main">
			
			<h3>Welcome ${email}</h3>
			
			<p><a href="<spring:url value="/" htmlEscape="true" />">Home</a></p>
			
			<c:choose>
				<c:when test="${email} == ''">
					<p><a href="<spring:url value="/access/sign_in.html" htmlEscape="true" />">Sign In</a></p>
					<p><a href="<spring:url value="/access/sign_up.html" htmlEscape="true" />">Sign Up</a></p>
				</c:when>
				<c:otherwise>
					<a href="<c:url value="j_spring_security_logout" />" >Sign Out</a>
				</c:otherwise>
			</c:choose>

		</div>

		<%@ include file="/WEB-INF/jsp/footer.jsp" %>
	
	</body>
	
</html>