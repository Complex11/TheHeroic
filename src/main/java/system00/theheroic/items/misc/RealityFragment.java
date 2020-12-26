package system00.theheroic.items.misc;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import system00.theheroic.init.ModItems;
import system00.theheroic.items.ItemBase;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.interfaces.IHasModel;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class RealityFragment extends ItemBase implements IHasModel {

	public RealityFragment(String name) {
		super(name);
		ModItems.ITEMS.add(this);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§70.3% chance to teleport to a random location in a radius[10] while held.");
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			Random rand = player.getRNG();
			if (rand.nextFloat() > 0.9 && rand.nextFloat() > 0.7 && rand.nextFloat() < 0.1) {
				HeroicUtil.SpecialTeleport(player.posX + rand.nextInt(10), player.posY + rand.nextInt(5),
						player.posZ + rand.nextInt(10), player);
			}
		}
	}
}
