package Vue;

import java.awt.GridLayout;
import javax.swing.JFrame;

import Modele.testMusique;


public class ApplicationEsporter {

	public static JFrame f;
	public static String nomEcurie;
	public static int idTypeCompte;
	public static int idCompte;
	public static String idTournoi;
	public static String equipe;
	public static String logo_Path;

	public static final int NB_MAX_EQUIPE_PAR_TOURNOI = 16;

	public static testMusique musique = new testMusique();

	public static void main(String[] args) {

		// musique.playMusic("src/musiques/Lil-Nas-X-STAR-WALKIN-_League-of-Legends-Worlds-Anthem_.wav");
		//musique.playMusic("src/musiques/Bury-The-Light-_Final-Boss-Ver._-Dante-Battle-Theme-_HQ-CLEAN-Rip_-Devil-May-Cry-5-Special-Edition.wav");

		f = new JFrame();
		f.setLayout(new GridLayout(1, 1));
		f.add(new PageAccueil());
		f.setVisible(true);
		f.pack();
		f.setTitle("E-sporter");
		f.setSize(800, 500);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// C'est la fin du code
	}
}