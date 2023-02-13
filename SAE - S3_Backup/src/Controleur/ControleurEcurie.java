package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import Modele.Equipe;
import Modele.FonctionsSQL;
import Modele.Joueur;
import Vue.ApplicationEsporter;
import Vue.Arbitre_Tournoi;
import Vue.Ecurie_Accueil;
import Vue.Ecurie_AddJoueur;
import Vue.Ecurie_AddJoueur.Erreurs;
import Vue.Ecurie_CreationEquipe;
import Vue.Ecurie_CreerEcurie;
import Vue.Ecurie_Equipes;
import Vue.Ecurie_GestionEquipe;
import Vue.Ecurie_InfoTournoi;
import Vue.Ecurie_Inscription;
import Vue.Ecurie_PreInscription;
import Vue.Ecurie_Tournoi;
import Vue.PageAccueil;

public class ControleurEcurie extends FocusAdapter implements ActionListener {

	public enum EtatEcurie{CREATION, ACCUEIL, TOURNOI, PREINSCRIPTION_TOURNOI, INSCRIPTION_TOURNOI, EQUIPES, CREATIONEQUIPE, GESTIONEQUIPE, AJOUTERJOUEUR};

	private JPanel vue;
	private EtatEcurie etat;

	private String pathLogo;

	// Controleur de la classe Ecurie
	public ControleurEcurie(JPanel vue, EtatEcurie etat) {
		this.vue = vue;
		this.etat = etat;
	}

	// Retourne le nom de l'écurie sur laquelle on se trouve
	public String getNomEcurie() throws SQLException {
		ResultSet nomEcurie = FonctionsSQL.select("saeecurie", "nom", "idcompte = " + ApplicationEsporter.idCompte);
		nomEcurie.next();
		return nomEcurie.getString(1);
	}

