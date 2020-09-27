package complex11.theheroic.event;

import java.util.Set;

import com.google.common.collect.Sets;

import complex11.theheroic.Main;
import complex11.theheroic.init.ModItems;
import complex11.theheroic.init.ModPotions;
import complex11.theheroic.potions.Potion4D;
import complex11.theheroic.potions.PotionTimeStop;
import complex11.theheroic.util.HeroicUtil;
import complex11.theheroic.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
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

@EventBusSubscriber(modid = Reference.MODID)
public class PotionEvent {

	//START TIME STOP
	
	@SubscribeEvent
	public static void onLivingUpdateEventTime(LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (entity.isPotionActive(ModPotions.TIME_STOP_EFFECT)) {
			PotionTimeStop.performEffectConsistent(entity, entity.getActivePotionEffect(ModPotions.TIME_STOP_EFFECT).getAmplifier());
		}
	}
	
	@SubscribeEvent
	public static void onPotionAddedEventTime(PotionAddedEvent event) {
		if (event.getEntity().world.isRemote && event.getPotionEffect().getPotion() == ModPotions.TIME_STOP_EFFECT
				&& event.getEntity() instanceof EntityPlayer) {
			Main.proxy.loadShader((EntityPlayer)event.getEntity(), PotionTimeStop.SHADER);
		}
	}

	@SubscribeEvent
	public static void timeTick(WorldTickEvent event) {
		if (!event.world.isRemote && event.phase == TickEvent.Phase.END) {
			PotionTimeStop.cleanUpEntities(event.world);
		}
	}
	
	@SubscribeEvent
	public static void onClientTickEvent(ClientTickEvent event){
		if(event.phase == TickEvent.Phase.END && !net.minecraft.client.Minecraft.getMinecraft().isGamePaused()) {
			World world = net.minecraft.client.Minecraft.getMinecraft().world;
			if(world == null) return;
			PotionTimeStop.cleanUpEntities(world);
		}
	}
	
	@SubscribeEvent
	public static void onPlayerLoggedOutEvent(PlayerLoggedOutEvent event) {
		if (event.player.updateBlocked) {
			event.player.updateBlocked = false;
		}
	}

	//END TIME STOP
	
	//START BLEED
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingUpdate(LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (entity.isPotionActive(ModPotions.BLEED_EFFECT)) {
			if (entity.world.getWorldTime() % 20 == 0) {
				entity.attackEntityFrom(DamageSource.GENERIC, 1);
				HeroicUtil.spawnParticleAtEntity(entity, EnumParticleTypes.DAMAGE_INDICATOR, 1);
			}
		}
	}
	
	//END BLEED
	
	//START SOUL REMOVAL
	
	@SubscribeEvent
	public static void onLivingUpdateSoul(LivingUpdateEvent event) {
		if (event.getEntity() instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase) event.getEntity();
			if (entity.isPotionActive(ModPotions.SOUL_REMOVAL_EFFECT) && entity.world.getTotalWorldTime() % 20 == 0) {
				entity.addPotionEffect(new PotionEffect(ModPotions.SOUL_REMOVAL_EFFECT, 100, 1, true, false));
				entity.setHealth((float) (0.95 * entity.getHealth()));
				if (entity.getHealth() < 2) {
					entity.removeActivePotionEffect(ModPotions.SOUL_REMOVAL_EFFECT);
				}
			}
		}
	}
	
	//END SOUL REMOVAL
	
	//START 4D

	private static Set<String> flyingPlayer = Sets.newHashSet();

	@SubscribeEvent
	public static void onLivingUpdateEvent4D(LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (player.isPotionActive(ModPotions.FOUR_DIMENSION_EFFECT)) {
				player.setInvisible(true);
				if (!flyingPlayer.contains(player.getName())) {
					flyingPlayer.add(player.getName());
					player.capabilities.allowFlying = true;
				}
			} else if (!player.isPotionActive(ModPotions.FOUR_DIMENSION_EFFECT)) {
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
	public static void onLivingAttack(LivingAttackEvent event) {
		if (event.getEntityLiving().isPotionActive(ModPotions.FOUR_DIMENSION_EFFECT)
				&& event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (player.inventory.hasItemStack(new ItemStack(ModItems.REALITY_BLADE))) {
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
	
	//END 4D
	
	//OTHER
	@SubscribeEvent
	public static void onPlayerTickEvent(PlayerTickEvent event){
		if(event.player == Minecraft.getMinecraft().player && event.phase == TickEvent.Phase.END){
			if(Minecraft.getMinecraft().entityRenderer.getShaderGroup() != null){
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
