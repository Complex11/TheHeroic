package system00.theheroic.event;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import system00.theheroic.init.ModItems;
import system00.theheroic.util.HeroicUtil;

public class WeaponEvent {
	
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		if (HeroicUtil.checkForItem(ModItems.BULWARKS_HALLMARK, event.player)) {
			event.player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 2, false, false));
			System.out.println("Item Detected");
		} else {
			System.out.println("somethings wrong");
		}
	}
}
