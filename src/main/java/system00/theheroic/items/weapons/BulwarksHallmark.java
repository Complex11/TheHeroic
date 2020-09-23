package system00.theheroic.items.weapons;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import system00.theheroic.Main;
import system00.theheroic.items.ItemBase;
import system00.theheroic.util.HeroicUtil;

public class BulwarksHallmark extends ItemBase {

	public BulwarksHallmark(String name) {
		super(name);
		this.setMaxDamage(26000);
		setCreativeTab(Main.heroicweaponstab);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (target.getHealth() < (target.getMaxHealth() * 0.2) || target.isPotionActive(MobEffects.GLOWING)) {
			target.handleStatusUpdate((byte) 3);
			ITextComponent deathMsg = new TextComponentString(
					"§2" + target.getName() + " was divinely judged and found unworthy.");
			if (attacker instanceof EntityPlayer) {
				Minecraft.getMinecraft().player.sendMessage(deathMsg);
				attacker.playSound(SoundEvents.ENTITY_LIGHTNING_THUNDER, 1.1f, 0.77f);
			}
		} else {
			HeroicUtil.Lifesteal(10, attacker, target);
			attacker.playSound(SoundEvents.ENTITY_ENDERMITE_AMBIENT, 1.0f, 1.0f);
		}
		HeroicUtil.damageAndCheckItem(stack);
        return true;
    }
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		
	}
}
