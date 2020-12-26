package system00.theheroic.potions;

import system00.theheroic.util.Reference;
import net.minecraft.util.ResourceLocation;

public class PotionHEAVEN extends PotionBase {

	public PotionHEAVEN(boolean isBadEffect, int liquidColour){
		super(isBadEffect, liquidColour, new ResourceLocation(Reference.MODID, "textures/gui/potion_icon_heaven.png"));
		this.setPotionName("effect." + Reference.MODID + ":heaven");
	}
}
