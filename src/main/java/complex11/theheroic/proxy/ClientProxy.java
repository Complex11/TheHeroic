package complex11.theheroic.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
	
	@Override
    public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
    }
	
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}
	
	@Override
	public void loadShader(EntityPlayer player, ResourceLocation shader) {
		if (Minecraft.getMinecraft().player == player && !Minecraft.getMinecraft().entityRenderer.isShaderActive())
			Minecraft.getMinecraft().entityRenderer.loadShader(shader);
	}
}
