/**
 *Yolo 
 */

function documentReady() {
	$(document).ready(function() {
		$("#moviesBtn").hide();
		$("#usersBtn").hide();
		getFilms();
		signIn();
		filmClick();
		userBtnClick();
	});
}

function singIn(){
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

function getFilms(){

			var content = $("#mainContent");
			var accessToken = localStorage.getItem("accessToken");
			var apiUrl;
			var urlHeaders;
			
			content.empty();
			
			
			if(accessToken==null){
				apiUrl ="./api/film/ratings";
				urlHeaders = {Accept:"application/json; charset=utf-8"};
			}else{
				apiUrl ="./api/film";
				urlHeaders = {Accept:"application/json; charset=utf-8","Authorization":accessToken};
			};
			
			
			$.ajax({
				type:"GET",
				url:apiUrl,
				headers:urlHeaders,
				 
				success:function(data){
					$.each(data,function(index,value){
						var title = value.titel;
						var imdbId = value.iMDBNummer;
						var poster;
						var average = parseFloat(value.average);
	
						if(average === NaN){
							average = "Zero Ratings";
						};
						
						$.ajax({
							type:"GET",
							url:("http://www.omdbapi.com/?i="+imdbId),
							success:function(data){
								
								poster = data.Poster;
								
								if(title.length>12){
									var shortTitle = title.substr(0,12)+"...";
								
								}else{
									var shortTitle = title;
								}
								//film object toevoegen
								
								content.append(
									"<div id='"+value.iMDBNummer+"'class='filmFrame col-md-3'>"+
									"<img id='filmPoster' class='col-md-10' alt='"+title+"'src="+poster+">"+
									"<h2 id='title'>"+shortTitle+"</h2>"+
									"<h3 id='regiseur'>"+value.regisseur+"</h3>"+
									"<h4 id='time'>Minutes: "+value.duur+"</h4>"+
									"<h4 id='releaseDate'>Release date: "+value.datum+"</h4>"+
									"<h4 id='average'>Rating: "+value.average+"</h4>"+
									"<div class='stars'>"+
						  			"<form action=''>"+
								    "<input class='star star-5' id='5-'"+value.iMDBNummer+" type='radio' name='star'/>"+
								    "<label class='star star-5' for='5-'"+value.iMDBNummer+"'></label>"+
								  	"<input class='star star-4' id='4-'"+value.iMDBNummer+" type='radio' name='star'/>"+
								  	"<label class='star star-4' for='4-'"+value.iMDBNummer+"'></label>"+
								  	"<input class='star star-3' id='3-'"+value.iMDBNummer+" type='radio' name='star'/>"+
								    "<label class='star star-3' for='3-'"+value.iMDBNummer+"'></label>"+
								  	"<input class='star star-2' id='2-'"+value.iMDBNummer+" type='radio' name='star'/>"+
								  	"<label class='star star-2' for='2-'"+value.iMDBNummer+"'></label>"+
								  	"<input class='star star-1' id='1-'"+value.iMDBNummer+" type='radio' name='star'/>"+
								    "<label class='star star-1' for='1-'"+value.iMDBNummer+"'></label>"+
						 			"</form>"+
									"</div>"+
									"</div>");
								
								//rating meegeven
								//var rating = value.average;
								//if(value>==1){
									
								//}
									
							},
							error:function(jqXHR,settings,error){
								alert("error in getFilms poster"+error);
							}
						});
						
						
					});
				},
				error:function(jqXHR,settings,error){
					alert("error in getFilms"+error+settings);
				}
			});
		
}

function filmClick(){
	
		var imdb = $(this).attr("id");
		
		alert(imdb);
	
}

function userBtnClick(){
	$("#usersBtn").click(function(){
			
			var accessToken = localStorage.getItem("accessToken");
			if(accessToken!==null){
				window.location.href = "users.html";
			}else{
				alert("you have to log in to see this page")
			}
			
		});
}

