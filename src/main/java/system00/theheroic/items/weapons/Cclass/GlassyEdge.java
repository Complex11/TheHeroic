package system00.theheroic.items.weapons.Cclass;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
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
import system00.theheroic.Main;
import system00.theheroic.init.ModPotions;
import system00.theheroic.items.tool.ToolSword;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.Reference;

import javax.annotation.Nullable;
import java.util.List;

public class GlassyEdge extends ToolSword {

	public static float shatter = 0;
	
	public GlassyEdge(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabC);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §eC");
		tooltip.add("§d§lPassive: §r§7Normal attacks build §6Shatter. §7An attack does [1 + §6Shatter§7] damage. Upon reaching 6 §6Shatter§7, the next attack will also apply §2Bleed[1] §7and reset §6Shatter.");
		tooltip.add("§d§lSpecial Ability: §r§7Consumes 4 Glass Blocks and repairs itself for 20 durability. §fCooldown: 0 seconds.");
		tooltip.add("§6Shatter: ");
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		target.attackEntityFrom(DamageSource.GENERIC, 1.0f + shatter);
		shatter++;
		if (shatter > 6) {
			target.addPotionEffect(new PotionEffect(ModPotions.BLEED_EFFECT, 200, 0, false, false));
			shatter = 0;
		}
		HeroicUtil.damageAndCheckItem(stack, 1);
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		if (player.inventory.hasItemStack(new ItemStack(Blocks.GLASS))) {
			player.inventory.clearMatchingItems(Item.getItemFromBlock(Blocks.GLASS), 0, 4, null);
		}
		item.setItemDamage(item.getItemDamage() - 20);
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
