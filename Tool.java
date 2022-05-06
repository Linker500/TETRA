public class Tool implements Item
{
   private int type; //Type of tool, 0 = attack, 1 = craft, 2 = charm
   private int tier; //Tier of tool, 0 = crude(+1), 1 = moderate[TODO: FIX NAME](+2), 2 = Masterpiece(+3)
   private int specType; //Specific tool, sword, spear etc. Purely cosmetic
   private int specTier; //Specific tier, crappy, broke, etc. Purely cosmetic
   private boolean isUsed;
   
   //TODO: FIND ALTERNATIVE TO MATH.RANDOM()
   public Tool()
   {
      type = (int)(Math.random()*3);
      tier = (int)(Math.random()*3);
      specType = (int)(Math.random()*4);
      specTier = (int)(Math.random()*5);
      isUsed = false;
   }
   
   public Tool(int newType, int newTier)
   {
      type = newType;
      tier = newTier;
      specType = (int)(Math.random()*4);
      specTier = (int)(Math.random()*5);
      isUsed = false;
   }
   
   public Tool(int newType, int newTier, int newSpecType, int newSpecTier)
   {
      type = newType;
      tier = newTier;
      specType = newSpecType;
      specTier = newSpecTier;
   }
   
   public int identify() {return 0;} //Identifies this Item as a tool
   
   public boolean isUsed()                   {return isUsed;}
   public void    setIsUsed(boolean newUsed) {isUsed = newUsed;}
   
   public int getTier() {return tier;}
   
   public void use(Character user)
   {
      int[] pot; //Potency of tool tiers, in array for easy access
      if(isUsed)
      {
         pot = new int[] {-1,-2,-3};
         isUsed = false;
         if(user instanceof PC)
            System.out.println("You un-equip the " + toString() + ".");
      }
      else
      {
         pot = new int[] {1,2,3};
         isUsed = true;
         if(user instanceof PC)
            System.out.println("You equip the " + toString() + ".");
      }
      
      if(type ==0)
         user.incAtk(pot[tier]);
      else if(type ==1)
         user.incCft(pot[tier]);
      else if(type ==2)
         user.incCrm(pot[tier]);
   }
   
   public String toString()
   {
      //TODO: how do you spell decent?
      String[] specTypes = {""};
      String[] specTiers = {""};
      String used = "";
      
      //Specific Tool type
      if(type == 0) //Attack tool
         specTypes = new String[] {"Sword","Spear","Shield","Bow"};
      if(type == 1) //Craft tool
         specTypes = new String[] {"Utility-Blade","Hammer","Saw","File"};
      if(type == 2) //Charm tool
         specTypes = new String[] {"Shiny-Pocket-Watch","Hypno-Goggles","Spinny-Disc","Flashy-Rainbow-Lights"};
      
      //Specific Tier types
      if(tier == 0) //Low tier
         specTiers = new String[] {"Crude","Flimsy","Made-In-China","Crappy","Broken"};
      if(tier == 1) //Mid tier
         specTiers = new String[] {"Decent","Mid-Range","Moderate","Normal","Unexceptional"};
      if(tier == 2) //High tier
         specTiers = new String[] {"Master's","Amazing","High-Quality","One-of-a-Kind","Excellent"};
            
      return specTiers[specTier] + " " + specTypes[specType];
   }
}