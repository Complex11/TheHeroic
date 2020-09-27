package complex11.theheroic.util.handlers;

import complex11.theheroic.network.RealityBladeAbilityPacket;
import complex11.theheroic.util.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public enum NetworkHandler {

	INSTANCE;
	
	private final SimpleNetworkWrapper channel = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
	
	private NetworkHandler() {
		int index = 0;
		this.channel.registerMessage(RealityBladeAbilityPacket.MessageHandler.class, RealityBladeAbilityPacket.class, index++, Side.SERVER);	
		System.out.println("registered packets");
	}
	
	public void sendMessageToServer(IMessage msg) {
		channel.sendToServer(msg);
	}
}
