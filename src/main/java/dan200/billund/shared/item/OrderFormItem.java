package dan200.billund.shared.item;

import dan200.billund.shared.core.BillundTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class OrderFormItem extends Item {

    public OrderFormItem(Properties properties) {
        super(properties.maxStackSize(1).group(BillundTabs.BILLUND_TAB));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        //TODO: Insert GUI
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
