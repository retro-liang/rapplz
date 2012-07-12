<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

	<%@ include file="/WEB-INF/jsp/includes.jsp" %>
	<%@ include file="/WEB-INF/jsp/header.jsp" %>

	<body onload="document.f.j_username.focus();">

		<div id="main">
			
			<h3>Sign Up</h3>
			
			<form:form action="/access/sign_up" method="post" commandName="user">
				<table>
					<tr>
						<td>First Name:</td>
						<td><form:input path="firstName" /></td>
					</tr>
					<tr>
						<td>Last Name:</td>
						<td><form:input path="lastName" /></td>
					</tr>
					<tr>
						<td>Email Address:</td>
						<td><form:input path="email" /></td>
					</tr>
					<tr>
						<td>Password:</td>
						<td><form:password path="password" /></td>
					</tr>
					<tr>
						<td colspan="2"><input type="submit" value="Register" /></td>
					</tr>
				</table>
			</form:form>

		</div>

		<%@ include file="/WEB-INF/jsp/footer.jsp" %>
	
	</body>
	
</html>