package complex11.theheroic.items.weapons.Aclass;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicates;

import complex11.theheroic.Main;
import complex11.theheroic.init.ModPotions;
import complex11.theheroic.items.tool.ToolSword;
import complex11.theheroic.util.HeroicUtil;
import complex11.theheroic.util.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class DeathMark extends ToolSword {

	public DeathMark(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabA);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add(I18n.format("Hitting entities with this marks them for death."));
		tooltip.add(I18n.format("Right-click to send them to Hell!"));
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		super.onLeftClickEntity(stack, player, entity);
		if (entity instanceof EntityLivingBase) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(ModPotions.DEATHMARK_EFFECT));
		}
		return false;
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
		HeroicUtil.damageAndCheckItem(stack);
        return true;
    }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		AxisAlignedBB bb = player.getEntityBoundingBox();
		bb = bb.grow(30.0, 10, 30.0);
		List<Entity> list = worldIn.getEntitiesInAABBexcluding(player, bb, Predicates.instanceOf(EntityLivingBase.class));
		list.removeIf(t -> t instanceof EntityPlayer);
		for (Entity entity : list) {
			if (((EntityLivingBase) entity).isPotionActive(ModPotions.DEATHMARK_EFFECT)) {
				entity.setDead();
				player.setHealth(player.getHealth() * 0.5f);
				HeroicUtil.spawnParticleAtEntity((EntityLivingBase) entity, EnumParticleTypes.SMOKE_NORMAL, 40);
			}
		}
		HeroicUtil.damageAndCheckItem(item);
		player.playSound(SoundEvents.ENTITY_WITHER_AMBIENT, 0.5f, 0.45f);
		player.getCooldownTracker().setCooldown(this, 60);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/aclass/death_mark", "inventory"));
	}
}
