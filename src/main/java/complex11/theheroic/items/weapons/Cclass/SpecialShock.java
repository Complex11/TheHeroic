package complex11.theheroic.items.weapons.Cclass;

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
		tooltip.add("§d§lPassive: §r§7Normal attacks §3Shock §7targets, deal 4 damage and apply §2Slowness[2]§7. If already §3Shocked§7, deal 8 damage and apply §2Slowness[4] and §2Weakness[2] instead.");
		tooltip.add("§d§lSpecial Ability: §r§7Apply §2Slowness[4]§7, §2Weakness[3] §7and §2Wither[1] §7to all entities in a radius[5]. §fCooldown: 10 seconds.");
	}
	
	@Override
	public boolean onLeftClickEntity(final ItemStack item, final EntityPlayer player, final Entity entity) {
		if (entity instanceof EntityLivingBase) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 1, false, false));
			if (!entity.getEntityData().getBoolean(NBT_KEY)) {
				entity.getEntityData().setBoolean(NBT_KEY, true);
				entity.attackEntityFrom(DamageSource.ON_FIRE, 4);
				HeroicUtil.damageAndCheckItem(item, 1);
			} else if (entity.getEntityData().getBoolean(NBT_KEY)) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 3, false, false));
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 200, 1, false, false));
				entity.attackEntityFrom(DamageSource.ON_FIRE, 8);
				HeroicUtil.damageAndCheckItem(item, 2);
			}
		}
		return false;
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
				entityLiving.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 3, false, false));
				entityLiving.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 200, 2, false, false));
				entityLiving.addPotionEffect(new PotionEffect(MobEffects.WITHER, 200, 0, false, false));
				entity.attackEntityFrom(DamageSource.ON_FIRE, 2);
			}
		}
		player.getCooldownTracker().setCooldown(this, 200);
		HeroicUtil.spawnParticleCircleAroundEntity(player, EnumParticleTypes.SMOKE_NORMAL, 5);
		HeroicUtil.damageAndCheckItem(item, 5);
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
