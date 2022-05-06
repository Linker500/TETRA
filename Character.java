//TODO: these comments SUCK!

import java.util.*;

public class Character
{  
   private Game game;
   private ArrayList<ArrayList<String>> summary = new ArrayList<ArrayList<String>>(); //TODO: maybe initialize in constructor???
   private ArrayList<String> newSummary = new ArrayList<String>();
   
   private String name; //Name used in event log for character
   private boolean life = true;   //Is character alive?
   private int xLoc;
   private int yLoc;
   
   private boolean fortified = false; //Whether player is fortified or not
   
   /* Natural Stats (Character Abilities)
    * These stats are created with the character, and can only be modified with serums.
    * These determine what a character is good at, and therefore should influence how they play.
    */
   private int phy = 0; //Physical- Used for attacking and fleeing
   private int men = 0; //Mental- Used for crafting and resisting charm
   private int soc = 0; //Social-Used in charming others
   
   /* Item Stats
    *
    *
    */
   private int atk = 0; //Bonus to attacking from items
   private int cft = 0; //Bonus to crafting from items
   private int crm = 0; //Bonus to charming from items
   
   private int hold = 0; //How many items person is holding
   
   
   /* Inventory:
    * 
    * 
    */
   
   private ArrayList<Item> inventory = new ArrayList<Item>();
   
   //TODO stats
   public Character(Game newGame)
   {
      game = newGame;
   }
   
   public Character(Game newGame, int newPhy, int newMen, int newSoc)
   {
      game = newGame;
      phy = newPhy;
      men = newMen;
      soc = newSoc;
   }
   public Game    getGame()                     {return game;}
   
   //Basic Info
   public void    setName(String newName)       {name = newName;}
   public String  getName()                     {return name;}
   
   public boolean getLife()                     {return life;}
   public void    setLife(boolean newLife)      {life = newLife;}
      
   //Locational
   public void    setLoc(int newX, int newY)    {xLoc = newX; yLoc = newY;}
   public int[]   getLoc()                      {int[] toReturn = {xLoc,yLoc}; return toReturn;}
   public int     getXLoc()                     {return xLoc;}
   public int     getYLoc()                     {return yLoc;}
   
   public void    setFortified(boolean newFort) {fortified = newFort;}
   public boolean getFortified()                {return fortified;} //TODO: maybe rename to isFortified()?
   
   //Stats
   public int     getPhy()                      {return phy;}
   public void    setPhy(int newPhy)            {phy = newPhy;}
   public int     getMen()                      {return men;}
   public void    setMen(int newMen)            {men = newMen;}
   public int     getSoc()                      {return soc;}
   public void    setSoc(int newSoc)            {soc = newSoc;}
   
   public int     getAtk()                      {return atk;}
   public void    setAtk(int newAtk)            {atk = newAtk;}
   public int     getCft()                      {return cft;}
   public void    setCft(int newCft)            {cft = newCft;}
   public int     getCrm()                      {return crm;}
   public void    setCrm(int newCrm)            {crm = newCrm;}
   
   public int     getHold()                     {return hold;}
   public void    setHold(int newHold)          {hold = newHold;}
   
   public ArrayList<Item> getInv()        {return inventory;}
   
   //Increment specific stats. Needed when using items
   public void    incPhy(int inc)               {phy+=inc;}
   public void    incMen(int inc)               {men+=inc;}
   public void    incSoc(int inc)               {soc+=inc;}
   
   public void    incAtk(int inc)               {atk+=inc;}
   public void    incCft(int inc)               {cft+=inc;}
   public void    incCrm(int inc)               {crm+=inc;}
   
   public void    incHold(int inc)              {hold+=inc;}
   
   public void act(Tile[][] map, int round) {}
   public int  encounter(Character enemy) {return -1;}
   
   //TODO: THIS IS TEMPERARY REMOVE LATER
   public void cheat()
   {
      inventory.add(new Tool());
      inventory.add(new Material(0));
      inventory.add(new Material(1));
      inventory.add(new Drug());
      inventory.add(new LootCrate());
   }
   
   public void cheat(int i)
   {
      inventory.add(new Tool(i,2));
      inventory.add(new Material(0));
      inventory.add(new Material(1));
      inventory.add(new Drug(i,2));
      inventory.add(new LootCrate(4));
   }
   
   //TODO! Condense into array...?
   public void move(int direction)
   {
      switch(direction)
      {
         case(0): //North
         {
            setLoc(xLoc,yLoc-1);
            break;
         }
         
         case(1): //West
         {
            setLoc(xLoc-1,yLoc);
            break;
         }
         
         case(2): //South
         {
            setLoc(xLoc,yLoc+1);
            break;
         }
         
         case(3): //East
         {
            setLoc(xLoc+1,yLoc);
            break;
         }
      }
   }
   
   //These three methods are for returning combat power
   public int attackOff()
   {
      int power = phy + atk + (int)(Math.random()*6+1);
      if(fortified) //A bonus assuming the player is fortified.
         power+=2;
      return power;
   }
   
   public int charmOff()
   {
      int power = soc + crm + (int)(Math.random()*6+1);
      if(fortified)
         power+=2;
      return power; 
   }
   
   public int attackDef()
   {
      int power = phy + atk + (int)(Math.random()*6+1);
      if(fortified) //A bonus assuming the player is fortified.
         power+=2;
      return power;
   }
   
   public int charmDef()
   {
      int power = soc + crm + (int)(Math.random()*6+1);
      if(fortified)
         power+=2;
      return power; 
   }
   
   public void reCalc() //TODO: Recalculate bonus from equipped items??? Maybe not needed, maybe nice to have, would certainly mitigate effectiveness of some exploits
   {
      
   }
   
   public void addSummary(ArrayList<String> sumList) //TODO: maybe remove when possible and move to new sumamry system. Will require significant code rewrites
   {
      summary.add(sumList);
   }
   
   public void addNewSummary(String summaryText)
   {
      newSummary.add(summaryText);
   }
   
   public void pushNewSummary()
   {
      summary.add(newSummary);
      newSummary = new ArrayList<String>();
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
   
   //TODO maybe add kill count?
   public String toString()
   {
      return
      "\n" + name +
      "\n----------" +
      "\nPhysical " + phy +
      "\n  Mental " + men +
      "\n  Social " + soc +
      "\n" +
      "\n  Attack " + atk +
      "\n   Craft " + cft +
      "\n   Charm " + crm +
      "\n";
   }
}