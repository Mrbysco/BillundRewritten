package dan200.billund.shared.registry;

import dan200.billund.shared.item.BrickItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.RegistryObject;

public class BrickHelper {
    public static ItemStack createBrickStack(boolean illuminated, boolean transparent, boolean smooth, DyeColor color, int width, int depth, int quantity) {
        for(RegistryObject<?> object : BillundRegistry.ITEMS.getEntries()) {
            if(object.get() instanceof BrickItem) {
                BrickItem brickItem = (BrickItem)object.get();
                if(brickItem.getWidth() == width && brickItem.getDepth() == depth && color == brickItem.getColor()) {
                    ItemStack stack = new ItemStack(brickItem, quantity);
                    CompoundNBT nbt = new CompoundNBT();
                    nbt.putBoolean("illuminated", illuminated);
                    nbt.putBoolean("transparent", transparent);
                    nbt.putBoolean("smooth", smooth);
                    stack.setTag(nbt);
                    return stack;
                }
            }
        }

        return ItemStack.EMPTY;
    }

    public static ItemStack createBrickStack(boolean illuminated, boolean transparent, boolean smooth, int color, int width, int depth, int quantity) {
        for(DyeColor dye : DyeColor.values()) {
            if(dye.getColorValue() == color) {
                return createBrickStack(illuminated, transparent, smooth, dye, width, depth, quantity);
            }
        }
        return createBrickStack(illuminated, transparent, smooth, DyeColor.WHITE, width, depth, quantity);
    }

    public static ItemStack createBrickStack(DyeColor color, int width, int depth, int quantity) {
        return createBrickStack(false, false, false, color, width, depth, quantity);
    }
}
