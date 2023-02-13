package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import Controleur.ControleurEsporter;
import Controleur.ControleurEsporter.EtatEsporter;
import Modele.FonctionsSQL;
import Modele.MyRendererAndEditor;

public class Esporter_Ecuries extends JPanel {

	private static JTable tableEcurie;
	private static DefaultTableModel model;

	private ControleurEsporter controleur = new ControleurEsporter(this, EtatEsporter.ECURIE);

	public Esporter_Ecuries() throws SQLException {
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

		JLabel lblNewLabel_2 = new JLabel("Ecuries");
		panel_7.add(lblNewLabel_2);

		JScrollPane scrollPane = new JScrollPane();
		panel_5.add(scrollPane, BorderLayout.CENTER);

		tableEcurie = this.setTable(new JTable());
		tableEcurie.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableEcurie.setToolTipText("");
		tableEcurie.getColumnModel().getColumn(0).setResizable(false);
		scrollPane.setViewportView(tableEcurie);
	}

	public JTable setTable(JTable table) throws SQLException {
		String columns[] = { "Nom de l'écurie" , "Nom du CEO" , " " , "  " };
		ResultSet count = FonctionsSQL.select("saeecurie", "count(*)", "");
		count.next();
		String data[][] = new String[count.getInt(1)][4];
		ResultSet res = FonctionsSQL.select("saeecurie", "*", "");
		int i = 0;
		while (res.next()) {
			data[i][0] = res.getString(1);
			data[i][1] = res.getString(2);
			i++;
		}
		model = new DefaultTableModel(data, columns);
		JTable returnTable = new JTable(model);
		returnTable.getColumn(" ").setCellRenderer(new MyRendererAndEditor(returnTable, "Acceder", controleur, null, null));
		returnTable.getColumn(" ").setCellEditor(new MyRendererAndEditor(returnTable, "Acceder", controleur, null, null));
		returnTable.getColumn("  ").setCellRenderer(new MyRendererAndEditor(returnTable, "Supprimer", controleur, null, null));
		returnTable.getColumn("  ").setCellEditor(new MyRendererAndEditor(returnTable, "Supprimer", controleur, null, null));
		return returnTable;
	}
	
	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 3;
	}

	public static JTable getTable() {
		return tableEcurie;
	}

	public static void DelRow(int row) {
		model = (DefaultTableModel) tableEcurie.getModel();
		model.removeRow(row);
	}
}
