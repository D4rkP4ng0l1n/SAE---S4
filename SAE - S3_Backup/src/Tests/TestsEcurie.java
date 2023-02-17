package Tests;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Modele.BDD.NomTablesBDD;
import Modele.Compte;
import Modele.FonctionsSQL;
import Modele.BDD.NomTablesBDD;

public class TestsEcurie {
	
	@Before
	public void setUp() throws NoSuchAlgorithmException, SQLException, Exception {
		Compte.creerCompte("CompteTestEcurie", "0000", Compte.Type.ECURIE);
		FonctionsSQL.update(NomTablesBDD.SAECOMPTE, "idcompte", "99999", "utilisateur = 'CompteTestEcurie'");
	}
	
	@After
	public void tearDown() throws SQLException {
		FonctionsSQL.delete(NomTablesBDD.SAEECURIE, "idcompte = 99999");
		FonctionsSQL.delete(NomTablesBDD.SAECOMPTE, "idcompte = 99999");
	}
	
	@Test
	public void testInsertEcurie() throws NoSuchAlgorithmException, Exception {			
		ResultSet rsBefore = FonctionsSQL.select("saeecurie", "count(*)", "");
		rsBefore.next();
		String[]aInserer = {"'G3'","'Evil John LeSihiho'", "'LogoG3.png'", "99999"};
		FonctionsSQL.insert(NomTablesBDD.SAEECURIE, aInserer);
		ResultSet rsAfter = FonctionsSQL.select("saeecurie", "count(*)", "");
		rsAfter.next();
		assertTrue(rsBefore.getInt(1) < rsAfter.getInt(1));
	}
	
	@Test
	public void testDeleteEcurie() throws NoSuchAlgorithmException, SQLException, Exception {
		String[]aInserer = {"'G3'","'Evil John LeSihiho'", "'LogoG3.png'", "99999"};
		FonctionsSQL.insert(NomTablesBDD.SAEECURIE, aInserer);
		ResultSet rsBefore = FonctionsSQL.select("saeecurie", "count(*)", "");
		rsBefore.next();
		FonctionsSQL.delete(NomTablesBDD.SAEECURIE, "idcompte = 99999");
		ResultSet rsAfter = FonctionsSQL.select("saeecurie", "count(*)", "");
		rsAfter.next();
		assertTrue(rsBefore.getInt(1) > rsAfter.getInt(1));	
	}
	
	@Test
	public void testModifEcurie() throws Exception {
		String[]aInserer = {"'G3'","'Evil John LeSihiho'", "'LogoG3.png'", "99999"};
		FonctionsSQL.insert(NomTablesBDD.SAEECURIE, aInserer);
		FonctionsSQL.update(NomTablesBDD.SAEECURIE, "nom", "'G4'", "idcompte = 99999");
		ResultSet rs = FonctionsSQL.select("saeecurie", "nom", "idcompte = 99999");
		rs.next();
		assertEquals(rs.getString(1), "G4");
	}
}
