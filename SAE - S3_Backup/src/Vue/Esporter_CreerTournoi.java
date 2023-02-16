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
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import Controleur.ControleurEsporter;
import Controleur.ControleurEsporter.EtatEsporter;
import Modele.FonctionsSQL;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;

@SuppressWarnings("serial")
public class Esporter_CreerTournoi extends JPanel{

	private static JTextField Lieu;
	private static SqlDateModel model;
	private static JComboBox<?> Jeu;
	private static JComboBox<?> heure;
	private static JComboBox<?> minute;
	private static JComboBox<?> am_pm;
	private static JList<String> Jeux;
	private static DefaultListModel<String> DLM = new DefaultListModel<String>();
	private static JLabel message;
	private static int erreur = 0;
	private ResultSet setUpCombo ;
	
	private ControleurEsporter controleur = new ControleurEsporter(this, EtatEsporter.CREE_TOURNOI);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Esporter_CreerTournoi() throws SQLException {
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

        JLabel lblNewLabel_2 = new JLabel("Ajouter tournoi");
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
        panel_10.setLayout(new GridLayout(0, 1, 0, 0));
        
        JPanel panel_16 = new JPanel();
        panel_10.add(panel_16);
        
        JButton BtnRetour = new JButton("Retour");
        panel_16.add(BtnRetour);
        BtnRetour.addActionListener(controleur);
        
        JButton Valider = new JButton("Valider");
        panel_16.add(Valider);
        Valider.addActionListener(controleur);
        
        JPanel panel_17 = new JPanel();
        panel_10.add(panel_17);
        
        Esporter_CreerTournoi.message = new JLabel("");
        panel_17.add(message);
        
        JPanel panel_11 = new JPanel();
        panel_9.add(panel_11, BorderLayout.CENTER);
        panel_11.setLayout(new GridLayout(2, 1, 0, 0));
        
        JPanel panel_13 = new JPanel();
        panel_11.add(panel_13);
        panel_13.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JLabel lblNewLabel_1 = new JLabel("Lieu");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        panel_13.add(lblNewLabel_1);
        
        Lieu = new JTextField();
        Lieu.setColumns(10);
        panel_13.add(Lieu);
        
        JPanel panel_14 = new JPanel();
        panel_11.add(panel_14);
        
        JLabel lblNewLabel_3 = new JLabel("Jeu");
        panel_14.add(lblNewLabel_3);
        
        this.setUpCombo= FonctionsSQL.select("SAEJeu", "nom", "");
        Esporter_CreerTournoi.Jeu = new JComboBox();
        Esporter_CreerTournoi.Jeu.setModel(new DefaultComboBoxModel(listJeu(setUpCombo)));
        panel_14.add(Esporter_CreerTournoi.Jeu);
        
        JButton btnAjouterJeu = new JButton("Ajouter le jeu");
        btnAjouterJeu.addActionListener(controleur);
        panel_14.add(btnAjouterJeu);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(250,100));
        panel_14.add(scrollPane);
        
        Esporter_CreerTournoi.Jeux = new JList();
        Jeux.setVisibleRowCount(5);
        Esporter_CreerTournoi.Jeux.setModel(new AbstractListModel() {
        	String[] values = new String[] {};
        	public int getSize() {
        		return values.length;
        	}
        	public Object getElementAt(int index) {
        		return values[index];
        	}
        });
        scrollPane.setViewportView(Esporter_CreerTournoi.Jeux);
        btnAjouterJeu.addActionListener(controleur);
        
        JPanel panel_12 = new JPanel();
        panel_11.add(panel_12);
        panel_12.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JLabel lblNewLabel_4 = new JLabel("Date (JJ/MM/AAAA)");
        panel_12.add(lblNewLabel_4);
        
        model = new SqlDateModel();
        model.setDate(2022, 12, 5);
        JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
        JDatePickerImpl date = new JDatePickerImpl(datePanel, new DateComponentFormatter());
        panel_12.add(date);
        
        JPanel panel_15 = new JPanel();
        panel_11.add(panel_15);
        
