$(document).ready(function(){
		$.ajax({
			type: "GET",
			url: "http://localhost:8080/CatalogService/all",
			xhr: function() {  // Custom XMLHttpRequest
				var myXhr = $.ajaxSettings.xhr();
				myXhr.upload;
				return myXhr;
			 },
			cache: false,
			contentType: false,
			processData: false
		
		}).done(function(response) {
			$(".lineitemslist").css("visibility", "visible");
			for (var i = 0; i < response.length; i++) {
				drawRow(response[i]);
			 } 
		}); 
	
	$('#submitbutton').click(function() { 
		var selectedCategory = '';
		var selectedBrand = '';
		$('input[name="typeCB"]:checked').each(function() {
//   		 alert($(this).val());
		});
		$('input[name="categoryCB"]:checked').each(function() {
	  		selectedCategory = $(this).val();
		});
		$('input[name="brandCB"]:checked').each(function() {
			selectedBrand = $(this).val();
		});
		
		$.ajax({
			type: "GET",
			url: "http://localhost:8080/CatalogService/catalog/"+selectedCategory+"/"+selectedBrand,
			xhr: function() {  // Custom XMLHttpRequest
				var myXhr = $.ajaxSettings.xhr();
				myXhr.upload;
				return myXhr;
			 },
			//Options to tell jQuery not to process data or worry about content-type.
			cache: false,
			contentType: false,
			processData: false
		
		}).done(function(response) {
			console.log(response);
			$(".lineitemslist").css("visibility", "visible");
			$(".lineitemslist").empty();
			for (var i = 0; i < response.length; i++) {
				drawRow(response[i]);
			 }  
		});
		
	});
		
	});	

    
    
    function drawRow(rowData) {
	   var row = $("<div class=\"img\">");
		$(".lineitemslist").append(row);
		row.append($("<a href=\""+rowData.affiliate_url+"\"> <img src="+ rowData.image_url+ " width=\"110\" height=\"90\"> </a> <div  class=\"keyword\"><p>"+rowData.keyword+"</p></div> "));
	}
	
	
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
				  loadFriendsDiv();
				} else if (response.status === 'not_authorized') {
					console.log("Not authorized");
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
  

		  function loadFriendsDiv() {
			 FB.api('/me/friends', function(response) {
		       console.log('Friends ' + JSON.stringify(response));
		       for (var i = 0; i < response.data.length; i++) {
				//drawRow(response.data[i].name);
				
				$(".friendsdiv").append(response.data[i].name);
				
			 }  
		    });
		        
		  }
    
	
	
	
	
	
	