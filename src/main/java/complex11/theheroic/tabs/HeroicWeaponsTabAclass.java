package complex11.theheroic.tabs;

import complex11.theheroic.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class HeroicWeaponsTabAclass extends CreativeTabs {

	public HeroicWeaponsTabAclass(String label) {
		super("heroicweaponstabaclass");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.FLASH_BLADE);
	}
}
