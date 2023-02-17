package Modele;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import Modele.BDD.NomTablesBDD;
import Vue.ApplicationEsporter;

public class Compte {

	public enum Type {ESPORTER, ARBITRE, ECURIE};
	
	private static final NomTablesBDD NOM_TABLE = NomTablesBDD.SAECOMPTE; 
	
	private String nomUtilisateur, mdp;
	private int idType;
	
	// Constructeur de la classe " Compte"
	private Compte(String nomUtilisateur, String mdp, Type type) { 
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
	
	// Crée un nouveau compte
	public static int creerCompte(String nomUtilisateur, String mdp, Type type) throws Exception { 
		if (nomUtilisateur.isEmpty() || mdp.isEmpty()) { // Lève l'exception si le champ d'utilisateur ou si le champ du mot de passe est vide
			throw new Exception("Mdp / Nom d'utilisateur vide");
		}
		Compte compte = new Compte(nomUtilisateur, mdp, type);
		String[] compteACreer = new String[1];
		compteACreer[0] = FonctionsSQL.newID(NOM_TABLE) + ", '" + compte.nomUtilisateur + "', '" + compte.mdp + "', " + compte.idType  ;
		if (!compteExiste(nomUtilisateur)) { // Effectue l'ajout uniquement si le nom d'utilisateur n'existe pas
            FonctionsSQL.insert(NOM_TABLE, compteACreer);
            return 1;
        }
		return -1;
	}
	
	// Permet de se connecter si le compte existe et que le mot de passe est bon
	public static void chargerCompte(String login, String mdp) {
		try {
			ResultSet rsCompte = FonctionsSQL.select(NOM_TABLE, "type, idcompte", "utilisateur = '" + login + "' AND mdp = '" + crypterMdp(mdp) + "'");
			rsCompte.next();
			ApplicationEsporter.idCompte = rsCompte.getInt("idcompte");
			ApplicationEsporter.idTypeCompte = rsCompte.getInt("type");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Vérifie si le compte existe
	public static boolean compteExiste(String login) {
		try {
			ResultSet rsCompte = FonctionsSQL.select(NOM_TABLE, "utilisateur", "utilisateur = '" + login + "'");
	        return(rsCompte.next());
		} catch(SQLException e) {
			e.printStackTrace();
		}
        return false;
    }
	
	// Vérifie si le mot de passe est bon
	public static boolean mdpOK(String login, String mdp) {
		try {
			ResultSet rsCompte = FonctionsSQL.select(NOM_TABLE, "idcompte" , "utilisateur = '" + login + "' AND mdp = '" + crypterMdp(mdp) + "'");
			return(rsCompte.next());
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// Permet de changer le mot de passe pour un utilisateur
	public static void changerMdp(String login, String mdp, String nouveauMdp) { 
		FonctionsSQL.update(NOM_TABLE, "mdp", "'" + Compte.crypterMdp(nouveauMdp) + "'", "utilisateur = " + "'" + login + "'" + "AND mdp = '" + Compte.crypterMdp(mdp) + "'");
	}
	
	public static String crypterMdp(String mdp) { // Chiffre le mot de passe
		try {
			MessageDigest msg = MessageDigest.getInstance("SHA-256");
	        byte[] hash = msg.digest(mdp.getBytes(StandardCharsets.UTF_8));
	        
	        StringBuilder s = new StringBuilder();
	        for (byte b : hash) {
	            s.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
	        }
	        return s.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	

}