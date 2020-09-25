package complex11.theheroic.items;

import complex11.theheroic.Main;
import complex11.theheroic.init.ModItems;
import complex11.theheroic.util.interfaces.IHasModel;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBase extends Item implements IHasModel {

	public ItemBase(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.heroicmisctab);
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}

