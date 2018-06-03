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
import javax.swing.JComboBox;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class BmanCharacter extends Bman{
  public void characterMenu(){
    Font titleFont = new Font("Serif", Font.BOLD, 30);
    Font myFont = new Font("Times New Roman", Font.PLAIN, 15);
    JPanel characterPanel = new JPanel();

    String[] chars1 = new String[] {"Tyler", "Kumar", "Obama", "Trump"};
    String[] chars2 = new String[] {"Kumar", "Tyler", "Obama", "Trump"};
    JComboBox<String> combo1 = new JComboBox<>(chars1);
    JComboBox<String> combo2 = new JComboBox<>(chars2);
    // combo1.addItem("Tyler");
    // combo1.addItem("Kumar");
    // combo1.addItem("Obama");
    // combo1.addItem("Trump");
    characterPanel.setLayout(new BoxLayout(characterPanel, BoxLayout.PAGE_AXIS));

    JLabel title = new JLabel("Choose Your Character", SwingConstants.CENTER);
    characterPanel.add(Box.createVerticalStrut(70));
    title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
    title.setFont(titleFont);
    characterPanel.add(title);

    JLabel p1label = new JLabel("Player 1 (Arrow Keys)", SwingConstants.CENTER);
    characterPanel.add(Box.createVerticalStrut(100));
    p1label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
    p1label.setFont(myFont);
    characterPanel.add(p1label);

    combo1.setAlignmentX(JButton.CENTER_ALIGNMENT);
    combo1.setFocusable(false);
    characterPanel.add(Box.createVerticalStrut(30));
    characterPanel.add(combo1);

    JLabel p2label = new JLabel("Player 2 (WASD)", SwingConstants.CENTER);
    characterPanel.add(Box.createVerticalStrut(100));
    p2label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
    p2label.setFont(myFont);
    characterPanel.add(p2label);

    combo2.setAlignmentX(JButton.CENTER_ALIGNMENT);
    combo2.setFocusable(false);
    characterPanel.add(Box.createVerticalStrut(30));
    characterPanel.add(combo2);

    JButton backButton = new JButton("back");
    characterPanel.add(Box.createVerticalStrut(100));
    backButton.setFont(myFont);
    backButton.setFocusable(false);

    characterPanel.add(backButton);
    con.add(characterPanel);
    characterPanel.setVisible(true);

    backButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        character1 = combo1.getItemAt(combo1.getSelectedIndex());
        character2 = combo2.getItemAt(combo2.getSelectedIndex());
        try{
          if(character1.equals("Tyler")){
            pyr1 = ImageIO.read(new File("tyler.png"));
          }
          else if(character1.equals("Kumar")){
            pyr1 = ImageIO.read(new File("kumz2.png"));
          }
          else if(character1.equals("Trump")){
            pyr1 = ImageIO.read(new File("trump.png"));
          }
          else if(character1.equals("Obama")){
            pyr1 = ImageIO.read(new File("obama.png"));
          }
          if(character2.equals("Tyler")){
            pyr2 = ImageIO.read(new File("tyler.png"));
          }
          else if(character2.equals("Kumar")){
            pyr2 = ImageIO.read(new File("kumz2.png"));
          }
          else if(character2.equals("Obama")){
            pyr2 = ImageIO.read(new File("obama.png"));
          }
          else if(character2.equals("Trump")){
            pyr2 = ImageIO.read(new File("trump.png"));
          }
        }catch(IOException e) {
          e.printStackTrace();
        }
        characterPanel.setVisible(false);
        menu.render();
      }
    });
  }
}
