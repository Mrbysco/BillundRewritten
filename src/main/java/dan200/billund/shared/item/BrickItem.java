package dan200.billund.shared.item;


import dan200.billund.Billund;
import dan200.billund.client.BillundISTERProvider;
import dan200.billund.shared.core.BillundTabs;
import dan200.billund.shared.data.Brick;
import dan200.billund.shared.data.Stud;
import dan200.billund.shared.tile.BillundTileEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BrickItem extends Item {
    private final DyeColor color;
    private final int width;
    private final int depth;

    public BrickItem(Item.Properties properties, int width, int depth, DyeColor dyeColor) {
        super(properties.maxStackSize(64).group(BillundTabs.BILLUND_TAB).setISTER(() -> BillundISTERProvider.brick()));
        this.width = width;
        this.depth = depth;
        this.color = dyeColor;
    }

    public int getWidth() {
        return width;
    }

    public int getDepth() {
        return depth;
    }

    public DyeColor getColor() {
        return color;
    }

    public int getColorValue() {
        return color.getColorValue();
    }

    public static BillundTileEntity.StudRaycastResult raycastFromPlayer(World world, PlayerEntity player, float f) {
        // Calculate the raycast origin and direction
        double yOffset2 = (!world.isRemote && player.isSneaking()) ? -0.08 : 0.0; // TODO: Improve
        float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        float yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        double x = player.prevPosX + (player.getPosX() - player.prevPosX) * (double) f;
        double y = player.prevPosY + (player.getPosY() - player.prevPosY) * (double) f + 1.62 - player.getYOffset() + yOffset2; // TODO: Improve
        double z = player.prevPosZ + (player.getPosZ() - player.prevPosZ) * (double) f;
        Vector3d position = new Vector3d(x, y, z);

        float f3 = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-pitch * 0.017453292F);
        float f6 = MathHelper.sin(-pitch * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;

        float distance = 5.0f;
        if (player instanceof ServerPlayerEntity) {
            distance = (float) ((ServerPlayerEntity) player).getAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).getValue();
        }
        Vector3d direction = new Vector3d((double) f7, (double) f6, (double) f8);

        // Do the raycast
        return BillundTileEntity.raycastStuds(world, position, direction, distance);
    }

    public static Brick getPotentialBrick(ItemStack stack, World world, PlayerEntity player, float f) {
        // Do the raycast
        BillundTileEntity.StudRaycastResult result = raycastFromPlayer(world, player, f);
        if (result != null) {
            // Bricks can't be directly placed on top of smooth bricks (because I'm evil)
            Stud stud = BillundTileEntity.getStud(world, result.hitX, result.hitY, result.hitZ);
            if (result.hitSide == 1 && stud.smooth) {
                return null;
            }

            BrickItem brickItem = (BrickItem)stack.getItem();

            // Calculate where to place the brick
            int defaultWidth = brickItem.getWidth();
            int defaultDepth = brickItem.getDepth();
            int width = 0;
            int depth = 0;
            int height = 1;

            int placeX = result.hitX;
            int placeY = result.hitY;
            int placeZ = result.hitZ;

            switch (0) { //BillundProxyCommon.rotate
                case 0:
                default:
                    depth = defaultDepth;
                    width = defaultWidth;
                    break;
                case 1:
                    depth = defaultWidth;
                    width = defaultDepth;
                    break;
                case 2:
                    depth = defaultDepth;
                    width = defaultWidth;
                    placeZ -= depth - 1;
                    break;
                case 3:
                    depth = defaultWidth;
                    width = defaultDepth;
                    placeX -= width - 1;
                    break;
            }

            switch (result.hitSide) {
                case 0:
                    placeY -= height;
                    break;
                case 1:
                    placeY++;
                    break;
                case 2:
                    placeZ -= depth;
                    break;
                case 3:
                    placeZ++;
                    break;
                case 4:
                    placeX -= width;
                    break;
                case 5:
                    placeX++;
                    break;
            }

            // Try a few positions nearby
            Brick brick = new Brick(getIlluminated(stack), getTransparent(stack), getSmooth(stack), brickItem.getColorValue(), placeX, placeY, placeZ, width, height, depth);
            for (int x = 0; x < width; ++x) {
                for (int z = 0; z < depth; ++z) {
                    for (int y = 0; y < height; ++y) {
                        brick.xOrigin = placeX - x;
                        brick.yOrigin = placeY - y;
                        brick.zOrigin = placeZ - z;
                        if (BillundTileEntity.canAddBrick(world, brick)) {
                            return brick;
                        }
                    }
                }
            }
        }
        return null;
    }

    public static Brick getExistingBrick(World world, PlayerEntity player, float f) {
        // Do the raycast
        BillundTileEntity.StudRaycastResult result = raycastFromPlayer(world, player, f);
        if (result != null) {
            Stud stud = BillundTileEntity.getStud(world, result.hitX, result.hitY, result.hitZ);
            if (stud != null && stud.actuallyExists) {
                return new Brick(stud.illuminated, stud.transparent, stud.smooth, stud.color, stud.xOrigin, stud.yOrigin, stud.zOrigin, stud.brickWidth, stud.brickHeight, stud.brickDepth);
            }
        }
        return null;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent(getWidth() + "x" + getDepth()));

        if (getIlluminated(stack)) {
            tooltip.add(new TranslationTextComponent(Billund.MOD_ID + ".brick.illuminated"));
        }

        if (getTransparent(stack)) {
            tooltip.add(new TranslationTextComponent(Billund.MOD_ID + ".brick.transparent"));
        }

        if (getSmooth(stack)) {
            tooltip.add(new TranslationTextComponent(Billund.MOD_ID + ".brick.smooth"));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        Brick brick = getPotentialBrick(stack, worldIn, playerIn, 1.0f);
        if (brick != null) {
            if (!worldIn.isRemote) {
                // Place the brick
                BillundTileEntity.addBrick(worldIn, brick);

                if (!playerIn.abilities.isCreativeMode) {
                    // Decrement stackSize
                    stack.shrink(1);
                }
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public static int getHeight(ItemStack stack) {
        return 1;
    }

    public static boolean getIlluminated(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getBoolean("illuminated") : false;
    }

    public static boolean getTransparent(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getBoolean("transparent") : false;
    }

    public static boolean getSmooth(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getBoolean("smooth") : false;
    }
}
