/**
 * This file is part of Billund - http://www.computercraft.info/billund
 * Copyright Daniel Ratcliffe, 2013. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */

package dan200.billund.shared.data;

import dan200.billund.shared.core.BrickHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class BillundSet {

    public static BillundSet get(int index) {
        return new BillundSet(index);
    }

    private static String[] s_setNames = new String[]{
            "pack.starter",
            "pack.colorA",
            "pack.colorB",
            "pack.colorC",
            "pack.colorD",
            "pack.random",
    };

    private static int[] s_setCosts = new int[]{
            7,
            10,
            10,
            10,
            10,
            15
    };

    private int m_index;

    public BillundSet(int index) {
        m_index = index;
    }

    public int getCost() {
        return s_setCosts[m_index];
    }

    public String getDescription() {
        return s_setNames[m_index];
    }

    private IInventory s_addInventory = null;
    private int s_addIndex = 0;

    public void populateChest(IInventory inv) {
        s_addIndex = 0;
        s_addInventory = inv;

        switch (m_index) {
            case 0: {
                // Starter set
                // Basic pieces in 6 colours
                addBasic(DyeColor.RED);
                addBasic(DyeColor.GREEN);
                addBasic(DyeColor.BLUE);
                addBasic(DyeColor.YELLOW);
                addBasic(DyeColor.WHITE);
                addBasic(DyeColor.BLACK);
                break;
            }
            case 1: {
                // color Pack
                // pieces in 3 colours
                addAll(DyeColor.RED);
                addAll(DyeColor.GREEN);
                addAll(DyeColor.BLUE);
                break;
            }
            case 2: {
                // color Pack
                // pieces in 3 colours
                addAll(DyeColor.ORANGE);
                addAll(DyeColor.YELLOW);
                addAll(DyeColor.LIME);
                break;
            }
            case 3: {
                // color Pack
                // pieces in 3 colours
                addAll(DyeColor.PINK);
                addAll(DyeColor.PURPLE);
                addAll(DyeColor.WHITE);
                break;
            }
            case 4: {
                // color Pack
                // pieces in 3 colours
                addAll(DyeColor.LIGHT_GRAY);
                addAll(DyeColor.GRAY);
                addAll(DyeColor.BLACK);
                break;
            }
            case 5: {
                for (int i=0; i<27; i++) {
                    addRandom();
                }
            }
        }
    }

    private void add(ItemStack stack) {
        int slot = s_addIndex++;
        if (slot < s_addInventory.getSizeInventory()) {
            s_addInventory.setInventorySlotContents(slot, stack);
        }
    }

    private void addBasic(DyeColor color) {
        add(BrickHelper.createBrickStack(color, 1, 2, 24));
        add(BrickHelper.createBrickStack(color, 1, 4, 24));
        add(BrickHelper.createBrickStack(color, 2, 2, 24));
        add(BrickHelper.createBrickStack(color, 2, 4, 24));
    }

    private void addAll(DyeColor color) {
        add(BrickHelper.createBrickStack(color, 1, 1, 24));
        add(BrickHelper.createBrickStack(color, 1, 2, 24));
        add(BrickHelper.createBrickStack(color, 1, 3, 24));
        add(BrickHelper.createBrickStack(color, 1, 4, 24));
        add(BrickHelper.createBrickStack(color, 1, 6, 24));
        add(BrickHelper.createBrickStack(color, 2, 2, 24));
        add(BrickHelper.createBrickStack(color, 2, 3, 24));
        add(BrickHelper.createBrickStack(color, 2, 4, 24));
        add(BrickHelper.createBrickStack(color, 2, 6, 24));
    }

    private void addRandom() {
        Random random = new Random();
        add(BrickHelper.createBrickStack(
                random.nextBoolean(),
                random.nextBoolean(),
                random.nextBoolean(),
                DyeColor.values()[random.nextInt(DyeColor.values().length)].getColorValue(),
                random.nextInt(2) + 1,
                random.nextInt(6) + 1,
                24
        ));
    }
}
