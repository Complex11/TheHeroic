package system00.theheroic.items.weapons.Cclass;

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
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import system00.theheroic.Main;
import system00.theheroic.init.ModPotions;
import system00.theheroic.items.tool.ToolSword;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.Reference;

import javax.annotation.Nullable;
import java.util.List;

public class Smokescreen extends ToolSword {
	
	public static float damage = 4;
	
	public Smokescreen(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabC);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §eC"); //e = C, b = B, a = A, 1 = S
		tooltip.add("§d§lPassive: §r§7Deal 4 damage. If in §3Smokescreen§7, deal 8 damage instead.");
		tooltip.add("§d§lSpecial Ability: §r§7Gain a §3Smokescreen§7 for 8 seconds. You are invisible to all entities during this period. §fCooldown: 25 seconds.");
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (attacker.isPotionActive(ModPotions.SMOKESCREEN_EFFECT)) {
			target.attackEntityFrom(DamageSource.GENERIC, 8);
		} else {
			target.attackEntityFrom(DamageSource.GENERIC, damage);
		}
		HeroicUtil.damageAndCheckItem(stack, 1);
        return true;
    }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		player.addPotionEffect(new PotionEffect(ModPotions.SMOKESCREEN_EFFECT, 160, 0, false, false));
		player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 160, 1, false, false));
		player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 160, 1, false, false));
		player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 160, 0, false, false));
		player.getCooldownTracker().setCooldown(this, 500);
		HeroicUtil.damageAndCheckItem(item, 1);
		player.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 0.85f, 1.0f);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
    }
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/cclass/smokescreen", "inventory"));
	}
}
