import java.util.*;

//TODO: These comments really suck

/* Turn Options
 * ==========================================
 * MAJOR ACTIONS (2 per turn normally)
 * go (moves- north south east west up? down?)
 * rest (saves energy)
 * search (look for loot)
 * fortify (prepare for an attack- bonus if attacked on that turn
 * 
 * MINOR ACTIONS (~3 per turn)
 * craft (crafts items)
 * 
 * USER ACTIONS (Actions to show user statsm inventory, etc. infinite per turn)
 * inv (shows inventory)
 * stats (shows player stats)
 * end (ends turn (asks for comfirmation)
 *
 * Major
 */

public class PC extends Character
{
   //Ansi escape codes, for map drawing and whatnot. Only works on UNIX systems... sorry but not sorry Windows users.
   public static final String ANSI_RES = "\u001B[0m";
   
   public static final String ANSI_BLA = "\u001B[30m";
   public static final String ANSI_RED = "\u001B[31m";
   public static final String ANSI_GRE = "\u001B[32m";
   public static final String ANSI_YEL = "\u001B[33m";
   public static final String ANSI_BLU = "\u001B[34m";
   public static final String ANSI_MAG = "\u001B[35m";
   public static final String ANSI_CYA = "\u001B[36m";
   public static final String ANSI_LGRA = "\u001B[37m";
   //38???
   public static final String ANSI_DEF = "\u001B[39m";
   
   public static final String ANSI_BLA_BG = "\u001B[40m";
   public static final String ANSI_RED_BG = "\u001B[41m";
   public static final String ANSI_GRE_BG = "\u001B[42m";
   public static final String ANSI_YEL_BG = "\u001B[43m";
   public static final String ANSI_BLU_BG = "\u001B[44m";
   public static final String ANSI_MAG_BG = "\u001B[45m";
   public static final String ANSI_CYA_BG = "\u001B[46m";
   public static final String ANSI_LGRA_BG = "\u001B[47m";
   //48?
   public static final String ANSI_DEF_BG = "\u001B[49m";
   
   //Gap...???
   
   public static final String ANSI_DGRA   = "\u001B[90m";
   public static final String ANSI_LRED   = "\u001B[91m";
   public static final String ANSI_LGRE   = "\u001B[92m";
   public static final String ANSI_LYEL   = "\u001B[93m";
   public static final String ANSI_LBLU   = "\u001B[94m";
   public static final String ANSI_LMAG   = "\u001B[95m";
   public static final String ANSI_LCYA   = "\u001B[96m";
   public static final String ANSI_WHI    = "\u001B[97m";
   //97-99?
   public static final String ANSI_DGRA_BG   = "\u001B[100m";
   public static final String ANSI_LRED_BG   = "\u001B[101m";
   public static final String ANSI_LGRE_BG   = "\u001B[102m";
   public static final String ANSI_LYEL_BG   = "\u001B[103m";
   public static final String ANSI_LBLU_BG   = "\u001B[104m";
   public static final String ANSI_LMAG_BG   = "\u001B[105m";
   public static final String ANSI_LCYA_BG   = "\u001B[106m";
   public static final String ANSI_WHI_BG    = "\u001B[107m";
   
   public PC(Game game)
   {
      super(game);
   }
   
