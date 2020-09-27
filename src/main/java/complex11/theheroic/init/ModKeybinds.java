package complex11.theheroic.init;

import org.lwjgl.input.Keyboard;

import complex11.theheroic.util.Reference;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ModKeybinds {

	public static final KeyBinding SPECIAL_1 = new KeyBinding("key." + Reference.MODID + ".special_1",
			KeyConflictContext.IN_GAME, KeyModifier.NONE, Keyboard.KEY_NUMPAD1, "key.category." + Reference.MODID);

	public static void init() {
		ClientRegistry.registerKeyBinding(SPECIAL_1);
	}
}
