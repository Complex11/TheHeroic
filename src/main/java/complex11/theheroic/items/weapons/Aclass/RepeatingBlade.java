package complex11.theheroic.items.weapons.Aclass;

import java.util.List;

import com.google.common.base.Predicates;

import complex11.theheroic.items.tool.ToolSword;
import complex11.theheroic.util.HeroicUtil;
import complex11.theheroic.util.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class RepeatingBlade extends ToolSword {

	public RepeatingBlade(String name, ToolMaterial material) {
		super(name, material);
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		super.onLeftClickEntity(stack, player, entity);
		if (entity instanceof EntityLivingBase) {
			for (int i = 0; i < 5; i++) {
				entity.attackEntityFrom(DamageSource.causePlayerDamage(player), (float) (3 * Math.pow(1.1, i)));
			}
		}
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		AxisAlignedBB bb = player.getEntityBoundingBox();
		bb = bb.grow(5.0, 2, 5.0);
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
