package complex11.theheroic.items.weapons.Bclass;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicates;

import complex11.theheroic.Main;
import complex11.theheroic.items.tool.ToolSword;
import complex11.theheroic.util.HeroicUtil;
import complex11.theheroic.util.Reference;
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
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class FlameWaker extends ToolSword {

	public static double hitCount = 0;
	
	public FlameWaker(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabB);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §aB");
		tooltip.add("§d§lPassive: §r§7Normal attacks build §6Hellfire§7. An attack does [6 + §6Hellfire §7count] damage to burning targets, and restores 1 health, but does [4 + §6Hellfire §7count] damage to non-burning targets. Upon reaching 10 §6Hellfire§7, Hellfire is reset in exchange for 1 Blaze Powder.");
		tooltip.add("§d§lSpecial Ability: §r§7Consume 10 Blaze Powder for 3 minutes of §2Fire Resistance[1]§7. §fCooldown: 0 seconds.");
		tooltip.add("§4§lAura: §r§7Entities in a radius[3] are set on fire for 3 seconds.");
	}
	
	@Override
	public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity) {
		if (entity instanceof EntityLivingBase) {
			if (entity.isBurning()) {
				entity.attackEntityFrom(DamageSource.LAVA, (float) hitCount * 2 + 6);
				player.setHealth(player.getHealth() + 2);
			} else {
				entity.attackEntityFrom(DamageSource.LAVA, (float) hitCount + 4);
			}
			if (hitCount < 10) {
				hitCount += 0.5;
				if (!Double.toString(hitCount).contains(".5")) {
					HeroicUtil.SendMsgToPlayer("Hellfire: " + hitCount, player);
				}
			} else if (hitCount == 10) {
				hitCount = 0;
				HeroicUtil.SendMsgToPlayer("Transforming Souls...", player);
				player.inventory.addItemStackToInventory(new ItemStack(Items.BLAZE_POWDER));
			}
		}
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		if (player.inventory.hasItemStack(new ItemStack(Items.BLAZE_POWDER))) {
			player.inventory.clearMatchingItems(Items.BLAZE_POWDER, 0, 10, null);
			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 3600, 1, false, false));
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		if (entity.world.getTotalWorldTime() % 20 == 0) {
			HeroicUtil.spawnParticleCircleAroundEntity((EntityLivingBase) entity, EnumParticleTypes.FLAME, 3f);
		}
		List<EntityLivingBase> list = HeroicUtil.getEntitiesWithinRadius(3, entity.posX, entity.posY, entity.posZ, world);
		list.removeIf(Predicates.instanceOf(EntityPlayer.class));
		for (Entity entityNear : list) {
			if (entity instanceof EntityLivingBase) {
				entityNear.setFire(3);
			}
		}
    }
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/bclass/flamewaker", "inventory"));
	}
}
