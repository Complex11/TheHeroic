package complex11.theheroic.items.weapons.Sclass;

import java.util.List;

import com.google.common.base.Predicates;

import complex11.theheroic.Main;
import complex11.theheroic.init.ModPotions;
import complex11.theheroic.items.tool.ToolSword;
import complex11.theheroic.util.HeroicUtil;
import complex11.theheroic.util.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
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

public class RealityBlade extends ToolSword {

	public static final String NBT_KEY = "does_not_exist";
	public static boolean realityBend = false;
	
	public RealityBlade(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabS);
	}

	@Override
	public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity) {
		if (entity instanceof EntityLiving) {
			((EntityLiving) entity).setNoAI(true);
			((EntityLiving) entity).hurtTime = 0;
			entity.getEntityData().setBoolean(NBT_KEY, true);
			entity.setSilent(true);
			entity.extinguish();
			entity.setFire(0);
			entity.setGlowing(true);
			entity.setInvisible(true);
			HeroicUtil.spawnParticleAtEntity((EntityLivingBase) entity, EnumParticleTypes.SUSPENDED_DEPTH, 80);
		}
		player.playSound(SoundEvents.AMBIENT_CAVE, 1.0f, 1.0f);
		return false;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		if (player.isSneaking()) {
			List<EntityLivingBase> list = HeroicUtil.getEntitiesWithinRadius(50, player.posX, player.posY, player.posZ, worldIn);
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200 * list.size(), 3, false, false));
			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200 * list.size(), 3, false, false));
			player.addPotionEffect(new PotionEffect(ModPotions.FOUR_DIMENSION_EFFECT, 400, 0, false, false));
			player.getCooldownTracker().setCooldown(this, 600);
		} else {
			player.addPotionEffect(new PotionEffect(ModPotions.TIME_STOP_EFFECT, 220, 0, false, false));
			player.getCooldownTracker().setCooldown(this, 400);
		}
		player.playSound(SoundEvents.ENTITY_WITHER_DEATH, 0.8f, 0.35f);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		String string1 = "11:11:11";
		EntityPlayer player = (EntityPlayer) entity;
		if (java.time.LocalTime.now().toString().contains(string1) && entity instanceof EntityPlayer) {
			player.inventory.addItemStackToInventory(new ItemStack(Blocks.BEDROCK));
		}
		if (player.getHealth() < 2 && !realityBend) {
			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 4, false, false));
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 9, false, false));
			AxisAlignedBB bb = player.getEntityBoundingBox();
			bb = bb.grow(100.0, 100.0, 100.0);
			List<Entity> list = world.getEntitiesInAABBexcluding(player, bb, Predicates.instanceOf(Entity.class));
			for (Entity entityA : list) {
				entityA.setDead();
			}
			realityBend = true;
		}
    }

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/sclass/reality_blade", "inventory"));
	}
}
