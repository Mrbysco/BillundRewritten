package dan200.billund.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import dan200.billund.client.render.model.ParachuteModel;
import dan200.billund.shared.entity.AirDropEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

public class AirDropRenderer extends EntityRenderer<AirDropEntity> {

    private static final ResourceLocation chuteTexture = new ResourceLocation("billund", "textures/models/chute.png");
    private final ParachuteModel parachuteModel = new ParachuteModel();

    public AirDropRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowSize = 0.5f;
    }

    @Override
    public void render(AirDropEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        World world = entityIn.getEntityWorld();
        final Block block = entityIn.block;
        final BlockState state = block.getDefaultState();
        if (state != world.getBlockState(entityIn.getPosition()) && state.getRenderType() != BlockRenderType.INVISIBLE) {
            matrixStackIn.push();
            matrixStackIn.translate(-0.5D, 0.0D, -0.5D);
            BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
            blockrendererdispatcher.renderBlock(state, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY);
            net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);
            matrixStackIn.pop();

            if (!entityIn.deployed) {
                matrixStackIn.push();
                matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(180));
                matrixStackIn.translate(-0f, -1.25F, 0f);
                matrixStackIn.scale(1.2f, 1.2f, 1.2f);

                parachuteModel.render(matrixStackIn, bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(chuteTexture)), packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                matrixStackIn.pop();
            }
        }
    }

    @Override
    public ResourceLocation getEntityTexture(AirDropEntity entity) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }
}
