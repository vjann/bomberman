import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class Bmenu{
  public void render(Graphics g){
    Font myFont = new Font("Serif", Font.BOLD, 50);
    g.setFont(myFont);
    g.setColor(Color.blue);
    g.drawString("BomberMan", Bman.units*Bman.unitSize/2, 100);
  }
}
