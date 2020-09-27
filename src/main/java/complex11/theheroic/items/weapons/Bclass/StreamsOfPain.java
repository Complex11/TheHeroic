package complex11.theheroic.items.weapons.Bclass;

import complex11.theheroic.Main;
import complex11.theheroic.items.tool.ToolSword;
import complex11.theheroic.util.HeroicUtil;
import complex11.theheroic.util.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class StreamsOfPain extends ToolSword {
	
	public static String NBT_KEY = "streams_of_pain";
	public static int air = 300;
	
	public StreamsOfPain(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabB);
	}
	
	@Override
	public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity) {
		if (entity instanceof EntityLivingBase) {
			if (!entity.getEntityData().getBoolean(NBT_KEY)) {
				entity.getEntityData().setBoolean(NBT_KEY, true);
				entity.attackEntityFrom(DamageSource.DROWN, 8);
			} else {
				entity.attackEntityFrom(DamageSource.DROWN, 12);
			}
		}
		return false;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);

		HeroicUtil.damageAndCheckItem(item, 1);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
    }
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/bclass/streams_of_pain", "inventory"));
	}
}
