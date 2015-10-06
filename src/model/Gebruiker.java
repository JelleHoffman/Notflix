package model;

import java.util.Random;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.SerializableSerializer;

@XmlRootElement(name= "gebruiker")
//@JsonIgnoreProperties("internal")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Gebruiker {
	private String voornaam;
	private String tussenvoegsel;
	private String achternaam;
	private String wachtwoord;
	private String nickname;
	private String accessToken = "";

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
	
	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public String getTussenvoegsel() {
		return tussenvoegsel;
	}
	
	public void setTussenvoegsel(String tussenvoegsel) {
		this.tussenvoegsel = tussenvoegsel;
	}

	public String getAchternaam() {
		return achternaam;
	}
	
	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}
	
	
	@XmlTransient
	@JsonIgnore
	public String getWachtwoord() {
		return wachtwoord;
	}

	@JsonProperty("wachtwoord")
	@XmlElement
	@XmlAttribute
	public void setWachtwoord(String wachtwoord) {
		this.wachtwoord = wachtwoord;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@XmlTransient
	@JsonIgnore
	public String getAccessToken() {
		if (accessToken.equals("")){
			String mogelijkheden ="abcdefghijklmnopqrstuvwxyz0123456789";
			Random r = new Random();
			for (int i = 0;i<10;i++){
				accessToken += mogelijkheden.charAt(r.nextInt(mogelijkheden.length()));
			}
			
		}
		return accessToken;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
