package fr.univnantes.encheres.client.impl.ihm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

import fr.univnantes.encheres.client.impl.Client;

public class AuthIhm extends BaseIhm {

	public AuthIhm(Client clientImp) {
		super(clientImp);
		initialize();
		this.frame.setVisible(true);

	}

	private JFrame frame;
	private JTextField userNameIn;

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 408, 196);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLayeredPane layeredPane = new JLayeredPane();
		frame.getContentPane().add(layeredPane, BorderLayout.CENTER);

		JLabel userNameLabel = new JLabel("Nom d'utilisateur :");
		userNameLabel.setBounds(20, 56, 136, 14);
		layeredPane.add(userNameLabel);

		userNameIn = new JTextField();
		userNameIn.setBounds(189, 53, 159, 20);
		layeredPane.add(userNameIn);
		userNameIn.setColumns(10);

		JButton btnConnexion = new JButton("Connexion");
		btnConnexion.setBounds(132, 81, 127, 23);
		btnConnexion.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (!userNameIn.getText().isEmpty())
					getClientImp().connexion(userNameIn.getText());
			}
		});
		layeredPane.add(btnConnexion);
		
		JLabel lblBienvenueAcheteur = new JLabel("Bienvenue Acheteur");
		lblBienvenueAcheteur.setBounds(68, 11, 247, 14);
		layeredPane.add(lblBienvenueAcheteur);
	}

	@Override
	public void close() {
		this.frame.dispose();
	}
}
