package Modele;

public class BDD {
	public enum NomTablesBDD {
		SAECOMPETITER, SAECOMPETITERPHASEFINALE, SAECOMPTE, SAECONCERNER, 
		SAECONCOURIR, SAEECURIE, SAEEQUIPE, SAEJEU, SAEJOUEUR, SAEPARTICIPER,
		SAEPARTIEPHASEFINALE, SAEPARTIEPOULE, SAEPHASEFINALE,
		SAEPOULE, SAESEQUALIFIER, SAETOURNOI, 
	};
	
	public static String getNomId(NomTablesBDD nomTable) {
		switch(nomTable) {
		case SAECOMPTE:
			return "idcompte";
		case SAEJOUEUR:
			return "idJoueur";
		case SAEPARTIEPHASEFINALE:
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
