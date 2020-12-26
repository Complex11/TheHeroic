package system00.theheroic.items.weapons.Aclass;

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
import system00.theheroic.items.ItemBase;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.Reference;

import javax.annotation.Nullable;
import java.util.List;

public class BulwarksHallmark extends ItemBase {

	private float damage = 3;

	public BulwarksHallmark(String name) {
		super(name);
		this.setMaxDamage(11000);
		this.setMaxStackSize(1);
		setCreativeTab(Main.heroicweaponstabA);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §aA");
		tooltip.add("§d§lPassive: §r§7Normal attacks deal 3 damage and §3Lifesteal§7 for 20% of the target's health. Instantly kill targets which have less than 20% total health or are glowing.");
		tooltip.add("§d§lSpecial Ability: §r§3Lifesteal§7 10% health from all entities in a radius[10] for 20 durability. §fCooldown: 30 seconds.");
		tooltip.add("§4§lAura: §r§2Regeneration[1] §7and §2Resistance[1] §7while held.");
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (target.getHealth() < (target.getMaxHealth() * 0.2) || target.isPotionActive(MobEffects.GLOWING)
				|| target.isGlowing()) {
			target.handleStatusUpdate((byte) 3);
			attacker.playSound(SoundEvents.ENTITY_LIGHTNING_IMPACT, 1.6f, 1.0f);
			if (attacker instanceof EntityPlayer) {
				HeroicUtil.SendMsgToPlayer("§2" + target.getName() + " was divinely judged and found unworthy.",
						(EntityPlayer) attacker);
			}
		} else {
			HeroicUtil.Lifesteal((float) (target.getHealth() * 0.2), attacker, target);
			target.attackEntityFrom(DamageSource.GENERIC, damage);
		}
		HeroicUtil.damageAndCheckItem(stack, 1);
		return true;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		List<EntityLivingBase> list = HeroicUtil.getEntitiesWithinRadius(50, player.posX, player.posY, player.posZ,
				worldIn);
		list.remove(player);
		for (EntityLivingBase entity : list) {
			HeroicUtil.Lifesteal((float) (entity.getHealth() * 0.1), player, entity);
		}
		HeroicUtil.damageAndCheckItem(item, 20);
		player.getCooldownTracker().setCooldown(this, 600);
		player.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1f, 0.4f);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean isSelected) {
		if (!((EntityLivingBase) entity).isPotionActive(MobEffects.REGENERATION)) {
			((EntityLivingBase) entity)
					.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 0, false, false));
		}
		if (!((EntityLivingBase) entity).isPotionActive(MobEffects.RESISTANCE)) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 0, false, false));
		}
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0,
				new ModelResourceLocation(Reference.MODID + ":weapons/aclass/bulwarks_hallmark", "inventory"));
	}
}
