package system00.theheroic.items.weapons.Sclass;

import com.google.common.collect.Lists;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import system00.theheroic.Main;
import system00.theheroic.init.ModItems;
import system00.theheroic.init.ModPotions;
import system00.theheroic.items.tool.ToolSword;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.Reference;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class HeavenlySmite extends ToolSword {

	public static float normalDamage = 15;
	public static boolean enlightened = false;
	public static boolean transcended = false;
	public static boolean heaven = false;
	public static DamageSource HEAVEN = new DamageSource("heaven").setDamageAllowedInCreativeMode()
			.setDamageBypassesArmor().setMagicDamage();

	public HeavenlySmite(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabS);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §1S");
		tooltip.add("§d§lPassive: §r§7Normal attacks vary based on the wielder's current state. If not in any state, deal 15 damage. If §cEnlightened§7, deal 30 damage of type HEAVEN scaled by the target's health. If §cTranscended§7. deal 100 damage of type HEAVEN scaled by the target's health, and apply §2Bleed[3]§7, §2Slowness[3]§7, §2Weakness[3]§7 and §2Wither[3]§7. If §cIn Heaven§7, deal 2^(target's maximum health) damage of type HEAVEN.");
		tooltip.add("§d§lSpecial Ability: §r§7Ascend to the next state of mind. Requires 64 Obsidian and 64 Diamond to become §cEnlightened§7, 16 Nether Stars and 64 Emeralds to become §cTranscended§7, and 1 Dragon Egg and 1 Shard Of Creation to become §cIn Heaven§7. If already §cIn Heaven§7, apply §2HEAVEN[1]§7 for 20 minutes.");
		tooltip.add("§4§lAura: §r§7While §cIn Heaven§7, entities within 4 blocks are instantly killed.");
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (!enlightened && !transcended && !heaven) {
			target.attackEntityFrom(DamageSource.GENERIC, normalDamage);
		} else if (enlightened && !transcended && !heaven) {
			target.attackEntityFrom(HEAVEN, 30 * (target.getMaxHealth() / target.getHealth()));
		} else if (enlightened && transcended && !heaven) {
			target.attackEntityFrom(HEAVEN, 100 * (target.getMaxHealth() / target.getHealth()));
			target.addPotionEffect(new PotionEffect(ModPotions.BLEED_EFFECT, 1200, 2, false, false));
			target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1200, 2, false, false));
			target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 1200, 2, false, false));
			target.addPotionEffect(new PotionEffect(MobEffects.WITHER, 1200, 2, false, false));
		} else if (enlightened && transcended && heaven) {
			float deal = Math.pow(2, target.getMaxHealth()) > Float.MAX_VALUE ? Float.MAX_VALUE : (float) Math.pow(2, target.getMaxHealth());
			target.attackEntityFrom(HEAVEN, deal);
		}
		return true;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		return leftClickEntity(player, entity);
	}

	public boolean leftClickEntity(EntityLivingBase holder, Entity entity) {
		if (!entity.world.isRemote && holder instanceof EntityPlayer && heaven) {
			areaSmite(holder);
			return true;
		}
		return false;
	}

	public static void areaSmite(EntityLivingBase source) {
		World world = source.world;
		List<Entity> entities = Lists.newArrayList();
		int range = 30;
		double slope = 90;
		for (int dist = 0; dist <= 30; dist += 2) {
			AxisAlignedBB bb = source.getEntityBoundingBox();
			Vec3d vec = source.getLookVec();
			vec = vec.normalize();
			bb = bb.grow(slope * dist + 2.0, slope * dist + 0.25, slope * dist + 2.0);
			bb = bb.offset(vec.x * dist, vec.y * dist, vec.z * dist);
			List<Entity> list = world.getEntitiesWithinAABB(EntityLivingBase.class, bb);
			list.removeAll(entities);
			list.removeIf(entity -> entity.getDistance(source) > range);
			entities.addAll(list);
		}
		entities.remove(source);
		for (Entity entity : entities) {
			entity.setDead();
			entity.handleStatusUpdate((byte) 3);
			entity.world.removeEntityDangerously(entity);
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		InventoryPlayer inv = player.inventory;
		if (!enlightened && !transcended && !heaven) {
			if (inv.hasItemStack(new ItemStack(Blocks.OBSIDIAN)) && inv.hasItemStack(new ItemStack(Items.DIAMOND))) {
				for (int i = 0; i < inv.getSizeInventory(); i++) {
					if (inv.getStackInSlot(i).getItem() == Blocks.OBSIDIAN
							.getItemDropped(Blocks.OBSIDIAN.getDefaultState(), new Random(), 0)
							&& inv.getStackInSlot(i).getCount() == 64) {
						inv.clearMatchingItems(
								Blocks.OBSIDIAN.getItemDropped(Blocks.OBSIDIAN.getDefaultState(), new Random(), 0), 0,
								64, null);
					}
					if (inv.getStackInSlot(i).getItem() == Items.DIAMOND && inv.getStackInSlot(i).getCount() == 64) {
						inv.clearMatchingItems(Items.DIAMOND, 0, 64, null);
					}
				}
				enlightened = true;
				HeroicUtil.SendMsgToPlayer("Enlightened", player);
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1200, 2, false, false));
				player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 1200, 2, false, false));
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 1200, 2, false, false));
				player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 1200, 2, false, false));
			}
		} else if (enlightened && !transcended && !heaven) {
			if (inv.hasItemStack(new ItemStack(Items.NETHER_STAR)) && inv.hasItemStack(new ItemStack(Items.EMERALD))) {
				for (int i = 0; i < inv.getSizeInventory(); i++) {
					if (inv.getStackInSlot(i).getItem() == Items.NETHER_STAR
							&& inv.getStackInSlot(i).getCount() >= 16) {
						inv.clearMatchingItems(Items.NETHER_STAR, 0, 16, null);
					}
					if (inv.getStackInSlot(i).getItem() == Items.EMERALD && inv.getStackInSlot(i).getCount() == 64) {
						inv.clearMatchingItems(Items.EMERALD, 0, 64, null);
					}
				}
				transcended = true;
				HeroicUtil.SendMsgToPlayer("Transcended", player);
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1200, 3, false, false));
				player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 1200, 5, false, false));
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 1200, 4, false, false));
				player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 1200, 4, false, false));
			}
		} else if (enlightened && transcended && !heaven) {
			if (inv.hasItemStack(new ItemStack(Blocks.DRAGON_EGG))
					&& inv.hasItemStack(new ItemStack(ModItems.SHARD_OF_CREATION))) {
				for (int i = 0; i < inv.getSizeInventory(); i++) {
					if (inv.getStackInSlot(i).getItem() == Blocks.DRAGON_EGG
							.getItemDropped(Blocks.DRAGON_EGG.getDefaultState(), new Random(), 0)
							&& inv.getStackInSlot(i).getCount() >= 1) {
						inv.clearMatchingItems(
								Blocks.DRAGON_EGG.getItemDropped(Blocks.DRAGON_EGG.getDefaultState(), new Random(), 0),
								0, 1, null);
					}
					if (inv.getStackInSlot(i).getItem() == ModItems.SHARD_OF_CREATION
							&& inv.getStackInSlot(i).getCount() >= 1) {
						inv.clearMatchingItems(ModItems.SHARD_OF_CREATION, 0, 1, null);
					}
				}
				heaven = true;
				HeroicUtil.SendMsgToPlayer("Heaven", player);
				player.addPotionEffect(new PotionEffect(ModPotions.HEAVEN_EFFECT, 24000, 0, false, false));
			}
		}
		if (enlightened && transcended && heaven && !player.isPotionActive(ModPotions.HEAVEN_EFFECT)) {
			HeroicUtil.SendMsgToPlayer("MADE IN HEAVEN!", player);
			player.addPotionEffect(new PotionEffect(ModPotions.HEAVEN_EFFECT, 24000, 0, false, false));
			player.getCooldownTracker().setCooldown(this, 2000);
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		if (!enlightened) {
			transcended = false;
			heaven = false;
		}
		if (!transcended) {
			heaven = false;
		}
		if (heaven) {
			AxisAlignedBB bb = entity.getEntityBoundingBox();
			bb = bb.grow(4.0, 4.0, 4.0);
			List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(entity, bb);
			for (Entity entitylist : list) {
				if (entitylist instanceof EntityLivingBase) {
					EntityLivingBase entitylist1 = (EntityLivingBase) entitylist;
					entitylist1.setDead();
					entitylist1.onDeath(HEAVEN);
					entitylist1.handleStatusUpdate((byte) 3);
					entitylist1.world.removeEntityDangerously(entitylist1);
				}
			}
		}
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0,
				new ModelResourceLocation(Reference.MODID + ":weapons/sclass/heavenly_smite", "inventory"));
	}
}
