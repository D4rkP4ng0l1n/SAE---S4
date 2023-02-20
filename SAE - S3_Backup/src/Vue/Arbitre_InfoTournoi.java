package Vue;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Controleur.ControleurArbitre;
import Controleur.ControleurArbitre.EtatArbitre;
import Modele.FonctionsSQL;
import Modele.BDD.NomTablesBDD;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class Arbitre_InfoTournoi extends JPanel{

	private JTable table;
	private DefaultTableModel model;
	private ControleurArbitre controleur = new ControleurArbitre(this, EtatArbitre.INFOTOURNOI);

	//constructeur de la page arbitre info tournoi
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Arbitre_InfoTournoi() {
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
		
		JLabel lblNewLabel_1 = new JLabel("Arbitre");
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
		btnNewButton_1.addActionListener(controleur);
		
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
		
		JLabel lblNewLabel_2 = new JLabel("Informations");
		Bandeau.add(lblNewLabel_2);
		
		JPanel panel = new JPanel();
		panel_5.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) panel_4.getLayout();
		flowLayout_4.setHgap(25);
		panel.add(panel_4, BorderLayout.NORTH);
		
		JPanel Bandeau_1 = new JPanel();
		@SuppressWarnings("unused")
		FlowLayout flowLayout = (FlowLayout) Bandeau_1.getLayout();
		Bandeau_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_4.add(Bandeau_1);
		
		JLabel lblNewLabel_2_1;
		lblNewLabel_2_1 = new JLabel(getDateEtHeureTournoi());
		Bandeau_1.add(lblNewLabel_2_1);
		
		JPanel Bandeau_2 = new JPanel();
		@SuppressWarnings("unused")
		FlowLayout flowLayout_3 = (FlowLayout) Bandeau_2.getLayout();
		Bandeau_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_4.add(Bandeau_2);
		
		JLabel lblNewLabel_2_2;
		lblNewLabel_2_2 = new JLabel(getJeuEtLieu());
		Bandeau_2.add(lblNewLabel_2_2);
		
		JPanel Bandeau_3 = new JPanel();
		@SuppressWarnings("unused")
		FlowLayout flowLayout_2 = (FlowLayout) Bandeau_3.getLayout();
		Bandeau_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_4.add(Bandeau_3);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Classement g\u00E9n\u00E9ral", "Phase finale", "Poule 1", "Poule 2", "Poule 3", "Poule 4"}));
		Bandeau_3.add(comboBox);
		
		JPanel panel_7 = new JPanel();
		panel.add(panel_7, BorderLayout.SOUTH);
		
		JPanel panel_8 = new JPanel();
		panel.add(panel_8, BorderLayout.CENTER);
		panel_8.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_9 = new JPanel();
		panel_8.add(panel_9, BorderLayout.NORTH);
		
		JPanel panel_10 = new JPanel();
		panel_8.add(panel_10, BorderLayout.WEST);
		
		JPanel panel_11 = new JPanel();
		panel_8.add(panel_11, BorderLayout.EAST);
		
		JPanel panel_12 = new JPanel();
		panel_8.add(panel_12, BorderLayout.SOUTH);
		
		JPanel panel_13 = new JPanel();
		panel_8.add(panel_13, BorderLayout.CENTER);
		panel_13.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_14 = new JPanel();
		panel_13.add(panel_14, BorderLayout.SOUTH);
		
		JButton btnNewButton_3 = new JButton("Voir les matchs");
		btnNewButton_3.addActionListener(controleur);
		panel_14.add(btnNewButton_3);
		
		JPanel panel_15 = new JPanel();
		panel_13.add(panel_15, BorderLayout.WEST);
		
		JPanel panel_16 = new JPanel();
		panel_13.add(panel_16, BorderLayout.NORTH);
		
		JPanel panel_17 = new JPanel();
		panel_13.add(panel_17, BorderLayout.EAST);
		
		JPanel panel_18 = new JPanel();
		panel_13.add(panel_18, BorderLayout.CENTER);
		panel_18.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_18.add(scrollPane, BorderLayout.CENTER);
		
		ActionListener cbActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String s = (String) comboBox.getSelectedItem();

                switch (s) {
                    case "Classement général":
					table = setTable();
                        scrollPane.setViewportView(table);
                        break;
                    case "Poule 1":
					table = setTablePoule(1);
                        scrollPane.setViewportView(table);
                    	break;
                    case "Poule 2":
					table = setTablePoule(2);
                        scrollPane.setViewportView(table);
                    	break;
                    case "Poule 3":
					table = setTablePoule(3);
                        scrollPane.setViewportView(table);
                    	break;
                    case "Poule 4":
					table = setTablePoule(4);
                        scrollPane.setViewportView(table);
                    	break;
                    case "Phase finale":
					table = setTablePhaseFinale();
                        scrollPane.setViewportView(table);
                        break;
                }
            }
        };
		comboBox.addActionListener(cbActionListener);
		
		table = setTable();
		scrollPane.setViewportView(table);
	}
	
	//retourn l'id du tournoi sélectionné dans la page arbitre tournoi
	private int getIdTournoiSelected() {
		try {
	        ResultSet selectTournoi = FonctionsSQL.select(NomTablesBDD.SAETOURNOI, "IDTOURNOI", "LIEU = '" + (String) Arbitre_Tournoi.getTable().getValueAt(Arbitre_Tournoi.getTable().getSelectedRow(), 0) + "' AND DATEETHEURE LIKE TO_DATE('" + (String) Arbitre_Tournoi.getTable().getValueAt(Arbitre_Tournoi.getTable().getSelectedRow(), 1) + "', 'YYYY-MM-DD')");
	        selectTournoi.next();
	        return selectTournoi.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
    }
	
	//retourne une JTable qui montre les équipes, leurs écuries et leurs points d'un tournoi
	private JTable setTable() {
		try {
			String columns[] = {"Ecuries", "Equipes", "Points"};
	        ResultSet countParticipant = FonctionsSQL.select(NomTablesBDD.SAEPARTICIPER, "count(*)", "IDTOURNOI = " + getIdTournoiSelected());
	        countParticipant.next();
	        String data[][] = new String[countParticipant.getInt(1)][3];
	        ResultSet res = FonctionsSQL.select("saeparticiper, saeequipe", "saeequipe.NOM, saeequipe.NOM_2, saeparticiper.classementFinal", 
	                                            "saeparticiper.IDTOURNOI = " + getIdTournoiSelected() + " AND saeparticiper.NOM = saeequipe.NOM ORDER BY saeparticiper.classementFinal DESC");
	        int i = 0;
	        while (res.next()) {
	            data[i][0] = res.getString(2);
	            data[i][1] = res.getString(1);
	            data[i][2] = res.getString(3);
	            i++;
	        }
	        model = new DefaultTableModel(data, columns);
	        JTable returnTable = new JTable(model);
	        return returnTable;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }
	
	//retourne une JTable qui montre les équipes, leurs écuries et leurs points d'une poule d'un tournoi 
	private JTable setTablePoule(int numPoule) {
		try {
	        String columns[] = {"Ecuries", "Equipes", "Points"};
	        String data[][] = new String[4][3];
	        ResultSet res = FonctionsSQL.select("saeconcourir, saepoule, saeequipe", "saeequipe.NOM, saeequipe.NOM_2, saeconcourir.classementpoule", 
	                                            "saepoule.IDTOURNOI = " + getIdTournoiSelected() + " AND saepoule.numero = " + numPoule + " AND saepoule.idpoule = saeconcourir.idpoule AND saeconcourir.NOM = saeequipe.NOM ORDER BY saeconcourir.classementpoule desc");
	        int i = 0;
	        while (res.next()) {
	            data[i][0] = res.getString(2);
	            data[i][1] = res.getString(1);
	            data[i][2] = res.getString(3);
	            i++;
	        }
	        model = new DefaultTableModel(data, columns);
	        JTable returnTable = new JTable(model);
	        return returnTable;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }
	
	//retourne une JTable qui montre les équipes, leurs écuries et leurs points d'une phase finale d'un tournoi
	public JTable setTablePhaseFinale() {
		try {
			String columns[] = {"Ecuries", "Equipes", "Points"};
			String data[][] = new String[4][3];
			ResultSet res = FonctionsSQL.select("saesequalifier, saephasefinale, saeequipe, saetournoi", "saeequipe.NOM, saeequipe.NOM_2, saesequalifier.classementphasefinale", 
	                "saetournoi.IDTOURNOI = " + getIdTournoiSelected() + " AND saephasefinale.idphasefinale = saesequalifier.idphasefinale AND saesequalifier.NOM = saeequipe.NOM ORDER BY saesequalifier.classementphasefinale desc");
			int i = 0;
			while (res.next()) {
				data[i][0] = res.getString(2);
	            data[i][1] = res.getString(1);
	            data[i][2] = res.getString(3);
	            i++;
	        }
	        model = new DefaultTableModel(data, columns);
	        JTable returnTable = new JTable(model);
	        return returnTable;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//Renvoie un String contenant la date et l'heure d'un tournoi
	private String getDateEtHeureTournoi() {
		try {
			ResultSet selectTournoi = FonctionsSQL.select(NomTablesBDD.SAETOURNOI, "DATEETHEURE", "LIEU = '" + (String) Arbitre_Tournoi.getTable().getValueAt(Arbitre_Tournoi.getTable().getSelectedRow(), 0) + "' AND DATEETHEURE LIKE TO_DATE('" + (String) Arbitre_Tournoi.getTable().getValueAt(Arbitre_Tournoi.getTable().getSelectedRow(), 1) + "', 'YYYY-MM-DD')");
	        selectTournoi.next();
	        return selectTournoi.getString(1);
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//retourne un string contenant le nom d'un jeu et le lieu du tournoi de ce jeu
	private String getJeuEtLieu() {
		try {
			ResultSet selectJeu = FonctionsSQL.select("saejeu, saetournoi, saeconcerner", "saejeu.nom", "saetournoi.idtournoi = '" + getIdTournoiSelected() + "' AND saetournoi.idtournoi = saeconcerner.idtournoi AND saejeu.nom = saeconcerner.nom");
	        selectJeu.next();
	        ResultSet selectLieu = FonctionsSQL.select("saejeu, saetournoi, saeconcerner", "saetournoi.lieu", "saetournoi.idtournoi = '" + getIdTournoiSelected() + "'");
	        selectLieu.next();
	        return selectJeu.getString(1) + " - " + selectLieu.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}