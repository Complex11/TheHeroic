package system00.theheroic.items;

import system00.theheroic.Main;
import system00.theheroic.init.ModItems;
import system00.theheroic.util.interfaces.IHasModel;
import net.minecraft.item.ItemFood;


public class FoodBase extends ItemFood implements IHasModel {

	public FoodBase(String name, int amount, float saturation, boolean isAnimalFood) {
		super(amount, saturation, isAnimalFood);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.heroicfoodtab);
		ModItems.ITEMS.add(this);
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
