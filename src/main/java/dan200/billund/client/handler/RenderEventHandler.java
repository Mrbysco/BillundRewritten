package dan200.billund.client.handler;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dan200.billund.client.helper.BrickRenderHelper;
import dan200.billund.client.render.BillundRenderType;
import dan200.billund.shared.data.Brick;
import dan200.billund.shared.item.BrickItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import static net.minecraft.client.renderer.RenderState.LIGHTMAP_ENABLED;
import static net.minecraft.client.renderer.RenderState.SHADE_ENABLED;

public class RenderEventHandler {

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        final Minecraft mc = Minecraft.getInstance();
        if (mc.gameSettings.hideGUI) {
            return;
        }

        // Draw the preview brick
        final PlayerEntity player = mc.player;
        final World world = mc.world;
        MatrixStack matrixStack = event.getMatrixStack();

        final IRenderTypeBuffer.Impl buffer = mc.getRenderTypeBuffers().getBufferSource();
        final RenderType renderType = BillundRenderType.billundRenderer();
        final IVertexBuilder vertexBuilder = buffer.getBuffer(renderType);

        matrixStack.push();
        final Vector3d projectedView = Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView();
        matrixStack.translate(-projectedView.x, -projectedView.y, -projectedView.z);
        if (player != null && world != null) {
            ItemStack currentStack = player.getHeldItemMainhand();
            if (currentStack != null && currentStack.getItem() instanceof BrickItem) {
                Brick brick = BrickItem.getPotentialBrick(currentStack, player.world, player, event.getPartialTicks());
                if (brick != null) {
                    BrickRenderHelper.renderBrick(matrixStack, vertexBuilder, world, brick);
                }
            }
        }
        buffer.finish(renderType);
        matrixStack.pop();
    }
}