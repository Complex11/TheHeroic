package system00.theheroic.event;

import system00.theheroic.init.ModKeybinds;
import system00.theheroic.items.weapons.Sclass.SchrodingersGift;
import system00.theheroic.items.weapons.Sclass.TheEnabler;
import system00.theheroic.network.RealityBladeAbilityPacket;
import system00.theheroic.network.TheEnablerAbilityPacket;
import system00.theheroic.util.handlers.NetworkHandler;
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
			if (player.getHeldItemMainhand().getItem() instanceof SchrodingersGift) {
				NetworkHandler.INSTANCE.sendMessageToServer(new RealityBladeAbilityPacket());
			}
			if (player.getHeldItemMainhand().getItem() instanceof TheEnabler) {
				NetworkHandler.INSTANCE.sendMessageToServer(new TheEnablerAbilityPacket());
			}
		}
	}
}
