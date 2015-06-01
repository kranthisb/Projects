$(document).ready(
    function(){
    		function checkLoginState() {
			  FB.login(function(response) {
			   console.log(response);
				statusChangeCallback(response);
			 });
		  	}
		  
			function statusChangeCallback(response) {
				console.log('statusChangeCallback');
				console.log(response);
				if (response.status === 'connected') {
				console.log("connected..");
				  console.log(response.authResponse.accessToken);
				  
	  				 $( "#mainlogin" ).show();
				  testAPI();
				} else if (response.status === 'not_authorized') {
					console.log("Not authorized");
				   $( "#mainlogin" ).show();
				} else {
				  document.getElementById('status').innerHTML = 'Please log ' +
					'into Facebook.';
				}
			}

		  window.fbAsyncInit = function() {
			  FB.init({
				  appId      : '1622503621340404',
				  cookie     : true,  // enable cookies to allow the server to access 
									 // the session
				  xfbml      : true,
				  version    : 'v2.3'
				});

			  FB.getLoginStatus(function(response) {
				statusChangeCallback(response);
			  });

		  };

		 (function(d, s, id) {
			var js, fjs = d.getElementsByTagName(s)[0];
			if (d.getElementById(id)) return;
			js = d.createElement(s); js.id = id;
			js.src = "//connect.facebook.net/en_US/sdk.js";
			fjs.parentNode.insertBefore(js, fjs);
		  }(document, 'script', 'facebook-jssdk'));
  

		  function testAPI() {
			console.log('Welcome!  Fetching your information.... ');
			// FB.api('/me', function(response) {
		//       console.log('Successful login for: ' + JSON.stringify(response));
		//     
		//       document.getElementById('status').innerHTML =
		//         'Thanks for logging in, ' + response.name + '!';
		//     });
		//     
		//     FB.api('/me/permissions', function(response) {
		//       console.log('Permission ' + JSON.stringify(response));
		//     
		//       document.getElementById('permissions').innerHTML =
		//         response.name ;
		//     });
		//     
		//     FB.api('/me/friends', function(response) {
		//       console.log('Friends ' + JSON.stringify(response));
		//     
		//     });
		// 
		// 	FB.api('/me/invitable_friends', function(response) {
		//       console.log('invitable_friends ' + JSON.stringify(response));
		//     
		//     });
		//     
		//     document.getElementById('content').innerHTML='<object type="text/html" data="shopcart/shopcartindex.html" ></object>';        
		  }
  
    });