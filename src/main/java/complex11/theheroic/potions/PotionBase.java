package complex11.theheroic.potions;

import complex11.theheroic.util.DrawingUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionBase extends Potion {

	private final ResourceLocation texture;
	
	public PotionBase(boolean isBadEffect, int color, ResourceLocation texture) {
		super(isBadEffect, color);
		this.texture = texture;
	}

	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc){
		drawIcon(x + 6, y + 7, effect, mc);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUDEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc, float alpha){
		net.minecraft.client.renderer.GlStateManager.color(1, 1, 1, alpha);
		drawIcon(x + 3, y + 3, effect, mc);
	}

	@SideOnly(Side.CLIENT)
	protected void drawIcon(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc){
		mc.renderEngine.bindTexture(texture);
		DrawingUtil.drawTexturedRect(x, y, 0, 0, 18, 18, 18, 18);
	}
	
}
