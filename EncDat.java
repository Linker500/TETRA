public class EncDat
{
   private int       playNumb;
   private boolean   isWon;
   private int       encType;
   
   public EncDat(int newPlayNumb, boolean newIsWon, int newEncType)
   {
      playNumb = newPlayNumb;
      isWon = newIsWon;
      encType = newEncType;
   }
   
   public int     getPlayNumb()  {return playNumb;}
   public boolean getisWon()     {return isWon;}
   public int     getEncType()   {return encType;}
   
   public String toString()
   {
      return "Encounter Data:\nWith: " + playNumb + "\nWon?: " + isWon + "\nEncType: " + encType;
   }
}