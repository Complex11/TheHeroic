package system00.theheroic.items.weapons.Aclass;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import system00.theheroic.Main;
import system00.theheroic.items.tool.ToolSword;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class QuickDraw extends ToolSword {
	
	public static float damage = 3;
	private static Random rand;
	
	public QuickDraw(String name, ToolMaterial material) {
		super(name, material);
		setMaxDamage(1000);
		setCreativeTab(Main.heroicweaponstabA);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §aA"); //e = C, b = B, a = A, 1 = S
		tooltip.add("§d§lPassive: §r§7Deal 3 damage. Roll 50% for an additional 3 magic damage.");
		tooltip.add("§d§lSpecial Ability: §r§7Resets cooldown for all other items in inventory. §fCooldown: 10 seconds.");
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		target.attackEntityFrom(DamageSource.GENERIC, damage);
		if (rand.nextInt(2) == 1) {
			target.attackEntityFrom(DamageSource.MAGIC, 3);
		}
		HeroicUtil.damageAndCheckItem(stack, 1);
        return true;
    }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		List<ItemStack> inventory = player.inventory.mainInventory;
		for (ItemStack itemIn : inventory) {
			player.getCooldownTracker().removeCooldown(itemIn.getItem());
		}
		player.getCooldownTracker().setCooldown(this, 200);
		HeroicUtil.damageAndCheckItem(item, 1);
		player.playSound(SoundEvents.BLOCK_NOTE_GUITAR, 1f, 1f);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
    }
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/aclass/quick_draw", "inventory"));
	}
}
