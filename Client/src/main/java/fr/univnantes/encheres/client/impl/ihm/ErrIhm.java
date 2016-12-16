package fr.univnantes.encheres.client.impl.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextArea;
import java.awt.SystemColor;

public class ErrIhm {

	private JFrame frame;
	private JTextArea textArea;
	public ErrIhm(String err) {
		initialize();
		textArea.setText(err);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 442, 292);
		JLayeredPane layeredPane = new JLayeredPane();
		frame.getContentPane().add(layeredPane, BorderLayout.CENTER);
		frame.setVisible(true);
		
		JLabel lblErreur = new JLabel("ERREUR :");
		lblErreur.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblErreur.setForeground(Color.RED);
		lblErreur.setBounds(187, 11, 78, 27);
		layeredPane.add(lblErreur);
		
		textArea = new JTextArea();
		textArea.setBackground(SystemColor.menu);
		textArea.setEditable(false);
		textArea.setForeground(Color.RED);
		textArea.setBounds(53, 61, 326, 159);
		layeredPane.add(textArea);
	}
}
