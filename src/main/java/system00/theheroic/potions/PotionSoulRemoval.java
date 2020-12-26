package system00.theheroic.potions;

import system00.theheroic.util.Reference;
import net.minecraft.util.ResourceLocation;

public class PotionSoulRemoval extends PotionBase {

	public PotionSoulRemoval(boolean isBadEffect, int liquidColour){
		super(isBadEffect, liquidColour, new ResourceLocation(Reference.MODID, "textures/gui/potion_icon_soul_removal.png"));
		this.setPotionName("effect." + Reference.MODID + ":soul_removal");
	}
}
