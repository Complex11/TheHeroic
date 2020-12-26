package system00.theheroic.items.weapons.Cclass;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
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
import system00.theheroic.Main;
import system00.theheroic.items.tool.ToolSword;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.Reference;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class NatureSlayer extends ToolSword {

	public static int count = 0;
	private static float damage = 4.5f;
	private static Random rand;
	
	public NatureSlayer(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabC);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §eC");
		tooltip.add("§d§lPassive: §r§7Normal attacks deal 4.5 damage and give sticks. Every 7th attack gives an apple.");
		tooltip.add("§d§lSpecial Ability: §r§7Consumes an apple in exchange for §2Regeneration[1]§7 for 10 seconds. Roll a 50% chance to apply §2Regeneration[3]§7 instead. §fCooldown: 0 seconds.");
		tooltip.add("§6Apple Counter: ");
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (attacker instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) attacker;
			target.attackEntityFrom(DamageSource.CACTUS, damage);
			player.inventory.addItemStackToInventory(new ItemStack(Items.STICK));
			count++;
			if (count > 6) {
				player.inventory.addItemStackToInventory(new ItemStack(Items.APPLE));
				count = 0;
			}
		}
		HeroicUtil.damageAndCheckItem(stack, 1);
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		if (player.inventory.hasItemStack(new ItemStack(Items.APPLE))) {
			if (rand.nextInt(2) == 1) {
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 0, false, false));
			} else {
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 2, false, false));
			}
			player.inventory.clearMatchingItems(Items.APPLE, 0, 1, null);
		}
		HeroicUtil.damageAndCheckItem(item, 1);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
    }
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/cclass/nature_slayer", "inventory"));
	}
}
