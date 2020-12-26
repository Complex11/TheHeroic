package system00.theheroic.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import system00.theheroic.init.ModItems;
import system00.theheroic.items.weapons.Bclass.*;
import system00.theheroic.items.weapons.Cclass.GlassyEdge;
import system00.theheroic.items.weapons.Cclass.NatureSlayer;
import system00.theheroic.items.weapons.Cclass.SpecialShock;
import system00.theheroic.items.weapons.Cclass.SpeedsterSword;
import system00.theheroic.items.weapons.Sclass.GodOfThunder;
import system00.theheroic.items.weapons.Sclass.HeavenlySmite;
import system00.theheroic.items.weapons.Sclass.Judgement;
import system00.theheroic.items.weapons.Sclass.SchrodingersGift;
import system00.theheroic.network.AreaSmitePacket;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.Reference;
import system00.theheroic.util.handlers.NetworkHandler;

import java.util.List;

@EventBusSubscriber(modid = Reference.MODID)
public class WeaponEvent {

	// S CLASS

	// SCHRÖDINGER'S GIFT

	@SubscribeEvent
	public static void onLivingHurtReality(LivingHurtEvent event) {
		if (event.getEntityLiving().getEntityData().getBoolean(SchrodingersGift.NBT_KEY)) {
			if (!(event.getSource().getTrueSource() instanceof EntityPlayer)) {
				event.setCanceled(true);
			} else if (event.getSource().getTrueSource() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
				if (!(player.getHeldItemMainhand().getItem() instanceof SchrodingersGift)) {
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onLivingAttackReality(LivingAttackEvent event) {
		if (event.getEntityLiving().getEntityData().getBoolean(SchrodingersGift.NBT_KEY)) {
			if (!(event.getSource().getTrueSource() instanceof EntityPlayer)) {
				event.setCanceled(true);
			} else if (event.getSource().getTrueSource() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
				if (!(player.getHeldItemMainhand().getItem() instanceof SchrodingersGift)) {
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingDeathReality(LivingDeathEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (player.inventory.hasItemStack(new ItemStack(ModItems.SCHRODINGERS_GIFT)) && !SchrodingersGift.realityBend) {
				event.setCanceled(true);
				player.setHealth(player.getMaxHealth());
				HeroicUtil.SendMsgToPlayer("§4§lReality Bent!", player);
				SchrodingersGift.realityBend = true;
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void realityUpdateEvent(LivingUpdateEvent event) {
		if (event.getEntityLiving().isBurning()
				&& event.getEntityLiving().getEntityData().getBoolean(SchrodingersGift.NBT_KEY)) {
			event.getEntityLiving().extinguish();
		}
		String string1 = "11:11";
		if (java.time.LocalTime.now().toString().contains(string1)
				&& event.getEntityLiving().getEntityData().getBoolean(SchrodingersGift.NBT_KEY)) {
			World world = event.getEntityLiving().world;
			world.removeEntityDangerously(event.getEntityLiving());
		}
	}

	// HEAVENLY SMITE

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onLeftClickHeavenly(PlayerInteractEvent.LeftClickEmpty event) {
		EntityPlayer player = event.getEntityPlayer();
		if (!player.isSpectator() && player.getHeldItemMainhand().getItem() instanceof HeavenlySmite) {
			if (!player.world.isRemote) {
				NetworkHandler.INSTANCE.sendMessageToServer(new AreaSmitePacket());
			}
		}
	}

	// GOD OF THUNDER
	
	@SubscribeEvent
	public static void thunderUpdateEvent(LivingUpdateEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (player.isBurning()
					&& player.inventory.hasItemStack(new ItemStack(ModItems.GOD_OF_THUNDER))) {
				player.extinguish();
			}
		}
	}
	
	@SubscribeEvent
	public static void onLivingHurtThunder(LivingHurtEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (player.getHeldItemMainhand().getItem() instanceof GodOfThunder) {
				event.setCanceled(true);
				player.setHealth(player.getHealth() - 1);
			}
		}
	}

	// JUDGEMENT

	@SubscribeEvent
	public static void onLivingUpdateJudgement(LivingUpdateEvent event) {
		if (event.getEntityLiving().getEntityData().getBoolean(Judgement.NBT_KEY)) {
			EntityLivingBase entity = event.getEntityLiving();
			if (entity.world.getTotalWorldTime() % 20 == 0) {
				HeroicUtil.createSpecialExplosionDamage(100, 4, entity.world, entity.posX, entity.posY, entity.posZ);
				HeroicUtil.spawnParticleAtEntity(event.getEntityLiving(), EnumParticleTypes.VILLAGER_ANGRY, 10);
			}
		}
	}

	// A CLASS

	@SubscribeEvent
	public static void tooltipEventPrime(ItemTooltipEvent event) {
		if (!event.getItemStack().isEmpty() && event.getItemStack().getItem() instanceof PrimeTarget) {
			List<String> tooltip = event.getToolTip();
			for (int i = 0; i < tooltip.size(); i++) {
				String tip = tooltip.get(i);
				if (tip.contains("§6Bound: ")) {
					tooltip.set(i, "§6Bound: " + PrimeTarget.bound);
				}
			}
		}
	}
	
	// B CLASS

	// STREAMS OF PAIN

	@SubscribeEvent
	public static void drownedUpdate(LivingUpdateEvent event) {
		if (event.getEntityLiving().getEntityData().getBoolean(StreamsOfPain.NBT_KEY)) {
			EntityLivingBase entity = event.getEntityLiving();
			if (entity.world.getTotalWorldTime() % 20 == 0) {
				StreamsOfPain.air = StreamsOfPain.air - 20;
				if (StreamsOfPain.air < 0) {
					entity.attackEntityFrom(DamageSource.DROWN, 4);
				}
				if (entity.getHealth() < entity.getMaxHealth() * 0.2) {
					entity.getEntityData().removeTag(StreamsOfPain.NBT_KEY);
				}
				HeroicUtil.spawnParticleAtEntity(event.getEntityLiving(), EnumParticleTypes.WATER_SPLASH, 16);
			}
		}
	}

	// FRUIT NINJA

	@SubscribeEvent
	public static void tooltipEventNinja(ItemTooltipEvent event) {
		if (!event.getItemStack().isEmpty() && event.getItemStack().getItem() instanceof FruitNinja) {
			List<String> tooltip = event.getToolTip();
			for (int i = 0; i < tooltip.size(); i++) {
				String tip = tooltip.get(i);
				if (tip.contains("§6Power Of The Fruit: ")) {
					tooltip.set(i, "§6Power Of The Fruit: " + FruitNinja.damage);
				}
			}
		}
	}

	// FLAMEWAKER

	@SubscribeEvent
	public static void tooltipEventFlamewaker(ItemTooltipEvent event) {
		if (!event.getItemStack().isEmpty() && event.getItemStack().getItem() instanceof FlameWaker) {
			List<String> tooltip = event.getToolTip();
			for (int i = 0; i < tooltip.size(); i++) {
				String tip = tooltip.get(i);
				if (tip.contains("§6Hellfire: ")) {
					tooltip.set(i, "§6Hellfire: " + FlameWaker.hitCount);
				}
			}
		}
	}

	// BURSTER

	@SubscribeEvent
	public static void tooltipEventBurster(ItemTooltipEvent event) {
		if (!event.getItemStack().isEmpty() && event.getItemStack().getItem() instanceof Burster) {
			List<String> tooltip = event.getToolTip();
			for (int i = 0; i < tooltip.size(); i++) {
				String tip = tooltip.get(i);
				if (tip.contains("§6Burst: ")) {
					tooltip.set(i, "§6Burst: " + Burster.damage);
				}
			}
		}
	}

	// C CLASS

	// SPECIAL SHOCK

	@SubscribeEvent
	public static void shockedUpdate(LivingUpdateEvent event) {
		if (event.getEntityLiving().getEntityData().getBoolean(SpecialShock.NBT_KEY)) {
			if (event.getEntityLiving().world.getTotalWorldTime() % 20 == 0) {
				HeroicUtil.spawnParticleAtEntity(event.getEntityLiving(), EnumParticleTypes.SMOKE_NORMAL, 5);
			}
		}
	}

	// SPEEDSTER SWORD

	@SubscribeEvent
	public static void tooltipEventSpeedster(ItemTooltipEvent event) {
		if (!event.getItemStack().isEmpty() && event.getItemStack().getItem() instanceof SpeedsterSword) {
			List<String> tooltip = event.getToolTip();
			for (int i = 0; i < tooltip.size(); i++) {
				String tip = tooltip.get(i);
				if (tip.contains("§6Speedy Stacks: ")) {
					tooltip.set(i, "§6Speedy Stacks: " + SpeedsterSword.count);
				}
			}
		}
	}

	// GLASSY EDGE

	@SubscribeEvent
	public static void tooltipEventGlassy(ItemTooltipEvent event) {
		if (!event.getItemStack().isEmpty() && event.getItemStack().getItem() instanceof GlassyEdge) {
			List<String> tooltip = event.getToolTip();
			for (int i = 0; i < tooltip.size(); i++) {
				String tip = tooltip.get(i);
				if (tip.contains("§6Shatter: ")) {
					tooltip.set(i, "§6Shatter: " + GlassyEdge.shatter);
				}
			}
		}
	}

	// NATURE SLAYER

	@SubscribeEvent
	public static void tooltipEventNature(ItemTooltipEvent event) {
		if (!event.getItemStack().isEmpty() && event.getItemStack().getItem() instanceof NatureSlayer) {
			List<String> tooltip = event.getToolTip();
			for (int i = 0; i < tooltip.size(); i++) {
				String tip = tooltip.get(i);
				if (tip.contains("§6Apple Counter: ")) {
					tooltip.set(i, "§6Apple Counter: " + NatureSlayer.count);
				}
			}
		}
	}
}
