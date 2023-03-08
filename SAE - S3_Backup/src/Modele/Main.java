package Modele;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import Modele.BDD.NomTablesBDD;

public class Main {

	public static void main(String[] args) throws NoSuchAlgorithmException, Exception {

		ResultSet select = FonctionsSQL.select(NomTablesBDD.SAECOMPTE, "*", "1 = 1 ORDER BY 1");
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