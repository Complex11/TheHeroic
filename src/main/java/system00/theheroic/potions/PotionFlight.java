package system00.theheroic.potions;

import system00.theheroic.util.Reference;
import net.minecraft.util.ResourceLocation;

public class PotionFlight extends PotionBase {

	public PotionFlight(boolean isBadEffect, int liquidColour){
		super(isBadEffect, liquidColour, new ResourceLocation(Reference.MODID, "textures/gui/potion_icon_flight.png"));
		this.setPotionName("effect." + Reference.MODID + ":flight");
	}
}

