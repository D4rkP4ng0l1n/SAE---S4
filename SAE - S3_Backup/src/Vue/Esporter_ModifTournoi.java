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

public class Esporter_ModifTournoi extends JPanel{

	private static JTextField Lieu;
	private static SqlDateModel model;
	private static JComboBox<Object> Jeu;
	private static JLabel heureSet;
	private static JComboBox<Object> heure, minute, am_pm;
	private static JList<String> Jeux;
	private static DefaultListModel<String> DLM = new DefaultListModel<String>();
	private static JLabel message;
	private static int erreur = 0;
	private ResultSet setUpCombo ;

	private ControleurEsporter controleur = new ControleurEsporter(this, EtatEsporter.MODIF_TOURNOI);

	public Esporter_ModifTournoi() throws SQLException {
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

		JLabel lblNewLabel_2 = new JLabel("Modifier tournoi");
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

		message = new JLabel("");
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
		Jeu = new JComboBox<Object>();
		Jeu.setModel(new DefaultComboBoxModel<Object>(listJeu(setUpCombo)));
		panel_14.add(Jeu);

		JButton btnAjouterJeu = new JButton("Ajouter le jeu");
		btnAjouterJeu.addActionListener(controleur);
		panel_14.add(btnAjouterJeu);

		JButton btnResetJeux = new JButton("Supprimer tout les jeux");
		btnResetJeux.addActionListener(controleur);
		panel_14.add(btnResetJeux);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(250,100));
		panel_14.add(scrollPane);

		Jeux = new JList<String>();
		Jeux.setVisibleRowCount(5);
		Jeux.setModel(new AbstractListModel() {
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane.setViewportView(Jeux);
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

		heure = new JComboBox<Object>();
		heure.setModel(new DefaultComboBoxModel<Object>(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		panel_15.add(heure);

		minute = new JComboBox<Object>();
		minute.setModel(new DefaultComboBoxModel<Object>(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"}));
		panel_15.add(minute);

		am_pm = new JComboBox<Object>();
		am_pm.setModel(new DefaultComboBoxModel<Object>(new String[] {"am", "pm"}));
		panel_15.add(am_pm);

		heureSet = new JLabel("");
		panel_15.add(heureSet);

		setPage();
	}

	public static String getLieu() {
		return Lieu.getText();
	}

	public static String getHeure() {
		return (String) heure.getSelectedItem();
	}

	public static String getMinute() {
		return (String) minute.getSelectedItem();
	}

	public static void setHeure(String heure) {
		
		heureSet.setText("ancienne date et heure:"+" "+heure);
	}

	public static boolean DLMIsVide() {
		return DLM.isEmpty();
	}

	public static String getJeu() {
		return (String) Jeu.getSelectedItem();
	}

	public static Date getDate() {
		return model.getValue();
	}

	public static JList<String> getJeux() {
		return Jeux;
	}

	public static boolean DateEstVide() {
		return  model.getValue() == null;
	}

	public static boolean datePassee() {
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

	public static void addList() {
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

	}

	public static void setMessage(String texte) {
		message.setText(texte);
	}

	public static void DLMVide() {
		DLM.removeAllElements();
	}

	private String[] listJeu(ResultSet rs) throws SQLException {
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

	public static boolean lieuEstVide() {
		return Lieu.getText().isEmpty();
	}

	public static void setLieu(String lieu) {
		Lieu.setText(lieu);
	}

	private static void setPage() {
		try {
			ResultSet tournoi = FonctionsSQL.select("saetournoi", "lieu", "IDTOURNOI = '" + ApplicationEsporter.idTournoi+"'");
			tournoi.next();
			setLieu(tournoi.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			ResultSet tournoi = FonctionsSQL.select("saeconcerner", "nom", "IDTOURNOI = '" + ApplicationEsporter.idTournoi+"'");
			while(tournoi.next()) {
				DLM.addElement(tournoi.getString(1));
				Jeux.setModel(DLM);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			ResultSet tournoi = FonctionsSQL.select("saetournoi", "DATEETHEURE", "IDTOURNOI = '" + ApplicationEsporter.idTournoi+"'");
			tournoi.next();
			String heureTournoi = tournoi.getString(1);
			char[] ancienneHeureTournoi= heureTournoi.toCharArray();
			ancienneHeureTournoi[0]='2';
			heureTournoi = "";
			for(char c:  ancienneHeureTournoi) {
				heureTournoi=heureTournoi+c;
			}
			tournoi = FonctionsSQL.select("saetournoi", "AM_PM", "IDTOURNOI = '" + ApplicationEsporter.idTournoi+"'");
			tournoi.next();
			heureTournoi= heureTournoi+" "+tournoi.getString(1);
			setHeure(heureTournoi);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			ResultSet tournoi = FonctionsSQL.select("saeconcerner", "nom", "IDTOURNOI = '" + ApplicationEsporter.idTournoi+"'");
			while(tournoi.next()) {
				if (DLM.contains(tournoi.getString(1))) {
				}else {
					DLM.addElement(tournoi.getString(1));
					Jeux.setModel(DLM);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static String getAmPm() {
		return (String) am_pm.getSelectedItem();
	}

	public static String formatDate(String avant) {
		char[] dateAvant= avant.toCharArray();
		String apres=""+dateAvant[8]+dateAvant[9]+dateAvant[7]+dateAvant[5]+dateAvant[6]+dateAvant[4]+dateAvant[2]+dateAvant[3];
		return apres;
	}

	public static void suprimerJeux() {
		DLMVide();
		Jeux.removeAll();
	}

	public static void formatAncienneDate() {
		ResultSet res;
		try {
			res = FonctionsSQL.select("saetournoi", "DATEETHEURE", "IDTOURNOI = '" + ApplicationEsporter.idTournoi+"'");
			String datadateduTournoi="";
			res.next();
			char[] dateduTournoi=  res.getDate(3).toString().toCharArray();
			if (dateduTournoi[0]=='1') {
				int dateOk=res.getDate(3).getYear();
				dateOk = (((dateOk - 1977) * -1) + 2024);
				String dateGalere = "" + dateOk;
				dateduTournoi[2]=dateGalere.charAt(2);
				dateduTournoi[3]=dateGalere.charAt(3);
			}
			dateduTournoi[0]='2';
			dateduTournoi[1]='0';
			datadateduTournoi="";
			for (char l: dateduTournoi) {
				datadateduTournoi= datadateduTournoi+l;
			}
			setHeure(datadateduTournoi);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}