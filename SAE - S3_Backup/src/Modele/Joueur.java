package Modele;

import java.sql.SQLException;
import java.time.LocalDate;

import org.jdatepicker.impl.SqlDateModel;

import Modele.BDD.NomTablesBDD;

public class Joueur {

	private int idJoueur;
	private String nom;
	private String pseudo;
	private SqlDateModel dateNaissance;
	private Equipe equipeAssocie;
	
	public Joueur(String nom, String pseudo, SqlDateModel dateNaissance, Equipe equipe) throws SQLException {
		this.idJoueur = FonctionsSQL.newID(NomTablesBDD.SAEJOUEUR);
		this.nom = nom;
		this.pseudo = pseudo;
		this.dateNaissance = dateNaissance;
		this.equipeAssocie = equipe;
	}
	
	public int calculAge() {
        LocalDate dateActuelle = LocalDate.now();
        int age = dateActuelle.getYear() - this.dateNaissance.getYear();
        if((dateActuelle.getMonthValue() < this.dateNaissance.getMonth() + 1)||(dateActuelle.getMonthValue() == this.dateNaissance.getMonth() + 1 && dateActuelle.getDayOfMonth() < this.dateNaissance.getDay())) {
            return age - 1;
        }
        return age;
    }
	
	public int getId() {
		return this.idJoueur;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public String getPseudo() {
		return this.pseudo;
	}
	
	public Equipe getEquipeAssocie() {
		return this.equipeAssocie;
	}
	
	public void setId(int id) {
		this.idJoueur = id;
	}
	
	public void ajouterJoueur() throws SQLException {
		String[]aInserer = {"" + this.idJoueur, "'" + this.nom + "'", "'" + this.pseudo + "'", "TO_DATE('" + this.dateNaissance.getValue() + "', 'YYYY-MM-DD')", "'" + this.equipeAssocie.getNomEquipe() + "'"};
		FonctionsSQL.insert(NomTablesBDD.SAEJOUEUR, aInserer);
	}
	
	public void supprimerJoueur() throws SQLException {
		FonctionsSQL.delete(NomTablesBDD.SAEJOUEUR, "idjoueur = " + this.idJoueur);
	}
}
