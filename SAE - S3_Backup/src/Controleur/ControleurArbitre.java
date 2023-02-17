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

	public enum EtatArbitre {ACCUEIL, TOURNOI, INFOTOURNOI, MATCHS}

	private EtatArbitre etat;
	private Arbitre_Match vueMatch;

	// Constructeur pour acc�der � une m�thode de Arbitre_Match
	public ControleurArbitre(Arbitre_Match vueMatch, EtatArbitre etat) {
		this.vueMatch = vueMatch;
		this.etat = etat;
	}

	// Constructeur du controleur arbitre
	public ControleurArbitre(JPanel vue, EtatArbitre etat) {
		this.etat = etat;
	}

	// Methode pour faciliter la navigation depuis n'importe o� en tant qu'Arbitre
	private Boolean changerDePage(JButton b) {
		if (b.getText().equals("D�connexion")) {
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

	// M�thode pour se d�connecter
	private void goDeconnexion() {
		ApplicationEsporter.f.setContentPane(new PageAccueil());
		ApplicationEsporter.f.validate();
	}

	// M�thode pour acc�der � la page d'accueil
	private void goAccueil() {
		ApplicationEsporter.f.setContentPane(new Arbitre_Accueil());
		ApplicationEsporter.f.validate();
	}

	// M�thode pour acc�der � la page des tournois
	private void goTournois() {
		ApplicationEsporter.f.setContentPane(new Arbitre_Tournoi());
		ApplicationEsporter.f.validate();
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
			while(idsPoules.next()) {
				ResultSet pouleTermines = FonctionsSQL.select(NomTablesBDD.SAEPARTIEPOULE, "count(resultat)", "IDPoule = " + idsPoules.getInt(1) + "AND resultat = 'aucune'");
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

	// G�n�re les phases finales
	private static void genererFinale() {
		try {
			ResultSet selectIdPhaseFinale = FonctionsSQL.select(NomTablesBDD.SAETOURNOI, "IDPhaseFinale", "IDTournoi = " + ApplicationEsporter.idTournoi);
			selectIdPhaseFinale.next();
			if (compterFinalistes() >= 4) {
				ResultSet finalistes = trouverFinalistes();
				qualifierEquipesPourPhaseFinale(finalistes, selectIdPhaseFinale.getInt(1));
			} else {
				// Cas o� il y a une �galit�
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	// Compte le nombre d'�quipe finaliste
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

	// Trouve les �quipes finalistes
	private static ResultSet trouverFinalistes() {
		return FonctionsSQL.select(NomTablesBDD.SAECONCOURIR, "nom", "classementPoule >= 3");
	}

	// Ajoute les �quipes finalistes dans la table "se qualifier"
	private static void qualifierEquipesPourPhaseFinale(ResultSet finalistes, int idPhaseFinale) {
		try {
			while(finalistes.next()) {
				String[]equipeFinaliste = {"'" + finalistes.getString(1) + "'", "" + idPhaseFinale, "0" };
				FonctionsSQL.insert(NomTablesBDD.SAESEQUALIFIER, equipeFinaliste);
			}
			genererMatchDemiFinale(idPhaseFinale);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	// G�n�re les 2 demis finales
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

	// D�fini les actions a effectuer selon le bouton et selon la page sur laquelle on se trouve
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
						ResultSet selectTournoi = FonctionsSQL.select(NomTablesBDD.SAETOURNOI, "idtournoi", "Lieu = '" + Arbitre_Tournoi.getTable().getValueAt(Arbitre_Tournoi.getTable().getSelectedRow(), 0) + "'");
						selectTournoi.next();
						ResultSet jeux = FonctionsSQL.select(NomTablesBDD.SAECONCERNER, "nom", "idtournoi = " + selectTournoi.getInt(1));
						String afficherJeux = "Liste des jeux : \n";
						while(jeux.next()) {
							afficherJeux += "   - " + jeux.getString(1) + "\n";
						}
						JOptionPane.showMessageDialog(null, afficherJeux);
					} catch(SQLException e1) {
						e1.printStackTrace();
					}
				}
				if(b.getText().equals("Acc�der")) {
					try {
						ResultSet selectIDTournoi = FonctionsSQL.select(NomTablesBDD.SAETOURNOI, "IDTournoi", "Lieu = '" + Arbitre_Tournoi.getTable().getValueAt(Arbitre_Tournoi.getTable().getSelectedRow(), 0)
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
				if(b.getText().equals("Victoire �quipe 1")) {
					String nomEquipe1 = Arbitre_Match.getEquipe(1);
					String nomEquipe2 = Arbitre_Match.getEquipe(2);
					try {
						ResultSet rsIdMatch = FonctionsSQL.select(NomTablesBDD.SAECOMPETITER, "id_partiepoule", "nom = '" + nomEquipe1 + "' INTERSECT select id_partiepoule from saecompetiter where nom = '" + nomEquipe2 + "'");
						rsIdMatch.next();
						int idMatch = rsIdMatch.getInt(1);
						//insertion du nom de l'�quipe gagante dans la table partiepoule
						FonctionsSQL.update(NomTablesBDD.SAEPARTIEPOULE, "resultat", "'" + nomEquipe1 + "'", "id_partiepoule = " + idMatch);

						//ajout du score dans les tables concourir et participer
						String equipeVictorieuse = (String) Arbitre_Match.getTable().getValueAt(Arbitre_Match.getTable().getSelectedRow(), 4);
						ResultSet rsIdPoule = FonctionsSQL.select(NomTablesBDD.SAEPARTIEPOULE, "idpoule", "id_partiepoule = " + idMatch);
						rsIdPoule.next();
						int idPoule = rsIdPoule.getInt(1);
						ResultSet rsIdTournoi = FonctionsSQL.select("saepoule p, saepartiepoule pp", "p.idtournoi", "p.idpoule = " + idPoule + " and pp.idpoule = p.idpoule and pp.id_partiepoule = " + idMatch);
						rsIdTournoi.next();
						int idTournoi = rsIdTournoi.getInt(1);
						if(!equipeVictorieuse.equals("aucune")) {
							FonctionsSQL.update(NomTablesBDD.SAEPARTICIPER, "classementfinal", "classementfinal - 1", "nom = '" + equipeVictorieuse + "' and idtournoi = " + idTournoi);
							FonctionsSQL.update(NomTablesBDD.SAECONCOURIR, "classementpoule", "classementpoule - 1", "nom = '" + equipeVictorieuse + "' and idpoule = " + idPoule);

						}
						FonctionsSQL.update(NomTablesBDD.SAEPARTICIPER, "classementfinal", "classementfinal + 1", "nom = '" + nomEquipe1 + "' and idtournoi = " + idTournoi);
						FonctionsSQL.update(NomTablesBDD.SAECONCOURIR, "classementpoule", "classementpoule + 1", "nom = '" + nomEquipe1 + "' and idpoule = " + idPoule);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					if(poulesTermines() && !demisFinalesTerminees()) {
						JOptionPane.showMessageDialog(null, "La phase de poule est termin�e !");
						JOptionPane.showMessageDialog(null, "Cr�ation des demis finales en cours");
						genererFinale();
						//delPoules();
						vueMatch.updateTable();
					}
					if(b.getText().equals("Victoire �quipe 2")) {
						try {
							ResultSet rsIdMatch = FonctionsSQL.select(NomTablesBDD.SAECOMPETITER, "id_partiepoule", "nom = '" + nomEquipe1 + "' INTERSECT select id_partiepoule from saecompetiter where nom = '" + nomEquipe2 + "'");
							rsIdMatch.next();
							int idMatch = rsIdMatch.getInt(1);
							//insertion du nom de l'�quipe gagnante dans la table partiepoule
							FonctionsSQL.update(NomTablesBDD.SAEPARTIEPOULE, "resultat", "'" + nomEquipe2 + "'", "id_partiepoule = " + idMatch);

							//ajout du score dans les tables concourir et participer
							String equipeVictorieuse = (String) Arbitre_Match.getTable().getValueAt(Arbitre_Match.getTable().getSelectedRow(), 4);
							ResultSet rsIdPoule = FonctionsSQL.select(NomTablesBDD.SAEPARTIEPOULE, "idpoule", "id_partiepoule = " + idMatch);
							rsIdPoule.next();
							int idPoule = rsIdPoule.getInt(1);
							ResultSet rsIdTournoi = FonctionsSQL.select("saepoule p, saepartiepoule pp", "p.idtournoi", "p.idpoule = " + idPoule + " and pp.idpoule = p.idpoule and pp.id_partiepoule = " + idMatch);
							rsIdTournoi.next();
							int idTournoi = rsIdTournoi.getInt(1);
							if(!equipeVictorieuse.equals("aucune")) {
								FonctionsSQL.update(NomTablesBDD.SAEPARTICIPER, "classementfinal", "classementfinal - 1", "nom = '" + equipeVictorieuse + "' and idtournoi = " + idTournoi);
								FonctionsSQL.update(NomTablesBDD.SAECONCOURIR, "classementpoule", "classementpoule - 1", "nom = '" + equipeVictorieuse + "' and idpoule = " + idPoule);
							}
							FonctionsSQL.update(NomTablesBDD.SAEPARTICIPER, "classementfinal", "classementfinal + 1", "nom = '" + nomEquipe2 + "' and idtournoi = " + idTournoi);
							FonctionsSQL.update(NomTablesBDD.SAECONCOURIR, "classementpoule", "classementpoule + 1", "nom = '" + nomEquipe2 + "' and idpoule = " + idPoule);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						if(poulesTermines() && !demisFinalesTerminees()) {
							JOptionPane.showMessageDialog(null, "La phase de poule est termin�e !");
							JOptionPane.showMessageDialog(null, "Cr�ation des demis finales en cours");
							genererFinale();
							//delPoules();
							vueMatch.updateTable();
						}
						if(b.getText().equals("Victoire �quipe 1 ")) {
							try {
								ResultSet rsIdMatch = FonctionsSQL.select(NomTablesBDD.SAECOMPETITERPHASEFINALE, "id_partiephasefinale", "nom = '" + nomEquipe1 + "' INTERSECT select id_partiephasefinale from saecompetiterphasefinale where nom = '" + nomEquipe2 + "'");
								rsIdMatch.next();
								int idMatch = rsIdMatch.getInt(1);
								//insertion du nom de l'�quipe gagante dans la table partiepoule
								FonctionsSQL.update(NomTablesBDD.SAEPARTIEPHASEFINALE, "resultat", "'" + nomEquipe1 + "'", "id_partiephasefinale = " + idMatch);

								//ajout du score dans les tables concourir et participer
								String equipeVictorieuse = (String) Arbitre_Match.getTable().getValueAt(Arbitre_Match.getTable().getSelectedRow(), 4);
								ResultSet rsIdPoule = FonctionsSQL.select(NomTablesBDD.SAEPARTIEPHASEFINALE, "idphasefinale", "id_partiephasefinale = " + idMatch);
								rsIdPoule.next();
								int idPoule = rsIdPoule.getInt(1);
								ResultSet rsIdTournoi = FonctionsSQL.select("saephasefinale f, saepartiephasefinale pf, saetournoi t", "t.idtournoi", "f.idphasefinale = " + idPoule + " and pf.idphasefinale = f.idphasefinale and f.idphasefinale = t.idphasefinale and pf.id_partiephasefinale = " + idMatch);
								rsIdTournoi.next();
								int idTournoi = rsIdTournoi.getInt(1);
								if(!equipeVictorieuse.equals("aucune")) {
									FonctionsSQL.update(NomTablesBDD.SAEPARTICIPER, "classementfinal", "classementfinal - 2", "nom = '" + equipeVictorieuse + "' and idtournoi = " + idTournoi);
									FonctionsSQL.update(NomTablesBDD.SAESEQUALIFIER, "classementphasefinale", "classementphasefinale - 2", "nom = '" + equipeVictorieuse + "' and idphasefinale = " + idPoule);
								}
								FonctionsSQL.update(NomTablesBDD.SAEPARTICIPER, "classementfinal", "classementfinal + 2", "nom = '" + nomEquipe1 + "' and idtournoi = " + idTournoi);
								FonctionsSQL.update(NomTablesBDD.SAESEQUALIFIER, "classementphasefinale", "classementphasefinale + 2", "nom = '" + nomEquipe1 + "' and idphasefinale = " + idPoule);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							vueMatch.updateTableFinale();
						}
						if(b.getText().equals("Victoire �quipe 2 ")) {
							try {
								ResultSet rsIdMatch = FonctionsSQL.select(NomTablesBDD.SAECOMPETITERPHASEFINALE, "id_partiephasefinale", "nom = '" + nomEquipe1 + "' INTERSECT select id_partiephasefinale from saecompetiterphasefinale where nom = '" + nomEquipe2 + "'");
								rsIdMatch.next();
								int idMatch = rsIdMatch.getInt(1);
								//insertion du nom de l'�quipe gagante dans la table partiepoule
								FonctionsSQL.update(NomTablesBDD.SAEPARTIEPHASEFINALE, "resultat", "'" + nomEquipe2 + "'", "id_partiephasefinale = " + idMatch);

								//ajout du score dans les tables concourir et participer
								String equipeVictorieuse = (String) Arbitre_Match.getTable().getValueAt(Arbitre_Match.getTable().getSelectedRow(), 4);
								ResultSet rsIdPoule = FonctionsSQL.select(NomTablesBDD.SAEPARTIEPHASEFINALE, "idphasefinale", "id_partiephasefinale = " + idMatch);
								rsIdPoule.next();
								int idPoule = rsIdPoule.getInt(1);
								ResultSet rsIdTournoi = FonctionsSQL.select("saephasefinale f, saepartiephasefinale pf, saetournoi t", "t.idtournoi", "f.idphasefinale = " + idPoule + " and pf.idphasefinale = f.idphasefinale and f.idphasefinale = t.idphasefinale and pf.id_partiephasefinale = " + idMatch);
								rsIdTournoi.next();
								int idTournoi = rsIdTournoi.getInt(1);
								if(!equipeVictorieuse.equals("aucune")) {
									FonctionsSQL.update(NomTablesBDD.SAEPARTICIPER, "classementfinal", "classementfinal - 2", "nom = '" + equipeVictorieuse + "' and idtournoi = " + idTournoi);
									FonctionsSQL.update(NomTablesBDD.SAESEQUALIFIER, "classementphasefinale", "classementphasefinale - 2", "nom = '" + equipeVictorieuse + "' and idphasefinale = " + idPoule);
								}
								FonctionsSQL.update(NomTablesBDD.SAEPARTICIPER, "classementfinal", "classementfinal + 2", "nom = '" + nomEquipe2 + "' and idtournoi = " + idTournoi);
								FonctionsSQL.update(NomTablesBDD.SAESEQUALIFIER, "classementphasefinale", "classementphasefinale + 2", "nom = '" + nomEquipe2 + "' and idphasefinale = " + idPoule);
								if(demisFinalesTerminees()) {
									//delDemisFinales();
									vueMatch.updateTableFinale();
								} 
							} catch(SQLException e1) {

								e1.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
}