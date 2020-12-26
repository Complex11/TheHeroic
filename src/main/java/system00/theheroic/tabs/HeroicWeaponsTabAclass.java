package system00.theheroic.tabs;

import system00.theheroic.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class HeroicWeaponsTabAclass extends CreativeTabs {

	public HeroicWeaponsTabAclass(String label) {
		super("heroicweaponstabaclass");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.FLASHY_SLASH);
	}
}
