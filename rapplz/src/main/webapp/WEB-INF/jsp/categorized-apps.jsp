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
					<h2>${categoryName}</h2>			
					<c:forEach var="app" items="${categorizedApps}">
						<c:set var="appName" value="${app.name}" />
						<c:set var="appUrl" value="${fn:replace(appName, ' ', '-')}" />
						<div class="app-box"> 
							<div class="app-box-header"> 
								<div class="app-name">${app.name}</div> 
								<div class="app-category">${app.categoryNames}</div> 
							</div> 
							<div class="app-box-middle"> 
								<div class="app-icon"> 
									<img src="${app.icon}" /> 
								</div> 
								<div class="app-info"> 
									<div class="have-info">${app.haveCount} users have it</div> 
									<div class="recommendation-info">${app.recommendationCount} users recommend it</div> 
									<div class="detail-link"><a href="/app/${app.name}.replace(/ /g,-)  ">App Detail</a></div> 
								</div> 
							</div> 
							<div class="app-box-footer"> 
								<ul> 
									<li><a href="javascript:void(0);" onclick="have('${app.rawId}','${app.name }','${item.icon}','','')" class="link-button">Have</a></li>
									<li><a href="javascript:void(0);" class="link-button">Comment</a></li>
									<li><a href="javascript:void(0);" onclick="recommend('${app.rawId},'${item.name},'${item.icon}','','')" class="link-button">Recommend</a></li> 
								</ul> 
							</div> 
						</div>
			       </c:forEach>
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