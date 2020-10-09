package dan200.billund.client;

import dan200.billund.client.render.ItemBrickRenderer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;

import java.util.concurrent.Callable;

public class BillundISTERProvider {
    public static Callable<ItemStackTileEntityRenderer> brick() {
        return ItemBrickRenderer::new;
    }
}
