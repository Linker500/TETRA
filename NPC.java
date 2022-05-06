//TODO: this whole thing
/*\
AI Turn

   START
   Move/Search
   Manage Inventory
   Update
   END
   
   ENCOUNTER
   Search player database for opponent
      If entry, use data to influence
   Assuming not dead- Update database
   END


AI Stuff to keep track of and features 

 AI Personality Stats:
   Agressiveness?
   Riskyness
   Favored stat
   Paranoia?
 
 Inventory "tier"
   Have alg. to output expected (either slighlty random or based on stats) global inventory tier, Compare it to own tier.
      If higher, move more
      If lower, search more
 
 Encounter info
   Every Player encountered
      Who won?
      What type of combat was it?
      Estimated threat level of player
\*/


//TODO: this has a LOT of useless variables passed. Just pass game and NOTHING else!!!

import java.util.*;

public class NPC extends Character
{  
   private ArrayList<ArrayList<EncDat>> encDat;
   private boolean[][] searchDat;
   
   private int strat; //General strategy- whether the bot is a "turtle" or a "rabbit"
   private int risk; //How risky the bot is in encounters and whatnot. TODO: perhaps increase and decrease riskiness dynamically from the base stat based on events?
   
   private int actPt;
   private int turnTimer; //AI strategies need counters for turns sometimes. This starts at zero and counts up once per turn.
   
   public NPC(Game game, int maxPlayers, Tile[][] map)
   {
      super(game); //TODO: implement stats when implemented in character
      turnTimer = 0;
      initData(maxPlayers,map);
   }
   
   public NPC(Game game, int newPhy, int newMen, int newSoc, int maxPlayers, Tile[][] map)
   {
      super(game, newPhy, newMen, newSoc); //TODO: implement stats when implemented in character
      turnTimer = 0;
      initData(maxPlayers,map);
   }
   
   private void initData(int maxPlayers, Tile[][] map) //Initialized Data arrays, only run once on construction
   {
      encDat = new ArrayList<ArrayList<EncDat>>();
      for(int i=0; i<maxPlayers; i++)
         encDat.add(new ArrayList<EncDat>());
      
      searchDat = new boolean[map.length][map[0].length];
   }
   
   /* AI Act:
    * Inventory value > move or search
    * ??? > move or fortify
    * Manage inventory
    *
    * AI Encounter:
    * Check database > decide action
    * Manage inventory
    */
   
   public void act(Tile[][] map, int round)
   {
      Util.clear();
      System.out.println(super.getName() + " taking turn.");
      //System.our.println("AI TURN: " +super.getName());
      //System.our.println("x=" + super.getXLoc());
      //System.our.println("y=" + super.getYLoc());
      actPt = 2;
      
      //SPECIAL: First round TODO: Make if first round check less... involved.
      //TODO: Siwtch case would be slightly faster and more appropriate...
      //Perhaps move out of while loop
      if(map[super.getYLoc()][super.getXLoc()].getBiome() == 1 && round==1) //TODO add round check... not very helpful but might not break ai in non standard situations
      {
         int stratScore = takeStrat();
         
         //TODO: maybe merge all of the while loops into one to remove code redundancy.
         if(stratScore >= 2) //Run to center (Major Rabbit)
         {
            while(actPt >1)
               go(map);
            if(takeRisk(3))
               go(map);
            else
               fortify();
         }
         else if(stratScore <= -2) //Stay at spawn and loot (Major Turtle)
         {
            while(actPt >1)
               search(map);
            if(takeRisk(3))
               search(map);
            else
               fortify();
         }
         else if(stratScore >=1) //Minor Rabbit
         {
            while(actPt >1)
               go(map);
            if(takeRisk(3))
               search(map);
            else
               fortify();
         }
         else if(stratScore <=1) //Minor Turtle
         {
            while(actPt >1)
               search(map);
            if(takeRisk(3))
               go(map);
            else
               fortify();
         }
         else //Neutral (random for now...) TODO: perhaps make more interesting? Though I can't imagine what a neutral decision would look like.
         { //TODO: this is hacked and redundant and hurts me inside.
            if((int)(Math.random())*2 == 1) //minor rabbit
            {
               while(actPt >1)
                  go(map);
               if(takeRisk(3))
                  search(map);
               else
                  fortify();
            }
            else //minor turtle
            {
               while(actPt >1)
                  search(map);
               if(takeRisk(3))
                  go(map);
               else
                  fortify();
            }
         }
      }
      
      //Main Decision Loop
      while(actPt > 0)
      {
         //TODO finish AI rewrite. Also redo go(map) and have influence for turtle or rabbit
         go(map);
         search(map);
         actPt-=2;
      }
      
      manInv();
      //System.our.println("TURN END");
      //Util.hold();
   }
   
