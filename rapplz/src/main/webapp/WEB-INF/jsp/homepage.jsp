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
				<div style="height: 330px;width: 100%;margin: 10px 0 10px 0;padding-bottom: 20px;">
					<img src="/img/new-ribbon.png" width="112" height="112" alt="New Ribbon" id="ribbon" />
					<div id="slides">
						<div class="slides_container">
							<a href="http://www.flickr.com/photos/michaelsilberstein/4294842946/" title="" target="_blank"><img src="/img/photo-1.jpg" width="835" height="300" alt="" /></a>
							<a href="http://www.flickr.com/photos/bigberto/2857852001/" title="" target="_blank"><img src="/img/photo-2.jpg" width="835" height="300" alt="" /></a>
							<a href="http://www.flickr.com/photos/yourdon/4133227641/" title="" target="_blank"><img src="/img/photo-3.jpg" width="835" height="300" alt="" /></a>
							<a href="http://www.flickr.com/photos/typicalnaloboy/4151014982/" title="" target="_blank"><img src="/img/photo-4.jpg" width="835" height="300" alt="" /></a>
						</div>
						<a href="#" class="prev"><img src="/img/arrow-prev.png" width="24" height="43" alt="Arrow Prev" /></a>
						<a href="#" class="next"><img src="/img/arrow-next.png" width="24" height="43" alt="Arrow Next" /></a>
					</div>
					<img src="/img/frame.png" width="980" height="340" id="frame">
				</div>
				<div id="left-column">
					<div id="recent-apps-box" style="width: 650px;float: left;border-bottom: 1px dotted #CECECE;padding-bottom: 15px;">
						<h1>Recent added apps:</h1>
					</div>
					
					<div id="game-apps-box" style="width: 650px;float: left;border-bottom: 1px dotted #CECECE;padding: 10px 0 15px 0;">
						<h1>Most recommended game apps:</h1>
					</div>
					
					<div id="entertainment-apps-box" style="width: 650px;float: left;border-bottom: 1px dotted #CECECE;padding: 10px 0 15px 0;">
						<h1>Most recommended entertainment apps:</h1>
					</div>
					
					<input id="os" value="IOS" type="hidden" />
				</div>
				<div id="right-column">
					<%@ include file="/WEB-INF/jsp/right.jsp" %>
				</div>
			</div>

			<%@ include file="/WEB-INF/jsp/footer.jsp" %>

		</div>
		
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
		<script type="text/javascript" src="/js/rapplz.js"></script>
		
		<script type="text/javascript">
			$(function(){
				$('#slides').slides({
					preload: true,
					preloadImage: '/img/loading.gif',
					play: 5000,
					pause: 2500,
					hoverPause: true
				});
			});
			
			$(document).ready(function()
			{				
				$(".callbacks").colorbox({
					onOpen:function(){ alert('onOpen: colorbox is about to open'); },
					onLoad:function(){ alert('onLoad: colorbox has started to load the targeted content'); },
					onComplete:function(){ alert('onComplete: colorbox has displayed the loaded content'); },
					onCleanup:function(){ alert('onCleanup: colorbox has begun the close process'); },
					onClosed:function(){ alert('onClosed: colorbox has completely closed'); }
				});
				
				$.ajax
				({
					url: "/app/load-apps",
					dataType: "json",
					type: "GET",
					success: function(data)
					{
						$.each(data, function(index, item)
						{
							$("#recent-apps-box").append('<div class="app-box">' +
																'<div class="app-box-header">' +
																'<div class="app-name">' + item.name + '</div>' +
																'<div class="app-category">' + item.categoryNames + '</div>' +
															'</div>' +
															'<div class="app-box-middle">' +
																'<div class="app-icon">' +
																	'<img src="' + item.icon + '" />' +
																'</div>' +
																'<div class="app-info">' +
																	'<div class="have-info">' + item.haveCount + ' users have it</div>' +
																	'<div class="recommendation-info">' + item.recommendationCount + ' users recommend it</div>' +
																	'<div class="detail-link"><a href="/app/' + item.name.replace(/ /g,'-') + '">App Detail</a></div>' +
																'</div>' +
															'</div>' +
															'<div class="app-box-footer">' +
																'<ul>' +
																	'<li><a href="javascript:void(0);" onclick="have(\'' + item.rawId + '\',\'' + item.name + '\',\'' + item.icon + '\',\'\',\'\')" class="link-button">Have</a></li>' +
																	'<li><a href="javascript:void(0);" class="link-button">Comment</a></li>' +
																	'<li><a href="javascript:void(0);" onclick="recommend(\'' + item.rawId + '\',\'' + item.name + '\',\'' + item.icon + '\',\'\',\'\')" class="link-button">Recommend</a></li>' +
																'</ul>' +
															'</div>' +
														'</div>');
							
							$("#game-apps-box").append('<div class="app-box">' +
																'<div class="app-box-header">' +
																'<div class="app-name">' + item.name + '</div>' +
																'<div class="app-category">' + item.categoryNames + '</div>' +
															'</div>' +
															'<div class="app-box-middle">' +
																'<div class="app-icon">' +
																	'<img src="' + item.icon + '" />' +
																'</div>' +
																'<div class="app-info">' +
																	'<div class="have-info">' + item.haveCount + ' users have it</div>' +
																	'<div class="recommendation-info">' + item.recommendationCount + ' users recommend it</div>' +
																	'<div class="detail-link"><a href="/app/' + item.name.replace(/ /g,'-') + '">App Detail</a></div>' +
																'</div>' +
															'</div>' +
															'<div class="app-box-footer">' +
																'<ul>' +
																	'<li><a href="javascript:void(0);" onclick="have(\'' + item.rawId + '\',\'' + item.name + '\',\'' + item.icon + '\',\'\',\'\')" class="link-button">Have</a></li>' +
																	'<li><a href="javascript:void(0);" class="link-button">Comment</a></li>' +
																	'<li><a href="javascript:void(0);" onclick="recommend(\'' + item.rawId + '\',\'' + item.name + '\',\'' + item.icon + '\',\'\',\'\')" class="link-button">Recommend</a></li>' +
																'</ul>' +
															'</div>' +
														'</div>');
							
							$("#entertainment-apps-box").append('<div class="app-box">' +
																		'<div class="app-box-header">' +
																		'<div class="app-name">' + item.name + '</div>' +
																		'<div class="app-category">' + item.categoryNames + '</div>' +
																	'</div>' +
																	'<div class="app-box-middle">' +
																		'<div class="app-icon">' +
																			'<img src="' + item.icon + '" />' +
																		'</div>' +
																		'<div class="app-info">' +
																			'<div class="have-info">' + item.haveCount + ' users have it</div>' +
																			'<div class="recommendation-info">' + item.recommendationCount + ' users recommend it</div>' +
																			'<div class="detail-link"><a href="/app/' + item.name.replace(/ /g,'-') + '">App Detail</a></div>' +
																		'</div>' +
																	'</div>' +
																	'<div class="app-box-footer">' +
																		'<ul>' +
																			'<li><a href="javascript:void(0);" onclick="have(\'' + item.rawId + '\',\'' + item.name + '\',\'' + item.icon + '\',\'\',\'\')" class="link-button">Have</a></li>' +
																			'<li><a href="javascript:void(0);" class="link-button">Comment</a></li>' +
																			'<li><a href="javascript:void(0);" onclick="recommend(\'' + item.rawId + '\',\'' + item.name + '\',\'' + item.icon + '\',\'\',\'\')" class="link-button">Recommend</a></li>' +
																		'</ul>' +
																	'</div>' +
																'</div>');
						});
					},
					error: function(jqXHR, textStatus, errorThrown)
					{
						alert("error: " + errorThrown);
					}
				});
			});
		
			var iosAppSearchUrl = "http://itunes.apple.com/search";
			
			$("#app-search-button").click(function(event)
			{
				event.preventDefault();
				event.stopPropagation();
				var keyword = $("#app-search-box").val();
				
				if(keyword != "")
				{
					$.ajax
					({
						url: iosAppSearchUrl,
						dataType: 'jsonp',
						data: 
						{
							country: "us",
							entity: "software",
							limit: "20",
							term: keyword
						},
						success: function(data)
						{
							var result = "";
							$.each(data.results, function(index, item)
							{
							    result += '<li style="padding: 30px;">' +
												'<a style="clear:left;float: left;margin: 10px;height: 60px;width: 60px;" target="_blank" href="' + item.artistViewUrl + '">' +
													'<img src="' + item.artworkUrl60 + '" >' +
												'</a>' +
												'<div style="float: left;height: 60px;width: 500px;">' +
													'<h3 style="margin: 15px 3px 5px 0;font-size: 15px;width: auto;clear: right;"><a style="color:#222222;" target="_blank" id="' + item.trackId + '_url" href="' + item.artistViewUrl + '"><span id="' + item.trackId + '_name">' + item.trackName + '</span></a></h3>' +
													'<div style="float: left;margin: 5px 0 0 0;width: auto;clear: right;">' +
														'<p style="font-size: 12px;float: left;">App store rating: ' + item.averageUserRating + ' (' + item.userRatingCount + ' ratings)</p>' +
													'</div>' +
													'<div style="float: right;margin: 5px;width: auto;clear: right;">' +
														'<p style="font-size: 12px;float: left;">' +
															'<a class="link-button" style="margin-right:10px;" id="' + item.trackId + '_have" href="javascript:void(0);" onclick="have(\'' + item.trackId + '\',\'' + item.trackName + '\',\'' + item.artworkUrl60 + '\',\'' + item.supportedDevices + '\',\'' + item.primaryGenreName + '\')">I Have</a>' +
															'<a class="link-button" id="' + item.trackId + '_recommend" href="javascript:void(0);" onclick="recommend(\'' + item.trackId + '\',\'' + item.trackName + '\',\'' + item.artworkUrl60 + '\',\'' + item.supportedDevices + '\',\'' + item.primaryGenreName + '\')">I Recommend</a>' +
														'</p>' +
													'</div>' +
												'</div>' +
											'</li>';
							});
							$("#search-result").html(result);
							$("div.holder").jPages
							({
					            containerID : "search-result",
					            previous : "←",
					            next : "→",
					            perPage : 5,
					            delay : 0
					        });
							$.colorbox
							({
								inline: true,
								href: "#search-result-box",
								width: "680px",
								height: "500px",
								onCleanup: function()
								{
									$("#search-result").html("");
									$(".holder").html("");
								}
							});
						},
						error: function(jqXHR, textStatus, errorThrown)
						{
							alert("error: " + errorThrown);
						}
					});
				}
			});
			
			$("#sign-in-button").click(function(event)
			{
				event.preventDefault();
				event.stopPropagation();
				$.colorbox({inline:true, href:"#sign-in-box"});
			});
			
			$("#sign-in-submit").click(function(event)
			{
				event.preventDefault();
				event.stopPropagation();
				
				$("#login_error_message").html("");
				
				var url = $("#sign-in-form").attr("action");
				var username = $("#j_username").val();
				var password = $("#j_password").val();
				var rememberMe = $("#remember-me").prop("checked");
				
				$.ajax
				({
					url: url,
					type: "POST",
					data: 
					{
						j_username: username,
						j_password: password,
						_spring_security_remember_me: rememberMe
					},
					beforeSend: function (xhr)
					{
						xhr.setRequestHeader("X-Ajax-call", "true");
			        },
					success: function(data)
					{
						if(data.token && data.token != "")
						{
							signInSuccessHandler(data);	
						}
						else
						{
							$("#login_error_message").html("Sign in failed.");
						}
					},
					error: function(jqXHR, textStatus, errorThrown)
					{
						alert("error: " + errorThrown);
					}
				});
			});
			
			function signInSuccessHandler(data)
			{
				$("#token").val(data.token);
				$("#avatar").attr("src", data.avatar);
				$("#user-link").html(data.firstName);
				$("#user-link").attr("href", ("user/" + data.firstName + "-" + data.lastName + ".html?token=" + data.token));
				$("#user-app-count").html(data.appCount);
				$("#user-recommendation-count").html(data.recommendationCount);
				$("#user-follower-count").html(data.followerCount);
				$("#user-following-count").html(data.followingCount);
				$("#user-details").attr("href", ("/user/" + data.firstName + "-" + data.lastName + ".html?token=" + data.token));
				$("#user-info-box").removeClass("hidden");
				$("#access-container-not-signed-in").addClass("hidden");
				$("#access-container-signed-in").removeClass("hidden");
				$("#cboxClose").click();
				return false;
			}
			
			$("#forget-password-button").click(function(event)
			{
				event.preventDefault();
				event.stopPropagation();
				$.colorbox({inline:true, href:"#forget-password-box"});
			});
			
			$("#forget-password-submit").click(function(event)
			{
				event.preventDefault();
				event.stopPropagation();
				
				var url = $("#forget-password-form").attr("action");
				var email = $("#forget-password-email").val();
				
				$.ajax
				({
					url: url,
					type: "POST",
					data: 
					{
						email: email								
					},
					beforeSend: function (xhr)
					{
						xhr.setRequestHeader("X-Ajax-call", "true");
			        },
					success: function(data)
					{
						if(data == "ok")
						{
							$("#forget-password-email").val("");
							$.colorbox({inline:true, href:"#forget-password-success-box"});
						}
						else
						{
							alert("failed: " + data);
						}
					},
					error: function(jqXHR, textStatus, errorThrown)
					{
						alert("error: " + errorThrown);
					}
				});
			});
			
			$("#sign-up-button").click(function(event)
			{
				event.preventDefault();
				event.stopPropagation();
				$.colorbox({inline:true, href:"#sign-up-box"});
			});
			
			$("#sign-in-sign-up-button").click(function(event)
			{
				event.preventDefault();
				event.stopPropagation();
				$.colorbox({inline:true, href:"#sign-up-box"});
			});
			
			$("#sign-up-submit").click(function(event)
			{
				event.preventDefault();
				event.stopPropagation();
				
				var url = $("#sign-up-form").attr("action");
				var firstName = $("#sign-up-first-name").val();
				var lastName = $("#sign-up-last-name").val();
				var email = $("#sign-up-email").val();
				var password = $("#sign-up-password").val();
				
				$.ajax
				({
					url: url,
					type: "POST",
					data: 
					{
						firstName: firstName,
						lastName: lastName, 
						email: email,
						password: password
					},
					beforeSend: function (xhr)
					{
						xhr.setRequestHeader("X-Ajax-call", "true");
			        },
					success: function(data)
					{
						if(data == "ok")
						{
							$("#sign-up-first-name").val("");
							$("#sign-up-last-name").val("");
							$("#sign-up-email").val("");
							$("#sign-up-password").val("");
							$.colorbox({inline:true, href:"#sign-up-success-box"});
						}
						else
						{
							alert("failed: " + data);
						}
					},
					error: function(jqXHR, textStatus, errorThrown)
					{
						alert("error: " + errorThrown);
					}
				});
			});
			
			function have(rawId, name, icon, device, category)
			{
				var os = $("#os").val();
				var token = $("#token").val();
				if(token != "")
				{
					$.ajax
					({
						url: "/user/have",
						data: 
						{
							os: os,
							token: token,
							rawId: rawId,
							name: name,
							icon: icon,
							device: device,
							category: category
						},
						success: function(data)
						{
							alert(data);
							this.disabled = "disabled";
						},
						error: function(jqXHR, textStatus, errorThrown)
						{
							alert("error: " + errorThrown);
						}
					});	
				}
				else
				{
					$.colorbox({inline:true, href:"#sign-in-box"});
				}
			}
			
			function recommend(rawId, name, icon, device, category)
			{
				var os = $("#os").val();
				var token = $("#token").val();
				var toTokens = "";
				
				if(token != "")
				{
					$.ajax
					({
						url: "/user/recommend",
						data: 
						{
							os: os,
							fromToken: token,
							toTokens: toTokens,
							rawId: rawId,
							name: name,
							icon: icon,
							device: device,
							category: category
						},
						success: function(data)
						{
							alert(data);
							this.disabled = "disabled";
						},
						error: function(jqXHR, textStatus, errorThrown)
						{
							alert("error: " + errorThrown);
						}
					});
				}
				else
				{
					$.colorbox({inline:true, href:"#sign-in-box"});
				}
			}
		</script>
		
		<script src="https://apis.google.com/js/client.js"></script>
		
		<script>
			var clientId = '929855298687.apps.googleusercontent.com';
			var apiKey = 'AIzaSyB7wu0K1GW6v-AQFMaYqc324wTw-AX85xI';
			var scopes = ['https://www.googleapis.com/auth/userinfo.profile','https://www.googleapis.com/auth/userinfo.email','https://www.googleapis.com/auth/plus.me'];
	      
			function googleSignIn()
			{
				gapi.client.setApiKey(apiKey);
				gapi.auth.authorize({client_id: clientId, scope: scopes, immediate: false}, googleAuthHandler);
			    return false;
			}
			
			function googleAuthHandler(authResult)
			{
				if(authResult && !authResult.error)
				{
					gapi.client.load('oauth2', 'v2', function()
					{
						var request = gapi.client.oauth2.userinfo.get();
						request.execute(function(data)
						{
							if(data && !data.error)
							{
								alert("login success: " + data.id + "-" + data.email + "-" + data.given_name + "-" + data.family_name + "-" + data.picture);
								var avatar = data.picture;
								if(!avatar)
								{
									avatar = "";
								}
								federalSignIn("GOOGLE", data.id, data.email, data.given_name, data.family_name, avatar);
							}
							else
							{
								alert("Error: " + data.error.message);
							}
						});
					});
				}
				else
				{
					alert("Authorization Error: " + data.error.message);
				}
			}
			
			function federalSignIn(type, id, email, firstName, lastName, avatar)
			{
				$.ajax
				({
					url: "/access/federal-sign-in",
					data: 
					{
						type: type,
						id: id,
						email: email,
						firstName: firstName,
						lastName: lastName,
						avatar: avatar
					},
					success: function(data)
					{
						alert(data);
						signInSuccessHandler(data);
					},
					error: function(jqXHR, textStatus, errorThrown)
					{
						alert("error: " + errorThrown);
					}
				});
			}
			
			$("#gravatar-button").click(function(event)
			{
				event.preventDefault();
				event.stopPropagation();
				var hash = md5( strtolower( trim( "MyEmailAddress@example.com " ) ) );
			});
		</script>

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