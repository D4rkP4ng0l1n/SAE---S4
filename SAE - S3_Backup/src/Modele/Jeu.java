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

    public boolean estNouveau() throws SQLException { // Vérifie que le jeu n'existe pas dans la bas de données
        ResultSet rs = FonctionsSQL.select("saejeu", "*", "nom = '" + this.nom + "'");
        return !rs.next();
    }

    public String getNom() { // Récupère le nom du jeu
        return this.nom;
    }

    public String getNbJoueursParEquipe() { // Récupère le nombre de joueur par équipe pour ce jeu
        return this.nbJoueursParEquipe;
    }

    public static ResultSet getTousLesJeux() throws SQLException { // Récupère tout les jeux
        return FonctionsSQL.select("saejeu", "*", "");
    }

    public void ajouterJeu() throws SQLException { // Ajoute le jeu dans la base de données
        String[] req = new String[1];
        req[0] =  "'" + this.getNom() + "', '" + this.getNbJoueursParEquipe() + "'";
        FonctionsSQL.insert(NomTablesBDD.SAEJEU, req);
    }

    public void supprimerJeu() throws SQLException { // Supprime le jeu
        FonctionsSQL.delete(NomTablesBDD.SAEJEU, "nom = '" + this.getNom() + "'");
    }
}