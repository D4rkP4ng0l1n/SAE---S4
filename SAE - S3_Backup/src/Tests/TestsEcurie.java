package Tests;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Modele.Compte;
import Modele.FonctionsSQL;

public class TestsEcurie {
	
	@Before
	public void setUp() throws NoSuchAlgorithmException, SQLException, Exception {
		Compte.creerCompte("CompteTestEcurie", "0000", Compte.Type.ECURIE);
		FonctionsSQL.update("saecompte", "idcompte", "99999", "utilisateur = 'CompteTestEcurie'");
	}
	
	@After
	public void tearDown() throws SQLException {
		FonctionsSQL.delete("saeecurie", "idcompte = 99999");
		FonctionsSQL.delete("saecompte", "idcompte = 99999");
	}
	
	@Test
	public void testInsertEcurie() throws NoSuchAlgorithmException, Exception {			
		ResultSet rsBefore = FonctionsSQL.select("saeecurie", "count(*)", "");
		rsBefore.next();
		String[]aInserer = {"'G3'","'Evil John LeSihiho'", "'LogoG3.png'", "99999"};
		FonctionsSQL.insert("saeecurie", aInserer);
		ResultSet rsAfter = FonctionsSQL.select("saeecurie", "count(*)", "");
		rsAfter.next();
		assertTrue(rsBefore.getInt(1) < rsAfter.getInt(1));
	}
	
	@Test
	public void testDeleteEcurie() throws NoSuchAlgorithmException, SQLException, Exception {
		String[]aInserer = {"'G3'","'Evil John LeSihiho'", "'LogoG3.png'", "99999"};
		FonctionsSQL.insert("saeecurie", aInserer);
		ResultSet rsBefore = FonctionsSQL.select("saeecurie", "count(*)", "");
		rsBefore.next();
		FonctionsSQL.delete("saeecurie", "idcompte = 99999");
		ResultSet rsAfter = FonctionsSQL.select("saeecurie", "count(*)", "");
		rsAfter.next();
		assertTrue(rsBefore.getInt(1) > rsAfter.getInt(1));	
	}
	
	@Test
	public void testModifEcurie() throws Exception {
		String[]aInserer = {"'G3'","'Evil John LeSihiho'", "'LogoG3.png'", "99999"};
		FonctionsSQL.insert("saeecurie", aInserer);
		FonctionsSQL.update("saeecurie", "nom", "'G4'", "idcompte = 99999");
		ResultSet rs = FonctionsSQL.select("saeecurie", "nom", "idcompte = 99999");
		rs.next();
		assertEquals(rs.getString(1), "G4");
	}
}
