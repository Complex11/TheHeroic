package system00.theheroic.items.weapons.Bclass;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import system00.theheroic.Main;
import system00.theheroic.items.tool.ToolSword;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.Reference;

import javax.annotation.Nullable;
import java.util.List;

public class Taskmaster extends ToolSword {

	private static float damage = 8;

	public Taskmaster(String name, ToolMaterial material) {
		super(name, material);
		setMaxDamage(10000);
		setCreativeTab(Main.heroicweaponstabB);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §bB");
		tooltip.add("§d§lPassive: §r§7Normal attacks deal 16 damage if the wielder has cobblestone in their inventory. Otherwise, deal 8 damage. This can also be used to break blocks.");
		tooltip.add("§d§lSpecial Ability: §r§7If the wielder has cobblestone in their inventory, apply §2Absorption[10]§7 for 6 minutes. Otherwise, apply §2Absorption[6]§7 for 2 minutes.");
		tooltip.add("§4§lAura: §r§2Haste[2]§7 while held.");
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		return 16.0F;
	}

	@Override
	public boolean canHarvestBlock(IBlockState blockIn) {
		return true;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (attacker instanceof EntityPlayer) {
			if (((EntityPlayer) attacker).inventory.hasItemStack(new ItemStack(Blocks.COBBLESTONE))) {
				target.attackEntityFrom(DamageSource.GENERIC, damage + 8);
			}
		} else {
			target.attackEntityFrom(DamageSource.GENERIC, damage);
		}
		HeroicUtil.damageAndCheckItem(stack, 1);
		return true;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		if (player.inventory.hasItemStack(new ItemStack(Blocks.COBBLESTONE))) {
			player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 7200, 9, false, false));
		} else {
			player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 2400, 5, false, false));
		}
		HeroicUtil.damageAndCheckItem(item, 1);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		if (entity instanceof EntityLivingBase) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.HASTE, 200, 1, false, false));
		}
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0,
				new ModelResourceLocation(Reference.MODID + ":weapons/bclass/taskmaster", "inventory"));
	}
}
