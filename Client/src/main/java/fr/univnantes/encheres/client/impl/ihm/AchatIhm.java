package fr.univnantes.encheres.client.impl.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import fr.univnantes.encheres.client.impl.Client;

public class AchatIhm extends BaseIhm {

	private JFrame frame;
	private JLayeredPane layeredPane;
	private JButton btnEncherir;
	private JTextField margeField;
	private JTextField titreField;
	private JTextField prixField;
	private JTextField gagnantField;
	private JTextField minuteField;
	private JTextField secField;
	private JLabel lblError;
	private JTextPane infoPane;
	private JScrollPane scrollPane;
	private JLabel lblBravoVoustes;

	public AchatIhm(Client client) {
		super(client);
		initialize();
		this.frame.setVisible(true);
		(new SyncChrono()).start();
	}

	public void setChrono(int val) {
		// val en seconde
		minuteField.setText("" + ((int) (val / 60)));
		secField.setText("" + (val % 60));

	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 640, 420);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(getClientImp().getId());

		layeredPane = new JLayeredPane();
		frame.getContentPane().add(layeredPane, BorderLayout.CENTER);

		btnEncherir = new JButton("Enchérir");
		btnEncherir.setBounds(167, 271, 99, 23);
		layeredPane.add(btnEncherir);

		JLabel lblArticle = new JLabel("Article : ");
		lblArticle.setBounds(20, 35, 99, 14);
		layeredPane.add(lblArticle);

		JLabel lblPrixInitial = new JLabel("prix actuel :");
		lblPrixInitial.setBounds(20, 60, 99, 14);
		layeredPane.add(lblPrixInitial);

		margeField = new JTextField();
		margeField.setBounds(20, 272, 137, 20);
		layeredPane.add(margeField);
		margeField.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(399, 184, 215, 186);
		layeredPane.add(scrollPane);

		infoPane = new JTextPane();
		scrollPane.setViewportView(infoPane);
		infoPane.setEditable(false);

		JLabel lblInformation = new JLabel("Information :");
		lblInformation.setBounds(399, 159, 115, 14);
		layeredPane.add(lblInformation);

		JLabel lblChrono = new JLabel("Chrono : ");
		lblChrono.setBounds(379, 21, 46, 14);
		layeredPane.add(lblChrono);

		lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setBounds(20, 176, 346, 14);
		layeredPane.add(lblError);

		JLabel lblGagnant = new JLabel("Gagnant :");
		lblGagnant.setBounds(20, 85, 99, 14);
		layeredPane.add(lblGagnant);

		titreField = new JTextField();
		titreField.setEditable(false);
		titreField.setBounds(136, 32, 179, 20);
		layeredPane.add(titreField);
		titreField.setColumns(10);

		prixField = new JTextField();
		prixField.setEditable(false);
		prixField.setBounds(136, 57, 179, 20);
		layeredPane.add(prixField);
		prixField.setColumns(10);

		gagnantField = new JTextField();
		gagnantField.setEditable(false);
		gagnantField.setColumns(10);
		gagnantField.setBounds(136, 82, 179, 20);
		layeredPane.add(gagnantField);

		JLabel lblSommeAjouter = new JLabel("Somme à ajouter :");
		lblSommeAjouter.setBounds(20, 247, 180, 14);
		layeredPane.add(lblSommeAjouter);

		JLabel label = new JLabel(":");
		label.setBounds(490, 21, 14, 14);
		layeredPane.add(label);

		minuteField = new JTextField();
		minuteField.setForeground(Color.BLUE);
		minuteField.setEditable(false);
		minuteField.setBounds(447, 18, 33, 20);
		layeredPane.add(minuteField);
		minuteField.setColumns(10);

		secField = new JTextField();
		secField.setEditable(false);
		secField.setForeground(Color.BLUE);
		secField.setBounds(500, 18, 33, 20);
		layeredPane.add(secField);
		secField.setColumns(10);

		lblBravoVoustes = new JLabel("BRAVO, Vous êtes le gagnant !");
		lblBravoVoustes.setEnabled(true);
		lblBravoVoustes.setForeground(new Color(50, 205, 50));
		lblBravoVoustes.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblBravoVoustes.setBounds(20, 135, 296, 52);

		btnEncherir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int marge = Integer.parseInt(margeField.getText());
					if (marge > 0) {
						getClientImp().encherir(Integer.parseInt(prixField.getText()) + marge);
						btnEncherir.setEnabled(false);
						lblError.setText("");
					} else
						lblError.setText("Donnez une somme positive");
				} catch (Exception exception) {
					exception.printStackTrace();
					margeField.setText("");
					lblError.setText("Donnez une somme sous forme d'un entier");
				}
			}
		});

	}

	public void printConsole(String t) {
		infoPane.setText(infoPane.getText() + "\n-------\n" + t);

	}

	@Override
	public void close() {
		this.frame.dispose();
	}

	public void setArticleDetail(String acheteurActuel, String titreArticle, int prix) {
		btnEncherir.setEnabled(true);
		titreField.setText(titreArticle);
		gagnantField.setText(acheteurActuel);
		prixField.setText("" + prix);
		lblError.setText("");
		layeredPane.remove(lblBravoVoustes);
	}

	/*****************************************************/

	private class SyncChrono extends Thread {
		@Override
		public void run() {
			while (true) {
				if (titreField.getText().equals("") && gagnantField.getText().equals("")
						&& prixField.getText().equals("")) {
					getClientImp().majVente();
				}
				setChrono(getClientImp().getChrono());
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void gagnant(String acheteurActuel) {
		if (acheteurActuel.equals(getClientImp().getId())) {
			layeredPane.add(lblBravoVoustes);
		} else {
			layeredPane.remove(lblBravoVoustes);
		}
	}
}
