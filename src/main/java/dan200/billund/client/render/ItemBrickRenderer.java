package dan200.billund.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import dan200.billund.client.helper.BrickRenderHelper;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;

public class ItemBrickRenderer extends ItemStackTileEntityRenderer {


    @Override
    public void func_239207_a_(ItemStack stack, TransformType type, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        switch (type) {
            case THIRD_PERSON_LEFT_HAND:
            case THIRD_PERSON_RIGHT_HAND:{
                BrickRenderHelper.renderBrick(stack, false, true);
                break;
            }
            case HEAD: {
                matrixStack.push();
                matrixStack.translate(0.6f, 0.6f, 0.6f);
                BrickRenderHelper.renderBrick(stack, false, true);
                matrixStack.pop();
                break;
            }
            case GUI: {
                matrixStack.push();
                matrixStack.translate(0.5f, 0.5f, 0.5f);
                BrickRenderHelper.renderBrick(stack, true, true);
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
