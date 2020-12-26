package system00.theheroic.items.misc;

import com.google.common.base.Predicates;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import system00.theheroic.init.ModItems;
import system00.theheroic.items.ItemBase;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.interfaces.IHasModel;

import javax.annotation.Nullable;
import java.util.List;

public class InfernusStar extends ItemBase implements IHasModel {

	public InfernusStar(String name) {
		super(name);
		ModItems.ITEMS.add(this);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§2Fire Resistance[1]§7 while held.");
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			List<EntityLivingBase> list = HeroicUtil.getEntitiesWithinRadius(3, entity.posX, entity.posY, entity.posZ,
					world);
			list.removeIf(Predicates.instanceOf(EntityPlayer.class));
			for (EntityLivingBase entityNear : list) {
				entityNear.setFire(2);
			}
			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 200, 0, false, false));
		}
	}
}
