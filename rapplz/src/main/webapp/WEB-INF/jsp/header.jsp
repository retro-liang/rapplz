		<div id="header-container">

			<c:set var="authenticated" value="${false}" />
			<sec:authorize access="isAuthenticated()">
			    <c:set var="authenticated" value="${true}" />
			</sec:authorize>

			<div id="logo-container" class="float_left">
				<a id="logo-link" href="<spring:url value="/" htmlEscape="true" />">
					<img id="logo-image" src="/img/logo.png" alt="Logo" />
				</a>
			</div>

			<div id="app-search-container" class="float_left">
				<input id="app-search-box" name="app-search-box" type="text" />
				<a id="app-search-button" href="#search-result-box" class="inline">Recommend</a>
			</div>

			<div id="access-container" class="float_left">
				<c:choose>
					<c:when test="${!authenticated}">
						<ul id="access-container-not-ssigned-in">
							<li id="sign-in-container"><a id="sign-in-link" href="<spring:url value="/access/sign_in.html" htmlEscape="true" />">Sign In</a></li>
							<li id="sign-up-container"><a id="sign-in-link" href="<spring:url value="/access/sign_up.html" htmlEscape="true" />">Sign Up</a></li>
						</ul>
					</c:when>
					<c:otherwise>
						<ul id="access-container-igned-in">
							<li id="welcome-container">Welcome <a id="user-link">"${user.email}</a></li>
							<li id="sign-out-container"><a id="sign-out-link" href="<c:url value="/j_spring_security_logout" />" >Sign Out</a></li>
						</ul>
					</c:otherwise>
				</c:choose>
			</div>

		</div>
		
		<div style="display:none;">
			<div id="search-result-box">
			<!-- Future navigation panel -->
			<div class="holder"></div>
			
			<!-- Item container (doesn't need to be an UL) -->
			<table>
	            <thead>
	            	<tr>
	            		<th>Rank</th>
	            		<th>Rating</th>
	            		<th>Title</th>
	            		<th>Votes</th>
	            	</tr>
	            </thead>
	            <tbody id="search-result">
	            	<tr>
	            		<td>1.</td>
	            		<td>9.2</td>
	            		<td>The Shawshank Redemption (1994)</td>
	            		<td>699,295</td>
	            	</tr>
				</tbody>
			</table>
			</div>
		</div>
            