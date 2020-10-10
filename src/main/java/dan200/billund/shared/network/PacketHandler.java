package dan200.billund.shared.network;

import dan200.billund.Billund;
import dan200.billund.shared.network.message.OrderMessage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

/**
 * @author dmillerw
 */
public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Billund.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int ID = 0;
    public static void initialize() {
        CHANNEL.registerMessage(ID++, OrderMessage.class, OrderMessage::encode, OrderMessage::decode, OrderMessage::handle);
//        CHANNEL.registerMessage(ID++, MessageRotate.class, MessageRotate::encode, MessageRotate::decode, MessageRotate::handle);
    }
}
