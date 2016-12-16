package fr.univnantes.encheres.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
	
	void majVente(String acheteurActuel, String titreArticle, int prixGagnant);
	String getId() throws RemoteException;
	void nouvelleVente(String titreArticle, int prix);
	void finVente(String acheteurActuel, String titreArticle, int prix);
	void printInfo(String t)throws RemoteException;
}
