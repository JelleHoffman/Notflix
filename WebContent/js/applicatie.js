/**
 *Yolo 
 */


$(document).ready(function() {
	var accessToken = localStorage.getItem("accessToken");
	if(accessToken==null){
		$("#moviesBtn").hide();
		$("#usersBtn").hide();
	}else{
		$("#usersBtn").show();
		$("#moviesBtn").hide();
		$("#username").hide();
		$("#password").hide();
		$("#signIn").hide();
	}
	getFilms();
	signIn();
	userBtnClick();
	moviesBtnClick();
	filmClick();
	logoutClick();
	registerClick();
	registerSubmitClick();
	
});


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
				userList();
				
				$("#usersBtn").show();
				$("#username").hide();
				$("#password").hide();
				$("#signIn").hide();
				
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
			alert(accessToken);
			
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
						
						if(average == 0){
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
									"<img id='filmPoster' class='col-md-10' alt='"+title.replace("'","-")+"'src="+poster+">"+
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
								
								
									
							},
							error:function(jqXHR,settings,error){
								alert("error in getFilms poster"+error);
							}
						});
						
						
					});
				},
				error:function(jqXHR,settings,error){
					if(error == "Unauthorized"){
						localStorage.clear();
						window.location.href = "index.html";
					}
					
				}
			});
		
}

function filmClick(){
	$(document).on("click",".filmFrame",function(){
		
		var imdb = $(this).attr("id");
		alert(imdb);
		
		var content = $("#filmdetail");
		content.empty();
		var accessToken = localStorage.getItem("accessToken");
		alert(accessToken);
		if(accessToken!=null){
			$("#mainContent").hide();
			$.ajax({ 
			    type: 'GET',
			    url: "./api/film/" + imdb, 
			    headers:{
			    	Accept:"application/json; charset=utf-8",
			    	"Authorization":accessToken},
			    success:function(movie){
						var title = movie.titel;
						var deleteRate;
						var array = movie.ratings;
						for(i=0;i<array.length;i++){
							var object = array[i].rating;
							alert("het lukt, delete");
						}
						
						var poster;
						var average = parseFloat(movie.average);

						if(average === NaN){
							average = "Zero Ratings";
						};
						
						
						$.ajax({
							type:"GET",
							url:("http://www.omdbapi.com/?i="+imdb),
							success:function(data){
								
								poster = data.Poster;
								
								
								
								$("#filmdetail").append(
									"<div class='filmDetail'>"+
									"<img id='poster' alt='"+title.replace("'","-")+"'src='"+poster+"'>"+
									"<h2 id='title'>Title: "+title+"</h2>"+
									"<h3 id='regiseur'>Director: "+movie.regisseur+"</h3>"+
									"<h4 id='time'>Minutes: "+movie.duur+"</h4>"+
									"<h4 id='releaseDate'>Release date: "+movie.datum+"</h4>"+
									"<h4 id='average'>Rating: "+movie.average+"</h4>"+
									"<p id='beschrijving'>Description: "+movie.beschrijving+"</p>"+
									"<form id='rateForm' class='form'>"+
									"<input id='rateBox' type='number'  class='form-control' placeholder='Rate this film, number has to be between 0.5 en 5'>"+
									"<button id='rateBtn' type='button' class='button btn btn-success'>Rate</button>"+
									"</form>"+
									"<div class='stars'>"+
						  			"<form action=''>"+
								    "<input class='star star-5' id='5-'"+movie.iMDBNummer+" type='radio' name='star'/>"+
								    "<label class='star star-5' for='5-'"+movie.iMDBNummer+"'></label>"+
								  	"<input class='star star-4' id='4-'"+movie.iMDBNummer+" type='radio' name='star'/>"+
								  	"<label class='star star-4' for='4-'"+movie.iMDBNummer+"'></label>"+
								  	"<input class='star star-3' id='3-'"+movie.iMDBNummer+" type='radio' name='star'/>"+
								    "<label class='star star-3' for='3-'"+movie.iMDBNummer+"'></label>"+
								  	"<input class='star star-2' id='2-'"+movie.iMDBNummer+" type='radio' name='star'/>"+
								  	"<label class='star star-2' for='2-'"+movie.iMDBNummer+"'></label>"+
								  	"<input class='star star-1' id='1-'"+movie.iMDBNummer+" type='radio' name='star'/>"+
								    "<label class='star star-1' for='1-'"+movie.iMDBNummer+"'></label>"+
						 			"</form>"+
									"</div>"+
									"</div>");
								
								rateClick(imdb);
								
							},
							error:function(jqXHR,settings,error){
								alert("error in get poster film"+error+settings);
							}
						});
			    },
					error:function(jqXHR,settings,error){
						alert("error in get specific film"+error+settings);
					}
			});
		}else{
			alert("you have to log in to see or rate a movie");
		}
		
	});
	  
}

