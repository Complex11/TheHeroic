package system00.theheroic.entity.misc;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import system00.theheroic.init.ModItems;
import system00.theheroic.items.weapons.Sclass.Judgement;
import system00.theheroic.util.HeroicUtil;
import system00.theheroic.util.PlayerUtil;
import system00.theheroic.util.math.Vector3;

import javax.annotation.Nonnull;
import java.util.List;

public class EntityJudgementWeapon extends EntityThrowableCopy {

    private static final String TAG_CHARGING = "charging";
    private static final String TAG_VARIETY = "variety";
    private static final String TAG_CHARGE_TICKS = "chargeTicks";
    private static final String TAG_LIVE_TICKS = "liveTicks";
    private static final String TAG_DELAY = "delay";
    private static final String TAG_ROTATION = "rotation";

    private static final DataParameter<Boolean> CHARGING = EntityDataManager.createKey(EntityJudgementWeapon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> VARIETY = EntityDataManager.createKey(EntityJudgementWeapon.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> CHARGE_TICKS = EntityDataManager.createKey(EntityJudgementWeapon.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> LIVE_TICKS = EntityDataManager.createKey(EntityJudgementWeapon.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DELAY = EntityDataManager.createKey(EntityJudgementWeapon.class, DataSerializers.VARINT);
    private static final DataParameter<Float> ROTATION = EntityDataManager.createKey(EntityJudgementWeapon.class, DataSerializers.FLOAT);

    public EntityJudgementWeapon(World world) {
        super(world);
    }

    public EntityJudgementWeapon(World world, EntityLivingBase thrower) {
        super(world, thrower);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        setSize(0F, 0F);

        dataManager.register(CHARGING, false);
        dataManager.register(CHARGE_TICKS, 0);
        dataManager.register(LIVE_TICKS, 0);
        dataManager.register(DELAY, 0);
        dataManager.register(ROTATION, 0F);
    }

    @Override
    public boolean isImmuneToExplosions() {
        return true;
    }

    @Override
    public void onUpdate() {
        EntityLivingBase thrower = getThrower();
        if(!world.isRemote && (thrower == null || !(thrower instanceof EntityPlayer) || thrower.isDead)) {
            setDead();
            return;
        }
        EntityPlayer player = (EntityPlayer) thrower;
        boolean charging = isCharging();
        if(!world.isRemote) {
            ItemStack stack = player == null ? ItemStack.EMPTY : PlayerUtil.getFirstHeldItem(player, ModItems.JUDGEMENT);
            boolean newCharging = !stack.isEmpty() && Judgement.isCharging(stack);
            if(charging != newCharging) {
                setCharging(newCharging);
                charging = newCharging;
            }
        }

        double x = motionX;
        double y = motionY;
        double z = motionZ;

        int liveTime = getLiveTicks();
        int delay = getDelay();
        charging &= liveTime == 0;

        if(charging) {
            motionX = 0;
            motionY = 0;
            motionZ = 0;

            int chargeTime = getChargeTicks();
            setChargeTicks(chargeTime + 1);

            if(world.rand.nextInt(20) == 0)
                world.playSound(null, posX, posY, posZ, SoundEvents.ENTITY_WITHER_AMBIENT, SoundCategory.PLAYERS, 0.1F, 1F + world.rand.nextFloat() * 3F);
        } else {
            if(liveTime < delay) {
                motionX = 0;
                motionY = 0;
                motionZ = 0;
            } else if (liveTime == delay && player != null) {
                Vector3 playerLook;
                RayTraceResult lookat = HeroicUtil.raytraceFromEntity(world, player, true, 64);
                if(lookat == null) {
                    playerLook = new Vector3(player.getLookVec()).multiply(64).add(Vector3.fromEntity(player));
                } else {
                    playerLook = new Vector3(lookat.getBlockPos().getX() + 0.5, lookat.getBlockPos().getY() + 0.5, lookat.getBlockPos().getZ() + 0.5);
                }
                Vector3 thisVec = Vector3.fromEntityCenter(this);
                Vector3 motionVec = playerLook.subtract(thisVec).normalize().multiply(2);

                x = motionVec.x;
                y = motionVec.y;
                z = motionVec.z;
                world.playSound(null, posX, posY, posZ, SoundEvents.AMBIENT_CAVE, SoundCategory.PLAYERS, 2F, 0.1F + world.rand.nextFloat() * 3F);
            }
            setLiveTicks(liveTime + 1);

            if(!world.isRemote) {
                AxisAlignedBB axis = new AxisAlignedBB(posX, posY, posZ, lastTickPosX, lastTickPosY, lastTickPosZ).grow(2);
                List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, axis);
                for(EntityLivingBase living : entities) {
                    if(living == thrower)
                        continue;

                    if(living.hurtTime == 0) {
                        if(player != null)
                            living.attackEntityFrom(DamageSource.causePlayerDamage(player), 20);
                        else living.attackEntityFrom(DamageSource.GENERIC, 20);
                        onImpact(new RayTraceResult(living));
                        return;
                    }
                }
            }
        }

        super.onUpdate();

        motionX = x;
        motionY = y;
        motionZ = z;

        if(liveTime > 200 + delay) {
            setDead();
        }
    }

    @Override
    protected void onImpact(RayTraceResult pos) {
        EntityLivingBase thrower = getThrower();
        if(pos.entityHit == null || pos.entityHit != thrower) {
            HeroicUtil.SummonLightningAt(world, posX, posY, posZ);
            setDead();
        } else if (pos.entityHit instanceof EntityLivingBase) {
            if (pos.entityHit instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) pos.entityHit;
                if (player.inventory.hasItemStack(new ItemStack((ModItems.JUDGEMENT)))) {
                    return;
                }
            }
            ((EntityLivingBase) pos.entityHit).setDead();
            HeroicUtil.SummonLightningAt(world, pos.entityHit.posX, pos.entityHit.posY, pos.entityHit.posZ);
            setDead();
        }
        HeroicUtil.createSpecialExplosionDamage(100000, 4, world, posX, posY, posZ);
    }

    @Override
    public void writeEntityToNBT(@Nonnull NBTTagCompound cmp) {
        super.writeEntityToNBT(cmp);
        cmp.setBoolean(TAG_CHARGING, isCharging());
        cmp.setInteger(TAG_CHARGE_TICKS, getChargeTicks());
        cmp.setInteger(TAG_LIVE_TICKS, getLiveTicks());
        cmp.setInteger(TAG_DELAY, getDelay());
        cmp.setFloat(TAG_ROTATION, getRotation());
    }

    @Override
    public void readEntityFromNBT(@Nonnull NBTTagCompound cmp) {
        super.readEntityFromNBT(cmp);
        setCharging(cmp.getBoolean(TAG_CHARGING));
        setChargeTicks(cmp.getInteger(TAG_CHARGE_TICKS));
        setLiveTicks(cmp.getInteger(TAG_LIVE_TICKS));
        setDelay(cmp.getInteger(TAG_DELAY));
        setRotation(cmp.getFloat(TAG_ROTATION));
    }

    public boolean isCharging() {
        return dataManager.get(CHARGING);
    }

    public void setCharging(boolean charging) {
        dataManager.set(CHARGING, charging);
    }

    public int getChargeTicks() {
        return dataManager.get(CHARGE_TICKS);
    }

    public void setChargeTicks(int ticks) {
        dataManager.set(CHARGE_TICKS, ticks);
    }

    public int getLiveTicks() {
        return dataManager.get(LIVE_TICKS);
    }

    public void setLiveTicks(int ticks) {
        dataManager.set(LIVE_TICKS, ticks);
    }

    public int getDelay() {
        return dataManager.get(DELAY);
    }

    public void setDelay(int delay) {
        dataManager.set(DELAY, delay);
    }

    public float getRotation() {
        return dataManager.get(ROTATION);
    }

    public void setRotation(float rot) {
        dataManager.set(ROTATION, rot);
    }
}
