package system00.theheroic.items;

import net.minecraft.item.Item;
import system00.theheroic.Main;
import system00.theheroic.init.ModItems;
import system00.theheroic.util.interfaces.IHasModel;

public class ItemBase extends Item implements IHasModel {

	public ItemBase(String name) {
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.heroicmisctab);
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}

