package complex11.theheroic.items.weapons.Aclass;

import complex11.theheroic.Main;
import complex11.theheroic.items.ItemBase;
import complex11.theheroic.util.HeroicUtil;
import complex11.theheroic.util.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		
		return true;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		super.onLeftClickEntity(stack, player, entity);
		if (entity instanceof EntityLivingBase) {
			EntityLivingBase entityLiving = (EntityLivingBase) entity;
			if (entityLiving.getHealth() < (entityLiving.getMaxHealth() * 0.2)
					|| entityLiving.isPotionActive(MobEffects.GLOWING) || entityLiving.isGlowing()) {
				entityLiving.handleStatusUpdate((byte) 3);
				HeroicUtil.SendMsgToPlayer("§2" + entityLiving.getName() + " was divinely judged and found unworthy.", player);
			} else {
				HeroicUtil.Lifesteal(8, player, entityLiving);
			}
			player.playSound(SoundEvents.ENTITY_LIGHTNING_IMPACT, 1.6f, 1.0f);
			HeroicUtil.damageAndCheckItem(stack);
			return false;
		}
		return false;
	}
	
	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int itemslot, boolean isSelected) {
		if (!((EntityLivingBase) entity).isPotionActive(MobEffects.REGENERATION)) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 2, false, false));
		}
		if (!((EntityLivingBase) entity).isPotionActive(MobEffects.RESISTANCE)) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 0, false, false));
		}
    }
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/aclass/bulwarks_hallmark", "inventory"));
	}
}
