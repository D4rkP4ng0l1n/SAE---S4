package Tests;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import Modele.Jeu;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestsJeux {

    private Jeu jeu = new Jeu("Test", "1");
    
    @Test
    public void testAjoutJeu() throws SQLException {
        assertTrue(this.jeu.estNouveau());
        this.jeu.ajouterJeu();
        assertFalse(this.jeu.estNouveau());
    }
    
    @Test
    public void testSuppressionJeu() throws SQLException {
        this.jeu.supprimerJeu();
        assertTrue(this.jeu.estNouveau());
    }

}