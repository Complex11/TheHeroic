package complex11.theheroic.network;

import complex11.theheroic.Main;
import complex11.theheroic.items.weapons.Sclass.RealityBlade;
import complex11.theheroic.util.HeroicUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class RealityBladeAbilityPacket implements IMessage {
	    
	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}

	public RealityBladeAbilityPacket() {
	}

	public static class MessageHandler implements IMessageHandler<RealityBladeAbilityPacket, IMessage> {
		@Override
		public IMessage onMessage(RealityBladeAbilityPacket message, MessageContext ctx) {
			final EntityPlayerMP player = (EntityPlayerMP) Main.proxy.getPlayerEntityFromContext(ctx);
			RealityBlade.realityWarp = !RealityBlade.realityWarp;
			if (RealityBlade.realityWarp) {
				HeroicUtil.SendMsgToPlayer("Reality Warp Activated", player);
			} else {
				HeroicUtil.SendMsgToPlayer("Reality Warp Deactivated", player);
			}
			return null;
		}
	}
}
