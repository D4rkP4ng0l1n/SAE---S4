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
import Controleur.ControleurEsporter.EtatEsporter;
import Modele.FonctionsSQL;
import Modele.MyRendererAndEditor;

@SuppressWarnings("serial")
public class Esporter_Tournois extends JPanel{

	private static DefaultTableModel model;
	private static JTable tableTournois;

	private ControleurEsporter controleur = new ControleurEsporter(this, EtatEsporter.TOURNOI);

	public Esporter_Tournois() throws SQLException {
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

		JLabel lblNewLabel_2 = new JLabel("Tournois");
		panel_7.add(lblNewLabel_2);

		JPanel panel_8 = new JPanel();
		panel_5.add(panel_8, BorderLayout.CENTER);
		panel_8.setLayout(new BorderLayout(0, 0));

		JPanel panel_9 = new JPanel();
		panel_8.add(panel_9, BorderLayout.NORTH);

		JPanel panel_10 = new JPanel();
		panel_8.add(panel_10, BorderLayout.WEST);

		JPanel panel_11 = new JPanel();
		panel_8.add(panel_11, BorderLayout.SOUTH);

		JPanel panel_12 = new JPanel();
		panel_8.add(panel_12, BorderLayout.EAST);

		JPanel panel_13 = new JPanel();
		panel_13.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_8.add(panel_13, BorderLayout.CENTER);
		panel_13.setLayout(new BorderLayout(0, 0));



		JPanel panel_14 = new JPanel();
		panel_13.add(panel_14, BorderLayout.NORTH);

		JButton btnNewButton_4 = new JButton("Créer un tournoi");
		panel_14.add(btnNewButton_4);
		btnNewButton_4.addActionListener(controleur);

		JScrollPane scrollPane = new JScrollPane();
		panel_13.add(scrollPane, BorderLayout.CENTER);

		tableTournois = this.setTable(new JTable());
		tableTournois.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableTournois.setToolTipText("");
		tableTournois.getColumnModel().getColumn(1).setResizable(false);
		scrollPane.setViewportView(tableTournois);
	}

	private JTable setTable(JTable table) throws SQLException {
		String columns[] = { "Lieu" , "Date" , "Jeu(x)" , "Classement", " ", "  " };
		ResultSet selectCountTournoi = FonctionsSQL.select("saetournoi", "count(*)", "");
		selectCountTournoi.next();
		String data[][] = new String[selectCountTournoi.getInt(1)][5];
		ResultSet res = FonctionsSQL.select("saetournoi", "*", "");
		int i = 0;
		String datadateduTournoi="";
		String dateGalere;
		while (res.next()) {
			data[i][0] = res.getString(2);
			char[] dateduTournoi=  res.getDate(3).toString().toCharArray();
			if (dateduTournoi[0]=='1') {
				@SuppressWarnings("deprecation")
				int dateOk = res.getDate(3).getYear();
				dateOk = ((dateOk - 1977) * -1) + 2024;
				dateGalere = "" + dateOk;
				dateduTournoi[2]=dateGalere.charAt(2);
				dateduTournoi[3]=dateGalere.charAt(3);
			}
			dateduTournoi[0]='2';
			dateduTournoi[1]='0';
			datadateduTournoi="";
			for (char l: dateduTournoi) {
				datadateduTournoi= datadateduTournoi+l;
			}
			data[i][1] = datadateduTournoi;
			i++;
		}
		model = new DefaultTableModel(data, columns);
		JTable returnTable = new JTable(model);
		returnTable.getColumn("Jeu(x)").setCellRenderer(new MyRendererAndEditor(returnTable, "Voir le(s) jeu(x)", controleur, null, null));
		returnTable.getColumn("Jeu(x)").setCellEditor(new MyRendererAndEditor(returnTable, "Voir le(s) jeu(x)", controleur, null, null));
		returnTable.getColumn("Classement").setCellRenderer(new MyRendererAndEditor(returnTable, "Accéder", controleur, null, null));
		returnTable.getColumn("Classement").setCellEditor(new MyRendererAndEditor(returnTable, "Accéder", controleur, null, null));
		returnTable.getColumn(" ").setCellRenderer(new MyRendererAndEditor(returnTable, "Modifier", controleur, null, null));
		returnTable.getColumn(" ").setCellEditor(new MyRendererAndEditor(returnTable, "Modifier", controleur, null, null));
		returnTable.getColumn("  ").setCellRenderer(new MyRendererAndEditor(returnTable, "Supprimer", controleur, null, null));
		returnTable.getColumn("  ").setCellEditor(new MyRendererAndEditor(returnTable, "Supprimer", controleur, null, null));
		return returnTable;
	}

	public static JTable getTable() {
		return tableTournois;
	}
	
	public static void DelRow(int row) { // Supprime une ligne dans la table des tournois
		model = (DefaultTableModel) tableTournois.getModel();
		model.removeRow(row);
	}
}