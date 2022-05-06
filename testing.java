import java.util.*;
public class testing { public static void main(String[] args) {
//=============================================================================//
char[][] array = {{'a','b','c','d'},
                  {'e','f','g','h'},
                  {'i','j','k','l'}};

for(int y=0; y<array.length; y++)
{
   for(int x=0; x<array[y].length/2; x++)
   {
      char temp = array[y][x];
      array[y][x] = array[y][array[y].length-1];
      array[y][array[y].length-1] = temp;
   }
}


/*
for(int y=0; y < array.length/2; y++)
{
   for(int x=0; x < array[y].length; x++)
   {
      char temp = array[y][x];
      array[y][x] = array[array.length-1][x];
      array[array.length-1][x] = temp;
   }
}
*/

//prinout
for(int y=0; y < array.length; y++)
{
   for(int x=0; x < array[y].length; x++)
   {
      System.out.print(array[y][x]);
   }
   System.out.println();
}

//=============================================================================//
}}