package complex11.theheroic.items.tool;

import complex11.theheroic.Main;
import complex11.theheroic.init.ModItems;
import complex11.theheroic.util.interfaces.IHasModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ToolSword extends ItemSword implements IHasModel {

	public ToolSword(String name, ToolMaterial material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity) {
		return false;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
    }
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}