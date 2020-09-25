package complex11.theheroic.proxy;

import complex11.theheroic.init.ModEntities;
import complex11.theheroic.init.ModNetworking;
import complex11.theheroic.util.handlers.RegistryHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy {

	public void registerItemRenderer(Item item, int meta, String id) {}
	
	public void preInit(FMLPreInitializationEvent event) {
		ModNetworking.registerSimpleNetworking();
		ModEntities.registerEntities();
		RegistryHandler.preInitRegistries();
	}
	
	public void init(FMLInitializationEvent event) {
		RegistryHandler.initRegistries();
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		RegistryHandler.postInitRegistries();
	}
	
    public EntityPlayer getPlayerEntityFromContext(MessageContext ctx) {
        return ctx.getServerHandler().player;
    }
    
    public void loadShader(EntityPlayer player, ResourceLocation shader){}
}