   //Player Character Action
   public void act(Tile[][] map, int round)
   {
      Scanner in = new Scanner(System.in);
      Util.clear();
      System.out.println(super.getName() + "'s Turn");
      Util.hold();
      
      Util.clear();
      //System.out.println("Summary:\n------------------------------");
      //super.summarize();
      //super.getGame().printLife();
      
      //TODO: config file to enable global summary? It sucks normally and is a mess, but is nice to have occasionally.
      System.out.println("Global Summary:\n------------------------------");
      super.getGame().summarize();
      
      int actions = 2; //TODO: THIS IS TEMP
      
      //Main loop
      boolean breaker = true;
      while(breaker)
      {
         Util.clear();
         System.out.println("\n" + super.getName()+ "'s turn:");
         System.out.println("Round " + round);
         System.out.println("========================================================================================================================");
         
         {//Forces x and y variables into lower scope, as we only need them for a short while
            int x = super.getXLoc();
            int y = super.getYLoc();
            System.out.println();
            try
            {
               printText(map[y][x].getBiome()); //Prints flavor text for biome
               printLoc(x,y,map); //Prints out map
            }
            catch(ArrayIndexOutOfBoundsException e) //If the player is magically out of bounds
            {
               printText(0);
               printLoc(x,y,map);
            }
         }
         System.out.println("What do you want to do?"
                        + "\n[AP]: " + actions
                        + "\n1 AP: (g)o; (s)earch; (f)ortify;"
                        + "\n0 AP: (i)nventory; (m)ake; (v)iew stats; (h)elp; (e)nd turn;");
         System.out.print(">");
         String input = in.nextLine();
         input.toLowerCase();
         
         switch(input)
         {  
            //"Go" enters move menu
            case("g"):
            {
               if(actions > 0)
               {
                  actions = go(actions, map, in); //TODO: return true or false instead of action points removed
                  if(super.getFortified()) //Removes fortificaiton bonus area is left
                  {
                     super.setFortified(false);
                     System.out.println("Because you have moved, you are no longer fortified.");
                     Util.stop(1000);
                  }
               }
               else
               {
                  System.out.println("Not enough actions left!");
                  Util.stop(1000);
               }
               break;
            }
            
            case("s"):
            {
               if(actions > 0)
               {
                  search(map[super.getYLoc()][super.getXLoc()]);
                  actions--;
               }
               else
               {
                  System.out.println("Not enough actions left!");
                  Util.stop(1000);
               }
               break;
            }
            
            case("f"):
            {
               if(super.getFortified())
                  System.out.println("You are already fortified!");
               else if(actions >0)
               {
                  System.out.println("You fortify yourself, readying for an encounter.");
                  super.setFortified(true);
                  actions--;
                  Util.stop(1000);
               }
               else
               {
                  System.out.println("Not enough actions left!");
                  Util.stop(1000);
               }
               break;
            }
            
            case("i"):
            {
               inventory();
               break;
            }
            
            case("m"):
            {
               make();
               break;
            }
            
            case("v"): //TODO: put into own method?
            {
               Util.clear();
               System.out.println(this);
               Util.hold();
               break;
            }
            
            case("h"):
            {
               Util.clear();
               System.out.println("No help for you!");
               Util.hold();
               break;
            }
            
            case("e"):
            {
               breaker = false;
               break;
            }
            
            //TODO: IS A CHEAT CODE REMOVE AFTER
            case("item-p"):
            {
               super.cheat(0);
               System.out.println("You better be a dev...");
               Util.hold();
               break;
            }
            
            case("item-m"):
            {
               super.cheat(1);
               System.out.println("You better be a dev...");
               Util.hold();
               break;
            }
            
            case("item-s"):
            {
               super.cheat(2);
               System.out.println("You better be a dev...");
               Util.hold();
               break;
            }
            
            case("the-flash"):
            {
               actions = 999;
               System.out.println("You better be a dev...");
               Util.hold();
               break;
            }
            
            default:
            {
               System.out.println("Invalid option!");
               Util.stop(1000);
               break;
            }
         }
      }
   }
   
   private void search(Tile tile)
   {
      Item found = tile.search();
      
      if(found != null)
      {
         System.out.println("You found " + found + "!");
         super.getInv().add(found);
      }
      else
         System.out.println("You did not find anything.");
      Util.hold();
   }
   
