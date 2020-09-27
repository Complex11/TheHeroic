package complex11.theheroic.potions;

import java.util.ArrayList;
import java.util.List;

import complex11.theheroic.init.ModPotions;
import complex11.theheroic.util.HeroicUtil;
import complex11.theheroic.util.Reference;
import complex11.theheroic.util.interfaces.ISyncedPotion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class PotionTimeStop extends PotionBase implements ISyncedPotion {

	public static final String NBT_KEY = "time_stopped";
	public static final ResourceLocation SHADER = new ResourceLocation("shaders/post/invert.json");
	
	public PotionTimeStop(boolean isBadEffect, int liquidColour) {
		super(isBadEffect, liquidColour, new ResourceLocation(Reference.MODID, "textures/gui/potion_icon_time_stop.png"));
		this.setPotionName("effect." + Reference.MODID + ":time_stopped");
	}

	private static double getEffectRadius() {
		return 10.0;
	}

	public static void unblockNearbyEntities(EntityLivingBase host) {
		List<Entity> targetsBeyondRange = HeroicUtil.getEntitiesWithinRadius(getEffectRadius() + 3, host.posX, host.posY, host.posZ, host.world, Entity.class);
		targetsBeyondRange.forEach(e -> e.updateBlocked = false);
	}

	public static void performEffectConsistent(EntityLivingBase host, int strength) {
		List<Entity> targetsInRange = HeroicUtil.getEntitiesWithinRadius(getEffectRadius(), host.posX, host.posY, host.posZ, host.world, Entity.class);
		targetsInRange.remove(host);
		for (Entity entity : targetsInRange) {
			entity.getEntityData().setBoolean(NBT_KEY, true);
			entity.updateBlocked = true;
		}
		List<Entity> targetsBeyondRange = HeroicUtil.getEntitiesWithinRadius(getEffectRadius() + 3, host.posX, host.posY, host.posZ, host.world, Entity.class);
		targetsBeyondRange.removeAll(targetsInRange);
		targetsBeyondRange.forEach(e -> e.updateBlocked = false);
	}

	public static void cleanUpEntities(World world) {
		List<Entity> loadedEntityList = new ArrayList<>(world.loadedEntityList);
		for (Entity entity : loadedEntityList) {
			if (entity.getEntityData().getBoolean(NBT_KEY)) {
				List<EntityLivingBase> nearby = HeroicUtil.getEntitiesWithinRadius(getEffectRadius(), entity.posX, entity.posY, entity.posZ, entity.world, EntityLivingBase.class);
				if (nearby.stream().noneMatch(e -> e.isPotionActive(ModPotions.TIME_STOP_EFFECT))) {
					entity.getEntityData().removeTag(NBT_KEY);
					entity.updateBlocked = false;
				}
			}
		}
	}
}
