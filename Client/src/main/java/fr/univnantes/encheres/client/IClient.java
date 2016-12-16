package fr.univnantes.encheres.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
	
	void majVente(String acheteurActuel, String titreArticle, int prixGagnant)throws RemoteException;
	String getId() throws RemoteException;
	void nouvelleVente(String titreArticle, int prix)throws RemoteException;
	void finVente(String acheteurActuel, String titreArticle, int prix)throws RemoteException;
	void printInfo(String t)throws RemoteException;
	
}
