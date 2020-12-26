package system00.theheroic.items.weapons.Cclass;

import com.google.common.base.Predicates;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import system00.theheroic.Main;
import system00.theheroic.items.tool.ToolSword;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.Reference;

import javax.annotation.Nullable;
import java.util.List;

public class SpecialShock extends ToolSword {
	
	public static final String NBT_KEY = "shocked";
	
	public SpecialShock(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabC);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §eC");
		tooltip.add("§d§lPassive: §r§7Normal attacks §3Shock §7targets, deal 5 damage and apply §2Slowness[2]§7. If already §3Shocked§7, deal 9 damage and apply §2Slowness[3] and §2Weakness[1] instead.");
		tooltip.add("§d§lSpecial Ability: §r§7Apply §2Slowness[2]§7, §2Weakness[1] §7and §2Wither[1] §7to all §3Shocked §7entities in a radius[5], and apply §2Slowness[1]§7 to all others. §fCooldown: 5 seconds.");
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 1, false, false));
		if (!target.getEntityData().getBoolean(NBT_KEY)) {
			target.getEntityData().setBoolean(NBT_KEY, true);
			target.attackEntityFrom(DamageSource.ON_FIRE, 5);
			HeroicUtil.damageAndCheckItem(stack, 1);
		} else if (target.getEntityData().getBoolean(NBT_KEY)) {
			target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 2, false, false));
			target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 200, 0, false, false));
			target.attackEntityFrom(DamageSource.ON_FIRE, 9);
			HeroicUtil.damageAndCheckItem(stack, 1);
		}
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		AxisAlignedBB bb = player.getEntityBoundingBox();
		bb = bb.grow(5.0, 5.0, 5.0);
		List<Entity> list = worldIn.getEntitiesInAABBexcluding(player, bb, Predicates.instanceOf(EntityLivingBase.class));
		for (Entity entity : list) {
			EntityLivingBase entityLiving = (EntityLivingBase) entity;
			if (entityLiving.getEntityData().getBoolean(NBT_KEY)) {
				entityLiving.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 1, false, false));
				entityLiving.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 200, 0, false, false));
				entityLiving.addPotionEffect(new PotionEffect(MobEffects.WITHER, 200, 0, false, false));
			} else {
				entityLiving.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 0, false, false));
			}
		}
		player.getCooldownTracker().setCooldown(this, 100);
		HeroicUtil.spawnParticleCircleAroundEntity(player, EnumParticleTypes.SMOKE_NORMAL, 5);
		HeroicUtil.damageAndCheckItem(item, 1);
		player.playSound(SoundEvents.ENTITY_CREEPER_HURT, 1f, 1.75f);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
    }
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/cclass/special_shock", "inventory"));
	}
	
}
