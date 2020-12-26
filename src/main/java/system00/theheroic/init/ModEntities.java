package system00.theheroic.init;

import system00.theheroic.Main;
import system00.theheroic.entity.misc.*;
import system00.theheroic.entity.render.RenderJudgementWeapon;
import system00.theheroic.entity.render.RenderSpecialFireball;
import system00.theheroic.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class ModEntities {
	
	public static void registerEntities() {
		int index = 0;
		registerProjectile("special_fireball", index++, SpecialFireball.class);
		registerProjectile("special_pearl", index++, SpecialPearl.class);
		registerProjectile("magiball", index++, EntityMagiball.class);
		registerProjectile("mini_fireball", index++, EntityMiniFireball.class);
		registerProjectile("judgement_weapon", index++, EntityJudgementWeapon.class);
		registerRenders();
	}
	
	@SuppressWarnings("unused")
	private static void registerMob(String name, int id, Class<? extends Entity> entity, int range, int color1, int color2) {
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":" + name), entity, name, id, Main.instance, range, 3, true, color1, color2);
	}
	
	@SuppressWarnings("unused")
	private static void registerEntity(String name, int id, Class<? extends Entity> entity, int range) {
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":" + name), entity, name, id, Main.instance, range, 3, true);
	}
	
	private static void registerProjectile(String name, int id, Class<? extends Entity> entity) {
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":" + name), entity, name, id, Main.instance, 64, 10, true);
	}
	
	/*private static void registerProjectile(String name, int id, Class<? extends Entity> entity, Item item) {
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":" + name), entity, name, id, Main.instance, 64, 10, true);
	}*/
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	private static void registerRenders() {
		RenderingRegistry.registerEntityRenderingHandler(SpecialFireball.class, renderManager -> new RenderSpecialFireball(renderManager));
		RenderingRegistry.registerEntityRenderingHandler(SpecialPearl.class, renderManager -> new RenderSnowball(renderManager, Items.ENDER_PEARL, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityMagiball.class, renderManager -> new RenderSnowball(renderManager, ModItems.MAGIBALL, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityMiniFireball.class, renderManager -> new RenderSnowball(renderManager, Items.FIRE_CHARGE, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityJudgementWeapon.class, renderManager -> new RenderJudgementWeapon(renderManager));
	}
}
