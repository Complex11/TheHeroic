package system00.theheroic.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import system00.theheroic.init.ModItems;

public class HeroicMiscTab extends CreativeTabs {

	public HeroicMiscTab(String label) {
		super("heroicmisctab");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.ESSENCE);
	}
	
}
