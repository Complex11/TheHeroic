package system00.theheroic.items.consumables;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import system00.theheroic.init.ModPotions;
import system00.theheroic.items.FoodBase;
import system00.theheroic.util.Reference;

import javax.annotation.Nullable;
import java.util.List;


public class Ragetable extends FoodBase {

	public Ragetable(String name, int amount, float saturation, boolean isAnimalFood) {
			super(name, amount, saturation, isAnimalFood);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§dWhen eaten: Gain §2Strength[10]§7 and §2Bleed[1]§7 for 20 seconds.");
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
	{
		if(!worldIn.isRemote) {
			player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 400, 9, false, false));
			player.addPotionEffect(new PotionEffect(ModPotions.BLEED_EFFECT, 400, 0, false, false));
		}
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":food/ragetable", "inventory"));
	}
}
