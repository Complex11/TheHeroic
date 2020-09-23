package system00.theheroic.items.weapons;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import system00.theheroic.Main;
import system00.theheroic.init.ModPotions;
import system00.theheroic.items.ItemBase;
import system00.theheroic.util.HeroicUtil;

public class StaffOfEnd extends ItemBase {

	public StaffOfEnd(String name) {
		super(name);
		this.setMaxDamage(3000);
		setCreativeTab(Main.heroicweaponstab);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase entity, EntityLivingBase attacker) {
		if (entity instanceof EntityLivingBase) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(ModPotions.SOUL_REMOVAL_EFFECT, 100, 1, true, false));
		}
        return true;
    }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (!worldIn.isRemote) {
			Vec3d beamFrom = new Vec3d(playerIn.posX, playerIn.posY, playerIn.posZ);
			Vec3d beamTo = beamFrom.add(playerIn.getLook(1));
	       	Vec3d vecBeam = new Vec3d(beamTo.x - beamFrom.x, beamTo.y - beamFrom.y, beamTo.z - beamFrom.z);
    		WorldClient world = Minecraft.getMinecraft().world;
    		if (world != null) {
            	for (float i = 0; i < 65; i += 0.01f) {
            		Vec3d particleAt = beamFrom.add(vecBeam.scale(i / vecBeam.lengthVector()));
               		world.spawnParticle(EnumParticleTypes.PORTAL, particleAt.x, particleAt.y, particleAt.z, 0, 0, 0);
            	}
	        }
    		float damage = 420 + playerIn.getRNG().nextFloat() * 400;
           	float radiusInner = (float) (Math.sqrt(damage) - 1);
           	float radiusOuter = (float) (radiusInner + Math.sqrt(damage));
            List<Entity> entityList = playerIn.getEntityWorld().getEntitiesWithinAABBExcludingEntity(playerIn, new AxisAlignedBB(beamFrom.x - 65,
            		beamFrom.y - 65, beamFrom.z - 65, beamFrom.x + 65, beamFrom.y + 65, beamFrom.z + 65));
            for (Entity entity : entityList) {
               	Vec3d vecFromEntity = new Vec3d(beamFrom.x - entity.posX, beamFrom.y - entity.posY, beamFrom.z - entity.posZ);
            	if (new Vec3d(entity.posX - beamFrom.x, entity.posY - beamFrom.y, entity.posZ - beamFrom.z).dotProduct(vecBeam) < 0) {
            		continue;
            	}
            	double distance = Math.sqrt(vecBeam.lengthSquared() - Math.pow(vecBeam.dotProduct(vecFromEntity), 2) / vecFromEntity.lengthSquared());
	            if (entity instanceof EntityLivingBase) {
	            	if (distance > radiusInner && distance < radiusOuter) {
	           			HeroicUtil.PercentageAttack(0.2f, (EntityLivingBase) entity);
	           			entity.attackEntityFrom(DamageSource.causeMobDamage(playerIn), 10f);
	           		}
	       			if (distance < radiusInner) {
	           			entity.attackEntityFrom(DamageSource.causeMobDamage(playerIn), 8f);
	       			}
	            }
            }
        }		
        ItemStack stack = playerIn.getHeldItem(handIn);
        HeroicUtil.damageAndCheckItem(stack);
	    playerIn.playSound(SoundEvents.BLOCK_PORTAL_TRAVEL, 0.5f, 1.5f);
		playerIn.getCooldownTracker().setCooldown(this, 20);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}
