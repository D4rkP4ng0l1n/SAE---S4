package Vue;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Modele.playMusic;


public class ApplicationEsporter {

	public static JFrame f;
	public static String nomEcurie;
	public static int idTypeCompte;
	public static int idCompte;
	public static String idTournoi;
	public static String equipe;
	public static String logo_Path;

	public static final int NB_MAX_EQUIPE_PAR_TOURNOI = 16;

	public static void main(String[] args) {

		// playMusic musique = new playMusic("src/musiques/Bury-The-Light-_Final-Boss-Ver._-Dante-Battle-Theme-_HQ-CLEAN-Rip_-Devil-May-Cry-5-Special-Edition.wav");

		f = new JFrame();
		f.setLayout(new GridLayout(1, 1));
		f.add(new PageAccueil());
		f.setVisible(true);
		f.pack();
		f.setTitle("E-sporter");
		f.setSize(800, 500);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//permet de changer de page
	public static void changerDePage(JPanel fenetre) {
		ApplicationEsporter.f.setContentPane(fenetre);
		ApplicationEsporter.f.validate();
	}
}