package system00.theheroic.items.weapons.Bclass;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
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
import system00.theheroic.Main;
import system00.theheroic.items.tool.ToolSword;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.Reference;

import javax.annotation.Nullable;
import java.util.List;

public class Burster extends ToolSword {
	
	public static float damage = 0;
	
	public Burster(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabB);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §bB");
		tooltip.add("§d§lPassive: §r§7Normal attacks deal damage based on §6Burst§7. Each hit halves §6Burst§7.");
		tooltip.add("§d§lSpecial Ability: §r§7Double §6Burst§7 for 20 durability. §fCooldown: 2 seconds.");
		tooltip.add("§4§lAura: §r§7Gain +2 §6Burst§7 per second. §6Burst§7 is capped at 1000.");
		tooltip.add("§6Burst: ");
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		target.attackEntityFrom(DamageSource.GENERIC, damage);
		damage /= 2;
		HeroicUtil.damageAndCheckItem(stack, 1);
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		damage = damage * 2f;
		HeroicUtil.damageAndCheckItem(item, 20);
		player.getCooldownTracker().setCooldown(this, 40);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		if (entity instanceof EntityLivingBase) {
			if (world.getTotalWorldTime() % 20 == 0) {
				damage++;
				if (damage > 1000) {
					damage = 1000;
				}
			}
		}
    }
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/bclass/burster", "inventory"));
	}
}
