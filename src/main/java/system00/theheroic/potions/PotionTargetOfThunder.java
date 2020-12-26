package system00.theheroic.potions;

import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.Reference;
import system00.theheroic.util.interfaces.ISyncedPotion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class PotionTargetOfThunder extends PotionBase implements ISyncedPotion {
	
	public PotionTargetOfThunder(boolean isBadEffect, int liquidColour) {
		super(isBadEffect, liquidColour, new ResourceLocation(Reference.MODID, "textures/gui/potion_icon_thunder.png"));
		this.setPotionName("effect." + Reference.MODID + ":thunder");
	}

	public static void performEffectConsistent(EntityLivingBase entity, int strength) {
		if (!entity.world.isRemote) {
			entity.setHealth(entity.getHealth() * 0.5f);
			HeroicUtil.SummonLightningAt(entity.world, entity.posX, entity.posY, entity.posZ);
		}
	}
}
