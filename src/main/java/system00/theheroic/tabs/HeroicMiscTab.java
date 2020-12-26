package system00.theheroic.tabs;

import system00.theheroic.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class HeroicMiscTab extends CreativeTabs {

	public HeroicMiscTab(String label) {
		super("heroicmisctab");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.ESSENCE);
	}
	
}
