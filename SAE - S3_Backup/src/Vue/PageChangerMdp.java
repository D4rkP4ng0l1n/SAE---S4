package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import Controleur.ControleurChangerMdp;

public class PageChangerMdp extends JPanel {

	private JTextField nomUtilisateur;
	private JPasswordField mdp, nouveauMdp;
	private JLabel message;

	private ControleurChangerMdp controleur = new ControleurChangerMdp(this);

	public PageChangerMdp() {
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

		JLabel lblNewLabel_1 = new JLabel("Changer de mot de passe");
		panel_5.add(lblNewLabel_1);

		JPanel panel_6 = new JPanel();
		panel_3.add(panel_6, BorderLayout.CENTER);

		JPanel panel_7 = new JPanel();
		panel_6.add(panel_7);
		panel_7.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_7.add(panel_8);
		panel_8.setLayout(new GridLayout(3, 2, 2, 0));

		JPanel panel_10 = new JPanel();
		panel_8.add(panel_10);
		panel_10.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_2 = new JLabel("Utilisateur :");
		panel_10.add(lblNewLabel_2, BorderLayout.EAST);

		JPanel panel_11 = new JPanel();
		panel_8.add(panel_11);

		this.nomUtilisateur = new JTextField();
		panel_11.add(this.nomUtilisateur);
		this.nomUtilisateur.setColumns(10);

		JPanel panel_12 = new JPanel();
		panel_8.add(panel_12);
		panel_12.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_3 = new JLabel("Mot De Passe :");
		panel_12.add(lblNewLabel_3, BorderLayout.EAST);

		JPanel panel_13 = new JPanel();
		panel_8.add(panel_13);

		this.mdp = new JPasswordField();
		this.mdp.setColumns(10);
		panel_13.add(this.mdp);

		JPanel panel_15 = new JPanel();
		panel_8.add(panel_15);
		panel_15.setLayout(new BorderLayout(0, 0));

		JLabel nouveauMotDePasse = new JLabel("Nouveau mot de passe : ");
		panel_15.add(nouveauMotDePasse, BorderLayout.EAST);

		JPanel panel_16 = new JPanel();
		panel_8.add(panel_16);

		this.nouveauMdp = new JPasswordField();
		panel_16.add(this.nouveauMdp);
		this.nouveauMdp.setColumns(10);

		JPanel panel_9 = new JPanel();
		panel_7.add(panel_9);
		panel_9.setLayout(new BorderLayout(0, 0));

		JPanel panel_14 = new JPanel();
		panel_14.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_9.add(panel_14, BorderLayout.NORTH);

		JButton btnNewButton_1 = new JButton("D\u00E9j\u00E0 un compte?");
		panel_14.add(btnNewButton_1);
		btnNewButton_1.addActionListener(controleur);

		JButton btnNewButton = new JButton("Valider le mot de passe");
		btnNewButton.addActionListener(controleur);
		panel_14.add(btnNewButton);
		btnNewButton_1.addActionListener(controleur);

		JPanel panel_17 = new JPanel();
		panel_9.add(panel_17, BorderLayout.CENTER);
		panel_17.setLayout(new BorderLayout(0, 0));

		this.message = new JLabel("");
		panel_17.add(this.message, BorderLayout.NORTH);
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

	public String getNouveauMdp() {
		String returnMdp = "";
		for (char c : this.nouveauMdp.getPassword()) {
			returnMdp += c;
		}
		return returnMdp;
	}

	public void setMessage(String texte) {
		this.message.setText(texte);
	}

}