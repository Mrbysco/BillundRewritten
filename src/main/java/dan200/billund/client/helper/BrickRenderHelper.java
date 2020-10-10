package dan200.billund.client.helper;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.DefaultColorVertexBuilder;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dan200.billund.client.render.ColourFixedMatrixApplyingVertexBuilder;
import dan200.billund.shared.data.Brick;
import dan200.billund.shared.data.Stud;
import dan200.billund.shared.item.BrickItem;
import dan200.billund.shared.tile.BillundTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;

/**
 * @author dmillerw
 */
public class BrickRenderHelper {
    public static void renderBrick(ItemStack brick, MatrixStack matrixStack, IVertexBuilder vertexBuilder, boolean scale, boolean center) {
        int brightness = 15;

        boolean smooth = BrickItem.getSmooth(brick);
        final BrickItem item = (BrickItem) brick.getItem();
        int color = item.getColorValue();
        int width = item.getWidth();
        int height = BrickItem.getHeight(brick);
        int depth = item.getDepth();

        // Setup
        matrixStack.push();

        if (scale) {
            float scaleValue = ((float) BillundTileEntity.LAYERS_PER_BLOCK) / Math.max(2.0f, (float) Math.max(width, depth) - 0.5f);
            matrixStack.scale(scaleValue, scaleValue, scaleValue);
        }

        if (center) {
            matrixStack.translate(
                    -0.5f * ((float) width / (float) BillundTileEntity.ROWS_PER_BLOCK),
                    -0.5f * ((float) height / (float) BillundTileEntity.LAYERS_PER_BLOCK),
                    -0.5f * ((float) depth / (float) BillundTileEntity.ROWS_PER_BLOCK)
            );
        }

        DefaultColorVertexBuilder bufferBuilder = new ColourFixedMatrixApplyingVertexBuilder(vertexBuilder, matrixStack.getLast().getMatrix(), matrixStack.getLast().getNormal());

        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;
        bufferBuilder.setDefaultColor(r, g, b, 255);

        renderBrick(bufferBuilder, null, brightness, false, smooth, 0, 0, 0, width, height, depth);

        matrixStack.pop();
    }

    public static void renderBrick(MatrixStack matrixStack, IVertexBuilder vertexBuilder, IBlockDisplayReader world, Brick brick) {
        int localX = (brick.xOrigin % BillundTileEntity.ROWS_PER_BLOCK + BillundTileEntity.ROWS_PER_BLOCK) % BillundTileEntity.ROWS_PER_BLOCK;
        int localY = (brick.yOrigin % BillundTileEntity.LAYERS_PER_BLOCK + BillundTileEntity.LAYERS_PER_BLOCK) % BillundTileEntity.LAYERS_PER_BLOCK;
        int localZ = (brick.zOrigin % BillundTileEntity.ROWS_PER_BLOCK + BillundTileEntity.ROWS_PER_BLOCK) % BillundTileEntity.ROWS_PER_BLOCK;
        int blockX = (brick.xOrigin - localX) / BillundTileEntity.ROWS_PER_BLOCK;
        int blockY = (brick.yOrigin - localY) / BillundTileEntity.LAYERS_PER_BLOCK;
        int blockZ = (brick.zOrigin - localZ) / BillundTileEntity.ROWS_PER_BLOCK;
        BlockPos blockPos = new BlockPos(blockX, blockY, blockZ);

        BlockState state = world.getBlockState(blockPos);
        int brightness = WorldRenderer.getPackedLightmapCoords(world, state, blockPos);

        int r = brick.color >> 16 & 255;
        int g = brick.color >> 8 & 255;
        int b = brick.color & 255;
        int a = (int)(0.65f * 255f);

        DefaultColorVertexBuilder bufferBuilder = new ColourFixedMatrixApplyingVertexBuilder(vertexBuilder, matrixStack.getLast().getMatrix(), matrixStack.getLast().getNormal());
        bufferBuilder.setDefaultColor(r, g, b, a);

        renderBrick(bufferBuilder, world, brightness, brick.illuminated, brick.smooth, brick.xOrigin, brick.yOrigin, brick.zOrigin, brick.width, brick.height, brick.depth);
    }

