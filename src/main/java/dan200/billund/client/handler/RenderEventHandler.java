package dan200.billund.client.handler;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dan200.billund.client.helper.BrickRenderHelper;
import dan200.billund.shared.data.Brick;
import dan200.billund.shared.item.BrickItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import static net.minecraft.client.renderer.RenderState.LIGHTMAP_ENABLED;
import static net.minecraft.client.renderer.RenderState.SHADE_ENABLED;

public class RenderEventHandler {

    private final RenderType renderType;

    public RenderEventHandler() {
        boolean useDelegate = true; //Not sure about this, seems to be true for most blocks.
        final RenderType.State renderTypeState = RenderType.State.getBuilder()
                .shadeModel(SHADE_ENABLED)
                .lightmap(LIGHTMAP_ENABLED)
                .build(false);
        this.renderType = RenderType.makeType("billund", DefaultVertexFormats.POSITION_COLOR, GL11.GL_QUADS, 2097152, useDelegate, false, renderTypeState);
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (Minecraft.getInstance().gameSettings.hideGUI) {
            return;
        }

        // Draw the preview brick
        float f = event.getPartialTicks();
        PlayerEntity player = mc.player;
        World world = mc.world;
        MatrixStack matrixStack = event.getMatrixStack();

        IRenderTypeBuffer.Impl buffer = mc.getRenderTypeBuffers().getBufferSource();
        IVertexBuilder vertexBuilder = buffer.getBuffer(renderType);

        matrixStack.push();
        if (player != null && world != null) {
            ItemStack currentStack = player.inventory.getCurrentItem();
            if (currentStack != null && currentStack.getItem() instanceof BrickItem) {
                Brick brick = BrickItem.getPotentialBrick(currentStack, player.world, player, f);
                if (brick != null) {
                    // Setup
//                    GL11.glPushMatrix();
//                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
//                    GL11.glDisable(GL11.GL_LIGHTING);
//                    GL11.glDisable(GL11.GL_TEXTURE_2D);
//                    GL11.glBlendFunc( GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA );
//                    GL11.glEnable(GL11.GL_BLEND);

//                    BrickRenderHelper.translateToWorldCoords(mc.renderViewEntity, f);
                    BrickRenderHelper.renderBrick(matrixStack, vertexBuilder, world, brick);

                    // Teardown
//                    GL11.glEnable(GL11.GL_TEXTURE_2D);
//                    GL11.glEnable(GL11.GL_LIGHTING);
//                    GL11.glPopMatrix();
                }
            }
        }
        matrixStack.pop();
    }
}