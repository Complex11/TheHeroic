package system00.theheroic.potions;

import system00.theheroic.util.Reference;
import system00.theheroic.util.interfaces.ISyncedPotion;
import net.minecraft.util.ResourceLocation;

public class PotionSmokescreen extends PotionBase implements ISyncedPotion {

	public PotionSmokescreen(boolean isBadEffect, int liquidColour) {
		super(isBadEffect, liquidColour, new ResourceLocation(Reference.MODID, "textures/gui/potion_icon_smokescreen.png"));
		this.setPotionName("effect." + Reference.MODID + ":smokescreen");
	}
}
