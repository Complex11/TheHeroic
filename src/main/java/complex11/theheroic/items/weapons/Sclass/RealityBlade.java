package complex11.theheroic.items.weapons.Sclass;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import complex11.theheroic.Main;
import complex11.theheroic.init.ModPotions;
import complex11.theheroic.items.tool.ToolSword;
import complex11.theheroic.util.HeroicUtil;
import complex11.theheroic.util.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class RealityBlade extends ToolSword {

	public static final String NBT_KEY = "does_not_exist";
	public static boolean realityBend = false;
	public static boolean realityWarp = false;
	public static Random rand = new Random();
	
	public RealityBlade(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabS);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §6S");
		tooltip.add("§d§lPassive: §r§7Normal attacks trap targets in Limbo, making their status §6Unknown§7. Attacking §6Unknown §7targets remove them from this plane of existence.");
		tooltip.add("§d§lSpecial Ability: §r§7Gain 11 seconds of §2Stopped Time[1]§7. §fCooldown: 20 seconds.");
		tooltip.add("§d§lBonus Ability: §r§7For each entity in a radius[25], gain 10 seconds of §2Regeneration[4]§7, §2Resistance[2]§7 and §2Outer-Dimensional[1]§7. §fCooldown: 30 seconds.");
		tooltip.add("§d§lBonus Ability II: §r§7Teleports a maximum of 100 blocks in the direction faced. §fCooldown: 4 seconds.");
		tooltip.add("§4§lAura: §r§7If the time contains §2\"11:11\"§7, add 1 Bedrock to the wielder's inventory.");
	}
	
	@Override
	public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity) {
		if (entity instanceof EntityLiving) {
			if (entity.getEntityData().getBoolean(NBT_KEY)) {
				player.world.removeEntityDangerously(entity);
				return true;
			} else {
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
		}
		player.playSound(SoundEvents.AMBIENT_CAVE, 1.0f, 1.0f);
		return false;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		if (player.isSneaking() && !realityWarp) {
			List<EntityLivingBase> list = HeroicUtil.getEntitiesWithinRadius(25, player.posX, player.posY, player.posZ, worldIn);
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200 * list.size(), 3, false, false));
			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200 * list.size(), 1, false, false));
			player.addPotionEffect(new PotionEffect(ModPotions.FOUR_DIMENSION_EFFECT, 200 * list.size(), 0, false, false));
			player.getCooldownTracker().setCooldown(this, 600);
			player.playSound(SoundEvents.ENTITY_WITHER_DEATH, 0.8f, 0.35f);
		} else if (player.isSneaking() && realityWarp) {
			RayTraceResult traced = player.rayTrace(100, 1.0f);
			Vec3d look = traced.hitVec;
			HeroicUtil.SpecialTeleport(look.x, look.y, look.z, player);
			player.getCooldownTracker().setCooldown(this, 80);
			player.playSound(SoundEvents.ENTITY_ELDER_GUARDIAN_AMBIENT, 0.8f, 0.8f);
		} else {
			player.addPotionEffect(new PotionEffect(ModPotions.TIME_STOP_EFFECT, 220, 0, false, false));
			player.getCooldownTracker().setCooldown(this, 400);
			player.playSound(SoundEvents.ENTITY_ELDER_GUARDIAN_CURSE, 1.1f, 0.35f);
		}
		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		String string1 = "11:11";
		EntityPlayer player = (EntityPlayer) entity;
		if (java.time.LocalTime.now().toString().contains(string1) && entity instanceof EntityPlayer) {
			player.inventory.addItemStackToInventory(new ItemStack(Blocks.BEDROCK));
		}
    }

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/sclass/reality_blade", "inventory"));
	}
}
