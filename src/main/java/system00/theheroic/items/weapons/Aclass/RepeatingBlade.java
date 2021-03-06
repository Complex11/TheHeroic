package system00.theheroic.items.weapons.Aclass;

import com.google.common.base.Predicates;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import system00.theheroic.items.tool.ToolSword;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.Reference;

import javax.annotation.Nullable;
import java.util.List;

public class RepeatingBlade extends ToolSword {

	private DamageSource pierce = new DamageSource("pierce").setDamageBypassesArmor();

	public RepeatingBlade(String name, ToolMaterial material) {
		super(name, material);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("�9�lClass: �aA");
		tooltip.add("�d�lPassive: �r�7Deal 12 damage. If target has less than 80% of its total health, deal an additional 12 armor-piercing damage.");
		tooltip.add("�d�lSpecial Ability: �r�7Deals damage to all entities in a radius[5]. For each entity in this radius, damage is multiplied. �fCooldown: 0 seconds.");
	}
	
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		target.attackEntityFrom(DamageSource.GENERIC, 12);
		if (target.getHealth() < target.getMaxHealth() * 0.8) {
			target.attackEntityFrom(pierce, 12);
		}
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		AxisAlignedBB bb = player.getEntityBoundingBox();
		bb = bb.grow(5.0, 2.0, 5.0);
		List<Entity> list = worldIn.getEntitiesInAABBexcluding(player, bb, Predicates.instanceOf(EntityLivingBase.class));
		list.removeIf(t -> t instanceof EntityPlayer);
		int size = list.size();
		for (Entity entity : list) {
			if (entity instanceof EntityLivingBase) {
				HeroicUtil.spawnParticleAtEntity((EntityLivingBase) entity, EnumParticleTypes.FLAME, 8);
				HeroicUtil.StackAttack(4, size, (EntityLivingBase) entity);

			}
		}
		HeroicUtil.damageAndCheckItem(item, 1);
		player.playSound(SoundEvents.ENTITY_ELDER_GUARDIAN_CURSE, 0.6f, 0.7f);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/aclass/repeating_blade", "inventory"));
	}
}
