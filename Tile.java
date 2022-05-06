import java.util.*;

public class Tile
{
   private int biome;
   /* Biome Values:
    *-1: Shadow Zone??
    * 0: Barrier
    * 1: Plains
    * 2: Forest
    * 3: Water
    * 4: Bridge
    * 5: Desert
    * 6: Crag
    * 7: Tower
    */
   private boolean isShadow = false; //Is the tile consumed by shadow zone?
   private ArrayList<Item> inventory = new ArrayList<Item>(); //The "inventory" of the tile. What can be found from searching
    
   public Tile(int newBiome)
   {
      biome = newBiome;
      
      Math.random();
   }
    
   public int  getBiome()             {return biome;}
   public void setBiome(int newBiome) {biome = newBiome;}
   
   public Item search()
   {
      if(inventory.size() == 0)
         return null;
      //TODO: Find better random???
      int itemIndex = (int)(Math.random()*inventory.size()); //Chooses random index of inventory array
      Item toReturn = inventory.get(itemIndex); //Saves item in chosen index
      inventory.remove(itemIndex); //Removes item from chosen index
      return toReturn; //Returns saved item
   }
   
   public void genLoot(int tier)
   {
      //TODO: this randomness sucks
      if(biome == 8)
      {
         inventory.add(new Magik());
      }
      else if(biome == 1)
      {
         for(int i=0; i<4; i++)
            inventory.add(new LootCrate(tier));
      }
      else if((int)(Math.random()*4) < 3)
      {
         inventory.add(new LootCrate(tier));
      }
   }
}