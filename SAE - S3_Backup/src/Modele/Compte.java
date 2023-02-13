package Modele;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import Vue.ApplicationEsporter;

public class Compte {

	public enum Type {ESPORTER, ARBITRE, ECURIE};
	
	private static final String NOM_TABLE = "saecompte"; 
	
	private String nomUtilisateur, mdp;
	private int idType;
	
	private Compte(String nomUtilisateur, String mdp, Type type) throws NoSuchAlgorithmException { // Costructeur de la classe " Compte "
		this.nomUtilisateur = nomUtilisateur;

		this.mdp = crypterMdp(mdp);
		
		switch(type) {
			case ESPORTER:
				this.idType = 1;
				break;
			case ARBITRE:
				this.idType = 2;
				break;
			case ECURIE:
				this.idType = 3;
				break;
		}
	}
	
	public static int creerCompte(String nomUtilisateur, String mdp, Type type) throws NoSuchAlgorithmException, SQLException, Exception { // Crée un nouveau compte
		if (nomUtilisateur.isEmpty() || mdp.isEmpty()) { // Lève l'exception si le champ d'utilisateur ou si le champ du mot de passe est vide
			throw new Exception("Mdp / Nom d'utilisateur vide");
		}
		Compte compte = new Compte(nomUtilisateur, mdp, type);
		String[] compteACreer = new String[1];
		compteACreer[0] = FonctionsSQL.newID(NOM_TABLE) + ", '" + compte.nomUtilisateur + "', '" + compte.mdp + "', " + compte.idType  ;
		if (!compteExiste(nomUtilisateur)) { // Effectue l'ajout uniquement si le nom d'utilisateur n'existe pas
			
            FonctionsSQL.insert("saecompte", compteACreer);
            return 1;
        }
		return -1;
	}
	
	public static void chargerCompte(String login, String mdp) throws SQLException, NoSuchAlgorithmException { // Permet de se connecter si le compte existe et que le mot de passe est bon
		ResultSet rsCompte = FonctionsSQL.select(NOM_TABLE, "type, idcompte", "utilisateur = '" + login + "' AND mdp = '" + crypterMdp(mdp) + "'");
		rsCompte.next();
		ApplicationEsporter.idCompte = rsCompte.getInt("idcompte");
		ApplicationEsporter.idTypeCompte = rsCompte.getInt("type");
		
	}
	
	public static boolean compteExiste(String login) throws SQLException { // Vérifie si le compte existe
        ResultSet rsCompte = FonctionsSQL.select(NOM_TABLE, "utilisateur", "utilisateur = '" + login + "'");
        if (rsCompte.next()) {
            return true;
        } 
        return false;
    }
	
	public static boolean mdpOK(String login, String mdp) throws NoSuchAlgorithmException, SQLException { // Vérifie si le mot de passe est bon
		ResultSet rsCompte = FonctionsSQL.select(NOM_TABLE, "idcompte" , "utilisateur = '" + login + "' AND mdp = '" + crypterMdp(mdp) + "'");
		if (rsCompte.next()) {
			return true;
		}
		return false;
	}
	
	public static void changerMdp(String login, String mdp, String nouveauMdp) throws Exception { // Permet de changer le mot de passe pour un utilisateur
		FonctionsSQL.update(NOM_TABLE, "mdp", "'" + Compte.crypterMdp(nouveauMdp) + "'", "utilisateur = " + "'" + login + "'" + "AND mdp = '" + Compte.crypterMdp(mdp) + "'");
	}
	
	public static String crypterMdp(String mdp) throws NoSuchAlgorithmException { // Chiffre le mot de passe
		MessageDigest msg = MessageDigest.getInstance("SHA-256");
        byte[] hash = msg.digest(mdp.getBytes(StandardCharsets.UTF_8));
        
        StringBuilder s = new StringBuilder();
        for (byte b : hash) {
            s.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return s.toString();
	}
	

}