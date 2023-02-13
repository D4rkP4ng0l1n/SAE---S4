package Modele;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import Controleur.ControleurArbitre;
import Controleur.ControleurEcurie;
import Controleur.ControleurEsporter;

public class MyRendererAndEditor implements TableCellRenderer, TableCellEditor {
	private JButton btn;
	private int row;

	public MyRendererAndEditor(JTable table, String choixBouton, ControleurEsporter controleur, ControleurEcurie controleurE, ControleurArbitre controleurA) {
		btn = new JButton(choixBouton);
		if (controleur == null && controleurE == null) {
			btn.addActionListener(controleurA); 
		} else if (controleur == null && controleurA == null) {
			btn.addActionListener(controleurE); 
		} else if (controleurE == null && controleurA == null) {
			btn.addActionListener(controleur); 
		}
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object  value, boolean isSelected, boolean hasFocus, int row, int column) {
		return btn;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		this.row = row;
		return btn;
	}

	@Override
	public Object getCellEditorValue() { return true; }

	@Override
	public boolean isCellEditable(EventObject anEvent) { return true; }

	@Override
	public boolean shouldSelectCell(EventObject anEvent) { return true; }

	@Override
	public boolean stopCellEditing() { return true; }

	@Override
	public void cancelCellEditing() {}

	@Override
	public void addCellEditorListener(CellEditorListener l) {}

	@Override
	public void removeCellEditorListener(CellEditorListener l) {}


}