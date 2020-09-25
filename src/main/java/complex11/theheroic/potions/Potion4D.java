package complex11.theheroic.potions;

import java.util.Set;

import com.google.common.collect.Sets;

import complex11.theheroic.Main;
import complex11.theheroic.init.ModItems;
import complex11.theheroic.init.ModPotions;
import complex11.theheroic.util.Reference;
import complex11.theheroic.util.interfaces.ISyncedPotion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionAddedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class Potion4D extends PotionBase implements ISyncedPotion {

	private static Set<String> flyingPlayer = Sets.newHashSet();
	
	public static final ResourceLocation SHADER = new ResourceLocation("shaders/post/desaturate.json");

	public Potion4D(boolean isBadEffect, int liquidColour) {
		super(isBadEffect, liquidColour, new ResourceLocation(Reference.MODID, "textures/gui/potion_icon_fourd.png"));
		this.setPotionName("effect." + Reference.MODID + ":4_D");
	}

	@SubscribeEvent
	public static void onLivingUpdateEvent(LivingUpdateEvent event) {
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
	public static void onPotionAddedEvent(PotionAddedEvent event) {
		if (event.getEntity().world.isRemote && event.getPotionEffect().getPotion() == ModPotions.FOUR_DIMENSION_EFFECT
				&& event.getEntity() instanceof EntityPlayer) {
			Main.proxy.loadShader((EntityPlayer) event.getEntity(), SHADER);
		}
	}
}