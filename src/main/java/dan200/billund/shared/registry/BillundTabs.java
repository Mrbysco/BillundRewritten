package dan200.billund.shared.registry;

import dan200.billund.Billund;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BillundTabs {
    public static final ItemGroup BILLUND_TAB = new ItemGroup(Billund.MOD_ID + ".tab") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(BillundRegistry.WHITE_2_2_BRICK.get());
        }
    };
}
