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
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.client.model.ModelLoader;
import system00.theheroic.Main;
import system00.theheroic.items.tool.ToolSword;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.Reference;

import javax.annotation.Nullable;
import java.util.List;

public class BadWeather extends ToolSword {

	public static float damage = 10;
	
	public BadWeather(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabB);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §bB"); // e = C, b = B, a = A, 1 = S
		tooltip.add("§d§lPassive: §r§7Normal attacks deal 10 damage. Deal 4x damage and §2Lifesteal§7 if raining.");
		tooltip.add("§d§lSpecial Ability: §r§7Summon a thunderstorm for 20 durability. §fCooldown: 10 seconds.");
		tooltip.add("§4§lAura: §r§2Strength[3] §7while held if it is raining.");
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (attacker.world.isRaining()) {
			HeroicUtil.Lifesteal(damage * 4, attacker, target);
			HeroicUtil.SummonLightningAt(attacker.world, target.posX, target.posY, target.posZ);
		} else {
			target.attackEntityFrom(DamageSource.GENERIC, damage);
		}
		HeroicUtil.damageAndCheckItem(stack, 1);
		return true;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		WorldInfo info = worldIn.getWorldInfo();
		info.setCleanWeatherTime(0);
		info.setRainTime(1000);
		info.setThunderTime(1000);
		info.setRaining(true);
		info.setThundering(true);
		worldIn.setRainStrength(12);
		worldIn.setThunderStrength(12);
		List<EntityLivingBase> list = HeroicUtil.getEntitiesWithinRadius(20, player.posX, player.posY, player.posZ,
				worldIn);
		list.remove(player);
		for (EntityLivingBase entity : list) {
			for (int i = 0; i < 2; i++) {
				HeroicUtil.SummonLightningAt(worldIn, entity.posX, entity.posY, entity.posZ);
			}
		}
		player.getCooldownTracker().setCooldown(this, 200);
		HeroicUtil.damageAndCheckItem(item, 20);
		player.playSound(SoundEvents.BLOCK_END_GATEWAY_SPAWN, 1f, 0.3f);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		if (world.isRaining() && entity instanceof EntityLivingBase) {
			EntityLivingBase entityLiving = (EntityLivingBase) entity;
			entityLiving.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 200, 2, false, false));
		}
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0,
				new ModelResourceLocation(Reference.MODID + ":weapons/bclass/bad_weather", "inventory"));
	}
}
