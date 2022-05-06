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
      Estimated threat level of
\*/

import java.util.*;

public class NPC extends Character
{  
   private int agro; //agression scale: -5,5 players with a high value are more likely to seek to attack players
   private int risk; //risk scale: -5,5 players with a high value are more likely to take risky chances during encounters
   private ArrayList<ArrayList<EncDat>> encDat;
   private boolean[][] searchDat;
   
   //TODO: math.random is crap?
   public NPC(Game game, int maxPlayers, Tile[][] map)
   {
      super(game); //TODO: implement stats when implemented in character
      agro = (int)(Math.random()*12-6);
      risk = (int)(Math.random()*12-6);
      initData(maxPlayers,map);
   }
   
   public NPC(Game game, int newPhy, int newMen, int newSoc, int maxPlayers, Tile[][] map)
   {
      super(game, newPhy, newMen, newSoc); //TODO: implement stats when implemented in character
      agro = (int)(Math.random()*12-6);
      risk = (int)(Math.random()*12-6);
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
      //System.out.println("AI TURN: " +super.getName());
      //System.out.println("x=" + super.getXLoc());
      //System.out.println("y=" + super.getYLoc());
      
      {//TODO this logic could probably be cleaner with a loop... oh well
         int invVal = valInv(round);
         //System.out.println("invVal=" +invVal);
         if(invVal < 1)
         {
            if(searchDat[super.getYLoc()][super.getXLoc()] == true)
            {
               go(map);
               if(searchDat[super.getYLoc()][super.getXLoc()] == true)
                  go(map);
               else
                  search(map);
            }
            else
            {
               search(map);
               go(map);
            }
         }
         else
         {
            go(map);
            go(map);
         }
      }
      manInv();
      //System.out.println("TURN END");
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
      //System.out.println("SEARCH: " + found);
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
         //System.out.println("MOVE: " + direction);
         
      }
      //else
         //System.out.println("NO MOVE");
      
      //System.out.println("nX=" + super.getXLoc());
      //System.out.println("nY=" + super.getYLoc());
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
   
   private void make()
   {
      //TODO: finish!!!
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