package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Modele.Compte;
import Vue.ApplicationEsporter;
import Vue.PageConnexion;
import Vue.PageInscription;

public class ControleurInscription implements ActionListener {
	
	private PageInscription vue;
	
	// Constructeur du controleur
	public ControleurInscription(PageInscription vue) {
		this.vue = vue;
	}

	// Défini les actions a effectuer selon le bouton et selon la page sur laquelle on se trouve
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if (b.getText().equals("Déjà un compte?")) {
			ApplicationEsporter.changerDePage(new PageConnexion());
		} else {
			if (b.getText().equals("S'inscrire")) {
				try {
					// Si le compte n'existe pas, le compte est créé et l'utilisateur est redirigé vers la page de connexion
					if (Compte.creerCompte(this.vue.getNomUtilisateur(), this.vue.getMdp(), Compte.Type.ECURIE) == 1) {
						ApplicationEsporter.changerDePage(new PageConnexion());
					} else {
						this.vue.setMessage("Cet utilisateur existe déjà"); // Affiche un message d'erreur
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					this.vue.setMessage("Nom d'utilisateur ou mot de passe invalide"); // Affiche un message d'erreur si le mdp ou le nom d'utilisateur est vide
				}
			}
		}
	}
}
