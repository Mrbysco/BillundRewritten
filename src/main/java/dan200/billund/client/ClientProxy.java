package dan200.billund.client;

import dan200.billund.client.render.AirDropRenderer;
import dan200.billund.shared.registry.BillundRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy {

    public static void registerRenders(FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(KeyBindings.KEY_ROTATE);

//        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBillund.class, new BrickBlockRenderer());

        RenderingRegistry.registerEntityRenderingHandler(BillundRegistry.AIR_DROP.get(), AirDropRenderer::new);
    }
}
