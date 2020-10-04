package complex11.theheroic.event;

import java.util.List;

import complex11.theheroic.init.ModItems;
import complex11.theheroic.items.weapons.Bclass.Burster;
import complex11.theheroic.items.weapons.Bclass.FlameWaker;
import complex11.theheroic.items.weapons.Bclass.FruitNinja;
import complex11.theheroic.items.weapons.Bclass.StreamsOfPain;
import complex11.theheroic.items.weapons.Cclass.GlassyEdge;
import complex11.theheroic.items.weapons.Cclass.SpecialShock;
import complex11.theheroic.items.weapons.Cclass.SpeedsterSword;
import complex11.theheroic.items.weapons.Sclass.HeavenlySmite;
import complex11.theheroic.items.weapons.Sclass.RealityBlade;
import complex11.theheroic.network.AreaSmitePacket;
import complex11.theheroic.util.HeroicUtil;
import complex11.theheroic.util.Reference;
import complex11.theheroic.util.handlers.NetworkHandler;
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

@EventBusSubscriber(modid = Reference.MODID)
public class WeaponEvent {

	// S CLASS

	// REALITY BLADE

	@SubscribeEvent
	public static void onLivingHurtReality(LivingHurtEvent event) {
		if (event.getEntityLiving().getEntityData().getBoolean(RealityBlade.NBT_KEY)) {
			if (!(event.getSource().getTrueSource() instanceof EntityPlayer)) {
				event.setCanceled(true);
			} else if (event.getSource().getTrueSource() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
				if (!(player.getHeldItemMainhand().getItem() instanceof RealityBlade)) {
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onLivingAttackReality(LivingAttackEvent event) {
		if (event.getEntityLiving().getEntityData().getBoolean(RealityBlade.NBT_KEY)) {
			if (!(event.getSource().getTrueSource() instanceof EntityPlayer)) {
				event.setCanceled(true);
			} else if (event.getSource().getTrueSource() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
				if (!(player.getHeldItemMainhand().getItem() instanceof RealityBlade)) {
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingDeathReality(LivingDeathEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (player.inventory.hasItemStack(new ItemStack(ModItems.REALITY_BLADE)) && !RealityBlade.realityBend) {
				event.setCanceled(true);
				player.setHealth(player.getMaxHealth());
				HeroicUtil.SendMsgToPlayer("Reality Bent!", player);
				RealityBlade.realityBend = true;
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void extinguishEntities(LivingUpdateEvent event) {
		if (event.getEntityLiving().isBurning()
				&& event.getEntityLiving().getEntityData().getBoolean(RealityBlade.NBT_KEY)) {
			event.getEntityLiving().extinguish();
		}
		String string1 = "11:11";
		if (java.time.LocalTime.now().toString().contains(string1)
				&& event.getEntityLiving().getEntityData().getBoolean(RealityBlade.NBT_KEY)) {
			World world = event.getEntityLiving().world;
			world.removeEntityDangerously(event.getEntityLiving());
		}
	}

	// HEAVENLY SMITE

	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
	@SideOnly(Side.CLIENT)
	public void onLeftClickHeavenly(PlayerInteractEvent.LeftClickEmpty event) {
		EntityPlayer player = event.getEntityPlayer();
		if (!player.isSpectator() && !player.getHeldItemMainhand().isEmpty()
				&& player.getHeldItemMainhand().getItem() instanceof HeavenlySmite) {
			if (player.world.isRemote) {
				NetworkHandler.INSTANCE.sendMessageToServer(new AreaSmitePacket());
			}
		}
	}

	// A CLASS

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
				HeroicUtil.spawnParticleAtEntity(event.getEntityLiving(), EnumParticleTypes.SMOKE_NORMAL, 2);
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
}
