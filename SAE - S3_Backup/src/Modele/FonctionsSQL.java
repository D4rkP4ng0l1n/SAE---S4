package Modele;

import java.sql.*;

import Modele.BDD.NomTablesBDD;

public class FonctionsSQL {

	private static Statement statement;
	private static ResultSet resultSet;
	
	// Sélectionne dans une table des données
	public static ResultSet select(NomTablesBDD nomTable, String select, String conditions) { 
		try {
			Connexion.seConnecter();
			statement = Connexion.getConnexion().createStatement();
			if (conditions != "") {
				resultSet = statement.executeQuery(	"SELECT " + select + 
													" FROM CRJ3957A." + nomTable + 
													" WHERE " + conditions );
			} else {
				resultSet = statement.executeQuery("SELECT " + select + " FROM " + nomTable);
			}
			return resultSet;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Supprime les lignes d'une table qui corresponde au condition
	public static void delete(NomTablesBDD nomTable, String conditions) {
		try {
			Connexion.seConnecter();
			statement = Connexion.getConnexion().createStatement();
			statement.executeUpdate("DELETE FROM CRJ3957A." + nomTable + " WHERE " + conditions);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	// Insere une ligne dans une table
	public static void insert(NomTablesBDD nomTable, String[]aInserer) { 
		try {
			Connexion.seConnecter();
			statement = Connexion.getConnexion().createStatement();
			String insert = creationStringAInserer(aInserer);
			statement.executeUpdate("INSERT INTO CRJ3957A." + nomTable + " VALUES(" + insert + ")"  );
			Connexion.closeConnexion();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Creation d'un String à inserer dans la table à partir d'un tableau de String pour la fonction SQL INSERT
	private static String creationStringAInserer(String[] aInserer) {
		String insert = "";
		for (String i : aInserer) {
			insert += i + ", ";
		}
		return insert.substring(0, insert.length() - 2);
	}

	// Modifie les lignes dans une table qui respecte les conditions
	public static void update(NomTablesBDD nomTable, String colonne, String nouvelleValeur, String conditions) {
		try {
			Connexion.seConnecter();
			statement = Connexion.getConnexion().createStatement();
			statement.executeUpdate("UPDATE CRJ3957A." + nomTable + 
									" SET " + colonne + " = " + nouvelleValeur + 
									" WHERE " + conditions);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	// Récupère les id d'une table
	public static int getId(NomTablesBDD nomTable, String condition) {
		try {
			Connexion.seConnecter();
			Statement statement = Connexion.getConnexion().createStatement();
			ResultSet id = statement.executeQuery(	"SELECT idcompte FROM CRJ3957A." + nomTable + 
													" WHERE " + condition );
			id.next();
			return id.getInt(1);
		} catch(SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	// Donne une ID inexistante a un compte
	public static int newID(NomTablesBDD nomTable) {
		try {
			Connexion.seConnecter();
			statement = Connexion.getConnexion().createStatement();
			resultSet = statement.executeQuery("SELECT max(" + BDD.getNomId(nomTable) + ") FROM " + nomTable);
			resultSet.next();
			return resultSet.getInt(1) + 1;
		} catch(SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
}
