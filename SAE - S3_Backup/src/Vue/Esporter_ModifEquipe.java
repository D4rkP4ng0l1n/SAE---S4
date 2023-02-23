package Vue;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import Controleur.ControleurEsporter;
import Controleur.ControleurEsporter.EtatEsporter;
import Modele.FonctionsSQL;
import Modele.Jeu;

import javax.swing.JTextField;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class Esporter_ModifEquipe extends JPanel{
	
	private static JTextField NomEquipe;
	private static JLabel Image_placeholder;
	private static boolean imageSet;
	private String[] listjeu;
	@SuppressWarnings("rawtypes")
	private static JComboBox comboBox;
	private static JLabel messageErreur;
	
	private ControleurEsporter controleur = new ControleurEsporter(this, EtatEsporter.MODIF_EQUIPE);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Esporter_ModifEquipe() throws SQLException {
		setLayout(new BorderLayout(0,0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel_Esporter_0 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_Esporter_0.getLayout();
		flowLayout.setVgap(11);
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.add(panel_Esporter_0);
		
		JLabel lblNewLabel = new JLabel("E-Sporter");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel_Esporter_0.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		panel.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.add(panel_2);
		
		JLabel lblNewLabel_1 = new JLabel("Esporter");
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
		
		JButton BtnAccueil = new JButton("Accueil");
		panel_4.add(BtnAccueil);
		
		JButton BtnTournois = new JButton("Tournois");
		panel_4.add(BtnTournois);
		
		JButton BtnEcurie = new JButton("Ecuries");
		panel_4.add(BtnEcurie);
		
		JButton BtnJeux = new JButton("Jeux");
		panel_4.add(BtnJeux);
		
		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_6 = new JPanel();
		panel_5.add(panel_6, BorderLayout.NORTH);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new LineBorder(new Color(0, 0, 0)));
		FlowLayout flowLayout_2 = (FlowLayout) panel_7.getLayout();
		flowLayout_2.setHgap(55);
		panel_6.add(panel_7);
		
		JLabel lblNewLabel_2 = new JLabel("Modification de l'Equipe");
		panel_7.add(lblNewLabel_2);
		
		JPanel panel_8 = new JPanel();
		panel_5.add(panel_8, BorderLayout.CENTER);
		panel_8.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_9 = new JPanel();
		panel_8.add(panel_9, BorderLayout.CENTER);
		panel_9.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel_11 = new JPanel();
		panel_9.add(panel_11);
		panel_11.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel panel_12 = new JPanel();
		panel_11.add(panel_12);
		panel_12.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_15 = new JPanel();
		panel_12.add(panel_15, BorderLayout.NORTH);
		panel_15.setLayout(new GridLayout(3, 2, 0, 0));
		
		JPanel panel_16 = new JPanel();
		panel_15.add(panel_16);
		panel_16.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_4 = new JLabel("Nom de votre Equipe :");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_16.add(lblNewLabel_4, BorderLayout.EAST);
		
		JPanel panel_17 = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) panel_17.getLayout();
		flowLayout_4.setVgap(15);
		panel_15.add(panel_17);
		
		NomEquipe = new JTextField();
		NomEquipe.setHorizontalAlignment(SwingConstants.CENTER);
		panel_17.add(NomEquipe);
		NomEquipe.setColumns(10);
		
		JPanel panel_18 = new JPanel();
		panel_15.add(panel_18);
		panel_18.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_5 = new JLabel("Jeu :");
		panel_18.add(lblNewLabel_5, BorderLayout.EAST);
		
		JPanel panel_19 = new JPanel();
		panel_15.add(panel_19);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(listJeu()));
		panel_19.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_19.add(comboBox);
		
		JPanel panel_20 = new JPanel();
		panel_15.add(panel_20);
		panel_20.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_6 = new JLabel("Logo de votre Ecurie :");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_20.add(lblNewLabel_6);
		
		JPanel panel_21 = new JPanel();
		FlowLayout flowLayout_6 = (FlowLayout) panel_21.getLayout();
		flowLayout_6.setVgap(15);
		panel_15.add(panel_21);
		
		JButton btnAddlogo= new JButton("Modifier le Logo");
		btnAddlogo.addActionListener(controleur);
		panel_21.add(btnAddlogo);
		
		Image_placeholder = new JLabel("");
		panel_12.add(Image_placeholder, BorderLayout.EAST);
		
		messageErreur = new JLabel("");
		panel_12.add(messageErreur, BorderLayout.SOUTH);
		
		JPanel panel_10 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_10.getLayout();
		flowLayout_3.setHgap(25);
		panel_8.add(panel_10, BorderLayout.SOUTH);
		
		JButton btnNewButton_1 = new JButton("Annuler");
		btnNewButton_1.addActionListener(controleur);
		panel_10.add(btnNewButton_1);
		
		JButton btnNewButton_3 = new JButton("Modifier Equipe");
		btnNewButton_3.addActionListener(controleur);
		panel_10.add(btnNewButton_3);
		setPage();
	}
	
	public void setPage() {
		NomEquipe.setText(ApplicationEsporter.equipe);
		try {
			ResultSet nomJeu=FonctionsSQL.select("saeequipe", "nom_1", "nom = '"+ApplicationEsporter.equipe+"'");
			nomJeu.next();
			for(int i=0; i<comboBox.getItemCount(); i++) {
				if(comboBox.getItemAt(i).equals(nomJeu.getString(1))) {
					comboBox.setSelectedIndex(i);
				}
			}
			setLogo();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void setImage(String path) {
        ImageIcon image = new ImageIcon(path);
        Image img = image.getImage();
        img = img.getScaledInstance(200, 356, Image.SCALE_DEFAULT);
        ImageIcon imageAVisualiser = new ImageIcon(img);
        imageSet = true;
        Image_placeholder.setIcon(imageAVisualiser);
    }
	
	private String[] listJeu() throws SQLException {
		ResultSet rs = Jeu.getTousLesJeux();
        ResultSet count = FonctionsSQL.select("SAEJeu", "count(nom)", "");
        count.next();
        listjeu = new String[count.getInt(1) + 1];
        int i = 1;
        listjeu[0] = "Choisir un Jeu";
        while (rs.next()) {
            listjeu[i]=rs.getString(1);
            i++;
        }
        return listjeu;
    }

	public static Boolean tousRempli() {
		return (!NomEquipe.getText().isEmpty() && getJeu() != "Choisir un Jeu" && imageSet);
	}
	
	public static String getNomEquipe() {
		return NomEquipe.getText();
	}
	
	public static String getJeu() {
		return (String) comboBox.getSelectedItem();
	}
	
	public static void setMessageErreur(String message) {
		messageErreur.setText(message);
	}
	
	public static void setLogo() throws SQLException {
		ResultSet unLogo = FonctionsSQL.select("saeequipe", "logo", "nom = '" + ApplicationEsporter.equipe + "'");
		unLogo.next();
		setImage(unLogo.getString(1));
		ApplicationEsporter.logo_Path=unLogo.getString(1);
	}

}