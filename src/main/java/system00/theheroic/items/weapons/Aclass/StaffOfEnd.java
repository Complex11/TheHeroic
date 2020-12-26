package system00.theheroic.items.weapons.Aclass;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraftforge.client.model.ModelLoader;
import system00.theheroic.Main;
import system00.theheroic.entity.misc.SpecialPearl;
import system00.theheroic.init.ModPotions;
import system00.theheroic.items.ItemBase;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.Reference;

import javax.annotation.Nullable;
import java.util.List;

public class StaffOfEnd extends ItemBase {

	public StaffOfEnd(String name) {
		super(name);
		this.setMaxDamage(10000);
		this.setMaxStackSize(1);
		setCreativeTab(Main.heroicweaponstabA);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §aA");
		tooltip.add("§d§lPassive: §r§7Normal attacks remove targets' souls over time, leaving them extremely weak with 2 health.");
		tooltip.add("§d§lSpecial Ability: §r§7Shoots a beam that does 80% of target's health + 8 damage or 56% of target's heath + 6 damage, depending on how close the beam is to the target. Also shoots 12 explosive pearls that do 6 damage each and apply random negative effects on hit. §fCooldown: 3 seconds.");
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		target.addPotionEffect(new PotionEffect(ModPotions.SOUL_REMOVAL_EFFECT, 100, 1, true, false));
		HeroicUtil.damageAndCheckItem(stack, 1);
        return true;
    }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer playerIn, EnumHand handIn) {
		if (!world.isRemote) {
    		Vec3d vec = playerIn.getLookVec();
			vec = vec.scale(-4 / vec.lengthVector());
			double cphi = vec.x / new Vec3d(vec.x, 0, vec.z).lengthVector();
			double cpsi = vec.y / 4;
			for (int i = 0; i < 12; ++i) {
				SpecialPearl pearl = new SpecialPearl(world);
				double r = playerIn.getRNG().nextDouble() * 20 - 10;
				float theta = (float) (playerIn.getRNG().nextFloat() * 2 * Math.PI);
				Vec3d at = HeroicUtil.applyRotationMatrix(new Vec3d(r, 0, 0).rotateYaw(theta), cphi, cpsi);
				pearl.setPosition(playerIn.posX + vec.x + at.x, playerIn.posY + vec.y + at.y + 8, playerIn.posZ + vec.z + at.z);
				pearl.damage = 36;
				pearl.thrower = playerIn;
				pearl.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 3.0F, 0.0F);
				playerIn.getEntityWorld().spawnEntity(pearl);
			}
			Vec3d beamFrom = new Vec3d(playerIn.posX, playerIn.posY + 1.3, playerIn.posZ);
			Vec3d beamTo = beamFrom.add(playerIn.getLook(1));
	       	Vec3d vecBeam = new Vec3d(beamTo.x - beamFrom.x, beamTo.y - beamFrom.y, beamTo.z - beamFrom.z);
    		if (world != null) {
            	for (float i = 0.05f; i < 65; i += 0.1f) {
            		Vec3d particleAt = beamFrom.add(vecBeam.scale(i / vecBeam.lengthVector()));
               		world.spawnParticle(EnumParticleTypes.DRAGON_BREATH, particleAt.x, particleAt.y, particleAt.z, 0, 0, 0);
            	}
            	for (float i = 0; i < 65; i += 0.1f) {
            		Vec3d particleAt = beamFrom.add(vecBeam.scale(i / vecBeam.lengthVector()));
               		world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, particleAt.x, particleAt.y, particleAt.z, 0, 0, 0);
            	}
	        }
    		float damage = 40 + playerIn.getRNG().nextFloat() * 40;
           	float radiusInner = (float) (Math.sqrt(damage) - 1);
           	float radiusOuter = (float) (radiusInner + Math.sqrt(damage));
            List<Entity> entityList = playerIn.getEntityWorld().getEntitiesWithinAABBExcludingEntity(playerIn, new AxisAlignedBB(beamFrom.x - 10,
            		beamFrom.y - 10, beamFrom.z - 10, beamFrom.x + 10, beamFrom.y + 10, beamFrom.z + 10));
            
            for (Entity entity : entityList) {
               	Vec3d vecFromEntity = new Vec3d(beamFrom.x - entity.posX, beamFrom.y - entity.posY, beamFrom.z - entity.posZ);
            	if (new Vec3d(entity.posX - beamFrom.x, entity.posY - beamFrom.y, entity.posZ - beamFrom.z).dotProduct(vecBeam) < 0) {
            		continue;
            	}
            	double distance = Math.sqrt(vecBeam.lengthSquared() - Math.pow(vecBeam.dotProduct(vecFromEntity), 2) / vecFromEntity.lengthSquared());
	            if (entity instanceof EntityLivingBase) {
	            	if (distance > radiusInner && distance < radiusOuter) {
	           			HeroicUtil.PercentageAttack(0.2f, (EntityLivingBase) entity);
	           			entity.attackEntityFrom(DamageSource.causeMobDamage(playerIn), 8f);
	           		}
	       			if (distance < radiusInner) {
	       				HeroicUtil.PercentageAttack(0.44f, (EntityLivingBase) entity);
	           			entity.attackEntityFrom(DamageSource.causeMobDamage(playerIn), 6f);
	       			}
	            }
            }
        }		
        ItemStack stack = playerIn.getHeldItem(handIn);
        HeroicUtil.damageAndCheckItem(stack, 1);
	    playerIn.playSound(SoundEvents.BLOCK_PORTAL_TRAVEL, 0.44f, 1.8f);
		playerIn.getCooldownTracker().setCooldown(this, 60);
		return super.onItemRightClick(world, playerIn, handIn);
	}
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/aclass/staff_of_end", "inventory"));
	}
}
