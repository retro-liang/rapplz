		<div id="header-container">

			<c:set var="authenticated" value="${false}" />
			<sec:authorize access="isAuthenticated()">
			    <c:set var="authenticated" value="${true}" />
			</sec:authorize>
			
			<div id="logo-container">
				<a id="logo-link" href="<spring:url value="/" htmlEscape="true" />">
					<img id="logo-image" src="/img/logo.png" alt="Logo" title="Logo" />
				</a>
			</div>

			<div id="app-search-container">
				<input id="app-search-box" name="app-search-box" type="text" class="input-box" />
				<a id="app-search-button" href="<spring:url value="/recommend.html" htmlEscape="true" />" class="button blue">Recommend</a>
			</div>

			<div id="access-container">
				<ul id="access-container-not-signed-in">
					<li id="sign-in-container"><a id="sign-in-button" href="<spring:url value="/access/sign-in.html" htmlEscape="true" />" class="link-button">Sign In</a></li>
					<li id="sign-up-container"><a id="sign-up-button" href="<spring:url value="/access/sign-up.html" htmlEscape="true" />" class="link-button">Sign Up</a></li>
				</ul>
				<ul id="access-container-signed-in">
					<li id="welcome-container"><a id="user-link" class="link-button">${userInfo.firstName}</a></li>
					<li id="sign-out-container"><a id="sign-out-link" href="<c:url value="/j_spring_security_logout" />" class="link-button">Sign Out</a></li>
				</ul>
				<input id="token" type="hidden" value="${userInfo.token}" />
			</div>
			
			
			<c:choose>
				<c:when test="${!authenticated}">
					<script type="text/javascript">
						document.getElementById("access-container-signed-in").className += "hidden";
					</script>
				</c:when>
				<c:otherwise>
					<script type="text/javascript">document.getElementById("access-container-not-signed-in").className += "hidden";</script>
				</c:otherwise>
			</c:choose>
			
		</div>
		
		<div id="sub-header" style="width: auto;background-color: #333333;border-bottom: 1px solid #444444;border-top: 1px solid #383838;box-shadow: 0 2px 0 0 #262626, 0 -1px 0 0 #171717;display: block;height: 38px;margin: 55px auto 5px auto;">
			<ul style="width: 500px;float: left;padding-left: 50px;padding-top: 12px;">
				<li><a href="/app.html" style="float: left;font-size: 12px;font-weight: bold;text-shadow: 0 0 0 transparent, 0 1px rgba(29, 29, 29, 0.2);border-radius: 2px 2px 2px 2px;color: #C5C5C5;display: block;border-left: medium none;border-top: medium none;margin: 0 0;padding: 0 0.65em;text-decoration: none;">App</a></li>
				<li><a href="/people.html" style="float: left;font-size: 12px;font-weight: bold;text-shadow: 0 0 0 transparent, 0 1px rgba(29, 29, 29, 0.2);border-radius: 2px 2px 2px 2px;color: #C5C5C5;display: block;border-left: medium none;border-top: medium none;margin: 0 0;padding: 0 0.65em;text-decoration: none;">People</a></li>
				<li><a href="/news.html" style="float: left;font-size: 12px;font-weight: bold;border-radius: 2px 2px 2px 2px;color: #D1D4DA;display: block;border-left: medium none;border-top: medium none;margin: 0 0;padding: 0 0.65em;text-decoration: none;">News</a></li>
				<li><a href="/review.html" style="float: left;font-size: 12px;font-weight: bold;border-radius: 2px 2px 2px 2px;color: #D1D4DA;display: block;border-left: medium none;border-top: medium none;margin: 0 0;padding: 0 0.65em;text-decoration: none;">Review</a></li>
			</ul>
			<div id="search-box" style="float: right;margin: 5px 20px;">
				<input id="search-box" name="search-box" type="text" class="input-box" />
				<a id="search-button" class="button blue" style="font-size: 12px;font-weight: normal;margin-top: 4px;" href="<spring:url value="/search.html" htmlEscape="true" />">Search</a>
			</div>
		</div>
		
		<div id="top-ad-box" style="width: auto;margin: 5px auto 10px auto;padding-left: 100px;">
			<script type="text/javascript"><!--
			google_ad_client = "ca-pub-3748265501907091";
			/* rapplz-top */
			google_ad_slot = "3699626490";
			google_ad_width = 728;
			google_ad_height = 90;
			//-->
			</script>
			<script type="text/javascript"
			src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
			</script>
		</div>
		
		<div style="visibility:hidden;width:0px;height:0px;">
			<div id="search-result-box" style="margin: 20px;">
				<ul id="search-result" style="list-style-type: none;width: 500px;"></ul>
				<div class="holder" style="margin:0px;"></div>
			</div>
		</div>
		
		<div style="display: none;width:0px;height:0px;">
			<div id="sign-in-box" style="background-color: #3399DD;border-top-left-radius: 5px;border-top-right-radius: 5px;height: 220px;width: 350px;">
				<div class="sign-in-box-header" style="height: 30px;padding: 2px 5px;">
					<div class="app-name" style="font-size: 12px;font-weight: bold;color: #ffffff;width: auto;margin-top:5px;">Sign In</div>
				</div>
				<div class="sign-in-box-body" style="background-color: #FFFFFF;padding: 1px 5px;font-size: 12px;font-weight:bold;margin:0 5px;height: 150px;">
					<div id="google_login">
						<a href="javascript:void(0);" onclick="googleSignIn();" id="google-sign-in">Sign In with Google Account</a>
					</div>
					<div id="facebook_login">
						<p>Sign In with Your Google Account</p>
						<a href="/services/facebook/authorize" target="fb_auth" onclick="window.open('/services/facebook/authorize','fb_auth','width=580,height=400'); $.mq.send('user:facebook_connect');" class="btn_facebook fire_event" message="facebook_connect" id="facebook_account_auth">Connect with Facebook</a>
					</div>
					<form id="sign-in-form" action="/j_spring_security_check" method="POST">
						<p>Sign In with Your Account</p>
						<div class="form_field email">
							<label style="width: 120px;float: left;margin: 1px 3px;font-size:12px;">Email Address:</label>
							<input type="email" id="j_username" name="j_username" tabindex="0" value="" style="width: 150px;float: right;margin: 1px 3px;" />
						</div>
						<div class="form_field password">
							<label style="width: 120px;float: left;margin: 1px 3px;font-size:12px;">Password:</label>
							<input type="password" id="j_password" name="j_password" tabindex="0" style="width: 150px;float: right;margin: 1px 3px;" />
							<p style="width: 150px;float: right;margin: 1px 3px;clear:both;"><a id="forget-password-button" href="<spring:url value="/access/forget-password.html" htmlEscape="true" />">Forget password?</a></p>
						</div>
						<div class="form_field rememberMe" style="clear: both;width: 100%;height: 20px;float: left;">
							<label style="width: 110px;float: left;margin: 1px 3px;">Remember Me</label>
							<input type="checkbox" id="remember-me" name="_spring_security_remember_me" tabindex="0" style="float: left;" />
							<a id="sign-in-submit" href="javascript:void(0);" class="button blue" style="width: 50px;float: right;">Sign In</a>
						</div>
					</form>
					<div class="error_message" id="login_error_message"></div>
				</div>
				<div class="modal_footer" style="height: 30px;padding: 5px">
					<p style="color: #ffffff;font-size: 12px;padding: 2px;width: 150px;float: left;'">Don't have an account?</p><a id="sign-in-sign-up-button" href="/access/sign-up.html" class="button yellow" style="color: #ffffff;font-size: 12px;float: right;width: 100px;">Sign up now!</a>
				</div>
			</div>
		</div>
		
		<div style="display: none;width:0px;height:0px;">
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
		
		<div style="display: none;width:0px;height:0px;">
			<div id="forget-password-success-box">
				<h3>An email containing reset password information will be sent to you shortly.</h3>
			</div>
		</div>
		
		<div style="display: none;width:0px;height:0px;">
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
		
		<div style="display: none;width:0px;height:0px;">
			<div id="sign-up-success-box">
				<h3>Thank you</h3>
				<p>An activation email will be sent to you shortly.</p>
			</div>
		</div>
            