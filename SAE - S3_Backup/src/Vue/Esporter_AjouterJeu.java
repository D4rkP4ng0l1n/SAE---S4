package Vue;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import Controleur.ControleurEsporter;
import Controleur.ControleurEsporter.EtatEsporter;

import java.awt.Color;
import javax.swing.JTextField;

public class Esporter_AjouterJeu extends JPanel {

	private static JTextField textField, textField_1;
	private static JLabel labelErreur;

	private ControleurEsporter controleur = new ControleurEsporter(this, EtatEsporter.AJOUTER_JEU);

	public Esporter_AjouterJeu() {
		setLayout(new BorderLayout(0,0));

		JPanel Header = new JPanel();
		Header.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(Header, BorderLayout.NORTH);
		Header.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel panel_Esporter_0 = new JPanel();
		Header.add(panel_Esporter_0);
		panel_Esporter_0.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel(" E-Sporter");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel_Esporter_0.add(lblNewLabel);

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		Header.add(panel_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.add(panel_2);

		JLabel NomEcurie = new JLabel("Esporter");
		panel_2.add(NomEcurie);

		JButton BoutonDeco = new JButton("D\u00E9connexion");
		panel_2.add(BoutonDeco);
		BoutonDeco.addActionListener(controleur);

		JPanel Body = new JPanel();
		add(Body, BorderLayout.CENTER);
		Body.setLayout(new BorderLayout(0, 0));

		JPanel Titre = new JPanel();
		Body.add(Titre, BorderLayout.CENTER);
		Titre.setLayout(new BorderLayout(0, 0));

		JPanel panel_6 = new JPanel();
		Titre.add(panel_6, BorderLayout.NORTH);

		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new LineBorder(new Color(0, 0, 0)));
		FlowLayout flowLayout_2 = (FlowLayout) panel_7.getLayout();
		flowLayout_2.setHgap(55);
		panel_6.add(panel_7);

		JLabel lblNewLabel_2 = new JLabel("Ajouter jeu");
		panel_7.add(lblNewLabel_2);

		JPanel panel = new JPanel();
		Titre.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.NORTH);

		JPanel panel_4 = new JPanel();
		panel.add(panel_4, BorderLayout.WEST);

		JPanel panel_5 = new JPanel();
		panel.add(panel_5, BorderLayout.SOUTH);

		JPanel panel_8 = new JPanel();
		panel.add(panel_8, BorderLayout.EAST);

		JPanel panel_9 = new JPanel();
		panel_9.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(panel_9, BorderLayout.CENTER);
		panel_9.setLayout(new BorderLayout(0, 0));

		JPanel panel_10 = new JPanel();
		panel_9.add(panel_10, BorderLayout.SOUTH);

		JButton Valider = new JButton("Valider");
		Valider.addActionListener(controleur);

		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(controleur);
		panel_10.add(btnRetour);
		panel_10.add(Valider);

		JPanel panel_11 = new JPanel();
		panel_9.add(panel_11, BorderLayout.CENTER);
		panel_11.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel panel_13 = new JPanel();
		panel_11.add(panel_13);
		panel_13.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_1 = new JLabel("Nom du jeu");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_13.add(lblNewLabel_1);

		textField = new JTextField();
		textField.setColumns(10);
		panel_13.add(textField);

		JPanel panel_12 = new JPanel();
		panel_11.add(panel_12);
		panel_12.setLayout(new BorderLayout(0, 0));

		JPanel panel_14 = new JPanel();
		panel_12.add(panel_14, BorderLayout.SOUTH);

		labelErreur = new JLabel("");
		panel_14.add(labelErreur);

		JPanel panel_15 = new JPanel();
		panel_12.add(panel_15, BorderLayout.CENTER);

		JLabel lblNewLabel_4 = new JLabel("Nombre de joueurs par \u00E9quipes");
		panel_15.add(lblNewLabel_4);

		textField_1 = new JTextField();
		panel_15.add(textField_1);
		textField_1.setColumns(10);
	}


	public static String getNbJoueursParEquipe() {
		return textField_1.getText();
	}

	public static String getNomJeu() {
		return textField.getText();
	}

	public static boolean nomJeuEstVide() {
		return textField.getText().equals("");
	}

	public static boolean nbJoueursParEquipeEstVide() {
		return textField_1.getText().equals("");
	}

	public static void setLabelErreur(String msg) {
		labelErreur.setText(msg);
	}

}