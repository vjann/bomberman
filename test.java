import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Clip;
import javax.sound.sampled.*;
import java.io.File;

public class test{
  public static void main(String[] args){
    sound();
  }
  public static void sound(){
    File yourFile = new File("youtube.wav");
    AudioInputStream stream;
    AudioFormat format;
    DataLine.Info info;
    Clip clip;
    try{
      stream = AudioSystem.getAudioInputStream(yourFile);
      format = stream.getFormat();
      info = new DataLine.Info(Clip.class, format);
      clip = (Clip) AudioSystem.getLine(info);
      clip.open(stream);
      clip.start();
    }catch(Exception e) {
      System.out.println("Error with playing sound.");
      e.printStackTrace();
    }
  }
}
