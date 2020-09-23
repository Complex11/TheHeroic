package system00.theheroic;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import system00.theheroic.init.ModNetworking;
import system00.theheroic.proxy.CommonProxy;
import system00.theheroic.tabs.HeroicMiscTab;
import system00.theheroic.tabs.HeroicWeaponsTab;
import system00.theheroic.util.Reference;
import system00.theheroic.util.handlers.RegistryHandler;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class Main {
	
	@Instance(Reference.MODID)
	public static Main instance;
	
	public static final CreativeTabs heroicmisctab = new HeroicMiscTab("heroicmisctab");
	public static final CreativeTabs heroicweaponstab = new HeroicWeaponsTab("heroicweaponstab");
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		ModNetworking.registerSimpleNetworking();
		RegistryHandler.preInitRegistries();
        proxy.preInit(event);
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) {
		proxy.init(event);
	}
	
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event) {
		RegistryHandler.postInitRegistries();
	}
	
}
