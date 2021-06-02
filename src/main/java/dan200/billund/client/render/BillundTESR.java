package dan200.billund.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dan200.billund.client.helper.BrickRenderHelper;
import dan200.billund.shared.data.Brick;
import dan200.billund.shared.data.Stud;
import dan200.billund.shared.tile.BillundTileEntity;
import dan200.billund.shared.util.StudHelper;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BillundTESR extends TileEntityRenderer<BillundTileEntity> {

    public BillundTESR(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(BillundTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        final RenderType renderType = BillundRenderType.billundRenderer();
        IVertexBuilder vertexBuilder = bufferIn.getBuffer(renderType);

        matrixStackIn.push();
        final BlockPos pos = tileEntityIn.getPos();
        matrixStackIn.translate(-pos.getX(), -pos.getY(), -pos.getZ());
        final World world = tileEntityIn.getWorld();
        for (int x = 0; x < StudHelper.STUDS_PER_ROW; ++x) {
            for (int y = 0; y < StudHelper.STUDS_PER_COLUMN; ++y) {
                for (int z = 0; z < StudHelper.STUDS_PER_ROW; ++z) {
                    Stud stud = tileEntityIn.getStudLocal(x, y, z);
                    if (stud != null) {
                        int sx = pos.getX() * StudHelper.STUDS_PER_ROW + x;
                        int sy = pos.getY() * StudHelper.STUDS_PER_COLUMN + y;
                        int sz = pos.getZ() * StudHelper.STUDS_PER_ROW + z;
                        if (stud.xOrigin == sx && stud.yOrigin == sy && stud.zOrigin == sz) {
                            // Draw the brick
                            Brick brick = new Brick(stud.illuminated, stud.transparent, stud.smooth, stud.color, stud.xOrigin, stud.yOrigin, stud.zOrigin, stud.brickWidth, stud.brickHeight, stud.brickDepth);
                            BrickRenderHelper.renderBrick(matrixStackIn, vertexBuilder, world, brick);
                        }
                    }
                }
            }
        }

        if (vertexBuilder instanceof IRenderTypeBuffer.Impl) {
            ((IRenderTypeBuffer.Impl) vertexBuilder).finish();
        }
        matrixStackIn.pop();
    }
}