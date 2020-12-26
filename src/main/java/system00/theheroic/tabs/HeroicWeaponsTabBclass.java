package system00.theheroic.tabs;

import system00.theheroic.init.ModItems;
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
