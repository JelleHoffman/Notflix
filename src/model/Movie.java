package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement(name = "movie")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Movie {
	@JsonIgnore private int volgNummer;
	private String iMDBNummer;
	private String titel;
	private String datum;
	private int duur;
	private String regisseur;
	private String beschrijving;
	private ArrayList<Rating> ratings;
	private double average;
	
	public Movie() {
		
	}

	public Movie(int volgNummer, String iMDBNummer, String titel, String datum, int duur, String regisseur,
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
	
	@XmlTransient
	public int getVolgNummer() {
		return volgNummer;
	}

	public void setVolgNummer(int volgNummer) {
		this.volgNummer = volgNummer;
	}

	public String getiMDBNummer() {
		return iMDBNummer;
	}

	public void setiMDBNummer(String iMDBNummer) {
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
	
	public double getAverage(){
		double total = 0;
		for (Rating r:ratings){
			total += r.getRating();
		}
		average = (total/(double)ratings.size());
		return average;
	}
	public void setAverage(double average){
		this.average=average;
	}

	public boolean deleteRating(Rating r) {
		return ratings.remove(r);
		
	}

	
	
}

