		<div id="header-container">

			<c:set var="authenticated" value="${false}" />
			<sec:authorize access="isAuthenticated()">
			    <c:set var="authenticated" value="${true}" />
			</sec:authorize>

			<div id="logo-container">
				<a id="logo-link" href="<spring:url value="/" htmlEscape="true" />">
					<img id="logo-image" src="/img/logo.png" alt="Logo" />
				</a>
			</div>

			<div id="app-search-container">
				<input id="app-search-box" type="text" name="app-search" />
				<a id="app-recommend" href="<spring:url value="/recommend" htmlEscape="true" />">Recommend an app</a>
			</div>

			<div id="access-container">
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