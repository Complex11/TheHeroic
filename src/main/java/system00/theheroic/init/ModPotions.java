package system00.theheroic.init;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;
import system00.theheroic.potions.PotionDeathMark;
import system00.theheroic.potions.PotionSoulRemoval;
import system00.theheroic.util.Reference;

@ObjectHolder(Reference.MODID)
@Mod.EventBusSubscriber
public class ModPotions {
	
	public static final Potion DEATHMARK_EFFECT = new PotionDeathMark(true, 0);
	public static final Potion SOUL_REMOVAL_EFFECT = new PotionSoulRemoval(true, 124124124);
	
	
	public static void registerPotion(IForgeRegistry<Potion> registry, String name, Potion potion){
		potion.setRegistryName(Reference.MODID, name);
		potion.setPotionName("potion." + potion.getRegistryName().toString());
		registry.register(potion);
	}
	
	@SubscribeEvent
	public static void register(RegistryEvent.Register<Potion> event){
		IForgeRegistry<Potion> registry = event.getRegistry();

		registerPotion(registry, "mark_of_death", new PotionDeathMark(true, 0));
		registerPotion(registry, "soul_removal", new PotionSoulRemoval(true, 124124124));
	}
}
