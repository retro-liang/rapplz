<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	
	<%@ include file="/WEB-INF/jsp/head.jsp" %>

	<body>
		
		<div id="container">

			<%@ include file="/WEB-INF/jsp/header.jsp" %>

			<div class="main-container">
				<h1></h1>
				<div id="info-box">
					<p></p>
				</div>
				<div id="left-column">
					
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
				<div id="right-column">
					<%@ include file="/WEB-INF/jsp/right.jsp" %>
				</div>
			</div>

			<%@ include file="/WEB-INF/jsp/footer.jsp" %>

		</div>
		
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
		<script type="text/javascript" src="/js/rapplz.js"></script>
		
		<script type="text/javascript">
			var _gaq = _gaq || [];
			_gaq.push(['_setAccount', 'UA-30210796-1']);
			_gaq.push(['_setDomainName', 'rapplz.com']);
			_gaq.push(['_trackPageview']);
			
			(function() {
			  var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
			  ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
			  var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
			})();		
		</script>
	
	</body>
	
</html>