package Modele;

import java.sql.ResultSet;
import java.sql.SQLException;

import Modele.BDD.NomTablesBDD;
import Vue.ApplicationEsporter;

public class Equipe {

	private String nomEcurie;
	private String nomEquipe;
	private String nomJeu;
	private Jeu jeu;
	private String pathLogo;
	
	// Constructeur de la classe " Equipe "
	public Equipe(String nomEcurie, String nomEquipe, String nomJeu, String pathLogo) throws SQLException {
		this.nomEcurie = nomEcurie;
		this.nomEquipe = nomEquipe;
		this.nomJeu = nomJeu;
		ResultSet rs = FonctionsSQL.select(NomTablesBDD.SAEJEU, "*", "nom = '" + this.nomJeu + "'");
		rs.next();
		this.jeu = new Jeu(rs.getString(1), rs.getString(2));
		this.pathLogo = pathLogo;
	}
	
	public String getNomEquipe() { // Récupère le nom de l'équipe
		return this.nomEquipe;
	}
	
	public String getNomJeu() { // Récupère le nom du jeu
		return this.nomJeu;
	}
	
	public Jeu getJeu() { // Récupère le Jeu en tant que classe
		return this.jeu;
	}
	
	public String getPathLogo() { // Récupère le chemin du logo
		return this.pathLogo;
	}
	
	public void ajouterEquipe() throws SQLException { // Ajoute une équipe dans la base de données
		String[]aInserer = {"'" + this.nomEquipe + "'", "0" , "'" + this.pathLogo + "'" , "'" + this.nomJeu + "'" , "'" + this.nomEcurie + "'"};
		FonctionsSQL.insert(NomTablesBDD.SAEEQUIPE, aInserer);
	}
	
	public void modifierEquipe() throws SQLException { // Modifie une équipe dans la base de données
		try {
			try {
				this.ajouterEquipe();
			}catch(Exception e) {
			}
			if(this.nomEquipe.equals(ApplicationEsporter.equipe)) { // Modifie l'équipe dans la base de données si le nom de l'équipe ne change pas
				FonctionsSQL.update(NomTablesBDD.SAEEQUIPE, "logo", "'"+this.pathLogo+"'", "nom ='"+ApplicationEsporter.equipe+"'");
				FonctionsSQL.update(NomTablesBDD.SAEEQUIPE, "nom_1", "'"+this.nomJeu+"'", "nom ='"+ApplicationEsporter.equipe+"'");
			}else { // Modifie l'équipe et ceux qui utilise le nom de celui-ci quand le nom de l'équipe change
				System.out.println(this.nomEquipe+" "+ApplicationEsporter.equipe);
				this.ajouterEquipe();
				FonctionsSQL.update(NomTablesBDD.SAEJOUEUR, "nom_equipe", this.nomEquipe, "nom_equipe ='"+ApplicationEsporter.equipe+"'");
				FonctionsSQL.update(NomTablesBDD.SAEPARTICIPER, "nom_equipe", this.nomEquipe, "nom_equipe ='"+ApplicationEsporter.equipe+"'");
				FonctionsSQL.update(NomTablesBDD.SAECONCOURIR, "nom", this.nomEquipe, "nom ='"+ApplicationEsporter.equipe+"'");
				FonctionsSQL.update(NomTablesBDD.SAECOMPETITER, "nom", this.nomEquipe, "nom ='"+ApplicationEsporter.equipe+"'");
				FonctionsSQL.update(NomTablesBDD.SAECOMPETITERPHASEFINALE, "nom", this.nomEquipe, "nom ='"+ApplicationEsporter.equipe+"'");
				ResultSet rs = FonctionsSQL.select(NomTablesBDD.SAEEQUIPE, "nbpoints", "nom ='"+ApplicationEsporter.equipe+"'");
				rs.next();
				FonctionsSQL.update(NomTablesBDD.SAEEQUIPE, "nbpoints", ""+rs.getInt(1), "nom ='"+this.nomEquipe+"'");
				FonctionsSQL.delete(NomTablesBDD.SAEEQUIPE, "nom ='"+ApplicationEsporter.equipe+"'");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void supprimerEquipe() throws SQLException { // Supprime une équipe dans la base de données
		FonctionsSQL.delete(NomTablesBDD.SAEEQUIPE, "nom = '" + this.nomEquipe + "'");
	}
}