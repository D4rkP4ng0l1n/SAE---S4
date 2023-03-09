package Modele;

import java.sql.ResultSet;
import java.sql.SQLException;

import Modele.BDD.NomTablesBDD;

public class Jeu {

    private String nom, nbJoueursParEquipe;

    public Jeu(String nom, String nbJoueursParEquipe) {
        this.nom = nom;
        this.nbJoueursParEquipe = nbJoueursParEquipe;
    }

    // Vérifie que le jeu n'existe pas dans la bas de données
    public boolean estNouveau() throws SQLException { 
        ResultSet selectNomJeu = FonctionsSQL.select(NomTablesBDD.SAEJEU, "*", "nom = '" + this.nom + "'");
        return !selectNomJeu.next();
    }

    // Récupère le nom du jeu
    public String getNom() {
        return this.nom;
    }

    // Récupère le nombre de joueur par équipe pour ce jeu
    public String getNbJoueursParEquipe() {
        return this.nbJoueursParEquipe;
    }

    // Récupère tout les jeux
    public static ResultSet getTousLesJeux() throws SQLException {
        return FonctionsSQL.select("saejeu", "*", "");
    }

    // Ajoute le jeu dans la base de données
    public void ajouterJeu() throws SQLException {
        String[] req = new String[1];
        req[0] =  "'" + this.getNom() + "', '" + this.getNbJoueursParEquipe() + "'";
        FonctionsSQL.insert(NomTablesBDD.SAEJEU, req);
    }

    // Supprime le jeu
    public void supprimerJeu() throws SQLException { 
        FonctionsSQL.delete(NomTablesBDD.SAEJEU, "nom = '" + this.getNom() + "'");
    }
}