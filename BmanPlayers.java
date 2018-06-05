// Victor Jann, Shivam Misra, Sarvesh Mayilvahanan
public class BmanPlayers{ // class for players
  protected int xPos;
  protected int yPos;
  protected int lives;
  protected int bombs;
  protected int explodeSize;
  protected int maxBombs;
  protected boolean invincible;
  protected boolean canDrop;
  protected String character;
  public BmanPlayers(){ // default no argument constructor
    xPos = 0;
    yPos = 0;
    lives = 3;
    bombs = 3;
    explodeSize = 3;
    maxBombs = 3;
    invincible = false;
    canDrop = true;
    character = "";
  }
  // sets the character of the player
  public static void setChar(BmanPlayers player, String name){
    player.character = name;
  }
  // returns the character of the player
  public static String getChar(BmanPlayers player){
    return player.character;
  }
  // sets the position of the player
  public static void setPos(BmanPlayers player, int x, int y){
    player.xPos = x;
    player.yPos = y;
  }
  // sets the lives of the player
  public static void setLives(BmanPlayers player, int l){
    player.lives = l;
  }
  // increments the lives of player by 1
  public static void addLives(BmanPlayers player){
    player.lives++;
  }
  // increments the bombs of the player by 1
  public static void addBombs(BmanPlayers player){
    player.bombs++;
  }
  // increases the explosion size of the player's bombs
  public static void addExplodeSize(BmanPlayers player, int x){
    player.explodeSize += x;
  }
  // returns the x position of the player
  public static int getxPos(BmanPlayers player){
    return player.xPos;
  }
  // returns the y position of the player
  public static int getyPos(BmanPlayers player){
    return player.yPos;
  }
  // returns the lives of the player
  public static int getLives(BmanPlayers player){
    return player.lives;
  }
  // returns the bombs of the player
  public static int getBombs(BmanPlayers player){
    return player.bombs;
  }
  // returns the explosion size of the player's bombs
  public static int getexplodeSize(BmanPlayers player){
    return player.explodeSize;
  }
  // moves the player in the x direction
  public static void moveX(BmanPlayers player, int x){
    player.xPos += x;
  }
  // moves the player in the y direction
  public static void moveY(BmanPlayers player, int y){
    player.yPos -= y;
  }
  // player loses one life
  public static void loseLife(BmanPlayers player){
    player.lives -=1;
  }
  // increases the player's bombs
  public static void changeBombs(BmanPlayers player, int a){
    player.bombs += a;
  }
  // returns the player's max number of bombs
  public static int getMaxBombs(BmanPlayers player){
    return player.maxBombs;
  }
  // increases the player's max number of bombs
  public static void addMaxBombs(BmanPlayers player){
    player.maxBombs ++;
  }
  // returns the player's invincibility status
  public static boolean getInvincibility(BmanPlayers player){
    return player.invincible;
  }
  // sets the player's invincibility status
  public static void setInvinciblility(BmanPlayers player, boolean isInvincible){
    player.invincible = isInvincible;
  }
  // returns the player's drop obstacle (breakable brown box) status
  public static boolean getCanDrop(BmanPlayers player){
    return player.canDrop;
  }
  // sets the player's drop obstacle (breakable brown box) status
  public static void setCanDrop(BmanPlayers player, boolean bool){
    player.canDrop = bool;
  }
}
