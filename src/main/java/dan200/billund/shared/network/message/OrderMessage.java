package dan200.billund.shared.network.message;

import dan200.billund.Billund;
import dan200.billund.shared.data.BillundSet;
import dan200.billund.shared.entity.AirDropEntity;
import dan200.billund.shared.registry.BillundSetRegistry;
import dan200.billund.shared.util.EmeraldHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.Random;
import java.util.function.Supplier;

public class OrderMessage {
    public String setName;

    public OrderMessage() {
        this.setName = "BlindBag";
    }

    public OrderMessage(String setName) {
        this.setName = setName;
    }

    public void encode(PacketBuffer buf) {
        buf.writeString(setName);
    }

    public static OrderMessage decode(final PacketBuffer buf) {
        return new OrderMessage(buf.readString());
    }

    public void handle(Supplier<Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            if (ctx.getDirection().getReceptionSide().isServer() && ctx.getDirection().getOriginationSide().isClient()) {
                PlayerEntity player = ctx.getSender();
                Random r = new Random();
                if(player != null) {
                    Billund.LOGGER.info(String.format("Received order for %s from %s", setName, player.getDisplayName().getUnformattedComponentText()));

                    World world = player.world;
                    BillundSet set = BillundSetRegistry.instance().getBillundSet(setName);
                    int cost = set.getCost();
                    if (EmeraldHelper.removeEmeralds(player, cost)) {
                        world.addEntity(new AirDropEntity(
                                world, new BlockPos(
                                Math.floor(player.getPosX() - 8 + r.nextInt(16)) + 0.5f,
                                Math.min(world.getHeight(), 255) - r.nextInt(32) - 0.5f,
                                Math.floor(player.getPosZ() - 8 + r.nextInt(16)) + 0.5f
                        ), set
                        ));
                    }
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
