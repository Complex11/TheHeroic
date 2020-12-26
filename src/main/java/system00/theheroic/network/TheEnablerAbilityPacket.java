package system00.theheroic.network;

import system00.theheroic.Main;
import system00.theheroic.items.weapons.Sclass.TheEnabler;
import system00.theheroic.util.HeroicUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TheEnablerAbilityPacket implements IMessage {
    
	@Override
	public void fromBytes(ByteBuf buf) {
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
	}
	
	public TheEnablerAbilityPacket() {
	}
	
	public static class MessageHandler implements IMessageHandler<TheEnablerAbilityPacket, IMessage> {
		@Override
		public IMessage onMessage(TheEnablerAbilityPacket message, MessageContext ctx) {
			final EntityPlayerMP player = (EntityPlayerMP) Main.proxy.getPlayerEntityFromContext(ctx);
			TheEnabler.enable = !TheEnabler.enable;
			if (TheEnabler.enable) {
				HeroicUtil.SendMsgToPlayer("Power is yours.", player);
			} else {
				HeroicUtil.SendMsgToPlayer("As you wish.", player);
			}
			return null;
		}
	}
}
