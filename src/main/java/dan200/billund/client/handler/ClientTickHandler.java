package dan200.billund.client.handler;

import dan200.billund.Billund;
import dan200.billund.client.KeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientTickHandler {

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        PlayerEntity player = Minecraft.getInstance().player;

        if (player != null) {
            if (KeyBindings.KEY_ROTATE.isPressed()) {
                Billund.LOGGER.info("Pressed Rotate Keybind");
//                BillundProxyCommon.rotate = (BillundProxyCommon.rotate + 1) % 4;
//                MessageRotate messageRotate = new MessageRotate();
//                messageRotate.rotate = BillundProxyCommon.rotate;
//                PacketHandler.INSTANCE.sendToServer(messageRotate);
            }
        }
    }
}
