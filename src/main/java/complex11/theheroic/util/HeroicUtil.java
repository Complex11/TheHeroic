package complex11.theheroic.util;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class HeroicUtil {
	
	public static DamageSource heartPierce = new DamageSource("heartpierce").setDamageBypassesArmor().setDamageIsAbsolute();
	
	//UTILITY
	
	public static void SendMsgToPlayer(String msg, EntityPlayer player) {
		ITextComponent msgToSend = new TextComponentString(msg);
		player.sendMessage(msgToSend);
	}
	

	public static void damageAndCheckItem(ItemStack item, int damage) {
		item.setItemDamage(item.getItemDamage() + damage);
	    if (item.getItemDamage() > item.getMaxDamage()) {
	    	item.shrink(1);
	    }
	}
	
	public static void flyTo(Vec3d to, Entity me, double speed) {
		to = to.subtract(me.posX, me.posY, me.posZ);
		to = to.scale(speed / to.lengthVector());
		me.addVelocity(to.x, to.y, to.z);
	}
	
	
	public static boolean checkGameRule(World world, String gameRule) {
		return world.getGameRules().getBoolean(gameRule);
	}
	
	//ATTACKS
	
	public static void ScalingAttack(float damage, DamageSource source, EntityLivingBase target) {
		if (Float.isInfinite(damage) || Float.isNaN(damage)) {
			return;
		}
		if (target instanceof EntityPlayer && ((EntityPlayer) target).capabilities.isCreativeMode) {
			return;
		}
		float newDamage = 0;
		if (target.getMaxHealth() / 10 > 1) {
			newDamage = (float) (Math.pow(target.getMaxHealth() / 10, 2 + target.getMaxHealth() / 100)
					+ target.getMaxHealth() / 10 + damage);
		} else {
			newDamage = (float) (Math.pow(target.getMaxHealth() / 10 + 1, 2 + target.getMaxHealth() / 100)
					+ target.getMaxHealth() / 10 + 1 + damage);
		}
		target.attackEntityFrom(source, newDamage);
	}
	
	public static void PercentageAttack(float damage, EntityLivingBase target) {
		if (Float.isInfinite(damage) || Float.isNaN(damage) || damage > 1.0) {
			return;
		}
		if (target instanceof EntityPlayer && ((EntityPlayer) target).capabilities.isCreativeMode) {
			return;
		}
		if (target.getHealth() < 0) {
			return;
		}
		target.setHealth(target.getHealth() * damage);
	}
	
	public static void StackAttack(float damage, int stacksize, EntityLivingBase target) {
		if (Float.isInfinite(damage) || Float.isNaN(damage)) {
			return;
		}
		if (target instanceof EntityPlayer && ((EntityPlayer) target).capabilities.isCreativeMode) {
			return;
		}
		target.attackEntityFrom(DamageSource.GENERIC, (float) (damage * stacksize));
	}
	
	public static void BleedAttack(float damage, int bleedticks, EntityLivingBase target) {
		if (Float.isInfinite(damage) || Float.isNaN(damage)) {
			return;
		}
		if (target instanceof EntityPlayer && ((EntityPlayer) target).capabilities.isCreativeMode) {
			return;
		}
		spawnParticleAtEntity(target, EnumParticleTypes.VILLAGER_ANGRY, 5);
        target.attackEntityFrom(DamageSource.GENERIC, damage);
        target.addPotionEffect(new PotionEffect(MobEffects.WITHER, bleedticks, 1, true, false));
	}
	
	public static void HeartPierce(float damage, EntityLivingBase target) {
		if (Float.isInfinite(damage) || Float.isNaN(damage)) {
			return;
		}
		if (target instanceof EntityPlayer && ((EntityPlayer) target).capabilities.isCreativeMode) {
			return;
		}
		target.addTag("Heart Pierced");
		target.attackEntityFrom(heartPierce, damage);
	}
	
	public static void Lifesteal(float damage, EntityLivingBase attacker, EntityLivingBase target) {
		if (Float.isInfinite(damage) || Float.isNaN(damage)) {
			return;
		}
		if (target instanceof EntityPlayer && ((EntityPlayer) target).capabilities.isCreativeMode) {
			return;
		}
		target.attackEntityFrom(DamageSource.GENERIC, damage);
		if (attacker.getHealth() < attacker.getMaxHealth()) {
			attacker.setHealth(attacker.getHealth() + damage);
		} else {
			attacker.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 1, true, false));
		}
	}
	
	//PARTICLES
	
	public static void spawnParticleCircleAroundEntity(EntityLivingBase entity, EnumParticleTypes particle, float radius) {
		for (float a = 0; a < 36; a += 0.1f) {
			Vec3d at = new Vec3d(radius, 0, 0);
			at = at.rotateYaw(a / radius);
			at = at.addVector(entity.posX, entity.posY, entity.posZ);
			entity.world.spawnParticle(particle, at.x, at.y, at.z, 0, 0, 0);
		}
	}
	
	public static void spawnParticleAtEntity(EntityLivingBase entity, EnumParticleTypes particle, int number) {
		for (int i = 0; i < number; i++) {
			double d0 = entity.posX;
	        double d1 = entity.posY;
	        double d2 = entity.posZ;
	        World world = entity.world;
			Random random = ((EntityLivingBase) entity).getRNG(); 
			double d6 = (double)i / 127.0D;
	        float f = (random.nextFloat() - 0.5F) * 0.2F;
	        float f1 = (random.nextFloat() - 0.5F) * 0.2F;
	        float f2 = (random.nextFloat() - 0.5F) * 0.2F;
	        double d3 = d0 + (entity.posX - d0) * d6 + (random.nextDouble() - 0.5D) * (double)entity.width * 2.0D;
	        double d4 = d1 + (entity.posY - d1) * d6 + random.nextDouble() * (double)entity.height;
	        double d5 = d2 + (entity.posZ - d2) * d6 + (random.nextDouble() - 0.5D) * (double)entity.width * 2.0D;
	        world.spawnParticle(particle, d3, d4, d5, (double)f, (double)f1, (double)f2);
		}
	}
	
	public static void spawnParticleAtPosition(double x, double y, double z, EnumParticleTypes particle, int number) {
		for (int i = 0; i < number; i++) {
	        World world = Minecraft.getMinecraft().player.world;
			Random random = new Random(); 
			double d6 = (double)i / 127.0D;
	        float f = (random.nextFloat() - 0.5F) * 0.2F;
	        float f1 = (random.nextFloat() - 0.5F) * 0.2F;
	        float f2 = (random.nextFloat() - 0.5F) * 0.2F;
	        double d3 = x + x * d6 + (random.nextDouble() - 0.5D) * 1.5D * 2.0D;
	        double d4 = y + y * d6 + random.nextDouble() * 2D;
	        double d5 = z + z * d6 + (random.nextDouble() - 0.5D) * 1.5D * 2.0D;
	        world.spawnParticle(particle, d3, d4, d5, (double)f, (double)f1, (double)f2);
		}
	}
	
	//WORLD
	
	public static boolean SpecialTeleport(double x, double y, double z, EntityLivingBase entity) {
		entity.posX = x;
		entity.posY = y;
		entity.posZ = z;
		BlockPos blockpos = new BlockPos(entity);
		World world = entity.world;
		if (world.isBlockLoaded(blockpos)) {
			entity.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
		}
		if (entity instanceof EntityCreature) {
			((EntityCreature) entity).getNavigator().clearPath();
		}
		return true;
	}
	
	public static List<EntityLivingBase> getEntitiesWithinRadius(double radius, double x, double y, double z,
			World world){
		return getEntitiesWithinRadius(radius, x, y, z, world, EntityLivingBase.class);
	}
	
	public static <T extends Entity> List<T> getEntitiesWithinRadius(double radius, double x, double y, double z,
			World world, Class<T> entityType){
		AxisAlignedBB aabb = new AxisAlignedBB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius);
		List<T> entityList = world.getEntitiesWithinAABB(entityType, aabb);
		for(int i = 0; i < entityList.size(); i++){
			if(entityList.get(i).getDistance(x, y, z) > radius){
				entityList.remove(i);
				break;
			}
		}
		return entityList;
	}
	
	public static Vec3d rayTraceToBlock(Entity from, double d) {
		return rayTraceToBlock(from.getEntityWorld(), new Vec3d(from.posX, from.posY, from.posZ), from.getLook(1), d);
    }
	
	public static Vec3d rayTraceToBlock(World worldIn, Vec3d start, Vec3d dir, double d) {
        RayTraceResult result = worldIn.rayTraceBlocks(start, start.add(dir.scale(d)), false, false, true);
        if (result != null) {
        	return result.hitVec;
        }
        return start.add(dir.scale(d));
	}
	
	public static Vec3d applyRotationMatrix(Vec3d from, double cphi, double cpsi) {
		double sphi = Math.sqrt(1 - cphi * cphi);
		double spsi = Math.sqrt(1 - cpsi * cpsi);	
		return new Vec3d(from.x * cpsi * cphi - from.y * spsi - from.z * cpsi * sphi, from.x * spsi * cphi + from.y * cpsi - from.z * spsi * sphi, from.x * sphi + from.z * cphi);
	}
	
	public static void createSpecialExplosion(float damage, int power, World world, EntityLivingBase shooter, double x,
			double y, double z, boolean doFire, boolean doDestroyBlocks, Predicate<Entity> doDamageTo) {
		if (!world.isRemote) {
			float radius = (float) (Math.sqrt(damage) - 1);
			List<Entity> entityList = world.getEntitiesWithinAABBExcludingEntity(shooter,
					new AxisAlignedBB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius));
			for (int i = 0; i < entityList.size(); ++i) {
				if (entityList.get(i) instanceof EntityLivingBase) {
					((EntityLivingBase) entityList.get(i)).attackEntityFrom(DamageSource.GENERIC, damage);
				}
				if (doDamageTo.apply(entityList.get(i))) {
					float distanceX = (float) Math.abs(entityList.get(i).posX - x);
					float distanceY = (float) Math.abs(entityList.get(i).posY - y);
					float distanceZ = (float) Math.abs(entityList.get(i).posZ - z);
					if (Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ) > radius) {
						continue;
					}
				}
				entityList.get(i).setEntityInvulnerable(true);
			}
			world.newExplosion((Entity) null, x, y, z, power, doFire, doDestroyBlocks);
			for (int i = 0; i < entityList.size(); ++i) {
				entityList.get(i).setEntityInvulnerable(false);
			}
		}
	}
	
	public static void destroyBlocksIn(World worldIn, AxisAlignedBB bb, float hardness) {
		if (worldIn.isRemote) {
			return;
		}
		for (double i = bb.minX; i <= bb.maxX; ++i) {
			for (double j = bb.minY; j <= bb.maxY; ++j) {
				for (double k = bb.minZ; k <= bb.maxZ; ++k) {
					BlockPos at = new BlockPos(i, j, k);
					float hard = worldIn.getBlockState(at).getBlockHardness(worldIn, at);
					if (hard >= 0 && hard <= hardness) {
						worldIn.destroyBlock(at, Math.random() * 6 < 1);
					}
				}
			}
		}
	}
	
	public static Explosion createExplosion(@Nullable Entity exploder, World world, BlockPos pos, float strength) {
		return createExplosion(exploder, world, pos.getX(), pos.getY(), pos.getZ(), strength, true, false);
	}

	public static Explosion createExplosion(@Nonnull Entity exploder, World world, float strength) {
		return createExplosion(exploder, world, exploder.posX, exploder.posY, exploder.posZ, strength, true, false);
	}

	public static Explosion createExplosion(@Nullable Entity exploder, World world, @Nonnull Entity explodingEntity, float strength) {
		return createExplosion(exploder, world, explodingEntity.posX, explodingEntity.posY, explodingEntity.posZ, strength, true, false);
	}

	public static Explosion createExplosion(@Nullable Entity exploder, World world, double posX, double posY, double posZ, float strength) {
		return createExplosion(exploder, world, posX, posY, posZ, strength, true, false);
	}

	public static Explosion createExplosion(@Nullable Entity exploder, World world, double posX, double posY, double posZ, float strength, boolean destructiveExplosion) {
		return createExplosion(exploder, world, posX, posY, posZ, strength, destructiveExplosion, false);
	}

	public static Explosion createExplosion(@Nullable Entity exploder, World world, double posX, double posY, double posZ, float strength, boolean destructiveExplosion, boolean fieryExplosion) {
		return world.newExplosion(exploder, posX, posY, posZ, strength, fieryExplosion, destructiveExplosion);
	}
}

