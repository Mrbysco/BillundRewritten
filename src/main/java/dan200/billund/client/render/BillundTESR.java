package dan200.billund.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import dan200.billund.client.helper.BrickRenderHelper;
import dan200.billund.shared.data.Brick;
import dan200.billund.shared.data.Stud;
import dan200.billund.shared.tile.BillundTileEntity;
import dan200.billund.shared.util.StudHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import static net.minecraft.client.renderer.RenderState.LIGHTMAP_ENABLED;
import static net.minecraft.client.renderer.RenderState.SHADE_ENABLED;

public class BillundTESR extends TileEntityRenderer<BillundTileEntity> {

    private final RenderType renderType;

    public BillundTESR(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);

        boolean useDelegate = true; //Not sure about this, seems to be true for most blocks.
        final RenderType.State renderTypeState = RenderType.State.getBuilder()
                .shadeModel(SHADE_ENABLED)
                .lightmap(LIGHTMAP_ENABLED)
                .build(false);
        this.renderType = RenderType.makeType("billund", DefaultVertexFormats.POSITION_COLOR, GL11.GL_QUADS, 2097152, useDelegate, false, renderTypeState);
    }

    @Override
    public void render(BillundTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        matrixStackIn.push();
//        GL11.glPushMatrix();
//        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
//        GL11.glDisable(GL11.GL_LIGHTING);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glBlendFunc( GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA );
//        GL11.glEnable(GL11.GL_BLEND);

        final BlockPos pos = tileEntityIn.getPos();
        final World world = tileEntityIn.getWorld();
        for (int x = 0; x < StudHelper.STUDS_PER_ROW; ++x) {
            for (int y = 0; y < StudHelper.STUDS_PER_COLUMN; ++y) {
                for (int z = 0; z < StudHelper.STUDS_PER_ROW; ++z) {
                    Stud stud = tileEntityIn.getStudLocal(x, y, z);
                    if (stud != null && !stud.illuminated) {
                        int sx = pos.getX() * StudHelper.STUDS_PER_ROW + x;
                        int sy = pos.getY() * StudHelper.STUDS_PER_COLUMN + y;
                        int sz = pos.getZ() * StudHelper.STUDS_PER_ROW + z;
                        if (stud.xOrigin == sx && stud.yOrigin == sy && stud.zOrigin == sz) {
                            // Draw the brick
//                            int brightness = BillundBlocks.billund.getMixedBrightnessForBlock(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
                            Brick brick = new Brick(stud.illuminated, stud.transparent, stud.smooth, stud.color, stud.xOrigin, stud.yOrigin, stud.zOrigin, stud.brickWidth, stud.brickHeight, stud.brickDepth);
                            BrickRenderHelper.renderBrick(matrixStackIn, bufferIn.getBuffer(renderType), world, brick);
                        }
                    }
                }
            }
        }

//        char c0 = 61680;
//        int j = c0 % 65536;
//        int k = c0 / 65536;
//        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j / 1.0F, (float)k / 1.0F);
//        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

//        for (int x = 0; x < StudHelper.STUDS_PER_ROW; ++x) {
//            for (int y = 0; y < StudHelper.STUDS_PER_COLUMN; ++y) {
//                for (int z = 0; z < StudHelper.STUDS_PER_ROW; ++z) {
//                    Stud stud = tileEntityIn.getStudLocal(x, y, z);
//                    if (stud != null && stud.illuminated) {
//                        int sx = pos.getX() * StudHelper.STUDS_PER_ROW + x;
//                        int sy = pos.getY() * StudHelper.STUDS_PER_COLUMN + y;
//                        int sz = pos.getZ() * StudHelper.STUDS_PER_ROW + z;
//                        if (stud.xOrigin == sx && stud.yOrigin == sy && stud.zOrigin == sz) {
                            // Draw the brick
//                            int brightness = BillundBlocks.billund.getMixedBrightnessForBlock(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
//
//                            BrickRenderHelper.renderBrick(matrixStackIn, bufferIn.getBuffer(renderType), world, stud);
//                        }
//                    }
//                }
//            }
//        }

//        int i = tile.getWorldObj().getLightBrightnessForSkyBlocks(tile.xCoord, tile.yCoord, tile.zCoord, 0);
//        int j1 = i % 65536;
//        int k1 = i / 65536;
//        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j1 / 1.0F, (float)k1 / 1.0F);
//        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        // Teardown
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glEnable(GL11.GL_LIGHTING);
//        GL11.glPopMatrix();
        matrixStackIn.pop();
    }
}