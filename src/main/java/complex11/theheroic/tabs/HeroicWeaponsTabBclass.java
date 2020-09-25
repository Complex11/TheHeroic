package complex11.theheroic.tabs;

import complex11.theheroic.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class HeroicWeaponsTabBclass extends CreativeTabs {

	public HeroicWeaponsTabBclass(String label) {
		super("heroicweaponstabbclass");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.FLAMEWAKER);
	}
}