	// Génération des poules
	private static void  genererPoules(String IdTournoi) {
		for (int i = 1; i <= 4;i++) { 
			try {
				String[] poule = {"" + FonctionsSQL.newIDPoule(), "''", "" + i, "" + IdTournoi};
				FonctionsSQL.insert("saepoule", poule);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// Liens entre les équipes et les poules (insertion dans la table saeconcourir)
		try {
			// Sélection des équipes de celles avec le plus de points à celles avec le moins
			ResultSet listeEquipe = FonctionsSQL.select("saeequipe e, CRJ3957A.saeparticiper p","e.nom"," e.nom=p.nom and p.idtournoi= " + ApplicationEsporter.idTournoi + " order by e.nbpoints desc,1");

			int pouleCounter = 1;
			while (listeEquipe.next()) {
				// Récupération d'un id de poule avec le bon numéro
				ResultSet IdPoule = FonctionsSQL.select("saepoule", "idpoule", "numero = '" + pouleCounter + "' and idtournoi = " + ApplicationEsporter.idTournoi);
				IdPoule.next();
				String[] equipe = {"'" + listeEquipe.getString("nom") + "'", "" + IdPoule.getInt("Idpoule"), "'0'"};
				FonctionsSQL.insert("saeconcourir", equipe);
				pouleCounter = (pouleCounter % 4) + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			genererMatchs(IdTournoi);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Génère les matchs pour les phases de poules
	private static void genererMatchs(String idTournoi) throws SQLException {
		ResultSet idPoule = FonctionsSQL.select("SAEPoule", "IDPoule", "IDTournoi = " + idTournoi);
		String[]idPoules = new String[100];
		int numero = 0;
		while(idPoule.next()) {
			idPoules[numero] = "" + idPoule.getInt(1);
			numero++;
		}
		for (int numPoule = 0; numPoule < 4; numPoule++) {
			ResultSet listeEquipe = FonctionsSQL.select("SAEConcourir", "nom", "IDPoule = " + idPoules[numPoule]);
			int k = 0;
			String[]equipes = new String[4];
			while (listeEquipe.next()) {
				equipes[k] = listeEquipe.getString(1);
				k++;
			}
			int nbMatch = equipes.length * (equipes.length - 1) / 2;
			String[]matchsEquipe1 = new String[nbMatch];
			String[]matchsEquipe2 = new String[nbMatch];
			int index = 0;
			for (int i = 0; i < equipes.length; i++) {
				for (int j = i+1; j < equipes.length; j++) {
					matchsEquipe1[index] = equipes[i];
					matchsEquipe2[index] = equipes[j];
					index ++;
				}
			}
			for (int i = 0; i < nbMatch; i++) {
				int id = FonctionsSQL.newIDPartiePoule();
				String[]match = { "" + id, "'aucune'", "" + idPoules[numPoule] };
				String[]equipe1 = { "'" + matchsEquipe1[i] + "'", "" + id };
				String[]equipe2 = { "'" + matchsEquipe2[i] + "'", "" + id };
				FonctionsSQL.insert("SAEpartiePoule", match);
				FonctionsSQL.insert("SAEcompetiter", equipe1);
				FonctionsSQL.insert("SAEcompetiter", equipe2);
			}
		}
	}

	// Methode pour faciliter la navigation depuis n'importe où en tant qu'Ecurie
	private Boolean changerDePage(JButton b) {
		if(b.getText() == "Déconnexion") {
			goDeconnexion();
		} 
		if(b.getText() == "Accueil") {
			goAccueil();
		}
		if(b.getText() == "Mes équipes") {
			goEquipes();
		}
		if(b.getText() == "Tournois"){
			goTournois();
		}
		return false;
	}

	// Méthode pour se déconnecter
	private void goDeconnexion() {
		ApplicationEsporter.f.setContentPane(new PageAccueil());
		ApplicationEsporter.f.validate();
	}

	// Méthode pour aller à la page d'accueil
	private void goAccueil() {
		try {
			ApplicationEsporter.f.setContentPane(new Ecurie_Accueil());
			ApplicationEsporter.f.validate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Méthode pour accéder aux équipes
	private void goEquipes() {
		try {
			ApplicationEsporter.f.setContentPane(new Ecurie_Equipes());
			ApplicationEsporter.f.validate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Méthode pour acéder aux tournois
	private void goTournois() {
		try {
			ApplicationEsporter.f.setContentPane(new Ecurie_Tournoi());
			ApplicationEsporter.f.validate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	// Défini les actions a effectuer selon le bouton et selon la page sur laquelle on se trouve
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if(!changerDePage(b)) {
			switch(this.etat) {
			case CREATION :
				if(b.getText() == "Ajouter un logo") {
					JFileChooser j = new JFileChooser();
					j.setCurrentDirectory(new File("Images"));
					j.setFileFilter(new FileNameExtensionFilter("PNG, JPG, GIF", "png", "jpg", "gif"));
					j.showOpenDialog(this.vue);
					try {
						String fileName = j.getSelectedFile().getName();
						File src = new File("src/images/" + fileName);
						if(! src.isFile()) {
							Files.copy(Paths.get(j.getSelectedFile().getAbsolutePath()), Paths.get("src/images/" + fileName));
						}
						Ecurie_CreerEcurie.setImage("src/images/" + fileName);
						this.pathLogo = "src/images/" + fileName;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if(b.getText() == "Valider") {
					if (! (Ecurie_CreerEcurie.labelsVide() && this.pathLogo == null)) {
						String[] aInserer = {"'" + Ecurie_CreerEcurie.getNomEcurie() + "'", "'" + Ecurie_CreerEcurie.getNomCEO() + "'", "'" + this.pathLogo + "'", "" + ApplicationEsporter.idCompte};
						try {
							FonctionsSQL.insert("saeecurie", aInserer);
							ApplicationEsporter.f.setContentPane(new PageAccueil());
							ApplicationEsporter.f.validate();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					} else {
						Ecurie_CreerEcurie.setMessage("Information(s) manquante(s)");
					}
				}
				break;
			case ACCUEIL:
				break;
			case TOURNOI:
				if(b.getText() == "S'inscrire") {
					try {
						ApplicationEsporter.f.setContentPane(new Ecurie_PreInscription());
						ApplicationEsporter.f.validate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				if(b.getText() == "Voir le(s) jeu(x)") {
					try {
						ResultSet jeux = FonctionsSQL.select("saeconcerner", "NOM", "IDTOURNOI = " + Ecurie_Inscription.getIdTournoiSelected());
						String afficherJeux = "Liste des jeux : \n";
						while(jeux.next()) {
							afficherJeux += "   - " + jeux.getString(1) + "\n";
						}
						JOptionPane.showMessageDialog(null, afficherJeux);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				if(b.getText() == "Accéder") {
					try {
						ApplicationEsporter.f.setContentPane(new Ecurie_InfoTournoi());
						ApplicationEsporter.f.validate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				break;
			case PREINSCRIPTION_TOURNOI:
				if(b.getText() == "S'inscrire") {
					try {
						ResultSet selectIDTournoi = FonctionsSQL.select("SAETournoi", "IDTournoi", "Lieu = '" + Ecurie_Tournoi.getTable().getValueAt(Ecurie_Tournoi.getTable().getSelectedRow(), 0)
								+ "' AND DATEETHEURE LIKE TO_DATE('" + Ecurie_Tournoi.getTable().getValueAt(Ecurie_Tournoi.getTable().getSelectedRow(), 1) + "', 'YYYY-MM-DD')");
						selectIDTournoi.next();
						ApplicationEsporter.idTournoi = selectIDTournoi.getString(1);
						ApplicationEsporter.f.setContentPane(new Ecurie_Inscription());
						ApplicationEsporter.f.validate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				break;
			case INSCRIPTION_TOURNOI:
				if(b.getText() == "S'inscrire") {
					int result = JOptionPane.showConfirmDialog(null,"Attention, l'inscription est définitive\n Confirmer l'inscription ?", "Confirmer l'inscription", JOptionPane.YES_NO_OPTION);
					if (result == 0) {
						try {
							String[]aInserer = { "'" + Ecurie_Inscription.getCombo() + "'", ApplicationEsporter.idTournoi, "0" }; 
							FonctionsSQL.insert("SAEparticiper", aInserer);
							JOptionPane.showMessageDialog(null, Ecurie_Inscription.getCombo() + " a bien été inscrite au tournoi !");
							ResultSet countNbEquipesInscrites = FonctionsSQL.select("SAEparticiper", "count(*)", "IDTournoi = " + ApplicationEsporter.idTournoi);
							countNbEquipesInscrites.next();
							if (countNbEquipesInscrites.getInt(1) >= 16) {
								JOptionPane.showMessageDialog(null, "Vous êtes le dernier inscit, les poules sont en cours de création\nVeuillez patienter");
								genererPoules(ApplicationEsporter.idTournoi);
							}
							ApplicationEsporter.f.setContentPane(new Ecurie_Tournoi());
							ApplicationEsporter.f.validate();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
				if(b.getText() == "Pas encore d'équipe ?") {
					try {
						ApplicationEsporter.f.setContentPane(new Ecurie_CreationEquipe());
						ApplicationEsporter.f.validate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				break;
			case EQUIPES:
				if(b.getText() == "Ajouter une Equipe") {
					try {
						ApplicationEsporter.f.setContentPane(new Ecurie_CreationEquipe());
						ApplicationEsporter.f.validate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				if(b.getText() == "Acceder") {
					try {
						ApplicationEsporter.f.setContentPane(new Ecurie_GestionEquipe());
						ApplicationEsporter.f.validate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				if(b.getText() == "Valider") {
					try {
						ApplicationEsporter.f.setContentPane(new Ecurie_Equipes());
						ApplicationEsporter.f.validate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				if(b.getText() == "Annuler") {
					try {
						ApplicationEsporter.f.setContentPane(new Ecurie_Equipes());
						ApplicationEsporter.f.validate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				break;
			case GESTIONEQUIPE:
				if(b.getText() == "Retour") {
					try {
						ApplicationEsporter.f.setContentPane(new Ecurie_Equipes());
						ApplicationEsporter.f.validate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				break;
			case CREATIONEQUIPE:
				if(b.getText() == "Ajouter un Logo") {
					JFileChooser j = new JFileChooser();
					j.setCurrentDirectory(new File("Images"));
					j.setFileFilter(new FileNameExtensionFilter("PNG, JPG, GIF", "png", "jpg", "gif"));
					j.showOpenDialog(this.vue);
					try {
						String fileName = j.getSelectedFile().getName();
						File src = new File("src/images/" + fileName);
						if(! src.isFile()) {
							Files.copy(Paths.get(j.getSelectedFile().getAbsolutePath()), Paths.get("src/images/" + fileName));
						}
						Ecurie_CreationEquipe.setImage("src/images/" + fileName);
						this.pathLogo = "src/images/" + fileName;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if(b.getText() == "Annuler") {
					try {
						ApplicationEsporter.f.setContentPane(new Ecurie_Equipes());
						ApplicationEsporter.f.validate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				if(b.getText() == "Créer Equipe") {
					if (Ecurie_CreationEquipe.tousRempli()) {
						try {
							Ecurie_AddJoueur.setEquipe(new Equipe(getNomEcurie(), Ecurie_CreationEquipe.getNomEquipe(), Ecurie_CreationEquipe.getJeu(), this.pathLogo));
							Ecurie_AddJoueur.getEquipe().ajouterEquipe();
							ApplicationEsporter.f.setContentPane(new Ecurie_AddJoueur());
							ApplicationEsporter.f.validate();
						} catch (SQLException e1) {
							System.out.println("Catched");
							e1.printStackTrace();
						}
					} else {
						Ecurie_CreationEquipe.setMessageErreur("Information(s) manquante(s)");
					}
				}
				break;
			case AJOUTERJOUEUR:
				if(b.getText()=="Annuler") {
					try {
						Ecurie_AddJoueur.annuler();
						ApplicationEsporter.f.setContentPane(new Ecurie_Equipes());
						ApplicationEsporter.f.validate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				if(b.getText() == "Ajouter le joueur") {
					if(Ecurie_AddJoueur.isNomNull()) {
						Ecurie_AddJoueur.setErreur(Erreurs.ERREURNOMNUL);
					} else if(Ecurie_AddJoueur.isPseudoNull()) {
						Ecurie_AddJoueur.setErreur(Erreurs.ERREURPSEUDONUL);
					}
					try {
						if(!Ecurie_AddJoueur.joueurExiste()) {
							Ecurie_AddJoueur.addJoueur(new Joueur(Ecurie_AddJoueur.getNomJoueur(), Ecurie_AddJoueur.getPseudoJoueur(), Ecurie_AddJoueur.getModel(), Ecurie_AddJoueur.getEquipe()));
							if(Ecurie_AddJoueur.getLastJoueur().calculAge() >= 16) {
								Ecurie_AddJoueur.getLastJoueur().ajouterJoueur();
								ApplicationEsporter.f.setContentPane(new Ecurie_AddJoueur());
								ApplicationEsporter.f.validate();
							} else {
								Ecurie_AddJoueur.setErreur(Erreurs.ERREURDATE);
							}		
						} else {
							Ecurie_AddJoueur.setErreur(Erreurs.ERRERUJOUEUREXISTANT);
						}
					} catch(Exception e1) {
						e1.printStackTrace();
					}
				}
				if(b.getText() == "Supprimer") {
					String aSupprimerPseudo = (String) Ecurie_AddJoueur.getTable().getValueAt(Ecurie_AddJoueur.getTable().getSelectedRow(), 1);
					String aSupprimerEquipe = (String) Ecurie_AddJoueur.getTable().getValueAt(Ecurie_AddJoueur.getTable().getSelectedRow(), 3);
					try {
						int result = JOptionPane.showConfirmDialog(null,"Voulez vous vraiment supprimer " + aSupprimerPseudo, "Supprimer le joueur", JOptionPane.YES_NO_OPTION);
						if (result == 0) {
							Ecurie_AddJoueur.supprimerJoueur(aSupprimerPseudo);
							FonctionsSQL.delete("saejoueur", "NOM_EQUIPE = '" + aSupprimerEquipe + "' and PSEUDONYME = '" + aSupprimerPseudo + "'");
							ApplicationEsporter.f.setContentPane(new Ecurie_AddJoueur());
							ApplicationEsporter.f.validate();
						} 
					} catch(Exception e1) {
						JOptionPane.showMessageDialog(null,"Echec de la suppression");
						e1.printStackTrace();
					}
				}
				if(b.getText()=="Valider") {
					try {
						ApplicationEsporter.f.setContentPane(new Ecurie_Equipes());
						ApplicationEsporter.f.validate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				break;
			}
		}
	}
}