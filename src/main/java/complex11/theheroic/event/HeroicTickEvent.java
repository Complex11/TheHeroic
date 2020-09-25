package complex11.theheroic.event;

import complex11.theheroic.init.ModPotions;
import complex11.theheroic.potions.Potion4D;
import complex11.theheroic.potions.PotionTimeStop;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

@EventBusSubscriber
public class HeroicTickEvent {
	
	@SubscribeEvent
	public static void onClientTickEvent(ClientTickEvent event){
		if(event.phase == TickEvent.Phase.END && !net.minecraft.client.Minecraft.getMinecraft().isGamePaused()) {
			World world = net.minecraft.client.Minecraft.getMinecraft().world;
			if(world == null) return;
			PotionTimeStop.cleanUpEntities(world);
		}
	}
	
	@SubscribeEvent
	public static void onPlayerTickEvent(PlayerTickEvent event){
		if(event.player == Minecraft.getMinecraft().player && event.phase == TickEvent.Phase.END){
			if(Minecraft.getMinecraft().entityRenderer.getShaderGroup() != null){
				String activeShader = Minecraft.getMinecraft().entityRenderer.getShaderGroup().getShaderGroupName();
				if ((activeShader.equals(PotionTimeStop.SHADER.toString())
						&& !Minecraft.getMinecraft().player.isPotionActive(ModPotions.TIME_STOP_EFFECT))
						|| (activeShader.equals(Potion4D.SHADER.toString())
						&& !Minecraft.getMinecraft().player.isPotionActive(ModPotions.FOUR_DIMENSION_EFFECT))) {
					Minecraft.getMinecraft().entityRenderer.stopUseShader();
				}
			}
		}
	}
}
