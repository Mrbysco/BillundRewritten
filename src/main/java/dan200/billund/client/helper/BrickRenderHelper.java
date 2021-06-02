package dan200.billund.client.helper;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dan200.billund.shared.data.Brick;
import dan200.billund.shared.data.Stud;
import dan200.billund.shared.item.BrickItem;
import dan200.billund.shared.util.StudHelper;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;

/**
 * @author dmillerw
 */
public class BrickRenderHelper {

    public static void renderBrick(ItemStack brick, MatrixStack matrixStack, IVertexBuilder vertexBuilder, boolean scale, boolean center, int combinedLight) {
        boolean smooth = BrickItem.getSmooth(brick);
        final BrickItem item = (BrickItem) brick.getItem();
        int color = item.getColorValue();
        int width = item.getWidth();
        int height = BrickItem.getHeight(brick);
        boolean illuminated = BrickItem.getIlluminated(brick);
        int depth = item.getDepth();

        // Setup
        matrixStack.push();

        if (scale) {
            float scaleValue = ((float) StudHelper.LAYERS_PER_BLOCK) / Math.max(2.0f, (float) Math.max(width, depth) - 0.5f);
            matrixStack.scale(scaleValue, scaleValue, scaleValue);
        }

        if (center) {
            matrixStack.translate(
                    -0.5f * ((float) width / (float) StudHelper.ROWS_PER_BLOCK),
                    -0.5f * ((float) height / (float) StudHelper.LAYERS_PER_BLOCK),
                    -0.5f * ((float) depth / (float) StudHelper.ROWS_PER_BLOCK)
            );
        }

        Matrix4f matrix4f = matrixStack.getLast().getMatrix();
        renderBrick(vertexBuilder, matrix4f, null, color, 0.65F, combinedLight, illuminated, smooth, 0, 0, 0, width, height, depth);

        matrixStack.pop();
    }

    public static void renderBrick(MatrixStack matrixStack, IVertexBuilder vertexBuilder, IBlockDisplayReader world, Brick brick) {

        int localX = (brick.xOrigin % StudHelper.ROWS_PER_BLOCK + StudHelper.ROWS_PER_BLOCK) % StudHelper.ROWS_PER_BLOCK;
        int localY = (brick.yOrigin % StudHelper.LAYERS_PER_BLOCK + StudHelper.LAYERS_PER_BLOCK) % StudHelper.LAYERS_PER_BLOCK;
        int localZ = (brick.zOrigin % StudHelper.ROWS_PER_BLOCK + StudHelper.ROWS_PER_BLOCK) % StudHelper.ROWS_PER_BLOCK;
        int blockX = (brick.xOrigin - localX) / StudHelper.ROWS_PER_BLOCK;

        int blockY = (brick.yOrigin - localY) / StudHelper.LAYERS_PER_BLOCK;
        int blockZ = (brick.zOrigin - localZ) / StudHelper.ROWS_PER_BLOCK;
        BlockPos blockPos = new BlockPos(blockX, blockY, blockZ);

        int brightness;
        if (world != null) {
            brightness = WorldRenderer.getCombinedLight(world, blockPos);
        } else {
            brightness = 15728880;
        }

        Matrix4f matrix4f = matrixStack.getLast().getMatrix();

        renderBrick(vertexBuilder, matrix4f, world, brick.color, 0.65F, brightness, brick.illuminated, brick.smooth, brick.xOrigin, brick.yOrigin, brick.zOrigin, brick.width, brick.height,
                brick.depth);
    }

