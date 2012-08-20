<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	
	<%@ include file="/WEB-INF/jsp/head.jsp" %>

	<body>
		
		<div id="container">

			<%@ include file="/WEB-INF/jsp/header.jsp" %>

			<div class=main-container>
				<h1>Contact Us</h1>
				<div id="left-column">
					<div id='contact_form_holder'>
						<form action='index.php' method='post' id='contact_form'>
						<h2><img id='contact_logo' src='images/mail.png' /> Contact Us</h2>
						
							<p>
							Your Name:
							<div id='name_error' class='error'><img src='images/error.png'> I don't talk to strangers.</div>
							<div><input type='text' name='name' id='name'></div>
							</p>
							
							<p>
							Your Email:
							<div id='email_error' class='error'><img src='images/error.png'> You don't want me to answer?</div>
							<div><input type='text' name='email' id='email'>
							</p>
							
							<p>
							The Subject:
							<div id='subject_error' class='error'><img src='images/error.png'> What is the purpose of the contact?</div>
							<div><input type='text' name='subject' id='subject'></div>
							</p>
							
							<p>
							The Message:
							<div id='message_error' class='error'><img src='images/error.png'> Forgot why you came here?</div>
							<div><textarea name='message' id='message'></textarea></div>
							</p>
							
							<div id='mail_success' class='success'><img src='images/success.png'> Thank you. The mailman is on his way.</div>
							<div id='mail_fail' class='error'><img src='images/error.png'> Sorry, don't know what happened. Try later.</div>
							<p id='cf_submit_p'>
							<input type='submit' id='send_message' value='Send The Message'>
							</p>
						
						</form>
					</div>
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
		    $(document).ready(function(){
		        $('#send_message').click(function(e){
	
		            //stop the form from being submitted
		            e.preventDefault();
	
		            /* declare the variables, var error is the variable that we use on the end
		            to determine if there was an error or not */
		            var error = false;
		            var name = $('#name').val();
		            var email = $('#email').val();
		            var subject = $('#subject').val();
		            var message = $('#message').val();
	
		            /* in the next section we do the checking by using VARIABLE.length
		            where VARIABLE is the variable we are checking (like name, email),
		            length is a javascript function to get the number of characters.
		            And as you can see if the num of characters is 0 we set the error
		            variable to true and show the name_error div with the fadeIn effect.
		            if it's not 0 then we fadeOut the div( that's if the div is shown and
		            the error is fixed it fadesOut.
	
		            The only difference from these checks is the email checking, we have
		            email.indexOf('@') which checks if there is @ in the email input field.
		            This javascript function will return -1 if no occurence have been found.*/
		            if(name.length == 0){
		                var error = true;
		                $('#name_error').fadeIn(500);
		            }else{
		                $('#name_error').fadeOut(500);
		            }
		            if(email.length == 0 || email.indexOf('@') == '-1'){
		                var error = true;
		                $('#email_error').fadeIn(500);
		            }else{
		                $('#email_error').fadeOut(500);
		            }
		            if(subject.length == 0){
		                var error = true;
		                $('#subject_error').fadeIn(500);
		            }else{
		                $('#subject_error').fadeOut(500);
		            }
		            if(message.length == 0){
		                var error = true;
		                $('#message_error').fadeIn(500);
		            }else{
		                $('#message_error').fadeOut(500);
		            }
	
		            //now when the validation is done we check if the error variable is false (no errors)
		            if(error == false){
		                //disable the submit button to avoid spamming
		                //and change the button text to Sending...
		                $('#send_message').attr({'disabled' : 'true', 'value' : 'Sending...' });
	
		                /* using the jquery's post(ajax) function and a lifesaver
		                function serialize() which gets all the data from the form
		                we submit it to send_email.php */
		                $.post("send_email.php", $("#contact_form").serialize(),function(result){
		                    //and after the ajax request ends we check the text returned
		                    if(result == 'sent'){
		                        //if the mail is sent remove the submit paragraph
		                         $('#cf_submit_p').remove();
		                        //and show the mail success div with fadeIn
		                        $('#mail_success').fadeIn(500);
		                    }else{
		                        //show the mail failed div
		                        $('#mail_fail').fadeIn(500);
		                        //reenable the submit button by removing attribute disabled and change the text back to Send The Message
		                        $('#send_message').removeAttr('disabled').attr('value', 'Send The Message');
		                    }
		                });
		            }
		        });
		    });
		</script>
	
	</body>
</html>