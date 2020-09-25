package complex11.theheroic.potions;

import java.util.Random;

import complex11.theheroic.init.ModPotions;
import complex11.theheroic.util.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class PotionBleed extends PotionBase {

	public PotionBleed(boolean isBadEffect, int liquidColour){
		super(isBadEffect, liquidColour, new ResourceLocation(Reference.MODID, "textures/gui/potion_icon_bleed.png"));
		this.setPotionName("effect." + Reference.MODID + ":bleed");
	}
	
	public static void performEffectConsistent(EntityLivingBase entity, int strength) {
		if (entity.isPotionActive(ModPotions.BLEED_EFFECT)) {
			Random rand = new Random();
			if (rand.nextInt(200) % 20 == 0 || rand.nextInt(50) % 7 == 0) {
				entity.attackEntityFrom(DamageSource.MAGIC, 1);
			}
		}
	}
	
	@SubscribeEvent
	public static void onLivingUpdateEvent(LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (entity.isPotionActive(ModPotions.BLEED_EFFECT)) {
			performEffectConsistent(entity, entity.getActivePotionEffect(ModPotions.BLEED_EFFECT).getAmplifier());
		}
	}
}
