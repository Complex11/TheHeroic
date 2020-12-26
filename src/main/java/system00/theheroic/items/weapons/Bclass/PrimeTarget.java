package system00.theheroic.items.weapons.Bclass;

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
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import system00.theheroic.Main;
import system00.theheroic.items.tool.ToolSword;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.Reference;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class PrimeTarget extends ToolSword {

	public static float damage = 0;
	public static int bound = 4;
	private static Random rand = new Random();

	public PrimeTarget(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabB);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §bB"); // e = C, b = B, a = A, 1 = S
		tooltip.add("§d§lPassive: §r§7Roll a number between 1 and §6Bound§7. Sum all primes from 1 to this number and deal this damage to the target. Reduce bound by 5. The minimum bound is 0.");
		tooltip.add("§d§lSpecial Ability: §r§7Increase §6Bound§7 by 5. §6Bound§7 cannot go beyond 40. §fCooldown: 20 seconds.");
		tooltip.add("§d§lBonus Ability: §r§7Roll a 20% chance for §2Regeneration[5]§7 for 60 seconds§7. §fCooldown: 20 seconds.");
		tooltip.add("§4§lAura: §r§2Resistance[1] §7while held if §6Bound§7 is greater than 20.");
		tooltip.add("§6Bound: ");
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		int store = rand.nextInt(bound);
		HeroicUtil.PrimeAttack((float) store, target);
		int dmg = HeroicUtil.SumPrimes((float) store);
		if (attacker instanceof EntityPlayer) {
			HeroicUtil.SendMsgToPlayer("Dealt " + dmg + " damage.", (EntityPlayer) attacker);
		}
		HeroicUtil.damageAndCheckItem(stack, 1);
		if (bound - 5 < 0) {
			bound = 0;
		} else {
			bound -= 5;
		}
        return true;
    }

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		if (!player.isSneaking()) {
			bound += 2.5;
			player.getCooldownTracker().setCooldown(this, 400);
			HeroicUtil.damageAndCheckItem(item, rand.nextInt(bound));
		} else {
			int roll = rand.nextInt(bound);
			if (roll > bound * 0.8) {
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 1200, 4));
			}
			player.getCooldownTracker().setCooldown(this, 400);
		}
		player.playSound(SoundEvents.BLOCK_ANVIL_FALL, 0.44f, 1.8f);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		if (bound > 40) {
			bound = 40;
		}
		if (bound > 20 && entity instanceof EntityLivingBase) {
			EntityLivingBase entityLiving = (EntityLivingBase) entity;
			entityLiving.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE));
		}
 	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0,
				new ModelResourceLocation(Reference.MODID + ":weapons/bclass/prime_target", "inventory"));
	}
}
