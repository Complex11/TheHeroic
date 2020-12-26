package system00.theheroic.event;

import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionAddedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import system00.theheroic.Main;
import system00.theheroic.init.ModItems;
import system00.theheroic.init.ModPotions;
import system00.theheroic.potions.Potion4D;
import system00.theheroic.potions.PotionBleed;
import system00.theheroic.potions.PotionTargetOfThunder;
import system00.theheroic.potions.PotionTimeStop;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.Reference;

import java.util.Set;

@EventBusSubscriber(modid = Reference.MODID)
public class PotionEvent {
	
	private static Set<String> flyingPlayer = Sets.newHashSet();

	@SubscribeEvent
	public static void onLivingUpdateFlight(LivingUpdateEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (flyingPlayer.contains(player.getName())) {
				player.capabilities.allowFlying = true;
			}
		}
	}
	// TIME STOP

	@SubscribeEvent
	public static void onLivingUpdateEventTime(LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (entity.isPotionActive(ModPotions.TIME_STOP_EFFECT)) {
			PotionTimeStop.performEffectConsistent(entity,
					entity.getActivePotionEffect(ModPotions.TIME_STOP_EFFECT).getAmplifier());
		}
	}

	@SubscribeEvent
	public static void onPotionAddedEventTime(PotionAddedEvent event) {
		if (event.getEntity().world.isRemote && event.getPotionEffect().getPotion() == ModPotions.TIME_STOP_EFFECT
				&& event.getEntity() instanceof EntityPlayer) {
			Main.proxy.loadShader((EntityPlayer) event.getEntity(), PotionTimeStop.SHADER);
		}
	}

	@SubscribeEvent
	public static void timeTick(WorldTickEvent event) {
		if (!event.world.isRemote && event.phase == TickEvent.Phase.END) {
			PotionTimeStop.cleanUpEntities(event.world);
		}
	}

	@SubscribeEvent
	public static void onClientTickEvent(ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END && !net.minecraft.client.Minecraft.getMinecraft().isGamePaused()) {
			World world = net.minecraft.client.Minecraft.getMinecraft().world;
			if (world == null)
				return;
			PotionTimeStop.cleanUpEntities(world);
		}
	}

	@SubscribeEvent
	public static void onPlayerLoggedOutEvent(PlayerLoggedOutEvent event) {
		if (event.player.updateBlocked) {
			event.player.updateBlocked = false;
		}
	}

	// BLEED

	@SubscribeEvent
	public static void onLivingUpdateEventBleed(LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (entity.isPotionActive(ModPotions.BLEED_EFFECT) && entity.world.getWorldTime() % 20 == 0) {
			HeroicUtil.spawnParticleAtEntity(entity, EnumParticleTypes.DAMAGE_INDICATOR, 1);
			PotionBleed.performEffectConsistent(entity,
					entity.getActivePotionEffect(ModPotions.BLEED_EFFECT).getAmplifier());
		}
	}

	// SOUL REMOVAL

	@SubscribeEvent
	public static void onLivingUpdateSoul(LivingUpdateEvent event) {
		if (event.getEntity() instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase) event.getEntity();
			if (entity.isPotionActive(ModPotions.SOUL_REMOVAL_EFFECT)) {
				if (entity.world.getTotalWorldTime() % 20 == 0) {
					entity.addPotionEffect(new PotionEffect(ModPotions.SOUL_REMOVAL_EFFECT, 100, 1, true, false));
					entity.setHealth((float) (0.95 * entity.getHealth()));
					if (entity.getHealth() < 2) {
						entity.removeActivePotionEffect(ModPotions.SOUL_REMOVAL_EFFECT);
					}
				}
			}
		}
	}

	// 4D

	@SubscribeEvent
	public static void onLivingUpdateEvent4D(LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (player.isPotionActive(ModPotions.FOUR_DIMENSION_EFFECT)) {
				player.setInvisible(true);
				if (!flyingPlayer.contains(player.getName())) {
					flyingPlayer.add(player.getName());
				}
			} else if (!player.isPotionActive(ModPotions.FOUR_DIMENSION_EFFECT) && !player.isPotionActive(ModPotions.FLIGHT)) {
				if (flyingPlayer.contains(player.getName())) {
					flyingPlayer.remove(player.getName());
					if (!player.isSpectator() && !player.isCreative()) {
						player.capabilities.allowFlying = false;
						player.capabilities.isFlying = false;
					}
				}
				player.setInvisible(false);
			}
		}
	}

	@SubscribeEvent
	public static void onLivingHurt4D(LivingHurtEvent event) {
		if (event.getEntityLiving().isPotionActive(ModPotions.FOUR_DIMENSION_EFFECT)
				&& event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (player.inventory.hasItemStack(new ItemStack(ModItems.SCHRODINGERS_GIFT))) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void onPotionAddedEvent4D(PotionAddedEvent event) {
		if (event.getEntity().world.isRemote && event.getPotionEffect().getPotion() == ModPotions.FOUR_DIMENSION_EFFECT
				&& event.getEntity() instanceof EntityPlayer) {
			Main.proxy.loadShader((EntityPlayer) event.getEntity(), Potion4D.SHADER);
		}
	}

	// HEAVEN

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingUpdateEventHEAVEN(LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (player.isPotionActive(ModPotions.HEAVEN_EFFECT)) {
				player.setHealth(player.getMaxHealth());
				player.isDead = false;
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingHurtHEAVEN(LivingHurtEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (player.isPotionActive(ModPotions.HEAVEN_EFFECT)) {
				player.setHealth(player.getMaxHealth());
				player.isDead = false;
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingAttackHEAVEN(LivingAttackEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (player.isPotionActive(ModPotions.HEAVEN_EFFECT)) {
				player.setHealth(player.getMaxHealth());
				player.isDead = false;
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingDeathHEAVEN(LivingDeathEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (player.isPotionActive(ModPotions.HEAVEN_EFFECT)) {
				player.setHealth(player.getMaxHealth());
				player.isDead = false;
				event.setCanceled(true);
			}
		}
	}

	// SMOKESCREEN
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingSetAttackTargetSmokeScreen(LivingSetAttackTargetEvent event) {
		EntityLiving entity = (EntityLiving) event.getEntity();
		EntityLivingBase entityTarget = event.getTarget();
		if (entity.world.isRemote) {
			return;
		}
		if (entityTarget instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityTarget;
			if (player.isPotionActive(ModPotions.SMOKESCREEN_EFFECT)) {
				entity.setAttackTarget(null);
				entity.setRevengeTarget(null);
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingUpdateEventSmokeScreen(LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (player.isPotionActive(ModPotions.SMOKESCREEN_EFFECT)) {
				HeroicUtil.spawnParticleAtEntity(player, EnumParticleTypes.SMOKE_LARGE, 2);
			}
		}
	}
	
	// FLIGHT

	@SubscribeEvent
	public static void onLivingUpdateEventFlight(LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (player.isPotionActive(ModPotions.FLIGHT)) {
				if (!flyingPlayer.contains(player.getName())) {
					flyingPlayer.add(player.getName());
				}
			} else if (!player.isPotionActive(ModPotions.FOUR_DIMENSION_EFFECT) && !player.isPotionActive(ModPotions.FLIGHT)) {
				if (flyingPlayer.contains(player.getName())) {
					flyingPlayer.remove(player.getName());
					if (!player.isSpectator() && !player.isCreative()) {
						player.capabilities.allowFlying = false;
						player.capabilities.isFlying = false;
					}
				}
			}
		}
	}
	
	// TARGET OF THUNDER
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingUpdateThunder(LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (entity.isPotionActive(ModPotions.THUNDER) && entity.world.getWorldTime() % 20 == 0) {
			HeroicUtil.spawnParticleAtEntity(entity, EnumParticleTypes.FLAME, 5);
			PotionTargetOfThunder.performEffectConsistent(entity,
					entity.getActivePotionEffect(ModPotions.THUNDER).getAmplifier());
		}
	}

	// SHADER HANDLING

	@SubscribeEvent
	public static void onPlayerTickEvent(PlayerTickEvent event) {
		if (event.player == Minecraft.getMinecraft().player && event.phase == TickEvent.Phase.END) {
			if (Minecraft.getMinecraft().entityRenderer.getShaderGroup() != null) {
				String activeShader = Minecraft.getMinecraft().entityRenderer.getShaderGroup().getShaderGroupName();
				if ((activeShader.equals(PotionTimeStop.SHADER.toString())
						&& !Minecraft.getMinecraft().player.isPotionActive(ModPotions.TIME_STOP_EFFECT))
						|| (activeShader.equals(Potion4D.SHADER.toString())
								&& !Minecraft.getMinecraft().player.isPotionActive(ModPotions.FOUR_DIMENSION_EFFECT))) {
					Minecraft.getMinecraft().entityRenderer.stopUseShader();
				}
			}
		}
	}

}
