package dan200.billund.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dan200.billund.client.helper.BrickRenderHelper;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import org.lwjgl.opengl.GL11;

import static net.minecraft.client.renderer.RenderState.LIGHTMAP_ENABLED;
import static net.minecraft.client.renderer.RenderState.SHADE_ENABLED;

public class ItemBrickRenderer extends ItemStackTileEntityRenderer {

    @Override
    public void func_239207_a_(ItemStack stack, TransformType type, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        final RenderType renderType = BillundRenderType.billundRenderer();
        IVertexBuilder vertexBuilder = buffer.getBuffer(renderType);

        matrixStack.push();
        switch (type) {
            case THIRD_PERSON_LEFT_HAND:
            case THIRD_PERSON_RIGHT_HAND:{
                matrixStack.translate(0.5f, 0.6f, 0.5f);
                BrickRenderHelper.renderBrick(stack, matrixStack, vertexBuilder, false, true, combinedLight);
                break;
            }
            case HEAD: {
                matrixStack.translate(0.6f, 0.6f, 0.6f);
                BrickRenderHelper.renderBrick(stack, matrixStack, vertexBuilder, false, true, combinedLight);
                break;
            }
            case GUI: {
                matrixStack.translate(0.5F, 0.5F, 0.0F);
                matrixStack.rotate(Vector3f.XP.rotationDegrees(30));
                matrixStack.rotate(Vector3f.YP.rotationDegrees(225));
                matrixStack.scale(0.625f, 0.625f, 0.625f);
                BrickRenderHelper.renderBrick(stack, matrixStack, vertexBuilder, true, true, combinedLight);
                break;
            }
            case FIRST_PERSON_LEFT_HAND:
            case FIRST_PERSON_RIGHT_HAND:
            default: {
                matrixStack.translate(0.6f, 0.6f, 0.6f);
                matrixStack.scale(0.25F, 0.25F, 0.25F);
                BrickRenderHelper.renderBrick(stack, matrixStack, vertexBuilder, true, true, combinedLight);
                break;
            }
        }

        if (vertexBuilder instanceof IRenderTypeBuffer.Impl) {
            ((IRenderTypeBuffer.Impl) vertexBuilder).finish();
        }
        matrixStack.pop();
    }
}