   private void inventory()
   {
      Util.clear();
      Scanner in = new Scanner(System.in);
      ArrayList<Item> inventory = super.getInv();
      
      String input = "";
      int index = -1;
      boolean loop = true;
      while(loop)
      {
         Util.clear();
         System.out.println("\nInventory:");
         Util.printInv(inventory);
         System.out.println("Select an item number, or (c)ancel");
         input = in.nextLine();
         index = -1;
         
         //TODO improve with try catch statements!
         if(Util.isInt(input) && !input.equals(""))
         {
            index = Integer.parseInt(input);
            if(index <= inventory.size() && index > 0)
            {
               boolean loop2 = true;
               while(loop2)
               {
                  loop2 = false;
                  System.out.println("\nWhat would you like to do with your " + inventory.get(index-1) + "?\nYou may (u)se, (i)nspect or (d)rop the item. You may also (c)ancel.");
                  System.out.print(">");
                  String action = in.nextLine();
                  action.toLowerCase();
               
                  if(action.equals("u")) //Use specified item
                  {
                     inventory.get(index-1).use(this);
                     
                     //TODO: This deletes consumable items. When charming/searching items gets overhauled disable this!
                     if(inventory.get(index-1).identify() > 1)
                        inventory.remove(index-1);
                  }
                  
                  else if(action.equals("u"))
                  {
                     
                  }
                  
                  else if(action.equals("d")) //Drop specified item
                  {
                     System.out.println("You have dropped your " + inventory.get(index-1) + ".");
                     inventory.remove(index-1);
                  }
                  
                  else if(action.equals("c")) //Cancel
                  {
                     //Does nothing... 
                  }
                  
                  else
                  {
                     System.out.println("Invalid option");
                     Util.stop(1000);
                     loop2 = true;
                  }
               }
            }
         }
         else if(input.toLowerCase().equals("c"))
         {
            loop = false;
         }
         else
         {
            System.out.println("Choose a valid number!");
            Util.stop(1000);
         }
      }
   }
   
   private void make() //TODO: add ability to cancel craft in crafting menu
   {
      Util.clear();
      ArrayList<Item> inventory = super.getInv();
      ArrayList<Item> hold = new ArrayList<Item>();
      int[] matIndex = new int[3];
      
      { //Checks if holding 2 and only 2 materials
         int matsHeld = 0;
         for(int i=0; i<inventory.size() && matsHeld<=2; i++)
         {
            Item item = inventory.get(i);
            if(item.identify() == 1)
            {
               if(item.isUsed())
               {
                  matIndex[matsHeld] = i;
                  matsHeld++;
               }
            }
         }
         
         if(matsHeld !=2) //Aborts crafting if 2 materials are not held
         {
            System.out.println("You need to be holding 2 materials to craft!");
            Util.hold();
            return;
         }
      }
      
      int tierTot = 0;
      System.out.println("\nMaterials held:");
      for(int i=0; i<2; i++)
      {
         Item item = inventory.get(matIndex[i]);
         hold.add(item);
         tierTot+= item.getTier()+1; //+1 because dividing 0 = NaN, and NaN is bad.
         System.out.println(item);
      }
            
      int matTier = tierTot / 2;
      int toolTier = 0;
      int toolType = 0;
      
      boolean breaker = true;
      while(breaker)
      {
         System.out.println("\nWhat tool type do you want to make? [1: atk, 2: cft, 3: crm]");
         System.out.print(">");
         
         Scanner in = new Scanner(System.in);
         try
         {
            toolType = in.nextInt();
            if(toolType > 0 && toolType <=3)
               breaker = false;
            else
            {
               System.out.println("Invalid input!");
               Util.stop(1000);
            }
         }
         catch(Exception e) //For the idiots who don't know what an integer is... (or maybe just for who can't type)
         {
            System.out.println("Invalid input!");
            Util.stop(1000);
         }
      }
      
      breaker = true;
      while(breaker)
      {
         System.out.println("\nMaterial Tier = " + matTier);
         System.out.println("What tier do you want to make? [1: low, 2: med, 3: hig]");
         System.out.print(">");
         
         Scanner in = new Scanner(System.in);
         try
         {
            toolTier = in.nextInt();
            if(toolTier > 0 && toolTier <=3)
               breaker = false;
            else
            {
               System.out.println("Invalid input!");
               Util.stop(1000);
            }
         }
         catch(Exception e) //For the idiots who don't know what an integer is... (or maybe just for who can't type)
         {
            System.out.println("Invalid input!");
            Util.stop(1000);
         }
      }
      
      //Making craft check
      //TODO: math.random is bad?
      int diff = (toolTier-matTier)*2 + 2;
      int check = super.getMen() + super.getCft() + ((int)(Math.random()*6+1));     
      System.out.println("Crafting...");
      Util.stop(1000);
      
      System.out.println("Difficulty: " + diff); //TODO: REMOVE THESE LATER
      System.out.println("Craft Check: " + check); 
      //Remove materials for inventory
      inventory.remove(matIndex[0]);
      inventory.remove(matIndex[1]-1);
      
      if(check >= diff)
      {
         System.out.println("Success!");
         Util.stop(1000);
         Item crafted = new Tool(toolType-1,toolTier-1);
         System.out.println("\nYou have created " + crafted + "!");
         inventory.add(crafted);
      }
      else
      {
         System.out.println("Failure.");
         Util.stop(1000);
      }
      super.setHold(0);
      Util.hold();
   }
   
