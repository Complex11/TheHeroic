package system00.theheroic.items.consumables;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import system00.theheroic.items.FoodBase;
import system00.theheroic.util.Reference;

import javax.annotation.Nullable;
import java.util.List;


public class GummyHeart extends FoodBase {

	public GummyHeart(String name, int amount, float saturation, boolean isAnimalFood) {
			super(name, amount, saturation, isAnimalFood);
			setAlwaysEdible();
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§dWhen eaten: Restore 2 health and gain §2Regeneration[2]§7 for 10 seconds.");
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
	{
		if(!worldIn.isRemote) {
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 1, false, false));
			player.setHealth(player.getHealth() + 2);
		}
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":food/gummy_heart", "inventory"));
	}
}
