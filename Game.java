import java.util.*;

public class Game
{
   //TODO: initialize all variables in game constructor... eventually
   private Tile[][] map = Level.gen(0);
   private Character[] player;
   
   private boolean running;
   
   private ArrayList<ArrayList<String>> summary = new ArrayList<ArrayList<String>>();
   //TODO: HTF do I make a static array of ArrayLists???? That puts  wrench in my plan for personal summaries
   //private ArrayList<ArrayList<String>>[] personalSummary = new ArrayList<ArrayList<String>>[16];
   
   /* Game Construction
    * game method has 2 constructors:
    *   Default- No paramets, 16 players
    * Specified- A custom number of players.
    *    (Don't set to zero or below, etc, etc. I trust you aren't stupid and will use common sense) 
    */
   
   //Default: 16 players
   public Game()
   {
      running = true;
      player = new Character[16];
      player[0] = new PC(this);
      player[0].setName("Player 1");
      for(int i=1; i<player.length; i++)
      {
         player[i] = new NPC(this, 16, map); //TODO: only pass game... that is a bit of cleanup work though
         player[i].setName("Player " + (i+1));
      }
      drop();
      genLoot();
   }
   
   public Game(int humans)
   {
      running = true;
      player = new Character[16];
      for(int i=0; i<player.length; i++)
      {
         if(humans > 0)
         {
            player[i] = new PC(this);
            player[i].setName("Player " + (i+1));
            humans--;
         }
         else
         {
            player[i] = new NPC(this, 16, map);
            player[i].setName("Player " + (i+1));
         }
      }
      drop();
      genLoot();
   }
   
   //TODO: Remove me in final copy
   public Game(boolean test)
   {
      running = true;
      if(test == false)
      {
         player = new Character[4];
         player[0] = new PC(this);
         player[0].setName("Player 1");
         player[1] = new NPC(this,4,map);
         player[1].setName("Player 2");
         player[2] = new NPC(this,4,map);
         player[2].setName("Player 3");
         player[3] = new NPC(this,4,map);
         player[3].setName("Player 4");
      }
      else
      {
         player = new Character[4];
         player[0] = new PC(this);
         player[0].setName("Player 1");
         player[1] = new PC(this);
         player[1].setName("Player 2");
         player[2] = new PC(this);
         player[2].setName("Player 3");
         player[3] = new PC(this);
         player[3].setName("Player 4");
      }
   }
   
   public void start()
   {
      for(int i=1; running; i++)
         round(i);
   }
   
   public String toString() //Do I really need this??? Maybe useful for spectating once dead?
   {
      String toReturn = "";
      for(int i=0; i<player.length; i++)
         toReturn = toReturn + "\nPlayer " + i + "- " + "\"" + player[i].getName() +"\" X:" + player[i].getXLoc() + " Y:" + player[i].getYLoc();
      return toReturn;
   }
   
   //"Drops" players by distributing[SPELL] players across all spawn tiles
   private void drop()
   {
      //Locates and counts how many spawns there are
      ArrayList<Integer> spawnsX = new ArrayList<Integer>();
      ArrayList<Integer> spawnsY = new ArrayList<Integer>();
      for(int y=0; y<map.length; y++)
      {
         for(int x=0; x<map[y].length; x++)
         {
            if(map[y][x].getBiome() == 1)
            {
               spawnsX.add(x);
               spawnsY.add(y);
            }
         }
      }
      
      for(int i=0; i<player.length; i++) //TODO: randomize more to make it so p1 isn't always at top left, p2 top right... etc. 
      {
         player[i].setLoc(spawnsX.get(i%spawnsX.size()),spawnsY.get(i%spawnsX.size()));
      }
   }
   
   //TODO: make more interesting and correlate loot with tile difficulty
   private void genLoot()
   {
      for(int y=0; y<map.length; y++)
      {
         for(int x=0; x<map[y].length; x++)
         {
            //TODO: better then math.random?
            map[y][x].genLoot((int)(Math.random()*5));
         }
      }
   }
   
   public void round(int round)
   {
      //Actions (PC/NPC choices)
      for(int i=0; i<player.length; i++)
      {
         if(player[i].getLife()) //TODO, move check character classes?
            player[i].act(map,round); //TODO only pass game?
      }
      summary = new ArrayList<ArrayList<String>>();
      //Encounters
      for(int y=0; y<map.length; y++)
      {
         for(int x=0; x<map[y].length; x++)
         {
            boolean[] tileOccupants = getOccupants(y,x);
            ArrayList<Integer> occupants = new ArrayList<Integer>();
            for(int i=0; i<player.length; i++)
            {
               if(tileOccupants[i] == true)
                  occupants.add(i);
            }
            
            if(occupants.size() >1)
            {
               int totalOcc = occupants.size();
               for(int i=0; i<totalOcc/2; i++)
               {
                  int a = (int)(Math.random()*occupants.size());
                  Character A = player[occupants.get(a)];
                  occupants.remove(a);
                  int b = (int)(Math.random()*occupants.size());
                  Character B = player[occupants.get
                  (b)];
                  occupants.remove(b);
                  Encounter.start(A,B,this);
               }
            }            
         }
      }
      shade();
      life();
   }
   
