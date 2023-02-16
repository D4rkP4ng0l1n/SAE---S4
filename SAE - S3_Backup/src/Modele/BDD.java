package Modele;

public class BDD {
	public enum NomTablesBDD {
		SAECOMPETITER, SAECOMPETITERPHASEFINALE, SAECOMPTE, SAECONCERNER, 
		SAECONCOURIR, SAEEQUIPE, SAEJEU, SAEJOUEUR, SAEPARTICIPER,
		SAEPARTICIPERPHASEFINALE, SAEPARTIEPOULE, SAEPHASEFINALE,
		SAEPOULE, SAESEQUALIFIER, SAETOURNOI
	};
	
	public static String getNomId(NomTablesBDD nomTable) {
		switch(nomTable) {
		case SAECOMPTE:
			return "idcompte";
		case SAEJOUEUR:
			return "idJoueur";
		case SAEPARTICIPERPHASEFINALE:
			return "id_PartiePhaseFinale";
		case SAEPARTIEPOULE:
			return "id_PartiePoule";
		case SAEPHASEFINALE:
			return "idPhaseFinale";
		case SAEPOULE:
			return "idPoule";
		case SAETOURNOI:
			return "idTournoi";
		default:
			return "Erreur nom table";
		
		}
	}
}
