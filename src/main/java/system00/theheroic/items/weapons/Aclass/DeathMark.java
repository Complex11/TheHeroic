package system00.theheroic.items.weapons.Aclass;

import com.google.common.base.Predicates;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import system00.theheroic.Main;
import system00.theheroic.init.ModPotions;
import system00.theheroic.items.tool.ToolSword;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.Reference;

import javax.annotation.Nullable;
import java.util.List;

public class DeathMark extends ToolSword {

	private static float damage = 2;

	public DeathMark(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabA);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §aA");
		tooltip.add("§d§lPassive: §r§7Normal attacks deal 2 damage and apply §2Mark Of Death[1]§7 for 10 minutes.");
		tooltip.add("§d§lSpecial Ability: §r§7Kill all marked targets in a radius[25] Each entity killed this way costs 5% of the wielder's current health. §fCooldown: 30 seconds.");
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		target.attackEntityFrom(DamageSource.GENERIC, damage);
		target.addPotionEffect(new PotionEffect(ModPotions.DEATHMARK_EFFECT, 12000, 0, false, false));
		HeroicUtil.damageAndCheckItem(stack, 1);
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		if (!worldIn.isRemote) {
			AxisAlignedBB bb = player.getEntityBoundingBox();
			bb = bb.grow(25.0, 7.0, 25.0);
			List<Entity> list = worldIn.getEntitiesInAABBexcluding(player, bb, Predicates.instanceOf(EntityLivingBase.class));
			list.removeIf(t -> t instanceof EntityPlayer);
			float healthStore = player.getHealth();
			for (Entity entity : list) {
				if (((EntityLivingBase) entity).isPotionActive(ModPotions.DEATHMARK_EFFECT)) {
					HeroicUtil.spawnParticleAtEntity((EntityLivingBase) entity, EnumParticleTypes.SMOKE_LARGE, 40);
					((EntityLivingBase) entity).setDead();
					player.setHealth(player.getHealth() - healthStore * 0.05f);
				}
			}
		}
		HeroicUtil.damageAndCheckItem(item, 1);
		player.playSound(SoundEvents.ENTITY_WITHER_AMBIENT, 0.5f, 0.45f);
		player.getCooldownTracker().setCooldown(this, 600);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/aclass/death_mark", "inventory"));
	}
}
