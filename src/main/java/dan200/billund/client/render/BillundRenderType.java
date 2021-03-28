package dan200.billund.client.render;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import org.lwjgl.opengl.GL11;

public class BillundRenderType extends RenderType {
	public BillundRenderType(String nameIn, VertexFormat formatIn, int drawModeIn, int bufferSizeIn, boolean useDelegateIn, boolean needsSortingIn, Runnable setupTaskIn, Runnable clearTaskIn) {
		super(nameIn, formatIn, drawModeIn, bufferSizeIn, useDelegateIn, needsSortingIn, setupTaskIn, clearTaskIn);
	}

	public static RenderType billundRenderer() {
		RenderType.State renderTypeState = RenderType.State.getBuilder()
				.shadeModel(SHADE_ENABLED)
				.lightmap(LIGHTMAP_ENABLED)
				.build(false);
		return RenderType.makeType("billund", DefaultVertexFormats.POSITION_COLOR_LIGHTMAP, GL11.GL_QUADS, 2097152, true, false, renderTypeState);
	}
}
