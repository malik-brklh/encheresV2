package fr.univnantes.encheres.client.impl;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import fr.univnantes.encheres.client.IClient;
import fr.univnantes.encheres.client.impl.ihm.AchatIhm;
import fr.univnantes.encheres.client.impl.ihm.AuthIhm;
import fr.univnantes.encheres.client.impl.ihm.BaseIhm;
import fr.univnantes.encheres.client.impl.ihm.ErrIhm;
import fr.univnantes.encheres.serveur.IServeur;

public class Client extends UnicastRemoteObject /* JFrame */ implements IClient, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Chrono chrono;
	private IServeur serveur;
	private String id;
	private BaseIhm currentIhm;
	private boolean aEncheri = false;

	private String titre, acheteur;
	private int prix;

	public static void main(String[] args) throws RemoteException {
		new Client();
	}

	public Client() throws RemoteException {
		currentIhm = new AuthIhm(this);
		chrono = new Chrono(this);

	}

	private void printConsole(String t) {
		if (currentIhm instanceof AchatIhm) {
			((AchatIhm) currentIhm).printConsole(t);
		} else {
			System.out.println(t);
		}
	}

	public void connexion() {
		boolean inscrit = false;
		printConsole(this.getId() + " se connecte ...");
		try {
			serveur = (IServeur) Naming.lookup(IServeur.url);
			printConsole("Connexion effectué, demande d'inscription...");
			inscrit = serveur.demandeInscription(this);
			System.out.println("inscrit = " + inscrit);
			if (!inscrit) {
				new ErrIhm("Nom d'utilisateur déja pris");
			}
			if (inscrit) {
				currentIhm.close();
				currentIhm = new AchatIhm(this);
			}
		} catch (MalformedURLException | NotBoundException | RemoteException e) {
			e.printStackTrace();
			new ErrIhm("Connexion échoué");
		}

	}

	public void encherir(int nouveauPrix) throws RemoteException {
		if (!aEncheri) {
			chrono.aEncheri();
			printConsole("Votre proposition = " + nouveauPrix);
			serveur.enchirir(getId(), chrono.getCurrentChrono(), nouveauPrix);
			aEncheri = true;
		}

	}

	public void tempsEcoule() throws RemoteException {
		printConsole(id + " : temps ecoule");
		serveur.tempsEcoule(id);
	}

	@Override
	public void nouvelleVente(String titreArticle, int prix) {
		aEncheri = false;
		majVente("Aucun", titreArticle, prix);
		printConsole("client " + id + "\t*** NOUVELLE VENTE RECUE****");
		demarreChrono();
	}

	public void demarreChrono() {
		this.chrono = new Chrono(this);
		this.chrono.start();

	}

	@Override
	public void majVente(String acheteurActuel, String titreArticle, int prix) {
		this.titre = titreArticle;
		this.prix = prix;
		this.acheteur = acheteurActuel;
		if (currentIhm instanceof AchatIhm) {
			((AchatIhm) currentIhm).setArticleDetail(acheteurActuel, titreArticle, prix);
		}
	}

	/**
	 * méthode appelé par l'ihm achat pour mettre à jour les info sur l'ihm en
	 * cas de problème
	 */
	public void majVente() {
		if (currentIhm instanceof AchatIhm) {
			((AchatIhm) currentIhm).setArticleDetail(acheteur, titre, prix);
		}
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void finVente(String acheteurActuel, String titreArticle, int prix) {
		majVente(acheteurActuel, titreArticle, prix);
		if (acheteurActuel == null || acheteurActuel.equals("")) {
			printConsole("PERSONNE N'A ACHETE CET ARTICLE");
		} else if (acheteurActuel.equals(id)) {
			printConsole("BRAVO VOUS AVEZ GAGNE L'ENCHERE");
			((AchatIhm) currentIhm).gagnant(acheteurActuel);
		} else
			printConsole("Dommage pour vous, " + acheteurActuel + " a remporte l'enchere");
		printConsole("Article : " + titreArticle + "\tPrix : " + prix);

	}

	private void setId(String id) {
		this.id = id;

	}

	public void connexion(String text) {
		setId(text);
		connexion();

	}

	public int getChrono() {
		return chrono == null ? 0 : ((int) this.chrono.getCurrentChrono() / 1000);
	}

	@Override
	public void printInfo(String t) {
		printConsole("---MESSAGE FROM SERVER---\n" + t + "\n--------------");
	}
}
