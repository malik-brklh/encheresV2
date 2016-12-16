package fr.univnantes.encheres.client.impl.ihm;

import fr.univnantes.encheres.client.impl.Client;

public abstract class BaseIhm {

	private Client clientImp;

	public Client getClientImp() {
		return clientImp;
	}

	public void setClientImp(Client clientImp) {
		this.clientImp = clientImp;
	}

	public BaseIhm(Client clientImp) {
		super();
		this.clientImp = clientImp;
	}

	public abstract void close();
	
}
