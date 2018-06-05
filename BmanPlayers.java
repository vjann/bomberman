public class BmanPlayers{
  protected int xPos;
  protected int yPos;
  protected int lives;
  protected int bombs;
  protected int explodeSize;
  protected int maxBombs;
  protected boolean invincible;
  protected boolean canDrop;
  protected String character;
  public BmanPlayers(){
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
  public static void setChar(BmanPlayers player, String name){
    player.character = name;
  }
  public static String getChar(BmanPlayers player){
    return player.character;
  }
  public static void setPos(BmanPlayers player, int x, int y){
    player.xPos = x;
    player.yPos = y;
  }
  public static void setLives(BmanPlayers player, int l){
    player.lives = l;
  }
  public static void addLives(BmanPlayers player){
    player.lives++;
  }
  public static void addBombs(BmanPlayers player){
    player.bombs++;
  }
  public static void addExplodeSize(BmanPlayers player, int x){
    player.explodeSize += x;
  }
  public static int getxPos(BmanPlayers player){
    return player.xPos;
  }
  public static int getyPos(BmanPlayers player){
    return player.yPos;
  }
  public static int getLives(BmanPlayers player){
    return player.lives;
  }
  public static int getBombs(BmanPlayers player){
    return player.bombs;
  }
  public static int getexplodeSize(BmanPlayers player){
    return player.explodeSize;
  }
  public static void moveX(BmanPlayers player, int x){
    player.xPos += x;
  }
  public static void moveY(BmanPlayers player, int y){
    player.yPos -= y;
  }
  public static void loseLife(BmanPlayers player){
    player.lives -=1;
  }
  public static void changeBombs(BmanPlayers player, int a){
    player.bombs += a;
  }
  public static int getMaxBombs(BmanPlayers player){
    return player.maxBombs;
  }
  public static void addMaxBombs(BmanPlayers player){
    player.maxBombs ++;
  }
  public static boolean getInvincibility(BmanPlayers player){
    return player.invincible;
  }
  public static void setInvinciblility(BmanPlayers player, boolean isInvincible){
    player.invincible = isInvincible;
  }
  public static boolean getCanDrop(BmanPlayers player){
    return player.canDrop;
  }
  public static void setCanDrop(BmanPlayers player, boolean bool){
    player.canDrop = bool;
  }
}
