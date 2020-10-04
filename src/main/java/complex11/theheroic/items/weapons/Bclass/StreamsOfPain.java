package complex11.theheroic.items.weapons.Bclass;

import java.util.List;

import javax.annotation.Nullable;

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
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class StreamsOfPain extends ToolSword {

	public static String NBT_KEY = "streams_of_pain";
	public static int air = 300;
	public static boolean blessing = false;

	public StreamsOfPain(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabB);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §bB");
		tooltip.add("§d§lPassive: §r§7Normal attacks deal 8 damage and §3Drown §7targets. If the target is already §3Drowned§7, deal 12 extra damage and apply §2Absorption[2] §7to wielder. If the target's remaining health is less than 70% of it's total health, deal 14 extra damage and apply §2Speed[3] §7 to wielder.  If the target's remaining health is less than 40% of it's total health, deal 16 extra damage and apply §2Strength[3] §7and §2Water Breathing[1] §7to wielder.");
		tooltip.add("§d§lSpecial Ability: §r§7If §2Water Breathing§7 is active, activate §cBlessing§7. The next attack of this will deal damage equal to 80% of the target's total health.");
		tooltip.add("§4§lAura: §r§2Water Breathing[1] §7while held if §cBlessing§7 is active.");
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (blessing) {
			target.attackEntityFrom(DamageSource.DROWN, (float) (target.getMaxHealth() * 0.8));
			blessing = false;
		}
		if (!target.getEntityData().getBoolean(NBT_KEY)) {
			target.getEntityData().setBoolean(NBT_KEY, true);
			target.attackEntityFrom(DamageSource.DROWN, 8);
		} else {
			target.attackEntityFrom(DamageSource.DROWN, 12);
			target.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 800, 1, false, false));
			if (target.getHealth() < target.getMaxHealth() * 0.7) {
				target.attackEntityFrom(DamageSource.DROWN, 14);
				attacker.addPotionEffect(new PotionEffect(MobEffects.SPEED, 400, 2, false, false));
				if (target.getHealth() < target.getMaxHealth() * 0.4) {
					target.attackEntityFrom(DamageSource.DROWN, 16);
					attacker.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 400, 2, false, false));
					attacker.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 1200, 0, false, false));
				}
			}
			HeroicUtil.spawnParticleAtEntity(target, EnumParticleTypes.WATER_BUBBLE, 20);
		}
		HeroicUtil.damageAndCheckItem(stack, 1);
		return true;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		if (player.isPotionActive(MobEffects.WATER_BREATHING) && !blessing) {
			player.playSound(SoundEvents.BLOCK_WATER_AMBIENT, 2.0f, 1.0f);
			HeroicUtil.SendMsgToPlayer("Atlantis Blesses You...", player);
			player.getCooldownTracker().setCooldown(this, 600);
		}
		HeroicUtil.damageAndCheckItem(item, 1);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		if (blessing && entity instanceof EntityLivingBase) {
			EntityLivingBase entityLiving = (EntityLivingBase) entity;
			entityLiving.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING));
		}
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0,
				new ModelResourceLocation(Reference.MODID + ":weapons/bclass/streams_of_pain", "inventory"));
	}
}
