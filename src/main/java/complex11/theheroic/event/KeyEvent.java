package complex11.theheroic.event;

import complex11.theheroic.init.ModKeybinds;
import complex11.theheroic.items.weapons.Bclass.FruitNinja;
import complex11.theheroic.items.weapons.Sclass.RealityBlade;
import complex11.theheroic.network.RealityBladeAbilityPacket;
import complex11.theheroic.util.handlers.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

@EventBusSubscriber
public class KeyEvent {

	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public static void onKeyPressed(KeyInputEvent event) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		if (ModKeybinds.SPECIAL_1.isPressed()) {
			if (player.getHeldItemMainhand().getItem() instanceof RealityBlade) {
				NetworkHandler.INSTANCE.sendMessageToServer(new RealityBladeAbilityPacket());
			}
			if (player.getHeldItemMainhand().getItem() instanceof FruitNinja) {
				//NetworkHandler.INSTANCE.sendMessageToServer(new FruitNinjaAbilityPacket());
			}
		}
	}
}
