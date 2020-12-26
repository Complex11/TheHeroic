package system00.theheroic.items.weapons.Bclass;

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

public class FruitNinja extends ToolSword {
	
	public static float damage = 2;

	public FruitNinja(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabB);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §bB");
		tooltip.add("§d§lPassive: §r§7Normal attacks deal 2 base damage + §6Power Of The Fruit§7. After each attack, gain §6Power Of The Fruit§7 equal to 25% of the target's remaining health. §6Power Of The Fruit§7 cannot exceed 50. If §6Power Of The Fruit§7 is greater than 40, add a Golden Apple to the wielder's inventory. Otherwise, add a regular apple.");
		tooltip.add("§d§lSpecial Ability: §r§7If §6Power Of The Fruit§7 is less than or equal to 20, gain §2Speed[3]§7. If §6Power Of The Fruit§7 is greater than 20, gain §2Speed[6]§7. If §6Power Of The Fruit§7 is greater than 40, gain §2Speed[9]§7. §fCooldown: 50 seconds.");
		tooltip.add("§6Power Of The Fruit: ");
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		target.attackEntityFrom(DamageSource.GENERIC, damage);
		target.hurtResistantTime = 0;
		damage += target.getHealth() * 0.25;
		if (attacker instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) attacker;
			if (damage > 40) {
				player.inventory.addItemStackToInventory(new ItemStack(Items.GOLDEN_APPLE));
			} else {
				player.inventory.addItemStackToInventory(new ItemStack(Items.APPLE));
			}
		}
		HeroicUtil.damageAndCheckItem(stack, 1);
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		if (damage > 40) {
			player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1200, 8, false, false));
		} else if (damage > 20) {
			player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1200, 5, false, false));
		} else {
			player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1200, 2, false, false));
		}
		HeroicUtil.damageAndCheckItem(item, 1);
		player.getCooldownTracker().setCooldown(this, 1000);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		if (damage > 50) {
			damage = 50;
		}
    }
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/bclass/fruit_ninja", "inventory"));
	}
}
