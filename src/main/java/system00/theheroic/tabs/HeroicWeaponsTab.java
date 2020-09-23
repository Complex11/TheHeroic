package system00.theheroic.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import system00.theheroic.init.ModItems;

public class HeroicWeaponsTab extends CreativeTabs {

	public HeroicWeaponsTab(String label) {
		super("heroicweaponstab");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.FLASH_BLADE);
	}
}
