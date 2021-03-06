package system00.theheroic.items.weapons.Sclass;

import net.minecraft.client.gui.FontRenderer;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import system00.theheroic.Main;
import system00.theheroic.init.ModPotions;
import system00.theheroic.items.tool.ToolSword;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.NameFormatRenderer;
import system00.theheroic.util.Reference;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class SchrodingersGift extends ToolSword {

	public static final String NBT_KEY = "does_not_exist";
	public static boolean realityBend = false;
	public static boolean realityWarp = false;
	public static Random rand = new Random();
	
	public SchrodingersGift(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabS);
	}

	@SideOnly(Side.CLIENT)
	@Nullable
	@Override
	public FontRenderer getFontRenderer(ItemStack stack) {
		return NameFormatRenderer.get();
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("�9�lClass: �1S");
		tooltip.add("�d�lPassive: �r�7Normal attacks trap targets in Limbo, making their status �3Unknown�7. Attacking �3Unknown �7targets remove them from this plane of existence.");
		tooltip.add("�d�lSpecial Ability: �r�7Gain 11 seconds of �2Stopped Time[1]�7. �fCooldown: 20 seconds.");
		tooltip.add("�d�lBonus Ability: �r�7For each entity in a radius[25], gain 10 seconds of �2Regeneration[4]�7, �2Resistance[2]�7 and �2Outer-Dimensional[1]�7. �fCooldown: 30 seconds.");
		tooltip.add("�d�lBonus Ability II: �r�7Teleports a maximum of 100 blocks in the direction faced. �fCooldown: 4 seconds.");
		tooltip.add("�4�lAura: �r�7If the time contains �2\"11:11\"�7, add 1 Bedrock to the wielder's inventory.");
		tooltip.add("�6�lDeath's Rites: �r�7If the wielder of this would have died, negate death instead. Only activates once.");
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (target.getEntityData().getBoolean(NBT_KEY)) {
			attacker.world.removeEntityDangerously(target);
			return true;
		} else {
			((EntityLiving) target).setNoAI(true);
			((EntityLiving) target).hurtTime = 0;
			target.getEntityData().setBoolean(NBT_KEY, true);
			target.setSilent(true);
			target.extinguish();
			target.setFire(0);
			target.setGlowing(true);
			target.setInvisible(true);
			HeroicUtil.spawnParticleAtEntity(target, EnumParticleTypes.SUSPENDED_DEPTH, 80);
		}
		attacker.playSound(SoundEvents.AMBIENT_CAVE, 1.0f, 1.0f);
		HeroicUtil.damageAndCheckItem(stack, 1);
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		if (player.isSneaking() && !realityWarp) {
			List<EntityLivingBase> list = HeroicUtil.getEntitiesWithinRadius(25, player.posX, player.posY, player.posZ, worldIn);
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200 * (list.size() + 1), 3, false, false));
			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200 * (list.size() + 1), 1, false, false));
			player.addPotionEffect(new PotionEffect(ModPotions.FOUR_DIMENSION_EFFECT, 200 * (list.size() + 1), 0, false, false));
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
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/sclass/schrodingers_gift", "inventory"));
	}
}
