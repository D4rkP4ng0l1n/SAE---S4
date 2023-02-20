package Modele;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import Modele.BDD.NomTablesBDD;
import Vue.ApplicationEsporter;

public class Main {

	public static void main(String[] args) throws NoSuchAlgorithmException, Exception {
		
		
		String login = "TestJules";
		String mdp = "1234";
		System.out.println(crypterMdp(mdp));
		ResultSet rsCompte = FonctionsSQL.select(NomTablesBDD.SAECOMPTE, "type, idcompte", "utilisateur = '" + login + "' AND mdp = '" + crypterMdp(mdp) + "'");
		rsCompte.next();
		//System.out.println(rsCompte.getInt("idcompte"));
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