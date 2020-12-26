package system00.theheroic;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import system00.theheroic.proxy.CommonProxy;
import system00.theheroic.tabs.*;
import system00.theheroic.util.Reference;
import system00.theheroic.util.handlers.NetworkHandler;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class Main {
	
	@Instance(Reference.MODID)
	public static Main instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event) {
		NetworkHandler.INSTANCE.name();
        proxy.preInit(event);
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) {
		proxy.init(event);
	}
	
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
	
	public static final CreativeTabs heroicmisctab = new HeroicMiscTab("heroicmisctab");
	public static final CreativeTabs heroicweaponstabC = new HeroicWeaponsTabCclass("heroicweaponstabcclass");
	public static final CreativeTabs heroicweaponstabB = new HeroicWeaponsTabBclass("heroicweaponstabbclass");
	public static final CreativeTabs heroicweaponstabA = new HeroicWeaponsTabAclass("heroicweaponstabaclass");
	public static final CreativeTabs heroicweaponstabS = new HeroicWeaponsTabSclass("heroicweaponstabsclass");
	public static final CreativeTabs heroicfoodtab = new HeroicFoodTab("heroicfoodtab");
	
}
