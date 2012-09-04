	<div id="user-info-box">
		<ul>
			<li>User Apps:<span id="user-app-count">${userInfo.appCount}</span></li>
			<li>User Recommendations:<span id="user-recommendation-count">${userInfo.recommendationCount}</span></li>
			<li>User Followers:<span id="user-follower-count">${userInfo.followerCount}</span></li>
			<li>User Followings:<span id="user-following-count">${userInfo.followingCount}</span></li>
			<li><a id="user-details" href="/user/${userInfo.firstName}-${userInfo.lastName}.html?token=${userInfo.token}">User Details</a></li>
		</ul>
	</div>
	
	<c:choose>
		<c:when test="${!authenticated}">
			<script type="text/javascript">
				document.getElementById("user-info-box").className += "hidden";
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
	<div id="ad-box" style="margin-top: 15px;">
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