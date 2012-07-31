<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	
	<%@ include file="/WEB-INF/jsp/head.jsp" %>

	<body>
		
		<div id="container">

			<%@ include file="/WEB-INF/jsp/header.jsp" %>

			<div class=main-container>
				<div class="float-left">
					<h1>Contact Us</h1>
					<form>
						<span>Subject: </span><input id="" type="text" />
						<span>Message: </span><input id="" type="text" />
						<span>Name: </span><input id="" type="text" />
						<span>Email address: </span><input id="" type="text" />
						<a id="contact-submit" href="javascript:void(0);">Send</a>
					</form>
				</div>
				<%@ include file="/WEB-INF/jsp/right.jsp" %>
			</div>		

			<%@ include file="/WEB-INF/jsp/footer.jsp" %>

		</div>
		
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
		<script type="text/javascript" src="/js/rapplz.js"></script>
	
	</body>
</html>