   public void shade()
   {
      ArrayList<String> shadeSummary = new ArrayList<String>();
      ArrayList<Integer> xPos = new ArrayList<Integer>();
      ArrayList<Integer> yPos = new ArrayList<Integer>();
      
      for(int y=0; y<map.length; y++)
      {
         for(int x=0; x<map[y].length; x++)
         {
            if(map[y][x].getBiome() == -1)
            {
               boolean[] toDie = getOccupants(x,y);
               for(int i=0; i<toDie.length; i++)
               {
                  if(toDie[i])
                  {
                     int rand = (int)(Math.random()*100+1);
                     if(rand > 50)
                     {
                        player[i].setLife(false);
                        shadeSummary.add(player[i].getName() + " has died in the shade!");
                     }
                  }
               }
               
               xPos.add(x);
               yPos.add(y);
            }
            else if(map[y][x].getBiome() == 1)
               map[y][x].setBiome(-1);
         }
      }
      if(shadeSummary.size() != 0)
         addSummary(shadeSummary);
      
      for(int i=0; i<xPos.size(); i++)
      {
         ArrayList<Integer> dirs = new ArrayList<Integer>();
         int x = xPos.get(i);
         int y = yPos.get(i);
         
         map[y][x].setBiome(-1);
         
         //TODO: maybe not hardcode this?
         try
         {
            if(map[y-1][x].getBiome() != 0 && map[y-1][x].getBiome() != 8)
               dirs.add(0);
         }
         catch(ArrayIndexOutOfBoundsException e) {}
         try
         {
            if(map[y][x-1].getBiome() != 0 && map[y][x-1].getBiome() != 8)
               dirs.add(1);
         }
         catch(ArrayIndexOutOfBoundsException e) {}
         try
         {
            if(map[y+1][x].getBiome() != 0 && map[y+1][x].getBiome() != 8)
               dirs.add(2);
         }
         catch(ArrayIndexOutOfBoundsException e) {}
         try
         {
            if(map[y][x+1].getBiome() != 0 && map[y][x+1].getBiome() != 8)
               dirs.add(3);
         }
         catch(ArrayIndexOutOfBoundsException e) {}
         
         //TODO I really don't like these if statements below
         int direction = (int)(Math.random()*dirs.size());
         direction = dirs.get(direction);
         
         if(direction == 0)
            map[y-1][x].setBiome(-1);
         else if(direction == 1)
            map[y][x-1].setBiome(-1);
         else if(direction == 2)
            map[y+1][x].setBiome(-1);
         else if(direction == 3)
            map[y][x+1].setBiome(-1);
      }
   }
   
   public void life()
   {      
      int alive = 0;
      for(int i=0; i<player.length; i++)
      {
         if(player[i].getLife())
            alive++;
      }
      if(alive == 1)
      {
         int winner = -1;
         for(int i=0; i<player.length; i++)
         {
            if(player[i].getLife())
            {
               winner = i;
               break;
            }
         }
         
         System.out.println(player[winner].getName() + " is the last player alive!");
         Util.stop(1000);
         System.out.println("They live!");
         Util.stop(1000);
         System.out.println("For now...");
         Util.stop(1000);
         System.out.println("Thank you for your participation in the test... we hope to see you again.");
         Util.stop(1000);
         running = false;
      }
      else if(alive == 0)
      {
         System.out.println("Everyone is dead!");
         Util.stop(1000);
         System.out.println("There is no winner.");
         Util.stop(1000);
         System.out.println("Oh well...");
         Util.stop(1000);
         System.out.println("Thank you for your participation in the test... we hope to see you again.");
         Util.stop(1000);
         running = false;
      }
   }
   
   //Returns a boolean array the length of the number of players, one index corresponding with a player.
   //For a given tile, will return true or false per boolean for whether the corresponding player is in said tile.
   public boolean[] getOccupants(int x, int y)
   {
      boolean[] occupantList = new boolean[player.length];
      int       occupantCount = 0;
      
      for(int i=0; i<player.length; i++)
      {
         if(player[i].getLife() == true && player[i].getXLoc() == x && player[i].getYLoc() == y)
         {
            occupantList[i] = true;
            occupantCount++;
         }
      }
      return occupantList;
   }
   
   public int isOccupied(int x, int y) //TODO: is this really used anywhere? Can I remove it like the infamous boolean[][][] occupantData?
   {
      int occupantCount = 0;
      
      for(int i=0; i<player.length; i++)
      {
         if(player[i].getLife() == true && player[i].getXLoc() == x && player[i].getYLoc() == y)
            occupantCount++;
      }
      return occupantCount;
   }
   
   public void summarize()
   {
      if(summary.size() != 0)
      {
         for(int i=0; i<summary.size(); i++)
         {
            for(int j=0; j<summary.get(i).size(); j++)
            {
               System.out.println(summary.get(i).get(j));
            }
            Util.stop(1500);
            System.out.println();
         }
         Util.hold();
      }
   }
   
   public void printLife()
   {
      for(int i=0; i<player.length; i++)
      {
         String text = (player[i].getName() + ":");
         if(player[i].getLife())
            text+= " Alive";
         else
            text+= " Dead!";
         System.out.println(text);
      }
   }
   
   public void addSummary(ArrayList<String> sumList)
   {
      summary.add(sumList);
   }
}