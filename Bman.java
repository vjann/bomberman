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
  protected static int[][] well;//a reference of type for each unit: 0 for breakable boxes, 1 for pathway for movement (black),
  // 2 for set obstacle (gray), 3 for bomb P1, 4 for bomb P2, 5 p1 explosion, 6 is p2 explosion,
  protected static int units = 13;//each square is a units
  protected static int unitSize = 50;//size of each square
  public static BmanPlayers playerOne = new BmanPlayers();
  public static BmanPlayers playerTwo = new BmanPlayers();
  public static double boxprob = 0.4;    //CHANGED
  public double random;     //CHANGED
  public static Bmenu menu;
  public static Bendgame theEnd;

  private enum STATE{
    MENU,
    GAME,
    END
  };
  public STATE state = STATE.MENU;
  public static void main(String[] args){
    JFrame test = new JFrame("BomberMan!");//title in window bar
    test.setSize((units+2)*unitSize, units*unitSize);//size of whole frame, which is # of squares times its size
    test.setVisible(true);
    Bman game = new Bman();
    test.add(game);
    menu = new Bmenu();
    BmanPlayers.setPos(playerOne, units - 2, units - 2);
    BmanPlayers.setPos(playerTwo, 1, 1);
    game.init();
    game.repaint();
    //KEYLISTENER
    test.addKeyListener(new KeyListener() {
      public void keyTyped(KeyEvent e) {
      }
      public void keyPressed(KeyEvent e) {
        int p1x = BmanPlayers.getxPos(playerOne);
        int p1y = BmanPlayers.getyPos(playerOne);
        int p2x = BmanPlayers.getxPos(playerTwo);
        int p2y = BmanPlayers.getyPos(playerTwo);
        //when direction is pressed, check if prospective new position is either pathway(1), or rays of bomb explosion (5 or 6)
        if(e.getKeyCode() == KeyEvent.VK_UP && (well[p1x][p1y-1] == 1 || well[p1x][p1y-1] >=5 )){
          //check if new position is bomb ray, lose life when walked into
          nextStep(p1x, p1y-1, playerOne);
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN && (well[p1x][p1y+1] == 1 || well[p1x][p1y+1] >=5)){
          //check if new position is bomb ray, lose life when walked into
          nextStep(p1x, p1y+1, playerOne);
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT && (well[p1x-1][p1y] == 1 || well[p1x-1][p1y] >=5)){
          //check if new position is bomb ray, lose life when walked into
          nextStep(p1x-1, p1y, playerOne);
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT && (well[p1x+1][p1y] == 1 || well[p1x+1][p1y] >=5)){
          //check if new position is bomb ray, lose life when walked into
          nextStep(p1x+1, p1y, playerOne);
        }
        //drop bomb if space is pressed and still has availabe bombs
        else if(e.getKeyCode() == KeyEvent.VK_ENTER && BmanPlayers.getBombs(playerOne) > 0){
          new Thread() {
            @Override public void run() {
              try {
                if(well[p1x][p1y] == 1 && BmanPlayers.getBombs(playerOne) > 0){
                  // drop bomb, timer for 3 sec
                  BmanPlayers.changeBombs(playerOne, -1);
                  well[p1x][p1y] = 3;
                  game.repaint();
                  Thread.sleep(3000);
                  // bomb explodes
                  game.explode(playerOne, p1x, p1y, BmanPlayers.getexplodeSize(playerOne));
                  //bomb disappears
                  well[p1x][p1y] = 1;
                  BmanPlayers.changeBombs(playerOne, +1);
                  game.repaint();
                  //explosion 'rays' disappear
                  Thread.sleep(1000);
                  bombReset();
                  game.repaint();
                }
              } catch ( InterruptedException e ) {
                e.printStackTrace();
              }
            }
          }.start();
        }
        game.repaint();
        System.out.println("hi");

        // player two (WASD)
        if(e.getKeyCode() == KeyEvent.VK_W && well[p2x][p2y-1] == 1 || well[p2x][p2y-1] >=5){
          nextStep(p2x, p2y-1, playerTwo);
        }
        else if(e.getKeyCode() == KeyEvent.VK_S && well[p2x][p2y+1] == 1 || well[p2x][p2y+1] >=5){
          nextStep(p2x, p2y+1, playerTwo);
        }
        else if(e.getKeyCode() == KeyEvent.VK_A && well[p2x-1][p2y] == 1 || well[p2x-1][p2y] >=5){
          nextStep(p2x-1, p2y, playerTwo);
        }
        else if(e.getKeyCode() == KeyEvent.VK_D && well[p2x+1][p2y] == 1 || well[p2x+1][p2y] >=5){
          nextStep(p2x+1, p2y, playerTwo);
        }
        else if(e.getKeyCode() == KeyEvent.VK_T && BmanPlayers.getBombs(playerTwo) > 0){
          new Thread() {
            @Override public void run() {
              try {
                if(well[p2x][p2y] == 1 && BmanPlayers.getBombs(playerTwo) > 0){
                  // drop bomb, timer for 3 sec
                  BmanPlayers.changeBombs(playerTwo, -1);
                  well[p2x][p2y] = 4;
                  game.repaint();
                  Thread.sleep(3000);
                  //bomb explodes
                  game.explode(playerTwo, p2x, p2y, BmanPlayers.getexplodeSize(playerTwo));
                  //bomb disappears
                  well[p2x][p2y] = 1;
                  BmanPlayers.changeBombs(playerTwo, +1);
                  game.repaint();
                  //explosion 'rays' disappear
                  Thread.sleep(300);
                  bombReset();
                  game.repaint();
                }
              } catch (InterruptedException e) {
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
    if(BmanPlayers.getLives(playerOne) == 0 || BmanPlayers.getLives(playerTwo) == 0){
      return;
    }
  }
  public static int RNGESUS(BmanPlayers player){
    int exp = -1;
    if(player == playerOne){
      exp = 5;
    }
    else if(player == playerTwo){
      exp = 6;
    }
    int roll = (int)(100*Math.random());
    if(roll < 20){
      return 7;
    }
    else if (roll < 40){
      return 8;
    }
    else{
      return exp;
    }
  }
  public void explode(BmanPlayers player, int x, int y, int e){
    int p1x = BmanPlayers.getxPos(playerOne);
    int p1y = BmanPlayers.getyPos(playerOne);
    int p2x = BmanPlayers.getxPos(playerTwo);
    int p2y = BmanPlayers.getyPos(playerTwo);
    //exp stores bomb ray id, and depends on which player
    int exp = -1;
    if(player == playerOne){
      exp = 5;
    }
    else if(player == playerTwo){
      exp = 6;
    }
    //check units in all four directions up to radius e, sets well to explosions unless hits wall
    //BUG: bomb ray does not include bombs origin
    for(int i = 0 ; i < e; i++){
      //if well is wall (2), explosion stops
      //if well is destroyable obstacle (0), explosion destroys it and obstacle stops explosion
      if(well[x][y+i] == 0 || well[x][y+i] == 2){
        if(well[x][y+i] == 0){
          well[x][y+i] = RNGESUS(player);
        }
        break;
      }
      //if bomb ray hits players, they lose one life
      if(x == p1x && y + i == p1y){
        BmanPlayers.loseLife(playerOne);
      }
      if(x == p2x && y + i == p2y){
        BmanPlayers.loseLife(playerTwo);
      }
      well[x][y+i] = exp;
    }
    //next three for loops are the same but in dif. direction. Check comments for the first for loop for clarifications
    for(int j = 1; j < e; j++){
      if(well[x][y-j] == 0 || well[x][y-j] == 2){
        if(well[x][y-j] == 0){
          well[x][y-j] = RNGESUS(player);
        }
        break;
      }
      if(x == p1x && y - j == p1y){
        BmanPlayers.loseLife(playerOne);
      }
      if(x == p2x && y - j == p2y){
        BmanPlayers.loseLife(playerTwo);
      }
      well[x][y-j] = exp;
    }
    for(int k = 1; k < e; k++){
      if(well[x+k][y] == 0 || well[x+k][y] == 2){
        if(well[x+k][y] == 0){
          well[x+k][y] = RNGESUS(player);
        }
        break;
      }
      if(x + k == p1x && y == p1y){
        BmanPlayers.loseLife(playerOne);
      }
      if(x + k== p2x && y == p2y){
        BmanPlayers.loseLife(playerTwo);
      }
      well[x+k][y] = exp;
    }
    for(int l = 1; l < e; l++){
      if(well[x-l][y] == 0 || well[x-l][y] == 2){
        if(well[x-l][y] == 0){
          well[x-l][y] = RNGESUS(player);
        }
        break;
      }
      if(x - l == p1x && y== p1y){
        BmanPlayers.loseLife(playerOne);
      }
      if(x - l == p2x && y== p2y){
        BmanPlayers.loseLife(playerTwo);
      }
      well[x-l][y] = exp;
    }
    //paint the changes that were made above
    repaint();
  }
  public static void nextStep(int xPos, int yPos, BmanPlayers player){
    BmanPlayers.setPos(player, xPos, yPos);
    if(well[xPos][yPos] == 5 || well[xPos][yPos] == 6){
      BmanPlayers.loseLife(player);
    }
    else if(well[xPos][yPos] == 7 && BmanPlayers.getMaxBombs(player) <=7){
      BmanPlayers.addBombs(player);
      well[xPos][yPos] = 1;
    }
    else if(well[xPos][yPos] == 8 && BmanPlayers.getexplodeSize(player) <=7){
      BmanPlayers.addExplodeSize(player, 1);
      well[xPos][yPos] = 1;
    }
  }

  public void init(){//initialize game,       //CHANGED
      System.out.println("init");
      well = new int[units][units];
      //fills well with black, with gray on border and with the pattern, brown for breakable boxes
      for(int i = 0; i < units; i ++){
        for(int j = 0; j < units; j ++){
          if(i == 0 || i == units-1 || j == 0 || j == units-1 || (i % 2 == 0 && j % 2 == 0)){
            well[i][j] = 2;
          }
          else if ((i == 1 && (j == 1 || j == 2)) || (i == 2 && j == 1) || (i == 11 && (j == 11 || j == 10)) || (i == 10 && j == 11)){
            well[i][j] = 1;
          }
          else{
            random = Math.random();
            if (random <= boxprob){
              well[i][j]=0;
            }
            else{
              well[i][j]=1;
            }
           // well[i][j] = 1;
          }
        }
      }
      repaint();
    }
  public void paintComponent(Graphics g){
    if(state == STATE.GAME){
      //sets up icons and images of players, bombs, maybe bombrays
      BufferedImage pyr1 = null;
      BufferedImage pyr2 = null;
      BufferedImage p1Lives = null;
      BufferedImage p2Lives = null;
      BufferedImage redb = null;
      BufferedImage blueb = null;
      BufferedImage unbreak = null;

      Color transPink = new Color(255, 192, 203, 160);
      Color transBlue = new Color(0, 0, 255, 160);
      try {
        pyr1 = ImageIO.read(new File("tyler.png"));
        pyr2 = ImageIO.read(new File("kumz2.png"));
        redb = ImageIO.read(new File("redbomb.png"));
        blueb = ImageIO.read(new File("bluebomb.png"));
        p1Lives = ImageIO.read(new File("playerlives.jpg"));
        p2Lives = ImageIO.read(new File("playerlives.jpg"));
        unbreak = ImageIO.read(new File("unbreakable.png"));

      } catch (IOException e) {
        e.printStackTrace();
      }
      int color;
      for (int i = 0; i < units; i++) {
        for (int j = 0; j < units; j++) {
          color = well[i][j];
          //destroyable obstacle
          if(color == 0){      //CHANGED
            g.setColor(new Color(139,69,19));
            g.fillRect(unitSize*i+50, unitSize*j, unitSize-1, unitSize-1);
          }
          //pathway
          else if(color == 1){
            g.setColor(Color.black);
            g.fillRect(unitSize*i+50, unitSize*j, unitSize-1, unitSize-1);
          }
          //walls
          else if(color == 2){
            g.setColor(Color.darkGray);
            g.fillRect(unitSize*i+50, unitSize*j, unitSize-1, unitSize-1);
            // g.drawImage(unbreak, i*unitSize+50, j*unitSize, unitSize, unitSize, null);
          }
          //powerup addbomb
          else if(color == 7){
            g.setColor(Color.green);
            g.fillRect(unitSize*i+50, unitSize*j, unitSize-1, unitSize-1);
          }
          //powerup addbombradius
          else if(color == 8){
            g.setColor(Color.red);
            g.fillRect(unitSize*i+50, unitSize*j, unitSize-1, unitSize-1);
          }
          // player one (tyler)
          g.setColor(Color.pink);
          g.drawImage(pyr1, unitSize*BmanPlayers.getxPos(playerOne) +50, unitSize*BmanPlayers.getyPos(playerOne), 50, 50, null);
          // player two (kumz2)
          g.setColor(Color.blue);
          g.drawImage(pyr2, unitSize*BmanPlayers.getxPos(playerTwo) +50, unitSize*BmanPlayers.getyPos(playerTwo) , 50, 50, null);
          //redirects to paint non-background
          if(color == 3){
            g.setColor(Color.black);
            g.fillRect(unitSize*i+50, unitSize*j, unitSize-1, unitSize-1);
            g.drawImage(redb,unitSize*i + 55, unitSize*j + 5, unitSize-9, unitSize-9, null);
          }
          //player two bomb
          else if(color == 4){
            g.setColor(Color.black);
            g.fillRect(unitSize*i+50, unitSize*j, unitSize-1, unitSize-1);
            g.drawImage(blueb,unitSize*i + 55, unitSize*j + 5, unitSize-9, unitSize-9, null);
          }
          // player one bomb ray
          else if(color == 5){
            g.setColor(transPink);
            g.fillRect(unitSize*i+50, unitSize*j, unitSize-1, unitSize-1);
          }
          // player two bomb ray
          else if(color == 6){
            g.setColor(transBlue);
            g.fillRect(unitSize*i+50, unitSize*j, unitSize-1, unitSize-1);
          }
          g.setColor(Color.black);
        }
      }
      g.fillRect(0, 0, 50, units*unitSize);
      g.fillRect(units*unitSize+50, 0, 50, units*unitSize);
      for(int i = 0; i < BmanPlayers.getLives(playerTwo); i ++){
        g.drawImage(p1Lives, 0, unitSize*i, unitSize, unitSize, null);
      }
      for(int i = 0; i < BmanPlayers.getLives(playerOne); i ++){
        g.drawImage(p1Lives, units*unitSize+50, unitSize*i, unitSize, unitSize, null);
      }
      for(int i = 0; i < BmanPlayers.getBombs(playerTwo); i ++){
        g.drawImage(blueb, 0, unitSize*(i+5), unitSize, unitSize, null);
      }
      for(int i = 0; i < BmanPlayers.getBombs(playerOne); i ++){
        g.drawImage(redb, units*unitSize+50, unitSize*(i+5), unitSize, unitSize, null);
      }

      if(BmanPlayers.getLives(playerOne) <= 0){
        state = STATE.END;
        endGame(g, playerOne);
        return;
      }
      else if(BmanPlayers.getLives(playerTwo) <= 0){
        state = STATE.END;
        endGame(g, playerTwo);
        return;
      }
    }
    else if(state == STATE.MENU){
      menu.render(g);
      state = STATE.GAME;
    }

  }
  //erases the bomb rays
  public static void bombReset(){
    //goes through entire well, turns bomb rays (5 and 6) to background (1)
    /* BUG: if two bombs placed in rapid succession, erasing first bomb ray by the timer
    will erase all other current bomb rays before their times is up
    */
    for(int i = 1; i < units-1; i++){
      for(int j = 1; j < units-1; j++){
        if(well[i][j] == 5 || well [i][j] == 6){
          well[i][j] = 1;
        }
      }
    }
  }
  public void endGame(Graphics g, BmanPlayers player){
    Font myFont = new Font("Serif", Font.BOLD, 50);
    g.setColor(Color.RED);
    g.setFont(myFont);
    g.drawString("Game Over",(int) (units*unitSize*0.15), (int) (units*unitSize*0.25));
    String winner = "";
    if(player == playerOne){
      winner += "Kumz Wins";
    }
    else{
      winner += "Tyler Wins";
    }
    g.drawString(winner,(int) (units*unitSize*0.15), (int) (units*unitSize*0.75));
  }
}
