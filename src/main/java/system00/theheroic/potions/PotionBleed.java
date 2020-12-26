package system00.theheroic.potions;

import system00.theheroic.util.Reference;
import system00.theheroic.util.interfaces.ISyncedPotion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class PotionBleed extends PotionBase implements ISyncedPotion {

	public PotionBleed(boolean isBadEffect, int liquidColour){
		super(isBadEffect, liquidColour, new ResourceLocation(Reference.MODID, "textures/gui/potion_icon_bleed.png"));
		this.setPotionName("effect." + Reference.MODID + ":bleed");
	}

	public static void performEffectConsistent(EntityLivingBase host, int strength) {
		if (!host.world.isRemote) {
			host.attackEntityFrom(DamageSource.GENERIC,
					(float) (1 + strength));
		}
	}
}