function userBtnClick(){
	$("#usersBtn").click(function(){
		$("#moviesBtn").show();
		alert("ik ben in user btn click")
		
			var accessToken = localStorage.getItem("accessToken");
			if(accessToken!==null){
				window.location.href = "users.html";
			}else{
				alert("you have to log in to see this page")
			}
			
		});
}

function moviesBtnClick(){
	$("#moviesBtn").click(function(){
		$("#usersBtn").show();
		alert("ik ben in movie btn click");
		window.location.href = "index.html";
			
		});
}

function rateClick(imdb){
	$("#rateBtn").click(function(){
		var rating = $("#rateBox").val();
		alert(rating);
		var accessToken = localStorage.getItem("accessToken");
		alert(accessToken);
		alert(imdb);
		if(rating>=0.5&&rating<=5){
			$.ajax({
				type:"PUT",
				url:"./api/film/rate/" + imdb,
				headers:{
					Accept:"application/json; charset=utf-8",
			    	"Authorization":accessToken
				},
				data:{
					"rating":rating
				},
				success:function(data){
					alert("rating gelukt");
					getFilms();
					$("#mainContent").show();
					$("#filmdetail").hide();
				},
				error:function(jqXHR,settings,error){
					alert("error in pur rating film"+error+settings);
				}
			});
		}else {
			alert("rating has to between 0.5 en 5");
		}
	});
}

function logoutClick(){
	$("#signOut").click(function(){
		localStorage.clear();
		window.location.href = "index.html";
	});
}

function registerClick(){
	$("#register").click(function() {
		window.location.href = "register.html";
	});
}

function userList(){
			
			$("#usersBtn").hide();
			
			var content = $("#usersList");
			var accessToken = localStorage.getItem("accessToken");
			
			alert(accessToken);
			
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
		
}

function registerSubmitClick() {
	
	$("#registerSubmitBtn").click(function() {
		alert("sumbit is geclicked");
		
	var firstnameReg = $("#firstnameReg").val();
	var additionReg = $("#additionReg").val();
	var lastnameReg = $("#lastnameReg").val();
	var nicknameReg = $("#nicknameReg").val();
	var passwordReg = $("#passwordReg").val();
	
	if(firstnameReg != "" && lastnameReg != "" && nicknameReg != "" && passwordReg != "") {
	$.ajax({
		type:"POST",
		url: "./api/gebruikers/add-gebruiker",
			headers: {
				"Content-Type":"application/json;charset=utf-8",
				"Wachtwoord":passwordReg,
				"Voornaam":firstnameReg,
				"Tussenvoegsel":additionReg,
				"Achternaam":lastnameReg,
				"Nickname":nicknameReg,
					
			},
			
			
		success:function(data) {
			
			alert("succesvol geregistreerd");
			window.location.href = "index.html";
		
				
			
		},
		error:function(jqXHR,settings,error){
			alert("error in de register");
			alert(error);
		}
	});
	}else{
		alert("Onjuiste invoergegevens. Vul alle velden correct in!");
	}
	});
}

