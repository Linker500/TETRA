//TODO: maybe correlate likeliness to die with losing stat?

import java.util.*;

public class Encounter
{
   /* 0 = Attack
    * 1 = Charm
    * 2 = Run
    */
   
   static ArrayList<String> encSummary; //TODO: perhaps change game class over to new summary system?
   
   public static void start(Character a, Character b, Game game)
   {
      encSummary = new ArrayList<String>();
      
      int aChoice;
      int bChoice;
      
      aChoice = a.encounter(b);
      bChoice = b.encounter(a);
      
      encSummary.add(a.getName() + " encounters " + b.getName() + ".");
      
      //Player A attacks
      if(aChoice == 0)
      {
         if(bChoice == 0)
            duel(a,b); //A attacks, B attacks.
         else if(bChoice == 1)
            charmOrDie(b,a); //A attacks, B charms.
         else if(bChoice == 2)
            chase(b,a); //A attacks, B runs.
      }
      
      //Player A charms
      else if(aChoice == 1)
      {
         if(bChoice == 0)
            charmOrDie(a,b); //A charms B attacks.
         else if(bChoice == 1)
            charmOff(a,b); //A charms, B charms
         else if(bChoice == 2)
            charm(a,b); //A charms B runs.
      }
      
      //Player A runs
      else if(aChoice == 2)
      {
         if(bChoice == 0)
            chase(a,b); //A runs, B attacks.
         else if(bChoice == 1)
            charm(b,a); //A runs, B charms.
         else if(bChoice == 2)
            flee(a,b); //A runs, B runs.
      }
      
      game.addSummary(encSummary);
      a.pushNewSummary();
      b.pushNewSummary();
   }
   
   //TODO: add random encounter flavor text!!!
   
   
   /*=====ENCOUNTERS=====*/
   
   /* Duel:
    * Both sides choose to attack.
    * Comparison is physical on both sides
    * Loss is lethal, and gives chance of death.
    */
   private static void duel(Character a, Character b)
   {
      encSummary.add("They both engage, starting a duel.");
      a.addNewSummary("You and " + b.getName() + " engage, starting a duel.");
      b.addNewSummary("You and " + a.getName() + " engage, starting a duel.");
      
      int aScore = a.getPhy() + a.getAtk() + (int)(Math.random()*5+1); //TODO: find something better than math.random!!!
      int bScore = b.getPhy() + b.getAtk() + (int)(Math.random()*5+1);
      
      //Player A wins
      if(aScore > bScore)
      {
         encSummary.add(a.getName() + " slaughters " + b.getName() + "!");
         a.addNewSummary("You slaughter " + b.getName() + "!");
         b.addNewSummary(a.getName() + " slaughters you!");
         atkLose(b,a);
      }
      //Player B wins
      else if(bScore > aScore)
      {
         b.addNewSummary("You slaughter " + a.getName() + "!");
         a.addNewSummary(b.getName() + " slaughters you!");
         encSummary.add(b.getName() + " slaughters " + a.getName() + "!");
         atkLose(a,b);
      }
      //Draw
      else
      {
         encSummary.add("Both fighters tie.");
         a.addNewSummary("You both tie.");
         b.addNewSummary("You both tie.");
         
         atkLose(a,b);
         atkLose(b,a);
      }
   }
   
   /* Chase:
    * One side runs, while the other tries to hunt and kill them. The runner has an advantage in escaping.
    * Comparison is physical on both sides, but the runner gets a +2 advantage.
    * Loss is lethal only for runner. On attacker loss nothing happens, as the victim just escapes. 
    * Ties end in the runner's favor.
    */
   private static void chase(Character run, Character atk)
   {
      encSummary.add(run.getName() + " runs for their life while " + atk.getName() + " hunts them down!");
      run.addNewSummary("You run for your life while " + atk.getName() + " hunts you down!.");
      atk.addNewSummary("You chase " + run.getName() + " while they run for their life!.");
      
      int runScore = run.getPhy() + (int)(Math.random()*5+1)+2; //TODO: find something better than math.random!!!
      int atkScore = atk.getPhy() + atk.getAtk() + (int)(Math.random()*5+1);            
      //Runner player wins
      if(runScore >= atkScore)
      {
         encSummary.add(run.getName() + " escapes " + atk.getName() + "'s clutches.");
         run.addNewSummary("You escape " + atk.getName() + "'s clutches."); 
         atk.addNewSummary(run.getName() + " escapes your clutches.");
      }
      //Attacker player wins
      else
      {
         encSummary.add(atk.getName() + " slaughters " + run.getName());
         run.addNewSummary(atk.getName() + " slaughters you!"); 
         atk.addNewSummary("You slaughter " + run.getName() + "!");
         atkLose(run,atk);
      }
   }
   
   
   /* Flee:
    * Both players run away like little girls and nothing happens.
    * Freaking cowards.
    */
   private static void flee(Character a, Character b)
   {
      encSummary.add(a.getName() + " and " + b.getName() + " see each other and \"bravely\" run away.");
      encSummary.add("Cowards.");
      a.addNewSummary("You see " + b.getName() + " and both \"bravely\" run away.");
      a.addNewSummary("Cowards.");
      b.addNewSummary("You see " + a.getName() + " and both \"bravely\" run away.");
      b.addNewSummary("Cowards.");
   }
   
   
   /* Charm Or Die:
    * One player attempts to attack, while the other attempts to charm them.
    * Comparison is the attacker's intelligence vs the charmer's social.
    * If the charmer wins, they successfully deter the attacker.
    * If the attacker wins, they butcher the charmer with no remorse.
    * Attackers win ties
    */
   private static void charmOrDie(Character crm, Character atk)
   {
      encSummary.add(atk.getName() + " attacks " + crm.getName() + " but they attempt to charm in defense!");
      crm.addNewSummary("You attempt charm " + atk.getName() + " but they attack you in defense!");
      atk.addNewSummary("You attack " + crm.getName() + " but they attempt to charm in defense!");
      int crmScore = crm.getSoc() + crm.getCrm() + (int)(Math.random()*5+1); //TODO: find something better than math.random!!!
      int atkScore = atk.getPhy() + atk.getAtk() + (int)(Math.random()*5+1);
      
      //Charmer player wins
      if(crmScore > atkScore)
      {
         encSummary.add(crm.getName() + " charms " + atk.getName() + ".");
         crm.addNewSummary("You charm " + atk.getName() + ".");
         atk.addNewSummary("You are charmed by " + crm.getName() + ".");
         crmLose(atk,crm);
      }
      //Attacker player wins
      else
      {
         encSummary.add(atk.getName() + " slaughters " + crm.getName() + ".");
         crm.addNewSummary(atk.getName() + " slaughters you!");
         atk.addNewSummary("You slaughter " + crm.getName());
         atkLose(crm,atk);
      }
   }
   
