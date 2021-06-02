package dan200.billund.shared.tile;

import dan200.billund.shared.data.Stud;
import dan200.billund.shared.registry.BillundRegistry;
import dan200.billund.shared.util.StudHelper;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nullable;

public class BillundTileEntity extends TileEntity {

    public boolean globalIllumination;

    private final Stud[] m_studs;

    public BillundTileEntity(TileEntityType<?> tileTypeIn) {
        super(tileTypeIn);
        m_studs = new Stud[StudHelper.STUDS_PER_BLOCK];
    }

    public BillundTileEntity() {
        this(BillundRegistry.BILLUND_TILE.get());
    }

    public Stud getStudLocal(int x, int y, int z) {
        if (x >= 0 && x < StudHelper.STUDS_PER_ROW &&
                y >= 0 && y < StudHelper.STUDS_PER_COLUMN &&
                z >= 0 && z < StudHelper.STUDS_PER_ROW) {
            return m_studs[x + (z * StudHelper.STUDS_PER_ROW) + (y * StudHelper.STUDS_PER_LAYER)];
        }
        return null;
    }

    public void setStudLocal(int x, int y, int z, Stud stud) {
        System.out.println(String.format("Trying to set stud to %s, %s, %s", x, y, z));
        if (x >= 0 && x < StudHelper.STUDS_PER_ROW &&
                y >= 0 && y < StudHelper.STUDS_PER_COLUMN &&
                z >= 0 && z < StudHelper.STUDS_PER_ROW) {
            m_studs[x + (z * StudHelper.STUDS_PER_ROW) + (y * StudHelper.STUDS_PER_LAYER)] = stud;
            cullOrphans();
            markDirty();
        }
    }

    public boolean isEmpty() {
        for (int i = 0; i < StudHelper.STUDS_PER_BLOCK; ++i) {
            if (m_studs[i] != null) {
                return false;
            }
        }
        return true;
    }

    public void cullOrphans() {
        globalIllumination = false;
        boolean changed = false;
        for (int i = 0; i < StudHelper.STUDS_PER_BLOCK; ++i) {
            Stud stud = m_studs[i];
            if (stud != null) {
                Stud origin = StudHelper.getStud(world, stud.xOrigin, stud.yOrigin, stud.zOrigin);
                if (origin == null) {
                    m_studs[i] = null;
                    changed = true;
                } else {
                    if (!globalIllumination && stud.illuminated) {
                        globalIllumination = true;
                        changed = true;
                    }
                }
            }
        }
        if (changed) {
            if(world != null) {
                world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
            }
        }
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, 0, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        if(world != null) {
            world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
        }
        CompoundNBT compoundNBT = pkt.getNbtCompound();
        handleUpdateTag(getBlockState(), compoundNBT);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putBoolean("globalIllumination", globalIllumination);
        for (int i = 0; i < StudHelper.STUDS_PER_BLOCK; ++i) {
            Stud stud = m_studs[i];
            if (stud != null) {
                System.out.println(i);
                CompoundNBT studTag = new CompoundNBT();
                studTag.putBoolean("i", stud.illuminated);
                studTag.putBoolean("t", stud.transparent);
                studTag.putBoolean("s", stud.smooth);
                studTag.putInt("c", stud.color);
                studTag.putInt("x", stud.xOrigin);
                studTag.putInt("y", stud.yOrigin);
                studTag.putInt("z", stud.zOrigin);
                studTag.putInt("w", stud.brickWidth);
                studTag.putInt("h", stud.brickHeight);
                studTag.putInt("d", stud.brickDepth);
                compound.put("s" + i, studTag);
            }
        }

        return compound;
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
        super.read(state, compound);

        globalIllumination = compound.getBoolean("globalIllumination");
        for (int i = 0; i < StudHelper.STUDS_PER_BLOCK; ++i) {
            String key = "s" + i;
            if (compound.contains(key)) {
                Stud stud = new Stud();
                CompoundNBT studTag = compound.getCompound(key);
                stud.illuminated = studTag.getBoolean("i");
                stud.transparent = studTag.getBoolean("t");
                stud.smooth = studTag.getBoolean("s");
                stud.color = studTag.getInt("c");
                stud.xOrigin = studTag.getInt("x");
                stud.yOrigin = studTag.getInt("y");
                stud.zOrigin = studTag.getInt("z");
                stud.brickWidth = studTag.getInt("w");
                stud.brickHeight = studTag.getInt("h");
                stud.brickDepth = studTag.getInt("d");
                m_studs[i] = stud;
                System.out.println("hey");
            } else {
                m_studs[i] = null;
            }
        }
    }
}
