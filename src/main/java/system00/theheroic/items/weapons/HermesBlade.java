package system00.theheroic.items.weapons;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import system00.theheroic.Main;
import system00.theheroic.items.ItemBase;
import system00.theheroic.util.HeroicUtil;

public class HermesBlade extends ItemBase {

	public HermesBlade(String name, ToolMaterial material) {
		super(name);
		setMaxDamage(12000);
		setCreativeTab(Main.heroicweaponstab);
	}

	@Override
	public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity) {
		if (entity instanceof EntityLivingBase) {
			if (!entity.getTags().contains("Heart Pierced")) {
				entity.addTag("Heart Pierced");
			}
			HeroicUtil.BleedAttack(2f, 5, (EntityLivingBase) entity);
			entity.attackEntityFrom(DamageSource.causePlayerDamage(player), 1);
		}
		return false;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		if (player.isSneaking()) {
			List<Entity> list = worldIn.getEntities(EntityLivingBase.class, EntitySelectors.IS_ALIVE);
			list.removeIf(t -> t instanceof EntityPlayer);
			for (Entity entity : list) {
				if (entity instanceof EntityLivingBase) {
					if (entity.getTags().contains("Heart Pierced")) {
						worldIn.spawnEntity(new EntityLightningBolt(worldIn, entity.posX, entity.posY, entity.posZ, false));
					}
				}
			}
			HeroicUtil.damageAndCheckItem(item);
		} else {
			RayTraceResult traced = player.rayTrace(40, 1.0f);
			Vec3d look = traced.hitVec;
			HeroicUtil.SpecialTeleport(look.x, look.y, look.z, player);
			player.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 0.9f, 1.6f);
			player.getCooldownTracker().setCooldown(this, 70);
			HeroicUtil.damageAndCheckItem(item);
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}
}
