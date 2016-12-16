package fr.univnantes.encheres.serveur.impl;

public class Vente{

	/**
	 * 
	 */
	private String acheteurActuelId;
	private Article article;
	private int prixGagnant;
	private double tempsChrono;
	private int nbEnchirissement=0;
//	
//	public Vente() {
//		super();
//		// TODO Auto-generated constructor stub
//	}

	public Vente(String acheteurActuelId, Article article, int prixGagnant, double tempsChrono) {
		super();
		this.acheteurActuelId = acheteurActuelId;
		this.article = article;
		this.prixGagnant = prixGagnant;
		this.tempsChrono = tempsChrono;
	}

	public void majVente(String acheteur, int prixPropose, double chrono) {
		if ( prixPropose > prixGagnant
				|| (prixPropose == prixGagnant && chrono > tempsChrono)) {
			acheteurActuelId = acheteur;
			prixGagnant = prixPropose;
			tempsChrono = chrono;
		}
	}

	
	public String getAcheteurActuel() {
		return acheteurActuelId;
	}

	
	public void setAcheteurActuel(String acheteurActuelId) {
		this.acheteurActuelId = acheteurActuelId;
	}

	
	public Article getArticle() {
		return article;
	}

	
	public void setArticle(Article article) {
		this.article = article;
	}

	
	public int getPrixGagnant() {
		return prixGagnant;
	}

	
	public void setPrixGagnant(int prixGagnant) {
		this.prixGagnant = prixGagnant;
	}

	
	public double getTempsChrono() {
		return tempsChrono;
	}

	
	public void setTempsChrono(double tempsChrono) {
		this.tempsChrono = tempsChrono;
	}

	public void incNbEnchirissement(){
		nbEnchirissement++;
	}
	public int getNbEnchirissement(){
		return this.nbEnchirissement;
	}
}
