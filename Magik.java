public class Magik extends Tool
{  
   public void use(Character user)
   {
      if(super.isUsed())
      {
         user.incAtk(-4);
         user.incCft(-4);
         user.incCrm(-4);
         super.setIsUsed(false);
         if(user instanceof PC)
            System.out.println("You un-equip the " + toString() + ", and feels the power drain away.");
      }
      else
      {
         user.incAtk(4);
         user.incCft(4);
         user.incCrm(4);
         super.setIsUsed(true);
         if(user instanceof PC)
            System.out.println("You equip the " + toString() + " and feels its tremendous power surge through your body!");
      }
   }
   
   public String toString()
   {
      return "MAGIK";
   }
}