   //TODO: math.random?
   //TODO: no run option, flesh decision making with encDat
   public int encounter(Character enemy)
   {
      int atkScr = super.getPhy() + super.getAtk();
      int chaScr = super.getSoc() + super.getCrm();
      
      if(atkScr > chaScr)
         return 0;
      else if(chaScr > atkScr)
         return 1;
      else
         return (int)(Math.random()*2);
   }
   
   private void search(Tile[][] map)
   {
      Item found = map[super.getYLoc()][super.getXLoc()].search();
      
      if(found != null)
         super.getInv().add(found);
      searchDat[super.getYLoc()][super.getXLoc()] = true;
      ////System.our.println("SEARCH: " + found);
      actPt--;
   }
   
   //TODO: find alt for math.random()?
   private void go(Tile[][] map)
   {
      //TODO: forloop might be nice but difficult and little payoff
      ArrayList<Integer> dirs = new ArrayList<Integer>();
      
      int x = super.getXLoc();
      int y = super.getYLoc();
      
      //Checks which directions aren't obstructed, and puts allowed directions in an arraylist
      {
         int biome;
         
         //north
         try{biome = map[y-1][x].getBiome();}
         catch(ArrayIndexOutOfBoundsException e) {biome = 0;}
         if(biome > 0)
            dirs.add(0);
         //west
         try{biome = map[y][x-1].getBiome();}
         catch(ArrayIndexOutOfBoundsException e) {biome = 0;}
         if(biome > 0)
            dirs.add(1);
         //south
         try{biome = map[y+1][x].getBiome();}
         catch(ArrayIndexOutOfBoundsException e) {biome = 0;}
         if(biome > 0)
            dirs.add(2);
         //east
         try{biome = map[y][x+1].getBiome();}
         catch(ArrayIndexOutOfBoundsException e) {biome = 0;}
         if(biome > 0)
            dirs.add(3);
      }
      if(dirs.size() > 0)
      {  
         int direction = dirs.get((int)(Math.random()*dirs.size()));
         super.move(direction);
         //System.our.println("MOVE: " + direction);
         
      }
      //else
         ////System.our.println("NO MOVE");
      
      //System.our.println("nX=" + super.getXLoc());
      //System.our.println("nY=" + super.getYLoc());
      actPt--;
   }
   
   private void manInv()
   {
      ArrayList<Item> inv = super.getInv();      
      ArrayList<Integer> matIndex = new ArrayList<Integer>();
      
      for(int i=0; i<inv.size(); i++)
      {
         Item item = inv.get(i);
         int itemType = item.identify();
         
         if(itemType == 1) //Material
            matIndex.add(i);
         else
         {
            if(item.isUsed() == false)
            {
               item.use(this);
               
               //TODO: this remove consumable items to make item searching/charming work. Remove when overhauling system!
               if(itemType > 1)
               {
                  inv.remove(i);
                  i--;
               }
            }
         }
      }
   }
   
   //Attemps to fortify the bot, if successful, returns true, opposite if not.
   //Returned boolean is an option precaution in decision making.
   private boolean fortify()
   {
      if(super.getFortified())
         return false;
      else
      {
         super.setFortified(true);
         actPt--;
         return true;
      }
   }
   
   private void make()
   {
      //TODO: finish!!!
   }
   
   private boolean takeRisk (int riskLvl)  //Given a risk level, this returns whether or not the bot would take the risk based on their riskiness stat
   {
      if(riskLvl >= strat)
         return true;
      else
         return false;
   }
   
   private boolean takeStrat(int stratLvl)
   {
      //TODO add element of randomness!
      //50% chance for + or - 1, 10% chance for + or - 2 or something like that...
      if(stratLvl >= strat)
         return true;
      else
         return false;
   }
   
   //TODO: add randomness as well...?
   private int takeStrat()
   {
      return strat;
   }
   
   public void addDat(int playNumb, boolean isWon, int encType)
   {
      encDat.get(playNumb).add(new EncDat(playNumb, isWon, encType));
   }
   
   
   /* NOTE:
    * All item are assumed to have a tiers of 1-3, except lootcrates.
    * Any items added with differing tiering structure must be hardcoded in, like lootcrates.
    * Sorry not sorry whoever is reading this.
    */
   public int valInv(int round) //Calculates approximate inventory value for game relative to expected average value. 
   {
      int val = 0;
      for(int i=0; i<super.getInv().size(); i++)
      {
         Item item = super.getInv().get(i);
         
         if(item.identify() == 3) //If item is a lootcrate (tiers are outliers in lootcrates)
         {
            if(item.getTier() == 1 || item.getTier() == 2)
               val += 1;
            else if(item.getTier() == 3)
               val += 2;
            else if(item.getTier() == 4 || item.getTier() == 5)
               val += 3;
         }
         else
            val += item.getTier();
      }
      
      //TODO: this formula needs some tweaking... especially after gameplay is mostly finished
      int avgVal = round; //Estimated average inventory value
      
      return val - avgVal;
   }
   
   
}
