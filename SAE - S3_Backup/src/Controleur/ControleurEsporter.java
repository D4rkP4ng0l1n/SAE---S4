package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import Modele.BDD.NomTablesBDD;
import Modele.Equipe;
import Modele.FonctionsSQL;
import Modele.Jeu;
import Modele.Joueur;
import Vue.ApplicationEsporter;
import Vue.Ecurie_AddJoueur;
import Vue.Esporter_Accueil;
import Vue.Esporter_AjouterJeu;
import Vue.Esporter_Classement;
import Vue.Esporter_CreerTournoi;
import Vue.Esporter_Ecuries;
import Vue.Esporter_Equipes;
import Vue.Esporter_InfoTournoi;
import Vue.Esporter_Jeux;
import Vue.Esporter_Joueurs;
import Vue.Esporter_ModifEcurie;
import Vue.Esporter_ModifTournoi;
import Vue.Esporter_ModifEquipe;
import Vue.Esporter_ModifJoueur;
import Vue.Esporter_Tournois;
import Vue.PageAccueil;

public class ControleurEsporter implements ActionListener {

	public enum EtatEsporter{ACCUEIL, TOURNOI, INFO_TOURNOI, CREE_TOURNOI, MODIF_TOURNOI, ECURIE, EQUIPE, JOUEUR, MODIF, MODIF_EQUIPE, MODIFIER_JOUEUR, JEU, AJOUTER_JEU };

	private JPanel vue;
	private EtatEsporter etat;

	private String pathLogo;
	private Jeu jeu;
	private List<String> jeux;
	private int antiDoubleBtn = 0;

	// Le constructeur du controleur pour toutes les pages Esporter
	public ControleurEsporter(JPanel vue, EtatEsporter etat) { 
		this.vue = vue;
		this.etat = etat;
		this.jeux = new ArrayList<String>();
	}

	// Retourne le nom de l'écurie pour l'équipe courante
	public String getNomEcurie() throws SQLException { 
		ResultSet nomEcurie = FonctionsSQL.select(NomTablesBDD.SAEEQUIPE, "nom_2", "nom = '" + ApplicationEsporter.equipe+"'");
		nomEcurie.next();
		return nomEcurie.getString(1);
	}

	// Méthode pour aller sur les pages accessibles depuis n'importe où
	private Boolean changerDePage(JButton b) { 
		if (b.getText().equals("Déconnexion")) {
			goDeconnexion();
		}
		if(b.getText().equals("Accueil")) {
			goAccueil();
		}
		if(b.getText().equals("Tournois")) {
			goTournois();
		}
		if (b.getText().equals("Ecuries")) {
			goEcuries();
		}
		if (b.getText().equals("Jeux")) {
			goJeux();
		}
		return false;
	}

	// Se déconnecter de l'application
	private void goDeconnexion() { 
		ApplicationEsporter.f.setContentPane(new PageAccueil());
		ApplicationEsporter.f.validate();
	}

	// Aller sur la page accueil du compte Esporter
	private void goAccueil() { 
		ApplicationEsporter.f.setContentPane(new Esporter_Accueil());
		ApplicationEsporter.f.validate();
	}

