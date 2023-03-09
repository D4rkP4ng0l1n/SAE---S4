package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Controleur.ControleurEcurie;
import Controleur.ControleurEcurie.EtatEcurie;
import Modele.BDD.NomTablesBDD;
import Modele.FonctionsSQL;
import Modele.MyRendererAndEditor;

import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class Ecurie_Equipes extends JPanel {
	
	private ControleurEcurie controleur = new ControleurEcurie(this, EtatEcurie.EQUIPES);
	
	private static JTable tableEcurie;
	private static DefaultTableModel model;

	public Ecurie_Equipes() {
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
		
		JLabel lblNewLabel_1 = new JLabel(controleur.getNomEcurie());
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
		
		JButton btnNewButton_3 = new JButton("Mes \u00E9quipes");
		MenuNavigation.add(btnNewButton_3);
		btnNewButton_3.addActionListener(controleur);
		
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
		
		JLabel lblNewLabel_2 = new JLabel("Mes Equipes");
		Bandeau.add(lblNewLabel_2);
		
		JPanel panel = new JPanel();
		panel_5.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4, BorderLayout.WEST);
		
		JPanel panel_7 = new JPanel();
		panel.add(panel_7, BorderLayout.NORTH);
		
		JPanel panel_8 = new JPanel();
		panel.add(panel_8, BorderLayout.EAST);
		
		JPanel panel_9 = new JPanel();
		panel.add(panel_9, BorderLayout.SOUTH);
		
		JPanel panel_10 = new JPanel();
		panel_10.setBorder(null);
		panel.add(panel_10, BorderLayout.CENTER);
		panel_10.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_11 = new JPanel();
		@SuppressWarnings("unused")
		FlowLayout flowLayout = (FlowLayout) panel_11.getLayout();
		panel_11.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_10.add(panel_11, BorderLayout.SOUTH);
		
		JButton btnNewButton_4 = new JButton("Ajouter une Equipe");
		btnNewButton_4.addActionListener(controleur);
		panel_11.add(btnNewButton_4);
		
		JPanel panel_12 = new JPanel();
		panel_10.add(panel_12, BorderLayout.CENTER);
		panel_12.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_13 = new JPanel();
		panel_12.add(panel_13, BorderLayout.WEST);
		
		JPanel panel_14 = new JPanel();
		panel_12.add(panel_14, BorderLayout.SOUTH);
		
		JPanel panel_15 = new JPanel();
		panel_12.add(panel_15, BorderLayout.EAST);
		
		JPanel panel_16 = new JPanel();
		panel_12.add(panel_16, BorderLayout.NORTH);
		
		JPanel Body = new JPanel();
		Body.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_12.add(Body, BorderLayout.CENTER);
		Body.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		Body.add(scrollPane, BorderLayout.CENTER);
		
		tableEcurie = this.setTable(new JTable());
		scrollPane.setViewportView(tableEcurie);
	}
	
	public JTable setTable(JTable table) {
		try {
			String columns[] = { "Nom de l'équipe" , "Jeu" , " " };
			ResultSet count = FonctionsSQL.select(NomTablesBDD.SAEEQUIPE, "count(*)", "Nom_2 = '" + controleur.getNomEcurie() + "'");
			count.next();
			String data[][] = new String[count.getInt(1)][4];
			ResultSet selectEquipes = FonctionsSQL.select(NomTablesBDD.SAEEQUIPE, "*", "Nom_2 = '" + controleur.getNomEcurie() + "'");
			int i = 0;
			while (selectEquipes.next()) {
				data[i][0] = selectEquipes.getString(1);
				data[i][1] = selectEquipes.getString(4);
				i++;
			}
			model = new DefaultTableModel(data, columns);
			JTable returnTable = new JTable(model);
			returnTable.getColumn(" ").setCellRenderer(new MyRendererAndEditor(returnTable, "Acceder", null, controleur, null));
			returnTable.getColumn(" ").setCellEditor(new MyRendererAndEditor(returnTable, "Acceder", null, controleur, null));
			return returnTable;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static JTable getTable() {
		return tableEcurie;
	}
	
	public static void DelRow(int row) {
		model = (DefaultTableModel) tableEcurie.getModel();
		model.removeRow(row);
	}

}