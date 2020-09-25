package complex11.theheroic.items.misc;

import java.util.Random;

import complex11.theheroic.init.ModItems;
import complex11.theheroic.items.ItemBase;
import complex11.theheroic.util.HeroicUtil;
import complex11.theheroic.util.interfaces.IHasModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RealityFragment extends ItemBase implements IHasModel {

	public RealityFragment(String name) {
		super(name);
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			Random rand = player.getRNG();
			if (rand.nextDouble() > 0.9999) {
				HeroicUtil.SpecialTeleport(player.posX + rand.nextInt(10), player.posY + rand.nextInt(5),
						player.posZ + rand.nextInt(10), player);
			}
		}
	}
	
}
