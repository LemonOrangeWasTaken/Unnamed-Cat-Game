package com.source.framework.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.annotation.Resources;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
  
public class AudioPlayer 
{
    // to store current position
    Long currentFrame;
    Clip clip;
      
    // current status of clip
    String status;
      
    AudioInputStream audioInputStream;
    String filePath;
    
    boolean loop = false;
  
    // constructor to initialize streams and clip    
    public AudioPlayer(String filePath, boolean loop) throws LineUnavailableException, UnsupportedAudioFileException, IOException
        {
        	// create AudioInputStream object
        	URL fileURL = Resources.class.getResource("/resource/" + filePath);	//get sound
        	audioInputStream = AudioSystem.getAudioInputStream(new File(fileURL.getPath()));
        	clip = AudioSystem.getClip(null);
              
        	this.filePath = filePath;

        	// open audioInputStream to the clip
            clip.open(audioInputStream);
            if(loop) {            	
            	clip.loop(clip.LOOP_CONTINUOUSLY);
            }
            this.loop = loop;
        }
      
    // Method to play the audio
    public void play() 
    {
        //start the clip
        clip.start();
          
        status = "play";
    }
      
    // Method to pause the audio
    public void pause() 
    {
        if (status.equals("paused")) 
        {
            System.out.println("audio is already paused");
            return;
        }
        this.currentFrame = 
        this.clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }
      
    // Method to resume the audio
    public void resumeAudio() throws UnsupportedAudioFileException,
                                IOException, LineUnavailableException 
    {
        if (status.equals("play")) 
        {
            System.out.println("Audio is already "+
            "being played");
            return;
        }
        clip.close();
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        this.play();
    }
      
    // Method to restart the audio
    public void restart() throws IOException, LineUnavailableException,
                                            UnsupportedAudioFileException 
    {
        clip.stop();
        clip.close();
        resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play();
    }
      
    // Method to stop the audio
    public void stop() throws UnsupportedAudioFileException,
    IOException, LineUnavailableException 
    {
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }
      
    // Method to jump over a specific part
    public void jump(long c) throws UnsupportedAudioFileException, IOException,
                                                        LineUnavailableException 
    {
        if (c > 0 && c < clip.getMicrosecondLength()) 
        {
            clip.stop();
            clip.close();
            resetAudioStream();
            currentFrame = c;
            clip.setMicrosecondPosition(c);
            this.play();
        }
    }
      
    // Method to reset audio stream
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException,
                                            LineUnavailableException 
    {
    	URL fileURL = getClass().getResource(filePath);
    	audioInputStream = AudioSystem.getAudioInputStream(new File(fileURL.getPath()));
        clip.open(audioInputStream);
        if(loop) {
        	clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
}