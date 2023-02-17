package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import Controleur.ControleurEcurie;
import Controleur.ControleurEcurie.EtatEcurie;

@SuppressWarnings("serial")
public class Ecurie_CreerEcurie extends JPanel{

	private ControleurEcurie controleur = new ControleurEcurie(this, EtatEcurie.CREATION);

	private static JLabel previsualisationImage;
	private static JTextField nomEcurie;
	private static JTextField nomCEO;
	private static JLabel messageErreur;

	public Ecurie_CreerEcurie() {
		setLayout(new BorderLayout(0,0));

		JPanel Header = new JPanel();
		Header.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(Header, BorderLayout.NORTH);
		Header.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel panel_Esporter_0 = new JPanel();
		Header.add(panel_Esporter_0);
		panel_Esporter_0.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel(" Esporter");
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

		JLabel NomEcurie = new JLabel("Ecurie");
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

		JLabel lblNewLabel_2 = new JLabel("Création d'une ecurie");
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
		panel_10.add(Valider);

		JPanel panel_11 = new JPanel();
		panel_9.add(panel_11, BorderLayout.CENTER);
		panel_11.setLayout(new BorderLayout(0, 0));

		JPanel panel_13 = new JPanel();
		panel_11.add(panel_13, BorderLayout.NORTH);
		panel_13.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_1 = new JLabel("Nom Ecurie");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_13.add(lblNewLabel_1);

		nomEcurie = new JTextField();
		nomEcurie.setColumns(10);
		panel_13.add(nomEcurie);

		JLabel lblNewLabel_3 = new JLabel("Nom CEO");
		panel_13.add(lblNewLabel_3);

		nomCEO = new JTextField();
		panel_13.add(nomCEO);
		nomCEO.setColumns(10);

		JPanel panel_12 = new JPanel();
		panel_11.add(panel_12, BorderLayout.WEST);
		panel_12.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_4 = new JLabel("Logo Ecurie");
		panel_12.add(lblNewLabel_4);

		JButton ajouterLogo = new JButton("Ajouter un logo");
		panel_12.add(ajouterLogo);

		previsualisationImage = new JLabel("");
		panel_11.add(previsualisationImage, BorderLayout.CENTER);
		
		messageErreur = new JLabel("");
		panel_11.add(messageErreur, BorderLayout.SOUTH);
		ajouterLogo.addActionListener(controleur);
	}

	public static boolean labelsVide() {
		return nomEcurie.getText().isEmpty() && nomCEO.getText().isEmpty();
	}
	
	public static String getNomEcurie() {
		return nomEcurie.getText();
	}
	
	public static String getNomCEO() {
		return nomCEO.getText();
	}

	public static void setImage(String path) {
		ImageIcon image = new ImageIcon(path);
		Image img = image.getImage();
		img = img.getScaledInstance(512, 336, Image.SCALE_DEFAULT);
		ImageIcon imageAVisualiser = new ImageIcon(img);
		previsualisationImage.setIcon(imageAVisualiser);
	}
	
	public static void setMessage(String message) {
		messageErreur.setText(message);
	}
}