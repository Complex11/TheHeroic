package system00.theheroic.init;

import system00.theheroic.potions.Potion4D;
import system00.theheroic.potions.PotionBleed;
import system00.theheroic.potions.PotionDeathMark;
import system00.theheroic.potions.PotionFlight;
import system00.theheroic.potions.PotionHEAVEN;
import system00.theheroic.potions.PotionSmokescreen;
import system00.theheroic.potions.PotionSoulRemoval;
import system00.theheroic.potions.PotionTargetOfThunder;
import system00.theheroic.potions.PotionTimeStop;
import system00.theheroic.util.Reference;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder(Reference.MODID)
@Mod.EventBusSubscriber
public class ModPotions {
	
	public static final Potion DEATHMARK_EFFECT = new PotionDeathMark(true, 0);
	public static final Potion SOUL_REMOVAL_EFFECT = new PotionSoulRemoval(true, 124124124);
	public static final Potion TIME_STOP_EFFECT = new PotionTimeStop(false, 1234924);
	public static final Potion FOUR_DIMENSION_EFFECT = new Potion4D(false, 111333);
	public static final Potion BLEED_EFFECT = new PotionBleed(true, 16712192);
	public static final Potion HEAVEN_EFFECT = new PotionHEAVEN(false, 0101011);
	public static final Potion SMOKESCREEN_EFFECT = new PotionSmokescreen(false, 072072072);
	public static final Potion FLIGHT = new PotionFlight(false, 394222010);
	public static final Potion THUNDER = new PotionTargetOfThunder(true, 11022044);
	
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
		registerPotion(registry, "time_stopped", new PotionTimeStop(false, 1234924));
		registerPotion(registry, "4_D", new Potion4D(false, 111333));
		registerPotion(registry, "bleed", new PotionBleed(true, 16712192));
		registerPotion(registry, "heaven", new PotionHEAVEN(false, 0101011));
		registerPotion(registry, "smokescreen", new PotionSmokescreen(false, 072072072));
		registerPotion(registry, "flight", new PotionFlight(false, 394222010));
		registerPotion(registry, "thunder", new PotionTargetOfThunder(true, 11022044));
	}
}
