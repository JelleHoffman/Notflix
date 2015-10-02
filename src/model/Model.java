package model;

import java.util.ArrayList;

public class Model {
	private ArrayList<Gebruiker> gebruikers = new ArrayList<>();

	public ArrayList<Gebruiker> getGebruikers() {
		return gebruikers;
	}

	public void addGebruiker(Gebruiker gebruiker) {
		gebruikers.add(gebruiker);
	}
	
	
}
