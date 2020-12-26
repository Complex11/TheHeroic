package system00.theheroic.items.weapons.Sclass;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
import system00.theheroic.init.ModItems;
import system00.theheroic.init.ModPotions;
import system00.theheroic.items.ItemBase;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.Reference;

import javax.annotation.Nullable;
import java.util.List;

public class GodOfThunder extends ItemBase {

	public static DamageSource thundergod = new DamageSource("thundergod").setDamageAllowedInCreativeMode()
			.setDamageBypassesArmor();

	public GodOfThunder(String name) {
		super(name);
		this.setMaxDamage(30000);
		this.setMaxStackSize(1);
		setCreativeTab(Main.heroicweaponstabS);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §1S"); // e = C, b = B, a = A, 1 = S
		tooltip.add("§d§lPassive: §r§7Deal 10 magic damage and afflict target with 100 seconds of §2Target Of Thunder§7.");
		tooltip.add("§d§lSpecial Ability: §r§7Call down the wrath of a thousand storms. §fCooldown: 10 seconds.");
		tooltip.add("§4§lAura: §r§3Flight§7 while this is in inventory. While holding this, all damaged is reduced to 1 and immune to fire.");
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		target.attackEntityFrom(thundergod, 10);
		target.addPotionEffect(new PotionEffect(ModPotions.THUNDER, 2000, 0, false, false));
		HeroicUtil.damageAndCheckItem(stack, 1);
		return true;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		List<EntityLivingBase> list = HeroicUtil.getEntitiesWithinRadius(50, player.posX, player.posY, player.posZ,
				worldIn);
		list.remove(player);
		for (EntityLivingBase entity : list) {
			for (int i = 0; i < 5; i++) {
				HeroicUtil.SummonLightningAt(worldIn, entity.posX, entity.posY, entity.posZ);
			}
			entity.attackEntityFrom(thundergod, 1000);
		}
		player.getCooldownTracker().setCooldown(this, 200);
		HeroicUtil.damageAndCheckItem(item, 1);
		player.playSound(SoundEvents.ENTITY_LIGHTNING_THUNDER, 3f, 1f);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (player.inventory.hasItemStack(new ItemStack(ModItems.GOD_OF_THUNDER))) {
				player.addPotionEffect(new PotionEffect(ModPotions.FLIGHT, 200, 0, false, false));
			}
		}
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0,
				new ModelResourceLocation(Reference.MODID + ":weapons/sclass/god_of_thunder", "inventory"));
	}
}
