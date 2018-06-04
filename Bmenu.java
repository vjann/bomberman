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
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Bmenu extends Bman{
  public void render(){

    Font titleFont = new Font("Serif", Font.BOLD, 50);
    Font buttonFont = new Font("Times New Roman", Font.PLAIN, 20);

    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    panel.setBounds(100, 100, 300, 200);
    panel.setBackground(Color.black);

    Icon icon = new ImageIcon("title.gif");
    JLabel title = new JLabel(icon, SwingConstants.CENTER);
    panel.add(Box.createVerticalStrut(50));
    title.setForeground(Color.green);
    title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
    title.setFont(titleFont);
    panel.add(title);

    JButton startButton = new JButton("start");
    startButton.setFocusable(false);
    panel.add(Box.createVerticalStrut(300));
    startButton.setFont(buttonFont);
    // startButton.setSize(100, 300);
    startButton.setBackground(Color.red);
    startButton.setForeground(Color.black);
    startButton.setAlignmentX(JButton.CENTER_ALIGNMENT);

    panel.add(startButton);

    JButton helpButton = new JButton("help");
    helpButton.setFocusable(false);
    panel.add(Box.createVerticalStrut(5));
    helpButton.setFont(buttonFont);
    helpButton.setBackground(Color.red);
    helpButton.setForeground(Color.black);
    helpButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    panel.add(helpButton);

    JButton quitButton = new JButton("quit");
    quitButton.setFocusable(false);
    panel.add(Box.createVerticalStrut(5));
    quitButton.setFont(buttonFont);
    quitButton.setBackground(Color.red);
    quitButton.setForeground(Color.black);
    quitButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    panel.add(quitButton);

    JButton characterButton = new JButton("character");
    characterButton.setFocusable(false);
    panel.add(Box.createVerticalStrut(5));
    characterButton.setFont(buttonFont);
    characterButton.setBackground(Color.red);
    characterButton.setForeground(Color.black);
    characterButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    panel.add(characterButton);

    panel.setVisible(true);
    con.add(panel);
    // frame.add(startButton, BorderLayout.NORTH);

		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
        panel.setBackground(Color.white);
        panel.setVisible(false);
        frame.add(game);
        BmanPlayers.setPos(playerOne, units - 2, units - 2);
        BmanPlayers.setPos(playerTwo, 1, 1);
        game.repaint();
        game.init();
        game.actions();
      }
	  });
    helpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
        panel.setBackground(Color.white);
        panel.setVisible(false);
        Bhelp bhm = new Bhelp();
        bhm.helpMenu();
      }
    });

    quitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
      }
    });
    characterButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        panel.setBackground(Color.white);
        panel.setVisible(false);
        BmanCharacter bmc = new BmanCharacter();
        bmc.characterMenu();
      }
    });


  }
}
