	<div id="right-column">
		<div id="user-info">
			<table>
				<tr>
					<td>User Apps:</td>
					<td id="user-app-count">${userInfo.appCount}</td>
				</tr>
				<tr>
					<td>User Recommendations:</td>
					<td id="user-recommendation-count">${userInfo.recommendationCount}</td>
				</tr>
				<tr>
					<td>User Followers:</td>
					<td id="user-follower-count">${userInfo.followerCount}</td>
				</tr>
				<tr>
					<td>User Followings:</td>
					<td id="user-following-count">${userInfo.followingCount}</td>
				</tr>
				<tr>
					<td colspan="2"><a id="user-details" href="/user/${userInfo.firstName}-${userInfo.lastName}.html?token=${userInfo.token}">User Details</a></td>					
				</tr>
			</table>
		</div>
		
		<c:choose>
			<c:when test="${!authenticated}">
				<script type="text/javascript">
					document.getElementById("user-info").className += "hidden";
				</script>
			</c:when>
		</c:choose>
			
		<div id="hot-apps-box">
		</div>
		<div id="activity-box">
		</div>
		<div id="google-plus-box">
		</div>
		<div id="facebook-box">
		</div>
		<div id="ad-box">
			<script type="text/javascript">
				<!--
					google_ad_client = "ca-pub-3748265501907091";
					/* rapplz */
					google_ad_slot = "8283218969";
					google_ad_width = 300;
					google_ad_height = 250;
				//-->
			</script>
			<script type="text/javascript" src="http://pagead2.googlesyndication.com/pagead/show_ads.js"></script>
		</div>
	</div>