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
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import system00.theheroic.Main;
import system00.theheroic.init.ModPotions;
import system00.theheroic.items.tool.ToolSword;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.Reference;

import javax.annotation.Nullable;
import java.util.List;

public class RancidPoison extends ToolSword {

	public static float damage = 2;
	public static boolean thirsty = false;

	public RancidPoison(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabC);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §eC"); // e = C, b = B, a = A, 1 = S
		tooltip.add("§d§lPassive: §r§7Deal 2 damage and apply §2Bleed[1]§7 for 8 seconds.");
		tooltip.add(
				"§d§lSpecial Ability: §r§7Afflict all entities within a radius[10] with §2Wither[1]§7 for 10 seconds. §fCooldown: 4.5 seconds.");
		tooltip.add("§4§lAura: §r§7All entities in a radius[3] are afflicted with §2Wither[1]§7 for 4 seconds.");
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		target.attackEntityFrom(DamageSource.GENERIC, damage);
		target.addPotionEffect(new PotionEffect(ModPotions.BLEED_EFFECT, 160, 0));
		HeroicUtil.damageAndCheckItem(stack, 1);
		return true;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		HeroicUtil.spawnParticleCircleAroundEntity(player, EnumParticleTypes.SPELL_MOB_AMBIENT, 10f);
		List<EntityLivingBase> list = HeroicUtil.getEntitiesWithinRadius(10, player.posX, player.posY, player.posZ,
				worldIn);
		list.removeIf(Predicates.instanceOf(EntityPlayer.class));
		for (Entity entityNear : list) {
			if (entityNear instanceof EntityLivingBase) {
				((EntityLivingBase) entityNear).addPotionEffect(new PotionEffect(MobEffects.WITHER, 200, 0));
			}
		}
		HeroicUtil.damageAndCheckItem(item, 1);
		player.getCooldownTracker().setCooldown(this, 90);
		player.playSound(SoundEvents.BLOCK_CHORUS_FLOWER_DEATH, 0.8f, 1.2f);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		if (entity.world.getTotalWorldTime() % 20 == 0 && entity instanceof EntityLivingBase) {
			EntityLivingBase entityLiving = (EntityLivingBase) entity;
			if (entityLiving.getHeldItemMainhand().getItem() instanceof RancidPoison) {
				HeroicUtil.spawnParticleCircleAroundEntity(entityLiving, EnumParticleTypes.SUSPENDED_DEPTH, 3f);
				List<EntityLivingBase> list = HeroicUtil.getEntitiesWithinRadius(3, entityLiving.posX,
						entityLiving.posY, entityLiving.posZ, world);
				list.removeIf(Predicates.instanceOf(EntityPlayer.class));
				for (Entity entityNear : list) {
					if (entityNear instanceof EntityLivingBase) {
						((EntityLivingBase) entityNear).addPotionEffect(new PotionEffect(MobEffects.WITHER, 80, 0));
					}
				}
			}
		}
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0,
				new ModelResourceLocation(Reference.MODID + ":weapons/cclass/rancid_poison", "inventory"));
	}
}
