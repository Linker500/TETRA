//TODO: NPC names
/*
Linehan
Stuart
Phillip
Aiden
Kyle
Kelsey
Lawrence
Jaiden?
*/

//TODO: Any possesive statements should have an if for proper grammer
//user.getName()'s might end up with Douglas's which is grammatically incorrect.
//Fix later in development!

import java.util.*;

public class Tetra_Battle_Royale
{
   public static void main(String[] args)
   {
      //test();
      //title();
      //play(setup());
      play(1);
   }
   
   public static void title()
   {
      //TODO! Redo this entire thing in the same style
      Util.spell("Linker Labs Presents",-2,0);
      Util.spell("...",-3,0);
      Util.stop(500);
      System.out.println();
      Util.stop(500);
      System.out.println();
      Util.stop(500);
      System.out.print("TETRA:");
      System.out.println();
      Util.stop(500);
      Util.spell("           ",3,0);
      Util.stop(1000);
      Util.spell("Battle Royale",1,1);
      Util.stop(500);
      System.out.println();
      Util.stop(1500);
      //TODO: better implement title art
      System.out.println("\n" + TitleArt.get()+"\n");
      Util.hold();
   }
   
   public static int setup()
   {
      Scanner in = new Scanner(System.in);
      
      Util.clear();
      boolean breaker = true;
      int humans = 0;
      while(breaker)
      {
         System.out.println("How many human players are there? [0-16]");
         System.out.print(">");
         
         try
         {
            humans = in.nextInt();
            if(humans < 0 || humans > 16)
            {
               System.out.println("Invalid input!");
               Util.stop(1000);
            }
            else
               breaker = false;
         }
         catch(Exception e)
         {
            System.out.println("Invalid input!");
            Util.stop(1000);
         }
      }
      
      return humans;
   }
   
   //Game
   public static void play(int humans)
   {
      Util.clear();
      Game game = new Game(humans); //Hold game data- maps, players, etc.
      game.start();
         
         //TODO: Maybe these clear screen in terminal? Test later!
         //System.out.print("\033[H\033[2J");
         //System.out.flush();
         //System.out.println("Cleared?");
   }
   
   //TODO: Remove in final release
   public static void test()
   {
      
   }
}