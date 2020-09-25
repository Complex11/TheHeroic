package complex11.theheroic.tabs;

import complex11.theheroic.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class HeroicWeaponsTabSclass extends CreativeTabs {

	public HeroicWeaponsTabSclass(String label) {
		super("heroicweaponstabsclass");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.REALITY_BLADE);
	}
}
