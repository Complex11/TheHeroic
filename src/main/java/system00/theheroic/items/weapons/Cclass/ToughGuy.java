package system00.theheroic.items.weapons.Cclass;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
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

public class ToughGuy extends ToolSword {
	
	private static float damage = 4;
	
	public ToughGuy(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabC);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §eC");
		tooltip.add("§d§lPassive: §r§7Normal attacks §3Lifesteal§7 for 3 health. If the target has less than 35% health, §3Lifesteal§7 for another 6 health.");
		tooltip.add("§d§lSpecial Ability: §r§7Heal for 2 health. If already at maximum health, gain §2Strength[1] §7for 1 minute instead. §fCooldown: 7 seconds.");
		tooltip.add("§4§lAura: §r§2Resistance[1] §7while held.");
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		target.attackEntityFrom(DamageSource.GENERIC, damage);
		HeroicUtil.Lifesteal(3, attacker, target);
		if (target.getHealth() < target.getMaxHealth() * 0.35) {
			HeroicUtil.Lifesteal(6, attacker, target);
		}
		HeroicUtil.damageAndCheckItem(stack, 1);
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		player.setHealth(player.getHealth() + 2);
		if (player.getHealth() == player.getMaxHealth()) {
			player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 1200, 0, false, false));
		}
		player.getCooldownTracker().setCooldown(this, 140);
		HeroicUtil.damageAndCheckItem(item, 1);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		if (entity instanceof EntityLivingBase) {
			EntityLivingBase entityLiving = (EntityLivingBase) entity;
			entityLiving.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 0, false, false));
		}
    }
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/cclass/tough_guy", "inventory"));
	}
}
