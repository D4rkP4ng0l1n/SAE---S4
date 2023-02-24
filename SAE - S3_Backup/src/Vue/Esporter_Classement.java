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

@SuppressWarnings("serial")
public class Esporter_Classement extends JPanel {

	private JTable table;
	private ControleurEsporter controleur = new ControleurEsporter(this, EtatEsporter.JEU);
	private static DefaultTableModel model;

	public Esporter_Classement(String nomJeu) {
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
		BtnAccueil.addActionListener(controleur);
		panel_4.add(BtnAccueil);

		JButton BtnTournois = new JButton("Tournois");
		BtnTournois.addActionListener(controleur);
		panel_4.add(BtnTournois);

		JButton BtnEcurie = new JButton("Ecuries");
		BtnEcurie.addActionListener(controleur);
		panel_4.add(BtnEcurie);

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

		JLabel lblNewLabel_2 = new JLabel("Classement Mondial");
		panel_7.add(lblNewLabel_2);

		JPanel panel_8 = new JPanel();
		panel_5.add(panel_8, BorderLayout.CENTER);
		panel_8.setLayout(new BorderLayout(0, 0));

		JPanel panel_9 = new JPanel();
		panel_8.add(panel_9, BorderLayout.NORTH);

		JPanel panel_10 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_10.getLayout();
		flowLayout_3.setHgap(25);
		panel_9.add(panel_10);

		JLabel lblNewLabel_3 = new JLabel();
		lblNewLabel_3.setText(nomJeu);
		panel_10.add(lblNewLabel_3);

		JScrollPane scrollPane = new JScrollPane();
		panel_8.add(scrollPane, BorderLayout.CENTER);

		table = initialiseJTable(nomJeu);
		scrollPane.setViewportView(table);
	}

	public JTable initialiseJTable(String nomJeu) {
		try {
			nomJeu = "'" + nomJeu + "'";
			String columns[] = { "Ecurie" , "Equipe" , "Points", "Classement" };
			ResultSet count = FonctionsSQL.select("saeequipe", "count(*)", "nom_1 = " + nomJeu + " order by NBPOINTS DESC");
			count.next();
			String data[][] = new String[count.getInt(1)][4];
			ResultSet selectEquipe = FonctionsSQL.select("saeequipe", "*", "nom_1 = " + nomJeu + " order by NBPOINTS DESC");
			int i = 0;
			while (selectEquipe.next()) {
				data[i][0] = selectEquipe.getString(5);
				data[i][1] = selectEquipe.getString(1);
				data[i][2] = selectEquipe.getString(2);
				data[i][3] = String.valueOf(i + 1);
				i++;
			}
			model = new DefaultTableModel(data, columns);
			JTable returnTable =  new JTable(model);
			return returnTable;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}