   /* Charm:
    * One player attempts to charm a fleeing player
    * Upon charmer's success, they steal items from the players inventory
    * TODO: redo inventory stealing mechanic so you can see the players inventory
    */
   private static void charm(Character crm, Character run)
   {
      encSummary.add(crm.getName() + " attempts to charm " + run.getName() + " while they flee!");
      crm.addNewSummary("You attempt to charm " + run.getName() + " but they attempt to flee!");
      run.addNewSummary("You flee, but " + crm.getName() + " attempts to charm you!");
      int chaScore = crm.getSoc() + crm.getCrm() + (int)(Math.random()*5+1); //TODO: find something better than math.random!!!
      int runScore = run.getSoc() + (int)(Math.random()*5+1)+2;
            
      if(chaScore > runScore) //If Charmer wins
         crmLose(run,crm);
      else //If Runner wins
      {
         encSummary.add(run.getName() + " escaped " + crm.getName() + "!");
         crm.addNewSummary(run.getName() + " has escaped!");
         run.addNewSummary("You escape " + crm.getName() + "!");
      }
   }
   
   
   /* Charm Off:
    * Both players attempt to out charm each other.
    * The victor gets all of the losers items.
    */
   private static void charmOff(Character a, Character b)
   {
      encSummary.add(a.getName() + " and " + b.getName() + " both attempt to charm each other!");
      a.addNewSummary("You and " + b.getName() + " both attempt to charm each other!");
      b.addNewSummary("You and " + a.getName() + " both attempt to charm each other!");
      int aScore = a.getSoc() + a.getCrm() + (int)(Math.random()*5+1); //TODO: find something better than math.random!!!
      int bScore = a.getSoc() + a.getCrm() + (int)(Math.random()*5+1);      
      if(aScore > bScore) //If Player A wins
         crmLose(b,a);
      else if(bScore > aScore) //If Player B wins
         crmLose(a,b);
      else //Tie
      {
         a.addNewSummary("Neither of you succeed in charming the other.");
         b.addNewSummary("Neither of you succeed in charming the other.");
         encSummary.add("Neither players succeed in charming the other.");
      }
      
   }
   
   /* Attack Loss
    * The loser is subjected to a 50/50 shot death saving throw
    * If they succeed, their physical stat decreases by 1.
    * If the fail, they die.
    */
   private static void atkLose(Character l, Character w)
   {
      int chance = (int)(Math.random()*100+1);
      if(chance > 50)
      {
         encSummary.add(l.getName() + " survives their wounds, but is weakened.");
         l.addNewSummary("You survived your wounds, but are weakened.");
         w.addNewSummary(l.getName() + " survives their wounds, but is weakened.");
         
         l.setPhy(l.getPhy()-1);
      }
      else
      {
         encSummary.add(l.getName() + " dies from their wounds!");
         l.addNewSummary("You die from your wounds!");
         w.addNewSummary(l.getName() + " dies from their wounds!");
         l.setLife(false);
      }
   }
   
   /* Charm Loss
    *
    */
   
   private static void crmLose(Character l, Character w)
   {
      ArrayList<Item> loseInv = l.getInv();
      
      if(loseInv.size() == 0)
      {
         encSummary.add(l.getName() + " is compelled by " + w.getName() + ", but had nothing to give them.");
         l.addNewSummary("You are compelled by " + w.getName() + ", but had nothing to give them.");
         w.addNewSummary(l.getName() + " is compelled by you, but had nothing to give.");
      }
      else
      {
         ArrayList<Item> winInv = w.getInv();
         int randIndex = (int)(Math.random()*loseInv.size());  //TODO: math.random sucks??
         Item given = loseInv.get(randIndex);
         if(given.isUsed())
            given.use(l);
         
         loseInv.remove(randIndex);
         winInv.add(given);
         
         encSummary.add(l.getName() + " is compelled by " + w.getName() + ", and gave them their " + given + "!");
         l.addNewSummary("You are compelled by " + w.getName() + ", and gave them their " + given + "!");
         w.addNewSummary(l.getName() + " is compelled by you, and gave you their " + given + "!");
         //TODO: Select an item form their inventory istead of random?
      }
   }
   
}