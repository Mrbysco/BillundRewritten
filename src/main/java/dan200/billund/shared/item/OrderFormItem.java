package dan200.billund.shared.item;

import dan200.billund.client.gui.OrderFormScreen;
import dan200.billund.shared.registry.BillundSetRegistry;
import dan200.billund.shared.registry.BillundTabs;
import net.minecraft.client.Minecraft;
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
        ItemStack stack = playerIn.getHeldItem(handIn);
        if(worldIn.isRemote) {
            Minecraft.getInstance().displayGuiScreen(new OrderFormScreen(playerIn, BillundSetRegistry.instance().getAllSets()));
        }
        return ActionResult.resultPass(stack);
    }
}
