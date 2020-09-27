package complex11.theheroic.items.weapons.Cclass;

import java.util.Random;

import complex11.theheroic.Main;
import complex11.theheroic.items.tool.ToolSword;
import complex11.theheroic.util.HeroicUtil;
import complex11.theheroic.util.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class BladeOfMinorWounds extends ToolSword {

	private static Random rand = new Random();
	
	private static PotionEffect[] effects = { new PotionEffect(MobEffects.ABSORPTION, 200, 0, false, false),
			new PotionEffect(MobEffects.REGENERATION, 200, 0, false, false),
			new PotionEffect(MobEffects.SPEED, 200, 0, false, false) };

	public BladeOfMinorWounds (String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabB);
	}
	
	@Override
	public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity) {
		if (entity instanceof EntityLivingBase && player.getHealth() > player.getMaxHealth() * 0.5) {
			entity.attackEntityFrom(DamageSource.MAGIC, 5);
		} else {
			player.setHealth(player.getHealth() + 1);
			return true;
		}
		return false;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		player.addPotionEffect(effects[rand.nextInt(3)]);
		HeroicUtil.damageAndCheckItem(item, 1);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
    }
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/bclass/blade_of_minor_wounds", "inventory"));
	}
}
