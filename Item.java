/* Item Interface:
 * Items are objects that go into the inventory arrayList.
 * They only share one method- identify. This identifies what type of item it is, returning an int.
 * 
 * They are as follows:
 * 0: Tool
 * 1: Material
 * 2: Drug
 * 3: LootCrate
 */
public interface Item
{
   public int identify();
   public int getTier();
   public boolean isUsed();
   public void use(Character user);
}