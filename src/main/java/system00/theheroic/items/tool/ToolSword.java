package system00.theheroic.items.tool;

import net.minecraft.item.ItemSword;
import system00.theheroic.Main;
import system00.theheroic.init.ModItems;
import system00.theheroic.util.interfaces.IHasModel;

public class ToolSword extends ItemSword implements IHasModel {

	public ToolSword(String name, ToolMaterial material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.heroicweaponstab);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}