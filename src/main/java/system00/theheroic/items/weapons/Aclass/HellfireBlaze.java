package system00.theheroic.items.weapons.Aclass;

import com.google.common.base.Predicates;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import system00.theheroic.Main;
import system00.theheroic.entity.misc.EntityMiniFireball;
import system00.theheroic.entity.misc.SpecialFireball;
import system00.theheroic.items.ItemBase;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.Reference;

import javax.annotation.Nullable;
import java.util.List;

public class HellfireBlaze extends ItemBase {

	private static float damage = 9;

	public HellfireBlaze(String name) {
		super(name);
		this.setMaxDamage(12000);
		this.setMaxStackSize(1);
		setCreativeTab(Main.heroicweaponstabA);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §aA");
		tooltip.add("§d§lPassive: §r§7Deal 9 damage.");
		tooltip.add("§d§lSpecial Ability: §r§7Perform one of §cHell's Attacks. §fCooldown: 2 seconds.");
		tooltip.add("§c§lHell's Attacks:");
		tooltip.add("§c-Hell's Wrath: §7Shoot a large fireball that does 30 explosive damage.");
		tooltip.add("§c-Flaming Rites: §7Transform the blocks below all entities in a radius[20] into lava. §3Lifesteal§7 3 health and gain a random positive effect for 100 seconds for each entity in the radius.");
		tooltip.add("§c-Ray of Fire: §7Fire a infernal ray that deals 12 or 8 damage, depending on how close the ray is to the target. Also shoot a small fireball that does 6 damage on impact and applies negative effects.");
		tooltip.add("§4§lAura: §r§2Fire Resistance[2] §7while held.");
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		target.attackEntityFrom(DamageSource.GENERIC, damage);
		HeroicUtil.damageAndCheckItem(stack, 1);
		return true;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		playerIn.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 60, 3, false, false));
		ItemStack item = playerIn.getHeldItem(handIn);
		if (!worldIn.isRemote) {
			for (int a = 0; a < 2; a++) {
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
						fireball.damageCooldown = 4;
						fireball.radius = 6;
						fireball.duration = 500;
						fireball.explosionDamage = 30;
						fireball.explosionPower = 8;
						fireball.doExplosionDestroyBlocks = true;
						worldIn.spawnEntity(fireball);
						break;
					case 1:
						AxisAlignedBB bb = playerIn.getEntityBoundingBox();
						bb = bb.grow(20.0, 5.0, 20.0);
						List<Entity> list = worldIn.getEntitiesInAABBexcluding(playerIn, bb, Predicates.instanceOf(EntityLivingBase.class));
						int EntityCounter = 0;
						double xvalue = playerIn.posX;
						double yvalue = playerIn.posY;
						double zvalue = playerIn.posZ;
						HeroicUtil.spawnParticleCircleAtPosition(xvalue, yvalue, zvalue, EnumParticleTypes.LAVA, 20);
						for (Entity entity : list) {
							entity.setFire(20);
							entity.attackEntityFrom(DamageSource.LAVA, 3);
							EntityCounter++;
							worldIn.setBlockState(new BlockPos(entity.posX, entity.posY - 1, entity.posZ), Blocks.LAVA.getDefaultState());
						}
						for (int i = 0; i < EntityCounter; i++) {
							playerIn.setHealth(playerIn.getHealth() + 3);
							if (playerIn.getHealth() == playerIn.getMaxHealth()) {
								int randomEffect = playerIn.getRNG().nextInt(8);
								switch (randomEffect) {
									case 0:
										playerIn.addPotionEffect(new PotionEffect(MobEffects.SPEED, 2000, 0, false, false));
										break;
									case 1:
										playerIn.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 2000, 0, false, false));
										break;
									case 2:
										playerIn.addPotionEffect(new PotionEffect(MobEffects.HASTE, 2000, 0, false, false));
										break;
									case 3:
										playerIn.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 2000, 0, false, false));
										break;
									case 4:
										playerIn.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 2000, 0, false, false));
										break;
									case 5:
										playerIn.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 2000, 0, false, false));
										break;
									case 6:
										playerIn.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 2000, 0, false, false));
										break;
									case 7:
										playerIn.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 2000, 0, false, false));
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
									entity.attackEntityFrom(DamageSource.LAVA, 12f);
								}
								if (distance < radiusInner) {
									entity.attackEntityFrom(DamageSource.LAVA, 8f);
								}
							}
						}
						EntityMiniFireball miniFireball = new EntityMiniFireball(worldIn);
						miniFireball.setPosition(playerIn.posX, playerIn.posY + 5, playerIn.posZ);
						miniFireball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 4.0F, 0.0F);
						playerIn.getEntityWorld().spawnEntity(miniFireball);
						break;
				}
			}
		}
		playerIn.playSound(SoundEvents.ENTITY_GENERIC_BURN, 1.5f, 1.0f);
		HeroicUtil.damageAndCheckItem(item, 1);
		playerIn.getCooldownTracker().setCooldown(this, 40);
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
