package system00.theheroic.items.misc;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import system00.theheroic.init.ModItems;
import system00.theheroic.items.ItemBase;
import system00.theheroic.util.interfaces.IHasModel;

import javax.annotation.Nullable;
import java.util.List;

public class ShardOfCreation extends ItemBase implements IHasModel {

	public ShardOfCreation(String name) {
		super(name);
		ModItems.ITEMS.add(this);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§2Resistance[4]§7 while held.");
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 3, false, false));
		}
	}
}