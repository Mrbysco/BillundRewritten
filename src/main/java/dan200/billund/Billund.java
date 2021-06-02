package dan200.billund;

import dan200.billund.client.ClientProxy;
import dan200.billund.client.handler.ClientTickHandler;
import dan200.billund.client.handler.RenderEventHandler;
import dan200.billund.shared.handler.EntityEventHandler;
import dan200.billund.shared.network.PacketHandler;
import dan200.billund.shared.registry.BillundRegistry;
import dan200.billund.shared.registry.BillundSetRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Billund.MOD_ID)
public class Billund {
    public static final String MOD_ID = "billund";
    public static final Logger LOGGER = LogManager.getLogger();

    public Billund() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::setup);

        BillundRegistry.ENTITIES.register(eventBus);
        BillundRegistry.ITEMS.register(eventBus);
        BillundRegistry.BLOCKS.register(eventBus);
        BillundRegistry.TILES.register(eventBus);

        MinecraftForge.EVENT_BUS.register(new EntityEventHandler());

        MinecraftForge.EVENT_BUS.register(this);

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            eventBus.addListener(ClientProxy::registerRenders);
            MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
            MinecraftForge.EVENT_BUS.register(new RenderEventHandler());
        });
    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.initialize();
    }

    @SubscribeEvent
    public void serverStart(FMLServerStartingEvent event) {
        BillundSetRegistry.instance().initializeDefaults();
    }
}