package complex11.theheroic.event;

import complex11.theheroic.items.weapons.Sclass.RealityBlade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class WeaponEvent {
	
	@SubscribeEvent
	public static void onLivingHurtReality(LivingHurtEvent event) {
		if (event.getEntityLiving().getEntityData().getBoolean(RealityBlade.NBT_KEY)) {
			if (!(event.getSource().getTrueSource() instanceof EntityPlayer)) {
				event.setCanceled(true);
			} else if (event.getSource().getTrueSource() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
				if (!(player.getHeldItemMainhand().getItem() instanceof RealityBlade)) {
					event.setCanceled(true);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onLivingAttackReality(LivingAttackEvent event) {
		if (event.getEntityLiving().getEntityData().getBoolean(RealityBlade.NBT_KEY)) {
			if (!(event.getSource().getTrueSource() instanceof EntityPlayer)) {
				event.setCanceled(true);
			} else if (event.getSource().getTrueSource() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
				if (!(player.getHeldItemMainhand().getItem() instanceof RealityBlade)) {
					event.setCanceled(true);
				}
			}
		}
	}
	
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void extinguishEntities(LivingUpdateEvent event) {
		if (event.getEntityLiving().isBurning() && event.getEntityLiving().getEntityData().getBoolean(RealityBlade.NBT_KEY)) {
			event.getEntityLiving().extinguish();
		}
	}
}
