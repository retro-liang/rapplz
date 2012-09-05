	<head>
		<title>springed gae</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" type="text/css" href="/css/rapplz.css" />
		
		<style>
			h1 {
				font-size: 20px;
			}
		
			/* Slides Show */
            /* 
				Resets defualt browser settings
				reset.css
			*/
			ol,ul { list-style:none; }
			blockquote:before,blockquote:after,q:before,q:after { content:""; }
			blockquote,q { quotes:"" ""; }
			
			/*
				Page style
			*/
			
			body { 
				
			}
			
			#ribbon {
				position:absolute;
				left:55px;
				z-index:500;
			}
			
			#frame {
				position:absolute;
				z-index:0;
				/*width:739px;*/
				height:346px;
			}
			
			/*
				Slideshow
			*/
			
			#slides {
				position:absolute;
				top:73px;
				left:78px;
				z-index:100;
			}
			
			/*
				Slides container
				Important:
				Set the width of your slides container
				Set to display none, prevents content flash
			*/
			
			.slides_container {
				width: 835px;
				height:300px;
				overflow:hidden;
				position:relative;
				display:none;
			}
			
			/*
				Each slide
				Important:
				Set the width of your slides
				If height not specified height will be set by the slide content
				Set to display block
			*/
			
			.slides_container a {
				width:830px;
				height:300px;
				display:block;
			}
			
			/*
				Next/prev buttons
			*/
			
			#slides .next,#slides .prev {
				position:absolute;
				top:107px;
				left:-45px;
				width:24px;
				height:43px;
				display:block;
				z-index:101;
			}
			
			#slides .next {
				left:856px;
			}
			
			/*
				Pagination
			*/
			
			.pagination {
				margin: 28px auto 0 400px;
				width: 100px;
			}
			
			.pagination li {
				float:left;
				margin:0 1px;
				list-style:none;
			}
			
			.pagination li a {
				display:block;
				width:12px;
				height:0;
				padding-top:12px;
				background-image:url(/img/pagination.png);
				background-position:0 0;
				float:left;
				overflow:hidden;
			}
			
			.pagination li.current a {
				background-position:0 -12px;
			}
			
			/*
				Footer
			*/
			
			#footer {
				text-align:center;
				width:580px;
				margin-top:9px;
				padding:4.5px 0 18px;
				border-top:1px solid #dfdfdf;
			}
			
			#footer p {
				margin:4.5px 0;
				font-size:1.0em;
			}
			
			/*
				Anchors
			*/
			
			a:link,a:visited {
				color:#599100;
				text-decoration:none;
			}
			
			a:hover,a:active {
				color:#599100;
				text-decoration:underline;
			}
			/* End of Slides Show */
			
			.holder a {
				background: none repeat scroll 0 0 #FDFDFD;
			    border: 1px solid #DADAD3;
			    border-radius: 3px 3px 3px 3px;
			    color: #666666;
			    display: block;
			    float: left;
			    margin-right: 3px;
			    padding: 2px 9px;
			    text-decoration: none;
				-moz-transition: opacity 0.1s ease-in 0s;
    			color: #3399DD;
    			text-decoration: none;
			}
			.holder a:hover, .jp-current {
				background: -moz-linear-gradient(center top, #77BBE8 0%, #3399DD 100%) repeat scroll 0 0 transparent !important;
				border-color: #12486D !important;
				color: #FFFFFF !important;
			}
			
			/* Contact Form */
			#contact_form_holder {
			    font-family: 'Verdana'; /* this is a nice font-family, at least i think, if you don't like it change it <img src="http://web.enavu.com/wp-includes/images/smilies/icon_smile.gif" alt=":)" class="wp-smiley">  */
			    font-variant: small-caps; /* making the small letter looks like capital but keeping the size of it to smaller, looks cool */
			    width:400px; /* setting a fixed width of the contact form holder will make things easier later (like aligning and such) */
			}
			#contact_form_holder input, #contact_form_holder textarea {
			    width:100%; /* make all the inputs and the textarea same size (100% of the div they are into) */
			    font-family: inherit ; /* we must set this, so it inherits the font-family */
			    padding:5px; /* and make a custom padding, you can set whatever you like */
			}
			#contact_form_holder textarea {
			    height:100px; /* i never liked small textareas, so make it 100px in height */
			}
			#send_message {
			    width:200px !important; /* the width of the submit button  */
			    font-variant: small-caps; /* nicer font-variant (like explained before) */
			    border:1px solid black; /* remove the default border and put a normal black one */
			    cursor:pointer;
			    cursor:hand;
			}
			#cf_submit_p { text-align:right; } /* show the submit button aligned with the right side */
			
			/* styling */
			
			.error {
			    display: none; /* hide the errors */
			    /* add some styling */
			    padding:10px;
			    color: #D8000C;
			    font-size:12px;
			    background-color: #FFBABA;
			}
			.success {
			    display: none; /* hide the sucess div */
			    /* add some styling */
			    padding:10px;
			    color: #044406;
			    font-size:12px;
			    background-color: #B7FBB9;
			}
			
			#contact_logo { vertical-align: middle; }
			.error img { vertical-align:top; }
			/* End of Contact Form */
			
			/* Sign In Buttons */
			.button.google {
			    background: -moz-linear-gradient(center top , #3B0000 0%, #3B0000 50%, #320000 50%, #320000 100%) repeat scroll 0 0 transparent;
			    -moz-user-select: none;
			    border-radius: 5px 5px 5px 5px;
			    display: block;
    			box-shadow: 0 3px 0 #210000;
    			border: 1px solid #320000;
			    color: #FFFFFF;
			    font-weight: bold;
			    text-shadow: 0 1px 0 #320000;
			}
			
			.button.facebook {
			    background: -moz-linear-gradient(center top , #3B5998 0%, #3B5998 50%, #325190 50%, #325190 100%) repeat scroll 0 0 transparent;
			    -moz-user-select: none;
			    border-radius: 5px 5px 5px 5px;
			    display: block;
    			box-shadow: 0 3px 0 #213B6D;
    			border: 1px solid #325190;
			    color: #FFFFFF;
			    font-weight: bold;
			    text-shadow: 0 1px 0 #325190;
			}
			
			.button.twitter {
			    background: -moz-linear-gradient(center top , #32C1F0 0%, #32C1F0 49%, #30BBE9 49%, #30BBE9 100%) repeat scroll 0 0 transparent;
			    -moz-user-select: none;
			    border-radius: 5px 5px 5px 5px;
			    display: block;
    			box-shadow: 0 3px 0 #249BC4;
				border: 1px solid #30BBE9;
			    color: #FFFFFF;
			    text-shadow: -1px -1px 0 #30BBE9;
			}
			/* End of Sign In Buttons */
			
			.app-box {
				float: left;
				background-color: #444444;
				width: 300px;
				height: 150px;
				border-top-left-radius: 5px;
				border-top-right-radius: 5px;
				padding: 0 5px;
				margin-right: 10px;
				margin-bottom: 10px;
			}
			
			.app-box .app-box-header {
				width: 100%;
				height: 30px;
				padding: 2px 5px;
				overflow: hidden;
			}
			
			.app-box .app-box-header .app-name {
				float: left;
				font-size: 13px;
				font-weight: bold;
				color: #ffffff;
				width: auto;
				margin-top:5px;
			}
			
			.app-box .app-box-header .app-category {
				float: right;
				font-size: 12px;
				color: rgb(255, 255, 255);
				width: auto;
				text-align: right;
				padding-right: 15px;
				margin-top: 5px;
				overflow: hidden;
			}
			
			.app-box .app-box-middle {
				background-color: #FFFFFF;
				width: auto;
				height: 80px;
				padding: 1px 5px;
			}
			
			.app-box .app-box-middle .app-icon {
				float: left;
				width: 60px;
				height: 60px;
				padding: 15px 1px;
			}
			
			.app-box .app-box-middle .app-info {
				float: right;
				width: 200px;
				height: 90px;
			}
			
			.app-box .app-box-middle .app-info .have-info {
				width: 100%;
				padding: 5px;
				font-size: 12px;
			}
			
			.app-box .app-box-middle .app-info .recommendation-info {
				width: 100%;
				padding: 5px;
				font-size: 12px;
			}
			
			.app-box .app-box-middle .app-info .detail-link{
				width: 100%;
				padding: 5px;
				font-size: 12px;
				color: #000000
			}
			
			.app-box .app-box-footer {
				width: 100%;
				height: 30px;
				background-color: #ffffff;
			}
			
			.app-box .app-box-footer ul{
				height: 100%;
				width: 100%;
				float: left;
			}
			
			.app-box .app-box-footer ul li a{
				float: left !important;
				font-size: 12px !important;
				margin: 2px !important;
			}
			
			#user-info-box {
				width: 80%;
				font-size: 13px;
				background: none repeat scroll 0 0 #F9F9F9;
    			border: 1px solid #E1E1E1;
    			padding: 15px;
			    position: relative;
			}
			
			#user-info-box ul {
				width: 100%;
				height: 100px;
			}
			
			#user-info-box ul li {
				width: 100%;
				float: left;
				padding-bottom: 5px;
			}
			
			#user-info-box ul li span {
				margin-left: 5px;
			}
			
			#category-box li {
				float: left;
    			width: 100%;
			}
			
			#category-box li a{
				color: #DED9CD;
			}
		</style>
	</head>