package complex11.theheroic.event;

import complex11.theheroic.init.ModItems;
import complex11.theheroic.items.weapons.Sclass.RealityBlade;
import complex11.theheroic.util.HeroicUtil;
import complex11.theheroic.util.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = Reference.MODID)
public class WeaponEvent {
	
	//START REALITY BLADE
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
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingDeathReality(LivingDeathEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (player.inventory.hasItemStack(new ItemStack(ModItems.REALITY_BLADE)) && !RealityBlade.realityBend) {
				event.setCanceled(true);
				player.setHealth(player.getMaxHealth());
				HeroicUtil.SendMsgToPlayer("Reality Bent!", player);
				RealityBlade.realityBend = true;
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void extinguishEntities(LivingUpdateEvent event) {
		if (event.getEntityLiving().isBurning() && event.getEntityLiving().getEntityData().getBoolean(RealityBlade.NBT_KEY)) {
			event.getEntityLiving().extinguish();
		}
		String string1 = "11:11";
		if (java.time.LocalTime.now().toString().contains(string1) && event.getEntityLiving().getEntityData().getBoolean(RealityBlade.NBT_KEY)) {
			World world = event.getEntityLiving().world;
			world.removeEntityDangerously(event.getEntityLiving());
		}
	}
	
	//END REALITY BLADE
}
