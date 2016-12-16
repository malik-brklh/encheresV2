package fr.univnantes.encheres.serveur.impl;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import fr.univnantes.encheres.client.IClient;
import fr.univnantes.encheres.serveur.IServeur;

public class Serveur extends UnicastRemoteObject implements IServeur, Serializable {

	List<IClient> clientsInscrits = new ArrayList<IClient>();
	List<IClient> clientsEnAttente = new ArrayList<IClient>();
	List<Article> articles = new ArrayList<Article>();
	private LanceurVente lanceurVente = new LanceurVente();
	
	enum etatVenteEnum {
		EnAttente, Encours, Fin
	};

	etatVenteEnum etatVente = etatVenteEnum.EnAttente;
	Vente venteActuelle;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws RemoteException {
		new Serveur();
	}

	public Serveur() throws RemoteException {

		LocateRegistry.createRegistry(port);

		try {
			Naming.bind(url, this);
			System.out.println("serveur lancé");
//			testNouvelleVente();
			lanceurVente.start();
			
		} catch (MalformedURLException | AlreadyBoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	synchronized public boolean demandeInscription(IClient c) throws RemoteException {
		System.out.println(c.getId() + " veut s'inscrire");
		for (IClient cc : clientsEnAttente) {
			if (c.getId().equals(cc.getId())) {
				return false;
			}
		}
		for (IClient cc : clientsInscrits) {
			if (c.getId().equals(cc.getId())) {
				return false;
			}
		}

		System.out.println(c.getId() + " est inscrit");
		this.clientsEnAttente.add(c);
		System.out.println("il y a " + this.clientsEnAttente.size() + " clients inscrit en attente");
		return true;

	}

	@Override
	synchronized public void enchirir(String id, int currentChrono, int nouveauPrix) {
		System.out.println("SERVEUR -- ENCHIRISSEMENT");
		System.out.println("client : " + id + " - offre = " + nouveauPrix + " a " + currentChrono);
		venteActuelle.majVente(id, nouveauPrix, currentChrono);
		venteActuelle.incNbEnchirissement();
		if (venteActuelle.getNbEnchirissement() == this.clientsInscrits.size()) {
			try {
				finVente();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else
			for (IClient c : clientsInscrits) {
				c.majVente(venteActuelle.getAcheteurActuel(), venteActuelle.getArticle().getTitre(),
						venteActuelle.getPrixGagnant());
			}
	}

	@Override
	public synchronized void tempsEcoule(String id) throws RemoteException {
		System.out.println(id + " : son temps a été écoulé");
		((Vente) this.venteActuelle).incNbEnchirissement();
		System.out.println("temps ecoulé " + ((Vente) venteActuelle).getNbEnchirissement() + " on terminé");
		if (((Vente) venteActuelle).getNbEnchirissement() == this.clientsInscrits.size()) {
			finVente();
		}
	}

	private void finVente() throws RemoteException {
		alertClients("Vente terminée, "+venteActuelle.getAcheteurActuel()+" remporte l'enchère avec la somme de "+venteActuelle.getPrixGagnant());
		this.etatVente = etatVenteEnum.Fin;
		for (IClient c : clientsInscrits) {
			c.finVente(venteActuelle.getAcheteurActuel(), venteActuelle.getArticle().getTitre(),
					venteActuelle.getPrixGagnant());
		}
		articles.remove(0);
		this.venteActuelle = null;
		this.etatVente = etatVenteEnum.EnAttente;
//		testNouvelleVente();
	}

	public synchronized void nouvelleVente() throws RemoteException {

		System.out.println("nouvelle vente");
		for (IClient client : clientsEnAttente) {
			clientsInscrits.add(client);
		}
		for (IClient client : clientsInscrits) {
			clientsEnAttente.remove(client);
		}
		if (articles.isEmpty()) {
			articles.add(new Article((int) (Math.random() * 1000), "titre " + (int) (Math.random() * 1000),
					(int) (Math.random() * 1000)));
		}
		venteActuelle = new Vente(null, articles.get(0), 0, 0);
		System.out.println("article a vendre : ");
		for (IClient c : clientsInscrits) {
			c.nouvelleVente(venteActuelle.getArticle().getTitre(), venteActuelle.getPrixGagnant());
		}

	}

//	private void testNouvelleVente() {
//		lanceurVente.start();
//	}

	private void alertClients(String t) throws RemoteException {
		for (IClient client : clientsEnAttente) {
			client.printInfo(t);
		}
		for (IClient client : clientsInscrits) {
			client.printInfo(t);
		}
	}

	private class LanceurVente extends Thread {
		@Override
		public void run() {
			while(true){
			try {
				while (!(etatVente == etatVenteEnum.EnAttente
						&& (clientsEnAttente.size() + clientsInscrits.size()) >= nbUserMin)) {
					sleep(1000);
				}
				alertClients("nouvelle vente dans 10 secondes");
				sleep(10000);
				etatVente = etatVenteEnum.Encours;
				nouvelleVente();
				sleep(130000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			}
		}
	}
}