    public static void renderBrick(IVertexBuilder vertexBuilder, IBlockReader world, int brightness, boolean illuminated, boolean smooth, int sx, int sy, int sz, int width, int height, int depth) {
        // Draw the brick
        if (world != null && !illuminated) {
            vertexBuilder.lightmap(brightness);
        }

        float pixel = 1.0f / 96.0f;
        float xBlockSize = (float) BillundTileEntity.STUDS_PER_ROW;
        float yBlockSize = (float) BillundTileEntity.STUDS_PER_COLUMN;
        float zBlockSize = (float) BillundTileEntity.STUDS_PER_ROW;

        float startX = (float) sx / xBlockSize;
        float startY = (float) sy / yBlockSize;
        float startZ = (float) sz / zBlockSize;
        float endX = startX + ((float) width / xBlockSize);
        float endY = startY + ((float) height / yBlockSize);
        float endZ = startZ + ((float) depth / zBlockSize);
        renderBox(
                vertexBuilder,
                startX, startY, startZ,
                endX, endY, endZ,
                true
        );

        // Draw the studs
        int sny = sy + height;
        startY = (float) sny / yBlockSize;
        endY = startY + (0.1666f / yBlockSize);
        for (int snx = sx; snx < sx + width; ++snx) {
            startX = (float) snx / xBlockSize;
            endX = startX + (1.0f / xBlockSize);
            for (int snz = sz; snz < sz + depth; ++snz) {
                boolean drawStud;
                if (world != null) {
                    Stud above = BillundTileEntity.getStud(world, snx, sny, snz);
                    drawStud = (above == null) || (above.transparent);
                } else {
                    drawStud = true;
                }

                if (smooth) {
                    drawStud = false;
                }

                if (drawStud) {
                    startZ = (float) snz / zBlockSize;
                    endZ = startZ + (1.0f / zBlockSize);
                    renderBox(
                            vertexBuilder,
                            startX + pixel * 2.0f, startY, startZ + pixel * 4.0f,
                            startX + pixel * 4.0f, endY, endZ - pixel * 4.0f,
                            false
                    );
                    renderBox(
                            vertexBuilder,
                            startX + pixel * 4.0f, startY, startZ + pixel * 2.0f,
                            endX - pixel * 4.0f, endY, endZ - pixel * 2.0f,
                            false
                    );
                    renderBox(
                            vertexBuilder,
                            endX - pixel * 4.0f, startY, startZ + pixel * 4.0f,
                            endX - pixel * 2.0f, endY, endZ - pixel * 4.0f,
                            false
                    );
                }
            }
        }
    }

    private static void renderBox(IVertexBuilder vertexBuilder, float startX, float startY, float startZ, float endX, float endY, float endZ, boolean bottom) {
        // X faces
        renderFaceXNeg(vertexBuilder, startX, startY, startZ, endX, endY, endZ);
        renderFaceXPos(vertexBuilder, startX, startY, startZ, endX, endY, endZ);

        // Y faces
        if (bottom) {
            renderFaceYNeg(vertexBuilder, startX, startY, startZ, endX, endY, endZ);
        }
        renderFaceYPos(vertexBuilder, startX, startY, startZ, endX, endY, endZ);

        // Z faces
        renderFaceZNeg(vertexBuilder, startX, startY, startZ, endX, endY, endZ);
        renderFaceZPos(vertexBuilder, startX, startY, startZ, endX, endY, endZ);
    }

    private static void renderFaceXNeg(IVertexBuilder bufferBuilder, float startX, float startY, float startZ, float endX, float endY, float endZ) {
        bufferBuilder.pos(startX, endY, endZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(startX, endY, startZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(startX, startY, startZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(startX, startY, endZ).normal(0.0f, -1.0f, 0.0f).endVertex();
    }

    private static void renderFaceXPos(IVertexBuilder bufferBuilder, float startX, float startY, float startZ, float endX, float endY, float endZ) {
        bufferBuilder.pos(endX, startY, endZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(endX, startY, startZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(endX, endY, startZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(endX, endY, endZ).normal(0.0f, -1.0f, 0.0f).endVertex();
    }

    private static void renderFaceYNeg(IVertexBuilder bufferBuilder, float startX, float startY, float startZ, float endX, float endY, float endZ) {
        bufferBuilder.pos(startX, startY, endZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(startX, startY, startZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(endX, startY, startZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(endX, startY, endZ).normal(0.0f, -1.0f, 0.0f).endVertex();
    }

    private static void renderFaceYPos(IVertexBuilder bufferBuilder, float startX, float startY, float startZ, float endX, float endY, float endZ) {
        bufferBuilder.pos(endX, endY, endZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(endX, endY, startZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(startX, endY, startZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(startX, endY, endZ).normal(0.0f, -1.0f, 0.0f).endVertex();
    }

    private static void renderFaceZNeg(IVertexBuilder bufferBuilder, float startX, float startY, float startZ, float endX, float endY, float endZ) {
        bufferBuilder.pos(startX, endY, startZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(endX, endY, startZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(endX, startY, startZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(startX, startY, startZ).normal(0.0f, -1.0f, 0.0f).endVertex();
    }

    private static void renderFaceZPos(IVertexBuilder bufferBuilder, float startX, float startY, float startZ, float endX, float endY, float endZ) {
        bufferBuilder.pos(startX, startY, endZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(endX, startY, endZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(endX, endY, endZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(startX, endY, endZ).normal(0.0f, -1.0f, 0.0f).endVertex();
    }
}