   //Choice during encounter
   //TODO: maybe add vague stat description of enemy
   public int encounter(Character enemy)
   {
      Util.clear();
      System.out.println(super.getName() + "'s Encounter!");
      Util.hold();
      
      Util.clear();
      System.out.println("You see " + enemy.getName() + ".");
      System.out.println("========================================================================================================================");
      System.out.println("What do you want to do? You may attempt to (a)ttack, (c)harm the enemy, or (r)un away.");
      while(true)
      {
         Scanner in = new Scanner(System.in);
         System.out.print(">");
         String input = in.nextLine();
         input = input.toLowerCase(); //Avoid capitalization issues w/ next check.
         if(input.equals("a"))
            return 0;
         else if(input.equals("c"))
            return 1;
         else if(input.equals("r"))
            return 2;
         else
         {
            System.out.println("Invalid option!");
            Util.stop(1000);
         }
      }
   }
   
   //Prints flavor text for biome
   private void printText(int biome)
   {
      //Aww yeah! I love me some flavor text!
      String[] flavorText= 
     {/*Shadow*/"The growing shadow grows, you try to outrun it, but it quickly envelops you.\nYou suddenly feel your energy literally draining by the minute.\nIt doesn't take too much thought to realize this is not a good spot to stay.",
      /*Border*/"You are standing in a wall.\nLike, you are INSIDE the wall. PHASED in there.\nYou have this gut feeling that you aren't supposed to be in here.\nI'm honestly surprised the game hasn't crashed yet.",
      /*Loot  */"You come to a clearing.\nAtop a platform of concrete lies many crates and boxes.\nThere is loot far and wide, but you feel like this place with attract others.\nWhether or not you want to stay here for long is up to you.",
      /*Plains*/"You are in a very grassy, plain-like area.\nA cool breeze runs past you- waving the tall grass and occasional flower that lines the ground.\nIt is a comfortable[SPELL] temperature, but ever so slightly chilly.\nThe elevation is mostly similar, except for a few minor hills dotting the area.\nThe sun is shining perfectly, enough to see everything, but not enough to overwhelm you.\nYou would think this would be a nice area to relax for a bit, assuming this wasn't an area of death.",
      /*Forest*/"Very suddenly, the area turns into forest, casting shade onto most of the area.\nIt is a sharp divide, looking very manmade.\nThe trees are of varying species, but are mostly from a temperate climate.\nThe temperature is cool, although this is mostly because of the treetops filting out most of the sunlight.",
      /*River */"You push out on your raft into the river.\nThe water is slightly turbulent[SPELL] and a few waves spill out onto your ship.\nYou quickly discover it is salt water, as you feel a sharp sting emenating[SPELL] from your cuts.\nThere appear to be a few fish in the area, and you think that, with proper tools, you could manage to catch some.",
      /*Bridge*/"As your approach the river, you see a bridge crossing into the area beyond.\nIt is a rope bridge with wooden planks covering the bottom.\nIt would be fairly easy to cut the bridge with the right tools, rendering it unsuable.",
      /*Desert*/"Almost immediatley you feel a dry wave of heat as you step into what looks like a desert.\nThe entire area consists of sand dunes, varying in height dramatically.\nThe sand uncomftorably[SPELL] heats your feet, even through your shoes.\nThe air is dries out your skin, and you immediately[SPELL] break into a salty sweat, drying you out even more\nI think you get it- it's hot.",
      /*Crag  */"The area clears out into a rocky crag field. The gound is solid, uneven stone, with rocks jutting out of the ground, some extending many meters out.\nIn extreme contrast to the desert, the temperature is freezing, as dark, ominous clouds block out the sun.\nThough, it is still very dry.\nYou suspect it would be snowing, if not for the aparent lack of moisture.\nThe area is quite obviously devoid of life.",
      /*Tower */"Standing tall, in the center of the crag fields is a dark colored stone tower.\nIt appears to be about 5 meters tall, with doors on all four sides. You feel a strange warmth, an energy coming from the top of the tower."};
      System.out.println(flavorText[biome+1]);
   }
   
