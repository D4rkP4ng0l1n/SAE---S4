package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;

import Modele.Compte;
import Modele.FonctionsSQL;
import Vue.ApplicationEsporter;
import Vue.Arbitre_Accueil;
import Vue.Ecurie_Accueil;
import Vue.Ecurie_CreerEcurie;
import Vue.Esporter_Accueil;
import Vue.PageAccueil;
import Vue.PageChangerMdp;
import Vue.PageConnexion;
import Vue.PageInscription;

public class ControleurConnexion implements ActionListener {
	
	private PageConnexion vue;
	
	// Constructeur du controleur
	public ControleurConnexion(PageConnexion vue) { 
		this.vue = vue;
	}
	
	// Retourne True si l'écurie existe
	public boolean verifEcurie() { 
		ResultSet verifEcurie;
		try {
			verifEcurie = FonctionsSQL.select("saecompte, CRJ3957A.saeecurie", "count(nom)" , ApplicationEsporter.idCompte + " = saeecurie.idcompte");
			verifEcurie.next();
			if (verifEcurie.getInt(1) > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// Défini les actions a effectuer selon le bouton et selon la page sur laquelle on se trouve
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if (b.getText() == "Pas de compte?") {
			ApplicationEsporter.changerDePage(new PageInscription());
		} 
		if (b.getText()=="Retour") {
			ApplicationEsporter.changerDePage(new PageAccueil());
		}
		if (b.getText() == "Changer de mot de passe ?") {
			ApplicationEsporter.changerDePage(new PageChangerMdp());
		} else {
			if (b.getText() == "Se connecter") {
				try {
					if(Compte.compteExiste(this.vue.getNomUtilisateur())) {
						Compte.chargerCompte(this.vue.getNomUtilisateur(), this.vue.getMdp());
						switch(ApplicationEsporter.idTypeCompte) { // Selon le type de compte, on redirige l'utilisateur vers sa page accueil défini
						case 1:
							ApplicationEsporter.changerDePage(new Esporter_Accueil());
							break;
						case 2:
							ApplicationEsporter.changerDePage(new Arbitre_Accueil());
							break;
						case 3:
							if (verifEcurie()) {
								ApplicationEsporter.changerDePage(new Ecurie_Accueil());
							} else {
								ApplicationEsporter.changerDePage(new Ecurie_CreerEcurie());
							}
							break;

						}
					} else {
						this.vue.setMessage("Nom d'utilisateur ou mot de passe incorrect"); // Message d'erreur en cas de problème
					}
				} catch (Exception e1) {
					this.vue.setMessage("Nom d'utilisateur ou mot de passe incorrect"); // Message d'erreur en cas de problème
				}
			}
		}
		
	}
	
	
}