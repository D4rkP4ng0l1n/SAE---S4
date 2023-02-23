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
	
	public String getNomEquipe() { // R�cup�re le nom de l'�quipe
		return this.nomEquipe;
	}
	
	public String getNomJeu() { // R�cup�re le nom du jeu
		return this.nomJeu;
	}
	
	public Jeu getJeu() { // R�cup�re le Jeu en tant que classe
		return this.jeu;
	}
	
	public String getPathLogo() { // R�cup�re le chemin du logo
		return this.pathLogo;
	}
	
	public void ajouterEquipe() throws SQLException { // Ajoute une �quipe dans la base de donn�es
		String[]aInserer = {"'" + this.nomEquipe + "'", "0" , "'" + this.pathLogo + "'" , "'" + this.nomJeu + "'" , "'" + this.nomEcurie + "'"};
		FonctionsSQL.insert(NomTablesBDD.SAEEQUIPE, aInserer);
	}
	
	public void modifierEquipe() throws SQLException { // Modifie une �quipe dans la base de donn�es
		try {
			try {
				this.ajouterEquipe();
			}catch(Exception e) {
			}
			if(this.nomEquipe.equals(ApplicationEsporter.equipe)) { // Modifie l'�quipe dans la base de donn�es si le nom de l'�quipe ne change pas
				FonctionsSQL.update(NomTablesBDD.SAEEQUIPE, "logo", "'"+this.pathLogo+"'", "nom ='"+ApplicationEsporter.equipe+"'");
				FonctionsSQL.update(NomTablesBDD.SAEEQUIPE, "nom_1", "'"+this.nomJeu+"'", "nom ='"+ApplicationEsporter.equipe+"'");
			}else { // Modifie l'�quipe et ceux qui utilise le nom de celui-ci quand le nom de l'�quipe change
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
	
	public void supprimerEquipe() throws SQLException { // Supprime une �quipe dans la base de donn�es
		FonctionsSQL.delete(NomTablesBDD.SAEEQUIPE, "nom = '" + this.nomEquipe + "'");
	}
}