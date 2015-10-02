package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ser.std.SerializableSerializer;

@XmlRootElement(name= "gebruiker")
@JsonIgnoreProperties("internal")
public class Gebruiker {
	private String voornaam;
	private String tussenvoegsel;
	private String achternaam;
	private String wachtwoord;
	private String nickname;
	
	public Gebruiker() {
		
	}
	
	public Gebruiker(String voornaam, String tussenvoegsel, String achternaam, String wachtwoord, String nickname) {
		this.voornaam = voornaam;
		this.tussenvoegsel = tussenvoegsel;
		this.achternaam = achternaam;
		this.wachtwoord = wachtwoord;
		this.nickname = nickname;
	}

	public String getVoornaam() {
		return voornaam;
	}
	
	@XmlElement 
	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public String getTussenvoegsel() {
		return tussenvoegsel;
	}
	
	@XmlElement 
	public void setTussenvoegsel(String tussenvoegsel) {
		this.tussenvoegsel = tussenvoegsel;
	}

	public String getAchternaam() {
		return achternaam;
	}

	@XmlElement 
	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}
	
	@JsonIgnore
	@XmlTransient
	public String getWachtwoord() {
		return wachtwoord;
	}

	
	public void setWachtwoord(String wachtwoord) {
		this.wachtwoord = wachtwoord;
	}

	public String getNickname() {
		return nickname;
	}

	@XmlElement 
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
}
