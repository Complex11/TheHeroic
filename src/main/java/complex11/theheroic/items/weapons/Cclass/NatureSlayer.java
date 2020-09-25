package complex11.theheroic.items.weapons.Cclass;

import complex11.theheroic.Main;
import complex11.theheroic.items.tool.ToolSword;
import complex11.theheroic.util.HeroicUtil;
import complex11.theheroic.util.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class NatureSlayer extends ToolSword {

	private static int count = 0;
	
	public NatureSlayer(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabC);
	}
	
	@Override
	public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity) {
		if (entity instanceof EntityLivingBase) {
			player.inventory.addItemStackToInventory(new ItemStack(Items.STICK));
			count++;
			if (count > 30) {
				player.inventory.addItemStackToInventory(new ItemStack(Items.APPLE));
				count = 0;
			}
		}
		return false;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		if (player.inventory.hasItemStack(new ItemStack(Items.APPLE))) {
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 0, false, false));
			player.inventory.clearMatchingItems(Items.APPLE, 0, 1, null);
		}
		HeroicUtil.damageAndCheckItem(item);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
    }
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":cclass/nature_slayer", "inventory"));
	}
}
