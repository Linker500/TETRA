import java.util.*;

public class LootCrate implements Item
{
   private int tier; //0 = common; 1 = uncommon; 2 = epic; 3 = ultra; 4 = legendary
   private boolean isUsed;
   
   //TODO: find better than math.random?
   public LootCrate()
   {
      tier = (int)(Math.random()*5);
      isUsed = false;
   }
   
   public LootCrate(int newTier)
   {
      tier = newTier;
      isUsed = false;
   }
   
   public int identify() {return 3;} //Identifies this Item as a LootCrate
   
   public int getTier() {return tier;}
   
   public boolean isUsed() {return isUsed;}
   
   //TODO: Replace math.random!11
   public void use(Character user)
   {
      ArrayList<Item> inventory = user.getInv();
      int random = (int)(Math.random()*100+1);
      
      if(!isUsed)
      {
         isUsed = true;
         Item loot;
         
         if(tier == 0)
            loot = common(random);
         else if(tier == 1)
            loot = uncommon(random);
         else if(tier == 2)
            loot = epic(random);
         else if(tier == 3)
            loot = ultra(random);
         else
            loot = legendary(random);
         inventory.add(loot);
         if(user instanceof PC)
            System.out.println("You unboxed " + loot + "!");
      }
      else if(user instanceof PC)
         System.out.println("You can't open an empty LootCrate!");
   }
   
   private Item common(int random)
   {
      if(random <=30)
         return new Material((int)(Math.random()*2),0); //Random low tier Material
      else if(random >30 && random <=40)
         return new Material((int)(Math.random()*2),1); //Random mid tier Material
      else if(random >40 && random <=70)
         return new Tool((int)(Math.random()*3),0); //Random low tier Tool
      else if(random >70 && random <=80)
         return new Tool((int)(Math.random()*3),1); //Random mid tier Tool
      else if(random >80 && random <=95)
         return new Drug((int)(Math.random()*3),0); //Random low tier Drug
      else
         return new LootCrate(1); //Uncommon LootCrate
   }
   
   private Item uncommon(int random)
   {
      if(random <=25)
         return new Material((int)(Math.random()*2),0); //Random low tier Material
      else if(random >25 && random <=40)
         return new Material((int)(Math.random()*2),1); //Random mid tier Material
      else if(random >40 && random <=65)
         return new Tool((int)(Math.random()*3),0); //Random low tier Tool
      else if(random >65 && random <=80)
         return new Tool((int)(Math.random()*3),1); //Random mid tier Tool
      else if(random >80 && random <=90)
         return new Drug((int)(Math.random()*3),0); //Random low tier Drug
      else if(random >90 && random <= 95)
         return new Drug((int)(Math.random()*3),1); //Random mid tier Drug
      else
         return new LootCrate(2); //Epic LootCrate
   }
   
   private Item epic(int random)
   {
      if(random <=10)
         return new Material((int)(Math.random()*2),0); //Random low tier Material
      else if(random >10 && random <=30)
         return new Material((int)(Math.random()*2),1); //Random mid tier Material
      else if(random >30 && random <=35)
         return new Material((int)(Math.random()*2),2); //Random high tier Material
      else if(random >35 && random <=45)
         return new Tool((int)(Math.random()*3),0); //Random low tier Tool
      else if(random >45 && random <=65)
         return new Tool((int)(Math.random()*3),1); //Random mid tier Tool
      else if(random >65 && random <= 70)
         return new Tool((int)(Math.random()*3),2); //Random high tier Tool
      else if(random >70 && random <=80)
         return new Drug((int)(Math.random()*3),0); //Random low tier Drug
      else if(random >80 && random <= 90)
         return new Drug((int)(Math.random()*3),1); //Random mid tier Drug
      else if(random >90 && random <= 95)
         return new Drug((int)(Math.random()*3),2); //Random high tier Drug
      else
         return new LootCrate(3); //Ultra LootCrate
   }
   
   private Item ultra(int random)
   {
      if(random <=15)
         return new Material((int)(Math.random()*2),1); //Random mid tier Material
      else if(random >15 && random <=35)
         return new Material((int)(Math.random()*2),2); //Random high tier Material
      else if(random >35 && random <=50)
         return new Tool((int)(Math.random()*3),1); //Random mid tier Tool
      else if(random >50 && random <=70)
         return new Tool((int)(Math.random()*3),2); //Random high tier Tool
      else if(random >70 && random <=85)
         return new Drug((int)(Math.random()*3),1); //Random mid tier Drug
      else if(random >85 && random <= 95)
         return new Drug((int)(Math.random()*3),2); //Random high tier Drug
      else
         return new LootCrate(4); //Legendary LootCrate
   }
   
   private Item legendary(int random)
   {
      if(random <=10)
         return new Material((int)(Math.random()*2),1); //Random mid tier Material
      else if(random >10 && random <=35)
         return new Material((int)(Math.random()*2),2); //Random high tier Material
      else if(random >35 && random <=45)
         return new Tool((int)(Math.random()*3),1); //Random mid tier Tool
      else if(random >45 && random <=70)
         return new Tool((int)(Math.random()*3),2); //Random high tier Tool
      else if(random >70 && random <=80)
         return new Drug((int)(Math.random()*3),1); //Random mid tier Drug
      else if(random >80 && random <= 95)
         return new Drug((int)(Math.random()*3),2); //Random high tier Drug
      else
         return new LootCrate(0); //Common LootCrate. Yes, this is intentional, and hilarious.
   }
   
   
   public String toString()
   {
      String status = "";
      if(isUsed)
         status = "Empty ";
      String[] tiers = {"Common","Uncommon","Epic","Ultra","Legendary"};
      return status + tiers[tier] + " Crate";
   }
}