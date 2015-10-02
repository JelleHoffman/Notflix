package model;

public class Rating {
	private double rating;
	private Gebruiker gebruiker;
	
	public Rating() {
		
	}
	
	public Rating(double rating, Gebruiker gebruiker) {
		this.rating = rating;
		this.gebruiker = gebruiker;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Gebruiker getGebruiker() {
		return gebruiker;
	}

	public void setGebruiker(Gebruiker gebruiker) {
		this.gebruiker = gebruiker;
	}
}
