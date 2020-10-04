package complex11.theheroic.items.weapons.Aclass;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicates;

import complex11.theheroic.Main;
import complex11.theheroic.items.tool.ToolSword;
import complex11.theheroic.util.HeroicUtil;
import complex11.theheroic.util.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraftforge.client.model.ModelLoader;

public class FlashBlade extends ToolSword {

	public FlashBlade(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabA);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §aA");
		tooltip.add("§d§lPassive: §r§7Normal attacks deal damage based on target's health.");
		tooltip.add("§d§lSpecial Ability: §r§7Teleports to all entities in a radius[14] and attacks them for 34% of their current health + 8 magic damage. §fCooldown: 0 seconds.");
		tooltip.add("§d§lBonus Ability: §r§7Grants §2Speed[11] §7for 10 seconds.");
		tooltip.add("§4§lAura: §r§2Speed[2] §7while held if §2Speed §7is not already active.");
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		HeroicUtil.PercentageAttack(0.66f, target);
		target.attackEntityFrom(DamageSource.GENERIC, 5);
		attacker.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 0.6f, 1.4f);
		HeroicUtil.damageAndCheckItem(stack, 1);
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		if (player.isSneaking()) {
			AxisAlignedBB bb = player.getEntityBoundingBox();
			bb = bb.grow(14.0, 3.0, 14.0);
			List<Entity> list = worldIn.getEntitiesInAABBexcluding(player, bb, Predicates.instanceOf(EntityLivingBase.class));
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
			HeroicUtil.damageAndCheckItem(item, 1);
		} else {
			player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 200, 10, true, false));
			HeroicUtil.damageAndCheckItem(item, 1);
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}
	
	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		if (!((EntityLivingBase) entity).isPotionActive(MobEffects.SPEED)) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SPEED, 200, 2, false, false));
		}
    }
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/aclass/flash_blade", "inventory"));
	}
}