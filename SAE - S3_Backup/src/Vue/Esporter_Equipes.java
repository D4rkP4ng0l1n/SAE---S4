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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Controleur.ControleurEsporter;
import Modele.FonctionsSQL;
import Modele.MyRendererAndEditor;

@SuppressWarnings("serial")
public class Esporter_Equipes extends JPanel {
	
	private ControleurEsporter controleur = new ControleurEsporter(this, ControleurEsporter.EtatEsporter.EQUIPE);
	
	private static JTable tableEquipe;
	private static DefaultTableModel model;

	public Esporter_Equipes() {
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
		btnNewButton.addActionListener(controleur);
		
		JPanel panel_3 = new JPanel();
		add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new GridLayout(1, 4, 0, 0));
		
		JButton BtnAccueil = new JButton("Accueil");
		panel_4.add(BtnAccueil);
		BtnAccueil.addActionListener(controleur);
		
		JButton BtnTournois = new JButton("Tournois");
		panel_4.add(BtnTournois);
		BtnTournois.addActionListener(controleur);
		
		JButton BtnEcurie = new JButton("Ecuries");
		panel_4.add(BtnEcurie);
		BtnEcurie.addActionListener(controleur);
		
		JButton BtnJeux = new JButton("Jeux");
		BtnJeux.addActionListener(controleur);
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
		
		JLabel lblNewLabel_2 = new JLabel("Equipes");
		panel_7.add(lblNewLabel_2);
		
		JPanel panel_8 = new JPanel();
		panel_5.add(panel_8, BorderLayout.CENTER);
		panel_8.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_10 = new JPanel();
		panel_8.add(panel_10, BorderLayout.SOUTH);
		
		JButton btnNewButton_1 = new JButton("Modifier l'\u00E9curie");
		panel_10.add(btnNewButton_1);
		btnNewButton_1.addActionListener(controleur);
		
		JPanel panel_9 = new JPanel();
		panel_8.add(panel_9, BorderLayout.CENTER);
		panel_9.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_9.add(scrollPane);
		
		tableEquipe = this.setTable(new JTable());
		scrollPane.setViewportView(tableEquipe);
	}

	public JTable setTable(JTable table) {
		try {
			String columns[] = { "Nom de l'équipe" , "Nombre de points" , "Jeu" , " " , "  " };
			ResultSet count = FonctionsSQL.select("saeequipe", "count(*)", "NOM_2 = '" + ApplicationEsporter.nomEcurie + "'");
			count.next();
			String data[][] = new String[count.getInt(1)][5];
			ResultSet res = FonctionsSQL.select("saeequipe", "Nom, nbpoints, nom_1", "NOM_2 = '" + ApplicationEsporter.nomEcurie + "'");
			int i = 0;
			while (res.next()) {
				data[i][0] = res.getString(1);
				data[i][1] = res.getString(2);
				data[i][2] = res.getString(3);
				i++;
			}
			model = new DefaultTableModel(data, columns);
			JTable returnTable = new JTable(model);
			returnTable.getColumn(" ").setCellRenderer(new MyRendererAndEditor(returnTable, "Acceder", controleur, null, null));
			returnTable.getColumn(" ").setCellEditor(new MyRendererAndEditor(returnTable, "Acceder", controleur, null, null));
			returnTable.getColumn("  ").setCellRenderer(new MyRendererAndEditor(returnTable, "Supprimer", controleur, null, null));
			returnTable.getColumn("  ").setCellEditor(new MyRendererAndEditor(returnTable, "Supprimer", controleur, null, null));
			return returnTable;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static JTable getTable() {
		return tableEquipe;
	}
}
