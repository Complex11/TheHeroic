package system00.theheroic.items.weapons;

import java.util.List;

import javax.annotation.Nullable;

import system00.theheroic.Main;
import system00.theheroic.items.ItemBase;
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
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class TemplateNonSword extends ItemBase {

	public TemplateNonSword(String name) {
		super(name);
		this.setMaxDamage(3000);
		setCreativeTab(Main.heroicweaponstabA);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §"); //e = C, b = B, a = A, 1 = S
		tooltip.add("§d§lPassive: §r§7");
		tooltip.add("§d§lSpecial Ability: §r§7 . §fCooldown: 50 seconds.");
		tooltip.add("§4§lAura: §r§7");
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		
		HeroicUtil.damageAndCheckItem(stack, 1);
        return true;
    }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);

		player.getCooldownTracker().setCooldown(this, 200);
		HeroicUtil.damageAndCheckItem(item, 1);
		player.playSound(SoundEvents.BLOCK_PORTAL_TRAVEL, 0.44f, 1.8f);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
    }
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/cclass/", "inventory"));
	}
}