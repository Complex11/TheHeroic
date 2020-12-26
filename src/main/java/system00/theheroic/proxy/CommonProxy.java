package system00.theheroic.proxy;

import system00.theheroic.init.ModEntities;
import system00.theheroic.util.handlers.RegistryHandler;
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
