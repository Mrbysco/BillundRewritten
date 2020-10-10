package dan200.billund.client.helper;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.DefaultColorVertexBuilder;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.MatrixApplyingVertexBuilder;
import dan200.billund.client.render.ColourFixedMatrixApplyingVertexBuilder;
import dan200.billund.shared.data.Brick;
import dan200.billund.shared.data.Stud;
import dan200.billund.shared.item.BrickItem;
import dan200.billund.shared.tile.BillundTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import org.lwjgl.opengl.GL11;

/**
 * @author dmillerw
 */
public class BrickRenderHelper {

    public static void translateToWorldCoords(Entity entity, float frame) {
        double interpPosX = entity.lastTickPosX + (entity.getPosX() - entity.lastTickPosX) * frame;
        double interpPosY = entity.lastTickPosY + (entity.getPosY() - entity.lastTickPosY) * frame;
        double interpPosZ = entity.lastTickPosZ + (entity.getPosZ() - entity.lastTickPosZ) * frame;
        GL11.glTranslated(-interpPosX, -interpPosY, -interpPosZ);
    }

    public static int getColor(int r, int g, int b) {
        return ((r & 255) << 16) |
               ((g & 255) << 8)  |
               ((b & 255) << 0);
    }

    public static void renderBrick(ItemStack brick, MatrixStack matrixStack, IVertexBuilder vertexBuilder, boolean scale, boolean center) {
        int brightness = 15;

        boolean smooth = BrickItem.getSmooth(brick);
        int color = ((BrickItem)brick.getItem()).getColorValue();
        int width = ((BrickItem)brick.getItem()).getWidth();
        int height = BrickItem.getHeight(brick);
        int depth = ((BrickItem)brick.getItem()).getDepth();

        // Setup
        //GL11.glPushMatrix();
        matrixStack.push();

        if (scale) {
            float scaleValue = ((float) BillundTileEntity.LAYERS_PER_BLOCK) / Math.max(2.0f, (float) Math.max(width, depth) - 0.5f);
            //GL11.glScalef(scaleValue, scaleValue, scaleValue);
            matrixStack.scale(scaleValue, scaleValue, scaleValue);
        }
        if (center) {
            //GL11.glTranslatef(
            matrixStack.translate(
                    -0.5f * ((float) width / (float) BillundTileEntity.ROWS_PER_BLOCK),
                    -0.5f * ((float) height / (float) BillundTileEntity.LAYERS_PER_BLOCK),
                    -0.5f * ((float) depth / (float) BillundTileEntity.ROWS_PER_BLOCK)
            );
        }

        //GL11.glDisable(GL11.GL_TEXTURE_2D);
        //GL11.glDisable(GL11.GL_LIGHTING);
        //GL11.glColor4f(1.0f, 0.0f, 1.0f, 1.0f);

        DefaultColorVertexBuilder bufferBuilder = new ColourFixedMatrixApplyingVertexBuilder(vertexBuilder, matrixStack.getLast().getMatrix(), matrixStack.getLast().getNormal());

        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;
        bufferBuilder.setDefaultColor(r, g, b, 255);

        renderBrick(bufferBuilder, null, brightness, false, smooth, color, 1.0F, 0, 0, 0, width, height, depth);

        // Teardown
        //GL11.glEnable(GL11.GL_TEXTURE_2D);
        //GL11.glEnable(GL11.GL_LIGHTING);
        //GL11.glPopMatrix();
    }

    public static void renderBrick(IVertexBuilder vertexBuilder, IBlockDisplayReader world, Brick brick) {
        int localX = (brick.xOrigin % BillundTileEntity.ROWS_PER_BLOCK + BillundTileEntity.ROWS_PER_BLOCK) % BillundTileEntity.ROWS_PER_BLOCK;
        int localY = (brick.yOrigin % BillundTileEntity.LAYERS_PER_BLOCK + BillundTileEntity.LAYERS_PER_BLOCK) % BillundTileEntity.LAYERS_PER_BLOCK;
        int localZ = (brick.zOrigin % BillundTileEntity.ROWS_PER_BLOCK + BillundTileEntity.ROWS_PER_BLOCK) % BillundTileEntity.ROWS_PER_BLOCK;
        int blockX = (brick.xOrigin - localX) / BillundTileEntity.ROWS_PER_BLOCK;
        int blockY = (brick.yOrigin - localY) / BillundTileEntity.LAYERS_PER_BLOCK;
        int blockZ = (brick.zOrigin - localZ) / BillundTileEntity.ROWS_PER_BLOCK;
        BlockPos blockPos = new BlockPos(blockX, blockY, blockZ);

        //Tessellator tessellator = Tessellator.getInstance();
        BlockState state = world.getBlockState(blockPos);
        //BillundBlocks.billund.getMixedBrightnessForBlock(world, blockX, blockY, blockZ);
        int brightness = WorldRenderer.getPackedLightmapCoords(world, state, blockPos);

        int r = brick.color >> 16 & 255;
        int g = brick.color >> 8 & 255;
        int b = brick.color & 255;
        int a = (int)(0.65f * 255f);

        DefaultColorVertexBuilder bufferBuilder = new ColourFixedMatrixApplyingVertexBuilder(vertexBuilder, matrixStack.getLast().getMatrix(), matrixStack.getLast().getNormal());
        bufferBuilder.setDefaultColor(r, g, b, a);

        renderBrick(bufferBuilder, world, brightness, brick.illuminated, brick.smooth, brick.color, 0.65F, brick.xOrigin, brick.yOrigin, brick.zOrigin, brick.width, brick.height, brick.depth);
    }

