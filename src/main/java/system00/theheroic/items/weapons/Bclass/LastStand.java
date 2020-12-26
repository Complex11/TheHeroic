package system00.theheroic.items.weapons.Bclass;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import system00.theheroic.Main;
import system00.theheroic.items.tool.ToolSword;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.Reference;

import javax.annotation.Nullable;
import java.util.List;

public class LastStand extends ToolSword {

	public LastStand(String name, ToolMaterial material) {
		super(name, material);
		setCreativeTab(Main.heroicweaponstabB);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §aA"); //e = C, b = B, a = A, 1 = S
		tooltip.add("§d§lPassive: §r§7Kills target.");
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		target.setDead();
		HeroicUtil.spawnParticleAtEntity(target, EnumParticleTypes.WATER_BUBBLE, 40);
		stack.shrink(1);
		attacker.playSound(SoundEvents.ENTITY_ENDERDRAGON_DEATH, 0.9f, 1f);
        return true;
    }
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/aclass/last_stand", "inventory"));
	}
}
