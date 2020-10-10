package dan200.billund.shared.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

/**
 * @author dmillerw
 */
public class EmeraldHelper {

    public static boolean removeEmeralds(PlayerEntity player, int cost) {
        if (player.abilities.isCreativeMode) {
            return true;
        }

        // Find enough emeralds
        int emeralds = 0;
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() == Items.EMERALD) {
                emeralds += stack.getCount();
                if (emeralds >= cost) {
                    break;
                }
            }
        }

        if (emeralds >= cost) {
            // Then expend them
            emeralds = cost;
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack stack = player.inventory.getStackInSlot(i);
                if (stack != null && stack.getItem() == Items.EMERALD) {
                    if (stack.getCount() <= emeralds) {
                        player.inventory.setInventorySlotContents(i, null);
                        emeralds -= stack.getCount();
                    } else {
                        stack.setCount(stack.getCount() - emeralds);
//                        stack.stackSize -= emeralds;
                        emeralds = 0;
                    }
                    if (emeralds == 0) {
                        break;
                    }
                }
            }
            player.inventory.markDirty();
            return true;
        }
        return false;
    }
}