    public static void renderBrick(IVertexBuilder vertexBuilder, IBlockReader world, int brightness, boolean illuminated, boolean smooth, int colour, float alpha, int sx, int sy, int sz, int width, int height, int depth) {
        // Draw the brick
        if (world != null && !illuminated) {
            //Tessellator tessellator = Tessellator.getInstance();
//            tessellator.setBrightness(brightness);
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
                colour,
                alpha,
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
                            colour,
                            alpha,
                            startX + pixel * 2.0f, startY, startZ + pixel * 4.0f,
                            startX + pixel * 4.0f, endY, endZ - pixel * 4.0f,
                            false
                    );
                    renderBox(
                            vertexBuilder,
                            colour,
                            alpha,
                            startX + pixel * 4.0f, startY, startZ + pixel * 2.0f,
                            endX - pixel * 4.0f, endY, endZ - pixel * 2.0f,
                            false
                    );
                    renderBox(
                            vertexBuilder,
                            colour,
                            alpha,
                            endX - pixel * 4.0f, startY, startZ + pixel * 4.0f,
                            endX - pixel * 2.0f, endY, endZ - pixel * 4.0f,
                            false
                    );
                }
            }
        }
    }

    private static void renderBox(IVertexBuilder vertexBuilder, int color, float alpha, float startX, float startY, float startZ, float endX, float endY, float endZ, boolean bottom) {
        // X faces
        renderFaceXNeg(vertexBuilder, color, alpha, startX, startY, startZ, endX, endY, endZ);
        renderFaceXPos(vertexBuilder, color, alpha, startX, startY, startZ, endX, endY, endZ);

        // Y faces
        if (bottom) {
            renderFaceYNeg(vertexBuilder, color, alpha, startX, startY, startZ, endX, endY, endZ);
        }
        renderFaceYPos(vertexBuilder, color, alpha, startX, startY, startZ, endX, endY, endZ);

        // Z faces
        renderFaceZNeg(vertexBuilder, color, alpha, startX, startY, startZ, endX, endY, endZ);
        renderFaceZPos(vertexBuilder, color, alpha, startX, startY, startZ, endX, endY, endZ);
    }

    private static void renderFaceXNeg(IVertexBuilder bufferBuilder, int color, float alpha, float startX, float startY, float startZ, float endX, float endY, float endZ) {
//        float r = (float)(color >> 16 & 255) / 255.0F;
//        float g = (float)(color >> 8 & 255) / 255.0F;
//        float b = (float)(color & 255) / 255.0F;
//        tessellator.setColorRGBA_F(r * 0.6f, g * 0.6f,  b * 0.6f, alpha);
//        tessellator.addVertex(startX, endY, endZ);
//        tessellator.addVertex(startX, endY, startZ);
//        tessellator.addVertex(startX, startY, startZ);
//        tessellator.addVertex(startX, startY, endZ);
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder bufferBuilder = tessellator.getBuffer();
//        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(startX, endY, endZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(startX, endY, startZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(startX, startY, startZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(startX, startY, endZ).normal(0.0f, -1.0f, 0.0f).endVertex();
//        tessellator.draw();
    }

    private static void renderFaceXPos(IVertexBuilder bufferBuilder, int color, float alpha, float startX, float startY, float startZ, float endX, float endY, float endZ) {
//        float r = (float)(color >> 16 & 255) / 255.0F;
//        float g = (float)(color >> 8 & 255) / 255.0F;
//        float b = (float)(color & 255) / 255.0F;
//        tessellator.setColorRGBA_F(r * 0.6f, g * 0.6f,  b * 0.6f, alpha);
//        tessellator.addVertex(endX, startY, endZ);
//        tessellator.addVertex(endX, startY, startZ);
//        tessellator.addVertex(endX, endY, startZ);
//        tessellator.addVertex(endX, endY, endZ);
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder bufferBuilder = tessellator.getBuffer();
//        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(endX, startY, endZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(endX, startY, startZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(endX, endY, startZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(endX, endY, endZ).normal(0.0f, -1.0f, 0.0f).endVertex();
//        tessellator.draw();
    }

    private static void renderFaceYNeg(IVertexBuilder bufferBuilder, int color, float alpha, float startX, float startY, float startZ, float endX, float endY, float endZ) {
//        float r = (float)(color >> 16 & 255) / 255.0F;
//        float g = (float)(color >> 8 & 255) / 255.0F;
//        float b = (float)(color & 255) / 255.0F;
//        tessellator.setColorRGBA_F(r * 0.5f, g * 0.5f, b * 0.5f, alpha);
//        tessellator.addVertex(startX, startY, endZ);
//        tessellator.addVertex(startX, startY, startZ);
//        tessellator.addVertex(endX, startY, startZ);
//        tessellator.addVertex(endX, startY, endZ);
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder bufferBuilder = tessellator.getBuffer();
//        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(startX, startY, endZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(startX, startY, startZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(endX, startY, startZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(endX, startY, endZ).normal(0.0f, -1.0f, 0.0f).endVertex();
//        tessellator.draw();
    }

    private static void renderFaceYPos(IVertexBuilder bufferBuilder, int color, float alpha, float startX, float startY, float startZ, float endX, float endY, float endZ) {
//        float r = (float)(color >> 16 & 255) / 255.0F;
//        float g = (float)(color >> 8 & 255) / 255.0F;
//        float b = (float)(color & 255) / 255.0F;
        //bufferBuilder.color(r, g, b, alpha);
//        tessellator.setColorRGBA_F(r, g, b, alpha);
//        tessellator.addVertex(endX, endY, endZ);
//        tessellator.addVertex(endX, endY, startZ);
//        tessellator.addVertex(startX, endY, startZ);
//        tessellator.addVertex(startX, endY, endZ);
        //Tessellator tessellator = Tessellator.getInstance();
        //BufferBuilder bufferBuilder = tessellator.getBuffer();
        //bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(endX, endY, endZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(endX, endY, startZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(startX, endY, startZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(startX, endY, endZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        //tessellator.draw();
    }

    private static void renderFaceZNeg(IVertexBuilder bufferBuilder, int color, float alpha, float startX, float startY, float startZ, float endX, float endY, float endZ) {
//        float r = (float)(color >> 16 & 255) / 255.0F;
//        float g = (float)(color >> 8 & 255) / 255.0F;
//        float b = (float)(color & 255) / 255.0F;
//        tessellator.setColorRGBA_F(r * 0.8f, g * 0.8f, b * 0.8f, alpha);
//        tessellator.addVertex(startX, endY, startZ);
//        tessellator.addVertex(endX, endY, startZ);
//        tessellator.addVertex(endX, startY, startZ);
//        tessellator.addVertex(startX, startY, startZ);
        //Tessellator tessellator = Tessellator.getInstance();
        //BufferBuilder bufferBuilder = tessellator.getBuffer();
        //bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(startX, endY, startZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(endX, endY, startZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(endX, startY, startZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(startX, startY, startZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        //tessellator.draw();
    }

    private static void renderFaceZPos(IVertexBuilder bufferBuilder, int color, float alpha, float startX, float startY, float startZ, float endX, float endY, float endZ) {
//        float r = (float)(color >> 16 & 255) / 255.0F;
//        float g = (float)(color >> 8 & 255) / 255.0F;
//        float b = (float)(color & 255) / 255.0F;
//        tessellator.setColorRGBA_F((int)r * 0.8f, (int)g * 0.8f, (int)b * 0.8f, alpha);
//        tessellator.addVertex(startX, startY, endZ);
//        tessellator.addVertex(endX, startY, endZ);
//        tessellator.addVertex(endX, endY, endZ);
//        tessellator.addVertex(startX, endY, endZ);
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder bufferBuilder = tessellator.getBuffer();
//        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(startX, startY, endZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(endX, startY, endZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(endX, endY, endZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        bufferBuilder.pos(startX, endY, endZ).normal(0.0f, -1.0f, 0.0f).endVertex();
//        tessellator.draw();
    }
}
