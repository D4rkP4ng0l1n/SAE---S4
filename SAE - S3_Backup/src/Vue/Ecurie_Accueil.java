package Vue;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import Controleur.ControleurEcurie;
import Controleur.ControleurEcurie.EtatEcurie;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class Ecurie_Accueil extends JPanel {
	
	private ControleurEcurie controleur = new ControleurEcurie(this, EtatEcurie.TOURNOI);
	
	public Ecurie_Accueil() {
		setLayout(new BorderLayout(0,0));
		
		JPanel Header = new JPanel();
		Header.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(Header, BorderLayout.NORTH);
		Header.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel_Esporter_0 = new JPanel();
		Header.add(panel_Esporter_0);
		FlowLayout fl_panel_Esporter_0 = new FlowLayout(FlowLayout.LEFT, 5, 11);
		panel_Esporter_0.setLayout(fl_panel_Esporter_0);
		
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
		
		JLabel lblNewLabel_1 = new JLabel(controleur.getNomEcurie());
		panel_2.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("D\u00E9connexion");
		panel_2.add(btnNewButton);
		btnNewButton.addActionListener(controleur);
		
		JPanel panel_3 = new JPanel();
		add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel MenuNavigation = new JPanel();
		panel_3.add(MenuNavigation, BorderLayout.NORTH);
		MenuNavigation.setLayout(new GridLayout(1, 4, 0, 0));
		
		JButton btnNewButton_1 = new JButton("Accueil");
		MenuNavigation.add(btnNewButton_1);
		
		JButton btnNewButton_3 = new JButton("Mes \u00E9quipes");
		btnNewButton_3.addActionListener(controleur);
		MenuNavigation.add(btnNewButton_3);
		
		JButton btnNewButton_2 = new JButton("Tournois");
		MenuNavigation.add(btnNewButton_2);
		btnNewButton_2.addActionListener(controleur);
		
		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_6 = new JPanel();
		panel_5.add(panel_6, BorderLayout.NORTH);
		
		JPanel Bandeau = new JPanel();
		FlowLayout fl_Bandeau = (FlowLayout) Bandeau.getLayout();
		fl_Bandeau.setHgap(55);
		Bandeau.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_6.add(Bandeau);
		
		JLabel lblNewLabel_2 = new JLabel("Accueil");
		Bandeau.add(lblNewLabel_2);
		
		JPanel panel = new JPanel();
		panel_5.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4, BorderLayout.WEST);
		
		JPanel panel_7 = new JPanel();
		panel.add(panel_7, BorderLayout.NORTH);
		
		JPanel panel_8 = new JPanel();
		panel.add(panel_8, BorderLayout.SOUTH);
		
		JPanel panel_9 = new JPanel();
		panel.add(panel_9, BorderLayout.EAST);
		
		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(panel_10, BorderLayout.CENTER);
	}

}