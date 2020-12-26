package system00.theheroic.items.weapons.Aclass;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import system00.theheroic.Main;
import system00.theheroic.entity.misc.EntityMagiball;
import system00.theheroic.items.ItemBase;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.Reference;

import javax.annotation.Nullable;
import java.util.List;

public class Earthscorn extends ItemBase {

	public Earthscorn(String name) {
		super(name);
		this.setMaxDamage(5700);
		this.setMaxStackSize(1);
		setCreativeTab(Main.heroicweaponstabA);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add("§9§lClass: §aA"); //e = C, b = B, a = A, 1 = S
		tooltip.add("§d§lPassive: §r§7Deals 8+ lightning damage.");
		tooltip.add("§d§lSpecial Ability: §r§7Shoots 16 Magiballs that deal 6 damage each and apply §2Slowness[2]§7 for 60 seconds. Magiballs that strike entities summon lightning. §fCooldown: 10 seconds.");
		tooltip.add("§4§lAura: §r§2Haste[2] §7while held.");
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		World world = target.world;
		target.attackEntityFrom(DamageSource.LIGHTNING_BOLT, 8);
		HeroicUtil.SummonLightningAt(world, target.posX, target.posY, target.posZ);
		HeroicUtil.damageAndCheckItem(stack, 1);
        return true;
    }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack item = playerIn.getHeldItem(handIn);
		Vec3d vec = playerIn.getLookVec();
		if (!worldIn.isRemote) {
			vec = vec.scale(-4 / vec.lengthVector());
			double cphi = vec.x / new Vec3d(vec.x, 0, vec.z).lengthVector();
			double cpsi = vec.y / 4;
			for (int i = 0; i < 18; ++i) {
				EntityMagiball ball = new EntityMagiball(worldIn);
				double r = playerIn.getRNG().nextDouble() * 20 - 10;
				float theta = (float) (playerIn.getRNG().nextFloat() * 2 * Math.PI);
				Vec3d at = HeroicUtil.applyRotationMatrix(new Vec3d(r, 0, 0).rotateYaw(theta), cphi, cpsi);
				ball.setPosition(playerIn.posX + vec.x + at.x, playerIn.posY + vec.y + at.y + 9, playerIn.posZ + vec.z + at.z);
				ball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 3.75F, 0.5F);
				worldIn.spawnEntity(ball);
			}
		}
		playerIn.getCooldownTracker().setCooldown(this, 200);
		HeroicUtil.damageAndCheckItem(item, 1);
		playerIn.playSound(SoundEvents.BLOCK_CHORUS_FLOWER_GROW, 1.6f, 0.4f);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}

	@Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		if (entity instanceof EntityLivingBase) {
			EntityLivingBase entityLiving = (EntityLivingBase) entity;
			entityLiving.addPotionEffect(new PotionEffect(MobEffects.HASTE, 200, 1, false, false));
		}
    }
	
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/aclass/earthscorn", "inventory"));
	}
}