   //Prints map of surrounding area
   private void printLoc(int x, int y, Tile[][] map)
   {
      
      //TODO: Have different arts for different states!!!
      //String array contains ASCII art for map. Split into layers because you can only print one line at a time.
      /*String[][] mapGraphic =
     {/ *0* /{ANSI_MAG+"\\#\\#\\#\\#\\"+ANSI_RES,ANSI_MAG+"#/#/#/#/#"+ANSI_RES,ANSI_MAG+"\\#\\#\\#\\#\\"+ANSI_RES}, //Shadow
      / *1* /{ANSI_DGRA+"__|___|__"+ANSI_RES,ANSI_DGRA+"_|__|__|_"+ANSI_RES,ANSI_DGRA+"__|___|__"+ANSI_RES}, //Barrier
      / *2* /{ANSI_CYA+"  / | \\  "+ANSI_RES,ANSI_CYA+" ---X--- "+ANSI_RES,ANSI_CYA+"  \\ | /  "+ANSI_RES}, //Spawn
      / *3* /{ANSI_GRE+" ``````` "+ANSI_RES,ANSI_GRE+" ``````` "+ANSI_RES,ANSI_GRE+" ``````` "+ANSI_RES}, //Plains
      / *4* /{ANSI_LGRE+"/ \\/ \\/ \\"+ANSI_RES,ANSI_LGRE+"\\ /\\ /\\ /"+ANSI_RES,ANSI_DGRA+" |  |  | "+ANSI_RES}, //Forest
      / *5* /{ANSI_LBLU+" ~ ~ ~ ~ "+ANSI_RES,ANSI_LBLU+" ~ ~ ~ ~ "+ANSI_RES,ANSI_LBLU+" ~ ~ ~ ~ "+ANSI_RES}, //Water
      / *6* /{" ~  |  ~ "+ANSI_RES,"----+----"+ANSI_RES," ~  |  ~ "+ANSI_RES}, //Bridge TODO: REDO LATER!!!
      / *7* /{ANSI_LYEL+" / / / / "+ANSI_RES,ANSI_LYEL+" \\ \\ \\ \\ "+ANSI_RES,ANSI_LYEL+" / / / / "+ANSI_RES}, //Desert
      / *8* /{ANSI_DGRA+" ^ ^ ^ ^ "+ANSI_RES,ANSI_DGRA+" ^ ^ ^ ^ "+ANSI_RES,ANSI_DGRA+" ^ ^ ^ ^ "+ANSI_RES}, //Crag
      / *9* /{ANSI_LGRA+" |_|_|_| "+ANSI_RES,ANSI_LGRA+" |  _  | "+ANSI_RES,ANSI_LGRA+" | | | | "+ANSI_RES}}; //Tower
      */
      
      String[][] mapGraphic =
     {/*0*/{"\\#\\#\\#\\#\\","#/#/#/#/#","\\#\\#\\#\\#\\"}, //Shadow
      /*1*/{"__|___|__","_|__|__|_","__|___|__"}, //Barrier
      /*2*/{"  / | \\  "," ---X--- ","  \\ | /  "}, //Spawn
      /*3*/{" ``````` "," ``````` "," ``````` "}, //Plains
      /*4*/{"/ \\/ \\/ \\","\\ /\\ /\\ /"," |  |  | "}, //Forest
      /*5*/{" ~ ~ ~ ~ "," ~ ~ ~ ~ "," ~ ~ ~ ~ "}, //Water
      /*6*/{" ~  |  ~ ","---------"," ~  |  ~ "}, //Bridge TODO: REDO LATER!!!
      /*7*/{" / / / / "," \\ \\ \\ \\ "," / / / / "}, //Desert
      /*8*/{" ^ ^ ^ ^ "," ^ ^ ^ ^ "," ^ ^ ^ ^ "}, //Crag
      /*9*/{" |_|_|_| "," |  _  | "," | | | | "}}; //Tower

      
      String[][] g = new String[9][3];
      
      //Sets ASCII art layers to 9 string arrays for printing
      {
         int count = 0;
         for(int dY=-1; dY<=1; dY++)
         {
            for(int dX=-1; dX<=1; dX++)
            {
               try //If game tries to draw something out of bounds, a wall will be drawn instead
               {
                  int biNumb = map[y+dY][x+dX].getBiome(); //Biome number
                  g[count] = new String[] {mapGraphic[biNumb+1][0],mapGraphic[biNumb+1][1],mapGraphic[biNumb+1][2]};
                  count++;
               }
               catch(ArrayIndexOutOfBoundsException e)
               {
                  g[count] = new String[] {mapGraphic[1][0],mapGraphic[1][1],mapGraphic[1][2]};
                  count++;
               }
            }
         }
      }
      
      
      int count = 0;
      for(int dY=y-1; dY<=y+1; dY++)
      {
         for(int dX=x-1; dX<=x+1; dX++)
         {
            if(super.getGame().isOccupied(dX,dY) > 0)
            {
               for(int i=0; i<2; i++)
               {
                  char[] figure = {'o','|'};
                  String half1 = g[count][i].substring(0,4);
                  String half2 = g[count][i].substring(5,9);
                  g[count][i] = half1 + figure[i] + half2;
               }
            }
            count++;
         }
      }
      
      //Prints map frame with ASCII art inside inside.
      String b = ANSI_BLA;
      String c = ANSI_RES;
      
      /*
      System.out.println(b+"+---------+---------+---------+"+
                       "\n|"+c+g[0][0]+b+"|"+c+g[1][0]+b+"|"+c+g[2][0]+b+"|"+
                       "\n|"+c+g[0][1]+b+"|"+c+g[1][1]+b+"|"+c+g[2][1]+b+"|"+
                       "\n|"+c+g[0][2]+b+"|"+c+g[1][2]+b+"|"+c+g[2][2]+b+"|"+
                       "\n+---------+---------+---------+"+
                       "\n|"+c+g[3][0]+b+"|"+c+g[4][0]+b+"|"+c+g[5][0]+b+"|"+
                       "\n|"+c+g[3][1]+b+"|"+c+g[4][1]+b+"|"+c+g[5][1]+b+"|"+
                       "\n|"+c+g[3][2]+b+"|"+c+g[4][2]+b+"|"+c+g[5][2]+b+"|"+
                       "\n+---------+---------+---------+"+
                       "\n|"+c+g[6][0]+b+"|"+c+g[7][0]+b+"|"+c+g[8][0]+b+"|"+
                       "\n|"+c+g[6][1]+b+"|"+c+g[7][1]+b+"|"+c+g[8][1]+b+"|"+
                       "\n|"+c+g[6][2]+b+"|"+c+g[7][2]+b+"|"+c+g[8][2]+b+"|"+
                       "\n+---------+---------+---------+"+c);
      */
      System.out.println("+---------+---------+---------+"+
                       "\n|"+g[0][0]+"|"+g[1][0]+"|"+g[2][0]+"|"+
                       "\n|"+g[0][1]+"|"+g[1][1]+"|"+g[2][1]+"|"+
                       "\n|"+g[0][2]+"|"+g[1][2]+"|"+g[2][2]+"|"+
                       "\n+---------+---------+---------+"+
                       "\n|"+g[3][0]+"|"+g[4][0]+"|"+g[5][0]+"|"+
                       "\n|"+g[3][1]+"|"+g[4][1]+"|"+g[5][1]+"|"+
                       "\n|"+g[3][2]+"|"+g[4][2]+"|"+g[5][2]+"|"+
                       "\n+---------+---------+---------+"+
                       "\n|"+g[6][0]+"|"+g[7][0]+"|"+g[8][0]+"|"+
                       "\n|"+g[6][1]+"|"+g[7][1]+"|"+g[8][1]+"|"+
                       "\n|"+g[6][2]+"|"+g[7][2]+"|"+g[8][2]+"|"+
                       "\n+---------+---------+---------+");
   }
   
