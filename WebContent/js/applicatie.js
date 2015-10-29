/**
 *Yolo 
 */


$(document).ready(function() {
	var accessToken = localStorage.getItem("accessToken");
	if(accessToken==null){
		$("#moviesBtn").hide();
		$("#usersBtn").hide();
		$("#signOut").hide();
	}else{
		$("#usersBtn").show();
		$("#moviesBtn").show();
		$("#signOut").show();
		$("#username").hide();
		$("#password").hide();
		$("#signIn").hide();
		$("#register").hide();
	}
	
	signIn();
	getFilms();
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
				localStorage.setItem("username",username);
				$("#usersBtn").show();
				$("#moviesBtn").show();
				$("#signOut").show();
				$("#username").hide();
				$("#password").hide();
				$("#signIn").hide();
				$("#register").hide();
				
				getFilms();
				
				
				
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
					$("#moviesBtn").hide();
					$.each(data,function(index,value){
						
						$("#filmdetail").hide();
						
						var title = value.titel;
						var imdbId = value.iMDBNummer;
						var poster;
						var average = parseFloat(value.average);
						
						if(average === 0){
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
									"<h4 id='average'>Rating: "+average+"</h4>"+
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
		
		debugger;
		
		var imdb = $(this).attr("id");
		
		var content = $("#filmdetail");
		
		var accessToken = localStorage.getItem("accessToken");
		
		if(accessToken!=null){
			$("#mainContent").hide();
			$("#moviesBtn").show();
			$("#filmdetail").show();
			
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
						
						var poster;
						var average = parseFloat(movie.average);

						if(average === 0){
							average = "Zero Ratings";
						};
						
						
						$.ajax({
							type:"GET",
							url:("http://www.omdbapi.com/?i="+imdb),
							success:function(data){
								
								poster = data.Poster;
								
								content.empty();
								
								content.append(
									"<div class='filmDetail'>"+
									"<img id='poster' alt='"+title.replace("'","-")+"'src='"+poster+"'>"+
									"<h2 id='title'>Title: "+title+"</h2>"+
									"<h3 id='regiseur'>Director: "+movie.regisseur+"</h3>"+
									"<h4 id='time'>Minutes: "+movie.duur+"</h4>"+
									"<h4 id='releaseDate'>Release date: "+movie.datum+"</h4>"+
									"<h4 id='average'>Rating: "+average+"</h4>"+
									"<p id='beschrijving'>Description: "+movie.beschrijving+"</p>"+
									"<form id='rateForm' class='form'>"+
									"<input id='rateBox' type='number'  class='form-control' placeholder='Rate this film, number has to be between 0.5 en 5'>"+
									"<button id='rateBtn' type='button' class='button btn btn-success'>Rate</button>"+
									"<button id='deleteButton' type='button' class='button btn btn-success'>Delete your own rating</button>"+
									"</form>"+
									"</div>");
								
								$("#deleteButton").hide();
								if(array.length > 0){
									
									for(var i=0;i<array.length;i++){
										
										var object = array[i];
			
										var username = localStorage.getItem("username");
										if(username == object.gebruiker.nickname){
											
											$("#deleteButton").show();
											deleteRatingClick(imdb);
											
										}
										
										
									}
								};
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

function deleteRatingClick(imdb){
	$("#deleteButton").click(function(){
		
		
		var accessToken = localStorage.getItem("accessToken");
		
		$.ajax({
			type:"DELETE",
			url:"./api/film/rate/"+imdb,
			headers:{
				"Authorization":accessToken
			},
			success:function(data){
				$("#mainContent").show();
				$("#filmdetail").hide();
				getFilms();
				
			},
			error:function(jqXHR,settings,error){
				alert("error in delete film "+error)
			}
		});
	});
}

function userBtnClick(){
	$("#usersBtn").click(function(){
		$("#moviesBtn").show();
		
		
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
		
		window.location.href = "index.html";
			
		});
}

function rateClick(imdb){
	$("#rateBtn").click(function(){
		var rating = $("#rateBox").val();
		
		var accessToken = localStorage.getItem("accessToken");
		
		
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
					
					getFilms();
					filmClick();
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
		localStorage.clear();
		window.location.href = "register.html";
	});
}

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
			$("#moviesBtn").show();
			$("#signOut").show();
			$("#usersBtn").hide();
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

function registerSubmitClick() {
	
	$("#registerSubmitBtn").click(function() {
		
		
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

