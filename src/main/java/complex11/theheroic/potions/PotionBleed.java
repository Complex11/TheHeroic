package complex11.theheroic.potions;

import complex11.theheroic.util.Reference;
import complex11.theheroic.util.interfaces.ISyncedPotion;
import net.minecraft.util.ResourceLocation;

public class PotionBleed extends PotionBase implements ISyncedPotion {

	public PotionBleed(boolean isBadEffect, int liquidColour){
		super(isBadEffect, liquidColour, new ResourceLocation(Reference.MODID, "textures/gui/potion_icon_bleed.png"));
		this.setPotionName("effect." + Reference.MODID + ":bleed");
	}
}
