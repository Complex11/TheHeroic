package complex11.theheroic.items.weapons.Cclass;

import complex11.theheroic.Main;
import complex11.theheroic.init.ModPotions;
import complex11.theheroic.items.tool.ToolSword;
import complex11.theheroic.util.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class GlassyEdge extends ToolSword {

	public static float shatter = 0;
	
	public GlassyEdge(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabC);
	}
	
	@Override
	public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity) {
		if (entity instanceof EntityLivingBase) {
			entity.attackEntityFrom(DamageSource.causePlayerDamage(player), 1.0f + shatter);
			shatter++;
			if (shatter > 12) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(ModPotions.BLEED_EFFECT, 200, 0, false, false));
				shatter = 0;
			}
		}
		return false;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		if (player.inventory.hasItemStack(new ItemStack(Blocks.GLASS))) {
			player.inventory.clearMatchingItems(Item.getItemFromBlock(Blocks.GLASS), 0, 10, null);
		}
		item.setItemDamage(item.getItemDamage() - 10);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
    }
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/cclass/glassy_edge", "inventory"));
	}
	
}
