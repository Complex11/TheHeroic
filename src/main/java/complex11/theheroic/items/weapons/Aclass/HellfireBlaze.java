package complex11.theheroic.items.weapons.Aclass;

import java.util.List;

import com.google.common.base.Predicates;

import complex11.theheroic.Main;
import complex11.theheroic.entity.misc.SpecialFireball;
import complex11.theheroic.items.ItemBase;
import complex11.theheroic.util.HeroicUtil;
import complex11.theheroic.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class HellfireBlaze extends ItemBase {

	public HellfireBlaze(String name) {
		super(name);
		this.setMaxDamage(12000);
		setCreativeTab(Main.heroicweaponstabA);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		playerIn.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 60, 3, false, false));
		ItemStack item = playerIn.getHeldItem(handIn);
		if (!worldIn.isRemote) {
			int attack = playerIn.getRNG().nextInt(3);
			switch (attack) {
			case 0:
				Vec3d dir = playerIn.getLook(1);
				dir = dir.scale(0.42 / dir.lengthVector());
				SpecialFireball fireball = new SpecialFireball(worldIn, playerIn, 1, 1, 1);
				fireball.setPosition(playerIn.posX, playerIn.posY + 1, playerIn.posZ);
				fireball.shooter = playerIn;
				fireball.accelerationX = dir.x;
				fireball.accelerationY = dir.y;
				fireball.accelerationZ = dir.z;
				fireball.damage = 360;
				fireball.damageCooldown = 4;
				fireball.radius = 6;
				fireball.duration = 500;
				fireball.explosionDamage = 50;
				fireball.explosionPower = 5;
				fireball.doExplosionDestroyBlocks = true;
				worldIn.spawnEntity(fireball);
				break;
			case 1:
				AxisAlignedBB bb = playerIn.getEntityBoundingBox();
				bb = bb.grow(13.0, 5, 13.0);
				List<Entity> list = worldIn.getEntitiesInAABBexcluding(playerIn, bb, Predicates.instanceOf(EntityLivingBase.class));
				int EntityCounter = 0;
				for (Entity entity : list) {
					if (entity instanceof EntityLivingBase) {
						entity.setFire(20);
						EntityCounter++;
					}
					worldIn.setBlockState(new BlockPos(entity.posX, entity.posY - 1, entity.posZ), Blocks.LAVA.getDefaultState());
				}
				for (int i = 0; i < EntityCounter; i++) {
					playerIn.setHealth(playerIn.getHealth() + 2);
					if (playerIn.getHealth() == playerIn.getMaxHealth()) {
						int randomEffect = playerIn.getRNG().nextInt(3);
						switch (randomEffect) {
						case 0:
							playerIn.addPotionEffect(new PotionEffect(MobEffects.SPEED, 100, 0, false, false));
							break;
						case 1:
							playerIn.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 100, 0, false, false));
							break;
						case 2:
							playerIn.addPotionEffect(new PotionEffect(MobEffects.HASTE, 100, 0, false, false));
							break;
						}
					}
				}
				break;
			case 2:
				Vec3d beamFrom = new Vec3d(playerIn.posX, playerIn.posY + 1.3, playerIn.posZ);
				Vec3d beamTo = beamFrom.add(playerIn.getLook(1));
		       	Vec3d vecBeam = new Vec3d(beamTo.x - beamFrom.x, beamTo.y - beamFrom.y, beamTo.z - beamFrom.z);
	    		WorldClient world = Minecraft.getMinecraft().world;
	    		if (world != null) {
	            	for (float i = 0; i < 100; i += 0.01f) {
	            		Vec3d particleAt = beamFrom.add(vecBeam.scale(i / vecBeam.lengthVector()));
	               		world.spawnParticle(EnumParticleTypes.FLAME, particleAt.x, particleAt.y, particleAt.z, 0, 0, 0);
	            	}
		        }
	    		List<Entity> entityList = playerIn.getEntityWorld().getEntitiesWithinAABBExcludingEntity(playerIn, new AxisAlignedBB(beamFrom.x - 6,
	            		beamFrom.y - 6, beamFrom.z - 6, beamFrom.x + 6, beamFrom.y + 6, beamFrom.z + 6));
	    		for (Entity entity : entityList) {
	    			if (entity instanceof EntityLivingBase) {
	    				entity.setFire(100);
	    				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 2000, 3, false, false));
	    				HeroicUtil.createExplosion(null, worldIn, (EntityLivingBase) entity, 3);
	    			}
	    		}
				break;
			}
				
		}
		playerIn.playSound(SoundEvents.ENTITY_GENERIC_BURN, 1.5f, 1.0f);
		HeroicUtil.damageAndCheckItem(item);
		playerIn.getCooldownTracker().setCooldown(this, 30);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}
	
	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		entity.extinguish();
		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 200, 1, false, false));
    }
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/aclass/hellfire_blaze", "inventory"));
	}
}
