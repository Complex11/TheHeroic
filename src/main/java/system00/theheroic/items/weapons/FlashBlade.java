package system00.theheroic.items.weapons;

import java.util.List;

import com.google.common.base.Predicates;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import system00.theheroic.items.tool.ToolSword;
import system00.theheroic.util.HeroicUtil;

public class FlashBlade extends ToolSword {

	public FlashBlade(String name, ToolMaterial material) {
		super(name, material);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		super.onLeftClickEntity(stack, player, entity);
		stack.damageItem(1, player);
		if (entity instanceof EntityLivingBase) {
			HeroicUtil.PercentageAttack(0.66f, (EntityLivingBase) entity);
			entity.attackEntityFrom(DamageSource.causePlayerDamage(player), 5);
		}
		player.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 0.6f, 1.4f);
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		if (player.isSneaking()) {
			AxisAlignedBB bb = player.getEntityBoundingBox();
			bb = bb.grow(14.0, 3, 14.0);
			List<Entity> list = worldIn.getEntitiesInAABBexcluding(player, bb, Predicates.instanceOf(EntityLivingBase.class));
			list.removeIf(t -> t instanceof EntityPlayer);
			for (Entity entity : list) {
				HeroicUtil.SpecialTeleport(entity.posX, entity.posY, entity.posZ, player);
				if (entity instanceof EntityLivingBase) {
					HeroicUtil.spawnParticleAtEntity((EntityLivingBase) entity, EnumParticleTypes.CRIT_MAGIC, 32);
					HeroicUtil.PercentageAttack(0.66f, (EntityLivingBase) entity);
					entity.attackEntityFrom(DamageSource.MAGIC, 8);
					entity.hurtResistantTime = 0;
				}
			}
			player.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 0.8f, 2.0f);
			player.playSound(SoundEvents.ENTITY_SPLASH_POTION_BREAK, 0.66f, 1.5f);
			HeroicUtil.damageAndCheckItem(item);
		} else {
			if (player.isPotionActive(MobEffects.SPEED)) {
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 200, 10, true, false));
			} else {
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 200, 5, true, false));
			}
			HeroicUtil.damageAndCheckItem(item);
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

}