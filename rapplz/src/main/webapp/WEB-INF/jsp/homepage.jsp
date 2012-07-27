<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	
	<%@ include file="/WEB-INF/jsp/head.jsp" %>

	<body>
		
		<div id="container">

			<%@ include file="/WEB-INF/jsp/header.jsp" %>

			<div class=main-container>
			</div>		

			<%@ include file="/WEB-INF/jsp/footer.jsp" %>

		</div>
		
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
		<script type="text/javascript" src="/js/rapplz.js"></script>
		
		<script type="text/javascript">
		$(document).ready(function(){
			
			$(".callbacks").colorbox({
				onOpen:function(){ alert('onOpen: colorbox is about to open'); },
				onLoad:function(){ alert('onLoad: colorbox has started to load the targeted content'); },
				onComplete:function(){ alert('onComplete: colorbox has displayed the loaded content'); },
				onCleanup:function(){ alert('onCleanup: colorbox has begun the close process'); },
				onClosed:function(){ alert('onClosed: colorbox has completely closed'); }
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
						    result += "<tr><td><a target='_blank' href='" + item.artistViewUrl + "'><img src='" + item.artworkUrl60 + "' /></a></td><td><a target='_blank' href='" + item.artistViewUrl + "'>" + item.trackName + "</a></td><td>" + item.artistName + "</td><td>" + item.averageUserRating + "</td><td>" + item.userRatingCount + "</td><td><a href='/recommend'>Recommend</a></td></tr>";
						});
						$("#search-result").html(result);
						$("div.holder").jPages({ 
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
						alert(data.firstName + "-" + data.lastName + "-" + data.email);
					},
					error: function(jqXHR, textStatus, errorThrown)
					{
						alert("error: " + errorThrown);
					}
				});
			});
			
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
		</script>
		
		<script>
	    $(function(){
	        
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