import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;

public class Bmenu extends Bman{
  private Font titleFont = new Font("Serif", Font.BOLD, 50);

  public void render(){
    System.out.println("adsf");
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    panel.setBounds(100, 100, 300, 200);
    panel.setBackground(Color.blue);

    JLabel title = new JLabel("BomberMan!", SwingConstants.CENTER);
    title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
    title.setFont(titleFont);
    panel.add(title);

    JButton startButton = new JButton("start");
    startButton.setFocusable(false);
    startButton.setBackground(Color.green);
    startButton.setForeground(Color.yellow);
    startButton.setAlignmentX(JButton.CENTER_ALIGNMENT);

    panel.add(startButton);

    JButton helpButton = new JButton("help");
    helpButton.setFocusable(false);
    helpButton.setBackground(Color.green);
    helpButton.setForeground(Color.yellow);
    helpButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    panel.add(helpButton);

    JButton quitButton = new JButton("quit");
    quitButton.setFocusable(false);
    quitButton.setBackground(Color.green);
    quitButton.setForeground(Color.yellow);
    quitButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    panel.add(quitButton);

    con.add(panel);
    // frame.add(startButton, BorderLayout.NORTH);

		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
        state ="GAME";
        panel.setVisible(false);
        con.remove(panel);
        frame.remove(panel);
        frame.add(game);
        BmanPlayers.setPos(playerOne, units - 2, units - 2);
        BmanPlayers.setPos(playerTwo, 1, 1);
        game.repaint();
        game.init();
        game.actions();
      }
	  });
  }
}
