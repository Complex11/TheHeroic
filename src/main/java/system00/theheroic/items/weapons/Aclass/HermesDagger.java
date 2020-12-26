package system00.theheroic.items.weapons.Aclass;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import system00.theheroic.Main;
import system00.theheroic.init.ModPotions;
import system00.theheroic.items.ItemBase;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.Reference;

import javax.annotation.Nullable;
import java.util.List;

public class HermesDagger extends ItemBase {

	private static float damage = 10;

	public static final String NBT_KEY = "heart_pierced";
	
	public HermesDagger(String name, ToolMaterial material) {
		super(name);
		setMaxDamage(12000);
		this.setMaxStackSize(1);
		setCreativeTab(Main.heroicweaponstabA);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §aA");
		tooltip.add("§d§lPassive: §r§7Normal attacks do 10 damage, apply §2Bleed[2]§7 for 10 seconds and make targets §3Heartpierced§7.");
		tooltip.add("§d§lSpecial Ability: §r§7All entities in the world that are §3Heartpierced§7 are struck by lightning. §fCooldown: 3 seconds.");
		tooltip.add("§d§lBonus Ability: §r§7Teleports a maximum of 40 blocks in the direction faced. §fCooldown: 3.5 seconds.");
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (!target.getEntityData().getBoolean(NBT_KEY)) {
			target.getEntityData().setBoolean(NBT_KEY, true);
		}
		HeroicUtil.HeartPierce(damage, target);
		target.addPotionEffect(new PotionEffect(ModPotions.BLEED_EFFECT, 200, 1, false, false));
		HeroicUtil.damageAndCheckItem(stack, 1);
		return true;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		if (!worldIn.isRemote) {
			if (!player.isSneaking()) {
				List<Entity> list = worldIn.getEntities(EntityLivingBase.class, EntitySelectors.IS_ALIVE);
				list.removeIf(t -> t instanceof EntityPlayer);
				for (Entity entity : list) {
					if (entity instanceof EntityLivingBase) {
						if (entity.getEntityData().getBoolean(NBT_KEY)) {
							HeroicUtil.SummonLightningAt(worldIn, entity.posX, entity.posY, entity.posZ);
						}
					}
				}
				player.getCooldownTracker().setCooldown(this, 60);
				HeroicUtil.damageAndCheckItem(item, 1);
			} else {
				RayTraceResult traced = player.rayTrace(40, 1.0f);
				Vec3d look = traced.hitVec;
				HeroicUtil.SpecialTeleport(look.x, look.y, look.z, player);
				player.getCooldownTracker().setCooldown(this, 70);
			}
		}
		player.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 0.9f, 1.6f);
		HeroicUtil.damageAndCheckItem(item, 1);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/aclass/hermes_dagger", "inventory"));
	}
}
