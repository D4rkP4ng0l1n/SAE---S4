package Modele;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import Modele.BDD.NomTablesBDD;
import Vue.ApplicationEsporter;

public class Main {

	public static void main(String[] args) throws NoSuchAlgorithmException, Exception {

		ResultSet select = FonctionsSQL.select(NomTablesBDD.SAECOMPTE, "*", "");
		for (int i = 1; i < 5; i++) {
			if (i == 4) {
				System.out.print(select.getMetaData().getColumnName(i) + "\n");
			} else {
				System.out.print(select.getMetaData().getColumnName(i) + " - ");
			}
		}
		while(select.next()) {
			System.out.println(select.getString(1) + " - " + select.getString(2) + " - " + select.getString(3) + " - " + select.getString(4));
		}
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
