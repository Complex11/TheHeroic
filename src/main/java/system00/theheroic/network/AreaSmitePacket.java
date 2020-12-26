package system00.theheroic.network;

import system00.theheroic.items.weapons.Sclass.HeavenlySmite;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AreaSmitePacket implements IMessage {

	public AreaSmitePacket() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}

	public static class MessageHandler implements IMessageHandler<AreaSmitePacket, IMessage> {

		@Override
		public IMessage onMessage(AreaSmitePacket message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().player;
			if (!player.getServer().isCallingFromMinecraftThread()) {
				player.getServer().addScheduledTask(() -> {
					this.onMessage(message, ctx);
				});
			} else {
				HeavenlySmite.areaSmite(player);
			}
			return null;
		}
	}
}
