package complex11.theheroic.init;

import complex11.theheroic.util.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class ModNetworking {

	public static final String NETWORK_CHANNEL_NAME = Reference.MODID;
	public static SimpleNetworkWrapper network;

	public static void registerSimpleNetworking() {
		// DEBUG
		System.out.println("Registering simple networking");
		network = NetworkRegistry.INSTANCE.newSimpleChannel(NETWORK_CHANNEL_NAME);

		//int packetId = 0;
		// register messages from client to server
		// register messages from server to client
	}
}
