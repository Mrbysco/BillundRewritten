package dan200.billund.client;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy {

    public static void registerRenders(FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(KeyBindings.KEY_ROTATE);

//        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBillund.class, new BrickBlockRenderer());
    }
}
