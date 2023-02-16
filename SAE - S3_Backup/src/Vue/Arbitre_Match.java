package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Controleur.ControleurArbitre;
import Controleur.ControleurArbitre.EtatArbitre;
import Modele.FonctionsSQL;
import Modele.MyRendererAndEditor;


@SuppressWarnings("serial")
public class Arbitre_Match extends JPanel{
	private static JTable table;
	private DefaultTableModel model;
	private ControleurArbitre controleur = new ControleurArbitre(this, EtatArbitre.MATCHS);
	private JScrollPane scrollPane;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Arbitre_Match() {
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

		JLabel lblNewLabel_2 = new JLabel("Matchs");
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
		try {
			lblNewLabel_2_1 = new JLabel(getDateEtHeureTournoi());
			Bandeau_1.add(lblNewLabel_2_1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		JPanel Bandeau_2 = new JPanel();
		@SuppressWarnings("unused")
		FlowLayout flowLayout_3 = (FlowLayout) Bandeau_2.getLayout();
		Bandeau_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_4.add(Bandeau_2);

		JLabel lblNewLabel_2_2;
		try {
			lblNewLabel_2_2 = new JLabel(getJeuEtLieu());
			Bandeau_2.add(lblNewLabel_2_2);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		JPanel Bandeau_3 = new JPanel();
		@SuppressWarnings("unused")
		FlowLayout flowLayout_2 = (FlowLayout) Bandeau_3.getLayout();
		Bandeau_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_4.add(Bandeau_3);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Poule 1", "Poule 2", "Poule 3", "Poule 4", "Phase finale"}));
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

		JPanel panel_15 = new JPanel();
		panel_13.add(panel_15, BorderLayout.WEST);

		JPanel panel_16 = new JPanel();
		panel_13.add(panel_16, BorderLayout.NORTH);

		JPanel panel_17 = new JPanel();
		panel_13.add(panel_17, BorderLayout.EAST);

		JPanel panel_18 = new JPanel();
		panel_13.add(panel_18, BorderLayout.CENTER);
		panel_18.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		panel_18.add(scrollPane, BorderLayout.CENTER);

		ActionListener cbActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String s = (String) comboBox.getSelectedItem();

				switch (s) {
				case "Poule 1":
					try {
						table = setTablePoule(1);
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
					scrollPane.setViewportView(table);
					break;
				case "Poule 2":
					try {
						table = setTablePoule(2);
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
					scrollPane.setViewportView(table);
					break;
				case "Poule 3":
					try {
						table = setTablePoule(3);
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
					scrollPane.setViewportView(table);
					break;
				case "Poule 4":
					try {
						table = setTablePoule(4);
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
					scrollPane.setViewportView(table);
					break;
				case "Phase finale":
					try {
						table = setTablePhaseFinale();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
					scrollPane.setViewportView(table);
					break;
				}
			}
		};
		comboBox.addActionListener(cbActionListener);

		try {
			table = setTablePoule(1);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		scrollPane.setViewportView(table);
	}

	private int getIdTournoiSelected() throws SQLException {
		ResultSet selectTournoi = FonctionsSQL.select("saetournoi", "IDTOURNOI", "LIEU = '" + (String) Arbitre_Tournoi.getTable().getValueAt(Arbitre_Tournoi.getTable().getSelectedRow(), 0) + "' AND DATEETHEURE LIKE TO_DATE('" + (String) Arbitre_Tournoi.getTable().getValueAt(Arbitre_Tournoi.getTable().getSelectedRow(), 1) + "', 'YYYY-MM-DD')");
		selectTournoi.next();
		return selectTournoi.getInt(1);
	}

	private JTable setTablePoule(int numPoule) throws SQLException {
		String columns[] = {" ", "Equipe 1", "Equipe 2", "  ", "Equipe victorieuse"};
		String data[][] = new String[6][5];
		ResultSet idMatchs = FonctionsSQL.select("saepartiepoule pp, saepoule p", "pp.id_partiepoule", "p.idtournoi = " + getIdTournoiSelected() + " and p.idpoule = pp.idpoule and p.numero = " + numPoule);
		int i = 0;
		while (idMatchs.next()) {
			ResultSet equipeVictorieuse = FonctionsSQL.select("saepartiepoule", "resultat", "id_partiepoule = " + idMatchs.getInt(1));
			equipeVictorieuse.next();
			data[i][4] = equipeVictorieuse.getString(1);
			ResultSet equipes = FonctionsSQL.select("saecompetiter", "nom", "id_partiepoule = " + idMatchs.getInt("id_partiepoule"));
			int j = 1;
			while (equipes.next()) {
				data[i][j] = equipes.getString("nom"); 
				j = 2;
			}
			i++;
		}
		model = new DefaultTableModel(data, columns);
		JTable returnTable = new JTable(model);
		returnTable.getColumn(" ").setCellRenderer(new MyRendererAndEditor(new JTable(), "Victoire équipe 1", null, null, controleur));
		returnTable.getColumn(" ").setCellEditor(new MyRendererAndEditor(new JTable(), "Victoire équipe 1", null, null, controleur));
		returnTable.getColumn("  ").setCellRenderer(new MyRendererAndEditor(new JTable(), "Victoire équipe 2", null, null, controleur));
		returnTable.getColumn("  ").setCellEditor(new MyRendererAndEditor(new JTable(), "Victoire équipe 2", null, null, controleur));
		return returnTable;
	}

	public JTable setTablePhaseFinale() throws SQLException {
        String columns[] = {" ", "Equipe 1", "Equipe 2", "  ", "Equipe victorieuse"};
        String data[][] = new String[6][5];
        ResultSet idMatchs = FonctionsSQL.select("saepartiephasefinale pf, saephasefinale f, saetournoi t", "pf.id_partiephasefinale", "t.idtournoi = " + getIdTournoiSelected() + " and t.idphasefinale = f.idphasefinale and f.idphasefinale = pf.idphasefinale");
        int i = 0;
        while (idMatchs.next()) {
            ResultSet equipeVictorieuse = FonctionsSQL.select("saepartiephasefinale", "resultat", "id_partiephasefinale = " + idMatchs.getInt(1));
            equipeVictorieuse.next();
            data[i][4] = equipeVictorieuse.getString(1);
            ResultSet equipes = FonctionsSQL.select("saecompetiterphasefinale", "nom", "id_partiephasefinale = " + idMatchs.getInt("id_partiephasefinale"));
            int j = 1;
            while (equipes.next()) {
                data[i][j] = equipes.getString("nom"); 
                j = 2;
            }
            i++;
        }
        model = new DefaultTableModel(data, columns);
        JTable returnTable = new JTable(model);
        returnTable.getColumn(" ").setCellRenderer(new MyRendererAndEditor(new JTable(), "Victoire équipe 1 ", null, null, controleur));
        returnTable.getColumn(" ").setCellEditor(new MyRendererAndEditor(new JTable(), "Victoire équipe 1 ", null, null, controleur));
        returnTable.getColumn("  ").setCellRenderer(new MyRendererAndEditor(new JTable(), "Victoire équipe 2 ", null, null, controleur));
        returnTable.getColumn("  ").setCellEditor(new MyRendererAndEditor(new JTable(), "Victoire équipe 2 ", null, null, controleur));
        return returnTable;
    }

	private String getDateEtHeureTournoi() throws SQLException {
		ResultSet selectTournoi = FonctionsSQL.select("saetournoi", "DATEETHEURE", "LIEU = '" + (String) Arbitre_Tournoi.getTable().getValueAt(Arbitre_Tournoi.getTable().getSelectedRow(), 0) + "' AND DATEETHEURE LIKE TO_DATE('" + (String) Arbitre_Tournoi.getTable().getValueAt(Arbitre_Tournoi.getTable().getSelectedRow(), 1) + "', 'YYYY-MM-DD')");
		selectTournoi.next();
		return selectTournoi.getString(1);
	}

	private String getJeuEtLieu() throws SQLException {
		ResultSet selectJeu = FonctionsSQL.select("saejeu, saetournoi, saeconcerner", "saejeu.nom", "saetournoi.idtournoi = '" + getIdTournoiSelected() + "' AND saetournoi.idtournoi = saeconcerner.idtournoi AND saejeu.nom = saeconcerner.nom");
		selectJeu.next();
		ResultSet selectLieu = FonctionsSQL.select("saejeu, saetournoi, saeconcerner", "saetournoi.lieu", "saetournoi.idtournoi = '" + getIdTournoiSelected() + "'");
		selectLieu.next();
		return selectJeu.getString(1) + " - " + selectLieu.getString(1);
	}

	public static String getEquipe(int num) {
		return (String) table.getValueAt(table.getSelectedRow(), num);
	}

	public void updateTable() {
		try {
			table = setTablePoule(comboBox.getSelectedIndex() + 1);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		scrollPane.setViewportView(table);
	}

	public static JTable getTable() {
		return table;
	}

	public void updateTableFinale() {
		try {
			table = setTablePhaseFinale();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		scrollPane.setViewportView(table);
	}
}