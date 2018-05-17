import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Bman extends JPanel{
  protected static int[][] well;//a reference of type for each unit: 0 for set obstacle (gray),
  //1 for pathway for movement (black), and 2 for breakble obstacles (brown)
  protected static int units = 13;//each square is a units
  protected static int unitSize = 50;//size of each square
  protected static int[][] boxes; //breakable boxes
  public static BmanPlayers playerOne = new BmanPlayers();
  public static BmanPlayers playerTwo = new BmanPlayers();

  public Bman(){

  }
  public void drawAll(Graphics g){
    // player one (pink)
    g.setColor(Color.pink);
    g.fillOval(unitSize*BmanPlayers.getxPos(playerOne) + unitSize/4, unitSize*BmanPlayers.getyPos(playerOne) + unitSize/4, 25, 25);
    // player two (blue)
    g.setColor(Color.blue);
    g.fillOval(unitSize*BmanPlayers.getxPos(playerTwo) + unitSize/4, unitSize*BmanPlayers.getyPos(playerTwo) + unitSize/4, 25, 25);
  }
  public static void main(String[] args){
    JFrame test = new JFrame("Hi");//title in window bar
    test.setSize(units*unitSize + 10, units*unitSize + 10);//size of whole frame, which is # of squares times its size
    test.setVisible(true);
    Bman game = new Bman();
    test.add(game);
    BmanPlayers.setPos(playerOne, 1, 1);
    BmanPlayers.setPos(playerTwo, units - 2, units - 2);
    game.init();

    // BmanPlayers playerTwo = new BmanPlayers;
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
          game.repaint();
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN && well[p1x][p1y+1] == 1){
          BmanPlayers.moveY(playerOne, -1);
          game.repaint();
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT && well[p1x-1][p1y] == 1){
          BmanPlayers.moveX(playerOne, -1);
          game.repaint();
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT && well[p1x+1][p1y] == 1){
          BmanPlayers.moveX(playerOne, 1);
          game.repaint();
        }
        else if(e.getKeyCode() == KeyEvent.VK_SPACE && BmanPlayers.getBombs(playerOne) > 0){
          bomb(playerOne, p1x, p1y);
          game.repaint();
        }

          // player two (WASD)
        if(e.getKeyCode() == KeyEvent.VK_W && well[p2x][p2y-1] == 1){
          BmanPlayers.moveY(playerTwo, 1);
          game.repaint();
        }
        else if(e.getKeyCode() == KeyEvent.VK_S && well[p2x][p2y+1] == 1){
          BmanPlayers.moveY(playerTwo, -1);
          game.repaint();
        }
        else if(e.getKeyCode() == KeyEvent.VK_A && well[p2x-1][p2y] == 1){
          BmanPlayers.moveX(playerTwo, -1);
          game.repaint();
        }
        else if(e.getKeyCode() == KeyEvent.VK_D && well[p2x+1][p2y] == 1){
          BmanPlayers.moveX(playerTwo, 1);
          game.repaint();
        }
        else if(e.getKeyCode() == KeyEvent.VK_T && BmanPlayers.getBombs(playerTwo) > 0){
          bomb(playerTwo, p2x, p2y);
          game.repaint();
        }
		  }
			public void keyReleased(KeyEvent e) {
			}
		});
  }
  public void init(){//initialize game,
    well = new int[units][units];
    //fills well with black, with gray on border and with the pattern, brown for breakable boxes
    for(int i = 0; i < units; i ++){
      for(int j = 0; j < units; j ++){
        if(i == 0 || i == units-1 || j == 0 || j == units-1 || (i % 2 == 0 && j % 2 == 0)){
          well[i][j] = 0;
        }
        else{
          well[i][j] = 1;
        }
      }
    }
    repaint();
  }
  public static void bomb(BmanPlayers player, int xpos, int ypos){
    new Thread() {
			@Override public void run() {
        BmanPlayers.changeBombs(player, -1);
				try {
					Thread.sleep(3000);
					System.out.println("bomb");
				} catch ( InterruptedException e ) {}
        BmanPlayers.changeBombs(player, +1);
			}
		}.start();
  }
  public void paintComponent(Graphics g){
    int color;
    for (int i = 0; i < units; i++) {
      for (int j = 0; j < units; j++) {
        color = well[i][j];
        if(color == 0){
          g.setColor(Color.gray);
        }
        else if(color == 1){
          g.setColor(Color.black);
        }
        else if(color == 3){
          g.setColor(new Color(102, 51, 0));
        }
        g.fillRect(unitSize*i, unitSize*j, unitSize-1, unitSize-1);
      }
    }
    drawAll(g);
  }
}
