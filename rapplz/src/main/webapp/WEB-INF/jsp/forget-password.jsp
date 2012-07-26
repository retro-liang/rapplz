<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

	<%@ include file="/WEB-INF/jsp/includes.jsp" %>
	<%@ include file="/WEB-INF/jsp/header.jsp" %>

	<body onload="document.f.j_username.focus();">

		<div id="main">
			
			<h3>Enter your email</h3>
			
			<form action="/access/forget-password" method="POST">
				<table>
					<tr>
						<td>Email:</td>
						<td><input type="text" name="email" value="" /></td>
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