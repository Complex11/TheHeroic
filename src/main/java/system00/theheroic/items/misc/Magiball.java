package system00.theheroic.items.misc;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import system00.theheroic.entity.misc.EntityMagiball;
import system00.theheroic.init.ModItems;
import system00.theheroic.items.ItemBase;
import system00.theheroic.util.interfaces.IHasModel;

import javax.annotation.Nullable;
import java.util.List;

public class Magiball extends ItemBase implements IHasModel {

	public Magiball(String name) {
		super(name);
		ModItems.ITEMS.add(this);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§7Can be thrown. Entities hit by this are slowed, immolated and struck by lightning.");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		Vec3d look = playerIn.getLookVec();
		EntityMagiball magiball = new EntityMagiball(worldIn, 1.0d, 1.0d, 1.0d);
		magiball.setPosition(playerIn.posX + look.x * 1.5D, playerIn.posY + look.y * 1.5D + 1.5D, playerIn.posZ + look.z * 1.5D);
		magiball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0f, 2.0f, 0.5f);
		if (!worldIn.isRemote) {
			worldIn.spawnEntity(magiball);
		}
		worldIn.playSound(null, playerIn.getPosition(), SoundEvents.BLOCK_LAVA_POP, SoundCategory.PLAYERS, 1.4f, 1.0f);
		if (!playerIn.capabilities.isCreativeMode) {
			stack.shrink(1);
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}
}
