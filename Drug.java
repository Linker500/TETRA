public class Drug implements Item
{
   private int type; //0 = Phy; 1 = Men; 2 = Soc;
   private int tier; //0 = Low; 1 = Mid; 2 = Hig;
   private boolean isUsed;
   
   //TODO: Replace math.random???
   public Drug()
   {
      type = (int)(Math.random()*3);
      tier = (int)(Math.random()*3);
      isUsed = false;
   }
   
   public Drug(int newType)
   {
      type = newType;
      tier = (int)(Math.random()*3);
      isUsed = false; 
   }
   
   public Drug(int newType, int newTier)
   {
      type = newType;
      tier = newTier;
      isUsed = false;
   }
   
   public int identify() {return 2;}
   
   public int getTier() {return tier;}
   
   public boolean isUsed() {return isUsed;}
   
   public void use(Character user)
   {
      if(isUsed && user instanceof PC)
         System.out.println("You can't use an empty syringe!");
      else
      {
         int[] pot = {1,2,3}; //Variables controlling potency bonuses. Set up in an array for easy access
         if(type ==0) //Physical
         {
            if(tier ==0)
               user.incPhy(pot[0]);
            else if(tier ==1)
               user.incPhy(pot[1]);
            else if(tier ==2)
               user.incPhy(pot[2]);
         }
         else if(type ==1) //Mental
         {
            if(tier ==0)
               user.incMen(pot[0]);
            else if(tier ==1)
               user.incMen(pot[1]);
            else if(tier ==2)
               user.incMen(pot[2]);
         }
         else if(type ==2) //Social
         {
            if(tier ==0)
               user.incSoc(pot[0]);
            else if(tier ==1)
               user.incSoc(pot[1]);
            else if(tier ==2)
               user.incSoc(pot[2]);
         }
         isUsed = true;
      }
      if(user instanceof PC)
      {
         System.out.println("You stab the syringe into your leg, and inject the drug...");
         Util.stop(1000);
         {
            String[] strength = {"a little","considerably","significantly"};
            String[] stat = {"stronger","smarter","more charismatic"};
            System.out.print("and feel " + strength[tier] + " " + stat[type] + "!\n");
         }
      }
      Util.stop(1000);
   }
   
   public String toString()
   {
      String status = "";
      if(isUsed)
         status = "Empty ";
      String[] types = {"Fruit-Punch","Cool-Mint","Lemon-Lime"};
      String[] tiers = {"Contaminated","Impure","Pure"};
      return status + tiers[tier] + " " + types[type] + " Drug";
   }
}