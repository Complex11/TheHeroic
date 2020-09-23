package system00.theheroic.util;

import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class HeroicUtil {
	
	public static DamageSource heartPierce = new DamageSource("heartpierce").setDamageBypassesArmor().setDamageIsAbsolute();
			
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
        target.addPotionEffect(new PotionEffect(MobEffects.WITHER, 200, 1, true, false));
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
	
	public static void damageAndCheckItem(ItemStack item) {
		item.setItemDamage(item.getItemDamage() + 1);
	    if (item.getItemDamage() > item.getMaxDamage()) {
	    	item.shrink(1);
	    }
	}
	
	public static boolean checkForItem(Item item, EntityPlayer player) {
		boolean hasItem = false;
		if (player.inventory != null) {
			for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
				ItemStack stack = player.inventory.getStackInSlot(i);
				if (!stack.isEmpty() && stack.getItem().equals(item)) {
					hasItem = true;
				}
			}
			ItemStack stackInv = player.inventory.getItemStack();
			if (!stackInv.isEmpty() && stackInv.getItem().equals(item)) {
				hasItem = true;
			}
		}
		return hasItem;
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
	
	public static boolean checkGameRule(World world, String gameRule) {
		return world.getGameRules().getBoolean(gameRule);
	}
}

