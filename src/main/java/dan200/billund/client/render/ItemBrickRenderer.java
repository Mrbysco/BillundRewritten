package dan200.billund.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import dan200.billund.client.helper.BrickRenderHelper;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import static net.minecraft.client.renderer.RenderState.LIGHTMAP_ENABLED;
import static net.minecraft.client.renderer.RenderState.SHADE_ENABLED;

public class ItemBrickRenderer extends ItemStackTileEntityRenderer {

    private final RenderType renderType;

    public ItemBrickRenderer() {
        super();

        boolean useDelegate = true; //Not sure about this, seems to be true for most blocks.
        final RenderType.State renderTypeState = RenderType.State.getBuilder()
                .shadeModel(SHADE_ENABLED)
                .lightmap(LIGHTMAP_ENABLED)
                .build(false);
        this.renderType = RenderType.makeType("billund", DefaultVertexFormats.POSITION_COLOR, GL11.GL_QUADS, 2097152, useDelegate, false, renderTypeState);
    }

    @Override
    public void func_239207_a_(ItemStack stack, TransformType type, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        switch (type) {
            case THIRD_PERSON_LEFT_HAND:
            case THIRD_PERSON_RIGHT_HAND:{
                BrickRenderHelper.renderBrick(stack, matrixStack, buffer.getBuffer(renderType), false, true);
                break;
            }
            case HEAD: {
                matrixStack.push();
                matrixStack.translate(0.6f, 0.6f, 0.6f);
                BrickRenderHelper.renderBrick(stack, matrixStack, buffer.getBuffer(renderType), false, true);
                matrixStack.pop();
                break;
            }
            case GUI: {
                matrixStack.push();
                matrixStack.translate(0.5f, 0.5f, 0.5f);
                BrickRenderHelper.renderBrick(stack, matrixStack, buffer.getBuffer(renderType), true, true);
                matrixStack.pop();
                break;
            }
            case FIRST_PERSON_LEFT_HAND:
            case FIRST_PERSON_RIGHT_HAND:
            default: {
                break;
            }
        }
    }
}
