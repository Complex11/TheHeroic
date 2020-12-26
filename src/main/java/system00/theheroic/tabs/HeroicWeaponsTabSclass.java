package system00.theheroic.tabs;

import system00.theheroic.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class HeroicWeaponsTabSclass extends CreativeTabs {

	public HeroicWeaponsTabSclass(String label) {
		super("heroicweaponstabsclass");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.SCHRODINGERS_GIFT);
	}
}
