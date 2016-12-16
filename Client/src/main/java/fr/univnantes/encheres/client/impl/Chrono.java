package fr.univnantes.encheres.client.impl;

import java.rmi.RemoteException;

import fr.univnantes.encheres.serveur.IServeur;

public class Chrono extends Thread {

	private int delay = IServeur.dureeChrono;
	private int precision = 100;
	private Client proprio;
	private boolean aEncheri = false;

	public Chrono(Client proprio) {
		super();
		this.proprio = proprio;
	}

	public void aEncheri() {
		this.aEncheri = true;
	}

	@Override
	public void run() {
		for (; delay > 0 && !aEncheri; delay -= precision)
			try {
				sleep(precision);
			} catch (InterruptedException e) {
				// e.printStackTrace();
			}
		if (!aEncheri)
			try {
				proprio.tempsEcoule();
			} catch (RemoteException e) {
			}
	}

	public int getCurrentChrono() {
		return delay;
	}
}
