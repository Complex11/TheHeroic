package system00.theheroic.tabs;

import system00.theheroic.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class HeroicFoodTab extends CreativeTabs {

	public HeroicFoodTab(String label) {
		super("heroicfoodtab");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.GUMMY_HEART);
	}
	
}