    public static void renderBrick(IVertexBuilder vertexBuilder, Matrix4f matrix4f, IBlockReader world, int color, float alpha, int brightness, boolean illuminated, boolean smooth, int sx, int sy, int sz, int width,
                                   int height, int depth) {
        // Draw the brick
        if (world != null && !illuminated) {
            vertexBuilder.lightmap(brightness);
        }

        float pixel = 1.0f / 96.0f;
        float xBlockSize = (float) StudHelper.STUDS_PER_ROW;
        float yBlockSize = (float) StudHelper.STUDS_PER_COLUMN;
        float zBlockSize = (float) StudHelper.STUDS_PER_ROW;

        float startX = (float) sx / xBlockSize;
        float startY = (float) sy / yBlockSize;
        float startZ = (float) sz / zBlockSize;
        float endX = startX + ((float) width / xBlockSize);
        float endY = startY + ((float) height / yBlockSize);
        float endZ = startZ + ((float) depth / zBlockSize);
        renderBox(
                vertexBuilder, matrix4f, color, alpha,
                startX, startY, startZ,
                endX, endY, endZ,
                true, brightness
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
                    Stud above = StudHelper.getStud(world, snx, sny, snz);
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
                            vertexBuilder, matrix4f, color, alpha,
                            startX + pixel * 2.0f, startY, startZ + pixel * 4.0f,
                            startX + pixel * 4.0f, endY, endZ - pixel * 4.0f,
                            false, brightness
                    );
                    renderBox(
                            vertexBuilder, matrix4f, color, alpha,
                            startX + pixel * 4.0f, startY, startZ + pixel * 2.0f,
                            endX - pixel * 4.0f, endY, endZ - pixel * 2.0f,
                            false, brightness
                    );
                    renderBox(
                            vertexBuilder, matrix4f, color, alpha,
                            endX - pixel * 4.0f, startY, startZ + pixel * 4.0f,
                            endX - pixel * 2.0f, endY, endZ - pixel * 4.0f,
                            false, brightness
                    );
                }
            }
        }
    }

    private static void renderBox(IVertexBuilder vertexBuilder, Matrix4f matrix4f, int color, float alpha, float startX, float startY, float startZ, float endX, float endY, float endZ, boolean bottom, int combinedLightIn) {
        // X faces
        renderFaceXNeg(vertexBuilder, matrix4f, color, alpha, startX, startY, startZ, endX, endY, endZ, combinedLightIn);
        renderFaceXPos(vertexBuilder, matrix4f, color, alpha, startX, startY, startZ, endX, endY, endZ, combinedLightIn);

        // Y faces
        if (bottom) {
            renderFaceYNeg(vertexBuilder, matrix4f, color, alpha, startX, startY, startZ, endX, endY, endZ, combinedLightIn);
        }
        renderFaceYPos(vertexBuilder, matrix4f, color, alpha, startX, startY, startZ, endX, endY, endZ, combinedLightIn);

        // Z faces
        renderFaceZNeg(vertexBuilder, matrix4f, color, alpha, startX, startY, startZ, endX, endY, endZ, combinedLightIn);
        renderFaceZPos(vertexBuilder, matrix4f, color, alpha, startX, startY, startZ, endX, endY, endZ, combinedLightIn);
    }

    private static void renderFaceXNeg(IVertexBuilder bufferBuilder, Matrix4f matrix4f, int color, float alpha, float startX, float startY, float startZ, float endX, float endY, float endZ, int combinedLight) {
        final float r = (float)(color >> 16 & 255) / 255.0F;
        final float g = (float)(color >> 8 & 255) / 255.0F;
        final float b = (float)(color & 255) / 255.0F;
        bufferBuilder.pos(matrix4f, startX, endY, endZ).color(r * 0.8f, g * 0.8f, b * 0.8f, alpha).lightmap(combinedLight).endVertex();
        bufferBuilder.pos(matrix4f, startX, endY, startZ).color(r * 0.8f, g * 0.8f, b * 0.8f, alpha).lightmap(combinedLight).endVertex();
        bufferBuilder.pos(matrix4f, startX, startY, startZ).color(r * 0.8f, g * 0.8f, b * 0.8f, alpha).lightmap(combinedLight).endVertex();
        bufferBuilder.pos(matrix4f, startX, startY, endZ).color(r * 0.8f, g * 0.8f, b * 0.8f, alpha).lightmap(combinedLight).endVertex();
    }

    private static void renderFaceXPos(IVertexBuilder bufferBuilder, Matrix4f matrix4f, int color, float alpha, float startX, float startY, float startZ, float endX, float endY, float endZ, int combinedLight) {
        final float r = (float)(color >> 16 & 255) / 255.0F;
        final float g = (float)(color >> 8 & 255) / 255.0F;
        final float b = (float)(color & 255) / 255.0F;
        bufferBuilder.pos(matrix4f, endX, startY, endZ).color(r * 0.8f, g * 0.8f, b * 0.8f, alpha).lightmap(combinedLight).endVertex();
        bufferBuilder.pos(matrix4f, endX, startY, startZ).color(r * 0.8f, g * 0.8f, b * 0.8f, alpha).lightmap(combinedLight).endVertex();
        bufferBuilder.pos(matrix4f, endX, endY, startZ).color(r * 0.8f, g * 0.8f, b * 0.8f, alpha).lightmap(combinedLight).endVertex();
        bufferBuilder.pos(matrix4f, endX, endY, endZ).color(r * 0.8f, g * 0.8f, b * 0.8f, alpha).lightmap(combinedLight).endVertex();
    }

    private static void renderFaceYNeg(IVertexBuilder bufferBuilder, Matrix4f matrix4f, int color, float alpha, float startX, float startY, float startZ, float endX, float endY, float endZ, int combinedLight) {
        final float r = (float)(color >> 16 & 255) / 255.0F;
        final float g = (float)(color >> 8 & 255) / 255.0F;
        final float b = (float)(color & 255) / 255.0F;
        bufferBuilder.pos(matrix4f, startX, startY, endZ).color(r * 0.8f, g * 0.8f, b * 0.8f, alpha).lightmap(combinedLight).endVertex();
        bufferBuilder.pos(matrix4f, startX, startY, startZ).color(r * 0.8f, g * 0.8f, b * 0.8f, alpha).lightmap(combinedLight).endVertex();
        bufferBuilder.pos(matrix4f, endX, startY, startZ).color(r * 0.8f, g * 0.8f, b * 0.8f, alpha).lightmap(combinedLight).endVertex();
        bufferBuilder.pos(matrix4f, endX, startY, endZ).color(r * 0.8f, g * 0.8f, b * 0.8f, alpha).lightmap(combinedLight).endVertex();
    }

    private static void renderFaceYPos(IVertexBuilder bufferBuilder, Matrix4f matrix4f, int color, float alpha, float startX, float startY, float startZ, float endX, float endY, float endZ, int combinedLight) {
        final float r = (float)(color >> 16 & 255) / 255.0F;
        final float g = (float)(color >> 8 & 255) / 255.0F;
        final float b = (float)(color & 255) / 255.0F;
        bufferBuilder.pos(matrix4f, endX, endY, endZ).color(r * 0.8f, g * 0.8f, b * 0.8f, alpha).lightmap(combinedLight).endVertex();
        bufferBuilder.pos(matrix4f, endX, endY, startZ).color(r * 0.8f, g * 0.8f, b * 0.8f, alpha).lightmap(combinedLight).endVertex();
        bufferBuilder.pos(matrix4f, startX, endY, startZ).color(r * 0.8f, g * 0.8f, b * 0.8f, alpha).lightmap(combinedLight).endVertex();
        bufferBuilder.pos(matrix4f, startX, endY, endZ).color(r * 0.8f, g * 0.8f, b * 0.8f, alpha).lightmap(combinedLight).endVertex();
    }

    private static void renderFaceZNeg(IVertexBuilder bufferBuilder, Matrix4f matrix4f, int color, float alpha, float startX, float startY, float startZ, float endX, float endY, float endZ, int combinedLight) {
        final float r = (float)(color >> 16 & 255) / 255.0F;
        final float g = (float)(color >> 8 & 255) / 255.0F;
        final float b = (float)(color & 255) / 255.0F;
        bufferBuilder.pos(matrix4f, startX, endY, startZ).color(r * 0.8f, g * 0.8f, b * 0.8f, alpha).lightmap(combinedLight).endVertex();
        bufferBuilder.pos(matrix4f, endX, endY, startZ).color(r * 0.8f, g * 0.8f, b * 0.8f, alpha).lightmap(combinedLight).endVertex();
        bufferBuilder.pos(matrix4f, endX, startY, startZ).color(r * 0.8f, g * 0.8f, b * 0.8f, alpha).lightmap(combinedLight).endVertex();
        bufferBuilder.pos(matrix4f, startX, startY, startZ).color(r * 0.8f, g * 0.8f, b * 0.8f, alpha).lightmap(combinedLight).endVertex();
    }

    private static void renderFaceZPos(IVertexBuilder bufferBuilder, Matrix4f matrix4f, int color, float alpha, float startX, float startY, float startZ, float endX, float endY, float endZ, int combinedLight) {
        final float r = (float)(color >> 16 & 255) / 255.0F;
        final float g = (float)(color >> 8 & 255) / 255.0F;
        final float b = (float)(color & 255) / 255.0F;
        bufferBuilder.pos(matrix4f, startX, startY, endZ).color(r * 0.8f, g * 0.8f, b * 0.8f, alpha).lightmap(combinedLight).endVertex();
        bufferBuilder.pos(matrix4f, endX, startY, endZ).color(r * 0.8f, g * 0.8f, b * 0.8f, alpha).lightmap(combinedLight).endVertex();
        bufferBuilder.pos(matrix4f, endX, endY, endZ).color(r * 0.8f, g * 0.8f, b * 0.8f, alpha).lightmap(combinedLight).endVertex();
        bufferBuilder.pos(matrix4f, startX, endY, endZ).color(r * 0.8f, g * 0.8f, b * 0.8f, alpha).lightmap(combinedLight).endVertex();
    }
}
