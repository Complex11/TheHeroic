package system00.theheroic.potions;

import system00.theheroic.util.Reference;
import system00.theheroic.util.interfaces.ISyncedPotion;
import net.minecraft.util.ResourceLocation;

public class Potion4D extends PotionBase implements ISyncedPotion {
	
	public static final ResourceLocation SHADER = new ResourceLocation("shaders/post/desaturate.json");

	public Potion4D(boolean isBadEffect, int liquidColour) {
		super(isBadEffect, liquidColour, new ResourceLocation(Reference.MODID, "textures/gui/potion_icon_fourd.png"));
		this.setPotionName("effect." + Reference.MODID + ":4_D");
	}
}