package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Modele.BDD.NomTablesBDD;
import Modele.FonctionsSQL;
import Vue.ApplicationEsporter;
import Vue.Arbitre_Accueil;
import Vue.Arbitre_InfoTournoi;
import Vue.Arbitre_Match;
import Vue.Arbitre_Tournoi;
import Vue.PageAccueil;

public class ControleurArbitre implements ActionListener {

	public enum EtatArbitre {TOURNOI, INFOTOURNOI, MATCHS}

	private EtatArbitre etat;
	private Arbitre_Match vueMatch;

	// Constructeur pour accéder à une méthode de Arbitre_Match
	public ControleurArbitre(Arbitre_Match vueMatch, EtatArbitre etat) {
		this.vueMatch = vueMatch;
		this.etat = etat;
	}

	// Constructeur du controleur arbitre
	public ControleurArbitre(JPanel vue, EtatArbitre etat) {
		this.etat = etat;
	}

	// Methode pour faciliter la navigation depuis n'importe où en tant qu'Arbitre
	private Boolean changerDePageHeader(JButton b) {
		if (b.getText().equals("Déconnexion")) {
			ApplicationEsporter.changerDePage(new PageAccueil());
		}
		if(b.getText().equals("Accueil")) {
			ApplicationEsporter.changerDePage(new Arbitre_Accueil());
		}
		if(b.getText().equals("Tournois")) {
			ApplicationEsporter.changerDePage(new Arbitre_Tournoi());
		}
		return false;
	}

	// Recupere toutes les poules liees a un tournoi
	private ResultSet getIdsPoules() { 
		return FonctionsSQL.select(NomTablesBDD.SAEPOULE, "IDPoule", "IDTournoi = " + ApplicationEsporter.idTournoi);
	}

