package complex11.theheroic.items.weapons.Sclass;

import complex11.theheroic.Main;
import complex11.theheroic.items.ItemBase;
import complex11.theheroic.util.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class WrathOfOldGods extends ItemBase {

	public WrathOfOldGods(String name) {
		super(name);
		setCreativeTab(Main.heroicweaponstabS);
	}
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/sclass/wrath_of_old_gods", "inventory"));
	}
}
