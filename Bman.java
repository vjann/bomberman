import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.AlphaComposite;

public class Bman extends JPanel{
  protected static int[][] well;//a reference of type for each unit: 0 for breakable boxes, 1 for pathway for movement (black),
  // 2 for set obstacle (gray), 3 for bomb P1, 4 for bomb P2, 12 p1 explosion horiz, 13 p1 explosion vert, 14 is p2 explosion horiz, 15 is p2 explosion vert
  protected static int units = 13;//each square is a units
  protected static int unitSize = 50;//size of each square
  public static BmanPlayers playerOne = new BmanPlayers();
  public static BmanPlayers playerTwo = new BmanPlayers();
  public static double boxprob = 0.45;    //CHANGED
  public static Bmenu menu;
  protected static JFrame frame;//title in window bar
  protected static Container con;
  protected static JPanel panel;
  public static Bman game = new Bman();
  protected static String character1 = "Tyler";
  protected static String character2 = "Kumar";
  private static boolean restart = false;

  protected static BufferedImage pyr1 = null;
  protected static BufferedImage pyr2 = null;
  private static BufferedImage pLives = null;
  private static BufferedImage redb = null;
  private static BufferedImage blueb = null;
  private static BufferedImage unbreak = null;
  private static BufferedImage breakable = null;
  private static BufferedImage bombup = null;
  private static BufferedImage sizeUp = null;

