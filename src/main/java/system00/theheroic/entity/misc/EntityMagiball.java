package system00.theheroic.entity.misc;

import system00.theheroic.util.HeroicUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityMagiball extends EntityThrowable {

	public EntityMagiball(World worldIn) {
		super(worldIn);
	}
	
	public EntityMagiball(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}
	
	public EntityMagiball(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}
	
	@Override
	protected void onImpact(RayTraceResult result) {
		if (!this.world.isRemote) {
			setDead();
			if (result.entityHit instanceof EntityLivingBase) {
				EntityLivingBase entity = (EntityLivingBase) result.entityHit;
				HeroicUtil.SummonLightningAt(getEntityWorld(), entity.posX, entity.posY, entity.posZ);
				entity.setFire(10);
				entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1200, 1));
			}
			HeroicUtil.createSpecialExplosion(6, 3, getEntityWorld(), this.thrower, this.posX, this.posY, this.posZ, false, true, EntitySelectors.IS_ALIVE);
        	HeroicUtil.completelyDestroyBlocks(getEntityWorld(), this.getEntityBoundingBox().grow(0.2), 50);
		}
	}
}
