public class BmanPlayers{
  protected int xPos;
  protected int yPos;
  protected int lives;
  protected int bombs;
  protected int explodeSize;
  protected int maxBombs;
  public BmanPlayers(){
    xPos = 0;
    yPos = 0;
    lives = 3;
    bombs = 3;
    explodeSize = 5;
    maxBombs = 3;
  }
  public static void setPos(BmanPlayers player, int x, int y){
    player.xPos = x;
    player.yPos = y;
  }
  public static void setLives(BmanPlayers player, int l){
    player.lives = l;
  }
  public static void addBombs(BmanPlayers player){
    player.bombs++;
  }
  public static void setexplodeSize(BmanPlayers player, int x){
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
}
