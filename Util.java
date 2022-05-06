import java.util.*;

public class Util
{  
   //TODO- redo parameters and overall clean up method
   public static void spell(String word, int delay, int line)
   {
      int[] delays = {500,200,100,75,50,25,10};
      int d = delays[delay+3];      
      
      if(line == -1)
         System.out.println();
      int L = word.length();
      for (int j = 0; j < L; j+= 1)
      {
         char chr = word.charAt(j);
         stop(d);
         System.out.print(chr);
      }
      if(line == 1)
         System.out.println();
   }
   
   public static void hold()
   {
      Scanner in = new Scanner(System.in);
      System.out.println("Press enter to continue");
      in.nextLine();
   }
   
   public static void stop(long delay)
   {
      long end = System.currentTimeMillis() + delay;
      
      while(end>System.currentTimeMillis())
         System.out.print("");
   }
   
   public static void clear(){System.out.print("\033[H\033[2J");}
   
   /*
    *
    */
   
   //TODO delete when invnetory input has proper try-catch implemented
   //48-57 for number chars
   public static boolean isInt(String input)
   {
      boolean toReturn = true;
      for(int i=0; i< input.length() && toReturn; i++)
      {
         //Does string contain a non-numeral character?
         if((int)input.charAt(i) < 48 || (int)input.charAt(i) > 57)
            toReturn = false;
      }
      return toReturn;
   }
   
   
   //TODO maybe make a class of PC??
   public static boolean printInv(ArrayList<Item> inventory)
   {
      if(inventory.size() == 0)
      {
         System.out.println("Your inventory is empty!\n");
         return false;
      }
      else
      {
         for(int i=0; i<inventory.size(); i++)
         {
            String print = inventory.get(i).toString();
            
            if(inventory.get(i).isUsed())
            {
               if(inventory.get(i).identify() == 0)
                  print+=" [E]";
               else if(inventory.get(i).identify() == 1)
                  print+=" [H]";
            }
            
            /*(if(i%4 == 0) //Staggers items into groups of 4. Removed to use vertical instead of horizontal space
               System.out.println();
            System.out.print(i+1 + ": " + print + "; ");*/
            
            System.out.print("\n"+ (i+1) + ": " + print);
         }   
         System.out.println("\n");
         return true;
      }
   }
}