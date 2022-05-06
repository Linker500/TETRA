
/* This map is freaking huge
 * This class is dedicated to just creating maps to keep thier ugly filth out of my nicer methods
 * These arrays are digusting.
 */

public class Level
{
   public static Tile[][] gen(int numb)
   {
      switch(numb)
      {
         case(-1):
            return test();
         case( 0):
            return normal();
         case( 1):
            return large();
      }
      throw new IllegalArgumentException("Invalid level!");
   }
   
   private static Tile[][] test()
   {
      Tile[][] data =
     {{new Tile(2),new Tile(2),new Tile(2)},
      {new Tile(2),new Tile(1),new Tile(2)},
      {new Tile(2),new Tile(2),new Tile(2)}};
      return data;
   }
   
   //Normal map
   private static Tile[][] normal()
   {
      Tile[][] data =
     {{new Tile(1),new Tile(2),new Tile(3),new Tile(3),new Tile(3),new Tile(3),new Tile(3),new Tile(2),new Tile(1)},
      {new Tile(2),new Tile(2),new Tile(4),new Tile(4),new Tile(5),new Tile(4),new Tile(4),new Tile(2),new Tile(2)},
      {new Tile(2),new Tile(4),new Tile(6),new Tile(6),new Tile(6),new Tile(6),new Tile(6),new Tile(4),new Tile(3)},
      {new Tile(2),new Tile(4),new Tile(6),new Tile(7),new Tile(7),new Tile(7),new Tile(6),new Tile(4),new Tile(3)},
      {new Tile(2),new Tile(5),new Tile(6),new Tile(7),new Tile(8),new Tile(7),new Tile(6),new Tile(5),new Tile(3)},
      {new Tile(2),new Tile(4),new Tile(6),new Tile(7),new Tile(7),new Tile(7),new Tile(6),new Tile(4),new Tile(3)},
      {new Tile(2),new Tile(4),new Tile(6),new Tile(6),new Tile(6),new Tile(6),new Tile(6),new Tile(4),new Tile(3)},
      {new Tile(2),new Tile(2),new Tile(4),new Tile(4),new Tile(5),new Tile(4),new Tile(4),new Tile(2),new Tile(2)},
      {new Tile(1),new Tile(2),new Tile(3),new Tile(3),new Tile(3),new Tile(3),new Tile(3),new Tile(2),new Tile(1)}};
      return data;
   }
   
   //El huge mapo, maybe not use?
   private static Tile[][] large()
   {
      Tile[][] data =
     {{new Tile(2),new Tile(2),new Tile(2),/**/new Tile(2),new Tile(2),new Tile(2),/**/new Tile(2),new Tile(2),new Tile(2),/**/},
      {new Tile(2),new Tile(2),new Tile(2),/**/new Tile(2),new Tile(2),new Tile(2),/**/new Tile(2),new Tile(2),new Tile(2),/**/},
      {new Tile(2),new Tile(2),new Tile(2),/**/new Tile(2),new Tile(2),new Tile(2),/**/new Tile(2),new Tile(2),new Tile(2),/**/},
      /***********************************************************************************************************************/
      /***********************************************************************************************************************/
      {new Tile(2),new Tile(2),new Tile(2),/**/new Tile(2),new Tile(2),new Tile(2),/**/new Tile(2),new Tile(2),new Tile(2),/**/},
      {new Tile(2),new Tile(2),new Tile(2),/**/new Tile(2),new Tile(1),new Tile(2),/**/new Tile(2),new Tile(2),new Tile(2),/**/},
      {new Tile(2),new Tile(2),new Tile(2),/**/new Tile(2),new Tile(2),new Tile(2),/**/new Tile(2),new Tile(2),new Tile(2),/**/},
      /***********************************************************************************************************************/
      /***********************************************************************************************************************/
      {new Tile(2),new Tile(2),new Tile(2),/**/new Tile(2),new Tile(2),new Tile(2),/**/new Tile(2),new Tile(2),new Tile(2),/**/},
      {new Tile(2),new Tile(2),new Tile(2),/**/new Tile(2),new Tile(2),new Tile(2),/**/new Tile(2),new Tile(2),new Tile(2),/**/},
      {new Tile(2),new Tile(2),new Tile(2),/**/new Tile(2),new Tile(2),new Tile(2),/**/new Tile(2),new Tile(2),new Tile(2),/**/},
      };
      return data;
   }
}