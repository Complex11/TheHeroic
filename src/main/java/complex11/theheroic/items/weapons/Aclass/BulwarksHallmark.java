package complex11.theheroic.items.weapons.Aclass;

import java.util.List;

import javax.annotation.Nullable;

import complex11.theheroic.Main;
import complex11.theheroic.items.ItemBase;
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
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class BulwarksHallmark extends ItemBase {

	public BulwarksHallmark(String name) {
		super(name);
		this.setMaxDamage(26000);
		setCreativeTab(Main.heroicweaponstabA);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §aA");
		tooltip.add("§d§lPassive: §r§7Normal attacks lifesteal for 20% of the target's health.");
		tooltip.add(
				"§d§lSpecial Ability: §r§7Instantly kills targets which have less than 20% total health or are glowing.");
		tooltip.add("§4§lAura: §r§2Regeneration[2] §7and §2Resistance[1] §7while held.");
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (target.getHealth() < (target.getMaxHealth() * 0.2) || target.isPotionActive(MobEffects.GLOWING)
				|| target.isGlowing()) {
			target.handleStatusUpdate((byte) 3);
			if (attacker instanceof EntityPlayer) {
				HeroicUtil.SendMsgToPlayer("§2" + target.getName() + " was divinely judged and found unworthy.",
						(EntityPlayer) attacker);
			}
		} else {
			HeroicUtil.Lifesteal((float) (target.getHealth() * 0.2), attacker, target);
		}
		attacker.playSound(SoundEvents.ENTITY_LIGHTNING_IMPACT, 1.6f, 1.0f);
		HeroicUtil.damageAndCheckItem(stack, 1);
		return true;
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int itemslot, boolean isSelected) {
		if (!((EntityLivingBase) entity).isPotionActive(MobEffects.REGENERATION)) {
			((EntityLivingBase) entity)
					.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 1, false, false));
		}
		if (!((EntityLivingBase) entity).isPotionActive(MobEffects.RESISTANCE)) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 0, false, false));
		}
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0,
				new ModelResourceLocation(Reference.MODID + ":weapons/aclass/bulwarks_hallmark", "inventory"));
	}
}
