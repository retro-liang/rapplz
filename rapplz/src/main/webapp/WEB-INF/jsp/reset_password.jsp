<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

	<%@ include file="/WEB-INF/jsp/includes.jsp" %>
	<%@ include file="/WEB-INF/jsp/header.jsp" %>

	<body onload="document.f.j_username.focus();">

		<div id="main">
			
			<h3>Login with your email and password</h3>
			
			<form action="/access/reset_password" method="POST">
				<table>
					<tr>
						<td>New password:</td>
						<td><input type="text" name="password" value="" /></td>
					</tr>
					<tr>
						<td>Confirm password:</td>
						<td><input type="text" name="confirm_password" value="" /></td>
					</tr>
					<tr>
						<td colspan="2"><input name="submit" type="submit" value="Submit" /></td>
					</tr>
				</table>
			</form>

		</div>

		<%@ include file="/WEB-INF/jsp/footer.jsp" %>
	
	</body>
	
</html>