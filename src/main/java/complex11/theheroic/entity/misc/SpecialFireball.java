package complex11.theheroic.entity.misc;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import complex11.theheroic.util.HeroicUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SpecialFireball extends EntityFireball {
	
	public float radius = 0;
	public int damageCooldown = 1;
	public int duration = 1;
	public int explosionPower = 0;
	public float explosionDamage = 0;
	public boolean doExplosionDestroyBlocks = false;
	public boolean isHoming = false;
	public EntityLivingBase shooter = null;
	public EntityLivingBase target = null;
	public Predicate<Entity> noDamageTo = a -> a.equals(this.shooter);
	private float health = 180;
	
    public SpecialFireball(World worldIn) {
        super(worldIn);
        this.setSize(1.0F, 1.0F);
    }

    @SideOnly(Side.CLIENT)
    public SpecialFireball(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(worldIn, x, y, z, accelX, accelY, accelZ);
        this.setSize(1.0F, 1.0F);
    }

    public SpecialFireball(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
        super(worldIn, shooter, accelX, accelY, accelZ);
        this.setSize(1.0F, 1.0F);
    }
    
    @Override
    public float getMotionFactor() {
    	return 1.1f;
    }
    
	@Override
	public void onUpdate() {
		super.onUpdate();
		this.health += 0.5f;
		if (this.isHoming && this.target != null && !this.target.isDead && !getEntityWorld().isRemote) {
			HeroicUtil.flyTo(new Vec3d(this.target.posX, this.target.posY, this.target.posZ), this, 0.05);
			double distance = this.getDistance(target);
			if (distance > 6 && distance < 42) {
				Vec3d dir = new Vec3d(target.posX - this.posX, target.posY - this.posY, target.posZ - this.posZ);
				dir = dir.scale(-2.1 / distance / Math.sqrt(distance));
			}
		}
		if (this.ticksExisted > 800) {
			this.onImpact(new RayTraceResult(RayTraceResult.Type.BLOCK, Vec3d.ZERO, EnumFacing.UP, new BlockPos(this)));
		}
	}
    
    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.entityHit == null || !result.entityHit.isEntityEqual(this.shootingEntity)) {
            if (!getEntityWorld().isRemote) {
            	if (this.explosionPower > 0) {
            		HeroicUtil.createSpecialExplosion(this.explosionDamage, this.explosionPower, getEntityWorld(), shooter, this.posX,
            				this.posY, this.posZ, false, true, Predicates.not(this.noDamageTo));
            		if (this.doExplosionDestroyBlocks) {
            			HeroicUtil.destroyBlocksIn(getEntityWorld(), this.getEntityBoundingBox().grow(1), 50);
            		}
            	}
            	this.setDead();
            }
        }
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return false;
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
    	if (this.health <= 0) {
    		return false;
    	}
    	if (!getEntityWorld().isRemote && this.isHoming) {
    		this.health -= amount;
    		if (this.health <= 0) {
        		HeroicUtil.createSpecialExplosion(this.explosionDamage, this.explosionPower, getEntityWorld(), shooter, this.posX,
        				this.posY, this.posZ, false, this.doExplosionDestroyBlocks, Predicates.not(this.noDamageTo));
    			this.setDead();
    		}
    		return true;
    	}
        return false;
    }

    @Override
    protected EnumParticleTypes getParticleType() {
        return EnumParticleTypes.LAVA;
    }
}
