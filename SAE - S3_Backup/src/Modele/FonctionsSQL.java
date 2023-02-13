package Modele;

import java.sql.*;

public class FonctionsSQL {

	public static ResultSet select(String nomTable, String select, String conditions) throws SQLException { // Sélectionne dans une table des données
		Connexion.seConnecter();
		Statement statement = Connexion.getConnexion().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		ResultSet resultSet;
		if (conditions != "") {
			resultSet = statement.executeQuery("SELECT " + select + 
					" FROM CRJ3957A." + nomTable + 
					" WHERE " + conditions );
		} else {
			resultSet = statement.executeQuery("SELECT " + select + " FROM " + nomTable);
		}
		return resultSet;
	}

	public static void delete(String nomTable, String conditions) throws SQLException { // Supprime les lignes d'une table qui corresponde au condition
		Connexion.seConnecter();
		Statement statement = Connexion.getConnexion().createStatement();
		statement.executeUpdate("DELETE FROM CRJ3957A." + nomTable + " WHERE " + conditions);
		FonctionsSQL.commit();
		Connexion.closeConnexion();
	}

	public static void insert(String nomTable, String[]aInserer) throws SQLException { // Insere une ligne dans une table
		Connexion.seConnecter();
		Statement statement = Connexion.getConnexion().createStatement();
		String insert = "";
		for (String i : aInserer) {
			insert += i + ", ";
		}
		insert = insert.substring(0, insert.length() - 2);
		statement.executeUpdate("INSERT INTO CRJ3957A." + nomTable + " VALUES(" + insert + ")"  );
		FonctionsSQL.commit();
		Connexion.closeConnexion();
	}

	public static void update(String nomTable, String colonne, String nouvelleValeur, String conditions) throws Exception { // Modifie les lignes dans une table qui respecte les conditions
		Connexion.seConnecter();
		Statement statement = Connexion.getConnexion().createStatement();
		statement.executeUpdate("UPDATE CRJ3957A." + nomTable + 
				" SET " + colonne + " = " + nouvelleValeur + 
				" WHERE " + conditions);
	}

	private static void commit() throws SQLException { // Valide les modifications
		Connexion.seConnecter();
		Statement statement = Connexion.getConnexion().createStatement();
		statement.executeUpdate("commit");
	}

	public static int getId(String nomTable, String condition) throws SQLException { // Récupère les id d'une table
		Connexion.seConnecter();
		Statement statement = Connexion.getConnexion().createStatement();
		ResultSet id = statement.executeQuery("SELECT idcompte FROM CRJ3957A." + nomTable + " WHERE " + condition );
		id.next();
		return id.getInt(1);
	}

	public static int newID(String nomTable) throws SQLException { // Donne une ID inexistante a un compte
		Connexion.seConnecter();
		Statement statement = Connexion.getConnexion().createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT max(idcompte) FROM " + nomTable);
		resultSet.next();
		return resultSet.getInt(1) + 1;
	}

	public static int newIDTournoi(String nomTable) throws SQLException { // Donne une ID inexistante a un Tournoi
		Connexion.seConnecter();
		Statement statement = Connexion.getConnexion().createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT max(idTournoi) FROM " + nomTable);
		resultSet.next();
		return resultSet.getInt(1) + 1;
	}

	public static int newIDFinale(String nomTable) throws SQLException { // Donne une ID inexistante a un poule finale
		Connexion.seConnecter();
		Statement statement = Connexion.getConnexion().createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT max(idphasefinale) FROM " + nomTable);
		resultSet.next();
		return resultSet.getInt(1) + 1;
	}

	public static int newIDJoueur() throws SQLException { // Donne une ID inexistante a un joueur
		Connexion.seConnecter();
		Statement statement = Connexion.getConnexion().createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT max(idjoueur) FROM saejoueur");
		resultSet.next();
		return resultSet.getInt(1) + 1;
	}
	
	public static int newIDPoule() throws SQLException { // Donne une ID inexistante a une poule
		Connexion.seConnecter();
		Statement statement = Connexion.getConnexion().createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT max(idpoule) FROM saepoule");
		resultSet.next();
		return resultSet.getInt(1) + 1;
	}
	
	public static int newIDPartiePoule() throws SQLException { // Donne une ID inexistante aux matchs d'une poule
		Connexion.seConnecter();
		Statement statement = Connexion.getConnexion().createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT max(id_PartiePoule) FROM SAEPartiePoule");
		resultSet.next();
		return resultSet.getInt(1) + 1;
	}
	
	public static int newIDPartiePhaseFinale() throws SQLException { // Donne une ID inexistante aux matchs d'une poule
		Connexion.seConnecter();
		Statement statement = Connexion.getConnexion().createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT max(id_PartiePhaseFinale) FROM SAEPartiePhaseFinale");
		resultSet.next();
		return resultSet.getInt(1) + 1;
	}
}
