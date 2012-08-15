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
					<div id="popular-apps-box" style="width: 650px;float: left;">
						
					</div>
					<div id="must-have-apps-box">
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
					url: "/load-apps",
					dataType: "json",
					type: "GET",
					success: function(data)
					{
						$.each(data, function(index, item)
						{
							$("#popular-apps-box").append('<div class="app-box" style="float: left;background-color: #444444;width: 300px;height: 150px;border-top-left-radius: 5px;border-top-right-radius: 5px;padding:0 5px;margin-right:10px;margin-bottom:10px;">' +
																'<div class="app-box-header" style="width: 100%;height: 30px;padding: 2px 5px;">' +
																'<div class="app-name" style="float: left;font-size: 13px;font-weight: bold;color: #ffffff;width: auto;margin-top:5px;">' + item.name + '</div>' +
																'<div class="app-category" style="float: right;font-size: 12px;font-weigth: bold;color: #ffffff;width: auto;text-align:right;padding-right:15px;margin-top:5px;">' + item.categories + '</div>' +
															'</div>' +
															'<div class="app-box-middle" style="background-color: #FFFFFF;width: auto;height: 80px;padding: 1px 5px;">' +
																'<div class="app-icon" style="float: left;width: 60px;height: 60px;padding: 15px 1px;">' +
																	'<img src="' + item.icon + '" />' +
																'</div>' +
																'<div class="app-info" style="float: right;width: 200px;height: 90px;">' +
																	'<div style="width: 100%;padding: 5px;font-size: 12px;">' + item.haveCount + ' users have it</div>' +
																	'<div style="width: 100%;padding: 5px;font-size: 12px;">' + item.recommendationCount + ' users recommend it</div>' +
																	'<div style="width: 100%;padding: 5px;font-size: 12px;color: #000000"><a href="/app/' + item.name.replace(/ /g,'-') + '">App Detail</a></div>' +
																'</div>' +
															'</div>' +
															'<div class="app-box-footer" style="width: 100%;height: 30px;background-color: #ffffff;">' +
																'<ul style="height: 100%;width: 100%;float: left;">' +
																	'<li><a href="javascript:void(0);" onclick="have(\'' + item.rawId + '\',\'' + item.name + '\',\'' + item.icon + '\',\'\')" class="link-button" style="float: left;font-size: 12px;margin: 2px;">Have</a></li>' +
																	'<li><a href="javascript:void(0);" class="link-button" style="float: left;font-size: 12px;margin: 2px;">Comment</a></li>' +
																	'<li><a href="javascript:void(0);" onclick="have(\'' + item.rawId + '\',\'' + item.name + '\',\'' + item.icon + '\',\'\')" class="link-button" style="float: left;font-size: 12px;margin: 2px;">Recommend</a></li>' +
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
						//alert("success: " + data.resultCount);
						var result = "";
						$.each(data.results, function(index, item)
						{
						    //alert(item.trackId + ': ' + item.trackName);
						    //result += "<tr>" +
						    				//"<td><a target='_blank' href='" + item.artistViewUrl + "'><img id='" + item.trackId + "_icon' src='" + item.artworkUrl60 + "' /></a></td>" +
						    				//"<td><a id='" + item.trackId + "_url' target='_blank' href='" + item.artistViewUrl + "'><span  id='" + item.trackId + "_name'>" + item.trackName + "</span></a></td>" +
						    				//"<td><span id='" + item.trackId + "_company'>" + item.artistName + "</span></td>" +
						    				//"<td>" + item.averageUserRating + "</td>" +
						    				//"<td>" + item.userRatingCount + "</td>" +
						    				//"<td><a class='button yellow' id='" + item.trackId + "_have' href='javascript:void(0);' onclick='have(\"" + item.trackId + "\",\"" + item.trackName + "\",\"" + item.artworkUrl60 + "\",\"" + item.trackViewUrl + "\",\"" + item.supportedDevices + "\",\"" + item.primaryGenreName + "\")'>I Have</a></td>" +
						    				//"<td><a class='button blue' id='" + item.trackId + "_recommend' href='javascript:void(0);' onclick='recommend(\"" + item.trackId + "\",\"" + item.trackName + "\",\"" + item.artworkUrl60 + "\",\"" + item.trackViewUrl + "\",\"" + item.supportedDevices + "\",\"" + item.primaryGenreName + "\")'>I Recommend</a></td>" +
						    			//"</tr>";
						    result += '<li style="padding: 10px;overflow: auto;">' +
											'<a target="_blank" href="' + item.artistViewUrl + '" style=" float: left;margin: 0 15px 0 0;"><img src="' + item.artworkUrl60 + '" ></a>' +
											'<h3 style="font: bold 20px/1.5 Helvetica, Verdana, sans-serif;"><a target="_blank" id="' + item.trackId + '_url" href="' + item.artistViewUrl + '"><span id="' + item.trackId + '_name">' + item.trackName + '</span></a></h3>' +
											'<p style="font: 200 12px/1.5 Georgia, Times New Roman, serif;">' + item.averageUserRating + '</p>' +
											'<p style="font: 200 12px/1.5 Georgia, Times New Roman, serif;">' + item.userRatingCount + '</p>' +
											'<p style="font: 200 12px/1.5 Georgia, Times New Roman, serif;">' +
												'<a class="" id="' + item.trackId + '_have" href="javascript:void(0);" onclick="have(\'' + item.trackId + '\',\'' + item.trackName + '\',\'' + item.artworkUrl60 + '\',\'' + item.supportedDevices + '\',\'' + item.primaryGenreName + '\')">I Have</a>' +
												'<a class="" id="' + item.trackId + '_recommend" href="javascript:void(0);" onclick="recommend(\'' + item.trackId + '\',\'' + item.trackName + '\',\'' + item.artworkUrl60 + '\',\'' + item.supportedDevices + '\',\'' + item.primaryGenreName + '\')">I Recommend</a>' +
											'</p>' +
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
						$.colorbox({inline:true, href:"#search-result-box", width:"50%", height:"50%"});
					},
					error: function(jqXHR, textStatus, errorThrown)
					{
						alert("error: " + errorThrown);
					}
				});
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
						alert(data.token);
						if(data.token && data.token != "")
						{
							signInSuccessHandler(data);	
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
				$("#user-link").html(data.firstName);
				$("#user-link").attr("href", ("user/" + data.firstName + "-" + data.lastName + ".html?token=" + data.token));
				$("#user-app-count").html(data.appCount);
				$("#user-recommendation-count").html(data.recommendationCount);
				$("#user-follower-count").html(data.followerCount);
				$("#user-following-count").html(data.followingCount);
				$("#user-details").attr("href", ("user/" + data.firstName + "-" + data.lastName + ".html?token=" + data.token));
				$("#user-info").removeClass("hidden");
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
						url: "/have",
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
				var toTokens = [""];
				
				if(token != "")
				{
					$.ajax
					({
						url: "/recommend",
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