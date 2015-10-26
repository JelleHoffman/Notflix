/**
 *Yolo 
 */

function singIn(){
	$(document).ready(function() {
		$(".button").submit(function(){
			 alert("gelukt");
			});
	});
}

function getFilms(){
	$(document).ready(function(){
			var content = $("#mainContent");
			var accessToken = localStorage.getItem("accessToken");
			var apiUrl;
			var urlHeaders;
			
			alert(accessToken);
			
			if(accessToken==null){
				alert("in de if");
				apiUrl ="./api/film/ratings";
				urlHeaders = {
					Accept:"application/json; charset=utf-8",
				};
			}else{
				alert("in de else");
				apiUrl ="./api/film";
				urlHeaders = {
					Accept:"application/json; charset=utf-8",
					Authorization:accessToken,
				};
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
									"<div class='filmFrame col-md-3'>"+
									"<img id='filmPoster' class='col-md-10' alt='"+title+"'src="+poster+">"+
									
									"<h2 id='title'>"+shortTitle+"</h2>"+
									"<h3 id='regiseur'>"+value.regisseur+"</h3>"+
									"<h4 id='time'>Minutes: "+value.duur+"</h4>"+
									"<h4 id='releaseDate'>Release date: "+value.datum+"</h4>"+
									"<h4 id='average'>Rating: "+value.average+"</h4>"+
									"<div class='stars'>"+
						  			"<form action=''>"+
								    "<input class='star star-5' id='star-5' type='radio' name='star'/>"+
								    "<label class='star star-5' for='star-5'></label>"+
								  	"<input class='star star-4' id='star-4' type='radio' name='star'/>"+
							    "<label class='star star-4' for='star-4'></label>"+
							    "<input class='star star-3' id='star-3' type='radio' name='star'/>"+
								    "<label class='star star-3' for='star-3'></label>"+
								  	"<input class='star star-2' id='star-2' type='radio' name='star'/>"+
							    "<label class='star star-2' for='star-2'></label>"+
							    "<input class='star star-1' id='star-1' type='radio' name='star'/>"+
								    "<label class='star star-1' for='star-1'></label>"+
						 			"</form>"+
									"</div>"+
									"</div>");
								
								//rating meegeven
								//var rating = value.average;
								//if(value>==1){
									
								//}
									
							},
							error:function(jqXHR,settings,error){
								alert(error);
							}
						});
						
						
					});
				},
				error:function(jqXHR,settings,error){
					alert("error in getFilms"+error+settings);
				}
			});
		});
}
