import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

public class Bman extends JPanel{
  protected static int[][] well;//a reference of type for each unit: 1 for pathway for movement (black),
  // 2 for set obstacle (gray), 3 for bomb P1, 4 for bomb P2
  protected static int units = 13;//each square is a units
  protected static int unitSize = 50;//size of each square
  protected static ArrayList<Integer> boxes = new ArrayList<Integer>(); //breakable boxes, each have two indexes (x and y positions)
  public static BmanPlayers playerOne = new BmanPlayers();
  public static BmanPlayers playerTwo = new BmanPlayers();

  public static void main(String[] args){
    JFrame test = new JFrame("BomberMan!");//title in window bar
    test.setSize(units*unitSize + 10, units*unitSize + 10);//size of whole frame, which is # of squares times its size
    test.setVisible(true);
    Bman game = new Bman();
    test.add(game);
    BmanPlayers.setPos(playerOne, units - 2, units - 2);
    BmanPlayers.setPos(playerTwo, 1, 1);
    game.init();

    //KEYLISTENER
    test.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}
			public void keyPressed(KeyEvent e) {
        int p1x = BmanPlayers.getxPos(playerOne);
        int p1y = BmanPlayers.getyPos(playerOne);
        int p2x = BmanPlayers.getxPos(playerTwo);
        int p2y = BmanPlayers.getyPos(playerTwo);;
        if(e.getKeyCode() == KeyEvent.VK_UP && well[p1x][p1y-1] == 1){
          BmanPlayers.moveY(playerOne, 1);
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN && well[p1x][p1y+1] == 1){
          BmanPlayers.moveY(playerOne, -1);
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT && well[p1x-1][p1y] == 1){
          BmanPlayers.moveX(playerOne, -1);
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT && well[p1x+1][p1y] == 1){
          BmanPlayers.moveX(playerOne, 1);
        }
        else if(e.getKeyCode() == KeyEvent.VK_SPACE && BmanPlayers.getBombs(playerOne) > 0){
          new Thread() {
            @Override public void run() {
              try {
                if(well[p1x][p1y] == 1 && BmanPlayers.getBombs(playerOne) > 0){
                  well[p1x][p1y] = 3;
                  BmanPlayers.changeBombs(playerOne, -1);
                  game.repaint();
                  Thread.sleep(3000);
                  well[p1x][p1y] = 1;
                  BmanPlayers.changeBombs(playerOne, +1);
                  game.repaint();
                }
              } catch ( InterruptedException e ) {
                e.printStackTrace();
              }
            }
          }.start();
        }
        game.repaint();

          // player two (WASD)
        if(e.getKeyCode() == KeyEvent.VK_W && well[p2x][p2y-1] == 1){
          BmanPlayers.moveY(playerTwo, 1);
        }
        else if(e.getKeyCode() == KeyEvent.VK_S && well[p2x][p2y+1] == 1){
          BmanPlayers.moveY(playerTwo, -1);
        }
        else if(e.getKeyCode() == KeyEvent.VK_A && well[p2x-1][p2y] == 1){
          BmanPlayers.moveX(playerTwo, -1);
        }
        else if(e.getKeyCode() == KeyEvent.VK_D && well[p2x+1][p2y] == 1){
          BmanPlayers.moveX(playerTwo, 1);
        }
        else if(e.getKeyCode() == KeyEvent.VK_T && BmanPlayers.getBombs(playerTwo) > 0){
          new Thread() {
            @Override public void run() {
              try {
                if(well[p2x][p2y] == 1 && BmanPlayers.getBombs(playerTwo) > 0){
                  well[p2x][p2y] = 4;
                  BmanPlayers.changeBombs(playerTwo, -1);
                  game.repaint();
                  Thread.sleep(3000);
                  well[p2x][p2y] = 1;
                  BmanPlayers.changeBombs(playerTwo, +1);
                  game.repaint();
                }
              } catch ( InterruptedException e ) {
                e.printStackTrace();
              }
            }
          }.start();
        }
        game.repaint();
		  }

			public void keyReleased(KeyEvent e) {
			}
		});
  }
  
  public void init(){//initialize game,
    System.out.println("init");
    well = new int[units][units];
    //fills well with black, with gray on border and with the pattern, brown for breakable boxes
    for(int i = 0; i < units; i ++){
      for(int j = 0; j < units; j ++){
        if(i == 0 || i == units-1 || j == 0 || j == units-1 || (i % 2 == 0 && j % 2 == 0)){
          well[i][j] = 2;
        }
        else{
          well[i][j] = 1;
        }
      }
    }
    repaint();
  }
  public void paintComponent(Graphics g){
    int color;
    for (int i = 0; i < units; i++) {
      for (int j = 0; j < units; j++) {
        color = well[i][j];
        if(color == 1){
          g.setColor(Color.black);
        }
        else if(color == 2){
          g.setColor(Color.darkGray);
        }
        g.fillRect(unitSize*i, unitSize*j, unitSize-1, unitSize-1);
        g.setColor(Color.black);
      }
    }
    drawAll(g);
  }
  public void drawAll(Graphics f){

    // player one (pink)
    f.setColor(Color.pink);
    f.fillOval(unitSize*BmanPlayers.getxPos(playerOne) + unitSize/4, unitSize*BmanPlayers.getyPos(playerOne) + unitSize/4, 25, 25);
    // player two (blue)
    f.setColor(Color.blue);
    f.fillOval(unitSize*BmanPlayers.getxPos(playerTwo) + unitSize/4, unitSize*BmanPlayers.getyPos(playerTwo) + unitSize/4, 25, 25);
    //bombs
    BufferedImage pyr1 = null;
    BufferedImage pyr2 = null;
    BufferedImage redb = null;
    BufferedImage blueb = null;
    try {
      // pyr1 = ImageIO.read(new File("pyr1.png"));
      // pyr2 = ImageIO.read(new File("pyr2.png"));
      redb = ImageIO.read(new File("redbomb.png"));
      blueb = ImageIO.read(new File("bluebomb.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    for(int i = 0; i < units; i ++){
      for(int j = 0; j <units; j ++){
        if(well[i][j] == 3){
          f.setColor(Color.PINK);
          f.drawImage(redb,unitSize*i + 5, unitSize*j + 5, unitSize-9, unitSize-9, null);
        }
        else if(well[i][j] == 4){
          f.setColor(Color.blue);
          f.drawImage(blueb,unitSize*i + 5, unitSize*j + 5, unitSize-9, unitSize-9, null);
        }
      }
    }
  }
}
