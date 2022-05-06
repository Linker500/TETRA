public class Material implements Item
{
   private int type; //0 = Base; 1 = Binder
   private int tier; //0 = crap; 1 = mid; 2 = good
   private int spec = (int)(Math.random()*3); //Random number to decide random specific type. Purely cosmetic
   private boolean isUsed;
   
   //TODO: find alt to math.random?
   public Material()
   {
      type = (int)(Math.random()*2);
      tier = (int)(Math.random()*3);
      isUsed = false;
   }
   
   public Material(int newType)
   {
      type = newType;
      tier = (int)(Math.random()*3);
      isUsed = false;
   }

   public Material(int newType, int newTier)
   {
      type = newType;
      tier = newTier;
      isUsed = false;
   }
   
   public int identify() {return 1;} //Identifies this item as a Material
   
   public int getTier() {return tier;}
   
   public boolean isUsed() {return isUsed;}
   
   public void use(Character user)
   {
      if(isUsed)
      {
         isUsed = false;
         user.incHold(-1);
         if(user instanceof PC)
            System.out.println("You put your " + toString() + " away.");
      }
      else
      {
         if(user.getHold() < 2)
         {
            isUsed = true;
            user.incHold(1);
            if(user instanceof PC)
               System.out.println("You hold your " + toString() + ", ready to make something.");
         }
         else if(user instanceof PC)
         {
            System.out.println("You can only hold 2 materials!");
            Util.stop(1000);
         }
      }
   }
   
   //TODO: Find alt to math.random??
   public String toString()
   {
      String[] specs = {"temp"};
      String used = "";
      
      //Bases
      if(type == 0)
      {
         if(tier == 0)
            specs = new String[] {"\"Chinese-ium\"","Thin-Wood","Cheap-Plastic"};
         else if(tier == 1)
            specs = new String[] {"Iron","Thick Plastic","Heavy Wood"};
         else if(tier == 2)
            specs = new String[] {"Titanium","Carbon Fiber","Aluminum"};
      }
      //Binders
      else if(type == 1)
      {
         //Binders
         if(tier == 0)
            specs = new String[] {"String","Used-Nails","Scotch Tape"};            
         else if(tier == 1)
            specs = new String[] {"New-Nails","Rope","Duct-Tape"};
         else if(tier == 2)
            specs = new String[] {"Flex-Tape","Epoxy","Steel Wire"};
      }
      return specs[spec];
   }
}