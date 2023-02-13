package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import Controleur.ControleurAccueil;

public class PageAccueil extends JPanel {

	private ControleurAccueil controleur = new ControleurAccueil();

	public PageAccueil() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		
		JPanel panel_Esporter_0 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_Esporter_0.getLayout();
		flowLayout.setVgap(15);
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.add(panel_Esporter_0);
		
		JLabel lblNewLabel = new JLabel("E-Sporter");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		panel_Esporter_0.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		panel.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.add(panel_2);
		
		JButton btnSeConnecter = new JButton("Se Connecter");
		btnSeConnecter.addActionListener(controleur);
		panel_2.add(btnSeConnecter);
		
		JPanel panel_3 = new JPanel();
		add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.NORTH);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(new Color(0, 0, 0)));
		FlowLayout flowLayout_3 = (FlowLayout) panel_6.getLayout();
		flowLayout_3.setHgap(55);
		panel_4.add(panel_6);
		
        JLabel lblNewLabel_2 = new JLabel("Accueil");
        panel_6.add(lblNewLabel_2);
		
		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_21 = new JLabel("Connectez vous pour participer \u00E0 l'E-Sport de demain !");
		lblNewLabel_21.setHorizontalAlignment(SwingConstants.CENTER);
		panel_5.add(lblNewLabel_21, BorderLayout.NORTH);
		
		JPanel panel_7 = new JPanel();
		panel_5.add(panel_7, BorderLayout.WEST);
		
		JPanel panel_8 = new JPanel();
		panel_5.add(panel_8, BorderLayout.SOUTH);
		
		JPanel panel_9 = new JPanel();
		panel_5.add(panel_9, BorderLayout.EAST);
		
		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_5.add(panel_10, BorderLayout.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(PageAccueil.class.getResource("/images/fnatic.jpg")));
		panel_10.add(lblNewLabel_1);
	}
}