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

import Modele.BDD.NomTablesBDD;
import Modele.Equipe;
import Modele.FonctionsSQL;
import Modele.Joueur;
import Vue.ApplicationEsporter;
import Vue.Ecurie_Accueil;
import Vue.Ecurie_AddJoueur;
import Vue.Ecurie_AddJoueur.Erreurs;
import Vue.Ecurie_CreationEquipe;
import Vue.Ecurie_CreerEcurie;
import Vue.Ecurie_Equipes;
import Vue.Ecurie_VueEquipe;
import Vue.Ecurie_InfoTournoi;
import Vue.Ecurie_Inscription;
import Vue.Ecurie_PreInscription;
import Vue.Ecurie_Tournoi;
import Vue.PageAccueil;

public class ControleurEcurie extends FocusAdapter implements ActionListener {

	public enum EtatEcurie{CREATION, TOURNOI, PREINSCRIPTION_TOURNOI, INSCRIPTION_TOURNOI, EQUIPES, CREATIONEQUIPE, GESTIONEQUIPE, AJOUTERJOUEUR};

	private JPanel vue;
	private EtatEcurie etat;

	private String pathLogo;

	// Controleur de la classe Ecurie
	public ControleurEcurie(JPanel vue, EtatEcurie etat) {
		this.vue = vue;
		this.etat = etat;
	}

