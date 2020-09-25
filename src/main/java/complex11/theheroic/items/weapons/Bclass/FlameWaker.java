package complex11.theheroic.items.weapons.Bclass;

import java.util.List;

import com.google.common.base.Predicates;

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
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class FlameWaker extends ToolSword {

	public static double killCount = 4;
	
	public FlameWaker(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabB);
	}
	
	@Override
	public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity) {
		if (entity instanceof EntityLivingBase) {
			if (entity.isBurning()) {
				entity.attackEntityFrom(DamageSource.LAVA, (float) killCount * 2);
				player.setHealth(player.getHealth() + 2);
			} else {
				entity.attackEntityFrom(DamageSource.LAVA, (float) killCount);
			}
			if (killCount < 10) {
				killCount += 0.5;
				if (!Double.toString(killCount).contains("\\.")) {
					HeroicUtil.SendMsgToPlayer("Hellfire: " + killCount, player);
				}
			} else if (killCount == 10) {
				killCount = 0;
				HeroicUtil.SendMsgToPlayer("Transforming Souls...", player);
				player.inventory.addItemStackToInventory(new ItemStack(Items.BLAZE_POWDER));
			}
		}
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		if (player.inventory.hasItemStack(new ItemStack(Items.BLAZE_POWDER))) {
			player.inventory.clearMatchingItems(Items.BLAZE_POWDER, 0, 10, null);
			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 3600, 1, false, false));
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		List<EntityLivingBase> list = HeroicUtil.getEntitiesWithinRadius(3, entity.posX, entity.posY, entity.posZ, world);
		list.removeIf(Predicates.instanceOf(EntityPlayer.class));
		for (Entity entityNear : list) {
			entityNear.setFire(2);
		}
    }
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/bclass/flamewaker", "inventory"));
	}
}
