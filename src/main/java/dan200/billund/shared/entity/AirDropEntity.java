package dan200.billund.shared.entity;

import dan200.billund.shared.data.BillundSet;
import dan200.billund.shared.registry.BillundRegistry;
import dan200.billund.shared.registry.BillundSetRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.fml.network.NetworkHooks;

public class AirDropEntity extends Entity {
    public Block block;
    public BillundSet set;
    public boolean deployed;
    private float yOffset;
    protected static final DataParameter<BlockPos> ORIGIN = EntityDataManager.createKey(AirDropEntity.class, DataSerializers.BLOCK_POS);

    public AirDropEntity(EntityType<? extends AirDropEntity> entityType, World world) {
        super(entityType, world);
        this.block = Blocks.CHEST;
        this.setOrigin(this.getPosition());
    }

    public AirDropEntity(World world, BlockPos pos, BillundSet set) {
        this(BillundRegistry.AIR_DROP.get(), world);
        this.preventEntitySpawning = true;
        this.yOffset = this.getHeight() / 2.0f;
        this.setPosition(pos.getX(), pos.getY(), pos.getZ());
        this.setMotion(0.0, 0.0, 0.0);
        this.prevPosX = pos.getY();
        this.prevPosY = pos.getY();
        this.prevPosZ = pos.getY();
        this.set = set;
    }

    public BillundSet getSet() {
        return set != null ? set : BillundSetRegistry.instance().getBillundSet("BlindBag");
    }

    public void setOrigin(BlockPos origin) {
        this.dataManager.set(ORIGIN, origin);
    }

    public BlockPos getOrigin() {
        return this.dataManager.get(ORIGIN);
    }

    @Override
    public double getYOffset() {
        return yOffset;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected void registerData() {
        this.dataManager.register(ORIGIN, BlockPos.ZERO);
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    @Override
    public void tick() {
        this.prevPosX = this.getPosX();
        this.prevPosY = this.getPosY();
        this.prevPosZ = this.getPosZ();

        if (!this.deployed) {
            if (this.getPosY() <= getOrigin().getY())
                this.deployed = true;
        }

        Vector3d motion = getMotion();
        if (!this.deployed) {
            this.setMotion(motion.x, motion.y - 0.003, motion.z);
        } else {
            this.setMotion(motion.x, motion.y - 0.02, motion.z);
        }

        this.move(MoverType.SELF, getMotion());
        motion = getMotion();
        this.setMotion(motion.x * 0.98, motion.y * 0.98, motion.z * 0.98);

        if (!this.world.isRemote) {
            int blockX = MathHelper.floor(this.getPosX());
            int blockY = MathHelper.floor(this.getPosY());
            int blockZ = MathHelper.floor(this.getPosZ());

            if (this.onGround) {
                motion = getMotion();
                this.setMotion(motion.getX() * 0.7, motion.getY() * -0.5, motion.getZ() * 0.7);

                while (!world.isAirBlock(getPosition())) {
                    blockY++;
                }

                this.setDead();

                // Set the block
                this.world.setBlockState(getPosition(), Blocks.CHEST.getDefaultState());

                this.world.playEvent(2006, new BlockPos(blockX, blockY - 1, blockZ), 10);

                // Populate the block
                TileEntity entity = this.world.getTileEntity(getPosition());
                if (entity instanceof IInventory) {
                    IInventory inv = (IInventory) entity;
                    this.getSet().populateChest(inv);
                    inv.markDirty();
                }
            } else if (blockY < 0) {
                this.setDead();
            }
        }
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return super.onLivingFall(distance, damageMultiplier);
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putString("Set", this.set.getSetName());
        compound.putBoolean("Deployed", this.deployed);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.set = BillundSetRegistry.instance().getBillundSet(compound.getString("Set"));
        this.deployed = compound.getBoolean("Deployed");
    }
}
