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
			$(".inline").colorbox({inline:true, width:"50%"});
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
				var keyword = $("#app-search-box").val();
				alert("search: " + keyword);
				
				$.ajax
				({
					url: iosAppSearchUrl,
					dataType: 'jsonp',
					data: 
					{
						country: "us",
						entity: "software",
						limit: "10",
						term: keyword
					},
					success: function(data)
					{
						alert("success: " + data.resultCount);
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
		        $("div.holder").jPages({ 
		            containerID : "search-result",
		            previous : "<-",
		            next : "->",
		            perPage : 20,
		            delay : 20
		        });
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