package fr.univnantes.encheres.serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;

import fr.univnantes.encheres.client.IClient;

public interface IServeur extends Remote {

	int port = 8088;
	String url = "//localhost:" + port + "/enchere";
	int dureeChrono = 120000; // durée du chrono des client en millisecondes

	/*
	 * méthodes appelées par le client via RMI
	 */
	boolean demandeInscription(IClient c) throws RemoteException;

	void tempsEcoule(String id) throws RemoteException;

	void enchirir(String id, int currentChrono, int nouveauPrix) throws RemoteException;

}
