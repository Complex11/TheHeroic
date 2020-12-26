package system00.theheroic.entity.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import system00.theheroic.util.HeroicUtil;

import java.util.List;
import java.util.Random;

public class SpecialPearl extends EntityThrowable {

	public float damage = 1;
	boolean doExplode = true;
	public EntityLivingBase thrower = null;
	
	public SpecialPearl(World worldIn) {
	    super(worldIn);
	}

	@Override
	public void onUpdate() {
		this.motionX *= 0.99;
		this.motionY *= 0.99;
		this.motionZ *= 0.99;
		super.onUpdate();
	}
	
	protected void onImpact(RayTraceResult result) {
		double r = Math.sqrt(this.damage);
        List<Entity> entityList2 = getEntityWorld().getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB(this.posX - r, this.posY - r, this.posZ - r, this.posX + r, this.posY + r, this.posZ + r));
        for (Entity entity : entityList2) {
   			if (entity instanceof EntityLivingBase) {
   	        	float distance = (float) Math.sqrt((entity.posX - this.posX) * (entity.posX - this.posX) + (entity.posY - this.posY) * (entity.posY - this.posY) + (entity.posZ - this.posZ) * (entity.posZ - this.posZ));
   	        	Random rand = new Random();
     			if (rand.nextInt(50) == 0) {
               		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, (int) (200 / (distance + 1)), 0));
               	}
               	if (rand.nextInt(25) == 0) {           		
               		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.HUNGER, (int) (300 / (distance + 1)), 1));
               	}
               	if (rand.nextInt(18) == 0) {
               		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, (int) (400 / (distance + 1)), 0));
               	}
               	if (rand.nextInt(35) == 0) {
               		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, (int) (320 / (distance + 1)), 0));
               	}
               	if (rand.nextInt(30) == 0) {
               		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.POISON, (int) (500 / (distance + 1)), 2));
               	}
               	if (rand.nextInt(30) == 0) {
               		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, (int) (400 / (distance + 1)), 1));
               	}
               	if (rand.nextInt(40) == 0) {
               		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, (int) (500 / (distance + 1)), 1));
               	}
               	if (rand.nextInt(36) == 0) {
               		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.WITHER, (int) (500 / (distance + 1)), 2));
               	}
       		}
        }
        if (this.doExplode) {
        	HeroicUtil.createSpecialExplosion(6, 3, getEntityWorld(), this.thrower, this.posX, this.posY, this.posZ, false, true, EntitySelectors.IS_ALIVE);
        	HeroicUtil.destroyBlocksIn(getEntityWorld(), this.getEntityBoundingBox().grow(0.5), 50);
        }
        this.setDead();
	}
	
	@Override
	public float getGravityVelocity() {
		return 0.02f;
	}
	
	@Override
	public boolean hasNoGravity() {
		return this.ticksExisted <= 10;
	}
}
