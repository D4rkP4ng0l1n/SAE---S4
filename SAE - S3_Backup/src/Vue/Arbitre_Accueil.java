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

import Controleur.ControleurArbitre;
import Controleur.ControleurArbitre.EtatArbitre;

import java.awt.Color;

public class Arbitre_Accueil extends JPanel{

	private ControleurArbitre controleur = new ControleurArbitre(this, EtatArbitre.ACCUEIL);

	//constructeur de la page Arbitre Accueil
	public Arbitre_Accueil() {
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel panel_Arbitre_0 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_Arbitre_0.getLayout();
		flowLayout.setVgap(11);
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.add(panel_Arbitre_0);

		JLabel lblNewLabel = new JLabel("E-Sporter");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel_Arbitre_0.add(lblNewLabel);

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		panel.add(panel_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.add(panel_2);

		JLabel lblNewLabel_1 = new JLabel("Arbitre");
		panel_2.add(lblNewLabel_1);

		JButton btnNewButton = new JButton("D\u00E9connexion");
		panel_2.add(btnNewButton);
		btnNewButton.addActionListener(controleur);

		JPanel panel_3 = new JPanel();
		add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new GridLayout(1, 4, 0, 0));

		JButton btnNewButton_1 = new JButton("Accueil");
		panel_4.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Tournois");
		panel_4.add(btnNewButton_2);
		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new BorderLayout(0, 0));
		btnNewButton_2.addActionListener(controleur);

		JPanel panel_6 = new JPanel();
		panel_5.add(panel_6, BorderLayout.NORTH);

		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new LineBorder(new Color(0, 0, 0)));
		FlowLayout flowLayout_2 = (FlowLayout) panel_7.getLayout();
		flowLayout_2.setHgap(55);
		panel_6.add(panel_7);

		JLabel lblNewLabel_2 = new JLabel("Accueil");
		panel_7.add(lblNewLabel_2);

		JPanel panel_8 = new JPanel();
		panel_5.add(panel_8, BorderLayout.CENTER);
		panel_8.setLayout(new BorderLayout(0, 0));

		JPanel panel_9 = new JPanel();
		panel_8.add(panel_9, BorderLayout.NORTH);

		JPanel panel_10 = new JPanel();
		panel_8.add(panel_10, BorderLayout.WEST);

		JPanel panel_11 = new JPanel();
		panel_8.add(panel_11, BorderLayout.SOUTH);

		JPanel panel_12 = new JPanel();
		panel_8.add(panel_12, BorderLayout.EAST);

		JPanel panel_13 = new JPanel();
		panel_13.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_8.add(panel_13, BorderLayout.CENTER);
	}


}