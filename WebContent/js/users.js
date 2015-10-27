/**
 * 
 */
function documentReady(){
	$(document).ready(function(){
		signIn();
		userList();
	});
}


function signIn(){
	$("#signIn").click(function(){
		var username = $("#username").val();
		var password = $("#password").val();
		
		$.ajax({
			type:"GET",
			url:"./api/gebruikers/get-accesstoken",
			headers:{
				"Nickname":username,
				"Wachtwoord":password
			},
			success:function(data){
				var accessToken = data;
				localStorage.setItem("accessToken", accessToken);          							
				getFilms();
				$("#usersBtn").show();
				$("#moviesBtn").show();
				$("#username").hide();
				$("#password").hide();
				
			},
			error:function(jqXHR,settings,error){
				alert("error in de sign in")
				alert(error);            						
			}
		});
		});
	
}