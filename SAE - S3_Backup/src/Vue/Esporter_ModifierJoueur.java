package Vue;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import Controleur.ControleurEsporter;
import Controleur.ControleurEsporter.EtatEsporter;
import Modele.Equipe;
import Modele.FonctionsSQL;
import Modele.Joueur;
import Modele.MyRendererAndEditor;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class Esporter_ModifierJoueur extends JPanel {

	private static JTable tableJoueurs;
	private static DefaultTableModel model;
	private static SqlDateModel modelDate;
	private static JTextField textFieldName;
	private static JTextField textFieldPseudo;
	private static JLabel LabelErreur;
	private static JButton boutonValider;
	private JLabel compteurNbJoueurs;

	private static Equipe nouvelleEquipe;
	private static List<Joueur>joueurs = new ArrayList<Joueur>();
	private ControleurEsporter controleur = new ControleurEsporter(this, EtatEsporter.MODIFIER_JOUEUR);
	
	public enum Erreurs{ERREURDATE,ERREURNOMNUL,ERREURPSEUDONUL,ERRERUJOUEUREXISTANT};

	public Esporter_ModifierJoueur() throws SQLException{
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 2, 0, 0));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.add(panel_1);

		JLabel lblNewLabel = new JLabel("Esporter");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		panel_1.add(lblNewLabel);

		JPanel panel_2 = new JPanel();
		add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3, BorderLayout.NORTH);

		JLabel lblNewLabel_1 = new JLabel("Modifier Joueur");
		panel_3.add(lblNewLabel_1);

		JPanel panel_15 = new JPanel();
		panel_2.add(panel_15, BorderLayout.CENTER);
		panel_15.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel_15.add(panel_4, BorderLayout.SOUTH);
		panel_4.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel panel_6 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_6.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		panel_4.add(panel_6);

		JLabel lblNom = new JLabel("Nom");
		panel_6.add(lblNom);

		JPanel panel_7 = new JPanel();
		panel_4.add(panel_7);

		textFieldName = new JTextField();
		panel_7.add(textFieldName);
		textFieldName.setColumns(10);

		JPanel panel_8 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_8.getLayout();
		flowLayout_2.setAlignment(FlowLayout.RIGHT);
		panel_4.add(panel_8);

		JLabel lblPseudo = new JLabel("Pseudo");
		panel_8.add(lblPseudo);

		JPanel panel_9 = new JPanel();
		panel_4.add(panel_9);

		textFieldPseudo = new JTextField();
		panel_9.add(textFieldPseudo);
		textFieldPseudo.setColumns(10);

		JPanel panel_10 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_10.getLayout();
		flowLayout_3.setAlignment(FlowLayout.RIGHT);
		panel_4.add(panel_10);

		JLabel lblBirthDate = new JLabel("Date De Naisssance");
		panel_10.add(lblBirthDate);

		JPanel panel_11 = new JPanel();
		panel_4.add(panel_11);

		modelDate = new SqlDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(modelDate,new Properties());
		JDatePickerImpl date = new JDatePickerImpl(datePanel,new DateComponentFormatter());
		panel_11.add(date);

		JPanel panel_14 = new JPanel();
		panel_4.add(panel_14);

		LabelErreur = new JLabel();
		panel_14.add(LabelErreur);

		JPanel panel_16 = new JPanel();
		panel_15.add(panel_16, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		panel_16.add(scrollPane);

		tableJoueurs = setTable(new JTable());
		scrollPane.setViewportView(tableJoueurs);

		JPanel panel_5 = new JPanel();
		panel_2.add(panel_5, BorderLayout.SOUTH);

		boutonValider = new JButton("Valider");
		if(! nbJoueurSuffisant()) {
			boutonValider.setEnabled(false);
		} else {
			boutonValider.setEnabled(true);
		}
		boutonValider.addActionListener(controleur);
		panel_5.add(boutonValider);	
		
		JButton btnNewButton = new JButton("Ajouter le joueur");
		panel_5.add(btnNewButton);
		btnNewButton.addActionListener(controleur);
		
		compteurNbJoueurs = new JLabel("");
		setCompteur();
		panel_5.add(compteurNbJoueurs);
	}

	public static void setEquipe(Equipe equipe) {
		nouvelleEquipe = equipe;
	}
	
	public static void addJoueur(Joueur joueur) {
		joueurs.add(joueur);
	}
	
	public static Equipe getEquipe() {
		return nouvelleEquipe;
	}
	
	private static int lastIndex() {
		int index = 0;
		for (Joueur j : joueurs) {
			index++;
		}
		return index - 1;
	}
	
	private void setCompteur() throws SQLException {
		ResultSet compteur = FonctionsSQL.select("saejoueur", "count(*)", "NOM_EQUIPE = '" + nouvelleEquipe.getNomEquipe() + "'");
		compteur.next();
		try {
			compteurNbJoueurs.setText(compteur.getInt(1) + " / " + nouvelleEquipe.getJeu().getNbJoueursParEquipe());
		} catch(Exception e) {
			compteurNbJoueurs.setText("0 / " + nouvelleEquipe.getJeu().getNbJoueursParEquipe());
		}
	}
	
	public JTable setTable(JTable table) throws SQLException {
		String columns[] = { "Nom Joueur(s)" , "Pseudo" , "Age" , "Equipe", " " };
		ResultSet count = FonctionsSQL.select("saejoueur", "count(*)", "NOM_EQUIPE = '" + nouvelleEquipe.getNomEquipe() + "'");
		count.next();
		String data[][] = new String[count.getInt(1)][5];
		ResultSet res = FonctionsSQL.select("saejoueur", "*", "NOM_EQUIPE = '" + nouvelleEquipe.getNomEquipe() + "' ORDER BY IDJOUEUR");
		int i = 0;
		while (res.next()) {
			data[i][0] = res.getString(2);
			data[i][1] = res.getString(3);
			data[i][2] = getAge(res.getDate(4));
			data[i][3] = nouvelleEquipe.getNomEquipe();
			i++;
		}
		model = new DefaultTableModel(data, columns);
		JTable returnTable = new JTable(model);
		returnTable.getColumn(" ").setCellRenderer(new MyRendererAndEditor(returnTable, "Supprimer", controleur, null, null));
		returnTable.getColumn(" ").setCellEditor(new MyRendererAndEditor(returnTable, "Supprimer", controleur, null, null));
		return returnTable;
	}
	
	public static JTable getTable() {
		return tableJoueurs;
	}
	
	public static Joueur getLastJoueur() {
		return joueurs.get(lastIndex());
	}
	
	public static List<Joueur> getJoueurs() {
		return joueurs;
	}

	public static Date getDate() {
		return modelDate.getValue();
	}
	
	public static String getNomJoueur() {
		return textFieldName.getText();
	}
	
	public static String getPseudoJoueur() {
		return textFieldPseudo.getText();
	}
	
	public static SqlDateModel getModel() {
		return modelDate;
	}
	
	public static void setErreur(Erreurs newError) {
		switch (newError){
		case ERREURNOMNUL:
			LabelErreur.setText("Le joueur doit avoir un nom");
			break;
		case ERREURPSEUDONUL:
			LabelErreur.setText("Le joueur doit avoir un pseudo");
			break;
		case ERREURDATE:
			LabelErreur.setText("Le Joueur doit avoir plus de 16 ans");
			break;
		case ERRERUJOUEUREXISTANT:
			LabelErreur.setText("Le joueur existe déjà");
			break;
		}

	}

	public static boolean isNomNull() {
		return textFieldName.getText().isEmpty();
	}
	
	public static boolean isPseudoNull() {
		return textFieldPseudo.getText().isEmpty();
	}

	public static boolean joueurExiste() throws SQLException {
		ResultSet rs = FonctionsSQL.select("saejeu", "*", "nom = '" + textFieldName.getText() + "'");
		return rs.next();
	}
	
	public static void supprimerJoueur(String pseudo) {
		for(int i = 0; i < joueurs.size(); i++) {
			if(joueurs.get(i).getPseudo().equals(pseudo)) {
				joueurs.remove(i);
			}
		}
	}
	
	public static void annuler() throws SQLException {
		for(Joueur j : joueurs) {
			j.supprimerJoueur();
		}
		nouvelleEquipe.supprimerEquipe();
	}

	private boolean nbJoueurSuffisant() throws SQLException {
		ResultSet compteur = FonctionsSQL.select("saejoueur", "count(*)", "NOM_EQUIPE = '" + nouvelleEquipe.getNomEquipe() + "'");
		compteur.next();
		try {
			return compteur.getInt(1) == Integer.parseInt(nouvelleEquipe.getJeu().getNbJoueursParEquipe());
		} catch(Exception e) {
			return false;
		}
	}
	
	public String getAge(Date date) {
		int age;
		age= LocalDate.now().getYear()-(date.getYear()+1899);
		if(date.getMonth()<LocalDate.now().getMonthValue() || (date.getMonth()==LocalDate.now().getMonthValue() &&  date.getDay()<LocalDate.now().getDayOfMonth())) {
			age=age-1;
		}
		return ""+age;
	}
}