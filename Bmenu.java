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
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Bmenu extends Bman{
  public void render(){

    Font titleFont = new Font("Serif", Font.BOLD, 50);
    Font buttonFont = new Font("Times New Roman", Font.PLAIN, 20);

    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    panel.setBounds(100, 100, 300, 200);
    panel.setBackground(Color.black);

    // shows gif of title on main menu
    Icon icon = new ImageIcon("title.gif");
    JLabel title = new JLabel(icon, SwingConstants.CENTER);
    panel.add(Box.createVerticalStrut(50));//add spacing
    title.setForeground(Color.green);
    title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
    title.setFont(titleFont);
    panel.add(title);

    // creates start button to start game
    JButton startButton = new JButton("start");
    startButton.setFocusable(false);
    panel.add(Box.createVerticalStrut(300));
    startButton.setFont(buttonFont);
    // startButton.setSize(100, 300);
    startButton.setBackground(Color.red);
    startButton.setForeground(Color.black);
    startButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    panel.add(startButton);

    // creates help button to show controls, powerups
    JButton helpButton = new JButton("help");
    helpButton.setFocusable(false);
    panel.add(Box.createVerticalStrut(5));
    helpButton.setFont(buttonFont);
    helpButton.setBackground(Color.red);
    helpButton.setForeground(Color.black);
    helpButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    panel.add(helpButton);

    // creates quit button that allows players to exit
    JButton quitButton = new JButton("quit");
    quitButton.setFocusable(false);
    panel.add(Box.createVerticalStrut(5));
    quitButton.setFont(buttonFont);
    quitButton.setBackground(Color.red);
    quitButton.setForeground(Color.black);
    quitButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    panel.add(quitButton);

    // creates character button that allows players to pick characters
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

    //start button actions to start game
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
        //delete menu panel(screen)
        panel.setBackground(Color.white);
        panel.setVisible(false);
        //start game, initialize characters
        frame.add(game);
        BmanPlayers.setPos(playerOne, units - 2, units - 2);
        BmanPlayers.setPos(playerTwo, 1, 1);
        game.repaint();
        game.init();
        game.actions();
      }
	  });

    //help button actions for help menu
    helpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
        //delete (titlescreen) menu screen
        panel.setBackground(Color.white);
        panel.setVisible(false);
        Bhelp bhm = new Bhelp();
        //call help menu
        bhm.helpMenu();
      }
    });

    //quit button action to close window, quit game
    quitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
      }
    });

    //character button to choose character
    characterButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        panel.setBackground(Color.white);
        panel.setVisible(false);
        //open character menu
        BmanCharacter bmc = new BmanCharacter();
        bmc.characterMenu();
      }
    });


  }
}
