package fr.univnantes.encheres.serveur.impl;

import java.io.Serializable;

public class Article implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String titre;
	private int prixIni;
	
	public Article(int id, String titre, int prixIni) {
		super();
		this.id = id;
		this.titre = titre;
		this.prixIni = prixIni;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public int getPrixIni() {
		return prixIni;
	}

	public void setPrixIni(int prixIni) {
		this.prixIni = prixIni;
	}
	
	
	
}
