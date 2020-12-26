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
import java.util.Random;

public class BladeOfMinorWounds extends ToolSword {

	private static float damage = 6.0f;
	private static Random rand = new Random();
	
	private static PotionEffect[] effects = { new PotionEffect(MobEffects.ABSORPTION, 200, 3, false, false),
			new PotionEffect(MobEffects.REGENERATION, 200, 0, false, false),
			new PotionEffect(MobEffects.SPEED, 200, 0, false, false),
			new PotionEffect(MobEffects.STRENGTH, 200, 0, false, false),
			new PotionEffect(MobEffects.HASTE, 200, 0, false, false),
			new PotionEffect(MobEffects.NIGHT_VISION, 200, 0, false, false),
			new PotionEffect(MobEffects.FIRE_RESISTANCE, 200, 0, false, false),
			new PotionEffect(MobEffects.JUMP_BOOST, 200, 1, false, false)};

	public BladeOfMinorWounds (String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabC);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §eA");
		tooltip.add("§d§lPassive: §r§7Normal attacks deal damage based on wielder's health. If the wielder's health is greater than half their total health, deal 6 damage. Otherwise, deal 3 damage and restore 1 health.");
		tooltip.add("§d§lSpecial Ability: §r§7Gain a random positive effect and consume 5 durability. §fCooldown: 0 seconds.");
		tooltip.add("§4§lAura: §r§70.0025 chance to restore 2 health while held.");
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (attacker.getHealth() > attacker.getMaxHealth() * 0.5) {
			target.attackEntityFrom(DamageSource.MAGIC, damage);
		} else {
			attacker.setHealth(attacker.getHealth() + 1);
			target.attackEntityFrom(DamageSource.GENERIC, 3);
			return true;
		}
		HeroicUtil.damageAndCheckItem(stack, 1);
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		player.addPotionEffect(effects[rand.nextInt(3)]);
		HeroicUtil.damageAndCheckItem(item, 5);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		if (entity instanceof EntityLivingBase) {
			EntityLivingBase entityLiving = (EntityLivingBase) entity;
			if (entityLiving.getHealth() < entityLiving.getMaxHealth() && rand.nextInt(400) < 2) {
				entityLiving.setHealth(entityLiving.getHealth() + 2);
			}
		}
    }
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/cclass/blade_of_minor_wounds", "inventory"));
	}
}
