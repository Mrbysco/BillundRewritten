package dan200.billund.shared.block;

import dan200.billund.shared.registry.BrickHelper;
import dan200.billund.shared.data.Brick;
import dan200.billund.shared.item.BrickItem;
import dan200.billund.shared.tile.BillundTileEntity;
import dan200.billund.shared.util.StudHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BillundBlock extends ContainerBlock {
    
    private static Brick s_hoverBrick = null;

    public BillundBlock(Properties builder) {
        super(builder.hardnessAndResistance(0.25F));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    public static void setHoverBrick(Brick brick) {
        s_hoverBrick = brick;
    }

    @Override
    public boolean canBeReplacedByLeaves(BlockState state, IWorldReader world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof BillundTileEntity) {
                // Find a brick to destroy
                BillundTileEntity billund = (BillundTileEntity) tileEntity;
                Brick brick = BrickItem.getExistingBrick(world, player, 1.0f);
                if (brick != null) {
                    // Remove the brick
                    StudHelper.removeBrick(world, brick);

                    // Spawn an item for the destroyed brick
                    if (!player.abilities.isCreativeMode) {
                        float brickX = ((float) brick.xOrigin + (float) brick.width * 0.5f) / (float) StudHelper.ROWS_PER_BLOCK;
                        float brickY = ((float) brick.yOrigin + (float) brick.height) / (float) StudHelper.LAYERS_PER_BLOCK;
                        float brickZ = ((float) brick.zOrigin + (float) brick.depth * 0.5f) / (float) StudHelper.ROWS_PER_BLOCK;
                        ItemStack stack = BrickHelper.createBrickStack(brick.illuminated, brick.transparent, brick.smooth, brick.color, Math.min(brick.width, brick.depth), Math.max(brick.width, brick.depth), 1);
                        ItemEntity itemEntity = new ItemEntity(world, brickX, brickY + 0.05f, brickZ, stack);
                        itemEntity.setMotion(0.0F, 0.0F, 0.0F);
                        itemEntity.setPickupDelay(30);
                        world.addEntity(itemEntity);
                    }

                    // Clear the block
                    if (billund.isEmpty()) {
                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof BillundTileEntity) {
            BillundTileEntity billund = (BillundTileEntity) tileEntity;
            return billund.globalIllumination ? 15 : 0;
        }
        return 0;
    }

    @Override
    public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof BillundTileEntity) {
            BillundTileEntity billund = (BillundTileEntity) tileEntity;
            billund.cullOrphans();
            if (billund.isEmpty()) {
                tileEntity.getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (s_hoverBrick != null) {
            // See if the hovered brick is in the start bit
            int sx = s_hoverBrick.xOrigin;
            int sy = s_hoverBrick.yOrigin;
            int sz = s_hoverBrick.zOrigin;
            {
                int localX = (sx % StudHelper.ROWS_PER_BLOCK + StudHelper.ROWS_PER_BLOCK) % StudHelper.ROWS_PER_BLOCK;
                int localY = (sy % StudHelper.LAYERS_PER_BLOCK + StudHelper.LAYERS_PER_BLOCK) % StudHelper.LAYERS_PER_BLOCK;
                int localZ = (sz % StudHelper.ROWS_PER_BLOCK + StudHelper.ROWS_PER_BLOCK) % StudHelper.ROWS_PER_BLOCK;
                int blockX = (sx - localX) / StudHelper.ROWS_PER_BLOCK;
                int blockY = (sy - localY) / StudHelper.LAYERS_PER_BLOCK;
                int blockZ = (sz - localZ) / StudHelper.ROWS_PER_BLOCK;

                if ((pos.getX() == blockX || pos.getX() == blockX + 1) &&
                        (pos.getY() == blockY || pos.getY() == blockY + 1) &&
                        (pos.getZ() == blockZ || pos.getZ() == blockZ + 1)) {
                    float xScale = 16.0f / (float) StudHelper.ROWS_PER_BLOCK;
                    float yScale = 16.0f / (float) StudHelper.LAYERS_PER_BLOCK;
                    float zScale = 16.0f / (float) StudHelper.ROWS_PER_BLOCK;

                    float startX = (float) (sx - (pos.getX() * StudHelper.ROWS_PER_BLOCK)) * xScale;
                    float startY = (float) (sy - (pos.getY() * StudHelper.LAYERS_PER_BLOCK)) * yScale;
                    float startZ = (float) (sz - (pos.getZ() * StudHelper.ROWS_PER_BLOCK)) * zScale;
                    VoxelShape shape = Block.makeCuboidShape(startX, startY, startZ,
                            startX + (float) s_hoverBrick.width * xScale,
                            startY + (float) s_hoverBrick.height * yScale,
                            startZ + (float) s_hoverBrick.depth * zScale).simplify();
                    return shape;
                }
            }
        }

        // Set bounds to something that should hopefully never be hit
        return Block.makeCuboidShape(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return BrickHelper.createBrickStack(s_hoverBrick.illuminated, s_hoverBrick.transparent, s_hoverBrick.smooth, s_hoverBrick.color, s_hoverBrick.width, s_hoverBrick.depth, 1);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            if (s_hoverBrick != null) {
                ItemStack held = player.getHeldItem(handIn);

                if (held != null && held.getItem() instanceof DyeItem) {
//                    DyeItem dyeItem = (DyeItem)held.getItem();
//                    Brick newBrick = new Brick(s_hoverBrick.illuminated, s_hoverBrick.transparent, s_hoverBrick.smooth, s_hoverBrick.color, s_hoverBrick.xOrigin, s_hoverBrick.yOrigin, s_hoverBrick.zOrigin, s_hoverBrick.width, s_hoverBrick.height, s_hoverBrick.depth);
//                    Color brickColor = new Color(newBrick.color);
//                    Color dyeColor = new Color(dyeItem.getDyeColor());
//                    int br = brickColor.getRed();
//                    int bg = brickColor.getGreen();
//                    int bb = brickColor.getBlue();
//                    int dr = dyeColor.getRed();
//                    int dg = dyeColor.getGreen();
//                    int db = dyeColor.getBlue();
//                    int ar = (br + dr) / 2;
//                    int ag = (bg + dg) / 2;
//                    int ab = (bb + db) / 2;
//                    newBrick.color = new Color(ar, ag, ab).getRGB();
//                    BillundTileEntity.addBrick(worldIn, newBrick);
                }
            }
        }
        return ActionResultType.FAIL;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new BillundTileEntity();
    }
}
