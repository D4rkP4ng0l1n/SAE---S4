package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Modele.Compte;
import Vue.ApplicationEsporter;
import Vue.PageChangerMdp;
import Vue.PageConnexion;


public class ControleurChangerMdp implements ActionListener {

	private PageChangerMdp vue;

	// Constructeur du controleur
	public ControleurChangerMdp(PageChangerMdp vue) {
		this.vue = vue;
	}
	
	// Défini les actions a effectuer selon le bouton et selon la page sur laquelle on se trouve
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if (b.getText().equals("Valider le mot de passe")) {
			if(!this.vue.getNomUtilisateur().isEmpty() && !this.vue.getMdp().isEmpty() && !this.vue.getNouveauMdp().isEmpty()) {
				try {
					if(Compte.compteExiste(this.vue.getNomUtilisateur()) && Compte.mdpOK(this.vue.getNomUtilisateur(), this.vue.getMdp())) {
						Compte.changerMdp(this.vue.getNomUtilisateur(), this.vue.getMdp(), this.vue.getNouveauMdp());
						ApplicationEsporter.changerDePage(new PageConnexion());
					} else {
						// Message d'erreur en cas d'utilisateur ou de mot de passe incorrect
						this.vue.setMessage("Nom d'utilisateur ou mot de passe incorrect");
					}
				} catch (Exception e1) {
					// Message d'erreur en cas d'utilisateur ou de mot de passe incorrect
					this.vue.setMessage("Nom d'utilisateur ou mot de passe incorrect");
				}
			} else {
				// Message d'erreur en cas d'utilisateur ou de mot de passe incorrect
				this.vue.setMessage("Nom d'utilisateur ou mot de passe incorrect");
			}
		}
		if (b.getText().equals("Déjà un compte?")) {
			ApplicationEsporter.changerDePage(new PageConnexion());
		}
		
	}
}