   //Movement action
   private int go(int actions, Tile[][] map, Scanner in)
   {
      String input;
      //TODO: add support for non map boundary walls
      boolean breaker = true;
      while(breaker)
      {
         System.out.println("Go in which direction? [(n)orth; (s)outh; (e)ast; (w)est; (c)ancel]");
         System.out.print(">");
         input = in.nextLine();
         input.toLowerCase();
         
         breaker = false;
         switch(input)
         {
            case("n"): //Move north
            {
               if(super.getYLoc() == 0)
               {
                  System.out.println("You can't go into a wall!\n");
                  Util.stop(1000);
                  breaker = true;
               }
               else
               {
                  super.move(0);
                  actions--;
               }
               break;
            }
            case("w"): //Move west
            {
               if(super.getXLoc() == 0)
               {
                  System.out.println("You can't go into a wall!\n");
                  Util.stop(1000);
                  breaker = true;
               }
               else
               {
                  super.move(1);
                  actions--;
               }
               break;
            }
            case("s"): //Move south
            {
               if(super.getYLoc() == map.length-1)
               {
                  System.out.println("You can't go into a wall!\n");
                  Util.stop(1000);
                  breaker = true;
               }
               else
               {
                  super.move(2);
                  actions--;
               }
               break;
            }
            case("e"): //Move east
            {
               if(super.getXLoc() == map.length-1)
               {
                  System.out.println("You can't go into a wall!\n");
                  Util.stop(1000);
                  breaker = true;
               }
               else
               {
                  super.move(3);
                  actions--;
               }
               break;
            }
            case("c"): //Cancel move (no action points expended)
               break;
            default:
               System.out.println("Invalid direction!"); breaker = true; break;
         }
      }
      return actions;
   }
}




