package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Vue.ApplicationEsporter;
import Vue.PageConnexion;

public class ControleurAccueil implements ActionListener {

	public ControleurAccueil() { }

	// Défini les actions a effectuer selon le bouton et selon la page sur laquelle on se trouve
	@Override
	public void actionPerformed(ActionEvent e) {		
		ApplicationEsporter.changerDePage(new PageConnexion());
	}
}