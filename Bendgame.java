import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class Bendgame{
  public void endGame(Graphics g, BmanPlayers player){
    System.out.println("adsf;lk");
    Font myFont = new Font("Serif", Font.BOLD, 50);
    g.setColor(Color.RED);
    g.setFont(myFont);
    g.drawString("Game Over",(int) (Bman.units*Bman.unitSize*0.15), (int) (Bman.units*Bman.unitSize*0.25));
    String winner = "";
    if(player == Bman.playerOne){
      winner += "Kumz Wins";
    }
    else{
      winner += "Tyler Wins";
    }
    g.drawString(winner,(int) (Bman.units*Bman.unitSize*0.15), (int) (Bman.units*Bman.unitSize*0.75));
  }
}
