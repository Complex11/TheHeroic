package system00.theheroic.items.weapons.Sclass;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import system00.theheroic.Main;
import system00.theheroic.entity.misc.EntityJudgementWeapon;
import system00.theheroic.init.ModPotions;
import system00.theheroic.items.ItemBase;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.ItemNBTHelper;
import system00.theheroic.util.Reference;
import system00.theheroic.util.math.Vector3;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class Judgement extends ItemBase {

    public static String NBT_KEY = "judged";

    private static final String TAG_WEAPONS_SPAWNED = "weaponsSpawned";
    private static final String TAG_CHARGING = "charging";


    public Judgement(String name) {
        super(name);
        setMaxDamage(55555);
        setMaxStackSize(1);
        setCreativeTab(Main.heroicweaponstabS);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        tooltip.add("§9§lClass: §1S"); //e = C, b = B, a = A, 1 = S
        tooltip.add("§d§lPassive: §r§7Mark target entity with §3Judgement§7 and deal 2 magic damage.");
        tooltip.add("§d§lSpecial Ability: §r§7Summon 20 §3Eyes Of Judgement§7. Each eye does 100000 magic damage and explodes on impact. §fCooldown: 0 seconds.");
        tooltip.add("§4§lAura: §r§2Resistance[4]§7 while held.");
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (!target.getEntityData().getBoolean(NBT_KEY)) {
            target.getEntityData().setBoolean(NBT_KEY, true);
            target.attackEntityFrom(DamageSource.MAGIC, 2);
        }
        HeroicUtil.damageAndCheckItem(stack, 1);
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
        player.setActiveHand(handIn);
        ItemStack stack = player.getHeldItem(handIn);
        setCharging(stack, true);
        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase living, int time) {
        int spawned = getWeaponsSpawned(stack);
        if (spawned == 20) {
            setCharging(stack, false);
            setWeaponsSpawned(stack, 0);
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase living, int count) {
        living.addPotionEffect(new PotionEffect(ModPotions.TIME_STOP_EFFECT, 20, 0, false, false));
        HeroicUtil.spawnParticleAtEntity(living, EnumParticleTypes.ENCHANTMENT_TABLE, 10);
        int spawned = getWeaponsSpawned(stack);

        if (count != getMaxItemUseDuration(stack) && spawned < 20 && !living.world.isRemote) {
            Vector3 look = new Vector3(living.getLookVec()).multiply(1, 0, 1);

            double playerRot = Math.toRadians(living.rotationYaw + 90);
            if (look.x == 0 && look.z == 0)
                look = new Vector3(Math.cos(playerRot), 0, Math.sin(playerRot));

            look = look.normalize().multiply(-2);

            int div = spawned / 5;
            int mod = spawned % 5;

            Vector3 pl = look.add(Vector3.fromEntityCenter(living)).add(0, 1.6, div * 0.1);

            Random rand = living.world.rand;
            Vector3 axis = look.normalize().crossProduct(new Vector3(-1, 0, -1)).normalize();

            double rot = mod * Math.PI / 4 - Math.PI / 2;

            Vector3 axis1 = axis.multiply(div * 3.5 + 5).rotate(rot, look);
            if (axis1.y < 0) {
                axis1 = axis1.multiply(1, -1, 1);
            }
            Vector3 end = pl.add(axis1);

            EntityJudgementWeapon weapon = new EntityJudgementWeapon(living.world, living);
            weapon.posX = end.x;
            weapon.posY = end.y;
            weapon.posZ = end.z;
            weapon.rotationYaw = living.rotationYaw;
            weapon.setDelay(spawned);
            weapon.setRotation(MathHelper.wrapDegrees(-living.rotationYaw + 180));

            living.world.spawnEntity(weapon);
            weapon.playSound(SoundEvents.ENTITY_WITHER_SHOOT, 1F, 1F + living.world.rand.nextFloat() * 3F);
            setWeaponsSpawned(stack, spawned + 1);
        }
    }

    @Nonnull
    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 144000;
    }

    public static boolean isCharging(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, TAG_CHARGING, false);
    }

    public static int getWeaponsSpawned(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_WEAPONS_SPAWNED, 0);
    }

    public static void setCharging(ItemStack stack, boolean charging) {
        ItemNBTHelper.setBoolean(stack, TAG_CHARGING, charging);
    }

    public static void setWeaponsSpawned(ItemStack stack, int count) {
        ItemNBTHelper.setInt(stack, TAG_WEAPONS_SPAWNED, count);
    }

    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
        if (entity instanceof EntityLivingBase) {
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 4, false, false));
        }
    }

    @Override
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Reference.MODID + ":weapons/sclass/judgement", "inventory"));
    }
}
