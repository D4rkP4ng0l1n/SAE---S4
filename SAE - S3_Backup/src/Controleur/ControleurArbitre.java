package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Modele.FonctionsSQL;
import Vue.ApplicationEsporter;
import Vue.Arbitre_Accueil;
import Vue.Arbitre_InfoTournoi;
import Vue.Arbitre_Match;
import Vue.Arbitre_Tournoi;
import Vue.Ecurie_Tournoi;
import Vue.Esporter_Accueil;
import Vue.Esporter_Tournois;
import Vue.PageAccueil;

public class ControleurArbitre implements ActionListener {

	public enum EtatArbitre {ACCUEIL, TOURNOI, INFOTOURNOI, MATCHS}

	private JPanel vue;
	private EtatArbitre etat;
	private Arbitre_Match vueMatch;

	// Constructeur pour accéder à une méthode de Arbitre_Match
	public ControleurArbitre(Arbitre_Match vueMatch, EtatArbitre etat) {
		this.vueMatch = vueMatch;
		this.etat = etat;
	}

	// Constructeur du controleur arbitre
	public ControleurArbitre(JPanel vue, EtatArbitre etat) {
		this.vue = vue;
		this.etat = etat;
	}

	// Methode pour faciliter la navigation depuis n'importe où en tant qu'Arbitre
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
		return false;
	}

	// Méthode pour se déconnecter
	private void goDeconnexion() {
		ApplicationEsporter.f.setContentPane(new PageAccueil());
		ApplicationEsporter.f.validate();
	}

	// Méthode pour accéder à la page d'accueil
	private void goAccueil() {
		ApplicationEsporter.f.setContentPane(new Arbitre_Accueil());
		ApplicationEsporter.f.validate();
	}

	// Méthode pour accéder à la page des tournois
	private void goTournois() {
		ApplicationEsporter.f.setContentPane(new Arbitre_Tournoi());
		ApplicationEsporter.f.validate();
	}

	// Recupere toutes les poules liees a un tournoi
	private ResultSet getIdsPoules() throws SQLException { 
		return FonctionsSQL.select("SAEPoule", "IDPoule", "IDTournoi = " + ApplicationEsporter.idTournoi);
	}

	// Retourne true si toutes les poules sont termines
	private boolean poulesTermines() throws SQLException { 
		ResultSet idsPoules = getIdsPoules();
		int nbPouleFinis = 0;
		while(idsPoules.next()) {
			ResultSet pouleTermines = FonctionsSQL.select("SAEPartiePoule", "count(resultat)", "IDPoule = " + idsPoules.getInt(1) + "AND resultat = 'aucune'");
			pouleTermines.next();
			if(pouleTermines.getInt(1) == 0) {
				nbPouleFinis++;
			}
		}
		return(nbPouleFinis >= 4);
	}

	// Supprime tous les matchs des poules
	private void delPoules() throws SQLException { 
		ResultSet idsPoules = getIdsPoules();
		while(idsPoules.next()) {
			ResultSet idsPartiePoule = FonctionsSQL.select("SAEPartiePoule","ID_PartiePoule", "IDPoule = " + idsPoules.getInt(1));
			while(idsPartiePoule.next()) {
				FonctionsSQL.delete("SAECompetiter", "Id_PartiePoule = " + idsPartiePoule.getInt(1));
			}
			FonctionsSQL.delete("SAEPartiePoule", "IDPoule = " + idsPoules.getInt(1));
		}
	}

	// Retourne un result set contenant les ids des phases finales
	private ResultSet getIdsPhasesFinales() throws SQLException { 
		ResultSet selectIdPhaseFinale = FonctionsSQL.select("SAETournoi", "IDPhaseFinale", "IDTournoi = " + ApplicationEsporter.idTournoi);
		selectIdPhaseFinale.next();
		return FonctionsSQL.select("SAEPartiePhaseFinale", "Id_PartiePhaseFinale", "IDPhaseFinale = " + selectIdPhaseFinale.getInt(1));
	}

	// Retourne true si les demis finales sont terminees
	private boolean demisFinalesTerminees() throws SQLException { 
		ResultSet idsPhasesFinales = getIdsPhasesFinales();
		int nbDemisFinalesTerminees = 0;
		while(idsPhasesFinales.next()) {
			ResultSet phasesDemisFinalesTerminees = FonctionsSQL.select("SAEPartiePhaseFinale", "count(resultat)", "IDPhaseFinale = " + idsPhasesFinales.getInt(1) + " AND resultat = 'aucune'");
			phasesDemisFinalesTerminees.next();
			if(phasesDemisFinalesTerminees.getInt(1) == 0) {
				nbDemisFinalesTerminees++;

			}
		}
		return(nbDemisFinalesTerminees >= 2);
	}

	// Retourne le nom des équipes finalistes
	private ResultSet nomFinalistes() throws SQLException {
		ResultSet idsPhasesFinales = getIdsPhasesFinales();
		idsPhasesFinales.next();
		return FonctionsSQL.select("SAESeQualifier", "nom", "IDPhaseFinale = " + idsPhasesFinales.getInt(1));
	}

	// Supprime les 2 demis finales
	private void delDemisFinales() throws SQLException {
		ResultSet idsPhasesFinales = getIdsPhasesFinales();
		idsPhasesFinales.next();
		ResultSet selectIdPartiePhaseFinale = FonctionsSQL.select("SAEPartiePhaseFinale", "Id_PartiePhaseFinale", "IDPhaseFinale = " + idsPhasesFinales.getInt(1) + " AND resultat != 'aucune'");
		while(selectIdPartiePhaseFinale.next()) {	
			FonctionsSQL.delete("SAECompetiterPhaseFinale", "Id_PartiePhaseFinale = " + selectIdPartiePhaseFinale.getInt(1));
			FonctionsSQL.delete("SAEPartiePhaseFinale", "Id_PartiePhaseFinale = " + selectIdPartiePhaseFinale.getInt(1));
		}
	}

	// Génère les phases finales
	private static void genererFinale() throws SQLException {
		ResultSet selectIdPhaseFinale = FonctionsSQL.select("SAETournoi", "IDPhaseFinale", "IDTournoi = " + ApplicationEsporter.idTournoi);
		selectIdPhaseFinale.next();
		if (compterFinalistes() >= 4) {
			ResultSet finalistes = trouverFinalistes();
			qualifierEquipesPourPhaseFinale(finalistes, selectIdPhaseFinale.getInt(1));
		} else {
			// Cas où il y a une égalité
		}

	}

	// Compte le nombre d'équipe finaliste
	private static int compterFinalistes() throws SQLException {
		ResultSet nbFinaliste = FonctionsSQL.select("SAEConcourir", "count(nom)", "1 = 1 AND classementPoule >= 3");
		nbFinaliste.next();
		return nbFinaliste.getInt(1);
	}

	// Trouve les équipes finalistes
	private static ResultSet trouverFinalistes() throws SQLException {
		return FonctionsSQL.select("SAEConcourir", "nom", "classementPoule >= 3");
	}

	// Ajoute les équipes finalistes dans la table "se qualifier"
	private static void qualifierEquipesPourPhaseFinale(ResultSet finalistes, int idPhaseFinale) throws SQLException {
		while(finalistes.next()) {
			String[]equipeFinaliste = {"'" + finalistes.getString(1) + "'", "" + idPhaseFinale, "0" };
			FonctionsSQL.insert("SAESeQualifier", equipeFinaliste);
		}
		genererMatchDemiFinale(idPhaseFinale);
	}

	// Génère les 2 demis finales
	private static void genererMatchDemiFinale(int idPhaseFinale) throws SQLException {
		ResultSet finalistes = FonctionsSQL.select("SAESeQualifier", "nom", "IDPhaseFinale = " + idPhaseFinale);
		for(int k = 0; k < 2; k++) {
			int idPartiePhaseFinale = FonctionsSQL.newIDPartiePhaseFinale();
			String[]PhaseFinale = { "" + idPartiePhaseFinale , "'aucune'", "" + idPhaseFinale};
			FonctionsSQL.insert("SAEPartiePhaseFinale", PhaseFinale);
			finalistes.next();
			String[]DemiFinale_1 = {"'" + finalistes.getString(1) + "'", "" + idPartiePhaseFinale};
			finalistes.next();
			String[]DemiFinale_2 = {"'" + finalistes.getString(1) + "'", "" + idPartiePhaseFinale};
			FonctionsSQL.insert("SAECompetiterPhaseFinale", DemiFinale_1);
			FonctionsSQL.insert("SAECompetiterPhaseFinale", DemiFinale_2);
		}
	}
	// Défini les actions a effectuer selon le bouton et selon la page sur laquelle on se trouve
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if(!changerDePage(b)) {
			switch(this.etat) {
			case ACCUEIL:
				break;
			case TOURNOI:
				if (b.getText().equals("Voir le(s) jeu(x)")) {
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
				if(b.getText().equals("Accéder")) {
					try {
						ResultSet selectIDTournoi = FonctionsSQL.select("SAETournoi", "IDTournoi", "Lieu = '" + Arbitre_Tournoi.getTable().getValueAt(Arbitre_Tournoi.getTable().getSelectedRow(), 0)
								+ "' AND DATEETHEURE LIKE TO_DATE('" + Arbitre_Tournoi.getTable().getValueAt(Arbitre_Tournoi.getTable().getSelectedRow(), 1) + "', 'YYYY-MM-DD')");
						selectIDTournoi.next();
						ApplicationEsporter.idTournoi = selectIDTournoi.getString(1);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					ApplicationEsporter.f.setContentPane(new Arbitre_InfoTournoi());
					ApplicationEsporter.f.validate();
				}
				break;
			case INFOTOURNOI:
				if(b.getText().equals("Voir les matchs")) {
					ApplicationEsporter.f.setContentPane(new Arbitre_Match());
					ApplicationEsporter.f.validate();
					this.etat = EtatArbitre.MATCHS;
				}
				break;
			case MATCHS:
				if(b.getText().equals("Victoire équipe 1")) {
					String nomEquipe1 = Arbitre_Match.getEquipe(1);
					String nomEquipe2 = Arbitre_Match.getEquipe(2);
					try {
						ResultSet rsIdMatch = FonctionsSQL.select("saecompetiter", "id_partiepoule", "nom = '" + nomEquipe1 + "' INTERSECT select id_partiepoule from saecompetiter where nom = '" + nomEquipe2 + "'");
						rsIdMatch.next();
						int idMatch = rsIdMatch.getInt(1);
						//insertion du nom de l'équipe gagante dans la table partiepoule
						FonctionsSQL.update("saepartiepoule", "resultat", "'" + nomEquipe1 + "'", "id_partiepoule = " + idMatch);

						//ajout du score dans les tables concourir et participer
						String equipeVictorieuse = (String) Arbitre_Match.getTable().getValueAt(Arbitre_Match.getTable().getSelectedRow(), 4);
						ResultSet rsIdPoule = FonctionsSQL.select("saepartiepoule", "idpoule", "id_partiepoule = " + idMatch);
						rsIdPoule.next();
						int idPoule = rsIdPoule.getInt(1);
						ResultSet rsIdTournoi = FonctionsSQL.select("saepoule p, saepartiepoule pp", "p.idtournoi", "p.idpoule = " + idPoule + " and pp.idpoule = p.idpoule and pp.id_partiepoule = " + idMatch);
						rsIdTournoi.next();
						int idTournoi = rsIdTournoi.getInt(1);
						if(equipeVictorieuse != "aucune") {
							FonctionsSQL.update("saeparticiper", "classementfinal", "classementfinal - 1", "nom = '" + equipeVictorieuse + "' and idtournoi = " + idTournoi);
							FonctionsSQL.update("saeconcourir", "classementpoule", "classementpoule - 1", "nom = '" + equipeVictorieuse + "' and idpoule = " + idPoule);
						}
						FonctionsSQL.update("saeparticiper", "classementfinal", "classementfinal + 1", "nom = '" + nomEquipe1 + "' and idtournoi = " + idTournoi);
						FonctionsSQL.update("saeconcourir", "classementpoule", "classementpoule + 1", "nom = '" + nomEquipe1 + "' and idpoule = " + idPoule);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					try {
						if(poulesTermines() && !demisFinalesTerminees()) {
							JOptionPane.showMessageDialog(null, "La phase de poule est terminée !");
							JOptionPane.showMessageDialog(null, "Création des demis finales en cours");
							genererFinale();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					vueMatch.updateTable();
				}
				if(b.getText().equals("Victoire équipe 2")) {
					String nomEquipe1 = Arbitre_Match.getEquipe(1);
					String nomEquipe2 = Arbitre_Match.getEquipe(2);
					try {
						ResultSet rsIdMatch = FonctionsSQL.select("saecompetiter", "id_partiepoule", "nom = '" + nomEquipe1 + "' INTERSECT select id_partiepoule from saecompetiter where nom = '" + nomEquipe2 + "'");
						rsIdMatch.next();
						int idMatch = rsIdMatch.getInt(1);
						//insertion du nom de l'équipe gagnante dans la table partiepoule
						FonctionsSQL.update("saepartiepoule", "resultat", "'" + nomEquipe2 + "'", "id_partiepoule = " + idMatch);

						//ajout du score dans les tables concourir et participer
						String equipeVictorieuse = (String) Arbitre_Match.getTable().getValueAt(Arbitre_Match.getTable().getSelectedRow(), 4);
						ResultSet rsIdPoule = FonctionsSQL.select("saepartiepoule", "idpoule", "id_partiepoule = " + idMatch);
						rsIdPoule.next();
						int idPoule = rsIdPoule.getInt(1);
						ResultSet rsIdTournoi = FonctionsSQL.select("saepoule p, saepartiepoule pp", "p.idtournoi", "p.idpoule = " + idPoule + " and pp.idpoule = p.idpoule and pp.id_partiepoule = " + idMatch);
						rsIdTournoi.next();
						int idTournoi = rsIdTournoi.getInt(1);
						if(equipeVictorieuse != "aucune") {
							FonctionsSQL.update("saeparticiper", "classementfinal", "classementfinal - 1", "nom = '" + equipeVictorieuse + "' and idtournoi = " + idTournoi);
							FonctionsSQL.update("saeconcourir", "classementpoule", "classementpoule - 1", "nom = '" + equipeVictorieuse + "' and idpoule = " + idPoule);
						}
						FonctionsSQL.update("saeparticiper", "classementfinal", "classementfinal + 1", "nom = '" + nomEquipe2 + "' and idtournoi = " + idTournoi);
						FonctionsSQL.update("saeconcourir", "classementpoule", "classementpoule + 1", "nom = '" + nomEquipe2 + "' and idpoule = " + idPoule);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					try {
						if(poulesTermines() && !demisFinalesTerminees()) {
							JOptionPane.showMessageDialog(null, "La phase de poule est terminée !");
							JOptionPane.showMessageDialog(null, "Création des demis finales en cours");
							genererFinale();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					vueMatch.updateTable();
				}
				if(b.getText().equals("Victoire équipe 1 ")) {
					String nomEquipe1 = Arbitre_Match.getEquipe(1);
					String nomEquipe2 = Arbitre_Match.getEquipe(2);
					try {
						ResultSet rsIdMatch = FonctionsSQL.select("saecompetiterphasefinale", "id_partiephasefinale", "nom = '" + nomEquipe1 + "' INTERSECT select id_partiephasefinale from saecompetiterphasefinale where nom = '" + nomEquipe2 + "'");
						rsIdMatch.next();
						int idMatch = rsIdMatch.getInt(1);
						//insertion du nom de l'équipe gagante dans la table partiepoule
						FonctionsSQL.update("saepartiephasefinale", "resultat", "'" + nomEquipe1 + "'", "id_partiephasefinale = " + idMatch);

						//ajout du score dans les tables concourir et participer
						String equipeVictorieuse = (String) Arbitre_Match.getTable().getValueAt(Arbitre_Match.getTable().getSelectedRow(), 4);
						ResultSet rsIdPoule = FonctionsSQL.select("saepartiephasefinale", "idphasefinale", "id_partiephasefinale = " + idMatch);
						rsIdPoule.next();
						int idPoule = rsIdPoule.getInt(1);
						ResultSet rsIdTournoi = FonctionsSQL.select("saephasefinale f, saepartiephasefinale pf, saetournoi t", "t.idtournoi", "f.idphasefinale = " + idPoule + " and pf.idphasefinale = f.idphasefinale and f.idphasefinale = t.idphasefinale and pf.id_partiephasefinale = " + idMatch);
						rsIdTournoi.next();
						int idTournoi = rsIdTournoi.getInt(1);
						if(!equipeVictorieuse.equals("aucune")) {
							FonctionsSQL.update("saeparticiper", "classementfinal", "classementfinal - 2", "nom = '" + equipeVictorieuse + "' and idtournoi = " + idTournoi);
							FonctionsSQL.update("saesequalifier", "classementphasefinale", "classementphasefinale - 2", "nom = '" + equipeVictorieuse + "' and idphasefinale = " + idPoule);
						}
						FonctionsSQL.update("saeparticiper", "classementfinal", "classementfinal + 2", "nom = '" + nomEquipe1 + "' and idtournoi = " + idTournoi);
						FonctionsSQL.update("saesequalifier", "classementphasefinale", "classementphasefinale + 2", "nom = '" + nomEquipe1 + "' and idphasefinale = " + idPoule);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					try {
						if(demisFinalesTerminees()) {
							//delDemisFinales();
						}
						vueMatch.updateTableFinale();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					vueMatch.updateTableFinale();
				}
				if(b.getText().equals("Victoire équipe 2 ")) {
					String nomEquipe1 = Arbitre_Match.getEquipe(1);
					String nomEquipe2 = Arbitre_Match.getEquipe(2);
					try {
						ResultSet rsIdMatch = FonctionsSQL.select("saecompetiterphasefinale", "id_partiephasefinale", "nom = '" + nomEquipe1 + "' INTERSECT select id_partiephasefinale from saecompetiterphasefinale where nom = '" + nomEquipe2 + "'");
						rsIdMatch.next();
						int idMatch = rsIdMatch.getInt(1);
						//insertion du nom de l'équipe gagante dans la table partiepoule
						FonctionsSQL.update("saepartiephasefinale", "resultat", "'" + nomEquipe2 + "'", "id_partiephasefinale = " + idMatch);

						//ajout du score dans les tables concourir et participer
						String equipeVictorieuse = (String) Arbitre_Match.getTable().getValueAt(Arbitre_Match.getTable().getSelectedRow(), 4);
						ResultSet rsIdPoule = FonctionsSQL.select("saepartiephasefinale", "idphasefinale", "id_partiephasefinale = " + idMatch);
						rsIdPoule.next();
						int idPoule = rsIdPoule.getInt(1);
						ResultSet rsIdTournoi = FonctionsSQL.select("saephasefinale f, saepartiephasefinale pf, saetournoi t", "t.idtournoi", "f.idphasefinale = " + idPoule + " and pf.idphasefinale = f.idphasefinale and f.idphasefinale = t.idphasefinale and pf.id_partiephasefinale = " + idMatch);
						rsIdTournoi.next();
						int idTournoi = rsIdTournoi.getInt(1);
						if(equipeVictorieuse != "aucune") {
							FonctionsSQL.update("saeparticiper", "classementfinal", "classementfinal - 2", "nom = '" + equipeVictorieuse + "' and idtournoi = " + idTournoi);
							FonctionsSQL.update("saesequalifier", "classementphasefinale", "classementphasefinale - 2", "nom = '" + equipeVictorieuse + "' and idphasefinale = " + idPoule);
						}
						FonctionsSQL.update("saeparticiper", "classementfinal", "classementfinal + 2", "nom = '" + nomEquipe2 + "' and idtournoi = " + idTournoi);
						FonctionsSQL.update("saesequalifier", "classementphasefinale", "classementphasefinale + 2", "nom = '" + nomEquipe2 + "' and idphasefinale = " + idPoule);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					try {
						if(demisFinalesTerminees()) {
							//delDemisFinales();
						}
						vueMatch.updateTableFinale();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
}