	// Retourne true si toutes les poules sont termines
	private boolean poulesTermines() {
		try {
			ResultSet idsPoules = getIdsPoules();
			int nbPouleFinis = 0;
			ResultSet pouleTermines;
			while(idsPoules.next()) {
				pouleTermines = FonctionsSQL.select(NomTablesBDD.SAEPARTIEPOULE, "count(resultat)", "IDPoule = " + idsPoules.getInt(1) + "AND resultat = 'aucune'");
				pouleTermines.next();
				if(pouleTermines.getInt(1) == 0) {
					nbPouleFinis++;
				}
			}
			return(nbPouleFinis >= 4);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// Retourne un result set contenant les ids des phases finales
	private ResultSet getIdsPhasesFinales() { 
		try {
			ResultSet selectIdPhaseFinale = FonctionsSQL.select(NomTablesBDD.SAETOURNOI, "IDPhaseFinale", "IDTournoi = " + ApplicationEsporter.idTournoi);
			selectIdPhaseFinale.next();
			return FonctionsSQL.select(NomTablesBDD.SAEPARTIEPHASEFINALE, "Id_PartiePhaseFinale", "IDPhaseFinale = " + selectIdPhaseFinale.getInt(1));
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Retourne true si les demis finales sont terminees
		private boolean demisFinalesTerminees() {
		try {
			ResultSet idsPhasesFinales = getIdsPhasesFinales();
			int nbDemisFinalesTerminees = 0;
			ResultSet phasesDemisFinalesTerminees;
			while(idsPhasesFinales.next()) {
				phasesDemisFinalesTerminees = FonctionsSQL.select(NomTablesBDD.SAEPARTIEPHASEFINALE, "count(resultat)", "IDPhaseFinale = " + idsPhasesFinales.getInt(1) + " AND resultat = 'aucune'");
				phasesDemisFinalesTerminees.next();
				if(phasesDemisFinalesTerminees.getInt(1) == 0) {
					nbDemisFinalesTerminees++;
				}
			}
			return(nbDemisFinalesTerminees >= 2);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// Génère les phases finales
	private static void genererFinale() {
		try {
			ResultSet selectIdPhaseFinale = FonctionsSQL.select(NomTablesBDD.SAETOURNOI, "IDPhaseFinale", "IDTournoi = " + ApplicationEsporter.idTournoi);
			selectIdPhaseFinale.next();
			if (compterFinalistes() >= 4) {
				qualifierEquipesPourPhaseFinale(selectIdPhaseFinale.getInt(1));
			} else {
				// Cas où il y a une égalité
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	// Compte le nombre d'équipe finaliste
	private static int compterFinalistes() {
		try {
			ResultSet nbFinaliste = FonctionsSQL.select(NomTablesBDD.SAECONCOURIR, "count(nom)", "1 = 1 AND classementPoule >= 3");
			nbFinaliste.next();
			return nbFinaliste.getInt(1);
		} catch(SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	// Ajoute les équipes finalistes dans la table "se qualifier"
	private static void qualifierEquipesPourPhaseFinale(int idPhaseFinale) {
		try {
			ResultSet finalistes = FonctionsSQL.select(NomTablesBDD.SAECONCOURIR, "nom", "classementPoule >= 3");
			while(finalistes.next()) {
				String[]equipeFinaliste = {"'" + finalistes.getString(1) + "'", "" + idPhaseFinale, "0" };
				FonctionsSQL.insert(NomTablesBDD.SAESEQUALIFIER, equipeFinaliste);
			}
			genererMatchDemiFinale(idPhaseFinale);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	// Génère les 2 demis finales
	private static void genererMatchDemiFinale(int idPhaseFinale) {
		try {
			ResultSet finalistes = FonctionsSQL.select(NomTablesBDD.SAESEQUALIFIER, "nom", "IDPhaseFinale = " + idPhaseFinale);
			for(int k = 0; k < 2; k++) {
				int idPartiePhaseFinale = FonctionsSQL.newID(NomTablesBDD.SAEPARTIEPHASEFINALE);
				String[]PhaseFinale = { "" + idPartiePhaseFinale , "'aucune'", "" + idPhaseFinale};
				FonctionsSQL.insert(NomTablesBDD.SAEPARTIEPHASEFINALE, PhaseFinale);
				finalistes.next();
				String[]DemiFinale_1 = {"'" + finalistes.getString(1) + "'", "" + idPartiePhaseFinale};
				finalistes.next();
				String[]DemiFinale_2 = {"'" + finalistes.getString(1) + "'", "" + idPartiePhaseFinale};
				FonctionsSQL.insert(NomTablesBDD.SAECOMPETITERPHASEFINALE, DemiFinale_1);
				FonctionsSQL.insert(NomTablesBDD.SAECOMPETITERPHASEFINALE, DemiFinale_2);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}

	}

	// Défini les actions a effectuer selon le bouton et selon la page sur laquelle on se trouve
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if(!changerDePageHeader(b)) {
			switch(this.etat) {
			case TOURNOI:
				if (b.getText().equals("Voir le(s) jeu(x)")) {
					afficherJeux();
				}
				if(b.getText().equals("Accéder")) {
					stockageIdTournoi();
					ApplicationEsporter.changerDePage(new Arbitre_InfoTournoi());
				}
				break;
			case INFOTOURNOI:
				if(b.getText().equals("Voir les matchs")) {
					ApplicationEsporter.changerDePage(new Arbitre_Match());
				}
				break;
			case MATCHS:
				if(b.getText().equals("Victoire équipe 1") || b.getText().equals("Victoire équipe 2")) {
					if(b.getText().equals("Victoire équipe 1")) {
						victoireMatchPouleEquipe(1);
					}
					if(b.getText().equals("Victoire équipe 2")) {
						victoireMatchPouleEquipe(2);
					}
					setMessagePouleFiniDemisFinPasFini(); 
					vueMatch.updateTable();
				}
				if(b.getText().equals("Victoire équipe 1") || b.getText().equals("Victoire équipe 2")) {
					if(b.getText().equals("Victoire équipe 1 ")) {
						victoireMatchPhaseFinaleEquipe(1);
					}
					if(b.getText().equals("Victoire équipe 2 ")) {
						victoireMatchPhaseFinaleEquipe(2);
					}
					vueMatch.updateTableFinale();
				}
			}
		}
	}

	public void setMessagePouleFiniDemisFinPasFini() {
		if(poulesTermines() && !demisFinalesTerminees()) {
			JOptionPane.showMessageDialog(null, "La phase de poule est terminée !");
			JOptionPane.showMessageDialog(null, "Création des demis finales en cours");
			genererFinale();
		}
	}

	private void victoireMatchPhaseFinaleEquipe(int numEquipe) {
		String nomEquipe1 = Arbitre_Match.getEquipe(1);
		String nomEquipe2 = Arbitre_Match.getEquipe(2);
		String nomEquipeGagante = nomEquipe1;
		if (numEquipe == 2) {
			nomEquipeGagante = nomEquipe2;
		}
		try {
			ResultSet selectIdMatch = FonctionsSQL.select("saecompetiterphasefinale", "id_partiephasefinale", "nom = '" + nomEquipe1 + "' INTERSECT select id_partiephasefinale from saecompetiterphasefinale where nom = '" + nomEquipe2 + "'");
			selectIdMatch.next();
			int idMatch = selectIdMatch.getInt(1);
			//insertion du nom de l'équipe gagante dans la table partiepoule
			FonctionsSQL.update(NomTablesBDD.SAEPARTIEPHASEFINALE, "resultat", "'" + nomEquipeGagante + "'", "id_partiephasefinale = " + idMatch);

			//ajout du score dans les tables concourir et participer
			String equipeVictorieuse = (String) Arbitre_Match.getTable().getValueAt(Arbitre_Match.getTable().getSelectedRow(), 4);
			ResultSet selectIdPoule = FonctionsSQL.select("saepartiephasefinale", "idphasefinale", "id_partiephasefinale = " + idMatch);
			selectIdPoule.next();
			int idPoule = selectIdPoule.getInt(1);
			ResultSet selectIdTournoi = FonctionsSQL.select("saephasefinale f, saepartiephasefinale pf, saetournoi t", "t.idtournoi", "f.idphasefinale = " + idPoule + " and pf.idphasefinale = f.idphasefinale and f.idphasefinale = t.idphasefinale and pf.id_partiephasefinale = " + idMatch);
			selectIdTournoi.next();
			int idTournoi = selectIdTournoi.getInt(1);
			if(equipeVictorieuse != "aucune") {
				FonctionsSQL.update(NomTablesBDD.SAEPARTICIPER, "classementfinal", "classementfinal - 2", "nom = '" + equipeVictorieuse + "' and idtournoi = " + idTournoi);
				FonctionsSQL.update(NomTablesBDD.SAESEQUALIFIER, "classementphasefinale", "classementphasefinale - 2", "nom = '" + equipeVictorieuse + "' and idphasefinale = " + idPoule);
			}
			FonctionsSQL.update(NomTablesBDD.SAEPARTICIPER, "classementfinal", "classementfinal + 2", "nom = '" + nomEquipeGagante + "' and idtournoi = " + idTournoi);
			FonctionsSQL.update(NomTablesBDD.SAESEQUALIFIER, "classementphasefinale", "classementphasefinale + 2", "nom = '" + nomEquipeGagante + "' and idphasefinale = " + idPoule);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void victoireMatchPouleEquipe(int numEquipe) {
		String nomEquipe1 = Arbitre_Match.getEquipe(1);
		String nomEquipe2 = Arbitre_Match.getEquipe(2);
		String nomEquipeGagante = nomEquipe1;
		if (numEquipe == 2) {
			nomEquipeGagante = nomEquipe2;
		}
		try {
			ResultSet selectIdMatch = FonctionsSQL.select("saecompetiter", "id_partiepoule", "nom = '" + nomEquipe1 + "' INTERSECT select id_partiepoule from saecompetiter where nom = '" + nomEquipe2 + "'");
			selectIdMatch.next();
			int idMatch = selectIdMatch.getInt(1);
			//insertion du nom de l'équipe gagante dans la table partiepoule
			FonctionsSQL.update(NomTablesBDD.SAEPARTIEPOULE, "resultat", "'" + nomEquipeGagante + "'", "id_partiepoule = " + idMatch);

			//ajout du score dans les tables concourir et participer
			String equipeVictorieuse = (String) Arbitre_Match.getTable().getValueAt(Arbitre_Match.getTable().getSelectedRow(), 4);
			ResultSet selectIdPoule = FonctionsSQL.select("saepartiepoule", "idpoule", "id_partiepoule = " + idMatch);
			selectIdPoule.next();
			int idPoule = selectIdPoule.getInt(1);
			ResultSet selectIdTournoi = FonctionsSQL.select("saepoule p, saepartiepoule pp", "p.idtournoi", "p.idpoule = " + idPoule + " and pp.idpoule = p.idpoule and pp.id_partiepoule = " + idMatch);
			selectIdTournoi.next();
			int idTournoi = selectIdTournoi.getInt(1);
			if(equipeVictorieuse != "aucune") {
				FonctionsSQL.update(NomTablesBDD.SAEPARTICIPER, "classementfinal", "classementfinal - 1", "nom = '" + equipeVictorieuse + "' and idtournoi = " + idTournoi);
				FonctionsSQL.update(NomTablesBDD.SAECONCOURIR, "classementpoule", "classementpoule - 1", "nom = '" + equipeVictorieuse + "' and idpoule = " + idPoule);
			}
			FonctionsSQL.update(NomTablesBDD.SAEPARTICIPER, "classementfinal", "classementfinal + 1", "nom = '" + nomEquipeGagante + "' and idtournoi = " + idTournoi);
			FonctionsSQL.update(NomTablesBDD.SAECONCOURIR, "classementpoule", "classementpoule + 1", "nom = '" + nomEquipeGagante + "' and idpoule = " + idPoule);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	//stocke l'id du tournoi sélectionné dans la variable globale idTournoi
	private void stockageIdTournoi() {
		try {
			ResultSet selectIDTournoi = FonctionsSQL.select("SAETournoi", "IDTournoi", "Lieu = '" + Arbitre_Tournoi.getTable().getValueAt(Arbitre_Tournoi.getTable().getSelectedRow(), 0)
					+ "' AND DATEETHEURE LIKE TO_DATE('" + Arbitre_Tournoi.getTable().getValueAt(Arbitre_Tournoi.getTable().getSelectedRow(), 1) + "', 'YYYY-MM-DD')");
			selectIDTournoi.next();
			ApplicationEsporter.idTournoi = selectIDTournoi.getString(1);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	//affiche les jeux du tournoi sélectionné dans un popUp
	private void afficherJeux() {
		try {
			ResultSet selectTournoi = FonctionsSQL.select("saetournoi", "idtournoi", "Lieu = '" + Arbitre_Tournoi.getTable().getValueAt(Arbitre_Tournoi.getTable().getSelectedRow(), 0) + "'");
			selectTournoi.next();
			ResultSet jeux = FonctionsSQL.select("saeconcerner", "nom", "idtournoi = " + selectTournoi.getInt(1));
			String afficherJeux = "Liste des jeux : \n";
			while(jeux.next()) {
				afficherJeux += "   - " + jeux.getString(1) + "\n";
			}
			JOptionPane.showMessageDialog(null, afficherJeux);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}