	// Retourne le nom de l'écurie sur laquelle on se trouve
	public String getNomEcurie() {
		try {
			ResultSet selectnomEcurie = FonctionsSQL.select("saeecurie", "nom", "idcompte = " + ApplicationEsporter.idCompte);
			selectnomEcurie.next();
			return selectnomEcurie.getString(1);
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Génération des poules
	private static void  genererPoules(String IdTournoi) {
		for (int i = 1; i <= 4;i++) { 
			String[] poule = {"" + FonctionsSQL.newID(NomTablesBDD.SAEPOULE), "''", "" + i, "" + IdTournoi};
			FonctionsSQL.insert(NomTablesBDD.SAEPOULE, poule);
		}
		// Liens entre les équipes et les poules (insertion dans la table saeconcourir)
		try {
			// Sélection des équipes de celles avec le plus de points à celles avec le moins
			ResultSet selectlisteEquipe = FonctionsSQL.select("saeequipe e, CRJ3957A.saeparticiper p","e.nom"," e.nom=p.nom and p.idtournoi= " + ApplicationEsporter.idTournoi + " order by e.nbpoints desc,1");
			int pouleCounter = 1;
			ResultSet selectIdPoule;
			while (selectlisteEquipe.next()) {
				// Récupération d'un id de poule avec le bon numéro
				selectIdPoule = FonctionsSQL.select(NomTablesBDD.SAEPOULE, "idpoule", "numero = '" + pouleCounter + "' and idtournoi = " + ApplicationEsporter.idTournoi);
				selectIdPoule.next();
				String[] equipe = {"'" + selectlisteEquipe.getString("nom") + "'", "" + selectIdPoule.getInt("Idpoule"), "'0'"};
				FonctionsSQL.insert(NomTablesBDD.SAECONCOURIR, equipe);
				pouleCounter = (pouleCounter % 4) + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		genererMatchs(IdTournoi);
	}

	// Génère les matchs pour les phases de poules
	private static void genererMatchs(String idTournoi) {
		try {
			ResultSet idPoule = FonctionsSQL.select(NomTablesBDD.SAEPOULE, "IDPoule", "IDTournoi = " + idTournoi);
			String[]idPoules = new String[100];
			int numero = 0;
			while(idPoule.next()) {
				idPoules[numero] = "" + idPoule.getInt(1);
				numero++;
			}
			for (int numPoule = 0; numPoule < 4; numPoule++) {
				ResultSet listeEquipe = FonctionsSQL.select(NomTablesBDD.SAECONCOURIR, "nom", "IDPoule = " + idPoules[numPoule]);
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
					int id = FonctionsSQL.newID(NomTablesBDD.SAEPARTIEPOULE);
					String[]match = { "" + id, "'aucune'", "" + idPoules[numPoule] };
					String[]equipe1 = { "'" + matchsEquipe1[i] + "'", "" + id };
					String[]equipe2 = { "'" + matchsEquipe2[i] + "'", "" + id };
					FonctionsSQL.insert(NomTablesBDD.SAEPARTIEPOULE, match);
					FonctionsSQL.insert(NomTablesBDD.SAECOMPETITER, equipe1);
					FonctionsSQL.insert(NomTablesBDD.SAECOMPETITER, equipe2);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Methode pour faciliter la navigation depuis n'importe où en tant qu'Ecurie
	private Boolean changerDePageHeader(JButton b) {
		if(b.getText().equals("Déconnexion")) {
			ApplicationEsporter.changerDePage(new PageAccueil());
		} 
		if(b.getText().equals("Accueil")) {
			ApplicationEsporter.changerDePage(new Ecurie_Accueil());
		}
		if(b.getText().equals("Mes équipes")) {
			ApplicationEsporter.changerDePage(new Ecurie_Equipes());
		}
		if(b.getText().equals("Tournois")){
			ApplicationEsporter.changerDePage(new Ecurie_Tournoi());
		}
		return false;
	}

	// Défini les actions a effectuer selon le bouton et selon la page sur laquelle on se trouve
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if(!changerDePageHeader(b)) {
			switch(this.etat) {
			case CREATION :
				if(b.getText().equals("Ajouter un logo")) {
					ajoutImage();
				}
				if(b.getText().equals("Valider")) {
					creationEcurie();
				}
				break;
			case TOURNOI:
				if(b.getText().equals("S'inscrire")) {
					ApplicationEsporter.changerDePage(new Ecurie_PreInscription());
				}
				if(b.getText().equals("Voir le(s) jeu(x)")) {
					afficherJeu();
				}
				if(b.getText().equals("Accéder")) {
					ApplicationEsporter.changerDePage(new Ecurie_InfoTournoi());
				}
				break;
			case PREINSCRIPTION_TOURNOI:
				if(b.getText().equals("S'inscrire")) {
					try {
						stockageIdTournoi();
						ApplicationEsporter.changerDePage(new Ecurie_Inscription());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				break;
			case INSCRIPTION_TOURNOI:
				if(b.getText().equals("S'inscrire")) {
					int result = JOptionPane.showConfirmDialog(null,"Attention, l'inscription est définitive\n Confirmer l'inscription ?", "Confirmer l'inscription", JOptionPane.YES_NO_OPTION);
					if (result == 0) {
						inscription();
						ApplicationEsporter.changerDePage(new Ecurie_Tournoi());
					}
				}
				if(b.getText().equals("Pas encore d'équipe ?")) {
					ApplicationEsporter.changerDePage(new Ecurie_CreationEquipe());
				}
				break;
			case EQUIPES:
				if(b.getText().equals("Ajouter une Equipe")) {
					ApplicationEsporter.changerDePage(new Ecurie_CreationEquipe());
				}
				if(b.getText().equals("Acceder")) {
					ApplicationEsporter.changerDePage(new Ecurie_VueEquipe());
				}
				if(b.getText().equals("Valider")) {
					ApplicationEsporter.changerDePage(new Ecurie_Equipes());
				}
				if(b.getText().equals("Annuler")) {
					ApplicationEsporter.changerDePage(new Ecurie_Equipes());
				}
				break;
			case GESTIONEQUIPE:
				if(b.getText().equals("Retour")) {
					ApplicationEsporter.changerDePage(new Ecurie_Equipes());
				}
				break;
			case CREATIONEQUIPE:
				if(b.getText().equals("Ajouter un Logo")) {
					ajoutImageEquipe();
				}
				if(b.getText().equals("Annuler")) {
					ApplicationEsporter.changerDePage(new Ecurie_Equipes());
				}
				if(b.getText().equals("Créer Equipe")) {
					if (Ecurie_CreationEquipe.tousRempli()) {
						creationEquipe();
					} else {
						Ecurie_CreationEquipe.setMessageErreur("Information(s) manquante(s)");
					}
				}
				break;
			case AJOUTERJOUEUR:
				if(b.getText().equals("Annuler")) {
					Ecurie_AddJoueur.annuler();
					ApplicationEsporter.changerDePage(new Ecurie_Equipes());
				}
				if(b.getText().equals("Ajouter le joueur")) {
					if(Ecurie_AddJoueur.isNomNull()) {
						Ecurie_AddJoueur.setErreur(Erreurs.ERREURNOMNUL);
					} else if(Ecurie_AddJoueur.isPseudoNull()) {
						Ecurie_AddJoueur.setErreur(Erreurs.ERREURPSEUDONUL);
					}
					ajoutJoueur();
				}
				if(b.getText().equals("Supprimer")) {
					supprimerJoueur();
				}
				if(b.getText().equals("Valider")) {
					ApplicationEsporter.changerDePage(new Ecurie_Equipes());
				}
				break;
			}
		}
	}

	private void supprimerJoueur() {
		String aSupprimerPseudo = (String) Ecurie_AddJoueur.getTable().getValueAt(Ecurie_AddJoueur.getTable().getSelectedRow(), 1);
		String aSupprimerEquipe = (String) Ecurie_AddJoueur.getTable().getValueAt(Ecurie_AddJoueur.getTable().getSelectedRow(), 3);
		try {
			int result = JOptionPane.showConfirmDialog(null,"Voulez vous vraiment supprimer " + aSupprimerPseudo, "Supprimer le joueur", JOptionPane.YES_NO_OPTION);
			if (result == 0) {
				Ecurie_AddJoueur.supprimerJoueur(aSupprimerPseudo);
				FonctionsSQL.delete(NomTablesBDD.SAEJOUEUR, "NOM_EQUIPE = '" + aSupprimerEquipe + "' and PSEUDONYME = '" + aSupprimerPseudo + "'");
				ApplicationEsporter.f.setContentPane(new Ecurie_AddJoueur());
				ApplicationEsporter.f.validate();
			} 
		} catch(Exception e1) {
			JOptionPane.showMessageDialog(null,"Echec de la suppression");
			e1.printStackTrace();
		}
	}

	private void ajoutJoueur() {
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

	private void creationEquipe() {
		Ecurie_AddJoueur.setEquipe(new Equipe(getNomEcurie(), Ecurie_CreationEquipe.getNomEquipe(), Ecurie_CreationEquipe.getJeu(), this.pathLogo));
		Ecurie_AddJoueur.getEquipe().ajouterEquipe();
		ApplicationEsporter.f.setContentPane(new Ecurie_AddJoueur());
		ApplicationEsporter.f.validate();
	}

	private void ajoutImageEquipe() {
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

	//inscrit l'équipe et génère les poules si le tournoi est rempli suite à l'inscription
	private void inscription() {
		try {
			String[]aInserer = { "'" + Ecurie_Inscription.getCombo() + "'", ApplicationEsporter.idTournoi, "0" }; 
			FonctionsSQL.insert(NomTablesBDD.SAEPARTICIPER, aInserer);
			JOptionPane.showMessageDialog(null, Ecurie_Inscription.getCombo() + " a bien été inscrite au tournoi !");
			ResultSet countNbEquipesInscrites = FonctionsSQL.select("SAEparticiper", "count(*)", "IDTournoi = " + ApplicationEsporter.idTournoi);
			countNbEquipesInscrites.next();
			if (countNbEquipesInscrites.getInt(1) >= 16) {
				JOptionPane.showMessageDialog(null, "Vous êtes le dernier inscit, les poules sont en cours de création\nVeuillez patienter");
				genererPoules(ApplicationEsporter.idTournoi);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void stockageIdTournoi() throws SQLException {
		ResultSet selectIDTournoi = FonctionsSQL.select("SAETournoi", "IDTournoi", "Lieu = '" + Ecurie_Tournoi.getTable().getValueAt(Ecurie_Tournoi.getTable().getSelectedRow(), 0)
				+ "' AND DATEETHEURE LIKE TO_DATE('" + Ecurie_Tournoi.getTable().getValueAt(Ecurie_Tournoi.getTable().getSelectedRow(), 1) + "', 'YYYY-MM-DD')");
		selectIDTournoi.next();
		ApplicationEsporter.idTournoi = selectIDTournoi.getString(1);
	}

	private void afficherJeu() {
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

	private void creationEcurie() {
		if (! (Ecurie_CreerEcurie.labelsVide() && this.pathLogo == null)) {
			String[] aInserer = {"'" + Ecurie_CreerEcurie.getNomEcurie() + "'", "'" + Ecurie_CreerEcurie.getNomCEO() + "'", "'" + this.pathLogo + "'", "" + ApplicationEsporter.idCompte};
			FonctionsSQL.insert(NomTablesBDD.SAEECURIE, aInserer);
			ApplicationEsporter.f.setContentPane(new PageAccueil());
			ApplicationEsporter.f.validate();
		} else {
			Ecurie_CreerEcurie.setMessage("Information(s) manquante(s)");
		}
	}

	private void ajoutImage() {
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
}