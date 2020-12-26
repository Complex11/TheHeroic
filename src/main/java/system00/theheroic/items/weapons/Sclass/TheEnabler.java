package system00.theheroic.items.weapons.Sclass;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
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

public class TheEnabler extends ToolSword {
	
	public static float damage = 10;
	public static boolean enable = false;
	public static DamageSource uberpowered = new DamageSource("uberpowered").setDamageAllowedInCreativeMode()
			.setDamageBypassesArmor();
	private static Random rand;
	
	public TheEnabler(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabS);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §1S"); //e = C, b = B, a = A, 1 = S
		tooltip.add("§d§lPassive: §r§7Deal 10 damage.");
		tooltip.add("§d§lSpecial Ability: §r§7Apply §2Regeneration[10]§7 for 60 seconds. §fCooldown: 10 seconds.");
		tooltip.add("§d§lBonus Ability: §r§7Activate §3UBERCHARGE§7. §3UBERCHARGE§7 drains 1 durability per second.");
		tooltip.add("§4§lAura: §r§7While §3UBERCHARGE§7 is activated, your weapons have no cooldown.");
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		target.attackEntityFrom(DamageSource.GENERIC, damage);
		HeroicUtil.damageAndCheckItem(stack, 1);
        return true;
    }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 1200, 9, false, false));
		player.getCooldownTracker().setCooldown(this, 200);
		HeroicUtil.damageAndCheckItem(item, 1);
		player.playSound(SoundEvents.BLOCK_NOTE_HARP, 2f, 1f);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		if (enable && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			List<ItemStack> inventory = player.inventory.mainInventory;
			for (ItemStack itemIn : inventory) {
				player.getCooldownTracker().removeCooldown(itemIn.getItem());
			}
			if (entity.world.getTotalWorldTime() % 20 == 0) {
				HeroicUtil.damageAndCheckItem(itemstack, 1);
			}
		}
    }
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/sclass/the_enabler", "inventory"));
	}
}
