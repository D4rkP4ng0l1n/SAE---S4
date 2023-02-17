package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.SqlDateModel;

import Controleur.ControleurEsporter;
import Controleur.ControleurEsporter.EtatEsporter;
import Modele.Equipe;
import Modele.FonctionsSQL;
import Modele.Joueur;

@SuppressWarnings("serial")
public class Esporter_Joueurs extends JPanel {

	private static JTable tableJoueurs;
	private static DefaultTableModel model;

	private static JLabel nomJeu;
	private static JLabel nbPoints;
	private static List<Joueur> joueurs = new ArrayList<Joueur>();
	
	private ControleurEsporter controleur = new ControleurEsporter(this, EtatEsporter.JOUEUR);

	public Esporter_Joueurs() throws SQLException {
		ApplicationEsporter.equipe=(String) Esporter_Equipes.getTable().getValueAt(Esporter_Equipes.getTable().getSelectedRow(), 0);
		setLayout(new BorderLayout(0, 0));
		
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

		JLabel lblNewLabel_2 = new JLabel("Gestion de l'Equipe");
		panel_7.add(lblNewLabel_2);

		JPanel panel_8 = new JPanel();
		panel_5.add(panel_8, BorderLayout.CENTER);
		panel_8.setLayout(new BorderLayout(0, 0));

		JPanel panel_9 = new JPanel();
		panel_8.add(panel_9, BorderLayout.CENTER);
		panel_9.setLayout(new BorderLayout(0, 0));

		JPanel panel_11 = new JPanel();
		panel_9.add(panel_11, BorderLayout.NORTH);

		JPanel panel_12 = new JPanel();
		panel_9.add(panel_12, BorderLayout.WEST);

		JPanel panel_13 = new JPanel();
		panel_9.add(panel_13, BorderLayout.SOUTH);

		JPanel panel_14 = new JPanel();
		panel_9.add(panel_14, BorderLayout.EAST);

		JPanel panel_15 = new JPanel();
		panel_9.add(panel_15, BorderLayout.CENTER);
		panel_15.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_15.add(scrollPane, BorderLayout.CENTER);

		tableJoueurs = setTable(new JTable());
		scrollPane.setViewportView(tableJoueurs);

		JPanel panel_16 = new JPanel();
		panel_15.add(panel_16, BorderLayout.NORTH);
		panel_16.setLayout(new BorderLayout(0, 0));

		JPanel panel_17 = new JPanel();
		panel_16.add(panel_17, BorderLayout.WEST);

		JPanel panel_18 = new JPanel();
		panel_16.add(panel_18, BorderLayout.EAST);
		
		nbPoints = new JLabel("");
		panel_18.add(nbPoints);
		setNbPoints();

		JPanel panel_19 = new JPanel();
		panel_19.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_16.add(panel_19, BorderLayout.CENTER);

		nomJeu = new JLabel("");
		panel_19.add(nomJeu);
		setNomJeu();

		JPanel panel_10 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_10.getLayout();
		flowLayout_3.setHgap(25);
		panel_8.add(panel_10, BorderLayout.SOUTH);

		JButton btnNewButton_1 = new JButton("Retour");
		btnNewButton_1.addActionListener(controleur);
		
		JButton btnNewButton_2 = new JButton("Modifier Equipe");
		btnNewButton_2.addActionListener(controleur);
		panel_10.add(btnNewButton_2);
		panel_10.add(btnNewButton_1);
	}

	private JTable setTable(JTable table) throws SQLException {
		setListJoueurs();
		String columns[] = { "Nom Joueur(s)" , "Pseudo" , "Age" , "Equipe" };
		ResultSet count = FonctionsSQL.select("saejoueur", "count(*)", "NOM_EQUIPE = '" + ApplicationEsporter.equipe + "'");
		count.next();
		String data[][] = new String[count.getInt(1)][4];
		ResultSet res = FonctionsSQL.select("saejoueur", "*", "NOM_EQUIPE = '" + ApplicationEsporter.equipe + "' ORDER BY IDJOUEUR");
		int i = 0;
		while (res.next()) {
			data[i][0] = res.getString(2);
			data[i][1] = res.getString(3);
			data[i][2] = "";
			data[i][3] = "" + ApplicationEsporter.equipe;
			i++;
		}
		int index;
		for(index = 0; index < data.length; index++) {
			data[index][2] = "" + joueurs.get(index).calculAge(); 
		}
		model = new DefaultTableModel(data, columns);
		JTable returnTable = new JTable(model);
		return returnTable;
	}

	private void setNomJeu() throws SQLException {
		ResultSet selectNomJeu = FonctionsSQL.select("saeequipe", "NOM_1" , "NOM = '" + ApplicationEsporter.equipe + "'");
		selectNomJeu.next();
		nomJeu.setText(selectNomJeu.getString(1));
	}
	
	private void setNbPoints() throws SQLException {
		ResultSet selectNbPoints = FonctionsSQL.select("saeequipe", "NBPOINTS" , "NOM = '" + ApplicationEsporter.equipe + "'");
		selectNbPoints.next();
		nbPoints.setText("Points : " + selectNbPoints.getInt(1));
	}

	private static void setListJoueurs() throws SQLException {
		ResultSet selectJoueur = FonctionsSQL.select("saejoueur", "*", "NOM_EQUIPE = '" + ApplicationEsporter.equipe + "'");
		ResultSet selectEquipe = FonctionsSQL.select("saeequipe", "*", "NOM = '" + ApplicationEsporter.equipe + "'");
		selectEquipe.next();
		Equipe equipe = new Equipe(selectEquipe.getString(5), selectEquipe.getString(1), selectEquipe.getString(4), selectEquipe.getString(3));
		int i;
		SqlDateModel date;
		while(selectJoueur.next()) {
			i = selectJoueur.getInt(1);
			date = new SqlDateModel(selectJoueur.getDate(4));
			joueurs.add(new Joueur(selectJoueur.getString(2), selectJoueur.getString(3), date, equipe ));
			joueurs.get(joueurs.size() - 1).setId(i);
		}
	}

}