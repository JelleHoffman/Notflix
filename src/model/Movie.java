package model;

import java.util.ArrayList;

public class Movie {
	private int volgNummer;
	private int iMDBNummer;
	private String titel;
	private String datum;
	private int duur;
	private String regisseur;
	private String beschrijving;
	private ArrayList<Rating> ratings;
	
	public Movie() {
		
	}

	public Movie(int volgNummer, int iMDBNummer, String titel, String datum, int duur, String regisseur,
			String beschrijving) {
		
		ratings = new ArrayList<Rating>();
		
		this.volgNummer = volgNummer;
		this.iMDBNummer = iMDBNummer;
		this.titel = titel;
		this.datum = datum;
		this.duur = duur;
		this.regisseur = regisseur;
		this.beschrijving = beschrijving;
		
	}
	
	public void addRating(Rating rating) {
		ratings.add(rating);
	}
	
	public ArrayList<Rating> getRatings() {
		return ratings;
	}

	public int getVolgNummer() {
		return volgNummer;
	}

	public void setVolgNummer(int volgNummer) {
		this.volgNummer = volgNummer;
	}

	public int getiMDBNummer() {
		return iMDBNummer;
	}

	public void setiMDBNummer(int iMDBNummer) {
		this.iMDBNummer = iMDBNummer;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public int getDuur() {
		return duur;
	}

	public void setDuur(int duur) {
		this.duur = duur;
	}

	public String getRegisseur() {
		return regisseur;
	}

	public void setRegisseur(String regisseur) {
		this.regisseur = regisseur;
	}

	public String getBeschrijving() {
		return beschrijving;
	}

	public void setBeschrijving(String beschrijving) {
		this.beschrijving = beschrijving;
	}
	
	
}

