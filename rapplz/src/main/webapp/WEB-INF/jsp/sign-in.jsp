<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

	<%@ include file="/WEB-INF/jsp/includes.jsp" %>
	<%@ include file="/WEB-INF/jsp/header.jsp" %>

	<body onload="document.f.j_username.focus();">

		<div id="main">
			
			<h3>Login with your email and password</h3>
			
			<form name="f" action="/j_spring_security_check" method="POST">
				<table>
					<tr>
						<td>Email:</td>
						<td><input type="text" name="j_username" value=""></td>
					</tr>
					<tr>
						<td>Password:</td>
						<td><input type="password" name="j_password" /></td>
					</tr>
					<tr>
						<td><input type="checkbox" name="_spring_security_remember_me" /></td>
						<td>Remember me on this computer.</td>
					</tr>
					<tr>
						<td colspan="2"><input name="submit" type="submit" value="Login" /></td>
					</tr>
				</table>
			</form>
			
			<p><a href="<spring:url value="/access/forget-password.html" htmlEscape="true" />">Forget password?</a></p>

		</div>

		<%@ include file="/WEB-INF/jsp/footer.jsp" %>
	
	</body>
	
</html>