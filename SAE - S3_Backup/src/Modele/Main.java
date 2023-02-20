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
}