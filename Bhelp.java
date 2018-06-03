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

public class Bhelp extends Bman{
  public void helpMenu(){
    Font myFont = new Font("Times New Roman", Font.PLAIN, 15);
    JPanel helpPanel = new JPanel();

    JLabel controls = new JLabel("<html><pre>Tyler: Arrow Keys to move, ENTER to drop bombs\nKumar: WASD to move, T to drop bombs</pre></html>");
    JLabel instructions = new JLabel("<html><pre>OBJECTIVE: reduce opponent's lives to zero using bombs\nPowerups: increase bomb range, increase max bomb capacity</pre></html>");
    controls.setFont(myFont);
    instructions.setFont(myFont);

    JButton backButton = new JButton("back");
    backButton.setFont(myFont);
    backButton.setFocusable(false);

    helpPanel.add(controls);
    helpPanel.add(instructions);
    helpPanel.add(backButton);
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
