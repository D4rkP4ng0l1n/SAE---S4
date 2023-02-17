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
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.SqlDateModel;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

import Controleur.ControleurEcurie;
import Modele.Equipe;
import Modele.FonctionsSQL;
import Modele.Joueur;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class Ecurie_Inscription extends JPanel {

	private ControleurEcurie controleur = new ControleurEcurie(this, ControleurEcurie.EtatEcurie.INSCRIPTION_TOURNOI);

	private JTable tableEquipe;
	private static DefaultTableModel model;

	private static JComboBox<String> comboEquipes;
	private static List<Joueur> joueurs = new ArrayList<Joueur>();


	public Ecurie_Inscription() throws SQLException {
		setLayout(new BorderLayout(0,0));

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel panel_Arbitre_0 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_Arbitre_0.getLayout();
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

		JLabel lblNewLabel_1 = new JLabel(controleur.getNomEcurie());
		panel_2.add(lblNewLabel_1);

		JButton btnNewButton = new JButton("D\u00E9connexion");
		panel_2.add(btnNewButton);

		JPanel panel_3 = new JPanel();
		add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new GridLayout(1, 4, 0, 0));

		JButton btnNewButton_1 = new JButton("Accueil");
		panel_4.add(btnNewButton_1);
		btnNewButton_1.addActionListener(controleur);

		JButton btnNewButton_3 = new JButton("Mes équipes");
		panel_4.add(btnNewButton_3);
		btnNewButton_3.addActionListener(controleur);

		JButton btnNewButton_2 = new JButton("Tournois");
		panel_4.add(btnNewButton_2);
		btnNewButton_2.addActionListener(controleur);

		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new BorderLayout(0, 0));

		JPanel panel_6 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_6.getLayout();
		flowLayout_3.setVgap(15);
		panel_5.add(panel_6, BorderLayout.NORTH);

		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new LineBorder(new Color(0, 0, 0)));
		FlowLayout flowLayout_2 = (FlowLayout) panel_7.getLayout();
		flowLayout_2.setHgap(55);
		panel_6.add(panel_7);

		JLabel lblNewLabel_2 = new JLabel("Informations");
		panel_7.add(lblNewLabel_2);

		JPanel panel_8 = new JPanel();
		panel_5.add(panel_8, BorderLayout.CENTER);
		panel_8.setLayout(new BorderLayout(0, 0));

		JPanel panel_9 = new JPanel();
		panel_8.add(panel_9, BorderLayout.NORTH);
		panel_9.setLayout(new GridLayout(0, 3, 0, 0));

		JPanel panel_10 = new JPanel();
		panel_8.add(panel_10, BorderLayout.WEST);

		JPanel panel_11 = new JPanel();
		panel_8.add(panel_11, BorderLayout.SOUTH);

		JButton btnNewButton_4 = new JButton("S'inscrire");
		btnNewButton_4.addActionListener(controleur);
		panel_11.add(btnNewButton_4);

		JPanel panel_12 = new JPanel();
		panel_8.add(panel_12, BorderLayout.EAST);

		JPanel panel_13 = new JPanel();
		panel_13.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_8.add(panel_13, BorderLayout.CENTER);
		panel_13.setLayout(new BorderLayout(0, 0));

		JPanel panel_14 = new JPanel();
		panel_13.add(panel_14, BorderLayout.NORTH);
		panel_14.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_3 = new JLabel(controleur.getNomEcurie());
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_14.add(lblNewLabel_3);

		JPanel panel_15 = new JPanel();
		panel_13.add(panel_15, BorderLayout.CENTER);
		panel_15.setLayout(new BorderLayout(0, 0));

		JButton btnNewButton_5 = new JButton("Pas encore d'\u00E9quipe ?");
		panel_15.add(btnNewButton_5, BorderLayout.SOUTH);
		btnNewButton_5.addActionListener(controleur);

		JScrollPane scrollPane = new JScrollPane();
		panel_15.add(scrollPane, BorderLayout.CENTER);

		tableEquipe = new JTable();
		scrollPane.setViewportView(tableEquipe);

		JPanel panel_16 = new JPanel();
		panel_15.add(panel_16, BorderLayout.NORTH);

		JLabel lblNewLabel_4 = new JLabel("S\u00E9lectionnez votre \u00E9quipe :");
		panel_16.add(lblNewLabel_4);

		setComboBox();
		ActionListener cbActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch(comboEquipes.getSelectedIndex()) {
				case 0:
					break;
				default:
					tableEquipe = setTable();
					scrollPane.setViewportView(tableEquipe);
				}
			}
		};
		comboEquipes.addActionListener(cbActionListener);
		panel_16.add(comboEquipes);
	}

	private static JTable setTable() {
		try {
			setListJoueurs();
			String columns[] = { "Nom" , "Pseudo" , "Age" , "Equipe" };
			ResultSet count = FonctionsSQL.select("saejoueur", "count(*)", "NOM_EQUIPE = '" + comboEquipes.getSelectedItem() + "'");
			count.next();
			String data[][] = new String[count.getInt(1)][4];
			ResultSet res = FonctionsSQL.select("saejoueur", "*", "NOM_EQUIPE = '" + comboEquipes.getSelectedItem() + "' ORDER BY IDJOUEUR");
			int i = 0;
			while (res.next()) {
				data[i][0] = res.getString(2);
				data[i][1] = res.getString(3);
				data[i][2] = "";
				data[i][3] = "" + comboEquipes.getSelectedItem();
				i++;
			}
			for(int index = 0; index < data.length; index++) {
				data[index][2] = "" + joueurs.get(index).calculAge(); 
			}
			model = new DefaultTableModel(data, columns);
			return (new JTable(model));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void setListJoueurs() {
		try {
			ResultSet selectJoueur = FonctionsSQL.select("saejoueur", "*", "NOM_EQUIPE = '" + comboEquipes.getSelectedItem() + "'");
			ResultSet selectEquipe = FonctionsSQL.select("saeequipe", "*", "NOM = '" + comboEquipes.getSelectedItem() + "'");
			selectEquipe.next();
			Equipe equipe = new Equipe(selectEquipe.getString(5), selectEquipe.getString(1), selectEquipe.getString(4), selectEquipe.getString(3));
			while(selectJoueur.next()) {
				int i = selectJoueur.getInt(1);
				SqlDateModel date = new SqlDateModel(selectJoueur.getDate(4));
				joueurs.add(new Joueur(selectJoueur.getString(2), selectJoueur.getString(3), date, equipe ));
				joueurs.get(joueurs.size() - 1).setId(i);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public JTable getTable() {
		return tableEquipe;
	}

	private void setComboBox() {
		try {
			ResultSet countEquipes = FonctionsSQL.select("SAEEquipe", "count(*)", "NOM_2 = '" + controleur.getNomEcurie() + "' AND NOM_1 = '" + setJeux() + "'");
			ResultSet selectEquipes = FonctionsSQL.select("SAEEquipe", "NOM", "NOM_2 = '" + controleur.getNomEcurie() + "' AND NOM_1 = '" + setJeux() + "'");
			countEquipes.next();
			String[]listeEquipes = new String[countEquipes.getInt(1) + 1];
			listeEquipes[0] = "Choisir votre equipe";
			int i = 1;
			while(selectEquipes.next()) {
				listeEquipes[i] = selectEquipes.getString(1);
				i++;
			}
			comboEquipes = new JComboBox<String>(listeEquipes);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static String getCombo() {
		return (String) comboEquipes.getSelectedItem();
	}

	public static int getIdTournoiSelected() {
		try {
			ResultSet selectTournoi = FonctionsSQL.select("saetournoi", "IDTOURNOI", "LIEU = '" + (String) Ecurie_Tournoi.getTable().getValueAt(Ecurie_Tournoi.getTable().getSelectedRow(), 0) + "' AND DATEETHEURE LIKE TO_DATE('" + (String) Ecurie_Tournoi.getTable().getValueAt(Ecurie_Tournoi.getTable().getSelectedRow(), 1) + "', 'YYYY-MM-DD')");
			selectTournoi.next();
			return selectTournoi.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	private String setJeux() {
		String listeJeux = "";
		try {
			ResultSet selectJeux = FonctionsSQL.select("saeconcerner", "NOM", "IDTOURNOI = " + getIdTournoiSelected());
			while(selectJeux.next()) {
				listeJeux += selectJeux.getString(1) + " - ";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listeJeux.substring(0, listeJeux.length() - 3);
	}
}