        JLabel labelHeure = new JLabel("Heure de d\u00E9but");
        panel_15.add(labelHeure);
        
        heure = new JComboBox();
        heure.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
        panel_15.add(heure);
        
        minute = new JComboBox();
        minute.setModel(new DefaultComboBoxModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"}));
        panel_15.add(minute);
        
        am_pm = new JComboBox();
        am_pm.setModel(new DefaultComboBoxModel(new String[] {"am", "pm"}));
        panel_15.add(am_pm);
    }
	
	public static String getLieu() { // Retourne le lieu d'un tournoi en cours de création
		return Lieu.getText();
	}
	
	public static String getHeure() { // Retourne l'heure d'un tournoi en cours de création
		return (String) heure.getSelectedItem();
	}
	
	public static String getMinute() { // Retourne les minutes d'un tournoi en cours de création
		return (String) minute.getSelectedItem();
	}
	
	public static String getAmPm() { // Retourne le "moment" (après-midi ou matin) d'un tournoi en cours de création
		return (String) am_pm.getSelectedItem();
	}
	
	public static boolean DLMIsVide() { // Retourne true si la liste des jeux est vide
		return DLM.isEmpty();
	}
	
	public static String getJeu() { // Retourne un String du jeu sélectionné dans la combobox
		return (String) Jeu.getSelectedItem();
	}
	
	public static Date getDate() { // Retourne la date sélectionnée
		model.setYear(model.getYear()-2000);
		return model.getValue();
	}
	
	@SuppressWarnings("rawtypes")
	public static JList getJeux() { // Retourne la liste de tous les jeux du tournoi
		return Jeux;
	}
	
	public static boolean DateEstVide() { // Retourne true si la date n'est pas sélectionnée
		return  model.getValue() == null;
	}
	
	public static boolean datePassee() { // Retourne true si la date sélectionnée est antérieure à la date actuelle
		if (model.getYear() > LocalDate.now().getYear()) {
			return false;
		} else if (model.getMonth()+1 > LocalDate.now().getMonthValue() &&  model.getYear() == LocalDate.now().getYear()) {
			return false;
		} else if (model.getDay() > LocalDate.now().getDayOfMonth() && model.getMonth()+1 == LocalDate.now().getMonthValue() &&  model.getYear() == LocalDate.now().getYear()) {
			return false;
		} else {
			return true;
		}
	}
	
	public static void addList() { // Ajoute le jeu sélectionné sur le combobox dans la liste de jeux du tournoi
		if (erreur==0) {
			erreur=1;
			if(getJeu()=="Choisir un Jeu") {
				setMessage("Choisissez un jeu avant de cliquer sur Ajouter le jeu");
			} else {
				if(DLM.contains(getJeu())) {
					setMessage("Selectionner un Nouveau jeu ou bien Valider la selection");
				} else {
					DLM.addElement(getJeu());
					Jeux.setModel(DLM);
					setMessage("");
				}
			}
		}else {
			erreur=0;
		}
		while(DLM.contains("Choisir un Jeu")){
			DLM.removeElement("Choisir un Jeu");
		}
		
	}
	
	public static void setMessage(String texte) { // Set un message d'erreur
		message.setText(texte);
	}
	
	public static void DLMVide() { // Retire tous les éléments de la liste des jeux du tournoi
		DLM.removeAllElements();
	}
	
	private String[] listJeu(ResultSet rs) throws SQLException { // Retourne la liste de tous les jeux de la base de données pour les mettre dans la combobox
		ResultSet rss = FonctionsSQL.select("SAEJeu", "count(nom)", "");
		rss.next();
		int count = rss.getInt(1);
		String[]listJeu= new String[count+1];
		int i = 1;
		listJeu[0]="Choisir un Jeu";
		while (rs.next()) {
			listJeu[i]=rs.getString(1);
			i++;
		}
		return listJeu;
	}
	
	public static boolean lieuEstVide() { // Retourne true si il n'y a pas de lieu pour le tournoi en cours de création
		return Lieu.getText().isEmpty();
	}
}