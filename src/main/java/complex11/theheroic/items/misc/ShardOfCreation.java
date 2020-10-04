package complex11.theheroic.items.misc;

import complex11.theheroic.init.ModItems;
import complex11.theheroic.items.ItemBase;
import complex11.theheroic.util.interfaces.IHasModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ShardOfCreation extends ItemBase implements IHasModel {

	public ShardOfCreation(String name) {
		super(name);
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 4, false, false));
		}
	}
}