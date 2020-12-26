package system00.theheroic.potions;

import system00.theheroic.util.Reference;
import net.minecraft.util.ResourceLocation;

public class PotionDeathMark extends PotionBase {

	public PotionDeathMark(boolean isBadEffect, int liquidColour){
		super(isBadEffect, liquidColour, new ResourceLocation(Reference.MODID, "textures/gui/potion_icon_mark_of_death.png"));
		this.setPotionName("effect." + Reference.MODID + ":mark_of_death");
	}
}
