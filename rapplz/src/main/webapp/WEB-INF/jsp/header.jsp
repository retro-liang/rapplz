		<div class="header">

			<c:set var="authenticated" value="${false}" />
			<sec:authorize access="isAuthenticated()">
			    <c:set var="authenticated" value="${true}" />
			</sec:authorize>

			<div class="logo">
				<a href="<spring:url value="/" htmlEscape="true" />"><img src="/img/logo.png" alt="Logo" /></a>
			</div>

			<div class="recommend-box">
				<input type="text" id="app-search" name="app-search" />
				<a href="<spring:url value="/recommend" htmlEscape="true" />">Recommend an app</a>
			</div>

			<div class="access-box">
				<c:choose>
					<c:when test="${!authenticated}">
						<ul>
							<li><a href="<spring:url value="/access/sign_in.html" htmlEscape="true" />">Sign In</a></li>
							<li><a href="<spring:url value="/access/sign_up.html" htmlEscape="true" />">Sign Up</a></li>
						</ul>
					</c:when>
					<c:otherwise>
						<ul>
							<li>Welcome <c:if test="${authenticated}">${user.email}</c:if></li>
							<li><a href="<c:url value="/j_spring_security_logout" />" >Sign Out</a></li>
						</ul>
					</c:otherwise>
				</c:choose>
			</div>

		</div>