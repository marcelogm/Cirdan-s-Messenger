/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import javafx.application.Platform;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author 00271922
 */
public class USound {
    // Referencia prórpia
    private static USound usound;
    private final String alert = "sounds\\alert.wav";
    private final String buzz = "sounds\\buzz.wav";
    
    private USound() {
    }
    
    public static USound getInstance(){
        if(usound == null) {
            usound = new USound();
        }
        return usound;
    }
    
    public void alert(){
        this.play(this.alert);
    }
    
    public void buzz(){
        this.play(this.buzz);
    }
    
    public void play(String filename){
        Platform.runLater(() -> {
            try
            {
                Clip clip = AudioSystem.getClip();
                File file = new File(filename);
                if(file.exists()){
                    clip.open(AudioSystem.getAudioInputStream(file));
                }
                clip.start();
            }
            catch (Exception exc)
            {
                exc.printStackTrace(System.out);
            }
        });
    }
}
