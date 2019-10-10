# BomberMan
**Synopsis**
Based on the classic NES game BomberMan, there are 2 players in a grid playing field filled with breakable and unbreakable obstacles. The players can drop bombs that on explosion, will break boxes and kill players. The players start off with 3 bombs and 3 lives each, and the last player standing wins. The breakable boxes also have the potential to contain power ups that give the player unique abilities such as a larger explosion range for their bombs. The field of breakable boxes and positions of power ups within the field will be randomly generated so that no two games are similar.

**Bombs**
Bombs are placed by players (who move with WASD and the arrow keys around the map) by pressing their respective bomb placement keys (“Space” and “T” atm). A maximum of 3 bombs can be placed by a player at a time, unless a powerup to increase the max to 5 is acquired Blocks like bombs interact with the “well” 2-D array, which determines what object is placed in the grid map at a certain time, with 0 representing a walkable pathway, 1 representing a breakable block, etc. Bombs can only be placed on a pathway block. Bombs have a certain range depending on if the player has acquired a powerup or not. Initially, all bombs have a range of 3 blocks, and can increase up to 5 with a powerup. 

**Explosions**
Explosions only travel horizontally and vertically. This allows for the player to hide behind cover to prevent them from being in range of enemy or friendly fire, as you can lose lives if under the range of your own bomb. 

**Unbreakable Blocks**
These blocks are placed on set positions on the intersections of every odd numbered column and row. They cannot be destroyed, so they provide basic cover from explosions. 

**Breakable Blocks**
These blocks are randomly placed around the map, except for on top of the non-breakable blocks. There is also a gap at the spawn point of both the players to allow for the players to safely get out of their initial position. This also uses the “well” 2D array to work, and breaks when in range of an explosion from a bomb. As soon as the explosion reaches a breakable block, the explosion does not continue to its max range (if not on max range at impact with the breakable block) , but breaks the breakable block it hits.

**Powerups**
Powerups include: gain an extra life, increase number of bombs held, increase bomb's explosion size. The extra life powerup has a 5% probability of occurring. The add bomb powerup has a 10% probability of occurring. The increase bomb size has a 10% probability of occurring.

**Screen Grabs**
![Home Screen](/readme_pics/menu.png)
![Game Play](/readme_pics/gameplay0.png)
![Game Play](/readme_pics/gameplay1.png)
![Help](/readme_pics/help.png)
![Character Select](/readme_pics/character.png)
