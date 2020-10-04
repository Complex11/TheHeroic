package complex11.theheroic.potions;

import complex11.theheroic.util.Reference;
import net.minecraft.util.ResourceLocation;

public class PotionHEAVEN extends PotionBase {

	public PotionHEAVEN(boolean isBadEffect, int liquidColour){
		super(isBadEffect, liquidColour, new ResourceLocation(Reference.MODID, "textures/gui/potion_icon_HEAVEN.png"));
		this.setPotionName("effect." + Reference.MODID + ":heaven");
	}
}
