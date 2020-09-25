package complex11.theheroic.tabs;

import complex11.theheroic.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class HeroicWeaponsTabCclass extends CreativeTabs {

	public HeroicWeaponsTabCclass(String label) {
		super("heroicweaponstabcclass");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.NATURE_SLAYER);
	}
}
