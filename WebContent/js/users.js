/**
 * 
 */

function userList(){
	$(document).ready(function(){
			var content = $("#usersList");
			var accessToken = localStorage.getItem("accessToken");
			
			
			$.ajax({
				type:"GET",
				url:"./api/gebruikers",
				headers:{
					Accept:"application/json; charset=utf-8",
					"Authorization":accessToken
				},
				success:function(data){
					$.each(data,function(index,value){
						var tussenvoegsel = value.tussenvoegsel;
						if(tussenvoegsel == "" || tussenvoegsel == null){
							content.append(
									"<div class='user'>"+
									"<h1>"+value.nickname+"</h1>"+
									"<h2>"+value.voornaam+" "+value.achternaam+"</h2>"+
									"</div>");	
						}else{
							content.append(
									"<div class='user'>"+
									"<h1>"+value.nickname+"</h1>"+
									"<h2>"+value.voornaam+" "+value.tussenvoegsel+" "+value.achternaam+"</h2>"+
									"</div>");	
						}
							
							
						
						
					});
				},
				error:function(jqXHR,settings,error){
					alert("error in getUsers "+error);
				}
			});
		});
}