/* TEMP

   private void make()
   {
      ArrayList<Item> inventory = super.getInv();
      ArrayList<Item> hold = new ArrayList<Item>();
      System.out.println("\nMaterials held:");
      
      int tierTot = 0;
      for(int i=0; i<inventory.size(); i++)
      {
         Item item = inventory.get(i);
         if(item.identify() == 1)
         {
            if(item.isUsed())
            {
               hold.add(item);
               inventory.remove(i);
               System.out.println(item);
               tierTot+= item.getTier()+1; //+1 because dividing 0 = NaN, and NaN is bad.
               i--;
            }
         }
      }
      
      if(hold.size() != 2)
      {
         if(hold.size() == 0)
            System.out.println("You aren't holding anything!");
         System.out.println("You need to be holding 2 materials to craft!");
         Util.stop(2500);
      }
      else
      {
         int matTier = tierTot / 2;
         int toolTier = 0;
         int toolType = 0;
         
         boolean breaker = true;
         while(breaker)
         {
            System.out.println("\nWhat tool type do you want to make? [1: atk, 2: cft, 3: crm]");
            System.out.print(">");
            
            Scanner in = new Scanner(System.in);
            try
            {
               toolType = in.nextInt();
               if(toolType > 0 && toolType <=3)
                  breaker = false;
               else
               {
                  System.out.println("Invalid input!");
                  Util.stop(1000);
               }
            }
            catch(Exception e) //For the idiots who don't know what an integer is... (or maybe just for who can't type)
            {
               System.out.println("Invalid input!");
               Util.stop(1000);
            }
         }
         
         breaker = true;
         while(breaker)
         {
            System.out.println("\nMaterial Tier = " + matTier);
            System.out.println("What tier do you want to make? [1: low, 2: med, 3: hig]");
            System.out.print(">");
            
            Scanner in = new Scanner(System.in);
            try
            {
               toolTier = in.nextInt();
               if(toolTier > 0 && toolTier <=3)
                  breaker = false;
               else
               {
                  System.out.println("Invalid input!");
                  Util.stop(1000);
               }
            }
            catch(Exception e) //For the idiots who don't know what an integer is... (or maybe just for who can't type)
            {
               System.out.println("Invalid input!");
               Util.stop(1000);
            }
         }
         
         //Making craft check
         //TODO: math.random is bad?
         int diff = (toolTier-matTier)*2 + 4;
         int check = super.getMen() + super.getCft() + ((int)(Math.random()*6+1));
         System.out.println("Difficulty: " + diff); //TODO: REMOVE THESE LATER
         System.out.println("Craft Check: " + check);      
         System.out.println("Crafting...");
         Util.stop(1000);
         
         if(check >= diff)
         {
            System.out.println("Success!");
            Util.stop(1000);
            Item crafted = new Tool(toolType-1,toolTier-1);
            System.out.println("\nYou have created " + crafted + "!");
            inventory.add(crafted);
         }
         else
         {
            System.out.println("Failure.");
            Util.stop(1000);
         }
         super.setHold(0);
         Util.hold();
      }
   }
   */
   
   
