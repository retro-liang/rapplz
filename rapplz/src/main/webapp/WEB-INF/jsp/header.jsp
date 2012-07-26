		<div id="header-container">

			<c:set var="authenticated" value="${false}" />
			<sec:authorize access="isAuthenticated()">
			    <c:set var="authenticated" value="${true}" />
			</sec:authorize>

			<div id="logo-container" class="float_left">
				<a id="logo-link" href="<spring:url value="/" htmlEscape="true" />">
					<img id="logo-image" src="/img/logo.png" alt="Logo" title="Logo" />
				</a>
			</div>

			<div id="app-search-container" class="float_left">
				<input id="app-search-box" name="app-search-box" type="text" />
				<a id="app-search-button" href="<spring:url value="/recommend.html" htmlEscape="true" />">Recommend</a>
			</div>

			<div id="access-container" class="float_left">
				<c:choose>
					<c:when test="${!authenticated}">
						<ul id="access-container-not-ssigned-in">
							<li id="sign-in-container"><a id="sign-in-button" href="<spring:url value="/access/sign-in.html" htmlEscape="true" />">Sign In</a></li>
							<li id="sign-up-container"><a id="sign-up-button" href="<spring:url value="/access/sign-up.html" htmlEscape="true" />">Sign Up</a></li>
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
		
		<div style="visibility:hidden;width:0px;height:0px;">
			<div id="search-result-box">
			<table>
	            <thead>
	            	<tr>
	            		<th></th>
	            		<th>Name</th>
	            		<th>Company</th>
	            		<th>Rating</th>
	            		<th>Votes</th>
	            	</tr>
	            </thead>
	            <tbody id="search-result">
	            	
				</tbody>
			</table>
			
			<div class="holder" style="margin:0px;"></div>
			</div>
		</div>
		
		<div style="visibility:hidden;width:0px;height:0px;">
			<div id="sign-in-box">
				<div class="sign-in-box-header">
					<h2>Sign In</h2>
				</div>
				<div class="sign-in-box-body">
					<div id="facebook_login">
						<p>Sign In with Your Facebook Account</p>
						<a href="/services/facebook/authorize" target="fb_auth" onclick="window.open('/services/facebook/authorize','fb_auth','width=580,height=400'); $.mq.send('user:facebook_connect');" class="btn_facebook fire_event" message="facebook_connect" id="facebook_account_auth">Connect with Facebook</a>
					</div>
					<form id="sign-in-form" action="/j_spring_security_check" method="POST">
						<p>Sign In with Your Account</p>
						<div class="form_field email">
							<label for="email">Email Address:</label>
							<input type="email" id="j_username" name="j_username" tabindex="0" value="" />
						</div>
						<div class="form_field password">
							<label for="password">Password:</label>
							<input type="password" id="j_password" name="j_password" tabindex="0" />
							<p><a id="forget-password-button" href="<spring:url value="/access/forget-password.html" htmlEscape="true" />">Forget password?</a></p>
						</div>
						<div class="form_field rememberMe">
							<label for="rememberMe">Remember Me</label>
							<input type="checkbox" id="remember-me" name="_spring_security_remember_me" tabindex="0" />
						</div>
						<a id="sign-in-submit" href="javascript:void(0);">Sign In</a>
					</form>
					<div class="error_message" id="login_error_message"></div>
				</div>
				<div class="modal_footer">
					<p>Don't have an account? <a id="sign-in-sign-up-button" href="/access/sign-up.html" class="user_event">Sign up now!</a></p>
				</div>
			</div>
		</div>
		
		<div style="visibility:hidden;width:0px;height:0px;">
			<div id="forget-password-box">
				<h3>Enter your email address</h3>
				<form id="forget-password-form" action="/access/forget-password" method="POST">
					<table>
						<tr>
							<td>Email:</td>
							<td><input id="forget-password-email" name="forget-password-email" type="text" /></td>
						</tr>
						<tr>
							<td colspan="2"><a id="forget-password-submit" href="javascript:void(0);">submit</a></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		
		<div style="visibility:hidden;width:0px;height:0px;">
			<div id="forget-password-success-box">
				<h3>An email containing reset password information will be sent to you shortly.</h3>
			</div>
		</div>
		
		<div style="visibility:hidden;width:0px;height:0px;">
			<div id="sign-up-box">
				<h3>Sign Up</h3>
				<form id="sign-up-form" action="/access/sign-up" method="POST">
					<table>
						<tr>
							<td>First Name:</td>
							<td><input id="sign-up-first-name" name="sign-up-first-name" type="text" /></td>
						</tr>
						<tr>
							<td>Last Name:</td>
							<td><input id="sign-up-last-name" name="sign-up-last-name" type="text" /></td>
						</tr>
						<tr>
							<td>Email Address:</td>
							<td><input id="sign-up-email" name="sign-up-email" type="text" /></td>
						</tr>
						<tr>
							<td>Password:</td>
							<td><input id="sign-up-password" name="sign-up-password" type="text" /></td>
						</tr>
						<tr>
							<td colspan="2"><a id="sign-up-submit" href="javascript:void(0);">Sign Up</a></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		
		<div style="visibility:hidden;width:0px;height:0px;">
			<div id="sign-up-success-box">
				<h3>Thank you</h3>
				<p>An activation email will be sent to you shortly.</p>
			</div>
		</div>
            