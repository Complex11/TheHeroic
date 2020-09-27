package complex11.theheroic.items.weapons.Cclass;

import java.util.List;

import javax.annotation.Nullable;

import complex11.theheroic.Main;
import complex11.theheroic.items.tool.ToolSword;
import complex11.theheroic.util.HeroicUtil;
import complex11.theheroic.util.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
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

public class SpeedsterSword extends ToolSword {

	private static double count = 0;

	public SpeedsterSword(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabC);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §eC");
		tooltip.add("§d§lPassive: §7Normal attacks build §6Speedy Stacks§7 and do 4 damage.");
		tooltip.add("§d§lSpecial Ability: §7Consumes 10 §6Speedy Stacks§7 and applies §2Speed[3] for 1 minute.");
		tooltip.add("§4§lAura: §7While the wielder has more than 20 §6Speedy Stacks§7, grant §2Speed[1]§7.");
	}
	
	@Override
	public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity) {
		if (entity instanceof EntityLivingBase) {
			count += 0.5;
			entity.attackEntityFrom(DamageSource.GENERIC, 4);
		}
		return false;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		if (count > 10) {
			count -= 10;
			player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1200, 2, false, false));
			HeroicUtil.damageAndCheckItem(item, 1);
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		if (count > 20 && entity instanceof EntityLivingBase) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SPEED, 200, 0, false, false));
		}
    }
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/cclass/speedster_sword", "inventory"));
	}
}
