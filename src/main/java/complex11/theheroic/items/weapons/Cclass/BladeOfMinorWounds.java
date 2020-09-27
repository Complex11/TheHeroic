package complex11.theheroic.items.weapons.Cclass;

import java.util.List;
import java.util.Random;

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
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class BladeOfMinorWounds extends ToolSword {

	private static Random rand = new Random();
	
	private static PotionEffect[] effects = { new PotionEffect(MobEffects.ABSORPTION, 200, 0, false, false),
			new PotionEffect(MobEffects.REGENERATION, 200, 0, false, false),
			new PotionEffect(MobEffects.SPEED, 200, 0, false, false),
			new PotionEffect(MobEffects.STRENGTH, 200, 0, false, false) };

	public BladeOfMinorWounds (String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabC);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §bA");
		tooltip.add("§d§lPassive: §r§7Normal attacks deal damage based on wielder's health. If the wielder's health is greater than half their total, deal 5 damage. Otherwise, deal 2 damage.");
		tooltip.add("§d§lSpecial Ability: §r§7Gain a random positive effect and consume 10 durability. §fCooldown: 0 seconds.");
		tooltip.add("§4§lAura: §r§70.001 chance to restore 0.5 health while held.");
	}
	
	@Override
	public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity) {
		if (entity instanceof EntityLivingBase && player.getHealth() > player.getMaxHealth() * 0.5) {
			entity.attackEntityFrom(DamageSource.MAGIC, 5);
		} else {
			player.setHealth(player.getHealth() + 1);
			entity.attackEntityFrom(DamageSource.GENERIC, 2);
			return true;
		}
		return false;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		player.addPotionEffect(effects[rand.nextInt(3)]);
		HeroicUtil.damageAndCheckItem(item, 10);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		if (entity instanceof EntityLivingBase) {
			EntityLivingBase entityLiving = (EntityLivingBase) entity;
			if (entityLiving.getHealth() < entityLiving.getMaxHealth() && rand.nextInt(1000) < 2) {
				entityLiving.setHealth(entityLiving.getHealth() + 1);
			}
		}
    }
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/cclass/blade_of_minor_wounds", "inventory"));
	}
}
