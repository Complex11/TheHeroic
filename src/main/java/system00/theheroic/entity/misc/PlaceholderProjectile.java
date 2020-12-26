package system00.theheroic.entity.misc;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class PlaceholderProjectile extends EntityThrowable {

	public PlaceholderProjectile(World worldIn, EntityLivingBase throwerIn) {
	    super(worldIn, throwerIn);
	}

	public PlaceholderProjectile(World worldIn) {
	    super(worldIn);
	}

	public PlaceholderProjectile(World worldIn, double x, double y, double z) {
	    this(worldIn);
	    setPosition(x, y, z);
	}
	
	@Override
	protected void onImpact(RayTraceResult result) {
		if (result.entityHit != null) {
			this.setDead();
		}
	}
}
