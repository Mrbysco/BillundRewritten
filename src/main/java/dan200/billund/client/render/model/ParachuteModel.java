
package dan200.billund.client.render.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dan200.billund.shared.entity.AirDropEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ParachuteModel extends EntityModel<AirDropEntity> {
    private final ModelRenderer root;
    private final ModelRenderer wire3;
    private final ModelRenderer wire4;
    private final ModelRenderer parachute;
    private final ModelRenderer wire1;
    private final ModelRenderer wire2;
    private final ModelRenderer leftWing;
    private final ModelRenderer rightWing;

    public ParachuteModel() {
        textureWidth = 64;
        textureHeight = 64;

        root = new ModelRenderer(this);
        root.setRotationPoint(0.0F, 0.0F, 0.0F);

        wire3 = new ModelRenderer(this);
        wire3.setRotationPoint(0.0F, 0.0F, 0.0F);
        root.addChild(wire3);
        wire3.setTextureOffset(0, 0).addBox(-5.75F, 0.5F, 5.0F, 1.0F, 10.0F, 1.0F, 0.0F, false);

        wire4 = new ModelRenderer(this);
        wire4.setRotationPoint(0.0F, 0.0F, 0.0F);
        root.addChild(wire4);
        wire4.setTextureOffset(0, 0).addBox(4.75F, 0.5F, 5.0F, 1.0F, 10.0F, 1.0F, 0.0F, false);

        parachute = new ModelRenderer(this);
        parachute.setRotationPoint(0.0F, 0.0F, 0.0F);
        root.addChild(parachute);
        parachute.setTextureOffset(0, 0).addBox(-6.0F, -0.5F, -6.0F, 12.0F, 1.0F, 12.0F, 0.0F, false);

        wire1 = new ModelRenderer(this);
        wire1.setRotationPoint(-5.25F, 5.5F, -5.5F);
        root.addChild(wire1);
        wire1.setTextureOffset(0, 0).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 10.0F, 1.0F, 0.0F, false);

        wire2 = new ModelRenderer(this);
        wire2.setRotationPoint(5.25F, 5.5F, -5.5F);
        root.addChild(wire2);
        wire2.setTextureOffset(0, 0).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 10.0F, 1.0F, 0.0F, false);

        leftWing = new ModelRenderer(this);
        leftWing.setRotationPoint(0.0F, 0.0F, 0.0F);
        root.addChild(leftWing);
        setRotationAngle(leftWing, 0.0F, 0.0F, 2.5656F);
        leftWing.setTextureOffset(0, 0).addBox(4.6892F, 2.6722F, -6.0F, 6.0F, 1.0F, 12.0F, 0.0F, false);

        rightWing = new ModelRenderer(this);
        rightWing.setRotationPoint(-0.3F, -0.2F, 0.0F);
        root.addChild(rightWing);
        setRotationAngle(rightWing, 0.0F, 0.0F, -2.5656F);
        rightWing.setTextureOffset(0, 0).addBox(-11.1584F, 2.6722F, -6.0F, 6.0F, 1.0F, 12.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(AirDropEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        root.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