	// Aller sur la page tournoi du compte Esporter
	private void goTournois() { 
		try {
			ApplicationEsporter.f.setContentPane(new Esporter_Tournois());
			ApplicationEsporter.f.validate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	// Aller sur la page des ecuries du compte Esporter
	private void goEcuries() { 
		try {
			ApplicationEsporter.f.setContentPane(new Esporter_Ecuries());
			ApplicationEsporter.f.validate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	// Aller sur la page des jeux du compte Esporter
	private void goJeux() { 
		ApplicationEsporter.f.setContentPane(new Esporter_Jeux());
		ApplicationEsporter.f.validate();
	}

	// Défini les actions a effectuer selon le bouton et selon la page sur laquelle on se trouve
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if(! changerDePage(b)) {
			switch(this.etat) {
			case ACCUEIL:
				break;
			case TOURNOI:
				if(b.getText().equals("Créer un tournoi")) {
					try {
						// Changement de page
						ApplicationEsporter.f.setContentPane(new Esporter_CreerTournoi());
						ApplicationEsporter.f.validate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				if (b.getText().equals("Supprimer")) {
					String condition = (String) Esporter_Tournois.getTable().getValueAt(Esporter_Tournois.getTable().getSelectedRow(), 0);
					String condition2;
					condition2 = Esporter_Tournois.getTable().getValueAt(Esporter_Tournois.getTable().getSelectedRow(), 1).toString();
					try {
						condition2=Esporter_ModifTournoi.formatDate(condition2);
						ResultSet tournoi = FonctionsSQL.select(NomTablesBDD.SAETOURNOI, "IDTOURNOI, IDPHASEFINALE", "LIEU = '" + condition + "' and TO_DATE(DATEETHEURE, 'YYYY-MM-DD') = TO_DATE('" + condition2 + "', 'YYYY-MM-DD')");
						tournoi.next();
						String idTournoi= tournoi.getString(1);
						String idPhaseFinale= tournoi.getString(2);
						try {
							int result = JOptionPane.showConfirmDialog(null,"Voulez vous vraiment supprimer le tournoi de " + condition+" prevu le "+condition2, "Supprimer le tournoi", JOptionPane.YES_NO_OPTION);
							if (result == 0) {
								FonctionsSQL.delete(NomTablesBDD.SAECONCERNER, "idtournoi = '" + idTournoi + "'");
								FonctionsSQL.delete(NomTablesBDD.SAETOURNOI, "idtournoi = '" + idTournoi + "'");
								FonctionsSQL.delete(NomTablesBDD.SAEPHASEFINALE, "idphasefinale = '" + idPhaseFinale + "'");
							}
							ApplicationEsporter.f.setContentPane(new Esporter_Tournois());
							ApplicationEsporter.f.validate();
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null,"Echec de la suppression");
							e1.printStackTrace();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				if (b.getText().equals("Voir le(s) jeu(x)")) {
					try {
						// Sélectionne les jeux du tournois où l'utilisateur a cliqué
						ResultSet selectTournoi = FonctionsSQL.select(NomTablesBDD.SAETOURNOI, "idtournoi", "Lieu = '" + Esporter_Tournois.getTable().getValueAt(Esporter_Tournois.getTable().getSelectedRow(), 0) + "'");
						selectTournoi.next();
						ResultSet jeux = FonctionsSQL.select(NomTablesBDD.SAECONCERNER, "nom", "idtournoi = " + selectTournoi.getInt(1));
						String afficherJeux = "Liste des jeux : \n";
						while(jeux.next()) {
							afficherJeux += "   - " + jeux.getString(1) + "\n";
						}
						// Affiche une pop-up sur laquelle se trouve la liste des jeux du tournoi sélectionné
						JOptionPane.showMessageDialog(null, afficherJeux); 
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				if (b.getText().equals("Modifier")) {
					// Créer un String pour stocker le lieu du tournoi sélectionné
					String condition = (String) Esporter_Tournois.getTable().getValueAt(Esporter_Tournois.getTable().getSelectedRow(), 0);
					// Créer un String pour stocker la date du tournoi sélectionné
					String condition2 = Esporter_Tournois.getTable().getValueAt(Esporter_Tournois.getTable().getSelectedRow(), 1).toString();
					try {
						condition2=Esporter_ModifTournoi.formatDate(condition2);
						// Sélectionne dans la base de données le bon tournoi à l'aide des conditions
						ResultSet tournoi = FonctionsSQL.select(NomTablesBDD.SAETOURNOI, "IDTOURNOI", "LIEU = '" + condition + "' and TO_DATE(DATEETHEURE, 'YYYY-MM-DD') = TO_DATE('" + condition2 + "', 'YYYY-MM-DD')");
						tournoi.next();
						// On stocke l'id du tournoi sélectionné dans une "variable globale" pour pouvoir le modifier sur la page adéquate
						ApplicationEsporter.idTournoi= tournoi.getString(1);
						// Changement de page
						ApplicationEsporter.f.setContentPane(new Esporter_ModifTournoi());
						ApplicationEsporter.f.validate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				if(b.getText().equals("Accéder")) {
					ApplicationEsporter.f.setContentPane(new Esporter_InfoTournoi());
					ApplicationEsporter.f.validate();
				}
				break;
			case CREE_TOURNOI:
				if(b.getText().equals("Ajouter le jeu")) {
					// Ajoute le jeu sélectionné dans la liste des jeu du tournoi courant
					Esporter_CreerTournoi.addList();
					if (antiDoubleBtn == 0) { // Problème parce que le bouton s'active 2 fois, du coup on effectue l'action une seule fois
						antiDoubleBtn = 1;
						this.jeux.add(Esporter_CreerTournoi.getJeu()); // On ajoute le dernier jeu ajouté tournoi courant dans une variable
					} else {
						antiDoubleBtn = 0;
					}
				}
				if(b.getText().equals("Valider")) {
					// Mise en place des messages d'erreurs au cas où un label est vide
					if(Esporter_CreerTournoi.lieuEstVide()) {
						Esporter_CreerTournoi.setMessage("Veuillez entrer un Lieu");
					}else if(Esporter_CreerTournoi.DateEstVide()) {
						Esporter_CreerTournoi.setMessage("Veuillez entrer une Date");
					}else if(Esporter_CreerTournoi.datePassee()) {
						Esporter_CreerTournoi.setMessage("Veuillez entrer une Date future");
					}else if(Esporter_CreerTournoi.DLMIsVide()) {
						Esporter_CreerTournoi.setMessage("Veillez sélectionner au moins un jeu avant de valider");
					}else {
						String[]finaleData = { "" + FonctionsSQL.newID(NomTablesBDD.SAEPHASEFINALE), "0"};
						String[]tournoiData = { "" + FonctionsSQL.newID(NomTablesBDD.SAETOURNOI), "'" + Esporter_CreerTournoi.getLieu() + "'", "TO_DATE('" + Esporter_CreerTournoi.getDate() + " " + Esporter_CreerTournoi.getHeure() + ":" + Esporter_CreerTournoi.getMinute() + "', 'YYYY-MM-DD HH:MI')", finaleData[0], "'" + Esporter_CreerTournoi.getAmPm() + "'" };
						FonctionsSQL.insert(NomTablesBDD.SAEPHASEFINALE, finaleData);
						FonctionsSQL.insert(NomTablesBDD.SAETOURNOI, tournoiData);
						String[] concernerData;
						for (String jeu : this.jeux) {
							concernerData = new String[2];
							concernerData[0] = "'" + jeu + "'";
							concernerData[1] = tournoiData[0];
							FonctionsSQL.insert(NomTablesBDD.SAECONCERNER, concernerData );
						}
						Esporter_CreerTournoi.DLMVide();
						try {
							// Changement de page
							ApplicationEsporter.f.setContentPane(new Esporter_Tournois());
							ApplicationEsporter.f.validate();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
				if(b.getText().equals("Retour")) {
					Esporter_CreerTournoi.DLMVide();
					try {
						// Changement de page
						ApplicationEsporter.f.setContentPane(new Esporter_Tournois());
						ApplicationEsporter.f.validate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				break;
			case MODIF_TOURNOI:
				if(b.getText().equals("Ajouter le jeu")) {
					Esporter_ModifTournoi.addList();
					if (antiDoubleBtn == 0) {
						antiDoubleBtn = 1;
						this.jeux.add(Esporter_ModifTournoi.getJeu());
					} else {
						antiDoubleBtn = 0;
					}
				}
				if(b.getText().equals("Valider")) {
					if(Esporter_ModifTournoi.lieuEstVide()) {
						Esporter_ModifTournoi.setMessage("Veuillez entrer un Lieu");
					}else if(Esporter_ModifTournoi.DateEstVide()) {
						Esporter_ModifTournoi.setMessage("Veuillez entrer une Date");
					}else if(Esporter_ModifTournoi.datePassee()) {
						Esporter_ModifTournoi.setMessage("Veuillez entrer une Date future");
					}else if(Esporter_ModifTournoi.DLMIsVide()) {
						Esporter_ModifTournoi.setMessage("Veillez sélectionner au moins un jeu avant de valider");
					}else {
						String[]tournoiData = new String[3] ;
						tournoiData[0] ="'" + Esporter_ModifTournoi.getLieu() + "'";
						tournoiData[1] = "TO_DATE('" + Esporter_ModifTournoi.getDate() + " " + Esporter_ModifTournoi.getHeure() + ":" + Esporter_ModifTournoi.getMinute() + "', 'YYYY-MM-DD HH:MI')";
						tournoiData[2] = "'" + Esporter_ModifTournoi.getAmPm() + "'";
						try {
							FonctionsSQL.update(NomTablesBDD.SAETOURNOI, "Lieu", tournoiData[0], "IDTOURNOI = '" + ApplicationEsporter.idTournoi+"'");
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						try {
							FonctionsSQL.update(NomTablesBDD.SAETOURNOI, "DATEETHEURE", tournoiData[1], "IDTOURNOI = '" + ApplicationEsporter.idTournoi+"'");
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						try {
							FonctionsSQL.update(NomTablesBDD.SAETOURNOI, "AM_PM", tournoiData[2], "IDTOURNOI = '" + ApplicationEsporter.idTournoi+"'");
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						FonctionsSQL.delete(NomTablesBDD.SAECONCERNER, "idtournoi = '" + ApplicationEsporter.idTournoi + "'");
						String[] concernerData;
						for (String jeu : this.jeux) {
							concernerData = new String[2];
							concernerData[0] = "'" + jeu + "'";
							concernerData[1] = ApplicationEsporter.idTournoi;
							FonctionsSQL.insert(NomTablesBDD.SAECONCERNER, concernerData );
						}
						Esporter_ModifTournoi.DLMVide();
						try {
							ApplicationEsporter.f.setContentPane(new Esporter_Tournois());
							ApplicationEsporter.f.validate();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
				if(b.getText().equals("Retour")) {
					Esporter_ModifTournoi.DLMVide();
					try {
						ApplicationEsporter.f.setContentPane(new Esporter_Tournois());
						ApplicationEsporter.f.validate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				if(b.getText().equals("Supprimer tout les jeux")) {
					Esporter_ModifTournoi.suprimerJeux();
				}
				break;
			case ECURIE:
				if (b.getText().equals("Supprimer")) {
					String aSupprimer = (String) Esporter_Ecuries.getTable().getValueAt(Esporter_Ecuries.getTable().getSelectedRow(), 0);
					try {
						int yesOrNo = JOptionPane.showConfirmDialog(null,"Voulez vous vraiment supprimer " + aSupprimer, "Supprimer l'écurie", JOptionPane.YES_NO_OPTION);
						if (yesOrNo == 0) {
							FonctionsSQL.delete(NomTablesBDD.SAEECURIE, "nom = '" + aSupprimer + "'");
						}
						ApplicationEsporter.f.setContentPane(new Esporter_Ecuries());
						ApplicationEsporter.f.validate();
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null,"Echec de la suppression, veuillez vérifier que toutes les équipes sont supprimées");
						e1.printStackTrace();
					}
				}
				if (b.getText().equals("Acceder")) {
					String condition = (String) Esporter_Ecuries.getTable().getValueAt(Esporter_Ecuries.getTable().getSelectedRow(), 0);
					try {
						ResultSet ecurie = FonctionsSQL.select(NomTablesBDD.SAEECURIE, "nom", "nom = '" + condition + "'");
						ecurie.next();
						ApplicationEsporter.nomEcurie = ecurie.getString(1);
						ApplicationEsporter.f.setContentPane(new Esporter_Equipes());
						ApplicationEsporter.f.validate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				break;
			case EQUIPE:
				if(b.getText().equals("Acceder")) {
					try {
						ApplicationEsporter.f.setContentPane(new Esporter_Joueurs());
						ApplicationEsporter.f.validate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				if (b.getText().equals("Modifier l'écurie")) {
					ApplicationEsporter.f.setContentPane(new Esporter_ModifEcurie());
					ApplicationEsporter.f.validate();
				}
				if (b.getText().equals("Supprimer")) {
					String aSupprimer = (String) Esporter_Equipes.getTable().getValueAt(Esporter_Equipes.getTable().getSelectedRow(), 0);
					int yesOrNo = JOptionPane.showConfirmDialog(null,"Voulez vous vraiment supprimer " + aSupprimer, "Supprimer l'équipe", JOptionPane.YES_NO_OPTION);
					if (yesOrNo == 0) {
						FonctionsSQL.delete(NomTablesBDD.SAEEQUIPE, "nom = '" + aSupprimer + "'");
					}
					ApplicationEsporter.f.setContentPane(new Esporter_Equipes());
					ApplicationEsporter.f.validate();
				}
				break;
			case MODIF:
				if(b.getText().equals("Annuler")) {
					ApplicationEsporter.f.setContentPane(new Esporter_Equipes());
					ApplicationEsporter.f.validate();
				}
				if(b.getText().equals("Ajouter un logo")) {
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
						Esporter_ModifEcurie.setImage("src/images/" + fileName);
						this.pathLogo = "src/images/" + fileName;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if(b.getText().equals("Valider")) {
					if (! (Esporter_ModifEcurie.labelsVide() && this.pathLogo == null)) {
						try {
							FonctionsSQL.update(NomTablesBDD.SAEECURIE, "CEO", "'" + Esporter_ModifEcurie.getNomCEO() + "'", "Nom = '" + ApplicationEsporter.nomEcurie + "'");
							FonctionsSQL.update(NomTablesBDD.SAEECURIE, "LOGO", "'" + this.pathLogo + "'", "Nom = '" + ApplicationEsporter.nomEcurie + "'");
							FonctionsSQL.update(NomTablesBDD.SAEECURIE, "Nom", "'" + Esporter_ModifEcurie.getNomEcurie() + "'", "Nom = '" + ApplicationEsporter.nomEcurie + "'");
							ApplicationEsporter.f.setContentPane(new Esporter_Ecuries());
							ApplicationEsporter.f.validate();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} else {
						Esporter_ModifEcurie.setMessage("Information(s) manquante(s)");
					}
				}
				break;
			case MODIF_EQUIPE:
				if(b.getText().equals("Modifier le Logo")) {
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
						Esporter_ModifEquipe.setImage("src/images/" + fileName);
						ApplicationEsporter.logo_Path = "src/images/" + fileName;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if(b.getText().equals("Annuler")) {
					try {
						ApplicationEsporter.f.setContentPane(new Esporter_Joueurs());
						ApplicationEsporter.f.validate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				if(b.getText().equals("Modifier Equipe")) {
					if (Esporter_ModifEquipe.tousRempli()) {
						try {
							Esporter_ModifJoueur.setEquipe(new Equipe(getNomEcurie(), Esporter_ModifEquipe.getNomEquipe(), Esporter_ModifEquipe.getJeu(), ApplicationEsporter.logo_Path));
							ResultSet jeu= FonctionsSQL.select(NomTablesBDD.SAEEQUIPE, "nom_1", "nom ='"+ApplicationEsporter.equipe+"'");
							jeu.next();
							ResultSet equipeIsParticipe= FonctionsSQL.select(NomTablesBDD.SAEPARTICIPER, "count(nom)", "nom ='"+ApplicationEsporter.equipe+"'");
							equipeIsParticipe.next();
							if(equipeIsParticipe.getInt(1)==0 || jeu.getString(1).equals(Esporter_ModifEquipe.getJeu())) {
								Esporter_ModifJoueur.getEquipe().modifierEquipe();
								ApplicationEsporter.f.setContentPane(new Esporter_ModifJoueur());
								ApplicationEsporter.f.validate();
							}else {
								Esporter_ModifEquipe.setMessageErreur("Vous ne pouvez pas changer le jeu d'une équipe qui est actuellement inscrite a un tournoi");
							}
						} catch (SQLException e1) {
							System.out.println("Catched");
							e1.printStackTrace();
						}
					} else {
						Esporter_ModifEquipe.setMessageErreur("Information(s) manquante(s)");
					}
				}
				break;
			case JOUEUR:
				if (b.getText().equals("Retour")) {
					ApplicationEsporter.f.setContentPane(new Esporter_Equipes());
					ApplicationEsporter.f.validate();
				}
				if (b.getText().equals("Modifier Equipe")) {
					try {
						ApplicationEsporter.f.setContentPane(new Esporter_ModifEquipe());
						ApplicationEsporter.f.validate();
						this.etat = EtatEsporter.MODIF_EQUIPE;
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				break;
			case MODIFIER_JOUEUR:
				if(b.getText().equals("Annuler")) {
					Ecurie_AddJoueur.annuler();
					ApplicationEsporter.f.setContentPane(new Esporter_Equipes());
					ApplicationEsporter.f.validate();
				}
				if(b.getText().equals("Ajouter le joueur")) {
					if(Esporter_ModifJoueur.isNomNull()) {
						Esporter_ModifJoueur.setErreur(Esporter_ModifJoueur.Erreurs.ERREURNOMNUL);
					} else if(Esporter_ModifJoueur.isPseudoNull()) {
						Esporter_ModifJoueur.setErreur(Esporter_ModifJoueur.Erreurs.ERREURPSEUDONUL);
					}
					try {
						if(!Esporter_ModifJoueur.joueurExiste()) {
							Esporter_ModifJoueur.addJoueur(new Joueur(Esporter_ModifJoueur.getNomJoueur(), Esporter_ModifJoueur.getPseudoJoueur(), Esporter_ModifJoueur.getModel(), Esporter_ModifJoueur.getEquipe()));
							if(Esporter_ModifJoueur.getLastJoueur().calculAge() >= 16) {
								Esporter_ModifJoueur.getLastJoueur().ajouterJoueur();
								ApplicationEsporter.changerDePage(new Esporter_ModifJoueur());
							} else {
								Esporter_ModifJoueur.setErreur(Esporter_ModifJoueur.Erreurs.ERREURDATE);
							}		
						} else {
							Esporter_ModifJoueur.setErreur(Esporter_ModifJoueur.Erreurs.ERRERUJOUEUREXISTANT);
						}
					} catch(Exception e1) {
						e1.printStackTrace();
					}
				}
				if(b.getText().equals("Valider")) {
					ApplicationEsporter.changerDePage(new Esporter_Equipes());
				}
				break;
			case JEU:
				if(b.getText().equals("Ajouter un jeu")) {
					ApplicationEsporter.changerDePage(new Esporter_AjouterJeu());
					this.etat = EtatEsporter.AJOUTER_JEU;
				}
				if(b.getText().equals("Supprimer")) {
					String aSupprimer = (String) Esporter_Jeux.getTable().getValueAt(Esporter_Jeux.getTable().getSelectedRow(), 0);
					int result = JOptionPane.showConfirmDialog(null,"Voulez vous vraiment supprimer " + aSupprimer, "Supprimer le jeu", JOptionPane.YES_NO_OPTION);
					if (result == 0) {
						FonctionsSQL.delete(NomTablesBDD.SAEJEU, "nom = '" + aSupprimer + "'");
					}
					ApplicationEsporter.f.setContentPane(new Esporter_Jeux());
					ApplicationEsporter.f.validate();
				}
				if(b.getText().equals("Accéder au classement")) {
					String nomJeu = (String) Esporter_Jeux.getTable().getValueAt(Esporter_Jeux.getTable().getSelectedRow(), 0);
					ApplicationEsporter.changerDePage(new Esporter_Classement(nomJeu));
				}
				break;
			case AJOUTER_JEU:
				if(b.getText().equals("Valider")) {
					if (Esporter_AjouterJeu.nomJeuEstVide() || Esporter_AjouterJeu.nbJoueursParEquipeEstVide()) {
						Esporter_AjouterJeu.setLabelErreur("Veuillez remplir les champs textuels");
					} else {
						this.jeu = new Jeu(Esporter_AjouterJeu.getNomJeu(), Esporter_AjouterJeu.getNbJoueursParEquipe());
						try {
							if (this.jeu.estNouveau()) {
								String[] ajoutJeu = new String[1];
								ajoutJeu[0] =  "'" + this.jeu.getNom() + "', '" + this.jeu.getNbJoueursParEquipe() + "'";
								FonctionsSQL.insert(NomTablesBDD.SAEJEU, ajoutJeu);
								ApplicationEsporter.changerDePage(new Esporter_Jeux());
							} else {
								Esporter_AjouterJeu.setLabelErreur("Ce jeu existe déjà");
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
				if(b.getText().equals("Retour")) {
					ApplicationEsporter.changerDePage(new Esporter_Jeux());
				}
				break;
			case INFO_TOURNOI:
				break;
			default:
				break;
			}
		}
	}
}