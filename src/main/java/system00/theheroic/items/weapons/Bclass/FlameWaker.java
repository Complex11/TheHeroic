package system00.theheroic.items.weapons.Bclass;

import com.google.common.base.Predicates;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import system00.theheroic.Main;
import system00.theheroic.items.tool.ToolSword;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.Reference;

import javax.annotation.Nullable;
import java.util.List;

public class FlameWaker extends ToolSword {

	public static double hitCount = 0;

	public FlameWaker(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabB);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §bB");
		tooltip.add(
				"§d§lPassive: §r§7Normal attacks build §6Hellfire§7. An attack does [6 + §6Hellfire §7count] damage to burning targets, and restores 1 health, but does [4 + §6Hellfire §7count] damage to non-burning targets. Upon reaching 12 §6Hellfire§7, Hellfire is reset in exchange for 1 Blaze Powder.");
		tooltip.add(
				"§d§lSpecial Ability: §r§7Consume 1 Blaze Powder and apply §2Fire Resistance[1]§7 for 5 minutes. §fCooldown: 0 seconds.");
		tooltip.add("§4§lAura: §r§7Entities in a radius[3] are set on fire for 3 seconds. This builds §6Hellfire§7.");
		tooltip.add("§6Hellfire: ");
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (target.isBurning()) {
			target.attackEntityFrom(DamageSource.LAVA, (float) hitCount * 2 + 6);
			attacker.setHealth(attacker.getHealth() + 2);
		} else {
			target.attackEntityFrom(DamageSource.LAVA, (float) hitCount + 4);
		}
		hitCount++;
		HeroicUtil.damageAndCheckItem(stack, 1);
		return true;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		if (player.inventory.hasItemStack(new ItemStack(Items.BLAZE_POWDER))) {
			player.inventory.clearMatchingItems(Items.BLAZE_POWDER, 0, 1, null);
			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 6000, 0, false, false));
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		if (hitCount == 12) {
			hitCount = 0;
			if (entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;
				player.inventory.addItemStackToInventory(new ItemStack(Items.BLAZE_POWDER));
			}
		}
		if (entity.world.getTotalWorldTime() % 20 == 0 && entity instanceof EntityLivingBase) {
			EntityLivingBase entityLiving = (EntityLivingBase) entity;
			if (entityLiving.getHeldItemMainhand().getItem() instanceof FlameWaker) {
				HeroicUtil.spawnParticleCircleAroundEntity(entityLiving, EnumParticleTypes.FLAME, 3f);
				List<EntityLivingBase> list = HeroicUtil.getEntitiesWithinRadius(3, entity.posX, entity.posY, entity.posZ,
						world);
				list.removeIf(Predicates.instanceOf(EntityPlayer.class));
				for (Entity entityNear : list) {
					hitCount++;
					if (entity instanceof EntityLivingBase) {
						entityNear.setFire(3);
					}
				}
			}
		}
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0,
				new ModelResourceLocation(Reference.MODID + ":weapons/bclass/flamewaker", "inventory"));
	}
}
