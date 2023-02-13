package Modele;

import java.sql.*;

public class Connexion {    

	private static Connection connexion;

	private Connexion() { // Constructeur de la classe " Connexion "
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch(SQLException e) {
			e.printStackTrace();
		}

		try {
			connexion = DriverManager.getConnection("jdbc:oracle:thin:@telline.univ-tlse3.fr:1521:ETUPRE", "CRJ3957A", "ratio");
		} catch(Exception ee) {
			ee.printStackTrace();
		}
	}

	public static void seConnecter() { // Permet de se connecter a un compte
		new Connexion();
	}

	public static Connection getConnexion() { // Récupère la connexion
		return connexion;
	}

	public static void closeConnexion() throws SQLException { // Ferme la connexion
		connexion.close();
	}

}