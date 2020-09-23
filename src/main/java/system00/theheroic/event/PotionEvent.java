package system00.theheroic.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import system00.theheroic.init.ModPotions;

public class PotionEvent {

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event) {
		if (event.getEntity() instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase) event.getEntity();
			if (entity.isPotionActive(ModPotions.SOUL_REMOVAL_EFFECT)) {
				entity.addPotionEffect(new PotionEffect(ModPotions.SOUL_REMOVAL_EFFECT, 100, 1, true, false));
				entity.setHealth((float) (0.95 * entity.getHealth()));
				if (entity.getHealth() < 2) {
					entity.attackEntityFrom(DamageSource.OUT_OF_WORLD, 1000);
					entity.onDeath(DamageSource.OUT_OF_WORLD);
				}
			}
		}
	}
}
