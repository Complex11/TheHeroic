package complex11.theheroic.event;

import complex11.theheroic.init.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class PotionEvent {

	@SubscribeEvent
	public static void onLivingUpdate(LivingUpdateEvent event) {
		if (event.getEntity() instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase) event.getEntity();
			if (entity.isPotionActive(ModPotions.SOUL_REMOVAL_EFFECT)) {
				entity.addPotionEffect(new PotionEffect(ModPotions.SOUL_REMOVAL_EFFECT, 100, 1, true, false));
				entity.setHealth((float) (0.95 * entity.getHealth()));
				if (entity.getHealth() < 2) {
					entity.removeActivePotionEffect(ModPotions.SOUL_REMOVAL_EFFECT);
				}
			}
		}
	}
}
