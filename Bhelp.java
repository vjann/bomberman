// Victor Jann, Shivam Misra, Sarvesh Mayilvahanan
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Bhelp extends Bman{
  public void helpMenu(){
    Font myFont = new Font("Times New Roman", Font.PLAIN, 15);
    JPanel helpPanel = new JPanel();
    JPanel backPanel = new JPanel();
    JLabel background = new JLabel();
    try{
      background = new JLabel(new ImageIcon(ImageIO.read(new File("helpmenu.png"))));
    }catch(IOException e){
      e.printStackTrace();
    }
    // adds instructions and controls for players
    backPanel.add(background);
    con.add(backPanel);
    JLabel controls = new JLabel("<html><pre>CONTROLS\nPlayer 1: Arrow Keys to move, ENTER to drop bombs, \\ to add obstacle\nPlayer 2: WASD to move, T to drop bombs, Y to add obstacle</pre></html>");
    JLabel instructions = new JLabel("<html><pre>OBJECTIVE: reduce opponent's lives to zero using bombs\nPowerups: increase bomb range, increase max bomb capacity</pre></html>");
    controls.setFont(myFont);
    instructions.setFont(myFont);

    // creates button to return to main menu
    JButton backButton = new JButton("back");
    backButton.setFont(myFont);
    backButton.setFocusable(false);

    helpPanel.add(controls);
    helpPanel.add(instructions);
    helpPanel.add(backButton);
    helpPanel.setOpaque(false);
    con.add(helpPanel);
    helpPanel.setVisible(true);

    backButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        helpPanel.setVisible(false);
        menu.render();
      }
    });
  }
}
