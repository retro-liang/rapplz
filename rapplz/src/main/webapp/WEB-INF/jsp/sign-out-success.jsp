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
					<h3>You have been signed out securely.</h3>			
					<p><a href="<spring:url value="/" htmlEscape="true" />">Go to homepage</a></p>
				</div>
				<div id="right-column">
					<%@ include file="/WEB-INF/jsp/right.jsp" %>
				</div>
			</div>

			<%@ include file="/WEB-INF/jsp/footer.jsp" %>

		</div>

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