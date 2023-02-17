package Vue;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.border.LineBorder;

import Controleur.ControleurConnexion;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class PageConnexion extends JPanel {

	private ControleurConnexion controleur = new ControleurConnexion(this);
	private JTextField nomUtilisateur;
	private JPasswordField mdp;
	private JLabel message;
	
	public PageConnexion() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setVgap(11);
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.add(panel_1);
		
		JLabel lblNewLabel = new JLabel("E-Sporter");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel_1.add(lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		
		JPanel panel_3 = new JPanel();
		add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.NORTH);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(new Color(0, 0, 0)));
		FlowLayout flowLayout_1 = (FlowLayout) panel_5.getLayout();
		flowLayout_1.setHgap(50);
		panel_4.add(panel_5);
		
		JLabel lblNewLabel_1 = new JLabel("Connexion");
		panel_5.add(lblNewLabel_1);
		
		JPanel panel_6 = new JPanel();
		panel_3.add(panel_6, BorderLayout.CENTER);
		
		JPanel panel_7 = new JPanel();
		panel_6.add(panel_7);
		panel_7.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_7.add(panel_8);
		panel_8.setLayout(new GridLayout(2, 2, 2, 0));
		
		JPanel panel_10 = new JPanel();
		panel_8.add(panel_10);
		panel_10.setLayout(new BorderLayout(0, 0));
		
		JLabel nomUtil = new JLabel("Utilisateur :");
		panel_10.add(nomUtil, BorderLayout.EAST);
		
		JPanel panel_11 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_11.getLayout();
		flowLayout_2.setVgap(20);
		flowLayout_2.setHgap(0);
		panel_8.add(panel_11);
		
		this.nomUtilisateur = new JTextField();
		panel_11.add(this.nomUtilisateur);
		this.nomUtilisateur.setColumns(10);
		
		JPanel panel_12 = new JPanel();
		panel_8.add(panel_12);
		panel_12.setLayout(new BorderLayout(0, 0));
		
		JLabel mdp = new JLabel("Mot De Passe :");
		panel_12.add(mdp, BorderLayout.EAST);
		
		JPanel panel_13 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_13.getLayout();
		flowLayout_3.setHgap(0);
		flowLayout_3.setVgap(20);
		panel_8.add(panel_13);
		
		this.mdp = new JPasswordField();
		this.mdp.setColumns(10);
		panel_13.add(this.mdp);
		
		JPanel panel_9 = new JPanel();
		panel_7.add(panel_9);
		panel_9.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_14 = new JPanel();
		panel_14.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_9.add(panel_14, BorderLayout.NORTH);
		panel_14.setLayout(new BorderLayout(0, 0));
		
		JButton mdpOublie = new JButton("Changer de mot de passe ?");
		panel_14.add(mdpOublie, BorderLayout.SOUTH);
		mdpOublie.addActionListener(controleur);
		
		JButton btnNewButton = new JButton("Se connecter");
		panel_14.add(btnNewButton, BorderLayout.CENTER);
		
		JButton btnNewButton_1 = new JButton("Pas de compte?");
		panel_14.add(btnNewButton_1, BorderLayout.EAST);
		btnNewButton_1.addActionListener(controleur);
		btnNewButton.addActionListener(controleur);
		
		JPanel panel_15 = new JPanel();
		panel_9.add(panel_15, BorderLayout.CENTER);
		panel_15.setLayout(new BorderLayout(0, 0));
		
		this.message = new JLabel("");
		panel_15.add(this.message, BorderLayout.CENTER);
		
		JPanel panel_16 = new JPanel();
		panel_15.add(panel_16, BorderLayout.SOUTH);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(controleur);
		panel_16.add(btnRetour);
	}
	
	public String getNomUtilisateur() {
		return this.nomUtilisateur.getText();
	}
	
	public String getMdp() {
		String returnMdp = "";
		for (char c : this.mdp.getPassword()) {
			returnMdp += c;
		}
		return returnMdp;
	}
	
	public void setMessage(String texte) {
		this.message.setText(texte);
	}

}