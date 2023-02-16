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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Controleur.ControleurEcurie;
import Modele.FonctionsSQL;

import javax.swing.JScrollPane;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ecurie_PreInscription extends JPanel {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private ControleurEcurie controleur = new ControleurEcurie(this, ControleurEcurie.EtatEcurie.PREINSCRIPTION_TOURNOI);
	
	private static JTable tableEquipes;
	private DefaultTableModel model;
	private JLabel Jeux;

	public Ecurie_PreInscription() throws SQLException {
		setLayout(new BorderLayout(0,0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel_Arbitre_0 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_Arbitre_0.getLayout();
		flowLayout.setVgap(11);
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
		btnNewButton.addActionListener(controleur);
		
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
        panel_9.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_8.add(panel_9, BorderLayout.NORTH);
        panel_9.setLayout(new BorderLayout(0, 0));
        
        Jeux = new JLabel(setJeux());
        Jeux.setFont(new Font("Tahoma", Font.PLAIN, 15));
        Jeux.setHorizontalAlignment(SwingConstants.CENTER);
        panel_9.add(Jeux);
        
        JPanel panel_10 = new JPanel();
        panel_8.add(panel_10, BorderLayout.WEST);
        
        JPanel panel_11 = new JPanel();
        panel_8.add(panel_11, BorderLayout.SOUTH);
        
        JButton btnNewButton_4 = new JButton("S'inscrire");
        btnNewButton_4.addActionListener(controleur);
        panel_11.add(btnNewButton_4);
        if(getNbEquipesInscrites() >= ApplicationEsporter.NB_MAX_EQUIPE_PAR_TOURNOI || dejaInscrit() || getNbEquipesInscrites() >= ApplicationEsporter.NB_MAX_EQUIPE_PAR_TOURNOI) {
        	btnNewButton_4.setEnabled(false);
        } else {
        	btnNewButton_4.setEnabled(true);
        }
        
        JPanel panel_12 = new JPanel();
        panel_8.add(panel_12, BorderLayout.EAST);
        
        JPanel panel_13 = new JPanel();
        panel_13.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_8.add(panel_13, BorderLayout.CENTER);
        panel_13.setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollPane = new JScrollPane();
        panel_13.add(scrollPane, BorderLayout.CENTER);
        
        tableEquipes = setTable(new JTable());
        tableEquipes.setBorder(new LineBorder(new Color(0, 0, 0)));
        tableEquipes.setToolTipText("");
        scrollPane.setViewportView(tableEquipes);
    }
	
	private int getIdTournoiSelected() throws SQLException {
		ResultSet selectTournoi = FonctionsSQL.select("saetournoi", "IDTOURNOI", "LIEU = '" + (String) Ecurie_Tournoi.getTable().getValueAt(Ecurie_Tournoi.getTable().getSelectedRow(), 0) + "' AND DATEETHEURE LIKE TO_DATE('" + (String) Ecurie_Tournoi.getTable().getValueAt(Ecurie_Tournoi.getTable().getSelectedRow(), 1) + "', 'YYYY-MM-DD')");
		selectTournoi.next();
		ApplicationEsporter.idTournoi = "" + selectTournoi.getInt(1);
		return selectTournoi.getInt(1);
	}
	
	private int getNbEquipesInscrites() throws SQLException {
		ResultSet countParticipant = FonctionsSQL.select("saeparticiper", "count(*)", "IDTOURNOI = " + getIdTournoiSelected());
		countParticipant.next();
		return countParticipant.getInt(1);
	}
	
	private boolean dejaInscrit() throws SQLException {
		ResultSet res = FonctionsSQL.select("saeparticiper, CRJ3957A.saeequipe", "CRJ3957A.saeequipe.NOM_2", "CRJ3957A.saeparticiper.IDTOURNOI = " + getIdTournoiSelected() + " AND CRJ3957A.saeparticiper.NOM = CRJ3957A.saeequipe.NOM");
		res.next();
		try {
			return res.getString(1).equals(controleur.getNomEcurie());
		} catch (Exception e) {
			return false;
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
	
	private JTable setTable(JTable table) throws SQLException {
		String columns[] = {"Ecuries", "Equipes", "Points"};
		ResultSet countParticipant = FonctionsSQL.select("saeparticiper", "count(*)", "IDTOURNOI = " + getIdTournoiSelected());
		countParticipant.next();
		String data[][] = new String[countParticipant.getInt(1)][3];
		ResultSet res = FonctionsSQL.select("saeparticiper, CRJ3957A.saeequipe", "CRJ3957A.saeequipe.NOM, CRJ3957A.saeequipe.NOM_2, CRJ3957A.saeequipe.NBPOINTS", 
											"CRJ3957A.saeparticiper.IDTOURNOI = " + getIdTournoiSelected() + " AND CRJ3957A.saeparticiper.NOM = CRJ3957A.saeequipe.NOM ORDER BY CRJ3957A.saeequipe.NBPOINTS DESC");
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
	}

	public static JTable getTable() {
			return tableEquipes;
	}

}