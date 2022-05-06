//   File Name: Project2.java
//  Programmer: Stuart MacLeod
//Last Updated: 2018-09-17
 
/*
------------------------------------------------------------------------------------------------------------------
Description:
------------------------------------------------------------------------------------------------------------------
*/

//Copyright MacLeod Industries 2017. All rights reserved. 


import java.util.Scanner;




public class Project2
{

   //*********Main Method*********\\

   public static void main(String[] args)
   {  
      Scanner i = new Scanner(System.in);
    //Player Stats:  Phy Men Soc
    //Equip  Stats:  Wep Fod Wat
      
    //Player 1 ("Brute")
      int[] pstat1 = { 3, 0,-2};
      int[] estat1 = { 0, 0, 0};
      
    //Player 2 ("Nerd")
      int[] pstat1 = {-2, 3, 0);
      int[] estat1 = { 0, 0, 0};

    //Player 3 ("Blonde")
      int[] pstat1 = { 0,-2, 3};
      int[] estat1 = { 0, 0, 0};
      
    //****************************************************************************\\
   
      
      
            
   }
   
   //*****************************\\
   
   
   
   
   //**********UTILITIES**********\\
   
   public static void delay(int n) 
   {
      long startDelay = System.currentTimeMillis();
      long endDelay = 0;
      while(endDelay - startDelay < n)
      {
         endDelay = System.currentTimeMillis();
      }
   }
    
   //*****************************\\   
}//Class End