  private static Color transPink = new Color(255, 192, 203, 160);
  private static Color transBlue = new Color(0, 0, 255, 160);
  private static float alpha = (float) 0.3; //draw half transparent
  private static AlphaComposite trans = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);

  public static void main(String[] args){
    frame = new JFrame("BomberMan!");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize((units+2)*unitSize, units*unitSize);//size of whole frame, which is # of squares times its size

    con = frame.getContentPane();
    menu = new Bmenu();

    try {
      pyr1 = ImageIO.read(new File("tyler.png"));
      pyr2 = ImageIO.read(new File("kumz2.png"));
      redb = ImageIO.read(new File("redbomb.png"));
      blueb = ImageIO.read(new File("bluebomb.png"));
      pLives = ImageIO.read(new File("playerlives.jpg"));
      unbreak = ImageIO.read(new File("unbreakable.png"));
      breakable = ImageIO.read(new File("breakable.jpg"));
      bombup = ImageIO.read(new File("bombup.png"));
      sizeUp = ImageIO.read(new File("sizeUp.png"));

    } catch (IOException e) {
      e.printStackTrace();
    }
    backgroundMusic();
    panel = new JPanel();
    menu.render();
    frame.setVisible(true);
  }
  public void init(){//initialize game,       //CHANGED
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
            if (Math.random() <= boxprob){
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
  public static void actions(){
    //KEYLISTENER
    frame.addKeyListener(new KeyListener() {
      public void keyTyped(KeyEvent e) {
      }
      public void keyPressed(KeyEvent e) {
        int p1x = BmanPlayers.getxPos(playerOne);
        int p1y = BmanPlayers.getyPos(playerOne);
        int p2x = BmanPlayers.getxPos(playerTwo);
        int p2y = BmanPlayers.getyPos(playerTwo);
        //when direction is pressed, check if prospective new position is either pathway(1), or rays of bomb explosion (12-15)
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
                  // drop bomb, timer for 2.5 sec
                  BmanPlayers.changeBombs(playerOne, -1);
                  well[p1x][p1y] = 3;
                  game.repaint();
                  Thread.sleep(2500);
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
        else if(e.getKeyCode() == KeyEvent.VK_BACK_SLASH && BmanPlayers.getCanDrop(playerOne)){
          well[p1x][p1y] = 0;
          new Thread() {
            @Override public void run() {
              try {
                BmanPlayers.setCanDrop(playerOne, false);
                Thread.sleep(3000);
                BmanPlayers.setCanDrop(playerOne, true);
              }catch(InterruptedException e){
                e.printStackTrace();
              }
            }
          }.start();
        }
        game.repaint();
        // player two (WASD)
        if(e.getKeyCode() == KeyEvent.VK_W && (well[p2x][p2y-1] == 1 || well[p2x][p2y-1] >=5)){
          nextStep(p2x, p2y-1, playerTwo);
        }
        else if(e.getKeyCode() == KeyEvent.VK_S && (well[p2x][p2y+1] == 1 || well[p2x][p2y+1] >=5)){
          nextStep(p2x, p2y+1, playerTwo);
        }
        else if(e.getKeyCode() == KeyEvent.VK_A && (well[p2x-1][p2y] == 1 || well[p2x-1][p2y] >=5)){
          nextStep(p2x-1, p2y, playerTwo);
        }
        else if(e.getKeyCode() == KeyEvent.VK_D && (well[p2x+1][p2y] == 1 || well[p2x+1][p2y] >=5)){
          nextStep(p2x+1, p2y, playerTwo);
        }
        else if(e.getKeyCode() == KeyEvent.VK_T && (BmanPlayers.getBombs(playerTwo) > 0)){
          new Thread() {
            @Override public void run() {
              try {
                if(well[p2x][p2y] == 1 && BmanPlayers.getBombs(playerTwo) > 0){
                  // drop bomb, timer for 2.5 sec
                  BmanPlayers.changeBombs(playerTwo, -1);
                  well[p2x][p2y] = 4;
                  game.repaint();
                  Thread.sleep(2500);
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
        else if(e.getKeyCode() == KeyEvent.VK_Y && BmanPlayers.getCanDrop(playerTwo)){
          well[p2x][p2y] = 0;
          new Thread() {
            @Override public void run() {
              try {
                BmanPlayers.setCanDrop(playerTwo, false);
                Thread.sleep(3000);
                BmanPlayers.setCanDrop(playerTwo, true);
              }catch(InterruptedException e){
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
  public static void nextStep(int xPos, int yPos, BmanPlayers player){
    BmanPlayers.setPos(player, xPos, yPos);
    if(!BmanPlayers.getInvincibility(player) && (well[xPos][yPos] == 5 || well[xPos][yPos] == 6)){
      BmanPlayers.loseLife(player);
    }
    else if(well[xPos][yPos] == 7){
      if(BmanPlayers.getMaxBombs(player) <=6){
        BmanPlayers.addMaxBombs(player);
        BmanPlayers.addBombs(player);
      }
      well[xPos][yPos] = 1;
    }
    else if(well[xPos][yPos] == 8){
      if(BmanPlayers.getexplodeSize(player) <=6){
        BmanPlayers.addExplodeSize(player, 1);

      }
      well[xPos][yPos] = 1;
    }
    else if(well[xPos][yPos] == 9){
      if(BmanPlayers.getLives(player) <= 4){
        BmanPlayers.addLives(player);
      }
      well[xPos][yPos] = 1;
    }
  }
  public void explode(BmanPlayers player, int x, int y, int e){
    sound();
    int p1x = BmanPlayers.getxPos(playerOne);
    int p1y = BmanPlayers.getyPos(playerOne);
    int p2x = BmanPlayers.getxPos(playerTwo);
    int p2y = BmanPlayers.getyPos(playerTwo);
    int bombRay = -1;
    //check units in all four directions up to radius e, sets well to explosions unless hits wall
    //BUG: bomb ray does not include bombs origin
    for(int i = 1 ; i < e; i++){
      //if well is wall (2), explosion stops
      //if well is destroyable obstacle (0), explosion destroys it and obstacle stops explosion
      if(well[x][y+i] == 0 || well[x][y+i] == 2 || well[x][y+i] == 3 || well[x][y+i] == 4){
        if(well[x][y+i] == 3 || well[x][y+i] == 4){
          continue;
        }
        if(well[x][y+i] == 0){
          if(player == playerOne){
            bombRay = 13;
          }
          else if (player == playerTwo){
            bombRay = 15;
          }
          well[x][y+i] = RNGESUS(player, bombRay);
        }
        break;
      }
      //if bomb ray hits players, they lose one life
      if(x == p1x && y + i == p1y && !BmanPlayers.getInvincibility(playerOne)){
        BmanPlayers.loseLife(playerOne);
        new Thread() {
          @Override public void run() {
            try {
              BmanPlayers.setInvinciblility(playerOne, true);
              Thread.sleep(1000);
              BmanPlayers.setInvinciblility(playerOne, false);
            }catch(InterruptedException e){
              e.printStackTrace();
            }
          }
        }.start();
      }
      if(x == p2x && y + i == p2y && !BmanPlayers.getInvincibility(playerTwo)){
        BmanPlayers.loseLife(playerTwo);
        new Thread() {
          @Override public void run() {
            try {
              BmanPlayers.setInvinciblility(playerTwo, true);
              Thread.sleep(1000);
              BmanPlayers.setInvinciblility(playerTwo, false);
            }catch(InterruptedException e){
              e.printStackTrace();
            }
          }
        }.start();
      }
      if(player == playerOne){
        well[x][y + i] = 13;
      }
      else if(player == playerTwo){
        well[x][y + i] = 15;
      }
    }
    //next three for loops are the same but in dif. direction. Check comments for the first for loop for clarifications
    for(int j = 1; j < e; j++){
      if(well[x][y-j] == 0 || well[x][y-j] == 2 || well[x][y-j] == 3 || well[x][y-j] == 4){
        if(well[x][y-j] == 3 || well[x][y-j] == 4){
          continue;
        }
        if(well[x][y-j] == 0){
          if(player == playerOne){
            bombRay = 13;
          }
          else if(player == playerTwo){
            bombRay = 15;
          }
          well[x][y-j] = RNGESUS(player, bombRay);
        }
        break;
      }
      if(x == p1x && y - j == p1y && !BmanPlayers.getInvincibility(playerOne)){
        BmanPlayers.loseLife(playerOne);
        new Thread() {
          @Override public void run() {
            try {
              BmanPlayers.setInvinciblility(playerOne, true);
              Thread.sleep(1000);
              BmanPlayers.setInvinciblility(playerOne, false);
            }catch(InterruptedException e){
              e.printStackTrace();
            }
          }
        }.start();
      }
      if(x == p2x && y - j == p2y && !BmanPlayers.getInvincibility(playerTwo)){
        BmanPlayers.loseLife(playerTwo);
        new Thread() {
          @Override public void run() {
            try {
              BmanPlayers.setInvinciblility(playerTwo, true);
              Thread.sleep(1000);
              BmanPlayers.setInvinciblility(playerTwo, false);
            }catch(InterruptedException e){
              e.printStackTrace();
            }
          }
        }.start();
      }
      if(player == playerOne){
        well[x][y - j] = 13;
      }
      else if(player == playerTwo){
        well[x][y - j] = 15;
      }
    }
    for(int k = 1; k < e; k++){
      if(well[x+k][y] == 0 || well[x+k][y] == 2 || well[x+k][y] == 3 || well[x+k][y] == 4){
        if(well[x+k][y] == 3 || well[x+k][y] == 4){
          continue;
        }
        if(well[x+k][y] == 0){
          if(player == playerOne){
            bombRay = 12;
          }
          else if(player == playerTwo){
            bombRay = 14;
          }
          well[x+k][y] = RNGESUS(player, bombRay);
        }
        break;
      }
      if(x + k == p1x && y == p1y && !BmanPlayers.getInvincibility(playerOne)){
        BmanPlayers.loseLife(playerOne);
        new Thread() {
          @Override public void run() {
            try {
              BmanPlayers.setInvinciblility(playerOne, true);
              Thread.sleep(1000);
              BmanPlayers.setInvinciblility(playerOne, false);
            }catch(InterruptedException e){
              e.printStackTrace();
            }
          }
        }.start();
      }
      if(x + k== p2x && y == p2y && !BmanPlayers.getInvincibility(playerTwo)){
        BmanPlayers.loseLife(playerTwo);
        new Thread() {
          @Override public void run() {
            try {
              BmanPlayers.setInvinciblility(playerTwo, true);
              Thread.sleep(1000);
              BmanPlayers.setInvinciblility(playerTwo, false);
            }catch(InterruptedException e){
              e.printStackTrace();
            }
          }
        }.start();
      }
      if(player == playerOne){
        well[x + k][y] = 12;
      }
      else if(player == playerTwo){
        well[x + k][y] = 14;
      }
    }
    for(int l = 1; l < e; l++){
      if(well[x-l][y] == 0 || well[x-l][y] == 2 || well[x-l][y] == 3 || well[x-l][y] == 4){
        if(well[x-l][y] == 3 || well[x-l][y] == 4){
          continue;
        }
        if(well[x-l][y] == 0){
          if(player == playerOne){
            bombRay = 12;
          }
          else if(player == playerTwo){
            bombRay = 14;
          }
          well[x-l][y] = RNGESUS(player, bombRay);
        }
        break;
      }
      if(x - l == p1x && y== p1y && !BmanPlayers.getInvincibility(playerOne)){
        BmanPlayers.loseLife(playerOne);
        new Thread() {
          @Override public void run() {
            try {
              BmanPlayers.setInvinciblility(playerOne, true);
              Thread.sleep(1000);
              BmanPlayers.setInvinciblility(playerOne, false);
            }catch(InterruptedException e){
              e.printStackTrace();
            }
          }
        }.start();
      }
      if(x - l == p2x && y == p2y && !BmanPlayers.getInvincibility(playerTwo)){
        BmanPlayers.loseLife(playerTwo);
        new Thread() {
          @Override public void run() {
            try {
              BmanPlayers.setInvinciblility(playerTwo, true);
              Thread.sleep(1000);
              BmanPlayers.setInvinciblility(playerTwo, false);
            }catch(InterruptedException e){
              e.printStackTrace();
            }
          }
        }.start();
      }
      if(player == playerOne){
        well[x - l][y] = 12;
      }
      else if(player == playerTwo){
        well[x - l][y] = 14;
      }
    }
    //paint the changes that were made above
    repaint();
  }
  public void paintComponent(Graphics g){
    Graphics2D g2 = (Graphics2D)g;
    //sets up icons and images of players, bombs, maybe bombrays
    g.setColor(Color.white);
    g.fillRect(0, 0, (units+2)*unitSize, units*unitSize);
    int color;
    for (int i = 0; i < units; i++) {
      for (int j = 0; j < units; j++) {
        color = well[i][j];
        //destroyable obstacle
        if(color == 0){      //CHANGED
          g.drawImage(breakable, i*unitSize+50, j*unitSize, unitSize-1, unitSize-1, null);
        }
        //pathway
        else if(color == 1){
          g.setColor(Color.black);
          g.fillRect(unitSize*i+50, unitSize*j, unitSize-1, unitSize-1);
        }
        //walls
        else if(color == 2){
          g.drawImage(unbreak, i*unitSize+50, j*unitSize, unitSize, unitSize, null);
        }
        //powerup addbomb
        else if(color == 7){
          g.setColor(Color.black);
          g.fillRect(unitSize*i+50, unitSize*j, unitSize-1, unitSize-1);
          g.drawImage(bombup, unitSize*i+50, unitSize*j, unitSize-1, unitSize-1, null);
        }
        //powerup addbombradius
        else if(color == 8){
          g.setColor(Color.black);
          g.fillRect(unitSize*i+50, unitSize*j, unitSize-1, unitSize-1);
          g.drawImage(sizeUp, unitSize*i+50, unitSize*j, unitSize-1, unitSize-1, null);
        }
        else if(color == 9){
          g.drawImage(pLives, unitSize*i+50, unitSize*j, unitSize-1, unitSize-1, null);
        }
        // player one (tyler)
        g.drawImage(pyr1, unitSize*BmanPlayers.getxPos(playerOne) +50, unitSize*BmanPlayers.getyPos(playerOne), 50, 50, null);
        // player two (kumz2)
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
        else if(color == 12){
          g.setColor(Color.black);
          g.fillRect(unitSize*i+50, unitSize*j, unitSize-1, unitSize-1);
          g.setColor(transPink);
          g.fillRect(unitSize*i+50, unitSize*j + unitSize/4, unitSize-1, unitSize/2);
        }
        else if(color == 13){
          g.setColor(Color.black);
          g.fillRect(unitSize*i+50, unitSize*j, unitSize-1, unitSize-1);
          g.setColor(transPink);
          g.fillRect(unitSize*i+50 + unitSize/4, unitSize*j, unitSize/2, unitSize-1);
        }
        // player two bomb ray
        else if(color == 14){
          g.setColor(Color.black);
          g.fillRect(unitSize*i+50, unitSize*j, unitSize-1, unitSize-1);
          g.setColor(transBlue);
          g.fillRect(unitSize*i+50, unitSize*j + unitSize/4, unitSize-1, unitSize/2);
        }
        else if(color == 15){
          g.setColor(Color.black);
          g.fillRect(unitSize*i+50, unitSize*j, unitSize-1, unitSize-1);
          g.setColor(transBlue);
          g.fillRect(unitSize*i+50 + unitSize/4, unitSize*j, unitSize/2, unitSize-1);
        }
      g.setColor(Color.black);
      }
    }
    g.fillRect(0, 0, 50, units*unitSize);
    g.fillRect(units*unitSize+50, 0, 50, units*unitSize);
    for(int i = 0; i < BmanPlayers.getLives(playerTwo); i ++){
      g.drawImage(pLives, 0, unitSize*i, unitSize, unitSize, null);
    }
    for(int i = 0; i < BmanPlayers.getLives(playerOne); i ++){
      g.drawImage(pLives, units*unitSize+50, unitSize*i, unitSize, unitSize, null);
    }
    for(int i = 0; i < BmanPlayers.getBombs(playerTwo); i ++){
      g.drawImage(blueb, 0, unitSize*(i+5), unitSize, unitSize, null);
    }
    for(int i = 0; i < BmanPlayers.getBombs(playerOne); i ++){
      g.drawImage(redb, units*unitSize+50, unitSize*(i+5), unitSize, unitSize, null);
    }
    alpha = (float) 0.3; //draw half transparent
    trans = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
    g2.setComposite(trans);
    for(int i = 0; i < BmanPlayers.getMaxBombs(playerTwo); i ++){
      g.drawImage(blueb, 0, unitSize*(i+5), unitSize, unitSize, null);
    }
    for(int i = 0; i < BmanPlayers.getMaxBombs(playerOne); i ++){
      g.drawImage(redb, units*unitSize+50, unitSize*(i+5), unitSize, unitSize, null);
    }
    if(BmanPlayers.getLives(playerOne) <= 0){
      endGame(g, playerOne);
      return;
    }
    else if(BmanPlayers.getLives(playerTwo) <= 0){
      endGame(g, playerTwo);
      return;
    }
  }
  //erases the bomb rays
  public static void bombReset(){
    //goes through entire well, turns bomb rays (12-15) to background (1)
    /* BUG: if two bombs placed in rapid succession, erasing first bomb ray by the timer
    will erase all other current bomb rays before their times is up
    */
    for(int i = 1; i < units-1; i++){
      for(int j = 1; j < units-1; j++){
        if(well[i][j] == 12 || well [i][j] == 13 || well [i][j] == 14 || well [i][j] == 15){
          well[i][j] = 1;
        }
      }
    }
  }
  public void endGame(Graphics g, BmanPlayers player){
    Graphics2D g2 = (Graphics2D)g;
    alpha = (float) 1; //draw half transparent
    trans = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
    g2.setComposite(trans);

    Font myFont = new Font("Serif", Font.BOLD, 50);
    g.setColor(Color.RED);
    g.setFont(myFont);
    g.drawString("Game Over",(int) (units*unitSize*0.15), (int) (units*unitSize*0.25));
    String winner = "";
    if(player == playerOne){
      winner += character2 + " Wins";
    }
    else if(player == playerTwo){
      winner += character1 + " Wins";
    }
    g.drawString(winner,(int) (units*unitSize*0.15), (int) (units*unitSize*0.75));
    // menu.render();
  }
  public static int RNGESUS(BmanPlayers player, int bombRay){
    int roll = (int)(100*Math.random());
    if(roll < 10){
      return 7; //add bomb
    }
    else if(roll < 20){
      return 8; //add bomb size
    }
    else if(roll< 25){
      return 9; //add lives
    }
    else{
      return bombRay;
    }
  }
  public void sound(){
    File yourFile = new File("pta.wav");
    AudioInputStream stream;
    AudioFormat format;
    DataLine.Info info;
    Clip clip;
    try{
      stream = AudioSystem.getAudioInputStream(yourFile);
      format = stream.getFormat();
      info = new DataLine.Info(Clip.class, format);
      clip = (Clip) AudioSystem.getLine(info);
      clip.open(stream);
      clip.start();
    }catch(Exception e) {
      e.printStackTrace();
    }
  }
  public static void backgroundMusic(){
    File yourFile = new File("smash.wav");
    AudioInputStream stream;
    AudioFormat format;
    DataLine.Info info;
    Clip clip;
    try{
      stream = AudioSystem.getAudioInputStream(yourFile);
      format = stream.getFormat();
      info = new DataLine.Info(Clip.class, format);
      clip = (Clip) AudioSystem.getLine(info);
      clip.open(stream);
      clip.start();
    }catch(Exception e) {
      e.printStackTrace();
    }
  }
}
