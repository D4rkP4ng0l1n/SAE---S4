package Modele;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class testMusique {

    public void playMusic(String path) {
        
        try {
            File musicPath = new File(path);
            if (musicPath.exists()) {
                AudioInputStream audio = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.start();
                
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("pas de fic");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}